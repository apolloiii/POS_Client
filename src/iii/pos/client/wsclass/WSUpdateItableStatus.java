package iii.pos.client.wsclass;

import iii.pos.client.server.ConfigurationServer;
import iii.pos.client.server.ConfigurationWS;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


/* ----------update status==2 when invoiced added--------- */
public class WSUpdateItableStatus extends AsyncTask<Void, Void, Void> {
	private int check = 0;
	private String user_id ;
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
	public WSUpdateItableStatus(Context mContext, int check, String user_id, ArrayList<String> itemTable1) {
		this.check = check;
		this.user_id = user_id;
		itemTable = new ArrayList<String>();
		mWS = new ConfigurationWS(mContext);
		for (String string : itemTable1) {
			itemTable.add(string);
		}
	}

	// --------background method-------------------//
	@Override
	protected Void doInBackground(Void... params) {
		try {

			String UrlCheckItableFocus = ConfigurationServer.getURLServer() + "wsupdateitable.php";
			for (String table_code : itemTable) {
				JSONObject json = new JSONObject();
				json.put("check", check);
				json.put("table_code", table_code);
				json.put("user_id", user_id);
				mWS.connectWS_Put_Data(UrlCheckItableFocus, json);
			}

		} catch (JSONException e) {
		}
		return null;
	}
}