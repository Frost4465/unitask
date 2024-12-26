package com.unitask.service.impl;

import com.unitask.dao.AppUserDAO;
import com.unitask.dao.GroupDao;
import com.unitask.dto.GroupMemberListDto;
import com.unitask.dto.PageRequest;
import com.unitask.dto.group.GroupRequest;
import com.unitask.dto.group.GroupResponse;
import com.unitask.dto.group.GroupStudentResponse;
import com.unitask.entity.Group;
import com.unitask.entity.User.AppUser;
import com.unitask.exception.ServiceAppException;
import com.unitask.mapper.GroupMapper;
import com.unitask.service.GroupService;
import com.unitask.util.PageUtil;
import com.unitask.util.PageWrapperVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private AppUserDAO appUserDAO;

    @Override
    public void createGroup(GroupRequest groupRequest) {
        List<AppUser> appUsers = appUserDAO.findByIds(groupRequest.getGroupMemberIds());

        Group group = groupDao.save(GroupMapper.INSTANCE.toEntity(groupRequest));
        //TODO FIX THIS SHIT, LINK STUDENT ASS TO GROUP
//        groupMemberDao.saveAll(appUsers.stream().map(user -> {
//            return GroupMemberMapper.INSTANCE.toEntity(user, group);
//        }).toList());
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

    @Override
    public PageWrapperVO getList(PageRequest pageRequest) {
        Pageable pageable = PageUtil.pageable(pageRequest);
        Page<Group> groupAssessmentTuple = groupDao.getList(pageRequest.getSearch(), pageable);
        List<GroupResponse> groupResponseList = groupAssessmentTuple.getContent().stream().map(group -> {
            GroupResponse groupResponse = new GroupResponse();
            groupResponse.setId(group.getId());
            groupResponse.setName(group.getName());
            groupResponse.setDescription(group.getDescription());

            //TODO FIX THIS SHIT, LINK STUDENT ASS TO GROUP
//            groupResponse.setGroupMemberList(group.getGroupMembers().stream().map(member -> {
//                GroupMemberListDto groupMemberListDto = new GroupMemberListDto();
//                groupMemberListDto.setId(member.getId());
//                groupMemberListDto.setName(member.getAppUser().getName());
//                return groupMemberListDto;
//            }).toList());
            return groupResponse;
        }).toList();
        return new PageWrapperVO(groupAssessmentTuple, groupResponseList);
    }

    @Override
    public List<GroupStudentResponse> getStudentListing() {
        return appUserDAO.findStudents().stream().map(user -> {
            GroupStudentResponse groupStudentResponse = new GroupStudentResponse();
            groupStudentResponse.setId(user.getId());
            groupStudentResponse.setName(user.getName());
            return groupStudentResponse;
        }).toList();
    }

    private void updateGroup(Group group, List<Long> groupId) {
        List<AppUser> appUser = appUserDAO.findByIds(groupId);
        if (CollectionUtils.isEmpty(appUser)) {
            throw new ServiceAppException(HttpStatus.BAD_REQUEST, "Group must have at least one member");
        }

        //TODO FIX THIS SHIT, LINK STUDENT ASS TO GROUP
//        Map<Long, GroupMember> groupMemberHashMap =
//                group.getGroupMembers().stream().collect(Collectors.toMap(x -> x.getAppUser().getId(), x -> x));
//        List<GroupMember> groupMemberList = new ArrayList<>();
//        groupId.forEach(id -> {
//            GroupMember groupMember;
//            if (groupMemberHashMap.containsKey(id)) {
//                groupMemberList.add(groupMemberHashMap.get(id));
//                groupMemberHashMap.remove(id);
//            } else {
//                groupMember = new GroupMember();
//                AppUser user = appUserDAO.findById(id);
//                groupMember.setGroup(group);
//                groupMember.setAppUser(user);
//                groupMemberList.add(groupMember);
//            }
//        });
//        groupMemberDao.deleteAll(groupMemberHashMap.values());
//        groupMemberDao.saveAll(groupMemberList);

    }

}
