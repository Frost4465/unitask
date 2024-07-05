package com.chuan.taskmanagement.mapper;

import com.chuan.taskmanagement.dto.DropdownResponse;
import com.chuan.taskmanagement.entity.AppUser;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserMapper {

    public List<DropdownResponse> toDropdown(List<AppUser> appUserList) {
        if (CollectionUtils.isEmpty(appUserList)) {
            return null;
        }

        List<DropdownResponse> dropdownResponseList = new ArrayList<>();
        for (AppUser appUser : appUserList) {
            DropdownResponse dropdownResponse = new DropdownResponse();
            dropdownResponse.setId(appUser.getId());
            dropdownResponse.setLabel(appUser.getName());
            dropdownResponseList.add(dropdownResponse);
        }

        return dropdownResponseList;
    }
}
