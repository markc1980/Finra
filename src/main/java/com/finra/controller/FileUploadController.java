package com.finra.controller;

import com.finra.dto.FileMetaDataDto;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.bind.annotation.*;
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
    public void uploadFile(@RequestPart("file") MultipartFile multipartFile,
                           @RequestPart("fileMetaData")FileMetaDataDto fileMetaDataDto){
        try {
            System.out.println("File Meta Data "+fileMetaDataDto);
            IOUtils.copyLarge(multipartFile.getInputStream(), new FileOutputStream(new File(multipartFile.getOriginalFilename())));
            System.out.println("In here");
        }catch(Exception e){

        }

    }
}
