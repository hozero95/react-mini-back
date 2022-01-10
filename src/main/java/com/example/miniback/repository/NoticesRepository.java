package com.example.miniback.repository;

import com.example.miniback.entity.vo.Notices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoticesRepository extends JpaRepository<Notices, Long> {
    List<Notices> findAll();

    Optional<Notices> findByNoticeUnum(Long noticeUnum);

    Long deleteByNoticeUnum(Long noticeUnum);

    boolean existsByNoticeUnum(Long noticeUnum);
}
