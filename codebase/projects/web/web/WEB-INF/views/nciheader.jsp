<%@ include file="Includes/taglibs.jsp" %>
<%@ include file="Includes/imports.jsp" %>
      <!-- NCI header begins -->
      	<%
			String headerLayout = SystemManager.getConfigurationManager().getValue(ConfigurationManager.NCI_ADOPTER);	
			
		%>	 
		
		<%
			if("NCI".equalsIgnoreCase(headerLayout)){
		%>
		      <table width="100%" border="0" cellspacing="0" cellpadding="0" class="hdrBG">
	        <tr>
	          <td width="283" height="37" align="left"><a href=<configuration:configure key='<%=ConfigurationManager.NCI_ADOPTER_URL%>'/>><img alt=<configuration:configure key='<%=ConfigurationManager.NCI_ADOPTER_LOGO_TEXT%>'/> src="Images/logotype.gif" width="295" height="37" border="0"></a></td>
	          <td>&nbsp;</td>
	          <td width="295" height="37" align="right"><a href=<configuration:configure key='<%=ConfigurationManager.NCI_ADOPTER_URL%>'/>><img alt=<configuration:configure key='<%=ConfigurationManager.NCI_ADOPTER_TAG_LINE_TEXT%>'/> src="Images/tagline.gif" width="295" height="37" border="0"></a></td>
	        </tr>
	      </table>

		
		<%
			}
		%>
	  <!-- NCI header ends -->      
      <!-- Lombardi Header begins -->
		<%
			if("LOMBARDI".equalsIgnoreCase(headerLayout)){
		%>
		      <table width="100%" border="0" cellspacing="0" cellpadding="0">
	        <tr>
	          <td  align="left"><a href=<configuration:configure key='<%=ConfigurationManager.NCI_ADOPTER_URL%>'/>><img alt=<configuration:configure key='<%=ConfigurationManager.NCI_ADOPTER_LOGO_TEXT%>'/> src="Images/logotype_lombardi2.gif"  border="0"></a></td>
			  <td  align="center"><a href=<configuration:configure key='<%=ConfigurationManager.NCI_ADOPTER_URL%>'/>><img alt=<configuration:configure key='<%=ConfigurationManager.NCI_ADOPTER_LOGO_TEXT%>'/> src="Images/logotype_lombardi3.gif"  border="0"></a></td>
	          <td   align="right"><a href=<configuration:configure key='<%=ConfigurationManager.NCI_ADOPTER_URL%>'/>><img alt=<configuration:configure key='<%=ConfigurationManager.NCI_ADOPTER_TAG_LINE_TEXT%>'/> src="Images/tagline_lombardi.gif"  border="0"></a></td>
	        </tr>
	      </table>

		
		<%
			}
		%>
		 
      <!-- Lombardi Header ends -->
       <!-- Chao Header begins -->
		<%
			if("CHAO".equalsIgnoreCase(headerLayout)){
		%>
		      <table width="100%" border="0" cellspacing="0" cellpadding="0">
	        <tr>
	          <td  align="left"><a href=<configuration:configure key='<%=ConfigurationManager.NCI_ADOPTER_URL%>'/>><img alt=<configuration:configure key='<%=ConfigurationManager.NCI_ADOPTER_LOGO_TEXT%>'/> src="Images/logotype_chao_left.gif"  border="0"></a></td>
			  <td  align="center"><a href=<configuration:configure key='<%=ConfigurationManager.NCI_ADOPTER_URL%>'/>><img alt=<configuration:configure key='<%=ConfigurationManager.NCI_ADOPTER_LOGO_TEXT%>'/> src="Images/logotype_chao_center.gif"  border="0"></a></td>
	        </tr>
	      </table>

		
		<%
			}
		%>
		 
      <!-- Chao Header ends -->