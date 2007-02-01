<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<portlet:defineObjects/>
<%--<ui:messagebox beanId="messageBox"/>--%>
<ui:table>
    <ui:tablerow>

       <ui:tablecell valign="top" width="50%">
            <ui:table>
                <ui:tablerow header="true">
                    <ui:tablecell width="100%">
                        <b><ui:text value="Generic Application" style="nostyle"/></b>
                    </ui:tablecell>
                </ui:tablerow>
                <ui:tablerow>
                    <ui:tablecell width="*">
                        <ui:include page="/jsp/job/submission/components/JobApplicationSpecComp.jsp"/>
                    </ui:tablecell>
                </ui:tablerow>
            </ui:table>
        </ui:tablecell>

        <ui:tablecell valign="top" width="50%">
            <ui:table>
                <ui:tablerow header="true">
                    <ui:tablecell width="100%">
                        <b><ui:text value="Resource Requirements" style="nostyle"/></b>
                    </ui:tablecell>
                </ui:tablerow>
                <ui:tablerow>
                    <ui:tablecell width="*">
                        <ui:include page="/jsp/job/submission/components/JobResourceSpecComp.jsp"/>
                     </ui:tablecell>
                </ui:tablerow>
            </ui:table>
         </ui:tablecell>

    </ui:tablerow>

</ui:table>
