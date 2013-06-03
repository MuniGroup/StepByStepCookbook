package cz.muni.muniGroup.cookbook.activities;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import cz.muni.muniGroup.cookbook.entities.Recipe;
import cz.muni.muniGroup.cookbook.exceptions.ConnectivityException;
import cz.muni.muniGroup.cookbook.exceptions.CookbookException;
import cz.muni.muniGroup.cookbook.managers.RecipeManagerImpl;

public class MyLoader extends AsyncTaskLoader<ArrayList<Recipe>>
{
	private static final String TAG = "MyLoader";
	// reference to the Loaders data
	private ArrayList<Recipe> mRecipes = null;
	private int categoryId;
	private int order;
	private int idLoaderu;

	public MyLoader(Context context, Bundle args)
	{
		// Loaders may be used across multiple Activity's (assuming they aren't
		// bound to the LoaderManager), so NEVER hold a reference to the context
		// directly. Doing so will cause you to leak an entire Activity's context.
		// The superclass constructor will store a reference to the Application
		// Context instead, and can be retrieved with a call to getContext().
		super(context);
		order = args.getInt("order");
		categoryId = args.getInt("categoryId");
		idLoaderu = args.getInt("idLoaderu");
		Log.i(TAG + idLoaderu, "new loader: categoryId "+categoryId+" order "+ order + " idloaderu " + args.getInt("idLoaderu"));
	}
	
	
	
    /**
     * @return true if idCategory was changed
     * */
	public boolean setCategoryId(int categoryId) {
		if (this.categoryId != categoryId){
			this.categoryId = categoryId;
			Log.i(TAG + idLoaderu, "categoryId "+categoryId);
			mRecipes = null;
			forceLoad();
			return true;
		}
		return false;
	}



	/****************************************************/
	/** (1) A task that performs the asynchronous load **/
	/****************************************************/


	/**
	 * This method is called on a background thread and generates a List of {@link Level} objects.
	 */
	@Override
	public ArrayList<Recipe> loadInBackground()
	{
		
		Log.i(TAG + idLoaderu, "load in background, categoryId: "+categoryId+", order: "+order);
		RecipeManagerImpl recipeManagerImpl = new RecipeManagerImpl();
		try {
			mRecipes = (ArrayList<Recipe>) recipeManagerImpl.getRecipes(categoryId, order, 0, 10);
			return mRecipes;
		} catch (ConnectivityException e) {
			Log.i(TAG + idLoaderu, "ConnectivityException: "+e.getMessage());
			return new ArrayList<Recipe>();
		} catch (CookbookException e) {
			Log.i(TAG + idLoaderu, "CookbookException: "+e.getMessage());
			return new ArrayList<Recipe>();
		}
	}



	/*******************************************/
	/** (2) Deliver the results to the client **/
	/*******************************************/

	/**
	 * Called when there is new data to deliver to the client. The superclass will deliver it to the registered listener (i.e. the
	 * LoaderManager), which will forward the results to the client through a call to onLoadFinished.
	 */
	@Override
	public void deliverResult(ArrayList<Recipe> recipes)
	{
		if (isReset())
		{
			Log.i(TAG + idLoaderu,"[Loader was reset!]");

			// The Loader has been reset; ignore the result and invalidate the data.
			// This can happen when the Loader is reset while an asynchronous query
			// is working in the background. That is, when the background thread
			// finishes its work and attempts to deliver the results to the client,
			// it will see here that the Loader has been reset and discard any
			// resources associated with the new data as necessary.
			onReleaseResources(mRecipes);
			return;
		}

		// Hold a reference to the old data so it doesn't get garbage collected.
		// The old data may still be in use (i.e. bound to an adapter, etc.), so
		// we must protect it until the new data has been delivered.
		List<Recipe> oldRecipes = mRecipes;
		mRecipes = recipes;

		if (isStarted())
		{
			Log.d(TAG + idLoaderu,"[Delivering results to the LoaderManager]");
			// If the Loader is in a started state, have the superclass deliver the
			// results to the client.
			super.deliverResult(recipes);
		}

		// Invalidate the old data as we don't need it any more.
		if (oldRecipes != null && oldRecipes != recipes)
		{
			Log.i(TAG + idLoaderu,"[Releasing any old data associated with this Loader!]");
			onReleaseResources(oldRecipes);
		}
	}

	/*********************************************************/
	/** (3) Implement the Loaders state-dependent behavior **/
	/*********************************************************/

	@Override
	protected void onStartLoading()
	{
		Log.i(TAG + idLoaderu,"[onStartLoading]");

		if (mRecipes != null)
		{
			// Deliver any previously loaded data immediately.
			Log.i(TAG + idLoaderu,"[Delivering previously loaded data to the client...]");
			deliverResult(mRecipes);
		}

		// Register the observers that will notify the Loader when changes are made.

		if (takeContentChanged())
		{
			// When the observer detects a new installed application, it will call
			// onContentChanged() on the Loader, which will cause the next call to
			// takeContentChanged() to return true. If this is ever the case (or if
			// the current data is null), we force a new load.
			Log.i(TAG + idLoaderu,"[A content change has been detected... so force load!]");
			forceLoad();
		}
		else if (mRecipes == null)
		{
			// If the current data is null... then we should make it non-null! :)
			Log.i(TAG + idLoaderu,"[The current data is data is null... so force load!]");
			forceLoad();
		}
	}

	@Override
	protected void onStopLoading()
	{
		Log.i(TAG,"[onStopLoading]");

		// The Loader has been put in a stopped state, so we should attempt to
		// cancel the current load (if there is one).
		cancelLoad();

		// Note that we leave the observer as is; Loaders in a stopped state
		// should still monitor the data source for changes so that the Loader
		// will know to force a new load if it is ever started again.
	}

	@Override
	protected void onReset()
	{
		Log.i(TAG + idLoaderu,"[onReset]");

		// Ensure the loader is stopped.
		onStopLoading();

		// At this point we can release the resources associated with 'apps'.
		onReleaseResources(mRecipes);
	}

	@Override
	public void onCanceled(ArrayList<Recipe> recipes)
	{
		// Attempt to cancel the current asynchronous load.
		super.onCanceled(recipes);

		Log.i(TAG + idLoaderu,"[onCanceled]");

		// The load has been canceled, so we should release the resources associated with 'mAdverts'.
		onReleaseResources(recipes);
	}

	@Override
	public void forceLoad()
	{
		super.forceLoad();

		Log.i(TAG + idLoaderu,"[forceLoad]");
	}

	/**
	 * Helper method to take care of releasing resources associated with an actively loaded data set.
	 */
	protected void onReleaseResources(List<Recipe> data)
	{
//		if (data != null)
//		{
//			data.clear();
//		}
	}
}
