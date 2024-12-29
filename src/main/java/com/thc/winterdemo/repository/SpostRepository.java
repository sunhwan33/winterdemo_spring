package com.thc.winterdemo.repository;

import com.thc.winterdemo.domain.Spost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpostRepository extends JpaRepository<Spost, Long>{

}
