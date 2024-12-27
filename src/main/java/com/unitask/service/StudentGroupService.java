package com.unitask.service;

import com.unitask.dto.group.GroupResponse;
import org.springframework.web.multipart.MultipartFile;

public interface StudentGroupService {

    void joinGroup(Long id);

    void submitGroup(Long id, MultipartFile multipartFile);

    void leaveGroup(Long id);

    GroupResponse getGroup(Long id);

}
