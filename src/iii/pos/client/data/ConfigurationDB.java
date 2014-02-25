package iii.pos.client.data;

import iii.pos.client.R;
import iii.pos.client.model.Category;
import iii.pos.client.model.Invoice;
import iii.pos.client.model.Invoice_Detail;
import iii.pos.client.model.Items;
import iii.pos.client.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class ConfigurationDB {

	private static final String DATABASE_NAME = "POSIII.db";
	private SQLiteDatabase mDB;
	private DatabaseHelper myDataHelPer;
	private static final int DATABASE_VERSION = 1;
	private Context mcontext;

	// 1-----------------------Table Category-------------------//

	private static String Cate_category_id = "category_id";
	private static String Cate_Name = "name";
	private static String Cate_description = "description";
	private static String Cate_create_time = "create_time";
	private static String Cate_update_time = "update_time";
	private static String Cate_flag = "flag";

	private static final String CATE_DATABASE_TABLE_CATE = "category";

	private static final String CATE_DATABASE_CREATE_CATE = "CREATE TABLE ["
			+ CATE_DATABASE_TABLE_CATE + "]([" + Cate_category_id
			+ "] INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, [" + Cate_Name
			+ "] TEXT NOT NULL,[" + Cate_description + "] TEXT NOT NULL , ["
			+ Cate_create_time + "] DATETIME NOT NULL, [" + Cate_update_time
			+ "] DATETIME NOT NULL, [" + Cate_flag + "] INTEGER NULL);";

	// 2-----------------------Table Client-------------------//

	private static String Client_client_id = "client_id";
	private static String Client_Name = "name";
	private static String Client_phone = "phone";
	private static String Client_address = "address";
	private static String Client_itable_id = "itable_id";

	private static final String CLIENT_DATABASE_TABLE_CLIENT = "client";
	private static final String CLIENT_DATABASE_CREATE_CLIENT = "CREATE TABLE ["
			+ CLIENT_DATABASE_TABLE_CLIENT
			+ "](["
			+ Client_client_id
			+ "] INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ["
			+ Client_Name
			+ "] TEXT NOT NULL, ["
			+ Client_phone
			+ "] TEXT NOT NULL, ["
			+ Client_address
			+ "] TEXT NOT NULL , ["
			+ Client_itable_id
			+ "] INTEGER NOT NULL);";

	// 3-----------------------Table Comment-------------------//

	private static String Comment_comment_id = "comment_id";
	private static String Comment_Name = "name";
	private static String Comment_value = "value";
	private static String Comment_create_time = "create_time";
	private static String Comment_update_time = "update_time";
	private static String Comment_flag = "flag";

	private static final String COMMENT_DATABASE_TABLE_COMMENT = "comment";
	private static final String COMMENT_DATABASE_CREATE_COMMENT = "CREATE TABLE ["
			+ COMMENT_DATABASE_TABLE_COMMENT
			+ "](["
			+ Comment_comment_id
			+ "] INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ["
			+ Comment_Name
			+ "] TEXT NOT NULL, ["
			+ Comment_value
			+ "] TEXT NOT NULL,["
			+ Comment_create_time
			+ "] DATETIME NOT NULL, ["
			+ Comment_update_time
			+ "] DATETIME NOT NULL, ["
			+ Comment_flag
			+ "] INTEGER NOT NULL);";

	// 4-----------------------Table Group-------------------//

	private static String Group_group_id = "group_id";
	private static String Group_group_name = "group_name";
	private static String Group_update_time = "update_time";
	private static String Group_create_time = "create_time";
	private static String Group_flag = "flag";
	private static String Group_description = "description";

	private static final String GROUP_DATABASE_TABLE_GROUP = "group";
	private static final String GROUP_DATABASE_CREATE_GROUP = "CREATE TABLE ["
			+ GROUP_DATABASE_TABLE_GROUP + "]([" + Group_group_id
			+ "] INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ["
			+ Group_group_name + "] varchar(200) NOT NULL, ["
			+ Group_update_time + "] DATETIME NULL , [" + Group_create_time
			+ "] DATETIME NULL , [" + Group_flag + "] INTEGER NOT NULL, ["
			+ Group_description + "] TEXT NOT NULL);";

	// 5-----------------------Table Invoice-------------------//

	private static String Inv_inv_id = "inv_id";
	private static String Inv_inv_code = "inv_code";
	private static String Inv_total = "total";
	private static String Inv_cost = "cost";
	private static String Inv_vat = "vat";
	private static String Inv_commision = "commision";
	private static String Inv_inv_endtime = "inv_endtime";
	private static String Inv_inv_starttime = "inv_starttime";
	private static String Inv_user_id = "user_id";
	private static String Inv_status = "status";

	private static final String INVOICE_DATABASE_TABLE_INVOICE = "invoice";
	private static final String INVOICE_DATABASE_CREATE_INVOICE = "CREATE TABLE ["
			+ INVOICE_DATABASE_TABLE_INVOICE
			+ "](["
			+ Inv_inv_id
			+ "] INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,["
			+ Inv_inv_code
			+ "] varchar(20) NOT NULL, ["
			+ Inv_total
			+ "] float NULL, ["
			+ Inv_cost
			+ "] float NULL,["
			+ Inv_vat
			+ "] float NULL, ["
			+ Inv_commision
			+ "] float NULL, ["
			+ Inv_inv_endtime
			+ "] datetime NULL, ["
			+ Inv_inv_starttime
			+ "] datetime NOT NULL, ["
			+ Inv_user_id
			+ "] INTEGER NOT NULL, ["
			+ Inv_status + "] INTEGER NOT NULL);";

	// 6-----------------------Table Invoice_Detail-------------------//

	private static String Inv_Detail_inv_id = "id";
	private static String Inv_Detail_inv_id_item = "item_id";
	private static String Inv_Detail_inv_code = "inv_code";
	private static String Inv_Detail_inv_quantity = "quantity";
	private static String Inv_Detail_flag = "flag";

	private static final String INVOICE_DETAIL_DATABASE_TABLE_INVOICE_DETAIL = "invoice_detail";
	private static final String INVOICE_DETAIL_DATABASE_CREATE_INVOICE_DETAIL = "CREATE TABLE ["
			+ INVOICE_DETAIL_DATABASE_TABLE_INVOICE_DETAIL
			+ "](["
			+ Inv_Detail_inv_id
			+ "] INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,["
			+ Inv_Detail_inv_id_item
			+ "] INTEGER NOT NULL, ["
			+ Inv_Detail_inv_code
			+ "] VARCHAR(50) NOT NULL,["
			+ Inv_Detail_inv_quantity
			+ "] INTEGER NOT NULL,["
			+ Inv_Detail_flag + "] INTEGER NOT NULL);";

	// 7-----------------------Table ITable-------------------//

	private static String Itable_itable_id = "itable_id";
	private static String Itable_code_table = "code_table";
	private static String Itable_description_table = "description_table";
	private static String Itable_create_time = "create_time";
	private static String Itable_update_time = "update_date";
	private static String Itable_flag = "flag";
	private static String Itable_status = "status";
	private static String Itable_pos_x = "pos_x";
	private static String Itable_pos_y = "pos_y";

	private static final String ITABLE_DATABASE_TABLE_ITABLE = "itable";
	private static final String ITABLE_DATABASE_CREATE_ITABLE = "CREATE TABLE ["
			+ ITABLE_DATABASE_TABLE_ITABLE
			+ "](["
			+ Itable_itable_id
			+ "] INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ["
			+ Itable_code_table
			+ "] TEXT NOT NULL, ["
			+ Itable_description_table
			+ "] TEXT NOT NULL, ["
			+ Itable_create_time
			+ "] datetime NOT NULL, ["
			+ Itable_update_time
			+ "] datetime NULL, ["
			+ Itable_flag
			+ "] INTEGER NOT NULL, ["
			+ Itable_status
			+ "] INTEGER NOT NULL, ["
			+ Itable_pos_x
			+ "] INTEGER NOT NULL, ["
			+ Itable_pos_y
			+ "] INTEGER NOT NULL);";

	private static String Item_item_id = "item_id";
	private static String Item_name = "name";
	private static String Item_description = "description";
	private static String Item_category_id = "category_id";
	private static String Item_create_time = "create_time";
	private static String Item_update_time = "update_date";
	private static String Item_flag = "flag";

	private static final String ITEMS_DATABASE_TABLE_ITEMS = "items";
	private static final String ITEMS_DATABASE_CREATE_ITEMS = "CREATE TABLE ["
			+ ITEMS_DATABASE_TABLE_ITEMS + "]([" + Item_item_id
			+ "] INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, [" + Item_name
			+ "] TEXT NOT NULL, [" + Item_description + "] TEXT NOT NULL, ["
			+ Item_category_id + "] INTEGER NOT NULL, [" + Item_create_time
			+ "] TEXT NOT NULL, [" + Item_update_time + "] TEXT NOT NULL, ["
			+ Item_flag + "] INTEGER NOT NULL);";

	// 10-----------------------Table Items_Cost_Detail-------------------//

	private static String Item_cost_id = "items_cost_id";
	private static String Item_cost_price = "price";
	private static String Item_cost_flag = "flag";

	private static final String ITEMS_COST_DATABASE_TABLE_ITEMS_COST = "items_cost_detail";
	private static final String ITEMS_COST_DATABASE_CREATE_ITEMS_COST = "CREATE TABLE ["
			+ ITEMS_COST_DATABASE_TABLE_ITEMS_COST
			+ "](["
			+ Item_cost_id
			+ "] INTEGER NOT NULL,["
			+ Item_cost_price
			+ "] float NOT NULL, ["
			+ Item_cost_flag + "] INTEGER NOT NULL);";

	// 11 -----------------------Table Items_Extra-------------------//

	private static String Item_Extra_id = "items_extra_id";
	private static String Item_Extra_items_id = "items_id";
	private static String Item_Extra_name = "name";
	private static String Item_Extra_value = "value";
	private static String Item_Extra_create_time = "create_time";
	private static String Item_Extra_update_time = "update_date";
	private static String Item_Extra_flag = "flag";

	private static final String ITEMS_EXTRA_DATABASE_TABLE_ITEMS_EXTRA = "items_extra";
	private static final String ITEMS_EXTRA_DATABASE_CREATE_ITEMS_EXTRA = "CREATE TABLE ["
			+ ITEMS_EXTRA_DATABASE_TABLE_ITEMS_EXTRA
			+ "](["
			+ Item_Extra_id
			+ "] INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ["
			+ Item_Extra_items_id
			+ "] INTEGER NOT NULL, ["
			+ Item_Extra_name
			+ "] TEXT NOT NULL, ["
			+ Item_Extra_value
			+ "] TEXT NOT NULL, ["
			+ Item_Extra_create_time
			+ "] datetime NOT NULL, ["
			+ Item_Extra_update_time
			+ "] datetime NOT NULL, ["
			+ Item_Extra_flag + "] INTEGER NOT NULL);";

	// -----------------------Table item_cost-------------------//

	private static String Item_cost_id_1 = "item_cost_id";
	private static String Item_cost_Item_id_1 = "item_id";
	private static String Item_Cost_date_start_1 = "start_date";
	private static String Item_Cost_date_end_1 = "end_date";

	private static final String ITEMS_COST_DATABASE_TABLE_ITEMS_COST_1 = "item_cost";
	private static final String ITEMS_COST_DATABASE_CREATE_ITEMS_COST_1 = "CREATE TABLE ["
			+ ITEMS_COST_DATABASE_TABLE_ITEMS_COST_1
			+ "](["
			+ Item_cost_id_1
			+ "] INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ["
			+ Item_cost_Item_id_1
			+ "] INTEGER NOT NULL, ["
			+ Item_Cost_date_start_1
			+ "] TEXT NOT NULL, ["
			+ Item_Cost_date_end_1 + "] TEXT NOT NULL);";

	// 12-----------------------Table Media-------------------//

	private static String Media_media_id = "media_id";
	private static String Media_name = "name";
	private static String Media_size = "size";
	private static String Media_type = "type";
	private static String Media_create_time = "create_time";
	private static String Media_update_time = "update_date";
	private static String Media_flag = "flag";

	private static final String MEDIA_DATABASE_TABLE_MEDIA = "media";
	private static final String MEDIA_DATABASE_CREATE_MEDIA = "CREATE TABLE ["
			+ MEDIA_DATABASE_TABLE_MEDIA + "]([" + Media_media_id
			+ "] INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, [" + Media_name
			+ "] TEXT NOT NULL, [" + Media_size + "] INTEGER NOT NULL, ["
			+ Media_type + "] TEXT NOT NULL, [" + Media_create_time
			+ "] TEXT NULL, [" + Media_update_time + "] TEXT NULL, ["
			+ Media_flag + "] INTEGER NOT NULL);";

	// 13-----------------------Table Media_Category-------------------//

	private static String Media_Cate_media_category_id = "media_category_id";
	private static String Media_Cate_media_id = "media_id";
	private static String Media_Cate_category_id = "category_id";

	private static final String MEDIA_CATEGORY_DATABASE_TABLE_MEDIA_CATEGORY = "media_category";
	private static final String MEDIA_CATEGORY_DATABASE_CREATE_MEDIA_CATEGORY = "CREATE TABLE ["
			+ MEDIA_CATEGORY_DATABASE_TABLE_MEDIA_CATEGORY
			+ "](["
			+ Media_Cate_media_category_id
			+ "] INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ["
			+ Media_Cate_media_id
			+ "] INTEGER NOT NULL, ["
			+ Media_Cate_category_id + "] INTEGER NOT NULL);";

	// 14-----------------------Table Media_Items-------------------//

	private static String Media_Item_media_items_id = "media_items_id";
	private static String Media_Item_media_id = "media_id";
	private static String Media_Item_items_id = "items_id";

	private static final String MEDIA_ITEM_DATABASE_TABLE_MEDIA_ITEM = "media_items";
	private static final String MEDIA_ITEM_DATABASE_CREATE_MEDIA_ITEM = "CREATE TABLE ["
			+ MEDIA_ITEM_DATABASE_TABLE_MEDIA_ITEM
			+ "](["
			+ Media_Item_media_items_id
			+ "] INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ["
			+ Media_Item_media_id
			+ "] INTEGER NOT NULL, ["
			+ Media_Item_items_id + "] INTEGER NOT NULL);";

	// 15 -----------------------Table Unit-------------------//

	private static String Unit_unit_id = "unit_id";
	private static String Unit_name = "name";
	private static String Unit_create_time = "create_time";
	private static String Unit_update_time = "update_date";
	private static String Unit_flag = "flag";

	private static final String UNIT_DATABASE_TABLE_UNIT = "unit";
	private static final String UNIT_DATABASE_CREATE_UNIT = "CREATE TABLE ["
			+ UNIT_DATABASE_TABLE_UNIT + "]([" + Unit_unit_id
			+ "] INTEGER NULL, [" + Unit_name + "] VARCHAR(100)  NULL, ["
			+ Unit_create_time + "] datetime  NULL, [" + Unit_update_time
			+ "] datetime  NULL, [" + Unit_flag + "] INTEGER  NULL);";

	// 16-----------------------Table Users-------------------//

	private static String User_user_id = "user_id";
	private static String User_user_name = "user_name";
	private static String User_password = "password";
	private static String User_sex = "sex";
	private static String User_age = "age";
	private static String User_email = "email";
	private static String User_address = "address";
	private static String User_create_time = "create_time";
	private static String User_update_time = "update_date";
	private static String User_flag = "flag";

	private static final String USER_DATABASE_TABLE_USER = "users";
	private static final String USER_DATABASE_CREATE_USER = "CREATE TABLE ["
			+ USER_DATABASE_TABLE_USER + "]([" + User_user_id
			+ "] INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ["
			+ User_user_name + "] TEXT NOT NULL, [" + User_password
			+ "] TEXT NOT NULL, [" + User_sex + "] INTEGER NOT NULL, ["
			+ User_age + "] INTEGER NOT NULL, [" + User_email
			+ "] varchar(50) NOT NULL, [" + User_address + "] TEXT NOT NULL, ["
			+ User_create_time + "] datetime NULL, [" + User_update_time
			+ "] datetime NULL, [" + User_flag + "] INTEGER NOT NULL);";

	// 17-----------------------Table User_Group-------------------//

	private static String User_Group_user_group_id = "user_group_id";
	private static String User_Group_user_id = "user_id";
	private static String User_Group_group_id = "group_id";

	private static final String USER_GROUP_DATABASE_TABLE_USER_GROUP = "user_group";
	private static final String USER_GROUP_DATABASE_CREATE_USER_GROUP = "CREATE TABLE ["
			+ USER_GROUP_DATABASE_TABLE_USER_GROUP
			+ "](["
			+ User_Group_user_group_id
			+ "] INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ["
			+ User_Group_user_id
			+ "] INTEGER NOT NULL, ["
			+ User_Group_group_id + "] INTEGER NOT NULL);";

	// 18-----------------------Table action-------------------//

	private static String Action_action_id = "action_id";
	private static String Action_user_group_id = "user_group_id";
	private static String Action_items_id = "items_id";
	private static String Action_value = "value";

	private static final String ACTION_DATABASE_TABLE_ACTION = "action";
	private static final String ACTION_DATABASE_CREATE_ACTION = "CREATE TABLE ["
			+ ACTION_DATABASE_TABLE_ACTION
			+ "](["
			+ Action_action_id
			+ "] INTEGER NOT NULL, ["
			+ Action_user_group_id
			+ "] INTEGER NULL, ["
			+ Action_items_id
			+ "] INTEGER NULL, ["
			+ Action_value + "] TEXT NULL);";

	// 19-----------------------Table stuff_invoice-------------------//

	private static String Stuff_Inv_stuffinvoice_id = "stuffinvoice_id";
	private static String Stuff_Inv_supplier_id = "supplier_id";
	private static String Stuff_Inv_total = "total";
	private static String Stuff_Inv_vat = "vat";
	private static String Stuff_Inv_commission = "commission";
	private static String Stuff_Inv_inv_startdate = "inv_startdate";
	private static String Stuff_Inv_inv_enddate = "inv_enddate";
	private static String Stuff_Inv_user_id = "user_id";
	private static String Stuff_Inv_stuffinvoice_code = "stuffinvoice_code";

	private static final String STUFF_INV_DATABASE_TABLE_STUFF_INV = "stuff_invoice";
	private static final String STUFF_INV_DATABASE_CREATE_STUFF_INV = "CREATE TABLE ["
			+ STUFF_INV_DATABASE_TABLE_STUFF_INV
			+ "](["
			+ Stuff_Inv_stuffinvoice_id
			+ "] INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ["
			+ Stuff_Inv_supplier_id
			+ "] INTEGER NOT NULL, ["
			+ Stuff_Inv_total
			+ "] float NOT NULL, ["
			+ Stuff_Inv_vat
			+ "] float NULL, ["
			+ Stuff_Inv_commission
			+ "] float NULL, ["
			+ Stuff_Inv_inv_startdate
			+ "] datetime NULL, ["
			+ Stuff_Inv_inv_enddate
			+ "] datetime NULL, ["
			+ Stuff_Inv_user_id
			+ "] INTEGER NULL, ["
			+ Stuff_Inv_stuffinvoice_code
			+ "] nvarchar(50) NULL);";

	// 20-----------------------Table stuff_invoice_details-------------------//

	private static String Stuff_Inv_Detail_stuffinvoice_details_id = "stuffinvoice_details_id";
	private static String Stuff_Inv_Detail_stuffinvoice_id = "stuffinvoice_id";
	private static String Stuff_Inv_Detail_create_time = "create_date";
	private static String Stuff_Inv_Detail_update_time = "update_date";
	private static String Stuff_Inv_Detail_flag = "flag";

	private static final String STUFF_INV_DETAIL_DATABASE_TABLE_STUFF_INV_DETAIL = "stuff_invoice_details";
	private static final String STUFF_INV_DETAIL_DATABASE_CREATE_STUFF_INV_DETAIL = "CREATE TABLE ["
			+ STUFF_INV_DETAIL_DATABASE_TABLE_STUFF_INV_DETAIL
			+ "](["
			+ Stuff_Inv_Detail_stuffinvoice_details_id
			+ "] INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ["
			+ Stuff_Inv_Detail_stuffinvoice_id
			+ "] INTEGER NOT NULL, ["
			+ Stuff_Inv_Detail_create_time
			+ "] datetime NULL, ["
			+ Stuff_Inv_Detail_update_time
			+ "] datetime NULL, ["
			+ Stuff_Inv_Detail_flag + "] INTEGER NULL);";

	// 21 - Tổng bảng trong SQL 84 -----------------------Table
	// supplier-------------------//

	private static String Supplier_supplier_id = "supplier_id";
	private static String Supplier_name = "name";
	private static String Supplier_address = "address";
	private static String Supplier_phone = "phone";
	private static String Supplier_description = "description";

	private static final String SUPPLIER_DATABASE_TABLE_SUPPLIER = "supplier";
	private static final String SUPPLIER_DATABASE_CREATE_SUPPLIER = "CREATE TABLE ["
			+ SUPPLIER_DATABASE_TABLE_SUPPLIER
			+ "](["
			+ Supplier_supplier_id
			+ "] INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ["
			+ Supplier_name
			+ "] TEXT NULL, ["
			+ Supplier_address
			+ "] TEXT NULL, ["
			+ Supplier_phone
			+ "] TEXT NULL, ["
			+ Supplier_description
			+ "] TEXT NULL);";

	private static String Inv_It_ID = "id";
	private static String Inv_Inv = "inv_id";
	private static String Inv_It = "itable_id";

	private static final String INVOICE_ITABLE_DATABASE_TABLE_INVOICE_ITABLE = "invoice_itable";
	private static final String INVOICE_ITABLE_DATABASE_CREATE_INVOICE_ITABLE = "CREATE TABLE ["
			+ INVOICE_ITABLE_DATABASE_TABLE_INVOICE_ITABLE
			+ "](["
			+ Inv_It_ID
			+ "] INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ["
			+ Inv_Inv
			+ "] INTEGER NOT NULL, [" + Inv_It + "] INTEGER NOT NULL);";

	private static class DatabaseHelper extends SQLiteOpenHelper {

		ProgressDialog progress;
		Context context;

		public DatabaseHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, context.getExternalFilesDir(null).getAbsolutePath()
					+ "/" + DATABASE_NAME, null, DATABASE_VERSION);
			// super(context, name, factory, version);
			this.context = context;
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CATE_DATABASE_CREATE_CATE);
			db.execSQL(CLIENT_DATABASE_CREATE_CLIENT);
			db.execSQL(COMMENT_DATABASE_CREATE_COMMENT);
			db.execSQL(GROUP_DATABASE_CREATE_GROUP);
			db.execSQL(INVOICE_DATABASE_CREATE_INVOICE);
			db.execSQL(INVOICE_DETAIL_DATABASE_CREATE_INVOICE_DETAIL);
			db.execSQL(ITABLE_DATABASE_CREATE_ITABLE);
			// db.execSQL(ITABLETEMP_DATABASE_CREATE_ITABLETEMP);
			db.execSQL(ITEMS_DATABASE_CREATE_ITEMS);
			db.execSQL(ITEMS_COST_DATABASE_CREATE_ITEMS_COST);
			db.execSQL(ITEMS_EXTRA_DATABASE_CREATE_ITEMS_EXTRA);
			db.execSQL(ITEMS_COST_DATABASE_CREATE_ITEMS_COST_1);
			db.execSQL(MEDIA_DATABASE_CREATE_MEDIA);
			db.execSQL(MEDIA_CATEGORY_DATABASE_CREATE_MEDIA_CATEGORY);
			db.execSQL(MEDIA_ITEM_DATABASE_CREATE_MEDIA_ITEM);
			db.execSQL(UNIT_DATABASE_CREATE_UNIT);
			db.execSQL(USER_DATABASE_CREATE_USER);
			db.execSQL(USER_GROUP_DATABASE_CREATE_USER_GROUP);
			db.execSQL(ACTION_DATABASE_CREATE_ACTION);
			db.execSQL(STUFF_INV_DATABASE_CREATE_STUFF_INV);
			db.execSQL(STUFF_INV_DETAIL_DATABASE_CREATE_STUFF_INV_DETAIL);
			db.execSQL(SUPPLIER_DATABASE_CREATE_SUPPLIER);
			db.execSQL(INVOICE_ITABLE_DATABASE_CREATE_INVOICE_ITABLE);

			new InsertBg().execute(db);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.i("Log : ", "Database Updating.....");
			db.execSQL("DROP TABLE IF EXISTS " + CATE_DATABASE_TABLE_CATE);
			db.execSQL("DROP TABLE IF EXISTS " + CLIENT_DATABASE_TABLE_CLIENT);
			db.execSQL("DROP TABLE IF EXISTS " + COMMENT_DATABASE_TABLE_COMMENT);
			db.execSQL("DROP TABLE IF EXISTS " + GROUP_DATABASE_TABLE_GROUP);
			db.execSQL("DROP TABLE IF EXISTS " + INVOICE_DATABASE_TABLE_INVOICE);
			db.execSQL("DROP TABLE IF EXISTS "
					+ INVOICE_DETAIL_DATABASE_TABLE_INVOICE_DETAIL);
			db.execSQL("DROP TABLE IF EXISTS " + ITABLE_DATABASE_TABLE_ITABLE);
			// db.execSQL("DROP TABLE IF EXISTS "
			// + ITABLETEMP_DATABASE_TABLE_ITABLETEMP);
			db.execSQL("DROP TABLE IF EXISTS " + ITEMS_DATABASE_TABLE_ITEMS);
			db.execSQL("DROP TABLE IF EXISTS "
					+ ITEMS_COST_DATABASE_TABLE_ITEMS_COST);
			db.execSQL("DROP TABLE IF EXISTS "
					+ ITEMS_EXTRA_DATABASE_TABLE_ITEMS_EXTRA);
			db.execSQL("DROP TABLE IF EXISTS "
					+ ITEMS_COST_DATABASE_TABLE_ITEMS_COST_1);
			db.execSQL("DROP TABLE IF EXISTS " + MEDIA_DATABASE_TABLE_MEDIA);
			db.execSQL("DROP TABLE IF EXISTS "
					+ MEDIA_CATEGORY_DATABASE_TABLE_MEDIA_CATEGORY);
			db.execSQL("DROP TABLE IF EXISTS "
					+ MEDIA_ITEM_DATABASE_TABLE_MEDIA_ITEM);
			db.execSQL("DROP TABLE IF EXISTS " + UNIT_DATABASE_TABLE_UNIT);
			db.execSQL("DROP TABLE IF EXISTS " + USER_DATABASE_TABLE_USER);
			db.execSQL("DROP TABLE IF EXISTS "
					+ USER_GROUP_DATABASE_TABLE_USER_GROUP);
			db.execSQL("DROP TABLE IF EXISTS " + ACTION_DATABASE_TABLE_ACTION);
			db.execSQL("DROP TABLE IF EXISTS "
					+ STUFF_INV_DATABASE_TABLE_STUFF_INV);
			db.execSQL("DROP TABLE IF EXISTS "
					+ STUFF_INV_DETAIL_DATABASE_TABLE_STUFF_INV_DETAIL);
			db.execSQL("DROP TABLE IF EXISTS "
					+ SUPPLIER_DATABASE_TABLE_SUPPLIER);
			db.execSQL("DROP TABLE IF EXISTS "
					+ INVOICE_ITABLE_DATABASE_TABLE_INVOICE_ITABLE);

			onCreate(db);
		}

		int arrID[] = { R.drawable.datacategoryitem };

		private class InsertBg extends
				AsyncTask<SQLiteDatabase, Integer, SQLiteDatabase> {

			@Override
			protected SQLiteDatabase doInBackground(SQLiteDatabase... params) {
				SQLiteDatabase db = params[0];
				Log.d("Log : ", "---------------------------Load Data");
				for (int i = 0; i < arrID.length; i++) {

					InputStream is = context.getResources().openRawResource(
							arrID[i]);
					BufferedReader br = new BufferedReader(
							new InputStreamReader(is));
					String readLine = null;
					int count = 0;
					try {
						while ((readLine = br.readLine()) != null) {
							Log.d("Log : ", "readLine " + count + " : "
									+ readLine);
							db.execSQL(readLine);
							count++;
						}
					} catch (Exception e) {
						Log.i("Log : ", e.getMessage());
						Log.i(this.getClass().getName(), e.getMessage());
					} finally {
						try {
							is.close();
							br.close();

						} catch (IOException e) {
							Log.i(this.getClass().getName(), e.getMessage());
						}
					}

				}

				return db;
			}

			@Override
			protected void onPostExecute(SQLiteDatabase result) {
				super.onPostExecute(result);
				// progress.dismiss();
			}

			@Override
			protected void onProgressUpdate(Integer... values) {
				super.onProgressUpdate(values);
				// progress.setProgress(values[0]);
			}

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				// try {
				// progress = ProgressDialog.show(context, "Loading...",
				// "Please Waiting...");
				// progress.show();
				// } catch (Exception e) {
				// Log.v("Log : ", "Exception : " + e.getMessage());
				// }
			}
		}

	}

	public ConfigurationDB(Context context) {
		this.mcontext = context;
	}

	public ConfigurationDB OpenDB() {
		myDataHelPer = new DatabaseHelper(mcontext, DATABASE_NAME, null,
				DATABASE_VERSION);
		mDB = myDataHelPer.getWritableDatabase();

		return this;
	}

	public void closeDB() {
		mDB.close();
	}

	//
	// // ----------------- Get All Categorry ----------------//
	//
	// public List<Category> getAllCategory() {
	// List<Category> cateList = new ArrayList<Category>();
	//
	// Cursor cursor = mDB.query(CATE_DATABASE_CREATE_CATE, null, null, null,
	// null, null, null);
	// // cursor.moveToFirst();
	// if (cursor.moveToNext()) {
	// Category category = new Category();
	// category.setCtegory_id(cursor.getInt(0));
	// category.setName(cursor.getString(1));
	// category.setMedia_id(cursor.getInt(2));
	// category.setDescription(cursor.getString(3));
	// category.setCreate_time(cursor.getString(4));
	// category.setUpdate_time(cursor.getString(5));
	// category.setFlag(cursor.getInt(6));
	//
	// cateList.add(category);
	//
	// }
	//
	// return cateList;
	// }
	// //
	// // ----------------- Get All Items ----------------//
	//
	// public List<Items> getAllItem(int category_id) {
	// List<Items> itemList = new ArrayList<Items>();
	//
	// Cursor cursor = mDB.query(ITEMS_DATABASE_CREATE_ITEMS, null,
	// Item_category_id + " = '" + category_id + "'", null, null,
	// null, null);
	// // cursor.moveToFirst();
	// if (cursor.moveToNext()) {
	// Items items = new Items();
	//
	// items.setItem_id(cursor.getInt(0));
	// items.setName(cursor.getString(1));
	// items.setDescription(cursor.getString(2));
	// items.setCategory_id(cursor.getInt(3));
	// items.setMedia_id(cursor.getInt(4));
	// items.setPrice(cursor.getFloat(5));
	// items.setCreate_time(cursor.getString(6));
	// items.setUpdate_time(cursor.getString(7));
	// items.setFlag(cursor.getInt(8));
	//
	// itemList.add(items);
	//
	// }
	//
	// return itemList;
	// }

	// // ----------------- Get All Invoice ----------------//
	//
	// public List<Invoice_Detail> getAllInvoice() {
	// List<Invoice_Detail> invoiceList = new ArrayList<Invoice_Detail>();
	//
	// Cursor cursor = mDB.query(INVOICE_DATABASE_CREATE_INVOICE, null, null,
	// null, null, null, null);
	// // cursor.moveToFirst();
	// if (cursor.moveToNext()) {
	// Invoice_Detail invoice = new Invoice_Detail();
	//
	// invoice.setInv_id(cursor.getInt(0));
	// invoice.setTotal(cursor.getFloat(1));
	// invoice.setInv_code(cursor.getString(2));
	// invoice.setVat(cursor.getFloat(3));
	// invoice.setCommision(cursor.getFloat(4));
	// invoice.setStart_date(cursor.getString(5));
	// invoice.setEnd_date(cursor.getString(6));
	// invoice.setUser_id(cursor.getInt(7));
	// invoice.setClient_id(cursor.getInt(8));
	//
	// invoiceList.add(invoice);
	//
	// }
	//
	// return invoiceList;
	// }

	public ArrayList<Category> getCategoryPage() {
		ArrayList<Category> categorylist = new ArrayList<Category>();

		String QUERRY_CATEGORY = "SELECT * FROM category As cate order by cate.category_id ASC ";

		Cursor cursor = mDB.rawQuery(QUERRY_CATEGORY, null);
		if (cursor.getCount() > 0) {
			// cursor.moveToFirst();
			while (cursor.moveToNext()) {
				Category category = new Category();
				int category_id = cursor.getInt(0);
				category.setCategory_id(category_id);
				category.setName(cursor.getString(1));
				// category.setMedia_id(cursor.getInt(2)); IIIPOS
				category.setDescription(cursor.getString(3));
				category.setCreate_time(cursor.getString(4));
				category.setUpdate_time(cursor.getString(5));
				category.setFlag(cursor.getInt(6));
				category.setImgname(getCategoryMedia(category_id));
				categorylist.add(category);
			}
		}

		return categorylist;
	}

	private String getCategoryMedia(int category_id) {
		String SQL = "select name from media where media_id in (select media_id from media_category where category_id = "
				+ category_id + ")";
		String imageName = "";
		Cursor cursor = mDB.rawQuery(SQL, null);
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				imageName = cursor.getString(cursor.getColumnIndex("name"));
			}
		}
		return imageName;
	}

	public ArrayList<Items> getCategoryItem(int category_id) {
		ArrayList<Items> itemlist = new ArrayList<Items>();
		String simpDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String SQL_GETITEM = "select DISTINCT it.item_id, it.name, it.description, it.category_id, it.media_id, it_c.start_date,"
				+ "				 it_c.end_date, it_c_dt.price, "
				+ "				it_c.item_cost_id, it.flag, it_c.item_cost_id  from  items As it  inner join item_cost"
				+ "				it_c on it_c.item_id = it.item_id  inner join items_cost_detail it_c_dt "
				+ "				on it_c_dt.items_cost_id = it_c.item_cost_id  where it.category_id = "
				+ category_id
				+ "				and strftime('%m-%d-%Y', date(it_c.start_date))  < strftime('%m-%d-%Y', date('"
				+ simpDate
				+ "')) "
				+ "				and strftime('%m-%d-%Y', date('"
				+ simpDate
				+ "'))  < strftime('%m-%d-%Y', date(it_c.end_date)) "
				+ "				 order by it.item_id ASC";

		Cursor cursor = mDB.rawQuery(SQL_GETITEM, null);
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				Items items = new Items();
				int item_id = cursor.getInt(0);
				items.setItem_id(item_id);
				items.setName(cursor.getString(1));
				items.setDescription(cursor.getString(2));
				// items.setCategory_id(cursor.getInt(3));
				// items.setMedia_id(cursor.getInt(4));
				items.setCreate_time(cursor.getString(5));
				items.setUpdate_time(cursor.getString(6));
				items.setPrice(cursor.getFloat(7));
				items.setFlag(cursor.getInt(8));
				// items.setItem_cost_id(cursor.getInt(10)); IIIPOS
				items.setImgName(getItemMedia(item_id));
				itemlist.add(items);
			}
		} else {
			Toast.makeText(mcontext, "Không có dữ liệu ", Toast.LENGTH_SHORT)
					.show();
		}
		Log.i("Log : ", "Count : " + cursor.getCount());
		return itemlist;
	}

	private String getItemMedia(int item_id) {
		String SQL = "SELECT name FROM media WHERE media_id IN (SELECT media_id FROM media_items WHERE items_id = "
				+ item_id + ")";
		String imageName = "";
		Cursor cursor = mDB.rawQuery(SQL, null);
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				imageName = cursor.getString(cursor.getColumnIndex(Media_name));
			}
		}
		return imageName;
	}

	public ArrayList<Invoice> getInvoicePage(int uer_id) {

		ArrayList<Invoice> listInvoice = new ArrayList<Invoice>();
		//
		// String SQL_GET_INVOICE =
		// "SELECT inv.inv_id, inv.total, inv.inv_code, inv.vat , "+
		// "inv.commision, inv.inv_endtime, inv.inv_starttime, "+
		// "inv.[user_id], i.code_table FROM invoice As inv "+
		// "inner join itable i on inv.inv_code = i.inv_code "+
		// "where inv.[user_id] = "+uer_id+
		// " order by inv.inv_id ASC";

		String SQL_GET_INVOICE = "SELECT distinct inv.inv_id, inv.inv_code, inv.total, inv.cost, inv.vat , "
				+ " inv.commision, inv.inv_endtime, inv.inv_starttime, "
				+ " inv.[user_id], inv.status  FROM invoice As inv "
				+ " where inv.user_id = "
				+ uer_id
				+ " order by inv.inv_id DESC";

		Cursor cursor = mDB.rawQuery(SQL_GET_INVOICE, null);
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				Invoice invoice = new Invoice();
				invoice.setInv_id(cursor.getInt(0));
				String inv_code = cursor.getString(1);
				invoice.setInv_code(inv_code);
				invoice.setTotal(cursor.getFloat(2));
				invoice.setCost(cursor.getFloat(3));
				invoice.setVat(cursor.getInt(4));
				invoice.setCommision(cursor.getInt(05));
				invoice.setInv_endtime(cursor.getString(6));
				invoice.setInv_starttime(cursor.getString(7));
				invoice.setUser_id(cursor.getInt(8));
				invoice.setStatus(cursor.getInt(9));
				String invoice_code[] = inv_code.split("_");
				// invoice.setCode_table(invoice_code[0] + "_" +
				// invoice_code[1]); IIIPOS
				listInvoice.add(invoice);
			}
		}

		for (int i = 0; i < listInvoice.size(); i++) {
			Log.i("Log : ", "listInvoice : " + listInvoice.get(i).getInv_code());
		}

		return listInvoice;
	}

	public ArrayList<Invoice_Detail> getInvoiceDetailPage(String inv_code) {

		ArrayList<Invoice_Detail> listInvoice = new ArrayList<Invoice_Detail>();

		//
		// String SQL_GET_INVOICE =
		// "select inv.inv_code, it_c.start_date, it_c.end_date , it.name, "+
		// " it.description, it_c_d.quantity, it_c_d.price, inv.item_cost_id, it_c.item_id "+
		// " from invoice_detail inv "+
		// " Inner join item_cost it_c on it_c.item_cost_id = inv.item_cost_id "+
		// " Inner join items it on it.item_id = it_c.item_id "+
		// " Inner join items_cost_detail it_c_d on it_c_d.items_cost_id = it_c.item_cost_id "+
		// " where inv.inv_code = '"+inv_code+"'";

		String SQL_GET_INVOICE = "select inv.inv_code, it_c.start_date, it_c.end_date , it.name, "
				+

				" it.description, inv.quantity, it_c_d.price, inv.item_id, it_c.item_id, md.name as imagename "
				+

				" from invoice_detail  inv        "
				+

				" Inner join item_cost it_c on it_c.item_id = inv.item_id "
				+

				" Inner join items_cost_detail it_c_d on it_c_d.items_cost_id = it_c.item_cost_id "
				+

				" Inner join items it on it.item_id = it_c.item_id                               "
				+

				" Inner join media_items md_it on md_it.items_id =  it.item_id                     "
				+

				" Inner join media md on md.media_id = md_it.media_id " +

				" where inv.inv_code = '" + inv_code + "'";

		Cursor cursor = mDB.rawQuery(SQL_GET_INVOICE, null);

		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				Invoice_Detail invoice = new Invoice_Detail();
				// invoice.setInv_code(cursor.getString(0)); IIIPOS
				invoice.setStart_date(cursor.getString(1));
				invoice.setEnd_date(cursor.getString(2));
				invoice.setName(cursor.getString(3));
				invoice.setDescription(cursor.getString(4));
				invoice.setQuantity(cursor.getInt(5));
				invoice.setPrice(cursor.getFloat(6));
				invoice.setItem_id(cursor.getInt(8));
				// invoice.setItem_cost_id(cursor.getInt(7)); IIIPOS
				invoice.setImgName(cursor.getString(9));
				listInvoice.add(invoice);
			}
		}

		return listInvoice;
	}

	public String addNewInvoice(String table_code, int id_table, int user_id,
			String simpDate) {

		String SQL_GETCOUNT_INVOICE = "select * from invoice";

		Cursor cursor = mDB.rawQuery(SQL_GETCOUNT_INVOICE, null);

		int countInvoice = cursor.getCount() + 1;

		// String simpDate = new SimpleDateFormat("yyyy-MM-dd hh a").format(new
		// Date());
		String invoicode = table_code + "_" + simpDate + "_" + countInvoice;

		ContentValues contentInvoice = new ContentValues();
		contentInvoice.put(Inv_inv_code, invoicode);
		contentInvoice.put(Inv_total, 0.0f);
		contentInvoice.put(Inv_cost, 0.0f);
		contentInvoice.put(Inv_vat, 0);
		contentInvoice.put(Inv_commision, 0);
		contentInvoice.put(Inv_inv_starttime, simpDate);
		contentInvoice.put(Inv_user_id, user_id);
		contentInvoice.put(Inv_status, 1);
		mDB.insert(INVOICE_DATABASE_TABLE_INVOICE, null, contentInvoice);

		ContentValues contentItableInvoice = new ContentValues();
		contentItableInvoice.put(Inv_Inv, countInvoice);
		contentItableInvoice.put(Inv_It, id_table);
		mDB.insert(INVOICE_ITABLE_DATABASE_TABLE_INVOICE_ITABLE, null,
				contentItableInvoice);

		return invoicode;
	}

	public void insertInvoiceDitail(ArrayList<Invoice_Detail> lstInvoiceDetail,
			String inv_code) {

		int size = lstInvoiceDetail.size();
		for (int i = 0; i < size; i++) {
			String querryCheck = "DELETE FROM invoice_detail WHERE inv_code = '"
					+ inv_code
					+ "' AND item_id = "
					+ lstInvoiceDetail.get(i).getItem_id();
			if (lstInvoiceDetail.get(i).getInv_code()
					.equalsIgnoreCase(inv_code)
					&& lstInvoiceDetail.get(i).getItem_id() == lstInvoiceDetail
							.get(i).getItem_id()) {
				mDB.rawQuery(querryCheck, null);
			} else {
				ContentValues values = new ContentValues();
				// values.put(Inv_Detail_inv_id,
				// lstInvoiceDetail.get(i).getId());
				// values.put(Inv_Detail_inv_id_item,
				// lstInvoiceDetail.get(i).getItem_id());
				// values.put(Inv_Detail_inv_code,
				// lstInvoiceDetail.get(i).getInv_code());
				values.put(Inv_Detail_inv_quantity, lstInvoiceDetail.get(i)
						.getQuantity());
				// values.put(Inv_Detail_flag,
				// lstInvoiceDetail.get(i).getFlag());
				mDB.insert(INVOICE_DETAIL_DATABASE_TABLE_INVOICE_DETAIL, null,
						values);
			}
		}
		return;
	}

	public void insertInvoiceDitail(Invoice_Detail inv_detail, float total,
			float cost, String inv_code, boolean check, int vat, int committion) {

		if (check) {

			String QUERRY_INVDETAIL = "SELECT * FROM "
					+ INVOICE_DETAIL_DATABASE_TABLE_INVOICE_DETAIL + " where "
					+ Inv_Detail_inv_code + " = '" + inv_code + "'";
			Cursor cursorINVDETAIL = mDB.rawQuery(QUERRY_INVDETAIL, null);
			if (cursorINVDETAIL.getCount() > 0) {
				mDB.delete(INVOICE_DETAIL_DATABASE_TABLE_INVOICE_DETAIL,
						Inv_Detail_inv_code + " = '" + inv_code + "'", null);
			}
		}

		String simpDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		int itemCostID = 0;

		String SQL_GET_ITEMCOST = "select item_cost_id from item_cost it_c where item_id = "
				+ inv_detail.getItem_id()
				+ " and strftime('%m-%d-%Y', date(it_c.start_date))  < strftime('%m-%d-%Y', date('"
				+ simpDate
				+ "')) "
				+ " and strftime('%m-%d-%Y', date('"
				+ simpDate
				+ "'))  < strftime('%m-%d-%Y', date(it_c.end_date)) ";

		Cursor cursor = mDB.rawQuery(SQL_GET_ITEMCOST, null);
		;
		Log.i("Log : ", "count cur  : " + cursor.getCount());
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				itemCostID = cursor.getInt(0);
				break;
			}
		}

		ContentValues contentInvoiceDetail = new ContentValues();
		contentInvoiceDetail.put(Inv_Detail_inv_id_item,
				inv_detail.getItem_id());
		contentInvoiceDetail.put(Inv_Detail_inv_code, inv_code);
		contentInvoiceDetail.put(Inv_Detail_inv_quantity,
				inv_detail.getQuantity());
		contentInvoiceDetail.put(Inv_Detail_flag, 1);
		mDB.insert(INVOICE_DETAIL_DATABASE_TABLE_INVOICE_DETAIL, null,
				contentInvoiceDetail);
		ContentValues contentUpdateInvoice = new ContentValues();
		contentUpdateInvoice.put(Inv_commision, committion);
		contentUpdateInvoice.put(Inv_vat, vat);
		contentUpdateInvoice.put(Inv_total, total);
		contentUpdateInvoice.put(Inv_cost, cost);
		mDB.update(INVOICE_DATABASE_TABLE_INVOICE, contentUpdateInvoice,
				Inv_inv_code + " = '" + inv_code + "'", null);
		// mDB.update(INVOICE_DATABASE_TABLE_INVOICE, "("+Inv_total
		// +") values ("+total+")", "where "+Inv_inv_code+" = "+inv_code, null);

		return;
	}

	public int getInvoiceVisible() {

		String SQL_GET_INVOICE = "SELECT inv_id from invoice order by inv_id DESC";
		Cursor cursor = mDB.rawQuery(SQL_GET_INVOICE, null);
		int inv_id = 0;
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				inv_id = cursor.getInt(0);
			}
		}

		return inv_id;
	}

	public void updateInvoiceItable(String inv_code, String table_code) {
		String endtime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String SQL_Update_Invoice = "update invoice set [status] = '0', [user_id] = '0', [inv_endtime] = '"
				+ endtime + "' WHERE inv_code = '" + inv_code + "'";

		/*-----------------hien tai chua co itable nen rao tam lai xu ly sau*/
		String SQL_Update_Itable = "update itable set status = 0, flag=0 where code_table = '"
				+ table_code + "' ";

		mDB.rawQuery(SQL_Update_Invoice, null);
		/* mDB.rawQuery(SQL_Update_Itable, null); */

	}

	/*--Update status and user_id in table invoice--*/
	public long Update(String inv_code) {
		ContentValues values = new ContentValues();
		values.put("user_id", 0);
		values.put("status", 0);
		return mDB.update("invoice", values, "inv_code" + "=?",
				new String[] { inv_code });
	}

	public void delete_item_invoice_detail(String inv_code, int item_id) {

		String simpDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		int itemCostID = 0;

		String SQL_GET_ITEMCOST = "select item_cost_id from item_cost it_c where item_id = "
				+ item_id
				+ " and strftime('%m-%d-%Y', date(it_c.start_date))  < strftime('%m-%d-%Y', date('"
				+ simpDate
				+ "')) "
				+ " and strftime('%m-%d-%Y', date('"
				+ simpDate
				+ "'))  < strftime('%m-%d-%Y', date(it_c.end_date)) ";

		Cursor cursor = mDB.rawQuery(SQL_GET_ITEMCOST, null);
		;
		Log.i("Log : ", "count cur  : " + cursor.getCount());
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				itemCostID = cursor.getInt(0);
				break;
			}
		}

		String querry = "delete from invoice_detail where inv_code = '"
				+ inv_code + "' and item_id = " + itemCostID;
		mDB.rawQuery(querry, null);
	}

	// ========= check User login ==========//
	public int checkUser(String userName, String passWord) {
		int user_id = 0;
		String querry = "SELECT user_id, user_name, password FROM users "
				+ "WHERE user_name = '" + userName + "' and password = '"
				+ passWord + "'";
		Cursor cursor = mDB.rawQuery(querry, null);

		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				User user = new User();
				user.setUser_id(cursor.getInt(cursor.getColumnIndex("user_id")));
				user.setUsername(cursor.getString(cursor
						.getColumnIndex("user_name")));
				user.setPassword(cursor.getString(cursor
						.getColumnIndex("password")));
				user_id = user.getUser_id();
			}
			Log.i("USER ID >>>>>>>>>>>>>>>>>>: ", user_id + "");
			return user_id;
		}
		return 0;
	}

}
