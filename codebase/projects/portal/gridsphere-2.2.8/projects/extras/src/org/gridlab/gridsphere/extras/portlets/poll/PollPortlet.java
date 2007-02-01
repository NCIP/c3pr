
package org.gridlab.gridsphere.extras.portlets.poll;

import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.provider.portlet.jsr.ActionPortlet;
import org.gridlab.gridsphere.provider.event.jsr.ActionFormEvent;
import org.gridlab.gridsphere.provider.event.jsr.RenderFormEvent;
import org.gridlab.gridsphere.provider.portletui.beans.*;
import org.gridlab.gridsphere.extras.services.poll.*;

import javax.portlet.*;
import java.util.List;
import java.util.Calendar;



public class PollPortlet extends ActionPortlet{


        private static PortletLog log = SportletLog.getInstance(PollPortlet.class);
        private PollService pollservice = null;
        private QuestionService questionservice = null;
        private ChoiceService choiceservice = null;
        private boolean newPoll = true;
        private boolean editpoll = false;
        Poll temp = null;


      public void init(PortletConfig config)throws PortletException{
           super.init(config);
           try {

             pollservice = (PollService) createPortletService(PollService.class);
             questionservice = (QuestionService) createPortletService(QuestionService.class);
             choiceservice = (ChoiceService) createPortletService(ChoiceService.class);

            } catch (PortletServiceException e) {
                 log.error("Unable to initialize PollService: "+ e);
            }
            DEFAULT_VIEW_PAGE = "showPolls";
      }

      public void showPolls(RenderFormEvent event)throws PortletException{
          PortletRequest request = event.getRenderRequest();
          ListBoxBean alb = event.getListBoxBean("polls");
          alb.setSize(0);
          alb.clear();
          alb.setMultipleSelection(false);
          try{
          List pollslist = pollservice.getPolls();
          if (pollslist.size()>0) {
             for (int i=0; i<pollslist.size(); i++) {
                 Poll poll = (Poll)pollslist.get(i);
                 ListBoxItemBean item = new ListBoxItemBean();
                 item.setName(poll.getOid());
                 item.setValue(poll.getTitle());
                 alb.addBean(item);
             }

             alb.sortByValue();
          }
          }catch(Exception e){}

          fillListDates(event);
          request.setAttribute("poll",null);
          request.setAttribute("actionsubmit", "CREATE POLL");
          setNextState(request, "poll/editpoll.jsp");
      }

       public void showPolls(ActionFormEvent event)throws PortletException{
          PortletRequest request = event.getActionRequest();
          ListBoxBean alb = event.getListBoxBean("polls");
          RadioButtonBean rb = event.getRadioButtonBean("graphic");
          rb.setSelected(false);
          temp = null;
          alb.setSize(0);
          alb.clear();
          alb.setMultipleSelection(false);
          try{
          List pollslist = pollservice.getPolls();
          if (pollslist.size()>0) {
             for (int i=0; i<pollslist.size(); i++) {
                 Poll poll = (Poll)pollslist.get(i);
                 ListBoxItemBean item = new ListBoxItemBean();
                 item.setName(poll.getOid());
                 item.setValue(poll.getTitle());
                 alb.addBean(item);
             }

             alb.sortByValue();
          }
          }catch(Exception e){}

           newPoll = true;
          deleteText(event);
          fillListDates(event);
          request.setAttribute("poll",null);
          request.setAttribute("actionsubmit", "CREATE POLL");
          setNextState(request, "poll/editpoll.jsp");
      }

      public void applyDesc(ActionFormEvent event)throws PortletException{
         PortletRequest request = event.getActionRequest();
         TextFieldBean desc = event.getTextFieldBean("newDesc");
         if(! desc.getValue().equals(new String("")) || editpoll){
            if(newPoll){
               Poll poll = new Poll(desc.getValue());
               temp = poll;
               newPoll = false;
           }
            else{
               temp.setTitle(desc.getValue());
               if(editpoll) fillFields(event);
           }
         }
         else{
               FrameBean frame = event.getFrameBean("frame");
               frame.setValue("Please put a description!");
         }
         RadioButtonBean chb = event.getRadioButtonBean("picture");
          chb.setSelected(false);
         fillListDates(event);
         fillListBoxPolls(event);
         fillListBoxChoices(event);
         request.setAttribute("poll",temp);
         if(!editpoll)request.setAttribute("actionsubmit", "CREATE POLL");
          else request.setAttribute("actionsubmit", "Save changes");
         setNextState(request, "poll/editpoll.jsp");
      }

