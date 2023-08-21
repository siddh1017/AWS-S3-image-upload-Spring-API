package com.springAWS.demo.profile;

import com.springAWS.demo.datastore.DynamoDBFileStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserProfileDataAccessService {
    @Autowired
    private final DynamoDBFileStore dynamoDBFileStore;

    UserProfileDataAccessService(DynamoDBFileStore dynamoDBFileStore) {
        this.dynamoDBFileStore = dynamoDBFileStore;
    }

    public List<UserProfile> getUserProfiles() {
        return dynamoDBFileStore.getUserProfiles();
    }
}
