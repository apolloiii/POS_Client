package iii.pos.client.wsclass;

import iii.pos.client.activity.MainPosActivity;
import iii.pos.client.model.Itable;
import iii.pos.client.server.ConfigurationServer;
import iii.pos.client.server.ConfigurationWS;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class WSGetAllItableFreeByFloor extends AsyncTask<Void, Void, ArrayList<Itable>>{
	
	private ConfigurationWS mWS;
	private ProgressDialog dialog;
	private String companyCode;
	private int floor;
	
	public WSGetAllItableFreeByFloor(Context mContext, String companyCode, int floor) {
		super();
		mWS = new ConfigurationWS(mContext);
		this.companyCode = companyCode;
		this.floor = floor;
		dialog = new ProgressDialog(mContext);
		
	}

	@Override
	protected void onPreExecute() {
		dialog.setMessage("Loading");
		dialog.show();
		super.onPreExecute();
	}
	@Override
	protected ArrayList<Itable> doInBackground(Void... params) {
		ArrayList<Itable> lstItableByFloors = new ArrayList<Itable>();
		try {
			// ---------------get String ------------------------//
			String URL = ConfigurationServer.getURLServer() + "wsgetallitable.php";
			JSONObject json = new JSONObject();
			json.put("floor", floor);
			json.put("user_id", MainPosActivity.user.getUser_id());
			json.put("company_code", companyCode);
			
			JSONArray arrITable = mWS.connectWSPut_Get_Data(URL, json, "itable");
			for (int i = 0; i < arrITable.length(); i++) {
				JSONObject results = arrITable.getJSONObject(i);
				Itable itable = new Itable();
				itable.setItable_id(results.getInt("itable_id"));
				itable.setCode_table(results.getString("code_table"));
				itable.setDescription_table(results.getString("description_table"));
				itable.setStatus(results.getInt("status"));
				itable.setCreate_time(results.getString("create_time"));
				itable.setUpdate_date(results.getString("update_time"));
				itable.setFlag(results.getInt("flag"));
				itable.setPos_x(results.getInt("pos_x"));
				itable.setPos_y(results.getInt("pos_y"));
				lstItableByFloors.add(itable);
			}
		} catch (Exception e) {
			Log.i("Log : ", "Exception : " + e.getMessage());
		}
		return lstItableByFloors;
	}
	
	@Override
	protected void onPostExecute(ArrayList<Itable> result) {
		super.onPostExecute(result);
		if(dialog.isShowing()){
			dialog.dismiss();
		}
	}

}
