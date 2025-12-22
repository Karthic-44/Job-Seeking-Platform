package com.karthic.JobSeekingPlatform.controller;

import com.karthic.JobSeekingPlatform.model.Users;
import com.karthic.JobSeekingPlatform.payload.APIResponse;
import com.karthic.JobSeekingPlatform.payload.JobDTO;
import com.karthic.JobSeekingPlatform.payload.UsersDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.karthic.JobSeekingPlatform.service.UserService;

@RestController
@RequestMapping("/api")

    public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users/create")
        public ResponseEntity<UsersDTO> createUser(@Valid  @RequestBody UsersDTO userDTO) {

            UsersDTO savedUserDTO = userService.createUser(userDTO);
            return new ResponseEntity<>(savedUserDTO, HttpStatus.CREATED);

        }

//        @GetMapping("/{id}")
//        public APIResponse getUserById(@PathVariable Long id) { }
//
//        @PutMapping("/{id}")
//        public APIResponse updateUser(@PathVariable Long id,
//                                      @RequestBody UsersDTO usersDTO) { }
//
//        @DeleteMapping("/{id}")
//        public APIResponse deleteUser(@PathVariable Long id) { }
    }



