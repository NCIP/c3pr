<%@include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<page:applyDecorator name="standard">
    <html>
    <head>
        <style>
            div.error {
                font-size: 26px;
                text-align: left;
                color: #333366;
				margin-left:-2px;
				padding-bottom:16px;
            }

            div.errorMessage {
                font-size: 12px;
                text-align: left;
            }

            table.errortd {
                border: 0px gray dotted;
                font-size: 11px;
            }

            table.errortd td {
                border: 1px gray dotted;
                font-size: 11px;
				background-color:white;
            }
        </style>
        
        <title>Access Denied</title>

    </head>
    <body>
    <div style="overflow:auto; margin-bottom:10px;">
        <img src="<c:url value="/images/error.png" />" style="float:left; margin:10px;">
        <div style="float:left; padding-left:20px; padding-top:12px;">
            <div class="error">
                Access Denied!
            </div>
            <div class="errorMessage">
                 You do not have sufficient privileges to access this resource.
            </div>
            <br>
            <div class="errorMessage">
                <!--[if IE]>
                    &nbsp;&nbsp;
                <![endif]-->
                <c:set var="homeHref">
                    <c:url value='/'/>
                </c:set>
                <!--[if IE]>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <![endif]--><tags:button color="blue" onclick="javascript:location.href='${homeHref}'" value="Go Home" />
            </div>
        </div>
    </body>
    </html>
</page:applyDecorator>
<!-- END decorated-error.jsp -->