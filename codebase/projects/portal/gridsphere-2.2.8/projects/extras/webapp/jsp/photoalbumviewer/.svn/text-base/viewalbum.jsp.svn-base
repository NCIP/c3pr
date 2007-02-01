<%@ page import="org.gridlab.gridsphere.extras.services.photoalbum.Photo"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<jsp:useBean id="photoalbum" class="org.gridlab.gridsphere.extras.services.photoalbum.PhotoAlbum" scope="request"/>
<jsp:useBean id="value" class="java.lang.String" scope="request"/>
<jsp:useBean id="pageNum" class="java.lang.Object" scope="request"/>
<jsp:useBean id="userName" class="java.lang.String" scope="request"/>


 <%
     Integer pagenum = (Integer) pageNum;
     int numPhotos = photoalbum.photolist.size();
     String text1 = "";
     String text2 = "";
     String text3 = "";
     String text4 = "";
     String text5 = "";
     String value1 = "";
     String value2 = "";
     String value3 = "";
     String value4 = "";
     String value5 = "";
     String textPrev = "";
     String textNext = "";
     String valuePrev = "";
     String valueNext = "";

     if( pagenum.intValue() == 1){
         if(numPhotos > 10){
            text1 = "1";
            value2 = "2";
            textPrev = "<<";
            valueNext = ">>";
         }
         if(numPhotos > 20)value3 = "3";
         if(numPhotos > 30)value4 = "4";
         if(numPhotos > 40)value5 = "5";
     }
     if( pagenum.intValue() == 2){
         valuePrev = "<<";
         value1 = "1";
         text2 = "2";
         if(numPhotos >20){
              value3 = "3";
              valueNext = ">>";
         }
         else textNext=">>";
         if(numPhotos > 30)value4 = "4";
         if(numPhotos > 40)value5 = "5";
     }
     if(pagenum.intValue() == 3){
         valuePrev = "<<" ;
         value1 = "1";
         value2 = "2";
         text3 = "3";
         if(numPhotos > 30){
             value4 = "4";
             valueNext = ">>";
         }
         else textNext=">>";
         if(numPhotos > 40)value5 = "5";
     }
     if(pagenum.intValue() == 4){
         valuePrev = "<<";
         value1 = "1";
         value2 = "2";
         value3 = "3";
         text4 = "4";
         if(numPhotos > 40){
             value5 = "5";
             valueNext = ">>";
         }
         else textNext=">>";
     }
     if(pagenum.intValue() == 5){
         valuePrev = "<<";
         value1 = "1";
         value2 = "2";
         value3 = "3";
         value4 = "4";
         text5 = "5";
         textNext = ">>";
     }
     String widthNext = "100";
     if(textNext.equals(new String("")) && valueNext.equals(new String("")))widthNext = "";

     String val = "'s Album ";
     if(userName.equals(new String("")))val = "";

 %>
<ui:form>
 <ui:frame>
     <ui:tablerow>
         <ui:tablecell width="500"/>
         <ui:tablecell>
             <ui:text value="<%=userName +val+ photoalbum.getTitle()%>"/>
         </ui:tablecell>
     </ui:tablerow>
    </ui:frame>
  <ui:frame>
    <ui:tablerow>
        <ui:tablecell height="20"/>
    </ui:tablerow>
     <ui:tablerow>
          <ui:tablecell width="500"/>
         <ui:tablecell width="80">
             <ui:text value="<%= textPrev%>"/>
             <ui:actionlink action="previousPage" value="<%= valuePrev%>">
                 <ui:actionparam name="previousPage" value="<%= pagenum.toString()%>"/>
             </ui:actionlink>
         </ui:tablecell>
         <ui:tablecell width="<%= widthNext%>">
             <ui:text value="<%= text1%>"/>
             <ui:actionlink action= "showPage1" value="<%= value1%>"/>
             <ui:text value="<%= text2%>"/>
             <ui:actionlink action= "showPage2" value="<%= value2%>"/>
             <ui:text value="<%= text3%>"/>
             <ui:actionlink action= "showPage3" value="<%= value3%>"/>
             <ui:text value="<%= text4%>"/>
             <ui:actionlink action= "showPage4" value="<%= value4%>"/>
             <ui:text value="<%= text5%>"/>
             <ui:actionlink action= "showPage5" value="<%= value5%>"/>
         </ui:tablecell>
         <ui:tablecell>
             <ui:text value="<%= textNext%>"/>
             <ui:actionlink action="nextPage" value="<%= valueNext%>">
                  <ui:actionparam name="nextPage" value="<%= pagenum.toString()%>"/>
             </ui:actionlink>
         </ui:tablecell>
     </ui:tablerow>
     <ui:tablerow>
         <ui:tablecell width="150">
             <ui:text value="PRIVATE ALBUMS:"/>
         </ui:tablecell>
     </ui:tablerow>
 </ui:frame>
 <ui:frame>
  <%
        Integer numpage = (Integer) pageNum;
        int temp = ((numpage.intValue()-1)*10);
        int photocounter = temp;
        Photo photo;
        String width = "200";
        int photos = photoalbum.photolist.size();
