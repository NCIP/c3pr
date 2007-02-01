package org.gridlab.gridsphere.extras.portlets.photoalbum;

import org.gridlab.gridsphere.provider.portlet.jsr.ActionPortlet;
import org.gridlab.gridsphere.provider.event.jsr.RenderFormEvent;
import org.gridlab.gridsphere.provider.event.jsr.ActionFormEvent;
import org.gridlab.gridsphere.provider.portletui.beans.ListBoxBean;
import org.gridlab.gridsphere.provider.portletui.beans.ListBoxItemBean;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.core.user.UserManagerService;
import org.gridlab.gridsphere.extras.services.photoalbum.PhotoAlbumService;
import org.gridlab.gridsphere.extras.services.photoalbum.PhotoAlbum;

import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import java.util.List;
import java.util.Map;




public class PhotoAlbumViewerPortlet extends ActionPortlet {


     private static PortletLog log = SportletLog.getInstance(PhotoAlbumViewerPortlet.class);

       // Portlet services
     private PhotoAlbumService photoalbumservice = null;
     private UserManagerService userManagerService = null;

     PhotoAlbum tempalbum = null;


     public void init(PortletConfig config) throws PortletException {
         super.init(config);

         try {
             userManagerService = (UserManagerService) createPortletService(UserManagerService.class);
             photoalbumservice = (PhotoAlbumService) createPortletService(PhotoAlbumService.class);
         } catch (PortletServiceException e) {
             log.error("Unable to initialize PhotoAlbumService: "+ e);
         }
         DEFAULT_VIEW_PAGE = "viewAlbumsList";
         DEFAULT_EDIT_PAGE = "photoalbum/viewalbum.jsp";
     }

    public void viewAlbumsList(RenderFormEvent event)throws PortletException{
         PortletRequest request = (PortletRequest)event.getRenderRequest();
         String userId = (String) ((Map) event.getRenderRequest().getAttribute(PortletRequest.USER_INFO)).get("user.name.id");

         ListBoxBean alb1 = event.getListBoxBean("albumslist1");
         ListBoxBean alb2 = event.getListBoxBean("albumslist2");
          //set size to 15
         alb1.setSize(15);
         alb2.setSize(15);
         List privateAlbums = photoalbumservice.getPhotoAlbums(userId);
         List publicAlbums = photoalbumservice.getPublicPhotoAlbums(true);
         getAlbumsList(privateAlbums, alb1, false);
         getAlbumsList(publicAlbums, alb2, true);

         if(privateAlbums.size()==0) {
               ListBoxItemBean item = new ListBoxItemBean();
               item.setValue("no private albums");
               alb1.addBean(item);
         }

        if(publicAlbums.size()==0){
               ListBoxItemBean item = new ListBoxItemBean();
               item.setValue("no public albums :(");
               alb2.addBean(item);
         }
        if(privateAlbums.size() != 0 || publicAlbums.size() != 0)  {
             if(privateAlbums.size() !=0)tempalbum = (PhotoAlbum)privateAlbums.get(0);
               else if(publicAlbums.size() != 0)tempalbum = (PhotoAlbum) publicAlbums.get(0);
                  photoalbumservice.showPhotoAlbum(event, tempalbum);

                  request.setAttribute("photoalbum",tempalbum);
                  request.setAttribute("pageNum", new Integer(1));
                  setNextState(request, "photoalbumviewer/viewalbum.jsp");
        }
          else{
                  setNextState(request,"photoalbumviewer/viewalbumslist.jsp");
        }
    }

     public void showPhotoAlbum(ActionFormEvent event,PhotoAlbum photoalbum,String value){
         PortletRequest request = (PortletRequest)event.getActionRequest();
         List photolist = null;
         List privateAlbums = photoalbumservice.getAlbums();
         List publicAlbums = photoalbumservice.getPublicPhotoAlbums(true);
         listBoxes(event);

         if(privateAlbums.size() != 0 || publicAlbums.size() != 0)  {
             tempalbum = photoalbum;
             photoalbumservice.showPhotoAlbum(event, tempalbum);
             photolist = tempalbum.getPhotos();
             if(photolist.isEmpty() && (value.equals(new String("")))) {
                 request.setAttribute("albumname","There are no pictures in album " + tempalbum.getTitle()+" !");
             }
             else {
                 request.setAttribute("albumname",tempalbum.getTitle());
             }
                 request.setAttribute("userName", userManagerService.getUser(tempalbum.getUserId()).getFullName());
                 request.setAttribute("photoalbum", tempalbum);
                 request.setAttribute("pageNum", new Integer(1));
                 request.setAttribute("value",value);
                 setNextState(request, "photoalbumviewer/viewalbum.jsp");
         }
         else{
         setNextState(request,"photoalbumviewer/viewalbumlist.jsp");
        }
     }

    public void showSelectedPhotoAlbum(ActionFormEvent event){
         ListBoxBean alb1 = event.getListBoxBean("albumslist1");
         ListBoxBean alb2 = event.getListBoxBean("albumslist2");
         String albumoid = "";
         String value = "";
         if(alb1.hasSelectedValue() || alb2.hasSelectedValue()){
            if(alb1.hasSelectedValue())albumoid = alb1.getSelectedName();
             else if(alb2.hasSelectedValue())albumoid = alb2.getSelectedName();
                 tempalbum = (PhotoAlbum)photoalbumservice.getPhotoAlbumByOid(albumoid);
                 showPhotoAlbum(event,tempalbum,value);
        }
        else{
                 value = "Please select an album";
                 showPhotoAlbum(event, tempalbum, value);
        }
    }

