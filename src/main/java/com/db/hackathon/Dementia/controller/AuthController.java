package com.db.hackathon.Dementia.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.db.hackathon.Dementia.dto.UserDto;
import com.db.hackathon.Dementia.entity.Patient;
import com.db.hackathon.Dementia.entity.User;
import com.db.hackathon.Dementia.repository.PatientRepository;
import com.db.hackathon.Dementia.repository.UserRepository;
import com.db.hackathon.Dementia.service.UserService;

import jakarta.validation.Valid;

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
	@CrossOrigin(origins = "*")
    public ResponseEntity<List<Patient>> patientList(Long userId){
    	logger.info("patientList for userID: "+ userId);
    	return new ResponseEntity<List<Patient>>(userService.patientList(userId),HttpStatus.OK);
    }
	
	@PostMapping("/patient/save")
	@CrossOrigin(origins = "*")
    public ResponseEntity<String> patientSave(@Valid @RequestBody Patient patient){
		logger.info("patientSave started");
        patientRepository.save(patient);
        return new ResponseEntity<String>(HttpStatus.CREATED);
        
    }
	
	// handler method to handle login request
    @PostMapping("/login")
    @CrossOrigin(origins = "*")
    public ResponseEntity<User> login(@RequestBody User request){
    	logger.info("login started");
    	return userService.loginUser(request);
    }
	
	@GetMapping("/prompt")
	@CrossOrigin(origins = "*")
	public String getResponse(String prompt, String geminiKey) {
		return userService.callApi(prompt,geminiKey);
	}

 // handler method to handle user registration form submit request
    @PostMapping("/register/save")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> registration(@Valid @RequestBody User user){
//        User existingUser = userRepository.findByUserName(userDto.getUserName());
//        if(existingUser != null && existingUser.getUserName() != null && !existingUser.getUserName().isEmpty()){
//        	return new ResponseEntity<String>(HttpStatus.CREATED);
//        }
        userService.saveUser(user);
        return new ResponseEntity<String>(HttpStatus.CREATED);
    }
    
    @GetMapping("/users")
    @CrossOrigin(origins = "*")
    public List<UserDto> users(Model model){
    	logger.info("getUser started");
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
