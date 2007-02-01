<%--
  Copyright 2003 Sun Microsystems, Inc.  All rights reserved.
  PROPRIETARY/CONFIDENTIAL.  Use of this product is subject to license terms.
--%>

<%@ page import="java.util.StringTokenizer" %>
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

        String encodedChannelName = rRes.getNamespace();
        String windowPref = pref.getValue("windowPref", "");

%>

<SCRIPT LANGUAGE=JavaScript>	
      var parentWindow = typeof parentWin;
      var counter = 0;
      
      function <%= BookmarkPortlet.FORMNAME_ENCODER.encode(encodedChannelName) %>_openURL(url){

               if (url.charAt(0) != '/') {
			var re = /:\/\//;
			var found = url.search(re);

			if (found == -1) {
				url = "http://" + url;
			}
		}

 		var surf_form_URL = url;

		counter++;
      
		var urlWin;
		var windowID = "";
		
		var windowOption = "<%= windowPref %>";
		
		if( windowOption == "all_new" ){
			windowID = "Webtop_url_number"+counter;
			urlWin = window.open( surf_form_URL, windowID );
			urlWin.focus();
		}
      		else if( windowOption == "one_new" ){
			windowID = "Webtop_urls";
			urlWin = window.open( surf_form_URL, windowID );
			urlWin.focus();
		}
		else if( windowOption == "same" ){
			if (parentWindow != "undefined" && parentWindow != null){
				parentWindow.location = url;
				parentWindow.focus(); 
			} else {
				location = url;
			}
		}
	}

       function <%= BookmarkPortlet.FORMNAME_ENCODER.encode(encodedChannelName) %>_findWindow( url ){	
		
		counter++;
      
		var urlWin;
		var windowID = "";
		
		var windowOption = "<%= windowPref %>";
		if( windowOption == "all_new" ){
			windowID = "Webtop_url_number"+counter;
			urlWin = window.open( url, windowID );
			urlWin.focus();
		}
      		else if( windowOption == "one_new" ){
			windowID = "Webtop_urls";
			urlWin = window.open( url, windowID );
			urlWin.focus();
		}
		else if( windowOption == "same" ){
			if (parentWindow != "undefined" && parentWindow != null){
				parentWindow.location = url;
				parentWindow.focus(); 
			} else {
				location = url;
			}
		}
	}

</SCRIPT>
<TABLE>
<TR>
<TD>
<FORM ACTION="" onSubmit="javascript: <%= BookmarkPortlet.FORMNAME_ENCODER.encode(encodedChannelName) %>_openURL(document.<%= BookmarkPortlet.FORMNAME_ENCODER.encode(encodedChannelName) %>.url.value); return false;" NAME=<%= BookmarkPortlet.FORMNAME_ENCODER.encode(encodedChannelName) %>>
<B><LABEL FOR="url">Enter URL Below:</LABEL></B><BR>
<INPUT TYPE=TEXT NAME="url" SIZE="25" VALUE="" ID="url">
<BR>
</FORM> 
</TD>
</TR>
<% for (int i=0; i<targets.length;i++) {
	String targ = (String)targets[i];
        Target target = new Target(targ);

        String encodedName =
	    BookmarkPortlet.HTML_ENCODER.encode(target.getName());
        String encodedLink = 
	    BookmarkPortlet.HTML_ENCODER.encode(target.getValue());

        StringBuffer windowName = new StringBuffer();
        StringTokenizer tokens = 
            new StringTokenizer(target.getName(), 
                                " (),=+#[]@<>$%./!");

        while (tokens.hasMoreTokens()) {
            windowName.append((String)tokens.nextToken());
            if (tokens.hasMoreElements()) {
                windowName.append("_");
            }
        }
%>

 <TR>
    <TD> 
          <A HREF="<%= encodedLink %>" target="<%= windowName.toString() %>" onClick="javascript:<%= BookmarkPortlet.FORMNAME_ENCODER.encode(encodedChannelName) %>_findWindow('<%= encodedLink %>');return false;" >
          <%= encodedName %>
          </A>
    </TD> 
</TR> 
           
<%
    }
%>

<BR>
</TABLE>
