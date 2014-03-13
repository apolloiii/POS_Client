package iii.pos.client.wsclass;

import iii.pos.client.activity.MainPosActivity;
import iii.pos.client.server.ConfigurationServer;
import iii.pos.client.server.ConfigurationWS;

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
/* -----------add new an Invoice to server------------------------------ */
public class WSDeleteNewInvoice extends AsyncTask<Void, Void, Void> {
	private String inv_code;
	private String code_table;
	private ConfigurationWS mWS;

	// --------constructor-----------------------------//
	public WSDeleteNewInvoice(Context mContext, String inv_code, String code_table) {
		this.inv_code = inv_code;
		this.code_table = code_table;
		mWS = new ConfigurationWS(mContext);
	}
	// --------background method-------------------//
	@Override
	protected synchronized Void doInBackground(Void... params) {
		try {

			String URLAddNewInvoice = ConfigurationServer.getURLServer() + "wsclient_delete_new_invoice.php";
			JSONObject json = new JSONObject();
			json.put("inv_code", inv_code);
			json.put("code_table", code_table);
			json.put("user_id", MainPosActivity.phoneNumber);
			json.put("company_code", MainPosActivity.company_code);
			
			JSONArray arrItem = mWS.connectWSPut_Get_Data(URLAddNewInvoice, json, "posts");
			if (arrItem != null) {
				JSONObject results = arrItem.getJSONObject(0);
				Log.i("Log : ", "Xoa Thanh cong: " + results.getString("success"));
			}
		} catch (Exception e) {
			Log.i("Log : ", "Insert INV Detail : " + e.getMessage());
		}
		return null;
	}
}