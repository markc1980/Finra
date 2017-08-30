package com.finra.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by markchin on 8/29/17.
 */
@RestController
public class FileUploadController {

    @PostMapping("/uploadFile")
    public void uploadFile(@RequestParam("file") MultipartFile multipartFile){
        try {
            IOUtils.copyLarge(multipartFile.getInputStream(), new FileOutputStream(new File(multipartFile.getName())));
            System.out.println("In here");
        }catch(Exception e){

        }

    }
}
