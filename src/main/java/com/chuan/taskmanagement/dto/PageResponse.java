package com.chuan.taskmanagement.dto;

import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;

public class PageResponse<T> extends PagedModel<T> {
    public PageResponse(Page<T> page) {
        super(page);
    }

    public boolean isLast() {
        return getMetadata().number() == getMetadata().totalPages();
    }

    public boolean isFirst() {
        return getMetadata().number() == 0;
    }
}
