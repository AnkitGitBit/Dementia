package com.db.hackathon.Dementia.controller;

import jakarta.validation.Valid;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.db.hackathon.Dementia.dto.UserDto;
import com.db.hackathon.Dementia.entity.Forgot;
import com.db.hackathon.Dementia.entity.Login;
import com.db.hackathon.Dementia.entity.Patient;
import com.db.hackathon.Dementia.entity.Result;
import com.db.hackathon.Dementia.entity.User;
import com.db.hackathon.Dementia.repository.PatientRepository;
import com.db.hackathon.Dementia.repository.UserRepository;
import com.db.hackathon.Dementia.service.UserService;

@RestController
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	
	@GetMapping("/patientList")
    public ResponseEntity<List<Patient>> patientList(Long userId){
    	logger.info("patientList for userID: "+ userId);
    	return new ResponseEntity<List<Patient>>(userService.patientList(userId),HttpStatus.OK);
    }
	
	@PostMapping("/patient/save")
    public ResponseEntity<String> patientSave(@Valid @RequestBody Patient patient){
		logger.info("patientSave started");
        patientRepository.save(patient);
        return new ResponseEntity<String>(HttpStatus.CREATED);
        
    }
	
	// handler method to handle login request
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User request){
    	logger.info("login started");
    	return userService.loginUser(request);
    }
	
	@GetMapping("/prompt")
	public String getResponse(String prompt, String geminiKey) {
		return userService.callApi(prompt,geminiKey);
	}

 // handler method to handle user registration form submit request
    @PostMapping("/register/save")
    public Result registration(@Valid @RequestBody UserDto userDto){
        User existingUser = userRepository.findByUserName(userDto.getUserName());
        Result res= new Result();
        if(existingUser != null && existingUser.getUserName() != null && !existingUser.getUserName().isEmpty()){
        	res.setMessage(
                    "There is already an account registered with the same email");
        	return res;
        }

        userService.saveUser(userDto);
        res.setMessage(userDto.getUserName() +" is registered succesfully");
        return res;
    }
    
    @GetMapping("/users")
    public List<UserDto> users(Model model){
    	logger.info("login started");
        List<UserDto> users = userService.findAllUsers();
        return users;
    }
    
 
    
//    @PostMapping("/forgot")
//    public Result forgotPassword(@RequestBody Forgot request) {
//    	logger.info("forgot password started");
//    	return userService.forgotPassword(request);
//    	
//    }
    
//    @GetMapping("/test")
//    public Result test(){
//    	logger.info("test started");
//    	Result res = new Result();
//    	res.setMessage("Success");
//        return res;
//    }
    
}
