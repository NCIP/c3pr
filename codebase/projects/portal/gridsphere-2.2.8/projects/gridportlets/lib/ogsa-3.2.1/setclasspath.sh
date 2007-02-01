#!/bin/sh
# -----------------------------------------------------------------------------
# Bootstraps the OGSA 3.2.1 jars in order to support the use of the "httpg"
# protocol in the GrmsJobSubmissionService and other httpg client code.
# -----------------------------------------------------------------------------

. "$CATALINA_HOME"/bin/setclasspath-catalina.sh

# ---------------------------------------------
# Grid Portlets Httpg Bootstrapping Jars
# ---------------------------------------------

# Must bootstrap all jars
for i in "$CATALINA_HOME"/common/lib/*.jar "$CATALINA_HOME"/common/endorsed/*.jar "$CATALINA_HOME"/server/lib/*.jar "$CATALINA_HOME"/shared/lib/activation.jar "$CATALINA_HOME"/shared/lib/mail.jar
do
CLASSPATH="$CLASSPATH":"$i"
done
