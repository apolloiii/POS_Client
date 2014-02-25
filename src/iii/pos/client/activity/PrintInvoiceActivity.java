/*package iii.pos.client.activity;

import iii.pos.client.R;
import iii.pos.client.adapter.ListInvoiceReportAdapter;
import iii.pos.client.model.Client;
import iii.pos.client.model.Invoice_Detail;
import iii.pos.client.server.ConfigurationServer;
import iii.pos.client.server.ConfigurationWS;
import iii.pos.client.wsclass.WSPayment;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

------------this class to display inv_detail when client payment----------
public class PrintInvoiceActivity extends Activity {

	---------------Fields----------------------------------
	private TextView tvInvoiceReportCustomName, tvInvoiceReportCustomAddress,
			tvInvoiceReportCustomPhone, tvInvoiceReportCustomTable;
	private TextView tvInvoiceReportVAT, tvInvoiceReportCommition,
			tvInvoiceReportTotal;
	private Button btnInvoiceReportPrint, btnInvoiceReportCancel, btnno22;

	-------------------------------------------------------
	private ListView lvPrintInvoiceReport;
	private ArrayList<Invoice_Detail> listItemsCurrent;
	private ListInvoiceReportAdapter adapterInvoice;
	private Spinner spPrintInvoice;
	private float VAT, COM, TONG, COST;
	float VAT1;
	float COM1;
	private HashMap<String, String> hm;
	private ArrayList<String> lstCode;
	private String inv_code;
	private float temp = 1;
	private String code = " NVD";

	public static String client = "";
	public static String address = "";

	-------------------create view here-------------------
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.invoice_report);
		Bundle bl = getIntent().getExtras();
		if (bl != null) {
			this.inv_code = bl.getString("inv_code");
			this.VAT = bl.getInt("vat");
			this.COM = bl.getInt("commision");
		}
		init();
		displayItems();
		disPlayInfo();
		setAdapter();
	}

	 =======================initialize============================ 
	private void init() {
		tvInvoiceReportCustomName = (TextView) findViewById(R.id.tvInvoiceReportCustomName);
		if (client != "")
			tvInvoiceReportCustomName.setText(client);
		tvInvoiceReportCustomAddress = (TextView) findViewById(R.id.tvInvoiceReportCustomAddress);
		if (address != "")
			tvInvoiceReportCustomAddress.setText(address);
//		tvInvoiceReportCustomPhone = (TextView) findViewById(R.id.tvInvoiceReportCustomPhone);
//		tvInvoiceReportCustomTable = (TextView) findViewById(R.id.tvInvoiceReportCustomTable);

		tvInvoiceReportVAT = (TextView) findViewById(R.id.tvInvoiceReportVAT);
		tvInvoiceReportCommition = (TextView) findViewById(R.id.tvInvoiceReportCommition);
		tvInvoiceReportTotal = (TextView) findViewById(R.id.tvInvoiceReportTotal);

		lvPrintInvoiceReport = (ListView) findViewById(R.id.lvPrintInvoiceReport);

		btnInvoiceReportPrint = (Button) findViewById(R.id.btnInvoiceReportPrint);
		btnInvoiceReportCancel = (Button) findViewById(R.id.btnInvoiceReportCancel);
		btnno22 = (Button)findViewById(R.id.btnno22);

		spPrintInvoice = (Spinner) findViewById(R.id.spPrintInvoice);

		btnInvoiceReportPrint.setOnClickListener(btnClick);
		btnInvoiceReportCancel.setOnClickListener(btnClick);
		btnno22.setOnClickListener(btnClick);
		lvPrintInvoiceReport.setEnabled(true);

		spPrintInvoice.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				code = lstCode.get(arg2);
				String curren = hm.get(code);

				if (curren.contains(",")) {
					String strsplit[] = curren.split(",");
					curren = strsplit[0] + strsplit[1];
				}
				temp = Float.parseFloat(curren);
				disPlayInfo(temp);
				displayItems();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}

	private void setAdapter() {
		// PosCurrency posCurr = new PosCurrency();
		// hm = posCurr.Currency();
		// lstCode = posCurr.lstCode;
		// ArrayAdapter<String> adap = new
		// ArrayAdapter<String>(getBaseContext(),
		// android.R.layout.simple_list_item_1, lstCode);
		// spPrintInvoice.setAdapter(adap);
		if (new ConfigurationServer(PrintInvoiceActivity.this).isOnline()) {
			new WSGetCurrency().execute();
		} else {
			Toast.makeText(PrintInvoiceActivity.this, "Network not found",
					Toast.LENGTH_SHORT).show();
		}

	}

	*//*** Get Item from bindata show listview *//*
	private void displayItems() {
		listItemsCurrent = MainPosActivity.beanDataAll
				.getlstInvDetail(inv_code);
		adapterInvoice = new ListInvoiceReportAdapter(PrintInvoiceActivity.this,
				R.layout.invoice_report, listItemsCurrent, code, temp);
		lvPrintInvoiceReport.setAdapter(adapterInvoice);

	}

	--- show VAT, COMITION, TOTAL ----
	private void disPlayInfo() {
		for (Invoice_Detail invDetail : listItemsCurrent) {
			TONG += invDetail.getPrice() * invDetail.getQuantity();
		}
		COST = TONG;

		VAT1 = TONG * (float) (VAT / 100);
		COM1 = TONG * (float) (COM / 100);
		TONG = COST + VAT1 - COM1;
		new WSPayment(PrintInvoiceActivity.this, inv_code, VAT, COM, COST, TONG, 0)
				.execute();// ==0 kieu hoa don thanh toan binh thuong
		tvInvoiceReportVAT.setText(String.valueOf(VAT1));
		tvInvoiceReportCommition.setText(String.valueOf(COM1));
		tvInvoiceReportTotal.setText(String.valueOf(TONG));

	}

	--- show VAT, COMITION, TOTAL ----
	private void disPlayInfo(float curr) {
		TONG = 0;
		for (Invoice_Detail invDetail : listItemsCurrent) {
			TONG += invDetail.getPrice() * invDetail.getQuantity();
		}
		VAT = (TONG * (float) (VAT / 100)) / curr;
		COM = (TONG * (float) (COM / 100)) / curr;
		TONG = TONG + VAT - COM;
		TONG = TONG / curr;
		String TOTAL = String.format("%.2f", TONG) + " " + code;
		// String TOTAL = String.valueOf(TONG);
		tvInvoiceReportVAT.setText(String.format("%.2f", VAT) + " " + code);
		tvInvoiceReportCommition.setText(String.format("%.2f", COM) + " "
				+ code);
		tvInvoiceReportTotal.setText(TOTAL);

	}

	*//** * Click Button print và cancel *//*
	OnClickListener btnClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnInvoiceReportPrint:
//				Toast.makeText(getBaseContext(), "Print", Toast.LENGTH_SHORT)
//						.show();
				finish();
				break;
			case R.id.btnInvoiceReportCancel:
				finish();
				break;
			case R.id.btnno22:
				// ----------B1 lưu vào csdl bảng client
				final View addView = getLayoutInflater().inflate(
						R.layout.addrowdebt, null);
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(
						PrintInvoiceActivity.this);
				// Setting Dialog Title
				alertDialog.setTitle("Message");
				// Setting Icon to Dialog

				alertDialog.setView(addView);
				alertDialog.setCancelable(false);
				// Setting Positive "Yes" Button
				alertDialog.setPositiveButton("CANCEL",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
							}
						});

				// Setting Negative "NO" Button
				alertDialog.setNegativeButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								EditText edtprice = (EditText) addView
										.findViewById(R.id.price);
								String price = edtprice.getText().toString()
										.trim();

								EditText edtdesc = (EditText) addView
										.findViewById(R.id.debt_date);
								String mydate = edtdesc.getText().toString()
										.trim();
								float myprice = Float.parseFloat(String
										.valueOf(price));

								try {
									Client client = getInfoClient();
									if (inv_code != "") {
										new WSSaveClientDebt(inv_code, client
												.getName(),
												client.getAddress(), client
														.getPhone(), mydate,
												myprice, VAT1, COM1, TONG,
												PrintInvoiceActivity.this).execute();

									}
								} catch (Exception e) {
								}

							}
						});

				// Showing Alert Message
				alertDialog.show();

				// ----------B2 lưu vào bảng công nợ
				break;
			}
		}
	};
	
	private Client getInfoClient() {
		Client client = new Client();

		String name = tvInvoiceReportCustomName.getText().toString();

		String address = tvInvoiceReportCustomAddress.getText().toString();

		String phone = tvInvoiceReportCustomPhone.getText().toString();

		client.setName(name);
		client.setAddress(address);
		client.setPhone(phone);

		return client;
	}
	
	// ============ws get Number of times call items============//
		private class WSSaveClientDebt extends AsyncTask<Void, Void, Void> {

			private String inv_code;
			private String clientName;
			private String clientAdd;
			private String clientPhone;
			private float price;
			private String date;
			private float vat;
			private float com;
			private float tong;
			private ConfigurationWS mWS;
			private ProgressDialog mProgress;

			public WSSaveClientDebt(String inv_code, String clientName,
					String clientAdd, String clientPhone, String date, float price,
					float vat, float com, float tong, Context mcontext) {
				this.clientName = clientName;
				this.clientAdd = clientAdd;
				this.clientPhone = clientPhone;
				this.inv_code = inv_code;
				this.date = date;
				this.price = price;
				this.vat = vat;
				this.com = com;
				this.tong = tong;
				mWS = new ConfigurationWS(mcontext);
				mProgress = new ProgressDialog(mcontext);
			}

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				mProgress.setMessage("Loading...");
				mProgress.setCancelable(false);
				mProgress.show();
			}

			@Override
			protected Void doInBackground(Void... params) {
				try {
					String wsgetdetailinvdetail = ConfigurationServer
							.getURLServer() + "wsdebt.php";
					JSONObject json = new JSONObject();
					json.put("clientName", clientName);
					json.put("clientAdd", clientAdd);
					json.put("clientPhone", clientPhone);
					json.put("date_debt", date);
					json.put("price", price);
					json.put("inv_code", inv_code);
					json.put("vat", vat);
					json.put("com", com);
					json.put("total", tong);
					JSONArray arrItem = new JSONArray();
					arrItem = mWS.connectWSPut_Get_Data(wsgetdetailinvdetail, json,
							"posts");
					if (arrItem != null) {
						// for (int i = 0; i < arrItem.length(); i++) {
						// JSONObject results = arrItem.getJSONObject(i);
						//
						// Invoice_Detail inv_detail = new Invoice_Detail();
						// inv_detail.setId(results.getInt("id"));
						// inv_detail.setInv_code(results.getString("inv_code"));
						// inv_detail.setItem_id(results.getInt("item_id"));
						// inv_detail.setFlag(results.getInt("flag"));
						// inv_detail.setQuantity(results.getInt("quantity"));
						// try {
						// inv_detail.setComment(results.getString("comment"));
						// inv_detail.setChecked(results.getInt("checked"));
						// inv_detail.setStart_date(results
						// .getString("invd_createtime"));
						// inv_detail.setEnd_date(results
						// .getString("invd_updatetime"));
						// } catch (Exception e) {
						// }
						//
						// lstDetailInvDetail.add(inv_detail);
						// }
					}
				} catch (Exception e) {
					Log.i("LOG", "Insert INV Detail : " + e.getMessage());
				}

				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				Toast.makeText(getBaseContext(), "Payment is success",
						Toast.LENGTH_SHORT).show();
				try {
					mProgress.dismiss();
				} catch (Exception e) {
				}
				PrintInvoiceActivity.this.finish();
			}
		}


	// ----------Threading make data invoice-------//
	private class WSGetCurrency extends AsyncTask<Void, Void, Void> {
		private ProgressDialog progressDialog;
		public WSGetCurrency() {
			progressDialog = new ProgressDialog(PrintInvoiceActivity.this);
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
				hm = Currency();
			} catch (Exception e) {
				Log.i("Log : ", "Exception : " + e.getMessage());
				progressDialog.dismiss();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			ArrayAdapter<String> adap = new ArrayAdapter<String>(
					getBaseContext(), android.R.layout.simple_spinner_item,
					lstCode);
			
//			AdapteSpinnerCost adap = new AdapteSpinnerCost(getBaseContext(), lstCode);
			spPrintInvoice.setAdapter(adap);
			if (progressDialog != null)
				progressDialog.dismiss();
		}
	}

	public HashMap<String, String> Currency() {
		// ---------------variable-----------------------------//
		HashMap<String, String> hm = new HashMap<String, String>();
		ArrayList<String> lstCode = new ArrayList<String>();
		ArrayList<String> lstPrice = new ArrayList<String>();

		int j = 0;
		lstCode.add("VND");
		lstPrice.add("1");
		hm = new HashMap<String, String>();
		hm.put("đ", "1");
		// --------------get currency from internet-------
		ArrayList<String> list = getCurrency();
		for (int i = 0; i < list.size(); i++) {
			if (i % 4 == 0) {
				lstCode.add(list.get(i));
			}
			if (i % 4 == 1) {
				lstPrice.add(list.get(i));
				hm.put(lstCode.get(j), lstPrice.get(j));
				j++;
			}
		}
		this.lstCode = lstCode;
		hm.put(lstCode.get(j), lstPrice.get(j));

		return hm;
	}

	------------return list currency from internet---
	private ArrayList<String> getCurrency() {
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		ArrayList<String> list = new ArrayList<String>();
		HtmlCleaner cleaner = new HtmlCleaner();
		CleanerProperties props = cleaner.getProperties();
		props.setTranslateSpecialEntities(true);
		props.setTransResCharsToNCR(true);
		props.setOmitComments(true);

		Object[] table;
		try {
			TagNode root = cleaner.clean(new URL(
					"http://www.vietcombank.com.vn/"));
			table = root.evaluateXPath("//table[@class='tbl-exch']");
			if (table.length > 0) {

				TagNode tablenode = (TagNode) table[0];
				String xml = cleaner.getInnerHtml(tablenode);
				Log.i("xml", "Table node  \n" + xml);
				Object[] tien = tablenode.evaluateXPath("//td");
				for (Object tiennode : tien) {
					TagNode tiennodechild = (TagNode) tiennode;
					list.add(cleaner.getInnerHtml(tiennodechild));
				}
			}
		} catch (IOException ex) {

		} catch (XPatherException ex) {
		}
		return list;
	}
}
*/