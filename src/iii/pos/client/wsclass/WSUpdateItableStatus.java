package iii.pos.client.wsclass;

import iii.pos.client.activity.MainPosActivity;
import iii.pos.client.server.ConfigurationServer;
import iii.pos.client.server.ConfigurationWS;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


/* ----------update status==2 when invoiced added--------- */
public class WSUpdateItableStatus extends AsyncTask<Void, Void, Boolean> {
	private int check = 0;
	private ArrayList<String> itemTable;
	private static ConfigurationWS mWS;

	// ----------asyntask contructor----------------------//
	/**
	 * 
	 * @param mContext
	 * @param check
	 * @param user_id
	 * @param itemTable1
	 */
	public WSUpdateItableStatus(Context mContext, int check, ArrayList<String> itemTable1) {
		this.check = check;
		itemTable = new ArrayList<String>();
		mWS = new ConfigurationWS(mContext);
		for (String string : itemTable1) {
			itemTable.add(string);
		}
	}

	// --------background method-------------------//
	@Override
	protected Boolean doInBackground(Void... params) {
		boolean isSuccess = false;
		try {
			String UrlCheckItableFocus = ConfigurationServer.getURLServer() + "wsclient_update_invdetail.php";
			for (String table_code : itemTable) {
				JSONObject json = new JSONObject();
				json.put("check", check);
				json.put("table_code", table_code);
				json.put("user_id", MainPosActivity.user.getUser_id());
				json.put("company_code", MainPosActivity.user.getCompanyCode());
				//mWS.connectWS_Put_Data(UrlCheckItableFocus, json);
				JSONArray arrItem = mWS.connectWSPut_Get_Data(UrlCheckItableFocus, json, "posts");
				if (arrItem != null) {
					JSONObject results = arrItem.getJSONObject(0);
					Log.i("Log : ", "Thanh cong: " + results.getString("success"));
					isSuccess = true;
				}
			}
		} catch (JSONException e) { }
		return isSuccess;
	}
}