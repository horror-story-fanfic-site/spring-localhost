package com.revature.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RestController;

import com.revature.annotations.Authorized;
import com.revature.models.Post;
import com.revature.models.User;
import com.revature.services.PostService;
import com.revature.services.UserService;


/**
 * This controller enables the user to update their username and description 
 * 
 * @author Jordan Parsa
 * @version 1.0
 * @since 31-08-2022
 * 
 *
 */

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class UserController {
	
	private final UserService userService;
	private final PostService postService;
	
	
	public UserController(UserService userService, PostService postService) {
		super();
		this.userService = userService;
		this.postService = postService;
	}

	@PostMapping("/updateusername")
	public String updateUsername(HttpSession session, HttpServletRequest req) {
		User sessionUser = (User) session.getAttribute("user");
		User user = userService.findByUsernameCredentials(sessionUser.getUsername(), sessionUser.getPassword()).get();
		//User user = service.findByCredentials(sessionUser.getEmail(), sessionUser.getPassword()).get();
		String username = req.getParameter("newUsername");
		user.setUsername(username);
		try {
			userService.save(user);
			return "true";
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "false";
	}

	@PostMapping("/updatedescription")
	public String updateDescription(HttpSession session, HttpServletRequest req) {
		User sessionUser = (User) session.getAttribute("user");
		User user = userService.findByUsernameCredentials(sessionUser.getUsername(), sessionUser.getPassword()).get();
		//User user = service.findByCredentials(sessionUser.getEmail(), sessionUser.getPassword()).get();
		String description = req.getParameter("newDescription");
		user.setDescription(description);
		try {
		userService.save(user);
		return "true";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "false";

	}

	@PostMapping("/search")
	public ResponseEntity<List<User>> searchUser(HttpServletRequest req){
		String query=req.getParameter("query");
		return ResponseEntity.ok(userService.searchUsers(query));
	}
	
	@Authorized
	@PostMapping("/viewPost")
	public String viewPost(HttpSession session, HttpServletRequest req){
		User sessionUser = (User) session.getAttribute("user");
		User user = userService.findByUsernameCredentials(sessionUser.getUsername(), sessionUser.getPassword()).get();
		int postId=Integer.parseInt(req.getParameter("postId"));
		Post post=postService.getPost(postId);
		boolean viewedBefore=userService.viewPost(user, post);
		if (viewedBefore)
			return "false";
		else
			return "true";
		
	}
	
}
