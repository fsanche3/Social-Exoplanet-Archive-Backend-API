package com.dev.controllers.test;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.dev.controllers.ExoUserController;
import com.dev.models.ExoUser;
import com.dev.models.dtos.ExoUserDto;
import com.dev.services.AwsService;
import com.dev.services.ExoUserService;
import com.dev.utils.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = ExoUserController.class)
public class ExoUserControllerTest {

	@MockBean
	private  ExoUserService userServ;
	
	@MockBean
	private AwsService awsServ;
	
	@MockBean
	private JwtUtil jwt;
	
	@Autowired
	private MockMvc mockMvc;
		
	private ObjectMapper om = new ObjectMapper();
	
	@Test
	public void getById() throws JsonProcessingException, Exception {
		ExoUserDto userDto = new ExoUserDto(1,"","");
		
		Mockito.when(userServ.findById(Mockito.anyInt())).thenReturn(Optional.of(new ExoUser(1,"","","")));
		
		mockMvc.perform(get("/user/1").contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(userDto)))
		.andExpect(status().isOk());
	}
	
	@Test
	public void updateUser() throws JsonProcessingException, Exception {
		ExoUser user = new ExoUser(1,"","","img");
		
		Mockito.when(userServ.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
		Mockito.when(userServ.upsert(Mockito.any())).thenReturn(user);
		
		mockMvc.perform(put("/user/update-img/1").contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(user)))
		.andExpect(status().isOk());
	}
	
}
