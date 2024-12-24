package com.unitask.service;

import com.unitask.dto.group.GroupRequest;
import com.unitask.dto.group.GroupResponse;

public interface GroupService {

    void createGroup(GroupRequest groupRequest);

    void updateGroup(Long id, GroupRequest groupRequest);

    GroupResponse getGroup(Long id);

}
