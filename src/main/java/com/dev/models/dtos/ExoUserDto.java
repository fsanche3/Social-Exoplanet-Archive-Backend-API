package com.dev.models.dtos;

import com.dev.models.ExoUser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor @Data
public class ExoUserDto {

	private String username;
	private String img;
	
	public ExoUserDto(ExoUser user) {
		this.username = user.getUsername();
		this.img = user.getImg();
	}
}
