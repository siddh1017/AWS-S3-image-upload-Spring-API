package com.springAWS.demo.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonConfig {
    @Bean
    public AmazonS3 s3() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(
                "AKIA2EFUHTUDLKKIIDN2",
                "/OyOFXQ3KeOxNvJf8mIZ9fq7m/pVhXtPtCcClIVS"
        );
        return AmazonS3ClientBuilder.
                standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion("ap-south-1")
                .build();
    }

    @Bean
    public DynamoDBMapper mapper() {
        return new DynamoDBMapper(amamzonDynamoDBConfig());
    }

    private AmazonDynamoDB amamzonDynamoDBConfig() {
        return AmazonDynamoDBClientBuilder
                .standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(
                                "dynamodb.ap-south-1.amazonaws.com", "ap-south-1"
                        )
                )
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(
                                "AKIA2EFUHTUDLKKIIDN2", "/OyOFXQ3KeOxNvJf8mIZ9fq7m/pVhXtPtCcClIVS"
                        )
                ))
                .build();
    }
}
