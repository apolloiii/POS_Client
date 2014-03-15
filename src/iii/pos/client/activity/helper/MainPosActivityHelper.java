package iii.pos.client.activity.helper;

import iii.pos.client.R;
import iii.pos.client.activity.MainPosActivity;
import iii.pos.client.data.Constaints;
import iii.pos.client.fragment.InvoiceDetailPosFragment.IAddMenu;
import iii.pos.client.library.CheckValidate;
import iii.pos.client.library.GetDeviceInfo;
import iii.pos.client.model.Company;
import iii.pos.client.model.Floor;
import iii.pos.client.model.Itable;
import iii.pos.client.model.User;
import iii.pos.client.server.ConfigurationServer;
import iii.pos.client.server.ConfigurationWS;
import iii.pos.client.wsclass.WSAddInvTable;
import iii.pos.client.wsclass.WSAddNewInvoice;
import iii.pos.client.wsclass.WSDeleteNewInvoice;
import iii.pos.client.wsclass.WSGetAllItableFreeByFloor;
import iii.pos.client.wsclass.WSGetCompanyCode;
import iii.pos.client.wsclass.WSGetFloorByCompany;
import iii.pos.client.wsclass.WSUpdateItableStatus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
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
	//private UserDB userDB;
	private int user_id = 0;
	private User user;
	//private String user_title = "";
	public OnListenerLogInOrOut onListenerLogInOrOut;
	public IOnClickButtonFloor onClickButtonFloor;
	//private ISelectItable iSelectItable;
	private IAddMenu iAddMenu ;
	
	private String companyCode;
	private int floor = -1;
	
	public MainPosActivityHelper(Context mContext, ConfigurationWS mWS, User user){
		this.mContext = mContext;
		this.mWS = mWS;
	//	this.myShare = myShare;
		//this.userDB = userDB;
		this.user = user;
		//this.iSelectItable = (ISelectItable) mContext;
		this.iAddMenu = (IAddMenu) mContext;
	}
	
	public void setOnListenerLogInOrOut(OnListenerLogInOrOut onListenerLogInOrOut){
		this.onListenerLogInOrOut = onListenerLogInOrOut;
	}
	
	private void setOnClickButtonFloor(IOnClickButtonFloor onClickButtonFloor){
		this.onClickButtonFloor = onClickButtonFloor;
	}
	
	public void logInOrOut(final String URL){
		final Dialog dialog = new Dialog(new ContextThemeWrapper(mContext, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen));
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.login_pos);
		dialog.setCancelable(false);
		dialog.show();
		
		//final Spinner spMainFloor = (Spinner) dialog.findViewById(R.id.spMainFloor);
		//final Spinner spMainTable = (Spinner) dialog.findViewById(R.id.spMainTable);
		
		final EditText edtMainGuestName = (EditText) dialog.findViewById(R.id.edtMainGuestName);
		final EditText edtMainGuestPhoneNumber = (EditText) dialog.findViewById(R.id.edtMainGuestPhoneNumber);
		
		final EditText edtMainGuestCompany = (EditText) dialog.findViewById(R.id.edtMainGuestCompany);
		final EditText edtMainGuestFloor = (EditText) dialog.findViewById(R.id.edtMainGuestFloor);
		final EditText edtMainGuestTable = (EditText) dialog.findViewById(R.id.edtMainGuestTable);
		
		final ImageButton imbMainGuestChooseCompany = (ImageButton) dialog.findViewById(R.id.imbMainGuestChooseCompany);
		final ImageButton imbMainGuestChooseFloor = (ImageButton) dialog.findViewById(R.id.imbMainGuestChooseFloor);
		final ImageButton imbMainGuestChooseTable = (ImageButton) dialog.findViewById(R.id.imbMainGuestChooseTable);
		
		// Click nut chon Company
		imbMainGuestChooseCompany.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				getAllCompanyCode();
				
				setOnClickButtonFloor(new IOnClickButtonFloor() {
					@Override
					public void getFloor(int floor) {
						
					}

					@Override
					public void getCompany(String company) {
						companyCode = company;
						edtMainGuestCompany.setText(companyCode);
						edtMainGuestFloor.setText("");
						edtMainGuestTable.setText("");
					}

					@Override
					public void getTable(String table) {
						
					}
				});
			}
		});
		
		// Click nut chon Tang => Theo CompayCode
		imbMainGuestChooseFloor.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if( !TextUtils.isEmpty(companyCode)){
					// Hiển thị tất cả các tầng
					getAllFloorByCompany(companyCode);
					
					setOnClickButtonFloor(new IOnClickButtonFloor() {
						@Override
						public void getFloor(int _floor) {
							floor = _floor;
							edtMainGuestFloor.setText("Floor " + floor);
							edtMainGuestTable.setText("");
						}

						@Override
						public void getCompany(String companyCode) {
							
						}

						@Override
						public void getTable(String table) {
							
						}
					});
				}else{
					Toast.makeText(mContext, "Please choose one floor !", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		// Click nut chon Ban
		imbMainGuestChooseTable.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if( floor != -1 ){
					
					getNameTableByFloor(companyCode, floor);
					
					setOnClickButtonFloor(new IOnClickButtonFloor() {
						@Override
						public void getFloor(int floor) {
							
						}

						@Override
						public void getCompany(String companyCode) {
							
						}

						@Override
						public void getTable(String table) {
							edtMainGuestTable.setText(table);
						}
					});
				}else{
					Toast.makeText(mContext, "Vui long chon tang", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
		// Lấy SĐT của device hiển thị lên listview
		String phoneNumber = new GetDeviceInfo(mContext).getPhoneNumber();
		edtMainGuestPhoneNumber.setText(phoneNumber);
		
		// Click button OK chuyển sang màn hình chọn món
		dialog.findViewById(R.id.btnMainGuestOK).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String username = "Guest";
				
				final String floor = edtMainGuestFloor.getText().toString();
				
				// Kiểm tra thông tin Name hoặc SĐT
				String sGuestName = edtMainGuestName.getText().toString().trim();
				String sGuestPhoneNumber = edtMainGuestPhoneNumber.getText().toString().trim();
				
				if( TextUtils.isEmpty( sGuestName ) && TextUtils.isEmpty( sGuestPhoneNumber ) ){
					Toast.makeText(mContext, "Please input Name or Phone number !", Toast.LENGTH_SHORT).show();
					return;
				}
				
				// Kiểm tra tên user
				if( !TextUtils.isEmpty( sGuestName ) ){
					username = sGuestName;
				} 
				
				// Kiểm tra số điện thoại
				if( TextUtils.isEmpty( sGuestPhoneNumber ) ){
					Toast.makeText(mContext, "Please input phone number !", Toast.LENGTH_SHORT).show();
					return;
					//MainPosActivity.phoneNumber = 0 ;
				}else{
					boolean check = new CheckValidate().isPhoneValidate(sGuestPhoneNumber);
					if( check == false ){
						Toast.makeText(mContext, "Phone number format wrong !", Toast.LENGTH_SHORT).show();
						return;
					}
				}
				
				// Kiểm tra thông tin chọn nhà hàng
				String isChooseCompanyCode = edtMainGuestCompany.getText().toString();
				if( isChooseCompanyCode.contains("No") ){
					Toast.makeText(mContext, "Please choose Company !", Toast.LENGTH_SHORT).show();
					return;
				}
				
				// Kiểm tra thông tin chọn tầng
				String isChooseFloor = edtMainGuestCompany.getText().toString();
				if( isChooseFloor.contains("No") ){
					Toast.makeText(mContext, "Please choose floor !", Toast.LENGTH_SHORT).show();
					return;
				}
				
								
				// Kiểm tra thông tin chọn bàn
				String codeTable = edtMainGuestTable.getText().toString();
				String invCode = "Guest"+"_"+codeTable; // Guest_T1_B1
				
				if( codeTable.contains("No") ){
					Toast.makeText(mContext, "Floor is not free table !", Toast.LENGTH_SHORT).show();
					return;
				}
				
				/**
				 * Các bước cần làm khi Guest tạo mới invoice:
				 * 
				 * (xong)B1: Nhập thông tin : Tên hoặc SĐT
				 * (xong)B2: Chọn Tầng + bàn 
				 * (xong)B3: Tạo mới invoice và Insert invoice mới vào bảng pos_invoice với status = 4 ( Chỉ dành cho khách )
				 * (xong)B4: Update bàn đổi trạng thái = 2 ( Đang sử dụng )
				 * (xong)B5: Hiển thị màn hình chọn món ăn
				 * (xong)B6: Sau khi chọn món ăn xong insert DL vào bảng pos_invoice_detail
				 * (xong)B7: Hiển thị invoice của Guest trên màn hình invoice pos (ví dụ: Guest HD Bàn (1) Tầng 1 )
				 * (xong)B8: Tạo thêm nút nhấn xác nhận cho những hóa đơn Guest, cho phép Waiter xác nhận 
				 * B9: Waiter sẽ nhận thấy invoice mới của khách và sẽ tới xác nhận
				 * 	   Khi waiter xác nhận :
				 * 			+ Đổi trạng thái bảng pos_invoice với status = 3 ( Đang sử dụng )
				 * 			+ Đổi lại tên hóa đơn của Guest này.( Ví dụ: G_TenNV_.....)
				 * 			trong bảng pos_invoice, pos_invoice_detail, pos_invoice_itable
				 * B10: Sau khi thanh toán hóa đơn của Guest: Hủy hóa đơn 
				 */
				
				//Server
				if (new ConfigurationServer((MainPosActivity)mContext).isOnline()) {
						dialog.dismiss();
						onListenerLogInOrOut.onLoginSuccess(sGuestPhoneNumber, username, isChooseCompanyCode);
						try{
							//iSelectItable.onSelectItable(2, 1, "T1_B1", user_id);
							if (new ConfigurationServer((MainPosActivity)mContext).isOnline()) {
								
								// Kiểm tra xem User có đang ngồi trong nhà hàng
								//ArrayList<Double> lstLatLon = new GetCurrentLocationGPS(mContext).getLocation();
								
								ArrayList<String> lstCodeTable = new ArrayList<String>();
								lstCodeTable.add(codeTable);
								
								boolean isSuccessInv = new WSAddNewInvoice(mContext, invCode, 4, "", 0).execute().get();
								boolean isSuccessItable = false ;//= new WSAddInvTable(mContext, invCode, lstCodeTable).execute().get();
								boolean isSuccessUpStatus = false ;//= new WSUpdateItableStatus(mContext, 2, lstCodeTable).execute().get();
								
								if( isSuccessInv == true ){
									isSuccessItable = new WSAddInvTable(mContext, invCode, lstCodeTable).execute().get();
								}
								if( isSuccessItable == true ){
									isSuccessUpStatus = new WSUpdateItableStatus(mContext, 2, lstCodeTable).execute().get();
								}
								if( isSuccessInv && isSuccessItable && isSuccessUpStatus){
									// Nếu đã tạo xong 3 bước trên
									// Chuyển sang màn hình gọi món ăn
									iAddMenu.addMenuActivity(invCode);
								}else{
									// Xảy ra lỗi 1 trong 3 bước trên
									// Xóa DL vừa mới insert vào
									Toast.makeText(mContext, "Error while create new invoice !", Toast.LENGTH_LONG).show();
									new WSDeleteNewInvoice(mContext, invCode, codeTable).execute();
									return;
								}
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
		dialog.findViewById(R.id.btnMainGuestCancel).setOnClickListener(new OnClickListener() {
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
	
	/**
	 * Lấy tất cả danh sách nhà hàng
	 */
	public void getAllCompanyCode() {
		// Lấy tất cả Company code hiển thị 
		ArrayList<Company> lstCompanys = new ArrayList<Company>();
		final ArrayList<String> lstCompanyCode  = new ArrayList<String>();
		
		try {
			lstCompanys = new WSGetCompanyCode(mContext).execute().get();
			for (Company company2 : lstCompanys) {
				lstCompanyCode.add(company2.getCompanycode());
			}
		} catch (Exception e) { e.printStackTrace(); }
		
		if( lstCompanyCode.size() == 0 ) lstCompanyCode.add("No data");
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.select_dialog_singlechoice, lstCompanyCode);
		new AlertDialog.Builder(mContext)
		.setIcon(android.R.drawable.ic_menu_more)
		.setTitle("Choose company")
		.setSingleChoiceItems(adapter, 0, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int position) {
				//ecompany.setText(String.valueOf( lstCompanyCode.get(position) ));
				onClickButtonFloor.getCompany( lstCompanyCode.get(position) ) ;
				dialog.cancel();
			}
		})
		.create().show();
	}
	
	/**
	 * Lấy tất cả danh sách bàn của tầng
	 * @param floor : Mã tầng
	 */
	public void getNameTableByFloor(String company, int floor){
		try {
			ArrayList<Itable> lstItableByFloors =  new WSGetAllItableFreeByFloor( mContext, company, floor ).execute().get();
			final ArrayList<String> lstTableCodes = new ArrayList<String>();
			for (Itable itable : lstItableByFloors) {
				if( itable.getStatus()== 0 ) // Chỉ lấy những bàn trống hiển thị lên
					lstTableCodes.add(itable.getCode_table());
			}
			
			if( lstTableCodes.size() == 0 ) lstTableCodes.add("No table free");
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.select_dialog_singlechoice, lstTableCodes);
			
			new AlertDialog.Builder(mContext)
			.setIcon(android.R.drawable.ic_menu_more)
			.setTitle("Chon ban")
			.setSingleChoiceItems(adapter, 0, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int position) {
					onClickButtonFloor.getTable( lstTableCodes.get(position) ) ;
					dialog.cancel();
				}
			}).create().show();
			
		} catch (Exception e) {
			Log.e("Error", e.getMessage().toString());
		}  
	}
	
	/**
	 * Lấy tất cả tầng của nhà hàng
	 * @param companyCode : Mã nhà hàng
	 */
	public void getAllFloorByCompany(String companyCode){
		try {
			// ArrayList<String> lstFloor = new WSGetAllFloor(mContext).execute().get();
			 ArrayList<Floor> lstFloors = new WSGetFloorByCompany(mContext, companyCode).execute().get();
			 final ArrayList<Integer> lstFloorId = new ArrayList<Integer>();
			
			 for (Floor floor : lstFloors) {
				 lstFloorId.add(Integer.parseInt( floor.getCode() ));
			 }
			
			 if( lstFloorId.size() == 0 ) lstFloorId.add(0); 
			
			 ArrayAdapter<Integer> adapterFloor = new ArrayAdapter<Integer>(mContext, android.R.layout.select_dialog_singlechoice, android.R.id.text1, lstFloorId);
			
			 new AlertDialog.Builder(mContext)
				.setIcon(android.R.drawable.ic_menu_more)
				.setTitle("Choose company")
				.setSingleChoiceItems(adapterFloor, 0, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int position) {
						onClickButtonFloor.getFloor( lstFloorId.get(position) ) ;
						dialog.cancel();
					}
				})
				.create().show();
			  
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
	}
	
	// bo? ko dung
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
						//userDB.addUser(user);
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
		public void onLoginSuccess(String phone, String username, String company);
		public void onBackPressd();
	}
	
	/**
	 * Tạo interface để click vào các nút chọn Nhà hàng, Tầng, Bàn
	 * @author GIACNGO
	 *
	 */
	private interface IOnClickButtonFloor{
		public void getCompany(String companyCode);
		public void getFloor(int floor);
		public void getTable(String table);
		
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
