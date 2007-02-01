<% String errorCode = pReq.getParameter( NotepadPortlet.ERROR_CODE ); %>

<% if( errorCode != null && errorCode.length()>0 ) { %>
<table>
  <tr>
    <td align=center><font color=red>
<% if( errorCode.equals(NotepadPortlet.ERR_TITLE_EMPTY) ) { %>
  Title of the note is empty, note is not saved.
<% } else if( errorCode.equals(NotepadPortlet.ERR_BODY_EMPTY) ) { %>
  Body of the note is empty, note is not saved.
<% } else if( errorCode.equals(NotepadPortlet.ERR_CATEGORY_EMPTY) ) { %>
  Category name is empty, category is not added.
<% } else if( errorCode.equals(NotepadPortlet.ERR_CATEGORY_EXIST) ) { %>
  Category name already existed, category is not added.
<% } else if( errorCode.equals(NotepadPortlet.ERR_DISPLAY_MAX) ) { %>
  Invalid value for "Maximum no. of note per screen". Value entered must a positive number.
<% } else if( errorCode.equals(NotepadPortlet.ERR_UNKNOWN) ) { %>
  Unknown Error, please contact administrator.
<% } %>
    </font></td>
  </tr>
</table>
<% } %>
