package iii.pos.client.util;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import android.os.StrictMode;
import android.util.Log;


public class PosCurrency {
	public ArrayList<String> lstCode = new ArrayList<String>();

	public PosCurrency() {

	}

	public HashMap<String, String> Currency() {
		HashMap<String, String> hm = new HashMap<String, String>();
		ArrayList<String> lstCode = new ArrayList<String>();
		ArrayList<String> lstPrice = new ArrayList<String>();

		int j = 0;
		lstCode.add("VND");
		lstPrice.add("1");
		hm = new HashMap<String, String>();
		hm.put("VND", "1");

		ArrayList<String> list = getCurrency();
		for (int i = 0; i < list.size(); i++) {
			if (i % 4 == 0) {
				lstCode.add(list.get(i));
			}
			if (i % 4 == 1) {
				lstPrice.add(list.get(i));
				hm.put(lstCode.get(j), lstPrice.get(j));
				j++;
			}
		}
		this.lstCode = lstCode;
		hm.put(lstCode.get(j), lstPrice.get(j));

		return hm;
	}

	/*------------return list currency from internet---*/
	private ArrayList<String> getCurrency() {
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		ArrayList<String> list = new ArrayList<String>();

		HtmlCleaner cleaner = new HtmlCleaner();
		CleanerProperties props = cleaner.getProperties();

		props.setTranslateSpecialEntities(true);
		props.setTransResCharsToNCR(true);
		props.setOmitComments(true);

		Object[] table;
		try {
			TagNode root = cleaner.clean(new URL(
					"http://www.vietcombank.com.vn/"));
			table = root.evaluateXPath("//table[@class='tbl-exch']");

			if (table.length > 0) {

				TagNode tablenode = (TagNode) table[0];
				String xml = cleaner.getInnerHtml(tablenode);
				Log.i("xml", "Table node  \n" + xml);
				Object[] tien = tablenode.evaluateXPath("//td");
				for (Object tiennode : tien) {
					TagNode tiennodechild = (TagNode) tiennode;
					list.add(cleaner.getInnerHtml(tiennodechild));
				}
			}
		} catch (IOException ex) {

		} catch (XPatherException ex) {

		}
		return list;
	}

}
