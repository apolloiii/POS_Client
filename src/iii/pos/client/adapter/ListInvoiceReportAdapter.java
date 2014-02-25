package iii.pos.client.adapter;

import iii.pos.client.R;
import iii.pos.client.model.Invoice_Detail;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListInvoiceReportAdapter extends ArrayAdapter<Invoice_Detail> {

	// ----------------Fields ----------------------------------//
	private Context context;
	public ArrayList<Invoice_Detail> invoiceitemlist;
	private String code;float curr=1f;
	// ===============constructor=============================//
	public ListInvoiceReportAdapter(Context context, int textViewResourceId,
			ArrayList<Invoice_Detail> invoiceitemlist, String code, float curr) {
		super(context, textViewResourceId, invoiceitemlist);
		this.context = context;
		this.invoiceitemlist = invoiceitemlist;
		this.code =code;
		this.curr = curr;
	}
	// =================initialize here========================//
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View invoiceItem = convertView;
		try {
			if (invoiceItem == null) {
				LayoutInflater inflate = (LayoutInflater) this.context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				invoiceItem = inflate.inflate(
						R.layout.invoice_custom_listview_report, null);
			}
			final Invoice_Detail invoiceItemdetail = invoiceitemlist
					.get(position);

			if (invoiceItemdetail != null) {
				LinearLayout lnLayout = (LinearLayout) invoiceItem
						.findViewById(R.id.invoiceReport);
				TextView index = (TextView) invoiceItem
						.findViewById(R.id.indexReport);
				TextView name_item = (TextView) invoiceItem
						.findViewById(R.id.nameitemReport);
				TextView amount_item = (TextView) invoiceItem
						.findViewById(R.id.amount_itemReport);
				TextView cost_item = (TextView) invoiceItem
						.findViewById(R.id.cost_itemReport);
				TextView notes = (TextView) invoiceItem
						.findViewById(R.id.note_itemReport);
				index.setText(String.valueOf(position + 1));
				name_item.setText(String.valueOf(invoiceItemdetail.getName()));
				amount_item.setText(String.valueOf(invoiceItemdetail
						.getQuantity()));
				cost_item.setText(String.format("%.2f",invoiceItemdetail.getPrice()/curr)+code);
				notes.setText(String.format("%.2f", invoiceItemdetail.getQuantity()
						* invoiceItemdetail.getPrice()/curr)+code);
			}

		} catch (Exception e) {
			Log.i("Log : ", "Exception : " + e.getMessage());
		}
		return invoiceItem;
	}
}
