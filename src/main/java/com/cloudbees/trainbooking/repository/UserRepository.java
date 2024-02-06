package com.cloudbees.trainbooking.repository;

import com.cloudbees.trainbooking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

