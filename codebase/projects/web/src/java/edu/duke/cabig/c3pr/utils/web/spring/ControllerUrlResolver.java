package edu.duke.cabig.c3pr.utils.web.spring;
/**
 * @author Rhett Sutphin
 */
/* TODO: much of this class is shared with PSC.  Refactor into a shared library. */
public interface ControllerUrlResolver {
    ResolvedControllerReference resolve(String controllerBeanName);
}
