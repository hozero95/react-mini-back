package com.example.miniback.controller;

import com.example.miniback.entity.vo.Users;
import com.example.miniback.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Users>> getUsersAll() {
        List<Users> users = usersService.getUsersAll();
        if (users == null || users.size() <= 0) {
            return new ResponseEntity<>(users, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Users>> getUsers(@RequestParam Long userUnum) {
        Optional<Users> users = usersService.getUsers(userUnum);
        if (users == null || users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(users);
    }

    @PutMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    @Transactional
    public ResponseEntity<String> updateUsers(@RequestBody Users users) {
        Users user = usersService.updateUsers(users);
        if (user == null || user.getUserUnum() == null) {
            return new ResponseEntity<>("fail", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok("success");
    }

    @DeleteMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    @Transactional
    public ResponseEntity<String> deleteUsers(@RequestParam Long userUnum) {
        Long result = usersService.deleteUsers(userUnum);
        if (result == null || result <= 0) {
            return new ResponseEntity<>("fail", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok("success");
    }
}
