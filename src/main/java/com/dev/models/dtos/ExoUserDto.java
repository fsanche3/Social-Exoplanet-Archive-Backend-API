package com.dev.models.dtos;

import java.util.List;

import com.dev.models.ExoUser;
import com.dev.models.Post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor @Data
public class ExoUserDto {

	private String username;
	private String img;
	private List<Post> post;
	
	public ExoUserDto(ExoUser user) {
		this.username = user.getUsername();
		this.img = user.getImg();
		this.post = user.getPost();
	}
}
