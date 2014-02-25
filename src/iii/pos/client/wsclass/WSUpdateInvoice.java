package iii.pos.client.wsclass;

import iii.pos.client.server.ConfigurationServer;
import iii.pos.client.server.ConfigurationWS;

import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


//-------------ws class update invoice --------------------//
public class WSUpdateInvoice extends AsyncTask<Void, Void, Void> {
	private String inv_code;
	private float totalVAT;
	private float cost;
	private int vat;
	private int commision;
	private static ConfigurationWS mWS;

	// ----------asyntask Contructor----------------------//
	public WSUpdateInvoice(Context mContext, String inv_code, float totalVAT,
			float cost, int vat, int commision) {
		this.inv_code = inv_code;
		this.totalVAT = totalVAT;
		this.cost = cost;
		this.vat = vat;
		this.commision = commision;
		mWS = new ConfigurationWS(mContext);
	}

	// --------background method-------------------//
	@Override
	protected synchronized Void doInBackground(Void... params) {
		try {

			String URLAddnewItem = ConfigurationServer.getURLServer()
					+ "wsupdateinvoice.php";

			JSONObject json = new JSONObject();
			json.put("inv_code", inv_code);
			json.put("total", totalVAT);
			json.put("cost", cost);
			json.put("vat", vat);
			json.put("commision", commision);

			mWS.connectWS_Put_Data(URLAddnewItem, json);

		} catch (Exception e) {
			Log.i("Log : ", "Exception : " + e.getMessage());
		}
		return null;
	}

}
