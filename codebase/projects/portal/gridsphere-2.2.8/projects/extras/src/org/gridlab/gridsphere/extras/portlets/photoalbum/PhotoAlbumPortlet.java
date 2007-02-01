/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: PhotoAlbumPortlet.java,v 1.1.1.1 2007-02-01 20:07:37 kherm Exp $
 */

package org.gridlab.gridsphere.extras.portlets.photoalbum;

import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.provider.event.jsr.RenderFormEvent;
import org.gridlab.gridsphere.provider.event.jsr.ActionFormEvent;
import org.gridlab.gridsphere.provider.portlet.jsr.ActionPortlet;
import org.gridlab.gridsphere.provider.portletui.beans.*;
import org.gridlab.gridsphere.services.core.user.UserManagerService;
import org.gridlab.gridsphere.services.core.file.FileManagerService;
import org.gridlab.gridsphere.extras.services.photoalbum.PhotoAlbum;
import org.gridlab.gridsphere.extras.services.photoalbum.PhotoAlbumService;
import org.gridlab.gridsphere.extras.services.photoalbum.PhotoService;
import org.gridlab.gridsphere.extras.services.photoalbum.Photo;

import javax.portlet.*;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.io.File;
import java.io.IOException;

public class PhotoAlbumPortlet extends ActionPortlet {

    // JSP pages used by this portlet
     public static final String EDIT_JSP = "photoalbum/viewalbum.jsp";
     public static final String VIEW_ALBUMS_LIST_JSP = "photoalbum/viewalbumlist.jsp";

     PhotoAlbum tempalbum = null;
     String selected;

     private static PortletLog log = SportletLog.getInstance(PhotoAlbumPortlet.class);

       // Portlet services
     private PhotoAlbumService photoalbumservice = null;
     private PhotoService photoservice = null;
     private UserManagerService userManagerService = null;                          
     private FileManagerService fileManagerService = null;


     public void init(PortletConfig config) throws PortletException {
         super.init(config);

         try {
             userManagerService = (UserManagerService) createPortletService(UserManagerService.class);
             photoalbumservice = (PhotoAlbumService) createPortletService(PhotoAlbumService.class);
             photoservice = (PhotoService) createPortletService(PhotoService.class);
             fileManagerService = (FileManagerService) createPortletService(FileManagerService.class);
         } catch (PortletServiceException e) {
             log.error("Unable to initialize PhotoAlbumService: "+ e);
         }
          // DEFAULT_VIEW_PAGE = "photoalbum/editalbum.jsp";
         DEFAULT_VIEW_PAGE = "viewAlbumList";
         DEFAULT_EDIT_PAGE = EDIT_JSP;

     }
             int y =0;
     public void viewAlbumList(RenderFormEvent event)throws PortletException{
         y++;
         PortletRequest request = (PortletRequest)event.getRenderRequest();
         String userId=(String) ((Map) event.getRenderRequest().getAttribute(PortletRequest.USER_INFO)).get("user.name.id");
         List albums = photoalbumservice.getPhotoAlbums(userId);
         log.error(String.valueOf(albums.size())+"  .ppp  "+String.valueOf(y));

         ListBoxBean alb = event.getListBoxBean("albumslist");
         // set size to 15
         alb.setSize(15);
         //clear the list
         alb.clear();
         alb.setMultipleSelection(false);

         if (albums !=null) {
             for (int i=0; i<albums.size(); i++) {
                 PhotoAlbum photoalbum = (PhotoAlbum)albums.get(i);
                 ListBoxItemBean item = new ListBoxItemBean();
                 item.setName(photoalbum.getOid());
                 item.setValue(photoalbum.getTitle());
                 alb.addBean(item);
             }

         alb.sortByValue();
         }
         if(albums.size()==0) {
               ListBoxItemBean item = new ListBoxItemBean();
               item.setValue("create an album");
               alb.addBean(item);
         }
         request.setAttribute("selected",selected);
         setNextState(request, VIEW_ALBUMS_LIST_JSP);

     }

