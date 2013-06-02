package cz.muni.muniGroup.cookbook.activities;

import android.content.Context;
import android.view.View;

import com.actionbarsherlock.view.ActionProvider;
import com.actionbarsherlock.view.SubMenu;

public class CategoriesActionProvider extends ActionProvider {

	private Context mContext;
	
	public CategoriesActionProvider(Context context) {
		super(context);
		mContext = context;
	}

	@Override
	public boolean hasSubMenu() {
		return true;
	}

	@Override
	public void onPrepareSubMenu(SubMenu subMenu) {
		subMenu.add("Neco1");
		subMenu.add("Neco2");
	}

	@Override
	public View onCreateActionView() {
		return null;
	}

}
