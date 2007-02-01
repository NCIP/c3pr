package org.gridlab.gridsphere.extras.services.photoalbum.impl;



import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerRdbms;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.provider.event.jsr.RenderFormEvent;
import org.gridlab.gridsphere.provider.event.jsr.ActionFormEvent;
import org.gridlab.gridsphere.extras.services.photoalbum.PhotoAlbumService;
import org.gridlab.gridsphere.extras.services.photoalbum.PhotoAlbum;
import org.gridlab.gridsphere.extras.services.photoalbum.Photo;

import javax.portlet.PortletRequest;
import java.util.List;


public class PhotoAlbumServiceImpl implements PortletServiceProvider, PhotoAlbumService{

             // logging
        private static PortletLog log = SportletLog.getInstance(PhotoAlbumServiceImpl.class);

            // our persistencemanager
        private PersistenceManagerRdbms pm = null;
        private long albumsNum = 0;

          /**
           * Init the service
           * @param config
           * @throws PortletServiceUnavailableException

           */

        public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
              // create the persistencemanager
           this.pm = PersistenceManagerFactory.createPersistenceManagerRdbms("extras");
        }


          /**
           * Destroy the service and free ressources.
          */

        public void destroy() {
            try {
                 pm.destroy();
            } catch (PersistenceManagerException e) {
                 log.info("Problems shutting down PhotoAlbumService.");
            }
        }

        private List queryDB(String condition) {
        List result = null;
        try {
            // try to get the address
            result = pm.restoreList("from " + PhotoAlbum.class.getName() + " as photoalbum "+condition);
        } catch (PersistenceManagerException e) {
            log.error("Could not retrieve photoalbum(s) :"+e);
        }

        return result;

       }

        public PhotoAlbum getPhotoAlbumByOid(String oid) {
            List result = queryDB("where photoalbum.oid='" + oid + "'");
            if(result != null){
                return (PhotoAlbum) result.get(0);
            }else {
              return null;
            }
        }

        public PhotoAlbum getPhotoAlbumByTitle(String title) {
            List result = queryDB("where photoalbum.title='" + title + "'");
            if(result != null){
                return (PhotoAlbum) result.get(0);
            }else {
              return null;
            }
        }


        public void deletePhotoAlbum(PhotoAlbum album) {
            try {
                pm.delete(album);
            } catch (PersistenceManagerException e) {
                log.error("Error deleting photoalbum object."+e);
            }
        }

        public void savePhotoAlbum(PhotoAlbum photoalbum) {
            try {
                    if (photoalbum.getOid()==null) {
                      //  photoalbum.setUserId(user.getID());
                        pm.create(photoalbum);
                    } else {
                        pm.update(photoalbum);
                    }
            } catch (PersistenceManagerException e) {
                log.error("Error creating/updating the photoalbum object!"+e);
            }
        }

       public void addPhotoAlbum(PhotoAlbum photoalbum,String userId){
               photoalbum.setUserId(userId);
           try{
                pm.create(photoalbum);
             } catch (PersistenceManagerException e){
               log.error("Error creating the photoalbum object!"+e);
               }
       }

       public List getAlbums() {
            return queryDB("");
       }

    public List getPhotoAlbums(String userId){
        List result = queryDB("where photoalbum.userId='" + userId + "'");
            if(result != null){
                return result;
            }else {
              return null;
            }
       }

      public List getPublicPhotoAlbums(boolean publicPhotoAlbum){
          List result = queryDB("where photoalbum.publicPhotoAlbum='" + publicPhotoAlbum + "'");
            if(result != null){
                return result;
            }else {
              return null;
            }
       }

      public int getNumAlbumsPerUser(String userId){
        List photoalbums = getPhotoAlbums(userId);
        if(photoalbums != null)return photoalbums.size();
          else return 0 ;
      }

      public void setNumAlbumsPerUser(long num){
        albumsNum = num;
     }

    public void showPhotoAlbum(RenderFormEvent event,PhotoAlbum tempalbum) {
        PortletRequest request = event.getRenderRequest();
        List photolist = tempalbum.getPhotos();
        String value = "";
        if(photolist.size() ==50)value="the album is full" ;
        int counter = 0;
        if(photolist.size() <= 10)counter = 1;
         else{
            counter = (photolist.size()-1)/10 +1;
        }

        request. setAttribute("pageNum", new Integer(counter));
        request.setAttribute("value",value);
        request.setAttribute("photolist",photolist);

    }

    public void showPhotoAlbum(ActionFormEvent event,PhotoAlbum tempalbum) {
        PortletRequest request = event.getActionRequest();
        List photolist = tempalbum.getPhotos();
        int counter = 0;
        if(photolist.size() <= 10)counter = 1;
        if(photolist.size() >10 && photolist.size() <=20)counter = 2;
        request. setAttribute("pageNum", new Integer(counter));
        request.setAttribute("photolist",photolist);
    }

    /*
    public  void getAlbumsList(List albums, ListBoxBean alb){

         //clear the list
         alb.clear();
         alb.setMultipleSelection(false);

         if (albums.size()>0) {
             for (int i=0; i<albums.size(); i++) {
                 PhotoAlbum photoalbum = (PhotoAlbum)albums.get(i);
                 ListBoxItemBean item = new ListBoxItemBean();
                 item.setName(photoalbum.getOid());
                 item.setValue(photoalbum.getTitle());
                 alb.addBean(item);
             }

         alb.sortByValue();
         }

      }
      */
      public void showPicture(ActionFormEvent event,PhotoAlbum photoalbum) {
        PortletRequest request = event. getActionRequest();
        String next = "next >>";
        String previous = "<< previous";
        String oid = event.getAction().getParameter("showPhotoByOid");
        Photo photo = photoalbum.getPhotoByOid(oid);

        if(photoalbum.isPhotoFirst(photo))previous="";
        if(photoalbum.isPhotoLast(photo))next="";

        request.setAttribute("photoalbum",photoalbum);
        request.setAttribute("previous",previous);
        request.setAttribute("next",next);
        request.setAttribute("photo",photo);
     }

      public void nextPhoto(ActionFormEvent event, PhotoAlbum photoalbum) {
        PortletRequest request = event.getActionRequest();
        String previous = "<< previous";
        String next ="next >>";

        String oid = event.getAction().getParameter("photoNextID");
        Photo photoOld = photoalbum.getPhotoByOid(oid);
        Photo photo = photoalbum.getNextPhoto(photoOld);

        if(photoalbum.isPhotoLast(photo))next ="";

        request.setAttribute("photoalbum",photoalbum);
        request.setAttribute("previous",previous);
        request.setAttribute("next",next);
        request.setAttribute("photo",photo);
    }

    public void previousPhoto(ActionFormEvent event, PhotoAlbum photoalbum) {
        PortletRequest request = event.getActionRequest();
        String next = "next >>";
        String previous = "<< previous";
        String photoOid = event.getAction().getParameter("photoPrevID");
        Photo photoOld = photoalbum.getPhotoByOid(photoOid);
        Photo photo = photoalbum.getPreviousPhoto(photoOld);

        if(photoalbum.isPhotoFirst(photo))previous = "";

        request.setAttribute("photoalbum",photoalbum);
        request.setAttribute("next",next);
        request.setAttribute("previous",previous );
        request.setAttribute("photo",photo);
    }

}
