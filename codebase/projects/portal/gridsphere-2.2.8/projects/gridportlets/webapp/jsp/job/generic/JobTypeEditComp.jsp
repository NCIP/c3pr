<%@ page import="org.gridlab.gridsphere.services.ui.ActionComponent"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<portlet:defineObjects/>
    <ui:table>
        <ui:tablerow>
            <ui:tablecell>
                <ui:text>
                    Select the type of job you would like to submit and the service
                    you would like to use to submit the job.
                </ui:text>
            </ui:tablecell>
        </ui:tablerow>
    </ui:table>
<%--    <ui:messagebox beanId="messageBox"/>--%>
<%--    <br>--%>
    <ui:group label="Job Setup">
<%
    Integer jobProfileListSize = (Integer)ActionComponent.getPageAttribute(request, "jobProfileListSize", new Integer(1));
    Integer jobServiceListSize = (Integer)ActionComponent.getPageAttribute(request, "jobServiceListSize", new Integer(1));
%>
    <ui:table>
        <ui:tablerow>
            <% if (jobProfileListSize.intValue() > 1) { %>
            <ui:tablecell align="left" valign="top">
                <ui:table>
                    <ui:tablerow>
                        <ui:tablecell>
                            <ui:text><b>Type of job to submit</b></ui:text>
                        </ui:tablecell>
                    </ui:tablerow>
                    <ui:tablerow>
                        <ui:tablecell>
                            <ui:listbox beanId="jobProfileListBox" size="5"/>
                        </ui:tablecell>
                    </ui:tablerow>
                </ui:table>
            </ui:tablecell>
            <ui:tablecell width="100" align="left" valign="top">
                &nbsp;
            </ui:tablecell>
            <% } %>
            <% if (jobServiceListSize.intValue() > 1) { %>
            <ui:tablecell align="left" valign="top">
                <ui:table>
                    <ui:tablerow>
                        <ui:tablecell>
                            <ui:text><b>Job submission service</b></ui:text>
                        </ui:tablecell>
                    </ui:tablerow>
                    <ui:tablerow>
                        <ui:tablecell>
                            <ui:listbox beanId="jobServiceTypeListBox" size="2"/>
                        </ui:tablecell>
                    </ui:tablerow>
                </ui:table>
            </ui:tablecell>
            <% } %>
        </ui:tablerow>
    </ui:table>
    </ui:group>