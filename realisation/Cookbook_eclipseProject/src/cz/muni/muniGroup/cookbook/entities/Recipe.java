package cz.muni.muniGroup.cookbook.entities;

import java.util.Set;

public class Recipe {

	private Set<RecipeCategory> category;
	private User author;
	private int id;
	private String name;
	
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