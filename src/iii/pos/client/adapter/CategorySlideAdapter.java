package iii.pos.client.adapter;

import iii.pos.client.R;
import iii.pos.client.data.Constaints;
import iii.pos.client.model.Category;
import iii.pos.client.util.ImageLoader;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CategorySlideAdapter extends BaseAdapter {

	// ===============fields=========================//
	private Context context;
	private ArrayList<Category> plotsImages;
	private static ViewHolder holder;
	private ImageLoader imageLoader;
	// ===============constructor=============================//
	public CategorySlideAdapter(Activity context,
			ArrayList<Category> plotsImages) {
		this.context = context;
		this.plotsImages = plotsImages;
		imageLoader = new ImageLoader(context);
	}

	// =================initialize here========================//
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {

			holder = new ViewHolder();
			LayoutInflater inflate = (LayoutInflater) this.context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflate.inflate(R.layout.category_slide_custom, null);
			holder.imageView = (ImageView) convertView
					.findViewById(R.id.thumbImage);
			holder.txtCategory = (TextView) convertView.findViewById(R.id.text);
			holder.imageView.setPadding(3, 3, 3, 3);
			convertView.setTag(holder);

		} else {

			holder = (ViewHolder) convertView.getTag();
		}
		Category category = plotsImages.get(position);
		String filepath = Environment.getExternalStorageDirectory().getPath()
				+ Constaints.URLImageCategory + category.getImgname() + ".png";

		//Bitmap bm = getImageBitmap(context, category.getImgname());// BitmapFactory.decodeFile(filepath);
		//holder.imageView.setImageBitmap(bm);
		String URLdownloading = context.getResources().getString(
				R.string.URLdownloading);
		imageLoader.DisplayImage(URLdownloading + category.getImgname(),
				holder.imageView);
		holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
		holder.txtCategory.setText(category.getName());
		return convertView;
	}
	// =================return bitmap method========================//
	public Bitmap getImageBitmap(Context context, String imgname) {
		Bitmap bm = null;
		if (imgname != null) {

			int id = context.getResources().getIdentifier(imgname, "drawable",
					context.getPackageName());

			if (id != 0) {
				bm = BitmapFactory.decodeResource(context.getResources(), id);
			}
		}
		return bm;
	}

	@Override
	public int getCount() {
		return plotsImages.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	// ======== viewholder class======//
	private static class ViewHolder {
		ImageView imageView;
		TextView txtCategory;
	}
}
