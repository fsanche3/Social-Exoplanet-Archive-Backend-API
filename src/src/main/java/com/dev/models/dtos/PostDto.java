package com.dev.models.dtos;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import com.dev.models.ExoUser;
import com.dev.models.Post;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor @Data
public class PostDto {
	
	private int id;
	private String texts;
	private String img_url;
	private String planet;
	private Timestamp date;
	private ExoUserDto userDto;
	private int parent_id;
	private Set<ExoUserDto> usersDto;
	
	public PostDto(Post post) {
		this.id = post.getId();
		this.texts = post.getTexts();
		this.img_url = post.getImg_url();
		this.planet = post.getPlanet();
		this.date = post.getDate();
		this.userDto = new ExoUserDto(post.getAuthor());
		this.parent_id = post.getParent_id();
				
		this.usersDto = new HashSet<>();
		for(ExoUser user: post.getUsers()) {
			this.usersDto.add(new ExoUserDto(user));
		}
		
				
	}


}
