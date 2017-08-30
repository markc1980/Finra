package com.finra.service.impl;

import com.finra.dto.FileMetaDataDto;
import com.finra.dto.SearchFileMetaDataRequestDto;
import com.finra.dto.SearchFileMetaDataResponseDto;
import com.finra.model.FileMetaData;
import com.finra.repo.FileRepo;
import com.finra.service.FileService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

/**
 * Created by mchin on 8/30/2017.
 */
@Service
@Transactional
public class FileServiceImpl implements FileService{

    @Autowired
    private FileRepo fileRepo;

    @Override
    public String saveFile(FileMetaData fileMetaData, InputStream is) {
        IOUtils.copyLarge(is, new FileOutputStream(new File(fileMetaData.getFileName())));
        fileRepo.save(fileMetaData);
        return fileMetaData.getId();
    }

    @Override
    public void downloadFile(String fileId, OutputStream os) {
        FileMetaData fileMetaData = fileRepo.findOne(fileId);
        try(final InputStream myFile = openFile(fileMetaData)) {
            IOUtils.copy(myFile, os);
        }
    }

    @Override
    public Page<FileMetaData> searchFiles(String fileName, Date startDate, Date endDate, Pageable pageable) {
        return fileRepo.findByfileNameContainingAndUploadDateBetween(fileName, startDate, endDate, pageable);
        //        Criteria criteriaCount = session.createCriteria(FileMetaData.class);
//        criteriaCount.setProjection(Projections.rowCount());
//        Long count = (Long) criteriaCount.uniqueResult();
//
//        Criteria criteria = session.createCriteria(FileMetaData.class);
//        criteria.setFirstResult(0);
//        criteria.setMaxResults(2);
//        List<FileMetaData> firstPage = criteria.list();

    }

    private InputStream openFile(FileMetaData fileMetaData) throws FileNotFoundException {
        return new FileInputStream(fileMetaData.getFileLocation());
    }
}
