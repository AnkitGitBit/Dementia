package com.db.hackathon.Dementia.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Forgot {
	
	 private static final long serialVersionUID = 1L;

	    @NotEmpty
	    @NotBlank
	    private String email;

	    @NotEmpty
	    @NotBlank
	    private String password;
	    
}
