<%--
  Copyright 2003 Sun Microsystems, Inc.  All rights reserved.
  PROPRIETARY/CONFIDENTIAL.  Use of this product is subject to license terms.
--%>
<%@ page import="javax.portlet.RenderRequest" %>
<%@ page import="javax.portlet.PortletPreferences" %>
<%@ page import="javax.portlet.RenderResponse" %>
<%@ page import="javax.portlet.PortletURL" %>
<%@ page import="com.sun.portal.portlet.samples.bookmarkportlet.BookmarkPortlet" %>
<%@ page import="com.sun.portal.portlet.samples.bookmarkportlet.Target" %>
<%@ page session="false" %>

<%

	RenderRequest pReq = 
	    (RenderRequest)request.getAttribute("javax.portlet.request");
	RenderResponse rRes = 
	    (RenderResponse)request.getAttribute("javax.portlet.response");

        PortletPreferences pref = pReq.getPreferences();
        String[] targets = pref.getValues(BookmarkPortlet.TARGETS, null);

        // Get the resource count
        String resourceCountString = Integer.toString(targets.length);

        // Get resource name and resource URL
        String resourceName = pReq.getParameter("resourceName");

        if (resourceName == null) {
            resourceName = "";
        }        
        String resourceURL = pReq.getParameter("resourceURL");	
    	if (resourceURL == null) {
            resourceURL = "";
        }

        // create the action URI and the render URI
        PortletURL actionURI = rRes.createActionURL(); 
        
        // Error message if there is any
        String errorMsg =
            pReq.getParameter(BookmarkPortlet.RENDER_PARAM_ERROR);        
        if (errorMsg != null) {          
%>
		<font size="+0" color="red">
         	<hr>
         	<b>
		<%= errorMsg %>
		</b>
		<br>
		</font>
<%
        }
%>

<form ACTION="<%= actionURI.toString() %>" TARGET="_parent" NAME="edit_form" METHOD=POST ENCTYPE="application/x-www-form-urlencoded"> 
<table BORDER=0 CELLPADDING=0 CELLSPACING=3 WIDTH="100%">
<tr>
<td WIDTH="100%" VALIGN=TOP>
<center>
<font SIZE="+2" FACE="sans-serif">


<table border=0 cellspacing=0 cellpadding=2 width="100%">
                          
  <tr>                
    <td bgcolor="#666699" colspan="2"><font face="sans-serif" color="#FFFFFF" size="+1"><b>Add a new bookmark:</b></font></td>
  </tr>
              
              
  <tr>           
    <td colspan="2">                 
      <input type=HIDDEN name="resourceCount" value="<%= resourceCountString  %>">              
      <input type=HIDDEN name="add_more" value="">
    </td>
  </tr>
              
  <tr>               
    <td align="right"><font face="sans-serif" size=+0><b><label for="resourcename">Bookmark Name:</label></b></font></td>
    <td><font face="Sans-serif" size=+0><input type=TEXT name="resourceName" size=45 value="<%= resourceName  %>" id="resourcename"></font></td>
  </tr>
              
  <tr>               
    <td align="right"><font face="sans-serif" size=+0><b><label for="url">URL:</label></b></font></td>
    <td><font face="Sans-serif" size=+0><input type=TEXT name="resourceURL" size=45 value="<%= resourceURL  %>" id="url"></font></td>
  </tr>
                       
</table>

<font face="sans-serif" size="+0"><input type=SUBMIT name="<%=BookmarkPortlet.ADD_RESOURCE%>" value="Add Resource" onClick="add_more.value='true';" ></font>

<br>
<br>

<table border=0 cellspacing=0 cellpadding=2 width="100%">
              
  <tr>                
    <td bgcolor="#666699"><font face="sans-serif" size="+1" color="#FFFFFF"><b>Edit an existing bookmark:</b></font></td>
  </tr>
              
  <tr>               
    <td>&nbsp;</td>
  </tr>
            
</table>
			
<table border=0 cellspacing=1 cellpadding=0 width="75%" align="center">

  <tr>
    <td align="center" bgcolor="#CCCCCC"><p><font face="sans-serif" size=+0><b>Remove</b></font></td>
    <td align="center" bgcolor="#CCCCCC"><p><font face="sans-serif" size=+0><b>Name</b></font></td>
    <td align="center" bgcolor="#CCCCCC"><p><font face="sans-serif" size=+0><b>URL</b></font></td>
  </tr>

<%
    	//construct the html for the [] NAME  URL listing 
        StringBuffer resourceList = new StringBuffer("");

    	for (int i = 0; i < targets.length; i++) {
    	    String targ = (String)targets[i];
    	    Target target = new Target(targ);
%>        
	    <TR>
  	    <TD>
    	        <CENTER>
                <INPUT TYPE="CHECKBOX" VALUE="1" NAME="remove<%= String.valueOf(i)%>">
            	</CENTER>
  	    </TD>
      
  	    <TD>
    	    	<FONT FACE="sans-serif" SIZE=-1>
            	<INPUT TYPE="TEXT" VALUE="<%= BookmarkPortlet.HTML_ENCODER.encode(target.getName()) %>" SIZE="40" NAME="name<%= String.valueOf(i) %>">
            	</FONT>
  	    </TD>             
  	    <TD>
    	    	<FONT FACE="sans-serif" SIZE=-1>
            	<INPUT TYPE="TEXT" RPROXY-NOPARSE VALUE="<%= BookmarkPortlet.HTML_ENCODER.encode(target.getValue()) %>" SIZE="40" NAME="url<%= String.valueOf(i)  %>">
            	</FONT>
            </TD>
  	    </TR>
<%
        }
    	String all_new_checked = new String( "" );
    	String one_new_checked = new String( "" );
    	String same_checked = new String( "" );

        // Get the window preference
        String wp = pref.getValue("windowPref", "");
    	if (wp.equals("all_new")){
    	    all_new_checked = "CHECKED";
    	}    
    	if (wp.equals("one_new")) {
    	    one_new_checked = "CHECKED";
    	}
    	if (wp.equals("same")) {
    	    same_checked = "CHECKED";
    	}
%>

  <tr>
    <td colspan=3><br><br>
    <font face="sans-serif" size=+0>
	<INPUT TYPE="RADIO" NAME="windowPref" VALUE="all_new" "<%= all_new_checked  %>" > "<%= BookmarkPortlet.OWN_WINDOW %>"
        <BR>
	<INPUT TYPE="RADIO" NAME="windowPref" VALUE="one_new" "<%= one_new_checked %>" > "<%= BookmarkPortlet.SINGLE_WINDOW %>" 
	<BR>
	<INPUT TYPE="RADIO" NAME="windowPref" VALUE="same" "<%= same_checked %>" > "<%= BookmarkPortlet.MAIN_WINDOW %>" 
	<BR>
    </font></td>
  </tr>
</table>

</center>
</td>
</tr>
</table>
<br>
<center>
<font SIZE=+0 FACE="sans-serif">
<input TYPE=SUBMIT NAME="<%=BookmarkPortlet.FINISHED_EDIT%>" onClick="finish.value='true';" VALUE="Finished">
<input TYPE=SUBMIT NAME="<%=BookmarkPortlet.CANCEL_EDIT%>" onClick="cancel.value='true';" VALUE="Cancel">
</font>
</center>
<br>
<p>
</form>
<br>
