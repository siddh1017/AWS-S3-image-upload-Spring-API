package com.springAWS.demo.bucket;

public enum BucketName {
    PROFILE_IMAGE("profile-image-upload-bucket");
    private final String bucketName;


    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }

}
