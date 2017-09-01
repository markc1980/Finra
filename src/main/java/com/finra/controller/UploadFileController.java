package com.finra.controller;

import com.finra.dto.FileMetaDataDto;
import com.finra.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by markchin on 8/29/17.
 */
@RestController
public class UploadFileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/uploadFile")
    public void uploadFile(@RequestPart("file") MultipartFile multipartFile,
                           @RequestPart("fileMetaData")FileMetaDataDto fileMetaDataDto){
        fileService.saveFileData(fileMetaDataDto, multipartFile);
    }
}
