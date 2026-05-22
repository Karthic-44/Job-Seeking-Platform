package com.karthic.JobSeekingPlatform.repositories;

import com.karthic.JobSeekingPlatform.model.AppRole;

import com.karthic.JobSeekingPlatform.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {


   Optional<Role> findByRoleName(AppRole appRole);
}