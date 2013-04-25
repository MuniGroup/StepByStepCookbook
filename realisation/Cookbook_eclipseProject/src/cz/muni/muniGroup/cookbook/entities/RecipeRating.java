package cz.muni.muniGroup.cookbook.entities;

public class RecipeRating {

	private int recipeId;
	private int userId;
	private int recipe_quality;
	private int result_quality;
	private int difficulty;
	private String in_words;
	
	
	public int getRecipe_quality() {
		return recipe_quality;
	}
	public void setRecipe_quality(int recipe_quality) {
		this.recipe_quality = recipe_quality;
	}
	public int getResult_quality() {
		return result_quality;
	}
	public void setResult_quality(int result_quality) {
		this.result_quality = result_quality;
	}
	public int getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	public String getIn_words() {
		return in_words;
	}
	public void setIn_words(String in_words) {
		this.in_words = in_words;
	}
	public int getRecipeId() {
		return recipeId;
	}
	public void setRecipeId(int recipeId) {
		this.recipeId = recipeId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + recipeId;
		result = prime * result + userId;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RecipeRating other = (RecipeRating) obj;
		if (recipeId != other.recipeId)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	
}