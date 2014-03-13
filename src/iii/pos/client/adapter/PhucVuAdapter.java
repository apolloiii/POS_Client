package iii.pos.client.adapter;

import iii.pos.client.R;
import iii.pos.client.activity.MainPosActivity;
import iii.pos.client.model.Invoice_Detail;
import iii.pos.client.server.ConfigurationServer;
import iii.pos.client.server.ConfigurationWS;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class PhucVuAdapter extends BaseAdapter {
	// ===============fields=========================//
	private ArrayList<Invoice_Detail> lstInvDetail;
	private Context context;
	private InvHolder holder;

	// ===============constructor=============================//
	public PhucVuAdapter(ArrayList<Invoice_Detail> lstInvDetail, Context context) {
		super();
		this.lstInvDetail = lstInvDetail;
		this.context = context;
	}
	// =================initialize here========================//
	@Override
	public View getView(final int position, View view, ViewGroup arg2) {
		if (view == null) {
			holder = new InvHolder();

			view = ((Activity) context).getLayoutInflater().inflate(
					R.layout.row_phuc_vu_xu_ly, null);
			holder.starttime = (TextView) view.findViewById(R.id.starttime);
			holder.endtime = (TextView) view.findViewById(R.id.endtime);
			holder.tvpvSL = (TextView) view.findViewById(R.id.tvpvSL);
			holder.cbpvCheck = (CheckBox) view.findViewById(R.id.cbpvCheck);
			holder.imgpvDelete = (ImageButton) view
					.findViewById(R.id.imgpvDelete);
			view.setTag(holder);
			holder.cbpvCheck.setTag(lstInvDetail.get(position));
		} else {
			holder = (InvHolder) view.getTag();
			((InvHolder) view.getTag()).cbpvCheck.setTag(lstInvDetail
					.get(position));
		}
		final InvHolder holder1 = (InvHolder) view.getTag();
		final Invoice_Detail inv_detail = lstInvDetail.get(position);

		holder1.starttime.setText(inv_detail.getStart_date());
		holder1.endtime.setText(inv_detail.getEnd_date());
		holder1.tvpvSL.setText(inv_detail.getQuantity() + "");
		if (inv_detail.getChecked() == 1) {
			holder1.cbpvCheck.setChecked(true);
			holder1.cbpvCheck.setEnabled(false);
			holder1.imgpvDelete.setEnabled(false);
		}
		holder1.cbpvCheck
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						int flag = buttonView.isChecked() ? 1 : 0;

						if (flag == 1) {
							inv_detail.setChecked(flag);
							buttonView.setEnabled(false);
							final String updatetime = new SimpleDateFormat(
									"yyyy-MM-dd hh:mm:ss").format(new Date());

							holder1.endtime.setText(updatetime);
							if (new ConfigurationServer(context).isOnline()) {
								new UpdateCheckInvDetail(context, inv_detail
										.getId()).execute();
							} else {
								Toast.makeText(context, "Network not found",
										Toast.LENGTH_SHORT).show();
							}

						}
					}
				});

		holder1.imgpvDelete.setOnClickListener(new View.OnClickListener() {
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
										if (lstInvDetail.size() > 0) {
											lstInvDetail.remove(position);
											notifyDataSetChanged();
										}
									}
								}).setNegativeButton(context.getResources().getString(R.string.Cancel), null).show();
			}
		});
		return view;
	}

	@Override
	public int getCount() {
		return lstInvDetail.size();
	}

	@Override
	public Object getItem(int arg0) {
		return lstInvDetail.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	// -------------ws class update UpdateCheckInvDetail --------------------//
	public class UpdateCheckInvDetail extends AsyncTask<Void, Void, Void> {

		private int id;
		private ConfigurationWS mWS;
		private boolean ok = false;
		private ProgressDialog progressDialog;

		// ----------asyntask Contructor----------------------//
		public UpdateCheckInvDetail(Context mContext, int id) {

			this.id = id;
			mWS = new ConfigurationWS(mContext);
			progressDialog = new ProgressDialog(context);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.setMessage("Loading...");
			progressDialog.setCancelable(false);
			progressDialog.show();

		}

		// --------background method-------------------//
		@Override
		protected synchronized Void doInBackground(Void... params) {
			try {

				String wsupdatedetailinvdetail = ConfigurationServer
						.getURLServer() + "wsupdatedetailinvdetail.php";

				JSONObject json = new JSONObject();
				Log.d("id", "ID CAN XU LY LA: " + id);
				json.put("id", id);
				json.put("user_id", MainPosActivity.phoneNumber);
				json.put("company_code", MainPosActivity.company_code);
				JSONArray arrItem = new JSONArray();
				arrItem = mWS.connectWSPut_Get_Data(wsupdatedetailinvdetail,
						json, "posts");
				if (arrItem != null) {
					JSONObject results = arrItem.getJSONObject(0);
					if (results.getInt("success") == 1) {
						ok = true;
					}
				}

			} catch (Exception e) {
				Log.i("Log : ", "Exception : " + e.getMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			try {
				if (progressDialog != null)
					progressDialog.dismiss();
			} catch (Exception e) {
			}
		}
	}

	// ======== viewholder class======//
	class InvHolder {
		TextView starttime, endtime, tvpvSL;
		CheckBox cbpvCheck;
		ImageButton imgpvDelete;
	}
}
