package com.example.miniback.controller;

import com.example.miniback.entity.vo.Users;
import com.example.miniback.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @GetMapping("/test")
    public ResponseEntity<String> getTest() {
        return ResponseEntity.ok("users test");
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Users>> getUsersAll() {
        List<Users> users = usersService.getUsersAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Users>> getUsers(@RequestParam Long userUnum) {
        Optional<Users> users = usersService.getUsers(userUnum);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
