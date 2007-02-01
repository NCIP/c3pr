    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:group>
                    <table border="0" cellspacing="0" cellpadding="1">
                        <tr>
                            <td>
                    <ui:text style="bold" value="Label:"/><ui:text style="alert" value="*"/>
                            </td>
                            <td>
                    <ui:textfield beanId="credentialLabelField" size="20"/>
                    &nbsp;
                    <ui:text value="(Required: Label to display for credential in portal)"/>
                            </td>
                        </tr>
                        <tr>
                            <td width="150">
                    <ui:text style="bold" value="User Name:"/><ui:text style="alert" value="*"/>
                            </td>
                            <td width="100">
                    <ui:textfield beanId="userNameField" size="20"/>
                    &nbsp;
                    <ui:text value="(Required: <code>-l</code> or <code>--username</code> option to <code>myproxy-init</code>)"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                    <ui:text style="bold" value="Credential Name:"/>
                            </td>
                            <td>
                    <ui:textfield beanId="credentialNameField" size="20"/>
                    &nbsp;
                    <ui:text value="(Optional: <code>-k</code> or <code>--credname</code> option to <code>myproxy-init</code>)"/>
                            </td>
                        </tr>
                        <tr>
                            <td width="150">
                    <ui:text style="bold" value="Credential Lifetime:"/>
                            </td>
                            <td width="100">
                    <ui:textfield beanId="lifetimeField" size="20"/>&nbsp;
                    <ui:text value="(in seconds)"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                    <ui:text style="bold" value="Passphrase:"/><ui:text style="alert" value="*"/>
                            </td>
                            <td>
                    <ui:password beanId="passphrase" size="20"/>
                    &nbsp;
                    <ui:text value="(Required: Your credential repository password)"/>
                            </td>
                        </tr>
                    </table>
                </ui:group>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>
