package com.karthic.JobSeekingPlatform.service;

import com.karthic.JobSeekingPlatform.Exception.APIException;
import com.karthic.JobSeekingPlatform.model.Users;
import com.karthic.JobSeekingPlatform.payload.UsersDTO;
import com.karthic.JobSeekingPlatform.repositories.JobRepository;
import com.karthic.JobSeekingPlatform.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    public ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UsersDTO createUser(UsersDTO userDTO) {
        Users user = modelMapper.map(userDTO,Users.class);
        Users userDb = userRepository.findByUserName(user.getUserName());
        if (userDb!=null){
            throw new APIException("User "+ user.getUserName() + " already exists");
        }
        Users savedUser = userRepository.save(user);
        return  modelMapper.map(savedUser, UsersDTO.class);
    }
}
