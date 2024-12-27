package com.unitask.service.impl;

import com.unitask.constant.Enum.GeneralStatus;
import com.unitask.dao.AppUserDAO;
import com.unitask.dao.AssessmentDao;
import com.unitask.dao.GroupDao;
import com.unitask.dao.StudentAssessmentDao;
import com.unitask.dto.PageRequest;
import com.unitask.dto.group.GroupRequest;
import com.unitask.dto.group.GroupResponse;
import com.unitask.dto.group.DropdownResponse;
import com.unitask.entity.Group;
import com.unitask.entity.StudentAssessment;
import com.unitask.entity.User.AppUser;
import com.unitask.entity.assessment.Assessment;
import com.unitask.exception.ServiceAppException;
import com.unitask.mapper.GroupMapper;
import com.unitask.service.ContextService;
import com.unitask.service.GroupService;
import com.unitask.util.PageUtil;
import com.unitask.util.PageWrapperVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl extends ContextService implements GroupService {

    @Autowired
    private GroupDao groupDao;

    @Autowired
    private AppUserDAO appUserDAO;
    @Autowired
    private AssessmentDao assessmentDao;
    @Autowired
    private StudentAssessmentDao studentAssessmentDao;

    @Override
    public void createGroup(GroupRequest groupRequest) {
        List<AppUser> appUserList = appUserDAO.findByIds(groupRequest.getMembers());
        Assessment assessment = assessmentDao.findById(groupRequest.getAssessmentId());
        Group group = new Group();
        group.setName(groupRequest.getName());
        group.setDescription(groupRequest.getDescription());
        group.setAssessment(assessment);
        Group savedGroup = groupDao.save(group);

        List<StudentAssessment> studentAssessmentList = studentAssessmentDao.findByAssessmentAndAppUserList(groupRequest.getMembers(), assessment.getId());
        if (!CollectionUtils.isEmpty(studentAssessmentList)) {
            studentAssessmentDao.saveAll(studentAssessmentList.stream().map(studentAss -> {
                studentAss.setGroup(savedGroup);
                return studentAss;
            }).toList());
        }
    }

    @Override
    public void updateGroup(Long id, GroupRequest groupRequest) {
        List<AppUser> appUserList = appUserDAO.findByIds(groupRequest.getMembers());
        if (CollectionUtils.isEmpty(appUserList)) {
            throw new ServiceAppException(HttpStatus.BAD_REQUEST, "Group must have at least one member");
        }
        Group group = groupDao.findById(id);
        if (group == null) {
            throw new ServiceAppException(HttpStatus.BAD_REQUEST, "Group does not Exists");
        }
        group.setName(groupRequest.getName());
        group.setDescription(groupRequest.getDescription());
        Group savedGroup = groupDao.save(group);
        Set<StudentAssessment> studentAssessmentSet = group.getStudentAssessment();
        Map<Long, StudentAssessment> studentAssessmentMap =
                studentAssessmentSet.stream().collect(Collectors.toMap(x -> x.getUser().getId(), x -> x));
        Map<Long, AppUser> appUserMap = appUserList.stream().collect(Collectors.toMap(AppUser::getId, x -> x));
        List<StudentAssessment> studentAssessmentList = new ArrayList<>();
        groupRequest.getMembers().forEach(memberId -> {
            if (studentAssessmentMap.containsKey(memberId)) {
                studentAssessmentList.add(studentAssessmentMap.get(memberId));
                studentAssessmentMap.remove(memberId);
            } else {
                StudentAssessment studentAssessment = new StudentAssessment();
                studentAssessment.setUser(appUserMap.get(memberId));
                studentAssessment.setAssessment(savedGroup.getAssessment());
                studentAssessment.setGroup(savedGroup);
                studentAssessment.setStatus(GeneralStatus.ACTIVE);
                studentAssessment.setEnrollmentDate(LocalDate.now());
            }
        });
        studentAssessmentList.addAll(
                studentAssessmentMap.values().stream().map(studentAss -> {
                    studentAss.setGroup(null);
                    return studentAss;
                }).toList());
        studentAssessmentDao.saveAll(studentAssessmentList);
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
        return new PageWrapperVO(groupAssessmentTuple, GroupMapper.INSTANCE.toResponseList(groupAssessmentTuple.getContent()));
    }

    @Override
    public List<DropdownResponse> getStudentListing() {
        return appUserDAO.findStudents().stream().map(user -> {
            DropdownResponse dropdownResponse = new DropdownResponse();
            dropdownResponse.setId(user.getId());
            dropdownResponse.setName(user.getName());
            return dropdownResponse;
        }).toList();
    }

    @Override
    public List<DropdownResponse> getStudentAssignmentDropdown(Long id) {
        Assessment ass = assessmentDao.findById(id);
        List<StudentAssessment> studentAssessmentList = studentAssessmentDao.findByAssignment(ass.getId());
        if (CollectionUtils.isEmpty(studentAssessmentList)) {
            return null;
        }
        return studentAssessmentList.stream().map(studentAss -> {
            DropdownResponse dropdownResponse = new DropdownResponse();
            dropdownResponse.setId(studentAss.getId());
            dropdownResponse.setName(studentAss.getUser().getName());
            return dropdownResponse;
        }).toList();
    }
}
