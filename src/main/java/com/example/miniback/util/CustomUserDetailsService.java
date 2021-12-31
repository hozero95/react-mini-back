package com.example.miniback.util;

import com.example.miniback.entity.vo.Users;
import com.example.miniback.repository.UsersRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    private final UsersRepository usersRepository;

    public CustomUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    // 로그인 시에 DB에서 유저 정보와 권한정보를 가져오고, 해당 정보를 기반으로 userdetails.User 객체를 생성해서 리턴한다.
    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String userId) throws UsernameNotFoundException {
        return usersRepository.findOneWithAuthoritiesByUserId(userId)
                .map(user -> createUser(userId, user))
                .orElseThrow(() -> new UsernameNotFoundException(userId + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    private org.springframework.security.core.userdetails.User createUser(String userId, Users users) {
        List<GrantedAuthority> grantedAuthorityList = users.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(users.getUserId(), users.getUserPassword(), grantedAuthorityList);
    }
}
