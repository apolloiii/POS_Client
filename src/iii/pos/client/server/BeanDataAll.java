package iii.pos.client.server;

import iii.pos.client.activity.MainPosActivity;
import iii.pos.client.model.Category;
import iii.pos.client.model.Invoice;
import iii.pos.client.model.Invoice_Detail;
import iii.pos.client.model.Itable;
import iii.pos.client.model.Items;
import iii.pos.client.model.User;
import iii.pos.client.wsclass.WSGetAllTable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;


public class BeanDataAll {

	/*-----------------list User-----------------------------*/
	//public ArrayList<User> lstUser;
	/*-----------------list Invoice---------------------------*/
	public List<Invoice> lstInvoice;
	/*-----------------list table by floor--------------------*/
	//public List<Itable> lstItableByFloor;
	/*-----------------list Invoice_detail--------------------*/
	public List<Invoice_Detail> lstInvDetail;
	/*-----------------list Item -----------------------------*/
	public ArrayList<Items> lstItems;
	/*-----------------list Category -------------------------*/
	public ArrayList<Category> lstcategory;
	/*-----------------list An InvDetail ---------------------*/
	public List<Invoice_Detail> lstAnInvDetail;
	
	// Trọng Khiêm : Đổi lại tên lstItable thành lstItableByFloor ko sử dụng của anh Thuận nữa.
	// Khai báo một lstItable chứa tất cả các bàn của các tầng khác nhau
	//public ArrayList<Itable> lstAllItable ;
	//public static List<Invoice> lstInvoiceMoke;
	
	private ConfigurationWS mWS;
	private Context context;
	private ProgressDialog progressMakeBean;

	/*-------URL-----------------------------------------------*/
	private String URLgetAllUser;
	private String URLGetAllItable;
	private String URLGetAllInvoice;
	private String URLGetAllCategories;
	private String URLGetAllItems;
	private String URLGetAllInvdetail;

	/*-----------initialize beanall----------------------------*/
	
	public BeanDataAll(Context context) {

		lstInvoice = new CopyOnWriteArrayList<Invoice>();
		lstItems = new ArrayList<Items>();
		//lstItableByFloor = new CopyOnWriteArrayList<Itable>();
		lstcategory = new ArrayList<Category>();
		//lstUser = new ArrayList<User>();
		lstInvDetail = new CopyOnWriteArrayList<Invoice_Detail>();
		lstAnInvDetail = new CopyOnWriteArrayList<Invoice_Detail>();
		/*lstAllItable = new ArrayList<Itable>();
		lstInvoiceMoke = new ArrayList<Invoice>();*/
		
		this.context = context;
		mWS = new ConfigurationWS(context);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		if (new ConfigurationServer(context).isOnline()) {
			// --------methods to get data and fill to bean-------------//
			// Khởi tạo các mảng Dữ Liệu
			new MakeBean(context).execute();
		} else {
			Toast.makeText(context, "Network not found", Toast.LENGTH_SHORT).show();
		}

	}

	/* -----------------add invoice to bean------------- */
	public void addInvoice(Invoice inv) {
		if (lstInvoice != null) {
			lstInvoice.add(inv);
		}
	}

	/* --------------get User data from server-------- */
	/*public void makeDataUser() {
		if (lstUser != null)
			lstUser.clear();
		try {
			JSONObject json = new JSONObject();
			json.put("username", "dohai");
			json.put("pass", "123");
			// ---------------get String ------------------------//
			URLgetAllUser = ConfigurationServer.getURLServer() + "wsgetalluser.php";
			JSONArray arrUser = mWS.connectWSPut_Get_Data(URLgetAllUser, json, "users");
			for (int i = 0; i < arrUser.length(); i++) {
				JSONObject results = arrUser.getJSONObject(i);
				User users = new User();
				users.setUsername(results.getString("user_name"));
				users.setPassword(results.getString("password"));
				users.setEmail(results.getString("email"));
				users.setFlag(results.getInt("flag"));
				lstUser.add(users);
			}
		} catch (Exception e) {
			Log.i("Log : ", "Exception : " + e.getMessage());
		}
	}*/
	
	
	/* --------------get ITable data from server-------- */
	
