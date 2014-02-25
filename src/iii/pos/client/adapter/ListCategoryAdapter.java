package iii.pos.client.adapter;

import iii.pos.client.R;
import iii.pos.client.data.Constaints;
import iii.pos.client.model.Category;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/*----------------this class to display inv_detail ---------*/
public class ListCategoryAdapter extends ArrayAdapter<Category> {

	// ---------------fields--------------------------------//
	private ArrayList<Category> categorylist;
	private Context context;
	private ImageView imgView;

	// --------------constructor-----------------------------//
	public ListCategoryAdapter(Context context, int textViewResourceId,
			ArrayList<Category> category) {
		super(context, textViewResourceId, category);
		this.context = context;
		this.categorylist = category;
	}

	// ---------------display on the screen--------------------------//
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View item = convertView;

		if (item == null) {
			LayoutInflater inflate = (LayoutInflater) this.context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			item = inflate.inflate(R.layout.temp_listcategory, null);
		}
		final Category itemdetail = categorylist.get(position);
		if (categorylist != null) {

			TextView tvName = (TextView) item.findViewById(R.id.tvNameCategory);
			TextView tvDetail = (TextView) item
					.findViewById(R.id.tvDescriptionCategory);
			imgView = (ImageView) item.findViewById(R.id.img_category);

			String filepath = Environment.getExternalStorageDirectory()
					.getPath()
					+ Constaints.URLImageCategory
					+ itemdetail.getImgname() + ".png";
			Bitmap bm = BitmapFactory.decodeFile(filepath);
			imgView.setImageBitmap(bm);

			tvName.setText(String.valueOf(itemdetail.getName()));
			tvDetail.setText(String.valueOf(itemdetail.getDescription()));
		}
		return item;
	}
}