package com.unitask.controller;

import com.unitask.dto.DocumentPageRequest;
import com.unitask.dto.PageRequest;
import com.unitask.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping(path = "document")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @GetMapping("/list")
    public ResponseEntity<?> getDocumentList(@RequestBody DocumentPageRequest documentPageRequest) {
        return ResponseEntity.ok().body(documentService.getListing(documentPageRequest));
    }


}
