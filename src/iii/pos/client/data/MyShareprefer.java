package iii.pos.client.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class MyShareprefer {

	private SharedPreferences iShare;

	public static final String GET_INFOUSER_ITEM = "MyInfoUser";

	public static final String USER_ITEM = "USER";
	public static final String PASSWORD_ITEM = "PASSWORD";
	public static final String USER_ID = "USER_ID";
	public static final String INVOICE = "INVOICE";
	public static final String TIMEOUT = "TIMEOUT";
	public static final String USER_TITLE = "USER_TITLE";

	public MyShareprefer(Context context, String share_name) {
		iShare = context.getSharedPreferences(share_name, Context.MODE_PRIVATE);
	}

	public void logout() {
		iShare.edit().putString(USER_ITEM, "null").commit();
		iShare.edit().putString(PASSWORD_ITEM, "null").commit();
		iShare.edit().putInt(USER_ID, -1).commit();
	}

	public String getUerItem() {
		return iShare.getString(USER_ITEM, "null");
	}

	public String getPassWordItem() {
		return iShare.getString(PASSWORD_ITEM, "null");
	}

	public void setUserItem(String user_name) {
		iShare.edit().putString(USER_ITEM, user_name).commit();
	}

	public void setPassWordItem(String password) {
		iShare.edit().putString(PASSWORD_ITEM, password).commit();
	}

	public int getUser_id() {
		return iShare.getInt(USER_ID, -1);
	}

	public void setUser_id(int user_id) {
		iShare.edit().putInt(USER_ID, user_id).commit();
	}
	
	public String getUser_title() {
		Log.e("...........111111111", "" + iShare.getString(USER_ITEM, "null"));
		return iShare.getString(USER_ITEM, "null");
	}

	public void setUser_title(String user_title) {
		Log.e(">>>>>>>>>>>>>>222222222", "" + user_title);
		iShare.edit().putString(USER_TITLE, user_title).commit();
	}

	public int getInvoice() {
		return iShare.getInt(INVOICE, -1);
	}

	public void setInvoice(int invoice) {
		iShare.edit().putInt(INVOICE, invoice).commit();
	}

	public String getTimeOut() {
		return iShare.getString(TIMEOUT, "null");
	}

	public void setTimeOut(String timeout) {
		iShare.edit().putString(TIMEOUT, timeout).commit();
	}

}
