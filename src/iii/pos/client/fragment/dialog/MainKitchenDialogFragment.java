package iii.pos.client.fragment.dialog;
//package iii.pos.coffe.fragment.dialog;
//
//import iii.pos.coffe.R;
//import iii.pos.coffe.adapter.CookAdapter;
//import iii.pos.coffe.adapter.OrderInvoiceDetailAdapter;
//import iii.pos.coffe.model.CookItem;
//import iii.pos.coffe.model.Inv_Cook;
//import iii.pos.coffe.model.Invoice_Detail;
//import iii.pos.coffe.model.Voice;
//import iii.pos.coffe.server.ConfigurationServer;
//import iii.pos.coffe.server.ConfigurationWS;
//import iii.pos.coffe.wsclass.WSVoiceService_UpdateVoice;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.concurrent.CopyOnWriteArrayList;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.media.MediaPlayer;
//import android.media.MediaPlayer.OnCompletionListener;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.os.Environment;
//import android.os.Handler;
//import android.os.Message;
//import android.os.StrictMode;
//import android.os.Vibrator;
//import android.support.v4.app.DialogFragment;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.EditText;
//import android.widget.GridView;
//import android.widget.ImageButton;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//public class MainKitchenDialogFragment extends DialogFragment implements OnItemClickListener{
//	public static class Builder{
//		Activity mActivity;
//		
//		Bundle bundle;
//		
//		public Builder(Activity mActivity){
//			this.mActivity = mActivity;
//			bundle = new Bundle();
//		}
//		
//		public MainKitchenDialogFragment build(){
//			MainKitchenDialogFragment dialog = new MainKitchenDialogFragment();
//			dialog.setArguments(bundle);
//			return dialog;
//		}
//	}
//	
//	private List<Inv_Cook> lstInvCookTmpl, lstInvCook;
//	private List<Invoice_Detail> lstTmplInvDetail;
//	private OrderInvoiceDetailAdapter adapter;
//	private CookAdapter adapterCook;
//	private List<CookItem> lstCookItem;
//	private List<CookItem> lstTmpCook;
//	private GridView gvOrderDisplay;
//	private ConfigurationWS mWS;
//	private boolean flagUI = false;
//	private boolean flagSound = false;
//	private ListView lvpvSP;
//	private ArrayList<Voice> lstVoice;
//	private MediaPlayer media;
//	private long time;
//	private Thread myUIThread;
//	private Thread soundThread;
//	
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
//		final View v = inflater.inflate(R.layout.manhinhbep_nam_19_08, null);
//		policyConnect();
//		MediaPlayer mediaPlayer = new MediaPlayer();
//
//		try {
//			String tablename = "/bunxaocan.mp3";
//			Uri myUri1 = Uri.parse(tablename);
//			mediaPlayer.setDataSource(tablename);
//			mediaPlayer.prepare();
//			mediaPlayer.start();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		init(v);
//		return v;
//	}
//	
//	@Override
//	public void onDismiss(DialogInterface dialog) {
//		super.onDismiss(dialog);
//	}
//	
//	private void policyConnect() {
//		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//		StrictMode.setThreadPolicy(policy);
//	}
//
//	private void init(View v) {
//		lstVoice = new ArrayList<Voice>();
//		lstTmplInvDetail = new CopyOnWriteArrayList<Invoice_Detail>();
//		lstTmpCook = new CopyOnWriteArrayList<CookItem>();
//		lstInvCookTmpl = new CopyOnWriteArrayList<Inv_Cook>();
//		lstInvCook = new CopyOnWriteArrayList<Inv_Cook>();
//		lstCookItem = new CopyOnWriteArrayList<CookItem>();
//		mWS = new ConfigurationWS(getActivity());
//		gvOrderDisplay = (GridView)v.findViewById(R.id.gvOrderDisplay);
//
//		myUIThread = new Thread(myThread);
//		soundThread = new Thread(thread_sound);
//		refeshAdapter();
//	}
//
//	@Override
//	public void onItemClick(AdapterView<?> arg0, View arg1, final int position,
//			long arg3) {
//		dialogShow(position);
//	}
//
//	// ------------------ SET ADAPTER ---------------------------------//
//	private void refeshAdapterCook() {
//		adapterCook = new CookAdapter(lstCookItem, getActivity());
//		lvpvSP.setAdapter(adapterCook);
//		adapterCook.notifyDataSetChanged();
//	}
//
//	// -------------DIALOG SHOW----------------------------------------//
//	private void dialogShow(final int position) {
//		final int item_id = lstInvCook.get(position).getItem_id();
//		final int user_id = 1;
//		final Dialog dialog = new Dialog(getActivity());
//		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		dialog.setContentView(R.layout.row_cook_dialog);
//		final ImageButton imgpvClose = (ImageButton) dialog.findViewById(R.id.imgpvClose);
//		final ImageButton imgpvAdd = (ImageButton) dialog.findViewById(R.id.imgpvAdd);
//		lvpvSP = (ListView) dialog.findViewById(R.id.lvpvSP);
//		final TextView tvPVTitle = (TextView) dialog.findViewById(R.id.tvPVTitle);
//		tvPVTitle.setText(lstInvCook.get(position).getName());
//		new WSGetAllCookItem_id(item_id, user_id, getActivity()).execute();
//		refeshAdapterCook();
//		// -----------------CLOSE DIALOG-------------------------------//
//		imgpvClose.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				new AlertDialog.Builder(getActivity())
//						.setIcon(android.R.drawable.ic_dialog_info)
//						.setTitle(getResources().getString(R.string.cookdalogtitle))
//						.setPositiveButton(getResources().getString(R.string.dongy),
//								new DialogInterface.OnClickListener() {
//									@Override
//									public void onClick(DialogInterface arg0,int arg1) {
//										dialog.cancel();
//									}
//								})
//						.setNegativeButton(getResources().getString(R.string.boqua), null)
//						.show();
//			}
//		});
//		// ------------------ADD QUANTITY------------------------------//
//		imgpvAdd.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				final View addView = getActivity().getLayoutInflater()
//						.inflate(R.layout.add1, null);
//				final EditText etAlerOrderAmount = (EditText) addView
//						.findViewById(R.id.etpvAmount);
//				new AlertDialog.Builder(getActivity())
//						.setIcon(android.R.drawable.ic_input_add)
//						.setTitle(getResources().getString(R.string.cookadd))
//						.setView(addView)
//						.setPositiveButton(
//								getResources().getString(R.string.cookaddok),
//								new DialogInterface.OnClickListener() {
//									@Override
//									public void onClick(DialogInterface arg0, int arg1) {
//										String sl = etAlerOrderAmount.getText().toString();
//										int quality = 0;
//										try {
//											quality = Integer.parseInt(sl);
//										} catch (Exception e) {
//										}
//										if (sl != null || sl != "") {
//											final String timein = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
//											if (quality > 0 && quality <= lstInvCook.get(position).getQuantity1()) {
//												new WSInsertCookItem(getActivity(), item_id, user_id, quality, timein, "Note", 1).execute();
//												lstCookItem.add(new CookItem(item_id, user_id, quality, timein, "", 1, "note"));
//												refeshAdapterCook();
//												dialog.cancel();
//											} else {
//												new AlertDialog.Builder(getActivity())
//														.setIcon(android.R.drawable.ic_dialog_info)
//														.setTitle("Nofiticate")
//														.setMessage(getResources().getString(R.string.cookmsg) + lstInvCook.get(position).getQuantity1())
//														.setPositiveButton("OK", null)
//														.show();
//											}
//										}
//									}
//								})
//						.setNegativeButton(getResources().getString(R.string.cookaddnok),null).show();
//			}
//		});
//
//		dialog.show();
//	}
//
//	// -------------- F5 ADAPTER INVOICEDETAIL ------------------//
//	private void refeshAdapter() {
//
//		adapter = new OrderInvoiceDetailAdapter(lstInvCook, getActivity());
//		gvOrderDisplay.setAdapter(adapter);
//		gvOrderDisplay.setOnItemClickListener(this);
//		adapter.notifyDataSetChanged();
//	}
//
//	// --------------- ONRESUME --------------------------------//
//	@Override
//	public void onResume() {
//		super.onResume();
//		flagUI = true;
//		flagSound = true;
//		if (flagUI) {
//			if (!myUIThread.isAlive())
//				myUIThread.start();
//
//		}
//		if (flagSound) {
//			if (!soundThread.isAlive())
//				soundThread.start();
//		}
//	}
//
//	@Override
//	public void onDestroy() {
//		super.onDestroy();
//		flagUI = false;
//		flagSound = false;
//	}
//
//	// ----------------- THREAD GET ALL SOUND IN VOICE TABLE -----//
//	private Runnable thread_sound = new Runnable() {
//		@Override
//		public void run() {
//			while (flagSound) {
//				try {
//					if (lstVoice != null) {
//						lstVoice.clear();
//					}
//					getAllSound();
//					ReadSound(lstVoice);
//					Thread.sleep(1000);
//				} catch (Exception e) {
//					Log.i("TAG", "ERROR : " + e.getMessage());
//				}
//			}
//		}
//	};
//	// ----------- THREAD GET ALL COOKINVOICEDETAIL -----------//
//	private Runnable myThread = new Runnable() {
//		@Override
//		public void run() {
//			while (flagUI) {
//				try {
//					getAllCookInvDetail();
//					getAllCookItem();
//					if (lstInvCookTmpl != null) {
//						lstInvCookTmpl.clear();
//						lstInvCookTmpl = checkSum(lstTmplInvDetail, lstTmpCook);
//					}
//					handler.sendMessage(handler.obtainMessage());
//					System.out.println("THREAD IS RUNNING");
//					Thread.sleep(1000);
//				} catch (Exception e) {
//					Log.i("TAG", "ERROR : " + e.getMessage());
//				}
//			}
//		}
//	};
//	private Handler handler = new Handler() {
//		@Override
//		public void handleMessage(Message msg) {
//			getActivity().runOnUiThread(new Runnable() {
//				@Override
//				public void run() {
//					notifyDataSetChanged(lstInvCookTmpl);
//				}
//			});
//		};
//	};
//
//	// -------------UPDATE LISTVIEW MSG--------------------//
//	private void notifyDataSetChanged(final List<Inv_Cook> arr) {
//		getActivity().runOnUiThread(new Runnable() {
//			public void run() {
//				if (lstInvCook != null) {
//					lstInvCook.clear();
//					lstInvCook.addAll(arr);
//					if (adapter != null)
//						adapter.notifyDataSetChanged();
//					System.gc();
//				}
//			}
//		});
//	}
//
//	// ----------- GET ALL COOKINVDETAIL-------------//
//	private void getAllCookInvDetail() {
//		try {
//			if (lstTmplInvDetail != null)
//				lstTmplInvDetail.clear();
//			JSONObject json = new JSONObject();
//			json.put("key", ConfigurationServer.language_code);
//			String strURL = ConfigurationServer.getURLServer()
//					+ "wsgetcookinvdetail.php";
//			JSONArray arrItem = new JSONArray();
//			arrItem = mWS.connectWSPut_Get_Data(strURL, json, "cookinv");
//			if (arrItem != null) {
//				for (int i = 0; i < arrItem.length(); i++) {
//					JSONObject results = arrItem.getJSONObject(i);
//					Invoice_Detail invDetail = new Invoice_Detail();
//					invDetail.setItem_id(results.getInt("item_id"));
//					invDetail.setName(results.getString("name"));
//					invDetail.setImgName(results.getString("imgname"));
//					invDetail.setQuantity(results.getInt("total"));
//					lstTmplInvDetail.add(invDetail);
//				}
//			}
//		} catch (Exception e) {
//		}
//	}
//
//	// --------- GET ALL COOKITEM---------------//
//	private void getAllCookItem() {
//		try {
//			if (lstTmpCook != null) {
//				lstTmpCook.clear();
//			}
//			String strURL = ConfigurationServer.getURLServer()
//					+ "wsgetcookitem.php";
//			JSONObject json = new JSONObject();
//			json.put("user_id", 1);
//			JSONArray arrItem = new JSONArray();
//			arrItem = mWS.connectWSPut_Get_Data(strURL, json, "gettotalcook");
//			if (arrItem != null) {
//				for (int i = 0; i < arrItem.length(); i++) {
//					JSONObject results = arrItem.getJSONObject(i);
//					CookItem cookitem = new CookItem();
//					cookitem.setItem_id(results.getInt("item_id"));
//					cookitem.setQuantity(results.getInt("total"));
//					lstTmpCook.add(cookitem);
//
//				}
//			}
//		} catch (Exception e) {
//		}
//	}
//
//	// --------- CHECK INV EXIST IN LIST<CookItem>----------------
//	private Inv_Cook checkExist(Invoice_Detail inv, List<CookItem> lstCookItem) {
//		Inv_Cook inv_cook = new Inv_Cook();
//		for (CookItem cookItem : lstCookItem) {
//			if (inv.getItem_id() == cookItem.getItem_id()) {
//				int newtotal = inv.getQuantity() - cookItem.getQuantity();
//				inv_cook.setQuantity1(newtotal);
//				inv_cook.setQuantity2(inv.getQuantity());
//				inv_cook.setQuantity3(cookItem.getQuantity());
//				inv_cook.setImgname(inv.getImgName());
//				inv_cook.setName(inv.getName());
//				inv_cook.setItem_id(inv.getItem_id());
//				return inv_cook;
//			}
//		}
//		return null;
//	}
//
//	// ------- -CONVERT TO INVCOOK -----------------
//	private Inv_Cook convertToInvCook(Invoice_Detail inv) {
//		Inv_Cook inv_cook = new Inv_Cook();
//		if (inv != null) {
//			inv_cook.setQuantity1(inv.getQuantity());
//			inv_cook.setQuantity2(inv.getQuantity());
//			inv_cook.setQuantity3(0);
//			inv_cook.setImgname(inv.getImgName());
//			inv_cook.setName(inv.getName());
//			inv_cook.setItem_id(inv.getItem_id());
//		}
//		return inv_cook;
//	}
//
//	// ------- CHECK SUM ----------------
//	private List<Inv_Cook> checkSum(List<Invoice_Detail> lstInv,
//			List<CookItem> lstCook) {
//		List<Inv_Cook> lstInvCook = new ArrayList<Inv_Cook>();
//		for (Invoice_Detail inv_detail : lstInv) {
//			Inv_Cook inv_cook = checkExist(inv_detail, lstCook);
//			if (inv_cook != null) {
//				lstInvCook.add(inv_cook);
//			} else {
//				lstInvCook.add(convertToInvCook(inv_detail));
//			}
//		}
//
//		return lstInvCook;
//	}
//
//	// ------- GET ALL COOKITEM -------------
//	public class WSGetAllCookItem_id extends AsyncTask<Void, Void, Void> {
//		private ConfigurationWS mWS;
//		private Context context;
//		private int item_id;
//		private int user_id;
//
//		public WSGetAllCookItem_id(int item_id, int user_id, Context mcontext) {
//			super();
//			this.item_id = item_id;
//			this.user_id = user_id;
//			this.context = mcontext;
//			mWS = new ConfigurationWS(mcontext);
//		}
//
//		@Override
//		protected Void doInBackground(Void... params) {
//			try {
//				if (lstCookItem != null) {
//					lstCookItem.clear();
//				}
//				String strURL = ConfigurationServer.getURLServer()
//						+ "wsgetallcookitempos.php";
//				JSONObject json = new JSONObject();
//				json.put("item_id", item_id);
//				json.put("user_id", user_id);
//				JSONArray arrItem = new JSONArray();
//				arrItem = mWS
//						.connectWSPut_Get_Data(strURL, json, "cookallitem");
//				if (arrItem != null) {
//					for (int i = 0; i < arrItem.length(); i++) {
//						JSONObject results = arrItem.getJSONObject(i);
//						CookItem cookitem = new CookItem();
//						try {
//							cookitem.setId(results.getInt("id"));
//						} catch (Exception e) {
//						}
//						try {
//							cookitem.setItem_id(results.getInt("item_id"));
//						} catch (Exception e) {
//						}
//						try {
//							cookitem.setUser_id(results.getInt("user_id"));
//						} catch (Exception e) {
//						}
//						try {
//							cookitem.setQuantity(results.getInt("quantity"));
//						} catch (Exception e) {
//						}
//						try {
//							cookitem.setCook_createtime(results
//									.getString("cook_createtime"));
//						} catch (Exception e) {
//						}
//						try {
//							cookitem.setCook_updatetime(results
//									.getString("cook_updatetime"));
//						} catch (Exception e) {
//						}
//						try {
//							cookitem.setNotes(results.getString("notes"));
//						} catch (Exception e) {
//						}
//						try {
//							cookitem.setChecked(results.getInt("checked"));
//						} catch (Exception e) {
//						}
//
//						lstCookItem.add(cookitem);
//					}
//				}
//			} catch (Exception e) {
//			}
//			return null;
//		}
//
//		@Override
//		protected void onPostExecute(Void result) {
//			super.onPostExecute(result);
//			Toast.makeText(context, "THUAN" + lstTmpCook.size(),
//					Toast.LENGTH_SHORT).show();
//			adapterCook.notifyDataSetChanged();
//		}
//	}
//
//	// -------- INSERT COOKITEM ---------------
//	public class WSInsertCookItem extends AsyncTask<Void, Void, Void> {
//		private ConfigurationWS mWS;
//		private int item_id;
//		private int user_id;
//		private int quantity;
//		private String cook_createtime;
//		private int checked;
//		private String notes;
//
//		public WSInsertCookItem(Context mcontext, int item_id, int user_id,
//				int quality, String cook_createtime, String notes, int check) {
//			super();
//			this.item_id = item_id;
//			this.user_id = user_id;
//			this.quantity = quality;
//			this.notes = notes;
//			this.checked = check;
//			this.cook_createtime = cook_createtime;
//			mWS = new ConfigurationWS(mcontext);
//		}
//
//		@Override
//		protected Void doInBackground(Void... params) {
//			try {
//				String strURL = ConfigurationServer.getURLServer() + "wsaddcookitem.php";
//				JSONObject json = new JSONObject();
//				json.put("item_id", item_id);
//				json.put("user_id", user_id);
//				json.put("quantity", quantity);
//				json.put("cook_createtime", cook_createtime);
//				json.put("notes", notes);
//				json.put("checked", checked);
//				JSONArray arrItem = new JSONArray();
//				arrItem = mWS.connectWSPut_Get_Data(strURL, json, "posts");
//				if (arrItem != null) {
//					JSONObject results = arrItem.getJSONObject(0);
//					Log.i("LOG", "Thanh cong: " + results.getString("result"));
//				}
//			} catch (Exception e) {
//			}
//			return null;
//		} 
//
//		@Override
//		protected void onPostExecute(Void result) {
//			super.onPostExecute(result);
//		}
//	}
//
//	// --------------- GET ALL VOICE STATUS = 0------------------------------
//	private void getAllSound() {
//		try {
//			String wsgetdetailinvdetail = ConfigurationServer.getURLServer() + "wsvoiceservice_getallvoice.php";
//			JSONObject json = new JSONObject();
//			JSONArray arrItem = new JSONArray();
//			arrItem = mWS.connectWSPut_Get_Data(wsgetdetailinvdetail, json, "voiceservice_getallvoice");
//			Log.d("LOG", "COOK: " + arrItem.length());
//			if (arrItem != null) {
//				for (int i = 0; i < arrItem.length(); i++) {
//					JSONObject results = arrItem.getJSONObject(i);
//					Voice voice = new Voice();
//					try {
//						voice.setVoice_id(results.getInt("voice_id"));
//					} catch (Exception e) {
//					}
//					try {
//						voice.setCode_table(results.getString("code_table"));
//					} catch (Exception e) {
//					}
//					try {
//						voice.setName(results.getString("name"));
//					} catch (Exception e) {
//					}
//					try {
//						voice.setQuantity(results.getInt("quantity"));
//					} catch (Exception e) {
//					}
//					try {
//						voice.setStatus(results.getInt("status"));
//					} catch (Exception e) {
//					}
//					try {
//						voice.setFlag(results.getInt("flag"));
//					} catch (Exception e) {
//					}
//					try {
//						voice.setChecked(results.getInt("checked"));
//					} catch (Exception e) {
//					}
//					lstVoice.add(voice);
//				}
//
//			}
//		} catch (JSONException e1) {
//			e1.printStackTrace();
//		}
//	}
//
//	// -------------READ SOUND-------------
//	private void ReadSound(final ArrayList<Voice> arr) {
//		try {
//			for (Voice voice : arr) {
//				String name = voice.getName().toLowerCase()
//						.replaceAll("\\s", "");
//				if (checkSound(name)) {
//					int flag = voice.getFlag();
//					String code_table = convertNameTable(voice.getCode_table()
//							.trim());
//					int quantity = voice.getQuantity();
//					int checked = voice.getChecked();
//					if (name.equalsIgnoreCase("")) {
//						/*makeVoiceAddItems("lam", "so_luong", 1, "lauga",
//								"ban_so", "ban6");
//						new WSVoiceService_UpdateVoice(getActivity(),
//								voice.getVoice_id(), 0, 0).execute();*/
//					} else {
//						if (flag == 1) {
//							try {
//								if (checked == 0) {
//									makeVoiceAddItems("lam", "so_luong", quantity, name, "ban_so", code_table);
//									Log.i("SOUND COOK::::", "lam " + "+" + "so luongv" + "+ " + voice.getQuantity() + "+ " + name + "+ "
//											+ "ban so" + " + " + code_table);
//									// --finish reading update to database----//
//									new WSVoiceService_UpdateVoice(getActivity(), voice.getVoice_id(), 0, 0).execute();
//								} else if (checked == 1) {
//									makeVoiceAddItems("them", "so_luong", quantity, name, "ban_so", code_table);
//									Log.i("SOUND COOK::::", "lam " + "+" + "so luongv" + " + " + voice.getQuantity() + "+ " + name + "+ "
//											+ "ban so" + "+ " + code_table);
//									new WSVoiceService_UpdateVoice(getActivity(), voice.getVoice_id(), 0, 0).execute();
//								}
//							} catch (Exception e) {
//							}
//
//						} else if (flag == 0) {
//							try {
//								makeVoiceRemoveItems("huy", "so_luong", quantity, name, "ban_so", code_table);
//								// --finish reading update to database----//
//								new WSVoiceService_UpdateVoice(getActivity(), voice.getVoice_id(), 0, 0).execute();
//							} catch (Exception e) {
//							}
//						}
//					}
//
//				} else {
//					Log.i("SOUND COOK::::", voice.getCode_table() + "/ " + name + "/ " + voice.getQuantity());
//					callVibrate();
//					new WSVoiceService_UpdateVoice(getActivity(), voice.getVoice_id(), 0, 0).execute();
//				}
//			}
//		} catch (Exception e) {
//		}
//	}
//
//	// ===============goiNhacThemMon=====================
//	public void makeVoiceAddItems(String them, String wordSL, int quantity,
//			String name, String wordBS, String table) {
//
//		callMessage(them);
//		callWord(wordSL);
//		callQuantity(quantity);
//		callName(name);
//		callWord(wordBS);
//		callTable(table);
//
//	}
//
//	// ===============goiNhacHuyMon=====================
//	public void makeVoiceRemoveItems(String huy, String wordSL, int quantity,
//			String name, String wordBS, String table) {
//		callMessage(huy);
//		callWord(wordSL);
//		callQuantity(quantity);
//		callName(name);
//		callWord(wordBS);
//		callTable(table);
//	}
//
//	// ======= Đọc tên bàn =======================
//	private void callTable(String table) {
//		try {
//			if (table != null) {
//				media = new MediaPlayer();
//				String tablevoice = Environment.getExternalStorageDirectory()
//						+ "/POS/Media/number/" + table + ".mp3";
//				Uri uitable = Uri.parse(tablevoice);
//				media.setDataSource(getActivity(), uitable);
//				media.prepare();
//				media.setOnCompletionListener(new OnCompletionListener() {
//					@Override
//					public void onCompletion(MediaPlayer mp) {
//						mp.release();
//					}
//				});
//				media.start();
//				time = media.getDuration();
//				Thread.sleep(time);
//			} else {
//				Toast.makeText(getActivity(), getResources().getString(R.string.notvoice), Toast.LENGTH_SHORT).show();
//			}
//		} catch (Exception e) {
//		}
//	}
//
//	// ======= Đọc tên món ăn =======================
//	private void callName(String name) {
//		try {
//			if (name != null) {
//				media = new MediaPlayer();
//				String namevoice = Environment.getExternalStorageDirectory()
//						+ "/POS/Media/food/" + name + ".mp3";
//				Uri uiname = Uri.parse(namevoice);
//				media.setDataSource(getActivity(), uiname);
//				media.prepare();
//				// media.start();
//				// .create(MainActivityCoomek.this, tablename);
//				media.setOnCompletionListener(new OnCompletionListener() {
//					@Override
//					public void onCompletion(MediaPlayer mp) {
//						mp.release();
//					}
//				});
//				media.start();
//				time = media.getDuration();
//				Thread.sleep(time);
//
//			} else {
//				Toast.makeText(getActivity(), getResources().getString(R.string.notvoice), Toast.LENGTH_SHORT).show();
//			}
//		} catch (Exception e) {
//		}
//	}
//
//	// ======= Đọc số lượng món ăn =======================
//	private void callQuantity(int quantity) {
//		try {
//			if (quantity != 0) {
//				media = new MediaPlayer();
//				String quantityvoice = Environment
//						.getExternalStorageDirectory()
//						+ "/POS/Media/number/"
//						+ "ban" + quantity + ".mp3";
//				Uri uiquantity = Uri.parse(quantityvoice);
//				media.setDataSource(getActivity(), uiquantity);
//				media.prepare();
//				media.setOnCompletionListener(new OnCompletionListener() {
//					@Override
//					public void onCompletion(MediaPlayer mp) {
//						mp.release();
//					}
//				});
//				media.start();
//				time = media.getDuration();
//				Thread.sleep(time);
//
//			} else {
//				Toast.makeText(getActivity(), getResources().getString(R.string.notvoice), Toast.LENGTH_SHORT).show();
//			}
//		} catch (Exception e) {
//		}
//	}
//
//	// ======= Đọc thông báo thêm, hủy món ăn =======================
//	private void callMessage(String message) {
//		try {
//			media = MediaPlayer.create(getActivity(), getIdByName(message));
//			if (message != null) {
//				media = new MediaPlayer();
//				String messvoice = Environment.getExternalStorageDirectory()
//						+ "/POS/Media/action/" + message + ".mp3";
//				Uri uimessage = Uri.parse(messvoice);
//				media.setDataSource(getActivity(), uimessage);
//				media.prepare();
//				media.setOnCompletionListener(new OnCompletionListener() {
//					@Override
//					public void onCompletion(MediaPlayer mp) {
//						mp.release();
//					}
//				});
//				media.start();
//				time = media.getDuration();
//				Thread.sleep(time);
//
//			} else {
//				Toast.makeText(getActivity(), getResources().getString(R.string.notvoice), Toast.LENGTH_SHORT).show();
//			}
//		} catch (Exception e) {
//		}
//	}
//
//	// ======= Đọc tu dem =======================
//	private void callWord(String word) {
//		try {
//			if (word != null) {
//				media = new MediaPlayer();
//				String messvoice = Environment.getExternalStorageDirectory()
//						+ "/POS/Media/word_bufer/" + word + ".mp3";
//				Uri uimessage = Uri.parse(messvoice);
//				media.setDataSource(getActivity(), uimessage);
//				media.prepare();
//				media.setOnCompletionListener(new OnCompletionListener() {
//					@Override
//					public void onCompletion(MediaPlayer mp) {
//						mp.release();
//					}
//				});
//				media.start();
//				time = media.getDuration();
//				Thread.sleep(time);
//
//			} else {
//				Toast.makeText(getActivity(), getResources().getString(R.string.notvoice), Toast.LENGTH_SHORT).show();
//			}
//		} catch (Exception e) {
//		}
//	}
//
//	// ============ convert name to raw ==============
//	private int getIdByName(String name) {
//		int id = getActivity().getResources().getIdentifier(name, "raw", getActivity().getPackageName());
//		if (id == 0) {
//			return getActivity().getResources().getIdentifier("ban1", "raw", getActivity().getPackageName());
//		}
//		return id;
//	}
//
//	// ============ Check sound exist to folder raw ==========
//	private boolean checkSound(String nameSound) {
//		if (getIdByName(nameSound) != 0)
//			return true;
//
//		return true;
//	}
//
//	// =========== sound not exist call vibrate =========
//	private void callVibrate() {
//		try {
//			time = 1000;
//			Vibrator vibrate = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
//			vibrate.vibrate(time);
//			Thread.sleep(time);
//		} catch (Exception e) {
//		}
//	}
//
//	// =========== convert code_table to Name Table ========
//	private String convertNameTable(String code_table) {
//		String nameTable = null;
//		if (code_table.contains("B20")) {
//			nameTable = "ban20";
//		} else if (code_table.contains("B19")) {
//			nameTable = "ban19";
//		} else if (code_table.contains("B18")) {
//			nameTable = "ban18";
//		} else if (code_table.contains("B17")) {
//			nameTable = "ban17";
//		} else if (code_table.contains("B16")) {
//			nameTable = "ban16";
//		} else if (code_table.contains("B15")) {
//			nameTable = "ban15";
//		} else if (code_table.contains("B14")) {
//			nameTable = "ban14";
//		} else if (code_table.contains("B13")) {
//			nameTable = "ban13";
//		} else if (code_table.contains("B12")) {
//			nameTable = "ban12";
//		} else if (code_table.contains("B11")) {
//			nameTable = "ban11";
//		} else if (code_table.contains("B10")) {
//			nameTable = "ban10";
//		} else if (code_table.contains("B9")) {
//			nameTable = "ban9";
//		} else if (code_table.contains("B8")) {
//			nameTable = "ban8";
//		} else if (code_table.contains("B7")) {
//			nameTable = "ban7";
//		} else if (code_table.contains("B6")) {
//			nameTable = "ban6";
//		} else if (code_table.contains("B5")) {
//			nameTable = "ban5";
//		} else if (code_table.contains("B4")) {
//			nameTable = "ban4";
//		} else if (code_table.contains("B3")) {
//			nameTable = "ban3";
//		} else if (code_table.contains("B2")) {
//			nameTable = "ban2";
//		} else if (code_table.contains("B1")) {
//			nameTable = "ban1";
//		}
//		if (nameTable == null) {
//			nameTable = "ban20";
//		}
//		return nameTable;
//	}
//}
