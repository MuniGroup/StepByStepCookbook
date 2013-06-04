package cz.muni.muniGroup.cookbook.activities;



import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RatingBar;
import android.widget.TextView;
import cz.muni.muniGroup.cookbook.R;
import cz.muni.muniGroup.cookbook.entities.Recipe;

public class MyListAdapter extends ArrayAdapter<Recipe>
{

	private static final String TAG = "MyListAdapter";
	private ArrayList<Recipe> array;
	private LayoutInflater inflater;	
	private Context context;
	private Activity activity;
    private Runnable runnableDownloadImages;
    private int [] alreadyDrawn;

	public MyListAdapter(Activity a,int textViewResourceId,ArrayList<Recipe> recipes){
		super(a,textViewResourceId,recipes);
		this.array=recipes;
		this.activity=a;	
		this.context=activity.getApplicationContext();
		inflater=LayoutInflater.from(context);
		alreadyDrawn = new int [recipes.size()];
	}
	

	public void setData(ArrayList<Recipe> items){		
		
		if(array==null){
			array=new ArrayList<Recipe>();
		}
		clear();	
		if(items!=null){
			for(Recipe item:items){
				if(item==null){
					Log.i("adapter","item is null");
				}
				add(item);			
			}
			alreadyDrawn = new int [items.size()];
			notifyDataSetChanged();
		}
		else{
			Log.i("adapter","items is null");
		}
		
	}
	
	@Override
	public int getCount()
	{
		if (array == null)
			return 0;
		return array.size();
	}

	@Override
	public Recipe getItem(int position)
	{
		if(array==null || array.isEmpty())
			return null;
		
		return array.get(position);
		
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public int getItemViewType(int position)
	{
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder;
		
		if (convertView == null)
		{
			convertView=inflater.inflate(R.layout.recipe_row, null);

			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.text1);
			holder.author = (TextView) convertView.findViewById(R.id.text2);
			holder.rating = (RatingBar) convertView.findViewById(R.id.ratingBar1);
			holder.icon = (ImageView) convertView.findViewById(R.id.icon);
			
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
				
		final Recipe recipe = getItem(position);
		holder.name.setText(recipe.getName());
		holder.author.setText(recipe.getAuthor().getName());
		holder.rating.setRating(recipe.getRating());
		
		setImage(holder, recipe, position);
		
		return convertView;
	}
	

	private void setImage(ViewHolder holder, final Recipe recipe, int position) {
		holder.icon.setImageResource(R.drawable.recipe_icon_default);
    	holder.icon.setScaleType(ScaleType.FIT_CENTER);
    	if (recipe.getIcon() != null) 
    		Log.i(TAG, "recipe.getIcon() != null");
        if (recipe.getIcon() == null && alreadyDrawn[position] != 1) 
        {
        	alreadyDrawn[position] = 1;
        	String iconUrl = Recipe.ICON_DIR + "/" + Recipe.ICON_FILE + recipe.getId() + ".jpg";
    		Log.d(TAG, "start stazeni icony z "+iconUrl);
        	new GetImageTask(context, holder.icon, iconUrl, recipe).execute();
        }
        if (recipe.getIcon() != null){
        	holder.icon.setImageBitmap(recipe.getIcon());
        }
		
	}


	@Override
	public int getViewTypeCount()
	{
		return 1;
	}

	static class ViewHolder
	{
		TextView name;
		TextView author;
		ImageView icon;	
		RatingBar rating;
	}
	
}

