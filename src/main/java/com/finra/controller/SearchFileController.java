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
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * Created by mchin on 8/30/2017.
 */
@RestController
public class SearchFileController {

    @Autowired
    private FileService fileService;

    @GetMapping(value = "/searchFiles/{fileName}/owner/{owner}/startDate/{startDate}/endDate/{endDate}" )
    public Page<FileMetaData> searchFileMetaData(@PathVariable("fileName") String fileName,
                                                 @PathVariable("owner") String owner,
                                   @PathVariable("startDate") String startDateParam,
                                   @PathVariable("endDate") String endDateParam,
                                   Pageable pageable){


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(startDateParam, formatter);
        Instant instant = start.atStartOfDay().atZone(ZoneId.systemDefault())
                .toInstant();
        Date startDate = Date.from(instant);

        LocalDate end = LocalDate.parse(endDateParam, formatter);
         Instant einstant = end.atStartOfDay().atZone(ZoneId.systemDefault())
                .toInstant();
        Date endDate = Date.from(einstant);


        Page<FileMetaData> result =  fileService.searchFiles(fileName, owner, startDate, endDate, pageable);
        return result;
    }
}
