/*package iii.pos.client.fragment;

import iii.pos.client.R;
import iii.pos.client.fragment.base.FragmentBase;
import iii.pos.client.model.Floor;
import iii.pos.client.server.ConfigurationWS;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MapPosFragment extends FragmentBase{

	private int floor = 0;
	//private ListView lvFloor;
	private ArrayList<Floor> lstFloor;
	private ConfigurationWS mWS;
	private Context mcontext;
	//private int key;
	//private Button btnAddFloor;
	//private FloorDB floorDB ;
	public MapPosFragment() {
	}

	public MapPosFragment(int floor) {
		this.floor = floor;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View v = inflater.inflate(R.layout.map_pos, container, false);
		initView(v);
		//floorDB = new FloorDB(getActivity().getBaseContext());
		return v;

	}
	
	public void initView(View v){
		mcontext = v.getContext();
		
		mWS = new ConfigurationWS(mcontext);
		
		lvFloor = (ListView) v.findViewById(R.id.lvFloor);
		lvFloor.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
				Toast.makeText(getActivity(), lstFloor.get(pos).getId() +""	, Toast.LENGTH_SHORT).show();
				ConfigurationServer.floor = lstFloor.get(pos).getId();
				setFloor(ConfigurationServer.floor);
			}
		});

		lvFloor.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
				showdialogLongClick(pos);
				return false;
			}
		});
		//btnAddFloor = (Button) v.findViewById(R.id.btnAddFloor);
		//btnAddFloor.setOnClickListener(this);

		if (ConfigurationServer.floor == 0)
			setFloor(this.floor);
		else
			setFloor(ConfigurationServer.floor);

		//new WSGetFloor(getActivity()).execute();
	}
	
	@Override
	public void onClick(View v) {
		if(v == btnAddFloor){
			new WSInsertFloor(getActivity()).execute();
		}
	}

	*//**
	 * Long click vào tầng Cho phép sửa tên tầng và xóa tầng
	 * @param pos : vị trí tầng
	 *//*
	private void showdialogLongClick(final int pos) {
		key = 0;
		new AlertDialog.Builder(new ContextThemeWrapper(mcontext,
				android.R.style.Theme_Dialog))
				.setTitle(" ")
				.setSingleChoiceItems(getResources().getStringArray(R.array.optionfloor), 0, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								key = whichButton;
							}
						})

				.setPositiveButton(getResources().getString(R.string.OK),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {

								switch (key) {
								case 0:
									showdialogEdit(pos);
									break;
								case 1:
									showdialogDelete(pos);
									break;
								default:
									break;
								}
							}
						})

				.setNegativeButton(getResources().getString(R.string.Cancel), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

					}
				})
				.setCancelable(true).create().show();
	}

		private void showdialogEdit(final int pos) {
	final View addView = ((Activity) mcontext).getLayoutInflater().inflate(R.layout.add2, null);
		final EditText etEditnameFloor = (EditText) addView.findViewById(R.id.etpvAmount2);
		new AlertDialog.Builder(mcontext)
				.setIcon(android.R.drawable.ic_input_add)
				.setTitle(getResources().getString(R.string.editfloor))
				.setView(addView)
				.setPositiveButton(getResources().getString(R.string.update),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int pos1) {
								String editNameFloor = etEditnameFloor.getText().toString();
								int id = lstFloor.get(pos).getId();
								new WSUpdateNameFloor(editNameFloor, id, mcontext).execute();
							}
						})
				.setNegativeButton(getResources().getString(R.string.Cancel), null).show();

	}

	private void showdialogDelete(final int pos) {
		new AlertDialog.Builder(mcontext)
				.setTitle(getResources().getString(R.string.deletefllor))
				.setPositiveButton(getResources().getString(R.string.delete),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								int id = lstFloor.get(pos).getId();
								new WSUpdateNameFloor("delete", id, mcontext)
										.execute();

							}
						})
				.setNegativeButton(getResources().getString(R.string.Cancel), null).show();

	}

	public void setFloor(int floor1) {
		Fragment mapFloorFragemnt = null;
		int floor = floor1;
		mapFloorFragemnt = new MapFloorFragment(floor);
		FragmentManager fragmentManager = getChildFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		if (mapFloorFragemnt != null) {
			fragmentTransaction.replace(R.id.fmapcontainer, mapFloorFragemnt);
		}
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}
	

	private class WSUpdateNameFloor extends AsyncTask<Void, Void, Void> {
		private String edtNamefloor;
		private int id;
		private Context context;
		public int kq = -1;
		private ConfigurationWS mWS_Insert;
		private ProgressDialog progressDialog;

		public WSUpdateNameFloor(String edtNamefloor, int id,
				Context mcontext) {
			super();
			this.edtNamefloor = edtNamefloor;
			this.id = id;
			this.context = mcontext;
			mWS_Insert = new ConfigurationWS(mcontext);
			progressDialog = new ProgressDialog(mcontext);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog.setMessage("Loading...");
			progressDialog.setCancelable(false);
			progressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {

			try {
				lstFloor = new ArrayList<Floor>();
				String URLGetAllFloor = ConfigurationServer.getURLServer() + "wsupdate_floorname.php";
				JSONObject json = new JSONObject();
				json.put("description", edtNamefloor);
				json.put("id", id);

				JSONArray arrItem = new JSONArray();
				arrItem = mWS.connectWSPut_Get_Data(URLGetAllFloor, json, "posts");

				JSONObject results = arrItem.getJSONObject(0);

				kq = results.getInt("result");

			} catch (Exception e) {
				Log.i("LOG", "Insert INV Detail : " + e.getMessage());

			}
			return null;

		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (kq == 1 && edtNamefloor != "delete") {
				new WSGetFloor(mcontext).execute();
				Toast.makeText(getActivity(), "Update success", 1).show();
			} else if (kq == 1 && edtNamefloor == "delete") {
				new WSGetFloor(mcontext).execute();
				Toast.makeText(getActivity(), "Delete success", 1).show();
			} else {
				Toast.makeText(getActivity(), "Update error", 1).show();
			}
			try {
				progressDialog.dismiss();
			} catch (Exception e) {

			}
		}
	}

	// ------------------default floor1-----------------------------//
	private class WSGetFloor extends AsyncTask<Void, Void, Void> {

		private Context context;
		private ConfigurationWS mWs;
		private ProgressDialog mProgress;

		public WSGetFloor(Context mContext) {
			super();
			this.context = mContext;
			mWs = new ConfigurationWS(mContext);
			mProgress = new ProgressDialog(mContext);
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
				lstFloor = new ArrayList<Floor>();
				// ---------------get String ------------------------//
				String URLGetAllFloor = ConfigurationServer.getURLServer() + "wsgetallfloor.php";
				JSONObject json = new JSONObject();

				JSONArray arrITable = mWS.connectWSPut_Get_Data(URLGetAllFloor, json, "floor");

				for (int i = 0; i < arrITable.length(); i++) {
					JSONObject results = arrITable.getJSONObject(i);

					Floor floor = new Floor();
					floor.setId(results.getInt("id"));
					floor.setCode(results.getString("code"));
					floor.setName(results.getString("name"));
					floor.setStatus(results.getInt("status"));
					lstFloor.add(floor);
					
					
					if ( floorDB.checkExist(results.getString("id")) == false) {
						Floor floor = new Floor();
						floor.setIDSV(results.getInt("id"));
						floor.setCode(results.getString("code"));
						floor.setName(results.getString("name"));
						floor.setStatus(results.getInt("status"));
						floorDB.addFloor(floor);
						//lstFloor.add(floor);
					}
					
				}
			} catch (Exception e) {
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			//lstFloor = floorDB.getFloor();
			
			super.onPostExecute(result);
			try {
				MapPosAdapter floorAdapter = new MapPosAdapter(context, R.layout.row_map_pos, lstFloor);
				lvFloor.setAdapter(floorAdapter);
				floorAdapter.notifyDataSetChanged();
				mProgress.dismiss();
			} catch (Exception e) {

			}
		}
	}

	// =================Insert Table==================//
	public class WSInsertFloor extends AsyncTask<Void, Void, Void> {
		private ConfigurationWS mWS;
		private String msg = "";
		private ProgressDialog mProgress;

		// ===============constructor=============================//
		public WSInsertFloor(Context mContext) {
			mWS = new ConfigurationWS(mContext);
			mProgress = new ProgressDialog(mContext);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			// mProgress.setMessage("Updating user info");
			mProgress.setMessage("Loading...");
			mProgress.setCancelable(false);
			mProgress.show();
		}

		// ===============do in background==================//
		@Override
		protected synchronized Void doInBackground(Void... params) {
			try {

				String wsinsertfloor = ConfigurationServer.getURLServer() + "wsinsertfloor.php";
				JSONObject json = new JSONObject();
				JSONArray arrITable = mWS.connectWSPut_Get_Data(wsinsertfloor, json, "posts");
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
				new WSGetFloor(getActivity()).execute();
				mProgress.dismiss();
			} catch (Exception e) {
			}
			if (msg != "") {
				Toast.makeText(getActivity(), "Add Floor " + msg, Toast.LENGTH_SHORT).show();
			}
		}
	}

}*/