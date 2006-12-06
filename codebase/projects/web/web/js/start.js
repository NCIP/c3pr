//  Title       : start.js
//  Created By  : Jin Soo Choi - AC Tech
//  Created     : 05/07/03
//  Description :

function openLoginWindow()
{
  //var url = "login_frame.html";
  var url = "CPR-Start.html";
  var welcome = "Welcome";
/*  if (document.frmLogin.WindowState == 1)
  {
  	alert ("got here");
  	document.frmLogin.WindowState = 2;
  }
*/
  frameWindow = window.open(url, welcome, "toolbar=no, location=no, menubar=no, resizable=yes, scrollbars=yes, status=no, statusbar=no, directories=no, height=600, width=840");
  //frameWindow = window.open(url, welcome, "toolbar=no, location=no, menubar=no, resizable=yes, scrollbars=yes, status=yes, directories=no, type=fullWindow,fullscreen");
  document.frmLogin.submit();
}

/*
function old_LogoutClose()
{
  //document.frmStart.target = "_new";
  //return true;

  // document.frmStart.submit();
  // parent.window.close();


  document.frmStart.target="_new";
  window.open("", frmStart.target, 'scrollbars=0,toolbar=0,menubar=0,location=0,status=0');
  document.frmStart.action = "logout_fwd.jsp";
  document.frmStart.submit();


  //var strLocation = "logout.jsp";
  //if (!winReports)
  //{
  //  var winReports = window.open("", frmStart.target, 'scrollbars=0,toolbar=0,menubar=0,location=0,status=0');
  //}
  //else
  //{
  //  winReports.location.href = strLocation;
  //  winReports.focus();
  //}

}
*/