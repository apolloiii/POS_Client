package iii.pos.client.library;

import java.util.ArrayList;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

public class GetCurrentLocationGPS {
	private Context context;

	public GetCurrentLocationGPS(Context mContext) {
		this.context = mContext;
	}

	// Lấy Location
	public ArrayList<Double> getLocation() {
		ArrayList<Double> lstLatLon = new ArrayList<Double>();
		LocationManager locationManager = (LocationManager) context .getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		String bestProvider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(bestProvider);
		Double lat, lon;
		try {
			lat = location.getLatitude();
			lon = location.getLongitude();
			lstLatLon.add(lat);
			lstLatLon.add(lon);
			return lstLatLon;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return null;
		}
	}

}
