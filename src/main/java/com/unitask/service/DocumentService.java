package com.unitask.service;

import com.unitask.dto.DocumentPageRequest;
import com.unitask.util.PageWrapperVO;

public interface DocumentService {

    PageWrapperVO getListing(DocumentPageRequest documentPageRequest);

}
