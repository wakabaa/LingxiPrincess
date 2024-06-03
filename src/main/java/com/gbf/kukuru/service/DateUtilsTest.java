package com.gbf.kukuru.service;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Locale;

import org.junit.Test;

public class DateUtilsTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testIsNthWeekOfMonth();
	}

	
    @Test
    public static void testIsNthWeekOfMonth() {
        int n = 2;
        LocalDate today = LocalDate.of(2024, 6, 3);
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int weekOfMonth = today.get(weekFields.weekOfMonth());
        
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.JULY, 3);
        int weekOfMonth1 = calendar.get(Calendar.WEEK_OF_MONTH);
        System.out.println("这周是这个月的第 " + weekOfMonth + " 周");
        
        
        
    }
    
}
