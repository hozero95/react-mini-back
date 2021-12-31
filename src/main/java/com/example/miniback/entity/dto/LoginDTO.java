package com.example.miniback.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDTO {
    @NotNull
    private String userId;

    @NotNull
    private String userPassword;
}
