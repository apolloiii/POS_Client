package iii.pos.client.adapter;

import iii.pos.client.R;
import iii.pos.client.activity.MainPosActivity;
import iii.pos.client.fragment.InvoicePosFragment;
import iii.pos.client.library.FormatFloorTableName;
import iii.pos.client.model.Invoice;
import iii.pos.client.model.Itable;
import iii.pos.client.model.Table;
import iii.pos.client.server.ConfigurationServer;
import iii.pos.client.wsclass.WSAddInvTable2;
import iii.pos.client.wsclass.WSGetAllTable;
import iii.pos.client.wsclass.WSInsertNewInvoice;
import iii.pos.client.wsclass.WSUpdateInvoiceDetail;
import iii.pos.client.wsclass.WSUpdateItableStatus;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ListInvoiceAdapter extends ArrayAdapter<Invoice> {

	private Context context;
	public List<Invoice> lstInv;
	private viewHolderInvoice holder = null;
	private ArrayList<String> itemTable = new ArrayList<String>();
	//tao mot arraylist chua nhung ten invoice bo check
	private ArrayList<String> items = new ArrayList<String>();
	//tao mot arraylist chua ten cua tat ca invoice
	private ArrayList<String> nameInvoices = new ArrayList<String>();
	private String table_code;
	private ArrayList<String> codeTables;
	private ArrayList<Table> lstTableTmp;
	private String nameInvoice;
	private String nameOldInvoice;
	private IGetInvCode iGetInvCode;
	 
	 public void getInvCode( IGetInvCode IGetInvCode){
		 this.iGetInvCode = IGetInvCode;
	 }

	public ListInvoiceAdapter(Context context, int textViewResourceId, List<Invoice> invoiceitemlist, String table_code) {
		super(context, textViewResourceId, invoiceitemlist);
		this.context = context;
		this.lstInv = invoiceitemlist;
		this.table_code = table_code;
	}
	
	@Override
	public View getView( final int position, View convertView, ViewGroup parent) {
		try {
			if (convertView == null) {
				holder = new viewHolderInvoice();
				LayoutInflater inflate = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflate.inflate(R.layout.invoice_custom_listview, null);
				holder.lnMain = (LinearLayout) convertView.findViewById(R.id.layoutinvoice);
				holder.cbCheckbox = (CheckBox) convertView.findViewById(R.id.cbCheck);
				holder.index = (TextView) convertView.findViewById(R.id.index);
				holder.name_item = (TextView) convertView.findViewById(R.id.nameitem);
				holder.total = (TextView) convertView.findViewById(R.id.note_item);
				holder.updateTableImage = (ImageView)convertView.findViewById(R.id.img_update_table);
				convertView.setTag(holder);
			} else {
				holder = (viewHolderInvoice) convertView.getTag();
				holder.getCbCheckbox();
			}
			
			final Invoice inv = lstInv.get(position);
			//----Vu: thay doi mau cho cac trang thai invoice:
			// da thanh toan: xam
			// chua thanh toan: xanh
			// hoa don huy; do
			if(inv.getStatus() == 0) // da thanh toan
			{
				holder.index.setTextColor(Color.parseColor("#B7B7B7"));
				holder.name_item.setTextColor(Color.parseColor("#B7B7B7"));
				holder.total.setTextColor(Color.parseColor("#B7B7B7"));
				holder.updateTableImage.setEnabled(false);
			}else if(inv.getStatus() == 2)// hoa don huy
			{
				holder.index.setTextColor(Color.parseColor("#C82E31"));
				holder.name_item.setTextColor(Color.parseColor("#C82E31"));
				holder.total.setTextColor(Color.parseColor("#C82E31"));
				holder.updateTableImage.setEnabled(false);
			}else// 1: chua thanh toan
			{ 
				holder.index.setTextColor(Color.parseColor("#5BBD2B"));
				holder.name_item.setTextColor(Color.parseColor("#5BBD2B"));
				holder.total.setTextColor(Color.parseColor("#5BBD2B"));
				holder.updateTableImage.setEnabled(true);
			}
			holder.cbCheckbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
						@Override
						public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
							if (buttonView.isChecked()) {
								if (!InvoicePosFragment.lstinv_code.contains(inv.getInv_code())) {
									InvoicePosFragment.lstinv_code.add(inv.getInv_code());
									iGetInvCode.getInvCode(inv.getInv_code());
								}
								inv.setCheck(true);
							} else {
								inv.setCheck(false);
								if (InvoicePosFragment.lstinv_code.contains(inv.getInv_code())) {
									InvoicePosFragment.lstinv_code.remove(inv.getInv_code());
								}
							}
						}
					});  //no comment// ssss
			holder.cbCheckbox.setChecked(lstInv.get(position).isCheck());
			if (inv.getUser_id() !=  MainPosActivity.user_id ) {
				holder.cbCheckbox.setEnabled(true);
			}   
			if (inv != null) {
				holder.index.setText(String.valueOf(position + 1));
				
				// Đổi lại tên invoice ko để là T1_B1_08987876
				// Đổi thành HD Bàn (B1) Tầng 1
				String mInvCode = new FormatFloorTableName().formatNameInvoice(inv.getInv_code());
				mInvCode = "Guest "+mInvCode;
				holder.name_item.setText(mInvCode);
				holder.total.setText(formatDecimal(inv.getTotal()) + "");
			}
			holder.updateTableImage.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					codeTables = lstInv.get(position).getLstCodeTables();
					ArrayList<Itable> lstAllItableOfAllFloor ;
					try {
						nameInvoice = "";
						nameInvoice = lstInv.get(position).getInv_code();
						nameOldInvoice = lstInv.get(position).getInv_code();
						
						lstTableTmp = new ArrayList<Table>();
						
						lstAllItableOfAllFloor = new WSGetAllTable(context).execute(ConfigurationServer.getURLServer() + "wsget_all_itable.php").get();
						ArrayList<String> lstTableFree = getItable(lstAllItableOfAllFloor);
						//lstTableFree.addAll(codeTables);
						for (int i = 0; i < codeTables.size(); i++) {
							Table tb = new Table();
							tb.setName(codeTables.get(i));
							tb.setCheck(true);
							lstTableTmp.add(tb);
							nameInvoices.add(codeTables.get(i));
						}
						
						
						chooseTable(lstTableFree);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} catch (Exception e) {
			Log.i("Log : ", "Exception : " + e.getMessage());
		}
		return convertView;
	}

	/**
	 * Interface ném mã hóa đơn: inv_code ra ngoài main xử lý cho tác vụ hủy hóa đơn 
	 *
	 */
	public interface IGetInvCode {
		public void getInvCode(String invCode);
	}
	
	public String formatDecimal(double number) {

		DecimalFormat nf = new DecimalFormat("###,###,###,##0");
		String formatted = nf.format(number);
		return formatted;
	}

	public CheckBox getCheckBok() {
		CheckBox cb = new CheckBox(context);
		cb = holder.getCbCheckbox();
		return cb;
	}

	class viewHolderInvoice {
		LinearLayout lnMain;
		TextView index;
		CheckBox cbCheckbox;
		TextView name_item;
		TextView total;
		ImageView updateTableImage;

		public void setCbCheckbox(CheckBox cbCheckbox) {
			this.cbCheckbox = cbCheckbox;
		}

		public CheckBox getCbCheckbox() {
			return cbCheckbox;
		}

	}
	
	private ArrayList<String> getItable(List<Itable> listItable) {
		ArrayList<String> listValueItable = new ArrayList<String>();
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> hasMapItable = new HashMap<String, String>();
		for (Itable hashMap : listItable) {
			Log.i("Log Code_table: ", hashMap.getCode_table());
			Log.i("Log Description_table: ", hashMap.getDescription_table());
		}
		// check status table used ? No => put ->Hasmap ->Show in spinner
		/*for (int i = 0; i < listItable.size(); i++) {
			if (listItable.get(i).getStatus() != 2) {
				hasMapItable.put(listItable.get(i).getCode_table(), listItable.get(i).getDescription_table());
			}
		}*/
		// Loại bỏ bàn đang sử dụng: status = 2
		for (Itable	itable : listItable) {
			if( itable.getStatus() != 2 ){
				listValueItable.add(itable.getCode_table());
			}
		}
		/*for (Map.Entry<String, String> e : hasMapItable.entrySet()) {
			String key = e.getKey();
			String value = e.getValue();
			listValueItable.add(key);
			Collections.sort(listValueItable);
		}*/
		list.add(hasMapItable);
		return listValueItable;
	}
	
	private void chooseTable(final ArrayList<String> data) {
		//createDialog(data);
	}
	
	/**
	 * @param lstString : Danh sách các bàn trống cho phép người dùng chọn
	 */
	/*private void createDialog(ArrayList<String> lstString) {
		for (String str : lstString) {
			Log.d("___Danh sách bàn trống hiển thị lên Dialog choose table:", str);
		}
		final Dialog dialog = new Dialog(new ContextThemeWrapper(context, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar));
		dialog.setTitle(context.getString(R.string.selectchoosetable));
		// dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		itemTable.clear();
		for (int i = 0; i < lstString.size(); i++) {
			Table tb = new Table();
			tb.setName(lstString.get(i));
			if (table_code != "" && lstString.get(i).contains(table_code))
				tb.setCheck(true);
			lstTableTmp.add(tb);
		}
		dialog.setContentView(R.layout.tablelayout);
		GridView gridView1 = (GridView) dialog.findViewById(R.id.gridViewTable);
		// dialog.setCancelable(false);
		ShowTableAdapter adapter = new ShowTableAdapter(context, lstTableTmp);
		gridView1.setAdapter(adapter);

		ImageButton btnDiaSplitChooseTableOK = (ImageButton) dialog.findViewById(R.id.btnDiaSplitChooseTableOK);
		ImageButton btnDiaSplitChooseTableCancel = (ImageButton) dialog.findViewById(R.id.btnDiaSplitChooseTableCancel);
		btnDiaSplitChooseTableOK.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				int k = 0;
				String sCodeTable = "";
				String newNameCode = "";
				String[] codeTables = nameInvoice.split("_");
				int size = codeTables.length;
				newNameCode =  codeTables[size - 2] + "_" + codeTables[size - 1];
				for (Table table : lstTableTmp) {
					if (table.isCheck()) {
						itemTable.add(table.getName());
						sCodeTable += table.getName() + "";
						k++;
					}
				}
				
				for(int i = 0; i < nameInvoices.size(); i++){
					if(!itemTable.contains(nameInvoices.get(i))){
						items.add(nameInvoices.get(i));
					}
				}
				
				if(!"".equals(sCodeTable)){
					newNameCode = sCodeTable + "_" + newNameCode;
					if(k != 0 && !newNameCode.equals(nameOldInvoice) && !"".equals(sCodeTable)){
						//them mot invoice moi vao bang invoice
						new WSInsertNewInvoice(context, nameOldInvoice, newNameCode, 1, MainPosActivity.user_id, "", 0).execute();
						new WSAddInvTable2(context, nameOldInvoice, newNameCode, MainPosActivity.user_id, itemTable).execute();
						new WSUpdateItableStatus(context, 2, MainPosActivity.user_id, itemTable).execute();
						new WSUpdateItableStatus(context, 0, MainPosActivity.user_id, items).execute();
						new WSUpdateInvoiceDetail(getContext(), nameOldInvoice, newNameCode, ConfigurationServer.language_code).execute();
					}else{
						Toast.makeText(getContext(), "khong co gi thay doi", 1).show();
					}
				}else{
					Toast.makeText(getContext(), "chua co ban nao duoc chon", 1).show();
				}
				dialog.cancel();
			}
		});
		btnDiaSplitChooseTableCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialog.cancel();
			}
		});
		try {
			dialog.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
}
