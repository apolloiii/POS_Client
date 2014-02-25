package iii.pos.client.fragment.base;

import iii.pos.client.activity.base.FragmentActivityBase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentBase extends Fragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	public void switchFragment(Fragment content, String tag){
		((FragmentActivityBase)getActivity()).switchFragment(content, tag);
	}
	
	protected void backMethod() {
		if (getActivity() != null) {
			FragmentManager fm = getActivity().getSupportFragmentManager();
			if (fm.getBackStackEntryCount() > 1) {
				fm.popBackStack();
			} else {
				getActivity().finish();
			}
		}
	}
	
}
