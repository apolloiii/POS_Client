package iii.pos.client.adapter;

import iii.pos.client.R;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SpinnerCostAdapte extends BaseAdapter{
	private ArrayList<String> txttext=new ArrayList<String>();
	private LayoutInflater mInflater;
	public SpinnerCostAdapte(Context context,  ArrayList<String> text) {
		 this.mInflater = LayoutInflater.from(context);
		    this.txttext=text;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.txttext.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
	    if (convertView == null) 
	    {
	        convertView = mInflater.inflate(R.layout.item_spinner_cost, null);
	        holder = new ViewHolder();
	        holder.text = (TextView) convertView.findViewById(R.id.text);
	        convertView.setTag(holder);
	    } 
	    else 
	    {
	        holder = (ViewHolder) convertView.getTag();
	    }
	    holder.text.setText(txttext.get(position));
		return convertView;
	}
	
	class ViewHolder{
		TextView text;
	}

}
