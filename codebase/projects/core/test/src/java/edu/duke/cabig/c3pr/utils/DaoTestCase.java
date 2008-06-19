package edu.duke.cabig.c3pr.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.orm.hibernate3.HibernateJdbcException;
import org.springframework.orm.hibernate3.support.OpenSessionInViewInterceptor;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import edu.nwu.bioinformatics.commons.StringUtils;
import edu.nwu.bioinformatics.commons.testing.DbTestCase;
import gov.nih.nci.cabig.ctms.audit.domain.DataAuditInfo;
import gov.nih.nci.cabig.ctms.domain.DomainObject;

/**
 * Base Dao Test Case for all dao level test cases
 * 
 * @author Rhett Sutphin, Priyatam
 */
public abstract class DaoTestCase extends DbTestCase {
    protected final Log log = LogFactory.getLog(getClass());

    protected MockHttpServletRequest request = new MockHttpServletRequest();

    protected MockHttpServletResponse response = new MockHttpServletResponse();

    protected WebRequest webRequest = new ServletWebRequest(request);

    private boolean shouldFlush = true;

    public static final DataAuditInfo INFO = new DataAuditInfo("user", "127.0.0.0", DateUtil
                    .createDate(2004, Calendar.NOVEMBER, 2), "c3pr/study");

    protected void setUp() throws Exception {
        super.setUp();
        SecurityContextTestUtils.switchToSuperuser();
        DataAuditInfo.setLocal(INFO);
        beginSession();
    }

    protected void tearDown() throws Exception {
        // endSession();
        SecurityContextTestUtils.switchToNobody();
        DataAuditInfo.setLocal(null);
        endSession();
        super.tearDown();
    }

    public void runBare() throws Throwable {
        setUp();
        try {
            runTest();
        }
        catch (Throwable throwable) {
            shouldFlush = false;
            throw throwable;
        }
        finally {
            tearDown();
        }
    }

    private void beginSession() {
        log.info("-- beginning DaoTestCase interceptor session --");
        findOpenSessionInViewInterceptor().preHandle(webRequest);
    }

    private void endSession() {
        log.info("--    ending DaoTestCase interceptor session --");
        OpenSessionInViewInterceptor interceptor = findOpenSessionInViewInterceptor();
        try {
            if (shouldFlush) {
                interceptor.postHandle(webRequest, null);
            }
        }catch (RuntimeException exception) {
            if (exception instanceof HibernateJdbcException) {
                if (exception != null) {
                    System.out.println(exception); // Log the exception
                    // Get cause if present
                    Throwable t = ((HibernateJdbcException) exception).getRootCause();

                    while (t != null) {
                        System.out.println("*************Cause: " + t);
                        t = t.getCause();
                    }

                }
            }
            throw exception;
        }finally{
            interceptor.afterCompletion(webRequest, null);
        }
    }

    protected void interruptSession() {
        endSession();
        log.info("-- interrupted DaoTestCase session --");
        beginSession();
    }
    
    protected void interruptSessionForceNewSession() {
        try{
        	endSession();
        }finally{
	        log.info("-- interrupted DaoTestCase session --");
	        beginSession();
        }
    }

    private OpenSessionInViewInterceptor findOpenSessionInViewInterceptor() {
        return (OpenSessionInViewInterceptor) getApplicationContext().getBean(
                        "openSessionInViewInterceptor");
    }

    protected DataSource getDataSource() {
        return (DataSource) getApplicationContext().getBean("dataSource");
    }

    protected IDatabaseConnection getConnection() throws Exception {
        DatabaseConnection databaseConnection = new DatabaseConnection(getDataSource()
                        .getConnection(), getSchema());
        databaseConnection.getConfig()
                        .setProperty("http://www.dbunit.org/properties/datatypeFactory",
                                        createDataTypeFactory());
        return databaseConnection;
    }

    /**
     * For Oracle typically it is "C3PR_DEV" (note- upper case). For Postgres - "public". Dont
     * forget to override this depending on your database
     * 
     * @return
     */
    protected static String getSchema() {
    	URL url =  ClassLoader.getSystemResource("context/datasource.properties");
    	Properties p = new Properties();
    	try {
			p.load(new FileInputStream(new File(url.getFile())));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return p.getProperty("datasource.testschema");
    }
    
    public static void main(String args[]){
    	DaoTestCase.getSchema();
    }
    public ApplicationContext getApplicationContext() {
        return ApplicationTestCase.getDeployedCoreApplicationContext();
    }

    protected final void dumpResults(String sql) {
        List<Map<String, String>> rows = new JdbcTemplate(getDataSource()).query(sql,
                        new ColumnMapRowMapper() {
                            protected Object getColumnValue(ResultSet rs, int index)
                                            throws SQLException {
                                Object value = super.getColumnValue(rs, index);
                                return value == null ? "null" : value.toString();
                            }
                        });
        StringBuffer dump = new StringBuffer(sql).append('\n');
        if (rows.size() > 0) {
            Map<String, Integer> colWidths = new HashMap<String, Integer>();
            for (String colName : rows.get(0).keySet()) {
                colWidths.put(colName, colName.length());
                for (Map<String, String> row : rows) {
                    colWidths.put(colName, Math.max(colWidths.get(colName), row.get(colName)
                                    .length()));
                }
            }

            for (String colName : rows.get(0).keySet()) {
                StringUtils.appendWithPadding(colName, colWidths.get(colName), false, dump);
                dump.append("   ");
            }
            dump.append('\n');

            for (Map<String, String> row : rows) {
                for (String colName : row.keySet()) {
                    StringUtils.appendWithPadding(row.get(colName), colWidths.get(colName), false,
                                    dump);
                    dump.append(" | ");
                }
                dump.append('\n');
            }
        }
        dump.append("  ").append(rows.size()).append(" row")
                        .append(rows.size() != 1 ? "s\n" : "\n");

        System.out.print(dump);
    }

    public List<Integer> collectIds(List<? extends DomainObject> actual) {
        List<Integer> ids = new ArrayList<Integer>(actual.size());
        for (DomainObject object : actual) {
            ids.add(object.getId());
        }
        return ids;
    }
}