	/*public ArrayList<Itable> getAllItableOfAllFloor() {
		// Trọng Khiêm thêm vào:
		// Lấy thêm tất cả các bàn thuộc các tầng khác nhau
		try {
			lstAllItable = new WSGetAllTable(context).execute(ConfigurationServer.getURLServer() + "wsget_all_itable.php").get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lstAllItable;  
	}
	
	
	*//** Set get DL ảo cho ListInvoice *//*
	public static void setLstInvoiceMoke( Invoice lstInvoiceMoke) {
		BeanDataAll.lstInvoiceMoke.add(lstInvoiceMoke);
	}
	public static List<Invoice> getLstInvoiceMoke() {
		return lstInvoiceMoke;
	}
	*/
	
	/*
	public void makeDataTableByFloor(int floor) {

		if (lstItableByFloor != null)
			lstItableByFloor.clear();
		try {
			// ---------------get String ------------------------//
			URLGetAllItable = ConfigurationServer.getURLServer() + "wsgetallitable.php";
			JSONObject json = new JSONObject();
			json.put("floor", floor);
			JSONArray arrITable = mWS.connectWSPut_Get_Data(URLGetAllItable, json, "itable");

			for (int i = 0; i < arrITable.length(); i++) {
				JSONObject results = arrITable.getJSONObject(i);
				Log.i("Dòng 123 BeanAllData Log : ", "TABLE : " + results);

				Itable itable = new Itable();
				itable.setItable_id(results.getInt("itable_id"));
				itable.setCode_table(results.getString("code_table"));
				itable.setDescription_table(results.getString("description_table"));
				itable.setStatus(results.getInt("status"));
				itable.setCreate_time(results.getString("create_time"));
				itable.setUpdate_date(results.getString("update_time"));
				itable.setFlag(results.getInt("flag"));
				itable.setPos_x(results.getInt("pos_x"));
				itable.setPos_y(results.getInt("pos_y"));
				lstItableByFloor.add(itable);
			}
		} catch (Exception e) {
			Log.i("Log : ", "Exception : " + e.getMessage());
		}

	}*/

	/*--------------get Invoice data from server--------*/
	public void makeDataInvoice1() {
		if (new ConfigurationServer(context).isOnline()) {
			new WSGetInvoice().execute();
		} else {
			Toast.makeText(context, "Network not found", Toast.LENGTH_SHORT).show();
		}

	}

	/*--------------get Invoice data from server--------*/
	public void makeDataInvoice() {
		if (lstInvoice != null)
			lstInvoice.clear();
		try {
			JSONObject json = new JSONObject();
			// ---------------get String ------------------------//
			URLGetAllInvoice = ConfigurationServer.getURLServer() + "wsgetallinvoice.php";
			json.put("totalItemCount", "1");
			json.put("number_pager", "2");
			json.put("user_id", MainPosActivity.user.getUser_id());
			json.put("company_code", MainPosActivity.user.getCompanyCode());
					JSONArray arrInvoice = mWS.connectWSPut_Get_Data(URLGetAllInvoice, json, "invoice");
			Log.e("............>>", "arrInvoice =" + arrInvoice);
			for (int i = 0; i < arrInvoice.length(); i++) {
				JSONObject results = arrInvoice.getJSONObject(i);
				Invoice invoice = new Invoice();
				invoice.setInv_id(results.getInt("inv_id"));
				invoice.setInv_code(results.getString("inv_code"));
				invoice.setTotal(Float.parseFloat(results.getString("total")));
				invoice.setCost(Float.parseFloat(results.getString("cost")));
				invoice.setVat(Integer.parseInt(results.getString("vat")));
				invoice.setCommision(Integer.parseInt(results.getString("commision")));
				invoice.setInv_starttime(results.getString("inv_endtime"));
				invoice.setInv_endtime(results.getString("inv_starttime"));
				invoice.setUser_id(results.getInt("user_id"));
				invoice.setStatus(results.getInt("status"));
				ArrayList<String> listCodeTable = new ArrayList<String>();
				JSONArray codeTableArray = results.getJSONArray("code_table");
				for(int j = 0; j < codeTableArray.length(); j++){
					listCodeTable.add(codeTableArray.get(j).toString());
				}
				invoice.setLstCodeTables(listCodeTable);
				lstInvoice.add(invoice);
			}
			Log.i("DLINV", "" + lstInvoice.size());
		} catch (Exception e) {
			Log.i("Log : ", "Exception : " + e.getMessage());
		}
	}