    public void editPublicPhotoAlbum(ActionFormEvent event){
        PortletRequest request = (PortletRequest) event.getActionRequest();
        CheckBoxBean checkbox = event.getCheckBoxBean("checkbox");
        if(checkbox.isSelected()){
            selected = "true";
            tempalbum.setPublicPhotoAlbum(true);
            photoalbumservice.savePhotoAlbum(tempalbum);
        }
        else {
            selected = "false";
            tempalbum.setPublicPhotoAlbum(false);
            photoalbumservice.savePhotoAlbum(tempalbum);
        }
        request.setAttribute("selected",selected);
        setNextState(request, "showPhotoAlbum");
    }


    public void showAlbumList(ActionFormEvent event){
         PortletRequest request = (PortletRequest)event.getActionRequest();
         String userId = (String) ((Map) event.getActionRequest().getAttribute(PortletRequest.USER_INFO)).get("user.name.id");
         List albums = photoalbumservice.getPhotoAlbums(userId);

         ListBoxBean alb = event.getListBoxBean("albumslist");
         // set size to 10
         alb.setSize(15);
         //clear the list
         alb.clear();
         alb.setMultipleSelection(false);

         if (albums != null) {
             for (int i=0; i<albums.size(); i++) {
                 PhotoAlbum photoalbum = (PhotoAlbum)albums.get(i);
                 ListBoxItemBean item = new ListBoxItemBean();
                 item.setName(photoalbum.getOid());
                 item.setValue(photoalbum.getTitle());
                 alb.addBean(item);
             }

         alb.sortByValue();
         }
         else {
               ListBoxItemBean item = new ListBoxItemBean();
               item.setValue("create an album");
               alb.addBean(item);
         }
         setNextState(request,VIEW_ALBUMS_LIST_JSP);
     }

     public void editPhotoAlbum(ActionFormEvent event)throws PortletException{
         PortletRequest request = event.getActionRequest();
         ListBoxBean albumlist = event.getListBoxBean("albumslist");
      if(albumlist.hasSelectedValue()){
         String oid = albumlist.getSelectedName();
         tempalbum = photoalbumservice.getPhotoAlbumByOid(oid);
         if(tempalbum.getPublicPhotoAlbum())selected="true";
         else selected = "false";
         String value= "";
         if(tempalbum.photolist.size()==10)value="the album is full";

          request. setAttribute("pageNum", new Integer(1));
          request.setAttribute("photoalbum", tempalbum);
          request.setAttribute("selected",selected);
          request.setAttribute("value", value);
          setNextState(request,"photoalbum/viewalbum.jsp");
      }
         else {
           String value = "Please select an album";
           request.setAttribute("value",value);
           setNextState(request, "viewAlbumList");
      }
     }

     public void doCancelEditAlbum(ActionFormEvent event)
            throws PortletException {
              showAlbumList(event);
    }

    public void showPhotoAlbum(RenderFormEvent event){
        PortletRequest request = event.getRenderRequest();
        photoalbumservice.showPhotoAlbum(event,tempalbum) ;

        request.setAttribute("photoalbum", tempalbum);
        setNextState(request, "photoalbum/viewalbum.jsp");
    }


   public void showPhotoAlbum(ActionFormEvent event)throws PortletException{
       PortletRequest request = event.getActionRequest();
       photoalbumservice.showPhotoAlbum(event, tempalbum);
       User user = userManagerService.getUser(tempalbum.getUserId());

       request.setAttribute("userName", user.getUserName());
       request.setAttribute("photoalbum", tempalbum);
       setNextState(request, EDIT_JSP);
       //setNextState(request, "photoalbum/edit.jsp");
   }

     public void addPhotoAlbum(ActionFormEvent event){
         String userId = (String) ((Map) event.getActionRequest().getAttribute(PortletRequest.USER_INFO)).get("user.name.id");
         List albums = photoalbumservice.getPhotoAlbums(userId);

         ListBoxBean alb = event.getListBoxBean("albumslist");
         // set size to 10
         alb.setSize(15);
         //clear the list
         alb.clear();
         alb.setMultipleSelection(false);

         if (albums != null ) {
             for (int i=0; i<albums.size(); i++) {
                 PhotoAlbum photoalbum = (PhotoAlbum)albums.get(i);
                 ListBoxItemBean item = new ListBoxItemBean();
                 item.setName(photoalbum.getOid());
                 item.setValue(photoalbum.getTitle());
                 alb.addBean(item);
             }

         alb.sortByValue();
         }
         else {
               ListBoxItemBean item = new ListBoxItemBean();
               item.setValue("create an album");
               alb.addBean(item);
         }
          setNextState(event.getActionRequest(), "photoalbum/addalbum.jsp");
     }

