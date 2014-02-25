package iii.pos.client.menu;

import iii.pos.client.model.Invoice_Detail;
import iii.pos.client.model.Items;

import java.util.ArrayList;

import android.util.Log;


public class BeanInvDetailTmpl {
	private ArrayList<Invoice_Detail> listItem;

	public int getSize() {
		if (listItem != null) {
			return listItem.size();
		}
		return 0;
	}

	public BeanInvDetailTmpl() {
		listItem = new ArrayList<Invoice_Detail>();
	}

	public Invoice_Detail check(Items item) {
		if (listItem != null)
			for (Invoice_Detail inv : listItem) {
				if (item.getItem_id() == inv.getItem_id())
					return inv;
			}
		return null;
	}

	// ---------add item to lsttempl------//
	public void bindData(Items items) {
		if (listItem != null && listItem.size() > 0) {
			addAnItem(items);
		} else {
			listItem.add(convertInv_Detail(items));
		}
	}

	// ---------add item to lsttempl------//
	private void addAnItem(Items items) {
		boolean check = false;
		for (int i = 0; i < listItem.size(); i++) {
			Log.i("Log : ", "Item ID : " + listItem.get(i).getItem_id());
			if (listItem.get(i).getItem_id() == items.getItem_id()) {
				check = true;
				break;
			}
		}
		if (check) {
			updateItem(items);
			check = false;
		} else {
			listItem.add(convertInv_Detail(items));
		}
	}

	// -----------remove an item-----------//
	public void removeItem(Items item) {
		boolean icheck = false;
		int i_i = 0;
		for (int i = 0; i < listItem.size(); i++) {
			if (listItem.get(i).getItem_id() == item.getItem_id()) {
				icheck = true;
				i_i = i;
				break;
			}
		}
		if (icheck) {
			Log.i("Log : ", "Item : Delete : " + listItem.get(i_i).getName());
			listItem.remove(i_i);
			icheck = false;
			return;
		}
	}

	// ----------------remove inv_detail---------//
	public void removeItem(Invoice_Detail inv_detail) {
		boolean icheck = false;
		int i_i = 0;
		for (int i = 0; i < listItem.size(); i++) {
			if (listItem.get(i).getItem_id() == inv_detail.getItem_id()) {
				icheck = true;
				i_i = i;
				break;
			}
		}
		if (icheck) {
			Log.i("Log : ", "Item : Delete : " + listItem.get(i_i).getName());
			listItem.remove(i_i);
			icheck = false;
			return;
		}
	}

	// ---------update an items------------------//
	public void updateItem(Items items) {
		boolean icheck = false;
		int i_i = 0;
		for (int i = 0; i < listItem.size(); i++) {
			if (listItem.get(i).getItem_id() == items.getItem_id()) {
				i_i = i;
				icheck = true;
				break;
			}
		}
		if (icheck) {
			listItem.remove(i_i);
			listItem.add(i_i, convertInv_Detail(items));
			icheck = false;
			return;
		}
	}

	// ---------update an inv_detail----------------//
	public void updateItem(Invoice_Detail inv_detail) {
		boolean icheck = false;
		int i_i = 0;
		for (int i = 0; i < listItem.size(); i++) {
			if (listItem.get(i).getItem_id() == inv_detail.getItem_id()) {
				i_i = i;
				icheck = true;
				break;
			}
		}
		if (icheck) {
			listItem.remove(i_i);
			listItem.add(i_i, inv_detail);
			icheck = false;
			return;
		}
	}

	// ----------getter---------------------------//
	public ArrayList<Invoice_Detail> getListItem() {
		return listItem;
	}

	// ----------setter-----------------------------//
	public void setListItem(ArrayList<Invoice_Detail> listItem) {
		this.listItem.clear();
		this.listItem = listItem;
	}

	// ----------clear lst ------------------------//
	public void removeList() {
		listItem.clear();
	}

	// ------------checking---------------------------//
	public boolean isNull() {
		if (listItem != null && listItem.size() > 0) {
			return false;
		}
		return true;
	}

	// -------------converter item to inv_detail---------//
	private Invoice_Detail convertInv_Detail(Items items) {

		Invoice_Detail inv_detail = new Invoice_Detail();
		inv_detail.setCategory_id(items.getCategory_id());
		inv_detail.setDescription(items.getDescription());
		inv_detail.setFlag(items.getFlag());
		inv_detail.setName(items.getName());
		inv_detail.setImgName(items.getImgName());
		inv_detail.setEnd_date(items.getUpdate_time());
		inv_detail.setStart_date(items.getCreate_time());
		inv_detail.setItem_id(items.getItem_id());
		inv_detail.setPrice(items.getPrice());
		inv_detail.setQuantity(items.getQuantityItem());
		inv_detail.setComment(items.getDescription());
		inv_detail.setInputAmounFloat(items.getInputAmoutFloat());

		//System.out.println(items.getInputAmoutFloat() + " Float");
		return inv_detail;
	}
}
