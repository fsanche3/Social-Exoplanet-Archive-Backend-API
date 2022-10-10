package com.dev.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.models.ExoUser;

@Repository
public interface ExoUserRepo extends JpaRepository<ExoUser, Integer> {

	public List<ExoUser> findByUsername(String username);
	
	
}
