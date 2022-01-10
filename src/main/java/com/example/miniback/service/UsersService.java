package com.example.miniback.service;

import com.example.miniback.entity.vo.Users;
import com.example.miniback.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    public List<Users> getUsersAll() {
        return usersRepository.findAll();
    }

    public Optional<Users> getUsers(Long userUnum) {
        return usersRepository.findByUserUnum(userUnum);
    }

    public Users updateUsers(Users users) {
        Optional<Users> user = getUsers(users.getUserUnum());
        if (user != null && !user.isEmpty()) {
            Users user2 = Users.builder()
                    .userUnum(users.getUserUnum())
                    .userId(users.getUserId())
                    .userPassword(user.get().getUserPassword())
                    .postcode(users.getPostcode())
                    .address(users.getAddress())
                    .detailAddress(users.getDetailAddress())
                    .extraAddress(users.getExtraAddress())
                    .email(users.getEmail())
                    .tel(users.getTel())
                    .regdate(new Date())
                    .build();
            return usersRepository.save(user2);
        }
        return null;
    }

    public Long deleteUsers(Long userUnum) {
        if (usersRepository.existsByUserUnum(userUnum)) {
            return usersRepository.deleteByUserUnum(userUnum);
        }
        return null;
    }
}
