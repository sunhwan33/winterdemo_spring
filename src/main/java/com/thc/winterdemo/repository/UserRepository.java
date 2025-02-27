package com.thc.winterdemo.repository;

import com.thc.winterdemo.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User findByUsername(String username);
    User findByUsernameAndPassword(String username, String password);

    /*
    * 로그인 기능 모두 구현하고 쓸 거!
    * 최초 조회 시 조인이 같이 하기위해 쓰는 어노테이션
    * 꼭 조인할 때만 쓸 것*/

    @EntityGraph(attributePaths = {"userRoleType.roleType"})
    Optional<User> findEntityGraphRoleTypeById(Long userId);
}
