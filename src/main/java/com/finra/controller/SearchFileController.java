package com.finra.controller;

import com.finra.dto.SearchFileMetaDataRequestDto;
import com.finra.dto.SearchFileMetaDataResponseDto;
import com.finra.model.FileMetaData;
import com.finra.service.FileService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Created by mchin on 8/30/2017.
 */
@RestController
public class SearchFileController {

    @Autowired
    private FileService fileService;

    @GetMapping("/searchFiles")
    public Page<FileMetaData> searchFileMetaData(@PathVariable("fileName") String fileName,
                                   @PathVariable("startDate") Date startDate,
                                   @PathVariable("endDate") Date endDate,
                                   Pageable pageable){
        return fileService.searchFiles(fileName, startDate, endDate, pageable);
    }
}
