package ${package}.test.testcase.pageobject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.WebElement;

public class Utils {

	static long getLongFromElement(WebElement element) {
		if (element!=null && "".compareTo(element.getAttribute("innerHTML"))!=0) {
			Pattern pattern = Pattern.compile("([\\d.]+)");
			Matcher matcher = pattern.matcher(element.getAttribute("innerHTML"));
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
