package hong.gom.withcrossfit.util;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class RegexValidator {
	
	public boolean isRightValue(String regex, String value) {
		if (Pattern.matches(regex, value)) {
		    return true;
		} 	
		return false;
	}
	
}	
