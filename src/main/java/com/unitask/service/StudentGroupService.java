package com.unitask.service;

import com.unitask.dto.GroupMemberListDto;
import com.unitask.dto.group.GroupMemberTuple;
import com.unitask.dto.group.GroupResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StudentGroupService {

    void joinGroup(Long id);

    void submitGroup(Long id, MultipartFile multipartFile);

    void leaveGroup(Long id);

    GroupResponse getGroup(Long id);

    List<GroupMemberTuple> getGroupList(Long id);

}
