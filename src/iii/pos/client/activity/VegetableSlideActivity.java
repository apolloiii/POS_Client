package iii.pos.client.activity;

import iii.pos.client.R;
import iii.pos.client.adapter.CategorySlideAdapter;
import iii.pos.client.adapter.VegetViewPagerAdapter;
import iii.pos.client.data.ConfigurationDB;
import iii.pos.client.model.Category;
import iii.pos.client.model.Items;
import iii.pos.client.server.ConfigurationServer;
import iii.pos.client.wsclass.WSAddNewInvDetail;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.Toast;

public class VegetableSlideActivity extends FragmentActivity implements
		OnClickListener {

	private Button btnVegetSlideActivityFood, btnVegetSlideActivityWine,
			btnVegetSlideActivityBeverages;
	private ImageButton btnVegetSlideActivityOk, btnVegetSlideActivityClose;
	private ViewPager viewPageVegetSliActivityItemSlide;
	private VegetViewPagerAdapter adapterveget;
	private ArrayList<Items> lstVegetItem;
	private ConfigurationDB mDB;
	// private String code_table;
	private String inv_code;
	private Context mContext;
	private int category_id = 31;
	private CategorySlideAdapter galImageAdapter;
	private Gallery galleryCateVegetItemSliActivityItem;
	private ArrayList<Category> categoryCurren = new ArrayList<Category>();
	private ArrayList<Category> categoryNext = new ArrayList<Category>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category_slide_vegetable);

		mContext = VegetableSlideActivity.this;
		init();
		// ---------get code_table, inv_code from preview activity----------//
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			inv_code = bundle.getString("INVOICE_CODE");
			Log.d("T:", "INV: " + inv_code);
		}
	}

	private void init() {
		btnVegetSlideActivityClose = (ImageButton) findViewById(R.id.btnVegetSlideActivityClose);
		btnVegetSlideActivityFood = (Button) findViewById(R.id.btnVegetSlideActivityFood);
		btnVegetSlideActivityWine = (Button) findViewById(R.id.btnVegetSlideActivityWine);
		btnVegetSlideActivityOk = (ImageButton) findViewById(R.id.btnVegetSlideActivityOk);
		btnVegetSlideActivityBeverages = (Button) findViewById(R.id.btnVegetSlideActivityBeverages);

		btnVegetSlideActivityFood.setOnClickListener(this);
		btnVegetSlideActivityWine.setOnClickListener(this);
		btnVegetSlideActivityOk.setOnClickListener(this);
		btnVegetSlideActivityBeverages.setOnClickListener(this);
		btnVegetSlideActivityClose.setOnClickListener(this);
		mDB = new ConfigurationDB(VegetableSlideActivity.this);
		viewPageVegetSliActivityItemSlide = (ViewPager) findViewById(R.id.gridVegetableSliActivityItemSlide);
		lstVegetItem = new ArrayList<Items>();
		galleryCateVegetItemSliActivityItem = (Gallery) findViewById(R.id.galleryCateVegetItemSliActivityItem);
		galImageAdapter = new CategorySlideAdapter(this, categoryCurren);
		galleryCateVegetItemSliActivityItem.setAdapter(galImageAdapter);

		getDrawablesList();
		// ------------------choose category----------------------------//
		galleryCateVegetItemSliActivityItem
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						category_id = categoryCurren.get(position)
								.getCategory_id();
						// --------------load items with
						// cate_id-----------------//
						// loaddataItem(2);
						if (new ConfigurationServer(mContext).isOnline()) {
							new WSGetItem().execute();
						} else {
							Toast.makeText(mContext, "Network not found",
									Toast.LENGTH_SHORT).show();
						}

					}
				});
		if (new ConfigurationServer(mContext).isOnline()) {
			new WSGetItem().execute();
		} else {
			Toast.makeText(mContext, "Network not found", Toast.LENGTH_SHORT)
					.show();
		}

	}

	// ----------Threading make data item-------//
	private class WSGetItem extends AsyncTask<Void, Void, Void> {

		private ProgressDialog progressDialog;

		public WSGetItem() {
			progressDialog = new ProgressDialog(VegetableSlideActivity.this);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			progressDialog.setMessage("Loading...");
			progressDialog.setCancelable(false);
			progressDialog.show();

		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				// ---------------get all wine from bean-----------------//
				MainPosActivity.beanDataAll.makeDataItems(category_id);
			} catch (Exception e) {
				Log.i("Log : ", "Exception : " + e.getMessage());
				progressDialog.dismiss();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			lstVegetItem = MainPosActivity.beanDataAll.lstItems;
			adapterveget = new VegetViewPagerAdapter(
					getSupportFragmentManager(), lstVegetItem);
			viewPageVegetSliActivityItemSlide.setAdapter(adapterveget);
			if (progressDialog != null)
				progressDialog.dismiss();
		}

	}

	// ---------action next, back page-------//
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnVegetSlideActivityFood:
			Toast.makeText(VegetableSlideActivity.this, getResources().getString(R.string.food),
					Toast.LENGTH_SHORT).show();
			startActivity(new Intent(this, CategoryItemSlideActivity.class)
					.putExtra(MainPosActivity.INVOICE_CODE, inv_code));
			finish();
			break;
		case R.id.btnVegetSlideActivityWine:
			startActivity(new Intent(this, WineSlideActivity.class).putExtra(
					MainPosActivity.INVOICE_CODE, inv_code));
			finish();
			Toast.makeText(VegetableSlideActivity.this, getResources().getString(R.string.drinks),
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.btnVegetSlideActivityOk:
			try {
				addVegetable();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		
			break;
		case R.id.btnVegetSlideActivityClose:
			try {
				finish();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			break;
		case R.id.btnVegetSlideActivityBeverages:
			Toast.makeText(VegetableSlideActivity.this, getResources().getString(R.string.coffe),
					Toast.LENGTH_SHORT).show();
			break;
		}
	}

	// --------------add inv_detail method-----------------//
	private void addVegetable() {

		if (!MainPosActivity.beanInvDetailTmpl.isNull()) {
			// ---------insert invoice_detail to bean---------------//
			// MainPosActivity.beanDataAll.addListInvoiceDetailNEW(
			// MainPosActivity.beanInvDetailTmpl.getListItem(), inv_code);

			// --------insert invoice_detail to server--------------//
			new WSAddNewInvDetail(mContext, inv_code,
					MainPosActivity.beanInvDetailTmpl.getListItem()).execute();
			/*----------------------insert invoice_detail to Sqlite------------------*/
			Toast.makeText(getApplicationContext(),
					"Thêm Mới Món Ăn Thành Công", Toast.LENGTH_SHORT).show();
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					new ContextThemeWrapper(
							VegetableSlideActivity.this,
							android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar));
			builder.setIcon(android.R.drawable.ic_menu_delete)
					.setTitle(getResources().getString(R.string.notinsertfood))
					.setMessage(getResources().getString(R.string.addinvdetailmsg))
					.setCancelable(false)
					.setPositiveButton(getResources().getString(R.string.OK),
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									finish();
									return;
								}
							})
					.setNegativeButton(getResources().getString(R.string.Cancel),
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									arg0.cancel();
								}
							}).show();
		}

	}

	// ------------ get category from bean-------------//
	private void getDrawablesList() {
		mDB.OpenDB(); //
		// categoryNext = MainPosActivity.beanDataAll.lstcategory;
		categoryNext = MainPosActivity.beanDataAll.getCategoryByParent(3);// 3--vegetable

		for (int i = 0; i < categoryNext.size(); i++) {
			categoryCurren.add(categoryNext.get(i));
		}
		Log.d("Cate:", "  " + categoryNext.size());
		galImageAdapter.notifyDataSetChanged();
		mDB.closeDB();
	}
}
