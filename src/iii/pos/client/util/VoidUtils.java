package iii.pos.client.util;

import iii.pos.client.R;
import iii.pos.client.activity.helper.MainKitchenActivityHelper;
import iii.pos.client.model.Voice;
import iii.pos.client.server.ConfigurationServer;
import iii.pos.client.server.ConfigurationWS;
import iii.pos.client.wsclass.WSVoiceService_UpdateVoice;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Environment;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

public class VoidUtils {
	
	// --------------- GET ALL VOICE STATUS = 0------------------------------
		public static void getAllSound(ConfigurationWS mWS, ArrayList<Voice> lstVoice) {
			try {
				String wsgetdetailinvdetail = ConfigurationServer.getURLServer() + "wsvoiceservice_getallvoice.php";
				JSONObject json = new JSONObject();
				JSONArray arrItem = new JSONArray();
				arrItem = mWS.connectWSPut_Get_Data(wsgetdetailinvdetail, json, "voiceservice_getallvoice");
				Log.d("LOG", "COOK: " + arrItem.length());
				if (arrItem != null) {
					for (int i = 0; i < arrItem.length(); i++) {
						JSONObject results = arrItem.getJSONObject(i);
						Voice voice = new Voice();
						try {
							voice.setVoice_id(results.getInt("voice_id"));
						} catch (Exception e) {
						}
						try {
							voice.setCode_table(results.getString("code_table"));
						} catch (Exception e) {
						}
						try {
							voice.setName(results.getString("name"));
						} catch (Exception e) {
						}
						try {
							voice.setQuantity(results.getInt("quantity"));
						} catch (Exception e) {
						}
						try {
							voice.setStatus(results.getInt("status"));
						} catch (Exception e) {
						}
						try {
							voice.setFlag(results.getInt("flag"));
						} catch (Exception e) {
						}
						try {
							voice.setChecked(results.getInt("checked"));
						} catch (Exception e) {
						}
						lstVoice.add(voice);
					}

				}
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}
		
		// -------------READ SOUND-------------
		public static void ReadSound(Context mContext, MediaPlayer media, long time, final ArrayList<Voice> arr) {
			try {
			 
				for (Voice voice : arr) {
					String name = voice.getName().toLowerCase().replaceAll("\\s", "");
					if (checkSound(mContext, name)) {
						int flag = voice.getFlag();
						String code_table = MainKitchenActivityHelper.convertNameTable(voice.getCode_table().trim());
						int quantity = voice.getQuantity();
						int checked = voice.getChecked();
						if (name.equalsIgnoreCase("")) {
							makeVoiceAddItems(mContext, media, time, "lam", "so_luong", 1, "lauga", "ban_so", "ban6");
							new WSVoiceService_UpdateVoice(mContext, voice.getVoice_id(), 0, 0).execute();
						} else {
							if (flag == 1) {
								try {
									if (checked == 0) {
										makeVoiceAddItems(mContext, media, time, "lam", "so_luong", quantity, name, "ban_so", code_table);
										Log.i("SOUND COOK::::", "lam " + "+" + "so luongv" + "+ " + voice.getQuantity() + "+ " + name + "+ " + "ban so" + "+ " + code_table);
										// --finish reading update to database----//
										new WSVoiceService_UpdateVoice(mContext, voice.getVoice_id(), 0, 0).execute();
									} else if (checked == 1) {
										makeVoiceAddItems(mContext, media, time, "them", "so_luong", quantity, name, "ban_so", code_table);
										Log.i("SOUND COOK::::", "lam " + "+" + "so luongv" + "+ " + voice.getQuantity() + "+ " + name + "+ " + "ban so" + "+ " + code_table);
										new WSVoiceService_UpdateVoice(mContext, voice.getVoice_id(), 0, 0).execute();
									}
								} catch (Exception e) {
								}

							} else if (flag == 0) {
								try {
									makeVoiceRemoveItems(mContext, media, time, "huy", "so_luong", quantity, name, "ban_so", code_table);
									// --finish reading update to database----//
									new WSVoiceService_UpdateVoice(mContext, voice.getVoice_id(), 0, 0).execute();
								} catch (Exception e) {
								}
							}
						}

					} else {
						Log.i("SOUND COOK::::", voice.getCode_table() + "/ " + name + "/ " + voice.getQuantity());
						callVibrate(mContext, time);
						new WSVoiceService_UpdateVoice(mContext, voice.getVoice_id(), 0, 0).execute();
					}
				}
			} catch (Exception e) {
			}
		}
		
		// ===============goiNhacThemMon=====================
		public static void makeVoiceAddItems(Context mContext, MediaPlayer media, long time, String them, String wordSL, int quantity,
				String name, String wordBS, String table) {

			callMessage(mContext, media, time, them);
			callWord(mContext, media, wordSL, time);
			callQuantity(mContext, media, time, quantity);
			callName(mContext, media, time, name);
			callWord(mContext, media, wordBS, time);
			callTable(mContext, media, time, table);

		}
		
		// ===============goiNhacHuyMon=====================
		public static void makeVoiceRemoveItems(Context mContext, MediaPlayer media, long time, String huy, String wordSL, int quantity, String name, String wordBS, String table) {
			callMessage(mContext, media, time, huy);
			callWord(mContext, media, wordSL, time);
			callQuantity(mContext, media, time, quantity);
			callName(mContext, media, time, name);
			callWord(mContext, media, wordBS, time);
			callTable(mContext, media, time, table);
		}
		
		// ======= Đọc tên bàn =======================
		public static void callTable(Context mContext, MediaPlayer media, long time, String table) {
			try {
				if (table != null) {
					media = new MediaPlayer();
					String tablevoice = Environment.getExternalStorageDirectory() + "/POS/Media/number/" + table + ".mp3";
					Uri uitable = Uri.parse(tablevoice);
					media.setDataSource(mContext, uitable);
					media.prepare();
					media.setOnCompletionListener(new OnCompletionListener() {
						@Override
						public void onCompletion(MediaPlayer mp) {
							mp.release();
						}
					});
					media.start();
					time = media.getDuration();
					Thread.sleep(time);
				} else {
					Toast.makeText(mContext, mContext.getResources().getString(R.string.notvoice), Toast.LENGTH_SHORT).show();
				}
			} catch (Exception e) {
			}
		}
		
		// ======= Đọc tên món ăn =======================
		public static void callName(Context mContext, MediaPlayer media, long time, String name) {
			try {
				if (name != null) {
					media = new MediaPlayer();
					String namevoice = Environment.getExternalStorageDirectory() + "/POS/Media/food/" + name + ".mp3";
					Uri uiname = Uri.parse(namevoice);
					media.setDataSource(mContext, uiname);
					media.prepare();
					media.setOnCompletionListener(new OnCompletionListener() {
						@Override
						public void onCompletion(MediaPlayer mp) {
							mp.release();
						}
					});
					media.start();
					time = media.getDuration();
					Thread.sleep(time);
				} else {
					Toast.makeText(mContext, mContext.getResources().getString(R.string.notvoice), Toast.LENGTH_SHORT).show();
				}
			} catch (Exception e) {
			}
		}
		
		// ======= Đọc số lượng món ăn =======================
		public static void callQuantity(Context mContext, MediaPlayer media, long time, int quantity) {
			try {
				if (quantity != 0) {
					media = new MediaPlayer();
					String quantityvoice = Environment.getExternalStorageDirectory() + "/POS/Media/number/" + "ban" + quantity + ".mp3";
					Uri uiquantity = Uri.parse(quantityvoice);
					media.setDataSource(mContext, uiquantity);
					media.prepare();
					media.setOnCompletionListener(new OnCompletionListener() {
						@Override
						public void onCompletion(MediaPlayer mp) {
							mp.release();
						}
					});
					media.start();
					time = media.getDuration();
					Thread.sleep(time);
				} else {
					Toast.makeText(mContext, mContext.getResources().getString(R.string.notvoice), Toast.LENGTH_SHORT).show();
				}
			} catch (Exception e) {
			}
		}
		
		// ======= Đọc thông báo thêm, hủy món ăn =======================
			public static void callMessage(Context mContext, MediaPlayer media, long time, String message) {
				try {
					media = MediaPlayer.create(mContext, getIdByName(mContext, message));
					if (message != null) {
						media = new MediaPlayer();
						String messvoice = Environment.getExternalStorageDirectory() + "/POS/Media/action/" + message + ".mp3";
						Uri uimessage = Uri.parse(messvoice);
						media.setDataSource(mContext, uimessage);
						media.prepare();
						media.setOnCompletionListener(new OnCompletionListener() {
							@Override
							public void onCompletion(MediaPlayer mp) {
								mp.release();
							}
						});
						media.start();
						time = media.getDuration();
						Thread.sleep(time);

					} else {
						Toast.makeText(mContext, mContext.getResources().getString(R.string.notvoice), Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {
				}
			}
		
		// ======= Đọc tu dem =======================
		public static void callWord(Context mContext, MediaPlayer media, String word, long time) {
			try {
				if (word != null) {
					media = new MediaPlayer();
					String messvoice = Environment.getExternalStorageDirectory() + "/POS/Media/word_bufer/" + word + ".mp3";
					Uri uimessage = Uri.parse(messvoice);
					media.setDataSource(mContext, uimessage);
					media.prepare();
					media.setOnCompletionListener(new OnCompletionListener() {
						@Override
						public void onCompletion(MediaPlayer mp) {
							mp.release();
						}
					});
					media.start();
					time = media.getDuration();
					Thread.sleep(time);
				} else {
					Toast.makeText(mContext, mContext.getResources().getString(R.string.notvoice), Toast.LENGTH_SHORT).show();
				}
			} catch (Exception e) {
			}
		}
		
		// ============ Check sound exist to folder raw ==========
		public static boolean checkSound(Context mContext, String nameSound) {
			if (getIdByName(mContext, nameSound) != 0)
				return true;
			return true;
		}
		
		// ============ convert name to raw ==============
		public static int getIdByName(Context mContext, String name) {
			int id = mContext.getResources().getIdentifier(name, "raw", mContext.getPackageName());
			if (id == 0) {
				return mContext.getResources().getIdentifier("ban1", "raw", mContext.getPackageName());
			}
			return id;
		}

		// =========== sound not exist call vibrate =========
		public static void callVibrate(Context mContext, long time) {
			try {
				time = 1000;
				Vibrator vibrate = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
				vibrate.vibrate(time);
				Thread.sleep(time);
			} catch (Exception e) {
			}
		}
		
		// =========== convert code_table to Name Table ========
		public static String convertNameTable(String code_table) {
			String nameTable = null;
			if (code_table.contains("B20")) {
				nameTable = "ban20";
			} else if (code_table.contains("B19")) {
				nameTable = "ban19";
			} else if (code_table.contains("B18")) {
				nameTable = "ban18";
			} else if (code_table.contains("B17")) {
				nameTable = "ban17";
			} else if (code_table.contains("B16")) {
				nameTable = "ban16";
			} else if (code_table.contains("B15")) {
				nameTable = "ban15";
			} else if (code_table.contains("B14")) {
				nameTable = "ban14";
			} else if (code_table.contains("B13")) {
				nameTable = "ban13";
			} else if (code_table.contains("B12")) {
				nameTable = "ban12";
			} else if (code_table.contains("B11")) {
				nameTable = "ban11";
			} else if (code_table.contains("B10")) {
				nameTable = "ban10";
			} else if (code_table.contains("B9")) {
				nameTable = "ban9";
			} else if (code_table.contains("B8")) {
				nameTable = "ban8";
			} else if (code_table.contains("B7")) {
				nameTable = "ban7";
			} else if (code_table.contains("B6")) {
				nameTable = "ban6";
			} else if (code_table.contains("B5")) {
				nameTable = "ban5";
			} else if (code_table.contains("B4")) {
				nameTable = "ban4";
			} else if (code_table.contains("B3")) {
				nameTable = "ban3";
			} else if (code_table.contains("B2")) {
				nameTable = "ban2";
			} else if (code_table.contains("B1")) {
				nameTable = "ban1";
			}
			if (nameTable == null) {
				nameTable = "ban20";
			}
			return nameTable;
		}
}
