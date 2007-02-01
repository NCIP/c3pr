<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<portlet:defineObjects/>
<ui:hiddenfield beanId="srcCurrentHostField"/>
<ui:hiddenfield beanId="srcCurrentPathField"/>
<ui:hiddenfield beanId="dstCurrentHostField"/>
<ui:hiddenfield beanId="dstCurrentPathField"/>
<h3><ui:text value="Transfer Files" style="nostyle"/></h3>
<p><ui:text beanId="messageText"/></p>
<ui:table>
    <ui:tablerow>

        <ui:tablecell align="left" valign="top">
            <ui:text value="Destination" style="bold"/><br>
            <ui:group>
            <ui:table width="100%">
                <ui:tablerow>
                     <ui:tablecell>
                        <ui:text value="Destination Host" style="bold"/>
                    </ui:tablecell>
                    <ui:tablecell>
                        <ui:textfield beanId="dstFileHostField"/>
                    </ui:tablecell>
                </ui:tablerow>
                <ui:tablerow>
                    <ui:tablecell>
                        <ui:text value="Destination Path" style="bold"/>
                    </ui:tablecell>
                    <ui:tablecell>
                        <ui:textfield beanId="dstFilePathField"/>
                    </ui:tablecell>
                </ui:tablerow>
                <ui:tablerow>
                    <ui:tablecell valign="top">
                        <ui:text value="Destination Files" style="bold"/>
                    </ui:tablecell>
                    <ui:tablecell>
                        <ui:listbox size="10"/>
                    </ui:tablecell>
                </ui:tablerow>
            </ui:table>
            </ui:group>
        </ui:tablecell>

        <ui:tablecell align="left" valign="center" width="100">
            <ui:table>
                <ui:tablerow>
                    <ui:tablecell>
                        <ui:text beanId="transferText" style="bold"/>
                        <br>
                        <ui:text><b>&lt;&lt;&lt;&lt;&lt;&lt;&lt;&lt;&lt;</b></ui:text>
                    </ui:tablecell>
                </ui:tablerow>
            </ui:table>
        </ui:tablecell>

        <ui:tablecell align="left" valign="top">
            <ui:text value="Source" style="bold"/><br>
            <ui:group>
            <ui:table width="100%">
                <ui:tablerow>
                     <ui:tablecell>
                        <ui:text value="Source Host" style="bold"/>
                    </ui:tablecell>
                    <ui:tablecell>
                        <ui:textfield beanId="srcFileHostField"/>
                    </ui:tablecell>
                </ui:tablerow>
                <ui:tablerow>
                    <ui:tablecell>
                        <ui:text value="Source Path" style="bold"/>
                    </ui:tablecell>
                    <ui:tablecell>
                        <ui:textfield beanId="srcFilePathField"/>
                    </ui:tablecell>
                </ui:tablerow>
                <ui:tablerow>
                    <ui:tablecell valign="top">
                        <ui:text value="Source Files" style="bold"/>
                    </ui:tablecell>
                    <ui:tablecell>
                        <ui:listbox beanId="srcFilePathList"/>
                    </ui:tablecell>
                </ui:tablerow>
            </ui:table>
            </ui:group>
        </ui:tablecell>
    </ui:tablerow>
</ui:table>

<ui:table width="800">
    <ui:tablerow>
        <ui:tablecell>
            <ui:actionsubmit action="doTransferFilesApply" value="Apply"/>
            &nbsp;&nbsp;
            <ui:actionsubmit action="doTransferFilesCancel" value="Cancel"/>
        </ui:tablecell>
    </ui:tablerow>
</ui:table>




