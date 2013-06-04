package cz.muni.muniGroup.cookbook.activities;

import cz.muni.muniGroup.cookbook.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RecipeDetailMoreFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.recipe_detail_more_fragment, container, false);
    }

	public static RecipeDetailMoreFragment newInstance(int recipeId) {
		RecipeDetailMoreFragment f = new RecipeDetailMoreFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("recipeId", recipeId);
        f.setArguments(args);

        return f;
    }
	
	
	private int recipeId;
	
    @Override
    public void onStart() {
    	super.onStart();
    	
    	Bundle args = getArguments();
    	
    	recipeId = args.getInt("recipeId");
    	
    }
}