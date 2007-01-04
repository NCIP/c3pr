This is the C3PR V2 codebase.  Please see https://gforge.nci.nih.gov/plugins/wiki/index.php?id=214&type=g for more information.

Important Info to checkout, run ant tasks, resolve dependencies, deploy
------------------------------------------------------------------------

1) Firstly checkout complete codebase folder from cvs to your local directory. If you're using
eclipse, make sure you checkout from cvs perspective as a "new project" so that the .project & 
.classpath files are read with the corresponding classpaths set by default for you. 

2) This build file is currently configured to work with Tomcat 5.5. Make sure you set your
CATALINA_HOME on your env path

3) There are 4 primary build files

c3pr\build.xml                 - the main build file
c3pr\artifacts\build.xml       - artifacts for resolving dependencies
c3pr\projects\core\build.xml   - build file for building core project
c3pr\projects\web\build.xml    - build file for building web project
.....
.....

If everything is checked out currectly with tomcat set: -

1) deploy-webapp : should compile everything from scratch and deploy core/web projects into a your 
<tomcat>\webapps\c3pr\*

2) build-core or build-xxx : If you just want to build individual projects. This builds the corresponding 
sub projects and placea a xxx.jar file in respective folder - jars\xxx

3) To run tests of sub projects - use test ant target from individual build files 
(ie., c3pr\artifacts\build.xml)

 -> Run clean ant task in c3pr\build.xml as necessary

Do not try to run compile or build from individual build files (ie., c3pr\artifacts\build.xml)
as the dependencies on other projects will not have been resolved. The outer build does exactly that by
copying all dependency jars/files from other projects/share and place it in respective ext\lib folder

Troubleshooting
-------------------
-> If you get errors, make sure you have checked out the entire codebase inc all
sub projects under codebase\projects and codebase\share
-> try running undeploy from c3pr\projects\web\build.xml to cleanup your webapps\c3pr folder
