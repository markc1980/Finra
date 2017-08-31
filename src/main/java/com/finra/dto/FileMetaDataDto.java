package com.finra.dto;

import java.util.Date;

/**
 * Created by markchin on 8/29/17.
 */
public class FileMetaDataDto {

    private String fileName;

    private Date uploadDate;

    private String description;

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
