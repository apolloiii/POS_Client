package iii.pos.client.adapter;

import iii.pos.client.fragment.GridWineFragment;
import iii.pos.client.model.Items;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;


public class WineViewPagerAdapter extends FragmentStatePagerAdapter {

	private ArrayList<Items> lstItemsWine;
	private int mNumItemsWine = 0;
	private int mNumFragmentsWine = 0;

	public WineViewPagerAdapter(FragmentManager fm, ArrayList<Items> lstItems) {
		super(fm);
		setup(lstItems);
	}

	@Override
	public int getCount() {
		return mNumFragmentsWine;
	}

	@Override
	public Fragment getItem(int position) {
		// Create a new Fragment and supply the fragment number, image
		// position, and image count as arguments.
		// (This was how arguments were handled in the original pager
		// example.)
		Bundle bundle = new Bundle();
		bundle.putInt("numWine", position + 1);
		bundle.putInt("firstImageWine", position * mNumItemsWine);

		// The last page might not have the full number of items.
		int imageCount = mNumItemsWine;
		if (position == (mNumFragmentsWine - 1)) {
			int numTopics = lstItemsWine.size();
			int rem = numTopics % mNumItemsWine;
			if (rem > 0)
				imageCount = rem;
		}
		bundle.putInt("imageCountWine", imageCount);
		bundle.putSerializable("topicListWine", lstItemsWine);

		// Return a new GridFragment object.
		GridWineFragment f = new GridWineFragment();
		f.setArguments(bundle);
		return f;
	}

	void setup(ArrayList<Items> tlist) {
		lstItemsWine = tlist;
		if ((tlist == null)) {
			mNumItemsWine = 4;
			mNumFragmentsWine = 4;
		} else {
			int numTopics = tlist.size();
			int numRows = 2;
			int numCols = 3;
			int numTopicsPerPage = numRows * numCols;
			int numFragments = numTopics / numTopicsPerPage;
			if (numTopics % numTopicsPerPage != 0)
				numFragments++; // Add one if there is a partial page
			mNumFragmentsWine = numFragments;
			mNumItemsWine = numTopicsPerPage;
		}

		Log.d("GridViewPager", "Num fragments, topics per page: "
				+ mNumFragmentsWine + " " + mNumItemsWine);

	}

}