      public void applyQuestion(ActionFormEvent event)throws PortletException{
         PortletRequest request = event.getActionRequest();
         fillListBoxPolls(event);
         fillListBoxChoices(event);
         TextFieldBean desc = event.getTextFieldBean("newQuestion");
         if(newPoll != true){
            if(!desc.getValue().equals(new String(""))){
                Question question = new Question(desc.getValue());
                if(temp.getQuestions().size() != 0){
                   Question q = (Question) temp.getQuestions().get(0);
                   q.newDesc(desc.getValue());
                }
                else {
                   temp.addQuestion(question);
                }
            }
             else{
                  FrameBean frame = event.getFrameBean("frame");
                  frame.setValue("Please create a question!");
            }
            }

          else{
               FrameBean frame = event.getFrameBean("frame");
               frame.setValue("Please create first a polldescription!");
         }
         RadioButtonBean chb = event.getRadioButtonBean("picture");
          chb.setSelected(false);
          fillListDates(event);
         request.setAttribute("poll",temp);
         if(!editpoll)request.setAttribute("actionsubmit", "CREATE POLL");
          else request.setAttribute("actionsubmit", "Save changes");
         setNextState(request, "poll/editpoll.jsp");
      }

      public void applyChoice(ActionFormEvent event)throws PortletException{
         PortletRequest request = event.getActionRequest();
         fillListBoxPolls(event);
         TextFieldBean desc = event.getTextFieldBean("newChoice");
         Choice choice = new Choice(desc.getValue());
         if(editpoll){
             if(! desc.getValue().equals(new String(""))) {
                 Question question = (Question)temp.getQuestions().get(0);
                 question.getChoices().add(choice);
             }
            else{
                FrameBean frame = event.getFrameBean("frame");
                frame.setValue("Please put a choice!");
            }
             fillFields(event);
         }
         else{
             if(!temp.getTitle().equals(new String(""))){
             TextFieldBean desc2 = event.getTextFieldBean("newQuestion");
             if((! desc2.getValue().equals(new String("")))){
                if(! desc.getValue().equals(new String(""))){
                   Question question = (Question)temp.getQuestions().get(0);
                   if(question != null){
                       question.getChoices().add(choice);
                   }
               }
               else{
                    FrameBean frame = event.getFrameBean("frame");
                    frame.setValue("Please put a choice!");
               }
            }
            else{
               FrameBean frame = event.getFrameBean("frame");
               frame.setValue("Please create first a question!");
            }
             }
         else{
             FrameBean frame = event.getFrameBean("frame");
               frame.setValue("Please create first a polldescription!");
         }

         }
          desc.setValue("");
         RadioButtonBean chb = event.getRadioButtonBean("graphic");
         chb.setSelected(false);
         fillListDates(event);
         fillListBoxChoices(event);
         if(!editpoll)request.setAttribute("actionsubmit", "CREATE POLL");
          else request.setAttribute("actionsubmit","Save changes");
         request.setAttribute("poll",temp);
         setNextState(request, "poll/editpoll.jsp");
      }

      public void fillListBoxPolls(ActionFormEvent event)throws PortletException{
         ListBoxBean alb = event.getListBoxBean("polls");
         alb.clear();
         alb.setMultipleSelection(false);
         try{
         List pollslist = pollservice.getPolls();
         if (pollslist.size()>0) {
             for (int i=0; i<pollslist.size(); i++) {
                 Poll poll = (Poll)pollslist.get(i);
                 ListBoxItemBean item = new ListBoxItemBean();
                 item.setName(poll.getOid());
                 item.setValue(poll.getTitle());
                 if(temp != null){
                     if(temp.getTitle().equals(poll.getTitle()))item.setSelected(true);
                 }
                 alb.addBean(item);
             }
         alb.sortByValue();
         }
         }catch(Exception e){log.error(e.toString());}
      }

