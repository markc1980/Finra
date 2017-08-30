package com.finra.dto;

import com.finra.model.FileMetaData;

import java.util.List;

/**
 * Created by mchin on 8/30/2017.
 */
public class SearchFileMetaDataResponseDto {

    private int matchingResults;
    private List<FileMetaData> results;

    public int getMatchingResults() {
        return matchingResults;
    }

    public void setMatchingResults(int matchingResults) {
        this.matchingResults = matchingResults;
    }

    public List<FileMetaData> getResults() {
        return results;
    }

    public void setResults(List<FileMetaData> results) {
        this.results = results;
    }
}
