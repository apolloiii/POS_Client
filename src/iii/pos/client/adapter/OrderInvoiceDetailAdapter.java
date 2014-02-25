package iii.pos.client.adapter;

import iii.pos.client.R;
import iii.pos.client.model.Inv_Cook;
import iii.pos.client.util.ImageLoader;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderInvoiceDetailAdapter extends BaseAdapter {

	private List<Inv_Cook> lstInvCook;
	private Activity context;
	private InvDetailViewHolder holder;
	private ImageLoader imageLoader;

	public OrderInvoiceDetailAdapter(List<Inv_Cook> lstInvoiceDetail,
			Activity context) {
		super();
		this.lstInvCook = lstInvoiceDetail;
		this.context = context;
		imageLoader = new ImageLoader(context);
	}

	@Override
	public int getCount() {
		return lstInvCook.size();
	}

	@Override
	public Object getItem(int arg0) {
		return lstInvCook.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	// ------------------------------------------------------------------
	@Override
	public View getView(final int position, View view, ViewGroup arg2) {

		if (view == null) {
			holder = new InvDetailViewHolder();
			view = context.getLayoutInflater().inflate(R.layout.row_monan_bep,
					null);
			holder.imgOrderImage = (ImageView) view
					.findViewById(R.id.imgOrderImage);
			holder.tvOrderName = (TextView) view.findViewById(R.id.tvOrderName);
			holder.tvOrderAmount = (Button) view
					.findViewById(R.id.tvOrderAmount);
			holder.btnOrderDalam = (Button) view
					.findViewById(R.id.etOrderDalam);
			holder.etOrderConlai = (Button) view
					.findViewById(R.id.etOrderConlai);

			view.setTag(holder);
		} else {
			holder = (InvDetailViewHolder) view.getTag();
		}

		Inv_Cook inv_cook = lstInvCook.get(position);
		// ------------------------------------------------------------------
		try {
			// holder.imgOrderImage.setImageResource(getIdImageByName(inv_cook
			// .getImgname()));

			holder.tvOrderName.setText(inv_cook.getName());
			holder.tvOrderAmount.setText(inv_cook.getQuantity1() + "");
			// ------------------------------------------------------------------
			holder.etOrderConlai.setText(inv_cook.getQuantity2() + "");
			holder.btnOrderDalam.setText(inv_cook.getQuantity3() + "");
			try {
				String URLdownloading = context.getResources().getString(
						R.string.URLdownloading);
				imageLoader.DisplayImage(
						URLdownloading + inv_cook.getImgname(),
						holder.imgOrderImage);
				// holder.imgView.setImageBitmap(bm);
			} catch (Exception e) {
			}
		} catch (Exception e) {
		}
		return view;
	}

	// ------------------------------------------------------------------
	class InvDetailViewHolder {
		ImageView imgOrderImage;
		TextView tvOrderName;
		Button tvOrderAmount;
		Button btnOrderDalam;
		Button etOrderConlai;

	}

	// ------------------------------------------------------------------
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

	private int getIdImageByName(String name) {
		return context.getResources().getIdentifier(name, "drawable",
				context.getPackageName());
	}
}