      public void fillListBoxChoices(ActionFormEvent event)throws PortletException{
          ListBoxBean alb = event.getListBoxBean("choices");
          alb.setSize(1);
          alb.clear();
          alb.setMultipleSelection(false);
          try{
          Question q = (Question)temp.getQuestions().get(0);
          List choices = q.getChoices();
          if(choices.size() > 0){
             for(int i=0; i<choices.size(); i++){
                 Choice answer = (Choice)choices.get(i);
                 ListBoxItemBean item = new ListBoxItemBean();
                 item.setName(answer.getOid());
                 item.setValue(answer.getChoice());
                 alb.addBean(item);
             }
           alb.sortByValue();
          }
          }catch(Exception e){ log.error(e.toString());}
      }

      public void deletePoll(ActionFormEvent event)throws PortletException{
          PortletRequest request = event.getActionRequest();
          ListBoxBean alb = event.getListBoxBean("polls");
          String value = "";
          Poll poll = null;
          try{
          if(alb.hasSelectedValue()){
             String oid = alb.getSelectedName();
             poll = pollservice.getPollByOid(oid);
                 Question question = (Question)poll.getQuestions().get(0);
                 if(question != null){
                   if(question.getChoices() != null){
                       List choices = question.getChoices();
                       for(int i =0; i<choices.size(); i++){
                           Choice choice = (Choice)choices.get(i);
                           question.removeChoice(choice);
                           choiceservice.deleteChoice(choice);
                       }
                    }
                poll.removeQuestion(question);
                questionservice.deleteQuestion(question);
                }
             pollservice.deletePoll(poll);
          }
          else{
              value = "Please, select poll";
              event.getActionRequest().setAttribute("value", value);
          }
              fillListBoxPolls(event);

          } catch(Exception e){
                 log.error(e.toString());
             }
          deleteText(event);
          newPoll = true;
          fillListDates(event);
          setNextState(request, "poll/editpoll.jsp");
      }

      public void showPoll(ActionFormEvent event)throws PortletException{
            PortletRequest request = event.getActionRequest();
            ListBoxBean alb = event.getListBoxBean("polls");
       try{
            Poll poll = temp;
            String oid = alb.getSelectedName();
            poll = pollservice.getPollByOid(oid);
            temp = poll;
            poll = pollservice.getPollByOid(poll.getOid());
            Question q = (Question)poll.getQuestions().get(0);
          /*
           List choices = q.getChoices();
           for(int i = 0; i<choices.size(); i++){
               Choice ch = (Choice)choices.get(i);
           }  */
            fillListBoxPolls(event);
            fillListBoxChoices(event);
            fillListDates(event);
            deleteText(event);
            request.setAttribute("actionsubmit", "CREATE POLL");
            request.setAttribute("poll",poll);
            setNextState(request, "poll/editpoll.jsp");
          }catch(Exception e){}

      }

      public void deleteChoice(ActionFormEvent event)throws PortletException{
          PortletRequest request = event.getActionRequest();
          RadioButtonBean rb = event.getRadioButtonBean("choicebutton");
          String value = "";
          if(rb.isSelected()){
             String rbtype = rb.getSelectedValue();
             rb.setSelected(false);
             int choiceValue = Integer.valueOf(rbtype).intValue();
             Choice choice = (Choice)((Question)temp.getQuestions().get(0)).getChoices().get(choiceValue);

              Question q = (Question)temp.getQuestions().get(0);
              q.removeChoice(choice);

             choiceservice.deleteChoice(choice);
             questionservice.saveQuestion(q);
             pollservice.savePoll(temp);
          }
          else{
                value = "Please select choice";
                event.getActionRequest().setAttribute("value", value);
         }
         fillFields(event);
         TextFieldBean desc = event.getTextFieldBean("newChoice");
         desc.setValue("");
         fillListBoxPolls(event);
         fillListBoxChoices(event);
          if(editpoll){

              request.setAttribute("actionsubmit", "Save changes");
          }
          else{
              request.setAttribute("actionsubmit", "CREATE POLL");
          }
         fillListDates(event);
         request.setAttribute("poll",temp);
         setNextState(request, "poll/editpoll.jsp");
      }

