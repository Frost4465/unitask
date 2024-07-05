package com.chuan.taskmanagement.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class BaseViewOption {

    @Min(value = 1, message = "Cannot be less than {value}.")
    private int page = 1;

    @Min(value = 0, message = "Cannot be less than {value}.")
    private int pageSize = 10;

    private String searchQuery;

}
