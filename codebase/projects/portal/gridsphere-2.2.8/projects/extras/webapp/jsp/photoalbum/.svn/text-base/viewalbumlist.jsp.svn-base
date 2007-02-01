<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<jsp:useBean id="value" class="java.lang.String" scope="request"/>

<ui:form>
    <ui:frame>
    <ui:tablerow>
    <ui:tablecell height="2%"/>
    </ui:tablerow>
    </ui:frame>
   <ui:frame>
    <ui:tablerow>
    <ui:tablecell height="3%"/>
    <ui:tablecell>
    <ui:text value="Albums:"/>
    </ui:tablecell>
    </ui:tablerow>
    <ui:tablerow>
    <ui:tablecell width="5%"/>
    <ui:tablecell>
    <ui:listbox beanId="albumslist"/>
    </ui:tablecell>
    </ui:tablerow>
    </ui:frame>
    <ui:frame>
    <ui:tablerow>
    <ui:tablecell width="5%"/>
    <ui:tablecell>
    <ui:actionsubmit action="addPhotoAlbum" value="Add"/>
    <ui:actionsubmit action="deletePhotoAlbum" value="Delete"/>
    <ui:actionsubmit action="editPhotoAlbum" value="Edit"/>
    </ui:tablecell>
    </ui:tablerow>
    </ui:frame>
    <ui:frame>
<%
      String height ="5%";
      if(value.equals(new String("")))height="2%";
%>
        <ui:tablerow>
           <ui:tablecell height="<%= height%>"/>
           <ui:tablecell width="5%"/>
           <ui:tablecell>
               <ui:text value="<%= value%>"/>
           </ui:tablecell>
       </ui:tablerow>
    </ui:frame>
</ui:form>
