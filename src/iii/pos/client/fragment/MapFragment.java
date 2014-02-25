/**
 * 
 */
package iii.pos.client.fragment;

import iii.pos.client.R;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

// --------------this class constant google map -----------------//
public class MapFragment extends Fragment {

	// ----------------Fields -----------------------------------//
	private MapView map = null;
	private MyLocationOverlay me = null;

	// ---------------initialize method---------------------------//
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = null;
		if (map == null) {
			v = new FrameLayout(getActivity());
		}
		return v;
	}

	// ---------------initialize method---------------------------//
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		try {

			if (map == null) {
				map = new MapView(getActivity(),
						"0Q4bWBX5SDRCYSNYYxxBSnQY2wgwRTx8XjHkbrw");
				map.setClickable(true);
				map.getController().setZoom(17);
				map.setBuiltInZoomControls(true);

				Drawable marker = getResources().getDrawable(R.drawable.cakho);

				marker.setBounds(0, 0, marker.getIntrinsicWidth(),
						marker.getIntrinsicHeight());

				me = new MyLocationOverlay(getActivity(), map);
				map.getOverlays().add(me);

				((ViewGroup) getView()).addView(map);
			}
		} catch (Exception e) {

		}
	}
}