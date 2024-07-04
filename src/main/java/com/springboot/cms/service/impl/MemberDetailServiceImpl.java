package com.springboot.cms.service.impl;

import com.springboot.cms.dto.MemberDetailDto;
import com.springboot.cms.entity.MemberDetailEntity;
import com.springboot.cms.entity.MemberEntity;
import com.springboot.cms.entity.UserEntity;
import com.springboot.cms.exception.NotFoundMemberException;
import com.springboot.cms.mapper.MemberDetailMapper;
import com.springboot.cms.repository.MemberDetailRepository;
import com.springboot.cms.repository.MemberRepository;
import com.springboot.cms.security.CustomUserDetails;
import com.springboot.cms.service.MemberDetailService;
import com.springboot.cms.utils.Setter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class MemberDetailServiceImpl implements MemberDetailService {
    private final MemberDetailRepository memberDetailRepository;
    private final MemberRepository memberRepository;
    private final MemberDetailMapper memberDetailMapper;

    public MemberDetailServiceImpl(MemberDetailRepository memberDetailRepository,
                                   MemberRepository memberRepository,
                                   MemberDetailMapper memberDetailMapper) {
        this.memberDetailRepository = memberDetailRepository;
        this.memberRepository = memberRepository;
        this.memberDetailMapper = memberDetailMapper;
    }

    @Override
    public MemberDetailDto findByMemberId(Integer id) {
        return memberDetailRepository.findById(id)
                .map(memberDetailMapper::mappedToDto)
                .orElseThrow(() -> new NotFoundMemberException(id));
    }

    @Override
    public MemberDetailDto create(MemberDetailDto memberDetailDto) {
        validateMemberDetailDto(memberDetailDto);
        CustomUserDetails principal = getCurrentUserDetails();
        MemberEntity memberEntity = getMemberEntity(memberDetailDto);

        if (!isAdmin(principal) && !isSameUser(principal, memberEntity.getUserEntity())) {
            throw new AccessDeniedException("You do not have permission to create detailed information for this member");
        }

        MemberDetailEntity memberDetailEntity = memberDetailMapper.mappedToEntity(memberDetailDto);
        memberDetailEntity.setMember(memberEntity);
        return memberDetailMapper.mappedToDto(memberDetailRepository.save(memberDetailEntity));
    }

    @Override
    public MemberDetailDto update(MemberDetailDto memberDetailDto) {
        validateMemberDetailDto(memberDetailDto);
        CustomUserDetails principal = getCurrentUserDetails();
        MemberDetailEntity existingMemberDetail = memberDetailRepository.findById(memberDetailDto.getDetailId())
                .orElseThrow(() -> new NotFoundMemberException(memberDetailDto.getDetailId()));

        if (!isAdmin(principal) && !isSameUser(principal, existingMemberDetail.getMember().getUserEntity())) {
            throw new AccessDeniedException("You do not have permission to update detailed information for this member");
        }

        Setter.setMemberDetailEntity(memberDetailDto, existingMemberDetail);
        memberDetailRepository.save(existingMemberDetail);
        return memberDetailMapper.mappedToDto(existingMemberDetail);
    }

    @Override
    public void delete(Integer id) {
        CustomUserDetails principal = getCurrentUserDetails();
        MemberDetailEntity memberDetailEntity = memberDetailRepository.findById(id)
                .orElseThrow(() -> new NotFoundMemberException(id));

        if (!isAdmin(principal) && !isSameUser(principal, memberDetailEntity.getMember().getUserEntity())) {
            throw new AccessDeniedException("You do not have permission to delete this information");
        }

        memberDetailRepository.deleteById(id);
    }

    private void validateMemberDetailDto(MemberDetailDto memberDetailDto) {
        if (memberDetailDto == null) {
            throw new IllegalArgumentException("Member cannot be null");
        }
    }

    private CustomUserDetails getCurrentUserDetails() {
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private MemberEntity getMemberEntity(MemberDetailDto memberDetailDto) {
        return memberRepository.findByIdWithUserEntity(Long.valueOf(memberDetailDto.getMember().getId()))
                .orElseThrow(() -> new NotFoundMemberException(memberDetailDto.getMember().getId()));
    }

    private boolean isAdmin(CustomUserDetails principal) {
        return principal.getUser().getRoles().stream()
                .anyMatch(role -> role.getRoleName().equals("ADMIN"));
    }

    private boolean isSameUser(CustomUserDetails principal, UserEntity userEntity) {
        return principal.getUser().getId().equals(userEntity.getId());
    }
}
