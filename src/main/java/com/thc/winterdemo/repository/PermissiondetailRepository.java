package com.thc.winterdemo.repository;

import com.thc.winterdemo.domain.Permissiondetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissiondetailRepository extends JpaRepository<Permissiondetail, Long>{

}
