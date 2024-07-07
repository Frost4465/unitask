package com.chuan.taskmanagement.mapper;

import com.chuan.taskmanagement.dto.DropdownResponse;
import com.chuan.taskmanagement.dto.user.ProfileResponse;
import com.chuan.taskmanagement.entity.AppUser;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public ProfileResponse toResponse(AppUser appUser) {
        if (Objects.isNull(appUser)) {
            return null;
        }

        ProfileResponse profileResponse = new ProfileResponse();
        profileResponse.setName(appUser.getName());
        profileResponse.setId(appUser.getId());
        return profileResponse;
    }
}
