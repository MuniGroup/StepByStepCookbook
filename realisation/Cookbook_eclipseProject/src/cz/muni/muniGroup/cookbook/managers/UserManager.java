package cz.muni.muniGroup.cookbook.managers;

import cz.muni.muniGroup.cookbook.entities.*;
import cz.muni.muniGroup.cookbook.exceptions.ConnectivityException;
import cz.muni.muniGroup.cookbook.exceptions.CookbookException;

public interface UserManager {

	/**
	 * 
	 * @param User
	 * @throws ConnectivityException 
	 * @throws CookbookException 
	 */
	void create(User user) throws ConnectivityException, CookbookException;

	/**
	 * 
	 * @param id of user
	 */
	void delete(int id);

	/**
	 * 
	 * @param User
	 */
	void update(User user);
	/**
	 * 
	 * @param id
	 */
	User getUserById(int id);

	/**
	 * 
	 * @param login
	 * @param email
	 * @throws ConnectivityException 
	 * @throws CookbookException
	 * @return user if the login data was correct, null otherwise
	 */
	User loginUser(String email, String password) throws ConnectivityException, CookbookException;

}