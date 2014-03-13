package iii.pos.client.adapter;

import iii.pos.client.R;
import iii.pos.client.activity.MainPosActivity;
import iii.pos.client.fragment.InvoiceDetailPosFragment;
import iii.pos.client.model.Invoice_Detail;
import iii.pos.client.server.ConfigurationServer;
import iii.pos.client.server.ConfigurationWS;
import iii.pos.client.util.ImageLoader;
import iii.pos.client.wsclass.WSVoiceService;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.InputType;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class InvoiceDetailAdapter extends ArrayAdapter<Invoice_Detail>{
	private Context context;
	private Context context1;
	public ArrayList<Invoice_Detail> lstInvDetail;
	private boolean status = false;
	private InvoiceDetailPosFragment myActivity;
	private ConfigurationWS mWS;
	//private ConfigurationDB mDB;
	private ImageLoader imageLoader;
	private ConfirmAdapter adapterConfirm;
	private ListView lvConfirm;
	private ArrayList<Invoice_Detail> lstDetailInvDetail;
	Double RXmax;
	
	public InvoiceDetailAdapter(boolean status, Context context, InvoiceDetailPosFragment activity, Context context1, int textViewResourceId, ArrayList<Invoice_Detail> invoicelist) {
		super(context, textViewResourceId, invoicelist);
		this.context = context;
		this.context1 = context1;
		this.lstInvDetail = invoicelist;
		this.status = status;
		myActivity = activity;
		imageLoader = new ImageLoader(context1);
		lstDetailInvDetail = new ArrayList<Invoice_Detail>();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolderInvDetail holder = null;

		try {
			if (convertView == null) {
				holder = new ViewHolderInvDetail();
				LayoutInflater inflate = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflate.inflate(R.layout.temp_listinvoice, null);
				holder.imgSoundAdd = (ImageView) convertView.findViewById(R.id.imgSoundAdd);
				holder.imgSoundStop = (ImageView) convertView.findViewById(R.id.imgSoundStop);
				holder.imgView = (ImageView) convertView.findViewById(R.id.imageIcon);
				holder.name = (TextView) convertView.findViewById(R.id.tvnameitem);
				holder.textAmount = (TextView) convertView.findViewById(R.id.textAmount);
				holder.price = (TextView) convertView.findViewById(R.id.tvunit_item);
				holder.imgBtnDel = (ImageButton) convertView.findViewById(R.id.imbuttondelete);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolderInvDetail) convertView.getTag();
			}
			
			final Invoice_Detail invoicedetail = lstInvDetail.get(position);
			mWS = new ConfigurationWS(context);
			//mDB = new ConfigurationDB(context);
			/*if (this.status) {
			}else{
				holder.imgSoundAdd.setEnabled(this.status);
				holder.imgSoundStop.setEnabled(this.status);
				holder.textAmount.setEnabled(this.status);
				holder.name.setEnabled(this.status);
				holder.imgBtnDel.setEnabled(this.status);
			}*/
			holder.imgBtnDel.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					removeitem(invoicedetail);
				}
			});
			try {
				String URLdownloading = context.getResources().getString(R.string.URLdownloading);
				imageLoader.DisplayImage(URLdownloading + invoicedetail.getImgName(),holder.imgView);
			} catch (Exception e) {
			}
			// ------------- click sound --------------------------------//
			holder.imgSoundAdd.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if (invoicedetail.getId() != 0) {
						try {
							//new WSVoiceService(context1, invoicedetail.getId(),invoicedetail.getItem_id(), invoicedetail.getInv_code(), 1, 1).execute();
							createDialog(context1, invoicedetail);
							
							Log.i("__SOUND INVOICE DETAIL:", invoicedetail.getInv_code());
							
						} catch (Exception e) {
						}
					} else {
						Toast.makeText(context1, context1.getResources().getString(R.string.testcodetable),Toast.LENGTH_SHORT).show();
					}
				}
			});
			// ------------- click sound stop-----------------------------//
			holder.imgSoundStop.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if (invoicedetail.getId() != 0) {
						try {
							new WSVoiceService(context1, invoicedetail.getId(), invoicedetail.getItem_id(), invoicedetail.getInv_code(), 1, 0, 0).execute();
						} catch (Exception e) {
						}
					} else {
						Toast.makeText(context1, context1.getResources().getString(R.string.testcodetable), Toast.LENGTH_SHORT).show();
					}
				}
			});
			// --end click sound -------
			holder.name.setText(invoicedetail.getName());
			holder.name.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					showDialog(invoicedetail);
				}
			});
			holder.textAmount.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					showDialog(invoicedetail);
				}
			});
			if (invoicedetail.getComment().equals(" ") || invoicedetail.getComment().equals("")) {
				holder.name.setTextColor(Color.YELLOW);
				holder.price.setTextColor(Color.YELLOW);
				holder.textAmount.setTextColor(Color.YELLOW);
			} else {
				holder.name.setTextColor(Color.RED);
				// ----------click to name---------------------//
				holder.name.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						Toast.makeText(context, invoicedetail.getComment(), Toast.LENGTH_LONG).show();
						showDialog(invoicedetail);
					}
				});

				holder.imgView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Toast.makeText(context, invoicedetail.getComment(), Toast.LENGTH_LONG).show();
					}
				});
			}
			holder.price.setText(String.valueOf(formatDecimal(invoicedetail.getPrice() * invoicedetail.getQuantity())+ " đ"));
			String text = invoicedetail.getLstOrder();
			if (text.length() > 0)
				holder.textAmount.setText(text.substring(0, text.length() - 1));
		} catch (Exception e) {
		}
		return convertView;
	}
	
	public String formatDecimal(double number) {

		DecimalFormat nf = new DecimalFormat("###,###,###,##0");
		String formatted = nf.format(number);

		return formatted;
	}

	private void showDialog(final Invoice_Detail inv_detail) {
		final Dialog dialog = new Dialog(context1);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.phuc_vu_xu_ly);
		dialog.setCancelable(false);

		if (new ConfigurationServer(context1).isOnline()) {
			new WSGetNumberItems(inv_detail.getItem_id(), inv_detail.getInv_code(), context1).execute();
		} else {
			Toast.makeText(context1, "Network not found", Toast.LENGTH_SHORT).show();
		}
		TextView tv = (TextView) dialog.findViewById(R.id.titleItem);
		tv.setText(inv_detail.getName());

		final ImageButton imgpvClose = (ImageButton) dialog.findViewById(R.id.imgpvClose);
		final ImageButton imgpvAdd = (ImageButton) dialog.findViewById(R.id.imgpvAdd);
		lvConfirm = (ListView) dialog.findViewById(R.id.lvpvSP);
		adapterConfirm = new ConfirmAdapter(lstDetailInvDetail, context1, myActivity);
	 
		imgpvClose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(
						new ContextThemeWrapper(context1, android.R.style.Theme_Black_NoTitleBar))
						.setIcon(android.R.drawable.ic_dialog_info)
						.setTitle(context1.getResources().getString(R.string.exit))
						.setPositiveButton(context1.getResources().getString(R.string.OK),
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface arg0, int arg1) {
										myActivity.lstInvDetail = MainPosActivity.beanDataAll.getlstInvDetail(inv_detail.getInv_code());
										myActivity.setAdapter(false);
										myActivity.displayResult();
										dialog.cancel();
									}
								}).setNegativeButton(context1.getResources().getString(R.string.Cancel), null).show();
			}
		});
		imgpvAdd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final View addView = ((Activity) context1).getLayoutInflater().inflate(R.layout.add, null);
				final EditText etAlerOrderAmount = (EditText) addView.findViewById(R.id.etpvAmount);
				etAlerOrderAmount.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED|InputType.TYPE_CLASS_NUMBER);

				new AlertDialog.Builder(
						new ContextThemeWrapper(context1, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar))
						.setIcon(android.R.drawable.ic_input_add)
						.setTitle(context1.getResources().getString(R.string.adddishes))
						.setView(addView)
						.setPositiveButton(context1.getResources().getString(R.string.add),
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface arg0, int arg1) {
										String sl = etAlerOrderAmount.getText().toString();
										int amount = 0;
										try {
											amount = Integer.parseInt(sl);
										} catch (Exception e) {
										}
										//if (amount > 0) {
											final String createtime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
											Invoice_Detail inv = new Invoice_Detail();
											inv.setInv_code(inv_detail.getInv_code());
											inv.setItem_id(inv_detail.getItem_id());
											inv.setQuantity(amount);
											inv.setChecked(0);
											inv.setStart_date(createtime);
											inv.setFlag(1);
											//lstDetailInvDetail.add(inv);
											ArrayList<Invoice_Detail> arr = new ArrayList<Invoice_Detail>();
											arr.add(inv);
											//MainPosActivity.beanDataAll.addListInvoiceDetailNEW(arr, inv_detail.getInv_code());
											new AddNewInvDetail(context1, inv_detail.getInv_code(), arr, inv_detail).execute();
										//}
									}
								}).setNegativeButton(context1.getResources().getString(R.string.cancel), null).show();
			}
		});
		
		lvConfirm.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Toast.makeText(context1, position + " click", Toast.LENGTH_SHORT).show();
			}
		});
		dialog.show();
	}

	// ==========delINVDetail in SQLite======================//
	private void delInvDetail(String inv_code, int item_id) {
		/*mDB.OpenDB();
		mDB.delete_item_invoice_detail(inv_code, item_id);
		mDB.closeDB();*/
	}

	// ==========update item=================================//
	public void updateItem(Invoice_Detail items, int position) {
		lstInvDetail.remove(position);
		lstInvDetail.add(position, items);
		return;
	}

	// ---------this method to remove (Delete an item)---------//
	private void removeitem(final Invoice_Detail itemtemp) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context1, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
		builder.setIcon(android.R.drawable.ic_menu_delete)
				.setTitle(context1.getResources().getString(R.string.delete))
				.setMessage(context1.getResources().getString(R.string.questiondelete))
				.setCancelable(false)
				.setPositiveButton(context1.getResources().getString(R.string.delete),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {

								if (new ConfigurationServer(context1).isOnline()) {
									// ------------delete from bean----//
									for (int i = 0; i < MainPosActivity.beanDataAll.lstInvDetail.size(); i++) {
										if (MainPosActivity.beanDataAll.lstInvDetail.get(i).getItem_id() == itemtemp.getItem_id()) {
											MainPosActivity.beanDataAll.lstInvDetail.remove(i);
										}

									}
									new DeleteInvDetail(itemtemp.getInv_code(), itemtemp.getItem_id()).execute();
									lstInvDetail.remove(itemtemp);

								} else {
									Toast.makeText(context1, "Network not found", Toast.LENGTH_SHORT).show();
								}

								// -------delete form SQLite------//
								delInvDetail(itemtemp.getInv_code(), itemtemp.getItem_id());
								myActivity.refreshAdapter();
								myActivity.displayResult();
								return;
							}
						})
				.setNegativeButton(context1.getResources().getString(R.string.Cancel),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
							}
						}).show();
	}

	public Bitmap getImageBitmap(Context context, String imgname) {
		Bitmap bm = null;
		if (imgname != null) {
			int id = context.getResources().getIdentifier(imgname, "drawable", context.getPackageName());
			if (id != 0) {
				bm = BitmapFactory.decodeResource(context.getResources(), id);
			}
		}
		return bm;
	}

	class ViewHolderInvDetail {
		ImageView imgView;
		TextView name;
		TextView textAmount;
		TextView price;
		ImageButton imgBtnDel;
		ImageView imgSoundAdd, imgSoundStop;
	}

	private class DeleteInvDetail extends AsyncTask<Void, Void, Void> {
		private String inv_code;
		private int item_id;

		public DeleteInvDetail(String inv_code, int item_id) {
			this.inv_code = inv_code;
			this.item_id = item_id;
		}

		@Override
		protected synchronized Void doInBackground(Void... params) {
			try {
				String UrlDeleteInv_Detail = ConfigurationServer.getURLServer() + "wsdeleteinvoice_detail.php";
				JSONObject json = new JSONObject();
				json.put("inv_code", inv_code);
				json.put("item_id", item_id);
				json.put("user_id", MainPosActivity.phoneNumber);
				mWS.connectWS_Put_Data(UrlDeleteInv_Detail, json);
			} catch (JSONException e) {

			}
			return null;
		}
	}
	
	public class AddNewInvDetail extends AsyncTask<Void, Void, Void>{
		private String inv_code;
		private List<Invoice_Detail> lstInvoice;
		private ConfigurationWS mWS;
		private Context mContext;
		private ProgressDialog dialog;
		private Invoice_Detail inv_detail;

		public  AddNewInvDetail(Context mContext, String inv_code, List<Invoice_Detail> lstInvoice, Invoice_Detail inv_detail) {
			this.inv_code = inv_code;
			this.lstInvoice = lstInvoice;
			this.mContext = mContext;
			this.inv_detail = inv_detail;
			mWS = new ConfigurationWS(mContext);
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(mContext);
			dialog.setMessage("Loading ...");
			dialog.show();
		}
		@Override
		protected Void doInBackground(Void... params) {
			try {
				String URLAddnewItem = ConfigurationServer.getURLServer() + "wsaddnewinvoicedetail.php";
				for (Invoice_Detail items : lstInvoice) {
					JSONObject json = new JSONObject();
					json.put("inv_code", inv_code);
					json.put("item_id", items.getItem_id());
					json.put("quantity", items.getQuantity());
					json.put("comment", items.getComment());
					json.put("user_id", MainPosActivity.phoneNumber);
					mWS.connectWS_Put_Data(URLAddnewItem, json);
				}
			} catch (Exception e) {
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			Toast.makeText(context, "Thêm Mới Món Ăn Thành Công", Toast.LENGTH_SHORT).show();
			new WSGetNumberItems(inv_detail.getItem_id(), inv_detail.getInv_code(), context1).execute();
			try {
				if (dialog.isShowing()) {
					dialog.dismiss();
				}
			} catch (Exception e) {
			}
			super.onPostExecute(result);
		}
	}
	
	private class WSGetNumberItems extends AsyncTask<Void, Void, Void> {

		private int item_id;
		private String inv_code;
		private ConfigurationWS mWS;
		private ProgressDialog mProgress;

		public WSGetNumberItems(int item_id, String inv_code, Context mcontext) {
			this.item_id = item_id;
			this.inv_code = inv_code;
			mWS = new ConfigurationWS(mcontext);
			mProgress = new ProgressDialog(mcontext);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgress.setMessage("Loading...");
			mProgress.setCancelable(false);
			mProgress.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				lstDetailInvDetail.clear();
				String wsgetdetailinvdetail = ConfigurationServer.getURLServer() + "wsgetdetailinvdetail.php";
				JSONObject json = new JSONObject();
				json.put("item_id", item_id);
				json.put("inv_code", inv_code);
				json.put("user_id", MainPosActivity.phoneNumber);
				JSONArray arrItem = new JSONArray();
				arrItem = mWS.connectWSPut_Get_Data(wsgetdetailinvdetail, json, "inv_d");
				if (arrItem != null) {
					for (int i = 0; i < arrItem.length(); i++) {
						JSONObject results = arrItem.getJSONObject(i);
						Invoice_Detail inv_detail = new Invoice_Detail();
						inv_detail.setId(results.getInt("id"));
						inv_detail.setInv_code(results.getString("inv_code"));
						inv_detail.setItem_id(results.getInt("item_id"));
						inv_detail.setFlag(results.getInt("flag"));
						inv_detail.setQuantity(results.getInt("quantity"));
						try {
							inv_detail.setComment(results.getString("comment"));
							Log.e(">>>>>>>>>>>>>> ", ">> i =" + results.getInt("checked"));
							inv_detail.setChecked(results.getInt("checked"));
							inv_detail.setStart_date(results.getString("invd_createtime"));
							inv_detail.setEnd_date(results.getString("invd_updatetime"));
						} catch (Exception e) {
						}
						lstDetailInvDetail.add(inv_detail);
					}
				}
			} catch (Exception e) {
				Log.i("LOG", "Insert INV Detail : " + e.getMessage());
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (adapterConfirm != null) {
				lvConfirm.setAdapter(adapterConfirm);
				MainPosActivity.beanDataAll.makeDataInvDetail();
				notifyDataSetChanged();	 
			}
			try {
				mProgress.dismiss();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * khiemnd tao dialog phan quyen
	 * 0 - public
	 * 1 - waiter
	 * 2 - cashier
	 * 3 - manager
	 * 4 - kitchen
	 * 5 - client
	 */
	public void createDialog(final Context context, final Invoice_Detail invoicedetail){
		final String[] permissionArr = getContext().getResources().getStringArray(R.array.permission_array);
		final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.choice_dialog);
		ListView permissionList = (ListView)dialog.findViewById(R.id.lst_permission);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.item_permission, R.id.txt_permission, permissionArr);
		permissionList.setAdapter(adapter);
		
		permissionList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
				new WSVoiceService(context1, invoicedetail.getId(), invoicedetail.getItem_id(), invoicedetail.getInv_code(), 1, 1, pos).execute();
				Toast.makeText(getContext(), "" + permissionArr[pos], Toast.LENGTH_SHORT).show();
				dialog.dismiss();
			}
		});
		dialog.show();
	}
}
