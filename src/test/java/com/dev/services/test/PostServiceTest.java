package com.dev.services.test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.dev.SocialExoplanetArchiveApplication;
import com.dev.models.ExoUser;
import com.dev.models.Post;
import com.dev.models.dtos.ExoUserDto;
import com.dev.models.dtos.LikesResponse;
import com.dev.models.dtos.PostDto;
import com.dev.repos.PostRepo;
import com.dev.services.PostService;

@SpringBootTest(classes = SocialExoplanetArchiveApplication.class)
public class PostServiceTest {
	
	@MockBean
	PostRepo postRepo;
	
	@Autowired
	PostService postServ;
	
	@Test
	public void findByPlanet() {
		List<PostDto> list = new ArrayList<>();
		List<Post> list2 = new ArrayList<>();
		Post post = new Post(1,"","","",Timestamp.valueOf(LocalDateTime.now()), 0, new ExoUser(), new HashSet<>());
		list.add(new PostDto(post));
		list2.add(post);
		
		Mockito.when(postRepo.findByPlanetOrderByDateDesc(Mockito.anyString())).thenReturn(list2);
		
		Assertions.assertEquals(postServ.findByPlanet(""), list);
	}
	
	@Test
	public void findPostByUserId() {
		List<PostDto> list = new ArrayList<>();
		List<Post> list2 = new ArrayList<>();
		Post post = new Post(1,"","","",Timestamp.valueOf(LocalDateTime.now()), 0, new ExoUser(), new HashSet<>());
		list.add(new PostDto(post));
		list2.add(post);
		ExoUser user = new ExoUser(1,"","","");
		user.setPost(list2);
		
		Assertions.assertEquals(postServ.findPostByUserId(user), list);

	}
	
	@Test
	public void findCommentsByUserId() {
		List<PostDto> list = new ArrayList<>();
		List<Post> list2 = new ArrayList<>();
		Post post = new Post(1,"","","",Timestamp.valueOf(LocalDateTime.now()), 1, new ExoUser(), new HashSet<>());
		list.add(new PostDto(post));
		list2.add(post);
		ExoUser user = new ExoUser(1,"","","");
		user.setPost(list2);
		
		Assertions.assertEquals(postServ.findCommentsByUserId(user), list);
	}
	
	@Test
	public void findCommentsByPostId() {
		List<PostDto> list = new ArrayList<>();
		List<Post> list2 = new ArrayList<>();
		Post post = new Post(1,"","","",Timestamp.valueOf(LocalDateTime.now()), 1, new ExoUser(), new HashSet<>());
		list.add(new PostDto(post));
		list2.add(post);

		Mockito.when(postRepo.findByPlanet_Id(Mockito.anyInt())).thenReturn(list2);
		
		Assertions.assertEquals(postServ.findCommentsByPostId(Mockito.anyInt()), list);
	}
	
	@Test
	public void getLikesByUser() {
		ExoUser user = new ExoUser(1,"","","");
		List<PostDto> list = new ArrayList<>();
		List<PostDto> list2 = new ArrayList<>();
		Post post = new Post(1,"","","",Timestamp.valueOf(LocalDateTime.now()), 1, user, new HashSet<>());
		list.add(new PostDto(post));
		list2.add(new PostDto(post));
		LikesResponse likes = new LikesResponse(0);
		
		Assertions.assertNotNull(postServ.getLikesByUser(user));
	}
	
	@Test
	public void upsert() {
		Post post = new Post(1,"","","",Timestamp.valueOf(LocalDateTime.now()), 1, new ExoUser(), new HashSet<>());

		Mockito.when(postRepo.save(post)).thenReturn(post);
		
		Assertions.assertEquals(postServ.upsert(post), post);
	}
	
	@Test
	public void findById() {
		Post post = new Post(1,"","","",Timestamp.valueOf(LocalDateTime.now()), 1, new ExoUser(), new HashSet<>());
		
		Mockito.when(postRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(post));
		
		Assertions.assertEquals(postServ.findById(Mockito.anyInt()), Optional.of(post));
	}

}