	/*--------------get category data from server--------*/
	public void makeDatacategory() {
		if (lstcategory != null)
			lstcategory.clear();
		try {
			// ---------------get String ------------------------//
			URLGetAllCategories = ConfigurationServer.getURLServer() + "wsgetallcategories.php";
			JSONObject json = new JSONObject();
			json.put("language_code", ConfigurationServer.language_code);
			json.put("user_id", MainPosActivity.user.getUser_id());
			json.put("company_code", MainPosActivity.user.getCompanyCode());
			JSONArray arrItem = mWS.connectWSPut_Get_Data(URLGetAllCategories, json, "product");
			for (int i = 0; i < arrItem.length(); i++) {
				JSONObject results = arrItem.getJSONObject(i);
				Log.i("Log CATEGORY : ", "Results ---------------  : " + results);

				Category cate = new Category();
				cate.setCategory_id(results.getInt("category_id"));
				cate.setName(results.getString("name"));
				cate.setDescription(results.getString("description"));
				cate.setCreate_time(results.getString("create_time"));
				cate.setUpdate_time(results.getString("update_time"));
				cate.setFlag(results.getInt("flag"));
				cate.setImgname(results.getString("imgName"));
				cate.setParent_id(results.getInt("parent_id"));

				lstcategory.add(cate);

			}
		} catch (Exception e) {
			Log.i("Log333 : ", "Exception : " + e.getMessage());
		}
	}

	// ---------------get category by parent_id---------//
	public ArrayList<Category> getCategoryByParent(int parent_id) {

		ArrayList<Category> arrCate = new ArrayList<Category>();
		for (Category category : lstcategory) {
			if (category.getParent_id() == parent_id) {
				arrCate.add(category);
			}
		}
		return arrCate;
	}

	/* --------------get Items data from server-------- */
	public void makeDataItems(int category_id) {
		if (lstItems != null)
			lstItems.clear();
		try {
			// ---------------get String ------------------------//
			URLGetAllItems = ConfigurationServer.getURLServer() + "wsgetallitems.php";
			JSONObject json = new JSONObject();
			json.put("cate_id", category_id);
			json.put("language_code", ConfigurationServer.language_code);
			json.put("user_id", MainPosActivity.user.getUser_id());
			json.put("company_code", MainPosActivity.user.getCompanyCode());
			
			JSONArray arrItem = mWS.connectWSPut_Get_Data(URLGetAllItems, json, "items");

			Log.i("Log : ", "Ok");
			for (int i = 0; i < arrItem.length(); i++) {
				JSONObject results = arrItem.getJSONObject(i);
				Log.i("Log : ", "Results : " + results);

				Items item = new Items();
				item.setItem_id(results.getInt("item_id"));
				item.setName(results.getString("name"));
				item.setDescription(results.getString("description"));
				item.setCategory_id(results.getInt("category_id"));
				item.setCreate_time(results.getString("create_time"));
				item.setUpdate_time(results.getString("update_time"));
				item.setFlag(results.getInt("flag"));
				// item.setQuantityItem(results.getInt("quantity"));
				item.setPrice(Float.parseFloat(results.getString("price")));
				item.setImgName(results.getString("ImgName"));

				lstItems.add(item);
			}

		} catch (Exception e) {
			Log.i("Log : ", "Exception : " + e.getMessage());
		}
	}

