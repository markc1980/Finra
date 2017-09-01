package com.finra.controller;

import com.finra.model.FileMetaData;
import com.finra.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.OutputStream;

/**
 * Created by mchin on 8/30/2017.
 */
@RestController
public class DownloadFileController {

    @Autowired
    private FileService fileService;

    @GetMapping(value = "/downloadFile/{fileId}",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void download(OutputStream output, @PathVariable("fileId") String fileId) {
        fileService.downloadFileData(fileId, output);
    }

    @GetMapping(value = "/downloadFileMetaData/{fileId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public FileMetaData download(@PathVariable("fileId") String fileId) {
        return fileService.getMetaData(fileId);
    }

}
