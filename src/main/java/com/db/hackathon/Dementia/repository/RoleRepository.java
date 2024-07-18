package com.db.hackathon.Dementia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.db.hackathon.Dementia.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}