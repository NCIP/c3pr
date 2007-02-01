<ui:panel>
    <ui:frame>
        <ui:tablerow header="true">
            <ui:tablecell>
                <ui:text style="bold"value="Credential Info"/>
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
                    <ui:text style="bold" value="Certificate:"/>
                            </td>
                            <td width="*">
                    <ui:text beanId="dnText"/>
                            </td>
                        </tr>
                        <tr>
                            <td width="150" height="20">
                    <ui:text style="bold" value="User Name:"/>
                            </td>
                            <td width="*">
                    <ui:text beanId="userNameText"/>
                            </td>
                        </tr>
                        <tr>
                            <td width="150" height="20">
                    <ui:text style="bold" value="Credential Name:"/>
                            </td>
                            <td width="*">
                    <ui:text beanId="credentialNameText"/>
                            </td>
                        </tr>
                        <tr>
                            <td width="150" height="20">
                    <ui:text style="bold" value="Credential Lifetime:"/>
                            </td>
                            <td width="*">
                    <ui:text beanId="lifetimeText"/>&nbsp;
                    <ui:text value="(in seconds)"/>
                            </td>
                        </tr>
                        <tr>
                            <td width="150" height="20">
                    <ui:text style="bold" value="Credential Label:"/>
                            </td>
                            <td width="*">
                    <ui:text beanId="credentialLabelText"/>
                            </td>
                        </tr>
                    </table>
                </ui:text>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>
</ui:panel>
