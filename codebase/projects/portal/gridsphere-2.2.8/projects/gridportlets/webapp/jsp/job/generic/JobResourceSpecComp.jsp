<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<portlet:defineObjects/>
<ui:table>
    <ui:tablerow>
        <ui:tablecell width="150">
            <b><ui:text value="Job Resource" style="nostyle"/></b>
        </ui:tablecell>
        <ui:tablecell>
            <ui:listbox beanId="jobResourceList"/>
        </ui:tablecell>
    </ui:tablerow>
    <ui:tablerow>
        <ui:tablecell width="150">
            <b><ui:text value="Job Scheduler" style="nostyle"/></b>
        </ui:tablecell>
        <ui:tablecell>
            <ui:listbox beanId="jobSchedulerNameList"/>
        </ui:tablecell>
    </ui:tablerow>
    <ui:tablerow>
        <ui:tablecell width="150">
            <b><ui:text value="Job Queue" style="nostyle"/></b>
        </ui:tablecell>
        <ui:tablecell>
            <ui:textfield beanId="jobQueueNameField" size="49"/>
        </ui:tablecell>
    </ui:tablerow>
    <ui:tablerow>
        <ui:tablecell width="150">
            <b><ui:text value="CPU Count" style="nostyle"/></b>
        </ui:tablecell>
        <ui:tablecell>
            <ui:textfield beanId="cpuCountField" size="49"/>
        </ui:tablecell>
    </ui:tablerow>
    <ui:tablerow>
        <ui:tablecell width="150">
           <b><ui:text value="Minimum Memory" style="nostyle"/></b>
        </ui:tablecell>
        <ui:tablecell>
            <ui:textfield beanId="minMemoryField" size="49"/>
        </ui:tablecell>
    </ui:tablerow>
</ui:table>
