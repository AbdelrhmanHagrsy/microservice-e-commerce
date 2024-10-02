package com.abdelrahman.productservice.service;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class AmazonS3Service {

    private final AmazonS3 amazonS3;

    @Value("${spring.application.bucket.name}")
    private String bucketName;


    public String uploadFile(MultipartFile multipartPart) {

        File file = convertMultiPartFileToFile(multipartPart);
        String fileName = System.currentTimeMillis() + "_" + file.getName();
        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file));

        return fileName;

    }

    public byte[] downloadFile(String fileName) {
        S3Object s3Object = amazonS3.getObject(bucketName, fileName);
        S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent();

        try {
            byte[] content = IOUtils.toByteArray(s3ObjectInputStream);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteFile(String fileName){
        amazonS3.deleteObject(bucketName,fileName);
        log.info("File name {} deleted from aws successfully",fileName);
    }
    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }
}
