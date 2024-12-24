package com.unitask.dto.group;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GroupRequest {

    private String name;
    private String description;
    private List<Long> groupMemberIds;

}
