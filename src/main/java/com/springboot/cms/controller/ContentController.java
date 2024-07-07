package com.springboot.cms.controller;


import com.springboot.cms.constant.AppConstant;
import com.springboot.cms.dto.ContentDto;
import com.springboot.cms.dto.common.BaseResponse;
import com.springboot.cms.dto.paging.ContentSearchRequestDto;
import com.springboot.cms.dto.paging.PageDto;
import com.springboot.cms.service.ContentService;
import com.springboot.cms.utils.ResponseFactory;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/content")
public class ContentController {

    private final ContentService contentService;
    @Autowired
    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }
    @GetMapping
    public ResponseEntity<BaseResponse<PageDto<ContentDto>>> getContent(@Valid @RequestBody ContentSearchRequestDto contentSearchRequestDto){
        return ResponseFactory.ok(contentService.getAllContent(contentSearchRequestDto));
    }
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ContentDto>> getContentById(@PathVariable Integer id) {
      return ResponseFactory.ok(contentService.getContentById(id));
    }
    @PostMapping()
    public ResponseEntity<BaseResponse<ContentDto>> addContent(@RequestBody ContentDto contentDto) {
        return ResponseFactory.ok(contentService.createContent(contentDto));
    }
    @PutMapping()
    public ResponseEntity<BaseResponse<ContentDto>> updateContent(@RequestBody ContentDto contentDto) {
        return ResponseFactory.ok(contentService.updateContent(contentDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteContentById(@PathVariable Integer id) {
        contentService.deleteContent(id);
        return ResponseFactory.ok(null);
    }

    @GetMapping("/search")
    public ResponseEntity<BaseResponse<PageDto<ContentDto>>> searchContent(
            @Valid @RequestBody ContentSearchRequestDto contentSearchRequestDto){
        return ResponseFactory.ok(contentService.search(contentSearchRequestDto));
    }
}
