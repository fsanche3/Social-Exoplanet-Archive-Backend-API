package com.dev.controllers;

import java.io.UnsupportedEncodingException;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dev.models.ExoUser;
import com.dev.models.dtos.ExoUserDto;
import com.dev.models.dtos.ImageRequest;
import com.dev.services.AwsService;
import com.dev.services.ExoUserService;
import com.dev.utils.JwtUtil;

@RestController
@RequestMapping(path = "/user")
public class ExoUserController {
	
	private AwsService awsServ;
	private ExoUserService userServ;
	private JwtUtil jwt;
	
	public ExoUserController(AwsService awsServ, ExoUserService userServ, JwtUtil jwt) {
		this.awsServ = awsServ;
		this.userServ = userServ;
		this.jwt = jwt;
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<ExoUserDto> getUserById(@PathVariable("id") int id) {

		ExoUserDto dto = new ExoUserDto(userServ.findById(id).get());

		return ResponseEntity.status(HttpStatus.OK).body(dto);

	}
	
	@PutMapping("/update-img/{id}")
	public ResponseEntity<Boolean> updateUser(@PathVariable("id") int id, @RequestBody ExoUser exo)

	{
		ExoUser user = userServ.findById(id).get();
		user.setImg(exo.getImg());
		
		userServ.upsert(user);
	       return new ResponseEntity<>(true, HttpStatus.OK);

	}
	
	@PostMapping("/upload")
    public ResponseEntity<ImageRequest> uploadFile(@RequestParam(value = "file") MultipartFile file)
	{	
		ImageRequest resp = new ImageRequest(awsServ.uploadFile(file));
       return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {
        byte[] data = awsServ.downloadFile(fileName);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName,
			@RequestHeader(value = "Authorization", required = true) String authorization)
 {
        return new ResponseEntity<>(awsServ.deleteFile(fileName), HttpStatus.OK);
    }

}
