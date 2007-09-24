
This is the skeleton Registration consumer grid service intended for CCTS
applications that will comsumer C3PR Registration messages. CCTS applications
need to provide an implementation for

gov.nih.nci.ccts.grid.common.RegistrationConsumer interface

The implementation class is injected at runtime using Spring.
Please see the c3pr sample implementation for details

http://gforge.nci.nih.gov/plugins/scmcvs/cvsweb.php/c3prv2/codebase/projects/grid/RegistrationConsumerImpl/?cvsroot=c3prv2


Prerequisits:
=======================================
Java 1.5 and JAVA_HOME env defined
Ant 1.6.5 and ANT_HOME env defined
Tomcat 5.0.28 installed and "CATALINA_HOME" env defined with globus deployed to it
Globus 4.0.3 installed and GLOBUS_LOCATION env defined
Globus 4.0.3 is deployed in Tomcat at the following location ${CATALINA_HOME}/webapps/wsrf

To Build:
=======================================
"ant all" will build 
"ant deployGlobus" will deploy to "GLOBUS_LOCATION"
"ant deployTomcat" will deploy to "CATALINA_HOME"

"ant -f build-ctms.xml deploySkeletonService" will build the skeleton grid
service in the build/RegistrationConsumer_Grid_Service.zip.

You can unzip the RegistrationConsumer_Grid_Service.zip file in the
${CATALINA_HOME} directory.

CCTS teams will then provide the implementation of this skeleton grid service
by providing the implementation classes in the classpath for the globus deployment
in ${CATALINA_HOME}/webapps/wsrf