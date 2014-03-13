package iii.pos.client.adapter;

import iii.pos.client.R;
import iii.pos.client.model.Floor;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SpinnerAdapterFloor extends BaseAdapter {

	private ArrayList<Floor> lstFloors;
	private Context context;
	
	
	public SpinnerAdapterFloor(ArrayList<Floor> lstFloors, Context context) {
		super();
		this.lstFloors = lstFloors;
		this.context = context;
	}

	@Override
	public int getCount() {
		return lstFloors.size();
	}

	@Override
	public Object getItem(int arg0) {
		return lstFloors.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			LayoutInflater inflate = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflate.inflate(R.layout.row_spinner_table, null);
			holder.tvTableName = (TextView) convertView.findViewById(R.id.tvTableName);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		Floor floor = lstFloors.get(position);
		holder.tvTableName.setText("Floor "+floor.getId());
		
		return convertView;
	}

	private class ViewHolder{
		TextView tvTableName;
	}
	
}