%>
     <ui:tablerow>
         <ui:tablecell width="140">
             <ui:listbox beanId="albumslist1"/>
         </ui:tablecell>
<%
             int tablecellcounter = 0;
             while((photocounter < 5+temp) && (photos >temp)){
             photo =(Photo)photoalbum.photolist.get(tablecellcounter+temp);
             if(photoalbum.photolist.size()==tablecellcounter+temp+1)width="";

%>
          <ui:tablecell width="<%= width%>">
             <ui:actionlink action="showPicture">
                 <ui:actionparam name="showPhotoByOid" value="<%= photo.getOid()%>"/>
                    <ui:image src="<%= photo.getUrl()%>" title="<%= photo.getTitle()%>" width="180" height="220"/>
             </ui:actionlink>
         </ui:tablecell>
<%
          tablecellcounter ++;
          photocounter++;
          photos --;
         }
%>
     </ui:tablerow>
     <ui:tablerow>
         <ui:tablecell>
             <ui:text value="PUBLIC ALBUMS:"/>
         </ui:tablecell>
     </ui:tablerow>
     <ui:tablerow>
         <ui:tablecell>
             <ui:listbox beanId="albumslist2"/>
         </ui:tablecell>
<%
        photos = photoalbum.photolist.size()-5-temp;
        int tablecellcounter = 5;
        while((photocounter>=5+temp) && (photocounter<=9+temp) && photos>0){
        photo =(Photo)photoalbum.photolist.get(tablecellcounter+temp);
        if(photoalbum.photolist.size()==tablecellcounter+temp+1)width="";

%>
          <ui:tablecell width="<%= width%>">
             <ui:actionlink action="showPicture">
                 <ui:actionparam name="showPhotoByOid" value="<%= photo.getOid() %>"/>
                    <ui:image src="<%= photo.getUrl()%>" title="<%= photo.getTitle()%>" width="180" height="220"/>
             </ui:actionlink>
         </ui:tablecell>
<%
            tablecellcounter ++;
            photocounter ++;
            photos--;
         }
%>

     </ui:tablerow>
 </ui:frame>

    <ui:actionsubmit action="showSelectedPhotoAlbum" value="Show Album"/>
     <ui:frame>
      <ui:tablerow>
          <ui:tablecell>
              <ui:text value="<%= value%>"/>
          </ui:tablecell>
      </ui:tablerow>
  </ui:frame>
  <ui:frame>

     <ui:tablerow>
         <ui:tablecell height="30"/>
         <ui:tablecell width="470"/>
         <ui:tablecell width="80">
             <ui:text value="<%= textPrev%>"/>
             <ui:actionlink action="previousPage" value="<%= valuePrev%>">
                 <ui:actionparam name="previousPage" value="<%= pagenum.toString()%>"/>
             </ui:actionlink>
         </ui:tablecell>
         <ui:tablecell width="<%= widthNext%>">
             <ui:text value="<%= text1%>"/>
             <ui:actionlink action= "showPage1" value="<%= value1%>"/>
             <ui:text value="<%= text2%>"/>
             <ui:actionlink action= "showPage2" value="<%= value2%>"/>
             <ui:text value="<%= text3%>"/>
             <ui:actionlink action= "showPage3" value="<%= value3%>"/>
             <ui:text value="<%= text4%>"/>
             <ui:actionlink action= "showPage4" value="<%= value4%>"/>
             <ui:text value="<%= text5%>"/>
             <ui:actionlink action= "showPage5" value="<%= value5%>"/>
         </ui:tablecell>
         <ui:tablecell>
             <ui:text value="<%= textNext%>"/>
             <ui:actionlink action="nextPage" value="<%= valueNext%>">
                  <ui:actionparam name="nextPage" value="<%= pagenum.toString()%>"/>
             </ui:actionlink>
         </ui:tablecell>
     </ui:tablerow>
    </ui:frame>
</ui:form>
