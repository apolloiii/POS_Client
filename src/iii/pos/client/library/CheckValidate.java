package iii.pos.client.library;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckValidate {

	public boolean isPhoneValidate(String sPhoneNumber) {
		//String sPhoneNumber = "605-8889999";
	      //String sPhoneNumber = "605-88899991";
	      //String sPhoneNumber = "605-888999A";
		// Pattern pattern = Pattern.compile("\\d{3}-\\d{7}");
	     
	      Pattern pattern10 = Pattern.compile("\\d{10}");
	      Matcher matcher10 = pattern10.matcher(sPhoneNumber);
	      
	      Pattern pattern11 = Pattern.compile("\\d{11}");
	      Matcher matcher11 = pattern11.matcher(sPhoneNumber);
	      
	      
	      if ( matcher10.matches() || matcher11.matches() ) return true;
	      else return false;
	}
}
