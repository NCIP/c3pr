<%@ page import="org.gridlab.gridsphere.services.ui.job.JobComponent,
                 org.gridlab.gridsphere.services.job.JobType,
                 org.gridlab.gridsphere.services.ui.job.JobComponent"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<portlet:defineObjects/>
<% JobType jobType = (JobType)JobComponent.getPageAttribute(request, "jobType", JobType.INSTANCE); %>
<% Boolean isReadOnly = (Boolean)JobComponent.getPageAttribute(request, "isReadOnly", Boolean.FALSE); %>
<ui:table>
    <ui:tablerow>
        <ui:tablecell>
            <b><ui:text value="Description" style="nostyle"/></b>
        </ui:tablecell>
        <ui:tablecell>
            <ui:textfield beanId="descriptionField" size="49"/>
        </ui:tablecell>
    </ui:tablerow>
<%  if (jobType.getSupportsDirectory()) { %>
    <ui:tablerow>
        <ui:tablecell width="150">
            <b><ui:text value="Directory" style="nostyle"/></b>
        </ui:tablecell>
<%      if (isReadOnly.booleanValue()) { %>
        <ui:tablecell>
            <ui:textfield beanId="directoryField" size="49"/>
        </ui:tablecell>
<%      } else { %>
        <ui:tablecell>
            <ui:textfield beanId="directoryField" size="39"/>
            &nbsp;
            <ui:actionsubmit action="doDirectoryBrowse" value="Browse"/>
        </ui:tablecell>
<%      } %>
    </ui:tablerow>
<%  } %>
    <ui:tablerow>
        <ui:tablecell>
            <b><ui:text value="Executable" style="nostyle"/></b>
        </ui:tablecell>
<%  if (isReadOnly.booleanValue()) { %>
        <ui:tablecell>
            <ui:textfield beanId="executableField" size="49"/>
        </ui:tablecell>
<%  } else { %>
        <ui:tablecell>
            <ui:textfield beanId="executableField" size="39"/>
            &nbsp;
            <ui:actionsubmit action="doExecutableFileBrowse" value="Browse"/>
        </ui:tablecell>
<%  } %>
    </ui:tablerow>
    <ui:tablerow>
        <ui:tablecell>
            <b><ui:text value="Executable Type" style="nostyle"/></b>
        </ui:tablecell>
        <ui:tablecell>
            <ui:listbox beanId="execMethodList"/>
        </ui:tablecell>
    </ui:tablerow>
    <ui:tablerow>
        <ui:tablecell>
            <b><ui:text value="Stdout" style="nostyle"/></b>
        </ui:tablecell>
<%  if (isReadOnly.booleanValue()) { %>
        <ui:tablecell>
            <ui:textfield beanId="stdoutField" size="49"/>
        </ui:tablecell>
<%  } else { %>
        <ui:tablecell>
            <ui:textfield beanId="stdoutField" size="39"/>
            &nbsp;
            <ui:actionsubmit action="doStdoutFileBrowse" value="Browse"/>
        </ui:tablecell>
<%  } %>
    </ui:tablerow>
    <ui:tablerow>
        <ui:tablecell>
            <b><ui:text value="Stderr" style="nostyle"/></b>
        </ui:tablecell>
<%  if (isReadOnly.booleanValue()) { %>
        <ui:tablecell>
            <ui:textfield beanId="stderrField" size="49"/>
        </ui:tablecell>
<%  } else { %>
        <ui:tablecell>
            <ui:textfield beanId="stderrField" size="39"/>
            &nbsp;
            <ui:actionsubmit action="doStderrFileBrowse" value="Browse"/>
        </ui:tablecell>
<%  } %>
    </ui:tablerow>
</ui:table>
<ui:table>
    <ui:tablerow>
        <ui:tablecell>
            <b><ui:text value="Arguments" style="nostyle"/></b>
        </ui:tablecell>
    </ui:tablerow>
    <ui:tablerow>
        <ui:tablecell>
            <ui:textarea beanId="argumentsArea" cols="60" rows="4"/>
        </ui:tablecell>
    </ui:tablerow>
</ui:table>
<ui:table>
    <ui:tablerow>
        <ui:tablecell width="100%">
            <b><ui:text value="Stage-In Files" style="nostyle"/></b>
        </ui:tablecell>
    </ui:tablerow>
    <ui:tablerow>
        <ui:tablecell>
            <ui:textarea beanId="stageInArea" cols="60" rows="4"/>
        </ui:tablecell>
    </ui:tablerow>
</ui:table>
<ui:table>
    <ui:tablerow>
        <ui:tablecell width="100%">
            <b><ui:text value="Stage-Out Files" style="nostyle"/></b>
        </ui:tablecell>
    </ui:tablerow>
    <ui:tablerow>
        <ui:tablecell>
            <ui:textarea beanId="stageOutArea" cols="60" rows="4"/>
        </ui:tablecell>
    </ui:tablerow>
</ui:table>
<% if (jobType.getSupportsCheckPointing()) { %>
<ui:table>
    <ui:tablerow>
        <ui:tablecell width="100%">
            <b><ui:text value="Checkpoint Files" style="nostyle"/></b>
        </ui:tablecell>
    </ui:tablerow>
    <ui:tablerow>
        <ui:tablecell>
            <ui:textarea beanId="checkpointArea" cols="60" rows="4"/>
        </ui:tablecell>
    </ui:tablerow>
</ui:table>
<% } %>
<ui:table>
    <ui:tablerow>
        <ui:tablecell width="100%">
            <b><ui:text value="Environment" style="nostyle"/></b>
        </ui:tablecell>
    </ui:tablerow>
    <ui:tablerow>
        <ui:tablecell>
            <ui:textarea beanId="environmentArea" cols="60" rows="4"/>
        </ui:tablecell>
    </ui:tablerow>
</ui:table>
