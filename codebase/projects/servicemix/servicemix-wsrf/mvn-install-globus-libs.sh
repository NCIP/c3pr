#!/bin/sh

mvn install:install-file -Dfile=ext/lib/addressing-1.0.jar -DgroupId=globus.jars -DartifactId=addressing.jar -Dversion=4.0  -Dpackaging=jar -DgeneratePom=true

mvn install:install-file -Dfile=ext/lib/axis.jar -DgroupId=globus.jars -DartifactId=axis.jar -Dversion=4.0  -Dpackaging=jar -DgeneratePom=true

mvn install:install-file -Dfile=ext/lib/axis-url.jar -DgroupId=globus.jars -DartifactId=axis-url.jar -Dversion=4.0  -Dpackaging=jar -DgeneratePom=true

 
mvn install:install-file -Dfile=ext/lib/bootstrap.jar -DgroupId=globus.jars -DartifactId=bootstrap.jar -Dversion=4.0  -Dpackaging=jar -DgeneratePom=true

mvn install:install-file -Dfile=ext/lib/cog-axis.jar -DgroupId=globus.jars -DartifactId=cog-axis.jar -Dversion=4.0  -Dpackaging=jar -DgeneratePom=true

mvn install:install-file -Dfile=ext/lib/cog-jglobus.jar -DgroupId=globus.jars -DartifactId=cog-jglobus.jar -Dversion=4.0  -Dpackaging=jar -DgeneratePom=true

mvn install:install-file -Dfile=ext/lib/cog-tomcat.jar -DgroupId=globus.jars -DartifactId=cog-tomcat.jar -Dversion=4.0  -Dpackaging=jar -DgeneratePom=true

mvn install:install-file -Dfile=ext/lib/cog-url.jar -DgroupId=globus.jars -DartifactId=cog-url.jar -Dversion=4.0  -Dpackaging=jar -DgeneratePom=true

mvn install:install-file -Dfile=ext/lib/commonj.jar -DgroupId=globus.jars -DartifactId=commonj.jar -Dversion=4.0  -Dpackaging=jar -DgeneratePom=true

mvn install:install-file -Dfile=ext/lib/commons-beanutils.jar -DgroupId=globus.jars -DartifactId=commons-beanutils.jar -Dversion=4.0  -Dpackaging=jar -DgeneratePom=true

mvn install:install-file -Dfile=ext/lib/commons-cli-2.0.jar -DgroupId=globus.jars -DartifactId=commons-cli.jar -Dversion=4.0  -Dpackaging=jar -DgeneratePom=true

mvn install:install-file -Dfile=ext/lib/commons-collections-3.0.jar -DgroupId=globus.jars -DartifactId=commons.collections.jar -Dversion=4.0  -Dpackaging=jar -DgeneratePom=true

mvn install:install-file -Dfile=ext/lib/commons-digester.jar -DgroupId=globus.jars -DartifactId=commons-digester.jar -Dversion=4.0  -Dpackaging=jar -DgeneratePom=true

mvn install:install-file -Dfile=ext/lib/commons-discovery.jar -DgroupId=globus.jars -DartifactId=commons-discovery.jar -Dversion=4.0  -Dpackaging=jar -DgeneratePom=true

 mvn install:install-file -Dfile=ext/lib/concurrent.jar -DgroupId=globus.jars -DartifactId=concurrent.jar -Dversion=4.0  -Dpackaging=jar -DgeneratePom=true

mvn install:install-file -Dfile=ext/lib/globus_wsrf_servicegroup.jar -DgroupId=globus.jars -DartifactId=globus_wsrf_servicegroup.jar -Dversion=4.0  -Dpackaging=jar -DgeneratePom=true

mvn install:install-file -Dfile=ext/lib/globus_wsrf_servicegroup_stubs.jar -DgroupId=globus.jars -DartifactId=globus_wsrf_servicegroup_stubs.jar -Dversion=4.0  -Dpackaging=jar -DgeneratePom=true

mvn install:install-file -Dfile=ext/lib/jaxrpc.jar -DgroupId=globus.jars -DartifactId=jaxrpc.jar -Dversion=4.0  -Dpackaging=jar -DgeneratePom=true

 mvn install:install-file -Dfile=ext/lib/naming-java.jar -DgroupId=globus.jars -DartifactId=naming-java.jar -Dversion=4.0  -Dpackaging=jar -DgeneratePom=true

mvn install:install-file -Dfile=ext/lib/saaj.jar -DgroupId=globus.jars -DartifactId=saaj.jar -Dversion=4.0  -Dpackaging=jar -DgeneratePom=true

mvn install:install-file -Dfile=ext/lib/wsdl4j.jar -DgroupId=globus.jars -DartifactId=wsdl4j.jar -Dversion=4.0  -Dpackaging=jar -DgeneratePom=true

mvn install:install-file -Dfile=ext/lib/wsrf_core.jar -DgroupId=globus.jars -DartifactId=wsrf_core.jar -Dversion=4.0  -Dpackaging=jar -DgeneratePom=true

mvn install:install-file -Dfile=ext/lib/wsrf_core_stubs.jar -DgroupId=globus.jars -DartifactId=wsrf_core_stubs.jar -Dversion=4.0  -Dpackaging=jar -DgeneratePom=true

mvn install:install-file -Dfile=ext/lib/wss4j.jar -DgroupId=globus.jars -DartifactId=wss4j.jar -Dversion=4.0  -Dpackaging=jar -DgeneratePom=true


mvn install:install-file -Dfile=ext/lib/caGrid-1.0-core.jar -DgroupId=cagrid.jars -DartifactId=caGrid-1.0-core.jar -Dversion=1.0  -Dpackaging=jar -DgeneratePom=true

mvn install:install-file -Dfile=ext/lib/caGrid-1.0-metadata-security.jar -DgroupId=cagrid.jars -DartifactId=caGrid-1.0-metadata-security.jar -Dversion=1.0  -Dpackaging=jar -DgeneratePom=true

mvn install:install-file -Dfile=ext/lib/caGrid-1.0-ServiceSecurityProvider-client.jar -DgroupId=cagrid.jars -DartifactId=caGrid-1.0-ServiceSecurityProvider-client.jar -Dversion=1.0  -Dpackaging=jar -DgeneratePom=true

mvn install:install-file -Dfile=ext/lib/caGrid-1.0-ServiceSecurityProvider-common.jar -DgroupId=cagrid.jars -DartifactId=caGrid-1.0-ServiceSecurityProvider-common.jar -Dversion=1.0  -Dpackaging=jar -DgeneratePom=true

mvn install:install-file -Dfile=ext/lib/caGrid-1.0-ServiceSecurityProvider-stubs.jar -DgroupId=cagrid.jars -DartifactId=caGrid-1.0-ServiceSecurityProvider-stubs.jar -Dversion=1.0  -Dpackaging=jar -DgeneratePom=true

mvn install:install-file -Dfile=ext/lib/RegistrationConsumer-client.jar -DgroupId=cagrid.jars -DartifactId=RegistrationConsumer-client.jar -Dversion=1.0  -Dpackaging=jar -DgeneratePom=true

mvn install:install-file -Dfile=ext/lib/RegistrationConsumer-common.jar -DgroupId=cagrid.jars -DartifactId=RegistrationConsumer-common.jar -Dversion=1.0  -Dpackaging=jar -DgeneratePom=true

mvn install:install-file -Dfile=ext/lib/RegistrationConsumer-stubs.jar -DgroupId=cagrid.jars -DartifactId=RegistrationConsumer-stubs.jar -Dversion=1.0  -Dpackaging=jar -DgeneratePom=true



