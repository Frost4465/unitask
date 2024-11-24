package com.unitask.dto.subject;

import com.unitask.constant.Enum.GeneralStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectRequest {

    private Long lecturerId;
    private String name;
    private GeneralStatus status;

}
