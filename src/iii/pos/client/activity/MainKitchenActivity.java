package iii.pos.client.activity;

import iii.pos.client.R;
import iii.pos.client.adapter.CookAdapter;
import iii.pos.client.adapter.OrderInvoiceDetailAdapter;
import iii.pos.client.model.CookItem;
import iii.pos.client.model.Inv_Cook;
import iii.pos.client.model.Invoice_Detail;
import iii.pos.client.model.Voice;
import iii.pos.client.server.ConfigurationServer;
import iii.pos.client.server.ConfigurationWS;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class MainKitchenActivity extends Activity implements OnItemClickListener {

	private List<Inv_Cook> lstInvCookTmpl, lstInvCook;
	private List<Invoice_Detail> lstTmplInvDetail;
	private OrderInvoiceDetailAdapter adapter;
	private CookAdapter adapterCook;
	private List<CookItem> lstCookItem;
	private List<CookItem> lstTmpCook;
	private GridView gvOrderDisplay;
	private ConfigurationWS mWS;
	private boolean flagUI = false;
	private boolean flagSound = false;
	private ListView lvpvSP;
	private ArrayList<Voice> lstVoice;
	private MediaPlayer media;
	private long time;
	private Thread myUIThread;
	//private Thread soundThread;
	
	public static void show(Activity activity){
		Intent intent = new Intent(activity, MainKitchenActivity.class);
		activity.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		policyConnect();

		setContentView(R.layout.manhinhbep_nam_19_08);
		MediaPlayer mediaPlayer = new MediaPlayer();

		try {
			String tablename = "/bunxaocan.mp3";
			Uri myUri1 = Uri.parse(tablename);
			mediaPlayer.setDataSource(tablename);
			mediaPlayer.prepare();
			mediaPlayer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		init();
	}

	// --------------------POLICY------------------------------------------//
	private void policyConnect() {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		MainKitchenActivity.this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	private void init() {
		lstVoice = new ArrayList<Voice>();
		lstTmplInvDetail = new CopyOnWriteArrayList<Invoice_Detail>();
		lstTmpCook = new CopyOnWriteArrayList<CookItem>();
		lstInvCookTmpl = new CopyOnWriteArrayList<Inv_Cook>();
		lstInvCook = new CopyOnWriteArrayList<Inv_Cook>();
		lstCookItem = new CopyOnWriteArrayList<CookItem>();
		mWS = new ConfigurationWS(MainKitchenActivity.this);
		gvOrderDisplay = (GridView) findViewById(R.id.gvOrderDisplay);

		myUIThread = new Thread(myThread);
		//soundThread = new Thread(thread_sound);
		refeshAdapter();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {
		dialogShow(position);
	}

	private void refeshAdapterCook() {
		adapterCook = new CookAdapter(lstCookItem, MainKitchenActivity.this);
		lvpvSP.setAdapter(adapterCook);
		adapterCook.notifyDataSetChanged();
	}

	// -------------DIALOG SHOW----------------------------------------//
	private void dialogShow(final int position) {
		final int item_id = lstInvCook.get(position).getItem_id();
		final int user_id = 1;
		final Dialog dialog = new Dialog(MainKitchenActivity.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.row_cook_dialog);
		final ImageButton imgpvClose = (ImageButton) dialog.findViewById(R.id.imgpvClose);
		final ImageButton imgpvAdd = (ImageButton) dialog.findViewById(R.id.imgpvAdd);
		lvpvSP = (ListView) dialog.findViewById(R.id.lvpvSP);
		final TextView tvPVTitle = (TextView) dialog.findViewById(R.id.tvPVTitle);
		tvPVTitle.setText(lstInvCook.get(position).getName());
		new WSGetAllCookItem_id(item_id, user_id, MainKitchenActivity.this).execute();
		refeshAdapterCook();
		// -----------------CLOSE DIALOG-------------------------------//
		imgpvClose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(MainKitchenActivity.this)
						.setIcon(android.R.drawable.ic_dialog_info)
						.setTitle(getResources().getString(R.string.cookdalogtitle))
						.setPositiveButton(getResources().getString(R.string.dongy),
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface arg0, int arg1) {
										dialog.cancel();
									}
								})
						.setNegativeButton(getResources().getString(R.string.boqua), null)
						.show();
			}
		});
		// ------------------ADD QUANTITY------------------------------//
		imgpvAdd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final View addView = MainKitchenActivity.this.getLayoutInflater().inflate(R.layout.add1, null);
				final EditText etAlerOrderAmount = (EditText) addView.findViewById(R.id.etpvAmount);
				new AlertDialog.Builder(MainKitchenActivity.this)
						.setIcon(android.R.drawable.ic_input_add)
						.setTitle(getResources().getString(R.string.cookadd))
						.setView(addView)
						.setPositiveButton(
								getResources().getString(R.string.cookaddok),
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface arg0, int arg1) {
										String sl = etAlerOrderAmount.getText().toString();
										int quality = 0;
										try {
											quality = Integer.parseInt(sl);
										} catch (Exception e) {
										}
										if (sl != null || sl != "") {
											final String timein = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
											if (quality > 0 && quality <= lstInvCook.get(position).getQuantity1()) {
												new WSInsertCookItem(MainKitchenActivity.this, item_id, user_id, quality, timein, "Note", 1).execute();
												lstCookItem.add(new CookItem(item_id, user_id, quality, timein, "", 1, "note"));
												refeshAdapterCook();
												dialog.cancel();
											} else {
												new AlertDialog.Builder(
														MainKitchenActivity.this)
														.setIcon(android.R.drawable.ic_dialog_info)
														.setTitle("Nofiticate")
														.setMessage(getResources().getString(R.string.cookmsg) + lstInvCook.get(position).getQuantity1())
														.setPositiveButton("OK", null)
														.show();
											}
										}
									}
								})
						.setNegativeButton(getResources().getString(R.string.cookaddnok), null).show();
			}
		});

		dialog.show();
	}

	// -------------- F5 ADAPTER INVOICEDETAIL ------------------//
	private void refeshAdapter() {
		adapter = new OrderInvoiceDetailAdapter(lstInvCook, MainKitchenActivity.this);
		gvOrderDisplay.setAdapter(adapter);
		gvOrderDisplay.setOnItemClickListener(this);
		adapter.notifyDataSetChanged();
	}

	// --------------- ONRESUME --------------------------------//
	@Override
	protected void onResume() {
		super.onResume();
		flagUI = true;
		flagSound = true;
		if (flagUI) {
			if (!myUIThread.isAlive())
				myUIThread.start();

		}
		/*if (flagSound) {
			if (!soundThread.isAlive())
				soundThread.start();
		}*/
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		flagUI = false;
		//flagSound = false;
	}

	// ----------------- THREAD GET ALL SOUND IN VOICE TABLE -----//
	/*private Runnable thread_sound = new Runnable() {
		@Override
		public void run() {
			while (flagSound) {
				try {
					if (lstVoice != null) {
						lstVoice.clear();
					}
					MainKitchenActivityHelper.getAllSound(mWS, lstVoice);
					MainKitchenActivityHelper.ReadSound(MainKitchenActivity.this, media, time, lstVoice);
					Thread.sleep(1000);
				} catch (Exception e) {
					Log.i("TAG", "ERROR : " + e.getMessage());
				}
			}
		}
	};*/
	// ----------- THREAD GET ALL COOKINVOICEDETAIL -----------//
	private Runnable myThread = new Runnable() {
		@Override
		public void run() {
			while (flagUI) {
				try {
					getAllCookInvDetail();
					getAllCookItem();
					if (lstInvCookTmpl != null) {
						lstInvCookTmpl.clear();
						lstInvCookTmpl = checkSum(lstTmplInvDetail, lstTmpCook);
					}
					handler.sendMessage(handler.obtainMessage());
					//System.out.println("THREAD IS RUNNING");
					Thread.sleep(1000);
				} catch (Exception e) {
					Log.i("TAG", "ERROR : " + e.getMessage());
				}
			}
		}
	};
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					notifyDataSetChanged(lstInvCookTmpl);
				}
			});
		};
	};

	// -------------UPDATE LISTVIEW MSG--------------------//
	private void notifyDataSetChanged(final List<Inv_Cook> arr) {
		runOnUiThread(new Runnable() {
			public void run() {
				if (lstInvCook != null) {
					lstInvCook.clear();
					lstInvCook.addAll(arr);
					if (adapter != null)
						adapter.notifyDataSetChanged();
					System.gc();
				}
			}
		});
	}

	// ----------- GET ALL COOKINVDETAIL-------------//
	private void getAllCookInvDetail() {
		try {
			if (lstTmplInvDetail != null)
				lstTmplInvDetail.clear();
			JSONObject json = new JSONObject();
			json.put("key", ConfigurationServer.language_code);
			json.put("company_code", MainPosActivity.user.getCompanyCode());
			
			String strURL = ConfigurationServer.getURLServer()+ "wsgetcookinvdetail.php";
			JSONArray arrItem = new JSONArray();
			arrItem = mWS.connectWSPut_Get_Data(strURL, json, "cookinv");
			if (arrItem != null) {
				for (int i = 0; i < arrItem.length(); i++) {
					JSONObject results = arrItem.getJSONObject(i);
					Invoice_Detail invDetail = new Invoice_Detail();
					invDetail.setItem_id(results.getInt("item_id"));
					invDetail.setName(results.getString("name"));
					invDetail.setImgName(results.getString("imgname"));
					invDetail.setQuantity(results.getInt("total"));
					lstTmplInvDetail.add(invDetail);
				}
			}
		} catch (Exception e) {
		}
	}

	// --------- GET ALL COOKITEM---------------//
	private void getAllCookItem() {
		try {
			if (lstTmpCook != null) {
				lstTmpCook.clear();
			}
			String strURL = ConfigurationServer.getURLServer() + "wsgetcookitem.php";
			JSONObject json = new JSONObject();
			json.put("user_id", 1);
			json.put("company_code", MainPosActivity.user.getCompanyCode());
			JSONArray arrItem = new JSONArray();
			arrItem = mWS.connectWSPut_Get_Data(strURL, json, "gettotalcook");
			if (arrItem != null) {
				for (int i = 0; i < arrItem.length(); i++) {
					JSONObject results = arrItem.getJSONObject(i);
					CookItem cookitem = new CookItem();
					cookitem.setItem_id(results.getInt("item_id"));
					cookitem.setQuantity(results.getInt("total"));
					lstTmpCook.add(cookitem);
				}
			}
		} catch (Exception e) {
		}
	}

	// --------- CHECK INV EXIST IN LIST<CookItem>----------------
	private Inv_Cook checkExist(Invoice_Detail inv, List<CookItem> lstCookItem) {
		Inv_Cook inv_cook = new Inv_Cook();
		for (CookItem cookItem : lstCookItem) {
			if (inv.getItem_id() == cookItem.getItem_id()) {
				int newtotal = inv.getQuantity() - cookItem.getQuantity();
				inv_cook.setQuantity1(newtotal);
				inv_cook.setQuantity2(inv.getQuantity());
				inv_cook.setQuantity3(cookItem.getQuantity());
				inv_cook.setImgname(inv.getImgName());
				inv_cook.setName(inv.getName());
				inv_cook.setItem_id(inv.getItem_id());
				return inv_cook;
			}
		}
		return null;
	}

	// ------- -CONVERT TO INVCOOK -----------------
	private Inv_Cook convertToInvCook(Invoice_Detail inv) {
		Inv_Cook inv_cook = new Inv_Cook();
		if (inv != null) {
			inv_cook.setQuantity1(inv.getQuantity());
			inv_cook.setQuantity2(inv.getQuantity());
			inv_cook.setQuantity3(0);
			inv_cook.setImgname(inv.getImgName());
			inv_cook.setName(inv.getName());
			inv_cook.setItem_id(inv.getItem_id());
		}
		return inv_cook;
	}

	// ------- CHECK SUM ----------------
	private List<Inv_Cook> checkSum(List<Invoice_Detail> lstInv,
			List<CookItem> lstCook) {
		List<Inv_Cook> lstInvCook = new ArrayList<Inv_Cook>();
		for (Invoice_Detail inv_detail : lstInv) {
			Inv_Cook inv_cook = checkExist(inv_detail, lstCook);
			if (inv_cook != null) {
				lstInvCook.add(inv_cook);
			} else {
				lstInvCook.add(convertToInvCook(inv_detail));
			}
		}

		return lstInvCook;
	}

	// ------- GET ALL COOKITEM -------------
	public class WSGetAllCookItem_id extends AsyncTask<Void, Void, Void> {
		private ConfigurationWS mWS;
		private Context context;
		private int item_id;
		private int user_id;

		public WSGetAllCookItem_id(int item_id, int user_id, Context mcontext) {
			super();
			this.item_id = item_id;
			this.user_id = user_id;
			this.context = mcontext;
			mWS = new ConfigurationWS(mcontext);
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				if (lstCookItem != null) {
					lstCookItem.clear();
				}
				String strURL = ConfigurationServer.getURLServer() + "wsgetallcookitempos.php";
				JSONObject json = new JSONObject();
				json.put("item_id", item_id);
				json.put("user_id", user_id);
				json.put("company_code", MainPosActivity.user.getCompanyCode());
				JSONArray arrItem = new JSONArray();
				arrItem = mWS.connectWSPut_Get_Data(strURL, json, "cookallitem");
				if (arrItem != null) {
					for (int i = 0; i < arrItem.length(); i++) {
						JSONObject results = arrItem.getJSONObject(i);
						CookItem cookitem = new CookItem();
						try {
							cookitem.setId(results.getInt("id"));
						} catch (Exception e) {
						}
						try {
							cookitem.setItem_id(results.getInt("item_id"));
						} catch (Exception e) {
						}
						try {
							cookitem.setUser_id(results.getInt("user_id"));
						} catch (Exception e) {
						}
						try {
							cookitem.setQuantity(results.getInt("quantity"));
						} catch (Exception e) {
						}
						try {
							cookitem.setCook_createtime(results.getString("cook_createtime"));
						} catch (Exception e) {
						}
						try {
							cookitem.setCook_updatetime(results.getString("cook_updatetime"));
						} catch (Exception e) {
						}
						try {
							cookitem.setNotes(results.getString("notes"));
						} catch (Exception e) {
						}
						try {
							cookitem.setChecked(results.getInt("checked"));
						} catch (Exception e) {
						}

						lstCookItem.add(cookitem);
					}
				}
			} catch (Exception e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			adapterCook.notifyDataSetChanged();
		}
	}

	// -------- INSERT COOKITEM ---------------
	public class WSInsertCookItem extends AsyncTask<Void, Void, Void> {
		private ConfigurationWS mWS;
		private int item_id;
		private int user_id;
		private int quantity;
		private String cook_createtime;
		private int checked;
		private String notes;

		public WSInsertCookItem(Context mcontext, int item_id, int user_id, int quality, String cook_createtime, String notes, int check) {
			super();
			this.item_id = item_id;
			this.user_id = user_id;
			this.quantity = quality;
			this.notes = notes;
			this.checked = check;
			this.cook_createtime = cook_createtime;
			mWS = new ConfigurationWS(mcontext);
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				String strURL = ConfigurationServer.getURLServer() + "wsaddcookitem.php";
				JSONObject json = new JSONObject();
				json.put("item_id", item_id);
				json.put("user_id", user_id);
				json.put("quantity", quantity);
				json.put("cook_createtime", cook_createtime);
				json.put("notes", notes);
				json.put("checked", checked);
				json.put("company_code", MainPosActivity.user.getCompanyCode());
				JSONArray arrItem = new JSONArray();
				arrItem = mWS.connectWSPut_Get_Data(strURL, json, "posts");
				if (arrItem != null) {
					JSONObject results = arrItem.getJSONObject(0);
					Log.i("LOG", "Thanh cong: " + results.getString("result"));
				}
			} catch (Exception e) {
			}
			return null;
		} 

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
		}
	}

}
