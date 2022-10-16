package com.dev.models.dtos;

import java.util.ArrayList;
import java.util.List;

import com.dev.models.ExoUser;
import com.dev.models.Post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor @Data
public class ExoUserDto {

	private int id;
	private String username;
	private String img;
	
	public ExoUserDto(ExoUser user) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.img = user.getImg();
	}
}
