package com.example.miniback.controller;

import com.example.miniback.entity.vo.Notices;
import com.example.miniback.service.NoticesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notices")
public class NoticesController {
    @Autowired
    private NoticesService noticesService;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Notices>> getNoticesAll() {
        List<Notices> notices = noticesService.getNoticesAll();
        if (notices == null || notices.size() <= 0) {
            return new ResponseEntity<>(notices, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(notices);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Notices>> getNotices(@RequestParam Long noticeUnum) {
        Optional<Notices> notices = noticesService.getNotices(noticeUnum);
        if (notices == null || notices.isEmpty()) {
            return new ResponseEntity<>(notices, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(notices);
    }

    @PostMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> addNotices(@RequestBody Notices notices) {
        Notices notice = noticesService.addNotices(notices);
        if (notice == null || notice.getNoticeUnum() == null) {
            return new ResponseEntity<>("fail", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok("success");
    }

    @PutMapping(value = "/views", produces = MediaType.TEXT_PLAIN_VALUE)
    @Transactional
    public ResponseEntity<String> viewNotices(@RequestBody Notices notices) {
        Notices notice = noticesService.updateNotices(notices);
        if (notice == null || notice.getNoticeUnum() == null) {
            return new ResponseEntity<>("fail", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok("success");
    }

    @PutMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Transactional
    public ResponseEntity<String> updateNotices(@RequestBody Notices notices) {
        Notices notice = noticesService.updateNotices(notices);
        if (notice == null || notice.getNoticeUnum() == null) {
            return new ResponseEntity<>("fail", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok("success");
    }

    @DeleteMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Transactional
    public ResponseEntity<String> deleteNotices(@RequestParam Long noticeUnum) {
        Long result = noticesService.deleteNotices(noticeUnum);
        if (result == null || result <= 0) {
            return new ResponseEntity<>("fail", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok("success");
    }
}
