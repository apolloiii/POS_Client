package iii.pos.client.adapter;

import iii.pos.client.R;
import iii.pos.client.model.Table;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class ShowTableAdapter extends BaseAdapter {
	ViewHolder holder = null;
	ArrayList<Table> lstString;
	Context context;

	public ShowTableAdapter(Context context, ArrayList<Table> lstTableTmp) {
		this.context = context;
		this.lstString = lstTableTmp;
	}

	@Override
	public View getView(int pos, View converView, ViewGroup arg2) {

		if (converView == null) {
			holder = new ViewHolder();
			LayoutInflater inflate = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			converView = inflate.inflate(R.layout.cell, null);
			holder.txtName = (TextView) converView.findViewById(R.id.textView1);
			holder.cbpvCheck = (CheckBox) converView.findViewById(R.id.checkBox1);
			converView.setTag(holder);
		} else {
			holder = (ViewHolder) converView.getTag();
		}
		final Table tb = lstString.get(pos);
		String name = tb.getName();
		if (name.length() < 6)
			name = name + " ";
		holder.txtName.setText(name);
		holder.cbpvCheck.setChecked(tb.isCheck());
		holder.cbpvCheck.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CheckBox cb = (CheckBox) v;
				if (cb.isChecked()) {
					tb.setCheck(true);
					//Toast.makeText(context, " " + tb.getName(), 1).show();
				} else {
					tb.setCheck(false);
					//Toast.makeText(context, " " + tb.getName(), 1).show();
				}
			}
		});

		return converView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lstString.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return lstString.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	public CheckBox getCheckBok() {
		CheckBox cb = new CheckBox(context);
		cb = holder.getCbMediaActivityFocusChoose();
		return cb;
	}

	// ======== viewholder class======//
	static class ViewHolder {
		TextView txtName;
		CheckBox cbpvCheck;

		public CheckBox getCbMediaActivityFocusChoose() {

			return cbpvCheck;
		}
	}
	
}
