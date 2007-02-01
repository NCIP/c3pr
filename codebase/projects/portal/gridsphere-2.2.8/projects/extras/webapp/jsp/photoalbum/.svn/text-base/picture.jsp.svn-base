<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<jsp:useBean id="value2" class="java.lang.String" scope="request"/>

<%@ include file="/jsp/photoalbum/viewalbumlist.jsp" %>

 <ui:form>
    <ui:frame>
          <ui:tablerow>
            <ui:tablecell width="5%"/>
            <ui:tablecell>
                <ui:text value="<%= value2%>"/>
            </ui:tablecell>
        </ui:tablerow>
       <ui:tablerow>
           <ui:tablecell width="5%"/>
           <ui:tablecell>
               <ui:actionsubmit action="yes" value="Yes"/>
               <ui:actionsubmit action="showAlbumList" value="Cancel"/>
           </ui:tablecell>
         </ui:tablerow>
      </ui:frame>
</ui:form>