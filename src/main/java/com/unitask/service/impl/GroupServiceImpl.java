package com.unitask.service.impl;

import com.unitask.dao.AppUserDAO;
import com.unitask.dao.GroupDao;
import com.unitask.dao.GroupMemberDao;
import com.unitask.dto.group.GroupRequest;
import com.unitask.dto.group.GroupResponse;
import com.unitask.entity.Group;
import com.unitask.entity.GroupMember;
import com.unitask.entity.User.AppUser;
import com.unitask.exception.ServiceAppException;
import com.unitask.mapper.GroupMapper;
import com.unitask.mapper.GroupMemberMapper;
import com.unitask.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupDao groupDao;
    @Autowired
    private GroupMemberDao groupMemberDao;

    @Autowired
    private AppUserDAO appUserDAO;

    @Override
    public void createGroup(GroupRequest groupRequest) {
        List<AppUser> appUsers = appUserDAO.findByIds(groupRequest.getGroupMemberIds());

        Group group = groupDao.save(GroupMapper.INSTANCE.toEntity(groupRequest));
        groupMemberDao.saveAll(appUsers.stream().map(user -> {
            return GroupMemberMapper.INSTANCE.toEntity(user, group);
        }).toList());
    }

    @Override
    public void updateGroup(Long id, GroupRequest groupRequest) {
        Group group = groupDao.findById(id);
        if (group == null) {
            throw new ServiceAppException(HttpStatus.BAD_REQUEST, "Group does not Exists");
        }
        group.setName(groupRequest.getName());
        group.setDescription(groupRequest.getDescription());
        Group savedGroup = groupDao.save(group);
        updateGroup(savedGroup, groupRequest.getGroupMemberIds());
    }

    @Override
    public GroupResponse getGroup(Long id) {
        Group group = groupDao.findById(id);
        if (group == null) {
            throw new ServiceAppException(HttpStatus.BAD_REQUEST, "Group does not Exists");
        }
        return GroupMapper.INSTANCE.toResponse(group);
    }

    private void updateGroup(Group group, List<Long> groupId) {
        List<AppUser> appUser = appUserDAO.findByIds(groupId);
        if (CollectionUtils.isEmpty(appUser)) {
            throw new ServiceAppException(HttpStatus.BAD_REQUEST, "Group must have at least one member");
        }
        Map<Long, GroupMember> groupMemberHashMap =
                group.getGroupMembers().stream().collect(Collectors.toMap(x -> x.getAppUser().getId(), x -> x));
        List<GroupMember> groupMemberList = new ArrayList<>();
        groupId.forEach(id -> {
            GroupMember groupMember;
            if (groupMemberHashMap.containsKey(id)) {
                groupMemberList.add(groupMemberHashMap.get(id));
                groupMemberHashMap.remove(id);
            } else {
                groupMember = new GroupMember();
                AppUser user = appUserDAO.findById(id);
                groupMember.setGroup(group);
                groupMember.setAppUser(user);
                groupMemberList.add(groupMember);
            }
        });
        groupMemberDao.deleteAll(groupMemberHashMap.values());
        groupMemberDao.saveAll(groupMemberList);

    }

}
