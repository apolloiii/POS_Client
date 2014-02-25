package iii.pos.client.activity.base;

import iii.pos.client.R;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.BackStackEntry;
import android.support.v4.app.FragmentTransaction;

public class FragmentActivityBase extends FragmentActivity{
	
	public void switchFragment(Fragment content, String tag){
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		int index = fm.getBackStackEntryCount() > 0 ? fm.getBackStackEntryCount() - 1 : 0;
		Fragment fragment = null;
		try {
			if (index != 0) {
				BackStackEntry entry = fm.getBackStackEntryAt(fm.getBackStackEntryCount() - 1);
				fragment = fm.findFragmentByTag(entry.getName());
			} else {
				fragment = (Fragment) fm.findFragmentById(R.id.body_Pos_Container);
			}
			if(fragment!=null) ft.hide(fragment);
		} catch (Exception e) {
		}
		ft.replace(R.id.body_Pos_Container, content, tag).addToBackStack(tag).commit();
	}
}
