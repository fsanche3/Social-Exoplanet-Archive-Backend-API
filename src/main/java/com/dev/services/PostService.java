package com.dev.services;

import org.springframework.stereotype.Service;

import com.dev.models.Post;
import com.dev.repos.PostRepo;

import lombok.extern.slf4j.Slf4j;

@Service @Slf4j
public class PostService {

	private PostRepo postRepo;
	
	public PostService(PostRepo postRepo) {
		this.postRepo = postRepo;
	}
	
	public void upsert(Post post) {
		postRepo.save(post);
	}
}
