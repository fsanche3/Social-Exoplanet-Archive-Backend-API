package com.dev.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dev.models.ExoUser;
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
			if(p.getParent_id() == 0) {
			ans.add(new PostDto(p));
			}
		}
		return ans;
	}
	
	public List<PostDto> findPostByUserId(ExoUser user){
		List<PostDto> list = new ArrayList<>();
		for(Post p : user.getPost()) {
			if(p.getParent_id() == 0) {
			list.add(new PostDto(p));
			}
		}
		return list;
	}
	
	public List<PostDto> findCommentsByUserId(ExoUser user){
		List<PostDto> list = new ArrayList<>();
		for(Post p : user.getPost()) {
			if(p.getParent_id() != 0) {
			list.add(new PostDto(p));
			}
		}
		return list;
	}
	
	public List<PostDto> findCommentsByPostId(int id){
		List<PostDto> list = new ArrayList<>();
		List<Post> post = postRepo.findByPlanet_Id(id); 
		for(Post p : post) {
			list.add(new PostDto(p));
			
		}
		return list;
	}
	
	public void upsert(Post post) {
		postRepo.save(post);
		return;
	}
	
	public Optional<Post> findById(int id){
		return postRepo.findById(id);
	}
}
