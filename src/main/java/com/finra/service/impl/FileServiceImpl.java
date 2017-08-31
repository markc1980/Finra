package com.finra.service.impl;

import com.finra.dto.FileMetaDataDto;
import com.finra.model.FileMetaData;
import com.finra.repo.FileRepo;
import com.finra.service.FileService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

    @Value("${data.storage.location}")
    private String fileLocation;

    @Override
    public String saveFileData(FileMetaDataDto fileMetaDataDto, MultipartFile multipart) {
        FileMetaData fileMetaData = null;
        try {

            IOUtils.copyLarge(multipart.getInputStream(), new FileOutputStream(
                    new File(fileLocation, multipart.getOriginalFilename())));

            String checksum = DigestUtils.md5Hex(multipart.getInputStream());
            fileMetaData = new FileMetaData(
                    multipart.getOriginalFilename(), multipart.getSize(),
                    new java.sql.Date(new Date().getTime()),
                    fileLocation, checksum );
            fileRepo.save(fileMetaData);
            return fileMetaData.getId();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void downloadFileData(String fileId, OutputStream os) {
        FileMetaData fileMetaData = fileRepo.findOne(fileId);
        try(final InputStream myFile = openFile(fileMetaData)) {
            IOUtils.copy(myFile, os);
        }catch(Exception e){
            throw new RuntimeException(e);
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
