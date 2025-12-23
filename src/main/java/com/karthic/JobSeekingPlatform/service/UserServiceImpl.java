package com.karthic.JobSeekingPlatform.service;

import com.karthic.JobSeekingPlatform.Exception.APIException;
import com.karthic.JobSeekingPlatform.Exception.ResourceNotFoundException;
import com.karthic.JobSeekingPlatform.model.Users;
import com.karthic.JobSeekingPlatform.payload.UserResponse;
import com.karthic.JobSeekingPlatform.payload.UsersDTO;
import com.karthic.JobSeekingPlatform.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

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

    @Override
    public UsersDTO deleteUsers(Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Users", "userId", userId));
        userRepository.delete(user);
        return modelMapper.map(user, UsersDTO.class);
    }

    @Override
    public UserResponse searchUsersByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Users> pageUsers = userRepository.findByUserNameLikeIgnoreCase('%' + keyword + '%', pageDetails);

        List<Users> users = pageUsers.getContent();
        List<UsersDTO> usersDTOS = users.stream()
                .map(user -> modelMapper.map(user, UsersDTO.class))
                .toList();

        if(users.isEmpty()){
            throw new APIException("Users not found with keyword: " + keyword);
        }

        UserResponse usersResponse = new UserResponse();
        usersResponse.setContent(usersDTOS);
        usersResponse.setPageNumber(pageUsers.getNumber());
        usersResponse.setPageSize(pageUsers.getSize());
        usersResponse.setTotalElements(pageUsers.getTotalElements());
        usersResponse.setTotalPages(pageUsers.getTotalPages());
        usersResponse.setLastPage(pageUsers.isLast());
        return usersResponse;
    }

    @Override
    public UserResponse searchUsersById(Long userId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Users> pageUsers = userRepository.findByUserId(userId, pageDetails);

        List<Users> users = pageUsers.getContent();
        List<UsersDTO> usersDTOS = users.stream()
                .map(user -> modelMapper.map(user, UsersDTO.class))
                .toList();

        if(users.isEmpty()){
            throw new APIException("Users not found with id: " + userId);
        }

        UserResponse usersResponse = new UserResponse();
        usersResponse.setContent(usersDTOS);
        usersResponse.setPageNumber(pageUsers.getNumber());
        usersResponse.setPageSize(pageUsers.getSize());
        usersResponse.setTotalElements(pageUsers.getTotalElements());
        usersResponse.setTotalPages(pageUsers.getTotalPages());
        usersResponse.setLastPage(pageUsers.isLast());
        return usersResponse;
    }

    @Override
    public UsersDTO updateUsers(Long userId, UsersDTO usersDTO) {
        Users usersFromDb = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Users", "userId", userId));

        Users users = modelMapper.map(usersDTO, Users.class);

        usersFromDb.setUserName(users.getUserName());
        usersFromDb.setEmail(users.getEmail());
        usersFromDb.setPassword(users.getPassword());
        usersFromDb.setUserPhoneNumber(users.getUserPhoneNumber());
        usersFromDb.setSkills(users.getSkills());


        Users savedUsers = userRepository.save(usersFromDb);

        return modelMapper.map(savedUsers, UsersDTO.class);
    }


}
