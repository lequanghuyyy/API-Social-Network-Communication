package com.springboot.cms.mapper;


import com.springboot.cms.dto.MemberDetailDto;
import com.springboot.cms.entity.MemberDetailEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberDetailMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public MemberDetailMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public MemberDetailDto mappedToDto(MemberDetailEntity memberDetailEntity) {
        MemberDetailDto memberDetailDto = modelMapper.map(memberDetailEntity, MemberDetailDto.class);
        memberDetailDto.getMember().setUserId(memberDetailEntity.getMember().getUserEntity().getId());
        return memberDetailDto;
    }

    public MemberDetailEntity mappedToEntity(MemberDetailDto memberDetailDto) {
        MemberDetailEntity memberDetailEntity = modelMapper.map(memberDetailDto, MemberDetailEntity.class);
 return memberDetailEntity;
    }
}
