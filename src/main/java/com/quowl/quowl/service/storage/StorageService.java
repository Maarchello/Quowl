package com.quowl.quowl.service.storage;

import com.quowl.quowl.domain.logic.user.User;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@PropertySource("classpath:properties/s3.properties")
@Service
public class StorageService {

    @Resource
    Environment env;

    private final static String IMAGES_PATH = "images/";
    private final static String DEFAULT_AVATAR = "nonava.png";

    public String getAvatarUrl(User user) {
        String url = env.getRequiredProperty("s3.url");
        if (user.getFilename() != null && !user.getFilename().isEmpty()) {
            return url + IMAGES_PATH + user.getNickname() + "/" + user.getFilename();
        } else {
            return url + IMAGES_PATH + DEFAULT_AVATAR;
        }
    }

}
