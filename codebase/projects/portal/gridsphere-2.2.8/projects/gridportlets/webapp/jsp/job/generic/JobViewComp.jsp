<%@ page import="org.gridlab.gridsphere.services.ui.ActionComponent,
                 org.gridlab.gridsphere.services.ui.job.JobComponent,
                 org.gridlab.gridsphere.services.job.JobType,
                 org.gridlab.gridsphere.services.ui.ActionComponent,
                 java.util.Map,
                 javax.portlet.PortletRequest"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<portlet:defineObjects/>
<%
    String jobOid = (String)ActionComponent.getPageAttribute(request, "jobOid");
    Map userInfo = (Map) request.getAttribute(PortletRequest.USER_INFO);
    String userName = (String)userInfo.get("user.name");
    System.out.println("userName=" + userName);
%>
<script LANGUAGE=JavaScript>
var portletReq;

// Generic AJAX function
function asynchGet(updateURL){
    if (window.XMLHttpRequest) {
        portletReq = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        portletReq = new ActiveXObject("Microsoft.XMLHTTP");
    }
    portletReq.onreadystatechange = processReqChange;
    portletReq.open("GET", updateURL, true);
    portletReq.send(null);
}

// Generic AJAX function
// process the response when available
function processReqChange() {
    if (portletReq.readyState == 4) {
        if (portletReq.status == 200) {
            // process response
            displayJobStdout();
        }
    }
}

function jobStdoutButton_onClick(evt) {
    asynchGet("/gridportlets/JobOutputServlet?jobOid=<%=jobOid%>&userName=<%=userName%>");
}

function displayJobStdout() {
    // substitute new invoice HTML content into "portletcontent" <div> tag
    var div = document.getElementById("portletcontent");
    div.innerHTML = "";
    div.innerHTML = portletReq.responseText;

}
</script>
<%--<ui:messagebox beanId="messageBox"/>--%>
    <!-- JOB ATTRIBUTES -->
    <ui:actioncomponent beanId="jobTaskBean"/>
    <br>
<%--
    <input type="button" name="jobStdoutButton" value="Stdout" onClick="jobStdoutButton_onClick(event)"/>
--%>
    <div id="portletcontent"></div>
    <!-- JOB PROFILE -->
    <ui:group width="100%">
        <ui:table>
            <ui:tablerow>
                <ui:tablecell>
                    <div style="font-weight:bold">
                    <ui:actionlink style="nostyle" action="doJobView" value="Job Spec">
                        <ui:actionparam name="jobOidParam" value="<%=jobOid%>"/>
                        <ui:actionparam name="jobProfileParam" value="JOB_SPEC_VIEW"/>
                    </ui:actionlink>
                    </div>
                </ui:tablecell>
                <ui:tablecell width="10">
                    &nbsp;
                </ui:tablecell>
                <ui:tablecell>
                    <div style="font-weight:bold">
                    <ui:actionlink style="nostyle" action="doJobView" value="Job String">
                        <ui:actionparam name="jobOidParam" value="<%=jobOid%>"/>
                        <ui:actionparam name="jobProfileParam" value="JOB_SPEC_STRING"/>
                    </ui:actionlink>
                    </div>
                </ui:tablecell>
                <ui:tablecell width="10">
                    &nbsp;
                </ui:tablecell>
                <ui:tablecell>
                    <div style="font-weight:bold">
                    <ui:actionlink style="nostyle" action="doJobView" value="Job Output">
                        <ui:actionparam name="jobOidParam" value="<%=jobOid%>"/>
                        <ui:actionparam name="jobProfileParam" value="JOB_OUTPUT"/>
                    </ui:actionlink>
                    </div>
                </ui:tablecell>
                <ui:tablecell width="10">
                    &nbsp;
                </ui:tablecell>
                <ui:tablecell>
                    <div style="font-weight:bold">
                    <ui:actionlink style="nostyle" action="doJobView" value="Job Location">
                        <ui:actionparam name="jobOidParam" value="<%=jobOid%>"/>
                        <ui:actionparam name="jobProfileParam" value="JOB_LOCATION"/>
                    </ui:actionlink>
                    </div>
                </ui:tablecell>
                <ui:tablecell width="*">
                    &nbsp;
                </ui:tablecell>
            </ui:tablerow>
        </ui:table>
        <br>
        <ui:actioncomponent beanId="jobDetailsBean"/>
    </ui:group>

