/*package iii.pos.client.activity;

import iii.pos.client.R;
import iii.pos.client.adapter.ItableManagerAdapter;
import iii.pos.client.adapter.ItableManagerAdapter.IitableManager;
import iii.pos.client.library.EnableEditUpdate;
import iii.pos.client.library.GetCurrentLocationGPS;
import iii.pos.client.model.Itable;
import iii.pos.client.server.ConfigurationServer;
import iii.pos.client.wsclass.WSDeleteTableById;
import iii.pos.client.wsclass.WSGetAllFloor;
import iii.pos.client.wsclass.WSGetAllTable;
import iii.pos.client.wsclass.WSInsertNewItable;
import iii.pos.client.wsclass.WSUpdateItable;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

*//**
 * 
 * @author Trọng khiêm: Lớp xử lý xem tất cả danh sách Itable và location của
 *         Itable Lớp này cho phép xóa bàn => Cập nhật BeanAllData và Server
 * 
 *//*
public class SettingItableManager extends Activity implements OnClickListener, OnItemClickListener {

	private ItableManagerAdapter adapter;
	private ListView lvItableManagerItable;
	private List<Itable> lstItable = null;
	private Button btnItableManagerEditUpdate;
	private ImageButton imbItableManagerAddItable, imbItabManagerClose;
	private boolean isEdit = false;
	private int currentFloor;
	private ArrayList<Integer> lstItableId; // Click button Delete: Tạo mảng chứa tất cả các table_id cần xóa để xóa trên server
	private ArrayList<Itable> lstItableUpdate; // Tạo 1 mảng lưu lại những row bị thay đổi và sau đó update lên server
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_itable_manager);
		init();
	}

	// ======= Khởi tạo các view =========
	private void init() {
		currentFloor = ConfigurationServer.floor ;
		Log.i("CURRENT_FLOOR:", currentFloor+"");
		
		lvItableManagerItable = (ListView) findViewById(R.id.lvItableManagerItable);
		btnItableManagerEditUpdate = (Button)findViewById(R.id.btnItableManagerEditUpdate);
		imbItableManagerAddItable = (ImageButton) findViewById(R.id.imbItableManagerAddItable);
		imbItabManagerClose = (ImageButton) findViewById(R.id.imbItabManagerClose);
		
		imbItabManagerClose.setOnClickListener(this);
		imbItableManagerAddItable.setOnClickListener(this);
		btnItableManagerEditUpdate.setOnClickListener(this);
		lvItableManagerItable.setOnItemClickListener(this);
		
		lstItableId = new ArrayList<Integer>();
		lstItable = new ArrayList<Itable>();
		lstItableUpdate = new ArrayList<Itable>();
		
		try {
			lstItable = new WSGetAllTable(SettingItableManager.this).execute(ConfigurationServer.getURLServer() + "wsget_all_itable.php").get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//lstItable = MainPosActivity.beanDataAll.lstItable;
		refreshDataOnListView(lstItable);
		
	}
	
	// ======= Đổ DL lên ListView ===========
	private void refreshDataOnListView(final List<Itable> lstItable) {
		adapter = new ItableManagerAdapter(SettingItableManager.this, lstItable, new IitableManager() {
			// Hàm onClick chỉ xử lý riêng cho nút Delete
			@Override
			public void onClick(int position) {
				// Lấy bàn ở vị trí click vào
				Itable itable = (Itable) lvItableManagerItable.getItemAtPosition(position);
				// Ném table_id bàn cần xóa vào trong lstItableId để sau khi nhấn Update thì gửi 
				// tất cả các id bàn này lên server và xóa
				lstItableId.add(itable.getItable_id());
				// Xóa itable trong ArrayList<itable> 
				lstItable.remove(itable);
				// Cập nhật lại dữ liệu sau khi xóa
				refreshDataOnListView(lstItable);
				
			}
			
			// Xử lý chung cho các Edittext
			// typeColumn : 
			// 0: Sửa cột TableName
			// 1: Sửa cột GPS_X
			// 2: Sửa cột GPS_Y
			@Override
			public void onChange(int position, Itable itable) {
				Itable currItable = (Itable) lvItableManagerItable.getItemAtPosition(position);
				
				int currItableId = currItable.getItable_id();
				int currGPS_X =  currItable.getPos_x() ;
				int currGPS_Y =  currItable.getPos_y() ;
				String currItableCode = currItable.getCode_table() ;
				
				Log.i("curr_ITABLE: id_table: ",currItableId+"" );
				Log.i("curr_ITABLE: Code_table: ",currItableCode );
				Log.i("curr_ITABLE: GPS_X",currGPS_X+"");
				Log.i("curr_ITABLE: GPS_Y", currGPS_Y+"");
				
				
				int itableId = itable.getItable_id();
				int GPS_X =  itable.getPos_x() ;
				int GPS_Y =  itable.getPos_y() ;
				String itableCode = itable.getCode_table() ;
				
				Log.i("_ITABLE: id_table: ",itableId+"" );
				Log.i("_ITABLE: Code_table: ",itableCode );
				Log.i("_ITABLE: GPS_X",GPS_X+"");
				Log.i("_ITABLE: GPS_Y", GPS_Y+"");
				
				adapter.notifyDataSetChanged();
				
				lstItableUpdate.add(itable);
				
				
				if( currItable.equals(itable)){
					Toast.makeText(SettingItableManager.this, "--Chưa bị thay đổi--", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(SettingItableManager.this, "--Đã bị thay đổi--", Toast.LENGTH_SHORT).show();
				}
				
				int currItableId = currItable.getItable_id();
				int currGPS_X =  currItable.getPos_x() ;
				int currGPS_Y =  currItable.getPos_y() ;
				String currItableCode = currItable.getCode_table() ;
				
				Log.i("curr_ITABLE: id_table: ",currItableId+"" );
				Log.i("curr_ITABLE: Code_table: ",currItableCode );
				Log.i("curr_ITABLE: GPS_X",currGPS_X+"");
				Log.i("curr_ITABLE: GPS_Y", currGPS_Y+"");
				
				
				int newGPS_X = currGPS_X;
				int newGPS_Y = currGPS_Y;
				String newTableCode = currItableCode;
				
				if( typeColumn == 0){
					newTableCode = strChange;
				}else if( typeColumn == 1){
					newGPS_X = Integer.parseInt(strChange.trim());
				}else if( typeColumn == 2){
					newGPS_Y = Integer.parseInt(strChange.trim());
				}
				
				boolean isTableChange = isTableCodeChange( currItableCode, newTableCode );
				boolean isGPS_XChange = isGPS_XChange( currGPS_X, newGPS_X );
				boolean isGPS_YChange = isGPS_YChange( currGPS_Y, newGPS_Y  );
				
				if( isTableChange == false || isGPS_XChange == false || isGPS_YChange == false ){
					// Kiểm tra xem nếu table đã tồn tại => Update các trường mới vào 
					if( lstItableUpdate.size() > 0){
						for (Itable table : lstItableUpdate) {
							if( table.getItable_id() == currItableId){
								// Cập nhật lại DL
								 if( isTableCodeChange( table.getCode_table(), newTableCode ) == false ){
									 table.setCode_table(newTableCode);
								 }else if( isGPS_XChange = isGPS_XChange( table.getPos_x(), newGPS_X ) == false ){
									 table.setPos_x(newGPS_X);
								 }else if( isGPS_YChange( table.getPos_y(), newGPS_Y  ) == false ){
									 table.setPos_x(newGPS_Y);
								 }
							}
						}
					}else{
						// Thêm mới DL
						lstItableUpdate.add(new Itable(currItableId, newTableCode, newGPS_X, newGPS_Y));
					}
					Toast.makeText(SettingItableManager.this, position +"--Đã bị thay đổi--"+ strChange, Toast.LENGTH_SHORT).show();
				} 
				Log.i("new_ITABLE_: Code_table: ", newTableCode);
				Log.i("new_ITABLE_: GPS_X", newGPS_X+"");
				Log.i("new_ITABLE_: GPS_Y", newGPS_Y+"");
			}
		});
		lvItableManagerItable.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

	
	private boolean isTableCodeChange( String input, String compare) {
		if( input.equalsIgnoreCase(compare) ) return true; // không thay đổi
		return false; // đã bị thay đổi
	}
	private boolean isGPS_XChange( float input, float compare) {
		if( input == compare ) return true; // không thay đổi
		return false; // đã bị thay đổi
	}
	private boolean isGPS_YChange( float input, float compare) {
		if( input == compare ) return true; // không thay đổi
		return false; // đã bị thay đổi
	}
	
	// ======= Click button close va button Edit/Update ===========
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imbItabManagerClose:
			this.finish();
			break;
		case R.id.btnItableManagerEditUpdate:
			// cho phép Edit / update dữ liệu trên ListView
			isEdit = !isEdit;
			EnableEditUpdate.enable = isEdit;
			adapter.notifyDataSetChanged();
			editUpdateItable(isEdit);
			break;
		case R.id.imbItableManagerAddItable:
			addNewItable();
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> adap, View view, final int position, long arg3) {
		final Itable itable = (Itable) lvItableManagerItable.getItemAtPosition(position);
		Toast.makeText(getBaseContext(), itable.getCode_table(), Toast.LENGTH_SHORT).show();
	}

	*//** isEdit ? enableEditData() : enableUpdateData ()  *//*
	private void editUpdateItable(boolean _isEdit) {
		if (_isEdit) enableEditData();
		else enableUpdateData();
	}
	
	//====== Cho phép thao tác với DL : thêm - xóa =========
	private void enableEditData(){
		lvItableManagerItable.setClickable(true);
		btnItableManagerEditUpdate.setText("Update");
		Toast.makeText(getBaseContext(), "Edit", Toast.LENGTH_SHORT).show();
	}
	
	//====== Update DL vào BeanAllData và Server ============
	private void enableUpdateData(){
		lvItableManagerItable.setClickable(true);
		btnItableManagerEditUpdate.setText("Edit");
		// Nhấn Update thực hiện xóa tất cả các bàn trên server dựa vào id table gửi lên
		for (Integer tableId : lstItableId) {
			Toast.makeText(getBaseContext(), "Xóa:"+tableId, Toast.LENGTH_SHORT) .show();
			new WSDeleteTableById(SettingItableManager.this).execute(tableId);
		}
		// sau khi xóa xong các bàn này trên server => xóa list table id 
		lstItableId.clear();
		
		for (Itable table : lstItableUpdate) {
			Log.i("ItableUpdate: id_table: ",table.getItable_id()+"" );
			Log.i("ItableUpdate: Code_table: ", table.getCode_table() );
			Log.i("ItableUpdate: GPS_X",table.getPos_x()+"");
			Log.i("ItableUpdate: GPS_Y", table.getPos_y()+"");
			new WSUpdateItable(SettingItableManager.this).execute(table);
		}
		// Giải phóng list table chứa dữ liêu thay đổi
		lstItableUpdate.clear();
	}
	
	//======== Add new itable ============
	private void addNewItable() {
		
		final Dialog dialog = new Dialog(SettingItableManager.this);
		dialog.setContentView(R.layout.row_add_new_itable_manager);
		dialog.setTitle("Add new Table");
		dialog.show();
		
		final EditText edtItabManagerTableName = (EditText) dialog.findViewById(R.id.edtItabManagerTableName);
		final EditText edtItabManagerLocation_X = (EditText) dialog.findViewById(R.id.edtItabManagerLocation_X);
		final EditText edtItabManagerLocation_Y = (EditText) dialog.findViewById(R.id.edtItabManagerLocation_Y);
		final EditText edtItabManagerGPS_X = (EditText) dialog.findViewById(R.id.edtItabManagerGPS_X);
		final EditText edtItabManagerGPS_Y = (EditText) dialog.findViewById(R.id.edtItabManagerGPS_Y);
		
		final Button btnItabManagerCancel = (Button) dialog.findViewById(R.id.btnItabManagerCancel);
		final Button btnItabManagerSave = (Button) dialog.findViewById(R.id.btnItabManagerSave);
		
		final Spinner spItabManagerFloor = (Spinner) dialog.findViewById(R.id.spItabManagerFloor);
		
		final ImageView imgItabManagerCurrentLocation = (ImageView) dialog.findViewById(R.id.imgItabManagerCurrentLocation);
		final ImageView imgItabManagerStatus= (ImageView) dialog.findViewById(R.id.imgItabManagerStatus);
		
		try {
			//new String[]{"tang 1", "tang 2", "tang 3"};
			ArrayList<String> lstAllFloor = new ArrayList<String>();
			lstAllFloor = new WSGetAllFloor( SettingItableManager.this).execute().get();
			if( lstAllFloor.size() > 0){
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, lstAllFloor);
				spItabManagerFloor.setAdapter(adapter);
			} 
			
		} catch (Exception e) {
			e.printStackTrace();
		}  
		 
		//Click vào chọn tầng	
		spItabManagerFloor.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View v, int position, long longId) {
				String item = spItabManagerFloor.getItemAtPosition(position).toString();
				Toast.makeText(getBaseContext(), item, Toast.LENGTH_SHORT).show();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		//Lấy vị trí hiện tại của người phục vụ
		imgItabManagerCurrentLocation.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ArrayList<Double> lstLatLon = new GetCurrentLocationGPS(SettingItableManager.this).getLocation();
				// Location: lat
				edtItabManagerLocation_X.setText( String.valueOf(lstLatLon.get(0)) ); 
				// Location: lon
				edtItabManagerLocation_Y.setText( String.valueOf(lstLatLon.get(1)) ); 
				// Lấy vị trí location hiện tại của phục vụ
				Toast.makeText(getBaseContext(), lstLatLon.get(0)+"," + lstLatLon.get(1) +"", Toast.LENGTH_SHORT).show();
			}
		});
		// Hủy tác vụ
		btnItabManagerCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		// Thêm bàn
		btnItabManagerSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String tableName = edtItabManagerTableName.getText().toString().trim();
				
				String locationX = edtItabManagerLocation_X.getText().toString().trim();
				String locationY = edtItabManagerLocation_Y.getText().toString().trim();
				
				String GPS_X = edtItabManagerGPS_X.getText().toString().trim();
				if( TextUtils.isEmpty(GPS_X) ) GPS_X = 0+"";
				String GPS_Y = edtItabManagerGPS_Y.getText().toString().trim();
				if( TextUtils.isEmpty(GPS_Y) ) GPS_Y = 0+"";
				String floor = spItabManagerFloor.getSelectedItem().toString();
				
				if( !TextUtils.isEmpty(tableName) ){
					// Lưu tạm biến tầng vào biến flag để gửi lên server
					Itable itable = new Itable();
						itable.setCode_table( tableName );
						itable.setFlag( Integer.parseInt(floor) );
						itable.setPos_x( Integer.parseInt(GPS_X) );
						itable.setPos_y( Integer.parseInt(GPS_Y) );
						itable.setLocation_X( Double.parseDouble(locationX) );
						itable.setLocation_Y( Double.parseDouble(locationY) );
						 
					//lstItable.add(itable);	
					Log.i("NEW_ITABLE: Code_table: ", itable.getCode_table());
					Log.i("NEW_ITABLE: locationX", itable.getLocation_X()+"");
					Log.i("NEW_ITABLE: locationY", itable.getLocation_Y()+"");
					Log.i("NEW_ITABLE: GPS_X", itable.getPos_x()+"");
					Log.i("NEW_ITABLE: GPS_Y", itable.getPos_y()+"");
					Log.i("NEW_ITABLE: floor", floor+"");
					//new WSInsertFloor(getActivity()).execute();
					
					// Thêm mới bàn : truyền vào tầng chứa bàn đó và Dữ liệu mô tả bàn
					new WSInsertNewItable(SettingItableManager.this ).execute(itable);
					try {
						// Cập nhật lại dữ liệu sau trên List Table sau khi thêm bàn mới
						lstItable = new WSGetAllTable(SettingItableManager.this).execute(ConfigurationServer.getURLServer() + "wsget_all_itable.php").get();
						refreshDataOnListView(lstItable);
					} catch (Exception e) {
						e.printStackTrace();
					}  
					dialog.dismiss();
					Toast.makeText(getBaseContext(), "Save", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(getBaseContext(), "Table name not null!", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		EnableEditUpdate.enable = false;
		lstItableId.clear();
		lstItableUpdate.clear();
		// Refresh lại map pos sau khi đóng Activity Itable Manager
		Intent intentResul = new Intent();
		setResult(RESULT_OK, intentResul);
	}
}
*/