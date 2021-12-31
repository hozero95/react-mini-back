package com.example.miniback.service;

import com.example.miniback.entity.vo.Users;
import com.example.miniback.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    public List<Users> getUsersAll() {
        List<Users> users = usersRepository.findAll();
        return users;
    }

    public Optional<Users> getUsers(Long userUnum) {
        Optional<Users> users = usersRepository.findByUserUnum(userUnum);
        return users;
    }
}
