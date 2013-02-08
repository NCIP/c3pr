/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr;

import edu.duke.cabig.c3pr.dao.C3PRBaseDao;
import edu.nwu.bioinformatics.commons.ComparisonUtils;
import edu.nwu.bioinformatics.commons.testing.CoreTestCase;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.easymock.IArgumentMatcher;
import org.easymock.classextension.EasyMock;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.*;

/**
 * @author Biju Joseph
 */
public class AbstractTestCase extends CoreTestCase {

    private static Log log = LogFactory.getLog(AbstractTestCase.class);

    protected Set<Object> mocks = new HashSet<Object>();

    ////// MOCK REGISTRATION AND HANDLING

    protected <T> T registerMockFor(Class<T> forClass) {
        return registered(EasyMock.createMock(forClass));
    }

    protected <T> T registerMockFor(Class<T> forClass, Method... methodsToMock) {
        return registered(EasyMock.createMock(forClass, methodsToMock));
    }

    protected <T extends C3PRBaseDao<?>> T registerDaoMockFor(Class<T> forClass) {
        List<Method> methods = new LinkedList<Method>(Arrays.asList(forClass.getMethods()));
        for (Iterator<Method> iterator = methods.iterator(); iterator.hasNext();) {
            Method method = iterator.next();
            if ("domainClass".equals(method.getName())) {
                iterator.remove();
            }
        }
        return registerMockFor(forClass, methods.toArray(new Method[methods.size()]));
    }

    protected void replayMocks() {
        for (Object mock : mocks) EasyMock.replay(mock);
    }

    protected void verifyMocks() {
        for (Object mock : mocks) EasyMock.verify(mock);
    }

    protected void resetMocks() {
        for (Object mock : mocks) EasyMock.reset(mock);
    }

    private <T> T registered(T mock) {
        mocks.add(mock);
        return mock;
    }

    protected static <T> T matchByProperties(T template) {
        EasyMock.reportMatcher(new PropertyMatcher<T>(template));
        return null;
    }

    /**
     * Finds a file in the same module as the given class.  For example, say you have a package named
     * <kbd>web</kbd> and you need to refer to <kbd>web/src/main/foo/bar</kbd>.  Pass this method a
     * class that's in that module (i.e., compiled into <kbd>web/target/test-classes</kbd>) and
     * <kbd>src/main/foo/bar</kbd> to get the path to the local copy of
     * <kbd>web/src/main/foo/bar</kbd>.
     *
     * @param clazz    A class from the desired module
     * @param filename Path to the desired file, relative to the module root
     */
    public static File getModuleRelativeFile(Class<?> clazz, String filename) {
        try {
            File classDirectory = new File(clazz.getResource("/").toURI());
            return new File(new File(classDirectory, "../.."), filename);
        } catch (URISyntaxException e) {
            throw new RuntimeException("Could not locate '/' relative to " + clazz.getName(), e);
        }
    }

    /**
     * Easymock matcher that compares two objects on their property values
     */
    @SuppressWarnings("unchecked")
    private static class PropertyMatcher<T> implements IArgumentMatcher {
        private T template;
        private Map<String, Object> templateProperties;

        public PropertyMatcher(T template) {
            this.template = template;
            try {
                templateProperties = PropertyUtils.describe(template);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }

        public boolean matches(Object argument) {
            try {
                Map<String, Object> argumentProperties = PropertyUtils.describe(argument);
                for (Map.Entry<String, Object> entry : templateProperties.entrySet()) {
                    Object argProp = argumentProperties.get(entry.getKey());
                    Object templProp = entry.getValue();
                    if (!ComparisonUtils.nullSafeEquals(templProp, argProp)) {
                        throw new AssertionError("Argument's " + entry.getKey()
                                + " property doesn't match template's: " + templProp + " != " + argProp);
                    }
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }

            return true;
        }


        public void appendTo(StringBuffer buffer) {
            buffer.append(template).append(" (by properties)");
        }
    }
}
