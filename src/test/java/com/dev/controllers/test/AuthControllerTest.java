package com.dev.controllers.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.auth0.jwt.JWT;
import com.dev.controllers.AuthController;
import com.dev.models.ExoUser;
import com.dev.models.dtos.LoginRequest;
import com.dev.services.ExoUserService;
import com.dev.utils.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = AuthController.class)
public class AuthControllerTest {

	@MockBean
	private  ExoUserService userServ;
	
	@MockBean
	private JwtUtil jwtUtil;
	
	@Autowired
	private MockMvc mockMvc;
	
	private ObjectMapper om = new ObjectMapper();
	
	@Test
	public void login() throws Exception {
		LoginRequest body = new LoginRequest("username","password");
		ExoUser user = new ExoUser(1,"","","");

		Mockito.when(userServ.verifyAuth(Mockito.any())).thenReturn(user);
		Mockito.when(jwtUtil.getJwtBuilder()).thenReturn(JWT.create());

		mockMvc.perform(post("/auth").contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(body)))
		.andExpect(status().isOk());
	}
	
	@Test
	public void cannotLogin() throws Exception {
		LoginRequest body = new LoginRequest("username","password");
		ExoUser user = null;

		Mockito.when(userServ.verifyAuth(Mockito.any())).thenReturn(user);
		Mockito.when(jwtUtil.getJwtBuilder()).thenReturn(JWT.create());

		mockMvc.perform(post("/auth").contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(body)))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void registerUser() throws JsonProcessingException, Exception {
		ExoUser user = new ExoUser(1,"","","");

		Mockito.when(userServ.verifyRegistration(Mockito.any())).thenReturn(true);
		
		mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(user)))
		.andExpect(status().isCreated());
	}
	
	@Test
	public void cannotRegisterUser() throws JsonProcessingException, Exception {
		ExoUser user = new ExoUser(1,"","","");

		Mockito.when(userServ.verifyRegistration(Mockito.any())).thenReturn(false);
		
		mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(user)))
		.andExpect(status().isNotAcceptable());
	}
}
