package com.finra.repo;

import com.finra.model.FileMetaData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by mchin on 8/30/2017.
 */
@Repository
public interface FileRepo extends CrudRepository<FileMetaData, String> {

    Page<FileMetaData> findByfileNameContainingAndUploadDateBetween(String name, Date startDate, Date endDate, Pageable pageable);

}
