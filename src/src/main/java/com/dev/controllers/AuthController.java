package com.dev.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.dev.models.ExoUser;
import com.dev.models.dtos.ExoUserDto;
import com.dev.models.dtos.LoginRequest;
import com.dev.services.ExoUserService;
import com.dev.utils.JwtUtil;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {
	
	private JwtUtil jwtUtil;
	private ExoUserService userServ;
	
	public AuthController(JwtUtil jwtUtil, ExoUserService userServ) {
		this.jwtUtil = jwtUtil;
		this.userServ = userServ;
	}
	
	@PostMapping()
	public ResponseEntity<ExoUserDto> loginPost(@RequestBody LoginRequest body) {

		String token = "";

		ExoUser user = userServ.verifyAuth(body);
		
		if (user != null) {
			ExoUserDto userDto = new ExoUserDto(user);
			JWTCreator.Builder builder = jwtUtil.getJwtBuilder();
			Algorithm algorithm = Algorithm.HMAC256("franklyn");

			token = builder.withIssuer("auth0").withClaim("id", user.getId()).withClaim("username", user.getUsername())
					.withClaim("img", user.getImg()).sign(algorithm);
			
			return ResponseEntity.status(200).header("Auth", token).body(userDto);
		} else {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

		}

	}
	
	@PostMapping(path = "/register")
	public ResponseEntity<Boolean> registerUser(@RequestBody ExoUser body) {
		
		boolean usernameDoesNotExist = userServ.verifyRegistration(body);
		if (usernameDoesNotExist) {
			return ResponseEntity.status(HttpStatus.CREATED).body(true);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(false);
		}

	}

}
