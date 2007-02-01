<% if( mode.equals("Add") ) { %>
Add new note to category:
<% } else { %>
Modify note from category:
<% } %>
&nbsp;&nbsp;&nbsp;<b><%= category %></b>

<%
String title = "";
String body ="";
String location = pReq.getParameter( NotepadPortlet.NOTE_INDEX );

if( mode.equals( "Edit" )) { 
  int loc = 0;
  try {
    loc = Integer.parseInt( location );
  } catch (Exception e ) {
  }
  title = mgr.getTitle( loc );
  body = mgr.getNote( loc );
}
%>

<table>
  <tr>
    <td><LABEL FOR="title">Title:</LABEL></td>
    <td><input type=text name=<%=NotepadPortlet.TITLE%> size=30 ID="title" value=<%=title%>/></td>
  </tr>
  <tr>
    <td><LABEL FOR="body">Body:</LABEL></td>
    <td><textarea name=<%=NotepadPortlet.BODY%> cols=30 rows=3 ID="body"><%=body%></textarea></td>
  </tr>
  <tr>
    <td colspan=2>
<% if( mode.equals("Add") ) { %>
<INPUT TYPE="SUBMIT" NAME="<%=NotepadPortlet.ADD%>" VALUE="Add"/>
<% } else { %>
<input type=hidden name=<%= NotepadPortlet.NOTE_INDEX %>  value="<%=location%>"/>
<INPUT TYPE="SUBMIT" NAME="<%=NotepadPortlet.SAVE%>" VALUE="Save"/>
<% } %>
<INPUT TYPE="SUBMIT" NAME="<%=NotepadPortlet.CANCEL_NOTE%>" VALUE="Cancel"/>
    </td>
  </tr>
</table>
