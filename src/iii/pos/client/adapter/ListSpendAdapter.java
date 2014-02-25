package iii.pos.client.adapter;

import iii.pos.client.R;
import iii.pos.client.model.ListSpend;

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

public class ListSpendAdapter extends ArrayAdapter<ListSpend> {
	private Context context;
	private List<ListSpend> arrayList;

	public ListSpendAdapter(Context context, int resource, List<ListSpend> list) {
		super(context, resource, list);
		this.context = context;
		this.arrayList = list;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolderInvDetail holder = null;
		try {
			if (convertView == null) {
				holder = new ViewHolderInvDetail();
				LayoutInflater inflate = (LayoutInflater) this.context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflate.inflate(R.layout.listcosr_item_layout,
						null);
				/*-----------------------------------------------------------*/
				holder.cbListcost = (CheckBox) convertView
						.findViewById(R.id.cbListcost);
				holder.codeList = (TextView) convertView
						.findViewById(R.id.txtCodeList);
				holder.nameCost = (TextView) convertView
						.findViewById(R.id.txtnameCost);
				holder.price = (TextView) convertView
						.findViewById(R.id.txtPrice);
				holder.notes = (TextView) convertView
						.findViewById(R.id.txtNotes);
				holder.typeCost = (TextView) convertView
						.findViewById(R.id.txtTypeCost);
				holder.groupCost = (TextView) convertView
						.findViewById(R.id.txtGroupCost);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolderInvDetail) convertView.getTag();
			}
			final ListSpend listCost = arrayList.get(position);
			holder.codeList.setText(listCost.getCodeList());
			holder.nameCost.setText(listCost.getNameCost());
			holder.price.setText(listCost.getPrice());
			holder.notes.setText(listCost.getNotes());
			holder.typeCost.setText(listCost.getTypeCost());
			holder.groupCost.setText(listCost.getGroupCost());
			holder.cbListcost .setOnCheckedChangeListener(new OnCheckedChangeListener() {
						@Override
						public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
							if(isChecked)
								arrayList.get(position).setCheck(true);
							else
								arrayList.get(position).setCheck(false);
						}
					});

		} catch (Exception e) {
			// TODO: handle exception
		}

		return convertView;
	}

	class ViewHolderInvDetail {
		CheckBox cbListcost;
		TextView codeList;
		TextView nameCost;
		TextView price;
		TextView notes;
		TextView typeCost;
		TextView groupCost;
	}

}
