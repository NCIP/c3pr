<ui:panel>
    <ui:frame>
        <ui:tablerow header="true">
            <ui:tablecell>
                <ui:text style="bold" value="Credential Status"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>
    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:text>
                    <table border="0" cellspacing="0" cellpadding="1">
                        <tr>
                            <td width="150" height="20">
                    <ui:text style="bold" value="Credential Status:"/>
                            </td>
                            <td width="*">
                    <ui:text beanId="credentialStatusText"/>
                            </td>
                        </tr>
                        <tr>
                            <td width="150" height="20">
                    <ui:text style="bold" value="Time Remaining:"/>
                            </td>
                            <td width="*">
                    <ui:text beanId="remainingLifetimeText"/>
                            </td>
                        </tr>
                        <tr>
                            <td width="150" height="20">
                    <ui:text style="bold" value="Date Created:"/>
                            </td>
                            <td width="*">
                    <ui:text beanId="dateCreatedText"/>
                            </td>
                        </tr>
                        <tr>
                            <td width="150" height="20">
                    <ui:text style="bold" value="Last Retreived:"/>
                            </td>
                            <td width="*">
                    <ui:text beanId="dateLastRetrievedText"/>
                            </td>
                        </tr>
                    </table>
                </ui:text>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>
</ui:panel>
