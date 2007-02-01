

package org.gridlab.gridsphere.extras.services.photoalbum;



import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;



public class PhotoAlbum {

    public List photolist;
    public String title;
    public String oid;
    public String userId;
    public long numPhotosPerAlbum;
    public int numAlbumsPerUser;
    public boolean publicPhotoAlbum = false;

    public PhotoAlbum(){
        photolist =  new ArrayList();
    }

    public PhotoAlbum(String desc, List photoAlbums){
        this.title = desc;
        this.photolist = photoAlbums;
    }

    public PhotoAlbum(String desc){
        photolist =  new ArrayList();
        this.title = desc;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public List getPhotos(){
        return photolist;
    }

    public void setPhotos(List photos){
        this.photolist = photos;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getUserId(){
        return userId;
    }

    public void setPublicPhotoAlbum(boolean bool){
        this.publicPhotoAlbum = bool;
    }

    public boolean getPublicPhotoAlbum(){
        return publicPhotoAlbum;
    }

    public void addPhoto(Photo photo){
        photolist.add(photo);
    }
    public void removePhoto(Photo photo){
        photolist.remove(photo);
    }
    public Photo getPhotoByUrl(String url){
        Photo photo = null;
        for(int i=0; i<photolist.size(); i++ ){
            photo = (Photo) photolist.get(i);
            if(photo.getUrl().equals(url)){
               return photo;
            }
        }
        return null;
    }

   public Photo getPhotoByOid(String oid){
        Photo photo = null;
        for(int i=0; i<photolist.size(); i++ ){
            photo = (Photo) photolist.get(i);
            if(photo.getOid().equals(oid)){
               return photo;
            }
        }
        return null;
    }

    public boolean isPhotoFirst(Photo photo){
         if(photolist.get(0).equals(photo)) return true;
          else return false;
    }

    public boolean isPhotoLast(Photo photo){
        if(photolist.get(photolist.size()-1).equals(photo)) return true;
        else return false;
    }

    public Photo getNextPhoto(Photo photo){
       Iterator photosIterator = photolist.iterator();

       while (photosIterator.hasNext()) {
         // Get next photo
        Photo nextPhoto = (Photo)photosIterator.next();
        if(photo.getOid().equals(new String(nextPhoto.getOid()))){
            return (Photo)photosIterator.next();
        }
    }
        return null;
    }

    public Photo getPreviousPhoto(Photo photo){
       Iterator photosIterator = photolist.iterator();
       Photo tempphoto = (Photo)photosIterator.next();

       while (photosIterator.hasNext()) {
         // Get next photo
        Photo nextPhoto = (Photo)photosIterator.next();
        if(photo.getOid().equals(new String(nextPhoto.getOid()))){
            return tempphoto;
        }
        tempphoto = nextPhoto;
    }
        return null;
    }

    public void setNumPhotosPerAlbum(long num){
        this.numPhotosPerAlbum = num;
    }

    public  long getNumPhotosPerAlbum(){
        return numPhotosPerAlbum;
    }

    public void setnumAlbumsPerUser(int numAlbumsPerUser){
        this.numAlbumsPerUser = numAlbumsPerUser;
    }

    public int getNumAlbumsPerUser(){
      return (int) this.numAlbumsPerUser;
    }
   
    public String getNumPhoto(Photo photo){
        Photo tempphoto = null;
        for(int i=0; i<photolist.size(); i++) {
            tempphoto = (Photo)photolist.get(i);
            if(tempphoto.equals(photo))return String.valueOf(i+1);
        }
        return "";
    }

    public boolean containsPhoto(String title){
        Photo photo = null;
        for(int i=0; i<photolist.size(); i++){
            photo = (Photo)photolist.get(i);
            if(photo.getTitle().equals(new String(title))) {
                return true;
            }
        }
          return false;
    }
}
