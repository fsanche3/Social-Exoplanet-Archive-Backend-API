package com.dev.services.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.dev.SocialExoplanetArchiveApplication;
import com.dev.models.ExoUser;
import com.dev.models.dtos.LoginRequest;
import com.dev.repos.ExoUserRepo;
import com.dev.services.ExoUserService;

@SpringBootTest(classes = SocialExoplanetArchiveApplication.class)
public class ExoUserServiceTest {
	
	@MockBean
	ExoUserRepo userRepo;
	
	@Autowired
	ExoUserService userServ;
	
	@Test
	public void findById() {
		ExoUser user = new ExoUser(1,"","","");
		
		Mockito.when(userRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
		
		Assertions.assertEquals(userServ.findById(1), Optional.of(user));
	}
	
	@Test
	public void upsert() {
		ExoUser user = new ExoUser(1,"","","");
		
		Mockito.when(userRepo.save(Mockito.any())).thenReturn(user);
		
		Assertions.assertEquals(userServ.upsert(user), user);
	}
	
	@Test
	public void verifyRegistration() {
		List<ExoUser> list = new ArrayList<ExoUser>();
		ExoUser user = new ExoUser(1,"","","");
		
		Mockito.when(userRepo.findByUsername(user.getUsername())).thenReturn(list);
		Mockito.when(userRepo.save(Mockito.any())).thenReturn(user);
		
		Assertions.assertEquals(userServ.verifyRegistration(user), true);		
	}
	
	@Test
	public void cannotVerifyRegistration() {
		List<ExoUser> list = new ArrayList<ExoUser>();
		ExoUser user = new ExoUser(1,"","","");
		list.add(user);
		
		Mockito.when(userRepo.findByUsername(user.getUsername())).thenReturn(list);
		Mockito.when(userRepo.save(Mockito.any())).thenReturn(user);
		
		Assertions.assertEquals(userServ.verifyRegistration(user), false);
	}
	
	@Test
	public void verifyAuth() {
		List<ExoUser> list = new ArrayList<>();
		LoginRequest body = new LoginRequest("username","password");
		ExoUser user = new ExoUser(1,"username","password","");

		list.add(user);
		
		Mockito.when(userRepo.findByUsername(Mockito.anyString())).thenReturn(list);
		
		Assertions.assertEquals(userServ.verifyAuth(body), user);
	}
	
	@Test
	public void cannotVerifyUsernameAuth() {
		List<ExoUser> list = new ArrayList<>();
		LoginRequest body = new LoginRequest("franklynBanking","password");
		ExoUser user = new ExoUser(1,"","","");
		
		Mockito.when(userRepo.findByUsername(Mockito.anyString())).thenReturn(list);
		
		Assertions.assertEquals(userServ.verifyAuth(body), null);
	}
	
	@Test
	public void cannotVerifyPasswordAuth() {
		List<ExoUser> list = new ArrayList<>();
		LoginRequest body = new LoginRequest("franklynBanking","password");
		ExoUser user = new ExoUser(1,"","","");

		list.add(user);
		
		Mockito.when(userRepo.findByUsername(Mockito.anyString())).thenReturn(list);
		
		Assertions.assertEquals(userServ.verifyAuth(body), null);
	}

}
