Welcome to the C3PR Project!
=====================================

C3PR is an Open Source project that is a web-based application used for end-to-end registration of patients in clinical trials.

It is written in Java using a number of Open Source technologies, such as Tomcat, Spring, and Postgres.

The goal of C3PR is to provide clinical workflows that are enabled by both subject- and study-centric views into the registration process

C3PR is distributed under the BSD 3-Clause License.
Please see the NOTICE and LICENSE files for details.

You will find more details about <PROJECTNAME> in the following links:

    *  https://wiki.nci.nih.gov/display/C3PR/C3PR
    *  https://wiki.nci.nih.gov/display/caBIGCommunityCode/CBIIT+Open+Source+Development+Initiative
    *  https://cabig-kc.nci.nih.gov/CTMS/forums/viewforum.php?f=9&sid=341356a57f096cb3d65ec7b98fba6145
    *  https://tracker.nci.nih.gov/browse/SUITE
    *  https://wiki.nci.nih.gov/display/C3PR/C3PR+Documentation
    *  http://github.com/NCIP/c3pr

Please join us in further developing and improving C3PR.

Important Info to checkout, setup DB, run ant tasks, resolve dependencies, deploy
----------------------------------------------------------------------------------

1) Firstly checkout complete codebase folder from cvs to your local directory. If you're using
eclipse, make sure you checkout from cvs perspective as a "new project" so that the .project & 
.classpath files are read with the corresponding classpaths set by default for you. 

2) This build file is currently configured to work with Tomcat 5.5. Make sure you set your
CATALINA_HOME on your env path

3) The file codebase\projects\core\db\oracle\c3pr_ddl.sql - consists of the ddl for oracle schema
   Corresponding ddl for postgreSQL is present in 
   codebase\projects\core\db\PostGreSQL\c3pr_ddl.sql
  
   The property file 'database.properties' has the db setup properties. By default this has sample 
   connection setup to Oracle's DB @SemanticBits. Change this to your local DB and the build-core
   ant task will copy these properties, place it into datasource.properties for Spring's
   applicationContext to pick up.
  
4) There are 4 primary build files

c3pr\build.xml                 - the main build file
c3pr\artifacts\build.xml       - artifacts for resolving dependencies
c3pr\projects\core\build.xml   - build file for building core project
c3pr\projects\web\build.xml    - build file for building web project
.....
.....

If everything is checked out currectly with tomcat set: -

4.1) deploy-webapp : should compile everything from scratch and deploy core/web projects into a your 
<tomcat>\webapps\c3pr\*

4.2) build-core or build-xxx : If you just want to build individual projects. This builds the corresponding 
sub projects and placea a xxx.jar file in respective folder - jars\xxx

4.3) To run tests of sub projects - use test ant target from individual build files 
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
