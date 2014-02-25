package iii.pos.client.fragment;

import iii.pos.client.R;
import iii.pos.client.activity.MainPosActivity;
import iii.pos.client.model.Itable;
import iii.pos.client.server.ConfigurationServer;
import iii.pos.client.server.ConfigurationWS;
import iii.pos.client.util.DragController;
import iii.pos.client.util.DragLayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class MapFloorFragment extends Fragment implements View.OnLongClickListener, View.OnClickListener, View.OnTouchListener {

	private DragController mDragController; // Object that sends out drag-drop
											// events while a view is being
											// moved.
	private DragLayer mDragLayer; // The ViewGroup that supports drag-drop.
	private boolean mLongClickStartsDrag = true; // If true, it takes a long
													// click to start the drag
													// operation. Otherwise, any
													// touch
	// event starts a drag.
	private static final int CHANGE_TOUCH_MODE_MENU_ID = Menu.FIRST;
	private static final int ADD_OBJECT_MENU_ID = Menu.FIRST + 1;
	public static final boolean Debugging = false;
	// flag for threading
	private boolean ok = false;
	private ConfigurationWS mWS;
	private ISelectItable iSelectItable;
	private String table_code_g;
	private ArrayList<String> listValueItable;
	private int floor;
	private ArrayList<Itable> tmpItablelst;
	private boolean addtableFlag;

	public MapFloorFragment() {
	}

	public MapFloorFragment(int floor) {
		this.floor = floor;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			iSelectItable = (ISelectItable) activity;
		} catch (Exception e) {
		}
	}

	/**
	 * onCreate - called when the activity is first created.
	 * 
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View mapLayout = inflater.inflate(R.layout.mainmap, container,
				false);
		mDragController = new DragController(getActivity());
		mWS = new ConfigurationWS(getActivity());
		addtableFlag = false;
		tmpItablelst = new ArrayList<Itable>();
		setupViews(mapLayout);
		getHeightWidth();

		new WSGetAllTable(getActivity(), floor).execute();

		return mapLayout;
	}

	@Override
	public void onResume() {
		setOk(true);// to running the thread update status
		intervalUpdateTable();// starting threading
		super.onResume();
	}

	@Override
	public void onPause() {
		setOk(false);
		super.onPause();
	}

	// ---this method to interval and update table status-----//
	public void intervalUpdateTable() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				while (ok) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// MainPosActivity.beanDataAll.makeDataTable(floor);
					makeColorDataTable(floor);
					myHandle.sendMessage(myHandle.obtainMessage());
				}
			}

			// ----hanlder to update background thread--------//
			Handler myHandle = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					try {
						// addTable(MainPosActivity.beanDataAll.lstItable);
						Iterator<Itable> iter = mylstItable.iterator();
						while (iter.hasNext()) {
							Itable itable = iter.next();
							TextView tv = (TextView) mDragLayer
									.findViewWithTag(itable.getCode_table());

							if (itable.getStatus() == 0) {
								tv.setBackgroundResource(R.drawable.table_white);
							}
							if (itable.getStatus() == 1) {
								tv.setBackgroundResource(R.drawable.table_yellow);
							}
							if (itable.getStatus() == 2) {
								tv.setBackgroundResource(R.drawable.table_yellow);
							}
						}
					} catch (Exception e) {
					}
				}
			};
		}).start();
	}

	public ArrayList<Itable> check(ArrayList<Itable> list) {
		/*
		 * pos_table.openDB(); tmpItablelst = pos_table.getItablelst(floor);
		 */
		Log.d("____________hai2", tmpItablelst.size() + "");
		ArrayList<Itable> arr = new ArrayList<Itable>();
		int i;
		for (Itable itable : list) {
			i = 0;
			for (Itable itable2 : tmpItablelst) {
				if (itable2.getCode_table().equals(itable.getCode_table())) {
					i++;
				}
			}
			if (i == 0) {
				Log.d("itable", itable.getCode_table());
				arr.add(itable);
			}
		}
		return arr;
	}

	// initialize all the table on the background
	/**
	 * Xử lý việc add bàn lên MapPos dựa vào vị trí bàn 
	 * Hàm này cần xử lý hiển thị Đa thiết bị.
	 * @param list : bàn của tầng.
	 * 
	 */
	public synchronized void addTable(List<Itable> list) {

		if (addtableFlag == true) {
			ArrayList<Itable> itablearr = new ArrayList<Itable>();
			int i;
			for (Itable itable : list) {
				i = 0;
				for (Itable itable2 : tmpItablelst) {
					if (itable2.getCode_table().equals(itable.getCode_table())) {
						i++;
					}
				}
				if (i == 0) {
					Log.d("itable", itable.getCode_table());
					itablearr.add(itable);
				}
			}
			for (Itable itable : itablearr) {
					TextView newView = new TextView(getActivity());
					newView.setTag(itable.getCode_table().toString());
					try {
						if (itable.getStatus() == 0) {
							newView.setBackgroundResource(R.drawable.table_white);
						} else {
							if (itable.getStatus() == 1) {
								newView.setBackgroundResource(R.drawable.table_yellow);
							} else {
								newView.setBackgroundResource(R.drawable.table_yellow);
							}
						}
					} catch (Exception e) {
						Log.d("THUAN: ", e.toString());
					}
					newView.setTextColor(Color.BLACK);
					newView.setText(itable.getDescription_table().toString());
					newView.setTextSize(10);
					newView.setPadding(30, 18, 0, 0);
					int w = 80;
					int h = 80;
					int left = itable.getPos_x();
					int top = itable.getPos_y();
					DragLayer.LayoutParams lp = new DragLayer.LayoutParams(w,
							h, left, top);
					mDragLayer.addView(newView, lp);
					newView.setOnClickListener(this);
					newView.setOnLongClickListener(this);
					newView.setOnTouchListener(this);
				
			}
		
		}
		if (addtableFlag == false) {
			Iterator<Itable> lstTable = list.iterator();
			while (lstTable.hasNext()) {
				Itable itable = lstTable.next();
				if (itable != null) {
					TextView newView = new TextView(getActivity());
					newView.setTag(itable.getCode_table().toString());
					try {
						if (itable.getStatus() == 0) {
							newView.setBackgroundResource(R.drawable.table_white);
						} else {
							if (itable.getStatus() == 1) {
								newView.setBackgroundResource(R.drawable.table_yellow);
							} else {
								newView.setBackgroundResource(R.drawable.table_yellow);
							}
						}
					} catch (Exception e) {
						Log.d("THUAN: ", e.toString());
					}
					newView.setTextColor(Color.BLACK);
					newView.setText(itable.getDescription_table().toString());
					newView.setTextSize(10);
					newView.setPadding(30, 18, 0, 0);
					int w = 80;
					int h = 80;
					int left = itable.getPos_x();
					int top = itable.getPos_y();
					DragLayer.LayoutParams lp = new DragLayer.LayoutParams(w,
							h, left, top);
					mDragLayer.addView(newView, lp);
					newView.setOnClickListener(this);
					newView.setOnLongClickListener(this);
					newView.setOnTouchListener(this);
				}
			}
		}
	}

	private void getHeightWidth() {
		DisplayMetrics displayMetrics = new DisplayMetrics();
		WindowManager wm = (WindowManager) getActivity().getSystemService(
				Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(displayMetrics);
		/*int width = displayMetrics.widthPixels;
		int height = displayMetrics.heightPixels;
		ImageView img = new ImageView(getActivity());
		img.setTag("imgadd");
		img.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				addtableFlag = true;
				new WSInsertTable(getActivity(), ConfigurationServer.floor)
						.execute();
			}
		});
		img.setBackgroundResource(R.drawable.selector_footer_add);
		DragLayer.LayoutParams lp = new DragLayer.LayoutParams(50, 50,
				width - 200, height - 280);
		mDragLayer.addView(img, lp);*/
	}

	/**
	 * Handle a click on a view. Tell the user to use a long click (press).
	 * 
	 */

	// ------III Click to change status of table----//
	public void onClick(View v) {
		if (mLongClickStartsDrag) { // Tell the user that it takes a long
			// click to start dragging. //

			String code_table = v.getTag().toString();
			new WSClickTable(getActivity(), code_table, MainPosActivity.user_id)
					.execute();
		}

	}

	// -------passing user_id to checking which user are executing at that
	// table---------
	// -------passing table_code
	// -------

	int option;

	private void checkingItable(final int user_id, final String code_table,
			int status, boolean isUser) {

		if (isUser) {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					new ContextThemeWrapper(getActivity(),
							android.R.style.Theme_Holo_Light_Dialog));
			builder.setMessage(getActivity().getString(R.string.maptitle)
					+ code_table + " ?");
			builder.setCancelable(false);
			builder.setNeutralButton(getActivity().getString(R.string.dongy),
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							/*--------add new Invoice here-------------------*/
							iSelectItable.onSelectItable(2, 1, code_table,
									user_id);
						}
					});
			builder.setNegativeButton(getActivity().getString(R.string.boqua),
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
							if (new ConfigurationServer(getActivity())
									.isOnline()) {
								new UpdateItableStatus(0, code_table, user_id)
										.execute();
							} else {
								Toast.makeText(getActivity(),
										"Network not found", Toast.LENGTH_SHORT)
										.show();
							}
						}
					});
			builder.show();
		} else {
			new AlertDialog.Builder(new ContextThemeWrapper(getActivity(),
					android.R.style.Theme_Holo_Light_Dialog))
					.setIcon(android.R.drawable.ic_dialog_info)
					.setTitle(R.string.titleTachban)
					.setSingleChoiceItems(
							getResources().getStringArray(R.array.ghepban), 0,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									option = 0;
									option = whichButton;
								}
							})
					.setCancelable(true)
					.setPositiveButton(R.string.OK,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									optionSelect(option, code_table);
									dialog.cancel();
								}
							}).setNegativeButton(R.string.Cancel, null)
					.create().show();
		}
	}

	// ============view invoice, transfer table===================//
	private void optionSelect(int which, final String code_table_t) {
		switch (which) {
		// ============view invoice========================//
		case 0:
			iSelectItable.onSelectItable(2, 2, code_table_t,
					MainPosActivity.user_id);
			// da su
			// dung
			// Toast.makeText(getActivity(), "View invoice", Toast.LENGTH_SHORT)
			// .show();

			break;
		// ============transfer table========================//
		case 1:
			option = 0;
			MainPosActivity.beanDataAll
					.makeDataTableByFloor(ConfigurationServer.floor);
			getItable(MainPosActivity.beanDataAll.lstItableByFloor);
			View view = getActivity().getLayoutInflater().inflate(
					R.layout.spinner, null);
			AlertDialog.Builder builder = new AlertDialog.Builder(
					new ContextThemeWrapper(getActivity(),
							android.R.style.Theme_Holo_Light_Dialog));
			final Spinner spRowTransferItem = (Spinner) view
					.findViewById(R.id.spRowTransferItem);
			ArrayAdapter<String> TransferItemAdap = new ArrayAdapter<String>(
					new ContextThemeWrapper(getActivity(),
							android.R.style.Theme_Holo_Light_Dialog),
					android.R.layout.simple_spinner_item, android.R.id.text1,
					listValueItable);
			TransferItemAdap
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spRowTransferItem.setAdapter(TransferItemAdap);

			if (!TextUtils.isEmpty(table_code_g)) {
				int spinnerPosition = TransferItemAdap
						.getPosition(table_code_g);
				spRowTransferItem.setSelection(spinnerPosition);
			}
			spRowTransferItem
					.setOnItemSelectedListener(new OnItemSelectedListener() {
						@Override
						public void onItemSelected(AdapterView<?> arg0,
								View arg1, int pos, long arg3) {
							table_code_g = listValueItable.get(pos).toString();
							Toast.makeText(getActivity(),
									table_code_g + "/" + code_table_t,
									Toast.LENGTH_SHORT).show();
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
						}
					});
			builder.setIcon(android.R.drawable.ic_dialog_info)
					.setTitle(R.string.titleTachban)
					.setView(view)
					.setPositiveButton(R.string.OK,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									try {

										int user_id = MainPosActivity.user_id;
										new WSGetInvCodeByItable(getActivity(),
												table_code_g, code_table_t,
												user_id).execute();
									} catch (Exception e) {
									}
									dialog.cancel();
								}
							}).setNegativeButton(R.string.Cancel, null)
					.create().show();
			// Toast.makeText(getActivity(), "Chuyển món", Toast.LENGTH_SHORT)
			// .show();
			break;
		}
	}

	// =========spinner add key and value for it=============================//
	private void getItable(List<Itable> listItable) {

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
				hasMapItable.put(listItable.get(i).getCode_table(), listItable
						.get(i).getDescription_table());
			}
		}
		for (Map.Entry<String, String> e : hasMapItable.entrySet()) {
			String key = e.getKey();
			String value = e.getValue();
			listValueItable.add(key);
			Collections.sort(listValueItable);
		}
		for (String str : listValueItable) {
			Log.i("Ten ban:", str.toString());
		}
		list.add(hasMapItable);
	}

	public boolean onLongClick(View v) {
		if (mLongClickStartsDrag) {
			if (!v.isInTouchMode()) {
				toast("isInTouchMode returned false. Try touching the view again.");
				return false;
			}
			return startDrag(v);
		}
		return false;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case CHANGE_TOUCH_MODE_MENU_ID:
			mLongClickStartsDrag = !mLongClickStartsDrag;
			String message = mLongClickStartsDrag ? "Changed touch mode. Drag now starts on long touch (click)."
					: "Changed touch mode. Drag now starts on touch (click).";
			Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
			return true;
		case ADD_OBJECT_MENU_ID:
			ImageView newView = new ImageView(getActivity());
			int w = 60;
			int h = 60;
			int left = 80;
			int top = 400;
			DragLayer.LayoutParams lp = new DragLayer.LayoutParams(w, h, left,
					top);
			mDragLayer.addView(newView, lp);
			newView.setOnClickListener(this);
			newView.setOnLongClickListener(this);
			newView.setOnTouchListener(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public boolean onTouch(View v, MotionEvent ev) {
		if (mLongClickStartsDrag)
			return false;
		boolean handledHere = false;
		final int action = ev.getAction();
		toast("thuan " + v.getTag());
		if (action == MotionEvent.ACTION_DOWN) {
			handledHere = startDrag(v);
		}
		return handledHere;
	}

	public boolean startDrag(View v) {
		Object dragInfo = v;
		mDragController.startDrag(v, mDragLayer, dragInfo,
				DragController.DRAG_ACTION_MOVE);
		return true;
	}

	private void setupViews(View v) {
		DragController dragController = mDragController;

		mDragLayer = (DragLayer) v.findViewById(R.id.drag_layer);
		mDragLayer.setDragController(dragController);
		dragController.addDropTarget(mDragLayer);
	}

	public void toast(String msg) {
		Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
	} // end toast

	/**
	 * Send a message to the debug log and display it using Toast.
	 */

	public void trace(String msg) {
		if (!Debugging)
			return;
		toast(msg);
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		setOk(false);
		super.onDetach();
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public interface ISelectItable {
		/**
		 * 
		 * @param id 
		 * @param status
		 * @param table_code 
		 * @param user_id
		 */
		public void onSelectItable(int id, int status, String table_code,
				int user_id);
	}

	// ----------private update <Only this class>------------------------//
	private class UpdateItableStatus extends AsyncTask<Void, Void, Void> {

		private int check = 0;
		private String table_code;
		private int user_id = 0;

		public UpdateItableStatus(int check, String table_code, int user_id) {
			this.check = check;
			this.table_code = table_code;
			this.user_id = user_id;
		}

		@Override
		protected synchronized Void doInBackground(Void... params) {

			try {

				String UrlCheckItableFocus = ConfigurationServer.getURLServer()
						+ "wsupdateitable.php";
				JSONObject json = new JSONObject();
				json.put("check", check);
				json.put("table_code", table_code);
				json.put("user_id", user_id);
				mWS.connectWS_Put_Data(UrlCheckItableFocus, json);
			} catch (JSONException e) {

			}

			return null;
		}

	}

	// ============ Get inv_code by Itable ==============
	class WSClickTable extends AsyncTask<Void, Void, Void> {
		private ProgressDialog dialog;
		private ConfigurationWS mWS;
		private Context context;
		private String code_table;
		private int user_id;
		private int status = 0;
		private int flag = 0;

		public WSClickTable(Context mcontext, String code_table, int user_id) {
			super();
			this.context = mcontext;
			this.code_table = code_table;
			this.user_id = user_id;
			dialog = new ProgressDialog(mcontext);
			mWS = new ConfigurationWS(mcontext);
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
			String wstableselect = ConfigurationServer.getURLServer()
					+ "wstableselect.php";
			try {
				JSONObject json = new JSONObject();
				json.put("code_table", code_table);
				json.put("user_id", user_id);
				JSONArray updatePos = mWS.connectWSPut_Get_Data(wstableselect,
						json, "posts");
				JSONObject results = updatePos.getJSONObject(0);
				this.status = results.getInt("status");
				this.flag = results.getInt("flag");

			} catch (Exception e) {
				Log.i("Log : ", "Exception : " + e.getMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			if (this.status == 0) {
				checkingItable(flag, code_table, status, true);
			}
			if (this.status == 2 || this.status == 1) {
				checkingItable(flag, code_table, status, false);
			}
			try {
				if (dialog.isShowing()) {
					dialog.dismiss();
				}
			} catch (Exception e) {
			}
		}
	}

	// ============ Get inv_code by Itable ==============
	class WSGetInvCodeByItable extends AsyncTask<Void, Void, Void> {
		private ProgressDialog dialog;
		private ConfigurationWS mWS;
		private Context context;
		private String code_table_g;
		private String code_table_t;
		private int user_id;

		public WSGetInvCodeByItable(Context mcontext, String code_table_g,
				String code_table_t, int user_id) {
			super();
			this.context = mcontext;
			this.code_table_g = code_table_g;
			this.code_table_t = code_table_t;
			this.user_id = user_id;
			dialog = new ProgressDialog(mcontext);
			mWS = new ConfigurationWS(mcontext);
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
			try {
				String URLAddLocation = ConfigurationServer.getURLServer()
						+ "wsupdatetransfertable.php";
				JSONObject json = new JSONObject();
				json.put("code_table_t", code_table_t);
				json.put("code_table_g", code_table_g);
				json.put("user_id", user_id);
				JSONArray arrItem = new JSONArray();
				arrItem = mWS.connectWSPut_Get_Data(URLAddLocation, json,
						"posts");
				if (arrItem != null) {
					JSONObject element = arrItem.getJSONObject(0);
					Log.i("inv_code: ", element.getString("result"));
				}
			} catch (Exception e) {
				Log.i("LOG", "get data error : " + e.getMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (dialog.isShowing()) {
				dialog.dismiss();
				Toast.makeText(getActivity(), "Transfer success !",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	// =================Insert Table==================//
	/*public class WSInsertTable extends AsyncTask<Void, Void, Void> {
		private int floor;
		private ConfigurationWS mWS;
		private String msg = "";
		private ProgressDialog mProgress;

		public WSInsertTable(Context mContext, int floor) {
			this.floor = floor;
			mWS = new ConfigurationWS(mContext);
			mProgress = new ProgressDialog(mContext);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mProgress.setMessage("Loading...");
			mProgress.setCancelable(false);
			mProgress.show();
		}

		@Override
		protected synchronized Void doInBackground(Void... params) {
			try {

				String URLAddnewItem = ConfigurationServer.getURLServer()
						+ "wsinserttable.php";
				JSONObject json = new JSONObject();
				json.put("floor", floor);
				JSONArray arrITable = mWS.connectWSPut_Get_Data(URLAddnewItem,
						json, "posts");
				JSONObject results = arrITable.getJSONObject(0);
				if (!results.getString("result").equals("false")) {
					msg = results.getString("result");
				}
			} catch (Exception e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			try {
				mProgress.dismiss();
			} catch (Exception e) {
			}
			if (msg != "") {
				// ___them ban tmpItablelst________
				for (Itable itable : MainPosActivity.beanDataAll.lstItableByFloor) {
					int i = 0;
					for (Itable itable2 : tmpItablelst) {
						if (itable2.getCode_table().equals(
								itable.getCode_table())) {
							i++;
						}
					}
					if (i == 0) {
						Log.d("itable", itable.getCode_table());
						tmpItablelst.add(itable);
					}
				}

				new WSGetAllTable(getActivity(), floor).execute();
				Toast.makeText(
						getActivity(),
						"Add table "
								+ msg
								+ "\n"
								+ getResources().getString(
										R.string.refreshscreen),
						Toast.LENGTH_SHORT).show();
			}
		}
	}
*/
	// ============ Get inv_code by Itable ==============
	class WSGetAllTable extends AsyncTask<Void, Void, Void> {
		private ProgressDialog dialog;
		private ConfigurationWS mWS;
		private Context context;
		private int id;

		public WSGetAllTable(Context mcontext, int id) {
			super();
			this.context = mcontext;
			this.id = id;
			dialog = new ProgressDialog(mcontext);
			mWS = new ConfigurationWS(mcontext);
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
			try {
				MainPosActivity.beanDataAll.makeDataTableByFloor(id);
			} catch (Exception e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			addTable(MainPosActivity.beanDataAll.lstItableByFloor);
			Log.d("dohai",
					String.valueOf(tmpItablelst.size()
							+ " Size of tmpItablelst"));
			Log.d("dohai",
					String.valueOf(MainPosActivity.beanDataAll.lstItableByFloor.size()
							+ " Size of table"));
		}
	}

	ArrayList<Itable> mylstItable = new ArrayList<Itable>();

	public void makeColorDataTable(int floor) {

		if (mylstItable != null)
			mylstItable.clear();
		try {
			// ---------------get String ------------------------//
			JSONObject json = new JSONObject();
			json.put("floor", floor);
			//Log.i("KHIEM:", floor+"");
			JSONArray arrITable = mWS.connectWSPut_Get_Data(
					ConfigurationServer.getURLServer() + "wsgetallitable.php",
					json, "itable");

			for (int i = 0; i < arrITable.length(); i++) {
				JSONObject results = arrITable.getJSONObject(i);
				//Log.i("Log : ", "TABLE : " + results);

				Itable itable = new Itable();
				itable.setItable_id(results.getInt("itable_id"));
				itable.setCode_table(results.getString("code_table"));
				itable.setDescription_table(results
						.getString("description_table"));
				itable.setStatus(results.getInt("status"));
				itable.setCreate_time(results.getString("create_time"));
				itable.setUpdate_date(results.getString("update_time"));
				itable.setFlag(results.getInt("flag"));
				itable.setPos_x(results.getInt("pos_x"));
				itable.setPos_y(results.getInt("pos_y"));
				mylstItable.add(itable);
			}
		} catch (Exception e) {
		}

	}

} // end class
