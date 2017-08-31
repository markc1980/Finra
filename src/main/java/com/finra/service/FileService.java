package com.finra.service;

import com.finra.dto.FileMetaDataDto;
import com.finra.model.FileMetaData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.util.Date;

/**
 * Created by mchin on 8/30/2017.
 */
public interface FileService {

    String saveFileData(FileMetaDataDto fileMetaDataDto, MultipartFile is);

    void downloadFileData(String fileId, OutputStream os);

    Page<FileMetaData> searchFiles(String fileName, String owner, Date startDate, Date endDate, Pageable pageable);

    FileMetaData getMetaData(String fileId);
}
