package com.springAWS.demo.filestore;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.springAWS.demo.bucket.BucketName;
import com.springAWS.demo.profile.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileStore {

    private final AmazonS3 s3;
    private static final String bucketName = "profile-image-upload-bucket";

    @Autowired
    public FileStore(AmazonS3 s3) {
        this.s3 = s3;
    }

    public void save(String path,
                     String fileName,
                     Optional<Map<String, String>> optionalMetadata,
                     InputStream inputStream,
                     MultipartFile file) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());

        optionalMetadata.ifPresent(map -> {
            if (!map.isEmpty()) {
                map.forEach(metadata::addUserMetadata);
            }
        });

        if (!s3.doesObjectExist(bucketName, path + "/")) {
            s3.putObject(bucketName, path + "/", "");
        }

//        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        s3.putObject(bucketName, path + "/" + fileName, inputStream,metadata);

    }

    public byte[] download(UserProfile user) {
        try {
            String userFolder = "" + user.getUserProfileId();
            String userImageLink  = user.getUserProfileImageLink().get();
            String s3Key = userFolder + "/" + userImageLink;
            System.out.println(s3Key);

            S3Object object = s3.getObject("profile-image-upload-bucket", s3Key);
            System.out.println("object received" + object);

            return IOUtils.toByteArray(object.getObjectContent());
        } catch (AmazonServiceException | IOException e) {
            throw new IllegalStateException("Failed to download file to s3", e);
        }
    }
}