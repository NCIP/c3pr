package edu.duke.cabig.c3pr.test.system.steps;

import com.atomicobject.haste.framework.Step;
import edu.duke.cabig.c3pr.test.system.util.ESBHelper;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Feb 15, 2007
 * Time: 7:46:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class ESBCreateStep extends Step {

   private ESBHelper esb;


    public ESBCreateStep(ESBHelper esb) {
        this.esb = esb;
    }

    public void runStep() throws Throwable {

        esb.createTempESB();


    }
}
