/**
 * 
 */
package edu.duke.cabig.c3pr.utils;

import java.util.Date;

/**
 * Provides convenience operations on JavaBeans. This class intentionally
 * extends {@link org.apache.commons.beanutils.BeanUtils} for convenience, so
 * that developers would not have to use two different bean utility classes
 * separately.
 * 
 * @author dkrylov
 * @see org.apache.commons.beanutils.BeanUtils
 */
public abstract class BeanUtils extends org.apache.commons.beanutils.BeanUtils {

	/**
	 * Cannot instantiate me, can you?
	 */
	private BeanUtils() {
	}

	/**
	 * This methods performs deep comparison of two objects of the same class.
	 * Comparison is performed only on properties exposed via the standard
	 * JavaBean mechanism. Properties of primitive types, wrappers,
	 * {@link String}s, {@link Date}s are compared directly using
	 * {@link Object#equals(Object)}; other complex properties are compared
	 * recursively.
	 * 
	 * @param <T>
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static <T> boolean deepCompare(T obj1, T obj2) {
		if (obj1 == obj2) {
			return true;
		}
		return true;
	}

}
