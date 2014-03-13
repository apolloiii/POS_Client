package iii.pos.client.wsclass;

import iii.pos.client.activity.MainPosActivity;
import iii.pos.client.model.Itable;
import iii.pos.client.server.ConfigurationWS;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
/**
 * 
 * @author MrTrongKhiem89
 * Tạo webservice lấy tất cả các bàn thuộc các tầng khác nhau
 * Đầu vào là URL
 * Đầu ra là một danh sách các bàn
 */
public class WSGetAllTable extends AsyncTask<String, Void, ArrayList<Itable>> {
	private ProgressDialog dialog;
	private ConfigurationWS mWS;

	public WSGetAllTable(Context mcontext) {
		super();
		dialog = new ProgressDialog(mcontext);
		mWS = new ConfigurationWS(mcontext);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog.setMessage("Loading...");
		dialog.setCancelable(false);
		dialog.show();
	}

	@Override
	protected ArrayList<Itable> doInBackground(String... params) {
		ArrayList<Itable> lstItable = new ArrayList<Itable>();
		try {
			//ConfigurationServer.getURLServer() + "wsget_all_itable.php"
			String URL = params[0];
			JSONObject json = new JSONObject();
			json.put("user_id", MainPosActivity.phoneNumber);
			json.put("company_code", MainPosActivity.company_code);
			JSONArray arrITable = mWS.connectWSPut_Get_Data(URL, json, "itable");
			for (int i = 0; i < arrITable.length(); i++) {
				JSONObject results = arrITable.getJSONObject(i);
				Log.i("Log : ", "GET ALL TABLE : " + results);
				Itable itable = new Itable();
					itable.setItable_id(results.getInt("itable_id"));
					itable.setCode_table(results.getString("code_table"));
					itable.setDescription_table(results .getString("description_table"));
					itable.setStatus(results.getInt("status"));
					itable.setCreate_time(results.getString("create_time"));
					itable.setUpdate_date(results.getString("update_time"));
					itable.setFlag(results.getInt("flag"));
					itable.setPos_x(results.getInt("pos_x"));
					itable.setPos_y(results.getInt("pos_y"));
					 
				lstItable.add(itable);
			}
		} catch (Exception e) {
		}
		return lstItable;
	}

	@Override
	protected void onPostExecute(ArrayList<Itable> result) {
		super.onPostExecute(result);
		if (dialog.isShowing()) {
			dialog.dismiss();
		}
	}
}
