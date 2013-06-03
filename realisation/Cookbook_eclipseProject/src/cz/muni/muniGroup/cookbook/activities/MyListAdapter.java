package cz.muni.muniGroup.cookbook.activities;



import java.util.ArrayList;

import cz.muni.muniGroup.cookbook.R;
import cz.muni.muniGroup.cookbook.entities.Recipe;
import cz.muni.muniGroup.cookbook.managers.DBWorker;
import cz.muni.muniGroup.cookbook.managers.ImageDownloader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

public class MyListAdapter extends ArrayAdapter<Recipe>
{

	private static final String TAG = "MyListAdapter";
	private ArrayList<Recipe> array;
	private LayoutInflater inflater;	
	private Context context;
	private Activity activity;
    private static final String DIR = "recipeIcons";
    private static final String FILE = "recipeIcon";
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
		
		setImage(holder, recipe, position);
		
		return convertView;
	}
	

	private void setImage(ViewHolder holder, final Recipe recipe, int position) {
		holder.icon.setImageResource(R.drawable.recipe_icon_default);
        
        String fname = null;
        String path = null;
        if ((recipe.getIcon() == null) && 
        		(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)))
        {
        	path = context.getExternalFilesDir(null).getAbsolutePath() + "/" + DIR;
        	fname = FILE + recipe.getId() + ".jpg";
        	System.err.println("pametova karta je k dispozici. Cesta: " + path + "/" + fname);
        	Bitmap bm = BitmapFactory.decodeFile(path + "/" + fname);
        	recipe.setIcon(bm);
        	if (bm!=null)
            	System.err.println("naloudováno z karty");
        		
        }
        if (recipe.getIcon() == null) {
        	if (alreadyDrawn[position] != 1) {
            	alreadyDrawn[position] = 1;
            	System.err.println("stahuju.");
            	final String fnameFinal = fname;
            	final String pathFinal = path;
            	runnableDownloadImages = new Runnable(){
                    public void run() {
                    	Bitmap bm = ImageDownloader.DownloadImage(ImageDownloader.URL + DIR + "/" + FILE + recipe.getId() + ".jpg");
                    	Log.d(TAG, "loaduju iconu z "+ImageDownloader.URL + DIR + "/" + FILE + recipe.getId() + ".jpg");
                    	if (bm != null){
                    		Log.d(TAG, "icona naloadovana");
                    		recipe.setIcon(bm);
                        	ImageDownloader.saveImage(bm, pathFinal, fnameFinal);
                        	activity.runOnUiThread(updateAdapter);
                    	}
                    }
                };
                Thread thread =  new Thread(null, runnableDownloadImages, "ImageOnBackground");
                thread.start();
        	} else System.err.println("uz se stahuje.");
        }
        else
        {
        	System.err.println("jiz ulozeno");
        	holder.icon.setScaleType(ScaleType.FIT_CENTER);
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
	
	private Runnable updateAdapter = new Runnable() {
        public void run() {
            notifyDataSetChanged();
        }
    };
}

