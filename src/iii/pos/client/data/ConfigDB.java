/*package iii.pos.client.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConfigDB extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "POS_DATA";

	// ------ bang user -------//
	public static final String USER_TABLE = "USER_TABLE";
	public static final String USER_ID = "USER_ID";
	public static final String USER_NAME = "USER_NAME";
	public static final String USER_PASS = "USER_PASS";

	// ------ bang floor ------//
	public static final String FL_TABLE = "FL_TABLE";
	public static final String FL_ID = "FL_ID";
	public static final String FL_ID_SV = "FL_ID_SV";
	public static final String FL_NAME = "FL_NAME";
	public static final String FL_CODE = "FL_CODE";
	public static final String FL_STATUS = "FL_STATUS";
	public static final String FL_CREATE_TIME = "FL_CREATE_TIME";
	public static final String FL_UPDATE_TIME = "FL_UPDATE_TIME";
	public static final String FL_DESCRIPTION = "FL_DESCRIPTION";
	public static final String FL_IMAGE = "FL_IMAGE";

	// ------ bang invoice ------//
	public static final String INV_TABLE = "INV_TABLE";
	public static final String INV_INV_ID = "INV_INV_ID";
	public static final String INV_INV_CODE = "INV_INV_CODE";
	public static final String INV_TOTAL = "INV_TOTAL";
	public static final String INV_COST = "INV_COST";
	public static final String INV_VAT = "INV_VAT";
	public static final String INV_COMMISION = "INV_COMMISION";
	public static final String INV_INV_ENDTIME = "INV_INV_ENDTIME";
	public static final String INV_INV_STARTTIME = "INV_INV_STARTTIME";
	public static final String INV_USER_ID = "INV_USER_ID";
	public static final String INV_STATUS = "INV_STATUS";
	public static final String INV_INV_TYPE = "INV_INV_TYPE";

	// ------ bang table ------//
	public static final String TABLE_NAME = "TABLE_NAME";
	public static final String ITABLE_ITABLE_IDSV = "ITABLE_ITABLE_IDSV";
	public static final String ITABLE_FLOOR = "ITABLE_FLOOR";
	public static final String ITABLE_CODE_TABLE = "ITABLE_CODE_TABLE";
	public static final String ITABLE_DESCRIPTION_TABLE = "ITABLE_DESCRIPTION_TABLE";
	public static final String ITABLE_CREATE_TIME = "ITABLE_CREATE_TIME";
	public static final String ITABLE_UPDATE_DATE = "ITABLE_UPDATE_DATE";
	public static final String ITABLE_STATUS = "ITABLE_STATUS";
	public static final String ITABLE_FLAG = "flag";
	public static final String ITABLE_POS_X = "pos_x";
	public static final String ITABLE_POS_Y = "pos_y";

	public ConfigDB(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// ---- create user table ---- //
		String user_create = "create table " + USER_TABLE + "( " + USER_ID
				+ " integer primary key not null, " + USER_NAME
				+ " text not null, " + USER_PASS + " text not null)";
		db.execSQL(user_create);

		// --- create user floor --- //
		String floor_create = "create table " + FL_TABLE + " ( " + FL_ID
				+ " integer primary key , " + FL_ID_SV + " integer , "
				+ FL_NAME + " nvarchar(250) , " + FL_CODE + " nvarchar(250 ), "
				+ FL_STATUS + " integer , " + FL_CREATE_TIME + " datetime , "
				+ FL_UPDATE_TIME + " datetime , " + FL_DESCRIPTION
				+ " nvarchar(500) , " + FL_IMAGE + " nvarchar(500) )";
		db.execSQL(floor_create);

		// --- create user invoice --- //
		String invoice_create = "create table " + INV_TABLE + " ( "
				+ INV_INV_ID + " integer primary key , " + INV_INV_CODE
				+ " nvarchar(500) , " + INV_TOTAL + " float , " + INV_COST
				+ " float , " + INV_VAT + " integer , " + INV_COMMISION
				+ " integer , " + INV_INV_ENDTIME + " datetime , "
				+ INV_INV_STARTTIME + " datetime , " + INV_USER_ID
				+ " integer , " + INV_INV_TYPE + " integer , " + INV_STATUS
				+ " integer )";

		db.execSQL(invoice_create);
		
		// ---- create table ---- //
		String table_create = "create table "+ TABLE_NAME + " ( "
				+ ITABLE_ITABLE_IDSV + " integer primary key , "
				+ ITABLE_FLOOR + " integer , "
				+ ITABLE_CODE_TABLE + " integer , "
				+ ITABLE_DESCRIPTION_TABLE + " integer , "
				+ ITABLE_CREATE_TIME + " integer , "
				+ ITABLE_UPDATE_DATE + " integer , "
				+ ITABLE_STATUS + " integer , "
				+ ITABLE_FLAG + " integer , "
				+ ITABLE_POS_X + " integer , "
				+ ITABLE_POS_Y + " nvarchar(500) )";
 
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop if table exists " + USER_TABLE);
		db.execSQL("drop if table exists " + FL_TABLE);
		db.execSQL("drop if table exists " + INV_TABLE);
		onCreate(db);
	}

}
*/