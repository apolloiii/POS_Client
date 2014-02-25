package iii.pos.client.fragment;

import iii.pos.client.R;
import iii.pos.client.activity.MainPosActivity;
import iii.pos.client.fragment.base.FragmentBase;
import iii.pos.client.model.Voice_pos;
import iii.pos.client.server.ConfigurationServer;
import iii.pos.client.server.ConfigurationWS;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class HeaderPosFragment extends FragmentBase {
	// -----------------constant-----------------------------//
	final static String ARG_POSITION = "position";

	// -----------------the fields---------------------------//
	@SuppressWarnings("unused")
	private int mCurrentPosition = -1;
	private ArrayList<Voice_pos> arrayList;
	private String name_voice;

	// action id for menu custom
	private static final int ID_LANGUAGE = 1;
	private static final int ID_CURRENCY = 2;
	private static final int ID_SERVER = 3;
	private static final int ID_INFO = 4;
	private static final int ID_COST = 5;
	private static final int ID_REPORT = 6;
	private static final int ID_INVSPEND = 7;
	private static final int ID_UPDATEVOICE=8;
	private static final int ID_ITABLE_MANAGER = 9;
	
	//private QuickMenuAction quickAction;
	//String strCurrency[] = { "VNĐ", "USD", "CNY", "JPY", "SGD", "LAK", "KRW", "KHR" };
	//String strLanguage[] = { "Việt Nam", "English", "Trung Quốc", "Nhật", "Singapore", "Lao", "Campuchia" };
	// ---------callback for action--------------
	OnHeaderSelectedListener mCallback;
	private Context mcontext;
	private ConfigurationWS mWS;
	
	private static final int REQUEST_SETTING_MANAGER = 1;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/*--------------------policy for connect to ws-------------*/
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		final View headerLayout = inflater.inflate(R.layout.header_pos, container, false);
		mcontext = headerLayout.getContext();
		mWS = new ConfigurationWS(mcontext);
		arrayList= new ArrayList<Voice_pos>();
		// If activity recreated (such as from screen rotate), restore
		// the previous article selection set by onSaveInstanceState().
		// This is primarily necessary when in the two-pane layout.
		if (savedInstanceState != null) {
			mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
		}
/*		ActionMenuItem languageItem = new ActionMenuItem(ID_LANGUAGE,
				mcontext.getString(R.string.language), getResources()
						.getDrawable(R.drawable.menu_language));
		
		ActionMenuItem itableManagerItem = new ActionMenuItem(ID_ITABLE_MANAGER,
				mcontext.getString(R.string.settingmenu_itablemanager), getResources()
						.getDrawable(R.drawable.footer_free));
		
		
		ActionMenuItem report = new ActionMenuItem(ID_REPORT,
				mcontext.getString(R.string.statistics_report), getResources().getDrawable(
						R.drawable.menu_report));*/

		/*ActionMenuItem invspend = new ActionMenuItem(ID_INVSPEND,
				mcontext.getString(R.string.expenses_invoice), getResources().getDrawable(
						R.drawable.menu_invoice));

		ActionMenuItem serverItem = new ActionMenuItem(ID_SERVER, "Server",
				getResources().getDrawable(R.drawable.menu_server));
		ActionMenuItem infoItem = new ActionMenuItem(ID_INFO,
				mcontext.getString(R.string.information), getResources()
						.getDrawable(R.drawable.menu_info));
		ActionMenuItem updVoiceItem = new ActionMenuItem(ID_UPDATEVOICE,
				mcontext.getString(R.string.updvoice), getResources()
						.getDrawable(R.drawable.voice));*/

		/*quickAction = new QuickMenuAction(headerLayout.getContext(),
				QuickMenuAction.VERTICAL);

		// add action items into QuickAction
		quickAction.addActionItem(languageItem);

		quickAction.addActionItem(serverItem);
		quickAction.addActionItem(report);
		quickAction.addActionItem(invspend);
		quickAction.addActionItem(updVoiceItem);

		quickAction.addActionItem(infoItem);
		quickAction.addActionItem(itableManagerItem);
		
		// Set listener for action item clicked
		quickAction.setOnActionItemClickListener(new QuickMenuAction.OnActionItemClickListener() {
					@SuppressWarnings("unused")
					@Override
					public void onItemClick(QuickMenuAction source, int pos,
							int actionId) {
						ActionMenuItem actionItem = quickAction
								.getActionItem(pos);

						// here we can filter which action item was clicked with
						// pos or actionId parameter
						if (actionId == ID_LANGUAGE) {
							createDialogSettingLanguage();
						} else if (actionId == ID_INFO) {
							createDialogSettingInfo();
						} else if (actionId == ID_CURRENCY) {
							createDialogSettingCurrency();
						} else if (actionId == ID_SERVER) {
							createDialogSettingServer();
						} else if (actionId == ID_COST) {
							Intent itent = new Intent(mcontext, ListSpendActivity.class);
							startActivity(itent);
						} else if (actionId == ID_INVSPEND) {
							Intent itent = new Intent(mcontext, InvoiceSpendActivity.class);
							startActivity(itent);
						} else if (actionId == ID_REPORT) {
							Intent itent = new Intent(mcontext, ReportSale_PaymentActivity.class);
							startActivity(itent);
						}
						else if (actionId == ID_UPDATEVOICE) {
//update voice
							new WSgetDataArise(mcontext).execute();
							new DownloadFile(mcontext).execute(); 
						}else if( actionId == ID_ITABLE_MANAGER){
							// Thêm bàn và xóa bàn
							Toast.makeText(mcontext, "Add Itable Manager", Toast.LENGTH_SHORT).show();
							//createDialogItableManager();
							Intent itent = new Intent(mcontext, SettingItableManager.class);
							startActivityForResult(itent, REQUEST_SETTING_MANAGER);
						}
					}
				});

*/		// set listnener for on dismiss event, this listener will be called only
		// if QuickAction dialog was dismissed
		// by clicking the area outside the dialog.
		/*quickAction.setOnDismissListener(new QuickMenuAction.OnDismissListener() {
					@Override
					public void onDismiss() {
					}
				});*/

		// show on btnSetting
		/*ImageButton btnSetting = (ImageButton) headerLayout.findViewById(R.id.btnSetting);
		btnSetting.setOnClickListener(btnFragmentOnClickListener);*/
		// show on btnTableMap
		/*Button btnTableMap = (Button) headerLayout.findViewById(R.id.btnMap);
		btnTableMap.setOnClickListener(btnFragmentOnClickListener);*/
		// show on btnInvoice
		Button btnInvoice = (Button) headerLayout.findViewById(R.id.btnInvoice);
		btnInvoice.setOnClickListener(btnFragmentOnClickListener);
		// show on btnLogin_out
		/*ImageButton btnLogin_out = (ImageButton) headerLayout.findViewById(R.id.btnLogin_Out);
		btnLogin_out.setOnClickListener(btnFragmentOnClickListener);*/

		/*Button btnPayment = (Button) headerLayout.findViewById(R.id.btnPayment);
		btnPayment.setOnClickListener(btnFragmentOnClickListener);
		Button btnKitchen = (Button) headerLayout.findViewById(R.id.btnKitchen);
		btnKitchen.setOnClickListener(btnFragmentOnClickListener);*/
		return headerLayout;
	}

	Button.OnClickListener btnFragmentOnClickListener = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			// Gọi thẳng tới màn hình Invoice
			mCallback.onMenuButtonClick(2, getView());
			/*
		 	int btnKey = 0;
			switch (v.getId()) {
			case R.id.btnMap:
				btnKey = 1;
				break;
			case R.id.btnInvoice:
				btnKey = 2;
				break;
			case R.id.btnKitchen:
				MainKitchenActivity.show(getActivity());
				break;
			case R.id.btnPayment:
				break;
			case R.id.btnSetting:
				if (quickAction != null)
					quickAction.show(v);
				break;
			case R.id.btnLogin_Out:
				btnKey = 5;
				break;
			default:
				break;
			}
			if(btnKey != 0)
				mCallback.onMenuButtonClick(btnKey, getView());
			*/
			 
		}
	};

	/**
	 * Hàm Refresh lại map khi Update Itable Manager trong menu setting 
	 */
	/*@Override
	public void onActivityResult(int requestCode, int resulCode, Intent intent) {
		super.onActivityResult(requestCode, resulCode, intent);
		
		if( requestCode == REQUEST_SETTING_MANAGER ){
			MapPosFragment myBodyFragemnt = new MapPosFragment(ConfigurationServer.floor);
			switchFragment(myBodyFragemnt, "");
		}
		
		
	}*/
	

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// This makes sure that the container activity has implemented
		// the callback interface. If not, it throws an exception.
		try {
			mCallback = (OnHeaderSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement OnHeadlineSelectedListener");
		}
	}

	// ----------creating dialog input server------//
	private void createDialogSettingServer() {

		final Dialog dialog = new Dialog(getActivity());
		dialog.setContentView(R.layout.setting_server);
		dialog.setTitle("IP Address : ");
		dialog.setCancelable(false);
		Button btnConnect = (Button) dialog.findViewById(R.id.btnConnect);
		btnConnect.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				EditText editURL = (EditText) dialog
						.findViewById(R.id.textIPAddress);
				String tempURL = editURL.getText().toString();
				if (tempURL == null || tempURL == "")
					Toast.makeText(mcontext,  getResources().getString(R.string.inputIP) ,
							Toast.LENGTH_LONG).show();
				else {
					ConfigurationServer.setURLServer(tempURL);
					// --------------save URL server here--------------//
					String URLServer = ConfigurationServer.getURLServer();

					//System.out.println("URL MOI LA: " + URLServer);
					// ----------------checking connection----------------//
					if (checkingServer(URLServer + "wslogin.php")) {
						Toast.makeText(mcontext, getResources().getString(R.string.connectsucess),
								Toast.LENGTH_LONG).show();
						ConfigurationServer conf = new ConfigurationServer(
								getActivity());
						conf.writeToFile(URLServer);
						dialog.dismiss();
					} else {
						// ----connecting failed show message---------//
						Toast.makeText(
								mcontext,
								getResources().getString(R.string.connectfail),
								Toast.LENGTH_LONG).show();
					}

				}
			}
		});
		Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialog.show();

	}

	// ----------------use to checking connection server------------------//
	private boolean checkingServer(String URLServer) {
		JSONObject json = new JSONObject();
		try {
			json.put("username", MainPosActivity.username);
			json.put("pass", MainPosActivity.pass);
			JSONArray jarr = mWS
					.connectWSPut_Get_Data(URLServer, json, "posts");
			Log.d("LOGIN: ", "  " + jarr);
			if (jarr != null) {
				return true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}

	String arrCurrency[] = { "VNĐ", "USD", "JPY" };

	HashMap<String, String> hm;

	private void createDialogSettingCurrency() {
		final Dialog dialog = new Dialog(getActivity());
		dialog.setTitle("Currency");
		dialog.setCancelable(true);
		dialog.setContentView(R.layout.setting_currency);

		Spinner currencyStart = (Spinner) dialog
				.findViewById(R.id.CurrencyStart);

		ArrayAdapter<String> adapterStart = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item,
				arrCurrency);
		adapterStart
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		currencyStart.setAdapter(adapterStart);

		final Spinner currencyEnd = (Spinner) dialog
				.findViewById(R.id.CurrencyEnd);

		ArrayAdapter<String> adapterEnd = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item,
				arrCurrency);
		adapterEnd
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		currencyEnd.setAdapter(adapterEnd);

		hm = new HashMap<String, String>();
		hm.put("VNĐ", "1");
		hm.put("USD", "20.5");
		hm.put("JPY", "224.48");

		final TextView CurrencyResult = (TextView) dialog
				.findViewById(R.id.CurrencyResult);

		final EditText textStart = (EditText) dialog
				.findViewById(R.id.textStart);

		Button btnConvert = (Button) dialog.findViewById(R.id.btnConvert);

		btnConvert.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String textStart_1 = textStart.getText().toString();

				if (!textStart_1.equals("")) {
					float unit = 0;

					unit = Float.parseFloat(textStart_1);
					String item = currencyEnd.getSelectedItem().toString();

					float curren = Float.parseFloat(hm.get(item));

					CurrencyResult.setText(displayCurrency(Locale.US,
							(unit / curren)));

				} else {
					Toast.makeText(getActivity(), "Nhập Số", Toast.LENGTH_LONG)
							.show();
					textStart.requestFocus();
				}
			}
		});

		Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialog.show();
	}

	private void createDialogSettingInfo() {
		final Dialog dialog = new Dialog(new ContextThemeWrapper(getActivity(), android.R.style.Theme_Holo_Light_Dialog));
		dialog.setContentView(R.layout.setting_info);
		dialog.setTitle("Infomation  ");

		dialog.show();
	}

	//Trọng Khiêm: Thêm submenu vào menu setting.
	// Lấy tất cả các itable hiện lên listivew
	private void createDialogItableManager() {
		final Dialog dialog = new Dialog(new ContextThemeWrapper(getActivity(), android.R.style.Theme_Holo_Light_Dialog));
		dialog.setContentView(R.layout.setting_itable_manager);
		dialog.setTitle("Itable Manager");
		ListView lvItable = (ListView) dialog.findViewById(R.id.lvItableManagerItable);

		//ItableManagerAdapter adapter = new ItableManagerAdapter(MainPosActivity.beanDataAll.lstItable, getActivity());
		
		
		
		
		dialog.show();
	}
	
	private String[] languages = { "English", "Vietnam" };

	private void createDialogSettingLanguage() {
		final Dialog dialog = new Dialog(getActivity());
		dialog.setContentView(R.layout.listlanguage_pos);
		dialog.setTitle("Language  ");

		ListView lv = (ListView) dialog.findViewById(R.id.listviewlanguage);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, languages);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Configuration config = new Configuration();
				switch (arg2) {
				case 0:
					Toast.makeText(getActivity().getBaseContext(), "English", Toast.LENGTH_SHORT).show();
					config.locale = Locale.ENGLISH;
					ConfigurationServer.language_code = 2;
					mCallback.updateLanguage("ENGLISH", getView());
					dialog.dismiss();
					break;
				case 1:
					config.locale = Locale.FRENCH;
					Toast.makeText(getActivity().getBaseContext(), "Vietnam", Toast.LENGTH_SHORT).show();
					mCallback.updateLanguage("FRENCH", getView());
					ConfigurationServer.language_code = 1;
					dialog.dismiss();
					break;
				default:
					config.locale = Locale.ENGLISH;
					break;
				}
				getActivity().getResources().updateConfiguration(config, null);
			}
		});
		lv.setAdapter(adapter);
		dialog.show();

	}

	static public String displayCurrency(Locale currentLocale, float unit) {
		// Double currency = new Double(34);
		NumberFormat currencyFormatter;
		String currencyOut;

		currencyFormatter = NumberFormat.getCurrencyInstance(currentLocale);
		currencyOut = currencyFormatter.format(unit);
		return currencyOut;
	}
	
	
	//---------------------------------------------Process DOWNLOAD---------------------------------------------//
	private class DownloadFile extends AsyncTask<String, Integer, String>{
		 private ProgressDialog mProgress;
		 public DownloadFile(Context mContext) {
			 mProgress = new ProgressDialog(mContext);
		}
		 @Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				mProgress.setMessage("Loading...");
				mProgress.setCancelable(false);
				mProgress.show();
			}
		@Override
		protected String doInBackground(String... url) {
		    int count;
		    try {
		    	for (Voice_pos voice : arrayList) {
					 name_voice = voice.getName();
					String type_voice = voice.getType().trim();
					 
				
		    	URL url1  = new URL("http://117.6.131.222:6789/pos/wspos/media/media/food/" + name_voice + ".mp3");
	            URLConnection conexion = url1.openConnection();
	            conexion.connect();
	            int lenghtOfFile = conexion.getContentLength();
	            InputStream is = new BufferedInputStream(url1.openStream());
	            File testDirectory1 =new File(Environment.getExternalStorageDirectory()+"/POS");
	            if(!testDirectory1.exists()){
	                testDirectory1.mkdir();
	            }
	            File testDirectory2 =new File(Environment.getExternalStorageDirectory()+"/POS/Media");
	            if(!testDirectory2.exists()){
	                testDirectory2.mkdir();
	            }
	            File testDirectory =new File(Environment.getExternalStorageDirectory()+"/POS/Media/food");
		            if(!testDirectory.exists()){
		                testDirectory.mkdir();
	            }
		        File testDirectory_action =new File(Environment.getExternalStorageDirectory()+"/POS/Media/action");
		            if(!testDirectory_action.exists()){
		            	testDirectory_action.mkdir();
	            }
		        File testDirectory_number =new File(Environment.getExternalStorageDirectory()+"/POS/Media/number");
		            if(!testDirectory_number.exists()){
		            	testDirectory_number.mkdir();
	            }
		        File testDirectory_worBuffer =new File(Environment.getExternalStorageDirectory()+"/POS/Media/word_bufer");
		            if(!testDirectory_worBuffer.exists()){
		            	testDirectory_worBuffer.mkdir();
	            }
		            if(type_voice.equalsIgnoreCase("action")){
		            	FileOutputStream fos = new FileOutputStream(testDirectory_action+"/"+name_voice +".mp3");
		   	            byte data[] = new byte[1024];
		   	            long total = 0;
		   	            int progress = 0;
		   	            while ((count=is.read(data)) != -1){
		   	                total += count;
		   	                int progress_temp = (int)total*100/lenghtOfFile;
		   	                if(progress_temp%10 == 0 && progress != progress_temp){
		   	                    progress = progress_temp;
		   	                }
		   	                	fos.write(data, 0, count);
		   					
		   	                
		   	            }
		   	            is.close();
		   	            fos.close();
		            } else if(type_voice.equalsIgnoreCase("food")){
		            	 FileOutputStream fos = new FileOutputStream(testDirectory+"/"+name_voice +".mp3");
			   	            byte data[] = new byte[1024];
			   	            long total = 0;
			   	            int progress = 0;
			   	            while ((count=is.read(data)) != -1){
			   	                total += count;
			   	                int progress_temp = (int)total*100/lenghtOfFile;
			   	                if(progress_temp%10 == 0 && progress != progress_temp){
			   	                    progress = progress_temp;
			   	                }
			   	                	fos.write(data, 0, count);
			   	            }
			   	            is.close();
			   	            fos.close();
		            }else if(type_voice.equalsIgnoreCase("word_bufer")){
		            	 FileOutputStream fos = new FileOutputStream(testDirectory_worBuffer+"/"+name_voice +".mp3");
			   	            byte data[] = new byte[1024];
			   	            long total = 0;
			   	            int progress = 0;
			   	            while ((count=is.read(data)) != -1){
			   	                total += count;
			   	                int progress_temp = (int)total*100/lenghtOfFile;
			   	                if(progress_temp%10 == 0 && progress != progress_temp){
			   	                    progress = progress_temp;
			   	                }
			   	                	fos.write(data, 0, count);
			   					
			   	                
			   	            }
			   	            is.close();
			   	            fos.close();
		            }else if(type_voice.equalsIgnoreCase("number")){
		            	 FileOutputStream fos = new FileOutputStream(testDirectory_number+"/"+name_voice +".mp3");
			   	            byte data[] = new byte[1024];
			   	            long total = 0;
			   	            int progress = 0;
			   	            while ((count=is.read(data)) != -1){
			   	                total += count;
			   	                int progress_temp = (int)total*100/lenghtOfFile;
			   	                if(progress_temp%10 == 0 && progress != progress_temp){
			   	                    progress = progress_temp;
			   	                }
			   	                	fos.write(data, 0, count);
			   					
			   	                
			   	            }
			   	            is.close();
			   	            fos.close();
		            }
	         
		    	} 
		    } catch (Exception e) {}
		    return null;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			
			try {
				if (mProgress != null)
					mProgress.dismiss();
			} catch (Exception e) {

			}
		}
	}
	
	// ---------------------------Get data----------------------------//

		private class WSgetDataArise extends AsyncTask<Void, Void, Void> {
			private ProgressDialog progressDialog;
			private ConfigurationWS mWS;
			public WSgetDataArise(Context mcontext) {
				progressDialog = new ProgressDialog(mcontext);
				mWS = new ConfigurationWS(mcontext);
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
					try {
						arrayList.clear();
						// ---------------get String ------------------------//
						String URL = ConfigurationServer.getURLServer()+ "wsgetnamevoice.php";
						JSONObject json = new JSONObject();
						JSONArray jarr = mWS.connectWSPut_Get_Data(URL, json, "posts");
						for (int i = 0; i < jarr.length(); i++) {
							JSONObject element = jarr.getJSONObject(i);
							Voice_pos voice = new Voice_pos();
							Log.e(">>>>>>>>>name = ", "" + element.getString("name"));
							voice.setName(element.getString("name"));
							voice.setType(element.getString("type"));
							arrayList.add(voice);
						}
					} catch (Exception e) {
						Log.i("Log : ", "Exception : " + e.getMessage());
					}
				} catch (Exception e) {
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				try {
					if (progressDialog != null)
						progressDialog.dismiss();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}

		// The container Activity must implement this interface so the frag can
		// deliver messages
		public interface OnHeaderSelectedListener {
			/** Called by HeadlinesFragment when a list item is selected */
			public void onMenuButtonClick(int btnKey, View view);

			public void updateLanguage(String languageKey, View view);
		}
}