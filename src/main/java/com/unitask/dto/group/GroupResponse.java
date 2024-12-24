package com.unitask.dto.group;

import com.unitask.dto.GroupMemberListDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GroupResponse {

    private Long Id;
    private String name;
    private String description;
    private List<GroupMemberListDto> groupMemberList;

}
