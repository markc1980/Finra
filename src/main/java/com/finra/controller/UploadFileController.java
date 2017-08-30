package com.finra.controller;

import com.finra.dto.FileMetaDataDto;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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

    @PostMapping("/uploadFile2")
    public void upload(HttpServletRequest request) throws Exception {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (!isMultipart) {
            // Inform user about invalid request
            return;
        }

        //String filename = request.getParameter("name");

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload();

        // Parse the request
        try {
            FileItemIterator iter = upload.getItemIterator(request);
            while (iter.hasNext()) {
                FileItemStream item = iter.next();
                String name = item.getFieldName();
                InputStream stream = item.openStream();
                if (item.isFormField()) {
                    System.out.println("Form field " + name + " with value " + Streams.asString(stream) + " detected.");
                } else {
                    System.out.println("File field " + name + " with file name " + item.getName() + " detected.");
                    // Process the input stream
                    OutputStream out = new FileOutputStream("incoming.gz");
                    IOUtils.copy(stream, out);
                    stream.close();
                    out.close();

                }
            }
        }catch (FileUploadException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
