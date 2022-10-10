package com.dev.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.models.Post;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer>{
	
	public List<Post> findByPlanet(String planet);

	public List<Post> findByAuthor(int id);

}
