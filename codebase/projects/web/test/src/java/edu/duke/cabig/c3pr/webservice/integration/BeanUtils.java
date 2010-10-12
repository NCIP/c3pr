/**
 * 
 */
package edu.duke.cabig.c3pr.webservice.integration;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <b style="text-transform:uppercase;font-size:16px;font-family:Arial Black;">
 * Copy-and-paste of /core/src/java/edu/duke/cabig/c3pr/utils/BeanUtils.java due
 * to issues with running C3PR in embedded Tomcat. Contact Denis Krylov for
 * explanation.</b> <br>
 * <br>
 * Provides convenience operations on JavaBeans. This class intentionally
 * extends {@link org.apache.commons.beanutils.BeanUtils} for convenience, so
 * that developers would not have to use two different bean utility classes
 * separately.
 * 
 * @author dkrylov
 * @see org.apache.commons.beanutils.BeanUtils
 */
public abstract class BeanUtils extends org.apache.commons.beanutils.BeanUtils {

	private static final Class<?>[] DIRECTLY_COMPARABLE_TYPES = new Class<?>[] {
			byte.class, char.class, short.class, int.class, long.class,
			float.class, double.class, Number.class, CharSequence.class,
			Date.class, Enum.class };

	private static final List<Class<?>> SKIP_TYPES = Arrays
			.asList(new Class<?>[] { Class.class });
	
	private static Log log = LogFactory.getLog(BeanUtils.class);

	/**
	 * Cannot instantiate me, can you?
	 */
	private BeanUtils() {
	}

	/**
	 * This methods performs deep comparison of two objects of the same class.
	 * Comparison is performed only on properties exposed via the standard
	 * JavaBean mechanism. Properties of primitive types, wrappers,
	 * {@link String}, {@link CharSequence}, {@link Date}, {@link Enum} are
	 * compared directly using {@link Object#equals(Object)}; other complex
	 * properties are compared recursively. Elements of {@link Collection}
	 * properties are iterated and compared.
	 * 
	 * @param <T>
	 * @param obj1
	 * @param obj2
	 * @return
	 * @throws NullPointerException
	 *             if any of the parameters is null.
	 */
	public static <T> boolean deepCompare(T obj1, T obj2) {
		if (obj1 == obj2) {
			return true;
		}
		// if it's a "simple" object, do direct comparison.
		for (Class<?> cls : DIRECTLY_COMPARABLE_TYPES) {
			if (cls.isAssignableFrom(obj1.getClass())) {
				if (!obj1.equals(obj2)) {
					log.info("Values don't match: "+obj1+" and "+obj2);
					return false;
				} else {
					return true;
				}
			}
		}
		try {
			PropertyDescriptor[] descriptors = PropertyUtils
					.getPropertyDescriptors(obj1.getClass());
			for (PropertyDescriptor pd : descriptors) {
				// ignore properties which cannot be read.
				if (pd.getReadMethod() != null) {
					Class<?> type = pd.getPropertyType();
					// this check will skip Object.getClass().
					if (SKIP_TYPES.contains(type)) {
						continue;
					}
					String name = pd.getName();
					Object v1 = PropertyUtils.getSimpleProperty(obj1, name);
					Object v2 = PropertyUtils.getSimpleProperty(obj2, name);
					if (v1 == v2 || (v1 == null && v2 == null)) {
						continue;
					}
					if ((v1 == null && v2 != null)
							|| (v1 != null && v2 == null)) {
						log.info("Values don't match: "+v1+" and "+v2);
						return false;
					}
					// Collections need special handling.
					if (Collection.class.isAssignableFrom(type)) {
						List l1 = new ArrayList((Collection) v1);
						List l2 = new ArrayList((Collection) v2);
						if (l1.size() != l2.size()) {
							log.info("Collection sizes don't match:"+l1+l2);
							return false;
						}
						for (int i = 0; i < l1.size(); i++) {
							Object el1 = l1.get(i);
							Object el2 = l2.get(i);
							if (!deepCompare(el1, el2)) {
								return false;
							}
						}

					} else if (!deepCompare(v1, v2)) {
						return false;
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(ExceptionUtils.getFullStackTrace(e));
		}
		return true;
	}

}
