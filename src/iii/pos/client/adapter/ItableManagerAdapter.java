package iii.pos.client.adapter;

import iii.pos.client.R;
import iii.pos.client.library.EnableEditUpdate;
import iii.pos.client.model.Itable;

import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ItableManagerAdapter extends BaseAdapter{
	private List<Itable> lstItable;
	private Context context;
	private IitableManager iItable;
	private Button btnDialogItableManagerSave;
	private EditText edtDialogItableManagerName;
	private EditText edtDialogItableManagerGPS_X;
	private EditText edtDialogItableManagerGPS_Y;
	private String output = null;
	private Dialog dialog ;
	private ItableManagerHolder holder = null;
	public ItableManagerAdapter(Context context, List<Itable> lstItable, IitableManager itable) {
		super();
		this.lstItable = lstItable;
		this.context = context;
		this.iItable = itable;
	}
	
	@Override
	public int getCount() {
		return lstItable.size();
	}

	@Override
	public Object getItem(int arg0) {
		return lstItable.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int position, View view, ViewGroup parent) {
		if (view == null) {
			holder = new ItableManagerHolder();
			LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflate.inflate(R.layout.row_itable_manager, parent, false);
			holder.edtItabManagerNameItable = (TextView) view.findViewById(R.id.edtItabManagerNameItable);
			holder.edtItabManagerX = (TextView) view.findViewById(R.id.edtItabManagerX);
			holder.edtItabManagerY = (TextView) view.findViewById(R.id.edtItabManagerY);
			holder.imgItabManagerStatus = (ImageView) view.findViewById(R.id.imgItabManagerStatus);
			holder.imbItabManagerLocation = (ImageButton) view.findViewById(R.id.imbItabManagerLocation);
			holder.imbItabManagerDelete = (ImageButton) view.findViewById(R.id.imbItabManagerDelete);
			view.setTag(holder);
		}else{
			holder = (ItableManagerHolder) view.getTag();
		}
		
		final Itable itable = lstItable.get(position);
		
		int status = itable.getStatus() ;
		if( status == 2){ // Bàn đang được sử dụng
			holder.imgItabManagerStatus.setImageResource(android.R.drawable.presence_online);
		}else{
			// Bàn chưa sử dụng
			holder.imgItabManagerStatus.setImageResource(android.R.drawable.radiobutton_off_background);
		}
		holder.edtItabManagerNameItable.setText(itable.getCode_table());
		holder.edtItabManagerX.setText(String.valueOf(itable.getPos_x()) );
		holder.edtItabManagerY.setText(String.valueOf(itable.getPos_y()) );
		
		// Lấy biến boolean enable cho phép view Enable hay disable row view
		if( EnableEditUpdate.enable == false ){
			//Không được phép sửa
			holder.edtItabManagerNameItable.setEnabled(false);
			holder.imgItabManagerStatus.setEnabled(false);
			holder.edtItabManagerX.setEnabled(false);
			holder.edtItabManagerY.setEnabled(false);
			holder.imbItabManagerLocation.setEnabled(false);
			holder.imbItabManagerDelete.setEnabled(false);
		}else{
			// Cho phép sửa DL
			holder.edtItabManagerNameItable.setEnabled(true);
			holder.imgItabManagerStatus.setEnabled(true);
			holder.edtItabManagerX.setEnabled(true);
			holder.edtItabManagerY.setEnabled(true);
			holder.imbItabManagerLocation.setEnabled(true);
			holder.imbItabManagerDelete.setEnabled(true);
		}
		 
		// Lắng nghe sự kiện thay đổi của các view
		holder.edtItabManagerNameItable.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onDialogChangeValue( position, itable);
			}
		});
		// Lắng nghe sự kiện thay đổi của các view
		holder.edtItabManagerX.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onDialogChangeValue( position, itable );
			}
		});
		// Lắng nghe sự kiện thay đổi của các view
		holder.edtItabManagerY.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onDialogChangeValue( position, itable );
			}
		});
		
		// Click vào button delete
		holder.imbItabManagerDelete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(context)
						.setIcon(android.R.drawable.ic_delete)
						.setTitle(context.getResources().getString(R.string.questiondelete))
						.setPositiveButton(context.getResources().getString(R.string.delete),
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface arg0, int pos) {
										iItable.onClick(position);
									}
								}).setNegativeButton(context.getResources().getString(R.string.Cancel), null).create().show();
				
			}
		});
		
		// Click vào button Location
		holder.imbItabManagerLocation.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(context, "Update location !", Toast.LENGTH_SHORT).show();
			}
		});
		return view;	
	}

	//--------- Tạo một Interface để ném các row cần xử lý trong adapter ra ngoài lớp main ------
	public interface IitableManager{
		public void onClick(int tableId);
		public void onChange(int position, Itable itable);
	}
	
	// position : Itable tại vị trí position
	// Type 0 - 1 : Loại editText nhập text hay number
	// input: String cần sửa
	// typeColumn 0-1-2: Để đánh dấu trường Name - GPS_X - GPS_Y
	public void onDialogChangeValue(final int position, final Itable itable ){
		
		Log.i("___ITABLE BEFOR CHANGE: table_id:", itable.getItable_id()+"");
		Log.i("___ITABLE BEFOR CHANGE: table_code:", itable.getCode_table()+"");
		Log.i("___ITABLE BEFOR CHANGE: table_id:", itable.getPos_x()+"");
		Log.i("___ITABLE BEFOR CHANGE: table_id:", itable.getPos_y()+"");
		
		dialog = new Dialog(context, android.R.style.Theme_Dialog);
		dialog.setContentView(R.layout.dialog_itable_manage_change_text);
		dialog.setTitle("Change value");
		dialog.show();

		edtDialogItableManagerName = (EditText) dialog.findViewById(R.id.edtDialogItableManagerName);
		edtDialogItableManagerGPS_X = (EditText) dialog.findViewById(R.id.edtDialogItableManagerGPS_X);
		edtDialogItableManagerGPS_Y = (EditText) dialog.findViewById(R.id.edtDialogItableManagerGPS_Y);
		btnDialogItableManagerSave = (Button) dialog.findViewById(R.id.btnDialogItableManagerSave);
		
		edtDialogItableManagerName.setText(itable.getCode_table());
		edtDialogItableManagerGPS_X.setText(String.valueOf(itable.getPos_x()));
		edtDialogItableManagerGPS_Y.setText(String.valueOf(itable.getPos_y()));
		
		btnDialogItableManagerSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				String tableName = edtDialogItableManagerName.getText().toString().trim();
				int GPS_X;
				int GPS_Y;
				try{
					GPS_X = Integer.parseInt(edtDialogItableManagerGPS_X.getText().toString().trim());
					GPS_Y = Integer.parseInt(edtDialogItableManagerGPS_Y.getText().toString().trim());
					
					Itable itableChange = itable;
					itableChange.setCode_table(tableName);
					itableChange.setPos_x(GPS_X);
					itableChange.setPos_y(GPS_Y);
					
					Log.i("___ITABLE CHANGE: table_id:", itableChange.getItable_id()+"");
					Log.i("___ITABLE CHANGE: table_code:", itableChange.getCode_table()+"");
					Log.i("___ITABLE CHANGE: table_Pos_x:", itableChange.getPos_x()+"");
					Log.i("___ITABLE CHANGE: table_Pos_y:", itableChange.getPos_y()+"");
					
					iItable.onChange( position, itableChange);
				}catch(Exception e){
					Toast.makeText(context, "Location is too large", Toast.LENGTH_SHORT).show();
				}
				
				
				
				//Toast.makeText(context, output, Toast.LENGTH_SHORT).show();
				dialog.dismiss();
			}
		});
	}
	
	// ======== viewholder class======//
	class ItableManagerHolder {
		ImageView imgItabManagerStatus;
		TextView edtItabManagerNameItable, edtItabManagerX, edtItabManagerY;
		ImageButton imbItabManagerLocation,imbItabManagerDelete;
	}

	/*@Override
	public void onClick(View v) {
		if(v == btnDialogItableManagerSave){
			ouput = edtDialogItableManagerName.getText().toString().trim();
			edtDialogItableManagerName.setText(ouput);
			dialog.dismiss();
		}
	}*/
}
