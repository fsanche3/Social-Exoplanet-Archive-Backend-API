package com.dev.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="exo_user") @NoArgsConstructor @Data
public class ExoUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username;
	private String password;
	private String img;
	
	@OneToMany
	@JoinColumn(name="user_id")
	@JsonIgnore
	private List<Post> post;

	public ExoUser(int id, String username, String password, String img, List<Post> post) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.img = img;
		this.post = new ArrayList<>();
	}
	
	

}
