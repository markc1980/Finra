package com.finra.dto;

/**
 * Created by markchin on 8/29/17.
 */
public class FileMetaDataDto {

    private String fileName;

    private Long fileSize;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }
}
