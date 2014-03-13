package iii.pos.client.wsclass;

import iii.pos.client.activity.MainPosActivity;
import iii.pos.client.model.Invoice_Detail;
import iii.pos.client.server.ConfigurationServer;
import iii.pos.client.server.ConfigurationWS;

import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

public class WSAddNewInvDetail extends AsyncTask<Void, Void, Void> {
	private String inv_code;
	private List<Invoice_Detail> lstInvoice;
	private ConfigurationWS mWS;
	private Context mContext;
	private ProgressDialog dialog;

	public  WSAddNewInvDetail(Context mContext, String inv_code, List<Invoice_Detail> lstInvoice) {
		this.inv_code = inv_code;
		this.lstInvoice = lstInvoice;
		this.mContext = mContext;
		mWS = new ConfigurationWS(mContext);
		dialog = new ProgressDialog(mContext);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog.setMessage("Loading...");
		dialog.setCancelable(false);
		dialog.show();
	}

	@Override
	protected synchronized Void doInBackground(Void... params) {
		try {
			String URLAddnewItem = ConfigurationServer.getURLServer() + "wsaddnewinvoicedetail.php";
			for (Invoice_Detail items : lstInvoice) {
				JSONObject json = new JSONObject();
				json.put("inv_code", inv_code);
				json.put("item_id", items.getItem_id());// IIIPOS
				json.put("quantity", items.getQuantity());
				json.put("comment", items.getComment());
				json.put("user_id", MainPosActivity.phoneNumber);
				mWS.connectWS_Put_Data(URLAddnewItem, json);
			}
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		Activity activity = ((Activity) mContext);
		Intent returnIntent = activity.getIntent();
		returnIntent.putExtra(MainPosActivity.INVOICE_CODE, inv_code);
		activity.setResult(Activity.RESULT_OK, returnIntent);
		if (!activity.getClass().getName().equalsIgnoreCase("iii.pos.coffe.activity.MainPosActivity")) {
			activity.finish();
		}
		Toast.makeText(activity.getApplicationContext(), "Thêm Mới Món Ăn Thành Công", Toast.LENGTH_SHORT).show();
		try {
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
		} catch (Exception e) {
		}
		MainPosActivity.beanDataAll.makeDataInvDetail();
		super.onPostExecute(result);
	}
}
