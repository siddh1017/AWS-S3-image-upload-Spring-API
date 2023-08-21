package com.springAWS.demo.profile;

import com.springAWS.demo.datastore.DynamoDBFileStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/user-profile")
@CrossOrigin("*")
public class UserProfileController {

    @Autowired
    private final UserProfileService userProfileService;
    private final DynamoDBFileStore dynamoDBFileStore;


    public UserProfileController(UserProfileService userProfileService, DynamoDBFileStore dynamoDBFileStore) {
        this.userProfileService = userProfileService;
        this.dynamoDBFileStore = dynamoDBFileStore;
    }

    @GetMapping()
    public List<UserProfile> getUserProfiles() {
        return userProfileService.getUserProfiles();
    }

    @PostMapping(
            value = "{userProfileId}/image/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void uploadUserProfileImage(@PathVariable("userProfileId") String userProfileId,
                                       @RequestParam("file") MultipartFile file) {
        userProfileService.uploadUserProfileImage(userProfileId, file);
    }

    @GetMapping("{userProfileId}/image/download")
    public byte[] downloadUserProfileImage(@PathVariable("userProfileId") String userProfileId) {
        System.out.println("called");
        return userProfileService.downloadUserProfileImage(userProfileId);
    }

    // ------------>
    @GetMapping("{userId}")
    public UserProfile getUserById(@PathVariable("userId") String userId) {
        return dynamoDBFileStore.getUser(userId);
    }

    @PostMapping("saveUserProfile")
    public String saveUser(@RequestBody UserProfile userProfile) {
        System.out.println("user adding method called");
        return dynamoDBFileStore.addUser(userProfile);
    }

    @DeleteMapping("{userId}/deleteUser")
    public String deleteUser(@PathVariable("userId") String userId) {
        return dynamoDBFileStore.deleteUser(userId);
    }

    @PutMapping("updateUser")
    public String updateUser(@RequestBody UserProfile userProfile) {
        return dynamoDBFileStore.updateUserProfile(userProfile);
    }
}
