package com.springboot.cms.utils;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class ResponseUser {
    private Long id;

    private String username;

    @NotEmpty
    private List<String> roles;
}
