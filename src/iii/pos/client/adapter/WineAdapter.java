package iii.pos.client.adapter;

import iii.pos.client.R;
import iii.pos.client.model.WinnerItem;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class WineAdapter extends BaseAdapter {
	// ===============fields=========================//
	private Context context;
	private ArrayList<WinnerItem> lstWinerItem;
	// ===============constructor=============================//
	public WineAdapter(Context context, ArrayList<WinnerItem> lstWinerItem) {
		super();
		this.context = context;
		this.lstWinerItem = lstWinerItem;
	}
	// =================initialize here========================//
	@Override
	public View getView(int position, View converView, ViewGroup arg2) {
		viewHolderWine holder = null;
		if(converView==null){
			holder = new viewHolderWine();
			LayoutInflater inflate = (LayoutInflater) this.context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			converView = inflate.inflate(
					R.layout.wine_row, null);
			holder.winneImage = (ImageView) converView.findViewById(R.id.imgWineImage);
			holder.winneName = (TextView) converView.findViewById(R.id.tvWineName);
			holder.wineQuality = (Spinner) converView.findViewById(R.id.spWineQuality);
			holder.wineChoose = (CheckBox) converView.findViewById(R.id.chWineChoose);
			holder.txtCost_1=(TextView) converView.findViewById(R.id.txtCost_1);
			converView.setTag(holder);
		}else{
			holder = (viewHolderWine)converView.getTag();
		}
			holder.winneImage.setImageResource(lstWinerItem.get(position).getWinneImage());
			holder.winneName.setText(lstWinerItem.get(position).getWinneName());
			holder.txtCost_1.setText(lstWinerItem.get(position).getWineCost());
			ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
					context, R.array.wine_array,
					android.R.layout.simple_spinner_item);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			holder.wineQuality.setAdapter(adapter );
			holder.wineChoose.setChecked(lstWinerItem.get(position).isWineChoose());
		
		return converView;
		
	}

	@Override
	public int getCount() {
		return lstWinerItem.size();
	}

	@Override
	public Object getItem(int arg0) {
		return lstWinerItem.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}
	// ======== viewholder class======//
	class viewHolderWine{
		ImageView winneImage;
		TextView winneName;
		TextView txtCost_1;
		Spinner wineQuality;
		CheckBox wineChoose;
	}

}