	/*--------------get (An Invoice detail) from server--------*/
	public void makeAnInvDetail(String inv_code) {
		if (lstAnInvDetail != null)
			lstAnInvDetail.clear();
		try {
			// ---------------get String ------------------------//
			URLGetAllInvdetail = ConfigurationServer.getURLServer() + "wsgetanallinvdetail.php";

			JSONObject json = new JSONObject();
			json.put("inv_code", inv_code);
			json.put("language_code", ConfigurationServer.language_code);
			json.put("user_id", MainPosActivity.user.getUser_id());
			json.put("company_code", MainPosActivity.user.getCompanyCode());
			
			JSONArray arrItem = mWS.connectWSPut_Get_Data(URLGetAllInvdetail, json, "aninvdetail");

			for (int i = 0; i < arrItem.length(); i++) {
				JSONObject results = arrItem.getJSONObject(i);

				Log.i("Log : ", "Results : " + results);
				Invoice_Detail inv_detail = new Invoice_Detail();
				inv_detail.setId(results.getInt("id"));
				inv_detail.setInv_code(results.getString("inv_code"));
				inv_detail.setStart_date(results.getString("start_date"));
				inv_detail.setEnd_date(results.getString("end_date"));
				inv_detail.setName(results.getString("name"));
				inv_detail.setDescription(results.getString("description"));
				inv_detail.setQuantity(results.getInt("quantity"));
				inv_detail.setPrice(Float.parseFloat(results.getString("price")));
				inv_detail.setItem_id(results.getInt("item_id"));
				inv_detail.setImgName(results.getString("imagename"));
				inv_detail.setComment(results.getString("comment"));
				inv_detail.setChecked(results.getInt("checked"));
				inv_detail.setStart_date(results.getString("invd_createtime"));
				inv_detail.setEnd_date(results.getString("invd_updatetime"));
				lstAnInvDetail.add(inv_detail);
			}
		} catch (Exception e) {
			Log.i("Log : ", "Exception : " + e.getMessage());
		}
	}

	/*--------------get (Invoice detail) from server--------1*/
	public void makeDataInvDetail() {
		if (lstInvDetail != null)
			lstInvDetail.clear();
		try {
			// ---------------get String ------------------------//
			URLGetAllInvdetail = ConfigurationServer.getURLServer() + "wsgetallinvdetail.php";

			JSONObject json = new JSONObject();
			json.put("cate_id", 0);
			json.put("language_code", ConfigurationServer.language_code);
			json.put("user_id", MainPosActivity.user.getUser_id());
			json.put("company_code", MainPosActivity.user.getCompanyCode());
			
			JSONArray arrItem = mWS.connectWSPut_Get_Data(URLGetAllInvdetail, json, "invoicedetails");
			for (int i = 0; i < arrItem.length(); i++) {
				JSONObject results = arrItem.getJSONObject(i);
				Log.e(">>>>", ">>>results = " + results);
				Invoice_Detail inv_detail = new Invoice_Detail();
				inv_detail.setInv_code(results.getString("inv_code"));
				inv_detail.setId(results.getInt("id"));
				inv_detail.setStart_date(results.getString("start_date"));
				inv_detail.setEnd_date(results.getString("end_date"));
				inv_detail.setName(results.getString("name"));
				inv_detail.setDescription(results.getString("description"));
				inv_detail.setQuantity(results.getInt("quantity"));
				inv_detail.setPrice(Float.parseFloat(results.getString("price")));
				inv_detail.setItem_id(results.getInt("item_id"));
				//inv_detail.setChecked(Integer.parseInt(results.getString("checked")));
				try {
					inv_detail.setImgName(results.getString("imagename"));
				} catch (Exception e) {
				}
				inv_detail.setComment(results.getString("comment"));
				lstInvDetail.add(inv_detail);
			}
		} catch (Exception e) {
			Toast.makeText(context, "Connecting to server failed",
					Toast.LENGTH_SHORT).show();
		}
	}

	// ==================ham tinh tong cac phan tu trong invoice==============//
	private List<Invoice_Detail> getAllSortInvDetail(List<Invoice_Detail> lst) {

		List<Invoice_Detail> lstTmp = new ArrayList<Invoice_Detail>();
		for (Invoice IN : lstInvoice) {
			lstTmp.addAll(totalInvDetail(getLstInvDetailByINV(IN.getInv_code())));
		}
		return lstTmp;
	}

