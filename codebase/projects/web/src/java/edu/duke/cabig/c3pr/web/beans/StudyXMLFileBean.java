package edu.duke.cabig.c3pr.web.beans;

import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.xml.StudyXMLImporter;
import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.bean.Row;
import org.extremecomponents.table.bean.Table;
import org.extremecomponents.table.context.Context;
import org.extremecomponents.table.context.HttpServletRequestContext;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.core.TableModelImpl;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

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

    public Reader getReader(){
        return new BufferedReader(new InputStreamReader(
                new ByteArrayInputStream(file)));
    }


}



 
