package com.db.hackathon.Dementia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.db.hackathon.Dementia.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    

}
