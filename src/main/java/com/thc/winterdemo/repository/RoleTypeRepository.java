package com.thc.winterdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.thc.winterdemo.domain.RoleType;
public interface RoleTypeRepository extends JpaRepository<RoleType, Long> {
    RoleType findByTypeName(String typeName);
}