    public void createPoll(ActionFormEvent event)throws PortletException{
         RadioButtonBean chb = event.getRadioButtonBean("graphic");
         PortletRequest request = event.getActionRequest();
         CheckBoxBean chb1 = event.getCheckBoxBean("multiple");
         FrameBean frame = event.getFrameBean("frame2");
         TextFieldBean desc = event.getTextFieldBean("newDesc");
         TextFieldBean desc1 = event.getTextFieldBean("newQuestion");
         TextFieldBean desc2 = event.getTextFieldBean("newChoice");
         if(desc.getValue().equals(new String("")) ||  desc1.getValue().equals(new String("")) ||
                 ((Question)temp.getQuestions().get(0)).getChoices() == null){
              frame.setValue("unfull polldata");

        }
        else{

           ListBoxBean day1 = event.getListBoxBean("day1");
           ListBoxBean day2 = event.getListBoxBean("day2");
           ListBoxBean month1 = event.getListBoxBean("month1");
           ListBoxBean month2 = event.getListBoxBean("month2");
           ListBoxBean year1 = event.getListBoxBean("year1");
           ListBoxBean year2 = event.getListBoxBean("year2");
           Calendar dateFrom = Calendar.getInstance();
           Calendar dateUntil = Calendar.getInstance();
           dateFrom.set(Integer.parseInt(year1.getSelectedValue()), Integer.parseInt(month1.getSelectedName()), Integer.parseInt(day1.getSelectedValue()));
           dateUntil.set(Integer.parseInt(year2.getSelectedValue()), Integer.parseInt(month2.getSelectedName()), Integer.parseInt(day2.getSelectedValue()));

            if(dateFrom.after(dateUntil)){
                frame.setValue("Do you really want finish the poll,befor it starts");
            }
            else{
                if( ! chb.isSelected()){
                    frame.setValue("Please select a graphic");
                }
                else{
                        String rbtype = chb.getSelectedValue();
                        int graphic = Integer.valueOf(rbtype).intValue();

                        if(graphic ==1) temp.setGraphic("pie");
                        else  temp.setGraphic("bar");

                     if(chb1.isSelected())temp.setMultiple(true);
                     else temp.setMultiple(false);
                     temp.setFromDate(dateFrom);
                     temp.setUntilDate(dateUntil);
                     saveDB(temp);
                     deleteText(event);
                     if(editpoll){
                         editpoll = false;
                     }
                     newPoll = true;
                     desc.setValue(" ");
                     desc1.setValue(" ");
                     desc2.setValue(" ");
                     temp = null;
                }
            }
         }
        chb1.setSelected(false);
        chb.setSelected(false);
        request.setAttribute("actionsubmit", "CREATE POLL");
        fillListBoxPolls(event);
        fillListBoxChoices(event);
        fillListDates(event);
        request.setAttribute("poll",temp);
        setNextState(request, "poll/editpoll.jsp");

    }

      public void saveDB(Poll poll){
        if(editpoll){
          Question question = (Question)poll.getQuestions().get(0);
          List chlist = question.getChoices();
          for(int i = 0; i<chlist.size(); i++){
             Choice ch = (Choice)chlist.get(i);
             choiceservice.saveChoice(ch);
          }

          questionservice.saveQuestion(question);
          pollservice.savePoll(poll);

        }
          else{
        Question question = (Question)poll.getQuestions().get(0);
        List chlist = question.getChoices();
        for(int i = 0; i<chlist.size(); i++){
            Choice ch = (Choice)chlist.get(i);
            choiceservice.addChoice(ch);
        }

        questionservice.addQuestion(question);
        pollservice.addPoll(poll);
        }
      }

     public void editPoll(ActionFormEvent event)throws PortletException{
         PortletRequest request = event.getActionRequest();
         ListBoxBean alb = event.getListBoxBean("polls");
         newPoll = false;
         editpoll = true;
         try{
            Poll poll = temp;
            String oid = alb.getSelectedName();
            poll = pollservice.getPollByOid(oid);
            temp = poll;
            fillFields(event);
         }catch(Exception e){}

        fillListBoxPolls(event);
        fillListBoxChoices(event);
        fillListDates(event);
        request.setAttribute("actionsubmit", "Save changes");
        request.setAttribute("poll",temp);
        setNextState(request, "poll/editpoll.jsp");
      }

      public void fillFields(ActionFormEvent event){
        try{
            TextFieldBean desc = event.getTextFieldBean("newDesc");
            TextFieldBean desc1 = event.getTextFieldBean("newQuestion");
            if(temp.getTitle() != null) desc.setValue(temp.getTitle());
            if(((Question)temp.getQuestions().get(0)) != null){
                 desc1.setValue(((Question)temp.getQuestions().get(0)).getQuestion());
            }
        }catch(Exception e){}
    }

