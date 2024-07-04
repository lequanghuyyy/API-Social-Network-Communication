    package com.springboot.cms.service.impl;


    import com.springboot.cms.dto.MemberDto;
    import com.springboot.cms.entity.MemberEntity;
    import com.springboot.cms.exception.NotFoundMemberException;
    import com.springboot.cms.mapper.MemberMapper;
    import com.springboot.cms.repository.ContentRepository;
    import com.springboot.cms.repository.MemberDetailRepository;
    import com.springboot.cms.repository.MemberRepository;
    import com.springboot.cms.security.CustomUserDetails;
    import com.springboot.cms.service.MemberService;
    import com.springboot.cms.utils.Setter;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.access.AccessDeniedException;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.stereotype.Service;

    import java.util.List;
    import java.util.Optional;

    @Service
    public class MemberServiceImpl implements MemberService {
        private final MemberRepository memberRepository;
        private final MemberDetailRepository memberDetailRepository;
        private final ContentRepository contentRepository;
        private final MemberMapper memberMapper;
        @Autowired
        public MemberServiceImpl(MemberRepository memberRepository, MemberDetailRepository memberDetailRepository, ContentRepository contentRepository, MemberMapper memberMapper) {
            this.memberRepository = memberRepository;
            this.memberDetailRepository = memberDetailRepository;
            this.contentRepository = contentRepository;
            this.memberMapper = memberMapper;
        }
        @Override
        public List<MemberDto> getAllMembers() {
            return memberRepository.findAll().stream().map(memberMapper::mappedToDto).toList();
        }
        @Override
        public MemberDto getMemberById(Integer id) {
            return memberRepository.findById(id).map(memberMapper::mappedToDto).orElseThrow(() -> new NotFoundMemberException(id));
        }
        @Override
        public MemberDto createMember(MemberDto memberDto) {
            CustomUserDetails principal = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (memberDto == null){
                throw new IllegalArgumentException("Member cannot be null");
            }
            if (principal.getUser().getRoles().stream()
                    .noneMatch(role -> role.getRoleName().equals("ADMIN"))) {
                if (!principal.getUser().getId().equals(memberDto.getUserId())) {
                    throw new AccessDeniedException("You do not have permission to update detailed information for this member");
                }
            }
                MemberEntity memberEntity = memberRepository.save(memberMapper.mappedToEntity(memberDto));
            memberDto.setId(memberEntity.getId());
            return memberDto;
        }
        @Override
        public MemberDto updateMember(MemberDto memberDto) {
            CustomUserDetails principal = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Optional<MemberEntity> existingMember = memberRepository.findById(memberDto.getId());
            if (existingMember.isEmpty()){
                throw new NotFoundMemberException(memberDto.getId());
            }
            if (principal.getUser().getRoles().stream()
                    .noneMatch(role -> role.getRoleName().equals("ADMIN"))) {
                if (!principal.getUser().getId().equals(existingMember.get().getUserEntity().getId())) {
                    throw new AccessDeniedException("You do not have permission to update detailed information for this member");
                }
            }
            MemberEntity memberEntity = existingMember.get();
            Setter.setMemberEntity(memberDto,memberEntity);
            memberRepository.save(memberEntity);
            return memberMapper.mappedToDto(memberEntity);
        }
    }
