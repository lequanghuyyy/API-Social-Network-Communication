package com.springboot.cms.controller;

import com.springboot.cms.dto.MemberDetailDto;
import com.springboot.cms.dto.common.BaseResponse;
import com.springboot.cms.service.MemberDetailService;
import com.springboot.cms.utils.ResponseFactory;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/memberDetail")
public class MemberDetailController {
    private final MemberDetailService memberDetailService;
    @Autowired
    public MemberDetailController(MemberDetailService memberDetailService) {
        this.memberDetailService = memberDetailService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<MemberDetailDto>> getMemberDetailById(@PathVariable Integer id) {
        return ResponseFactory.ok(memberDetailService.findByMemberId(id));
    }
    @PostMapping
    public ResponseEntity<BaseResponse<MemberDetailDto>> createMemberDetail(@Valid @RequestBody MemberDetailDto memberDetailDto) {
        return ResponseFactory.ok(memberDetailService.create(memberDetailDto));
    }
    @PutMapping
    public ResponseEntity<BaseResponse<MemberDetailDto>> updateMemberDetail(@Valid @RequestBody MemberDetailDto memberDetailDto) {
        return ResponseFactory.ok(memberDetailService.update(memberDetailDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteMemberDetail(@PathVariable Integer id) {
       memberDetailService.delete(id);
        return ResponseFactory.ok(null);
    }
}
