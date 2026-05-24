package com.karthic.JobSeekingPlatform.repositories;

import com.karthic.JobSeekingPlatform.model.AppRole;
import com.karthic.JobSeekingPlatform.model.Users;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface UserRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByUserName(@NotBlank @Size(max=50) String userName);

    Page<Users> findByUserNameLikeIgnoreCase(String name, Pageable pageDetails);

    Page<Users> findByUserId(Long userId, Pageable pageDetails);

    Boolean existsByUserName(String username);

    Boolean existsByEmail(String email);

    @Query("SELECT u FROM Users u JOIN u.roles r WHERE r.roleName = :role")
    Page<Users> findByRoleName(@Param("role") AppRole role, Pageable pageable);




}
