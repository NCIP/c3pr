<%@ page import="org.gridlab.gridsphere.services.ui.file.FileBrowserComp,
                 org.gridlab.gridsphere.portlet.impl.SportletProperties"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<portlet:defineObjects/>
<% Boolean displayModeFlag = (Boolean)FileBrowserComp.getPageAttribute(request, "displayModeFlag", Boolean.FALSE); %>
<% if (displayModeFlag.booleanValue()) { %>
<ui:table>
    <ui:tablerow>
        <ui:tablecell>
            <div style="font-weight:bold">
            <ui:actionlink style="nostyle" action="doSetMode" value="Physical Files">
                <ui:actionparam name="modeParam" value="PHYSICAL_BROWSER_MODE"/>
            </ui:actionlink>
            </div>
        </ui:tablecell>
        <ui:tablecell width="10">
            &nbsp;
        </ui:tablecell>
        <ui:tablecell>
            <div style="font-weight:bold">
            <ui:actionlink style="nostyle" action="doSetMode" value="Logical Files">
                <ui:actionparam name="modeParam" value="LOGICAL_BROWSER_MODE"/>
            </ui:actionlink>
            </div>
        </ui:tablecell>
    </ui:tablerow>
</ui:table>
<br>
<% } %>
<ui:table>
    <ui:tablerow>
        <!-- File browser 1 -->
        <ui:tablecell width="400" align="left" valign="top">
          <ui:text><b>File Browser 1</b></ui:text>
            <ui:group>
            <ui:actioncomponent beanId="fb1"/>
            </ui:group>
        </ui:tablecell>
        <!-- Transfer buttons -->
        <ui:tablecell align="center" valign="middle" width="50">
            <ui:table>
                <ui:tablerow>
                    <ui:tablecell align="center" valign="middle">
                        <ui:actionsubmit action="doCopyFilesFb1Fb2" value="Copy >>"/>
                        <br>
                        <ui:actionsubmit action="doMoveFilesFb1Fb2" value="Move >>"/>
                        <br>
                    </ui:tablecell>
                </ui:tablerow>
                <ui:tablerow>
                    <ui:tablecell align="center" valign="middle">
                        <br>
                        <ui:actionsubmit action="doCopyFilesFb2Fb1" value="<< Copy"/>
                        <br>
                        <ui:actionsubmit action="doMoveFilesFb2Fb1" value="<< Move"/>
                    </ui:tablecell>
                </ui:tablerow>
            </ui:table>
        </ui:tablecell>
        <!-- File browser 2 -->
        <ui:tablecell align="left" valign="top">
            <ui:text><b>File Browser 2</b></ui:text>
            <ui:group>
            <ui:actioncomponent beanId="fb2"/>
            </ui:group>
        </ui:tablecell>
    </ui:tablerow>
</ui:table>
