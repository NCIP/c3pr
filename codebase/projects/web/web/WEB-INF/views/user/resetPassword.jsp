<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See https://github.com/NCIP/c3pr/LICENSE.txt for details.
--%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<html>
    <head>
        <title>Reset Password</title>
        <script type="text/javascript">
        </script>
        <style type="text/css">
            body {
                background: #333333 url(../templates/mocha/images/lines_bg.png) scroll;
                background-repeat: repeat;
                color: white;
                font-family: Arial, Verdana, Helvetica, sans-serif;
                font-size: 10pt;
                margin: 0;
                padding: 0;
                text-align: center;
            } 
			.box {
                width: 35em;
                margin: 0 auto;
            } 
			.submit {
                float: right;
                margin-top: 1em;
            }  
			#main {
                top: 170px;
            } 
			#header {
                display: none;
            } 
			#login-logo {
            	margin-top:15px;
            } 
			#login-instName {
            	position:absolute;
                color: white;
                font-size: 17px;
                left: 70px;
                top: 470px;
                width: 850px;
            }
            div.row {
                font-size: 16px;
                color: white;
            }
            div.row input {
                font-size: 16px;
            } #login-form {
                position: absolute;
                width: 400px;
                left: 70px;
                top: 290px;
            } .login-header {
                font-size: 28px;
                color: #E4C48F;
                margin-bottom: 10px;
            } .forgot a{
                float: left;
                color: #E4C48F;
                font-size: 12px;
				margin-top:1em;
            }
            
            div.row div.value {
                margin-left: 6em;
            }
            
            div.row div.label {
                margin-left: 0;
                width: 6em;
            }
			.submit {
				position:absolute;
				top:-280px;
				left:500px;
				text-align:center;
				font-size:30px;
				color:white;
			}
			.submit input {
				margin-bottom:30px;
				outline:none;
			}
			p.errors {
				color:#FF9933;
			}
			#build-name {
            color: #2e3257;
            padding: 0px;
            margin-left: 17px;
            bottom: -175px;
        	}
        </style>
<SCRIPT language="JavaScript">
    upImage = new Image();
    upImage.src = "../templates/mocha/images/power-btn-up.png";
	var up = upImage.src;
    downImage = new Image();
    downImage.src = "../templates/mocha/images/power-btn-down.png"
	var down = downImage.src;
    hoverImage = new Image();
    hoverImage.src = "../templates/mocha/images/power-btn-hover.png";
	var hover = hoverImage.src;
    var loginimg = document.getElementById("power_btn");
    
    function changeImage()
    {
        document.getElementById("power_btn").src = hover;
        return true;
    }
    function changeImageBack()
    {
        document.getElementById("power_btn").src = up;
        return true;
    }
    function handleMDown()
    {
        document.getElementById("power_btn").src = down;
        return true;
    }
    function handleMUp()
    {
        changeImage();
        return true;
    }
</SCRIPT>
    </head>
    <body>
    </div>
</div>
<img id="login-logo" src="../../templates/mocha/images/login-logo.png" alt="C3PR - Cancer Clinical Central Participant Registry" />
<%--<img id="login-logo" src="../images/C3PO-login-logo.png" alt="C3P0 - Human-Cyborg Relations" />--%>
<div id="login-form">
    <form:form action="${action}">
        <div class="login-header">
            Please enter your username
        </div>
        You will be sent an email to reset your password.<br><br>       
        <c:if test="${not empty param.login_error}">
            <p class="errors">
                <img src="/c3pr/images/error-yellow.png" alt="" /> Incorrect username and/or password.  Please try again.
            </p>
        </c:if>
        <div class="row">
            <div class="label"><tags:requiredIndicator />Username</div>
            <div class="value">
                <form:input cssClass="required validate-notEmpty" path="userName"/>
            </div>
        </div>
        <div align="center" style="margin-top: 1em;">
            <tags:button type="submit" color="green" value="Reset Password"/>
		</div>
    </form:form>
</div>
<div id="login-instName">
    <img src="${siteName}" height="35px">&nbsp;<c:out value="${instName}" />
</div>
</body>
</html>
