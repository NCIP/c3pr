
package org.gridlab.gridsphere.extras.services.poll;

import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;


public class Poll{

     public List questions;
     public String title;
     public String oid;
     public long voices;
     public Calendar fromDate;
     public Calendar untilDate;
     public String graphic;
     public List userId;
     public boolean multiple;
     public boolean hiddenForGuest = false;
     public boolean hiddenForUser = false;
     public boolean hiddenForSuper = false;


     public Poll(){
         questions = new ArrayList();
         userId = new ArrayList();
         voices = 0;
     }

     public Poll(String title){
         questions = new ArrayList();
         userId = new ArrayList();
         this.title = title;
         voices = 0;
     }

     public List getQuestions(){
         return questions;
     }

     public void setQuestions(List questions){
         this.questions = questions;
     }

     public String getTitle(){
         return title;
     }

     public void setTitle(String title){
         this.title = title;
     }

     public String getOid(){
         return oid;
     }

     public void setOid(String oid){
         this.oid = oid;
     }

     public long getVoices(){
         return voices;
     }

     public void setVoices(long voices){
         this.voices = voices;
     }

     public Calendar getFromDate(){
         return fromDate;
     }

     public void setFromDate(Calendar date){
         this.fromDate = date;
     }

     public Calendar getUntilDate(){
         return untilDate;
     }

     public void setUntilDate(Calendar date){
         this.untilDate = date;
     }

     public String getGraphic(){
         return graphic;
     }

     public void setGraphic(String graphic){
         this.graphic = graphic;
     }


    public List getUserId(){
         return userId;
     }

     public void setUserId(List userId){
         this.userId=userId;
     }

     public boolean getMultiple(){
         return multiple;
     }

     public void setMultiple(boolean multiple){
         this.multiple = multiple;
     }

     public boolean getHiddenForGuest(){
         return hiddenForGuest;
     }

     public void setHiddenForGuest(boolean hiddenForGuest){
         this.hiddenForGuest = hiddenForGuest;
     }

     public boolean getHiddenForUser(){
         return hiddenForUser;
     }

     public void setHiddenForUser(boolean hiddenForUser){
         this.hiddenForUser = hiddenForUser;
     }

     public boolean getHiddenForSuper(){
         return hiddenForSuper;
     }

     public void setHiddenForSuper(boolean hiddenForSuper){
         this.hiddenForSuper= hiddenForSuper;
     }

     public void addQuestion(Question question){
         questions.add(question);
     }

     public void removeQuestion(Question question){
         questions.remove(question);
     }

     public void addUserId(PollUsers polluser){
         userId.add(polluser);
     }

     public void removeUserId(PollUsers polluser){
         userId.remove(polluser);
     }

     public void doVote(){
         voices ++;
     }

     public boolean hasVoted(String userID){
        for(int i=0; i<userId.size(); i++){
           PollUsers pll = (PollUsers)userId.get(i);
           if(pll.getUserId().equals(new String(userID))) return true;
        }
        return false;
     }

}
