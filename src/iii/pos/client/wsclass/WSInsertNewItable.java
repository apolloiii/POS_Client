package iii.pos.client.wsclass;

import iii.pos.client.activity.MainPosActivity;
import iii.pos.client.model.Itable;
import iii.pos.client.server.ConfigurationServer;
import iii.pos.client.server.ConfigurationWS;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


public class WSInsertNewItable extends AsyncTask<Itable, Void, Void> {
	private ConfigurationWS mWS;
	private ProgressDialog mProgress;
	
	public WSInsertNewItable( Context mContext ) {
		mWS = new ConfigurationWS(mContext);
		mProgress = new ProgressDialog(mContext);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		mProgress.setMessage("Inserting...");
		mProgress.setCancelable(false);
		mProgress.show();
	}

	@Override
	protected synchronized Void doInBackground(Itable... params) {
		try {
			Itable itable = (Itable) params[0];
			String URLAddnewItem = ConfigurationServer.getURLServer() + "wsinsertnewtable.php";
			
			JSONObject json = new JSONObject();
			json.put("floor", itable.getFlag()); // Mượn tạm trường flag để lưu biến Tầng
			json.put("code_table", itable.getCode_table());
			json.put("pos_x", itable.getPos_x());
			json.put("pos_y", itable.getPos_y());
			json.put("location_x", itable.getLocation_X());
			json.put("location_y", itable.getLocation_Y());
			json.put("user_id", MainPosActivity.user.getUser_id());
			json.put("company_code", MainPosActivity.user.getCompanyCode());
			
			Log.i("JSON NEW_ITABLE: code_table: ", itable.getCode_table());
			Log.i("JSON NEW_ITABLE: locationX", itable.getLocation_X()+"");
			Log.i("JSON NEW_ITABLE: locationY", itable.getLocation_Y()+"");
			Log.i("JSON NEW_ITABLE: GPS_X", itable.getPos_x()+"");
			Log.i("JSON NEW_ITABLE: GPS_Y", itable.getPos_y()+"");
			Log.i("JSON NEW_ITABLE: floor", itable.getFlag()+"");
			
			JSONArray arrITable = mWS.connectWSPut_Get_Data(URLAddnewItem, json, "posts");
			JSONObject results = arrITable.getJSONObject(0);
			if (!results.getString("result").equals("false")) {
				String msg = results.getString("result");
				Log.i("JSON RESUL NEW_ITABLE:", msg+"");
			}
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
			if( mProgress.isShowing() )
				mProgress.dismiss();
			
	}
}