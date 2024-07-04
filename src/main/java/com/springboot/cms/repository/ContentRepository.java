package com.springboot.cms.repository;


import com.springboot.cms.entity.ContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ContentRepository extends JpaRepository<ContentEntity, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM ContentEntity ce WHERE ce.member.id = :memberId")
    void deleteByMemberId(@Param("memberId") Integer memberId);
}
