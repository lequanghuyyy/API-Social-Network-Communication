package com.springboot.cms.repository;

import com.springboot.cms.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    Set<RoleEntity> findAllByRoleNameIn(List<String> roleNames);

}
