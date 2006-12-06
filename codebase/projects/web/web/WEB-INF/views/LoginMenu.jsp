<%@ include file="Includes/taglibs.jsp" %>
<%@ include file="Includes/imports.jsp" %>
<%
	 String homeOn="mainMenuItem";
     String helpOn="mainMenuItem";

     String homeOver="mainMenuItemOver";
     String helpOver="mainMenuItemOver";
                     
     String menuItemOver = "mainMenuItemOn";
     String menuOn = "mainMenuItemOn";
                     
     String currentMod = request.getParameter("module")==null?"":request.getParameter("module");
     if(currentMod != null && !currentMod.equalsIgnoreCase("") ) {
		
		if(currentMod.equalsIgnoreCase("home")){
           homeOn = menuOn;
           homeOver =  menuItemOver;
        }else if(currentMod.equalsIgnoreCase("help")){
           helpOn = menuOn;
           helpOver =  menuItemOver;
        }                                        
	}
%>       
  <table summary="" cellpadding="0" cellspacing="0" border="0" height="35" width="100%">
    <tr>
      <td height="30" valign="bottom">

        <!-- tabs begins -->
        <table summary="" cellpadding="0" cellspacing="0" border="0" width="100%">
          <tr>
            <td width="1"><!-- anchor to skip main menu --><a href="#content"><img src="Images/shim.gif" alt="Skip Menu" width="1" height="1" border="0" /></a></td>
			<td class="mainMenuSpacer">&nbsp;</td>
            <td height="20" class="<%=homeOn%>" onmouseover="changeMenuStyle(this,'<%=homeOver%>'),showCursor()" onmouseout="changeMenuStyle(this,'<%=homeOn%>'),hideCursor()">
              <a class="mainMenuLink" href="<%=ControllerUtil.getLoginPage()%>">Home</a>
            </td>
           	<td class="mainMenuSpacer">&nbsp;</td>
            <td height="20" class="<%=helpOn%>" 
            	onmouseover="changeMenuStyle(this,'<%=helpOver%>'),showCursor()" 
            	onmouseout="changeMenuStyle(this,'<%=helpOn%>'),hideCursor()">
				<configuration:configure key='<%=ConfigurationManager.HELP_WITHOUT_LINK%>'/>
            </td>
			<td width="99%" class="mainMenuSpacer">&nbsp;</td>
          </tr>
        </table>
        <!-- tabs ends -->
      </td>
    </tr>
    <tr>
      <td height="5" class="mainMenuSub">

        <!-- sub-items begin -->
        <table summary="" cellpadding="0" cellspacing="0" border="0" height="100%">
          <tr>
            <td class="mainMenuSubItems">&nbsp;&nbsp;</td>
          </tr>
        </table>
        <!-- sub-items end -->

      </td>
    </tr>
  </table>
