package com.springboot.cms.service;

import com.springboot.cms.dto.MemberDetailDto;
import org.springframework.stereotype.Service;

@Service
public interface MemberDetailService{
    MemberDetailDto findByMemberId(Integer id);
    MemberDetailDto create(MemberDetailDto memberDetailDto);
    MemberDetailDto update(MemberDetailDto memberDetailDto);
    void delete(Integer id);
}
