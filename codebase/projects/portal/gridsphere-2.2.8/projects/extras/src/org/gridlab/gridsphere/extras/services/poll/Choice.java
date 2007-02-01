package org.gridlab.gridsphere.extras.services.poll;



public class Choice{

    public String choice;
    String oid;
    public long voices;


    public Choice(){
        voices = 0;
    }

    public Choice(String choice){
        this.choice = choice;
        voices = 0;
    }

    public String getChoice(){
        return choice;
    }

    public void setChoice(String choice){
        this.choice = choice;
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

    public void doVote(){
        voices ++;
    }

}