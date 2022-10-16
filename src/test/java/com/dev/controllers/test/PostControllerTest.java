package com.dev.controllers.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.dev.controllers.PostController;
import com.dev.models.ExoUser;
import com.dev.models.Post;
import com.dev.models.dtos.LikesResponse;
import com.dev.models.dtos.PostDto;
import com.dev.services.ExoUserService;
import com.dev.services.PostService;
import com.dev.utils.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = PostController.class)
public class PostControllerTest {
	
	@MockBean
	private JwtUtil jwt;
	
	@MockBean
	private  ExoUserService userServ;
	
	@MockBean
	private PostService postServ;
	
	@Autowired
	private MockMvc mockMvc;
		
	private ObjectMapper om = new ObjectMapper();
	
	@Test
	public void uploadPost() throws JsonProcessingException, Exception {
		ExoUser user = new ExoUser(3,"username","password","img");
		Post post = new Post(1,"","","",Timestamp.valueOf(LocalDateTime.now()), 0, new ExoUser(), new HashSet<>());
		String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpbWciOiJodHRwczovL2V4b3BsYW5ldC1hcmNoaXZlLWltYWdlcy5zMy5hbWF6b25hd3MuY29tL0NvbnNvbGUtYmFuay1mbG93Y2hhcnQuUE5HIiwiaXNzIjoiYXV0aDAiLCJpZCI6MywidXNlcm5hbWUiOiJ1c2VybmFtZSJ9.q4VyRXy-KRhq0UtBljYgPRrfFd-jQqD29-7_ymhsGmQ";
		
		Mockito.when(userServ.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
		Mockito.when(postServ.upsert(Mockito.any())).thenReturn(post);
		
		mockMvc.perform(post("/post/upsert").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization",  jwtToken)
				.content(om.writeValueAsString(post)))
		.andExpect(status().isOk());
	}
	
	@Test
	public void uploadComment() throws JsonProcessingException, Exception {
		ExoUser user = new ExoUser(3,"username","password","img");
		Post post = new Post(6,"","","",Timestamp.valueOf(LocalDateTime.now()), 0, new ExoUser(), new HashSet<>());
		String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpbWciOiJodHRwczovL2V4b3BsYW5ldC1hcmNoaXZlLWltYWdlcy5zMy5hbWF6b25hd3MuY29tL0NvbnNvbGUtYmFuay1mbG93Y2hhcnQuUE5HIiwiaXNzIjoiYXV0aDAiLCJpZCI6MywidXNlcm5hbWUiOiJ1c2VybmFtZSJ9.q4VyRXy-KRhq0UtBljYgPRrfFd-jQqD29-7_ymhsGmQ";
		
		Mockito.when(userServ.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
		Mockito.when(postServ.upsert(Mockito.any())).thenReturn(post);
		
		mockMvc.perform(post("/post/comment/2").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization",  jwtToken)
				.content(om.writeValueAsString(post)))
		.andExpect(status().isOk());
	}
	
	@Test
	public void likePost() throws JsonProcessingException, Exception {
		ExoUser user = new ExoUser(3,"username","password","img");
		Post post = new Post(6,"","","",Timestamp.valueOf(LocalDateTime.now()), 0, new ExoUser(), new HashSet<>());
		String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpbWciOiJodHRwczovL2V4b3BsYW5ldC1hcmNoaXZlLWltYWdlcy5zMy5hbWF6b25hd3MuY29tL0NvbnNvbGUtYmFuay1mbG93Y2hhcnQuUE5HIiwiaXNzIjoiYXV0aDAiLCJpZCI6MywidXNlcm5hbWUiOiJ1c2VybmFtZSJ9.q4VyRXy-KRhq0UtBljYgPRrfFd-jQqD29-7_ymhsGmQ";
		
		Mockito.when(userServ.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
		Mockito.when(postServ.findById(Mockito.anyInt())).thenReturn(Optional.of(post));
		Mockito.when(postServ.upsert(Mockito.any())).thenReturn(post);

		mockMvc.perform(put("/post/like/3").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization",  jwtToken)
				.content(om.writeValueAsString(post)))
		.andExpect(status().isOk());
	}
	
	@Test
	public void unlikePost() throws JsonProcessingException, Exception {
		ExoUser user = new ExoUser(3,"username","password","img");
		Set<ExoUser> set = new HashSet<>();
		set.add(user);
		Post post = new Post(6,"","","",Timestamp.valueOf(LocalDateTime.now()), 0, new ExoUser(),set);
		String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpbWciOiJodHRwczovL2V4b3BsYW5ldC1hcmNoaXZlLWltYWdlcy5zMy5hbWF6b25hd3MuY29tL0NvbnNvbGUtYmFuay1mbG93Y2hhcnQuUE5HIiwiaXNzIjoiYXV0aDAiLCJpZCI6MywidXNlcm5hbWUiOiJ1c2VybmFtZSJ9.q4VyRXy-KRhq0UtBljYgPRrfFd-jQqD29-7_ymhsGmQ";
		
		Mockito.when(userServ.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
		Mockito.when(postServ.findById(Mockito.anyInt())).thenReturn(Optional.of(post));
		Mockito.when(postServ.upsert(Mockito.any())).thenReturn(post);

		mockMvc.perform(put("/post/unlike/3").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization",  jwtToken)
				.content(om.writeValueAsString(post)))
		.andExpect(status().isOk());
	}
	
	@Test
	public void getPostByUser() throws JsonProcessingException, Exception {
		ExoUser user = new ExoUser(1,"","","img");
		List<PostDto> list = new ArrayList<>();
		Post post = new Post(1,"","","",Timestamp.valueOf(LocalDateTime.now()), 0, new ExoUser(), new HashSet<>());
		list.add(new PostDto(post));
		
		Mockito.when(userServ.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
		Mockito.when(postServ.findPostByUserId(user)).thenReturn(list);
		
		mockMvc.perform(get("/post/user/1").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
	
	@Test
	public void getLikesByUser() throws JsonProcessingException, Exception {
		ExoUser user = new ExoUser(1,"","","img");
		
		Mockito.when(userServ.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
		Mockito.when(postServ.getLikesByUser(Mockito.any())).thenReturn(new LikesResponse(1));
		
		mockMvc.perform(get("/post/likes/1").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
	
	@Test
	public void getCommentsByUser() throws JsonProcessingException, Exception {
		ExoUser user = new ExoUser(1,"","","img");
		List<PostDto> list = new ArrayList<>();
		Post post = new Post(1,"","","",Timestamp.valueOf(LocalDateTime.now()), 0, new ExoUser(), new HashSet<>());
		list.add(new PostDto(post));
		
		Mockito.when(userServ.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
		Mockito.when(postServ.findPostByUserId(user)).thenReturn(list);
		
		mockMvc.perform(get("/post/user/comments/1").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
	
	@Test
	public void getPostsByPlanet() throws JsonProcessingException, Exception {
		List<PostDto> list = new ArrayList<>();
		Post post = new Post(1,"","","",Timestamp.valueOf(LocalDateTime.now()), 0, new ExoUser(), new HashSet<>());
		list.add(new PostDto(post));
		
		Mockito.when(postServ.findByPlanet(Mockito.anyString())).thenReturn(list);
		
		mockMvc.perform(get("/post/jkk").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}

	@Test
	public void getCommentsByPost() throws JsonProcessingException, Exception {
		List<PostDto> list = new ArrayList<>();
		Post post = new Post(1,"","","",Timestamp.valueOf(LocalDateTime.now()), 0, new ExoUser(), new HashSet<>());
		list.add(new PostDto(post));
		
		Mockito.when(postServ.findCommentsByPostId(Mockito.anyInt())).thenReturn(list);
		
		mockMvc.perform(get("/post/post-comments/1").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}

	@Test
	public void cannotUnlikePost() throws JsonProcessingException, Exception {
		ExoUser user = new ExoUser(3,"username","password","img");
		Post post = new Post(6,"","","",Timestamp.valueOf(LocalDateTime.now()), 0, new ExoUser(), new HashSet<>());
		String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpbWciOiJodHRwczovL2V4b3BsYW5ldC1hcmNoaXZlLWltYWdlcy5zMy5hbWF6b25hd3MuY29tL0NvbnNvbGUtYmFuay1mbG93Y2hhcnQuUE5HIiwiaXNzIjoiYXV0aDAiLCJpZCI6MywidXNlcm5hbWUiOiJ1c2VybmFtZSJ9.q4VyRXy-KRhq0UtBljYgPRrfFd-jQqD29-7_ymhsGmQ";
		
		Mockito.when(userServ.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
		Mockito.when(postServ.findById(Mockito.anyInt())).thenReturn(Optional.of(post));
		Mockito.when(postServ.upsert(Mockito.any())).thenReturn(post);

		mockMvc.perform(put("/post/unlike/3").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization",  jwtToken)
				.content(om.writeValueAsString(post)))
		.andExpect(status().isBadRequest());
	}

}
