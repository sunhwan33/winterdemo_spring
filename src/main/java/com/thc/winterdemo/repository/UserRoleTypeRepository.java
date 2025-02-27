package com.thc.winterdemo.repository;

import com.thc.winterdemo.domain.UserRoleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleTypeRepository extends JpaRepository<UserRoleType, String> {

}
