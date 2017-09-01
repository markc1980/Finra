package com.finra.controller;

import com.finra.model.FileMetaData;
import com.finra.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

/**
 * Created by mchin on 8/30/2017.
 */
@RestController
public class SearchFileController {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    private FileService fileService;

    @GetMapping(value = "/searchFiles/{fileName}/owner/{owner}/startDate/{startDate}/endDate/{endDate}" )
    public Page<FileMetaData> searchFileMetaData(@PathVariable("fileName") String fileName,
                                                 @PathVariable("owner") String owner,
                                   @PathVariable("startDate") String startDateParam,
                                   @PathVariable("endDate") String endDateParam,
                                   Pageable pageable){


        Date startDate = getDate(startDateParam);
        Date endDate = getDate(endDateParam);

        Page<FileMetaData> result =  fileService.searchFiles(fileName, owner, startDate, endDate, pageable);
        return result;
    }

    private Date getDate(@PathVariable("startDate") String date) {
        try {
            LocalDate start = LocalDate.parse(date, formatter);
            Instant instant = start.atStartOfDay().atZone(ZoneId.systemDefault())
                    .toInstant();

            return Date.from(instant);
        }catch(DateTimeParseException e){
            throw new IllegalArgumentException("Invalid date "+date, e);
        }
    }
}
