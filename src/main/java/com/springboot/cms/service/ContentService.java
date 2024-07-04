package com.springboot.cms.service;

import com.springboot.cms.dto.ContentDto;
import com.springboot.cms.dto.paging.ContentSearchRequestDto;
import com.springboot.cms.dto.paging.PageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContentService {
    List<ContentDto> getAllContent();
    ContentDto getContentById(Integer id);
    ContentDto createContent(ContentDto contentDto);
    ContentDto updateContent(ContentDto contentDto);
    void deleteContent(Integer id);
    PageDto<ContentDto> search(ContentSearchRequestDto contentSearchRequestDto);
}
