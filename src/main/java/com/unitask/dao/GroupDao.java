package com.unitask.dao;

import com.unitask.entity.Group;
import com.unitask.repository.GroupRepository;
import com.unitask.util.PageUtil;
import io.micrometer.common.util.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

@Service
public class GroupDao {

    @Autowired
    private GroupRepository groupRepository;

    public Group save(Group group) {
        if (group == null) {
            return null;
        }
        return groupRepository.save(group);
    }

    public Group findById(Long id) {
        if (id == null) {
            return null;
        }
        return groupRepository.findById(id).orElse(null);
    }

    public Page<Group> getList(String search, Pageable pageable) {
        String filter = search;
        if (StringUtils.isNotBlank(search)) {
            filter = PageUtil.likeSearch(search);
        } else if (StringUtils.isBlank(search)) {
            filter = null;
        }
        return groupRepository.findByName(filter, pageable);
    }

}
