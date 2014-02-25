package iii.pos.client.adapter;

import iii.pos.client.R;
import iii.pos.client.library.FormatFloorTableName;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SpinnerAdapterTable extends BaseAdapter {

	private ArrayList<String> lstTables;
	private Context context;
	
	
	public SpinnerAdapterTable(ArrayList<String> lstTables, Context context) {
		super();
		this.lstTables = lstTables;
		this.context = context;
	}

	@Override
	public int getCount() {
		return lstTables.size();
	}

	@Override
	public Object getItem(int arg0) {
		return lstTables.get(arg0);
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
		
		String tableCode = lstTables.get(position);
		
		// Kiểm tra nếu ko có bàn nào hiển thị "No table free"
		if( tableCode.contains("free") ){
			holder.tvTableName.setText("No table free");
		}else{
			// Đổi lại tên bàn : input : T2_B1 => Bàn 1
			String mCodeTable = new FormatFloorTableName().getTableName(tableCode);
			holder.tvTableName.setText(mCodeTable);
		}
		
		return convertView;
	}

	private class ViewHolder{
		TextView tvTableName;
	}
	
}
