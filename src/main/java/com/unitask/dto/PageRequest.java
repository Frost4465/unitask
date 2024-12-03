package com.unitask.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class PageRequest {

    @Min(value = 1, message = "Cannot be less than {value}.")
    private int page = 1;

    @Min(value = 1, message = "Cannot be less than {value}.")
    private int pageSize = 10;

    private String search;

    private List<String> sort;
}
