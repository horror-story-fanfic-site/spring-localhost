package com.revature.services;

import com.revature.models.User;
import com.revature.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import javax.imageio.ImageIO;

@Service
public class UserService { // implements UserServiceInterface {

	/////Variable
	private final UserRepository userRepository;


	/////Constructor
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
   

    @Deprecated
	public Optional<User> findByCredentials(String email, String password) {
		return userRepository.findByEmailAndPassword(email, password);
	}

    /**
     * Find user based on a username and password
     * @param username the username with will be looked for
     * @param password the password of the user
     * @return the user object of the 
     */
	public Optional<User> findByUsernameCredentials(String username, String password) {
		return userRepository.findByUsernameAndPassword(username, password);
	}

	public User getuserById(int id) {
		return userRepository.getReferenceById(id);
	}

	public User save(User user) {
		return userRepository.save(user);
	}

	/**
	 * This is the end point for search bar. Determines it by characters and
	 * followers their order.
	 * 
	 * @param search
	 * @return
	 */
	public List<User> searchUsers(String search) {
		// Grabs a list of all the current users.
		List<User> users = new ArrayList<User>();
		// Copies this over as to not mess with the original model.
		users.addAll(userRepository.findAll());

		// Sets this all to lower case as this is not meant to be case sensitive.
		search = search.toLowerCase();

		// The holder for the username.
		String username;

		// This loops through all the usernames.
		for (int x = 0; x < users.size(); x++) {
			username = users.get(x).getUsername().toLowerCase();
			int y, w;

			// Loop through all the characters in the search.
			charMatch: for (y = 0, w = 0; y < search.length(); y++) {

				// Loop through all the characters in the username.
				// This is to keep track of the order as to make it so characters before are not
				// counted.
				while (w < username.length()) {
					if (search.charAt(y) == username.charAt(w)) {
						continue charMatch;
					}
					w++;
				}
				// This is only reached if the username ran out without finding a char match.
				break;// This will exit the loop even if there is more characters to search.
			}

			// If the username hit the end before the characters to search hit the end this
			// is ran.
			if (w == username.length()) {
				// This removes the username from the list of returns.
				users.remove(users.get(x));
				x--;// This is to compensate for list order.
			}
		}
		return users;
	}
     
