package edu.duke.cabig.c3pr.ant;

import com.sun.mirror.apt.AnnotationProcessorFactory;
import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.declaration.AnnotationTypeDeclaration;

import java.util.Collection;
import java.util.Arrays;
import java.util.Set;
import java.lang.annotation.Annotation;

import gov.nih.nci.cabig.ctms.CommonsSystemException;
import edu.duke.cabig.c3pr.ant.UseCaseTraceabilityAnnotationProcessor;

/**
 * Central class in the automated association of use cases to unit test cases.
 * Each project that uses this system needs to provide an enum which implements
 * {@link edu.duke.cabig.c3pr.UseCase} and which contains values for all the
 * application's use cases.  The classname of this enum must be provided to apt
 * using the <code>useCasesAnnotationClassName</code> parameter.
 *
 * @author Rhett Sutphin
 */
public class UseCaseTraceabilityAnnotationProcessorFactory implements AnnotationProcessorFactory {
    public static final String USE_CASES_ANNOTATION_CLASS_NAME_OPT = "-AuseCasesAnnotationClassName=";
    public static final String PROJECT_NAME_OPT = "-Aproject.name=";


    public Collection<String> supportedOptions() {
        return Arrays.asList(USE_CASES_ANNOTATION_CLASS_NAME_OPT);
    }

    public Collection<String> supportedAnnotationTypes() {
        // don't have access to the options here, so...
        return Arrays.asList("*");
    }

    public AnnotationProcessor getProcessorFor(
        Set<AnnotationTypeDeclaration> annotations, AnnotationProcessorEnvironment env
    ) {
        String name = null;
        // this seems like it shouldn't be necessary
        for (String key : env.getOptions().keySet()) {
            if (key.startsWith(USE_CASES_ANNOTATION_CLASS_NAME_OPT)) {
                name = key.substring(USE_CASES_ANNOTATION_CLASS_NAME_OPT.length());
                break;
            }
        }
        if (name == null) {
            throw new CommonsSystemException(
                USE_CASES_ANNOTATION_CLASS_NAME_OPT + " option not provided: " + env.getOptions());
        }

        return new UseCaseTraceabilityAnnotationProcessor(
            env, useCasesAnnotationClass(name)
        );
    }

    /**
     * Return the class for the Annotation used to associate test cases with use cases.
     * This annotation is expected to have a method named <code>value()</code> which
     * returns instances of an enum which implements {@link edu.duke.cabig.c3pr.UseCase}.
     * @return
     */
    protected Class<? extends Annotation> useCasesAnnotationClass(String name) {
        try {
            Class<?> klass = Class.forName(name);
            return (Class<? extends Annotation>) klass;
        } catch (ClassNotFoundException e) {
            throw new CommonsSystemException("Could not load annotation class " + name, e);
        }
    }
}
