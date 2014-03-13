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
 * @author MrTrongKhiem89
 * Tạo WS xóa bàn trong bảng pos_itable và pos_stagge_itable dựa vào table_id truyền vào
 */
public class WSDeleteTableById extends AsyncTask<Integer, Void, Void> {
	private ProgressDialog dialog;
	private ConfigurationWS mWS;

	public WSDeleteTableById(Context mcontext) {
		super();
		dialog = new ProgressDialog(mcontext);
		mWS = new ConfigurationWS(mcontext);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog.setMessage("Updating...");
		dialog.setCancelable(false);
		dialog.show();
	}

	@Override
	protected Void doInBackground(Integer... params) {
		try {
			int tableId = params[0];
			String URL = ConfigurationServer.getURLServer() + "wsdelete_table_by_id.php" ;
			JSONObject json = new JSONObject();
			json.put("itable_id", tableId);
			json.put("user_id", MainPosActivity.phoneNumber);
			JSONArray arrITable = mWS.connectWSPut_Get_Data(URL, json, "itable");
			for (int i = 0; i < arrITable.length(); i++) {
				JSONObject results = arrITable.getJSONObject(i);
				Log.i("Log : ", "DELETE TABLE and STAGE_ITABLE BY TABLE_ID: " + results);
			}
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	protected void onPostExecute( Void result) {
		super.onPostExecute(result);
		if (dialog.isShowing()) {
			dialog.dismiss();
		}
	}
}
