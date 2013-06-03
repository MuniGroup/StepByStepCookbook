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
    private static final int RECIPES_LOADER_ID=10;
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
		
		
		// Start out with a progress indicator.
        setListShown(false);
        
        listAdapter = new MyListAdapter(getActivity(),android.R.id.list, new ArrayList<Recipe>());
        setListAdapter(listAdapter);
		
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
		app = (MyApplication) getActivity().getApplicationContext();
		categoryId = app.getCurrentCategoryId();
		currentTab = getArguments().getInt("tab");
		

        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        Log.i(TAG, "categoryId "+categoryId+" order "+currentTab);
        Bundle arg1=new Bundle();
		arg1.putInt("categoryId", categoryId);
		arg1.putInt("order", currentTab);
        myLoader=(MyLoader) getLoaderManager().initLoader(RECIPES_LOADER_ID+currentTab, arg1, this);
	}

	@Override
	public Loader<ArrayList<Recipe>> onCreateLoader(int arg0, Bundle arg1) {
		Log.i("loader","oncreate loader "+arg0);
		Bundle arg=new Bundle();
		arg.putInt("categoryId", arg1.getInt("categoryId"));
		arg.putInt("order", arg1.getInt("order"));
		arg.putInt("idLoaderu", arg0);
		return new MyLoader(getActivity(), arg);
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
