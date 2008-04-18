package edu.duke.cabig.c3pr.ant;

import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.apt.Filer;
import com.sun.mirror.declaration.AnnotationTypeDeclaration;
import com.sun.mirror.declaration.ClassDeclaration;
import com.sun.mirror.util.DeclarationFilter;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

import gov.nih.nci.cabig.ctms.CommonsSystemException;
import edu.duke.cabig.c3pr.UseCase;

/**
 * @author Rhett Sutphin
 * @author kherm manav.kher@semanticbits.com
 */
public class UseCaseTraceabilityAnnotationProcessor implements AnnotationProcessor {
    private AnnotationTypeDeclaration annotationDeclaration;
    private AnnotationProcessorEnvironment env;
    private Class<? extends Annotation> annotation;

    public UseCaseTraceabilityAnnotationProcessor(
        AnnotationProcessorEnvironment env, Class<? extends Annotation> useCasesAnnotation
    ) {
        this.env = env;
        this.annotationDeclaration
            = (AnnotationTypeDeclaration) env.getTypeDeclaration(useCasesAnnotation.getName());
        this.annotation = useCasesAnnotation;
    }

    public void process() {
        // mapping from FQ class names to annotated use cases
        Map<String, UseCase[]> useCases = new TreeMap<String, UseCase[]>();

        Collection<ClassDeclaration> classDecls
            = new DeclarationFilter().filter(
                env.getDeclarationsAnnotatedWith(annotationDeclaration), ClassDeclaration.class);
        for (ClassDeclaration classDecl : classDecls) {
            useCases.put(classDecl.getQualifiedName(), extractUseCases(classDecl));
        }

        RowWriter csv = createOutput();

        csv.write("Test case", "Use case major index", "Use case minor index", "Use case title");
        for (String className : useCases.keySet()) {
            for (UseCase useCase : useCases.get(className)) {
                csv.write(className, useCase.getMajor(), useCase.getMinor(), useCase.getTitle());
            }
        }
    }

    // this reflective hoo-hah is required because @interfaces can't implement/extend
    // other interfaces
    private UseCase[] extractUseCases(ClassDeclaration classDecl) {
        Annotation useCasesAnn = classDecl.getAnnotation(annotation);
        try {
            Method valueMethod = useCasesAnn.getClass().getMethod("value");
            Object result = valueMethod.invoke(useCasesAnn);
            if (!UseCase[].class.isAssignableFrom(result.getClass())) {
                throw new CommonsSystemException(useCasesAnn.getClass().getName()
                    + ".value() did not return an array of UseCase.  It returned "
                    + result + " (" + result.getClass().getName() + ')');
            }
            return (UseCase[]) result;
        } catch (NoSuchMethodException e) {
            throw new CommonsSystemException(
                "The annotation used with " + getClass().getSimpleName() + " must have a value() method.  "
                    + useCasesAnn.getClass().getName() + " does not.", e);
        } catch (IllegalAccessException e) {
            throw new CommonsSystemException("Unable to invoke the value method for " + useCasesAnn.getClass().getName(), e);
        } catch (InvocationTargetException e) {
            throw new CommonsSystemException("Unable to invoke the value method for " + useCasesAnn.getClass().getName(), e);
        }
    }

    private RowWriter createOutput() {
        RowWriter csv;
        String projectName = null;

          for (String keyName : env.getOptions().keySet())
         {
                 if (keyName.startsWith(UseCaseTraceabilityAnnotationProcessorFactory.PROJECT_NAME_OPT))
                {
                    projectName = keyName.substring(UseCaseTraceabilityAnnotationProcessorFactory.PROJECT_NAME_OPT.length());
                    break;
                }
            }
            if (projectName == null)
                projectName  = String.valueOf(Math.random());

        try {
            csv = new RowWriter(
                env.getFiler().createTextFile(Filer.Location.CLASS_TREE, "",
                    new File("use-case-trace-" + projectName + "-.csv"), null));
        } catch (IOException e) {
            throw new CommonsSystemException("Failed to create output file", e);
        }
        return csv;
    }

    private static class RowWriter {
        private PrintWriter csv;

        public RowWriter(PrintWriter csv) {
            this.csv = csv;
        }

        public void write(Object... columns) {
            for (int i = 0; i < columns.length; i++) {
                Object col = columns[i];
                csv.write(quote(col == null ? "" : col.toString()));
                if (i != columns.length - 1) {
                    csv.write(",");
                }
            }
            csv.write("\n");
        }

        private String quote(String s) {
            if (s.indexOf(',') < 0) return s;
            return new StringBuilder(s).insert(0, '"').append('"').toString();
        }
    }
}
