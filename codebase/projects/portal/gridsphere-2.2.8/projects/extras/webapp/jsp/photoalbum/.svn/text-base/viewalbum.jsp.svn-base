<%@ page import="org.gridlab.gridsphere.extras.services.photoalbum.Photo"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<jsp:useBean id="value" class="java.lang.String" scope="request"/>
<jsp:useBean id="pageNum" class="java.lang.Object" scope="request"/>
<jsp:useBean id="photoalbum" class="org.gridlab.gridsphere.extras.services.photoalbum.PhotoAlbum" scope="request"/>
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
    String widthNext = "120";
    if(textNext.equals(new String("")) && valueNext.equals(new String("")))widthNext = "";
%>

<%
        Integer numpage = (Integer) pageNum;
        int temp = ((numpage.intValue()-1)*10);
        int deletecounter = temp;
        int photocounter = temp;
        Photo photo;
        String width = "220";
        int deletephotos = photoalbum.photolist.size();
        int photos = photoalbum.photolist.size();

%>
<ui:frame>
        <ui:tablerow>
            <ui:tablecell width="510"/>
            <ui:tablecell>
                <ui:text value="<%= photoalbum.getTitle()%>"/>
            </ui:tablecell>
       </ui:tablerow>
</ui:frame>
   <ui:frame>
    <ui:tablerow>
        <ui:tablecell height="30"/>
        <ui:tablecell width="370"/>
        <ui:tablecell width="100">
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
<ui:frame>
        <ui:tablerow>
<%
             while((deletecounter < 5+temp) && (deletephotos >temp)){
             Photo picture = (Photo)photoalbum.photolist.get(deletecounter);
%>
          <ui:tablecell>
             <ui:actionlink action="deletePhoto" value="delete">
                <ui:actionparam name="photoOid" value="<%= picture.getOid() %>"/>
             </ui:actionlink>
          </ui:tablecell>
 <%
            deletecounter++;
            deletephotos--;
             }
 %>
         </ui:tablerow>
        <ui:tablerow>
 <%
             int tablecellcounter = 0;
             while((photocounter < 5+temp) && (photos >temp)){
             photo =(Photo)photoalbum.photolist.get(tablecellcounter+temp);
             if(photoalbum.photolist.size()==tablecellcounter+temp+1)width="";

%>
          <ui:tablecell width="<%= width%>">
             <ui:actionlink action="showPicture">
                 <ui:actionparam name="showPhotoByOid" value="<%= photo.getOid()%>"/>
                    <ui:image src="<%= photo.getUrl()%>" title="<%= photo.getTitle()%>" width="200" height="230"/>
             </ui:actionlink>
         </ui:tablecell>
<%
          tablecellcounter ++;
          photocounter++;
          photos --;
         }
%>

       </ui:tablerow>
    </ui:frame>

    <ui:frame>
        <ui:tablerow>
           <ui:tablecell height="10"/>
         </ui:tablerow>
 </ui:frame>
 <ui:frame>
         <ui:tablerow>
<%
             photos = photoalbum.photolist.size()-5-temp;
             while((deletecounter>=5+temp) && (deletecounter<=9+temp) && photos>0){
             Photo picture = (Photo)photoalbum.photolist.get(deletecounter);
%>
          <ui:tablecell>
             <ui:actionlink action="deletePhoto" value="delete">
                <ui:actionparam name="photoOid" value="<%= picture.getOid() %>"/>
             </ui:actionlink>
          </ui:tablecell>
 <%
            deletecounter++;
            photos--;
             }
 %>
         </ui:tablerow>

        <ui:tablerow>
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
                    <ui:image src="<%= photo.getUrl()%>" title="<%= photo.getTitle()%>" width="200" height="230"/>
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

   <ui:frame>
    <ui:tablerow>
        <ui:tablecell height="30"/>
        <ui:tablecell width="370"/>
        <ui:tablecell width="100">
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
<ui:panel>
     <ui:frame beanId="errorFrame"/>
     <ui:fileform action="uploadFile">
        <ui:frame>
            <ui:tablerow>
                <ui:tablecell width="60">
                    <ui:fileinput beanId="photofile" size="20" maxlength="20"/>
                </ui:tablecell>
                <ui:tablecell/>
            </ui:tablerow>
        </ui:frame>

       <ui:frame>
           <ui:tablerow>
               <ui:tablecell>
                   <ui:actionsubmit action="uploadFile" value="Upload Picture"/>
                </ui:tablecell>
           </ui:tablerow>
       </ui:frame>
    </ui:fileform>
 </ui:panel>
<ui:form>
   <ui:frame>
   <ui:tablerow>
          <ui:tablecell width="400">
            <ui:actionsubmit action="showAlbumList" value="Cancel"/>
       </ui:tablecell>
       <ui:tablecell width="25">
           <ui:checkbox beanId="checkbox" selected="<%= photoalbum.getPublicPhotoAlbum()%>"/>
       </ui:tablecell>
       <ui:tablecell width="80">
           <ui:text value="public album"/>
       </ui:tablecell>
       <ui:tablecell>
              <ui:actionsubmit action="editPublicPhotoAlbum" value="Apply changes" />
       </ui:tablecell>
       <ui:tablecell>
           <ui:text value="<%= value%>"/>
       </ui:tablecell>
   </ui:tablerow>
</ui:frame>
</ui:form>