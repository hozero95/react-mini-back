package com.example.miniback.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "zero_authority")
public class Authority {
    @Id
    @Column(name = "authority_name")
    private String authorityName;
}
