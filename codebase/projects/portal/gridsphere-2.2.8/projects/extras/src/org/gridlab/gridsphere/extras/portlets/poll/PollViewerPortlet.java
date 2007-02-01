
package org.gridlab.gridsphere.extras.portlets.poll;

import org.gridlab.gridsphere.provider.portlet.jsr.ActionPortlet;
import org.gridlab.gridsphere.provider.event.jsr.RenderFormEvent;
import org.gridlab.gridsphere.provider.event.jsr.ActionFormEvent;
import org.gridlab.gridsphere.provider.portletui.beans.*;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.core.user.UserManagerService;
import org.gridlab.gridsphere.services.core.charts.ChartService;
import org.gridlab.gridsphere.extras.services.poll.*;
import org.gridlab.gridsphere.services.core.charts.*;
import org.gridlab.gridsphere.services.core.secdir.FileLocationID;

import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.PortletException;
import java.util.Map;
import java.util.List;
import java.util.Calendar;
import java.util.Iterator;
import java.io.IOException;



public class PollViewerPortlet extends ActionPortlet {


     private static PortletLog log = SportletLog.getInstance(PollViewerPortlet.class);
     private static final String POLLAPP = "myPollApplication";
     private ChartService chartService = null;
     private PollService pollService = null;
     private ChoiceService choiceService = null;
     private UsersService polluserservice = null;

     private UserManagerService userManagerService = null;
     private Poll temp = null;
     boolean hasvoted;


     public void init(PortletConfig config) throws PortletException {
        super.init(config);
        try {
             chartService = (ChartService) createPortletService(ChartService.class);
             pollService = (PollService) createPortletService(PollService.class);
             choiceService = (ChoiceService) createPortletService(ChoiceService.class);
             polluserservice = (UsersService) createPortletService(UsersService.class);
             userManagerService = (UserManagerService) createPortletService(UserManagerService.class);
         } catch (PortletServiceException e) {
             log.error("Unable to initialize Service: "+ e);
         }

         DEFAULT_VIEW_PAGE = "createP";
    }

    public void create(RenderFormEvent event)throws PortletException{
        fillListBoxPolls(event);
        if(hasvoted)temp=null;
          if(temp == null)event.getRenderRequest().setAttribute("submit","");
            else event.getRenderRequest().setAttribute("submit","Submit");
        event.getRenderRequest().setAttribute("poll",temp);
        setNextState(event.getRenderRequest(), "poll/pollview.jsp");
    }
    public void createP(RenderFormEvent event)throws PortletException{
        fillListBoxPolls(event);
        event.getRenderRequest().setAttribute("submit","");
        event.getRenderRequest().setAttribute("poll",null);
        setNextState(event.getRenderRequest(), "poll/pollview.jsp");
    }

    public double percent(Choice choice)throws ArithmeticException{
        double allVoices = 0;
        List choices = ((Question)temp.getQuestions().get(0)).getChoices();
        for(int i = 0; i<choices.size(); i++){
            allVoices = allVoices + ((Choice)choices.get(i)).getVoices();
        }
        double choiceVoices = choice.getVoices();
        if(choiceVoices ==0 || allVoices ==0)return 0;
        else return (double)(Math.floor(((100*choiceVoices)/allVoices)*10)/10);
    }

    public void createPoll(RenderFormEvent event) throws PortletException,IOException, Exception {
        String userID=(String) ((Map) event.getRenderRequest().getAttribute(PortletRequest.USER_INFO)).get("user.name.id");
        fillListBoxPolls(event);
        try{
           String graphic = temp.getGraphic();
           if(graphic.equals(new String("bar"))) createBar3DChart(event);
             else createPie3DChart(event);
           FileLocationID chartLocationID = chartService.createChartLocationID(userID, POLLAPP, "some/directort\\with/poll");
           String chartURL = chartService.getChartUrl(chartLocationID);
           ImageBean imageBean=event.getImageBean("poll");
           if(chartURL!=null) imageBean.setSrc(chartURL);
        }catch(Exception e){}

        event.getRenderRequest().setAttribute("submit","");
        event.getRenderRequest().setAttribute("poll",null);
        setNextState(event.getRenderRequest(), "poll/pollview.jsp");

    }