     public void applyNewAlbum(ActionFormEvent event) {
         log.error("eee in methode");
         PortletRequest request = event.getActionRequest();
         String userId = (String) ((Map) event.getActionRequest().getAttribute(PortletRequest.USER_INFO)).get("user.name.id");
         // get the values
         TextFieldBean desc = event.getTextFieldBean("albumdesc");
         if(new String(desc.getValue()).equals("")){
            String value = "Please put a name for the new album";
            request.setAttribute("value",value);
            setNextState(request, "photoalbum/addalbum.jsp");
         }
         else{
             log.error("eeeeee "+desc.getValue());
             log.error("alle alben "+String.valueOf(photoalbumservice.getAlbums().size()));
            PhotoAlbum photoalbum = new PhotoAlbum(desc.getValue());
            photoalbum.setNumPhotosPerAlbum(50);
            photoalbumservice.addPhotoAlbum(photoalbum, userId);

            // show the mainpage again
             setNextState(request, "viewAlbumList");
         }
     }

     public void deletePhotoAlbum(ActionFormEvent event)throws PortletException{
        PortletRequest request = (PortletRequest)event.getActionRequest();
        String userId = (String) ((Map) event.getActionRequest().getAttribute(PortletRequest.USER_INFO)).get("user.name.id");
        ListBoxBean alb = event.getListBoxBean("albumslist");
        if(alb.hasSelectedValue()){
           String oid = alb.getSelectedName();
           tempalbum = photoalbumservice.getPhotoAlbumByOid(oid);
           List albums = photoalbumservice.getPhotoAlbums(userId);
           // set size to 10
           alb.setSize(15);
           //clear the list
           alb.clear();
           alb.setMultipleSelection(false);

             for (int i=0; i<albums.size(); i++) {
                 PhotoAlbum photoalbum = (PhotoAlbum)albums.get(i);
                 ListBoxItemBean item = new ListBoxItemBean();
                 item.setName(photoalbum.getOid());
                 item.setValue(photoalbum.getTitle());
                 alb.addBean(item);
             }

         alb.sortByValue();
         String deleteAlbum = "Delete album " + tempalbum.getTitle() +" ?";
         request.setAttribute("value2", deleteAlbum);
         setNextState(request,"photoalbum/picture.jsp");
       }

       else{
           String value = "Please select an album";
           request.setAttribute("value",value);
           setNextState(request, "viewAlbumList");
       }
     }

    public void yes(ActionFormEvent event)throws PortletException{
        PortletRequest request = event.getActionRequest();
         String userId = (String) ((Map) event.getActionRequest().getAttribute(PortletRequest.USER_INFO)).get("user.name.id");
        List photolist = tempalbum.getPhotos();
        Photo photo = null;
        for(int i=0; i<photolist.size(); i++){
             photo = (Photo)photolist.get(i);
             tempalbum.removePhoto(photo);
             photoservice.deletePhoto(photo);
             fileManagerService.deleteFile(getUser(request), photo.getTitle());
        }

         photoalbumservice.deletePhotoAlbum(tempalbum);
         List albums = photoalbumservice.getPhotoAlbums(userId);

         ListBoxBean alb = event.getListBoxBean("albumslist");
         // set size to 10
         alb.setSize(15);
         //clear the list
         alb.clear();
         alb.setMultipleSelection(false);

         if (albums != null) {
             for (int i=0; i<albums.size(); i++) {
                 PhotoAlbum photoalbum = (PhotoAlbum)albums.get(i);
                 ListBoxItemBean item = new ListBoxItemBean();
                 item.setName(photoalbum.getOid());
                 item.setValue(photoalbum.getTitle());
                 alb.addBean(item);
             }

         alb.sortByValue();
         }
         else {
               ListBoxItemBean item = new ListBoxItemBean();
               item.setValue("create an album");
               alb.addBean(item);
         }
        setNextState(event.getActionRequest(), "viewAlbumList");
    }


