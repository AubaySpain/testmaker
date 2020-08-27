package com.github.jorge2m.example_test.test.testcase.pageobject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.WebElement;

public class Utils {

	static long getLongFromElement(WebElement element) {
		if (element!=null && "".compareTo(element.getText())!=0) {
			Pattern pattern = Pattern.compile("([\\d.,]+)");
			Matcher matcher = pattern.matcher(element.getText());
			if (matcher.find()) {
				return Long.valueOf(
					matcher.group(0).
					trim().
					replace(",", "").
					replace(".", ""));
			}
		}
		return 0;
	}
	
}
