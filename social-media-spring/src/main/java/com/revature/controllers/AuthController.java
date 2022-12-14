package com.revature.controllers;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.annotations.Authorized;
import com.revature.dtos.LoginRequest;
import com.revature.dtos.RegisterRequest;
import com.revature.models.User;
import com.revature.services.AuthService;
import com.revature.services.UserService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://44.201.120.76:4200", allowCredentials = "true")
public class AuthController {

    private final AuthService authService;
    private final UserService userSvc;

    @Autowired
    public AuthController(AuthService authService, UserService userSvc) {
        this.authService = authService;
        this.userSvc=userSvc;
    }
    
    /**
     * finds account in database and stores current user into a server session
     * @param loginRequest
     * @param session
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        Optional<User> optional = authService.findByCredentials(loginRequest.getEmail(), loginRequest.getPassword());

        if(!optional.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        session.setAttribute("user", optional.get());

        return ResponseEntity.ok(optional.get());
    }
    
    /**
     * Verifies user email within the database
     * @param loginRequest
     * @param session
     * @return
     */
    @PostMapping("/forgottenpassword")
    public ResponseEntity<User> findEmail(@RequestBody LoginRequest loginRequest, HttpSession session) {
    	Optional<User> optional = authService.findByEmail(loginRequest.getEmail());
    	
    	 if(!optional.isPresent()) {
             return ResponseEntity.badRequest().build();
         }

         session.setAttribute("user", optional.get());

         return ResponseEntity.ok(optional.get());
    }
    
    /**
     * removes the session's current user
     * @param session
     * @return
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.removeAttribute("user");

        return ResponseEntity.ok().build();
    }
    
    /**
     * creates a new account and stores it into the database
     * @param registerRequest
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest registerRequest) {
        User created = new User(0,
                registerRequest.getEmail(),
                registerRequest.getPassword(),
                registerRequest.getFirstName(),
                registerRequest.getLastName(),
                registerRequest.getUsername(),
                "https://cdn.pixabay.com/photo/2021/06/07/13/45/user-6318003_960_720.png");
        		
        		

        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(created));
    }
    
    /**
     * changes current users password
     * @param loginRequest
     * @param session
     * @return
     */
    @Authorized
    @PostMapping("/resetuserpassword")
    public ResponseEntity<User> resetUserPassword(@RequestBody LoginRequest loginRequest, HttpSession session) {
    	
    	User sessionUser = (User) session.getAttribute("user");
    	sessionUser.setPassword(loginRequest.getPassword());
    	userSvc.save(sessionUser);
    	return ResponseEntity.ok(sessionUser);
    }
    

}