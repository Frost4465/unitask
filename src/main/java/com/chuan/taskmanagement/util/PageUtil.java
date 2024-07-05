package com.chuan.taskmanagement.util;

import com.chuan.taskmanagement.dto.BaseViewOption;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageUtil {

    public static Pageable getPage(BaseViewOption baseViewOption) {
        return PageRequest.of(baseViewOption.getPage() - 1, baseViewOption.getPageSize());
    }
}