    /**
     * Change the birthday of the user in the database
     * @param user the model of the user
     * @param newDay the day in which the user wants to change their birthday to
     * @param newMonth the month in which user wants to change their birthday to
     * @param newYear the year in which the user wants to change their birthday to
     * @return
     */
    public String changeDoB(User user, String newDay, String newMonth, String newYear) {
    	
    	user.setBirthDay(newDay);
    	user.setBirthMonth(newMonth);
    	user.setBirthYear(newYear);

		
			// Switch case to make sure the days of each month are correct
			switch (newMonth) {

				case ("1")://accounting for January, day must be between no lower than 1 and no greater than 31 
					if (Integer.parseInt(newDay) >= 1 && Integer.parseInt(newDay) <= 31) {
						userRepository.save(user);
						return "Birthday Changed";
					}
					break;
				
		
				//accounting for Feb, day must be between no lower than 1 and no greater than 28 except for leap year 
				case ("2"):
					if (new GregorianCalendar().isLeapYear(Integer.parseInt(newYear))) {
						if (Integer.parseInt(newDay) >= 1 && Integer.parseInt(newDay) <= 29) {
							userRepository.save(user);
							return "Birthday Changed";
						}
					} else {
						if (Integer.parseInt(newDay) >= 1 && Integer.parseInt(newDay) <= 28) {
							userRepository.save(user);
							return "Birthday Changed";
						}
					}
					break;

				//accounting for March, day must be between no lower than 1 and no greater than 31 
				case ("3"):
					if (Integer.parseInt(newDay) >= 1 && Integer.parseInt(newDay) <= 31) {
						userRepository.save(user);
						return "Birthday Changed";
					}
					break;

				//accounting for April, day must be between no lower than 1 and no greater than 30 
				case ("4"):
					if (Integer.parseInt(newDay) >= 1 && Integer.parseInt(newDay) <= 30) {
						userRepository.save(user);
						return "Birthday Changed";
					}
					break;

				//accounting for May, day must be between no lower than 1 and no greater than 31 
				case ("5"):
					if (Integer.parseInt(newDay) >= 1 && Integer.parseInt(newDay) <= 31) {
						userRepository.save(user);
						return "Birthday Changed";
					}
					break;
				//accounting for June, day must be between no lower than 1 and no greater than 30 
				case ("6"):
					if (Integer.parseInt(newDay) >= 1 && Integer.parseInt(newDay) <= 30) {
						userRepository.save(user);
						return "Birthday Changed";
					}
					break;

				//accounting for July, day must be between no lower than 1 and no greater than 31 
				case ("7"):
					if (Integer.parseInt(newDay) >= 1 && Integer.parseInt(newDay) <= 31) {
						userRepository.save(user);
						return "Birthday Changed";
					}
					break;
					
				//accounting for August, day must be between no lower than 1 and no greater than 31 
				case ("8"):
					if (Integer.parseInt(newDay) >= 1 && Integer.parseInt(newDay) <= 31) {
						userRepository.save(user);
						return "Birthday Changed";
					}
					break;

				//accounting for Sept, day must be between no lower than 1 and no greater than 30 
				case ("9"):
					if (Integer.parseInt(newDay) >= 1 && Integer.parseInt(newDay) <= 30) {
						userRepository.save(user);
						return "Birthday Changed";
					}
					break;

				//accounting for Oct, day must be between no lower than 1 and no greater than 31 
				case ("10"):
					if (Integer.parseInt(newDay) >= 1 && Integer.parseInt(newDay) <= 31) {
						userRepository.save(user);
						return "Birthday Changed";
					}
					break;

				//accounting for Nov, day must be between no lower than 1 and no greater than 30
				case ("11"):
					if (Integer.parseInt(newDay) >= 1 && Integer.parseInt(newDay) <= 30) {
						userRepository.save(user);
						return "Birthday Changed";
					}
					break;

				//accounting for Dec, day must be between no lower than 1 and no greater than 31 
				case ("12"):
					if (Integer.parseInt(newDay) >= 1 && Integer.parseInt(newDay) <= 31) {
						userRepository.save(user);
						return "Birthday Changed";
					}
					break;

				default:
					return "Invalid Date";
			}

		return "Invalid Date";
    	
    }
    
    
    /**
     * Change the user's profile picture
     * @param user the user model of the logged in user
     * @param newPicture the new picture for the user, this is in the form of a URL
     * @return a string stating if the picture has changed
     */
    public String changeProfilePicture(User user, String newPicture) {
    	
    	try {
    		//check if the url is valid picture
    		ImageIO.read(new URL(newPicture));
    		
    		//save the picture
    		user.setProfilePic(newPicture);
    		userRepository.save(user);
    		return "Picture Changed";
    		
    	}catch(Exception e) {
    		
    		e.printStackTrace();
    	}
    	
		return "Picture not Changed";
    	
    }
    
    /***
     * Run this to record when a user views a post.
     * @param user
     * @param post
     * @return
     */
//    public boolean viewPost(User user, Post post) {
//		List<Post> posts=user.getPostViews();
//		if (posts.contains(post)) {
//			return false;
//		}
//    	posts.add(post);
//		
//		userRepository.save(user);
//		return true;
//	}


	public Optional<User> findUserFollowRequest(String username) {
		return userRepository.findByUsername(username);
	}

	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	/***
	 * Returns all the others.
	 * @return a list of all users
	 */
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}


	/**
	 * 
	 * @param username the username of the User
	 * @return return a user if it exists
	 */
	public Optional<User> findByUsername(String username) {
		
		return userRepository.findByUsername(username);
	}


}

