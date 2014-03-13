package iii.pos.client.library;

import java.util.ArrayList;

/**
 * Lớp xử lý việc convert hóa đơn từ dạng 
 * input: T1_B2_T1_B3_T1_B4_098398786
 * output: HD (B2,B3,B4) Tầng 1
 */
public class FormatFloorTableName {

	/**
	 * Hàm convert tên invoice
	 * 
	 * @param sInvCode : input: T1_B2_T1_B3_T1_B4_098398786
	 * @return output: HD (B2,B3,B4) Tầng 1
	 */
	public String formatNameInvoice(String sInvCode){
		String sOutPut = null; // Biến đầu ra sau khi xử lý xong
		String[] arr = null; // Tên mảng sau khi cắt ký tự '_'
		ArrayList<String> arrFloorName = new ArrayList<String>(); // Mảng tên các tầng
		ArrayList<String> arrTableName = new ArrayList<String>(); // Mảng tên các bàn
		
		// Kiểm tra input có chứa ký tự "_" ?
		if( sInvCode.contains("_") ){
			arr = sInvCode.split("_");
			for (String str : arr) {
				if(str.contains("T")){
					if( !arrFloorName.contains(str) )
						arrFloorName.add(str.replace("T", ""));
				}
				if( str.contains("B")){
					if( !arrTableName.contains(str) )
						arrTableName.add(str.replace("B", ""));
				}
			}
		}
		if( arrFloorName.size() > 0 && arrTableName.size() > 0 ){
			String table = formatTableName(arrTableName);
			String floor = formatNameFloor(arrFloorName);
			sOutPut = joinNameInvoiceFormat(floor, table);
		}
		
		return sOutPut;
	}
	
	/**
	 *  Định dạng tên tầng
	 * @param arrFloorName : Danh sách tên floor. Ví dụ: T1,T2
	 * @return Tầng T1,T2
	 */
	public String formatNameFloor( ArrayList<String> arrFloorName){
		StringBuilder arrFloor = new StringBuilder();
		for (String sFloor : arrFloorName) {
			if( arrFloorName.size() >=2 ){
				arrFloor.append(sFloor);
				arrFloor.append(", ");
			}else{
				arrFloor.append(sFloor);
			}
			
		}
		String sFloor = "Floor " + arrFloor ;
		return sFloor;
	}
	
	/**
	 *  Định dạng tên bàn
	 *  
	 * @param arrTableName: Danh sách tên table. Ví dụ: B1,B2
	 * @return Bàn (B1,B2)
	 */
	public String formatTableName( ArrayList<String> arrTableName){
		StringBuilder arrTable = new StringBuilder();
		for (String sTable : arrTableName) {
			 if( arrTableName.size() >= 2 ){
				 arrTable.append(sTable);
				 arrTable.append(", ");
			 }else{
				 arrTable.append(sTable);
			}
			
		}
		String sTable = "Table (" + arrTable + ") " ;
		return sTable;
	}
	
	/**
	 *  Nối tên bàn và tên tầng lại
	 *  
	 * @param sFloor Tầng T1,T2
	 * @param sTable Bàn (B1,B2)
	 * @return HD Bàn (B1,B2) Tầng T1,T2
	 */
	public String joinNameInvoiceFormat( String sFloor, String sTable){
		String output = "HD " + sTable + sFloor ;
		return output;
	}
	
	/**
	 * Hàm tách chuỗi chỉ lấy ra Tên Tầng và Tên bàn
	 * @param sInvCode : T1_B2_20142402121212
	 * @return T1_B2
	 */
	public String joinFloorAndTable( String sInvCode ){
		String[] arr = null; // Tên mảng sau khi cắt ký tự '_'
		String sFloorName = null; // Mảng tên các tầng
		String sTableName = null; // Mảng tên các bàn
		
		// Kiểm tra input có chứa ký tự "_" ?
		if( sInvCode.contains("_") ){
			arr = sInvCode.split("_");
			for (String str : arr) {
				if(str.contains("T")){
					sFloorName = str;
				}
				if( str.contains("B")){
					sTableName = str;
				}
			}
		}
		return sFloorName+"_"+sTableName; // T1_B2
	}
	
	/**
	 * Hàm tách chuỗi chỉ lấy ra Tên bàn
	 * @param sInvCode : T1_B2_20142402121212
	 * @return Bàn 2
	 */
	public String getTableName( String sInvCode ){
		String[] arr = null; // Tên mảng sau khi cắt ký tự '_'
		String sTableName = null; // Mảng tên các bàn
		
		// Kiểm tra input có chứa ký tự "_" ?
		if( sInvCode.contains("_") ){
			arr = sInvCode.split("_");
			for (String str : arr) {
				if( str.contains("B")){
					sTableName = str.replace("B", "");
				}
			}
		}
		return "Table "+sTableName; // Bàn 2
	}
	
	
}
