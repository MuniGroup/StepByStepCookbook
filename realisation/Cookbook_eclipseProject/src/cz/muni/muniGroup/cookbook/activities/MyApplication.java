package cz.muni.muniGroup.cookbook.activities;

import android.app.Application;
import android.util.Log;

/**
 * Global data to access from all activities
 * @author muniGroup
 *
 */
public class MyApplication extends Application 
{     
	public static final String URL = "http://cookbook.greld.cz/";
	private static final String TAG = "MyApplication";
	
	private int currentRecipeTab;
	private int currentCategoryId;
	private int refreshTag;
	
	
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
	public void incrementRefreshTag() {
		Log.i(TAG, "incrementRefreshTag");
		this.refreshTag++;
	}
	public int getRefreshTag(){
		return refreshTag;
	}
	
	
		
	
}
