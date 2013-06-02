package cz.muni.muniGroup.cookbook.activities;

import android.app.Application;
import android.os.Handler;

/**
 * Global data to access from all activities
 * @author muniGroup
 *
 */
public class MyApplication extends Application 
{     
	
	private int currentRecipeTab;
	private int currentCategoryId;
	
	
	public int getCurrentRecipeTab() {
		return currentRecipeTab;
	}
	public void setCurrentRecipeTab(int currentRecipeTab) {
		this.currentRecipeTab = currentRecipeTab;
	}
	public int getCurrentCategoryId() {
		return currentCategoryId;
	}
	public void setCurrentCategoryId(int currentCategoryId) {
		this.currentCategoryId = currentCategoryId;
	}
	
	
		
	
}
