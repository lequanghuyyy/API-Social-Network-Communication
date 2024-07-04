package com.springboot.cms.service.impl;

import com.springboot.cms.dto.ContentDto;
import com.springboot.cms.dto.paging.ContentSearchRequestDto;
import com.springboot.cms.dto.paging.PageDto;
import com.springboot.cms.entity.ContentEntity;
import com.springboot.cms.entity.MemberEntity;
import com.springboot.cms.entity.UserEntity;
import com.springboot.cms.exception.NotFoundContentException;
import com.springboot.cms.exception.NotFoundMemberException;
import com.springboot.cms.mapper.ContentMapper;
import com.springboot.cms.repository.ContentCustomRepository;
import com.springboot.cms.repository.ContentRepository;
import com.springboot.cms.repository.MemberRepository;
import com.springboot.cms.security.CustomUserDetails;
import com.springboot.cms.service.ContentService;
import com.springboot.cms.utils.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContentServiceImpl implements ContentService {
    private final ContentRepository contentRepository;
    private final ContentMapper contentMapper;
    private final MemberRepository memberRepository;
    private final ContentCustomRepository customRepository;

    @Autowired
    public ContentServiceImpl(ContentRepository contentRepository,
                              ContentMapper contentMapper,
                              MemberRepository memberRepository,
                              @Qualifier("contentNativeQueryCustomRepository")
                              ContentCustomRepository customRepository) {
        this.contentRepository = contentRepository;
        this.contentMapper = contentMapper;
        this.memberRepository = memberRepository;
        this.customRepository = customRepository;
    }

    @Override
    public List<ContentDto> getAllContent() {
        return contentRepository.findAll().stream().map(contentMapper::mappedToDto).toList();
    }

    @Override
    public ContentDto getContentById(Integer id) {
        return contentRepository.findById(id).map(contentMapper::mappedToDto).orElseThrow(() -> new NotFoundContentException(id));
    }

    @Override
    public ContentDto createContent(ContentDto contentDto) {
        validateContentDto(contentDto);
        CustomUserDetails principal = getCurrentUserDetails();
        MemberEntity memberEntity = getMemberEntity(contentDto);

        if (!isAdmin(principal) && !isSameUser(principal, memberEntity.getUserEntity())) {
            throw new AccessDeniedException("You do not have permission to create this content");
        }

        ContentEntity contentEntity = contentMapper.mappedToEntity(contentDto);
        contentEntity.setMember(memberEntity);
        contentRepository.save(contentEntity);
        return contentMapper.mappedToDto(contentEntity);
    }

    @Override
    public ContentDto updateContent(ContentDto contentDto) {
        validateContentDto(contentDto);
        CustomUserDetails principal = getCurrentUserDetails();
        ContentEntity existingContentEntity = contentRepository.findById(contentDto.getId())
                .orElseThrow(() -> new NotFoundContentException(contentDto.getId()));

        if (!isAdmin(principal) && !isSameUser(principal, existingContentEntity.getMember().getUserEntity())) {
            throw new AccessDeniedException("You do not have permission to update this content");
        }

        Setter.setContentEntity(contentDto, existingContentEntity);
        contentRepository.save(existingContentEntity);
        return contentMapper.mappedToDto(existingContentEntity);
    }

    @Override
    public void deleteContent(Integer id) {
        CustomUserDetails principal = getCurrentUserDetails();
        ContentEntity contentEntity = contentRepository.findById(id).orElseThrow(() -> new NotFoundContentException(id));

        if (!isSameUser(principal, contentEntity.getMember().getUserEntity())) {
            throw new AccessDeniedException("You do not have permission to delete this content");
        }

        contentRepository.deleteById(id);
    }
    private boolean isSameUser(CustomUserDetails principal, UserEntity userEntity) {
        return principal.getUser().getId().equals(userEntity.getId());
    }

    @Override
    public PageDto<ContentDto> search(ContentSearchRequestDto contentSearchRequestDto) {
        List<ContentDto> contentDtos = customRepository.search(
                        contentSearchRequestDto.getKeySearch(),
                        contentSearchRequestDto.getPage(), contentSearchRequestDto.getSize()
                ).stream()
                .map(contentMapper::toDto)
                .toList();
        long totalElements = customRepository.count(contentSearchRequestDto.getKeySearch());
        long totalPages = (long) Math.ceil((double) totalElements / contentSearchRequestDto.getSize());

        return PageDto.<ContentDto>builder()
                .items(contentDtos)
                .page(contentSearchRequestDto.getPage())
                .size(contentSearchRequestDto.getSize())
                .totalElements(totalElements)
                .totalPages(totalPages)
                .build();
    }

    private void validateContentDto(ContentDto contentDto) {
        if (contentDto == null) {
            throw new IllegalArgumentException("Content cannot be null");
        }
    }

    private CustomUserDetails getCurrentUserDetails() {
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private MemberEntity getMemberEntity(ContentDto contentDto) {
        return memberRepository.findByIdWithUserEntity(Long.valueOf(contentDto.getMember().getId()))
                .orElseThrow(() -> new NotFoundMemberException(contentDto.getMember().getId()));
    }

    private boolean isAdmin(CustomUserDetails principal) {
        return principal.getUser().getRoles().stream()
                .anyMatch(role -> role.getRoleName().equals("ADMIN"));
    }


}
