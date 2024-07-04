package com.springboot.cms.dto;

import lombok.Data;

@Data
public class MemberDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private Integer age;
    private Long userId;
}