    public void createBar3DChart(RenderFormEvent event)throws IOException, Exception{
        String userID=(String) ((Map) event.getRenderRequest().getAttribute(PortletRequest.USER_INFO)).get("user.name.id");
        org.jfree.data.DefaultCategoryDataset dataset=new org.jfree.data.DefaultCategoryDataset();
         try{
            int s =((Question)temp.getQuestions().get(0)).getChoices().size();
            for(int i=0;i<s; i++){
               Choice choice = (Choice)((Question)temp.getQuestions().get(0)).getChoices().get(i);
               String s1 = choice.getChoice() + String.valueOf(choice.getVoices());
               dataset.setValue(choice.getVoices(), s1, String.valueOf(percent(choice)));
            }
            FileLocationID chartLocationID = chartService.createChartLocationID(userID, POLLAPP, "some/directort\\with/poll");

            ChartDescriptor chartDescriptor = chartService.createBar3DChart(chartLocationID, dataset);
            //ChartDescriptor chartDescriptor=chartService.createBar3DChart(userID, POLLAPP, "some/directort\\with/Poll" ,dataset);

//            All settings below are optional

//            setting descriptions for axises
            chartDescriptor.getChartInfo().getPlot().getSettings().getCategory().setRangeAxisLabel(String.valueOf(temp.getVoices())+" voices");
            chartDescriptor.getChartInfo().getPlot().getSettings().getCategory().setDomainAxisLabel("answers");

//            setting image for JPEG, maximum quality, image size(650,350)
            Image image=chartDescriptor.getFileInfo().getImage();
            image.setWidth(650);
            image.setHeight(350);
            image.setQuality(1.0f);
            image.setType("JPEG");

           // chartService.setChartDescriptor(userID, POLLAPP, "some/directort\\with/Poll", chartDescriptor);
           // chartService.setChartTitle(userID, POLLAPP, "some/directort\\with/Poll",((Question)temp.getQuestions().get(0)).getQuestion());
         } catch (Exception e) {}
     }

     public void createPie3DChart(RenderFormEvent event)throws PortletException{
           String userID=(String) ((Map) event.getRenderRequest().getAttribute(PortletRequest.USER_INFO)).get("user.name.id");
           org.jfree.data.DefaultPieDataset dataset=new org.jfree.data.DefaultPieDataset();
           try{
              int s =((Question)temp.getQuestions().get(0)).getChoices().size();
              for(int i=0;i<s; i++){
                 Choice choice = (Choice)((Question)temp.getQuestions().get(0)).getChoices().get(i);
                 dataset.setValue(choice.getChoice(), choice.getVoices());
           }
           fillListBoxPolls(event);
            FileLocationID chartLocationID = chartService.createChartLocationID(userID, POLLAPP, "poll");
            ChartDescriptor chartDescriptor = chartService.createPie3DChart(chartLocationID, dataset);
          // ChartDescriptor chartDescriptor=chartService.createPie3DChart(userID, POLLAPP, "some/directort\\with/Poll",dataset);
            chartDescriptor.getChartInfo().getPlot().setForegroundAlpha(0.5f);

            BackgroundPaint backgroundPaint=new BackgroundPaint();
            Color grey=new Color();
            grey.setBlue(200);
            grey.setRed(200);
            grey.setGreen(200);
            Gradient background=new Gradient();
            GradientPoint point1=new GradientPoint();
            point1.setColor(grey);
            point1.setX(0);
            point1.setY(0);
            GradientPoint point2=new GradientPoint();
            point2.setColor(grey);
            point2.setX(400);
            point2.setY(300);
            background.addGradientPoint(point1);
            background.addGradientPoint(point2);

            backgroundPaint.setGradient(background);
            chartDescriptor.getChartInfo().getPlot().setBackgroundPaint(backgroundPaint);

//            turn off legend

            chartDescriptor.getChartInfo().setLegend(false);

//            setting label generator - {0} for name {1} for value {2} for percents

            chartDescriptor.getChartInfo().getPlot().getSettings().getPie().setLabelGenerator("{0} {2}");

//            After making settings chartDescriptor have to be passed to method setChartDescriptor !!!

            String title;
            if(temp.getVoices() == 0)title ="no voites";
            else title =  ((Question)temp.getQuestions().get(0)).getQuestion()+"   "+temp.getVoices()+"voices";

           // chartService.setChartDescriptor(userID, POLLAPP, "some/directort\\with/Poll", chartDescriptor);
            //chartService.setChartTitle(userID, POLLAPP, "some/directort\\with/Poll", title);
        } catch (Exception e) {}
     }


