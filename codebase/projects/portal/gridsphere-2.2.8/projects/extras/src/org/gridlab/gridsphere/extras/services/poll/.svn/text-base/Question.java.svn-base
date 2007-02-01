
package org.gridlab.gridsphere.extras.services.poll;

import java.util.List;
import java.util.ArrayList;


public class Question{

    public List answers;
    public String oid;
    public String question;


    public Question(){
        answers = new ArrayList();
    }

    public Question(String question){
        answers = new ArrayList();
        this.question = question;
    }

    public List getChoices(){
        return answers;
    }

    public void setChoices(List choices){
        this.answers = choices;
    }

    public String getQuestion(){
        return question;
    }

    public void setQuestion(String question){
        this.question = question;
    }

    public String getOid(){
        return oid;
    }

    public void setOid(String oid){
        this.oid = oid;
    }

    public void addChoice(Choice choice){
        answers.add(choice);
    }

    public void removeChoice(Choice choice){
        answers.remove(choice);
    }

    public void newDesc(String desc){
        this.question = desc;
    }
}
