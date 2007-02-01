package org.gridlab.gridsphere.extras.services.photoalbum;

import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.provider.event.jsr.RenderFormEvent;
import org.gridlab.gridsphere.provider.event.jsr.ActionFormEvent;

import java.util.List;


public interface PhotoAlbumService extends PortletService{

      public int getNumAlbumsPerUser(String  userId);

      public void setNumAlbumsPerUser(long num);

      //public int getNumPhotosPerAlbum();

     // public void setNumPhotosPerAlbum(long num);

      public List getPhotoAlbums(String userId);

      public List getPublicPhotoAlbums(boolean publicPhotoAlbum);

      public PhotoAlbum getPhotoAlbumByOid(String oid);

      public PhotoAlbum getPhotoAlbumByTitle(String title);

      public void savePhotoAlbum(PhotoAlbum album);

      public void deletePhotoAlbum(PhotoAlbum album);

      public void addPhotoAlbum(PhotoAlbum photoalbum, String userId);

      public List getAlbums();

      //public  void getAlbumsList(List albums, ListBoxBean alb);

      public void showPhotoAlbum(RenderFormEvent event,PhotoAlbum tempalbum) ;

      public void showPhotoAlbum(ActionFormEvent event, PhotoAlbum tempalbum);

      public void showPicture(ActionFormEvent event,PhotoAlbum photoalbum);

      public void previousPhoto(ActionFormEvent event, PhotoAlbum photoalbum);

      public void nextPhoto(ActionFormEvent event, PhotoAlbum photoalbum);
}
