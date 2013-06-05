package cz.muni.muniGroup.cookbook.activities;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import cz.muni.muniGroup.cookbook.R;
import android.os.Bundle;

public class RecipeDetailActivity extends SherlockFragmentActivity {
	
	private int recipeId;
	private String recipeName;
	private String recipeAuthorName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recipe_detail);
		
		if (savedInstanceState == null) {

			Bundle extras = getIntent().getExtras();
			if (extras == null) {
				return;
			}
			
			getSupportActionBar().setTitle(R.string.recipe);
			
			recipeId = extras.getInt("cz.muni.muniGroup.cookbook.recipeId");
			recipeName = extras.getString("cz.muni.muniGroup.cookbook.recipeName");
			recipeAuthorName = extras.getString("cz.muni.muniGroup.cookbook.recipeAuthorName");
			
			RecipeDetailHeadFragment fHead = RecipeDetailHeadFragment.newInstance(recipeId, recipeName, recipeAuthorName);
            
            			
			RecipeDetailMoreFragment fMore = RecipeDetailMoreFragment.newInstance(recipeId);
            getSupportFragmentManager().beginTransaction().add(R.id.linearLayout, fHead)
            											  .add(R.id.linearLayout, fMore)
            											  .commit();
        }
		
				
	}
}
