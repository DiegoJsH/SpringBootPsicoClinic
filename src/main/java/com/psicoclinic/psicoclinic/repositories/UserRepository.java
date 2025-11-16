package com.psicoclinic.psicoclinic.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psicoclinic.psicoclinic.models.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByUsername(String username);
}