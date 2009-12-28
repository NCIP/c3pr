package edu.duke.cabig.c3pr.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils extends org.apache.commons.lang.StringUtils {

    public StringUtils() {
    }

    public static boolean isBlank(String str) {
        boolean test = false;
        if (str == null) {
            test = true;
        }
        else {
            if (str.equals("")) {
                test = true;
            }
        }
        return test;
    }

    public static String getBlankIfNull(String str) {
        boolean test = false;
        if (str == null) return "";
        return str;
    }

    public static String getBlankIfNull(Boolean bool) {
        if (bool == null) return "true";
        if (bool.toString().equals("")) return "true";
        return bool.toString();
    }

    public static String initString(String str) {
        String test = "";
        if (str != null) {
            test = str;
        }
        return test;
    }

    public static String initStringNbsp(String str) {
        String test = "&nbsp;";
        if (str != null && !str.equals("")) {
            test = str;
        }
        return test;
    }

    public static String getVal(Object val) {
        String test = "";
        if (val != null) {
            test = val.toString();
        }
        return test;
    }

    public static String replaceInString(String in, String from, String to) {
        StringBuffer sb = new StringBuffer(in.length() * 2);
        String posString = in.toLowerCase();
        String cmpString = from.toLowerCase();
        int i = 0;
        boolean done = false;
        while (i < in.length() && !done) {
            int start = posString.indexOf(cmpString, i);
            if (start == -1) {
                done = true;
            }
            else {
                sb.append(in.substring(i, start) + to);
                i = start + from.length();
            }
        }
        if (i < in.length()) {
            sb.append(in.substring(i));
        }
        return sb.toString();
    }

    public static String[] convertArrayListToStringArray(ArrayList al) {
        Object obj[] = (Object[]) al.toArray();
        String[] ar;
        if (obj.length > 0) {
            ar = new String[obj.length];
            for (int j = 0; j < obj.length; j++) {
                ar[j] = obj[j].toString();
            }
        }
        else {
            ar = null;
        }
        return ar;
    }

    public static String preAndPostwildCard(String _name) {

        String trimmedName = _name;

        if (trimmedName == null || trimmedName.equals("") || trimmedName.length() == 0) {
            return "";
        }
        trimmedName = _name.toString().trim();
        if (trimmedName.length() > 0 && !trimmedName.endsWith("%")) {
            trimmedName = trimmedName + "%";
        }
        if (trimmedName.length() > 0 && !trimmedName.startsWith("%")) {
            trimmedName = "%" + trimmedName;
        }

        return trimmedName;
    }

    public static String wildCard(String _name) {
        String trimmedName = _name.toString().trim();

        if (trimmedName == null || trimmedName.equals("") || trimmedName.length() == 0) {
            return "";
        }
        else if (trimmedName.length() > 0 && !trimmedName.endsWith("%")) {
            return trimmedName + "%";
        }
        else {
            return trimmedName;
        }
    }

    public static String removeWildCard(String _name) {
        String trimmedName = _name;

        if (trimmedName == null || trimmedName.equals("") || trimmedName.length() == 0) {
            return "";
        }
        trimmedName = _name.toString().trim();
        if (trimmedName.length() > 0 && trimmedName.endsWith("%")) {
            trimmedName = trimmedName.substring(0, (trimmedName.length() - 1));
        }
        if (trimmedName.length() > 0 && trimmedName.startsWith("%")) {
            trimmedName = trimmedName.substring(1, (trimmedName.length()));
        }

        return trimmedName;
    }

    public static String camelCase(String inputString) {

        String camelCaseStr = "";

        if (inputString == null) return camelCaseStr;

        String lowerCase = inputString.toLowerCase();

        StringTokenizer strTokenizer = new StringTokenizer(lowerCase, " ");
        String strToken = "";
        StringBuffer strBuf = null;
        char[] charAry = null;

        while (strTokenizer.hasMoreTokens()) {
            strBuf = new StringBuffer();
            strToken = strTokenizer.nextToken();
            charAry = strToken.toCharArray();
            strBuf.append(Character.toUpperCase(charAry[0]));
            int remaingstrLength = charAry.length - 1;
            strBuf.append(charAry, 1, remaingstrLength);
            camelCaseStr = camelCaseStr + " " + strBuf.toString();
        }
        return camelCaseStr;

    }

    /**
     * This method removes the first and last character from the input string and returns the
     * remaining string if the input string length is more than or equal to 3 charaters otherwise
     * returns the input string.
     */
    public static String removeFirstAndLastCharacters(String inputString) {
        if (inputString != null) {
            if (inputString.length() >= 3) return inputString.substring(1,
                            (inputString.length() - 1));
            else return inputString;
        }
        return "";
    }

    public static Date convertToDate(String dateString, String format) {

        if (dateString == null || dateString.length() == 0) return null;

        SimpleDateFormat sdf;
        boolean defaultFlag = false;
        if (format != null && format.length() > 0) {
            sdf = new SimpleDateFormat(format);
        }
        else {
            sdf = new SimpleDateFormat("MM/dd/yyyy");
            defaultFlag = true;
        }

        try {
            return sdf.parse(dateString);
        }
        catch (ParseException e) {
            if (defaultFlag) try {
                return (new SimpleDateFormat("MM-dd-yyyy")).parse(dateString);
            }
            catch (ParseException e1) {
                // TODO add better error logging here
                e1.printStackTrace();
            }
            else {
                e.printStackTrace(); // TODO add better error logging here
            }
        }

        return null;

    }

    public static String getTrimmedText(String text, int trimLength) {
        String trim = text;
        if (text != null && text.length() > trimLength) {
            trim = text.substring(0, trimLength - 1);
            trim += "...";
        }
        return trim;
    }

    /**
     * Returns an InputStream given a String str
     * 
     * @param str
     * @return
     */
    public static InputStream getInputStream(String str) {

        byte[] b = str.getBytes();

        ByteArrayInputStream iStream = new ByteArrayInputStream(b);
        return iStream;
    }
    
    public static String readFile(String filename) throws Exception{
        java.io.BufferedReader br = new java.io.BufferedReader(new java.io.InputStreamReader(
                        Thread.currentThread().getContextClassLoader().getResourceAsStream(
                                        filename)));
        StringBuffer sb = new StringBuffer();
        try {
            String newline = System.getProperty("line.separator");
            String tmp = null;
            while ((tmp = br.readLine()) != null) {
                sb.append(tmp);
                sb.append(newline);
            }
        }
        finally {
            if (br != null) br.close();
            return(sb.toString());
        }
    }
    
    public static boolean isValidEmail(String email){
//        return email.matches(".+@.+\\.[a-z][A-Z]+");
    	return email.matches(".+@.+\\.[a-zA-Z]+");
    }
    
    public static boolean isValidPhone(String phone){
        //return phone.matches("((\\d[ \\-])?\\d{3}[ \\-]\\d{3}[ \\-]\\d{4}([ \\-]ext[ \\-]\\d+)?)||\\d{10}");
    	return phone.matches("^(1\\s*[-\\/\\.]?)?(\\((\\d{3})\\)|(\\d{3}))\\s*[-\\/\\.]?\\s*(\\d{3})\\s*[-\\/\\.]?\\s*(\\d{4})\\s*(([xX]|[eE][xX][tT])\\.?\\-?\\s*(\\d+))*$");
    }
    
    public static boolean isValidFax(String phone){
        //return phone.matches("((\\d[ \\-])?\\d{3}[ \\-]\\d{3}[ \\-]\\d{4}([ \\-]ext[ \\-]\\d+)?)||\\d{10}");
    	return phone.matches("^(1\\s*[-\\/\\.]?)?(\\((\\d{3})\\)|(\\d{3}))\\s*[-\\/\\.]?\\s*(\\d{3})\\s*[-\\/\\.]?\\s*(\\d{4})\\s*");
    	
    }
    
//    public static void main(String args[]){
//    	System.out.println(isValidPhone("(123)-234-2345"));
//    	System.out.println(isValidPhone("1 123-234-2345 ext.123"));
//    	System.out.println(isValidPhone("1-123-234-2345 ext.123"));
//    	System.out.println(isValidPhone("123-234-2345 ext-123"));
//    	System.out.println(isValidPhone("123 134 2345 ext-123"));
//    	System.out.println(isValidPhone("123.123.2345 ext-123"));
//    	System.out.println(isValidPhone("1 123.123.2345 ext-123"));
//    	System.out.println(isValidPhone("1.123.123.2345 ext-123"));
//    	System.out.println(isValidPhone("1 123 123 2345 ext-123"));
//    	System.out.println(isValidPhone("123-234-2345 ext 123"));
//    	System.out.println(isValidPhone("123-234-2345 ext123"));
//    	System.out.println(isValidPhone("123-234-2345 ext.123"));
//    	System.out.println(isValidPhone("123-234-2345"));
//    }
//    
//
//    public static void main1(String args[]){
//    	System.out.println(isValidFax("(123)-234-2345"));
//    	System.out.println(isValidFax("1 123-234-2345"));
//    	System.out.println(isValidFax("1-123-234-2345"));
//    	System.out.println(isValidFax("123-234-2345"));
//    	System.out.println(isValidFax("123 134 2345"));
//    	System.out.println(isValidFax("123.123.2345"));
//    	System.out.println(isValidFax("1 123.123.2345"));
//    	System.out.println(isValidFax("1.123.123.2345"));
//    	System.out.println(isValidFax("1 123 123 2345"));
//    	System.out.println(isValidFax("123-234-2345"));
//    	System.out.println(isValidFax("123-234-2345"));
//    	System.out.println(isValidFax("123-234-2345"));
//    	System.out.println(isValidFax("123-234-2345"));
//    }
}