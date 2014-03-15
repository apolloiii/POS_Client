package iii.pos.client.fragment;

import iii.pos.client.R;
import iii.pos.client.activity.MainPosActivity;
import iii.pos.client.activity.helper.MainKitchenActivityHelper;
import iii.pos.client.adapter.ListInvoiceAdapter;
import iii.pos.client.adapter.ListInvoiceAdapter.IGetInvCode;
import iii.pos.client.fragment.InvoiceDetailPosFragment.IAddMenu;
import iii.pos.client.fragment.base.FragmentBase;
import iii.pos.client.model.Invoice;
import iii.pos.client.model.Invoice_Detail;
import iii.pos.client.model.Voice;
import iii.pos.client.server.ConfigurationServer;
import iii.pos.client.server.ConfigurationWS;
import iii.pos.client.wsclass.WSGetInvoiceClientById;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class InvoicePosFragment<viewHolderInvoice> extends FragmentBase implements OnClickListener {

	public static final String CALCULATOR_PACKAGE = "com.android.calculator2";
	public static final String CALCULATOR_CLASS = "com.android.calculator2.Calculator";
	//public InvoiceDB invoiceDB;
	//private ArrayList<Invoice> listInvDb;
	private int status = 0;
	private String table_code;
	private int user_id;
	private boolean ok = false;
	private IAddMenu iaddMenu;
	private IAddFragment addf;
	//private ConfigurationDB mDB;
	private Context context;
	private ConfigurationWS mWS;
	public ListInvoiceAdapter adapterInvoice;
	//private GridView gvDiaSplitChooseTable;
	private ListView invoiceList;
	//private ArrayList<String> itemTable = new ArrayList<String>();
	//private ArrayList<String> listValueItable;
	//private ArrayList<String> lstItable;
	//private ArrayList<String> lstItemFreeItable;
	public static ArrayList<String> lstinv_code = new ArrayList<String>();
	public List<Invoice> listInvoiceCurrent, listInvTmp;
	//private Button addNewInvoice, btnDestroy, btn_Coupling, btn_split, btnchoosetable, btnFreeTable;
	//private ImageButton btnDiaSplitChooseTableOK, btnDiaSplitChooseTableCancel;
	//private Button btnCalculator;
	//private Button volumeButton;
	
	private Thread soundThread;
	private ArrayList<Voice> lstVoice;
	private MediaPlayer media;
	private long time;
	
	
	private boolean flagSound = false;
	private String group = null;
	private String sOldName = "";
	
	private String invcode_name;


	public InvoicePosFragment(int status, String table_code, int user_id, String group) {
		this.status = status;
		this.table_code = table_code;
		this.user_id = user_id;
		this.group = group;
	}

	@Override
	public void onAttach(Activity arg0) {
		super.onAttach(arg0);
		try {
			addf = (IAddFragment) arg0;
		} catch (Exception e) {
		}
		try {
			iaddMenu = (IAddMenu) arg0;
		} catch (Exception e) {
		}
	}

	@Override
	public void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		context = getActivity().getApplicationContext();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
		final View v = inflater.inflate(R.layout.invoice_pos, container, false);
		initView(v);
		setData();
		return v;
	}

	public void initView(View v) {
		soundThread = new Thread(thread_sound);
		lstVoice = new ArrayList<Voice>();
		
		//invoiceDB = new InvoiceDB(getActivity());
		//lstItemFreeItable = new ArrayList<String>();
		//lstItable = new ArrayList<String>();
		//lstItemFreeItable = new ArrayList<String>();
		//btnFreeTable = (Button) v.findViewById(R.id.btnFreeTable);
		//btnFreeTable.setOnClickListener(this);
		//mDB = new ConfigurationDB(context);
		mWS = new ConfigurationWS(context);
		//addNewInvoice = (Button) v.findViewById(R.id.addNewInvoice);
		//addNewInvoice.setOnClickListener(this);

		//btnDestroy = (Button) v.findViewById(R.id.btn_Destroy);
		//btnDestroy.setOnClickListener(this);
		//btn_Coupling = (Button) v.findViewById(R.id.btn_Coupling);
		//btn_Coupling.setOnClickListener(this);
		//btn_split = (Button) v.findViewById(R.id.btn_split);
		//btn_split.setOnClickListener(this);
		invoiceList = (ListView) v.findViewById(R.id.listView);

		//mDB.OpenDB();
		listInvoiceCurrent = new CopyOnWriteArrayList<Invoice>();
		listInvTmp = new CopyOnWriteArrayList<Invoice>();
		//listInvoiceCurrent = MainPosActivity.beanDataAll.lstInvoice;
		//displaydata(listInvoiceCurrent);
		
		/*listInvoiceCurrent = BeanDataAll.getLstInvoiceMoke();
		displaydata(listInvoiceCurrent);*/
		
		
		/*ok = true;
		new Thread(myThread).start();*/
		
		/*btnCalculator = (Button) v.findViewById(R.id.btnCalculator);
		btnCalculator.setOnClickListener(this);
		volumeButton = (Button)v.findViewById(R.id.btn_volume);
		volumeButton.setOnClickListener(this);*/
		/**
		 * khiemnd start
		 */
		flagSound = true;//Constaints.flagSound;
		if (flagSound) {
			if (!soundThread.isAlive()){
				soundThread.start();
			}
		}
		/**
		 * khiemnd end
		 */
		
	}

	@Override
	public void onResume() {
		super.onResume();
		//Toast.makeText(getActivity(), "On Resume", Toast.LENGTH_LONG).show();
		try {
			listInvoiceCurrent = new WSGetInvoiceClientById(getActivity()).execute().get();
			displaydata(listInvoiceCurrent);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		//ok = true;
		//new Thread(myThread).start();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		//ok = false;
		//Toast.makeText(getActivity(), "On pause", Toast.LENGTH_LONG).show();
		
	}
	
	public void setData() {
		invoiceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {
						Log.i("------------------------AA", listInvoiceCurrent.get(position).getStatus()+"");
						if(listInvoiceCurrent.get(position).getStatus() == 2)
						{
							// Vu: Xu ly khong click duoc trong InvoiceDetail voi trang thai la huy hoa don
							MainPosActivity.category_container.setVisibility(LinearLayout.VISIBLE);
							String inv_code = listInvoiceCurrent.get(position).getInv_code();
							int committion = listInvoiceCurrent.get(position).getCommision();
							int vat = listInvoiceCurrent.get(position).getVat();
							addf.addfragment(false, inv_code, inv_code, MainPosActivity.beanDataAll.getlstInvDetail(inv_code), vat, committion);
							//addf.addfragment(false, inv_code, inv_code,
								//	new ArrayList<Invoice_Detail>(), 0, 0);
							//enableButton(false); // Vu: khong cho click cac nut: huy, tach, ghep....hoa don
						}else if(listInvoiceCurrent.get(position).getStatus() == 0)
						{
							// Vu: Xu ly khong click duoc trong InvoiceDetail voi trang thai la da thanh toan
							MainPosActivity.category_container.setVisibility(LinearLayout.VISIBLE);
							String inv_code = listInvoiceCurrent.get(position).getInv_code();
							int committion = listInvoiceCurrent.get(position).getCommision();
							int vat = listInvoiceCurrent.get(position).getVat();
							addf.addfragment(false, inv_code, inv_code,
									 MainPosActivity.beanDataAll.getlstInvDetail(inv_code), vat, committion);
							//enableButton(false);// Vu: khong cho click cac nut: huy, tach, ghep....hoa don
							//enableDestroy(false);
							
						}else{
							//enableButton(true);// Vu: cho phep click cac nut: huy, tach, ghep....hoa don
							MainPosActivity.category_container.setVisibility(LinearLayout.VISIBLE);
							String inv_code = listInvoiceCurrent.get(position).getInv_code();
							int committion = listInvoiceCurrent.get(position).getCommision();
							int vat = listInvoiceCurrent.get(position).getVat();
							int status = listInvoiceCurrent.get(position).getStatus();
							int other_user = listInvoiceCurrent.get(position).getUser_id();
							adapterInvoice.getCheckBok().setChecked(listInvoiceCurrent.get(position).isCheck());
							
							// -------checking status of invoice--------//
							int flag = 0;
							if (status == 0 || other_user !=  MainPosActivity.user.getUser_id() ) 
							{
								flag = 0;
								if (new ConfigurationServer(context).isOnline()) {
									new WSGetInvDetail(inv_code, inv_code, committion, vat, flag).execute();
								} else {
									Toast.makeText(context, "Network not found", Toast.LENGTH_SHORT).show();
								} 
							} else {
								flag = 1;
								if (new ConfigurationServer(context).isOnline()) {
									new WSGetInvDetail(inv_code, inv_code, committion, vat, flag).execute();
								} else {
									Toast.makeText(context, "Network not found", Toast.LENGTH_SHORT).show();
								}
							}
						}
					}
				});

		//setStateInvoice();
