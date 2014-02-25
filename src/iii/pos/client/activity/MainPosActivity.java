package iii.pos.client.activity;

import iii.pos.client.R;
import iii.pos.client.activity.base.FragmentActivityBase;
import iii.pos.client.activity.helper.MainPosActivityHelper;
import iii.pos.client.activity.helper.MainPosActivityHelper.OnListenerLogInOrOut;
import iii.pos.client.data.Constaints;
import iii.pos.client.data.UserDB;
import iii.pos.client.fragment.HeaderPosFragment;
import iii.pos.client.fragment.InvoiceDetailPosFragment;
import iii.pos.client.fragment.InvoiceDetailPosFragment.IAddMenu;
import iii.pos.client.fragment.InvoicePosFragment;
import iii.pos.client.fragment.InvoicePosFragment.IAddFragment;
import iii.pos.client.fragment.MapFloorFragment.ISelectItable;
import iii.pos.client.menu.BeanInvDetailTmpl;
import iii.pos.client.model.Invoice_Detail;
import iii.pos.client.model.User;
import iii.pos.client.server.BeanDataAll;
import iii.pos.client.server.ConfigurationServer;
import iii.pos.client.server.ConfigurationWS;
import iii.pos.client.wsclass.WSAddLogPos;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainPosActivity extends FragmentActivityBase
		implements HeaderPosFragment.OnHeaderSelectedListener, IAddFragment,
		ISelectItable, IAddMenu {

	public final static String CODE_TABLE = "CODE_TABLE";
	public final static String INVOICE_CODE = "INVOICE_CODE";
	public final static String SAVE_PASSWORD_KEY = "SavePass";

	private String URL = "";
	private View footer = null;
	public static User user;

	public static int user_id = 0;
	public static LinearLayout category_container;
	public static String inv_code;
	public static String code_table;
	public static BeanInvDetailTmpl beanInvDetailTmpl;
	public static BeanDataAll beanDataAll;

	//private MyShareprefer myShare;
	//private ConfigurationDB mDB;
	private UserDB userDB;

	private ConfigurationWS mWS;
	private Fragment myBodyFragemnt = null;
	public WSAddLogPos wsAddLogPos;
	
	private MainPosActivityHelper posActivityHelper;
	public static String username;
	public static String pass;
	public static long phoneNumber;
	
	private String TAG = "";
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_pos);
		initView();
		//new DisplayScreen(getApplicationContext()).displayMultiScreen(120, 200);
	}
	
	public void initView(){
		policy();

		String tempURL = new ConfigurationServer(MainPosActivity.this).getURLServerFromFile();
		ConfigurationServer.setURLServer(tempURL);
		
		//mDB = new ConfigurationDB(getApplicationContext());
		//mDB.OpenDB();
		userDB = new UserDB(this);

		beanDataAll = new BeanDataAll(MainPosActivity.this);
		beanInvDetailTmpl = new BeanInvDetailTmpl();
		
		mWS = new ConfigurationWS(this);
		//myShare = new MyShareprefer(getApplicationContext(), MyShareprefer.GET_INFOUSER_ITEM);

		category_container = (LinearLayout) this.findViewById(R.id.category_container);
		category_container.setVisibility(LinearLayout.GONE);
		
		user = new User();
		footer = (View) findViewById(R.id.footer_Pos_Fragment);

		//MapPosFragment mapPos = new MapPosFragment();
		//switchFragment(mapPos, "map pos fragment");
		loadFragment(2, 100, "", 1);
		/*if (myShare.getUerItem().equals("null") && myShare.getPassWordItem().equals("null") && myShare.getUser_id() == -1) {
		}*/

		if (ConfigurationServer.body != 0)
			//loadActivtyChild();
		
		posActivityHelper = new MainPosActivityHelper(this, mWS, userDB, user);
		
		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			logInOrOut(this);
		}
	}

	@Override
	public void onMenuButtonClick(int btnKey, View v) {
		loadFragment(btnKey, 100, "", 1);
	}

	private void loadFragment(int id, int status, String table_code, int user_id) {
		switch (id) {
		/*case 1:
			ConfigurationServer.body = id;
			category_container.setVisibility(LinearLayout.GONE);
			if (ConfigurationServer.floor != 0) {
				myBodyFragemnt = new MapPosFragment(ConfigurationServer.floor);
			} else
				myBodyFragemnt = new MapPosFragment();
			TAG = "map pos fragment";
			break;*/
		case 2:
			ConfigurationServer.body = id;
			ConfigurationServer.status = status;
			ConfigurationServer.table_code = table_code;
			ConfigurationServer.user_id = user_id;
			Log.e(">>>>>>>>>>>>>>>title", "" + user.getTitle());
			if("".equals(user.getTitle())){
				String title = Constaints.titleUser;
				myBodyFragemnt = new InvoicePosFragment(status, table_code, user_id, title);
			}else{
				myBodyFragemnt = new InvoicePosFragment(status, table_code, user_id, user.getTitle());
			}
			TAG = "invoice pos fragment";
			break;
		/*case 3:
			break;
		case 4:
			break;
		case 5:
			logInOrOut(this);
			new WSUpdateLogPos(MainPosActivity.this, MainPosActivity.user_id).execute();
			TextView footerText = (TextView) footer.findViewById(R.id.usernamestatus);
			footerText.setText("USER:                --- " +getResources().getString(R.string.logintime)+" 00:00:00");
			break;
		default:
			break;*/

		}
		if (myBodyFragemnt != null && !"".equals(TAG)) {
			switchFragment(myBodyFragemnt, TAG);
		}
	}

	// ------------------add INV_DETAIL to main Activity------------//
	private void initInviceDetail(boolean status, String inv_code, String code_table, ArrayList<Invoice_Detail> listItem, int vat, int Committion) {
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		Fragment inv_detail = new InvoiceDetailPosFragment(status, inv_code, code_table, listItem, 0, 0);
		fragmentTransaction.replace(R.id.category_container, inv_detail);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}

	// ---------Called when user pressed login--------------------//
	private void logInOrOut(Context context) {
		URL = ConfigurationServer.getURLServer() + "wslogin.php";
		posActivityHelper.setOnListenerLogInOrOut(new OnListenerLogInOrOut() {
			
			@Override
			public void onLoginSuccess(String username) {
				MainPosActivity.username = username;
				String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
				TextView footerText = (TextView) footer.findViewById(R.id.usernamestatus);
				footerText.setText("USER: " + username.toUpperCase() + "---" + getResources().getString(R.string.logintime) + currentDateTimeString.toUpperCase());
				user.setUsername(username);
				// ===============Insert user to Log==============//
				new WSAddLogPos(MainPosActivity.this, user_id).execute();
			}
			
			@Override
			public void onBackPressd() {
				onBackPressed();
			}
		});
		posActivityHelper.logInOrOut(URL);
	}

	// -------------update language----------------------------//
	@Override
	public void updateLanguage(String languageKey, View view) {
		Bundle bl = new Bundle();
		bl.putString("KEY", languageKey);
		
		Intent t = new Intent(this, MainPosActivity.class);

		t.putExtras(bl);
		startActivity(t);
		this.finish();
	}

	// ----------loading when initialize-------------------//
	private void loadActivtyChild() {
		loadFragment(ConfigurationServer.body, ConfigurationServer.status, ConfigurationServer.table_code, ConfigurationServer.user_id);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		System.gc();
		finish();
	}

	@Override
	public void addfragment(boolean status, String inv_code, String code_table,
			ArrayList<Invoice_Detail> lstInvDetail, int vat, int Committion) {
		initInviceDetail(status, inv_code, code_table, lstInvDetail, vat,
				Committion);
	}

	@Override
	public void onSelectItable(int id, int status, String table_code, int user_id) {
		loadFragment(id, status, table_code, user_id);
	}

	@Override
	public void addMenuActivity(String inv_code) {
		// TODO Auto-generated method stub
		if (MainPosActivity.beanInvDetailTmpl != null) {
			MainPosActivity.beanInvDetailTmpl.removeList();
		}
		MainPosActivity.inv_code = inv_code;
		startActivityForResult(new Intent(MainPosActivity.this, CategoryItemSlideActivity.class).putExtra(MainPosActivity.INVOICE_CODE, inv_code), 1);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		try {
			if (requestCode == 1) {
				new WSGetAnInvDetail(MainPosActivity.inv_code).execute();
			}
		} catch (Exception e) {
		}
	}

	// ========Threading make data invoice_detail by invcode==============//
	public class WSGetAnInvDetail extends AsyncTask<Void, Void, Void> {

		private ProgressDialog progressDialog;
		private ConfigurationWS mWS;
		private String inv_code = "";

		public WSGetAnInvDetail(String invcode) {
			this.inv_code = invcode;
			mWS = new ConfigurationWS(MainPosActivity.this);
			progressDialog = new ProgressDialog(MainPosActivity.this);
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
				MainPosActivity.beanDataAll.makeDataInvDetail();
			} catch (Exception e) {
				Log.i("Log : ", "Exception : " + e.getMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			category_container.setVisibility(LinearLayout.VISIBLE);

			ArrayList<Invoice_Detail> arr = MainPosActivity.beanDataAll.getlstInvDetail(inv_code);

			initInviceDetail(false, inv_code, inv_code, arr, 0, 0);
			if (progressDialog != null)
				progressDialog.dismiss();
		}
	}
	
	// ================== Policy ===================//
	private void policy() {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
	}
	
	@Override
	public void onBackPressed() {
		Toast.makeText(MainPosActivity.this, getResources().getString(R.string.exitlogout), Toast.LENGTH_SHORT).show();
	}
	
}
