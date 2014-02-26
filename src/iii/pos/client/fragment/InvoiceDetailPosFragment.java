package iii.pos.client.fragment;

import iii.pos.client.R;
import iii.pos.client.activity.MainPosActivity;
import iii.pos.client.adapter.InvoiceDetailAdapter;
import iii.pos.client.library.FormatFloorTableName;
import iii.pos.client.model.Invoice_Detail;
import iii.pos.client.server.ConfigurationServer;
import iii.pos.client.server.ConfigurationWS;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class InvoiceDetailPosFragment extends Fragment {

	/*------------------fields----------------------*/
	private static TextView tvTitle;
	private TextView textVAT;
	private TextView text_total;
	//private static ImageView btn_thanhtoan;
	private static ListView invoiceList;
	private TextView text_commition;
	private static ImageView btnAddInvDetail;
	private ImageView img_back;
	private ImageView btnAddnewSearch;
	private static Spinner spinVAT;
	private static Spinner spinCommition;
	private EditText edtSearchfast, edtItemCode, edtItemCount, edtSearchAmount;
	private LinearLayout lnSearchFast;
	private Button btnAddInvDetail1;
	
	private static boolean enable;

	private String code_table;
	private String inv_code;
	private int vattmp;
	private int commitiontmp;
	private boolean status = false;

	//private ConfigurationDB mDB;
	private IAddMenu iaddMenu;
	private Context context;

	public ArrayList<Invoice_Detail> lstInvDetail;

	private static InvoiceDetailAdapter adapterInvoice_Item;

	public InvoiceDetailPosFragment() {
	}

	/*-------------Constructor----------------------------*/
	public InvoiceDetailPosFragment(boolean status, String inv_code, String code_table, ArrayList<Invoice_Detail> listItems, int vat, int committion) {
		this.status = status;
		//this.status = false;
		this.vattmp = vat;
		this.inv_code = inv_code;
		this.code_table = code_table;
		this.commitiontmp = committion;
		this.lstInvDetail = listItems;
	}

	@Override
	public void onAttach(Activity arg0) {
		super.onAttach(arg0);
		try {
			iaddMenu = (IAddMenu) arg0;
		} catch (Exception e) {
		}
	}

	// ------------------------callback from other fragment------------------
	public interface IAddMenu {
		public void addMenuActivity(String inv_code);
	}

	// -------------creating context-------------//
	@Override
	public void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		context = getActivity().getApplicationContext();
	}

	/*------------initialize view----------------------*/
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View invoiceLayout = inflater.inflate(R.layout.invoice_detail_activiy, container, false);
		// -----------------------------------------------//
		//mDB = new ConfigurationDB(context);
		// ---------Event Add-----------------//
		btnAddInvDetail = (ImageView) invoiceLayout.findViewById(R.id.btnAddInvDetail);
		btnAddnewSearch = (ImageView) invoiceLayout.findViewById(R.id.btnAddnewSearch);

		// ----------------add inv_detail-----------------------//
		/*
		 * edtItemCode = (EditText)
		 * invoiceLayout.findViewById(R.id.edtItemCode); edtItemCount =
		 * (EditText) invoiceLayout.findViewById(R.id.edtItemCount);
		 * 
		 * btnAddInvDetail1 = (Button) invoiceLayout
		 * .findViewById(R.id.btnAddInvDetail1);
		 * btnAddInvDetail1.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { String itemCode =
		 * edtItemCode.getText().toString(); String itemCount =
		 * edtItemCount.getText().toString(); //
		 * ----------------------------------------------//
		 * 
		 * } });
		 */

		btnAddInvDetail.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (new ConfigurationServer(context).isOnline()) {
					iaddMenu.addMenuActivity(inv_code);
					Toast.makeText(context, "inv_code:"+inv_code, Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(context, "Network not found", Toast.LENGTH_SHORT).show();
				}
			}
		});
		img_back = (ImageView) invoiceLayout.findViewById(R.id.img_back);

		lnSearchFast = (LinearLayout) invoiceLayout .findViewById(R.id.lnedtsearch);
		edtSearchAmount = (EditText) invoiceLayout .findViewById(R.id.edtSearchAmount);
		textVAT = (TextView) invoiceLayout.findViewById(R.id.textVAT);
		textVAT.setText(String.format("%.2f", vatTotal(0)));
		edtSearchfast = (EditText) invoiceLayout .findViewById(R.id.edtSearchFast);
		text_commition = (TextView) invoiceLayout .findViewById(R.id.text_commition);
		try {
			text_commition.setText(String.format("%.2f", commitTotal(0)));
		} catch (Exception e) { }

		text_total = (TextView) invoiceLayout.findViewById(R.id.text_total);

		try {
			text_total.setText(String.format("%.2f", totalCost()) + " $");
		} catch (Exception e) { }
		spinVAT = (Spinner) invoiceLayout.findViewById(R.id.spinVAT);
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				getActivity(), R.array.spnVat,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinVAT.setAdapter(adapter);
		spinVAT.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				displayResult();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		spinCommition = (Spinner) invoiceLayout .findViewById(R.id.spinCommition);
		ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
				getActivity(), R.array.spnComposition,
				android.R.layout.simple_spinner_item);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinCommition.setAdapter(adapter1);
		setSpinner();
		spinCommition .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				displayResult();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		invoiceList = (ListView) invoiceLayout .findViewById(R.id.listViewInvoiceDetail);
		invoiceList.setEnabled(true);
		tvTitle = (TextView) invoiceLayout.findViewById(R.id.tvTitle);
		// Đổi lại tên invoice ko để là T1_B1_08987876
		// Đổi thành HD Bàn (B1) Tầng 1
		String mCodeTable = new FormatFloorTableName().formatNameInvoice(code_table);
		tvTitle.setText( mCodeTable );
		/*tvTitle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tvTitle.setVisibility(View.GONE);
				edtSearchAmount.setVisibility(View.VISIBLE);
				lnSearchFast.setVisibility(View.VISIBLE);

				img_back.setVisibility(View.VISIBLE);
				//btn_thanhtoan.setVisibility(View.GONE);
				btnAddnewSearch.setVisibility(View.VISIBLE);
				btnAddInvDetail.setVisibility(View.GONE);

			}
		});*/

		btnAddnewSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					int txtAmount = Integer.parseInt(edtSearchAmount.getText() .toString());
					int txtItemId = Integer.parseInt(edtSearchfast.getText() .toString());
					String inv_code = String.valueOf(code_table);

					Toast.makeText( getActivity(), "==>" + txtAmount + "  " + txtItemId + "   " + inv_code, 1).show();
					if (txtAmount > 0) {
						new WSInsertInvoiceDetail(inv_code, txtItemId, txtAmount, getActivity()).execute();
					} else {
						Toast.makeText(getActivity(), getResources().getString(R.string.noticeamount), 1).show();
					}
					edtSearchAmount.setText("");
					edtSearchfast.setText("");
				} catch (Exception e) { }
			}
		});
		/*if (this.status) {
			
		}else{
			// Vu: set enable cac view voi status = false
			enableLinnear(this.status);
		}*/
		img_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tvTitle.setVisibility(View.VISIBLE);
				edtSearchAmount.setVisibility(View.GONE);
				lnSearchFast.setVisibility(View.GONE);

				img_back.setVisibility(View.GONE);
				//btn_thanhtoan.setVisibility(View.VISIBLE);
				btnAddnewSearch.setVisibility(View.GONE);
				btnAddInvDetail.setVisibility(View.VISIBLE);

			}
		});
		/*btn_thanhtoan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// ------update status invo ice, itable to server------//
				// new Payment(context, inv_code, code_table).execute();
				// ------update status invoice, itable to Sqlite------//
				mDB.OpenDB();
				// mDB.updateInvoiceItable(inv_code, code_table);
				// mDB.Update(inv_code);
				mDB.closeDB();
				// ------update status invoice, itable to Bean-------//
				// MainPosActivity.beanDataAll.makeDataInvoice();
				// MainPosActivity.beanDataAll.makeDataTable();
				startActivity(new Intent(context, PrintInvoiceActivity.class)
						.putExtra("inv_code", inv_code).putExtra("vat", vat)
						.putExtra("commision", commition));
				lstInvDetail.clear();
				refreshAdapter();
			}
		});*/
		setAdapter(this.status);
		return invoiceLayout;
	}
	public void enableLinnear(boolean enable){
			Log.d("ENABLE", "FALSE");
			//btn_thanhtoan.setEnabled(enable);
			tvTitle.setEnabled(enable);
			btnAddInvDetail.setEnabled(enable);
			invoiceList.setEnabled(enable);
			spinVAT.setEnabled(enable);
			spinCommition.setEnabled(enable);
		}
	
	// -------------create adapter----------------------------------//
	public void setAdapter(boolean status) {
		try {
			adapterInvoice_Item = new InvoiceDetailAdapter(status, context,
					InvoiceDetailPosFragment.this, getActivity(),
					R.layout.invoice_detail_activiy, lstInvDetail);
			if (invoiceList != null) {
				invoiceList.setAdapter(adapterInvoice_Item);
			}
			adapterInvoice_Item.notifyDataSetChanged();
		} catch (Exception e) {
			// TODO: handle exception
		}
		

	}

	// =============this method to refresh inv_code from other activity=======//
	public void refreshInvdetail(String inv_code, int status) {
		if (status == 0) {
			lstInvDetail.clear();
			refreshAdapter();
		} else {
			new WSGetAnInvDetail(inv_code).execute();
		}
	}

	// --------------return total vat-------------------------------//
	private float vatTotal(int vat) {
		return (totalCost() * vat) / 100;
	}

	// --------------return total commit-----------------------------//
	private float commitTotal(int commit) {
		return (totalCost() * commit) / 100;
	}

	// --------------return total Cost-------------------------------//
	private float totalCost() {
		float total = 0;
		try {
			for (Invoice_Detail invDetail : lstInvDetail) {
				total += invDetail.getPrice() * invDetail.getQuantity();
			}
		} catch (Exception e) {

		}
		return total;
	}

	int commition = 0;
	int vat = 0;

	public void displayResult() {
		vat = Integer.parseInt( String.valueOf(spinVAT.getSelectedItem()));
		textVAT.setText( formatDecimal(vatTotal(vat)) + context.getString(R.string.curr));
		commition = Integer.parseInt( String.valueOf(spinCommition .getSelectedItem()));
		text_commition.setText( formatDecimal(commitTotal(commition)) + context.getString(R.string.curr));
		text_total.setText( formatDecimal(totalCost() + vatTotal(vat) - commitTotal(commition)) + context.getString(R.string.curr));
	}

	private String formatDecimal(double number) {

		DecimalFormat nf = new DecimalFormat("###,###,###,##0");
		String formatted = nf.format(number);

		return formatted;
	}

	// -------------refresh adapter----------------------------------//
	public void refreshAdapter() {
		if (adapterInvoice_Item != null) {
			adapterInvoice_Item.notifyDataSetChanged();
		}

	}

	private void setSpinner() {

		if (vattmp == 0) {
			spinVAT.setSelection(0);
		}
		if (vattmp == 5) {
			spinVAT.setSelection(1);
		}
		if (vattmp == 10) {
			spinVAT.setSelection(2);
		}
		if (vattmp == 15) {
			spinVAT.setSelection(3);
		}
		if (commitiontmp == 0) {
			spinCommition.setSelection(0);
		}
		if (commitiontmp == 5) {
			spinCommition.setSelection(1);
		}
		if (commitiontmp == 10) {
			spinCommition.setSelection(2);
		}
		if (commitiontmp == 15) {
			spinCommition.setSelection(3);
		}
		if (commitiontmp == 20) {
			spinCommition.setSelection(3);
		}
	}

	// ----------Threading make data invoice_detail by invcode-------//
	private class WSGetAnInvDetail extends AsyncTask<Void, Void, Void> {

		private ProgressDialog progressDialog;
		private ConfigurationWS mWS;
		private String inv_code = "";

		public WSGetAnInvDetail(String invcode) {
			this.inv_code = invcode;
			mWS = new ConfigurationWS(getActivity());
			progressDialog = new ProgressDialog(getActivity());
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.setMessage("Loading...");
			progressDialog.setCancelable(false);
			progressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				lstInvDetail.clear();
				ArrayList<Invoice_Detail> lstInvDetailTmpl = new ArrayList<Invoice_Detail>();
				// ---------------get String ------------------------//
				String URLGetAllInvdetail = ConfigurationServer.getURLServer()
						+ "wsgetanallinvdetail.php";

				JSONObject json = new JSONObject();
				json.put("inv_code", inv_code);
				json.put("language_code", ConfigurationServer.language_code);

				JSONArray arrItem = mWS.connectWSPut_Get_Data(
						URLGetAllInvdetail, json, "aninvdetail");

				for (int i = 0; i < arrItem.length(); i++) {
					JSONObject results = arrItem.getJSONObject(i);

					Log.i("Log : ", "Results : " + results);
					Invoice_Detail inv_detail = new Invoice_Detail();
					inv_detail.setInv_code(results.getString("inv_code"));
					inv_detail.setId(results.getInt("id"));
					inv_detail.setStart_date(results.getString("start_date"));
					inv_detail.setEnd_date(results.getString("end_date"));
					inv_detail.setName(results.getString("name"));
					inv_detail.setDescription(results.getString("description"));
					inv_detail.setQuantity(results.getInt("quantity"));
					inv_detail.setPrice(Float.parseFloat(results
							.getString("price")));
					inv_detail.setItem_id(results.getInt("item_id"));
					inv_detail.setImgName(results.getString("imagename"));
					inv_detail.setComment(results.getString("comment"));
					inv_detail.setChecked(results.getInt("checked"));
					inv_detail.setStart_date(results.getString("invd_createtime"));
					inv_detail.setEnd_date(results.getString("invd_updatetime"));
					lstInvDetailTmpl.add(inv_detail);
				}
				lstInvDetail.addAll(MainPosActivity.beanDataAll
						.getLstInvDetailSort1(lstInvDetailTmpl));
			} catch (Exception e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			setAdapter(true);
			if (progressDialog != null)
				progressDialog.dismiss();

		}

	}

	// ----------------------------Insert data----------------------------//

	private class WSInsertInvoiceDetail extends AsyncTask<Void, Void, Void> {
		private String inv_code;
		private int inv_item;
		private int quantity;
		private Context context;
		public int invdeltail_id = -1;
		private ConfigurationWS mWS_Insert;
		private ProgressDialog progressDialog;

		public WSInsertInvoiceDetail(String inv_code, int inv_item,
				int quantity, Context mcontext) {
			super();
			this.inv_code = inv_code;
			this.inv_item = inv_item;
			this.quantity = quantity;
			this.context = mcontext;
			mWS_Insert = new ConfigurationWS(mcontext);
			progressDialog = new ProgressDialog(mcontext);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.setMessage("Loading...");
			progressDialog.setCancelable(false);
			progressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				//____ hai sua ca webservice____________
				lstInvDetail.clear();
				String URL = ConfigurationServer.getURLServer()
						+ "wsaddannewinvoicedetail1.php";
				JSONObject json = new JSONObject();
				json.put("inv_code", inv_code);
				json.put("inv_item", inv_item);
				json.put("quantity", quantity);
				json.put("language_code", ConfigurationServer.language_code);
				//JSONArray arrItem = new JSONArray();
				mWS_Insert.connectWSPut_Get_Data(URL, json, "posts");
		/*		if (arrItem != null) {
					if (arrItem.length() > 0) {
						for (int i = 0; i < arrItem.length(); i++) {
							JSONObject results = arrItem.getJSONObject(i);

							Invoice_Detail inv_detail = new Invoice_Detail();
							inv_detail.setInv_code(results
									.getString("inv_code"));
							inv_detail.setId(results.getInt("id"));
							inv_detail.setStart_date(results
									.getString("start_date"));
							inv_detail.setEnd_date(results
									.getString("end_date"));
							inv_detail.setName(results.getString("name"));
							inv_detail.setDescription(results
									.getString("description"));
							inv_detail.setQuantity(results.getInt("quantity"));
							inv_detail.setPrice(Float.parseFloat(results
									.getString("price")));
							inv_detail.setItem_id(results.getInt("item_id"));
							try {
								inv_detail.setImgName(results
										.getString("imagename"));
							} catch (Exception e) {
							}
							inv_detail.setComment(results.getString("comment"));
							MainPosActivity.beanDataAll.lstInvDetail
									.add(inv_detail);

						}
//						Toast.makeText(getActivity(), "Success", 1).show();
					} else {
//						Toast.makeText(getActivity(),
//								"Dont have an item found", 1).show();
					}
				}*/
				MainPosActivity.beanDataAll.makeDataInvDetail();
			} catch (Exception e) {

			}
			return null;

		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			lstInvDetail = MainPosActivity.beanDataAll.getlstInvDetail(inv_code);

			setAdapter(false);
			displayResult();
			try {
				progressDialog.dismiss();
			} catch (Exception e) {

			}
		}
	}

}