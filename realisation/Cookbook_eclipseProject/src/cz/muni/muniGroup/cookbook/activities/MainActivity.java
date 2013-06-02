package cz.muni.muniGroup.cookbook.activities;

import java.util.ArrayList;
import java.util.List;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;

import cz.muni.muniGroup.cookbook.R;
import cz.muni.muniGroup.cookbook.entities.RecipeCategory;
import cz.muni.muniGroup.cookbook.entities.User;
import cz.muni.muniGroup.cookbook.exceptions.ConnectivityException;
import cz.muni.muniGroup.cookbook.exceptions.CookbookException;
import cz.muni.muniGroup.cookbook.managers.UserManager;
import cz.muni.muniGroup.cookbook.managers.UserManagerImpl;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.Toast;


public class MainActivity extends SherlockFragmentActivity {
	
	private SpinnerAdapter mSpinnerAdapter;
	private OnNavigationListener mOnNavigationListener;
	private static List<RecipeCategory> categories = new ArrayList<RecipeCategory>();
	private static List<String> tabNames = new ArrayList<String>();
    private MyApplication app;

	public static final int TAB_ALL = 0;
	public static final int TAB_NEWEST = 1;
	public static final int TAB_BEST = 2;
	public static final int TAB_MOST_DOWNLOADED = 3;
	

	public static final int MY_SECTION_ITEM_ID = 1;
	public static final int MENU_ITEMSID_BASE = 2000;
	public static final int MENU_CATEGORIES_GROUP_ID = 1;
	
	
	private ViewPager mViewPager;
	private MyPagerAdapter mPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
        app = (MyApplication) this.getApplicationContext();


		ActionBar actionBar = getSupportActionBar();
		
		
		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		
		mOnNavigationListener = new OnNavigationListener() {
			  @Override
			  public boolean onNavigationItemSelected(int position, long itemId) {
				app.setCurrentCategoryId(categories.get(position).getId());
				mPagerAdapter.notifyDataSetChanged();
			    return true;
			  }
			};
		
		
	    categories.add(getRecipeCategoryAll(this));
		mSpinnerAdapter = new ArrayAdapter<RecipeCategory>(MainActivity.this, R.layout.main_spinner_item, categories);
	
		actionBar.setListNavigationCallbacks(mSpinnerAdapter, mOnNavigationListener);
		
		new GetCategoriesTask(MainActivity.this, categories).execute();
		
	    actionBar.setDisplayShowTitleEnabled(false);
	    

	    tabNames.add(getResources().getString(R.string.all));
	    tabNames.add(getResources().getString(R.string.newest));
	    tabNames.add(getResources().getString(R.string.best));
	    tabNames.add(getResources().getString(R.string.mostDownloaded));
        
        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mPagerAdapter);
        
        
		
	}
	
	
	public static RecipeCategory getRecipeCategoryAll(Context context) {
		RecipeCategory recipeCategory = new RecipeCategory();
		recipeCategory.setId(0);
		recipeCategory.setName(context.getResources().getString(R.string.all));
		return recipeCategory;
	}


	public class MyPagerAdapter extends FragmentPagerAdapter {
	    public MyPagerAdapter(FragmentManager fm) {
	        super(fm);
	    }

	    @Override
	    public Fragment getItem(int position) {
            return ListRecipesFragment.newInstance(position);
	    }

	    @Override
	    public int getCount() {
	    	return tabNames.size();
	    }

		@Override
		public CharSequence getPageTitle(int position) {
			return tabNames.get(position);
		}

		public int getItemPosition(Object item) {
            return POSITION_NONE;
	    }
		
		
	}
	

	

	/**
	 * Only for test purpose
	 * */
	@SuppressWarnings("unused")
	private void testLogin() {
		final UserManager userManager = new UserManagerImpl();
		final String email = "neco@test.cz";
		final String password = "tajneHeslo";
				
		Runnable loading = new Runnable(){
            public void run() {
				try {
					System.out.println("Výpoètové vlákno loginu nastartováno.");
					User user = userManager.loginUser(email, password);
					System.out.println("Uživatel byl prihlasen. ID: "+user.getId()+" NAME: "+user.getName());
				} catch (ConnectivityException e) {
					System.out.println(e.getMessage());
				} catch (CookbookException e) {
					System.out.println(e.getMessage());
				}
            }
		};
		Thread threadLoading = new Thread(null, loading, "loading");
        threadLoading.start();
	}
	
	/**
	 * Only for test purpose
	 * */
	@SuppressWarnings("unused")
	private void testRegistration() {
		final UserManager userManager = new UserManagerImpl();
		User user = new User();
		user.setEmail("neco@test.cz");
		user.setName("Honza");
		user.setPassword("tajneHeslo");
		
		final User userFinal = user;
		
		Runnable loading = new Runnable(){
            public void run() {
				try {
					System.out.println("Výpoètové vlákno nastartováno.");
					userManager.create(userFinal);
					System.out.println("Uživatel byl vytvoøen. ID: "+userFinal.getId());
				} catch (ConnectivityException e) {
					System.out.println(e.getMessage());
				} catch (CookbookException e) {
					System.out.println(e.getMessage());
				}
            }
		};
		Thread threadLoading = new Thread(null, loading, "loading");
        threadLoading.start();
	}


	
	
	

}
