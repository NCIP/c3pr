<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <script type="text/javascript">
        function fireAction(action, selectedStratification, selectedAnswer) {
            document.getElementById('command')._target.name = '_noname';
            document.form._action.value = action;
            document.form._selectedStratification.value = selectedStratification;
            document.form._selectedAnswer.value = selectedAnswer;
            document.form.submit();
        }

    </script>
</head>

<body>
<tags:tabForm tab="${tab}" flow="${flow}" formName="form">
<jsp:attribute name="singleFields">
<div>
    <input type="hidden" name="_action" value="">
    <input type="hidden" name="_selectedStratification" value="">
    <input type="hidden" name="_selectedAnswer" value="">
</div>

            <p id="instructions">
                Add Stratatification factor:
                <a href="javascript:fireAction('addStratificationQuestion','0','0');"><img
                        src="<tags:imageUrl name="checkyes.gif"/>" border="0" alt="Add"></a>&nbsp;&nbsp
            </p>
            <br>

            <table border="0" cellspacing="0" cellpadding="0" id="mytable">
                <tr>
                    <th scope="col" align="left"></th>
                    <th scope="col" align="left"><b>Stratification Factor <span class="red">*</span></b></th>
                    <th scope="col" align="left"><b>add&nbsp;answers<span class="red">*</span></b></th>
                    <th scope="col" align="left"></th>
                </tr>
                <c:forEach items="${command.stratificationCriteria}" var="strat" varStatus="status">
                    <tr>
                        <td class="alt"><a
                                href="javascript:fireAction('removeStratificationQuestion','${status.index}','0');">
                            <img src="<tags:imageUrl name="checkno.gif"/>" border="0" alt="Add"></a>
                        </td>
                        <td class="alt">
                            <form:hidden path="stratificationCriteria[${status.index}].questionNumber"/>
                            <form:textarea path="stratificationCriteria[${status.index}].questionText" rows="1"
                                           cols="60" cssClass="validate-notEmpty"/>
                        </td>
                        <td class="alt"><a href="javascript:fireAction('addPermissibleAnswer',${status.index},'0');">
                            <img src="<tags:imageUrl name="checkyes.gif"/>" border="0"></a></td>
                        <td>
                            <table border="0" cellspacing="0" cellpadding="0" id="table1" width="50%">
                                <tr>
                                    <td class="alt">Answer</td>
                                    <td class="alt"></td>
                                </tr>
                                <c:forEach var="answer" varStatus="statusAns"
                                           items="${command.stratificationCriteria[status.index].permissibleAnswers}">
                                    <tr>
                                        <td class="alt">
                                            <form:input
                                                    path="stratificationCriteria[${status.index}].permissibleAnswers[${statusAns.index}].permissibleAnswer"
                                                    size="30" cssClass="validate-notEmpty"/></td>
                                        <td class="alt"><a
                                                href="javascript:fireAction('removePermissibleAnswer',${status.index},${statusAns.index});">
                                            <img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </td>
                    </tr>
                </c:forEach>
            </table>


     </jsp:attribute>
</tags:tabForm>
</body>
</html>