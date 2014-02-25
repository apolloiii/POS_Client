package iii.pos.client.adapter;

import iii.pos.client.util.ImageLoader;

import java.util.List;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;


public class WineGalleryImageAdapter extends BaseAdapter {
	// ===============fields=========================//
	private Activity context;
	private static ImageView imageView;
	private List<Drawable> plotsImages;
	private static ViewHolder holder;
	private ImageLoader imageLoader;
	// ===============constructor=============================//
	public WineGalleryImageAdapter(Activity context, List<Drawable> plotsImages) {
		this.context = context;
		this.plotsImages = plotsImages;
		imageLoader = new ImageLoader(context);
	}
	// =================initialize here=========================//
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {

			holder = new ViewHolder();

			imageView = new ImageView(this.context);

			imageView.setPadding(10, 10, 10, 10);

			convertView = imageView;

			holder.imageView = imageView;

			convertView.setTag(holder);

		} else {

			holder = (ViewHolder) convertView.getTag();
		}

		holder.imageView.setImageDrawable(plotsImages.get(position));
		holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
		holder.imageView.setLayoutParams(new Gallery.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

		return imageView;
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

	private static class ViewHolder {
		ImageView imageView;
	}

}
