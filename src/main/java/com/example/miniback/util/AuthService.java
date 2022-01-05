package com.example.miniback.util;

import com.example.miniback.entity.dto.LoginDTO;
import com.example.miniback.entity.dto.UsersDTO;
import com.example.miniback.entity.vo.Authority;
import com.example.miniback.entity.vo.Users;
import com.example.miniback.repository.UsersRepository;
import lombok.extern.java.Log;
import org.apache.ibatis.javassist.bytecode.DuplicateMemberException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Log
public class AuthService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원가입 로직을 수행하는 메소드
    @Transactional
    public Users signup(UsersDTO usersDTO) throws DuplicateMemberException {
        // userId가 DB에 존재하는지 검사
        if (usersRepository.findOneWithAuthoritiesByUserId(usersDTO.getUserId()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }

        // userId가 DB에 존재하지 않는다면 아래 로직 수행
        // Authority 정보와 User 정보 생성
        Authority authority = Authority.builder().authorityName("ROLE_USER").build();

        Users users = Users.builder()
                .userId(usersDTO.getUserId())
                .userPassword(passwordEncoder.encode(usersDTO.getUserPassword()))
                .postcode(usersDTO.getPostcode())
                .address(usersDTO.getAddress())
                .detailAddress(usersDTO.getDetailAddress())
                .extraAddress(usersDTO.getExtraAddress())
                .email(usersDTO.getEmail())
                .tel(usersDTO.getTel())
                .regdate(new Date())
                .authorities(Collections.singleton(authority))
                .build();

        // UserRepository의 save 메소드를 통해 DB에 정보를 저장
        return usersRepository.save(users);
    }

    // 로직이 뭔가 잘못됨
    // 똑같은 방법으로 암호화를 했는데 왜 다르게 나오는거지...
    // 나중에 다시 알아보도록 하자.
    public boolean passwordCheck(LoginDTO loginDTO) {
        String userId = loginDTO.getUserId();
        String userPassword = passwordEncoder.encode(loginDTO.getUserPassword());

        if (usersRepository.existsByUserId(userId)) {
            List<Users> users = usersRepository.findByUserId(userId);
            log.info("loginPassword: " + userPassword);
            log.info("userPassword: " + users.get(0).getUserPassword());
            if (userPassword.equals(users.get(0).getUserPassword())) {
                return true;
            }
        }

        return false;
    }

    // 유저, 권한 정보를 가져오는 메소드 2가지
    // 이 두 가지 메소드의 허용 권한을 다르게 해서 권한 검증에 대한 부분을 처리
    // userId를 기준으로 정보를 가져오는 메소드
    @Transactional(readOnly = true)
    public Optional<Users> getUserWithAuthorities(String userId) {
        return usersRepository.findOneWithAuthoritiesByUserId(userId);
    }

    // SecurityContext에 저장된 userId의 정보만 가져오는 메소드
    @Transactional(readOnly = true)
    public Optional<Users> getMyUserWithAuthorities() {
        return SecurityUtil.getCurrentUsername().flatMap(usersRepository::findOneWithAuthoritiesByUserId);
    }

    public Boolean getUserId(String userId) {
        return usersRepository.existsByUserId(userId);
    }
}
