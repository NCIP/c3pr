/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.test.system.steps;

import edu.duke.cabig.c3pr.test.system.util.ESBHelper;
import com.atomicobject.haste.framework.Step;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Feb 15, 2007
 * Time: 11:19:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class ESBCleanupStep extends Step {
    private ESBHelper esb;


    public ESBCleanupStep(ESBHelper esb) {
        this.esb = esb;
    }

    public void runStep() throws Throwable {
        esb.cleanupTempESB();
    }
}