	// ===============return a list invdetail by inv_code================//
	private List<Invoice_Detail> getLstInvDetailByINV(String inv_code) {
		List<Invoice_Detail> lstInvDetailTmp = new ArrayList<Invoice_Detail>();
		for (Invoice_Detail invDetail : lstInvDetail) {
			if (invDetail.getInv_code().equals(inv_code)) {
				lstInvDetailTmp.add(invDetail);
			}
		}
		return lstInvDetailTmp;
	}

	/* ------------add new inv_detail to bean--------------------------- */
	public void addListInvoiceDetail(ArrayList<Invoice_Detail> lstInvDetailTmpl) {
		for (Invoice_Detail invDetail : lstInvDetailTmpl) {
			this.lstInvDetail.add(invDetail);
		}
	}

	// ============ function to sum quantity of inv_detail======//
	public List<Invoice_Detail> totalInvDetail(List<Invoice_Detail> arr) {
		List<Invoice_Detail> arrtmp = new ArrayList<Invoice_Detail>();

		for (Invoice_Detail invoice_Detail : arr) {
			if (!checkinvDetail(invoice_Detail, arrtmp)) {
				arrtmp.add(invoice_Detail);
			}
		}
		return arrtmp;
	}

	// ============= checking inv_detail in lstinvdetail==========//
	private boolean checkinvDetail(Invoice_Detail inv,
			List<Invoice_Detail> lstINV) {
		for (Invoice_Detail invoice_Detail : lstINV) {
			if (inv.getItem_id() == invoice_Detail.getItem_id()) {
				// -------ghep chuoi-------------------------------------//
				invoice_Detail.setLstOrder(invoice_Detail.getLstOrder() + String.valueOf(inv.getQuantity() + "-"));
				invoice_Detail.setQuantity(invoice_Detail.getQuantity() + inv.getQuantity());
				return true;
			}
		}
		return false;
	}

	public void addListInvoiceDetailNEW(ArrayList<Invoice_Detail> lstInvDetailTmpl, String inv_code) {
		for (Invoice_Detail inv_detail : lstInvDetailTmpl) {
			inv_detail.setInv_code(inv_code);
		}
		lstInvDetail.addAll(lstInvDetailTmpl);
		lstInvDetail = getAllSortInvDetail(lstInvDetail);
	}

	/*------------add lst inv_detail to bean-------------------------*/
	public void addListInvoiceDetail666666666666666(
			ArrayList<Invoice_Detail> lstInvDetailTmpl, String inv_code) {

		// --------------------get list inv_detail with inv_code----------//
		for (Invoice_Detail inv_detail : lstInvDetailTmpl) {
			inv_detail.setInv_code(inv_code);
		}

		boolean check = false;
		int j_j = 0;
		int i_i = 0;
		for (int i = 0; i < lstInvDetailTmpl.size(); i++) {
			for (int j = 0; j < lstInvDetail.size(); j++) {
				if (lstInvDetail.get(j).getInv_code() != null && lstInvDetail.get(j).getInv_code().equals(inv_code)) {
					if (lstInvDetail.get(j).getItem_id() == lstInvDetailTmpl.get(i).getItem_id()) {
						check = true;
						j_j = j;
						i_i = i;
						break;
					} else {
						check = false;
					}
				}
			}
			if (check) {
				lstInvDetail.get(j_j).setQuantity(lstInvDetailTmpl.get(i_i).getQuantity() + lstInvDetail.get(j_j).getQuantity());
			} else {
				lstInvDetail.add(lstInvDetailTmpl.get(i));
			}
		}
	}

