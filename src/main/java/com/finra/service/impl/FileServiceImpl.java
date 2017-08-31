package com.finra.service.impl;

import com.finra.dto.FileMetaDataDto;
import com.finra.model.FileMetaData;
import com.finra.repo.FileRepo;
import com.finra.service.FileService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.Date;

/**
 * Created by mchin on 8/30/2017.
 */
@Service
@Transactional
public class FileServiceImpl implements FileService{

    private static final int EOF = -1;
    @Autowired
    private FileRepo fileRepo;

    @Value("${data.storage.location}")
    private String fileLocation;

    @Override
    public String saveFileData(FileMetaDataDto fileMetaDataDto, MultipartFile multipart) {
        try {
            byte[] buffer = new byte[1024 * 8];
            final InputStream is = multipart.getInputStream();
            final OutputStream os = new FileOutputStream(
                    new File(fileLocation, multipart.getOriginalFilename()));

            MessageDigest md = MessageDigest.getInstance("MD5");
            DigestInputStream dis = new DigestInputStream(is, md);
            try {
                int n;
                while (EOF != (n = dis.read(buffer))) {
                    os.write(buffer, 0, n);
                }
            } finally {
                IOUtils.closeQuietly(dis);
                IOUtils.closeQuietly(is);
                IOUtils.closeQuietly(os);
            }

            final String checkSum = DatatypeConverter.printHexBinary(md.digest());
            System.out.println("DatatypeConverter.printHexBinary(md.digest()) = "+ checkSum);

            FileMetaData fileMetaData = new FileMetaData(
                    multipart.getOriginalFilename(), multipart.getSize(),
                    new java.sql.Date(new Date().getTime()),
                    fileLocation, fileMetaDataDto.getOwner(), checkSum );
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
    public Page<FileMetaData> searchFiles(String fileName, String owner, Date startDate, Date endDate, Pageable pageable) {
        return fileRepo.findByFileNameContainingAndOwnerAndUploadDateBetween(fileName, owner.toUpperCase(), startDate, endDate,pageable);
    }

    @Override
    public FileMetaData getMetaData(String fileId) {
        return fileRepo.findOne(fileId);
    }

    private InputStream openFile(FileMetaData fileMetaData) throws FileNotFoundException {
        return new FileInputStream(fileMetaData.getFileLocation());
    }
}
