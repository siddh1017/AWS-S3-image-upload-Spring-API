package com.springAWS.demo.datastore;

import com.springAWS.demo.profile.UserProfile;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@Service
public class FakeUserProfileDataStore {
    private static final List<UserProfile>  USER_PROFILES = new ArrayList<>();

    static {
         USER_PROFILES.add(new UserProfile(UUID.fromString("d984885f-fee8-4d11-9739-c7d7f5e61248"), "siddh", null));
         USER_PROFILES.add(new UserProfile(UUID.fromString("2c6bb1e2-9770-455c-b0af-56769f29fe46"), "supan", null));
    }

    public List<UserProfile> getUserProfiles() {
        return USER_PROFILES;
    }
}
