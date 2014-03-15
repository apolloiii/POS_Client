package iii.pos.client.wsclass;

import iii.pos.client.activity.MainPosActivity;
import iii.pos.client.server.ConfigurationServer;
import iii.pos.client.server.ConfigurationWS;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class WSGetAllFloor extends AsyncTask<Void, Void, ArrayList<String>> {
	// ------------------default floor1-----------------------------//
		private ConfigurationWS mWs;
		private ProgressDialog mProgress;
		private ArrayList<String> lstAllFlor ;
		
		public WSGetAllFloor(Context mContext) {
			super();
			mWs = new ConfigurationWS(mContext);
			mProgress = new ProgressDialog(mContext);
			lstAllFlor = new ArrayList<String>();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgress.setMessage("Loading...");
			mProgress.setCancelable(false);
			mProgress.show();
		}

		@Override
		protected ArrayList<String> doInBackground(Void... params) {
			if( lstAllFlor.size() > 0 ) lstAllFlor.clear();
			try {
				// ---------------get String ------------------------//
				String URLGetAllFloor = ConfigurationServer.getURLServer() + "wsgetallfloor.php";
				JSONObject json = new JSONObject();
				json.put("user_id", MainPosActivity.user.getUser_id());
				json.put("company_code", MainPosActivity.user.getCompanyCode());
				
				JSONArray arrITable = mWs.connectWSPut_Get_Data(URLGetAllFloor, json, "floor");

				for (int i = 0; i < arrITable.length(); i++) {
					JSONObject results = arrITable.getJSONObject(i);
						lstAllFlor.add(results.getString("id"));
				}
			} catch (Exception e) {
			}

			return lstAllFlor;
		}

		@Override
		protected void onPostExecute( ArrayList<String> result) {
			if( mProgress.isShowing() )
				mProgress.dismiss();
		}
}
