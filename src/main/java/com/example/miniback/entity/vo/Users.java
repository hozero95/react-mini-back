package com.example.miniback.entity.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "zero_users")
public class Users {
    @Id
    @Column(name = "user_unum")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_zero_users")
    @SequenceGenerator(name = "seq_zero_users", sequenceName = "seq_zero_users", allocationSize = 1)
    private Long userUnum;

    @Column(name = "user_id")
    private String userId;

    @JsonIgnore
    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "postcode")
    private String postcode;

    @Column(name = "address")
    private String address;

    @Column(name = "detail_address")
    private String detailAddress;

    @Column(name = "extra_address")
    private String extraAddress;

    @Column(name = "email")
    private String email;

    @Column(name = "tel")
    private String tel;

    @Column(name = "regdate")
    private Date regdate;

    @ManyToMany
    @JoinTable(
            name = "zero_user_authority",
            joinColumns = {@JoinColumn(name = "user_unum", referencedColumnName = "user_unum")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")}
    )
    private Set<Authority> authorities;
}
