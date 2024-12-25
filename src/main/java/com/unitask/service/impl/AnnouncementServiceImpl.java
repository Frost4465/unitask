package com.unitask.service.impl;

import com.unitask.dao.AnnouncementDAO;
import com.unitask.dao.AppUserDAO;
import com.unitask.dao.SubjectDAO;
import com.unitask.dto.PageRequest;
import com.unitask.dto.annoucement.AnnouncementRequest;
import com.unitask.dto.annoucement.AnnouncementResponse;
import com.unitask.dto.annoucement.AnnouncementTuple;
import com.unitask.entity.Announcement;
import com.unitask.entity.Subject;
import com.unitask.entity.User.AppUser;
import com.unitask.mapper.AnnouncementMapper;
import com.unitask.service.AnnouncementService;
import com.unitask.service.ContextService;
import com.unitask.util.PageUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AnnouncementServiceImpl extends ContextService implements AnnouncementService {

    private final AppUserDAO appUserDAO;
    private final SubjectDAO subjectDAO;
    private final AnnouncementDAO announcementDAO;

    @Override
    public AnnouncementResponse create(AnnouncementRequest announcementRequest) {
        AppUser appUser = appUserDAO.findByEmail(getCurrentAuthUsername());
        Subject subject = subjectDAO.findById(announcementRequest.getSubjectId());
        Announcement announcement = AnnouncementMapper.INSTANCE.toEntity(announcementRequest, subject, appUser);
        announcementDAO.save(announcement);
        return AnnouncementMapper.INSTANCE.toResponse(announcement);
    }

    @Override
    public AnnouncementResponse read(Long id) {
        Announcement announcement = announcementDAO.findById(id);
        return AnnouncementMapper.INSTANCE.toResponse(announcement);
    }

    @Override
    public AnnouncementResponse update(Long id, AnnouncementRequest announcementRequest) {
        Announcement announcement = announcementDAO.findById(id);
        Subject subject = subjectDAO.findById(announcementRequest.getSubjectId());
        AnnouncementMapper.INSTANCE.toEntity(announcement, announcementRequest, subject);
        announcementDAO.save(announcement);
        return AnnouncementMapper.INSTANCE.toResponse(announcement);
    }

    @Override
    public void delete() {

    }

    @Override
    public Page<AnnouncementTuple> list(PageRequest pageRequest) {
        AppUser appUser = appUserDAO.findByEmail(getCurrentAuthUsername());
        Pageable pageable = PageUtil.pageable(pageRequest, Sort.by("postedDate").descending());
        return announcementDAO.findListing(pageable, appUser.getId(), pageRequest.getSearch());
    }
}
