package cz.muni.muniGroup.cookbook.managers;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.muni.muniGroup.cookbook.entities.Recipe;
import cz.muni.muniGroup.cookbook.entities.RecipeCategory;
import cz.muni.muniGroup.cookbook.entities.User;
import cz.muni.muniGroup.cookbook.exceptions.ConnectivityException;
import cz.muni.muniGroup.cookbook.exceptions.CookbookException;



public class RecipeManagerImpl implements RecipeManager{

	@Override
	public void create(Recipe recipe) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void delete(Recipe recipe) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void update(Recipe recipe) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
		
	}

	@Override
	public Recipe getRecipeById(int id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Recipe> getRecipes(int categoryId, int limitFrom, int limit) throws ConnectivityException, CookbookException {
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("categoryId", String.valueOf(categoryId)));
		nameValuePairs.add(new BasicNameValuePair("limitFrom", String.valueOf(limitFrom)));
		nameValuePairs.add(new BasicNameValuePair("limit", String.valueOf(limit)));

		JSONArray jArray = DBWorker.dbQuery(nameValuePairs, "getRecipes.php");
		if (jArray == null)
			throw new NullPointerException("Prazdne JSON pole.");

		try {
			
			if (jArray.getJSONObject(0).getBoolean("empty") == true)
				return new ArrayList<Recipe>();
			
			if (jArray.getJSONObject(0).getString("error") != null)
				throw new CookbookException("Chyba v DB dotazu: "+jArray.getJSONObject(0).getString("error"));
		} catch (JSONException e1) {}

		UserManager userManager = new UserManagerImpl();
		JSONObject json_data;
		List<Recipe> recipes = new ArrayList<Recipe>();
		Recipe recipe;
		User user;
		try {
			for (int i = 0; i < jArray.length(); i++){
				json_data = jArray.getJSONObject(i);
				recipe = new Recipe();
				recipe.setId(json_data.getInt("id"));
				recipe.setName(json_data.getString("name"));
				user = userManager.getUserById(json_data.getInt("author"));
				recipe.setAuthor(user);
				recipes.add(recipe);
			}
		} catch (JSONException e) {
			throw new CookbookException("Chyba pøi parsovaní JSON formátu návratové hodnoty.", e);
		} 
		return recipes;
	}

	@Override
	public List<Recipe> findByNamePart(String namePart) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public List<RecipeCategory> getRecipeCategories() throws ConnectivityException, CookbookException {
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		JSONArray jArray = DBWorker.dbQuery(nameValuePairs, "getRecipeCategories.php");
		if (jArray == null)
			throw new NullPointerException("Prazdne JSON pole.");

		try {
			
			if (jArray.getJSONObject(0).getBoolean("empty") == true)
				return new ArrayList<RecipeCategory>();
			
			if (jArray.getJSONObject(0).getString("error") != null)
				throw new CookbookException("Chyba v DB dotazu: "+jArray.getJSONObject(0).getString("error"));
		} catch (JSONException e1) {}

		JSONObject json_data;
		List<RecipeCategory> categories = new ArrayList<RecipeCategory>();
		RecipeCategory recipeCategory;
		try {
			for (int i = 0; i < jArray.length(); i++){
				json_data = jArray.getJSONObject(i);
				recipeCategory = new RecipeCategory();
				recipeCategory.setId(json_data.getInt("id"));
				recipeCategory.setName(json_data.getString("name"));
				categories.add(recipeCategory);
			}
		} catch (JSONException e) {
			throw new CookbookException("Chyba pøi parsovaní JSON formátu návratové hodnoty.", e);
		} 
		return categories;
	}

	
}