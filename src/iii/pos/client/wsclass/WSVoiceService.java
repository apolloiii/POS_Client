package iii.pos.client.wsclass;

import iii.pos.client.activity.MainPosActivity;
import iii.pos.client.server.ConfigurationWS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class WSVoiceService extends AsyncTask<Void, Void, Void> {
	private int invdetail_id;
	private int status;
	private int flag;
	private int item_id;
	private int type;
	private String inv_code;
	private ConfigurationWS mWS;

	public WSVoiceService(Context context, int invdetail_id, int item_id, String inv_code, int status, int flag, int type) {
		super();
		this.invdetail_id = invdetail_id;
		this.status = status;
		this.flag = flag;
		this.item_id = item_id;
		this.inv_code = inv_code;
		this.type = type;
		mWS = new ConfigurationWS(context);
	}

	@Override
	protected Void doInBackground(Void... params) {
		try {
			String wsgetdetailinvdetail = /*ConfigurationServer.getURLServer()*/"http://117.6.131.222:6789/pos/wspos/" + "wsvoiceservice.php";
			JSONObject json = new JSONObject();
			json.put("user_id", MainPosActivity.phoneNumber);
			json.put("company_code", MainPosActivity.company_code);
			json.put("invdetail_id", invdetail_id);
			Log.e("1..........", "" + invdetail_id);
			json.put("status", status);
			Log.e("2...........", "" + status);
			json.put("flag", flag);
			Log.e("3...........", "" + flag);
			json.put("item_id", item_id);
			Log.e("4...........", "" + item_id);
			json.put("inv_code", inv_code);
			Log.e("5...........", "" + inv_code);
			json.put("type", type);
			JSONArray arrItem = new JSONArray();
			arrItem = mWS.connectWSPut_Get_Data(wsgetdetailinvdetail, json, "voiceservice");
			if (arrItem != null) { 
				JSONObject results = arrItem.getJSONObject(0);
				try {
					Log.i("insert voice: ", results.getString("voiceservice"));
				} catch (Exception e) {
				}
			}
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		return null;
	}
}
