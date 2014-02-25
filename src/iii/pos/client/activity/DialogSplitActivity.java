/*package iii.pos.client.activity;

import iii.pos.client.R;
import iii.pos.client.adapter.DialogSplitAdapter;
import iii.pos.client.fragment.InvoicePosFragment;
import iii.pos.client.model.Invoice_Detail;
import iii.pos.client.model.Invoice_DetailTmpl;
import iii.pos.client.server.ConfigurationServer;
import iii.pos.client.server.ConfigurationWS;
import iii.pos.client.wsclass.WSAddInvTable;
import iii.pos.client.wsclass.WSAddNewInvoice;
import iii.pos.client.wsclass.WSUpdateItableStatus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DialogSplitActivity extends Activity implements OnClickListener {

	private ImageButton btnDiaSplitOK, btnDiaSplitCancel;
	private ListView lvDiaSplit1, lvDiaSplit2;
	private List<Invoice_Detail> listInvDetail1, listInvDetail2;
	private List<Invoice_DetailTmpl> lstInvDetailTmpl;
	private TextView tvDiaSplitTitle, tvDiaSplitTitleLV1, tvDiaSplitTitleLV2;
	private EditText edtTotal1, edtTotal2;
	private DialogSplitAdapter adapter;
	private ConfigurationWS mWS;
	private String inv_code = "";
	private String newInv_code = "New_INV";
	// --------choose table -----------
	private ImageButton btnDiaSplitAdd, btnDiaSplitRemove;
	private Button btnDiaSplitChooseTableOK, btnDiaSplitChooseTableCancel;
	private GridView gvDiaSplitChooseTable;
	private ArrayList<String> itemTable = new ArrayList<String>();

	// ------------------------------------------------------
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialogsplit);
		Bundle bl = new Bundle();
		bl = getIntent().getExtras();
		if (bl != null) {
			inv_code = bl.getString("inv_code");
			itemTable = bl.getStringArrayList("table_code");
		}
		mWS = new ConfigurationWS(DialogSplitActivity.this);
		init();
		makeData(inv_code);
	}

	// ------------------------------------------------------
	private void init() {
		btnDiaSplitAdd = (ImageButton) findViewById(R.id.btnDiaSplitAdd);
		btnDiaSplitRemove = (ImageButton) findViewById(R.id.btnDiaSplitRemove);
		btnDiaSplitOK = (ImageButton) findViewById(R.id.btnDiaSplitOK);
		btnDiaSplitCancel = (ImageButton) findViewById(R.id.btnDiaSplitCancel);
		tvDiaSplitTitle = (TextView) findViewById(R.id.tvDiaSplitTitle);
		tvDiaSplitTitleLV1 = (TextView) findViewById(R.id.tvDiaSplitTitleLV1);
		tvDiaSplitTitleLV1.setText(inv_code);

		tvDiaSplitTitleLV2 = (TextView) findViewById(R.id.tvDiaSplitTitleLV2);
		final String simpDate = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		newInv_code = genTableCode(itemTable);
		tvDiaSplitTitleLV2.setText(newInv_code);
		lvDiaSplit1 = (ListView) findViewById(R.id.lvDiaSplit1);
		lvDiaSplit2 = (ListView) findViewById(R.id.lvDiaSplit2);
		edtTotal1 = (EditText) findViewById(R.id.editText1);
		edtTotal2 = (EditText) findViewById(R.id.editText2);
		listInvDetail1 = new CopyOnWriteArrayList<Invoice_Detail>();
		listInvDetail2 = new CopyOnWriteArrayList<Invoice_Detail>();
		lstInvDetailTmpl = new CopyOnWriteArrayList<Invoice_DetailTmpl>();
		btnDiaSplitAdd.setOnClickListener(this);
		btnDiaSplitRemove.setOnClickListener(this);
		btnDiaSplitOK.setOnClickListener(this);
		btnDiaSplitCancel.setOnClickListener(this);
	}

	// ------------------------------------------------------
	private void makeData(String inv_code) {
		new WSGetInvoice(DialogSplitActivity.this, inv_code).execute();
	}

	// ------------------------------------------------------
	private void refeshAdapter1() {
		// Loại bỏ những món ăn có số lượng = 0 trước khi tách HD
		for (Invoice_Detail invDetail : listInvDetail1) {
			if( invDetail.getQuantity() <= 0 ){
				listInvDetail1.remove(invDetail);
			}
		}
		adapter = new DialogSplitAdapter(listInvDetail1, lstInvDetailTmpl, DialogSplitActivity.this);
		lvDiaSplit1.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		edtTotal1.setText(String.valueOf(totalInv(listInvDetail1)));
	}

	// ------------------------------------------------------
	private void refeshAdapter2() {
		adapter = new DialogSplitAdapter(listInvDetail2, lstInvDetailTmpl, DialogSplitActivity.this);
		lvDiaSplit2.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		edtTotal2.setText(String.valueOf(totalInv(listInvDetail2)));
	}

	// ==========action ==============================//s
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnDiaSplitAdd:
			diaSplitAdd();
			break;
		case R.id.btnDiaSplitRemove:
			diaSplitRemove();
			break;
		case R.id.btnDiaSplitOK:
			// xu ly update two inv here
			if (listInvDetail1.size() > 0 && listInvDetail2.size() > 0) {
				splitInv(inv_code, listInvDetail1, newInv_code, listInvDetail2);
			}else {
				Toast.makeText(DialogSplitActivity.this,getResources().getString(R.string.cantspilit), 1).show();
			}

			finish();
			break;
		case R.id.btnDiaSplitCancel:
			finish();
			break;
		}

	}

	// =========gen inv_code from list table_code==================//
	private String genTableCode(ArrayList<String> arr) {
		String table_code = "";
		String simpDate = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		for (String string : arr) {
			table_code = table_code + string;
		}
		return table_code + "_" + simpDate;
	}

	// ==========method to update two invoice here=====//
	private void splitInv(String inv1, List<Invoice_Detail> lstInvoiceOld, String inv2, List<Invoice_Detail> lstInvoiceNew) {

		ArrayList<Invoice_Detail> arrInvoiceOld = new ArrayList<Invoice_Detail>();
		ArrayList<Invoice_Detail> arrInvoiceNew = new ArrayList<Invoice_Detail>();
		for (Invoice_Detail invoice_Detail : lstInvoiceOld) {
			arrInvoiceOld.add(invoice_Detail);
		}
		for (Invoice_Detail invoice_Detail : lstInvoiceNew) {
			arrInvoiceNew.add(invoice_Detail);
		}
		insertNewInv(inv1, inv2);
		updateInvdetail1(inv1, arrInvoiceNew); // Update số lượng invoice cũ bằng cách đưa số lượng invoice mới lên và chèn slg là âm
		updateInvdetail2(inv2, arrInvoiceNew); // Add new số lượng invoice mới
		InvoicePosFragment.lstinv_code.clear();
	}

	// ========method add an new invoice that have just splited==//
	private synchronized void insertNewInv(String inv1, String inv2) {
		new WSAddNewInvoice(DialogSplitActivity.this, inv2, 1, MainPosActivity.user_id, inv1, 3).execute();
		new WSAddInvTable(DialogSplitActivity.this, inv2, MainPosActivity.user_id, itemTable).execute();
		new WSUpdateItableStatus(DialogSplitActivity.this, 2, MainPosActivity.user_id, itemTable).execute();
	}

	// ==============update lst inv_detail 1 ===========//
	private synchronized void updateInvdetail1(String inv1,
			List<Invoice_Detail> lst1) {
		// step one delete all
		// step two insert
		// --------insert invoice_detail to server--------------//
		new WSSplitInvoice1(DialogSplitActivity.this, inv1, lst1).execute();
	}

	// ==============update lst inv_detail 2 ===========//
	private synchronized void updateInvdetail2(String inv2,
			List<Invoice_Detail> lst2) {
		// insert new invoice
		MainPosActivity.inv_code = inv2;
		new WSSplitInvoice2(DialogSplitActivity.this, inv2, lst2).execute();
	}

	// =========method counting total inv==========//
	private float totalInv(List<Invoice_Detail> lstInv) {
		float total = 0;
		for (Invoice_Detail invoice_Detail : lstInv) {
			total = total + invoice_Detail.getQuantity() * invoice_Detail.getPrice();
		}
		return total;
	}

	// --------------Add------------------------------//
	
	//day la ham add so luong thang 1 vao thang hai thi phai
	private void diaSplitAdd() {
		for (Invoice_Detail inv : listInvDetail1) {
			if (inv.isTranferCheck()) {
				inv.setTranferCheck(false);
				int amount = check(inv, lstInvDetailTmpl);
				if (amount == 0) {
					amount = 1;
				}
				if (amount == inv.getQuantity()) {
					listInvDetail1.remove(inv);
				}
				inv.setQuantity(inv.getQuantity() - amount);
				Invoice_Detail newinvdetail = returnInvDetail(inv, amount);
				Invoice_Detail tmpl = checkInvDetail(newinvdetail, listInvDetail2);
				if (tmpl == null) {
					listInvDetail2.add(newinvdetail);
				} else {
					newinvdetail.setQuantity(newinvdetail.getQuantity() + tmpl.getQuantity());
					listInvDetail2.remove(tmpl);
					listInvDetail2.add(newinvdetail);
				}
			}
		}
		lstInvDetailTmpl.clear();
		refeshAdapter2();
		refeshAdapter1();
	}

	// --------------Remove------------------------------//
	//day la ham nho? thang 1 di khi chọn
	private void diaSplitRemove() {
		for (Invoice_Detail inv : listInvDetail2) {
			if (inv.isTranferCheck()) {
				inv.setTranferCheck(false);
				int amount = check(inv, lstInvDetailTmpl);
				if (amount == 0) {
					amount = 1;
				}
				if (amount == inv.getQuantity()) {
					listInvDetail2.remove(inv);
				}
				inv.setQuantity(inv.getQuantity() - amount);
				Invoice_Detail newinvdetail = returnInvDetail(inv, amount);
				Invoice_Detail tmpl = checkInvDetail(newinvdetail,
						listInvDetail1);
				if (tmpl == null) {
					listInvDetail1.add(newinvdetail);
				} else {
					newinvdetail.setQuantity(newinvdetail.getQuantity()
							+ tmpl.getQuantity());
					listInvDetail1.remove(tmpl);
					listInvDetail1.add(newinvdetail);
				}
			}
		}
		lstInvDetailTmpl.clear();
		refeshAdapter1();
		refeshAdapter2();
	}

	// ====================return invdetail =============================//
	private Invoice_Detail returnInvDetail(Invoice_Detail inv, int amount) {
		Invoice_Detail inv_detail = new Invoice_Detail();
		inv_detail.setInv_code(inv.getInv_code());
		inv_detail.setQuantity(amount);
		inv_detail.setComment(inv.getComment());
		inv_detail.setChecked(inv.getChecked());
		inv_detail.setFlag(inv.getFlag());
		inv_detail.setStart_date(inv.getStart_date());
		inv_detail.setEnd_date(inv.getEnd_date());
		inv_detail.setName(inv.getName());
		inv_detail.setItem_id(inv.getItem_id());
		inv_detail.setPrice(inv.getPrice());
		return inv_detail;
	}

	// ==========neu ton tai inv_detail trong list thi cong them so luong==/
	//day la cong
	private Invoice_Detail checkInvDetail(Invoice_Detail inv,
			List<Invoice_Detail> arr) {
		for (Invoice_Detail inv_detail : arr) {
			if (inv.getItem_id() == inv_detail.getItem_id()) {
				return inv_detail;
			}
		}
		return null;
	}

	// ===================================================================//
	private int check(Invoice_Detail inv, List<Invoice_DetailTmpl> arr) {
		for (Invoice_DetailTmpl invoice_Detail : arr) {
			if (inv.getItem_id() == invoice_Detail.getItem_id()) {
				return invoice_Detail.getQuantity();
			}
		}
		return 0;
	}

	// ================get (Invoice detail) from server==================//
	public void getAnInvDetail(String inv_code) {
		if (listInvDetail1 != null)
			listInvDetail1.clear();
		try {
			// ===============get String =========================-//
			String URLGetAllInvdetail = ConfigurationServer.getURLServer()
					+ "wsgetanallinvdetail.php";

			JSONObject json = new JSONObject();
			json.put("inv_code", inv_code);
			json.put("language_code", ConfigurationServer.language_code);

			JSONArray arrItem = mWS.connectWSPut_Get_Data(URLGetAllInvdetail,
					json, "aninvdetail");

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
				listInvDetail1.add(inv_detail);

			}
			listInvDetail1 = totalInvDetail(listInvDetail1);
		} catch (Exception e) {
		}
	}

	// ============ function to sum quantity of inv_detail======//
	public List<Invoice_Detail> totalInvDetail(List<Invoice_Detail> arr) {
		List<Invoice_Detail> arrtmp = new CopyOnWriteArrayList<Invoice_Detail>();

		for (Invoice_Detail invoice_Detail : arr) {
			if (!checkinvDetail(invoice_Detail, arrtmp)) {
				arrtmp.add(invoice_Detail);
			}
		}
		return arrtmp;
	}

	// ============= checking inv_detail in lstinvdetail==========//
	private boolean checkinvDetail(Invoice_Detail inv, List<Invoice_Detail> lstINV) {
		for (Invoice_Detail invoice_Detail : lstINV) {
			if (inv.getItem_id() == invoice_Detail.getItem_id()) {
				// -------ghep chuoi-------------------------------------//
				invoice_Detail.setLstOrder(invoice_Detail.getLstOrder() + String.valueOf(inv.getQuantity() + "-"));
				invoice_Detail.setQuantity(invoice_Detail.getQuantity() + inv.getQuantity());
				return true;
			}
		}
		return false;
	}

	// =========destroy==============//
	@Override
	protected void onDestroy() {
		super.onDestroy();
		listInvDetail1.clear();
		listInvDetail2.clear();
	}

	// ============ get all Owner ==============
	class WSGetInvoice extends AsyncTask<Void, Void, Void> {
		private ProgressDialog dialog;

		private Context context;
		private String inv_code;

		public WSGetInvoice(Context mcontext, String inv_code) {
			super();
			this.context = mcontext;
			dialog = new ProgressDialog(mcontext);
			this.inv_code = inv_code;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.setTitle("Loading...");
			dialog.setCancelable(false);
			dialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			getAnInvDetail(inv_code);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			refeshAdapter1();
			if (dialog.isShowing()) {
				dialog.dismiss();
			}

		}
	}

	// ==================slpit inv===============================//
	*//**
	 * Hàm update lại số lượng món ăn của hóa đơn cũ.
	 * Trừ đi số lượng món ăn mà bị chuyển sang hóa đơn mới
	 * @author GIACNGO
	 *
	 *//*
	private class WSSplitInvoice1 extends AsyncTask<Void, Void, Void> {
		private String inv_code;
		private List<Invoice_Detail> lstInvDetail1;
		private ConfigurationWS mWS;

		// --------constructor-----------------------------//
		public WSSplitInvoice1(Context mContext, String inv_code,
				List<Invoice_Detail> lstInvdetail) {
			this.inv_code = inv_code;
			this.lstInvDetail1 = lstInvdetail;
			Log.d("MX", "SIIIIZZZE LIST LA " + lstInvDetail1.size());
			mWS = new ConfigurationWS(mContext);
		}

		// --------background method-------------------//
		@Override
		protected synchronized Void doInBackground(Void... params) {
			try {
				String URLAddnewItem = ConfigurationServer.getURLServer()
						+ "wssplitinvoice.php";

				int i = 0;
				Log.d("MX", "INVOICE:   " + this.inv_code + "   size " + lstInvDetail1.size());
				for (Invoice_Detail items : lstInvDetail1) {
					Log.d("MX", inv_code + "  ----->" + items.getItem_id() + "  quantity: " + items.getQuantity());
					JSONObject json = new JSONObject();
					json.put("inv_code", inv_code);
					json.put("item_id", items.getItem_id());// IIIPOS
					json.put("quantity", items.getQuantity());
					json.put("comment", items.getComment());
					json.put("subAdd", 1); // Gửi server để biết khi nào là trừ item, khi nào là add item
											// 1: trừ đi số lượng item của inv_code
					if (i == 0)
						json.put("flag", 1);
					else
						json.put("flag", 0);
					mWS.connectWS_Put_Data(URLAddnewItem, json);
					Log.i("CT: ", "PUT: " + items.getItem_id() + " inv_code: " + inv_code);
					i++;
				}

			} catch (Exception e) {
				Log.i("Log : ", "Insert INV Detail : " + e.getMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}

	}

	// ==================slpit inv===============================//
	*//**
	 * Hàm update lại số lượng món ăn của hóa đơn mới.
	 * Cộng thêm số lượng món ăn được chuyển sang từ hóa đơn cũ
	 * @author GIACNGO
	 *
	 *//*
	private class WSSplitInvoice2 extends AsyncTask<Void, Void, Void> {
		private String inv_code;
		private List<Invoice_Detail> lstInvDetail1;
		private ConfigurationWS mWS;

		// --------constructor-----------------------------//
		public WSSplitInvoice2(Context mContext, String inv_code,
				List<Invoice_Detail> lstInvdetail) {
			this.inv_code = inv_code;
			this.lstInvDetail1 = lstInvdetail;
			Log.d("MX", "SIIIIZZZE LIST LA " + lstInvDetail1.size());
			mWS = new ConfigurationWS(mContext);
		}

		// --------background method-------------------//
		@Override
		protected synchronized Void doInBackground(Void... params) {
			try {
				String URLAddnewItem = ConfigurationServer.getURLServer()
						+ "wssplitinvoice.php";

				int i = 0;
				Log.d("MX", "INVOICE:   " + this.inv_code + "   size "
						+ lstInvDetail1.size());
				for (Invoice_Detail items : lstInvDetail1) {

					Log.d("MX", inv_code + "  ----->" + items.getItem_id()
							+ "  quantity: " + items.getQuantity());

					JSONObject json = new JSONObject();
					json.put("inv_code", inv_code);
					json.put("item_id", items.getItem_id());// IIIPOS
					json.put("quantity", items.getQuantity());
					json.put("comment", items.getComment());
					json.put("subAdd", 2); // Gửi server để biết khi nào là trừ item, khi nào là add item
											// 2 : Cộng thêm số lượng Item vào inv_code
					if (i == 0)
						json.put("flag", 1);
					else
						json.put("flag", 0);
					mWS.connectWS_Put_Data(URLAddnewItem, json);
					Log.i("CT: ", "PUT: " + items.getItem_id() + " inv_code: "
							+ inv_code);
					i++;
				}

			} catch (Exception e) {
				Log.i("Log : ", "Insert INV Detail : " + e.getMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			Log.d("KT", inv_code);
			Intent returnIntent = getIntent();
			returnIntent.putExtra(MainPosActivity.INVOICE_CODE, inv_code);
			setResult(Activity.RESULT_OK, returnIntent);
			super.onPostExecute(result);
		}

	}

}*/