<%@ page import="java.util.Locale, java.util.List,
                 org.gridlab.gridsphere.portlet.PortletLog,
                 org.gridlab.gridsphere.portlet.impl.SportletLog,
                 org.gridlab.gridsphere.extras.services.poll.Question,
                 org.gridlab.gridsphere.extras.services.poll.Choice,
                 org.gridlab.gridsphere.extras.portlets.poll.PollPortlet"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>


<jsp:useBean id="poll" class="org.gridlab.gridsphere.extras.services.poll.Poll" scope="request"/>
<jsp:useBean id="value" class="java.lang.String" scope="request"/>
<jsp:useBean id="actionsubmit" class="java.lang.String" scope="request"/>


<table>
<tr>
<td>

<ui:form>
    <h3>Display existing polls</h3>
     <ui:group>
        <ui:frame>
            <ui:text value="Select a poll:  "/>&nbsp;<ui:listbox beanId="polls"/>
            <ui:tablerow>
                <ui:tablecell width="60"/>
                <ui:tablecell>
                   <ui:actionsubmit action="showPoll" value="Show"/>
                   <ui:actionsubmit action="editPoll" value="Edit"/>
                   <ui:actionsubmit action="deletePoll" value="Delete"/>
                </ui:tablecell>
            </ui:tablerow>
            <ui:tablerow>
               <ui:tablecell height="15"/>
            </ui:tablerow>
       </ui:frame>
       </ui:group>
</ui:form>

    <ui:form>
    <h3>Create new poll</h3>
   <ui:group>
   <ui:frame>

        <ui:tablerow>
            <ui:tablecell>
            <ui:table width="100%">
               <ui:tablerow header="true">
                <ui:tablecell width="150">
                    <ui:text value="New Description:"/>
                </ui:tablecell>
                <ui:tablecell width="150">
                    <ui:text value="New Question:"/>
                </ui:tablecell>
                <ui:tablecell width="150">
                    <ui:text value="New Choice:"/>
                </ui:tablecell>
            </ui:tablerow>
                 <ui:tablerow >
                <ui:tablecell width="150">
                    <ui:textfield beanId="newDesc" value=" "/>
                </ui:tablecell>
                <ui:tablecell width="150">
                    <ui:textfield beanId="newQuestion"/>
                </ui:tablecell>
                <ui:tablecell width="150">
                    <ui:textfield beanId="newChoice" value=""/>
                </ui:tablecell>
            </ui:tablerow>
               <ui:tablerow>
                <ui:tablecell width="150">
                    <ui:actionsubmit action="applyDesc" value="Apply"/>
                </ui:tablecell>
                <ui:tablecell width="150">
                    <ui:actionsubmit action="applyQuestion" value="Apply"/>
                </ui:tablecell>
                <ui:tablecell width="150">
                    <ui:actionsubmit action="applyChoice" value="Apply"/>
                </ui:tablecell>
            </ui:tablerow>

            </ui:table>
            </ui:tablecell>
        </ui:tablerow>
     </ui:frame>
    <ui:frame beanId="frame"/>
     <ui:frame>
            <ui:tablerow>
               <ui:tablecell height="20"/>
            </ui:tablerow>
        <ui:tablerow>
            <ui:tablecell width="60">
                <ui:text value="start date"/>
            </ui:tablecell>
            <ui:tablecell width="20">
                <ui:listbox beanId="day1"/>
            </ui:tablecell>
            <ui:tablecell width="20">
                <ui:listbox beanId="month1"/>
            </ui:tablecell>
            <ui:tablecell width="20">
                <ui:listbox beanId="year1"/>
            </ui:tablecell>
            <ui:tablecell width="20"/>
            <ui:tablecell width="55">
                <ui:text value="end date"/>
            </ui:tablecell>
            <ui:tablecell width="20">
                <ui:listbox beanId="day2"/>
            </ui:tablecell>
            <ui:tablecell width="20">
                <ui:listbox beanId="month2"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:listbox beanId="year2"/>
            </ui:tablecell>
        </ui:tablerow>
     </ui:frame>
     <ui:frame>
            <ui:tablerow>
               <ui:tablecell height="15"/>
            </ui:tablerow>
     </ui:frame>
     <ui:frame>
<%
    try{
       boolean val1 = false;
       boolean val2 = false;
       boolean val3 = false;
       if(poll.getGraphic() != null &&  (poll.getGraphic()).equals(new String("pie")))val1 =true;
       else if(poll.getGraphic() !=null && (poll.getGraphic()).equals(new String("bar")))val2=true;
       if(poll.getGraphic() != null)val3=poll.getMultiple();
%>
        <ui:tablerow>
           <ui:tablecell>
             <ui:radiobutton beanId="graphic" value="1" selected="<%= val1%>"/>
              <ui:text value="create Pie 3D Chart"/>
           </ui:tablecell>
        </ui:tablerow>
        <ui:tablerow>
           <ui:tablecell>
             <ui:radiobutton beanId="graphic" value="2" selected="<%= val2%>"/>
              <ui:text value="create Bar 3D Chart"/>
           </ui:tablecell>
           <ui:tablecell width="30"/>
           <ui:tablecell>
             <ui:checkbox beanId="multiple" selected="<%= val3%>"/>
              <ui:text value="create multiple choice poll"/>
           </ui:tablecell>
        </ui:tablerow>
<%
    }catch(Exception e){}
