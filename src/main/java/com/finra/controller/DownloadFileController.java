package com.finra.controller;

import com.finra.model.FileMetaData;
import com.finra.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by mchin on 8/30/2017.
 */
@RestController
public class DownloadFileController {

    @Autowired
    private FileService fileService;

    @GetMapping(value = "/downloadFile",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void download(OutputStream output, @PathVariable("fileId") String fileId) throws IOException {
        fileService.downloadFileData(fileId, output);
    }

    @GetMapping(value = "/downloadMetaData",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public FileMetaData download(@PathVariable("fileId") String fileId) throws IOException {
        return fileService.getMetaData(fileId);
    }

}
