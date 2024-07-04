package com.springboot.cms.repository;


import com.springboot.cms.entity.MemberDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MemberDetailRepository extends JpaRepository<MemberDetailEntity,Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM MemberDetailEntity ce WHERE ce.member.id = :memberId")
    void deleteByMemberId(@Param("memberId") Integer memberId);
}
