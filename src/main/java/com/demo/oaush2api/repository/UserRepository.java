package com.demo.oaush2api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.demo.dto.Userlist;
import com.demo.oaush2api.dto.User;

import lombok.Builder;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByUserId(String userId);
    Optional<User> findByEmail(String email);
    boolean existsByUserId(String userId);
    int deleteByUserId(String userId);
}