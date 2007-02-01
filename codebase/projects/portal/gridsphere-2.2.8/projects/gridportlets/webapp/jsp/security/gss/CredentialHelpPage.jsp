<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<portlet:defineObjects/>
<ui:form>
<ui:hiddenfield beanId="dnField"/>
<ui:panel>
    <ui:frame>
        <ui:tablerow header="true">
            <ui:tablecell>
                <ui:text style="bold" value="Delegating Your Credentials"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>
    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:text>
<p>
In order to use this portlet, you must first delegate your
Grid credentials to the credential repository at <b><ui:text beanId="credentialRepositoryText"/></b>.
For each certificate you own, you can delegate a credential with the MyProxy client
using a the following command:
</p>
<pre>
    <b>myproxy</b> -l <i>username</i> -k <i>credname</i> -s <i>server</i>
</pre>
</p>
<p>
<ul>
<li><i>username</i> is your myproxy usename</li>
<li><i>credname</i> is the name you assign the credential</li>
<li><i>server</i> is the fully qualified domain name of the myproxy server</li>
</ul>
</p>
<p>
So, for example, to delegate a credential with your <ui:text beanId="dnText"/> certificate, you would type
the following command:
<pre>
    <b>myproxy</b> -l <ui:text beanId="userNameText"/> -k <ui:text beanId="credentialNameText"/> -s <ui:text beanId="credentialRepositoryText"/>
</pre>
</p>
<p>
For more details consult the <a href="http://grid.ncsa.uiuc.edu/myproxy/">Myproxy site</a>.
</p>
                </ui:text>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>
</ui:panel>
<ui:panel>
    <ui:frame>
        <ui:tablerow header="true">
            <ui:tablecell>
                <ui:text style="bold" value="Retrieving Your Credentials"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>
    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:text>
<p>
Once you have delegated a credential to <ui:text beanId="credentialRepositoryText"/>,
you can set up that credential for retrieval by clicking
<b>New Credential</b>. Taking our previous example, you would enter the following
values into the given fields:
</p>
                    <table border="0" cellspacing="0" cellpadding="1">
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
</ui:form>
