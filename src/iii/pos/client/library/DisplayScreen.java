package iii.pos.client.library;

import java.util.ArrayList;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

public class DisplayScreen {
	// Hiển thị màn hình nhỏ 320x480 px
	private static final int DEVICE_SMALL_WIDTH = 320;
	private static final int DEVICE_SMALL_HEIGHT = 480;
	
	// Hiển thị màn hình bình thường 480x800 px
	private static final int DEVICE_NORMAL_WIDTH = 480;
	private static final int DEVICE_NORMAL_HEIGHT = 800;
	
	// Hiển thị màn hình tablet 7 inch
	private static final int DEVICE_TABLET_7_WIDTH = 600;
	private static final int DEVICE_TABLET_7_HEIGHT = 1024;
	
	// Hiển thị màn hình tablet 10 inch
	private static final int DEVICE_TABLET_10_WIDTH = 720;
	private static final int DEVICE_TABLET_10_HEIGHT = 1280;
	
	private Context context;
	
	public DisplayScreen(Context mContext) {
		this.context = mContext;
	}

	/**
	 * Lấy Width và Height của màn hình
	 * @return ArrayList chứa Width và Height
	 */
	public ArrayList<Integer> getDisplayScreen() {
		ArrayList<Integer> lstScreens = new ArrayList<Integer>();
		DisplayMetrics displayMetrics = new DisplayMetrics();
		
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE); 
		wm.getDefaultDisplay().getMetrics(displayMetrics);
		
		int width = displayMetrics.widthPixels;
		int height = displayMetrics.heightPixels;
		
		lstScreens.add(width);lstScreens.add(height);
		
		Log.i("", "===================================");
		Log.i("___DefaultDisplay:", width + " - " + height);
		Log.i("", "===================================");
		
		return lstScreens;
	}
	
	/**
	 * Hàm chuyển đổi tọa độ để xử lý hiển thị đa thiết bị
	 * @param x : Vị trí tọa độ ox của bàn
	 * @param y : Vị trí tọa độ oy của bàn
	 * @return ArrayList chứa 2 vị trí của bàn hiển thị đa thiết bị
	 */
	public ArrayList<Integer> displayMultiScreen( int ox, int oy ){
		
		ArrayList<Integer> lstMultiScreens = new ArrayList<Integer>();
		
		DisplayMetrics displayMetrics = new DisplayMetrics();
		
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE); 
		wm.getDefaultDisplay().getMetrics(displayMetrics);
		
		int width = displayMetrics.widthPixels; //độ rộng width màn hình thiết bị
		int height = displayMetrics.heightPixels;//độ cao height màn hình thiết bị
		
		int dens = displayMetrics.densityDpi;
		double wi = (double) width / (double) dens;
		double hi = (double) height / (double) dens;
		double x = Math.pow(wi, 2);
		double y = Math.pow(hi, 2);
		double screenInches = Math.sqrt(x + y);
		 
		// Do màn hình ngang lên width = height và height = width
		
		if( height < DEVICE_SMALL_WIDTH){
			// 320x480
			Log.i("____ DEVICE SMALL:", width +"x"+height+" "+screenInches+" inches");
			Toast.makeText(context, "____ DEVICE SMALL:"+ width +"x"+height+" "+screenInches+" inches", Toast.LENGTH_SHORT).show();
			
		}else if( DEVICE_SMALL_WIDTH < height && height <= DEVICE_NORMAL_WIDTH ){
			// 480x800
			Log.i("____ DEVICE NORMAL:", width +"x"+height+" "+screenInches+" inches");
			Toast.makeText(context, "____ DEVICE NORMAL:"+ width +"x"+height+" "+screenInches+" inches", Toast.LENGTH_SHORT).show();
			
		}else if( DEVICE_NORMAL_WIDTH < height && height <= DEVICE_TABLET_7_WIDTH){
			// tablet 7 inch 720x1024
			Log.i("____ DEVICE TAB 7 INCH:", width +"x"+height+" "+screenInches+" inches");
			Toast.makeText(context, "____ DEVICE TAB 7 INCH:"+ width +"x"+height+" "+screenInches+" inches", Toast.LENGTH_SHORT).show();
			
		}else {
			// tablet 10 inch 800x1280
			Log.i("____ DEVICE TAB 10 INCH:", width +"x"+height+" "+screenInches+" inches");
			Toast.makeText(context, "____ DEVICE TAB 10 INCH:"+ width +"x"+height+" "+screenInches+" inches", Toast.LENGTH_SHORT).show();
			
		}
		
		
		return lstMultiScreens;
	}
}
