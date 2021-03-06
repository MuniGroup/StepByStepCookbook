package cz.muni.muniGroup.cookbook.managers;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import cz.muni.muniGroup.cookbook.entities.AssignedIngredient;
import cz.muni.muniGroup.cookbook.entities.Ingredient;
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
	public String getDescription(int id) throws ConnectivityException, CookbookException {
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("id", String.valueOf(id)));
		JSONArray jArray = DBWorker.dbQuery(nameValuePairs, "getDescriptionByRecipeId.php");
		if (jArray == null)
			throw new NullPointerException("Prazdne JSON pole.");

		try {
			
			if (jArray.getJSONObject(0).getBoolean("empty") == true)
				return new String();
			
			if (jArray.getJSONObject(0).getString("error") != null)
				throw new CookbookException("Chyba v DB dotazu: "+jArray.getJSONObject(0).getString("error"));
		} catch (JSONException e1) {}

		JSONObject json_data;
		String description;
		try {
			json_data = jArray.getJSONObject(0);
			description = new String(json_data.getString("description"));
		} catch (JSONException e) {
			throw new CookbookException("Chyba p�i parsovan� JSON form�tu n�vratov� hodnoty.", e);
		} 
		return description;
	}

	@Override
	public List<AssignedIngredient> getIngredients(int id) throws ConnectivityException, CookbookException {
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("id", String.valueOf(id)));
		JSONArray jArray = DBWorker.dbQuery(nameValuePairs, "getIngredientsByRecipeId.php");
		if (jArray == null)
			throw new NullPointerException("Prazdne JSON pole.");

		try {
			
			if (jArray.getJSONObject(0).getBoolean("empty") == true)
				return new ArrayList<AssignedIngredient>();
			
			if (jArray.getJSONObject(0).getString("error") != null)
				throw new CookbookException("Chyba v DB dotazu: "+jArray.getJSONObject(0).getString("error"));
		} catch (JSONException e1) {}

		
		JSONObject json_data;
		List<AssignedIngredient> assignedIngredients = new ArrayList<AssignedIngredient>();
		AssignedIngredient assignedIngredient;
		Ingredient ingredient;
		try {
			for (int i = 0; i < jArray.length(); i++){
				json_data = jArray.getJSONObject(i);
				ingredient = new Ingredient();
				assignedIngredient = new AssignedIngredient();
				ingredient.setId(json_data.getInt("id"));
				ingredient.setName(json_data.getString("name"));
				assignedIngredient.setIngredient(ingredient);
				assignedIngredient.setUnit(json_data.getString("unit"));
				assignedIngredient.setAmount(json_data.getInt("amount"));
				assignedIngredients.add(assignedIngredient);
			}
		} catch (JSONException e) {
			throw new CookbookException("Chyba p�i parsovan� JSON form�tu n�vratov� hodnoty.", e);
		} 
		return assignedIngredients;
	}
	
	@Override
	public List<Recipe> getRecipes(int categoryId, int order, int limitFrom, int limit) throws ConnectivityException, CookbookException {
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("categoryId", String.valueOf(categoryId)));
		nameValuePairs.add(new BasicNameValuePair("order", String.valueOf(order)));
		nameValuePairs.add(new BasicNameValuePair("limitFrom", String.valueOf(limitFrom)));
		nameValuePairs.add(new BasicNameValuePair("limit", String.valueOf(limit)));
		Log.i("getRecipes","BasicNameValuePair order "+order);
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
				recipe.setRating((float)json_data.getDouble("rating"));
				user = userManager.getUserById(json_data.getInt("author"));
				recipe.setAuthor(user);
				recipes.add(recipe);
			}
		} catch (JSONException e) {
			throw new CookbookException("Chyba p�i parsovan� JSON form�tu n�vratov� hodnoty.", e);
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
			throw new CookbookException("Chyba p�i parsovan� JSON form�tu n�vratov� hodnoty.", e);
		} 
		return categories;
	}

	
}