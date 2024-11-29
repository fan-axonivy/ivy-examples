package com.examples.ivy.core.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class PhoneNumberHelper {

	public static boolean isPhoneNumber(String phoneNumber) {
		if (StringUtils.isBlank(phoneNumber)) {
			return false;
		}

		String patterns = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
				+ "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
				+ "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";

		Pattern pattern = Pattern.compile(patterns);
		Matcher matcher = pattern.matcher(phoneNumber);
		return matcher.matches();
	}
}
