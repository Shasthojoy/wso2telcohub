
package lk.dialog.utils;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtil {
    
    public static Date  DateAdd(Date setDate, String category, int amout ) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(setDate);
        
        if (category.equals("n")) {
            calendar.add(Calendar.MINUTE, amout);
        } else if (category.equals("h")) {
            calendar.add(Calendar.HOUR_OF_DAY, amout);
        } else if (category.equals("d")) {
            calendar.add(Calendar.DAY_OF_MONTH, amout);
        } else if (category.equals("ww")) {
            calendar.add(Calendar.WEEK_OF_MONTH, amout);
        } else if (category.equals("m")) {
            calendar.add(Calendar.MONTH, amout);
        }  else if (category.equals("y")) {
            calendar.add(Calendar.YEAR, amout);
        }
        
        return calendar.getTime();
    }
    
    public static int daysInMonth(Date date) {
        
         Calendar calendar = Calendar.getInstance(); 
         calendar.setTime(date);
         int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        
         return days;
    }
    
    public static long  dateDiff(String category, Date startDate, Date finishDate ) {
        
        long amount = 0;    
        long startDt = startDate.getTime();
        long finishDt = finishDate.getTime();
        
        if (category.equals("n")) {
           amount = (finishDt - startDt) / (60 * 1000) ;
        } else if (category.equals("h")) {
           amount = (finishDt - startDt) / (60 * 60* 1000) ;
        } else if (category.equals("d")) {
           amount = (finishDt - startDt) / (60 * 60 * 24 * 1000) ;
        } else if (category.equals("m")) {
           amount = finishDate.getMonth() - startDate.getMonth();
        }  else if (category.equals("year")) {
            amount = finishDate.getYear() - startDate.getYear();
        }
        
        return amount;
    }
    
     public static Date stringToUtilDate(String date) {        
        
        Date utilDate = null;
        try {
            if (date  != null && !date.equals("")) {
                utilDate = new Date(date);
            }
        } catch(Exception e) {
            e.printStackTrace();
            //System.out.println("################:Can not canvert string date to util date ");
        }
        
        return utilDate;
    }
     
     
     public static int getYear(Date date) {        
        
        int year = 0;
        try {
            year = date.getYear();
        } catch(Exception e) {
            e.printStackTrace();
            //System.out.println("################:Date invalid ");
        }
        
        return year;
    }
    
    public static int getMonth(Date date) {        
        
        int month = 0;
        try {
            month = date.getMonth();
        } catch(Exception e) {
            e.printStackTrace();
            //System.out.println("################:Date invalid ");
        }
        
        return month;
    }
    
    public static int getDate(Date date) {        
        
        int dateM = 0;
        try {
            dateM = date.getDate();
        } catch(Exception e) {
            e.printStackTrace();
            //System.out.println("################:Date invalid ");
        }
        
        return dateM;
    }
    
    public static Date createDate(Integer year, Integer month, Integer date) {        
        
        Date utilDate = null;
        try {
            utilDate = new Date(year, month, date);
        } catch(Exception e) {
            e.printStackTrace();
            //System.out.println("################: invalid ");
        }
        
        return utilDate;
    }
    
    public static int getYearNow() {        
        
        int year = 0;
        try {
            year = (new Date()).getYear();
        } catch(Exception e) {
            e.printStackTrace();
            //System.out.println("################:Date invalid ");
        }
        
        return year;
    }
    
    public static int getMonthNow() {        
        
        int month = 0;
        try {
            month = (new Date()).getMonth();
        } catch(Exception e) {
            e.printStackTrace();
            //System.out.println("################:Date invalid ");
        }
        
        return month;
    }
    
    public static int getDateNow() {        
        
        int dateM = 0;
        try {
            dateM = (new Date()).getDate();
        } catch(Exception e) {
            e.printStackTrace();
            //System.out.println("################:Date invalid ");
        }
        
        return dateM;
    }
    
    public static int getDayOfWeek(Date date) {        
        
        int day = 0;
        try {
            day = date.getDay();
        } catch(Exception e) {
            e.printStackTrace();
            //System.out.println("################:Date invalid ");
        }
        
        return day;
    }
    
    public static Date  DateAdd(Date setDate, String category, Integer amout ) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(setDate);
        
        if (category.equals("n")) {
            calendar.add(Calendar.MINUTE, amout);
        } else if (category.equals("h")) {
            calendar.add(Calendar.HOUR_OF_DAY, amout);
        } else if (category.equals("d")) {
            calendar.add(Calendar.DAY_OF_MONTH, amout);
        } else if (category.equals("ww")) {
            calendar.add(Calendar.WEEK_OF_MONTH, amout);
        } else if (category.equals("m")) {
            calendar.add(Calendar.MONTH, amout);
        }  else if (category.equals("y")) {
            calendar.add(Calendar.YEAR, amout);
        }
        
        return calendar.getTime();
    }
    
    public static int getWeek(Date date) {
        
         Calendar calendar = Calendar.getInstance(); 
         calendar.setTime(date);
         int weekNumber = calendar.get(Calendar.WEEK_OF_YEAR);
        
         return weekNumber;
    }
    
    public static String getDateTime(String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String formatDateTime(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

     

}
