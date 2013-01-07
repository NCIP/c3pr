package edu.duke.cabig.c3pr.utils;

public class ApplicationUtils {

    public static boolean isUnix() {
        boolean test = false;
        String str = System.getProperty("os.name");
        int i = str.indexOf("Win");
        if (i != -1) {
            test = false;
        }
        else {
            test = true;
        }
        return test;
    }
}