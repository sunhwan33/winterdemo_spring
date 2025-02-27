package com.thc.winterdemo.repository;

import com.thc.winterdemo.domain.Scomment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScommentRepository extends JpaRepository<Scomment, Long>{

}
