package com.quowl.quowl.service.system;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.quowl.quowl.domain.logic.user.User;
import com.quowl.quowl.service.user.UserService;
import com.quowl.quowl.utils.SecurityUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@PropertySource("classpath:properties/db.properties")
@Service
public class FileStorageService {
    Logger log = LoggerFactory.getLogger(FileStorageService.class);

    @Inject private ResourceLoader resourceLoader;
    @Inject private UserService userService;

    @Resource
    Environment env;

    private final static String ACCESS_KEY_ID = "AKIAJVGAIJ3IYQ67J7QA";
    private final static String SECRET_KEY = "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";
    private final static String BUCKET_NAME = "elasticbeanstalk-us-west-2-755724412002";
    private final static String IMAGES_PATH = "images/";

    private AWSCredentials getCredentials() {
        return new BasicAWSCredentials(ACCESS_KEY_ID, SECRET_KEY);
    }

    public boolean uploadImage(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        User user = userService.getByNickname(SecurityUtils.getCurrentLogin());
        String filename = user.getNickname() + "." + extension;
        AmazonS3 client = new AmazonS3Client(getCredentials());
        try {
            InputStream is = file.getInputStream();
            client.putObject(new PutObjectRequest(BUCKET_NAME, IMAGES_PATH + user.getNickname() + "/" + filename, is, new ObjectMetadata()).withCannedAcl(CannedAccessControlList.PublicRead));
            user.setFilename(filename);
            userService.save(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

}
