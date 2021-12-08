package hong.gom.withcrossfit.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class RegexValidatorTest {
	
	@Autowired
	RegexValidator regexValidator = new RegexValidator();

	@Test
	void isRightRegex함수_테스트() {
		// given
		String timeRegex = "[0-9]{2}:[0-9]{2}";
		
		// when
		boolean result1 = regexValidator.isRightValue(timeRegex, "00:00");
		boolean result2 = regexValidator.isRightValue(timeRegex, "1234");
		
		// then
		assertEquals(true, result1);
		assertEquals(false, result2);
	}

}