<%@ include file="taglibs.jsp"%>
<html>
<head>
    <title>Configure application</title>
    <style type="text/css">
        div.row {
            padding: 5px 3px;
        }
        .row .value {
            margin-left: 22%;
        }
        .row .label {
            width: 20%;
            margin-left: 1em;
            text-align: right;
        }
        p.description {
            margin: 0.25em 0 0 1em;
        }
        div.submit {
            text-align: right;
        }
        .value input[type=text] {
            width: 80%;
        }

        form {
            margin-top: 1em;
        }

        .updated {
            border: #494 solid;
            border-width: 1px 0;
            background-color: #8C8;
            padding: 1em 2em;
            text-align: center;
            margin: 1em 30%;
            color: #fff;
            font-weight: bold;
            font-size: 1.1em;
        }
    </style>
</head>
<body>
    <chrome:box title="Configure C3PR" autopad="true">
    <tags:instructions code="configure" />
    <c:url value="/pages/admin/configure" var="action"/>
    <tags:errors path="*"/>
    <form:form action="${action}" cssClass="standard">
        <c:forEach items="${command.conf}" var="entry" varStatus="status">

            <c:if test="${entry.key != 'skinPath'}">

            <div class="row">
            <table>
            <tr><td width="40%">
                    <b><form:label path="conf[${entry.key}].value">${entry.value.property.name}</form:label>
                    </b> <tags:hoverHint keyProp="configure.${entry.key}" id="${beanPath}${status.index}" />
				</td>
 				<td>
                    <c:set var="beanPath">conf[${entry.key}].value</c:set>
                    <c:choose>
                        <c:when test="${entry.value.property.controlType == 'boolean'}">
                            <div>
                                <label><form:radiobutton path="${beanPath}" value="true"/> Yes</label>
                                <label><form:radiobutton path="${beanPath}" value="false"/> No</label>
                            </div>
                        </c:when>
                        <c:when test="${entry.value.property.controlType == 'text'}">
                            <form:input path="${beanPath}" size="70"/>
                        </c:when>
                        <c:otherwise>
                            <div>Unimplemented control type ${entry.value.controlType} for ${beanPath}</div>
                        </c:otherwise>
                    </c:choose>                    
                 </td>
              </tr>
              </table>
            </div>

            </c:if>

        </c:forEach>
        <div class="row submit">
            <tags:button type="submit" color="green" value="Save" icon="save"/>
        </div>

        <c:if test="${param.updated}">
            <p class="updated">Settings saved</p>
        </c:if>
    </form:form>
    </chrome:box>
</body>
</html>