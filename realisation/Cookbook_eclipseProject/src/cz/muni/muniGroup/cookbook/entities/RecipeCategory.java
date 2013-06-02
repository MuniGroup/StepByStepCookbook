package cz.muni.muniGroup.cookbook.entities;

public class RecipeCategory {

	private RecipeCategory parent;
	private int id;
	private String name;
	
	public RecipeCategory getParent() {
		return parent;
	}
	public void setParent(RecipeCategory parent) {
		this.parent = parent;
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
		RecipeCategory other = (RecipeCategory) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return getName();
	}
	
	
	
}