    public void deleteText(ActionFormEvent event){

         TextFieldBean desc = event.getTextFieldBean("newDesc");
         TextFieldBean desc1 = event.getTextFieldBean("newQuestion");
         TextFieldBean desc2 = event.getTextFieldBean("newChoice");
         desc.setValue("");
         desc1.setValue("");
         desc2.setValue("");
    }

    public void fillListDates(RenderFormEvent event){
        Calendar cal = Calendar.getInstance();
        Calendar startdate = null;
        Calendar enddate = null;
        int selday1 = -1;
        int selday2 = -1;
        int selmonth1 = -1;
        int selmonth2 = -1;
        if((temp != null) && (temp.getFromDate() != null)){
            startdate = temp.getFromDate();
            enddate = temp.getUntilDate();
        }
        else{
            startdate = cal;
            enddate = cal;
        }
        selday1 = startdate.get(Calendar.DAY_OF_MONTH);
        selday2 = enddate.get(Calendar.DAY_OF_MONTH);
        selmonth1 = startdate.get(Calendar.MONTH);
        selmonth2 = enddate.get(Calendar.MONTH);

        ListBoxBean day1 = event.getListBoxBean("day1");
        ListBoxBean day2 = event.getListBoxBean("day2");
        day1.setSize(1);
        day1.clear();
        day1.setMultipleSelection(false);
        day2.setSize(1);
        day2.clear();
        day2.setMultipleSelection(false);
        for(int i=1; i<=31; i++){
            fillListBox(day1, day2, String.valueOf(i), i, (selday1 == i),(selday2 == i));
        }

        ListBoxBean month1 = event.getListBoxBean("month1");
        ListBoxBean month2 = event.getListBoxBean("month2");
        month1.setSize(1);
        month1.clear();
        month1.setMultipleSelection(false);
        month2.setSize(1);
        month2.clear();
        month2.setMultipleSelection(false);
        fillListBox(month1, month2, "Januar", 0, (selmonth1 == 0), (selmonth2 == 0));
        fillListBox(month1, month2, "February", 1, (selmonth1 == 1), (selmonth2 == 1));
        fillListBox(month1, month2, "March", 2,(selmonth1 == 2), (selmonth2 == 2));
        fillListBox(month1, month2, "April", 3, (selmonth1 == 3), (selmonth2 == 3));
        fillListBox(month1, month2, "May", 4, (selmonth1 == 4), (selmonth2 == 4));
        fillListBox(month1, month2, "June", 5, (selmonth1 == 5), (selmonth2 == 5));
        fillListBox(month1, month2, "July", 6, (selmonth1 == 6), (selmonth2 == 6));
        fillListBox(month1, month2, "August", 7,(selmonth1 == 7), (selmonth2 == 7));
        fillListBox(month1, month2, "Septemer", 8,(selmonth1 == 8), (selmonth2 == 8));
        fillListBox(month1, month2, "October", 9,(selmonth1 == 9), (selmonth2 == 9));
        fillListBox(month1, month2, "November", 10,(selmonth1 == 10), (selmonth2 == 10));
        fillListBox(month1, month2, "December", 11,(selmonth1 == 11), (selmonth2 == 11));

        ListBoxBean year1 = event.getListBoxBean("year1");
        ListBoxBean year2 = event.getListBoxBean("year2");
        year1.setSize(1);
        year1.clear();
        year1.setMultipleSelection(false);
        year2.setSize(1);
        year2.clear();
        year2.setMultipleSelection(false);
        for(int i=0; i<11; i++){
            int year = cal.get(Calendar.YEAR);
            fillListBox(year1, year2, String.valueOf(year+i), (year+i),
                    ((startdate.get(Calendar.YEAR)) == (year+i)), ((enddate.get(Calendar.YEAR)) == (year+i)));
        }
    }

