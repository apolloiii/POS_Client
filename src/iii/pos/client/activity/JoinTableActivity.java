/*package iii.pos.client.activity;

import iii.pos.client.R;
import iii.pos.client.adapter.InvoiceCouplingAdapter;
import iii.pos.client.model.Invoice;
import iii.pos.client.model.Invoice_Detail;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class JoinTableActivity extends Activity implements OnClickListener {
	private InvoiceCouplingAdapter adapter;
	private List<Invoice> lstLv1;
	private List<Invoice> lstLv2;
	private ListView lv1;
	private ListView lv2;
	private Button btnAdd, btnRemove, btnOK, btnCancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.couplingdialog);
		show();
	}

	private void show() {
		lv1 = (ListView) findViewById(R.id.lv1);
		lv2 = (ListView) findViewById(R.id.lv2);
		btnAdd = (Button) findViewById(R.id.btnAdd);
		btnRemove = (Button) findViewById(R.id.btnRemove);
		btnOK = (Button) findViewById(R.id.btnOK);
		btnCancel = (Button) findViewById(R.id.btnCancel);

		btnAdd.setOnClickListener(this);
		btnRemove.setOnClickListener(this);
		btnOK.setOnClickListener(this);
		btnCancel.setOnClickListener(this);

		lstLv1 = new CopyOnWriteArrayList<Invoice>();
		lstLv2 = new CopyOnWriteArrayList<Invoice>();
		
		lstLv1 = MainPosActivity.beanDataAll.getLstInvoice();
		
		adapter = new InvoiceCouplingAdapter(lstLv1, JoinTableActivity.this);
		lv1.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnAdd:
			for (Invoice inv : lstLv1) {
				if (inv.isCheck()) {
					inv.setCheck(false);
					lstLv2.add(inv);
					lstLv1.remove(inv);
				}
			}
			adapter = new InvoiceCouplingAdapter(lstLv2, JoinTableActivity.this);
			lv2.setAdapter(adapter);

			break;
		case R.id.btnRemove:
			for (Invoice inv : lstLv2) {
				if (inv.isCheck()) {
					inv.setCheck(false);
					lstLv1.add(inv);
					lstLv2.remove(inv);
				}
			}
			adapter = new InvoiceCouplingAdapter(lstLv2, JoinTableActivity.this);
			lv2.setAdapter(adapter);
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
			Toast.makeText(getBaseContext(), "Thoat", Toast.LENGTH_SHORT).show();
			finish();
			break;
		}
	}

	// ==========method generate an invoice================//
	private Invoice generateInvoice(List<Invoice> lstInvoice) {
		Invoice inv = new Invoice();
		String invName = "_";
		for (Invoice invoice : lstInvoice) {
			invName = invName + "_" + invoice.getInv_code().substring(0, 6);
		}
		inv.setInv_code(invName.substring(1, invName.length()));
		inv.setCost(0);
		inv.setTotal(0);
		inv.setStatus(1);
		inv.setUser_id(MainPosActivity.user_id);
		return inv;
	}

	// ===========method return lstIvndetail ===============//
	private List<Invoice_Detail> getLstInvDetail(String inv_code, List<Invoice_Detail> lstInvDetail) {
		List<Invoice_Detail> lstInvDetailTmp = new CopyOnWriteArrayList<Invoice_Detail>();
		for (Invoice_Detail invDetail : lstInvDetail) {
			if (invDetail.getInv_code().equals(inv_code)) {
				lstInvDetailTmp.add(invDetail);
			}
		}
		return lstInvDetailTmp;
	}

	// ===========method sum total invoice_detail ===========//
	private ArrayList<Invoice_Detail> couplingInvoice(List<Invoice> lstInvoice, List<Invoice_Detail> lstInvDetail) {
		ArrayList<Invoice_Detail> lstInvTmp = new ArrayList<Invoice_Detail>();
		for (Invoice invoice : lstInvoice) {
			lstInvTmp.addAll(getLstInvDetail(invoice.getInv_code(),
					lstInvDetail));
		}
		return lstInvTmp;
	}
}
*/