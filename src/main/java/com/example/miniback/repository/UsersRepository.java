package com.example.miniback.repository;

import com.example.miniback.entity.vo.Users;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    List<Users> findAll();

    Optional<Users> findByUserUnum(Long userUnum);

    Long deleteByUserUnum(Long userUnum);

    boolean existsByUserUnum(Long userUnum);

    boolean existsByUserId(String userId);

    // AuthService
    // userId를 기준으로 User 정보를 가져올 때 권한 정보도 같이 가져옴
    @EntityGraph(attributePaths = "authorities")
    Optional<Users> findOneWithAuthoritiesByUserId(String userId);

    List<Users> findByUserId(String userId);
}