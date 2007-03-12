package edu.duke.cabig.c3pr.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.orm.hibernate3.support.OpenSessionInViewInterceptor;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import edu.duke.cabig.c3pr.domain.DomainObject;
import edu.nwu.bioinformatics.commons.StringUtils;
import edu.nwu.bioinformatics.commons.testing.DbTestCase;

/**
 * Base Dao Test Case for all dao level test cases
 * @author Rhett Sutphin, Priyatam
 */
public abstract class DaoTestCase extends DbTestCase {
    protected final Log log = LogFactory.getLog(getClass());

    protected MockHttpServletRequest request = new MockHttpServletRequest();
    protected MockHttpServletResponse response = new MockHttpServletResponse();
    protected WebRequest webRequest = new ServletWebRequest(request);
    private boolean shouldFlush = true;

    protected void setUp() throws Exception {
        super.setUp();
        beginSession();
    }

    protected void tearDown() throws Exception {
       // endSession();
        super.tearDown();
    }

    public void runBare() throws Throwable {
        setUp();
        try {
            runTest();
        } catch (Throwable throwable) {
            shouldFlush = false;
            throw throwable;
        } finally {
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
        if (shouldFlush) {
            interceptor.postHandle(webRequest, null);
        }
        interceptor.afterCompletion(webRequest, null);
    } 

    protected void interruptSession() {
        endSession();
        log.info("-- interrupted DaoTestCase session --");
        beginSession();
    }

    private OpenSessionInViewInterceptor findOpenSessionInViewInterceptor() {
        return (OpenSessionInViewInterceptor) getApplicationContext().getBean("openSessionInViewInterceptor");
    } 

    protected DataSource getDataSource() {
        return (DataSource) getApplicationContext().getBean("dataSource");
    }
    
    protected IDatabaseConnection getConnection() throws Exception {
        DatabaseConnection databaseConnection = new DatabaseConnection(getDataSource().getConnection(), getSchema());
        databaseConnection.getConfig().setProperty("http://www.dbunit.org/properties/datatypeFactory", createDataTypeFactory());
        return databaseConnection;
    }

    protected String getSchema()
    {
    	return "C3PR";
    }
    
   /* protected DatabaseOperation getSetUpOperation() throws Exception
	{
	    return DatabaseOperation.DELETE;
	}
    
    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.DELETE;
    }
    */
    public static ApplicationContext getApplicationContext() {
        return ApplicationTestCase.getDeployedCoreApplicationContext();
    }

    protected final void dumpResults(String sql) {
        List<Map<String, String>> rows = new JdbcTemplate(getDataSource()).query(
            sql,
            new ColumnMapRowMapper() {
                protected Object getColumnValue(ResultSet rs, int index) throws SQLException {
                    Object value = super.getColumnValue(rs, index);
                    return value == null ? "null" : value.toString();
                }
            }
        );
        StringBuffer dump = new StringBuffer(sql).append('\n');
        if (rows.size() > 0) {
            Map<String, Integer> colWidths = new HashMap<String, Integer>();
            for (String colName : rows.get(0).keySet()) {
                colWidths.put(colName, colName.length());
                for (Map<String, String> row : rows) {
                    colWidths.put(colName, Math.max(colWidths.get(colName), row.get(colName).length()));
                }
            }

            for (String colName : rows.get(0).keySet()) {
                StringUtils.appendWithPadding(colName, colWidths.get(colName), false, dump);
                dump.append("   ");
            }
            dump.append('\n');

            for (Map<String, String> row : rows) {
                for (String colName : row.keySet()) {
                    StringUtils.appendWithPadding(row.get(colName), colWidths.get(colName), false, dump);
                    dump.append(" | ");
                }
                dump.append('\n');
            }
        }
        dump.append("  ").append(rows.size()).append(" row").append(rows.size() != 1 ? "s\n" : "\n");

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
