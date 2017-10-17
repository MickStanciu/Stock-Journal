package com.example.common.rest.dto;

import java.io.Serializable;

public class PaginationDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int offset;
    private int limit;
    private int totalCount;

    //todo: not implemented
//    private String nextPage;
//    private String currentPage;
//    private String previousPage;

    public PaginationDto(int offset, int limit, int totalCount) {
        this.offset = offset;
        this.limit = limit;
        this.totalCount = totalCount;
    }

    public PaginationDto() {
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

    public int getTotalCount() {
        return totalCount;
    }
}
