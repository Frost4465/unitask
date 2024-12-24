package com.unitask.dao;

import com.unitask.entity.Group;
import com.unitask.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
