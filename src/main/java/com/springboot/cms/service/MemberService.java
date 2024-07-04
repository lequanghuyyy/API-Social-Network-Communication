package com.springboot.cms.service;

import com.springboot.cms.dto.MemberDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MemberService {
    List<MemberDto> getAllMembers();
    MemberDto getMemberById(Integer id);
    MemberDto createMember(MemberDto memberDto);
    MemberDto updateMember(MemberDto memberDto);
}
