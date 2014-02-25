package iii.pos.client.adapter;

import iii.pos.client.R;
import iii.pos.client.model.Invoice_Spend;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class InvoiceSpendAdapter extends ArrayAdapter<Invoice_Spend> {
	private Context context;
	private List<Invoice_Spend> arrayList;

	public InvoiceSpendAdapter(Context context, int resource,
			List<Invoice_Spend> list) {
		super(context, resource, list);
		this.context = context;
		this.arrayList = list;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		try {
			if (convertView == null) {
				holder = new ViewHolder();
				LayoutInflater inflate = (LayoutInflater) this.context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflate.inflate(
						R.layout.invoicespend_item_layout, null);
				/*-----------------------------------------------------------*/
				holder.cbInvoicespend = (CheckBox) convertView
						.findViewById(R.id.cbInvoicecost);
				holder.codeInvoice = (TextView) convertView
						.findViewById(R.id.txtCodeInvoice);
				holder.txtnameInvoice = (TextView) convertView
						.findViewById(R.id.txtnameInvoice);
				holder.txtPrice = (TextView) convertView
						.findViewById(R.id.txtPrice);
				holder.txtpersonPay = (TextView) convertView
						.findViewById(R.id.txtpersonPay);
				holder.txtPayfor = (TextView) convertView
						.findViewById(R.id.txtPayfor);
				holder.txtStatus = (TextView) convertView
						.findViewById(R.id.txtStatus);
				holder.txtSpend = (TextView) convertView
						.findViewById(R.id.txtCost);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			final Invoice_Spend invoice_Spend = arrayList.get(position);
			holder.codeInvoice.setText(invoice_Spend.getCode());
			holder.codeInvoice.setEnabled(false);
			holder.txtnameInvoice.setText(invoice_Spend.getNameCost());
			holder.txtPrice.setText(invoice_Spend.getPrice());
			holder.txtpersonPay.setText(invoice_Spend.getPersonPay());
			holder.txtPayfor.setText(invoice_Spend.getPayFor());
			holder.txtStatus.setText(invoice_Spend.getStatus());
			holder.txtSpend.setText(invoice_Spend.getCost());
			holder.cbInvoicespend
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {
						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							if (isChecked)
								arrayList.get(position).setCheck(true);
							else
								arrayList.get(position).setCheck(false);
						}
					});

		} catch (Exception e) {
		}
		return convertView;
	}

	class ViewHolder {
		CheckBox cbInvoicespend;
		TextView codeInvoice;
		TextView txtnameInvoice;
		TextView txtPrice;
		TextView txtpersonPay;
		TextView txtPayfor;
		TextView txtStatus;
		TextView txtSpend;

	}

}
