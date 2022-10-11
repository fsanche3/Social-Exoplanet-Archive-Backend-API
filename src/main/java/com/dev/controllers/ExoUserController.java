package com.dev.controllers;

import java.io.UnsupportedEncodingException;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dev.models.ExoUser;
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
	
	@PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam(value = "file") MultipartFile file,
    		@RequestHeader(value = "Authorization", required = true) String authorization) throws UnsupportedEncodingException
 {	
       return new ResponseEntity<>(awsServ.uploadFile(file), HttpStatus.OK);
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
