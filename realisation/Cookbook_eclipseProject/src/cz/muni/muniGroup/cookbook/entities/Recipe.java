package cz.muni.muniGroup.cookbook.entities;

import java.util.Set;

import android.graphics.Bitmap;

public class Recipe implements WithIcon{

	// cesta k iconam: ImageDownloader.URL+ICON_DIR/ICON_FILE+id.jpg
    public static final String ICON_DIR = "recipeIcons";
    public static final String ICON_FILE = "recipeIcon";
    
	private Set<RecipeCategory> category;
	private User author;
	private int id;
	private String name;
	private Bitmap icon;
	private float rating;
	
	public Set<RecipeCategory> getCategory() {
		return category;
	}
	public void setCategory(Set<RecipeCategory> category) {
		this.category = category;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
		
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	public Bitmap getIcon() {
		return icon;
	}
	public void setIcon(Bitmap icon) {
		this.icon = icon;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Recipe other = (Recipe) obj;
		if (id != other.id)
			return false;
		return true;
	}

	
}