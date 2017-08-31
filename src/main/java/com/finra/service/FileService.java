package com.finra.service;

import com.finra.dto.FileMetaDataDto;
import com.finra.dto.SearchFileMetaDataRequestDto;
import com.finra.dto.SearchFileMetaDataResponseDto;
import com.finra.model.FileMetaData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

/**
 * Created by mchin on 8/30/2017.
 */
public interface FileService {

    String saveFile(FileMetaData fileMetaDataDto, InputStream is);

    void downloadFile(String fileId, OutputStream os);

    Page<FileMetaData> searchFiles(String fileName, Date startDate, Date endDate, Pageable pageable);
}
