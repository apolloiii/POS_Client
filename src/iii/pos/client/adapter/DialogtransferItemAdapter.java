package iii.pos.client.adapter;

import iii.pos.client.R;
import iii.pos.client.model.Invoice_Detail;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class DialogtransferItemAdapter extends BaseAdapter {

	private List<Invoice_Detail> listInvoice;
	private Activity context;

	public DialogtransferItemAdapter(List<Invoice_Detail> listInvoice, Activity context) {
		super();
		this.listInvoice = listInvoice;
		this.context = context;
	}

	@Override
	public int getCount() {
		return listInvoice.size();
	}

	@Override
	public Object getItem(int arg0) {
		return listInvoice.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(final int pos, View v, ViewGroup arg2) {
		ViewHolder holder = null;
		if (v == null) {
			holder = new ViewHolder();
			v = context.getLayoutInflater().inflate(R.layout.rowcoupling, null);

			holder.tvName = (TextView) v.findViewById(R.id.tvName);
			holder.cbChoose = (CheckBox) v.findViewById(R.id.cbCheck);

			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		final Invoice_Detail inv = listInvoice.get(pos);
		holder.tvName.setText(inv.getInv_code());
		holder.cbChoose.setChecked(inv.isTranferCheck());

		holder.cbChoose.setOnClickListener(new OnClickListener() {
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

	class ViewHolder {
		TextView tvName;
		CheckBox cbChoose;
	}
}
