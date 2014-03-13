package iii.pos.client.adapter;

import iii.pos.client.R;
import iii.pos.client.activity.MainPosActivity;
import iii.pos.client.model.CookItem;
import iii.pos.client.server.ConfigurationServer;
import iii.pos.client.server.ConfigurationWS;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class CookAdapter extends BaseAdapter {

	private List<CookItem> lstCookItem;
	private Activity context;
	private InvHolder holder;

	public CookAdapter(List<CookItem> lstCookItem, Activity context) {
		super();
		this.lstCookItem = lstCookItem;
		this.context = context;
	}

	@Override
	public int getCount() {
		return lstCookItem.size();
	}

	@Override
	public Object getItem(int arg0) {
		return lstCookItem.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(final int position, View view, ViewGroup arg2) {
		if (view == null) {
			holder = new InvHolder();
			view = context.getLayoutInflater().inflate(R.layout.row_cook, null);
			holder.tvpvDateCreate = (TextView) view .findViewById(R.id.tvpvDateCreate);
			holder.tvpvDateUpdate = (TextView) view .findViewById(R.id.tvpvDateUpdate);
			holder.tvpvSL = (TextView) view.findViewById(R.id.tvpvSL);
			holder.cbpvCheck = (CheckBox) view.findViewById(R.id.cbpvCheck);
			holder.imgpvDelete = (ImageButton) view .findViewById(R.id.imgpvDelete);
			view.setTag(holder);
		} else {
			holder = (InvHolder) view.getTag();
		}
		holder.tvpvDateCreate.setText(lstCookItem.get(position) .getCook_createtime() + "");
		holder.tvpvDateUpdate.setText(lstCookItem.get(position) .getCook_updatetime() + "");
		holder.tvpvSL.setText(lstCookItem.get(position).getQuantity() + "");
		if (lstCookItem.get(position).getChecked() == 1) {
			holder.cbpvCheck.setChecked(true);
			holder.cbpvCheck.setEnabled(false);
		}
		holder.cbpvCheck.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (holder.cbpvCheck.isChecked()) {
					Toast.makeText(context, "check", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(context, "Uncheck", Toast.LENGTH_SHORT).show();
				}
			}
		});
		holder.imgpvDelete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(context)
						.setIcon(android.R.drawable.ic_delete)
						.setTitle(context.getResources().getString(R.string.questiondelete))
						.setPositiveButton(context.getResources().getString(R.string.delete),
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										if (lstCookItem.size() > 0) {
											try {
												int id = lstCookItem.get( position).getId();
												new WSUpdateCookItem(context, id).execute();
												notifyDataSetChanged();
											} catch (Exception e) {
												Toast.makeText( context, "Error Update CookItem, because Id  not valiable",
														Toast.LENGTH_SHORT) .show();
											}
											lstCookItem.remove(position);
											notifyDataSetChanged();
//											Toast.makeText(context, "removed", Toast.LENGTH_SHORT).show();
										}
									}
								}).setNegativeButton(context.getResources().getString(R.string.Cancel), null).show();
			}
		});
		return view;
	}

	// --------- update cookitem ------------------------
	private class WSUpdateCookItem extends AsyncTask<Void, Void, Void> {
		private ConfigurationWS mWS;
		private int id;

		public WSUpdateCookItem(Context mcontext, int id) {
			super();
			this.id = id;
			mWS = new ConfigurationWS(mcontext);
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				String strURL = ConfigurationServer.getURLServer() + "wsupdatecookitemkhiem.php";
				JSONObject json = new JSONObject();
				json.put("id", id);
				json.put("user_id", MainPosActivity.phoneNumber);
				json.put("company_code", MainPosActivity.company_code);
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
	public CheckBox getCheckBok(){
		CheckBox cb = new CheckBox(context);
		cb = holder.getCbpvCheck();
		return cb;
	}
	class InvHolder {
		TextView tvpvDateCreate, tvpvDateUpdate, tvpvSL;
		CheckBox cbpvCheck;
		ImageButton imgpvDelete;
		
		public void setCbpvCheck(CheckBox cbpvCheck) {
			this.cbpvCheck = cbpvCheck;
		}
		public CheckBox getCbpvCheck() {
			return cbpvCheck;
		}
	}
}
