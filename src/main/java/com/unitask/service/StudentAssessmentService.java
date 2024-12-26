package com.unitask.service;

import com.unitask.dto.PageRequest;
import com.unitask.util.PageWrapperVO;


public interface StudentAssessmentService {

    PageWrapperVO getAssessmentListing(PageRequest pageRequest);

    //TODO for individual only -> this will lead to create/update the individual submission
    //TODO if group -> this will lead to a create/update the group submission
    void submit();
}
