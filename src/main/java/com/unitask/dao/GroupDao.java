package com.unitask.dao;

import com.unitask.constant.Enum.GeneralStatus;
import com.unitask.dto.assessment.AssessmentSubmissionTuple;
import com.unitask.dto.assessment.AssessmentTuple;
import com.unitask.dto.group.GroupTuple;
import com.unitask.entity.Group;
import com.unitask.repository.GroupRepository;
import com.unitask.util.PageUtil;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<GroupTuple> getList(String search, Pageable pageable) {
        return groupRepository.findByName(search, pageable);
    }
}
