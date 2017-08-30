package com.finra.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by mchin on 8/30/2017.
 */
@Entity
@Table(name = "FILE_META_DATA",
        indexes = {@Index(name = "FILE_NAME_IDX",  columnList="FILE_NAME", unique = false),
                @Index(name = "UPLOAD_DATE_IDX", columnList="UPLOAD_DATE",     unique = false),
                @Index(name = "FILE_NAME_UPLOAD_DATE_IDX", columnList="FILE_NAME, UPLOAD_DATE",     unique = false),
        })
public class FileMetaData {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "ID")
    private String id;

    @Column(name="FILE_NAME", nullable = false)
    private String fileName;

    @Column(name="FILE_SIZE", nullable = false)
    private Long fileSize;

    @Column(name="UPLOAD_DATE", nullable = false)
    private Date uploadDate;

    @Column(name="FILE_LOCATION", nullable = false)
    private String fileLocation;

    @Column(name="CHECK_SUM", nullable = false)
    private String checksum;

    @Column(name="DESCRIPTION", nullable = true)
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
