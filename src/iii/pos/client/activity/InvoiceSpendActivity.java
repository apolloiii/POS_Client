/*package iii.pos.client.activity;

import iii.pos.client.R;
import iii.pos.client.adapter.InvoiceSpendAdapter;
import iii.pos.client.adapter.SpinnerAdapte;
import iii.pos.client.model.Invoice_Spend;
import iii.pos.client.model.ListSpend;
import iii.pos.client.server.ConfigurationServer;
import iii.pos.client.server.ConfigurationWS;

import java.util.ArrayList;
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
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class InvoiceSpendActivity extends Activity implements OnClickListener {
	private EditText edtcodeInvoice, edtPriceInvoice, edtPayfor, edtnameCost,
			edtPurposePay;
	private Spinner spncosts, spnStatus, spnPersonPay;
	private Button btnAddInvoiceCost, btnUpdateInvoiceCost,
			btnDeleteInvoiceCost;
	private List<Invoice_Spend> listInvoiceSpend;
	private InvoiceSpendAdapter adapterInvoiceSpend;
	private ListView listview_costInvoice;
	private String priceInvoice, nameSpend, code, payfor, purpose,  personpay, status;
	private int id,  user_id, spend_id;
	private ImageButton imgBSpending;
	private ArrayList<ListSpend> listSpend = new ArrayList<ListSpend>();
	private ArrayList<Invoice_Spend> listStatus = new ArrayList<Invoice_Spend>();
	private ArrayList<Invoice_Spend> listUser = new ArrayList<Invoice_Spend>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.invoice_spend_layout);
		loadUI();
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}

	private void loadUI() {
		edtcodeInvoice = (EditText) findViewById(R.id.edtcodeInvoice);
		edtPriceInvoice = (EditText) findViewById(R.id.edtPriceInvoice);
		edtPayfor = (EditText) findViewById(R.id.edtPayfor);
		edtnameCost = (EditText) findViewById(R.id.edtnameCost);
		edtPurposePay = (EditText) findViewById(R.id.edtPurposePay);
		
		imgBSpending= (ImageButton)findViewById(R.id.imgBSpending);
		imgBSpending.setOnClickListener(this);

		spnPersonPay = (Spinner) findViewById(R.id.spnPersonPay);
		spncosts = (Spinner) findViewById(R.id.spncosts);

		spnStatus = (Spinner) findViewById(R.id.spnStatus);
		spnStatus.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				status = listStatus.get(arg2).getStatus();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		spncosts.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				spend_id = listSpend.get(arg2).getId();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		spnPersonPay.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				user_id = listUser.get(arg2).getUser_id();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		btnDeleteInvoiceCost = (Button) findViewById(R.id.btnDeleteInvoiceCost);
		btnDeleteInvoiceCost.setOnClickListener(this);

		btnAddInvoiceCost = (Button) findViewById(R.id.btnAddInvoiceCost);
		btnAddInvoiceCost.setOnClickListener(this);

		btnUpdateInvoiceCost = (Button) findViewById(R.id.btnUpdateInvoiceCost);
		btnUpdateInvoiceCost.setOnClickListener(this);
		

		listview_costInvoice = (ListView) findViewById(R.id.listview_costInvoice);
		loadData();
	}

	private void setDataSpinnerStatus() {
		ArrayList<String> list = new ArrayList<String>();
		for (Invoice_Spend invoice_Spend : listStatus) {
			String status = invoice_Spend.getStatus();
			list.add(status);
		}
		SpinnerAdapte adapter = new SpinnerAdapte(InvoiceSpendActivity.this, list);
		spnStatus.setAdapter(adapter);
	}

	private void loadData() {
		new WSgetDataArise(InvoiceSpendActivity.this).execute();
		
		
		listview_costInvoice.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				btnDeleteInvoiceCost.setEnabled(true);
				btnUpdateInvoiceCost.setEnabled(true);

				String code = listInvoiceSpend.get(pos).getCode();
				String nameCost = listInvoiceSpend.get(pos).getNameCost();
				String price_cost = listInvoiceSpend.get(pos).getPrice();
				String namereciver = listInvoiceSpend.get(pos).getPayFor();
				String purpose = listInvoiceSpend.get(pos).getPurposePay();
				id = listInvoiceSpend.get(pos).getId();
				String spnstatus = listInvoiceSpend.get(pos).getStatus();
				String personpay = listInvoiceSpend.get(pos).getPersonPay();
				String spending = listInvoiceSpend.get(pos).getCost();
				Toast.makeText(InvoiceSpendActivity.this, spending, 1).show();
				

				edtcodeInvoice.setText(code);
				edtnameCost.setText(nameCost);
				edtPriceInvoice.setText(price_cost);
				edtPayfor.setText(namereciver);
				edtPurposePay.setText(purpose);
//				spnStatus.setSelection(position)
//				spnStatus.setSelection(spnstatus);
//				spnPersonPay.setSelection(personpay);
			
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnAddInvoiceCost:
			checkInsert();
			break;
		case R.id.btnUpdateInvoiceCost:
			checkUpdate();
			break;
		case R.id.btnDeleteInvoiceCost:
			for (Invoice_Spend cost : listInvoiceSpend) {
				if (cost.isCheck()) {
					new WSDeleteInvoiceCost(cost.getId(),
							InvoiceSpendActivity.this).execute();
				}
			}
			break;
		case R.id.imgBSpending:
			startActivity(new Intent(InvoiceSpendActivity.this, ListSpendActivity.class));
			finish();
			break;


		default:
			break;
		}
	}

	// ---------------------------Get data----------------------------//

	private class WSgetdataInvoiceCost extends AsyncTask<Void, Void, Void> {
		private ProgressDialog progressDialog;
		private ConfigurationWS mWS;
		private Context context;

		public WSgetdataInvoiceCost(Context mcontext) {
			progressDialog = new ProgressDialog(mcontext);
			mWS = new ConfigurationWS(mcontext);
			this.context = mcontext;
			listInvoiceSpend = new CopyOnWriteArrayList<Invoice_Spend>();
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
				try {
					listInvoiceSpend.clear();
					// ---------------get String ------------------------//
					String URL = ConfigurationServer.getURLServer()
							+ "wsgetinvoicecostIII.php";
					JSONObject json = new JSONObject();
					JSONArray jarr = mWS.connectWSPut_Get_Data(URL, json,
							"posts");
					for (int i = 0; i < jarr.length(); i++) {
						JSONObject element = jarr.getJSONObject(i);
						Invoice_Spend invoice_Spend = new Invoice_Spend();
						invoice_Spend.setCode(element.getString("code"));
						invoice_Spend.setNameCost(element.getString("name"));
						invoice_Spend.setPrice(element.getString("price"));
						invoice_Spend.setPayFor(element.getString("name_reiceive"));
						invoice_Spend.setPurposePay(element.getString("note"));
						invoice_Spend.setPersonPay(element.getString("username"));
						invoice_Spend.setCost(element.getString("namearise"));
						// invoice_Cost.setCost(element.getString(name))
						invoice_Spend.setId(element.getInt("id"));
						invoice_Spend.setStatus(element.getString("status"));
						
						listInvoiceSpend.add(invoice_Spend);
					}
				} catch (Exception e) {
					Log.i("Log : ", "Exception : " + e.getMessage());
				}
			} catch (Exception e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			new WSgetDataExtra(InvoiceSpendActivity.this).execute();
			new WSgetDataUser(InvoiceSpendActivity.this).execute();
			setAdapter();
			
			if (progressDialog != null)
				progressDialog.dismiss();
		}

	}

	// -------------create adapter----------------------------------//
	public void setAdapter() {
		adapterInvoiceSpend = new InvoiceSpendAdapter(
				InvoiceSpendActivity.this, R.layout.invoicespend_item_layout,
				listInvoiceSpend);
		listview_costInvoice.setAdapter(adapterInvoiceSpend);
		adapterInvoiceSpend.notifyDataSetChanged();
	}

	// -----------------------check Insert--------------------//

	private void checkInsert() {
		priceInvoice = edtPriceInvoice.getText().toString();
		nameSpend = edtnameCost.getText().toString();
		code = edtcodeInvoice.getText().toString();
		payfor= edtPayfor.getText().toString().trim();
		purpose = edtPurposePay.getText().toString().trim();
		Log.d("LOG", "" + status + " " + spend_id +" " + user_id);
		boolean information = true;
		if (priceInvoice.equalsIgnoreCase("") || nameSpend.equalsIgnoreCase("")) {
			information = false;
			Toast.makeText(InvoiceSpendActivity.this, getResources().getString(R.string.inoutallinfo), 1)
					.show();
		} else if (information) {
			try {
				new WSInsertInvoiceCost(code, nameSpend, priceInvoice,
						status, spend_id,payfor, purpose,user_id,
						InvoiceSpendActivity.this).execute();
			} catch (Exception e) {
			}
		}

	}

	// ----------------------------Insert data----------------------------//

	private class WSInsertInvoiceCost extends AsyncTask<Void, Void, Void> {
		private String nameCost;
		private String priceInvoice;
		private String code;
		private String status;
		private int spending;
		private String payfor;
		private String purpose;
		private int user_id;
		private Context context;
		public int kq = -1;
		private ConfigurationWS mWS_Insert;
		private ProgressDialog progressDialog;

		public WSInsertInvoiceCost(String code, String nameCost,
				String priceInvoice, String status, int spending, String payfor, String purpose,int user_id,
				Context mcontext) {
			super();
			this.nameCost = nameCost;
			this.priceInvoice = priceInvoice;
			this.code = code;
			this.status = status;
			this.spending = spending;
			this.context = mcontext;
			this.payfor = payfor;
			this.purpose = purpose;
			this.user_id = user_id;
			mWS_Insert = new ConfigurationWS(mcontext);
			progressDialog = new ProgressDialog(mcontext);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog.setMessage("Loading...");
			// mProgress.setMessage("Updating user info");
			progressDialog.setCancelable(false);
			progressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				listInvoiceSpend.clear();
				String URL = ConfigurationServer.getURLServer()
						+ "wsadd_invoicecost.php";
				JSONObject json = new JSONObject();
				json.put("nameCost", nameCost);
				json.put("priceInvoice", priceInvoice);
				json.put("status", status);
				json.put("spending", spending);
				json.put("payfor", payfor);
				json.put("purpose", purpose);
				json.put("user_id", user_id);
				JSONArray arrItem = new JSONArray();
				arrItem = mWS_Insert.connectWSPut_Get_Data(URL, json, "posts");

				if (arrItem != null) {
					for (int i = 0; i < arrItem.length(); i++) {
						JSONObject element = arrItem.getJSONObject(i);
						Invoice_Spend invoice_Spend = new Invoice_Spend();
						invoice_Spend.setCode(element.getString("code"));
						invoice_Spend.setNameCost(element.getString("name"));
						invoice_Spend.setPrice(element.getString("price"));
						invoice_Spend.setPayFor(element.getString("name_reiceive"));
						invoice_Spend.setPurposePay(element.getString("note"));
						invoice_Spend.setPersonPay(element.getString("username"));
						invoice_Spend.setCost(element.getString("namearise"));
						invoice_Spend.setId(element.getInt("id"));
						invoice_Spend.setStatus(element.getString("status"));
						listInvoiceSpend.add(invoice_Spend);
					}
				}

			} catch (Exception e) {
				Log.i("LOG", "Insert INV Detail : " + e.getMessage());

			}
			return null;

		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			adapterInvoiceSpend.notifyDataSetChanged();
			try {
				progressDialog.dismiss();
			} catch (Exception e) {

			}
		}
	}
 
	// --------------------------Check for update--------------------------//
	private void checkUpdate() {
		priceInvoice = edtPriceInvoice.getText().toString();
		nameSpend = edtnameCost.getText().toString();
		code = edtcodeInvoice.getText().toString();
		payfor = edtPayfor.getText().toString().trim();
		purpose = edtPurposePay.getText().toString().trim();
		boolean information = true;
		if (information) {
			try {
				new WSUpdateInvoiceCost(id, nameSpend, priceInvoice,payfor,purpose, status, spend_id, user_id,
						InvoiceSpendActivity.this).execute();
			} catch (Exception e) {
			}
		} else {
		}

	}

	// ---------------------------function Update----------------------------//

	private class WSUpdateInvoiceCost extends AsyncTask<Void, Void, Void> {
		private String nameCost;
		private String priceInvoice;
		private String code;
		private String payfor;
		private String purpose;
		private int id;
		private String status;
		private int spending;
		private int user_id;
		private Context context;
		public int kq = -1;
		private ConfigurationWS mWS_Insert;
		private ProgressDialog progressDialog;

		public WSUpdateInvoiceCost(int id, String nameCost,
				String priceInvoice, String payfor,String purpose, String status, int spending, int user_id, Context mcontext) {
			super();
			this.nameCost = nameCost;
			this.priceInvoice = priceInvoice;
			this.payfor = payfor;
			this.purpose = purpose;
			this.status = status;
			this.context = mcontext;
			this.spending = spending;
			this.id = id;
			this.user_id = user_id;
			mWS_Insert = new ConfigurationWS(mcontext);
			progressDialog = new ProgressDialog(mcontext);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog.setMessage("Loading...");
			// mProgress.setMessage("Updating user info");
			progressDialog.setCancelable(false);
			progressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				listInvoiceSpend.clear();
				String URL = ConfigurationServer.getURLServer()
						+ "wsupdate_invoicecost.php";
				JSONObject json = new JSONObject();
				json.put("nameCost", nameCost);
				json.put("priceInvoice", priceInvoice);
				json.put("codeInvoice", code);
				json.put("id", id);
				json.put("payfor", payfor);
				json.put("purpose", purpose);
				json.put("status", status);
				json.put("spending", spending);
				json.put("user_id", user_id);
				JSONArray arrItem = new JSONArray();
				arrItem = mWS_Insert.connectWSPut_Get_Data(URL, json, "posts");

				if (arrItem != null) {
					for (int i = 0; i < arrItem.length(); i++) {
						JSONObject element = arrItem.getJSONObject(i);
						Invoice_Spend invoice_Spend = new Invoice_Spend();
						invoice_Spend.setCode(element.getString("code"));
						invoice_Spend.setNameCost(element.getString("name"));
						invoice_Spend.setPrice(element.getString("price"));
						invoice_Spend.setPayFor(element.getString("name_reiceive"));
						invoice_Spend.setPurposePay(element.getString("note"));
						invoice_Spend.setPersonPay(element.getString("username"));
						invoice_Spend.setCost(element.getString("namearise"));
						invoice_Spend.setId(element.getInt("id"));
						invoice_Spend.setStatus(element.getString("status"));
						listInvoiceSpend.add(invoice_Spend);
					}
				}

			} catch (Exception e) {
				Log.i("LOG", "Insert INV Detail : " + e.getMessage());

			}
			return null;

		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			adapterInvoiceSpend.notifyDataSetChanged();
			switch (kq) {
			case 0:
				break;
			case 1:
				break;

			default:
				break;
			}
			try {
				progressDialog.dismiss();
			} catch (Exception e) {

			}
		}

	}

	// -------------------function Delete------------------------------//

	private class WSDeleteInvoiceCost extends AsyncTask<Void, Void, Void> {
		private int id;
		private Context context;
		public int kq = -1;
		private ConfigurationWS mWS_Insert;
		private ProgressDialog progressDialog;

		public WSDeleteInvoiceCost(int id, Context mcontext) {
			super();
			this.context = mcontext;
			this.id = id;
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
				listInvoiceSpend.clear();
				String URL = ConfigurationServer.getURLServer()
						+ "wsdelete_invoicecost.php";
				JSONObject json = new JSONObject();
				json.put("id", id);
				JSONArray arrItem = new JSONArray();
				arrItem = mWS_Insert.connectWSPut_Get_Data(URL, json, "posts");

				if (arrItem != null) {
					for (int i = 0; i < arrItem.length(); i++) {
						JSONObject element = arrItem.getJSONObject(i);
						Invoice_Spend invoice_Spend = new Invoice_Spend();
						invoice_Spend.setCode(element.getString("code"));
						invoice_Spend.setNameCost(element.getString("name"));
						invoice_Spend.setPrice(element.getString("price"));
						invoice_Spend.setPayFor(element.getString("name_reiceive"));
						invoice_Spend.setPurposePay(element.getString("note"));
						invoice_Spend.setPersonPay(element.getString("username"));
						invoice_Spend.setCost(element.getString("namearise"));
						invoice_Spend.setId(element.getInt("id"));
						invoice_Spend.setStatus(element.getString("status"));
						listInvoiceSpend.add(invoice_Spend);
					}
				}

			} catch (Exception e) {
				Log.i("LOG", "Insert INV Detail : " + e.getMessage());

			}
			return null;

		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			adapterInvoiceSpend.notifyDataSetChanged();
			switch (kq) {
			case 0:
				break;
			case 1:
				break;

			default:
				break;
			}
			try {
				progressDialog.dismiss();
			} catch (Exception e) {

			}
		}

	}
	String statusSpend;

	// ---------------------------Get data----------------------------//

	private class WSgetDataArise extends AsyncTask<Void, Void, Void> {
		private ProgressDialog progressDialog;
		private ConfigurationWS mWS;
		private Context context;

		public WSgetDataArise(Context mcontext) {
			progressDialog = new ProgressDialog(mcontext);
			mWS = new ConfigurationWS(mcontext);
			this.context = mcontext;
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
				try {
					listSpend.clear();
					// ---------------get String ------------------------//
					String URL = ConfigurationServer.getURLServer()
							+ "wsgetlistariseIII.php";
					JSONObject json = new JSONObject();
					JSONArray jarr = mWS.connectWSPut_Get_Data(URL, json,
							"posts");
					for (int i = 0; i < jarr.length(); i++) {
						JSONObject element = jarr.getJSONObject(i);
						ListSpend listarise = new ListSpend();
						listarise.setCodeList(element.getString("code"));
						listarise.setNameCost(element.getString("namearise"));
						listarise.setNotes(element.getString("note"));
						listarise.setPrice(element.getString("price"));
						listarise.setTypeCost(element.getString("type"));
						listarise.setGroupCost(element.getString("groupid"));
						listarise.setId(element.getInt("id"));
						listSpend.add(listarise);
					}
				} catch (Exception e) {
					Log.i("Log : ", "Exception : " + e.getMessage());
				}
			} catch (Exception e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			new WSgetdataInvoiceCost(InvoiceSpendActivity.this).execute();
			ArrayList<String> lstString = new ArrayList<String>();
			for (ListSpend lstSpend : listSpend) {
				String a = lstSpend.getNameCost();
				lstString.add(a);
				statusSpend = lstSpend.getCodeList();
				
			}
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					InvoiceSpendActivity.this,
					android.R.layout.simple_spinner_item, lstString);
			SpinnerAdapte adapter = new SpinnerAdapte(context, lstString);

			spncosts.setAdapter(adapter);

			if (progressDialog != null)
				progressDialog.dismiss();
		}
	}
	
	//-----------------------GET STATUS FROM shennong_extra_option---------------------------//
	private class WSgetDataExtra extends AsyncTask<Void, Void, Void> {
		private ProgressDialog progressDialog;
		private ConfigurationWS mWS;
		private Context context;

		public WSgetDataExtra(Context mcontext) {
			progressDialog = new ProgressDialog(mcontext);
			mWS = new ConfigurationWS(mcontext);
			this.context = mcontext;
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
				try {
					listStatus.clear();
					// ---------------get String ------------------------//
					String URL = ConfigurationServer.getURLServer()
							+ "wsgetallstatusextraoption.php";
					JSONObject json = new JSONObject();
					JSONArray jarr = mWS.connectWSPut_Get_Data(URL, json,
							"posts");
					for (int i = 0; i < jarr.length(); i++) {
						JSONObject element = jarr.getJSONObject(i);
						Invoice_Spend invoice_Spend = new Invoice_Spend();
						invoice_Spend.setStatus(element.getString("status"));
						listStatus.add(invoice_Spend);
					}
				} catch (Exception e) {
					Log.i("Log : ", "Exception : " + e.getMessage());
				}
			} catch (Exception e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			setDataSpinnerStatus();
			if (progressDialog != null)
				progressDialog.dismiss();
		}
	}
	//-----------------------GET username FROM pos_user---------------------------//
		private class WSgetDataUser extends AsyncTask<Void, Void, Void> {
			private ProgressDialog progressDialog;
			private ConfigurationWS mWS;
			private Context context;

			public WSgetDataUser(Context mcontext) {
				progressDialog = new ProgressDialog(mcontext);
				mWS = new ConfigurationWS(mcontext);
				this.context = mcontext;
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
					try {
						listUser.clear();
						// ---------------get String ------------------------//
						String URL = ConfigurationServer.getURLServer()
								+ "wsgetalluserinvoice.php";
						JSONObject json = new JSONObject();
						JSONArray jarr = mWS.connectWSPut_Get_Data(URL, json,
								"posts");
						for (int i = 0; i < jarr.length(); i++) {
							JSONObject element = jarr.getJSONObject(i);
							Invoice_Spend invoice_Spend = new Invoice_Spend();
							invoice_Spend.setUsername(element.getString("hotenkhaisinh"));
							invoice_Spend.setUser_id(element.getInt("user_id"));
							listUser.add(invoice_Spend);
						}
					} catch (Exception e) {
						Log.i("Log : ", "Exception : " + e.getMessage());
					}
				} catch (Exception e) {
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				ArrayList<String> list = new ArrayList<String>();
				for (Invoice_Spend invoice_Spend : listUser) {
					String username = invoice_Spend.getUsername();
					list.add(username);
				}
				SpinnerAdapte adapter = new SpinnerAdapte(InvoiceSpendActivity.this, list);
				spnPersonPay.setAdapter(adapter);
				if (progressDialog != null)
					progressDialog.dismiss();
			}
		}

}
*/