package com.revature.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
//	private final PostService postService;
	
	
	public UserController(UserService userService) {
		super();
		this.userService = userService;
//		this.postService = postService;
	}

	@PutMapping("/updateusername")
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

	@PutMapping("/updatedescription")
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

	
	/**
	 * The controller method to change the birthday of the user
	 * @param session the HTTP session
	 * @param req the HTTPServlet req
	 * @return A string stating whether or not the birthday was changed
	 */
	@PutMapping("/changeBirthday")
	public String updateBirthday(HttpSession session, HttpServletRequest req) {
		
		User sessionUser = (User) session.getAttribute("user");
		User user = userService.findByUsernameCredentials(sessionUser.getUsername(), sessionUser.getPassword()).get();
		
		
		String newBirthDay = req.getParameter("newBirthDay");
		String newBirthMonth = req.getParameter("newBirthMonth");
		String newBirthYear = req.getParameter("newBirthYear");
		
		
		return userService.changeDoB(user, newBirthDay, newBirthMonth, newBirthYear);
	}
	
	/**
	 * The endpoint used to change the profile picture
	 * @param session the HTTP session
	 * @param req the HTTPServlet req
	 * @return a string stating if the profile picture has changed
	 */
	@PutMapping("/changeProfilePicture")
	public String updatePicture(HttpSession session, HttpServletRequest req) {
		
		User sessionUser = (User) session.getAttribute("user");
		User user = userService.findByUsernameCredentials(sessionUser.getUsername(), sessionUser.getPassword()).get();
		
		String newProfilePicture = req.getParameter("newProfilePicture");
		
		return userService.changeProfilePicture(user, newProfilePicture);
	}


	/***
	 * This is a server side search statement.
	 * Currently not in use do to existence of angular side one.
	 * @param req	query
	 * @return
	 */
	@PostMapping("/search")
	public ResponseEntity<List<User>> searchUser(HttpServletRequest req){
		String query=req.getParameter("query");
		return ResponseEntity.ok(userService.searchUsers(query));
	}
	

	// view one user that is currently logged in.
//	@Authorized
//	@PostMapping("/viewPost")
//	public String viewPost(HttpSession session, HttpServletRequest req){
//		User sessionUser = (User) session.getAttribute("user");
//		User user = userService.findByUsernameCredentials(sessionUser.getUsername(), sessionUser.getPassword()).get();
//		int postId=Integer.parseInt(req.getParameter("postId"));
//		Post post=postService.getPost(postId);
//		boolean viewedBefore=userService.viewPost(user, post);
//		if (viewedBefore)
//			return "false";
//		else
//			return "true";
//		
//	}
	
	// view one user, BUT this functionality should already be handled by the login controller so this may be irrelevant.
	@GetMapping("/viewUser")
	public Optional<User> findUser(HttpSession session, HttpServletRequest req) {
		
		User sessionUser = (User) session.getAttribute("user");
		User user = userService.findByUsernameCredentials(sessionUser.getUsername(), sessionUser.getPassword()).get();
		
		//return findUser(user.getUsername());
		System.out.println("Getting Information");
		return userService.findByUsername(user.getUsername());
	}
	
	/**
	 * Find the User, given just a username, used for finding a person.
	 * This username is taken from a parameter
	 * @param req, looking for the parameter "username"
	 * @return the found User
	 */
//	@Authorized
	@GetMapping("/peek")
	public Optional<User> findUser(HttpServletRequest req) {
		
		String username = req.getParameter("username");
		
//		return userService.findByUsername(username);
		return findUser(username);
		
		
	}
	
	/**
	 * Find the user, given the username, given just a username
	 * @param username
	 * @return
	 */
	@Authorized
	@GetMapping("/peek/{username}")
	public Optional<User> findUser(@PathVariable("username") String username) {
		
		return userService.findByUsername(username);

		
		
	}

	/***
	 * This returns the name of all the users.
	 * @return
	 */
	@PostMapping("/getAllUsernames")
	public List<String> getAllUsernames(){
		
		List<String> usernameList = new ArrayList<>();
		List<User> userList = new ArrayList<>();
		userList = userService.findAllUsers();
		
		for(int i=0; i<userList.size(); i++) {
		String temp = userList.get(i).getUsername();
		usernameList.add(temp);
		}
		
		return usernameList;
	}
	
}
