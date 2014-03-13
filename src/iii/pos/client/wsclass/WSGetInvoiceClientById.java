package iii.pos.client.wsclass;

import iii.pos.client.activity.MainPosActivity;
import iii.pos.client.model.Invoice;
import iii.pos.client.server.ConfigurationServer;
import iii.pos.client.server.ConfigurationWS;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import android.content.Context;
import android.os.AsyncTask;

/**
 * Lớp chỉ lấy về những hóa đơn của Guest dựa vào SĐT
 * @author GIACNGO
 *
 */

public class WSGetInvoiceClientById extends AsyncTask< Integer, Void, ArrayList<Invoice> > {
	
	private ConfigurationWS mWs;

	public WSGetInvoiceClientById(Context mContext) {
		super();
		mWs = new ConfigurationWS(mContext);
	}

	@Override
	protected ArrayList<Invoice> doInBackground(Integer... params) {
		ArrayList<Invoice> lstInvoiceClient = new ArrayList<Invoice>();
		try {
			String phoneNumber = MainPosActivity.phoneNumber;
			String URLInvoiceClientById = ConfigurationServer.getURLServer() + "wsclient_getallinvoice_byphone.php";
			JSONObject json = new JSONObject();
			json.put("user_id", phoneNumber);
			json.put("company_code", MainPosActivity.company_code);
			JSONArray arrInvoice = mWs.connectWSPut_Get_Data( URLInvoiceClientById, json, "invoice");

			for (int i = 0; i < arrInvoice.length(); i++) {
				JSONObject results = arrInvoice.getJSONObject(i);
				Invoice invoice = new Invoice();
				invoice.setInv_id(results.getInt("inv_id"));
				invoice.setInv_code(results.getString("inv_code"));
				invoice.setTotal(Float.parseFloat(results.getString("total")));
				invoice.setCost(Float.parseFloat(results.getString("cost")));
				invoice.setVat(Integer.parseInt(results.getString("vat")));
				invoice.setCommision(Integer.parseInt(results .getString("commision")));
				invoice.setInv_starttime(results.getString("inv_endtime"));
				invoice.setInv_endtime(results.getString("inv_starttime"));
				invoice.setUser_id(results.getInt("user_id"));
				invoice.setStatus(results.getInt("status"));
				ArrayList<String> listCodeTable = new ArrayList<String>();
				JSONArray codeTableArray = results.getJSONArray("code_table");
				for (int j = 0; j < codeTableArray.length(); j++) {
					listCodeTable.add(codeTableArray.get(j).toString());
				}
				invoice.setLstCodeTables(listCodeTable);
				lstInvoiceClient.add(invoice);
			}
		} catch (Exception e) {
		}
		return lstInvoiceClient;
	}
}
