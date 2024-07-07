package com.chuan.taskmanagement.util;

import com.chuan.taskmanagement.dto.PageRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;

import java.util.Objects;

public class PageUtil {

    public static Pageable pageable(PageRequest request) {
        if (Objects.isNull(request)) {
            return org.springframework.data.domain.PageRequest.of(0, 15);
        }
        return org.springframework.data.domain.PageRequest.of(request.getPage() - 1, request.getPageSize());
    }

    public static String likeSearch(String string) {
        if (StringUtils.isBlank(string)) {
            return null;
        } else {
            return "%" + string + "%";
        }
    }
}
