package com.example.miniback.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersDTO {
    @NotNull
    private String userId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    private String userPassword;

    @NotNull
    private String postcode;

    @NotNull
    private String address;

    private String detailAddress;

    private String extraAddress;

    @NotNull
    private String email;

    @NotNull
    private String tel;
}
