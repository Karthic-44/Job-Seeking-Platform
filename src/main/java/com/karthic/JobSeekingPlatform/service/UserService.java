package com.karthic.JobSeekingPlatform.service;

import com.karthic.JobSeekingPlatform.payload.UsersDTO;
import jakarta.validation.Valid;

public interface UserService {
      UsersDTO createUser(@Valid UsersDTO userDTO);

      UsersDTO deleteUsers(Long userId);
}
