package com.dev.models;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="post") @NoArgsConstructor @Data @AllArgsConstructor
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String texts;
	private String img_url;
	private String planet;
	private Timestamp date;
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private ExoUser user_id;
	@OneToMany(cascade = CascadeType.ALL)
	private Set<Post> comments;
	@ManyToMany
	@JoinTable(name = "liked_post", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<ExoUser> users;
	
	
}
