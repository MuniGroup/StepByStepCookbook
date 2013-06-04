package cz.muni.muniGroup.cookbook.activities;

import cz.muni.muniGroup.cookbook.R;
import cz.muni.muniGroup.cookbook.entities.Recipe;
import cz.muni.muniGroup.cookbook.managers.ImageDownloader;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class RecipeDetailHeadFragment extends Fragment {
	
	public static RecipeDetailHeadFragment newInstance(int recipeId, String recipeName, String recipeAuthorName) {
		RecipeDetailHeadFragment f = new RecipeDetailHeadFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("recipeId", recipeId);
        args.putString("recipeName", recipeName);
        args.putString("recipeAuthorName", recipeAuthorName);
        f.setArguments(args);

        return f;
    }

	private String recipeAuthorName;
	private Recipe recipe;

	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    	
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.recipe_detail_head_fragment, container, false);
    }
    
    @Override
    public void onStart() {
    	super.onStart();
    	
    	Bundle args = getArguments();
    	
    	recipe = new Recipe();
    	recipe.setId(args.getInt("recipeId"));
    	recipe.setName(args.getString("recipeName"));
    	recipeAuthorName = args.getString("recipeAuthorName");
    	
    	if (recipe.getName() != null){
            TextView title = (TextView) getView().findViewById(R.id.title);
    		title.setText(recipe.getName());
    	}
    	if (recipeAuthorName != null){
    		TextView author = (TextView) getView().findViewById(R.id.author);
    		author.setText(recipeAuthorName);
    	}
    	
    	ImageView icon = (ImageView) getView().findViewById(R.id.icon);
    	
    	if (recipe.getIcon() == null){
	    	String iconUrl = Recipe.ICON_DIR + "/" + Recipe.ICON_FILE + recipe.getId() + ".jpg";
	    	new GetImageTask(getActivity(), icon, iconUrl, recipe).execute();
    	} else {
    		icon.setImageBitmap(recipe.getIcon());
    	}

		
    }
}
