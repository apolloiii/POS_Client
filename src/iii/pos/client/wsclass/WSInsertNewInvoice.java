package iii.pos.client.wsclass;

import iii.pos.client.activity.MainPosActivity;
import iii.pos.client.server.ConfigurationServer;
import iii.pos.client.server.ConfigurationWS;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


/**
 * 
 * @author tranminhthuan Copyright (C) 2013 III COMPANY
 * 
 */
/* -----------add new an Invoice to server------------------------------ */
public class WSInsertNewInvoice extends AsyncTask<Void, Void, Void> {
	private String inv_code;
	private int status;
	private int user_id;
	private String parent_inv;
	private int inv_type;
	private String nameOldInvoice;
	private ConfigurationWS mWS;
	private ProgressDialog dialog;
	private Context mContext;

	// --------constructor-----------------------------//
	public WSInsertNewInvoice(Context mContext, String nameOldInvoice, String inv_code, int status, int user_id,  String parent_inv, int inv_type) {
		this.inv_code = inv_code;
		this.mContext = mContext;
		this.status = status;
		this.user_id = user_id;
		this.parent_inv = parent_inv;
		this.inv_type = inv_type;
		this.nameOldInvoice = nameOldInvoice;
		mWS = new ConfigurationWS(mContext);
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog = new ProgressDialog(mContext);
		dialog.setMessage("Loading");
		dialog.show();
	}

	// --------background method-------------------//
	@Override
	protected synchronized Void doInBackground(Void... params) {
		try {

			String URLAddNewInvoice = ConfigurationServer.getURLServer() + "wsaddnewinvoicechangetable.php";
			JSONObject json = new JSONObject();
			json.put("inv_code", inv_code);
			json.put("status", 1);
			json.put("user_id", MainPosActivity.phoneNumber);
			json.put("inv_type", inv_type);
			json.put("parent_inv", parent_inv);
			json.put("inv_code_old", nameOldInvoice);
			JSONArray arrItem = mWS.connectWSPut_Get_Data(URLAddNewInvoice, json, "posts");
			if (arrItem != null) {
				JSONObject results = arrItem.getJSONObject(0);
				Log.i("Log : ", "Thanh cong: " + results.getString("success"));
			}
		} catch (Exception e) {
			Log.i("Log : ", "Insert INV Detail : " + e.getMessage());
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		dialog.dismiss();
	}

}