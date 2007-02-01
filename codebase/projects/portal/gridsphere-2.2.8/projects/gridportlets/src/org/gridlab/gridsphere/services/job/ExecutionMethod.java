/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ExecutionMethod.java,v 1.1.1.1 2007-02-01 20:40:40 kherm Exp $
 * <p>
 * Describes a method for running an excecutable. Typical
 * choices include a single processs or as an mpi job.
 * A number of constants are provided here, while job
 * implemenations can provide their own custom methods
 * as constants. One can iterate through the constants with
 * iterateConstants().
 * <p>
 */
package org.gridlab.gridsphere.services.job;

import java.util.HashMap;
import java.util.Iterator;

public class ExecutionMethod {

    private static HashMap constants = new HashMap(4);

    public static final ExecutionMethod CONDOR = new ExecutionMethod("condor");
    public static final ExecutionMethod MPI = new ExecutionMethod("mpi");
    public static final ExecutionMethod MULTIPLE = new ExecutionMethod("multiple");
    public static final ExecutionMethod SINGLE = new ExecutionMethod("single");

    private String name;

    private ExecutionMethod(String name) {
        this.name = name;
        constants.put(name, this);
    }

    /**
     * Tests if this method is equal to the one given.
     * @param method The execution method
     * @return True if equals, false otherwise
     */
    public boolean equals(ExecutionMethod method) {
        return (name.equals(method.name));
    }

    /**
     * Tests if the string value of this method equals that given here.
     * @param method The method value
     * @return True if equals, false othewise
     */
    public boolean equals(String method) {
        return (name.equalsIgnoreCase(method));
    }

    public String getName() {
        return name;
    }

    public static Iterator iterateConstants() {
        return constants.values().iterator();
    }

    public static ExecutionMethod toExecutionMethod(String name) {
        ExecutionMethod constant =
                (ExecutionMethod) constants.get(name);
        if (constant == null) {
            return SINGLE;
        }
        return constant;
    }
}
