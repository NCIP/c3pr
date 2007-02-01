
<script language="JavaScript">
    function switchCategory() {
        newSelection = document.NOTEPAD.categoryList.selectedIndex;
        category = document.NOTEPAD.categoryList.options[newSelection].value;
<%
PortletURL changeCatURL = rRes.createRenderURL();
changeCatURL.setParameter( NotepadPortlet.PAGE_NO, "0" );
%>
        jumptoURL = "<%=changeCatURL%>"+"&category="+newSelection;
        document.location.href = jumptoURL;
    }
</script>

<b>Category:&nbsp;</b>
<select name=categoryList size=1 onchange="switchCategory();">
<% for( int i = 0; i < categories.length; i++ ) { %>
<%
  String cat = categories[i];
%>
  <% if( cat.equals(category) ) { %>
     <option value="<%= cat %>" selected=true><%= cat %>
  <% } else { %>
     <option value="<%= cat %>"><%= cat %>
  <% } %>
<% } %>
</select>

<table>
<% for( int i = mgr.getStartIndex(); i < mgr.getEndIndex(); i++ ) { %>

  <tr>
    <td><%= Integer.toString(i+1) %></td>
    <td><input type=checkbox name=<%=NotepadPortlet.LOCATION%> value=<%= i %>></td>

<% PortletURL url = rRes.createRenderURL();
   url.setWindowState( WindowState.MAXIMIZED );
   url.setParameter( NotepadPortlet.CATEGORY, URLEncoder.encode(category) );
   url.setParameter( NotepadPortlet.PAGE_NO, Integer.toString(pageNo) );
   url.setParameter( NotepadPortlet.NOTE_INDEX, Integer.toString(i) );
   url.setParameter( NotepadPortlet.MODE, "Edit" );
%>

    <td width=25% align=left><a href=<%=url.toString()%>><%= mgr.getTitle( i ) %></a></td>
    <td><%= mgr.getNote( i ) %></td>
  </tr>
<% } %>

  <tr>
    <td colspan=4 nowrap>
      <INPUT TYPE="SUBMIT" NAME="<%=NotepadPortlet.ADD_NOTE%>" VALUE="Add A New Note">
      <% if( mgr.getNoteCount() > 0 ) { %>
      <INPUT TYPE="SUBMIT" NAME="<%=NotepadPortlet.DELETE_NOTE%>" VALUE="Delete Selected Note">
      <% } %>
    </td>
  </tr>

<%
PortletURL addNewURL = rRes.createRenderURL();
addNewURL.setParameter( NotepadPortlet.MODE, "Add" ); 
addNewURL.setParameter( NotepadPortlet.CATEGORY, category ); 
addNewURL.setWindowState( WindowState.MAXIMIZED );
%>

  <tr>
    <td colspan=4 align=right>
<% if( mgr.hasPreviousPage() ) { %>
<%
PortletURL previousPageUrl = rRes.createRenderURL();
previousPageUrl.setParameter( NotepadPortlet.CATEGORY, URLEncoder.encode(category) );
previousPageUrl.setParameter( NotepadPortlet.PAGE_NO, Integer.toString(pageNo-1) );
%>
<a href=<%=previousPageUrl.toString()%>>&lt;previous</a>
<% } %>

<% if( mgr.hasNextPage() ) { %>
<%
PortletURL nextPageUrl = rRes.createRenderURL();
nextPageUrl.setParameter( NotepadPortlet.CATEGORY, URLEncoder.encode(category) );
nextPageUrl.setParameter( NotepadPortlet.PAGE_NO, Integer.toString(pageNo+1) );
%>
&nbsp;&nbsp;
<a href=<%=nextPageUrl.toString()%>>next&gt;</a>
<% } %>
    </td>
  </tr>
</table>

