package com.dev.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dev.models.Post;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer>{
	
	public List<Post> findByPlanetOrderByDateDesc(String planet);

	@Query(value = "SELECT * FROM post where parent_id = ?1", nativeQuery = true)
	public List<Post> findByPlanet_Id(int planet_id);

}
