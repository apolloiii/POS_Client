package iii.pos.client.wsclass;

import iii.pos.client.server.ConfigurationWS;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
/**
 * Lớp xử lý phần Gửi lên location của Guest kiểm tra xem khách có đang ở trong nhà hàng hay ko
 * Nếu ở trong nhà hàng => cho phép gọi món ăn
 * Nếu ở ngoài => ko cho phép gọi món
 * 
 * Chưa xong
 * @author GIACNGO
 *
 */
public class WSCheckUserLocation extends AsyncTask<String, Void, Boolean> {

	private ProgressDialog dialog;
	private ConfigurationWS mWS;

	public WSCheckUserLocation(Context mcontext) {
		super();
		dialog = new ProgressDialog(mcontext);
		mWS = new ConfigurationWS(mcontext);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog.setMessage("Loading...");
		dialog.setCancelable(false);
		dialog.show();
	}

	@Override
	protected Boolean doInBackground(String... params) {
		boolean checkLocation = false;
		try {
			String URL = params[0];
			JSONObject json = new JSONObject();
			JSONArray arrITable = mWS.connectWSPut_Get_Data(URL, json, "itable");
			for (int i = 0; i < arrITable.length(); i++) {
				JSONObject results = arrITable.getJSONObject(i);
				Log.i("Log : ", "GET ALL TABLE : " + results);
				//itable.setItable_id(results.getInt("itable_id"));
			}
		} catch (Exception e) {
		}
		return checkLocation;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		if (dialog.isShowing()) {
			dialog.dismiss();
		}
	}
}
