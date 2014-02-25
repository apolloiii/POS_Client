package iii.pos.client.wsclass;

import iii.pos.client.server.ConfigurationServer;
import iii.pos.client.server.ConfigurationWS;

import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;


//=================Update user log==================//
public class WSUpdateLogPos extends AsyncTask<Void, Void, Void> {
	private int user_id;
	private ConfigurationWS mWS;

	// ===============constructor=============================//
	public WSUpdateLogPos(Context mContext, int user_id) {
		this.user_id = user_id;
		mWS = new ConfigurationWS(mContext);
	}

	// ===============do in background==================//
	@Override
	protected synchronized Void doInBackground(Void... params) {
		try {
			String URLAddnewItem = ConfigurationServer.getURLServer() + "wsupdatelogpos.php";
			JSONObject json = new JSONObject();
			json.put("user_id", user_id);
			mWS.connectWS_Put_Data(URLAddnewItem, json);
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
	}
}
