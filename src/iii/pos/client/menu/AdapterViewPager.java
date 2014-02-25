package iii.pos.client.menu;

import iii.pos.client.fragment.GridFragment;
import iii.pos.client.model.Items;

import java.util.ArrayList;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

public class AdapterViewPager extends FragmentStatePagerAdapter {

	private ArrayList<Items> lstItems;
	private int mNumItems = 0;
	private int mNumFragments = 0;

	public AdapterViewPager(FragmentManager fm, ArrayList<Items> lstItems) {
		super(fm);
		setup(lstItems);
	}
	@Override
	public int getCount() {
		return mNumFragments;
	}
	@Override
	public Fragment getItem(int position) {
		// Create a new Fragment and supply the fragment number, image
		// position, and image count as arguments.
		// (This was how arguments were handled in the original pager
		// example.)
		Bundle bundle = new Bundle();
		bundle.putInt("num", position + 1);
		bundle.putInt("firstImage", position * mNumItems);

		// The last page might not have the full number of items.
		int imageCount = mNumItems;
		if (position == (mNumFragments - 1)) {
			int numTopics = lstItems.size();
			int rem = numTopics % mNumItems;
			if (rem > 0)
				imageCount = rem;
		}
		bundle.putInt("imageCount", imageCount);
		bundle.putSerializable("topicList", lstItems);

		// Return a new GridFragment object.
		GridFragment f = new GridFragment();
		f.setArguments(bundle);
		return f;
	}

	void setup(ArrayList<Items> tlist) {
		lstItems = tlist;
		if ((tlist == null)) {
			mNumItems = 4;
			mNumFragments = 4;
		} else {
			int numTopics = tlist.size();
			int numRows = 2;
			int numCols = 3;
			int numTopicsPerPage = numRows * numCols;
			int numFragments = numTopics / numTopicsPerPage;
			if (numTopics % numTopicsPerPage != 0)
				numFragments++; // Add one if there is a partial page
			mNumFragments = numFragments;
			mNumItems = numTopicsPerPage;
		}

		Log.d("GridViewPager", "Num fragments, topics per page: "
				+ mNumFragments + " " + mNumItems);

	}  

}  