      public void uploadFile(ActionFormEvent event) throws PortletException {
         PortletRequest request = event.getActionRequest();
         if(tempalbum.photolist.size()<50){
         try {
            FileInputBean fi = event.getFileInputBean("photofile");
            String fileName = fi.getFileName();
             Random r = new Random();
            if(tempalbum.containsPhoto(fileName))fileName =String.valueOf(r.nextInt())+fileName;
            int size = fi.getSize();

            if (size > FileInputBean.MAX_UPLOAD_SIZE) {
                FrameBean errMsg = event.getFrameBean("errorFrame");
	            errMsg.setValue(this.getLocalizedText(event.getActionRequest(), "FILE_UPLOAD_TOOBIG"));
	            errMsg.setStyle("error");
                return;
            }
            System.err.println("filename = " + fileName);
            if (fileName.equals("")) return;

            String userLoc = fileManagerService.getLocationPath(getUser(request), "");
            File f = new File(userLoc);
            if (!f.exists()) {
                if (!f.mkdirs()) throw new IOException("Unable to create dir: " + userLoc);
            }
            String filepath = fileManagerService.getLocationPath(getUser(request), fileName);
            System.err.println("storeFile: " + filepath);
            fi.saveFile(filepath);
            Photo photo = new Photo("/gridsphere/tempdir/"+getUser(request).getID()+File.separator+fileName, fileName);
            tempalbum.addPhoto(photo);
            photoservice.addPhoto(photo);
            photoalbumservice.savePhotoAlbum(tempalbum);

            log.debug("fileinputbean value=" + fi.getValue());
          } catch (Exception e) {
            FrameBean errMsg = event.getFrameBean("errorFrame");
	        errMsg.setValue(this.getLocalizedText(event.getActionRequest(), "FILE_UPLOAD_FAIL"));
	        errMsg.setStyle("error");
	        log.error("Unable to store uploaded file ", e);
        }
        setNextState(event.getActionRequest(), "showPhotoAlbum");
        }
        else {
            String value = "please create a new album";
            request.setAttribute("value", value);
        }
    }


    public void showPicture(ActionFormEvent event) throws PortletException {
        photoalbumservice.showPicture(event, tempalbum);
        setNextState(event.getActionRequest(), "photoalbum/showpicture.jsp");
     }

      public void nextPhoto(ActionFormEvent event) throws PortletException {
        photoalbumservice.nextPhoto(event, tempalbum);
        setNextState(event.getActionRequest(), "photoalbum/showpicture.jsp");
    }

    public void previousPhoto(ActionFormEvent event) throws PortletException {
        photoalbumservice.previousPhoto(event, tempalbum);
        setNextState(event.getActionRequest(), "photoalbum/showpicture.jsp");
    }


    public void deletePhoto(ActionFormEvent event){
        PortletRequest request = event.getActionRequest();
        String photoOid = event.getAction().getParameter("photoOid");
        Photo photo = tempalbum.getPhotoByOid(photoOid);
        tempalbum.removePhoto(photo);
        photoservice.deletePhoto(photo);
        photoalbumservice.savePhotoAlbum(tempalbum);
        fileManagerService.deleteFile(getUser(request),photo.getTitle());
        setNextState(request, "showPhotoAlbum");
    }

    public void showPage(ActionFormEvent event,int page) throws PortletException {
        PortletRequest request = event.getActionRequest();
        request.setAttribute("photoalbum", tempalbum);
        request.setAttribute("pageNum",new Integer(page));
        request.setAttribute("selected",selected);
        setNextState(request, "photoalbum/viewalbum.jsp");
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
        showPage(event,4);
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

   public void applyPhotoDesc(ActionFormEvent event)throws PortletException{
         PortletRequest request = event.getActionRequest();
         String photoOid = request.getParameter("photoOid");
         log.error("PHOTOALBUM OID = "+photoOid);
         Photo photo = photoservice.getPhoto(photoOid);
         TextFieldBean desc = event.getTextFieldBean("photoDesc");
         String photoDesc = desc.getValue();
        // photoservice.applyDesc(photo, photoDesc);
         //photoalbumservice.savePhotoAlbum(tempalbum);
         request.setAttribute("photoalbum",tempalbum);
         request.setAttribute("next","next");
         request.setAttribute("previous","previous");
         request.setAttribute("photo",photo);
         setNextState(request, "photoalbum/showpicture.jsp");
   }
}
