package com.dev.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dev.models.Post;
import com.dev.models.dtos.PostDto;
import com.dev.repos.PostRepo;

import lombok.extern.slf4j.Slf4j;

@Service @Slf4j
public class PostService {

	private PostRepo postRepo;
	
	public PostService(PostRepo postRepo) {
		this.postRepo = postRepo;
	}
	
	public List<PostDto> findByPlanet(String planet){
		List<Post> list = postRepo.findByPlanet(planet);
		List<PostDto> ans = new ArrayList<>();
		for(Post p: list) {
			ans.add(new PostDto(p));
		}
		return ans;
	}
	
	public List<PostDto> findByUserId(int id){
		List<Post> list = postRepo.findByAuthor(id);
		List<PostDto> ans = new ArrayList<>();
		for(Post p: list) {
			ans.add(new PostDto(p));
		}
		return ans;
	}
	
	public void upsert(Post post) {
		postRepo.save(post);
		return;
	}
	
	public Optional<Post> findById(int id){
		return postRepo.findById(id);
	}
}
