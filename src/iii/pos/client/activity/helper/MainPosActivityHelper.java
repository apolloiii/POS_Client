package iii.pos.client.activity.helper;

import iii.pos.client.R;
import iii.pos.client.activity.MainPosActivity;
import iii.pos.client.adapter.SpinnerAdapterTable;
import iii.pos.client.data.Constaints;
import iii.pos.client.data.UserDB;
import iii.pos.client.fragment.InvoiceDetailPosFragment.IAddMenu;
import iii.pos.client.library.GetCurrentLocationGPS;
import iii.pos.client.library.GetDeviceInfo;
import iii.pos.client.model.Floor;
import iii.pos.client.model.Itable;
import iii.pos.client.model.User;
import iii.pos.client.server.ConfigurationServer;
import iii.pos.client.server.ConfigurationWS;
import iii.pos.client.wsclass.WSAddInvTable;
import iii.pos.client.wsclass.WSAddNewInvoice;
import iii.pos.client.wsclass.WSGetAllItableFreeByFloor;
import iii.pos.client.wsclass.WSGetFloor;
import iii.pos.client.wsclass.WSUpdateItableStatus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
/**
 * Màn hình xử lý Cho Client - Guest.
 * Bước 1: Không cần tài khoản, chỉ cần chọn tầng và chọn bàn
 * Bước 2: Sinh Hóa đơn 
 * Bước 3: Chọn món ăn
 * Bước 4: Hiển thị những món ăn đã chọn hiển thị lên listview Invoice 
 * 
 * final String simpDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
 * 
 * @author GIACNGO
 *
 */
public class MainPosActivityHelper {
	
	private Context mContext;
	private ConfigurationWS mWS;
	//private MyShareprefer myShare;
	//private ConfigurationDB mDB;
	private UserDB userDB;
	private int user_id = 0;
	private User user;
	//private String user_title = "";
	public OnListenerLogInOrOut onListenerLogInOrOut;
	//private ISelectItable iSelectItable;
	private IAddMenu iAddMenu ;
	
	public MainPosActivityHelper(Context mContext, ConfigurationWS mWS, UserDB userDB, User user){
		this.mContext = mContext;
		this.mWS = mWS;
	//	this.myShare = myShare;
		this.userDB = userDB;
		this.user = user;
		//this.iSelectItable = (ISelectItable) mContext;
		this.iAddMenu = (IAddMenu) mContext;
	}
	
	public void setOnListenerLogInOrOut(OnListenerLogInOrOut onListenerLogInOrOut){
		this.onListenerLogInOrOut = onListenerLogInOrOut;
	}
	
	public void logInOrOut(final String URL){
		final Dialog dialog = new Dialog(new ContextThemeWrapper(mContext, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar));
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.login_client);
		dialog.setCancelable(false);
		dialog.show();
		
		final Spinner spMainFloor = (Spinner) dialog.findViewById(R.id.spMainFloor);
		final Spinner spMainTable = (Spinner) dialog.findViewById(R.id.spMainTable);
		
		final EditText edtMainGuestName = (EditText) dialog.findViewById(R.id.edtMainGuestName);
		final EditText edtMainGuestPhoneNumber = (EditText) dialog.findViewById(R.id.edtMainGuestPhoneNumber);
		
		// Lấy SĐT của device hiển thị lên listview
		String phoneNumber = new GetDeviceInfo(mContext).getPhoneNumber();
		edtMainGuestPhoneNumber.setText(phoneNumber);
		
		// Hiển thị tất cả các tầng
		refreshSpinnerFloor(spMainFloor);
		
		// Hiển thị bàn theo mã tầng
		int floor = (Integer) spMainFloor.getSelectedItem();
		refreshSpinnerTable(spMainTable,floor);
		
