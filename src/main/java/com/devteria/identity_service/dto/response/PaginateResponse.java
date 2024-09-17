package com.devteria.identity_service.dto.response;

import java.util.List;

public class PaginateResponse<T> {
    private List<T> data;
    private long totalRows;

    public PaginateResponse(List<T> data, long totalRows) {
        this.data = data;
        this.totalRows = totalRows;
    }

    // Getters và Setters
    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public long getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(long totalRows) {
        this.totalRows = totalRows;
    }
}
