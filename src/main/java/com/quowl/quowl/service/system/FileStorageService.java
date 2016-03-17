package com.quowl.quowl.service.system;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileStorageService {

    public boolean uploadImage(MultipartFile file, String nickname) {
        try {
            FileUtils.writeByteArrayToFile(new File("D:/Project/ava/" + nickname + "/" + file.getOriginalFilename()), file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public byte[] getImage(String nickname) {
        byte[] img = null;
        try {
            File file = new File("D:/Project/ava/" + nickname + "/whale-shark.jpg");
            img = FileUtils.readFileToByteArray(file);
        } catch (IOException e ){
            e.printStackTrace();
        }
        return img;
    }

}
