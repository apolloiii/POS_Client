package iii.pos.client.wsclass;

import iii.pos.client.R;
import iii.pos.client.activity.MainPosActivity;
import iii.pos.client.model.Company;
import iii.pos.client.server.ConfigurationServer;
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
public class WSGetCompanyCode extends AsyncTask<String, Void, ArrayList<Company>> {
	private ProgressDialog progressDialog;
	private ConfigurationWS mWS;
	private Context context;

	public WSGetCompanyCode(Context mcontext) {
		progressDialog = new ProgressDialog(mcontext);
		mWS = new ConfigurationWS(mcontext);
		this.context = mcontext;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progressDialog.setMessage("Loading...");
		progressDialog.show();
	}

	@Override
	protected ArrayList<Company> doInBackground(String... arg0) {
		ArrayList<Company> lstCompanies = new ArrayList<Company>();
		try {
			try {
				lstCompanies.clear();
				// ---------------get String ------------------------//
				String URL = ConfigurationServer.getURLServer() + "wsgetcompanycode.php";
				JSONObject json = new JSONObject();
				json.put("company_code", MainPosActivity.user.getCompanyCode());
				
				JSONArray jarr = new JSONArray();
				jarr =mWS.connectWSPut_Get_Data(URL, json, "posts");
				for (int i = 0; i < jarr.length(); i++) {
					JSONObject results = jarr.getJSONObject(i);
					try{
						Company com = new Company();
						if(results.getString("companyname") != null)
							com.setCompanyname(results.getString("companyname"));
						if(results.getString("company_code") != null)
							com.setCompanycode(results.getString("company_code"));
						if(results.getString("lat") != null)
							com.setLat(Double.parseDouble(results.getString("lat")));
						if(results.getString("lon") != null)
							com.setLon(Double.parseDouble(results.getString("lon")));
						if(results.getString("radius") != null)
							com.setRadius(Float.parseFloat(results.getString("radius")));
						lstCompanies.add(com);
					}catch(Exception e){ }
				}
			} catch (Exception e) {
				Log.i("Log : ", "Exception : " + e.getMessage());
			}
		} catch (Exception e) {}
		return lstCompanies;
	}
	
	@Override
	protected void onPostExecute(ArrayList<Company> result) {
		super.onPostExecute(result);
		if (progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
	}

}
