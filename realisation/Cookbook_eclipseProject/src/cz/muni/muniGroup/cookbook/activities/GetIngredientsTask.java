package cz.muni.muniGroup.cookbook.activities;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import cz.muni.muniGroup.cookbook.R;
import cz.muni.muniGroup.cookbook.entities.AssignedIngredient;
import cz.muni.muniGroup.cookbook.entities.RecipeCategory;
import cz.muni.muniGroup.cookbook.entities.WithIcon;
import cz.muni.muniGroup.cookbook.exceptions.ConnectivityException;
import cz.muni.muniGroup.cookbook.exceptions.CookbookException;
import cz.muni.muniGroup.cookbook.managers.ImageDownloader;
import cz.muni.muniGroup.cookbook.managers.RecipeManager;
import cz.muni.muniGroup.cookbook.managers.RecipeManagerImpl;

public class GetIngredientsTask extends AsyncTask<Integer, Void, ArrayList<AssignedIngredient>>
{

	private Context context;
	private static final String TAG = "GetIngredientsTask";
	private final TextView ingr;
	private final ProgressBar progressBar;
	private int recipeId;
	
	/** Constructor for only one try to load
	 * */
	public GetIngredientsTask(Context context, TextView ingr,ProgressBar progresBar, int recipeID)
	{
		this.context = context;
		this.ingr = ingr;
		this.recipeId = recipeID;
		this.progressBar = progresBar;
	}
	


	@Override
	protected ArrayList<AssignedIngredient> doInBackground(Integer... ints)
	{
		RecipeManager recipeManager = new RecipeManagerImpl();
		ArrayList<AssignedIngredient> assignedIngredients;
		try {
			assignedIngredients = (ArrayList<AssignedIngredient>) recipeManager.getIngredients(recipeId);			
		} catch (ConnectivityException e) {
			e.printStackTrace();
			return null;
		} catch (CookbookException e) {
			e.printStackTrace();
			return null;
		}
		return assignedIngredients;

	}


	protected void onPostExecute(ArrayList<AssignedIngredient> result)
	{
		if (result == null){
			Toast.makeText(context, R.string.connectionProblem, Toast.LENGTH_SHORT).show();
			
		} else {
			String ingreString = new String();			
			for (AssignedIngredient assignedIngredient : result) {
				if (!ingreString.equals("")) {
					ingreString = ingreString + '\n';
				}
				ingreString = ingreString + assignedIngredient.getAmount() + ' ' +
						assignedIngredient.getUnit() + ' ' +
						assignedIngredient.getIngredient().getName(); 				
			}
			progressBar.setVisibility(View.GONE);
			ingr.setText(ingreString);
			ingr.setVisibility(View.VISIBLE);
		}
		
	}


}
