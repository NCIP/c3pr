<%@ include file="Includes/taglibs.jsp" %>
<%@ include file="Includes/imports.jsp" %>
 
                  <!-- target of anchor to skip menus --><a name="content" /></a>
                  <table summary="" cellpadding="0" cellspacing="0" border="0" class="contentPage" width="100%" height="100%">
										<tr>
											<td valign="top">
												<table summary="" cellpadding="0" cellspacing="0" border="0" height="100%" width="100%">
                          <tr>
                            <td valign="top" width="70%">

                              <!-- welcome begins -->
                              <table summary="" cellpadding="0" cellspacing="0" border="0" height="100%" width="100%">
                                <tr>
                                	<td class="welcomeTitle" height="20">WELCOME TO <configuration:configure key='<%=ConfigurationManager.APPLICATION_ABBR_TITLE%>'/></td>
                                </tr>
                                <tr>
						<td valign="top" align="left" class="welcomeContent"><br/>
						<b class="h3"><configuration:configure key='<%=ConfigurationManager.APPLICATION_LONG_TITLE%>'/> (<configuration:configure key='<%=ConfigurationManager.APPLICATION_ABBR_TITLE%>'/>)</b>
						<br/><configuration:configure key='<%=ConfigurationManager.HOME_APPLICATION_DESCRIPTION%>'/>
						</td>
					</tr>
                              </table>
                              <!-- welcome ends -->

                            </td>
                            <td valign="top" width="30%">

                              <!-- sidebar begins -->
                              <table summary="" cellpadding="0" cellspacing="0" border="0" height="100%">

                                <!-- login begins -->
                                <tr>
                                  <td valign="top">
                                    <table summary="" cellpadding="2" cellspacing="0" border="0" width="100%" class="sidebarSection">
                                      <tr>
                                        <td class="sidebarTitle" height="20">LOGIN TO <configuration:configure key='<%=ConfigurationManager.APPLICATION_ABBR_TITLE%>'/></td>
                                      </tr>
                                      <tr>
                                        <td class="sidebarContent">
                                        <html:form action="/login" onsubmit="return validateLoginForm(this);">
                                      
                                          <table cellpadding="2" cellspacing="0" border="0">
                                          	<tr>
												<td colspan=2><html:errors/></td>
											</tr>
                                            <tr>
                                              <td class="sidebarLogin" align="right"><bean:message key="prompt.login" /></td>
                                              <td class="formFieldLogin"><html:text property="login" size="14" tabindex="1" /></td>
                                            </tr>
                                            <tr>
                                              <td class="sidebarLogin" align="right"><bean:message key="prompt.password" /></td>
                                              <td class="formFieldLogin"><html:password property="mypassword" value="" size="14" tabindex="2" /></td>
                                            </tr>
                                            <tr>
                                              <td>&nbsp;</td>
                                              <td><html:submit><bean:message key="index.login" />&nbsp;</html:submit></td>
                                            </tr>
                                          </table>
                                             <html:hidden property="signedOn" value="NOSSO" />
                                          </html:form>
                                                                             
                                        </td>
                                      </tr>
                                    </table>
                                  </td>
                                </tr>
                                <!-- login ends -->

                                <!-- what's new begins -->
                                <tr>
                                  <td valign="top">
                                    <table summary="" cellpadding="0" cellspacing="0" border="0" width="100%" class="sidebarSection">
                                      <tr>
                                        <td class="sidebarTitle" height="20">WHAT'S NEW</td>
                                      </tr>
                                      <tr>
                                        <td class="sidebarContent"><configuration:configure key='<%=ConfigurationManager.HOME_WHATS_NEW%>'/></td>
                                      </tr>
                                    </table>
                                  </td>
                                </tr>
                                <!-- what's new ends -->

                                <!-- did you know? begins -->
                                <tr>
                                  <td valign="top">
                                    <table summary="" cellpadding="0" cellspacing="0" border="0" width="100%" height="100%" class="sidebarSection">
                                    <tr>
                                      <td class="sidebarTitle" height="20">DID YOU KNOW?</td>
                                    </tr>
                                    <tr>
                                      <td class="sidebarContent" valign="top"><configuration:configure key='<%=ConfigurationManager.HOME_DID_YOU_KNOW%>'/></td>
                                    </tr>
                                    </table>
                                  </td>
                                </tr>
                                <!-- did you know? ends -->

                                <!-- spacer cell begins (keep for dynamic expanding) -->
                                <tr><td valign="top" height="100%">
                                    <table summary="" cellpadding="0" cellspacing="0" border="0" width="100%" height="100%" class="sidebarSection">
                                    <tr>
                                      <td class="sidebarContent" valign="top">&nbsp;</td>
                                    </tr>
                                    </table>
																</td></tr>
                                <!-- spacer cell ends -->

                              </table>
                              <!-- sidebar ends -->

                            </td>
                          </tr>
                        </table>
                      </td>
										</tr>
									</table>
