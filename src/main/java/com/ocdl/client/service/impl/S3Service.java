package com.ocdl.client.service.impl;

import com.amazonaws.auth.*;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.ocdl.client.service.StorageService;
import org.springframework.stereotype.Component;

import java.io.File;


@Component
public class S3Service implements StorageService {

    private static AmazonS3 s3client;
    private static String bucketName = "ocdl-tagged-data";

    private void createStorage() {

        if (s3client == null) {
            System.out.println("create the S3 services  ====================================");
            //AWSCredentials credentials = new BasicAWSCredentials(accesskey, secretkey);

            // change the regions if you don't use us_east_virginia
            s3client = AmazonS3ClientBuilder
                    .standard()
                    .withCredentials(new DefaultAWSCredentialsProviderChain())
                    .withRegion(Regions.US_EAST_1)
                    .build();
        }
    }

    @Override
    public void uploadObject(String dataName, File file) {

        createStorage();
        s3client.putObject(new PutObjectRequest(bucketName, dataName,file)
                .withCannedAcl(CannedAccessControlList.PublicRead)
        );
    }
}

