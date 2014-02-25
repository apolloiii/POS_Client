/*package iii.pos.client.activity;

import iii.pos.client.R;
import iii.pos.client.adapter.ListSpendAdapter;
import iii.pos.client.adapter.SpinnerAdapte;
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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class ListSpendActivity extends Activity implements OnClickListener {
	private Spinner spnTypeSpend, spnGroupcleaning;
	private ListView lisviewTableSpend;
	private ListSpendAdapter adapterListSpend;
	private List<ListSpend> listSpend;

	private EditText edtcodeList, edtPrice, edtnameArise, edtNote;
	private Button btnAddListSpend, btnUpdateListSpend, btnDeleteListSpend;

	private String codeList, Price, nameArise, note;
	private ArrayList<ListSpend> listType = new ArrayList<ListSpend>();
	private ArrayList<ListSpend> listGroup = new ArrayList<ListSpend>();
	int id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listspending_layout);
		loadUI();
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}

	private void loadUI() {
		lisviewTableSpend = (ListView) findViewById(R.id.lisviewTableCost);
		spnTypeSpend = (Spinner) findViewById(R.id.spnTypecosts);
		spnGroupcleaning = (Spinner) findViewById(R.id.spnGroupWC);

		edtcodeList = (EditText) findViewById(R.id.edtcodeList);
		edtPrice = (EditText) findViewById(R.id.edtPrice);
		edtnameArise = (EditText) findViewById(R.id.edtnameArise);
		edtNote = (EditText) findViewById(R.id.edtNote);

		btnAddListSpend = (Button) findViewById(R.id.btnAddListCost);
		btnAddListSpend.setOnClickListener(this);

		btnUpdateListSpend = (Button) findViewById(R.id.btnUpdateListCost);
		btnUpdateListSpend.setOnClickListener(this);

		btnDeleteListSpend = (Button) findViewById(R.id.btnDeleteListCost);
		btnDeleteListSpend.setOnClickListener(this);

		loadData();

	}

	private void loadData() {
		setDataSpinner();
		new WSgetDataArise(ListSpendActivity.this).execute();

		lisviewTableSpend.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {

				Toast.makeText(ListSpendActivity.this, "Pos + " + pos, 1)
						.show();
				btnDeleteListSpend.setEnabled(true);
				btnUpdateListSpend.setEnabled(true);

				String code = listSpend.get(pos).getCodeList();
				String name = listSpend.get(pos).getNameCost();
				String price_cost = listSpend.get(pos).getPrice();
				String description_cost = listSpend.get(pos).getNotes();
				String group = listSpend.get(pos).getGroupCost();
				String type = listSpend.get(pos).getTypeCost();
				id = listSpend.get(pos).getId();

				edtcodeList.setText(code);
				edtnameArise.setText(name);
				edtPrice.setText(price_cost);
				edtNote.setText(description_cost);

			}
		});
	}

	String type_cost, group_cost;
	int type_cost_id, group_cost_id;
	// ------------------setadapter spinnet-------------------------//
	private void setDataSpinner() {
		new WSgetDataTypeCost(ListSpendActivity.this).execute();
		new WSgetDataGroupCost(ListSpendActivity.this).execute();
		
		spnTypeSpend.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				type_cost =listType.get(pos).getTypeCost();
				type_cost_id = listType.get(pos).getId();

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		spnGroupcleaning
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int pos, long arg3) {
						group_cost = listGroup.get(pos).getGroupCost();
						group_cost_id = listGroup.get(pos).getId();

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});
	}

	// -------------create adapter----------------------------------//
	public void setAdapter() {
		adapterListSpend = new ListSpendAdapter(ListSpendActivity.this,
				R.layout.listspending_layout, listSpend);
		lisviewTableSpend.setAdapter(adapterListSpend);
		adapterListSpend.notifyDataSetChanged();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnAddListCost:
			checkInsert();
			break;
		case R.id.btnUpdateListCost:
			checkUpdate();

			break;
		case R.id.btnDeleteListCost:
			for (ListSpend cost : listSpend) {
				if (cost.isCheck()) {
					new WSDeleteDataCost(cost.getId(), ListSpendActivity.this)
							.execute();
				}
			}
			break;

		default:
			break;
		}

	}

	// ---------------------------Get data----------------------------//

	private class WSgetDataArise extends AsyncTask<Void, Void, Void> {
		private ProgressDialog progressDialog;
		private ConfigurationWS mWS;
		private Context context;

		public WSgetDataArise(Context mcontext) {
			progressDialog = new ProgressDialog(mcontext);
			mWS = new ConfigurationWS(mcontext);
			this.context = mcontext;
			listSpend = new CopyOnWriteArrayList<ListSpend>();
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
			setAdapter();
			if (progressDialog != null)
				progressDialog.dismiss();
		}

	}

	// -----------------------check Insert--------------------//

	private void checkInsert() {
		codeList = edtcodeList.getText().toString();
		Price = edtPrice.getText().toString();
		nameArise = edtnameArise.getText().toString();
		note = edtNote.getText().toString();
		boolean information = true;
		if (codeList.equalsIgnoreCase("") || Price.equalsIgnoreCase("")
				|| nameArise.equalsIgnoreCase("") || note.equalsIgnoreCase("")) {
			information = false;
			Toast.makeText(ListSpendActivity.this,
					getString(R.string.noticepress), 1).show();
		} else if (information) {
			try {
				new WSInsertDataCost(codeList, Price, nameArise, note,
						type_cost_id, group_cost_id, ListSpendActivity.this)
						.execute();
			} catch (Exception e) {
			}
		}

	}

	// ----------------------------Insert data----------------------------//

	private class WSInsertDataCost extends AsyncTask<Void, Void, Void> {
		private String codeList;
		private String Price;
		private String nameArise;
		private String note;
		private int typeCost;
		private int groupCost;
		private Context context;
		public int kq = -1;
		private ConfigurationWS mWS_Insert;
		private ProgressDialog progressDialog;

		public WSInsertDataCost(String codeList, String price,
				String nameArise, String note, int typeCost, int groupCost,
				Context mcontext) {
			super();
			this.codeList = codeList;
			this.Price = price;
			this.nameArise = nameArise;
			this.note = note;
			this.typeCost = typeCost;
			this.groupCost = groupCost;
			this.context = mcontext;
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
				listSpend.clear();
				String URL = ConfigurationServer.getURLServer()
						+ "wsadd_listcost.php";
				JSONObject json = new JSONObject();
				json.put("codeList", codeList);
				json.put("Price", Price);
				json.put("nameArise", nameArise);
				json.put("note", note);
				json.put("typeCost", typeCost);
				json.put("groupCost", groupCost);

				JSONArray arrItem = new JSONArray();
				arrItem = mWS_Insert.connectWSPut_Get_Data(URL, json, "posts");

				if (arrItem != null) {
					for (int i = 0; i < arrItem.length(); i++) {
						JSONObject element = arrItem.getJSONObject(i);
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
			adapterListSpend.notifyDataSetChanged();
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

	// --------------------------Check for update--------------------------//
	private void checkUpdate() {
		codeList = edtcodeList.getText().toString();
		Price = edtPrice.getText().toString();
		nameArise = edtnameArise.getText().toString();
		note = edtNote.getText().toString();
		boolean information = true;
		if (information) {
			try {
				new WSUpdateDataCost(id, codeList, Price, nameArise, note,
						type_cost_id, group_cost_id, ListSpendActivity.this)
						.execute();
			} catch (Exception e) {
				// TODO: handle exception
			}
		} else {
		}

	}

	// ---------------------------function Update----------------------------//

	private class WSUpdateDataCost extends AsyncTask<Void, Void, Void> {
		private String codeList;
		private String Price;
		private String nameArise;
		private String note;
		private int typeCost;
		private int groupCost;
		private int id;
		private Context context;
		public int kq = -1;
		private ConfigurationWS mWS_Insert;
		private ProgressDialog progressDialog;

		public WSUpdateDataCost(int id, String codeList, String price,
				String nameArise, String note, int typeCost, int groupCost,
				Context mcontext) {
			super();
			this.codeList = codeList;
			this.Price = price;
			this.nameArise = nameArise;
			this.note = note;
			this.typeCost = typeCost;
			this.groupCost = groupCost;
			this.context = mcontext;
			this.id = id;
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
				listSpend.clear();
				String URL = ConfigurationServer.getURLServer()
						+ "wsupdate_listcost.php";
				JSONObject json = new JSONObject();
				json.put("codeList", codeList);
				json.put("Price", Price);
				json.put("nameArise", nameArise);
				json.put("note", note);
				json.put("typeCost", typeCost);
				json.put("groupCost", groupCost);
				json.put("id", id);

				JSONArray arrItem = new JSONArray();
				arrItem = mWS_Insert.connectWSPut_Get_Data(URL, json, "posts");

				if (arrItem != null) {
					for (int i = 0; i < arrItem.length(); i++) {
						JSONObject element = arrItem.getJSONObject(i);
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
			adapterListSpend.notifyDataSetChanged();
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

	private class WSDeleteDataCost extends AsyncTask<Void, Void, Void> {
		private int id;
		private Context context;
		public int kq = -1;
		private ConfigurationWS mWS_Insert;
		private ProgressDialog progressDialog;

		public WSDeleteDataCost(int id, Context mcontext) {
			super();
			this.context = mcontext;
			this.id = id;
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
				listSpend.clear();
				String URL = ConfigurationServer.getURLServer()
						+ "wsdelete_listcost.php";
				JSONObject json = new JSONObject();
				json.put("id", id);

				JSONArray arrItem = new JSONArray();
				arrItem = mWS_Insert.connectWSPut_Get_Data(URL, json, "posts");

				if (arrItem != null) {
					for (int i = 0; i < arrItem.length(); i++) {
						JSONObject element = arrItem.getJSONObject(i);
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
			adapterListSpend.notifyDataSetChanged();
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
	
	
	//-----------------------GET Name Type FROM shenlong_type_otherarise---------------------------//
			private class WSgetDataTypeCost extends AsyncTask<Void, Void, Void> {
				private ProgressDialog progressDialog;
				private ConfigurationWS mWS;
				private Context context;

				public WSgetDataTypeCost(Context mcontext) {
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
							listType.clear();
							// ---------------get String ------------------------//
							String URL = ConfigurationServer.getURLServer()
									+ "wsgettype_listspend.php";
							JSONObject json = new JSONObject();
							JSONArray jarr = mWS.connectWSPut_Get_Data(URL, json,
									"posts");
							for (int i = 0; i < jarr.length(); i++) {
								JSONObject element = jarr.getJSONObject(i);
								ListSpend listSpend = new ListSpend();
								listSpend.setTypeCost(element.getString("namearise"));
								listSpend.setId(element.getInt("id"));
								listType.add(listSpend);
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
					for (ListSpend listSpend : listType) {
						String type = listSpend.getTypeCost();
						list.add(type);
					}
					SpinnerAdapte adapter = new SpinnerAdapte(ListSpendActivity.this, list);
					spnTypeSpend.setAdapter(adapter);
					if (progressDialog != null)
						progressDialog.dismiss();
				}
			}
			
			//-----------------------GET Name Group FROM shenlong_group_otherarise---------------------------//
			private class WSgetDataGroupCost extends AsyncTask<Void, Void, Void> {
				private ProgressDialog progressDialog;
				private ConfigurationWS mWS;
				private Context context;

				public WSgetDataGroupCost(Context mcontext) {
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
							listGroup.clear();
							// ---------------get String ------------------------//
							String URL = ConfigurationServer.getURLServer()
									+ "wsgetgroup_listspend.php";
							JSONObject json = new JSONObject();
							JSONArray jarr = mWS.connectWSPut_Get_Data(URL, json,
									"posts");
							for (int i = 0; i < jarr.length(); i++) {
								JSONObject element = jarr.getJSONObject(i);
								ListSpend listSpend = new ListSpend();
								listSpend.setGroupCost(element.getString("namearise"));
								listSpend.setId(element.getInt("id"));
								listGroup.add(listSpend);
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
					for (ListSpend listSpend : listGroup) {
						String group = listSpend.getGroupCost();
						list.add(group);
					}
					SpinnerAdapte adapter = new SpinnerAdapte(ListSpendActivity.this, list);
					spnGroupcleaning.setAdapter(adapter);
					if (progressDialog != null)
						progressDialog.dismiss();
				}
			}
	
	
	
	@Override
	public void onBackPressed() {
		startActivity(new Intent(ListSpendActivity.this, InvoiceSpendActivity.class));
		super.onBackPressed();
	}
}
*/