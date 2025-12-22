package com.karthic.JobSeekingPlatform.controller;

import com.karthic.JobSeekingPlatform.payload.APIResponse;
import com.karthic.JobSeekingPlatform.payload.JobDTO;
import com.karthic.JobSeekingPlatform.payload.UserDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")

    public class UserController {

        @PostMapping("/users/create")
        public APIResponse createUser(@Valid  @RequestBody UserDTO userDTO) {

            JobDTO savedUserDTO = userService.createJob(userDTO);
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



