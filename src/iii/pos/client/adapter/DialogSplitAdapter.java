package iii.pos.client.adapter;

import iii.pos.client.R;
import iii.pos.client.model.Invoice_Detail;
import iii.pos.client.model.Invoice_DetailTmpl;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

public class DialogSplitAdapter extends BaseAdapter {

	private List<Invoice_Detail> listInvDetail;
	private List<Invoice_DetailTmpl> listInvDetailTmp;
	private Activity context;
	private ArrayList<Integer> lstAmount = new ArrayList<Integer>();
	private ViewHolder holder = null;

	public DialogSplitAdapter(List<Invoice_Detail> lstInvDetail,
			List<Invoice_DetailTmpl> lstInvDetailTmp, Activity context) {
		super();
		this.listInvDetail = lstInvDetail;
		this.listInvDetailTmp = lstInvDetailTmp;
		this.context = context;
	}

	@Override
	public int getCount() {
		return listInvDetail.size();
	}

	@Override
	public Object getItem(int arg0) {
		return listInvDetail.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(final int pos, View v, ViewGroup arg2) {
		if (v == null) {
			holder = new ViewHolder();
			v = context.getLayoutInflater().inflate(R.layout.rowsplit_invoice,
					null);
			holder.tvSplitInvName = (TextView) v
					.findViewById(R.id.tvSplitInvName);
			holder.cbSplitInvChoose = (CheckBox) v
					.findViewById(R.id.cbSplitInvChoose);
			holder.spSplitInv = (Spinner) v.findViewById(R.id.spSplitInv);
			holder.tvSplitInvSum = (TextView) v
					.findViewById(R.id.tvSplitInvSum);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		final Invoice_Detail inv = listInvDetail.get(pos);
		
		holder.tvSplitInvName.setText(inv.getName());
		holder.cbSplitInvChoose.setChecked(inv.isTranferCheck());
		holder.tvSplitInvSum.setText(inv.getQuantity() + "");
		lstAmount = randomAmount(inv.getQuantity());

		ArrayAdapter<Integer> splitAdap = new ArrayAdapter<Integer>(context,
				android.R.layout.simple_spinner_item, android.R.id.text1,
				lstAmount);
		splitAdap
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		holder.spSplitInv.setAdapter(splitAdap);
		
		holder.spSplitInv
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int pos, long arg3) {
						int amount = pos + 1;
						Invoice_DetailTmpl invTmpl = new Invoice_DetailTmpl();
						invTmpl.setItem_id(inv.getItem_id());
						invTmpl.setQuantity(amount);

						if (amount <= inv.getQuantity()) {
							Invoice_DetailTmpl id = check(invTmpl,
									listInvDetailTmp);
							if (id == null) {
								listInvDetailTmp.add(invTmpl);
							} else {
								listInvDetailTmp.remove(id);
								listInvDetailTmp.add(invTmpl);
							}
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});
		
		holder.cbSplitInvChoose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CheckBox cb = (CheckBox) v;
				inv.setTranferCheck(cb.isChecked());
				if (cb.isChecked()) {
					inv.setTranferCheck(true);
				} else {
					inv.setTranferCheck(false);
				}
			}
		});
		return v;
	}

	private Invoice_DetailTmpl check(Invoice_DetailTmpl inv,
			List<Invoice_DetailTmpl> arr) {
		for (Invoice_DetailTmpl invoice_DetailTmpl : arr) {
			if (inv.getItem_id() == invoice_DetailTmpl.getItem_id()) {
				return invoice_DetailTmpl;
			}
		}
		return null;
	}

	// ===============remove an inv_detail====================//
	public List<Invoice_Detail> removeInv(Invoice_Detail inv_detail,
			List<Invoice_Detail> lstInvDetail) {
		for (Invoice_Detail invdetail : lstInvDetail) {
			if (invdetail.getItem_id() == inv_detail.getItem_id()) {
				lstInvDetail.remove(invdetail);
			}
		}
		return lstInvDetail;
	}

	class ViewHolder {
		TextView tvSplitInvName, tvSplitInvSum;
		CheckBox cbSplitInvChoose;
		Spinner spSplitInv;
	}

	private ArrayList<Integer> randomAmount(int amount) {
		ArrayList<Integer> lstAmountTmp = new ArrayList<Integer>();
		for (int i = 1; i <= amount; i++) {
			lstAmountTmp.add(i);
		}
		return lstAmountTmp;
	}
}
