package cz.muni.muniGroup.cookbook.activities;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;
import cz.muni.muniGroup.cookbook.R;
import cz.muni.muniGroup.cookbook.entities.WithIcon;
import cz.muni.muniGroup.cookbook.managers.ImageDownloader;

public class GetImageTask extends AsyncTask<Integer, Void, Bitmap>
{

	private Context context;
	private static final String TAG = "GetImageTask";
	private final ImageView imageView;
	private final String path;
	private WithIcon withIcon;
	private int triesNum = 1;
	
	/** Constructor for only one try to load
	 * */
	public GetImageTask(Context context, ImageView imageView, String path, WithIcon withIcon)
	{
		this.context = context;
		this.imageView = imageView;
		this.path = path;
		this.withIcon = withIcon;
		this.triesNum  = 1;
	}
	
	/** Constructor with number of tries specification
	 * */
	public GetImageTask(Context context, ImageView imageView, String path, WithIcon withIcon, int triesNum)
	{
		this.context = context;
		this.imageView = imageView;
		this.path = path;
		this.withIcon = withIcon;
		this.triesNum  = triesNum;
	}


	@Override
	protected Bitmap doInBackground(Integer... ints)
	{
		if (path == null){
			throw new NullPointerException("path is null");
		}
        String cardPath = null;
        Bitmap bitmap = null;
        
        boolean haveCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
		boolean isAlreadyOnCard = false;
		String imageName = path.substring(path.lastIndexOf("/")+1);
		String dir = path.substring(0, path.lastIndexOf("/")-1);
		String cardDir = "";
		// je pametova karta k dispozici?
        if (haveCard)
	    {
			// už je na kartì? 
        	cardDir = context.getExternalFilesDir(null) + "/" + dir;
			cardPath = new File(cardDir, imageName).getAbsolutePath();
			Log.i(TAG, "pametova karta je k dispozici. Cesta: " + cardPath);
        	bitmap = BitmapFactory.decodeFile(cardPath);
        	if (bitmap != null){
        		isAlreadyOnCard = true;
        		Log.i(TAG, "Image was loaded from sdCard.");
        	}
	    }
		if (bitmap == null){
			Log.i(TAG, "stahuju iconu ze serveru: "+ ImageDownloader.URL + path);
        	bitmap = ImageDownloader.DownloadImage(ImageDownloader.URL + path);
        	if (bitmap == null){
        		Log.i(TAG, "bitmapu se nepodarilo stahnout ze serveru");
        	}
		}
		if (bitmap != null && haveCard && !isAlreadyOnCard){
        	ImageDownloader.saveImage(bitmap, cardDir, imageName);
		}
		return bitmap;

	}


	protected void onPostExecute(Bitmap result)
	{
		if (result == null){
			// Toast.makeText(context, R.string.connectionProblem, Toast.LENGTH_SHORT).show();
			Log.i(TAG, "Obrázek se nepodaøilo naèíst. Zbiva pokusu: "+ triesNum);
			// zkusime znovu pokud jsme nevycerpali pocet pokusu
			if (triesNum > 1)
				new GetImageTask(context, imageView, path, withIcon, triesNum-1).execute();
		} else {
			withIcon.setIcon(result);
			imageView.setImageBitmap(result);
		}
		
	}


}
