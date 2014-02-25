package iii.pos.client.adapter;

import iii.pos.client.R;
import iii.pos.client.data.Constaints;
import iii.pos.client.model.Items;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class ListDishesAdapter extends ArrayAdapter<Items> {
	// ===============fields=============================//
	private Context context;
	private List<Items> itemlist;
	public static List<Items> listItemSelect = new ArrayList<Items>();
	private int quantityItems = 1;
	int pos;
	int arrim[] = { R.drawable.soupga, R.drawable.cakho,
			R.drawable.canhraumuong, R.drawable.laumam };

	// ===============constructor=============================//
	public ListDishesAdapter(Context context, int textViewResourceId,
			List<Items> objects) {
		super(context, textViewResourceId, objects);
		this.itemlist = objects;
		this.context = context;
	}

	// =================initialize here=====================================//
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View item = convertView;
		try {

			if (item == null) {
				LayoutInflater inflate = (LayoutInflater) this.context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				item = inflate.inflate(R.layout.temp_listitem, null);
			}
			final Items itemdetail = itemlist.get(position);
			if (itemdetail != null) {
				TextView tvName = (TextView) item.findViewById(R.id.tvNameItem);
				TextView tvDetail = (TextView) item
						.findViewById(R.id.tvDetailItem);
				TextView tvPrice = (TextView) item
						.findViewById(R.id.tvPriceItem);

				float Price = itemdetail.getPrice();

				tvName.setText(String.valueOf(itemdetail.getName()));
				tvDetail.setText(String.valueOf(itemdetail.getDescription()));
				tvPrice.setText(String.valueOf(Price + " VNĐ"));

				final Spinner quantity = (Spinner) item
						.findViewById(R.id.textQuantity);

				ArrayAdapter<CharSequence> adapter = ArrayAdapter
						.createFromResource(getContext(),
								R.array.arrQuantityItem,
								android.R.layout.simple_spinner_item);
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				quantity.setAdapter(adapter);

				quantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {

						quantityItems = Integer.parseInt(String
								.valueOf(quantity.getSelectedItem()));
						int positionspinner = quantity
								.getSelectedItemPosition();
						doCheck(itemlist, position, quantityItems,
								positionspinner);
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});
				final CheckBox checkselect = (CheckBox) item
						.findViewById(R.id.checkSelect);
				if (checkselect.isChecked() == false) {
					quantity.setVisibility(Spinner.GONE);
				}
				checkselect
						.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
							@Override
							public void onCheckedChanged(
									CompoundButton buttonView, boolean isChecked) {

								if (checkselect.isChecked() == false) {
									quantity.setVisibility(EditText.GONE);
									doUnCheck(itemlist, position);
								}
								if (checkselect.isChecked() == true) {

									quantityItems = Integer.parseInt(String
											.valueOf(quantity.getSelectedItem()));

									int positionspinner = quantity
											.getSelectedItemPosition();
									doCheck(itemlist, position, quantityItems,
											positionspinner);
									quantity.setVisibility(Spinner.VISIBLE);

								}
							}
						});

				String filepath = Environment.getExternalStorageDirectory()
						.getPath()
						+ Constaints.URLImageCategory
						+ itemdetail.getImgName() + ".png";

				Bitmap bm = BitmapFactory.decodeFile(filepath);
				ImageView im = (ImageView) item.findViewById(R.id.image_view);
				im.setImageBitmap(bm);
			}

		} catch (Exception e) {
			Log.i(getClass().getName(), e.getMessage());
		}
		return item;
	}

	// =================check item exist===========================//
	private void doCheck(List<Items> list, int pointion, int quantityitem,
			int poisitionSpinner) {
		if (listItemSelect.size() > 0) {
			boolean check = true;
			int it = 0;
			int item_id = list.get(pointion).getItem_id();
			for (int i = 0; i < listItemSelect.size(); i++) {
				int id_item = listItemSelect.get(i).getItem_id();
				if (item_id == id_item) {
					check = false;
					it = i;
				}
			}
			if (check) {
				listItemSelect.add(list.get(pointion));
			} else {
				listItemSelect.get(it).setQuantityItem(quantityitem);
			}
		} else {
			list.get(pointion).setQuantityItem(quantityitem);
			listItemSelect.add(list.get(pointion));
		}

	}

	// ==================check uncheck===================//
	private void doUnCheck(List<Items> list, int pointion) {
		int item_id = list.get(pointion).getItem_id();

		for (int i = 0; i < listItemSelect.size(); i++) {
			if (listItemSelect.get(i).getItem_id() == item_id) {
				listItemSelect.remove(i);
			}
		}
	}

}