      public void fillListBoxPolls(ActionFormEvent event) throws PortletException{
         ListBoxBean alb = event.getListBoxBean("polls");
         alb.clear();
         alb.setMultipleSelection(false);
         String userID=(String) ((Map) event.getActionRequest().getAttribute(PortletRequest.USER_INFO)).get("user.name.id");
         User user = this.userManagerService.getUser(userID);
         //PortletRole role = aclManagerService.getRoleInGroup(user, PortletGroupFactory.GRIDSPHERE_GROUP);
         try{
            List pollslist = pollService.getPolls();
            if (pollslist.size()>0) {
              for (int i=0; i<pollslist.size(); i++) {
                 Poll poll = (Poll)pollslist.get(i);
                 //if(poll.getHiddenForSuper() && role.isSuper()) break;
                  // else if(poll.getHiddenForGuest() && role.isGuest())break;
                    // else if(poll.getHiddenForUser() && role.isUser())break;
                 ListBoxItemBean item = new ListBoxItemBean();
                 item.setName(poll.getOid());
                 item.setValue(poll.getTitle());
                 if((temp != null)&& (poll.getOid().equals(temp.getOid()))){
                    item.setSelected(true);
                }
                    alb.addBean(item);
              }
              alb.sortByValue();
            }
         }catch(Exception e){
             log.error(e.toString());
         }
      }

      public void fillListBoxPolls(RenderFormEvent event) throws PortletException{
        ListBoxBean alb = event.getListBoxBean("polls");
        alb.clear();
        alb.setMultipleSelection(false);
        String userID=(String) ((Map) event.getRenderRequest().getAttribute(PortletRequest.USER_INFO)).get("user.name.id");
        User user = this.userManagerService.getUser(userID);
        //PortletRole role = aclManagerService.getRoleInGroup(user, PortletGroupFactory.GRIDSPHERE_GROUP);
         try{
            List pollslist = pollService.getPolls();
            if (pollslist.size()>0) {
              for (int i=0; i<pollslist.size(); i++) {
                 Poll poll = (Poll)pollslist.get(i);
                // if(poll.getHiddenForSuper() && role.isSuper()) break;
                  // else if(poll.getHiddenForGuest() && role.isGuest())break;
                     //else if(poll.getHiddenForUser() && role.isUser())break;
                 ListBoxItemBean item = new ListBoxItemBean();
                 item.setName(poll.getOid());
                 item.setValue(poll.getTitle());
                 if((temp != null)&&((poll.getOid().equals(temp.getOid()))))item.setSelected(true);
                 alb.addBean(item);
              }
              alb.sortByValue();
            }
         }catch(Exception e){
             log.error(e.toString());
         }
      }

