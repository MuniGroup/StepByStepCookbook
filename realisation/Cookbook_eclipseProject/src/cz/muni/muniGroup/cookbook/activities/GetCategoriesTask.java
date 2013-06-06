package cz.muni.muniGroup.cookbook.activities;

import java.util.ArrayList;
import java.util.List;

import com.actionbarsherlock.view.Menu;

import cz.muni.muniGroup.cookbook.R;
import cz.muni.muniGroup.cookbook.entities.RecipeCategory;
import cz.muni.muniGroup.cookbook.exceptions.ConnectivityException;
import cz.muni.muniGroup.cookbook.exceptions.CookbookException;
import cz.muni.muniGroup.cookbook.managers.RecipeManager;
import cz.muni.muniGroup.cookbook.managers.RecipeManagerImpl;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class GetCategoriesTask extends AsyncTask<Integer, Void, ArrayList<RecipeCategory>>
{

	private Context context;
	private static final String TAG = "GetCategoriesTask";
	private final List<RecipeCategory> categories;
	

	public GetCategoriesTask(Context context, List<RecipeCategory> categories)
	{
		this.context = context;
		this.categories = categories;
	}


	@Override
	protected ArrayList<RecipeCategory> doInBackground(Integer... integers)
	{
		RecipeManager recipeManager = new RecipeManagerImpl();
		ArrayList<RecipeCategory> recipeCategories;
		try {
			recipeCategories = (ArrayList<RecipeCategory>) recipeManager.getRecipeCategories();
		} catch (ConnectivityException e) {
			e.printStackTrace();
			return null;
		} catch (CookbookException e) {
			e.printStackTrace();
			return null;
		}
		return recipeCategories;
	}


	protected void onPostExecute(ArrayList<RecipeCategory> result)
	{
		if (result == null){
			//Toast.makeText(context, R.string.connectionProblem, Toast.LENGTH_SHORT).show();
		} else {
			categories.clear();
			categories.add(MainActivity.getRecipeCategoryAll(context));
			categories.addAll(result);
		}
		
	}


}
