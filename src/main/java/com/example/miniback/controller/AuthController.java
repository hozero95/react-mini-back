package com.example.miniback.controller;

import com.example.miniback.config.jwt.JwtFilter;
import com.example.miniback.config.jwt.TokenProvider;
import com.example.miniback.entity.dto.LoginDTO;
import com.example.miniback.entity.dto.TokenDTO;
import com.example.miniback.entity.dto.UsersDTO;
import com.example.miniback.entity.vo.Users;
import com.example.miniback.util.AuthService;
import org.apache.ibatis.javassist.bytecode.DuplicateMemberException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final AuthService authService;

    public AuthController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, AuthService authService) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.authService = authService;
    }

    @PostMapping("/signin")
    public ResponseEntity<TokenDTO> signin(@Valid @RequestBody LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDTO.getUserId(), loginDTO.getUserPassword()
        );

        // authenticate 메소드가 실행이 될 때, CustomUserDetailsService.loadUserByUsername이 실행이 되면서 Authentication 객체를 생성
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // 생성된 Authentication 객체를 SecurityContext에 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Authentication 객체를 createToken 메소드를 통해서 JWT Token을 생성
        String jwt = tokenProvider.createToken(authentication);

        // 생성된 JWT Token을 Response Header에 넣어줌
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        // TokenDTO를 이용해서 Response Body에도 Token을 넣고 header와 함께 리턴
        return new ResponseEntity<>(new TokenDTO(jwt), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<Users> signup(@Valid @RequestBody UsersDTO usersDTO) throws DuplicateMemberException {
        return ResponseEntity.ok(authService.signup(usersDTO));
    }

    // 얘 로직이 틀림.
    @PostMapping("/passwordcheck")
    public ResponseEntity<Boolean> passwordCheck(@RequestBody LoginDTO loginDTO) {
        return authService.passwordCheck(loginDTO) ? new ResponseEntity<>(true, HttpStatus.OK) : new ResponseEntity<>(false, HttpStatus.OK);
    }

    @GetMapping("/idcheck")
    public ResponseEntity<Boolean> idCheck(@RequestParam String userId) {
        boolean check = authService.getUserId(userId);
        return new ResponseEntity<>(check, HttpStatus.OK);
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Users> getMyInfo() {
        return ResponseEntity.ok(authService.getMyUserWithAuthorities().get());
    }

    @GetMapping("/userid")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Users> getUserInfo(@RequestParam String userId) {
        return ResponseEntity.ok(authService.getUserWithAuthorities(userId).get());
    }
}
