package iii.pos.client.activity;

import iii.pos.client.R;
import iii.pos.client.adapter.CategorySlideWineAdapter;
import iii.pos.client.adapter.WineViewPagerAdapter;
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
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.Toast;

//=============class activity for wine=====================//
@SuppressWarnings("deprecation")
public class WineSlideActivity extends FragmentActivity implements
		OnClickListener {
	// ======================= fields ============================//
	private Button btnWineSlideActivityFood, btnWineSlideActivityWine,btnWineSlideActivityBeverages;
	private ImageButton btnWineSlideActivityOk, btnWineSlideActivityClose;
	private ViewPager gridWineSliActivityItemSlide;
	private WineViewPagerAdapter adapterWine;
	private ArrayList<Items> listItem;
	private ConfigurationDB mDB;
	private String inv_code;
	private Context mContext;
	private int category_id = 22;
	@SuppressWarnings("deprecation")
	private Gallery galleryCateWineItemSliActivityItem;
	private CategorySlideWineAdapter galImageAdapter;

	private ArrayList<Category> categoryCurren = new ArrayList<Category>();
	private ArrayList<Category> categoryNext = new ArrayList<Category>();

	// =============== create view here========================//
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_win_slide_activity);
		mDB = new ConfigurationDB(WineSlideActivity.this);
		this.mContext = WineSlideActivity.this;
		init();
		// ==========-get code_table, inv_code from preview activity=======//
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			inv_code = bundle.getString("INVOICE_CODE");
			Log.d("T:", "INV: " + inv_code);
		}
	}

	// =============== get category from bean===================//
	private void getDrawablesList() {
		categoryNext = MainPosActivity.beanDataAll.getCategoryByParent(2);// 2--wine
		for (int i = 0; i < categoryNext.size(); i++) {
			categoryCurren.add(categoryNext.get(i));
		}
		galImageAdapter.notifyDataSetChanged();
	}

	// ==============init all control here==========================//
	private void init() {
		btnWineSlideActivityFood = (Button) findViewById(R.id.btnWineSlideActivityFood);
		btnWineSlideActivityWine = (Button) findViewById(R.id.btnWineSlideActivityWine);
		btnWineSlideActivityOk = (ImageButton) findViewById(R.id.btnWineSlideActivityOk);
		btnWineSlideActivityClose = (ImageButton) findViewById(R.id.btnWineSlideActivityClose);
		btnWineSlideActivityBeverages = (Button) findViewById(R.id.btnWineSlideActivityBeverages);

		btnWineSlideActivityFood.setOnClickListener(this);
		btnWineSlideActivityWine.setOnClickListener(this);
		btnWineSlideActivityOk.setOnClickListener(this);
		btnWineSlideActivityBeverages.setOnClickListener(this);
		btnWineSlideActivityClose.setOnClickListener(this);
		gridWineSliActivityItemSlide = (ViewPager) findViewById(R.id.gridWineSliActivityItemSlide);
		galleryCateWineItemSliActivityItem = (Gallery) findViewById(R.id.galleryCateWineItemSliActivityItem);
		galImageAdapter = new CategorySlideWineAdapter(this, categoryCurren);
		galleryCateWineItemSliActivityItem.setAdapter(galImageAdapter);

		getDrawablesList();
		/*if (new ConfigurationServer(mContext).isOnline()) {
			new WSGetItem().execute();
		} else {
			Toast.makeText(mContext, "Network not found", Toast.LENGTH_SHORT)
					.show();
		}*/

		// =====================choose category========================//
		galleryCateWineItemSliActivityItem
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						// ==========get cate when user click===========//
						category_id = categoryCurren.get(position)
								.getCategory_id();
						if (new ConfigurationServer(mContext).isOnline()) {
							new WSGetItem().execute();
						} else {
							Toast.makeText(mContext, "Network not found",
									Toast.LENGTH_SHORT).show();
						}

					}
				});
		// ==============check online===================//
		if (new ConfigurationServer(mContext).isOnline()) {
			new WSGetItem().execute();
		} else {
			Toast.makeText(mContext, "Network not found", Toast.LENGTH_SHORT)
					.show();
		}

	}

	// ==================Threading make data item==============//
	private class WSGetItem extends AsyncTask<Void, Void, Void> {
		
		private ProgressDialog progressDialog;
		public WSGetItem() {
			progressDialog = new ProgressDialog(WineSlideActivity.this);
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
				Log.d("WINE", "CATE ID : " + category_id);
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
			listItem = MainPosActivity.beanDataAll.lstItems;
			adapterWine = new WineViewPagerAdapter(getSupportFragmentManager(),
					listItem);

			gridWineSliActivityItemSlide.setAdapter(adapterWine);
			if (progressDialog != null)
				progressDialog.dismiss();
		}

	}

	// ---------action next, back page-------//
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnWineSlideActivityFood:
			Toast.makeText(WineSlideActivity.this, getResources().getString(R.string.food), Toast.LENGTH_SHORT)
					.show();
			startActivity(new Intent(this, CategoryItemSlideActivity.class)
					.putExtra(MainPosActivity.INVOICE_CODE, inv_code));
			finish();
			break;
		case R.id.btnWineSlideActivityWine:
			Toast.makeText(WineSlideActivity.this, getResources().getString(R.string.drinks), Toast.LENGTH_SHORT)
					.show();
			break;
		case R.id.btnWineSlideActivityOk:
			try {
				addWine();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			break;
		case R.id.btnWineSlideActivityClose:
			try {
				finish();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			break;
		case R.id.btnWineSlideActivityBeverages:
			startActivity(new Intent(this, VegetableSlideActivity.class)
					.putExtra(MainPosActivity.INVOICE_CODE, inv_code));
			Toast.makeText(WineSlideActivity.this,getResources().getString(R.string.coffe),
					Toast.LENGTH_SHORT).show();
			finish();
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	// --------------add inv_detail method-----------------//
	private void addWine() {

		if (!MainPosActivity.beanInvDetailTmpl.isNull()) {
			// ---------insert invoice_detail to bean---------------//
			//MainPosActivity.beanDataAll.addListInvoiceDetailNEW(
			//		MainPosActivity.beanInvDetailTmpl.getListItem(), inv_code);
			// --------insert invoice_detail to server--------------//
			new WSAddNewInvDetail(mContext, inv_code,
					MainPosActivity.beanInvDetailTmpl.getListItem()).execute();
			/*----------------------insert invoice_detail to Sqlite------------------*/
			Toast.makeText(getApplicationContext(),
					"Thêm Mới Món Ăn Thành Công", Toast.LENGTH_SHORT).show();
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					new ContextThemeWrapper(
							WineSlideActivity.this,
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

}
