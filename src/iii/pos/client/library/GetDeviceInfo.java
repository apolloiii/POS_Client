package iii.pos.client.library;

import android.content.Context;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

public class GetDeviceInfo {
	
	private Context context;

	public GetDeviceInfo(Context context) {
		this.context = context;
	}

	public String getDeviceId() {
		return Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
	}
	
	public String getPhoneNumber(){
		TelephonyManager mTelephonyMgr;
	    mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
	    return mTelephonyMgr.getLine1Number();
	}
	
}
