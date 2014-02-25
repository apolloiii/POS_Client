/*package iii.pos.client.fragment;

import iii.pos.client.R;
import iii.pos.client.data.MyShareprefer;
import iii.pos.client.server.ConfigurationServer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.google.android.maps.MapView;

//--------------this class use to display the map for restaurant---------//
public class FloorFragment1 extends Fragment {

	private WebView webView;
	private MapView mapview;
	private String URLWebview = "";
	private MyShareprefer myShare;

	// ---------------------initialize method---------------------------//
	private String WsFloor = "";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		myShare = new MyShareprefer(getActivity(),
				MyShareprefer.GET_INFOUSER_ITEM);
		int user = myShare.getUser_id();
		user = 1;
		WsFloor = ConfigurationServer.getURLServer()+"map/main/floor1.php?android_id=";
		View mapFloorLayout = inflater.inflate(R.layout.floor1, container,
				false);
		if (mapFloorLayout != null) {
			webView = (WebView) mapFloorLayout.findViewById(R.id.webView1);
			webView.loadUrl(WsFloor + user);
			webView.getSettings().setJavaScriptEnabled(true);
		}
		return mapFloorLayout;
	}

}*/