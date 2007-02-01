package org.gridlab.gridsphere.services.resources.gram;

import org.gridlab.gridsphere.services.job.JobType;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GramJobType.java,v 1.1.1.1 2007-02-01 20:41:07 kherm Exp $
 * <p>
 * Describes a gram job type.
 */

public class GramJobType extends JobType {

    public static final GramJobType INSTANCE = new GramJobType(GramJob.class, GramJobSpec.class);

    /**
     * Protected so only one instance can be created.
     * @param taskClass
     * @param taskSpecClass
     */
    protected GramJobType(Class taskClass, Class taskSpecClass) {
        super(taskClass, taskSpecClass);
    }
}
