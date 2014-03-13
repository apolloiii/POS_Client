package iii.pos.client.activity;

import iii.pos.client.R;
import iii.pos.client.adapter.CategorySlideAdapter;
import iii.pos.client.library.FormatFloorTableName;
import iii.pos.client.library.GetDeviceInfo;
import iii.pos.client.menu.AdapterViewPager;
import iii.pos.client.model.Category;
import iii.pos.client.model.Invoice;
import iii.pos.client.model.Items;
import iii.pos.client.server.BeanDataAll;
import iii.pos.client.server.ConfigurationServer;
import iii.pos.client.wsclass.WSAddNewInvDetail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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

/*------------this class to display items to user chosen-----------*/
@SuppressWarnings("deprecation")
public class CategoryItemSlideActivity extends FragmentActivity implements
		OnClickListener {

	// -------------------------fields---------------------------------//
	private Gallery galleryCateItemSliActivityItem;
	private CategorySlideAdapter galImageAdapter;

	private ViewPager gridCateItemSliActivityItem;
	private AdapterViewPager adapterViewPager;

	private Button btnCateItemSliActivityFood, btnCateItemSliActivityWine,
			btnCateItemSliActivityBeverages;
	private ImageButton btnCateItemSliActivityOk, btnCateItemSliActivityClose;

	// ----------Configuration database file-------------------------------//
	private Activity context = CategoryItemSlideActivity.this;
	// --------------------------------------------------------------------//
	private ArrayList<Category> categoryCurren = new ArrayList<Category>();
	private ArrayList<Category> categoryNext = new ArrayList<Category>();
	private ArrayList<Items> listItem;

	// private String code_table;
	private String inv_code;
	private int category_id = 1;
	// ---------------------------

	//private ConfigurationDB mDB;

	// ----------initialize view---------------------//
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category_slide);
		
		/*String phoneId = new GetDeviceInfo(getApplicationContext()).getDeviceId();
		String phoneNumber = new GetDeviceInfo(getApplicationContext()).getPhoneNumber();
		Toast.makeText(context, phoneId +"   "+phoneNumber, Toast.LENGTH_SHORT).show();
		Toast.makeText(context, "  "+phoneNumber, Toast.LENGTH_SHORT).show();*/
		
		// ---------get code_table, inv_code from preview activity----------//
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			inv_code = bundle.getString("INVOICE_CODE");
			//makeDataInvoiceMoke(inv_code);
			//Toast.makeText(getApplicationContext(), "Get Intent " + inv_code, Toast.LENGTH_LONG) .show();
		}

		//mDB = new ConfigurationDB(context);

		gridCateItemSliActivityItem = (ViewPager) findViewById(R.id.viewPagerCateItemSliActivityItem);
		btnCateItemSliActivityClose = (ImageButton) findViewById(R.id.btnCateItemSliActivityClose);
		btnCateItemSliActivityFood = (Button) context .findViewById(R.id.btnCateItemSliActivityFood);
		btnCateItemSliActivityWine = (Button) context .findViewById(R.id.btnCateItemSliActivityWine);
		btnCateItemSliActivityOk = (ImageButton) context .findViewById(R.id.btnCateItemSliActivityOk);
		btnCateItemSliActivityBeverages = (Button) context .findViewById(R.id.btnCateItemSliActivityBeverages);
		btnCateItemSliActivityClose.setOnClickListener(this);
		btnCateItemSliActivityFood.setOnClickListener(this);
		btnCateItemSliActivityWine.setOnClickListener(this);
		btnCateItemSliActivityOk.setOnClickListener(this);
		btnCateItemSliActivityBeverages.setOnClickListener(this);
		galleryCateItemSliActivityItem = (Gallery) findViewById(R.id.galleryCateItemSliActivityItem);
		galImageAdapter = new CategorySlideAdapter(this, categoryCurren);
		galleryCateItemSliActivityItem.setAdapter(galImageAdapter);
		getDrawablesList();

		// ------------------choose category----------------------------//
		galleryCateItemSliActivityItem
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
						category_id = categoryCurren.get(position) .getCategory_id();
						if (new ConfigurationServer(context).isOnline()) {
							new WSGetItem().execute();
						} else {
							Toast.makeText(context, "Network not found", Toast.LENGTH_LONG).show();
						}
					}
				});

		if (new ConfigurationServer(context).isOnline()) {
			new WSGetItem().execute();
		} else {
			Toast.makeText(context, "Network not found", Toast.LENGTH_LONG) .show();
		}

	}

	/**
	 * Tạo mới invoice lưu trữ vào beanAllData
	 * @param sInvCode : Guest_T1_B2_20142402121212
	 *  
	 */
	/*private void makeDataInvoiceMoke(String sInvCode){
		String codeTable = new FormatFloorTableName().joinFloorAndTable(sInvCode); // T1_B2
		String simpDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
		
		List<Invoice> lstInvoices = BeanDataAll.getLstInvoiceMoke();
		if( lstInvoices.size() > 0){
			for (Invoice inv : lstInvoices) {
				if( !inv.getLstCodeTables().contains(codeTable) ){ // Kiểm tra trong bean
					Invoice invoice = new Invoice();
					invoice.setInv_id(1);
					invoice.setInv_code(sInvCode);
					//invoice.setTotal( Float.parseFloat("10000") );
					//invoice.setCost( Float.parseFloat("3000") );
					//invoice.setVat( Integer.parseInt("5") );
					//invoice.setCommision( Integer.parseInt("0") );
					invoice.setInv_starttime(simpDate);
					invoice.setInv_endtime(simpDate);
					invoice.setUser_id(22);
					invoice.setStatus(1);
					ArrayList<String> listCodeTable = new ArrayList<String>();
						listCodeTable.add(codeTable); // Lấy T1_B1
					invoice.setLstCodeTables(listCodeTable);
					BeanDataAll.setLstInvoiceMoke(invoice);
				}
			}
		}else{
			Invoice invoice = new Invoice();
			invoice.setInv_id(1);
			invoice.setInv_code(sInvCode);
			//invoice.setTotal( Float.parseFloat("10000") );
			//invoice.setCost( Float.parseFloat("3000") );
			//invoice.setVat( Integer.parseInt("5") );
			//invoice.setCommision( Integer.parseInt("0") );
			invoice.setInv_starttime(simpDate);
			invoice.setInv_endtime(simpDate);
			invoice.setUser_id(22);
			invoice.setStatus(1);
			ArrayList<String> listCodeTable = new ArrayList<String>();
				listCodeTable.add(codeTable); // Lấy T1_B1
			invoice.setLstCodeTables(listCodeTable);
			BeanDataAll.setLstInvoiceMoke(invoice);
		}
	}*/
	
	
	// ----------Threading make data item-------//
	private class WSGetItem extends AsyncTask<Void, Void, Void> {

		private ProgressDialog progressDialog;

		public WSGetItem() {
			progressDialog = new ProgressDialog(CategoryItemSlideActivity.this);
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
			setAdapterListslide();
			if (progressDialog != null)
				progressDialog.dismiss();
		}

	}

	// -------------set category ------------------------//
	private void setAdapterListslide() {
		try {
			adapterViewPager = new AdapterViewPager( getSupportFragmentManager(), listItem );
			gridCateItemSliActivityItem.setAdapter(adapterViewPager);
		} catch (Exception e) {
			Toast.makeText(getBaseContext(), "Error here ", Toast.LENGTH_SHORT) .show();
		}
	}

	// ------------ get category from bean-------------//
	private void getDrawablesList() {
		///mDB.OpenDB(); //
		categoryNext = MainPosActivity.beanDataAll.getCategoryByParent(1);// 1--food
		for (int i = 0; i < categoryNext.size(); i++) {
			categoryCurren.add(categoryNext.get(i));
		}
		Log.d("Cate:", "  " + categoryNext.size());
		galImageAdapter.notifyDataSetChanged();
		//mDB.closeDB();
	}

	// ---------action next, back page-------//
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnCateItemSliActivityClose:
			// Toast.makeText(CategoryItemSlideActivity.this, "Close",
			// Toast.LENGTH_SHORT).show();
			try {
				finish();
			} catch (Exception e) { }

			break;
		case R.id.btnCateItemSliActivityFood:
			Toast.makeText(CategoryItemSlideActivity.this, getResources().getString(R.string.food), Toast.LENGTH_SHORT) .show();
			break;
		case R.id.btnCateItemSliActivityWine:
			startActivity(new Intent(CategoryItemSlideActivity.this, WineSlideActivity.class).putExtra( MainPosActivity.INVOICE_CODE, inv_code));
			finish();
			Toast.makeText(CategoryItemSlideActivity.this, getResources().getString(R.string.drinks), Toast.LENGTH_SHORT).show();
			break;
		case R.id.btnCateItemSliActivityOk:
			try {
				addLstInv_Detail();
			} catch (Exception e) { }

			break;
		case R.id.btnCateItemSliActivityBeverages:
			startActivity(new Intent(CategoryItemSlideActivity.this, VegetableSlideActivity.class).putExtra( MainPosActivity.INVOICE_CODE, inv_code));
			finish();
			Toast.makeText(CategoryItemSlideActivity.this, getResources().getString(R.string.coffe), Toast.LENGTH_SHORT).show();
			break;
		}
	}

	// --------------add inv_detail method-----------------//
	private void addLstInv_Detail() {

		if (!MainPosActivity.beanInvDetailTmpl.isNull()) {
			// ---------insert invoice_detail to bean---------------//
			// MainPosActivity.beanDataAll.addListInvoiceDetailNEW(
			// MainPosActivity.beanInvDetailTmpl.getListItem(), inv_code);
			// --------insert invoice_detail to server--------------//
			new WSAddNewInvDetail(context, inv_code, MainPosActivity.beanInvDetailTmpl.getListItem()).execute();
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					new ContextThemeWrapper(
							CategoryItemSlideActivity.this,
							android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar));
			builder.setIcon(android.R.drawable.ic_menu_delete)
					.setTitle(getResources().getString(R.string.notinsertfood))
					.setMessage(
							getResources().getString(R.string.addinvdetailmsg))
					.setCancelable(false)
					.setPositiveButton(getResources().getString(R.string.OK),
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface arg0, int arg1) {
									finish();
									return;
								}
							})
					.setNegativeButton( getResources().getString(R.string.Cancel),
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface arg0, int arg1) {
									arg0.cancel();
								}
							}).show();
		}

	}
}