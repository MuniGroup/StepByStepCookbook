package cz.muni.muniGroup.cookbook.activities;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;


public class RecipeTabListener<T extends Fragment> implements TabListener {
    private Fragment mFragment;
    private final Activity mActivity;
    private final Class<T> mClass;
    private final int tabTag;
    private MyApplication app;

    /** Constructor used each time a new tab is created.
      * @param activity  The host Activity, used to instantiate the fragment
      * @param tag  The identifier tag of tab
      * @param clz  The fragment's Class, used to instantiate the fragment
      */
    public RecipeTabListener(Activity activity, int tag, Class<T> clz) {
        mActivity = activity;
        mClass = clz;
        tabTag = tag;
        app = (MyApplication) mActivity.getApplicationContext();
    }

    /* The following are each of the ActionBar.TabListener callbacks */

    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        // Check if the fragment is already initialized
        if (mFragment == null) {
            // If not, instantiate and add it to the activity
            mFragment = Fragment.instantiate(mActivity, mClass.getName());
            ft.add(android.R.id.content, mFragment, null);
        } else {
            // If it exists, simply attach it in order to show it
            ft.attach(mFragment);
        }
        // prepneme globalni informaci o aktualne zobrazovanem tabu
        app.setCurrentRecipeTab(tabTag);
    }

    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
        if (mFragment != null) {
            // Detach the fragment, because another one is being attached
            ft.detach(mFragment);
        }
    }

    public void onTabReselected(Tab tab, FragmentTransaction ft) {
        // User selected the already selected tab. Usually do nothing.
    }
}
