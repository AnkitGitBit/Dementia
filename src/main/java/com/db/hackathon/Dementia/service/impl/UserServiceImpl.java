package com.db.hackathon.Dementia.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.db.hackathon.Dementia.dto.UserDto;
import com.db.hackathon.Dementia.entity.Forgot;
import com.db.hackathon.Dementia.entity.Login;
import com.db.hackathon.Dementia.entity.Result;
import com.db.hackathon.Dementia.entity.Role;
import com.db.hackathon.Dementia.entity.User;
import com.db.hackathon.Dementia.repository.RoleRepository;
import com.db.hackathon.Dementia.repository.UserRepository;
import com.db.hackathon.Dementia.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
    private UserRepository userRepository;
	@Autowired
    private RoleRepository roleRepository;
	@Autowired
    private PasswordEncoder passwordEncoder;

//    public UserServiceImpl(UserRepository userRepository,
//                           RoleRepository roleRepository,
//                           PasswordEncoder passwordEncoder
//                          ) {
//        this.userRepository = userRepository;
//        this.roleRepository = roleRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
    
    
    
    @Autowired
	private RestTemplate restTemplate;

	private final String API_URL_TEMPLATE = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=%s";

	public String callApi(String prompt, String geminiKey) {
        String apiUrl = String.format(API_URL_TEMPLATE, geminiKey);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode contentNode = objectMapper.createObjectNode();
        ObjectNode partsNode = objectMapper.createObjectNode();
        partsNode.put("text", prompt);
        contentNode.set("parts", objectMapper.createArrayNode().add(partsNode));
        ObjectNode requestBodyNode = objectMapper.createObjectNode();
        requestBodyNode.set("contents", objectMapper.createArrayNode().add(contentNode));
        
        String requestBody;
        try {
            requestBody = objectMapper.writeValueAsString(requestBodyNode);
        } catch (Exception e) {
            throw new RuntimeException("Failed to construct JSON request body", e);
        }

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, request, String.class);

        return response.getBody();
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
     // encrypt the password using spring security
//        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setPassword(userDto.getPassword());
//        Role role = roleRepository.findByName("ROLE_ADMIN");
//        if(role == null){
//            role = checkRoleExist();
//        }
//        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }
    
    @Override
    public Result loginUser(Login request) {
    	User existingUser = userRepository.findByEmail(request.getEmail());
	    Result res= new Result();
	    if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
	    	if(request.getPassword().equals(existingUser.getPassword())) {
	    		res.setMessage(
		                "login successfull-" + existingUser.getName() + " Welcome to HA");
	    	}else {
	    		res.setMessage("password is incorrect");
	    	}
	    	return res;
	    }
	    res.setMessage("User "+request.getEmail() +" is not registered with HA");
	    return res;
    }
    
    @Override
    public Result forgotPassword(Forgot request) {
    	User existingUser = userRepository.findByEmail(request.getEmail());
	    Result res= new Result();
	    if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
	    	if(request.getPassword().equals(existingUser.getPassword())) {
	    		res.setMessage(
		                "Given password has been used before for " + existingUser.getName());
	    	}else {
	    		existingUser.setPassword(request.getPassword());
	    		userRepository.save(existingUser);
	    		res.setMessage("password has been updated successfully");
	    	}
	    	return res;
	    }
	    res.setMessage("User "+request.getEmail() +" is not registered with HA");
	    return res;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map((user) -> mapToUserDto(user))
                .collect(Collectors.toList());
    }

    private UserDto mapToUserDto(User user){
        UserDto userDto = new UserDto();
        String[] str = user.getName().split(" ");
        userDto.setFirstName(str[0]);
        userDto.setLastName(str.length > 1 ? str[1]:"");
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setPassword(user.getPassword());
        return userDto;
    }
    

//    private Role checkRoleExist(){
//        Role role = new Role();
//        role.setName("ROLE_ADMIN");
//        return roleRepository.save(role);
//    }
}
