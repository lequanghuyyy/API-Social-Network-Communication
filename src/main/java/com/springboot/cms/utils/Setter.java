package com.springboot.cms.utils;


import com.springboot.cms.dto.ContentDto;
import com.springboot.cms.dto.MemberDetailDto;
import com.springboot.cms.dto.MemberDto;
import com.springboot.cms.entity.ContentEntity;
import com.springboot.cms.entity.MemberDetailEntity;
import com.springboot.cms.entity.MemberEntity;

import java.util.Date;

public class Setter {
    private Setter(){};
    public static void setMemberDetailEntity(MemberDetailDto memberDetailDto, MemberDetailEntity memberDetailEntity){
        if(memberDetailDto.getCreatedDate() != null) {
            memberDetailEntity.setCreatedDate(memberDetailDto.getCreatedDate());
        }
            memberDetailEntity.setUpdateTime(new Date());
        if (memberDetailDto.getEmail() != null){
            memberDetailEntity.setEmail(memberDetailDto.getEmail());
        }
        if (memberDetailDto.getDescription() != null){
            memberDetailEntity.setDescription(memberDetailDto.getDescription());
        }
        if (memberDetailDto.getPhone() != null){
        memberDetailEntity.setPhone(memberDetailDto.getPhone());
        }
        memberDetailEntity.setUpdateTime(new Date());
    }
    public static void setMemberEntity(MemberDto memberDto, MemberEntity memberEntity){
        if (memberDto.getAge() != null) {
            memberEntity.setAge(memberDto.getAge());
        }
        if (memberDto.getFirstName() != null) {
            memberEntity.setFirstName(memberDto.getFirstName());
        }
        if (memberDto.getLastName() != null) {
            memberEntity.setLastName(memberDto.getLastName());
        }
    }
    public static void setContentEntity(ContentDto contentDto, ContentEntity contentEntity){
        if (contentDto.getTitle() != null) {
            contentEntity.setTitle(contentDto.getTitle());
        }
        if (contentDto.getContent() != null) {
            contentEntity.setContent(contentDto.getContent());
        }
        if (contentDto.getBrief() != null) {
            contentEntity.setBrief(contentDto.getBrief());
        }
        if (contentDto.getCategory() != null) {
            contentEntity.setCategory(contentDto.getCategory());
        }
        contentEntity.setUpdateTime(new Date());

    }
}
