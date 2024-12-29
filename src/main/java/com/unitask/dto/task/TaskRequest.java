package com.unitask.dto.task;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskRequest {

    private String name;
    private Long assigmentId;
    private Boolean checked;

}
