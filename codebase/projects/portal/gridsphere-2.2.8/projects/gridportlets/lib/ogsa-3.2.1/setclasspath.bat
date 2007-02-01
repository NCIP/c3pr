@echo off
if "%OS%" == "Windows_NT" setlocal
rem ---------------------------------------------------------------------------
rem Bootstraps the OGSA 3.2.1 jars in order to support the use of the "httpg"
rem protocol in the GrmsJobSubmissionService and other httpg client code.
rem ---------------------------------------------------------------------------

call "%CATALINA_HOME%\bin\setclasspath-catalina.bat"

rem ---------------------------------------------
rem Grid Portlets Httpg Bootstrapping Jars
rem ---------------------------------------------

rem For TOMCAT 4.1.X...
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\common\lib\activation.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\common\lib\ant.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\common\lib\commons-collections.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\common\lib\commons-dbcp-1.1.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\common\lib\commons-logging-api.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\common\lib\commons-pool-1.1.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\common\lib\jasper-compiler.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\common\lib\jasper-runtime.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\common\lib\jdbc2_0-stdext.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\common\lib\jndi.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\common\lib\jta.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\common\lib\mail.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\common\lib\naming-common.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\common\lib\naming-factory.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\common\lib\naming-resources.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\common\lib\servlet.jar

set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\common\endorsed\xercesImpl.jar

set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\catalina-ant.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\catalina.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\commons-beanutils.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\commons-digester.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\commons-fileupload-1.0.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\commons-logging.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\commons-modeler.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\jaas.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\jakarta-regexp-1.3.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\mx4j-jmx.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\servlets-common.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\servlets-default.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\servlets-invoker.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\servlets-manager.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\servlets-webdav.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\tomcat-coyote.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\tomcat-http11.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\tomcat-jk.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\tomcat-jk2.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\tomcat-util.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\tomcat-warp.jar

rem For TOMCAT 5.0.X...
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\common\lib\ant-launcher.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\common\lib\ant.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\common\lib\commons-collections.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\common\lib\commons-dbcp-1.1.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\common\lib\commons-el.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\common\lib\commons-pool-1.2.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\common\lib\jasper-compiler.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\common\lib\jasper-runtime.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\common\lib\jdbc2_0-stdext.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\common\lib\jndi.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\common\lib\jta.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\common\lib\jsp-api.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\common\lib\naming-common.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\common\lib\naming-factory.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\common\lib\naming-java.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\common\lib\naming-resources.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\common\lib\servlet-api.jar

set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\common\endorsed\xercesImpl.jar

set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\catalina-ant.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\catalina-cluster.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\catalina-i18n-es.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\catalina-i18n-fr.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\catalina-i18n-ja.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\catalina-optional.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\catalina.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\commons-beanutils.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\commons-digester.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\commons-fileupload-1.0.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\commons-modeler.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\jakarta-regexp-1.3.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\jkconfig.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\jkshm.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\servlets-cgi.renametojar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\servlets-common.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\servlets-default.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\servlets-invoker.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\servlets-manager.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\servlets-ssi.renametojar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\servlets-webdav.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\tomcat-coyote.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\tomcat-http11.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\tomcat-jk.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\tomcat-jk2.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\tomcat-util.jar

rem OGSA 3.2.1 Endorsed Jars
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME\common\endorsed\gridportlets-ogsa-3.2.1-xalan.jar

rem OGSA 3.2.1 Common Jars
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME\common\lib\gridportlets-ogsa-3.2.1-axis.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME\common\lib\gridportlets-ogsa-3.2.1-cog-axis.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME\common\lib\gridportlets-ogsa-3.2.1-cog-jglobus.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME\common\lib\gridportlets-ogsa-3.2.1-cog-tomcat.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME\common\lib\gridportlets-ogsa-3.2.1-commons-discovery.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME\common\lib\gridportlets-ogsa-3.2.1-commons-logging.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME\common\lib\gridportlets-ogsa-3.2.1-cryptix-asn1.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME\common\lib\gridportlets-ogsa-3.2.1-cryptix.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME\common\lib\gridportlets-ogsa-3.2.1-cryptix32.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME\common\lib\gridportlets-ogsa-3.2.1-jaxrpc.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME\common\lib\gridportlets-ogsa-3.2.1-jce-jdk13-120.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME\common\lib\gridportlets-ogsa-3.2.1-jgss.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME\common\lib\gridportlets-ogsa-3.2.1-log4j-1.2.8.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME\common\lib\gridportlets-ogsa-3.2.1-ogsa-samples.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME\common\lib\gridportlets-ogsa-3.2.1-ogsa.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME\common\lib\gridportlets-ogsa-3.2.1-puretls.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME\common\lib\gridportlets-ogsa-3.2.1-saaj.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME\common\lib\gridportlets-ogsa-3.2.1-wsdl4j.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME\common\lib\gridportlets-ogsa-3.2.1-wsif.jar
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME\common\lib\gridportlets-ogsa-3.2.1-xmlsec.jar

rem Grid Logon Server Jars
set CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\server\lib\gridportlets-anonymous-gsi.jar
