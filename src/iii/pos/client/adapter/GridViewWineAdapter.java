package iii.pos.client.adapter;

import iii.pos.client.R;
import iii.pos.client.activity.MainPosActivity;
import iii.pos.client.data.Constaints;
import iii.pos.client.model.Invoice_Detail;
import iii.pos.client.model.Items;
import iii.pos.client.util.ImageLoader;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class GridViewWineAdapter extends BaseAdapter {

	public static final int DEFAULT_CELL_SIZE = 80;
	private Context mContext;
	private ArrayList<Items> lstItem;
	private int mImageOffset = 0; // the index offset into the list of images
	private int mImageCount = -1; // -1 means that we display all images
	private int mNumTopics = 0; // the total number of topics in the topics
								// collection
	private int mCellWidth = DEFAULT_CELL_SIZE;
	private int mCellHeight = DEFAULT_CELL_SIZE;

	private ArrayList<String> listValue = new ArrayList<String>();
	private int[] quantitytmpl = new int[6];
	private String[] comment = new String[] { "", "", "", "", "", "" };
	private viewHolderWine holder;
	private ImageLoader imageLoader;

	public GridViewWineAdapter(Context c, ArrayList<Items> tlist,
			int imageOffset, int imageCount) {
		mContext = c;
		mImageOffset = imageOffset;
		mImageCount = imageCount;
		lstItem = tlist;
		mNumTopics = (tlist == null) ? 0 : lstItem.size();
		if (listValue != null)
			listValue.clear();
		listValue.add("0");
		listValue.add("1");
		listValue.add("2");
		listValue.add("3");
		listValue.add("4");
		listValue.add("5");
		imageLoader = new ImageLoader(mContext);
	}

	public GridViewWineAdapter(Context c, ArrayList<Items> tlist,
			int imageOffset, int imageCount, int cellWidth, int cellHeight) {
		this(c, tlist, imageOffset, imageCount);
		mCellWidth = cellWidth;
		mCellHeight = cellHeight;
	}

	public int getCount() {
		int count = mImageCount;
		if (mImageOffset + mImageCount >= mNumTopics)
			count = mNumTopics - mImageOffset;
		return count;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return mImageOffset + position;
	}

	// --------------------initialize view--------------------------//
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;
		int numTopics = lstItem.size();
		if (view == null) {
			// if it's not recycled, initialize some attributes
			holder = new viewHolderWine();
			LayoutInflater inflate = (LayoutInflater) this.mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflate.inflate(R.layout.wine_row, null);
			holder.txtCost = (TextView) view.findViewById(R.id.txtCost_1);

			holder.winneImage = (ImageView) view
					.findViewById(R.id.imgWineImage);
			holder.btnCommentWine = (ImageView) view
					.findViewById(R.id.btnCommentWine);
			holder.winneName = (TextView) view.findViewById(R.id.tvWineName);
			holder.wineQuality = (Spinner) view
					.findViewById(R.id.spWineQuality);
			holder.wineChoose = (CheckBox) view.findViewById(R.id.chWineChoose);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
					android.R.layout.simple_spinner_item, android.R.id.text1,
					listValue);

			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			holder.wineQuality.setAdapter(adapter);
			view.setTag(holder);
		} else {
			holder = (viewHolderWine) view.getTag();
		}
		int j = position + mImageOffset;
		if (j < 0)
			j = 0;
		if (j >= numTopics)
			j = numTopics - 1;
		if (j > lstItem.size())
			j = 0;
		try {

			final Items item = lstItem.get(j);

			// --------------IIIPOS check quantity----------------------------//
			holder.wineQuality
					.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
						@Override
						public void onItemSelected(AdapterView<?> arg0,
								View arg1, int arg2, long arg3) {
							item.setQuantityItem(arg2);
							quantitytmpl[position] = arg2;
							Log.d("THUAN:", "VI TRI: " + position
									+ " SOLUONG: " + arg2);
							MainPosActivity.beanInvDetailTmpl.updateItem(item);
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
						}
					});

			// -----------------IIIPOS check--------------------------------//
			holder.wineChoose
					.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							int quantityItem = quantitytmpl[position];
							String comment1 = comment[position];
							if (isChecked) {
								if (quantityItem <= 0) {
									quantityItem = 1;
								}
								Invoice_Detail inv_detail = MainPosActivity.beanInvDetailTmpl
										.check(item);
								if (inv_detail != null && position == 0) {
									item.setQuantityItem(inv_detail
											.getQuantity());
								} else {
									item.setQuantityItem(quantityItem);
								}
								item.setDescription(comment1);
								Log.d("THUAN:", "TICK VITRI: " + position);
								MainPosActivity.beanInvDetailTmpl
										.bindData(item);//
								// add- item to bean
							} else {
								// --------------------remove inv_detail----
								item.setQuantityItem(0);
								MainPosActivity.beanInvDetailTmpl
										.removeItem(item);
							}
						}
					});
			// ------------ Click to Comment ----------------------
			holder.btnCommentWine.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					final Dialog dialog = new Dialog(mContext);
					dialog.setContentView(R.layout.comment_dialog);
					dialog.setTitle(R.string.tvTitleComment);
					final EditText etContentDialog = (EditText) dialog
							.findViewById(R.id.etContentComment);
					Button dialogOK = (Button) dialog
							.findViewById(R.id.btnCommentOK);
					dialogOK.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							item.setDescription(etContentDialog.getText()
									.toString());
							comment[position] = etContentDialog.getText()
									.toString();

							Log.d("WINE ADAPTER:", comment[position]);

							MainPosActivity.beanInvDetailTmpl.updateItem(item);
							dialog.dismiss();
						}
					});
					Button dialogCancel = (Button) dialog
							.findViewById(R.id.btnCommentCancel);
					dialogCancel.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							dialog.dismiss();
						}
					});
					dialog.show();
				}
			});

			// ----------------------display data to grid
			// view------------------//
			setdata(item);
		} catch (Exception e) {
		}
		// -----------------------return view------------------------------//
		return view;
	}

	// ----------------display data to grid view------------------//
	private void setdata(Items item) {
		// Bitmap bm = getImageBitmap(mContext, item.getImgName());//
		// decodeSampledBitmapFromUri(item.getImgName(),
		// 120, 120);
		// holder.winneImage.setImageBitmap(bm);
		String URLdownloading = mContext.getResources().getString(
				R.string.URLdownloading);
		imageLoader.DisplayImage(URLdownloading + item.getImgName(),
				holder.winneImage);
		holder.winneName.setText(item.getName());
		holder.wineQuality.setSelection((int) item.getQuantityItem());
		holder.txtCost.setText(formatDecimal(item.getPrice()) + " đ");
		Invoice_Detail inv_detail = MainPosActivity.beanInvDetailTmpl
				.check(item);
		if (inv_detail != null) {
			holder.wineChoose.setChecked(true);
			holder.wineQuality.setSelection(inv_detail.getQuantity());
		}
	}

	public String formatDecimal(double number) {

		// DecimalFormat nf = new DecimalFormat("###,###,###,##0.00");
		DecimalFormat nf = new DecimalFormat("###,###,###,##0");
		String formatted = nf.format(number);

		return formatted;
	}

	// -----------------this class to holder tag---------------------//
	class viewHolderWine {
		ImageView winneImage;
		TextView txtCost;
		TextView winneName;
		Spinner wineQuality;
		CheckBox wineChoose;
		ImageView btnCommentWine;
	}

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

	public Bitmap decodeSampledBitmapFromUri(String path, int reqWidth,
			int reqHeight) {
		Bitmap bm = null;
		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		String filepath = Environment.getExternalStorageDirectory().getPath()
				+ Constaints.URLImageCategory + path + ".png";
		BitmapFactory.decodeFile(filepath, options);
		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);
		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		bm = BitmapFactory.decodeFile(filepath, options);

		return bm;
	}

	public int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			if (width > height) {
				inSampleSize = Math.round((float) height / (float) reqHeight);
			} else {
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}
		}
		return inSampleSize;
	}

}
