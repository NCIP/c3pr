package edu.duke.cabig.c3pr.test.system.steps;

import com.atomicobject.haste.framework.Step;
import gov.nci.nih.cagrid.tests.core.util.AntUtils;
import gov.nci.nih.cagrid.tests.core.util.EnvUtils;
import gov.nci.nih.cagrid.tests.core.util.GlobusHelper;

import java.io.File;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Feb 15, 2007
 * Time: 5:34:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class RegistrationGridServiceConfigStep extends Step {

    private GlobusHelper globus;
    private File gridDir;

    public RegistrationGridServiceConfigStep(GlobusHelper globus,File gridDir) {
        super();

        this.globus = globus;
        this.gridDir = gridDir;
    }

    public void runStep() throws Throwable {
        Properties sysProps = null;
        String[] envp = null;

        if (globus != null) {
            sysProps = new Properties();
            sysProps.setProperty("GLOBUS_LOCATION", globus.getTempGlobusLocation().toString());

            envp = new String[] {
                    "GLOBUS_LOCATION=" + globus.getTempGlobusLocation(),
            };

            envp = EnvUtils.overrideEnv(envp);
        }

        AntUtils.runAnt(gridDir,null,"deployGlobus",sysProps,envp);

    }
}
