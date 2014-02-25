package iii.pos.client.adapter;

import iii.pos.client.R;
import iii.pos.client.model.Floor;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MapPosAdapter extends ArrayAdapter<Floor> {
	private Context context;
	private ArrayList<Floor> lstFloor;

	public MapPosAdapter(Context context, int id, ArrayList<Floor> lstFloor) {
		super(context, id, lstFloor);
		this.context = context;
		this.lstFloor = lstFloor;
	}

	@Override
	public View getView(int position, View converView, ViewGroup arg2) {
		viewHolderWine holder = null;
		if (converView == null) {
			holder = new viewHolderWine();
			LayoutInflater inflate = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			converView = inflate.inflate(R.layout.row_map_pos, null);
			holder.imgFloor = (ImageView) converView.findViewById(R.id.imgFloor);
			holder.tvNameFloor = (TextView) converView.findViewById(R.id.tvNameFloor);
			converView.setTag(holder);
		} else {
			holder = (viewHolderWine) converView.getTag();
		}
		if (lstFloor.get(position).getStatus() != 0) {
			holder.imgFloor.setImageResource(R.drawable.floor2);
			holder.tvNameFloor.setText(lstFloor.get(position).getName());
		}

		return converView;

	}

	class viewHolderWine {
		ImageView imgFloor;
		TextView tvNameFloor;
	}

}