/*
		btnCalculator.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				flagSound = true;
				if (flagSound) {
					if (!soundThread.isAlive())
						soundThread.start();
				}
			}
		});*/
	}
	
	//private void enableButton(boolean enabled){
		//btnDestroy.setEnabled(enabled);
		//btn_split.setEnabled(enabled);
		//btn_Coupling.setEnabled(enabled);
		//volumeButton.setEnabled(enabled);
		//addNewInvoice.setEnabled(enabled);
		//btnCalculator.setEnabled(enabled);
		//btnFreeTable.setEnabled(enabled);
	//}
	
	//set data cho list invoice
	//sap xep invoice theo trang thai status 
	private void displaydata(List<Invoice> lstInvoice) {
		adapterInvoice = new ListInvoiceAdapter(getActivity() , R.layout.invoice_pos, lstInvoice, table_code);
		invoiceList.setAdapter(adapterInvoice);
		adapterInvoice.getInvCode(new IGetInvCode() {
			   @Override
			   public void getInvCode(String invCode) {
			    Toast.makeText(getActivity(), invCode, Toast.LENGTH_SHORT).show();
			    invcode_name = invCode;
			   }
			  });
		adapterInvoice.notifyDataSetChanged();
	}

/*	public void setStateInvoice() {
		switch (status) {
		case 1:
			// neu 1 = tao moi
			//initAddInvoice(getActivity());
			break;
		case 2:
			// neu 2 xem tt hoa don
			//viewInvoice(table_code);
			break;

		default:
			break;
		}
	}*/

	/**
	 * Thread chạy luôn luôn lấy DL từ server về và hiển thị lên listview
	 */
	private Runnable myThread = new Runnable() {

		@Override
		public void run() {
			while (ok) {
				try {
					Thread.sleep(5000);
					if (new ConfigurationServer(getActivity().getBaseContext()) .isOnline()) {
						listInvTmp.clear();
						MainPosActivity.beanDataAll.makeDataInvoice();
						listInvTmp = MainPosActivity.beanDataAll.lstInvoice;
						
						//listInvTmp = new WSGetInvoiceClientById(getActivity()).execute( (int) MainPosActivity.phoneNumber).get();
						/*for (Invoice invoice : listInvTmp) {
							invoiceDB.insertInvoice(invoice);
						}*/
						// ========set check true again in lst to display ====//
						
						  /*for (Invoice invoice : listInvTmp) {
						  invoiceDB.insertInvoice(invoice); }*/
						 
						// listInvDb = invoiceDB.getallInvoice();
						for (Invoice inv : listInvTmp) {
							if (lstinv_code.contains(inv.getInv_code())) {
								inv.setCheck(true);
							}
						}
					} /*else {
						listInvTmp.clear();
						listInvTmp = invoiceDB.getallInvoice();
						for (Invoice inv : listInvTmp) {
							if (lstinv_code.contains(inv.getInv_code())) {
								inv.setCheck(true);
							}
						}
						//Log.e("dohai", "invoice ko comang");
					}*/
					// ===========update screen=====================//
					handler.sendMessage(handler.obtainMessage());
					Thread.sleep(5000);
					//Log.e("dohai", "invoice comang");

				} catch (Exception e) {
					Log.i("Log : ", "Exception : " + e.getMessage());
				}
			}
		}

		// =====Handler to update ui thread======//
		private Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				notifyDataSetChanged(listInvTmp);
			};
		};

	};

	// =========================update from Handler===========================//
	private synchronized void notifyDataSetChanged(final List<Invoice> arr) {
		try {
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					if (listInvoiceCurrent != null) {
						listInvoiceCurrent.clear();
						listInvoiceCurrent.addAll(arr);
						if (adapterInvoice != null)
							adapterInvoice.notifyDataSetChanged();
					}

				}
			});
		} catch (Exception e) {

		}
	}

	private void viewInvoice(String table_code) {
		// b1: get invoice the same with table_code
		MainPosActivity.category_container.setVisibility(LinearLayout.VISIBLE);
		new WSGetInvByTable(table_code).execute();
	}

	// ----------Threading make data invoice_detail-------//
	private class WSGetInvByTable extends AsyncTask<Void, Void, Void> {
		private ProgressDialog progressDialog;
		private String code_table;
		private String inv_code = "";

		public WSGetInvByTable(String code_table) {
			this.code_table = code_table;
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
				try {
					// ---------------get String ------------------------//
					String URL = ConfigurationServer.getURLServer() + "wsgetInvByCodeTable.php";
					JSONObject json = new JSONObject();
					json.put("code_table", code_table);
					json.put("user_id", MainPosActivity.user.getUser_id());
					json.put("company_code", MainPosActivity.user.getCompanyCode());
					JSONArray jarr = mWS.connectWSPut_Get_Data(URL, json, "posts");
					for (int i = 0; i < jarr.length(); i++) {
						JSONObject element = jarr.getJSONObject(i);
						String inv_code1 = element.getString("inv_code");
						this.inv_code = inv_code1;
						if (!inv_code.equals("") && !(inv_code == null)) {
							this.inv_code = inv_code1;
						}
					}
				} catch (Exception e) {
				}
			} catch (Exception e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			InvoicePosFragment.this.table_code = "";
			if (!this.inv_code.equals("")) {
				new WSGetInvDetail(this.inv_code, this.code_table, 0, 0, 0)
						.execute();
			} else {
				Toast.makeText(getActivity(), "Load invoice detail failed",
						Toast.LENGTH_LONG).show();
			}
			if (progressDialog != null)
				progressDialog.dismiss();
		}

	}

	// ----------Threading make data invoice_detail-------//
	public class WSGetInvDetail extends AsyncTask<Void, Void, Void> {

		private ProgressDialog progressDialog;
		private String inv_code = "";
		private String code_table;
		private int committion;
		private int vat;
		private int flag = 0;

		public WSGetInvDetail(String invcode, String code_table, int commitsion, int vat, int flag) {
			this.inv_code = invcode;
			this.flag = flag;
			this.code_table = code_table;
			this.vat = vat;
			this.committion = commitsion;
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
		protected synchronized Void doInBackground(Void... params) {
			try {
				MainPosActivity.beanDataAll.makeDataInvDetail();
			} catch (Exception e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			if (flag == 0) {// true
				addf.addfragment(true, inv_code, code_table, MainPosActivity.beanDataAll.getlstInvDetail(inv_code), vat, committion);
			}

			if (flag == 1) {// false
				addf.addfragment(true, inv_code, code_table, MainPosActivity.beanDataAll.getlstInvDetail(inv_code), vat, committion);
			}

			if (progressDialog != null)
				progressDialog.dismiss();
		}

	}
	
	public class WSGetInvDetailUpdate extends AsyncTask<Void, Void, Void> {

		private ProgressDialog progressDialog;
		private String inv_code = "";
		private String code_table;
		private int committion;
		private int vat;
		private int flag = 0;
		private String oldInvCode;

		public WSGetInvDetailUpdate(String oldInvCode, String invcode, String code_table, int commitsion, int vat, int flag) {
			this.inv_code = invcode;
			this.flag = flag;
			this.code_table = code_table;
			this.vat = vat;
			this.committion = commitsion;
			this.oldInvCode = oldInvCode;
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
		protected synchronized Void doInBackground(Void... params) {
			try {
				MainPosActivity.beanDataAll.makeDataInvDetail();
			} catch (Exception e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			if (flag == 0) {// true
				addf.addfragment(true, inv_code, code_table, MainPosActivity.beanDataAll.getlstInvDetail(inv_code), vat, committion);
			}

			if (flag == 1) {// false
				addf.addfragment(true, inv_code, code_table, MainPosActivity.beanDataAll.getlstInvDetail(inv_code), vat, committion);
			}

			if (progressDialog != null)
				progressDialog.dismiss();
		}

	}

	/*private String genTableCode(ArrayList<String> arr) {
		String table_code = "";
		String simpDate = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		for (String string : arr) {
			if( TextUtils.isEmpty(table_code) ){
				table_code = table_code + string;
			}else{
				table_code = table_code +"_"+ string;
			}
		}
		return table_code + "_" + simpDate;
	}*/

	// =========spinner add key and value for it=============================//
	/*private ArrayList<String> getItable(List<Itable> listItable) {
		listValueItable = new ArrayList<String>();
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> hasMapItable = new HashMap<String, String>();
		for (Itable hashMap : listItable) {
			Log.i("Log Code_table: ", hashMap.getCode_table());
			Log.i("Log Description_table: ", hashMap.getDescription_table());
		}
		// check status table used ? No => put ->Hasmap ->Show in spinner
		for (int i = 0; i < listItable.size(); i++) {
			if (listItable.get(i).getStatus() != 2) {
				hasMapItable.put(listItable.get(i).getCode_table(), listItable.get(i).getDescription_table());
			}
		}
		// Loại bỏ bàn đang sử dụng: status = 2
		for (Itable	itable : listItable) {
			if( itable.getStatus() != 2 ){
				listValueItable.add(itable.getCode_table());
			}
		}
		for (Map.Entry<String, String> e : hasMapItable.entrySet()) {
			String key = e.getKey();
			String value = e.getValue();
			listValueItable.add(key);
			Collections.sort(listValueItable);
		}
		for (String	s : listValueItable) {
			Log.d("+++++listValueItable: ", s);
		}
		list.add(hasMapItable);
		return listValueItable;
	}*/

	// ------------------------callback from other fragment-----------------//
	public interface IAddFragment {
		public void addfragment(boolean status, String code_table, String inv_code, ArrayList<Invoice_Detail> lstInvDetail, int vat, int committion);
	}

	// -----------------------closed database when destroy------------------//
	@Override
	public void onDestroy() {
		super.onDestroy();
		//mDB.closeDB();
	}

	/**
	 * Tích vào nút chọn bàn ( Tác vụ tách hóa đơn )
	 * @param lstString : Danh sách các bàn trống cho phép người dùng chọn
	 */
	/*private void createDialog(ArrayList<String> lstString) {
		for (String str : lstString) {
			Log.d("___Danh sách bàn trống hiển thị lên Dialog choose table:", str);
		}
		final Dialog dialog = new Dialog(new ContextThemeWrapper(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar));
		dialog.setTitle(getResources().getString(R.string.selectchoosetable));
		// dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		final ArrayList<Table> lstTableTmp = new ArrayList<Table>();
		itemTable.clear();
		for (int i = 0; i < lstString.size(); i++) {
			Table tb = new Table();
			tb.setName(lstString.get(i));
			if (table_code != "" && lstString.get(i).contains(table_code))
				tb.setCheck(true);
			lstTableTmp.add(tb);
		}
		dialog.setContentView(R.layout.tablelayout);
		GridView gridView1 = (GridView) dialog.findViewById(R.id.gridViewTable);
		// dialog.setCancelable(false);
		ShowTableAdapter adapter = new ShowTableAdapter(getActivity(), lstTableTmp);
		gridView1.setAdapter(adapter);

		btnDiaSplitChooseTableOK = (ImageButton) dialog.findViewById(R.id.btnDiaSplitChooseTableOK);
		btnDiaSplitChooseTableCancel = (ImageButton) dialog.findViewById(R.id.btnDiaSplitChooseTableCancel);
		btnDiaSplitChooseTableOK.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				for (Table table : lstTableTmp) {
					if (table.isCheck()) {
						itemTable.add(table.getName());
					}
				}
				dialog.cancel();
			}
		});
		btnDiaSplitChooseTableCancel.setOnClickListener(new OnClickListener() {
			@Override			public void onClick(View arg0) {
				dialog.cancel();
			}
		});
		try {
			dialog.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
*/
	/*private void chooseTable(final ArrayList<String> data) {
		createDialog(data);
	}*/

	// --------------choose Free table -------------------------------------
/*	private void chooseFreeTable(final ArrayList<String> data) {
		// createDialog(data);

		final Dialog dialog = new Dialog(new ContextThemeWrapper(getActivity(),android.R.style.Theme_Holo_Light_NoActionBar));
		dialog.setContentView(R.layout.split_gridview_choose_table);
		dialog.setTitle(context.getResources().getString(R.string.freetable));

		gvDiaSplitChooseTable = (GridView) dialog.findViewById(R.id.gvDiaSplitChooseTable);

		btnDiaSplitChooseTableOK = (ImageButton) dialog.findViewById(R.id.btnDiaSplitChooseTableOK);
		btnDiaSplitChooseTableCancel = (ImageButton) dialog.findViewById(R.id.btnDiaSplitChooseTableCancel);

		ArrayAdapter<String> gvadapter = new ArrayAdapter<String>(new ContextThemeWrapper(getActivity(), android.R.style.Theme_Holo_Light_NoActionBar),
				android.R.layout.simple_list_item_checked, android.R.id.text1,
				data);

		gvDiaSplitChooseTable.setAdapter(gvadapter);
		gvadapter.notifyDataSetChanged();
		gvDiaSplitChooseTable.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long arg3) {
				CheckedTextView cbv = (CheckedTextView) v;
				if (!cbv.isChecked()) {
					cbv.setChecked(!cbv.isChecked());
					String exist = data.get(position);
					if (!lstItemFreeItable.contains(exist)) {
						lstItemFreeItable.add(exist);
					}
					Toast.makeText(getActivity(), data.get(position),
							Toast.LENGTH_SHORT).show();
				} else {
					cbv.setChecked(!cbv.isChecked());
					String exist = data.get(position);
					if (lstItemFreeItable.contains(exist)) {
						lstItemFreeItable.remove(exist);
					}
				}
			}
		});
		btnDiaSplitChooseTableOK.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (lstItemFreeItable.size() < lstItable.size()) {
					for (String str : lstItemFreeItable) {
						new WSRemoveAndUpdateStatus(getActivity(), str,invcode_name).execute();
					}
					dialog.cancel();
				} else {
					Toast.makeText(getActivity(), "Can not free table! check again", Toast.LENGTH_SHORT).show();
				}

			}
		});
		btnDiaSplitChooseTableCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialog.cancel();
			}
		});
		dialog.show();
	}*/

	// ==============method coupling invoice=============//
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		//ok = false;
		flagSound = false;
	}

	// --------------choose table -------------------------------------

	// ----------Threading make data invoice_detail-------//
	/*private class WSLoadTable extends AsyncTask<Void, Void, Void> {
		private ProgressDialog progressDialog;

		public WSLoadTable() {
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
				// Lấy tất cả các bàn thuộc id tầng
				MainPosActivity.beanDataAll.makeDataTableByFloor(ConfigurationServer.floor);
			} catch (Exception e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Sau khi lấy tất cả danh sách bàn thuộc các tầng cất vào BeanAllData 
			// Sau đó lấy ra sử dụng
			ArrayList<String> lstTableFree = getItable(MainPosActivity.beanDataAll.lstItableByFloor);
			chooseTable(lstTableFree);
			if (progressDialog != null)
				progressDialog.dismiss();
		}
	}*/

	// ----------Get all invoice_itable-------//
	/*private class WSLoadAllInvTable extends AsyncTask<Void, Void, Void> {
		private ProgressDialog progressDialog;
		private String inv_code;

		public WSLoadAllInvTable(Context mContext, String inv_code) {
			this.inv_code = inv_code;
			progressDialog = new ProgressDialog(mContext);
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
			lstItable.clear();
			try {
				String wsgetdetailinvdetail = ConfigurationServer.getURLServer() + "wsfree_getall_invoice_itable.php";
				JSONObject json = new JSONObject();
				json.put("inv_code", inv_code);
				JSONArray arrItem = new JSONArray();
				arrItem = mWS.connectWSPut_Get_Data(wsgetdetailinvdetail, json, "getallinvitable");
				if (arrItem != null) {
					for (int i = 0; i < arrItem.length(); i++) {
						JSONObject results = arrItem.getJSONObject(i);
						try {
							lstItable.add(results.getString("code_table"));
						} catch (Exception e) {
						}
					}
				}
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			ArrayAdapter<String> gvadapter = new ArrayAdapter<String>(new ContextThemeWrapper(getActivity(),
							android.R.style.Theme_Holo_Light_NoActionBar),
					android.R.layout.simple_list_item_checked,
					android.R.id.text1, lstItable);
			gvDiaSplitChooseTable.setAdapter(gvadapter);
			gvadapter.notifyDataSetChanged();
			if (progressDialog != null)
				progressDialog.dismiss();
		}
	}*/

	// ---------- Xoa ban trong bang invoice_itable va cap nhat status = 0 trong
	// bang itable-------//
	/*private class WSRemoveAndUpdateStatus extends AsyncTask<Void, Void, Void> {
		private String code_table;
		private String invcode_name;
		public WSRemoveAndUpdateStatus(Context mContext, String code_table, String invcode_name) {
			this.code_table = code_table;
			this.invcode_name = invcode_name;
		}

		@Override
		protected Void doInBackground(Void... params) {
			lstItable.clear();
			try {
				String wsgetdetailinvdetail = ConfigurationServer.getURLServer() + "wsremove_and_update_table.php";
				JSONObject json = new JSONObject();
				json.put("code_table", code_table);
				json.put("inv_code", invcode_name);
				JSONArray arrItem = new JSONArray();
				arrItem = mWS.connectWSPut_Get_Data(wsgetdetailinvdetail, json, "removeandupdate");
				if (arrItem != null) {
					JSONObject results = arrItem.getJSONObject(0);
					try {
						Log.i("Remove Table", results.getString("result"));
					} catch (Exception e) {
					}
				}
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			return null;
		}
	}*/
	
	@Override
	public void onClick(View v) {
		/*int id = v.getId();
		this.user_id = MainPosActivity.user_id;
		switch (id) {
		case R.id.addNewInvoice:
			initAddInvoice(getActivity());
			break;
		case R.id.btn_Destroy:
			destroyInv();
			break;
		case R.id.btn_split:
			splitInvoice();
			break;
		case R.id.btn_Coupling:// join invoice
			getActivity().startActivityForResult(new Intent(getActivity(), JoinInvoiceDialogActivity.class).putExtra(MainPosActivity.INVOICE_CODE, "ABC"), 1);
			break;
		case R.id.btnFreeTable:
			freeTable();
			break;
		case R.id.btn_volume:*/
			//if(flagSound = false){
				/*flagSound = true;
				Constaints.flagSound = true;
				Toast.makeText(getActivity(), "phat am thanh", 1).show();
				if (flagSound) {
					if (!soundThread.isAlive())
						soundThread.start();
				}*/
			//}
			/*break;
		default:
			break;
		}*/
	}

 
	/**
	 *  Nhấn vào nút tạo mới invoice
	 * @param context
	 */
	/*private void initAddInvoice(final Context context) {
		if (table_code != "") {
			itemTable.add(table_code);
		}
		final Dialog dialog = new Dialog(new ContextThemeWrapper(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar));
		dialog.setContentView(R.layout.invoice_add);
		dialog.setTitle(context.getResources().getString(R.string.createinvoice));
		dialog.setCancelable(false);
		dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		dialog.setCancelable(false);
		dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		final String simpDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());

		EditText edtCreateTime = (EditText) dialog.findViewById(R.id.Text_Time_In);
		edtCreateTime.setText(simpDate);

		btnchoosetable = (Button) dialog.findViewById(R.id.btnchoosetable);
		btnchoosetable.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// load all free table
				new WSLoadTable().execute();
			}
		});
*/
		/*-----------------add new an Invoice---------------*/
		/*ImageButton btnStart = (ImageButton) dialog.findViewById(R.id.btn_Add_New);
		btnStart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (itemTable.size() > 0) {
					EditText tvClient = (EditText) dialog.findViewById(R.id.Text_UserName);
					PrintInvoiceActivity.client = tvClient.getText().toString();
					EditText tvAddress = (EditText) dialog.findViewById(R.id.Text_Address);
					PrintInvoiceActivity.address = tvAddress.getText().toString();
					if (MainPosActivity.beanInvDetailTmpl != null) {
						MainPosActivity.beanInvDetailTmpl.removeList();
					}
					MainPosActivity.category_container.setVisibility(LinearLayout.VISIBLE);
					 ----add invoice to sqlite------------------- 
					String inv_code = genTableCode(itemTable);
					// String inv_code = mDB.addNewInvoice(table_code, table_id,
					// user_id, simpDate);
					dialog.dismiss();
					- ------------------add invoice to server------------------
					new WSAddNewInvoice(getActivity(), inv_code, 1, user_id, "", 0).execute();
					Log.d("add...", InvoicePosFragment.this.table_code);
					new WSAddInvTable(getActivity(), inv_code, MainPosActivity.user_id, itemTable).execute();
					-------------------add invoice to bean---------------------
					Invoice inv = new Invoice();
					inv.setInv_code(inv_code);
					inv.setInv_starttime(simpDate);
					inv.setUser_id(user_id);
					inv.setStatus(1);
					MainPosActivity.beanDataAll.addInvoice(inv);
					new WSUpdateItableStatus(InvoicePosFragment.this.context, 2, user_id, itemTable).execute();
					- -------------------- call activity items-----------------
					iaddMenu.addMenuActivity(inv_code);
					-----update screen list invoice ------------------------
					---------update status = 2 to itable-----------------------
					itemTable.clear();
					table_code = "";
				} else {
					Toast.makeText(getActivity(), "Please choosen table", Toast.LENGTH_SHORT).show();
				}

			}
		});

		ImageButton btnThoat = (ImageButton) dialog.findViewById(R.id.btn_Cancel);
		btnThoat.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				dialog.dismiss();
				itemTable.clear();
				table_code = "";
			}
		});
		dialog.show();
	}*/
	int committion;
	int vat;
	// ----destroy an invoice-----//
	/*private void destroyInv() {
		try {
			for (String inv_code : lstinv_code) {
				new WSDestroyInvoice(context, inv_code, 0, 0, 0, 0, 1).execute();
				for(Invoice list : listInvoiceCurrent){
					 committion = list.getCommision();
					 vat = list.getVat();
				}
				addf.addfragment(false, inv_code, inv_code,
						 MainPosActivity.beanDataAll.getlstInvDetail(inv_code), vat, committion);
				//addf.addfragment(false, inv_code, inv_code, new ArrayList<Invoice_Detail>(), 0, 0);
			}
			lstinv_code.clear();
		} catch (Exception e) {
		}//ddddddddd
	}
*/
	// ------------ Click Button Free table ----------------
	// Click vào nút giải phóng bàn
	/*private void freeTable() {
		if (lstinv_code.size() == 1) {
			for (String inv : lstinv_code) {
				new WSLoadAllInvTable(getActivity(), inv).execute();
				// lay dc danh sach table va hien thi no len
			}
			chooseFreeTable(lstItable);
		} else {

		}

	}*/

	// ======this method to split invoice===============//
	// Click vào nút Tách hóa đơn
	/*private void splitInvoice() {
		if (lstinv_code.size() == 1) {
			// Step 1: show new invoice
			// Step 2: transfer new items to this invoice
			// Step 3: insert database
			// Step 4: update
			// Step 5: refresh

			// ==========show new invoice============//
			String table_code = "";
			try {
				table_code = lstinv_code.get(0).substring(0, 6);
			} catch (Exception e) {
			}
			for (String	str : lstinv_code) {
				Log.i("___lstinv_code:", str);
			}
			if (!table_code.equals("")) {
				Toast.makeText(getActivity(), "table_code:"+table_code, Toast.LENGTH_LONG).show();
				addNewInvSplit(getActivity(), table_code, lstinv_code.get(0));
			}
		} else {
			Toast.makeText(getActivity(), "Please check an invoice only.", Toast.LENGTH_LONG).show();
		}
	}*/

	/**
	 * Sau khi chọn bàn nhấn nút Split table Mở dialog 
	 * @param context
	 * @param table_code : Lấy 6 ký tự đầu của mã bàn. Ví dụ: T2_B2_
	 * @param old_inv_code : Mã bàn đầy đủ: ví dụ: T2_B2_2002
	 */
	/*private void addNewInvSplit(final Context context, final String table_code, final String old_inv_code) {

		final Dialog dialog = new Dialog(new ContextThemeWrapper(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar));
		dialog.setContentView(R.layout.invoice_add);
		dialog.setTitle(context.getResources().getString(R.string.createinvoice));
		dialog.setCancelable(false);
		dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		final String simpDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		EditText edtCreateTime = (EditText) dialog.findViewById(R.id.Text_Time_In);
		final EditText edtCreateTimeOut = (EditText) dialog.findViewById(R.id.Text_Time_Out);

		edtCreateTime.setText(simpDate);
		// -----------SPINNER------------------------------------------//
		Button btnchoosetable = (Button) dialog
				.findViewById(R.id.btnchoosetable);
		btnchoosetable.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				itemTable.clear();
				InvoicePosFragment.this.table_code = "";
				//new WSLoadTable().execute();
				// chooseTable(listValueItable);
				
				//Cho phép tách hóa đơn thành nhiều hóa đơn khác nhau.
				// Do tách ra nhiều hóa đơn => Mỗi hóa đơn tương ứng với 1 bàn 
				// Bàn có thể nằm ở các tầng khác nhau
				ArrayList<Itable> lstAllItableOfAllFloor ;
				try {
					lstAllItableOfAllFloor = new WSGetAllTable(getActivity()).execute(ConfigurationServer.getURLServer() + "wsget_all_itable.php").get();
					ArrayList<String> lstTableFree = getItable(lstAllItableOfAllFloor);
					chooseTable(lstTableFree);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});
		-----------------add new an Invoice---------------
		ImageButton btnStart = (ImageButton) dialog.findViewById(R.id.btn_Add_New);
		btnStart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(itemTable.size() > 0){
					EditText tvClient = (EditText) dialog.findViewById(R.id.Text_UserName);
					PrintInvoiceActivity.client = tvClient.getText().toString();
					EditText tvAddress = (EditText) dialog.findViewById(R.id.Text_Address);
					PrintInvoiceActivity.address = tvAddress.getText().toString();
					dialog.dismiss();
	
					- -------------------- call activity items-----------------
					Intent isplit = new Intent(getActivity(), DialogSplitActivity.class);
					isplit.putExtra("inv_code", old_inv_code);
					isplit.putExtra("table_code", itemTable);
	
					getActivity().startActivityForResult(isplit, 1);
					-----update screen list invoice ------------------------
	
					if (new ConfigurationServer(getActivity()).isOnline()) {
						---------update status = 2 to itable-----------------------
					} else {
						Toast.makeText(InvoicePosFragment.this.context, "Network not found", Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.makeText(InvoicePosFragment.this.context, "Please choice table before!", Toast.LENGTH_SHORT).show();
				}  
				return;
			}
		});

		ImageButton btnThoat = (ImageButton) dialog.findViewById(R.id.btn_Cancel);
		btnThoat.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}
	*/
	/**
	 * khiemnd start
	 */
	
	private Runnable thread_sound = new Runnable() {
		@Override
		public void run() {
			while (flagSound) {
				try {
					if (lstVoice != null) {
						lstVoice.clear();
					}
					MainKitchenActivityHelper.getAllSound(mWS, lstVoice);
					ArrayList<Voice> voices = new ArrayList<Voice>();
					for(int i = 0; i < lstVoice.size(); i++){
						int type = lstVoice.get(i).getType();
						int iGroup = getGroup(group);
						if(type == 0){
							Voice voice = lstVoice.get(i);
							voices.add(voice);
						}else{
							if(type == iGroup){
								Voice voice = lstVoice.get(i);
								voices.add(voice);
							}
						}
					}
					
					MainKitchenActivityHelper.ReadSound(getActivity(), media, time, voices);
					Thread.sleep(1000);
				} catch (Exception e) {
					Log.i("TAG", "ERROR : " + e.getMessage());
				}
			}
		}
	};
	
	//new
	public int getGroup(String s){
		int i = 0;
		if("waiter".equals(s)){
			i = 1;
		}else if("cashier".equals(s)){
			i = 2;
		}else if("Manager".equals(s)){
			i = 3;
		}else if("kitchen".equals(s)){
			i = 4;
		}else if("client".equals(s)){
			i = 5;
		}
		return i;
	}
	/**
	 * khiemnd end
	 */
	
}
