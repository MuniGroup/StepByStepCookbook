package cz.muni.muniGroup.cookbook.activities;

import java.util.ArrayList;

import com.actionbarsherlock.view.Window;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import cz.muni.muniGroup.cookbook.R;
import cz.muni.muniGroup.cookbook.entities.Recipe;

public class ListRecipesFragment extends Fragment implements LoaderCallbacks<ArrayList<Recipe>> {
	

	private static final String TAG = "ListRecipesFragment";
    private int categoryId;
    private int currentTab;
    private static final int RECIPES_LOADER_ID=10;
	private MyListAdapter listAdapter;
	private MyApplication app;
	private MyLoader myLoader;
	private GridView gridView;
	private ProgressBar progressBar;
	
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

	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.recipe_grid,container,false);
    	gridView = (GridView) view.findViewById(R.id.gridview);
    	progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
    	
    	listAdapter = new MyListAdapter(getActivity(),R.id.gridview, new ArrayList<Recipe>());
    	gridView.setAdapter(listAdapter);
    	
    	gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Recipe recipe = listAdapter.getItem(position);
				
				Intent intent = new Intent();
		    	intent.putExtra("cz.muni.muniGroup.cookbook.recipeId", recipe.getId());
		    	intent.putExtra("cz.muni.muniGroup.cookbook.recipeName", recipe.getName());
		    	intent.putExtra("cz.muni.muniGroup.cookbook.recipeAuthorName", recipe.getAuthor().getName());
		    	
		        intent.setClass(getActivity(), RecipeDetailActivity.class);
		        startActivity(intent);
				
			}
		});    	
    	return view;
    }

	
	@Override
	public void onResume() {
		categoryId = app.getCurrentCategoryId();
		
		Log.i("ListRecipesFragment", "onResume: category "+categoryId+" tab "+currentTab);
		TextView emptyTextView = new TextView(getActivity());
		emptyTextView.setText("No data in category "+categoryId+" tab "+currentTab);
		gridView.setEmptyView(emptyTextView);
		
		boolean wasChanged = myLoader.setCategoryId(app.getCurrentCategoryId());
		if (wasChanged){
			listAdapter.setData(null);
			showLoadingScreen();
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
		
		

		// naèítání dalšich receptu pri scrollovaní
		// this.getListView().setOnScrollListener(new EndlessScrollListener());
			
        
        //gridView.setBackgroundColor(getResources().getColor(R.color.list_background));

        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        Log.i(TAG, "categoryId "+categoryId+" order "+currentTab);
        Bundle arg1=new Bundle();
		arg1.putInt("categoryId", categoryId);
		arg1.putInt("order", currentTab);
        myLoader=(MyLoader) getLoaderManager().initLoader(RECIPES_LOADER_ID+currentTab, arg1, this);
	}

	private void hideLoadingScreen() {
		progressBar.setVisibility(View.GONE);
		gridView.setVisibility(View.VISIBLE);
	}
	private void showLoadingScreen() {
		gridView.setVisibility(View.GONE);
		progressBar.setVisibility(View.VISIBLE);
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
				Log.i("loaderfragment","item:"+recipe.getName()+","+recipe.getAuthor().getName());
			}
			final ArrayList<Recipe> recipesList=list;
			listAdapter.setData(recipesList);
	        // The list should now be shown.
	        if (isResumed()) {
	        	Log.i("loaderfrag","set list shown");
	        	hideLoadingScreen();
	        } else {
	        	Log.i("loaderfrag","set list shown no animation");
	        	hideLoadingScreen();
	        }
	}

	@Override
	public void onLoaderReset(Loader<ArrayList<Recipe>> arg0) {
		// TODO Auto-generated method stub
		
	}
	
		

}
