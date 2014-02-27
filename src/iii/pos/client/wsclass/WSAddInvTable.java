package iii.pos.client.wsclass;

import iii.pos.client.activity.MainPosActivity;
import iii.pos.client.server.ConfigurationServer;
import iii.pos.client.server.ConfigurationWS;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


/**
 * 
 * @author tranminhthuan Copyright (C) 2013 III COMPANY
 * 
 */
/* ==============add new an Invoice to server================== */
public class WSAddInvTable extends AsyncTask<Void, Void, Boolean> {
	private String inv_code;
	private ArrayList<String> itemTable;
	private ConfigurationWS mWS;
	private int user_id;

	// =======================constructor===========================//
	public WSAddInvTable(Context mContext, String inv_code, int user_id, ArrayList<String> itemTable1) {
		this.inv_code = inv_code;
		this.user_id = user_id;
		this.itemTable = new ArrayList<String>();
		mWS = new ConfigurationWS(mContext);
		for (String string : itemTable1) {
			itemTable.add(string);
		}
	}

	// =================background method=====================-//
	@Override
	protected synchronized Boolean doInBackground(Void... params) {
		boolean isSuccess = false;
		try {
			String URLAddNewInvoice = ConfigurationServer.getURLServer() + "wsclient_add_inv_itable.php";
			for (String table_code : itemTable) {
				JSONObject json = new JSONObject();
				json.put("inv_code", inv_code);
				json.put("table_code", table_code);
				json.put("user_id", MainPosActivity.phoneNumber);
				JSONArray arrItem = mWS.connectWSPut_Get_Data(URLAddNewInvoice, json, "posts");
				if (arrItem != null) {
					JSONObject results = arrItem.getJSONObject(0);
					Log.i("Log : ", "Thanh cong: " + results.getString("success"));
					isSuccess = true;
				}
			}

		} catch (Exception e) {
			Log.i("Log : ", "Insert INV Detail : " + e.getMessage());
		}
		return isSuccess;
	}

}