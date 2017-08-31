package com.finra.dto;

import java.util.Date;

/**
 * Created by markchin on 8/29/17.
 */
public class FileMetaDataDto {

    private String owner;

    private String permissions;

    public FileMetaDataDto() {
    }

    public FileMetaDataDto(String owner, String permissions) {
        this.owner = owner;
        this.permissions = permissions;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }
}
