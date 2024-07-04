package com.springboot.cms.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MemberDetailDto {
    private Integer detailId;
    private String phone;
    private String email;
    private String description;
    private Date createdDate;
    private Date updateTime;
    private MemberDto member;

}
