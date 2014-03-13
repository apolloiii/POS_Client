package iii.pos.client.wsclass;

import iii.pos.client.activity.MainPosActivity;
import iii.pos.client.server.ConfigurationServer;
import iii.pos.client.server.ConfigurationWS;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;


//-------------payment (update invoice, update table)-------//
public class WSDestroyInvoice extends AsyncTask<Void, Void, Void> {
	private String inv_code = "";
	private ConfigurationWS mWS;
	private float vat, com, total, cost;
	private int inv_type;

	// --------constructor-----------------------------//
	public WSDestroyInvoice(Context mContext, String inv_code, float vat, float com,
			float cost, float total, int inv_type) {
		this.inv_code = inv_code;
		this.vat = vat;
		this.com = com;
		this.cost = cost;
		this.total = total;
		this.inv_type = inv_type;
		this.mWS = new ConfigurationWS(mContext);
	}

	// --------background method-------------------//
	@Override
	protected synchronized Void doInBackground(Void... params) {
		try {
			String UrlPayment = ConfigurationServer.getURLServer()
					+ "wsdestroyInvoice.php";
			JSONObject json = new JSONObject();
			json.put("inv_code", inv_code);
			json.put("vat", vat);
			json.put("com", com);
			json.put("cost", cost);
			json.put("total", total);
			json.put("inv_type", inv_type);
			json.put("user_id", MainPosActivity.phoneNumber);
			
			mWS.connectWS_Put_Data(UrlPayment, json);
		} catch (JSONException e) {

		}
		return null;
	}

}
