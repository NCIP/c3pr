/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileTaskSpec.java,v 1.1.1.1 2007-02-01 20:40:02 kherm Exp $
 */
package org.gridlab.gridsphere.services.file;

import org.gridlab.gridsphere.services.task.TaskSpec;

/*
 * Describes a specification for a file related task.
 * Typical file tasks include copying or moving files from one
 * location to another, listing the files in a particular
 * directory, or deleting files.
 */
public interface FileTaskSpec extends TaskSpec {

}
