<%@ include file="Includes/taglibs.jsp" %>
<%@ include file="Includes/imports.jsp" %>
                <%
                     String reportsOn="mainMenuItem";
                     String protocolsOn="mainMenuItem";
                     String participantsOn="mainMenuItem";
                     String helpOn="mainMenuItem";
                     String linksOn="mainMenuItem";
                     String homeOn="mainMenuItem";

                     String reportsOver="mainMenuItemOver";
                     String protocolsOver="mainMenuItemOver";
                     String participantsOver="mainMenuItemOver";
                     String helpOver="mainMenuItemOver";
                     String linksOver="mainMenuItemOver";
                     String homeOver="mainMenuItemOver";

                     String menuItemOver = "mainMenuItemOn";
                     String menuOn = "mainMenuItemOn";

					 String userGroup = (String)request.getSession().getAttribute("userGroup");

                     String currentMod = request.getParameter("module")==null?"":request.getParameter("module");
                     if(currentMod != null && !currentMod.equalsIgnoreCase("") ) {
                        if ( currentMod.equalsIgnoreCase("reports") ){
                          reportsOn = menuOn;
                          reportsOver = menuItemOver;
                        }else if(currentMod.equalsIgnoreCase("participants")){
                           participantsOn = menuOn;
                           participantsOver = menuItemOver;
                        }else if(currentMod.equalsIgnoreCase("participantsView")){
                           participantsOn = menuOn;
                        }else if(currentMod.equalsIgnoreCase("protocols")){
                           protocolsOn = menuOn;
                           protocolsOver = menuItemOver;
                        }else if(currentMod.equalsIgnoreCase("help")){
                           helpOn = menuOn;
                           helpOver =  menuItemOver;
                        }else if(currentMod.equalsIgnoreCase("home")){
                           homeOn = menuOn;
                           homeOver =  menuItemOver;
                        }else if(currentMod.equalsIgnoreCase("links")){
                           linksOn = menuOn;
                           linksOver =  menuItemOver;
                        }                                          
                	}
                        
                %>
                <!--  main menu begins -->
                  <table summary="" cellpadding="0" cellspacing="0" border="0"  height="35" width="100%">
                    <tr>
                      <td height="30" valign="bottom">
                      <!-- tabs begins -->
                        <table summary="" cellpadding="0" cellspacing="0" border="0" width="100%">
                          <tr>
                            <td width="1"><!-- anchor to skip main menu --><a href="#content"><img src="Images/shim.gif" alt="Skip Menu" width="1" height="1" border="0" /></a></td>
                            <td class="mainMenuSpacer">&nbsp;</td>
                            <secure:operation role="<%=userGroup%>" element="c3pr.Menu.Home" operation="ACCESS">
								<td height="20" class="<%=homeOn%>" 
								  onmouseover="changeMenuStyle(this,'<%=homeOver%>'),showCursor()" 
								  onmouseout="changeMenuStyle(this,'<%=homeOn%>'),hideCursor()">
								  <a class="mainMenuLink" href="loginHomeMenu.do">Home</a>
								</td>
                            </secure:operation>
                            <td class="mainMenuSpacer">&nbsp;</td>
                            <secure:operation role="<%=userGroup%>" element="c3pr.Menu.Participants " operation="ACCESS">
								<td height="20" class="<%=participantsOn%>"
								  onmouseover="changeMenuStyle(this,'<%=participantsOver%>'),showCursor()"
								  onmouseout="changeMenuStyle(this,'<%=participantsOn%>'),hideCursor()">
								  <a class="mainMenuLink" href="participantsMenu.do">Participants</a>
								</td>
							</secure:operation>                            
                            <td class="mainMenuSpacer">&nbsp;</td>
                            <secure:operation role="<%=userGroup%>" element="c3pr.Menu.Protocols" operation="ACCESS">                            
								<td height="20" class="<%=protocolsOn%>"
								  onmouseover="changeMenuStyle(this,'<%=protocolsOver%>'),showCursor()"
								  onmouseout="changeMenuStyle(this,'<%=protocolsOn%>'),hideCursor()">
								  <a class="mainMenuLink" href="protocolsMenu.do">Protocols</a>
								</td>
                            </secure:operation>
                            <td class="mainMenuSpacer">&nbsp;</td>
                            <secure:operation role="<%=userGroup%>" element="c3pr.Menu.Reports" operation="ACCESS">                                                        
								<td height="20" class="<%=reportsOn%>"
								  onmouseover="changeMenuStyle(this,'<%=reportsOver%>'),showCursor()"
								  onmouseout="changeMenuStyle(this,'<%=reportsOn%>'),hideCursor()">
								  <configuration:configure key='<%=ConfigurationManager.REPORTS_DESIGNATION%>'/>
								 </td>
                             </secure:operation>
                            <td class="mainMenuSpacer">&nbsp;</td>
                            <secure:operation role="<%=userGroup%>" element="c3pr.Menu.Links" operation="ACCESS">                             
								<td height="20" class="<%=linksOn%>"
								  onmouseover="changeMenuStyle(this,'<%=linksOver%>'),showCursor()"
								  onmouseout="changeMenuStyle(this,'<%=linksOn%>'),hideCursor()">
								  <a class="mainMenuLink" href="linksMenu.do">Links</a>
								</td>
							</secure:operation>
                            <td class="mainMenuSpacer">&nbsp;</td>
                            <secure:operation role="<%=userGroup%>" element="c3pr.Menu.Help" operation="ACCESS">                             
								<td height="20" class="<%=helpOn%>"
								  onmouseover="changeMenuStyle(this,'<%=helpOver%>'),showCursor()"
								  onmouseout="changeMenuStyle(this,'<%=helpOn%>'),hideCursor()">
								  <configuration:configure key='<%=ConfigurationManager.HELP_LINK%>'/>
								</td>
							</secure:operation>
                             <td width="99%" class="mainMenuSpacer">&nbsp;</td>
                          </tr>
                        </table>
                      <!-- tabs ends -->
                      </td>
        						</tr>
                    <tr>
                      <td height="5" class="mainMenuSub">
                      <!-- sub-items begin -->
                      <%if(currentMod.equalsIgnoreCase("participants")){%>
          				<table summary="" cellpadding="0" cellspacing="0" border="0"	height="100%">
                    			<tr>
                            <td class="mainMenuSubItems">&nbsp;&nbsp;</td>
                            <secure:operation role="<%=userGroup%>" element="Participants.Participants.Menu.Search.Sub-menu.Action" operation="Execute">                             
                             <td class="mainMenuSubItems"><a class="mainMenuSubLink" href="participantsMenu.do">Search</a></td>
              							</secure:operation>
                            <td class="mainMenuSubItemsSpacer"><img src="Images/mainMenuSeparator.gif" width="1" height="16" alt="" /></td>
                            <secure:operation role="<%=userGroup%>" element="Participants.Participants.Menu.Create.Sub-menu.Action" operation="Execute">                             
                             <td class="mainMenuSubItems"><a class="mainMenuSubLink" href="participantCreateMenu.do">Create</a></td>
              							</secure:operation>
          								</tr>
                        </table>
                        <%}else{%>
                        &nbsp
                        <%}%>
                      <!-- sub-items end -->
                      </td>
                    </tr>
                  </table>
                <!-- Participant main menu ends -->

