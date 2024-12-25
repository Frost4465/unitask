package com.unitask.service;

import com.unitask.dto.PageRequest;
import com.unitask.dto.group.GroupRequest;
import com.unitask.dto.group.GroupResponse;
import com.unitask.entity.Group;
import com.unitask.util.PageWrapperVO;
import org.springframework.data.domain.Page;

public interface GroupService {

    void createGroup(GroupRequest groupRequest);

    void updateGroup(Long id, GroupRequest groupRequest);

    GroupResponse getGroup(Long id);

    PageWrapperVO getList(PageRequest pageRequest);

}
