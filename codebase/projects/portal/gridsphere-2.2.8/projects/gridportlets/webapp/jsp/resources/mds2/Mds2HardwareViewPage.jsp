<%@ page import="java.util.Iterator,
                 org.gridlab.gridsphere.services.resource.HardwareResource,
                 java.util.List,
                 org.gridlab.gridsphere.services.ui.ActionComponent,
                 org.gridlab.gridsphere.portlet.impl.SportletProperties,
                 org.gridlab.gridsphere.services.ui.resource.browser.ResourceComponent"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib uri="/oscache" prefix="oscache" %>
<portlet:defineObjects/>
<% String compId = (String) request.getAttribute(SportletProperties.GP_COMPONENT_ID);
   String key = compId + "md2HardwareResourceView_" + (String)ResourceComponent.getPageAttribute(request, "resourceOid", "0");
   Boolean refreshContent = (Boolean)ResourceComponent.getPageAttribute(request, "refreshContent", Boolean.FALSE); %>
<oscache:cache key="<%=key%>" refresh="<%=refreshContent.booleanValue()%>">
<h3><ui:text style="nostyle" beanId="hostLabelText"/></h3>
<!-- RESOURCE ATTRIBUTES -->
<ui:table width="100%">

    <ui:tablerow>

        <ui:tablecell valign="top" width="50%">

            <ui:table width="100%">
                <ui:tablerow>
                    <ui:tablecell>
                        <b><ui:text value="Hostname" style="nostyle"/></b>
                    </ui:tablecell>
                    <ui:tablecell>
                        <ui:text beanId="hostNameText"/>
                    </ui:tablecell>
                </ui:tablerow>

                <ui:tablerow>
                    <ui:tablecell width="150">
                        <b><ui:text value="Description" style="nostyle"/></b>
                    </ui:tablecell>
                    <ui:tablecell>
                        <ui:text beanId="hostDescriptionText"/>
                    </ui:tablecell>
                </ui:tablerow>


                <ui:tablerow>
                    <ui:tablecell>
                        <b><ui:text value="Platform" style="nostyle"/></b>
                    </ui:tablecell>
                    <ui:tablecell>
                        <ui:text beanId="computerPlatformText"/>
                    </ui:tablecell>
                </ui:tablerow>

                <ui:tablerow>
                    <ui:tablecell>
                        <b><ui:text value="CPU Vendor" style="nostyle"/></b>
                    </ui:tablecell>
                    <ui:tablecell>
                        <ui:text beanId="cpuVendorText"/>
                    </ui:tablecell>
                </ui:tablerow>
                <ui:tablerow>
                    <ui:tablecell>
                        <b><ui:text value="CPU Version" style="nostyle"/></b>
                    </ui:tablecell>
                    <ui:tablecell>
                        <ui:text beanId="cpuVersionText"/>
                    </ui:tablecell>
                </ui:tablerow>
                <ui:tablerow>
                    <ui:tablecell>
                        <b><ui:text value="CPU Model" style="nostyle"/></b>
                    </ui:tablecell>
                    <ui:tablecell>
                        <ui:text beanId="cpuModelText"/>
                    </ui:tablecell>
                </ui:tablerow>
                <ui:tablerow>
                    <ui:tablecell>
                        <b><ui:text value="CPU Features" style="nostyle"/></b>
                    </ui:tablecell>
                    <ui:tablecell>
                        <ui:text beanId="cpuFeaturesText"/>
                    </ui:tablecell>
                </ui:tablerow>

                <ui:tablerow>
                    <ui:tablecell>
                        <b><ui:text value="OS Name" style="nostyle"/></b>
                    </ui:tablecell>
                    <ui:tablecell>
                        <ui:text beanId="osNameText"/>
                    </ui:tablecell>
                </ui:tablerow>
                <ui:tablerow>
                    <ui:tablecell>
                        <b><ui:text value="OS Release" style="nostyle"/></b>
                    </ui:tablecell>
                    <ui:tablecell>
                        <ui:text beanId="osReleaseText"/>
                    </ui:tablecell>
                </ui:tablerow>
            </ui:table>

        </ui:tablecell>

        <ui:tablecell valign="top" width="50%">

            <ui:table width="100%">
                <ui:tablerow>
                    <ui:tablecell width="150">
                        <b><ui:text value="CPU Count" style="nostyle"/></b>
                    </ui:tablecell>
                    <ui:tablecell>
                        <ui:text beanId="cpuCountText"/>
                    </ui:tablecell>
                </ui:tablerow>
                <ui:tablerow>
                    <ui:tablecell>
                        <b><ui:text value="CPU Cache" style="nostyle"/></b>
                    </ui:tablecell>
                    <ui:tablecell>
                        <ui:text beanId="cpuCache12KBText"/> X 12kb
                    </ui:tablecell>
                </ui:tablerow>
                <ui:tablerow>
                    <ui:tablecell>
                        <b><ui:text value="CPU Speed" style="nostyle"/></b>
                    </ui:tablecell>
                    <ui:tablecell>
                        <ui:text beanId="cpuSpeedText"/>Mhz
                    </ui:tablecell>
                </ui:tablerow>

                <ui:tablerow>
                    <ui:tablecell>
                        <b><ui:text value="CPU Load 1 min" style="nostyle"/></b>
                    </ui:tablecell>
                    <ui:tablecell>
                        <ui:text beanId="cpuLoad1MinText"/>
                    </ui:tablecell>
                </ui:tablerow>
                <ui:tablerow>
                    <ui:tablecell>
                        <b><ui:text value="CPU Load 5 min" style="nostyle"/></b>
                    </ui:tablecell>
                    <ui:tablecell>
                        <ui:text beanId="cpuLoad5MinText"/>
                    </ui:tablecell>
                </ui:tablerow>
                <ui:tablerow>
                    <ui:tablecell>
                        <b><ui:text value="CPU Load 15 min" style="nostyle"/></b>
                    </ui:tablecell>
                    <ui:tablecell>
                        <ui:text beanId="cpuLoad15MinText"/>
                    </ui:tablecell>
                </ui:tablerow>

                <ui:tablerow>
                    <ui:tablecell>
                        <b><ui:text value="Memory Size" style="nostyle"/></b>
                    </ui:tablecell>
                    <ui:tablecell>
                        <ui:text beanId="memorySizeMBText"/>MB
                    </ui:tablecell>
                </ui:tablerow>
                <ui:tablerow>
                    <ui:tablecell>
                        <b><ui:text value="Memory Free" style="nostyle"/></b>
                    </ui:tablecell>
                    <ui:tablecell>
                        <ui:text beanId="memoryFreeMBText"/>MB
                    </ui:tablecell>
                </ui:tablerow>
                <ui:tablerow>
                    <ui:tablecell>
                        <b><ui:text value="Virtual Memory Size" style="nostyle"/></b>
                    </ui:tablecell>
                    <ui:tablecell>
                        <ui:text beanId="virtualMemorySizeMBText"/>MB
                    </ui:tablecell>
                </ui:tablerow>
                <ui:tablerow>
                    <ui:tablecell>
                        <b><ui:text value="Virtual Memory Free" style="nostyle"/></b>
                    </ui:tablecell>
                    <ui:tablecell>
                        <ui:text beanId="virtualMemoryFreeMBText"/>MB
                    </ui:tablecell>
                </ui:tablerow>
            </ui:table>

        </ui:tablecell>

    </ui:tablerow>

</ui:table>

<ui:table>

    <ui:tablerow>
        <ui:tablecell>
            <ui:image beanId="cpuLoadChart"/>
        </ui:tablecell>
        <ui:tablecell>
            <ui:image beanId="memoryChart"/>
        </ui:tablecell>
        <ui:tablecell>
            <ui:image beanId="virtualMemoryChart"/>
        </ui:tablecell>
    </ui:tablerow>

</ui:table>
</oscache:cache>