	/*------------return inv_detail with inv_code----------------2*/
	public ArrayList<Invoice_Detail> getlstInvDetail(String inv_code) {

		ArrayList<Invoice_Detail> arrtemp = new ArrayList<Invoice_Detail>();
		for (int i = 0; i < lstInvDetail.size(); i++) {
			try {
				if ((lstInvDetail.get(i)).getInv_code().equals(inv_code)) {
					Invoice_Detail inv = new Invoice_Detail();
					inv.setCategory_id((lstInvDetail.get(i)).getCategory_id());
					inv.setChecked((lstInvDetail.get(i)).getChecked());
					inv.setComment((lstInvDetail.get(i)).getComment());
					inv.setDescription((lstInvDetail.get(i)).getDescription());
					inv.setEnd_date((lstInvDetail.get(i)).getEnd_date());
					inv.setFlag((lstInvDetail.get(i)).getFlag());
					inv.setId((lstInvDetail.get(i)).getId());
					inv.setImgName((lstInvDetail.get(i)).getImgName());
					inv.setInputAmounFloat((lstInvDetail.get(i)).getInputAmounFloat());
					inv.setInv_code((lstInvDetail.get(i)).getInv_code());
					inv.setItem_id((lstInvDetail.get(i)).getItem_id());
					inv.setLstOrder((lstInvDetail.get(i)).getLstOrder());
					inv.setName((lstInvDetail.get(i)).getName());
					inv.setPrice((lstInvDetail.get(i)).getPrice());
					inv.setQuantity((lstInvDetail.get(i)).getQuantity());
					inv.setStart_date((lstInvDetail.get(i)).getStart_date());
					arrtemp.add(inv);
				}
			} catch (Exception e) {
			}
		}
		return getLstInvDetailSort1(arrtemp);
	}

	// -------------gep chuoi so lan gọi--------------//
	public ArrayList<Invoice_Detail> getLstInvDetailSort1(
			ArrayList<Invoice_Detail> lstInvDetail) {
		ArrayList<Invoice_Detail> arrtemp1 = new ArrayList<Invoice_Detail>();
		boolean ok = false;
		for (int i = 0; i < lstInvDetail.size(); i++) {
			for (int j = 0; j < arrtemp1.size(); j++) {
				if (arrtemp1.get(j).getItem_id() == lstInvDetail.get(i).getItem_id()) {
					arrtemp1.get(j).setLstOrder(arrtemp1.get(j).getLstOrder() + String.valueOf(lstInvDetail.get(i).getQuantity() + ","));
					arrtemp1.get(j).setQuantity(arrtemp1.get(j).getQuantity() + lstInvDetail.get(i).getQuantity());
					//---------doan nay la lay ra id của inv_detail lon nhat----//
					if(arrtemp1.get(j).getId()<lstInvDetail.get(i).getId()){
						arrtemp1.get(j).setId(lstInvDetail.get(i).getId());
					}
					ok = true;
					break;
				}
			}
			if (!ok) {
				arrtemp1.add(lstInvDetail.get(i));
			}
			ok = false;
		}
		return arrtemp1;
	}

	// ----------Threading make data invoice-------//
	private class WSGetInvoice extends AsyncTask<Void, Void, Void> {

		private ProgressDialog progressDialog;

		public WSGetInvoice() {
			progressDialog = new ProgressDialog(context);
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
				makeDataInvoice();
			} catch (Exception e) {
				Log.i("Log : ", "Exception : " + e.getMessage());
				progressDialog.dismiss();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (progressDialog != null)
				progressDialog.dismiss();
		}
	}

	// ----------Threading load all data from server-------//
	private class MakeBean extends AsyncTask<Void, Void, Void> {
		public MakeBean(Context context) {
			progressMakeBean = new ProgressDialog(context);
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				//makeDataUser();
				//makeDataTableByFloor(4);
				//makeDataInvoice();
				makeDatacategory();
				makeDataItems(1);
				// makeDataInvDetail();

			} catch (Exception e) {
				Log.i("Log : ", "Exception : " + e.getMessage());
				progressMakeBean.dismiss();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			if (progressMakeBean != null)
				progressMakeBean.dismiss();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressMakeBean.setMessage("Loading...");
			progressMakeBean.setCancelable(false);
			progressMakeBean.show();
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}
	}

	public List<Invoice> getLstInvoice() {
		return lstInvoice;
	}
}
