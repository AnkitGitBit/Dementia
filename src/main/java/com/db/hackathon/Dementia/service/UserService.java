package com.db.hackathon.Dementia.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.db.hackathon.Dementia.dto.UserDto;
import com.db.hackathon.Dementia.entity.Forgot;
import com.db.hackathon.Dementia.entity.Login;
import com.db.hackathon.Dementia.entity.Patient;
import com.db.hackathon.Dementia.entity.Result;
import com.db.hackathon.Dementia.entity.User;

public interface UserService {
    void saveUser(UserDto userDto);

//    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
    
    ResponseEntity<User> loginUser(User request);
    
    List<Patient> patientList(Long userId);

    
    String callApi(String prompt, String geminiKey);
}
