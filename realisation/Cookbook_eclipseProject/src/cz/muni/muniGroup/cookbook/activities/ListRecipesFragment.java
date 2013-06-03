package cz.muni.muniGroup.cookbook.activities;

import java.util.ArrayList;

import com.actionbarsherlock.view.Window;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.widget.TextView;
import cz.muni.muniGroup.cookbook.entities.Recipe;

public class ListRecipesFragment extends ListFragment implements LoaderCallbacks<ArrayList<Recipe>> {
	

	private static final String TAG = "ListRecipesFragment";
    private int categoryId;
    private int currentTab;
    private static final int RECIPES_LOADER_ID=0;
	private MyListAdapter listAdapter;
	private MyApplication app;
	private MyLoader myLoader;
	
	/**
     * Create a new instance of CountingFragment, providing "num"
     * as an argument.
     * 
     * @return new instance of ListRecipesFragment with tab argument
     */
    static ListRecipesFragment newInstance(int num) {
    	ListRecipesFragment f = new ListRecipesFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("tab", num);
        f.setArguments(args);

        return f;
    }

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		app = (MyApplication) getActivity().getApplicationContext();
		categoryId = app.getCurrentCategoryId();
		
		// Start out with a progress indicator.
        setListShown(false);
        
        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        myLoader=(MyLoader) getLoaderManager().initLoader(RECIPES_LOADER_ID, null, this);
        
        listAdapter = new MyListAdapter(getActivity(),android.R.id.list, new ArrayList<Recipe>());
        setListAdapter(listAdapter);
		
	}
	
	@Override
	public void onStart() {
		
		
		super.onStart();
	}
	
	

	@Override
	public void onResume() {
		categoryId = app.getCurrentCategoryId();
		
		Log.i("ListRecipesFragment", "onResume: category "+categoryId+" tab "+currentTab);
		setEmptyText("No data in category "+categoryId+" tab "+currentTab);
		
		boolean wasChanged = myLoader.setCategoryId(app.getCurrentCategoryId());
		if (wasChanged){
			listAdapter.setData(null);
			setListShown(false);
			Log.i(TAG, "wasChanged");
		}
		Log.i(TAG, "setCategoryId "+app.getCurrentCategoryId());
		super.onResume();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		currentTab = getArguments().getInt("tab");
	}

	@Override
	public Loader<ArrayList<Recipe>> onCreateLoader(int arg0, Bundle arg1) {
		Log.i("loader","oncreate loader");
		arg1=new Bundle();
		arg1.putInt("categoryId", categoryId);
		return new MyLoader(getActivity(), arg1);
	}

	@Override
	public void onLoadFinished(Loader<ArrayList<Recipe>> loader, ArrayList<Recipe> list){
			for(Recipe recipe:list){
				Log.i("loaderfragment","item:"+recipe.getName()+","+recipe.getAuthor());
			}
			final ArrayList<Recipe> recipesList=list;
			listAdapter.setData(recipesList);
	        // The list should now be shown.
	        if (isResumed()) {
	        	Log.i("loaderfrag","set list shown");
	            setListShown(true);
	        } else {
	        	Log.i("loaderfrag","set list shown no animation");
	            setListShownNoAnimation(true);
	        }
	}

	@Override
	public void onLoaderReset(Loader<ArrayList<Recipe>> arg0) {
		// TODO Auto-generated method stub
		
	}
	
		

}
