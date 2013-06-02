package cz.muni.muniGroup.cookbook.managers;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.muni.muniGroup.cookbook.entities.*;
import cz.muni.muniGroup.cookbook.exceptions.*;

public class UserManagerImpl implements UserManager{

	@Override
	public void create(User user) throws ConnectivityException, CookbookException {
		if (user == null)
			throw new IllegalArgumentException("user is null");
		if (user.getEmail() == null || user.getEmail().length() == 0)
			throw new IllegalArgumentException("Email is empty");

		if (user.getPassword() == null || user.getPassword().length() < 4)
			throw new IllegalArgumentException("Password is shorter then 4 characters");
		
		if (user.getName() == null || user.getName().length() == 0)
			throw new IllegalArgumentException("Name is empty");

		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("email", user.getEmail()));
		nameValuePairs.add(new BasicNameValuePair("name", user.getName()));
		nameValuePairs.add(new BasicNameValuePair("password", user.getPassword()));

		JSONArray jArray = DBWorker.dbQuery(nameValuePairs, "user_create.php");
		if (jArray == null)
			throw new NullPointerException("Prazdne JSON pole.");

		try {
			String error = jArray.getJSONObject(0).getString("error");
			if (error.length() != 0)
				throw new CookbookException(error);
		} catch (JSONException e1) {}

		JSONObject json_data;
		try {
			json_data = jArray.getJSONObject(0);
			user.setId(json_data.getInt("id"));
		} catch (JSONException e) {
			throw new CookbookException("Chyba pøi parsovaní JSON formátu návratové hodnoty.", e);
		} 
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void update(User user) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public User getUserById(int id) throws ConnectivityException, CookbookException {
		if (id <= 0)
			throw new IllegalArgumentException("Id is not positive number");

		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("id", String.valueOf(id)));

		JSONArray jArray = DBWorker.dbQuery(nameValuePairs, "getUserById.php");
		if (jArray == null)
			throw new NullPointerException("Prazdne JSON pole.");

		try {
			if (jArray.getJSONObject(0).getBoolean("empty") == true)
				return null;
		} catch (JSONException e1) {}

		JSONObject json_data;
		User user;
		try {
			json_data = jArray.getJSONObject(0);
			user = new User();
			user.setId(json_data.getInt("id"));
			user.setEmail(json_data.getString("email"));
			user.setName(json_data.getString("name"));
		} catch (JSONException e) {
			throw new CookbookException("Chyba pøi parsovaní JSON formátu návratové hodnoty.", e);
		} 
		return user;
	}

	@Override
	public User loginUser(String email, String password) throws ConnectivityException, CookbookException {
		if (email == null || email.length() <= 0)
			throw new IllegalArgumentException("Email is empty");

		if (password == null || password.length() <= 0)
			throw new IllegalArgumentException("Password is empty");

		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("email", email));
		nameValuePairs.add(new BasicNameValuePair("password", password));

		JSONArray jArray = DBWorker.dbQuery(nameValuePairs, "user_login.php");
		if (jArray == null)
			throw new NullPointerException("Prazdne JSON pole.");

		try {
			if (jArray.getJSONObject(0).getBoolean("empty") == true)
				return null;
		} catch (JSONException e1) {}

		JSONObject json_data;
		User user;
		try {
			json_data = jArray.getJSONObject(0);
			user = new User();
			user.setId(json_data.getInt("id"));
			user.setEmail(json_data.getString("email"));
			user.setName(json_data.getString("name"));
		} catch (JSONException e) {
			throw new CookbookException("Chyba pøi parsovaní JSON formátu návratové hodnoty.", e);
		} 
		return user;
	}
}