package com.dev.controllers;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.models.ExoUser;
import com.dev.models.Post;
import com.dev.services.AwsService;
import com.dev.services.ExoUserService;
import com.dev.services.PostService;
import com.dev.utils.JwtUtil;

@RestController
@RequestMapping(path = "/post")
public class PostController {

	private PostService postServ;
	private JwtUtil jwtUtil;
	private ExoUserService userServ;
	

	public PostController(PostService postServ, JwtUtil jwtUtil, ExoUserService userServ) {
		this.postServ = postServ;
		this.jwtUtil = jwtUtil;
		this.userServ = userServ;
		
	}
	
	@PostMapping("/upsert")
	public ResponseEntity<Boolean> uploadPost(@RequestBody Post post, 
    		@RequestHeader(value = "Authorization", required = true) String authorization) throws UnsupportedEncodingException{
		

		int id = jwtUtil.getId(authorization);
		
		ExoUser user = userServ.findById(id).get();
		
		post.setDate(Timestamp.valueOf(LocalDateTime.now()));
		post.setUser_id(user);
		
		postServ.upsert(post);
		
        return new ResponseEntity<>(true, HttpStatus.OK);

	}

}
