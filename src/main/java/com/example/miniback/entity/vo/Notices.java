package com.example.miniback.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "zero_notices")
public class Notices {
    @Id
    @Column(name = "notice_unum")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_zero_notices")
    @SequenceGenerator(name = "seq_zero_notices", sequenceName = "seq_zero_notices", allocationSize = 1)
    private Long noticeUnum;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "views")
    private Long views;

    @Column(name = "regdate")
    private Date regdate;

    @Column(name = "moddate")
    private Date moddate;
}