		// Mỗi khi chọn tầng => Refesh lại màn hình chọn bàn
		spMainFloor.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View view, int position, long arg3) {
				int floor = (Integer) spMainFloor.getItemAtPosition(position);
				refreshSpinnerTable(spMainTable,floor);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	
		
		// Click button OK chuyển sang màn hình chọn món
		dialog.findViewById(R.id.btnMainOK).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String username = "Guest";
				final String floor = spMainFloor.getSelectedItem().toString();
				
				String codeTable = spMainTable.getSelectedItem().toString();
				//codeTable = genTableCode(codeTable); // T1_B1
				
				String invCode = "Guest"+"_"+codeTable; // Guest_T1_B1
				
				// Kiểm tra thông tin Name hoặc SĐT
				String sGuestName = edtMainGuestName.getText().toString().trim();
				String sGuestPhoneNumber = edtMainGuestPhoneNumber.getText().toString().trim();
				
				if( TextUtils.isEmpty( sGuestName ) && TextUtils.isEmpty( sGuestPhoneNumber ) ){
					Toast.makeText(mContext, "Please input Name or Phone number !", Toast.LENGTH_SHORT).show();
					return;
				}
				if( TextUtils.isEmpty( sGuestPhoneNumber ) ){
					MainPosActivity.username = "Guest";
				}else{
					MainPosActivity.username = sGuestName;
				}
				if( TextUtils.isEmpty( sGuestPhoneNumber ) ){
					MainPosActivity.phoneNumber = 0 ;
				}else{
					MainPosActivity.phoneNumber = Long.parseLong( sGuestPhoneNumber );
				}
				// Kiểm tra thông tin chọn bàn
				if( codeTable.contains("free") ){
					Toast.makeText(mContext, "Floor is not free table !", Toast.LENGTH_SHORT).show();
					return;
				}
				
				/**
				 * Các bước cần làm khi Guest tạo mới invoice:
				 * (xong)B1: Nhập thông tin : Tên + SĐT
				 * (xong)B2: Chọn Tầng + bàn 
				 * (xong)B3: Insert invoice mới vào bảng pos_invoice với status = 4 ( Chỉ dành cho khách )
				 * (xong)B4: Update bàn đổi trạng thái = 2 ( Đang sử dụng )
				 * (xong)B5: Hiển thị màn hình chọn món ăn
				 * (xong)B6: Sau khi chọn món ăn xong insert DL vào bảng pos_invoice_detail
				 * B7: Hiển thị invoice trên màn hình invoice pos
				 * B8: Waiter sẽ nhận thấy invoice mới của khách và sẽ tới xác nhận
				 * 	   Khi waiter xác nhận :
				 * 			+ Đổi trạng thái bảng pos_invoice với status = 2 ( Đang sử dụng )
				 * 			+ Đổi lại tên hóa đơn : G_TenNV_.....
				 */
				
				//Server
				if (new ConfigurationServer((MainPosActivity)mContext).isOnline()) {
						dialog.dismiss();
						onListenerLogInOrOut.onLoginSuccess(username);
						try{
							//iSelectItable.onSelectItable(2, 1, "T1_B1", user_id);
							if (new ConfigurationServer((MainPosActivity)mContext).isOnline()) {
								
								// Kiểm tra xem User có đang ngồi trong nhà hàng
								//ArrayList<Double> lstLatLon = new GetCurrentLocationGPS(mContext).getLocation();
								
								ArrayList<String> lstCodeTable = new ArrayList<String>();
								lstCodeTable.add(codeTable);
								
								new WSAddNewInvoice(mContext, invCode, 4, (int)MainPosActivity.phoneNumber, "", 0).execute();
								new WSAddInvTable(mContext, invCode, (int)MainPosActivity.phoneNumber, lstCodeTable).execute();
								new WSUpdateItableStatus(mContext, 2, (int)MainPosActivity.phoneNumber, lstCodeTable).execute();
								
								/*
								-------------------add invoice to bean---------------------
								Invoice inv = new Invoice();
								inv.setInv_code(inv_code);
								inv.setInv_starttime(simpDate);
								inv.setUser_id(user_id);
								inv.setStatus(1);
								MainPosActivity.beanDataAll.addInvoice(inv);
								new WSUpdateItableStatus(InvoicePosFragment.this.context, 2, user_id, itemTable).execute();*/
								
								
								
								// Chuyển sang màn hình gọi món ăn
								iAddMenu.addMenuActivity(invCode);
							} else {
								Toast.makeText(mContext, "Network not found", Toast.LENGTH_LONG).show();
							}
							
						}catch(Exception e){
							Log.e("ERROR:", "Lỗi 130 MainPosActivityHelper");
						}
						
				} 
			}
		});
		// Click button Cancel thoát khỏi chương trình
		dialog.findViewById(R.id.btnMainExit).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (dialog != null) {
					dialog.dismiss();
					System.exit(0);
				}
				onListenerLogInOrOut.onBackPressd();
			}
		});
	}
	
	/** Lấy tất cả danh sách Itable Free của tầng floorId */
	public void refreshSpinnerTable(Spinner spMainTable, int floor){
		try {
			ArrayList<Itable> lstItableByFloors =  new WSGetAllItableFreeByFloor(mContext).execute(floor).get();
			ArrayList<String> lstTableCodes = new ArrayList<String>();
			for (Itable itable : lstItableByFloors) {
				if( itable.getStatus()== 0 ) // Chỉ lấy những bàn trống hiển thị lên
					lstTableCodes.add(itable.getCode_table());
			}
			if( lstTableCodes.size() == 0 ) lstTableCodes.add("No table free");
			SpinnerAdapterTable spinnerAdapter = new SpinnerAdapterTable(lstTableCodes, mContext);
			
			/*ArrayAdapter<String> adapterTable = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, android.R.id.text1, lstTableCodes);
			spMainTable.setAdapter(adapterTable);*/
			spMainTable.setAdapter(spinnerAdapter);
			spinnerAdapter.notifyDataSetChanged();
			
		} catch (Exception e) {
			Log.e("Error", e.getMessage().toString());
		}  
	}
	
	/** Lấy Tất cả Danh sách Floor đổ lên spinner floor*/
	public void refreshSpinnerFloor(Spinner spMainFloor){
		try {
			// ArrayList<String> lstFloor = new WSGetAllFloor(mContext).execute().get();
			 ArrayList<Floor> lstFloors = new WSGetFloor(mContext).execute().get();
			 ArrayList<Integer> lstFloorId = new ArrayList<Integer>();
			 for (Floor floor : lstFloors) {
				 lstFloorId.add(floor.getId());
			 }
			 if( lstFloorId.size() == 0 ) lstFloorId.add(0); 
			 ArrayAdapter<Integer> adapterFloor = new ArrayAdapter<Integer>(mContext, android.R.layout.simple_list_item_1, android.R.id.text1, lstFloorId);
			 spMainFloor.setAdapter(adapterFloor);
			 adapterFloor.notifyDataSetChanged();
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
	}
	
	
	public boolean putDataLogin(String username, String pass, String URL) {
		JSONObject json = new JSONObject();
		try {
			json.put("username", username);
			json.put("pass", pass);
			JSONArray jarr = mWS.connectWSPut_Get_Data(URL, json, "posts");
			if (jarr != null) {
				for (int i = 0; i < jarr.length(); i++) {
					JSONObject element = jarr.getJSONObject(i);
					Log.e(">>>>>>>>>>>>> element = ", "" + element.toString());
					String result = element.getString("result");
					user_id = element.getInt("user_id");
					if (result.equals("success")) {
						user.setUser_id(user_id);
						user.setUsername(username);
						user.setPassword(pass);
						Log.e(">>>>>>>>>>>>>>>> title = ", "" + element.getString("title"));
						user.setTitle(element.getString("title"));
						userDB.addUser(user);
						/*myShare.setUser_id(user_id);
						myShare.setUserItem(username);
						myShare.setPassWordItem(pass);*/
						/**
						 * 15/2/2014
						 * khiemnd add
						 */
						Log.e("............. 111 = ", "" + element.getString("title"));
						Constaints.titleUser = element.getString("title");
						/**
						 * khiemnd end
						 */
						return true;
					}
				}
			}
		} catch (JSONException e) {
		}
		return false;
	}
	
	public interface OnListenerLogInOrOut{
		public void onLoginSuccess(String username);
		public void onBackPressd();
	}
	
	/**
	 * Hàm tự sinh ra mã hóa đơn
	 * input: T1_B2 
	 * output: T1_B2_20142312121212
	 * 
	 * @param tableCode : Mã Bàn 
	 * @return
	 */
	private String genTableCode( String  tableCode) {
		String table_code = "";
		String simpDate = new SimpleDateFormat("yyyyMMdd_HHmmss") .format(new Date());
			if( TextUtils.isEmpty(table_code) ){
				table_code = table_code + tableCode;
			}else{
				table_code = table_code +"_"+ tableCode;
			}
		return table_code + "_" + simpDate;
	}
	
}
