/*package iii.pos.client.activity;

import iii.pos.client.R;
import iii.pos.client.adapter.ReportSaleAdapter;
import iii.pos.client.model.Report;
import iii.pos.client.model.ReportItem;
import iii.pos.client.model.Report_SPay;
import iii.pos.client.server.ConfigurationServer;
import iii.pos.client.server.ConfigurationWS;

import java.text.DecimalFormat;
import java.util.ArrayList;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ReportSale_PaymentActivity extends Activity implements
		OnClickListener {

	public static final int REVENUE = 1;
	public static final int REVENUE_SPENDING = 2;
	public static final int REPORT_ITEM = 3;
	public static final int REPORT_INVOICE = 4;
	public static int KEY = 1;
	private ArrayList<Report_SPay> lstRevenueSpending;
	private ArrayList<Report> lstRevenue;
	private ArrayList<ReportItem> lstItem;
	private ReportSaleAdapter adapterReportSale;
	private ListView listReportSale;
	private Button btnReportsale, btnReportsale_payment, btnReport,
			btnReportItems, btnReportInv;
	private TextView txtSumSale, txtSumValue, tvIndex, tvDate_Name, tvPrice1,
			tvPrice2, tvResult, txtTitle_Report;

	private ImageView imgvCalendarfrom, imgvCalendarto;
	private EditText edtdate_from, edtdate_to;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reportsale_payment_layout);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		loadUI();
	}

	private void loadUI() {

		// -----------------------title-----------------------//
		tvIndex = (TextView) findViewById(R.id.tvIndex);
		tvDate_Name = (TextView) findViewById(R.id.tvDate_Name);
		tvPrice1 = (TextView) findViewById(R.id.txtPrice1);
		tvPrice2 = (TextView) findViewById(R.id.tvPrice2);
		tvResult = (TextView) findViewById(R.id.tvResult);
		txtTitle_Report = (TextView)findViewById(R.id.txtTitle_Report);
		// -----------------------RESULT SUM-----------------------//
		txtSumSale = (TextView) findViewById(R.id.txtSumSale);
		txtSumValue = (TextView) findViewById(R.id.txtSumValue);

		// --------------------only one listview-----------------------//
		listReportSale = (ListView) findViewById(R.id.listReportSale);

		// ===============list of each case================//
		lstRevenueSpending = new ArrayList<Report_SPay>();
		lstRevenue = new ArrayList<Report>();
		lstItem = new ArrayList<ReportItem>();

		// =================Button ================================//
		btnReportsale = (Button) findViewById(R.id.btnReportsale);
		btnReportsale.setOnClickListener(this);

		btnReportsale_payment = (Button) findViewById(R.id.btnReportsale_payment);
		btnReportsale_payment.setOnClickListener(this);

		btnReportItems = (Button) findViewById(R.id.btnReportItems);
		btnReportItems.setOnClickListener(this);

		btnReportInv = (Button) findViewById(R.id.btnReportInv);
		btnReportInv.setOnClickListener(this);

		btnReport = (Button) findViewById(R.id.btnReport);
		btnReport.setOnClickListener(this);

		// =================img View to select date==================//
		imgvCalendarfrom = (ImageView) findViewById(R.id.imgvCalendarfrom);
		imgvCalendarfrom.setOnClickListener(this);

		imgvCalendarto = (ImageView) findViewById(R.id.imgvCalendarto);
		imgvCalendarto.setOnClickListener(this);

		// ==================editText to store date time==============//
		edtdate_from = (EditText) findViewById(R.id.edtdate_from);
		edtdate_to = (EditText) findViewById(R.id.edtdate_to);
		resetUI(1);

	}

	private void resetUI(int key) {
		this.tvIndex.setText(getResources().getString(R.string.report_index));
		switch (key) {

		case REVENUE:
			this.btnReportsale.setBackgroundResource(R.drawable.ngoc_header_button2);
			this.btnReportsale_payment.setBackgroundResource(R.drawable.ngoc_header_button);
			this.btnReportItems.setBackgroundResource(R.drawable.ngoc_header_button);
			this.txtTitle_Report.setText(getResources().getString(R.string.report_btn1));
			this.tvDate_Name.setText(getResources().getString(
					R.string.report_date));
			this.tvPrice1
					.setText(getResources().getString(R.string.report_inv));
			this.tvPrice2.setText(getResources().getString(
					R.string.report_Revenue));
			this.tvResult.setText(getResources().getString(
					R.string.report_average));
			adapterReportSale = new ReportSaleAdapter(
					ReportSale_PaymentActivity.this,
					R.layout.item_reportsale_layout, lstRevenue, REVENUE);
			listReportSale.setAdapter(adapterReportSale);
			break;
		case REVENUE_SPENDING:
			this.btnReportsale_payment.setBackgroundResource(R.drawable.ngoc_header_button2);
			this.btnReportsale.setBackgroundResource(R.drawable.ngoc_header_button);
			this.btnReportItems.setBackgroundResource(R.drawable.ngoc_header_button);
			this.txtTitle_Report.setText(getResources().getString(R.string.report_btn2));
			this.tvDate_Name.setText(getResources().getString(
					R.string.report_date));
			this.tvPrice1.setText(getResources().getString(
					R.string.report_Revenue));
			this.tvPrice2.setText(getResources().getString(
					R.string.report_spending));
			this.tvResult.setText(getResources().getString(
					R.string.report_benefit));
			adapterReportSale = new ReportSaleAdapter(
					ReportSale_PaymentActivity.this,
					R.layout.item_reportsale_layout, lstRevenueSpending,
					REVENUE_SPENDING);
			listReportSale.setAdapter(adapterReportSale);
			break;
		case REPORT_ITEM:
			this.btnReportItems.setBackgroundResource(R.drawable.ngoc_header_button2);
			this.btnReportsale.setBackgroundResource(R.drawable.ngoc_header_button);
			this.btnReportsale_payment.setBackgroundResource(R.drawable.ngoc_header_button);
			this.txtTitle_Report.setText(getResources().getString(R.string.report_btn3));
			this.tvDate_Name.setText(getResources().getString(
					R.string.report_itemname));
			this.tvPrice1.setText(getResources().getString(
					R.string.report_itemamount));
			this.tvPrice2.setText(getResources().getString(
					R.string.report_itemunit));
			this.tvResult.setText(getResources().getString(
					R.string.report_itemtotal));
			adapterReportSale = new ReportSaleAdapter(
					ReportSale_PaymentActivity.this,
					R.layout.item_reportsale_layout, lstItem, REPORT_ITEM);
			listReportSale.setAdapter(adapterReportSale);
			break;
		case REPORT_INVOICE:
			finish();
			break;
		default :
			this.btnReportsale.setBackgroundResource(R.drawable.ngoc_header_button2);
			this.btnReportsale_payment.setBackgroundResource(R.drawable.ngoc_header_button);
			this.btnReportItems.setBackgroundResource(R.drawable.ngoc_header_button);
			this.txtTitle_Report.setText(getResources().getString(R.string.report_btn1));
			this.tvDate_Name.setText(getResources().getString(
					R.string.report_date));
			this.tvPrice1
					.setText(getResources().getString(R.string.report_inv));
			this.tvPrice2.setText(getResources().getString(
					R.string.report_Revenue));
			this.tvResult.setText(getResources().getString(
					R.string.report_average));
			adapterReportSale = new ReportSaleAdapter(
					ReportSale_PaymentActivity.this,
					R.layout.item_reportsale_layout, lstRevenue, REVENUE);
			listReportSale.setAdapter(adapterReportSale);
			break;

		}
		
		
	}

	private void filterByDay() {
		String start_date = edtdate_from.getText().toString();
		if (start_date == null) {
			start_date = "";
		}
		String end_date = edtdate_to.getText().toString();
		if (end_date == null) {
			end_date = "";
		}
		if (start_date != "" && end_date != "") {
			new WSReport(ReportSale_PaymentActivity.this, start_date, end_date)
					.execute();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnReportsale:
			KEY = 1;
			resetUI(KEY);
			break;
		case R.id.btnReportsale_payment:
			KEY = 2;
			resetUI(KEY);
			break;
		case R.id.btnReportItems:
			KEY = 3;
			resetUI(KEY);
			break;
		case R.id.btnReportInv:
			KEY = 4;
			resetUI(KEY);
			break;
		case R.id.btnReport:
			filterByDay();
			break;
		case R.id.imgvCalendarfrom:
			Intent intentcalenda = new Intent(ReportSale_PaymentActivity.this,
					CalendarViewPosActivity.class);
			startActivityForResult(intentcalenda, 111);
			break;
		case R.id.imgvCalendarto:
			Intent intentcalenda2 = new Intent(ReportSale_PaymentActivity.this,
					CalendarViewPosActivity.class);
			startActivityForResult(intentcalenda2, 222);
			break;

		default:
			break;
		}

	}

	public String formatDecimal(double number) {

		// DecimalFormat nf = new DecimalFormat("###,###,###,##0.00");
		DecimalFormat nf = new DecimalFormat("###,###,###,##0");
		String formatted = nf.format(number);

		return formatted;
	}

	private void setTotal1(ArrayList<Report> listReport) {
		float myCostTotal = 0.0f;
		int total = 0;
		for (Report report : listReport) {
			total = total + report.getTotalReport();
			myCostTotal = myCostTotal + report.getTotalValue();
		}

		this.txtSumSale.setText(String.valueOf(total));
		this.txtSumValue.setText(formatDecimal(myCostTotal)
				+ getResources().getString(R.string.curr));
	}

	private void setTotal3(ArrayList<ReportItem> listReport) {
		float myCostTotal = 0.0f;
		int total = 0;
		for (ReportItem report : listReport) {
			total = total + report.getAmount();
			myCostTotal = myCostTotal + report.getPrice() * report.getAmount();
		}

		this.txtSumSale.setText(String.valueOf(total));
		this.txtSumValue.setText(formatDecimal(myCostTotal)
				+ getResources().getString(R.string.curr));
	}

	private void setTotal(ArrayList<Report_SPay> listReport) {
		float totalSpending = 0.0f;
		float totalRevenue = 0;
		for (Report_SPay report : listReport) {
			totalRevenue = totalRevenue + report.getTotalRevenue();
			totalSpending = totalSpending + report.getTotalSpending();
		}
		this.txtSumSale.setText(String.valueOf(totalRevenue));
		this.txtSumValue.setText(formatDecimal(totalSpending)
				+ getResources().getString(R.string.curr));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		try {
			if (requestCode == 111 && resultCode == RESULT_OK) {
				String resul = data.getStringExtra("cal").toString();
				edtdate_from.setText(resul);
			}
		} catch (Exception e) {
		}

		try {
			if (requestCode == 222 && resultCode == RESULT_OK) {
				String resul2 = data.getStringExtra("cal").toString();
				edtdate_to.setText(resul2);
			}
		} catch (Exception e) {
		}

	}

	// ==================ws searching my phone====================//
	private class WSReport extends AsyncTask<Void, Void, Void> {
		private String TAG = "WSGetMyPhone";
		private ConfigurationWS mWS;
		private Context context;
		private ProgressDialog mProgress;
		private String start_date, end_date;

		public WSReport(Context mcontext, String start_date, String end_date) {
			super();
			this.context = mcontext;
			this.start_date = start_date;
			this.end_date = end_date;
			mWS = new ConfigurationWS(mcontext);
			mProgress = new ProgressDialog(mcontext);
		}

		// --------processing------------------------------//
		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mProgress.setMessage("Loading...");
			mProgress.setCancelable(false);
			mProgress.show();
		}

		private void loadRevenue() {
			try {
				lstRevenue.clear();
				String wsgetMyPhone = ConfigurationServer.getURLServer()
						+ "wsreport.php";
				JSONObject json = new JSONObject();
				json.put("start_date", start_date.replace("-", ""));
				json.put("end_date", end_date.replace("-", ""));
				JSONArray arrItem = new JSONArray();
				arrItem = mWS.connectWSPut_Get_Data(wsgetMyPhone, json,
						"report");
				if (arrItem != null) {
					for (int i = 0; i < arrItem.length(); i++) {
						JSONObject results = arrItem.getJSONObject(i);
						Report report = new Report();
						try {
							report.setDatetime(results.getString("Mydate"));
							report.setTotalReport(Integer.parseInt(results
									.getString("total")));
							report.setTotalValue(Float.parseFloat(results
									.getString("summed")));
						} catch (Exception e) {
						}
						lstRevenue.add(report);
					}
				}
			} catch (Exception e) {
				Log.i(TAG, "ERROR : " + e.getMessage());
			}
		}

		private void loadRevenueSpending() {
			try {
				lstRevenueSpending.clear();
				String wsgetMyPhone = ConfigurationServer.getURLServer()
						+ "wsreport.php";
				JSONObject json = new JSONObject();
				json.put("start_date", start_date.replace("-", ""));
				json.put("end_date", end_date.replace("-", ""));
				JSONArray arrItem = new JSONArray();
				arrItem = mWS.connectWSPut_Get_Data(wsgetMyPhone, json,
						"report");
				if (arrItem != null) {
					for (int i = 0; i < arrItem.length(); i++) {
						JSONObject results = arrItem.getJSONObject(i);
						Report_SPay report = new Report_SPay();
						try {
							report.setDate(results.getString("Mydate"));
							report.setTotalRevenue(Float.parseFloat(results
									.getString("summed")));
							report.setTotalSpending(0);

						} catch (Exception e) {
						}
						lstRevenueSpending.add(report);
					}
				}
			} catch (Exception e) {
			}
		}

		private void loadReportItem() {
			try {
				lstItem.clear();
				String wsgetMyPhone = ConfigurationServer.getURLServer()
						+ "wsreportitems.php";
				JSONObject json = new JSONObject();
				json.put("language_code", ConfigurationServer.language_code);
				json.put("start_date", start_date.replace("-", ""));
				json.put("end_date", end_date.replace("-", ""));
				JSONArray arrItem = new JSONArray();
				arrItem = mWS.connectWSPut_Get_Data(wsgetMyPhone, json,
						"reportitems");
				if (arrItem != null) {
					for (int i = 0; i < arrItem.length(); i++) {
						JSONObject results = arrItem.getJSONObject(i);
						Log.i("Log : ", "TTTTTTTT : " + results);
						ReportItem report = new ReportItem();
						try {
							report.setItem_id(results.getInt("item_id"));

							report.setName(results.getString("name"));
							report.setAmount(results.getInt("total"));
							String price = results.getString("price");
							try {
								report.setPrice(Float.parseFloat(price)
										* report.getAmount());
							} catch (Exception e) {
							}

						} catch (Exception e) {
						}
						lstItem.add(report);
					}
				}
				ArrayList<ReportItem> arr = lstItem;

			} catch (Exception e) {
			}
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			switch (KEY) {
			case REVENUE:
				loadRevenue();
				break;
			case REVENUE_SPENDING:
				loadRevenueSpending();
				break;
			case REPORT_ITEM:
				loadReportItem();
				break;
			case REPORT_INVOICE:
				break;
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			try {
				switch (KEY) {
				case REVENUE:
					setTotal1(lstRevenue);
					break;
				case REVENUE_SPENDING:
					setTotal(lstRevenueSpending);
					break;
				case REPORT_ITEM:
					setTotal3(lstItem);
					break;
				case REPORT_INVOICE:
					break;
				}

				adapterReportSale.notifyDataSetChanged();
				mProgress.dismiss();
			} catch (Exception e) {

			}
		}
	}

}
*/