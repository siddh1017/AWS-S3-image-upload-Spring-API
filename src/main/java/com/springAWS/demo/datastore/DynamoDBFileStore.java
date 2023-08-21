package com.springAWS.demo.datastore;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.springAWS.demo.profile.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.*;

@Repository
@Service
public class DynamoDBFileStore {
    @Autowired
    public DynamoDBMapper dynamoDBMapper;

    // get all user profiles
    public List<UserProfile> getUserProfiles() {
        return dynamoDBMapper.scan(UserProfile.class, new DynamoDBScanExpression());
    }

    // add User
    public String addUser(UserProfile userProfile) {
        dynamoDBMapper.save(userProfile);
        return "profile saved successfully !!";
    }

    // fetching userById
    public UserProfile getUser(String userId) {
        try {
            return dynamoDBMapper.load(UserProfile.class, userId);
        } catch (com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMappingException e) {
            System.err.println("UserProfile with ID " + userId + " not found.");
            return null;
        }
    }

    // deleting userProfile
    public String deleteUser(String userId) {
        UserProfile userProfileToDelete = new UserProfile();
        userProfileToDelete.setUserProfileId(userId);

        try {
            dynamoDBMapper.delete(userProfileToDelete);
            return "UserProfile deleted successfully";

        } catch (com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMappingException e) {
            return "UserProfile with ID " + userId + " not found. Unable to delete";
        }
    }

    // update userProfile
    public String updateUserProfile(UserProfile userProfile) {
        UserProfile existingUserProfile = dynamoDBMapper.load(UserProfile.class, userProfile.getUserProfileId());

        if (existingUserProfile == null) {
            return "No such user exists with ID: " + userProfile.getUserProfileId();
        }
        try {
            dynamoDBMapper.save(userProfile);
            return "UserProfile updated successfully.";
        } catch (com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMappingException e) {
            return "Error updating UserProfile: " + e.getMessage();
        }
    }
}
