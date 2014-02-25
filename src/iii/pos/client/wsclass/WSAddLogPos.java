package iii.pos.client.wsclass;

import iii.pos.client.server.ConfigurationServer;
import iii.pos.client.server.ConfigurationWS;

import org.json.JSONObject;
import android.content.Context;
import android.os.AsyncTask;


//================insert user log server================//
public class WSAddLogPos extends AsyncTask<Void, Void, Void> {
	private int user_id;
	private ConfigurationWS mWS;

	// ================constructor-================//
	public WSAddLogPos(Context mContext, int user_id) {
		this.user_id = user_id;
		mWS = new ConfigurationWS(mContext);
	}

	// ================background method================//
	@Override
	protected synchronized Void doInBackground(Void... params) {
		try {
			String URLAddnewItem = ConfigurationServer.getURLServer() + "wsaddlogpos.php";
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
