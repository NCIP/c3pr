<%@ page import="java.util.Iterator" %>
<%@ page import="edu.duke.cabig.c3pr.domain.Participant" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<jsp:useBean id="participants" class="java.util.ArrayList" scope="request"/>

<link href="resources/styles.css" rel="stylesheet" type="text/css">
<link href="resources/search.css" rel="stylesheet" type="text/css">

<script>
    function navRollOver(obj, state) {
    document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
    }
</script>


<ui:form>

    <table width="100%" border="0" cellpadding="0" cellspacing="0"
           bgcolor="white">
        <tr>

            <!-- LEFT CONTENT STARTS HERE -->


            <td valign="top" class="additionals"><!-- LEFT FORM STARTS HERE -->
                <table width="100%" border="0" cellspacing="0" cellpadding="0"
                       id="additionalList">
                    <tr align="center" class="label">
                        <td>Last Name, First Name</td>
                        <td>Primary Identifier</td>
                        <td>Gender</td>
                        <td>Race</td>
                        <td>Birth Date</td>
                        <td></td>
                    </tr>
                    <% int i=0; %>
                    <%
                        Iterator iter = participants.iterator();
                        while(iter.hasNext()){
                            Participant participant = (Participant) iter.next();
                    %>


                            <td><%= participant.getLastName()%></td>
                            <td><%= participant.getId()%></td>
                            <td><%= participant.getPrimaryIdentifier()%>   </td>
                            <td><%= participant.getRaceCode()%></td>
                            <td><%= participant.getBirthDate()%></td>
                        </tr>

                    <% }

                    %>

                </table>
                <!-- LEFT FORM ENDS HERE --></td>
            <!-- LEFT CONTENT ENDS HERE -->
        </tr>
    </table>
</ui:form>