    public void fillListDates(ActionFormEvent event){
        Calendar cal = Calendar.getInstance();
        Calendar startDate = null;
        Calendar endDate = null;
        int selday1 = -1;
        int selday2 = -1;
        int selmonth1 = -1;
        int selmonth2 = -1;
         if((temp!= null) && (temp.getFromDate() != null)){
             startDate = temp.getFromDate();
             endDate = temp.getUntilDate();
         }
        else{
             startDate = cal;
             endDate = cal;
         }
        selday1 = startDate.get(Calendar.DAY_OF_MONTH);
        selday2 = endDate.get(Calendar.DAY_OF_MONTH);
        selmonth1 = startDate.get(Calendar.MONTH);
        selmonth2 = endDate.get(Calendar.MONTH);

        ListBoxBean day1 = event.getListBoxBean("day1");
        ListBoxBean day2 = event.getListBoxBean("day2");
        day1.setSize(1);
        day1.clear();
        day1.setMultipleSelection(false);
        day2.setSize(1);
        day2.clear();
        day2.setMultipleSelection(false);
        for(int i=1; i<=31; i++){
            fillListBox(day1, day2, String.valueOf(i), i, (selday1 == i), (selday2 == i));
        }

        ListBoxBean month1 = event.getListBoxBean("month1");
        ListBoxBean month2 = event.getListBoxBean("month2");
        month1.setSize(1);
        month1.clear();
        month1.setMultipleSelection(false);
        month2.setSize(1);
        month2.clear();
        month2.setMultipleSelection(false);
        fillListBox(month1, month2, "Januar", 0, (selmonth1 == 0), (selmonth2 == 0));
        fillListBox(month1, month2, "February", 1, (selmonth1 == 1), (selmonth2 == 1));
        fillListBox(month1, month2, "March", 2,(selmonth1 == 2), (selmonth2 == 2));
        fillListBox(month1, month2, "April", 3, (selmonth1 == 3), (selmonth2 == 3));
        fillListBox(month1, month2, "May", 4, (selmonth1 == 4), (selmonth2 == 4));
        fillListBox(month1, month2, "June", 5, (selmonth1 == 5), (selmonth2 == 5));
        fillListBox(month1, month2, "July", 6, (selmonth1 == 6), (selmonth2 == 6));
        fillListBox(month1, month2, "August", 7,(selmonth1 == 7), (selmonth2 == 7));
        fillListBox(month1, month2, "Septemer", 8,(selmonth1 == 8), (selmonth2 == 8));
        fillListBox(month1, month2, "October", 9,(selmonth1 == 9), (selmonth2 == 9));
        fillListBox(month1, month2, "November", 10,(selmonth1 == 10), (selmonth2 == 10));
        fillListBox(month1, month2, "December", 11,(selmonth1 == 11), (selmonth2 == 11));
        ListBoxBean year1 = event.getListBoxBean("year1");
        ListBoxBean year2 = event.getListBoxBean("year2");
        year1.setSize(1);
        year1.clear();
        year1.setMultipleSelection(false);
        year2.setSize(1);
        year2.clear();
        year2.setMultipleSelection(false);
        for(int i=0; i<11; i++){
            int year = cal.get(Calendar.YEAR);
            fillListBox(year1, year2, String.valueOf(year+i), (year+i),((startDate.get(Calendar.YEAR))==(year+i)), ((endDate.get(Calendar.YEAR))==(year+i)));
        }
    }

    public void fillListBox(ListBoxBean lb1, ListBoxBean lb2, String itemValue,int name, boolean selected1,boolean selected2){
        ListBoxItemBean item = new ListBoxItemBean();
        ListBoxItemBean item2 = new ListBoxItemBean();
        item.setValue(itemValue);
        item2.setValue(itemValue);
        item.setName(String.valueOf(name));
        item2.setName(String.valueOf(name));
        if(selected1)item.setSelected(true);
        if(selected2) item2.setSelected(true);

        lb1.addBean(item);
        lb2.addBean(item2);
    }

    public void donePollHidden(ActionFormEvent event)throws PortletException{
        PortletRequest request = event.getActionRequest();
        CheckBoxBean chb = event.getCheckBoxBean("groups");
        List groups = chb.getSelectedValues();
        temp.setHiddenForUser(false);
        temp.setHiddenForGuest(false);
        temp.setHiddenForSuper(false);
        for(int i=0; i<groups.size(); i++){
            if(groups.get(i).equals(new String("USER"))){
                temp.setHiddenForUser(true);
            }
            if(groups.get(i).equals(new String("GUEST"))){
                temp.setHiddenForGuest(true);
            }
            if(groups.get(i).equals(new String("SUPER"))){
                temp.setHiddenForSuper(true);
            }
        }
        pollservice.savePoll(temp);
        temp = pollservice.getPollByOid(temp.getOid());

        newPoll = true;
        temp = null;
        chb.setSelected(false);
        showPolls(event);
        setNextState(request, "poll/editpoll.jsp");

    }
}
