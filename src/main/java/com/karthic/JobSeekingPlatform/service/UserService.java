package com.karthic.JobSeekingPlatform.service;

import com.karthic.JobSeekingPlatform.payload.UserResponse;
import com.karthic.JobSeekingPlatform.payload.UsersDTO;
import jakarta.validation.Valid;

public interface UserService {
      UsersDTO createUser(@Valid UsersDTO userDTO);

      UsersDTO deleteUsers(Long userId);

      UserResponse searchUsersByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

      UserResponse searchUsersById(Long userId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
}
