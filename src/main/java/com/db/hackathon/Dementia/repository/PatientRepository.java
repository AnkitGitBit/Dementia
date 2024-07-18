package com.db.hackathon.Dementia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.db.hackathon.Dementia.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    List<Patient> findByUserId(Long userId);
    
}
