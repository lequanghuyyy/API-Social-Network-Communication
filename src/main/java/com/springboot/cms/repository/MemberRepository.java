package com.springboot.cms.repository;

import com.springboot.cms.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {
        @Query("SELECT m FROM MemberEntity m JOIN FETCH m.userEntity WHERE m.id = :id")
        Optional<MemberEntity> findByIdWithUserEntity(@Param("id") Long id);
}
