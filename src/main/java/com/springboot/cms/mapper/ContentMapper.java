package com.springboot.cms.mapper;


import com.springboot.cms.dto.ContentDto;
import com.springboot.cms.entity.ContentEntity;
import com.springboot.cms.entity.MemberEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ContentMapper {
    private final ModelMapper modelMapper;
    private final MemberMapper memberMapper;

    @Autowired
    public ContentMapper(ModelMapper modelMapper, MemberMapper memberMapper) {
        this.modelMapper = modelMapper;
        this.memberMapper = memberMapper;
    }
    public ContentDto mappedToDto(ContentEntity contentEntity) {
        ContentDto contentDto = modelMapper.map(contentEntity, ContentDto.class);
        contentDto.getMember().setUserId(contentEntity.getMember().getUserEntity().getId());
        return contentDto;
    }
    public ContentEntity mappedToEntity(ContentDto contentDto) {
        ContentEntity contentEntity = modelMapper.map(contentDto, ContentEntity.class);
        return contentEntity;
    }
    public ContentDto toDto(ContentEntity contentEntity) {
        ContentDto contentDto = new ContentDto();
        contentDto.setId(contentEntity.getId());
        contentDto.setTitle(contentEntity.getTitle());
        contentDto.setContent(contentEntity.getContent());
        contentDto.setBrief(contentEntity.getBrief());
        contentDto.setCategory(contentEntity.getCategory());
        contentDto.setCreateDate(contentEntity.getCreateDate());
        contentDto.setUpdateTime(contentEntity.getUpdateTime());
        contentDto.setMember(memberMapper.mappedToDto(contentEntity.getMember()));
        return contentDto;
    }
}
