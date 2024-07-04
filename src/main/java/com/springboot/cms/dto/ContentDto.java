package com.springboot.cms.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ContentDto {

    private Integer id;

    private String title;

    private String brief;

    private String content;

    private String category;

    private Date createDate;

    private Date updateTime;
    private MemberDto member;
}
