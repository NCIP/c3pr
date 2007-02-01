<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>


<jsp:useBean id="photo" class="org.gridlab.gridsphere.extras.services.photoalbum.Photo" scope="request"/>
<jsp:useBean id="next" class="java.lang.String" scope="request"/>
<jsp:useBean id="previous" class="java.lang.String" scope="request"/>
<jsp:useBean id="photoalbum" class="org.gridlab.gridsphere.extras.services.photoalbum.PhotoAlbum" scope="request"/>

<ui:form>
 <ui:frame>
     <ui:tablerow>
         <ui:tablecell width="510"/>
         <ui:tablecell>
             <ui:actionlink action="showPhotoAlbum" value="<%= photoalbum.getTitle()%>"/>
         </ui:tablecell>
         <ui:tablecell height="5%"/>
     </ui:tablerow>
</ui:frame>
<ui:frame>
        <ui:tablerow>
            <ui:tablecell width="360"/>
            <ui:tablecell width="140">
<%
        String value = "";
        if(previous.equals(new String("")))value="<< previous";
%>
             <ui:text value="<%= value%>"/>
              <ui:actionlink action="previousPhoto" value="<%= previous%>">
                     <ui:actionparam name="photoPrevID" value="<%= photo.getOid()%>"/>
             </ui:actionlink>
             </ui:tablecell>
             <ui:tablecell width="120">
              <ui:text value="<%= photoalbum.getNumPhoto(photo)+" of "+ String.valueOf(photoalbum.photolist.size())%>"/>
             </ui:tablecell>
             <ui:tablecell>
<%
       String value = "";
       if(next.equals(new String("")))value="next >>";
%>
             <ui:text value="<%= value%>"/>
              <ui:actionlink action="nextPhoto" value="<%= next%>">
                     <ui:actionparam name="photoNextID" value="<%= photo.getOid()%>"/>
             </ui:actionlink>
          </ui:tablecell>
       </ui:tablerow>
    </ui:frame>

    <ui:frame>
       <ui:tablerow>
          <ui:tablecell width="360"/>
          <ui:tablecell>
              <ui:actionlink>
               <ui:image src="<%= photo.getUrl()%>" title="<%= photo.getTitle()%>"/>
             </ui:actionlink>
           </ui:tablecell>
         </ui:tablerow>
      </ui:frame>
</ui:form>
