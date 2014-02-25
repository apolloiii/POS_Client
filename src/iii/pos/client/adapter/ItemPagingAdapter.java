package iii.pos.client.adapter;

import iii.pos.client.R;
import iii.pos.client.data.Constaints;
import iii.pos.client.model.Items;

import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemPagingAdapter extends BaseAdapter {
	// ---------------fields--------------------------------//
	private List<Items> listMonAn;
	private Activity context;

	// ===================constructor====================================//
	public ItemPagingAdapter(List<Items> listMonAn, Activity context) {
		super();
		this.listMonAn = listMonAn;
		this.context = context;
	}

	// =================initialize here=====================================//
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		viewHolder holder = null;
		if (convertView == null) {
			convertView = ((Activity) context).getLayoutInflater().inflate(
					R.layout.paging_detail, null);
			holder = new viewHolder();
			holder.imgMonAn = (ImageView) convertView
					.findViewById(R.id.imgMonAn);
			holder.tvTenMonAn = (TextView) convertView
					.findViewById(R.id.tvTenMonAn);
			holder.tvGiaTien = (TextView) convertView
					.findViewById(R.id.tvGiaTien);
			convertView.setTag(holder);

		} else {
			holder = (viewHolder) convertView.getTag();

		}
		String filepath = Environment.getExternalStorageDirectory().getPath()
				+ Constaints.URLImageCategory
				+ listMonAn.get(position).getImgName() + ".png";
		Bitmap bm = BitmapFactory.decodeFile(filepath);
		holder.imgMonAn.setImageBitmap(bm);
		holder.tvTenMonAn.setText(listMonAn.get(position).getName());
		holder.tvGiaTien.setText(String.valueOf(listMonAn.get(position)
				.getPrice()));
		return convertView;
	}

	@Override
	public int getCount() {
		return listMonAn.size();
	}

	@Override
	public Object getItem(int arg0) {
		return listMonAn.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	// ======== viewholder class======//
	private class viewHolder {
		ImageView imgMonAn;
		TextView tvTenMonAn;
		TextView tvGiaTien;
	}

}