    public void listBoxes(ActionFormEvent event){
         List privateAlbums = photoalbumservice.getAlbums();
         List publicAlbums = photoalbumservice.getPublicPhotoAlbums(true);
         ListBoxBean alb1 = event.getListBoxBean("albumslist1");
         ListBoxBean alb2 = event.getListBoxBean("albumslist2");
         String albumoid = "";
         if(alb1.hasSelectedValue())albumoid = alb1.getSelectedName();
         else if(alb2.hasSelectedValue())albumoid = alb2.getSelectedName();
          //set size to 15
         alb1.setSize(15);
         alb2.setSize(15);
         getAlbumsList(privateAlbums, alb1, false);
         getAlbumsList(publicAlbums, alb2, true);
         if(privateAlbums.size()==0) {
               ListBoxItemBean item = new ListBoxItemBean();
               item.setValue("");
               alb1.addBean(item);
         }
         if(publicAlbums.size()==0){
             ListBoxItemBean item = new ListBoxItemBean();
             item.setValue("no public albums");
             alb2.addBean(item);
         }
    }

    public void upPhotoAlbum(ActionFormEvent event) throws PortletException {
        showPhotoAlbum(event, tempalbum, "");
    }

    public void showPicture(ActionFormEvent event) throws PortletException {
        photoalbumservice.showPicture(event, tempalbum);
        setNextState(event.getActionRequest(), "photoalbumviewer/showpicture.jsp");
     }

    public void nextPhoto(ActionFormEvent event) throws PortletException {
        photoalbumservice.nextPhoto(event, tempalbum);
        event.getActionRequest().setAttribute("photoalbum",tempalbum);
        setNextState(event.getActionRequest(), "photoalbumviewer/showpicture.jsp");
    }

    public void previousPhoto(ActionFormEvent event) throws PortletException {
        photoalbumservice.previousPhoto(event, tempalbum);
        event.getActionRequest().setAttribute("photoalbum",tempalbum);
        setNextState(event.getActionRequest(), "photoalbumviewer/showpicture.jsp");
    }

     public void showPage(ActionFormEvent event,int page) throws PortletException {
         PortletRequest request = event.getActionRequest();
         String userId = (String) ((Map) event.getActionRequest().getAttribute(PortletRequest.USER_INFO)).get("user.name.id");
         ListBoxBean alb1 = event.getListBoxBean("albumslist1");
         ListBoxBean alb2 = event.getListBoxBean("albumslist2");
         //set size to 15
         alb1.setSize(15);
         alb2.setSize(15);
         List privateAlbums = photoalbumservice.getPhotoAlbums(userId);
         List publicAlbums = photoalbumservice.getPublicPhotoAlbums(true);
         getAlbumsList(privateAlbums, alb1, false);
         getAlbumsList(publicAlbums, alb2,  true);
         if(privateAlbums.size()==0) {
               ListBoxItemBean item = new ListBoxItemBean();
               item.setValue("");
               alb1.addBean(item);
         }
         if(publicAlbums.size()==0){
             ListBoxItemBean item = new ListBoxItemBean();
             item.setValue("no public albums :(");
             alb2.addBean(item);
        }
        request.setAttribute("pageNum",new Integer(page));
        request.setAttribute("photoalbum", tempalbum);
        setNextState(request, "photoalbumviewer/viewalbum.jsp");
    }

    public void nextPage(ActionFormEvent event)throws PortletException{
        String num = event.getAction().getParameter("nextPage");
        int page = new Integer(num).intValue() + 1;
        showPage(event,page);
    }

    public void previousPage(ActionFormEvent event)throws PortletException{
        String num = event.getAction().getParameter("previousPage");
        int page = new Integer(num).intValue() -1;
        showPage(event,page);
    }

    public void showPage1(ActionFormEvent event)throws PortletException{
        showPage(event,1);
    }

    public void showPage2(ActionFormEvent event)throws PortletException{
        showPage(event,2);
    }

    public void showPage3(ActionFormEvent event)throws PortletException{
        showPage(event,3);
    }

   public void showPage4(ActionFormEvent event)throws PortletException{
        showPage(event,3);
    }

   public void showPage5(ActionFormEvent event)throws PortletException{
        showPage(event,5);
    }
    public void doError(RenderFormEvent event){

         log.error("error in PhotoAlbumPortlet ");
    }
   public User getUser(PortletRequest request){
        Map userInfo = (Map) request.getAttribute(PortletRequest.USER_INFO);
        String userId = (String) userInfo.get("user.name.id");
        return  userManagerService.getUser(userId) ;
   }

      public  void getAlbumsList(List albums, ListBoxBean alb,boolean isPublic){
         //clear the list
         alb.clear();
         alb.setMultipleSelection(false);

         if (albums != null) {
             for (int i=0; i<albums.size(); i++) {
                 PhotoAlbum photoalbum = (PhotoAlbum)albums.get(i);
                 ListBoxItemBean item = new ListBoxItemBean();
                 item.setName(photoalbum.getOid());
                 if(isPublic){

                    String userName = userManagerService.getUser(photoalbum.getUserId()).getFullName();
                    item.setValue(photoalbum.getTitle()+"("+userName+")");

                 }
                 else{
                    item.setValue(photoalbum.getTitle());
                 }
                 alb.addBean(item);
             }
             alb.sortByValue();
         }

      }
}
