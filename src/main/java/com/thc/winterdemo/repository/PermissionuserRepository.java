package com.thc.winterdemo.repository;

import com.thc.winterdemo.domain.Permissionuser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionuserRepository extends JpaRepository<Permissionuser, Long>{

}
