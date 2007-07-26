package edu.duke.cabig.c3pr.web.beans;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jul 13, 2007
 * Time: 10:48:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class StudyXMLFileBean {


    private byte[] file;

    public StudyXMLFileBean() {
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public byte[] getFile() {
        return file;
    }

    public Reader getReader() {
        return new BufferedReader(new InputStreamReader(
                new ByteArrayInputStream(file)));
    }

    public InputStream getInputStream() {
        return new ByteArrayInputStream(file);
    }

}



 