    public void gotoPoll(ActionFormEvent event) throws PortletException{
        String userID=(String) ((Map) event.getActionRequest().getAttribute(PortletRequest.USER_INFO)).get("user.name.id");
        FrameBean frame = event.getFrameBean("frame");
        ListBoxBean alb = event.getListBoxBean("polls");
        String value = "";
        try{
            if(alb.hasSelectedValue()){
                String oid = alb.getSelectedName();
                temp = pollService.getPollByOid(oid);
                hasvoted = temp.hasVoted(userID);
              try{

                 if(hasvoted){
                    frame.setValue("you have allrady voted,you can't vote again!");
                 }
                 else{
                    PollUsers pu = new PollUsers();
                    pu.setUserId(userID);
                    temp.addUserId(pu);
                    polluserservice.addPollUser(pu);
                    pollService.savePoll(temp);
                 }
                }catch(Exception e){}
           }
           else{
               value = "Please select poll";
               event.getActionRequest().setAttribute("value", value);
          }
          if(temp.getMultiple()){
             CheckBoxBean chbox = event.getCheckBoxBean("multiplechoice");
             chbox.setSelected(false);
          }
          else{
             RadioButtonBean rb = event.getRadioButtonBean("choicebutton");
             rb.setSelected(false);
          }
          if(!hasvoted)temp = pollService.getPollByOid(temp.getOid());
       }catch(Exception e){
          if(!alb.hasSelectedValue()) {
            frame.setValue("No polls in the list!");
          }
       }
        fillListBoxPolls(event);
        setNextState(event.getActionRequest(), "create");
    }

    public void showPoll(ActionFormEvent event)throws PortletException{
        ListBoxBean alb = event.getListBoxBean("polls");
        if(!alb.hasSelectedValue()){
            FrameBean frame = event.getFrameBean("frame");
            frame.setValue("No polls in the list!");
        }
        try{
            String oid = alb.getSelectedName();
            temp = pollService.getPollByOid(oid);

       }catch(Exception e){}

        event.getActionRequest().setAttribute("submit","");
        setNextState(event.getActionRequest(), "createPoll");

    }

    public void submitChoice(ActionFormEvent event) throws PortletException{
          try{
          CheckBoxBean chbox = event.getCheckBoxBean("multiple");
          RadioButtonBean rb = event.getRadioButtonBean("single");
          FrameBean frame = event.getFrameBean("frame");
          if(temp != null){
              Calendar currentDate = Calendar.getInstance();
              if(!temp.getMultiple()){
                if(rb.isSelected()){
                   String rbtype = rb.getSelectedValue();
                   System.err.println("rbtype= " + rbtype);
                   if(temp.getUntilDate().after(currentDate)){
                      int choiceValue = Integer.parseInt(rb.getSelectedValue());
                      Choice choice = (Choice)((Question)temp.getQuestions().get(0)).getChoices().get(choiceValue);
                      choice.doVote();
                      choiceService.saveChoice(choice);
                      temp.doVote();
                      pollService.savePoll(temp);
                      temp = pollService.getPollByOid(temp.getOid());
                   }
                   else{
                      frame.setValue("this poll in no more valid,you can't vote");
                   }
                }

                else{
                   frame.setValue("Please select a choice!");
                   setNextState(event.getActionRequest(), "create");
                }
                setNextState(event.getActionRequest(), "createPoll");
               }

             else {
                  if(chbox.isSelected()){
                      List selectedChoices = chbox.getSelectedValues();
                      if(temp.getUntilDate().after(currentDate)){
                      Iterator it = selectedChoices.iterator();
                      while(it.hasNext()){
                         String checkboxvalue = (String)it.next();
                         int choiceval = Integer.valueOf(checkboxvalue).intValue();
                         Choice choice = (Choice)((Question)temp.getQuestions().get(0)).getChoices().get(choiceval);
                         choice.doVote();
                         choiceService.saveChoice(choice);
                      }
                      temp.doVote();
                      pollService.savePoll(temp);
                      temp = pollService.getPollByOid(temp.getOid());
                      }
                      else{
                          frame.setValue("this poll in no more valid,you can't vote");
                      }
                  }
                  else{

                       frame.setValue("Please select a choice!");
                       setNextState(event.getActionRequest(), "create");
                  }

               }
                setNextState(event.getActionRequest(), "createPoll");

          }
          else{
                frame.setValue("Please select a poll!");
                setNextState(event.getActionRequest(), "create");
          }
          rb.setSelected(false);
          chbox.setSelected(false);
    }catch(Exception e){}
    }
}