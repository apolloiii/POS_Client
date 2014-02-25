/*package iii.pos.client.activity;

import iii.pos.client.R;
import iii.pos.client.adapter.InvoiceDetailAdapter;
import iii.pos.client.data.ConfigurationDB;
import iii.pos.client.fragment.InvoiceDetailPosFragment.IAddMenu;
import iii.pos.client.model.Invoice_Detail;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

*//**
 * 
 * @author tranminhthuan Copyright (C) 2013 III COMPANY
 * 
 *//*
public class InvDetailActivity extends Fragment {

	------------------fields----------------------
	private String code_table;
	private String inv_code;
	private boolean status = false;
	private int vattmp;
	private int commitiontmp;

	private Context context;

	private ListView invoiceList;
	private static InvoiceDetailAdapter adapterInvoice_Item;
	public ArrayList<Invoice_Detail> listItem;
	private ImageButton btnAddInvDetail;
	private Button btn_thanhtoan;

	private ConfigurationDB mDB;

	private IAddMenu iaddMenu;

	-------------Constructor----------------------------
	public InvDetailActivity(boolean status, String inv_code,
			String code_table, ArrayList<Invoice_Detail> listItems, int vat,
			int committion) {
		this.status = status;
		this.code_table = code_table;
		this.vattmp = vat;
		this.commitiontmp = committion;
		this.listItem = listItems;
		this.inv_code = inv_code;

	}
	

	------------initialize view----------------------
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View invoiceLayout = inflater.inflate(
				R.layout.invoice_detail_activiy, container, false);
		context = getActivity().getApplicationContext();
		setAdapter(this.status);
		return invoiceLayout;
	}

	private void setAdapter(boolean status) {

	}
}
*/