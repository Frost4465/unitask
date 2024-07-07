package com.chuan.taskmanagement.util;

import com.chuan.taskmanagement.dto.PageRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;

public class PageUtil {

    public static Pageable pageable(PageRequest pageRequest) {
        return org.springframework.data.domain.PageRequest.of(pageRequest.getPage() - 1, pageRequest.getPageSize());
    }

    public static String likeSearch(String string) {
        if (StringUtils.isBlank(string)) {
            return null;
        } else {
            return "%" + string + "%";
        }
    }
}