%>
        <ui:tablerow>
            <ui:tablecell height="20"/>
        </ui:tablerow>
</ui:frame>
     <ui:frame>
        <ui:tablerow>
            <ui:actionsubmit action="createPoll" value="<%= actionsubmit%>"/>
            <ui:actionsubmit action="showPolls" value="Cancle"/>
        </ui:tablerow>
     </ui:frame>

     <ui:frame beanId="frame2"/>
  </ui:group>
</ui:form>

</td>
<td>
  <ui:form>
     <h3>make the poll hidden</h3>
     <ui:group>
      <ui:frame>

           <ui:tablerow>
              <ui:tablecell>
                  <ui:text value="Select a group:"/>
              </ui:tablecell>
           </ui:tablerow>
            <ui:tablerow>
                <ui:tablecell height="30"/>
            </ui:tablerow>
<%

           PortletLog log = SportletLog.getInstance(PollPortlet.class);
           boolean val1 = false;
           boolean val2 = false;
           boolean val3 = false;
           if(poll != null){
               val1 = poll.getHiddenForUser();
               val2 = poll.getHiddenForGuest();
               val3 = poll.getHiddenForSuper();
           }
         log.error("val1"+ String.valueOf(val1));
         log.error("val2"+ String.valueOf(val2));
         log.error("val3"+String.valueOf(val3));
%>
           <ui:tablerow>
                <ui:tablecell>
                   <ui:checkbox beanId="groups" value="USER" selected="<%= val1%>"/>
                   <ui:text value="USER"/>
                </ui:tablecell>
            </ui:tablerow>
           <ui:tablerow>
                <ui:tablecell>
                   <ui:checkbox beanId="groups" value="GUEST" selected="<%= val2%>"/>
                   <ui:text value="GUEST"/>
                </ui:tablecell>
            </ui:tablerow>
           <ui:tablerow>
                <ui:tablecell>
                   <ui:checkbox beanId="groups" value="SUPER" selected="<%= val3%>"/>
                   <ui:text value="SUPER"/>
                </ui:tablecell>
            </ui:tablerow>
            <ui:tablerow>
                <ui:tablecell height="20"/>
            </ui:tablerow>
          </ui:frame>
         <ui:frame>
             <ui:actionsubmit action="donePollHidden" value="Apply Changes"/>
         </ui:frame>
     </ui:group>
  </ui:form>
  <ui:form>
  <h3>Poll Preview</h3>
     <ui:group>
       <ui:frame>
            <ui:tablerow >
                <ui:tablecell width="30"/>
            </ui:tablerow>
            <ui:tablerow >
                <ui:tablecell>
                    <ui:text value="<%= poll.getTitle()%>"/>
                </ui:tablecell>
            </ui:tablerow>

             <ui:tablerow>
               <ui:tablecell height="30"/>
            </ui:tablerow>
            <ui:tablerow >
                <ui:tablecell width="15"/>
            </ui:tablerow>
            <ui:tablerow>
<%
            try{
%>
                <ui:tablecell>
                   <ui:text value="<%= ((Question) poll.getQuestions().get(0)).getQuestion()%>"/>
                </ui:tablecell>
<%
            }catch (Exception e){}
%>
            </ui:tablerow>
            <ui:tablerow>
               <ui:tablecell height="20"/>
            </ui:tablerow>
   <%
       try{
        Question q = (Question)poll.getQuestions().get(0);
        if(q != null){
         List answers = q.getChoices();
        if(answers != null){
        int i = 0;
        while(i != answers.size()){
          Choice  answer = (Choice)answers.get(i);
   %>
            <ui:tablerow>
                <ui:tablecell>
                   <ui:radiobutton beanId="choicebutton" value="<%= String.valueOf(i)%>"/>
                   <ui:text value="<%= answer.getChoice()%>"/>
                </ui:tablecell>
            </ui:tablerow>
   <%
        i++;
        }
        }
        }
       }catch(Exception e){}
   %>
            <ui:tablerow>
                <ui:tablecell height="30"/>
            </ui:tablerow>
             <ui:tablerow>
                <ui:tablecell width="60">
                    <ui:actionsubmit action="deleteChoice" value="Delete Choice" />
                </ui:tablecell>
            </ui:tablerow>
            <ui:tablerow>
                <ui:tablecell height="30"/>
            </ui:tablerow>
            <ui:tablerow>
                <ui:tablecell>
                   <ui:text value="<%= value%>"/>
                </ui:tablecell>
            </ui:tablerow>
         </ui:frame>
      </ui:group>
    </ui:form>

</td>
</tr>
</table>
