/*package iii.pos.client.data;

import java.util.ArrayList;

import iii.pos.client.model.User;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserDB extends ConfigDB {

	public UserDB(Context context) {
		super(context);
	}

	public void addUser(User user) {
		if (!checkUserExist(user)) {
			SQLiteDatabase db = getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(USER_NAME, user.getUsername());
			values.put(USER_PASS, user.getPassword());
			db.insert(USER_TABLE, null, values);
			db.close();
		}
	}

	public void deleteUser() {

	}

	public void updateUser() {

	}

	public User getUser(int id) {
		User user = new User();
		return user;
	}

	public ArrayList<User> getAllUser() {
		ArrayList<User> users = new ArrayList<User>();
		SQLiteDatabase db = getWritableDatabase();
		String query = "select * from " + USER_TABLE;
		Cursor cursor = db.rawQuery(query, null);
		if (cursor.moveToFirst()) {
			do {
				User user = cursorToObject(cursor);
				users.add(user);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return users;
	}

	public boolean checkUserExist(User user) {
		SQLiteDatabase db = getWritableDatabase();
		String querry = " Select user_name from " + USER_TABLE
				+ " where user_name= '" + user.getUsername() + "'";
		Cursor cursor = db.rawQuery(querry, null);
		if (cursor.getCount() > 0) {
			cursor.close();
			return true;
		}
		cursor.close();
		return false;
	}

	public boolean checkUserExist(String name, String pass) {
		boolean kq = false;
		SQLiteDatabase db = getWritableDatabase();
		String query = "select " + USER_NAME + " , " + USER_PASS + " from "
				+ USER_TABLE + " where " + USER_NAME + " = '" + name + "' AND "
				+ USER_PASS + " = '" + pass + "'";
		Cursor cursor = db.rawQuery(query, null);
		if (cursor != null && cursor.getCount() != 0) {
			kq = true;
		} else {
			kq = false;
		}
		cursor.close();
		db.close();
		return kq;
	}

	public User cursorToObject(Cursor cursor) {
		User user = new User();
		user.setUser_id(Integer.parseInt(cursor.getString(0)));
		user.setPassword(cursor.getString(1));
		user.setPassword(cursor.getString(2));
		return user;
	}
}
*/