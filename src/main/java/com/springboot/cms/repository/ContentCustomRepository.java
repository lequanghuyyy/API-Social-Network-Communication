package com.springboot.cms.repository;

import com.springboot.cms.dto.ContentDto;
import com.springboot.cms.entity.ContentEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentCustomRepository {
    List<ContentEntity> search(String keySearch, int page, int pageSize);
    long count(String keySearch);
}
