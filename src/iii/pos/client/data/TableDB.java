/*package iii.pos.client.data;

import iii.pos.client.model.Itable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TableDB extends ConfigDB {

	public TableDB(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public void addFloor(Itable table) {
		SQLiteDatabase db = this.getWritableDatabase();
		if(checkExist(table)){
		ContentValues values = new ContentValues();
		values.put(Itable_floor, table.getFloor());
		values.put(Itable_code_table, table.getCode_table());
		values.put(Itable_status, table.getStatus());
		values.put(Itable_description_table, table.getDescription_table());
		values.put(Itable_pos_x, table.getPos_x());
		values.put(Itable_pos_y, table.getPos_y());
		values.put(Itable_flag, table.getFlag());
		values.put(Itable_create_time, "2013-09-09");
		values.put(Itable_update_time, "2013-09-09");
		// Inserting Row
		db.insert(TABLE_NAME, null, values);
		 // Closing database connection
		}
	}
	public boolean checkExist(Itable table) {
		SQLiteDatabase db = getWritableDatabase();
		String QUERRY = "SELECT * FROM pos_pos_itable WHERE code_table = '"
				+ table.getCode_table() + "' ";
		Cursor cursor = db.rawQuery(QUERRY, null);
		if (cursor.getCount() > 0) {
			// db.close();
			return false;
		}
	 db.close();
		return true;
	}


}
*/