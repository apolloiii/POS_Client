/*package iii.pos.client.activity;

import iii.pos.client.R;
import iii.pos.client.adapter.DialogtransferItemAdapter;
import iii.pos.client.model.Invoice_Detail;
import iii.pos.client.server.ConfigurationWS;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class DialogTransferItemActivity extends Activity implements OnClickListener {
	private DialogtransferItemAdapter adapter;
	private List<Invoice_Detail> lstInvDetail1;
	private List<Invoice_Detail> lstInvDetail2;
	private ListView lvDiaTransferItem1;
	private ListView lvDiaTransferItem2;
	private Button btnDiaTransferItemAdd, btnDiaTransferItemRemove;
	private Button btnDiaTransferItemOK, btnDiaTransferItemCancel;
	public List<Invoice_Detail> lstAnInvDetail;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialogtransferitem);
		show();
	}

	private void show() {
		lstAnInvDetail = new CopyOnWriteArrayList<Invoice_Detail>();
		lvDiaTransferItem1 = (ListView) findViewById(R.id.lvDiaTransferItem1);
		lvDiaTransferItem2 = (ListView) findViewById(R.id.lvDiaTransferItem2);
		btnDiaTransferItemAdd = (Button) findViewById(R.id.btnDiaTransferItemAdd);
		btnDiaTransferItemRemove = (Button) findViewById(R.id.btnDiaTransferItemRemove);
		btnDiaTransferItemOK = (Button) findViewById(R.id.btnDiaTransferItemOK);
		btnDiaTransferItemCancel = (Button) findViewById(R.id.btnDiaTransferItemCancel);
		
		btnDiaTransferItemAdd.setOnClickListener(this);
		btnDiaTransferItemRemove.setOnClickListener(this);
		btnDiaTransferItemOK.setOnClickListener(this);
		btnDiaTransferItemCancel.setOnClickListener(this);

		lstInvDetail1 = new CopyOnWriteArrayList<Invoice_Detail>();
		lstInvDetail2 = new CopyOnWriteArrayList<Invoice_Detail>();
		
		try {
			String inv_code = getIntent().getExtras().getString("inv_code");
			new WSGetInvDetail(DialogTransferItemActivity.this, inv_code).execute();
		} catch (Exception e) {
		}
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnAdd:
			for (Invoice_Detail inv : lstInvDetail1) {
				if (inv.isTranferCheck()) {
					inv.setTranferCheck(false);
					lstInvDetail2.add(inv);
					lstInvDetail1.remove(inv);
				}
			}
			adapter = new DialogtransferItemAdapter(lstInvDetail2, DialogTransferItemActivity.this);
			lvDiaTransferItem2.setAdapter(adapter);

			break;
		case R.id.btnRemove:
			for (Invoice_Detail inv : lstInvDetail2) {
				if (inv.isTranferCheck()) {
					inv.setTranferCheck(false);
					lstInvDetail1.add(inv);
					lstInvDetail2.remove(inv);
				}
			}
			adapter = new DialogtransferItemAdapter(lstInvDetail2, DialogTransferItemActivity.this);
			lvDiaTransferItem2.setAdapter(adapter);
			break;
		case R.id.btnOK:
			// xu ly ghep hoa don o day
			// cong cac inv_detail khac nhau thuoc list hoa don can ghep lai
			// lay invoice tu list cac invoice can ghep
			// insert invoice moi
			// insert cac invoice detail moi

			// ==============add invoice to server================= //
//			Invoice inv = generateInvoice(lstLv2);
//
//			new AddNewInvoice(GhepBan.this, inv.getInv_code(),
//					"2013-06-28 10:00:00", 1, MainPosActivity.user_id, "T1_B2")
//					.execute();
//			// ===============add lst InvDetail to server===========//
//			ArrayList<Invoice_Detail> lstInvDetailTmp = couplingInvoice(lstLv2,
//					lstInvDetail);
//
//			new AddNewInvDetail(GhepBan.this, inv.getInv_code(),
//					lstInvDetailTmp).execute();
//			finish();
			break;
		case R.id.btnCancel:
			Toast.makeText(getBaseContext(), "Thoat", Toast.LENGTH_SHORT)
					.show();
			finish();
			break;
		}
	}

	// ============ get all Owner ==============
		class WSGetInvDetail extends AsyncTask<Void, Void, Void> {
			private ProgressDialog dialog;
			private ConfigurationWS mWS;
			private Context context;
			private String inv_code;

			public WSGetInvDetail(Context mcontext, String inv_code) {
				super();
				this.context = mcontext;
				this.inv_code = inv_code;
				dialog = new ProgressDialog(mcontext);
				mWS = new ConfigurationWS(mcontext);
			}

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				dialog.setTitle("Loading...");
				dialog.setCancelable(false);
				dialog.show();
			}

			@Override
			protected Void doInBackground(Void... params) {
				MainPosActivity.beanDataAll.makeAnInvDetail(inv_code);
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				try {
					lstAnInvDetail = MainPosActivity.beanDataAll.lstAnInvDetail;
					adapter = new DialogtransferItemAdapter(lstAnInvDetail, DialogTransferItemActivity.this);
					lvDiaTransferItem1.setAdapter(adapter);
				} catch (Exception e) {
				}
				
				if (dialog.isShowing()) {
					dialog.dismiss();
				}

			}
		}
}
*/