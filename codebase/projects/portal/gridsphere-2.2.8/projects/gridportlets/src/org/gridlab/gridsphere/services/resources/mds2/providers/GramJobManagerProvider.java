/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GramJobManagerProvider.java,v 1.1.1.1 2007-02-01 20:41:21 kherm Exp $
 */
package org.gridlab.gridsphere.services.resources.mds2.providers;

import org.gridlab.gridsphere.services.resource.Resource;
import org.gridlab.gridsphere.services.resource.HardwareResource;
import org.gridlab.gridsphere.services.resource.ResourceRegistryService;
import org.gridlab.gridsphere.services.resource.ResourceException;
import org.gridlab.gridsphere.services.resources.gram.GramResource;
import org.gridlab.gridsphere.services.resources.gram.GramResourceType;
import org.gridlab.gridsphere.services.resources.gram.GramJobManager;
import org.gridlab.gridsphere.services.resources.mds2.Mds2ResourceProvider;
import org.gridlab.gridsphere.services.job.JobSchedulerType;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import javax.naming.directory.SearchControls;


/**
 dn: Mds-Software-deployment=jobmanager-pbs,Mds-Host-hn=peyote.aei.mpg.de, Mds
  -Vo-name=local,o=Grid
 Mds-Os-release: 2.4.18-27.7.x-perfctr
 Mds-Computer-manufacturer: pc
 Mds-Software-deployment: jobmanager-pbs
 Mds-Service-url: x-gram://peyote.aei.mpg.de:2119/jobmanager-pbs:unavailable a
  t time of install
 Mds-Service-port: 2119
 objectClass: Mds
 objectClass: MdsSoftware
 objectClass: MdsService
 objectClass: MdsServiceGram
 objectClass: MdsComputer
 objectClass: MdsOs
 Mds-Service-hn: peyote.aei.mpg.de
 Mds-keepto: 200405032033.23Z
 Mds-validto: 200405032033.23Z
 Mds-validfrom: 200405032032.53Z
 Mds-Service-Gram-schedulertype: pbs
 Mds-Computer-isa: i686
 Mds-Os-name: Linux
 Mds-Service-protocol: 0.1
 Mds-Service-type: x-gram
 */
public class GramJobManagerProvider extends Mds2ResourceProvider {

    private static PortletLog log = SportletLog.getInstance(GramJobManagerProvider.class);

    public GramJobManagerProvider(ResourceRegistryService resourceRegistry) {

        super(resourceRegistry);

        // Job queue provider
        addChildResourceProvider(new JobQueueProvider(resourceRegistry));
    }

    public Resource createResource() {
        GramJobManager gramJobManager = null;
        HardwareResource hardwareResource = (HardwareResource)parentProvider.getResource();

        log.debug("Retrieving gram resource on " + hardwareResource.getHost());
        GramResource gramResource = (GramResource)
                hardwareResource.getChildResource(GramResourceType.INSTANCE);
        if (gramResource == null) {
            gramResource = new GramResource();
            hardwareResource.addChildResource(gramResource);
            log.debug("Creating gram resource on " + hardwareResource.getHost());
            try {
                resourceRegistry.saveResource(hardwareResource);
            } catch (ResourceException e) {
                log.error("Unable to create gram resource ", e);
                return null;
            }
        }

        gramJobManager = gramResource.getGramJobManager(JobSchedulerType.PBS.getName());

        if (gramJobManager == null) {
            log.debug("Creating gram job manager " + resourceName + " on " + hardwareResource.getHost());
            gramJobManager = gramResource.createGramJobManager(resourceName);
            gramResource.putGramJobManager(gramJobManager);
            try {
                resourceRegistry.saveResource(hardwareResource);
            } catch (ResourceException e) {
                log.error("Unable to create gram job manager ", e);
                return null;
            }
        }

        return gramJobManager;
    }

    public String getResourceSearchDn() {
        return parentProvider.getResourceDn();
    }

    public String getResourceSearchFilter() {
        return "(objectclass=MdsServiceGram)";
    }

    public int getResourceSearchScope() {
        return SearchControls.ONELEVEL_SCOPE;
    }
}
