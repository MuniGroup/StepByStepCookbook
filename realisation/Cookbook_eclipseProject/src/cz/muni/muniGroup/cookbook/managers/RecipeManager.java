package cz.muni.muniGroup.cookbook.managers;

import java.util.List;

import cz.muni.muniGroup.cookbook.entities.AssignedIngredient;
import cz.muni.muniGroup.cookbook.entities.Recipe;
import cz.muni.muniGroup.cookbook.entities.RecipeCategory;
import cz.muni.muniGroup.cookbook.exceptions.ConnectivityException;
import cz.muni.muniGroup.cookbook.exceptions.CookbookException;

public interface RecipeManager {

	/**
	 * 
	 * @param recipe
	 */
	void create(Recipe recipe);

	/**
	 * 
	 * @param recipe
	 */
	void delete(Recipe recipe);

	/**
	 * 
	 * @param recipe
	 */
	void update(Recipe recipe);

	/**
	 * 
	 * @param id of recipe
	 */
	Recipe getRecipeById(int id);
	
	String getDescription(int id) throws ConnectivityException, CookbookException;
	
	List<AssignedIngredient> getIngredients(int id) throws ConnectivityException, CookbookException;
	/**
	 * 
	 * @param categoryId
	 * @param limitFrom
	 * @param limit
	 * @throws ConnectivityException 
	 * @throws CookbookException 
	 */
	List<Recipe> getRecipes(int categoryId, int order, int limitFrom, int limit) throws ConnectivityException, CookbookException;

	/**
	 * 
	 * @param namePart - part of recipe name
	 */
	List<Recipe> findByNamePart(String namePart);
	
	/**
	 * 
	 * @throws ConnectivityException 
	 * @throws CookbookException 
	 */
	List<RecipeCategory> getRecipeCategories() throws ConnectivityException, CookbookException;

}