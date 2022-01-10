package com.example.miniback.service;

import com.example.miniback.entity.vo.Notices;
import com.example.miniback.repository.NoticesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NoticesService {
    @Autowired
    private NoticesRepository noticesRepository;

    public List<Notices> getNoticesAll() {
        return noticesRepository.findAll();
    }

    public Optional<Notices> getNotices(Long noticeUnum) {
        return noticesRepository.findByNoticeUnum(noticeUnum);
    }

    public Notices addNotices(Notices notices) {
        Notices notice = Notices.builder()
                .title(notices.getTitle())
                .content(notices.getContent())
                .views(0L)
                .regdate(new Date())
                .build();
        return noticesRepository.save(notice);
    }

    public Notices updateNotices(Notices notices) {
        Optional<Notices> notice = getNotices(notices.getNoticeUnum());
        if (notice != null && !notice.isEmpty()) {
            Notices data = Notices.builder()
                    .noticeUnum(notices.getNoticeUnum())
                    .title(notices.getTitle())
                    .content(notices.getContent())
                    .views(notices.getViews())
                    .regdate(notice.get().getRegdate())
                    .moddate(new Date())
                    .build();
            return noticesRepository.save(data);
        }
        return null;
    }

    public Long deleteNotices(Long noticeUnum) {
        if (noticesRepository.existsByNoticeUnum(noticeUnum)) {
            return noticesRepository.deleteByNoticeUnum(noticeUnum);
        }
        return null;
    }
}
