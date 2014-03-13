package iii.pos.client.wsclass;

import iii.pos.client.activity.MainPosActivity;
import iii.pos.client.server.ConfigurationServer;
import iii.pos.client.server.ConfigurationWS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


//=========class to UPLOAD voice status to voicepos table======//
public class WSVoiceService_UpdateVoice extends AsyncTask<Void, Void, Void> {
	// ===============fields=================================//

	private int voice_id;
	private int status;
	private int flag;
	private ConfigurationWS mWS;

	// ===============constructor=============================//
	public WSVoiceService_UpdateVoice(Context context, int voice_id,
			int status, int flag) {
		super();
		this.voice_id = voice_id;
		this.status = status;
		this.flag = flag;
		mWS = new ConfigurationWS(context);
	}

	// ===============do in background=========================//
	@Override
	protected Void doInBackground(Void... params) {
		try {
			String wsgetdetailinvdetail = ConfigurationServer.getURLServer()
					+ "wsvoiceservice_updatevoice.php";
			JSONObject json = new JSONObject();
			json.put("voice_id", voice_id);
			json.put("status", status);
			json.put("flag", flag);
			json.put("user_id", MainPosActivity.phoneNumber);
			JSONArray arrItem = new JSONArray();
			arrItem = mWS.connectWSPut_Get_Data(wsgetdetailinvdetail, json,
					"wsvoiceservice_updatevoice");
			if (arrItem != null) {
				JSONObject results = arrItem.getJSONObject(0);
				try {
					Log.i("TAG:",
							results.getString("wsvoiceservice_updatevoice"));
				} catch (Exception e) {
				}
			}
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		return null;
	}

}
