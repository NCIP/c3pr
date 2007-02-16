package edu.duke.cabig.c3pr.test.system.steps;

import com.atomicobject.haste.framework.Step;
import edu.duke.cabig.c3pr.test.system.util.ESBHelper;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Feb 15, 2007
 * Time: 8:33:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class ESBStopStep extends Step {

    private ESBHelper esb;


    public ESBStopStep(ESBHelper esb) {
        this.esb = esb;
    }


    public void runStep() throws Throwable {
        esb.stopESB();
    }
}
