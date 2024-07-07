package com.chuan.taskmanagement.util;

import com.chuan.taskmanagement.dto.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageUtil {

    public static Pageable getPage(PageRequest pageRequest) {
        return org.springframework.data.domain.PageRequest.of(pageRequest.getPage() - 1, pageRequest.getPageSize());
    }
}
