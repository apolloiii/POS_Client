package iii.pos.client.wsclass;

import iii.pos.client.activity.MainPosActivity;
import iii.pos.client.model.Itable;
import iii.pos.client.server.ConfigurationServer;
import iii.pos.client.server.ConfigurationWS;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class WSUpdateItable extends AsyncTask<Itable, Void, Void> {
	private ConfigurationWS mWS;
	private ProgressDialog mProgress;
	private Context context;
	public WSUpdateItable(Context mContext) {
		mWS = new ConfigurationWS(mContext);
		mProgress = new ProgressDialog(mContext);
		this.context = mContext;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		mProgress.setMessage("Updating...");
		mProgress.setCancelable(false);
		mProgress.show();
	}

	@Override
	protected Void doInBackground(Itable... params) {
		try {
			String URLAddnewItem = ConfigurationServer.getURLServer() + "wsupdate_list_itable.php";
			Itable itable = (Itable) params[0];
			JSONObject json = new JSONObject();
			json.put("table_id", itable.getItable_id());
			json.put("code_table", itable.getCode_table());
			json.put("pos_x", itable.getPos_x());
			json.put("pos_y", itable.getPos_y());
			json.put("user_id", MainPosActivity.phoneNumber);
			json.put("company_code", MainPosActivity.company_code);
			
			Log.i("JSON UPDATE_ITABLE: table_id: ", itable.getItable_id()+"");
			Log.i("JSON UPDATE_ITABLE: code_table: ", itable.getCode_table());
			Log.i("JSON UPDATE_ITABLE: GPS_X", itable.getPos_x() + "");
			Log.i("JSON UPDATE_ITABLE: GPS_Y", itable.getPos_y() + "");
			Log.i("JSON UPDATE_ITABLE: floor", itable.getFlag() + "");

			JSONArray arrITable = mWS.connectWSPut_Get_Data(URLAddnewItem,
					json, "posts");

			JSONObject results = arrITable.getJSONObject(0);
			if (!results.getString("result").equals("false")) {
				String msg = results.getString("result");
				Log.i("JSON RESUL UPDATE_ITABLE:", msg + "");
			}
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		if (mProgress.isShowing())
			mProgress.dismiss();
		Toast.makeText(context, "Update success", Toast.LENGTH_SHORT) .show();
	}
}
