package com.unitask.dao;

import com.unitask.entity.GroupMember;
import com.unitask.repository.GroupMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

@Service
public class GroupMemberDao {

    @Autowired
    GroupMemberRepository groupMemberRepository;

    public List<GroupMember> saveAll(List<GroupMember> groupMembers) {
        if (CollectionUtils.isEmpty(groupMembers)) {
            return null;
        }
        return groupMemberRepository.saveAll(groupMembers);
    }

    public void deleteAll(Collection<GroupMember> groupMembers) {
        if (CollectionUtils.isEmpty(groupMembers)) {
            return;
        }
        groupMemberRepository.deleteAll(groupMembers);
    }

}
