<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<html>
    <head>
        <title>Reset Password</title>
        <style type="text/css">
            body {
                background: #333333 url(/c3pr/templates/mocha/images/lines_bg.png) scroll;
                background-repeat: repeat;
                color: white;
                font-family: Arial, Verdana, Helvetica, sans-serif;
                font-size: 10pt;
                margin: 0;
                padding: 0;
                text-align: center;
            } #header {
                display: none;
            } .box {
                width: 40em;
                margin: 0 auto;
            } .submit {
                float: right;
                margin-top: 1em;
            }
						#login-logo {
            	margin-top:15px;
            } 
			.row {
				font-size:16px;
				color:white;
			}
			.row input {
				font-size:30px;
				margin-top:10px;
			}
			#form {
                position: absolute;
                width: 425px;
                left: 510px;
                top:45px;
				font-size:24px;
				color:#E4C48F;
            }
			div.row div.label {
				margin-left:-116px;
				font-size:21px;
			}
        </style>
    </head>
    <body>
    	</div>
		</div>
		<a href="/c3pr/pages/login"><img id="login-logo" src="/c3pr/templates/mocha/images/login-logo.png" alt="C3PR - Cancer Clinical Central Participant Registry" /></a>
            <c:url value="/public/user/resetPassword" var="action"/>
            <div id="form">
			<form:form action="${action}">
				Reset Password
                <p class="errors">
                    ${reset_pwd_error}
                </p>
                <div class="row">
                    <div class="label">
						Username &nbsp;&nbsp;
                    </div>
                    <td class="label">
                        <form:input cssClass="required validate-notEmpty" path="userName"/> <input type="submit" value="Reset"/>
                    </td>
                </div>
            </form:form>
			</div>
    </body>
</html>