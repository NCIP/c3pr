<%@ page import="java.util.List,
                 org.gridlab.gridsphere.extras.services.poll.Question,
                 org.gridlab.gridsphere.extras.services.poll.Choice"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<jsp:useBean id="poll" class="org.gridlab.gridsphere.extras.services.poll.Poll" scope="request"/>
<jsp:useBean id="submit" class="java.lang.String" scope="request"/>


<table>
<tr>
<td>
  <ui:form>
    <h3>Display existing polls</h3>
   <ui:group>
     <ui:frame>
      <ui:tablerow>
         <ui:tablecell>
            <ui:text value="Select a poll:"/>
         </ui:tablecell>
         </ui:tablerow>
       <ui:tablerow>
         <ui:tablecell>
            <ui:listbox beanId="polls"/>
         </ui:tablecell>
      </ui:tablerow>
      <ui:tablerow>
          <ui:tablecell>
             <ui:actionsubmit action="gotoPoll" value="Vote"/>
             <ui:actionsubmit action="showPoll" value="Show"/>
          </ui:tablecell>
      </ui:tablerow>
       <ui:tablerow>
           <ui:tablecell height="30"/>
        </ui:tablerow>
        <ui:tablerow>
<%
    try{
    Question q = (Question)poll.getQuestions().get(0);
%>
           <ui:tablecell>
               <ui:text value="<%= q.getQuestion()%>"/>
           </ui:tablecell>
 <%
    }catch(Exception e){}
 %>
        </ui:tablerow>
        <ui:tablerow>
           <ui:tablecell height="30"/>
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
        if(poll.getMultiple()) {
   %>
            <ui:tablerow>
                <ui:tablecell>
                   <ui:checkbox beanId="multiple"  value="<%= String.valueOf(i)%>"/>
                   <ui:text value="<%= answer.getChoice()%>"/>
                </ui:tablecell>
            </ui:tablerow>
 <%
        i++;
        }
        else{
 %>
            <ui:tablerow>
                <ui:tablecell>
                   <ui:radiobutton beanId="single" value="<%= String.valueOf(i)%>"/>
                   <ui:text value="<%= answer.getChoice()%>"/>
                </ui:tablecell>
            </ui:tablerow>
   <%
        i++;
        }

        }
        }
        }
       }catch(Exception e){}
   %>
        <ui:tablerow>
           <ui:tablecell height="30"/>
        </ui:tablerow>
    </ui:frame>
<%
      if(!submit.equals(new String(""))){
%>
    <ui:frame>
            <ui:tablerow>
                <ui:tablecell width="20"/>
                <ui:tablecell>
                   <ui:actionsubmit action="submitChoice" value="Submit"/>
                </ui:tablecell>
            </ui:tablerow>
    </ui:frame>
<% }%>

    <ui:frame beanId="frame"/>
  </ui:group>
   </ui:form>
</td>
<td>
   <ui:form>
      <ui:frame>
         <ui:tablerow>
            <ui:tablecell width="60"/>
         </ui:tablerow>
      </ui:frame>
   </ui:form>
</td>
<td>
  <ui:form>
        <ui:frame>
          <ui:image beanId="poll"/>
        </ui:frame>
  </ui:form>

</td>
</tr>
</table>