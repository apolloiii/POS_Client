package iii.pos.client.data;

import iii.pos.client.model.Invoice;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class InvoiceDB extends ConfigDB {

	public InvoiceDB(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public ArrayList<Invoice> getallInvoice() {
		ArrayList<Invoice> invoiceList = new ArrayList<Invoice>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.query(INV_TABLE, null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				Invoice invoice = new Invoice();
				invoice.setInv_code(cursor.getString(cursor
						.getColumnIndex(INV_INV_CODE)));
				invoice.setTotal(Float.parseFloat(cursor.getString(cursor
						.getColumnIndex(INV_TOTAL))));
				invoice.setCommision(Integer.parseInt(cursor.getString(cursor
						.getColumnIndex(INV_COMMISION))));
				invoice.setCost(Float.parseFloat(cursor.getString(cursor
						.getColumnIndex(INV_COST))));
				invoice.setCost(Integer.parseInt(cursor.getString(cursor
						.getColumnIndex(INV_VAT))));
				invoice.setInv_endtime(cursor.getString(cursor
						.getColumnIndex(INV_INV_ENDTIME)));
				invoice.setInv_starttime(cursor.getString(cursor
						.getColumnIndex(INV_INV_STARTTIME)));
				invoice.setUser_id(Integer.parseInt(cursor.getString(cursor
						.getColumnIndex(INV_USER_ID))));
				invoice.setStatus(Integer.parseInt(cursor.getString(cursor
						.getColumnIndex(INV_STATUS))));
				invoice.setInv_type(Integer.parseInt(cursor.getString(cursor
						.getColumnIndex(INV_INV_TYPE))));
				invoiceList.add(invoice);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return invoiceList;

	}

	public long insertInvoice(Invoice invoice) {
		SQLiteDatabase db = this.getWritableDatabase();
		if (checkInvoice(invoice)) {
			ContentValues contentValues = new ContentValues();
			contentValues.put(INV_INV_CODE, invoice.getInv_code());
			contentValues.put(INV_TOTAL, invoice.getTotal());
			contentValues.put(INV_COST, invoice.getCost());
			contentValues.put(INV_VAT, invoice.getVat());
			contentValues.put(INV_COMMISION, invoice.getCommision());

			contentValues.put(INV_INV_ENDTIME, invoice.getInv_endtime());
			contentValues.put(INV_INV_STARTTIME, invoice.getInv_starttime());
			contentValues.put(INV_USER_ID, invoice.getUser_id());
			contentValues.put(INV_STATUS, invoice.getStatus());
			contentValues.put(INV_INV_TYPE, invoice.getInv_type());
			return db.insert(INV_TABLE, null, contentValues);
		}
		return 0;

	}

	public boolean checkInvoice(Invoice invoice) {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.query(INV_TABLE, null, null, null, null, null, null);

		if (cursor.moveToFirst()) {
			do {
				if (cursor.getString(cursor.getColumnIndex(INV_INV_CODE))
						.equals(invoice.getInv_code())) {
					return false;
				}

			} while (cursor.moveToNext());
		}
		return true;

	}

}
