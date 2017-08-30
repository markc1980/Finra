package com.finra.dto;

import java.util.Date;

/**
 * Created by mchin on 8/30/2017.
 */
public class SearchFileMetaDataRequestDto {

    private String fileName;

    private Date startUploadDate;

    private Date endUploadDate;

    private int offset;

    private int maxResults;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getStartUploadDate() {
        return startUploadDate;
    }

    public void setStartUploadDate(Date startUploadDate) {
        this.startUploadDate = startUploadDate;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public Date getEndUploadDate() {
        return endUploadDate;
    }

    public void setEndUploadDate(Date endUploadDate) {
        this.endUploadDate = endUploadDate;
    }
}
