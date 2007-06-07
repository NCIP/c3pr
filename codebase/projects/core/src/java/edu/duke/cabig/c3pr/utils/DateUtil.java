package edu.duke.cabig.c3pr.utils;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

import edu.nwu.bioinformatics.commons.DateUtils;

public class DateUtil extends DateUtils{
    // ========================= CLASS CONSTANTS ============================

    // constants to specify display formats for the dates.
    public static final String DISPLAY_DATE_FORMAT = "MM/dd/yyyy";
    public static final String DISPLAY_DATE_TIME_FORMAT =
        "Day mm/dd/yyyy HH:MM:SS EDT";
    public static final String JDBC_DATE_ESCAPE_FORMAT = "yyyy-MM-dd";
    private static final String DISPLAY_DATE_DELIMITER = "/";
    private static final String JDBC_DATE_DELIMITER = "-";
    private static final String ZONE = "EDT";
    private static String[] nameOfDay =
        { "0 no day", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };

    private static final String DEFAULT_DATE_FORMAT = "MM/dd/yyyy hh:mm:ss am";

    public static java.util.Date getServerTime() {

        GregorianCalendar date = new GregorianCalendar();

        return date.getTime();
    }

    /**
     * converts a java.util.Date to java.sql.Date
     * @param  utilDate      java.util.Date
     * @return sqlDate       java.sql.Date
     */
    public static java.sql.Date getSqlDate(java.util.Date utilDate) {
        return new java.sql.Date(utilDate.getTime());
    }

    /**
     * converts a java.util.Date to java.sql.Timestamp
     * @param  utilDate      java.util.Date
     * @return sqlTimestamp       java.sql.Timestamp
     */
    public static java.sql.Timestamp getTimestamp(java.util.Date utilDate) {
        return new java.sql.Timestamp(utilDate.getTime());
    }

    /**
     * converts a java.Sql.Date to java.util.Date
     * @param  SqlDate      java.sql.Date
     * @return utilDate       java.util.Date
     */
    public static java.util.Date getUtilDate(java.sql.Date sqlDate) {
        return new java.util.Date(sqlDate.getTime());
    }

    /**
     * converts a java.sql.Timestamp to java.util.Date
     * @param  sqlTimestamp       java.sql.Timestamp
     * @return utilDate           java.util.Date
     */
    public static java.util.Date getUtilDate(java.sql.Timestamp timestamp) {
        return new java.util.Date(timestamp.getTime());
    }

    /**
     * converts a String to java.sql.Date
     * @param  dateAsString      date in the form of a string
     * @param  format            format of the date string
     * @rerurn date              java.sql.Date, null if format is not one of the formats
     *                           specified in DateUtil class
     */
    public static java.sql.Date getSqlDateFromString(
        String dateAsString,
        String format) {
        // date string of the form "mm/dd/yyyy"
    	
        if (DISPLAY_DATE_FORMAT.equalsIgnoreCase(format)) {
            StringTokenizer dateAsTokens =
                new StringTokenizer(dateAsString, DISPLAY_DATE_DELIMITER);
            String month = dateAsTokens.nextToken();
            String date = dateAsTokens.nextToken();
            String year = dateAsTokens.nextToken();

            dateAsString =
                year + JDBC_DATE_DELIMITER + month + JDBC_DATE_DELIMITER + date;
            return java.sql.Date.valueOf(dateAsString);
        }
        // date string of the form "yyyy-mm-dd"
        else if (JDBC_DATE_ESCAPE_FORMAT.equalsIgnoreCase(format)) {
            return java.sql.Date.valueOf(dateAsString);
        }
        // default util.date to string convertion
        else {
            return null;
        }
    }

    /**
     * converts a String to java.util.Date
     * @param  dateAsString      date in the form of a string
     * @param  format            format of the date string
     * @rerurn date              java.util.Date, null if format is not one of the formats
     *                           specified in DateUtil class
     */
    public static java.util.Date getUtilDateFromString(
        String dateAsString,
        String format) {
        // date string of the form "mm/dd/yyyy"
        if (DISPLAY_DATE_FORMAT.equalsIgnoreCase(format)) {
            StringTokenizer dateAsTokens =
                new StringTokenizer(dateAsString, DISPLAY_DATE_DELIMITER);
            String month = dateAsTokens.nextToken();
            String date = dateAsTokens.nextToken();
            String year = dateAsTokens.nextToken();

            Calendar calendar = Calendar.getInstance();
            calendar.clear();
            calendar
                .set(
                    Integer.valueOf(year).intValue(),
                    (Integer.valueOf(month).intValue() - 1),
            // month in calendar 1-11
            Integer.valueOf(date).intValue());
            return calendar.getTime();
        }
        // date string of the form "yyyy-mm-dd"
        else if (JDBC_DATE_ESCAPE_FORMAT.equalsIgnoreCase(format)) {
            java.sql.Date sqlDate = java.sql.Date.valueOf(dateAsString);
            return new java.util.Date(sqlDate.getTime());
        }
        // default util.date to string convertion
        else {
            return null;
        }
    }

