package com.karthic.JobSeekingPlatform.repositories;

import com.karthic.JobSeekingPlatform.model.Users;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users,Long> {
    Users findByUserName(@NotBlank @Size(max=50) String userName);

    Page<Users> findByUserNameLikeIgnoreCase(String name, Pageable pageDetails);

    Page<Users> findByUserId(Long userId, Pageable pageDetails);

}
