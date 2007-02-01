<%@ page import="org.radeox.engine.BaseRenderEngine,
                 org.radeox.api.engine.RenderEngine,
                 org.gridlab.gridsphere.extras.services.wiki.PortletWikiRenderEngine,
                 org.radeox.api.engine.context.RenderContext,
                 org.radeox.engine.context.BaseRenderContext"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<jsp:useBean id="snipaction" class="java.lang.String" scope="request"/>
<jsp:useBean id="snipraw" class="java.lang.String" scope="request"/>
<jsp:useBean id="snipname" class="java.lang.String" scope="request"/>
<jsp:useBean id="snipcontent" class="java.lang.String" scope="request"/>

<ui:form>


<%
    if (snipaction.equals("show")) {
%>
    <ui:group label="select action">
    [<ui:actionlink action="cancelCreate" value="start">
        <ui:actionparam name="snipname" value="start"/>
    </ui:actionlink> ]

    [<ui:actionlink action="editSnip" value="edit">
        <ui:actionparam name="snipname" value="<%=snipname%>"/>
    </ui:actionlink> ]


    [<ui:actionlink action="deleteSnip" value="delete">
        <ui:actionparam name="snipname" value="<%=snipname%>"/>
    </ui:actionlink> ]

    <ui:textfield beanId="search"/><ui:actionsubmit action="searchSnip" value="search"/>

    </ui:group>
    <ui:group label="<%= snipname %>">
    <ui:text value="<%= snipcontent %>"/>
    </ui:group>

<%
    }

     
        if (snipaction.equals("edit") || snipaction.equals("create")) {
%>
        <ui:group>
<%
        if (snipaction.equals("edit") ) {
%>
            <ui:textfield beanId="snipname" readonly="true" value="<%=snipname%>"/>

<%
        } else {
%>
            <ui:textfield beanId="snipname" value="<%=snipname%>"/>
<%
        }
%>
        <br/>


        <ui:textarea rows="20" cols="80" beanId="snipcontent" value="<%=snipraw%>"/>
        <p/>

<%
        if (snipaction.equals("edit")) {
%>
            <ui:actionsubmit action="updateSnip" value="save"/>
            <ui:actionsubmit action="cancelEdit" value="cancel"/>
<%
        }  else {
%>
            <ui:actionsubmit action="createSnip" value="create"/>
            <ui:actionsubmit action="cancelCreate" value="cancel"/>
<%
        }
%>

        </ui:group>
<%

    }

    if (snipaction.equals("search")) {

%>
        <ui:group label="search">
        <ui:textfield beanId="search"/><ui:actionsubmit action="searchSnip" value="search"/>

        <p/>
        <%= snipcontent %>
        </ui:group>
<%

    }
%>

</ui:form>


