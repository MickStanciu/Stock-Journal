package com.example.shop.catalog.model;

import java.io.Serializable;

public class PaginationDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private final int offset;
    private final int limit;
    private final int totalCount;

    //todo: not implemented
//    private String nextPage;
//    private String currentPage;
//    private String previousPage;

    public PaginationDto(int offset, int limit, int totalCount) {
        this.offset = offset;
        this.limit = limit;
        this.totalCount = totalCount;
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
