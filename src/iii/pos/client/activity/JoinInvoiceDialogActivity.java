/*package iii.pos.client.activity;

import iii.pos.client.R;
import iii.pos.client.adapter.InvoiceCouplingAdapter;
import iii.pos.client.fragment.InvoicePosFragment;
import iii.pos.client.model.Invoice;
import iii.pos.client.model.Invoice_Detail;
import iii.pos.client.server.ConfigurationServer;
import iii.pos.client.server.ConfigurationWS;
import iii.pos.client.wsclass.WSAddNewInvDetail;
import iii.pos.client.wsclass.WSAddNewInvoice;
import iii.pos.client.wsclass.WSStatusInvoice;
import iii.pos.client.wsclass.WSUpdateJoinInv;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class JoinInvoiceDialogActivity extends FragmentActivity implements OnClickListener {

	private InvoiceCouplingAdapter adapter;
	private List<Invoice> lstLv1;
	private List<Invoice> lstLv2;
	private ListView lv1;
	private ListView lv2;
	private ImageButton btnAdd, btnRemove;
	private ImageButton btnJoinInv, btnCancel;
	private ConfigurationWS mWS;
	private List<Invoice> listInvTmp = new CopyOnWriteArrayList<Invoice>();
	private List<Invoice_Detail> lstInvDetail;

	// =================initialize view here===============//
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.couplingdialog);
		mWS = new ConfigurationWS(JoinInvoiceDialogActivity.this);
		show();
	}

	private void show() {
		lv1 = (ListView) findViewById(R.id.lv1);
		lv2 = (ListView) findViewById(R.id.lv2);
		btnAdd = (ImageButton) findViewById(R.id.btnAdd);
		btnRemove = (ImageButton) findViewById(R.id.btnRemove);
		btnJoinInv = (ImageButton) findViewById(R.id.btnJoinInv);
		btnCancel = (ImageButton) findViewById(R.id.btnCancel);

		btnAdd.setOnClickListener(this);
		btnRemove.setOnClickListener(this);
		btnJoinInv.setOnClickListener(this);
		btnCancel.setOnClickListener(this);

		lstLv1 = new CopyOnWriteArrayList<Invoice>();
		lstLv2 = new CopyOnWriteArrayList<Invoice>();
		lstInvDetail = new CopyOnWriteArrayList<Invoice_Detail>();
		new WSGetInvoice(JoinInvoiceDialogActivity.this).execute();
	}

	// =================On Click Action===============//
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// =================Add INV===============//
		case R.id.btnAdd:
			for (Invoice inv : lstLv1) {
				if (inv.isCheck()) {
					inv.setCheck(false);
					lstLv2.add(inv);
					lstLv1.remove(inv);
				}
			}
			adapter = new InvoiceCouplingAdapter(lstLv2, JoinInvoiceDialogActivity.this);
			lv2.setAdapter(adapter);

			break;
		// =================Remove INV===============//
		case R.id.btnRemove:
			for (Invoice inv : lstLv2) {
				if (inv.isCheck()) {
					inv.setCheck(false);
					lstLv1.add(inv);
					lstLv2.remove(inv);
				}
			}
			adapter = new InvoiceCouplingAdapter(lstLv2, JoinInvoiceDialogActivity.this);
			lv2.setAdapter(adapter);
			break;
		// =================Join INV===============//
		case R.id.btnJoinInv:
			// xu ly ghep hoa don o day
			// cong cac inv_detail khac nhau thuoc list hoa don can ghep lai
			// lay invoice tu list cac invoice can ghep
			// insert invoice moi
			// insert cac invoice detail moi

			// ==============add invoice to server================= //
			if (lstLv2.size() > 1) {
				Invoice newInv = generateInvoice(lstLv2);
				new WSAddNewInvoice(JoinInvoiceDialogActivity.this, newInv.getInv_code(), 1, MainPosActivity.user_id, newInv.getParent_inv(), newInv.getInv_type()).execute();
				// ===============add lst InvDetail to server===========//
				ArrayList<Invoice_Detail> lstInvDetailTmp = joinInvoice(lstLv2, lstInvDetail);

				MainPosActivity.inv_code = newInv.getInv_code();
				new WSAddNewInvDetail(JoinInvoiceDialogActivity.this, newInv.getInv_code(), lstInvDetailTmp).execute();

				// ------- add xong cap nhat lai lstinv status = 0--------//
				for (Invoice invoice : lstLv2) {
					new WSStatusInvoice(JoinInvoiceDialogActivity.this, invoice.getInv_code(), 0, 0, 0, 0, 2).execute();
					new WSUpdateJoinInv(JoinInvoiceDialogActivity.this, invoice.getInv_code(), newInv.getInv_code()).execute();
				}

				// ==========call back inv_detail================//
				InvoicePosFragment.lstinv_code.clear();
			} else {
				Toast.makeText(JoinInvoiceDialogActivity.this,getResources().getString(R.string.choose2table), Toast.LENGTH_SHORT).show();

			}

			break;
		// =================Cancel Join===============//
		case R.id.btnCancel:
//			Toast.makeText(getBaseContext(), "Thoat", Toast.LENGTH_SHORT)
//					.show();
			finish();
			break;
		}
	}

	// ==========method generate an invoice================//
	private Invoice generateInvoice(List<Invoice> lstInvoice) {
		Invoice inv = new Invoice();
		String invName = "_";
		String parent_inv = "_";
		for (Invoice invoice : lstInvoice) {
			invName = invName + invoice.getInv_code().substring(0, 6);
			parent_inv = parent_inv + invoice.getInv_code() + ",";
		}
		String simpDate = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		inv.setInv_code(invName.substring(1, invName.length()) + simpDate);
		inv.setCost(0);
		inv.setTotal(0);
		inv.setStatus(1);
		inv.setInv_type(2);// join invoice
		inv.setParent_inv(parent_inv.substring(0, parent_inv.length() - 1));
		inv.setUser_id(MainPosActivity.user_id);
		return inv;
	}

	// ===========method return lstIvndetail ===============//
	private List<Invoice_Detail> getLstInvDetail(String inv_code,
			List<Invoice_Detail> lstInvDetail) {
		List<Invoice_Detail> lstInvDetailTmp = new CopyOnWriteArrayList<Invoice_Detail>();
		for (Invoice_Detail invDetail : lstInvDetail) {
			if (invDetail.getInv_code().equals(inv_code)) {
				lstInvDetailTmp.add(invDetail);
			}
		}
		return lstInvDetailTmp;
	}

	// ===========method sum total invoice_detail ===========//
	private ArrayList<Invoice_Detail> joinInvoice(List<Invoice> lstInvoice, List<Invoice_Detail> lstInvDetail) {
		ArrayList<Invoice_Detail> lstInvTmp = new ArrayList<Invoice_Detail>();
		for (Invoice invoice : lstInvoice) {
			lstInvTmp.addAll(getLstInvDetail(invoice.getInv_code(), lstInvDetail));
		}
		return lstInvTmp;
	}

	// ==========method get all invoice============================//
	private void getInvoice() {
		try {
			if (listInvTmp != null)
				listInvTmp.clear();
			JSONObject json = new JSONObject();
			// ---------------get String ------------------------//
			String URLGetAllInvoice = ConfigurationServer.getURLServer()
					+ "wsgetallinvoice.php";
			json.put("totalItemCount", "1");
			JSONArray arrInvoice = mWS.connectWSPut_Get_Data(URLGetAllInvoice,
					json, "invoice");

			for (int i = 0; i < arrInvoice.length(); i++) {
				JSONObject results = arrInvoice.getJSONObject(i);

				Log.i("Log : ", "Results : " + results);

				Invoice invoice = new Invoice();

				invoice.setInv_id(results.getInt("inv_id"));
				invoice.setInv_code(results.getString("inv_code"));
				invoice.setTotal(Float.parseFloat(results.getString("total")));
				invoice.setCost(Float.parseFloat(results.getString("cost")));
				invoice.setVat(Integer.parseInt(results.getString("vat")));
				invoice.setCommision(Integer.parseInt(results
						.getString("commision")));
				invoice.setInv_starttime(results.getString("inv_endtime"));
				invoice.setInv_endtime(results.getString("inv_starttime"));
				invoice.setUser_id(results.getInt("user_id"));
				invoice.setStatus(results.getInt("status"));
				listInvTmp.add(invoice);
			}

		} catch (Exception e) {
			Log.i("Log : ", "Exception : " + e.getMessage());
		}
	}

	// ================get (Invoice detail) from server==================//
	public void getAllInvDetail() {
		if (lstInvDetail != null)
			lstInvDetail.clear();
		try {
			// ---------------get String ------------------------//
			String URLGetAllInvdetail = ConfigurationServer.getURLServer()
					+ "wsgetallinvdetail.php";

			JSONObject json = new JSONObject();
			json.put("cate_id", 0);
			json.put("language_code", ConfigurationServer.language_code);

			JSONArray arrItem = mWS.connectWSPut_Get_Data(URLGetAllInvdetail,
					json, "invoicedetails");

			for (int i = 0; i < arrItem.length(); i++) {
				JSONObject results = arrItem.getJSONObject(i);

				Log.i("Log : ", "Results : " + results);
				Invoice_Detail inv_detail = new Invoice_Detail();
				inv_detail.setInv_code(results.getString("inv_code"));
				inv_detail.setStart_date(results.getString("start_date"));
				inv_detail.setEnd_date(results.getString("end_date"));
				inv_detail.setName(results.getString("name"));
				inv_detail.setDescription(results.getString("description"));
				inv_detail.setQuantity(results.getInt("quantity"));
				inv_detail
						.setPrice(Float.parseFloat(results.getString("price")));
				inv_detail.setItem_id(results.getInt("item_id"));
				try {
					inv_detail.setImgName(results.getString("imagename"));
				} catch (Exception e) {

				}
				inv_detail.setComment(results.getString("comment"));
				lstInvDetail.add(inv_detail);
			}

		} catch (Exception e) {
			Log.i("Log : ", "Exception : " + e.getMessage());
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		lstLv1.clear();
		lstLv2.clear();
	}

	// ============ get all Owner ==============
	class WSGetInvoice extends AsyncTask<Void, Void, Void> {
		private ProgressDialog dialog;

		public WSGetInvoice(Context mcontext) {
			super();
			dialog = new ProgressDialog(mcontext);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.setMessage("Loading...");
			dialog.setCancelable(false);
			dialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			getInvoice();
			getAllInvDetail();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			lstLv1 = getInvoiceByStatus(listInvTmp);
			adapter = new InvoiceCouplingAdapter(lstLv1, JoinInvoiceDialogActivity.this);
			lv1.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
		}
	}
	*//**
	 *  Vu: Tao ham lay tat ca data voi status bang 1 (chua thanh toan)
	 * @param lstLv1
	 * @return lstInvByStatus
	 *//*
	public List<Invoice> getInvoiceByStatus (List<Invoice> listInvoice){
		List<Invoice> lstInvByStatus = new CopyOnWriteArrayList<Invoice>();
		for (Invoice inv : listInvoice) {
			if(inv.getStatus()== 1){
				lstInvByStatus.add(inv);
			}
		}
		return lstInvByStatus;
		
		
	}

}
*/