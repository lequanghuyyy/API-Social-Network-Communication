package com.springboot.cms.controller;


import com.springboot.cms.dto.MemberDto;
import com.springboot.cms.dto.common.BaseResponse;
import com.springboot.cms.service.MemberService;
import com.springboot.cms.utils.ResponseFactory;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/member")
public class MemberController {
    private final MemberService memberService;
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    @GetMapping("")
    public ResponseEntity<BaseResponse<List<MemberDto>>> getAllMembers() {
        return ResponseFactory.ok(memberService.getAllMembers());
    }
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<MemberDto>> getMemberById(@PathVariable("id") Integer id) {
            return ResponseFactory.ok(memberService.getMemberById(id));
    }
    @PostMapping("")
    public ResponseEntity<BaseResponse<MemberDto>> createMember(@Valid @RequestBody MemberDto memberDto) {
        return ResponseFactory.ok(memberService.createMember(memberDto));
    }
    @PutMapping
    public ResponseEntity<BaseResponse<MemberDto>> updateMember(@Valid @RequestBody MemberDto memberDto) {
        return ResponseFactory.ok(memberService.updateMember(memberDto));
    }
}
