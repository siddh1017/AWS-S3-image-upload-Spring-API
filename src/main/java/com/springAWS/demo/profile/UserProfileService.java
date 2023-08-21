package com.springAWS.demo.profile;

import com.springAWS.demo.datastore.DynamoDBFileStore;
import com.springAWS.demo.filestore.FileStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.*;

@Service
public class UserProfileService {

    private final UserProfileDataAccessService userProfileDataAccessService;
    private final FileStore fileStore;
    private final DynamoDBFileStore dynamoDBFileStore;

    @Autowired
    public UserProfileService(UserProfileDataAccessService userProfileDataAccessService,
                              FileStore fileStore, DynamoDBFileStore dynamoDBFileStore) {
        this.userProfileDataAccessService = userProfileDataAccessService;
        this.fileStore = fileStore;
        this.dynamoDBFileStore = dynamoDBFileStore;
    }

    List<UserProfile> getUserProfiles() {
        return userProfileDataAccessService.getUserProfiles();
    }

    void uploadUserProfileImage(String userProfileId, MultipartFile file) {
        // 1. Check if image is not empty
        isFileEmpty(file);
        // 2. If file is an image
        isImage(file);

        // 3. The user exists in our database
        UserProfile user = getUserProfileOrThrow(userProfileId);

        // 4. Grab some metadata from file if any
        Map<String, String> metadata = extractMetadata(file);

        // 5. Store the image in s3 and update database (userProfileImageLink) with s3 image link
        String path = String.format("%s", user.getUserProfileId());
        String filename = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());

        try {
            fileStore.save(path, filename, Optional.of(metadata), file.getInputStream(), file);
            user.setUserProfileImageLink(filename);

            // changes to make
            dynamoDBFileStore.updateUserProfile(user);


        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

    }

    byte[] downloadUserProfileImage(String userProfileId) {
        UserProfile user = getUserProfileOrThrow(userProfileId);
        return fileStore.download(user);
//        String path = String.format("%s",user.getUserProfileId());
//        return user.getUserProfileImageLink()
//                .map(key -> fileStore.download(path, key))
//                .orElse(new byte[0]);

    }

    private Map<String, String> extractMetadata(MultipartFile file) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        return metadata;
    }

    private UserProfile getUserProfileOrThrow(String userProfileId) {
        return userProfileDataAccessService
                .getUserProfiles()
                .stream()
                .filter(userProfile -> userProfile.getUserProfileId().equals(userProfileId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("User profile %s not found", userProfileId)));
    }

    private void isImage(MultipartFile file) {
        if (!Arrays.asList(
                IMAGE_JPEG.getMimeType(),
                IMAGE_PNG.getMimeType(),
                IMAGE_GIF.getMimeType()).contains(file.getContentType())) {
            throw new IllegalStateException("File must be an image [" + file.getContentType() + "]");
        }
    }

    private void isFileEmpty(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file [ " + file.getSize() + "]");
        }
    }

}