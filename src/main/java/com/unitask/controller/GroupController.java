package com.unitask.controller;

import com.unitask.dto.group.GroupRequest;
import com.unitask.service.GroupService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping(path = "group")
@AllArgsConstructor
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping("/create")
    public ResponseEntity<?> createGroup(@RequestBody GroupRequest groupRequest) {
        groupService.createGroup(groupRequest);
        return ResponseEntity.ok().body("OK");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateGroup(@PathVariable("id") Long id, @RequestBody GroupRequest groupRequest) {
        groupService.updateGroup(id, groupRequest);
        return ResponseEntity.ok().body("OK");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> getGroup(@PathVariable("id") Long id) {
        groupService.getGroup(id);
        return ResponseEntity.ok().body("OK");
    }


}
