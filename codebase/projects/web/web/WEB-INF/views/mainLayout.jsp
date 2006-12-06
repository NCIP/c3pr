<%@ include file="Includes/taglibs.jsp" %>
<%@ include file="Includes/imports.jsp" %>

<html>
<head>
  <title>
	<% String applicationTitlePrefix = SystemManager.getConfigurationManager().getValue(ConfigurationManager.APPLICATION_TITLE_PREFIX);%>
	<%=applicationTitlePrefix%> : <tiles:getAsString name="title"/>
  </title>
  <link rel="stylesheet" type="text/css" href="StyleSheet/NCIstyleSheet.css" />
  <script src="JavaScript/MenuScript.js" type="text/javascript"></script>  
</head>

<body>
<table summary="" cellpadding="0" cellspacing="0" border="0" width="100%" height="100%">
  <tr>
    <td>
      <tiles:insert attribute="nciheader"/>
    </td>
  </tr>      
  <tr>
    <td height="100%" align="center" valign="top">
      <table summary="" cellpadding="0" cellspacing="0" border="0" height="100%" width="771">
				<tr>
					<td height="50">        
            <tiles:insert attribute="appheader"/>
					</td>            
				</tr>
        <tr>          
          <td valign="top">
            <table summary="" cellpadding="0" cellspacing="0" border="0" height="100%" width="100%">
            	<tr>
                <td height="20" width="100%" class="mainMenu">              
                  <tiles:insert attribute="menu"/>  
      					</td>                  
            	</tr>
              <!--tiles:insert attribute="c3prlogo"/-->              
              <tr>
              	<td valign="top">              
                  <tiles:insert attribute="body"/>
                </td>
              </tr>  
              <tr>
                <td height="20" class="footerMenu">              
                  <tiles:insert attribute="appfooter"/>
                </td>                  
              </tr>              
            </table>
          </td>          
        </tr>
      </table>
    </td>    
  </tr>  
  <tr>
    <td>  
      <tiles:insert attribute="footer"/>
    </td>      
  </tr>    
</table>
</body>
</html>

