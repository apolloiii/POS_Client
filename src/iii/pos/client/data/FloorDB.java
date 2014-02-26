/*package iii.pos.client.data;

import iii.pos.client.model.Floor;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class FloorDB extends ConfigDB {

	public FloorDB(Context context) {
		super(context);
	}

	public void addFloor(Floor floor) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(FL_ID_SV, floor.getId_sv());
		values.put(FL_CODE, floor.getCode());
		values.put(FL_STATUS, floor.getStatus());
		values.put(FL_NAME, floor.getName());
		values.put(FL_CREATE_TIME, "2013-09-09");
		values.put(FL_UPDATE_TIME, "2013-09-09");
		values.put(FL_DESCRIPTION, "no thing");
		values.put(FL_IMAGE, "image");
		db.insert(FL_TABLE, null, values);
		db.close();
	}

	public ArrayList<Floor> getFloor() {
		ArrayList<Floor> args = new ArrayList<Floor>();
		try {
			// Select All Query
			String selectQuery = "SELECT  * FROM " + FL_TABLE;
			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {
					Floor floor = new Floor();
					// floor.setId_sv(Integer.parseInt(cursor.getString(1)));
					floor.setId(Integer.parseInt(cursor.getString(1)));
					floor.setName(cursor.getString(2));
					floor.setCode(cursor.getString(3));
					floor.setStatus(Integer.parseInt(cursor.getString(4)));
					args.add(floor);
				} while (cursor.moveToNext());
			}
			cursor.close();
			db.close();
		} catch (Exception e) {
			Log.e("all_contact", "" + e);
		}
		return args;
	}

	public boolean checkExist(String idSv) {
		String QUERRY = "SELECT " + FL_ID_SV + " FROM " + FL_TABLE + " WHERE "
				+ FL_ID_SV + " = '" + idSv + "' ";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(QUERRY, null);
		if (cursor.getCount() > 0) {
			return true;
		}
		return false;
	}
}
*/