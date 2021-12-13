package hong.gom.withcrossfit.util;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

@Component
public class DateUtil {
	
	public LocalDate makeToLocalDate(String dateStr) {
		String[] dates = dateStr.split("-");
		
		int year = Integer.parseInt(dates[0]);
		int month = Integer.parseInt(dates[1]);
		int date = Integer.parseInt(dates[2]);
		
		return LocalDate.of(year, month, date);
	}

}
