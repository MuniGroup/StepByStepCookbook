package cz.muni.muniGroup.cookbook.activities;



import java.util.ArrayList;

import cz.muni.muniGroup.cookbook.R;
import cz.muni.muniGroup.cookbook.entities.Recipe;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyListAdapter extends ArrayAdapter<Recipe>
{

	private ArrayList<Recipe> array;
	private LayoutInflater inflater;	
	private Context context;
	private Activity activity;

	public MyListAdapter(Activity a,int textViewResourceId,ArrayList<Recipe> recipes){
		super(a,textViewResourceId,recipes);
		this.array=recipes;
		this.activity=a;	
		this.context=activity.getApplicationContext();
		inflater=LayoutInflater.from(context);
		
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
			
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
				
		Recipe recipe=new Recipe();
		recipe=getItem(position);
		holder.name.setText(recipe.getName());
		holder.author.setText(recipe.getAuthor().getName());
		
		return convertView;
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
	}
}

