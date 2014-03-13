package iii.pos.client.wsclass;
/**
 * update hoa don vao bang invoice_detail khi them ban vao invoice
 */
import iii.pos.client.activity.MainPosActivity;
import iii.pos.client.server.ConfigurationServer;
import iii.pos.client.server.ConfigurationWS;

import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


//-------------ws class update invoice --------------------//
public class WSUpdateInvoiceDetail extends AsyncTask<Void, Void, Void> {
	private String inv_code_old;
	private String inv_code_new;
	private static ConfigurationWS mWS;
	private int language_code;

	// ----------asyntask Contructor----------------------//
	public WSUpdateInvoiceDetail(Context mContext, String inv_code_old, String inv_code_new, int language_code) {
		this.inv_code_new = inv_code_new;
		this.inv_code_old = inv_code_old;
		mWS = new ConfigurationWS(mContext);
		this.language_code = language_code;
	}

	// --------background method-------------------//
	@Override
	protected synchronized Void doInBackground(Void... params) {
		try {
			String URLUpdateJoinInv = ConfigurationServer.getURLServer() + "wsaddinvoicedetail_changetable.php";
			JSONObject json = new JSONObject();
			
			json.put("inv_code", inv_code_new);
			json.put("inv_code_old", inv_code_old);
			json.put("language_code", language_code);
			json.put("user_id", MainPosActivity.phoneNumber);
			mWS.connectWS_Put_Data(URLUpdateJoinInv, json);
		} catch (Exception e) {
			Log.i("Log : ", "Exception : " + e.getMessage());
		}
		return null;
	}

}
