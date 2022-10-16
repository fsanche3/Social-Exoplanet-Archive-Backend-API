package com.dev.controllers;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.models.ExoUser;
import com.dev.models.Post;
import com.dev.models.dtos.LikesResponse;
import com.dev.models.dtos.PostDto;
import com.dev.services.ExoUserService;
import com.dev.services.PostService;
import com.dev.utils.JwtUtil;

@RestController
@RequestMapping(path = "/post")
public class PostController {

	private PostService postServ;
	private JwtUtil jwtUtil;
	private ExoUserService userServ;
	

	public PostController(PostService postServ, JwtUtil jwtUtil, ExoUserService userServ) {
		this.postServ = postServ;
		this.jwtUtil = jwtUtil;
		this.userServ = userServ;
		
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable("id") int id){
			ExoUser user = userServ.findById(id).get();
	        return new ResponseEntity<>(postServ.findPostByUserId(user), HttpStatus.OK);
	}
	
	@GetMapping("/likes/{id}")
	public ResponseEntity<LikesResponse> getLikesByUser(@PathVariable("id") int id){
		ExoUser user = userServ.findById(id).get();
		
        return new ResponseEntity<>(postServ.getLikesByUser(user), HttpStatus.OK);
}
	
	@GetMapping("/user/comments/{id}")
	public ResponseEntity<List<PostDto>> getCommentsByUser(@PathVariable("id") int id){
			ExoUser user = userServ.findById(id).get();
	        return new ResponseEntity<>(postServ.findCommentsByUserId(user), HttpStatus.OK);
	}
	
	@GetMapping("/{planet}")
	public ResponseEntity<List<PostDto>> getPostsByPlanet(@PathVariable("planet") String planet){
        return new ResponseEntity<>(postServ.findByPlanet(planet), HttpStatus.OK);
	}

	@GetMapping("/post-comments/{post}")
	public ResponseEntity<List<PostDto>> getCommentsByPost(@PathVariable("post") int id){
        return new ResponseEntity<>(postServ.findCommentsByPostId(id), HttpStatus.OK);

	}
	
	@PostMapping("/upsert")
	public ResponseEntity<Boolean> uploadPost(@RequestBody Post post, 
    		@RequestHeader(value = "Authorization", required = true) String authorization) throws UnsupportedEncodingException{
		
		int id = jwtUtil.getId(authorization);
		ExoUser user = userServ.findById(id).get();
		
		post.setDate(Timestamp.valueOf(LocalDateTime.now()));
		post.setAuthor(user);
		post.setParent_id(0);
		
		postServ.upsert(post);
		
        return new ResponseEntity<>(true, HttpStatus.OK);

	}
	
	@PostMapping("/comment/{postId}")
	public ResponseEntity<Boolean> uploadComment(@PathVariable int postId, @RequestBody Post post,
    		@RequestHeader(value = "Authorization", required = true) String authorization) throws UnsupportedEncodingException{
		
		int id = jwtUtil.getId(authorization);
		ExoUser user = userServ.findById(id).get();

		post.setDate(Timestamp.valueOf(LocalDateTime.now()));
		post.setAuthor(user);
		post.setParent_id(postId);
		
		postServ.upsert(post);
        return new ResponseEntity<>(true, HttpStatus.OK);

	}

	
	
	@PutMapping("/like/{postId}")
	public ResponseEntity<Boolean> likePost(@PathVariable("postId") int post_id, 
			@RequestHeader(value = "Authorization", required = true) String authorization) throws UnsupportedEncodingException{
		
		int id = jwtUtil.getId(authorization);
		ExoUser user = userServ.findById(id).get();
		Post post = postServ.findById(post_id).get(); 
		
		Set<ExoUser> set = post.getUsers();
		set.add(user);
		post.setUsers(set);
		
		postServ.upsert(post);
        return new ResponseEntity<>(true, HttpStatus.OK);
	}

	@PutMapping("/unlike/{postId}")
	public ResponseEntity<Boolean> unlikePost(@PathVariable("postId") int post_id, 
			@RequestHeader(value = "Authorization", required = true) String authorization) throws UnsupportedEncodingException{
		
		int id = jwtUtil.getId(authorization);
		ExoUser user = userServ.findById(id).get();
		Post post = postServ.findById(post_id).get();
		
		Set<ExoUser> set = post.getUsers();
		
		if(set.contains(user)) {
			set.remove(user);
			post.setUsers(set);
			
			postServ.upsert(post);
	        return new ResponseEntity<>(true, HttpStatus.OK);
	        
		} else {
			
	        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
		}
		
	}
	

}
