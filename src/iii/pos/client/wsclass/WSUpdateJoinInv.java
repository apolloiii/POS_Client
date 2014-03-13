package iii.pos.client.wsclass;

import iii.pos.client.activity.MainPosActivity;
import iii.pos.client.server.ConfigurationServer;
import iii.pos.client.server.ConfigurationWS;

import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


//-------------ws class update invoice --------------------//
public class WSUpdateJoinInv extends AsyncTask<Void, Void, Void> {
	private String inv_code_old;
	private String inv_code_new;
	private static ConfigurationWS mWS;

	// ----------asyntask Contructor----------------------//
	public WSUpdateJoinInv(Context mContext, String inv_code_old,
			String inv_code_new) {
		this.inv_code_new = inv_code_new;
		this.inv_code_old = inv_code_old;
		mWS = new ConfigurationWS(mContext);
	}

	// --------background method-------------------//
	@Override
	protected synchronized Void doInBackground(Void... params) {
		try {

			String URLUpdateJoinInv = ConfigurationServer.getURLServer() + "wsupdatejoininv.php";

			JSONObject json = new JSONObject();
			json.put("inv_code_new", inv_code_new);
			json.put("inv_code_old", inv_code_old);
			json.put("user_id", MainPosActivity.phoneNumber);
			json.put("company_code", MainPosActivity.company_code);
			mWS.connectWS_Put_Data(URLUpdateJoinInv, json);

		} catch (Exception e) {
			Log.i("Log : ", "Exception : " + e.getMessage());
		}
		return null;
	}

}
