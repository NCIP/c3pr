//  Title       : login.js
//  Created By  : Jin Soo Choi - AC Tech
//  Created     : 05/07/03
//  Description : Checks if user has inputted a user name and password before
//                submitting to the server.

function checkLogin()
{
  if (document.frmLogin.txtUserName.value != "")
  {
    if (document.frmLogin.pwdPassword.value != "")
	{
          window.location.replace('participant_select.jsp');
	  document.frmLogin.submit();
	}
	else
	{
	  alert("You must supply a password to login!");
	}
  }
  else
  {
    alert("You must supply a user name to login!");
  }
}
