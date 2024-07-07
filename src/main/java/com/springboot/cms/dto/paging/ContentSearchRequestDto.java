package com.springboot.cms.dto.paging;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ContentSearchRequestDto {

    private String keySearch;

    @Min(value = 1, message = "Page must be at least 1")
    @Max(value = 1000, message = "Page must be at most 100")
    private int page = 1;

    @Min(value = 1, message = "Size must be at least 1")
    @Max(value = 100, message = "Size must be at most 100")
    private int size = 10;

}