    /**
     * converts a java.sql.Date string with appropriate format
     * @param  sqlDate      java.sql.Date
     * @param  format       specifying the output format for the return string
     * @return formatted string
     */
    public static String toString(java.sql.Date sqlDate, String format) {
        // convert Date to String of the form "mm/dd/yyyy"
        if (DISPLAY_DATE_FORMAT.equalsIgnoreCase(format)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sqlDate);
            return getDateAsString(
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DATE),
                calendar.get(Calendar.YEAR));
        }
        // convert Date to String of the form "mm/dd/yyyy HH:MM"
        else if (DISPLAY_DATE_TIME_FORMAT.equals(format)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sqlDate);

            return getDateTimeStringFromCalendar(calendar);
        }
        // convert Date to String of the form "yyyy-mm-dd"
        else if (JDBC_DATE_ESCAPE_FORMAT.equalsIgnoreCase(format)) {
            return sqlDate.toString();
        }
        // default util.date to string convertion
        else {
            java.util.Date defaultDate = new java.util.Date(sqlDate.getTime());
            return defaultDate.toString();
        }
    }

    /**
     * converts a java.util.Date string with appropriate format
     * @param  utilDate      java.util.Date
     * @param  format       specifying the output format for the return string
     * @return formatted string if utilDate is not null
     */
    public static String toString(java.util.Date utilDate, String format) {
        if (utilDate == null) {
            return "";
        }

        // convert Date to String of the form "mm/dd/yyyy"
        if (DISPLAY_DATE_FORMAT.equalsIgnoreCase(format)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(utilDate);

            return getDateAsString(
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DATE),
                calendar.get(Calendar.YEAR));
        }
        // convert Date to String of the form "mm/dd/yyyy HH:MM"
        else if (DISPLAY_DATE_TIME_FORMAT.equals(format)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(utilDate);

            return getDateTimeStringFromCalendar(calendar);
        }
        // convert Date to String of the form "yyyy-mm-dd"
        else if (JDBC_DATE_ESCAPE_FORMAT.equalsIgnoreCase(format)) {
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            return sqlDate.toString();
        }
        // default util.date to string convertion
        else {
            return utilDate.toString();
        }
    }

    // private utility method to convert month, day, year to mm/dd/yyyy format string
    private static String getDateAsString(int month, int date, int year) {
        StringBuffer dateAsString = new StringBuffer();

        if (month > 9) {
            dateAsString.append(month);
        } else {
            dateAsString.append("0" + month);
        }
        dateAsString.append(DISPLAY_DATE_DELIMITER);
        if (date > 9) {
            dateAsString.append(date);
        } else {
            dateAsString.append("0" + date);
        }
        dateAsString.append(DISPLAY_DATE_DELIMITER).append(year);

        return dateAsString.toString();
    }

    // private utility method to conver the date represented by the calender
    // into Wed 10/02/2002 13:25:46 EDT format
    private static String getDateTimeStringFromCalendar(Calendar calendar) {
        String dateString = nameOfDay[calendar.get(Calendar.DAY_OF_WEEK)] + " ";
        dateString
            += getDateAsString(
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DATE),
                calendar.get(Calendar.YEAR));
        dateString
            += (" "
                + calendar.get(Calendar.HOUR_OF_DAY)
                + ":"
                + calendar.get(Calendar.MINUTE)
                + ":"
                + calendar.get(calendar.SECOND)
                + " "
                + ZONE);
        return dateString;
    }

    public static String getDateForLetter()
    {
      GregorianCalendar todaysDate = new GregorianCalendar();
      int year = todaysDate.get(Calendar.YEAR);
      int month = todaysDate.get(Calendar.MONTH);
      int day = todaysDate.get(Calendar.DAY_OF_MONTH);

      return months[month]+" "+day+","+year;
    }
   public static String getCurrentDate(String formatStr)
    {
   		//This is temporary fix, lot of places in the code the dateformat is coded as mm/dd/yyyy instead of MM/dd/yyyy.
   		if("mm/dd/yyyy".equals(formatStr)){
   			formatStr = DISPLAY_DATE_FORMAT;
   		}
   		
        Date d = new Date();
        SimpleDateFormat formatter   = new SimpleDateFormat (formatStr);
        Date currentDate = new Date();
        return formatter.format(currentDate);
    }
    public static String getFormattedDate(String formatStr,Date d)
    {
        SimpleDateFormat formatter   = new SimpleDateFormat (formatStr);
        Date currentDate = new Date();
        return formatter.format(d);
    }
    
    public static final String formatDate(Date date, String toFormat)
    	throws ParseException
    {   
    	return new SimpleDateFormat(toFormat).format(date);
    }
    
    private static String[] months = {"Jan","Feb","March","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
}
