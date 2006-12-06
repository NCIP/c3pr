<script language="javascript">
<!--
  setTimeouts();
  sessionRefreshed = false;

  function setTimeouts() {
      window.setTimeout('doTimeoutAlert()',   2400000);
      window.setTimeout('doTimeoutMessage()',2700000);
  } //end setTimeouts()

  function doTimeoutAlert() {
      var timeoutOptions = "toolbar=0" + ",location=0" + ",directories=0" +          ",status=0" + ",menubar=0" + ",scrollbars=0" +",width=320" + ",height=300";

      var timeoutWindow = window.open("", "timeoutwindow",              timeoutOptions, true);

      var timeoutText = "<html><body>";
      timeoutText += "Your session will be timed out in 5 minutes.  What would you like to do?<br> <ul><li>Keep Session - Continue editing your page.  You will be warned again if your session is in danger</li><li>Abandon Session - Your session will expire normally and you may loose any data you are currently editing.  You may fix your session later by logging in again.</li></ul>";
      timeoutText += "<br><form action=\"restoreSession.jsp\"><input type=\"submit\" value=\"Keep Session\" onClick=\"window.opener.sessionRefreshed=true;window.opener.setTimeouts()\"><input type=\"button\" value=\"Abandon Session\" onClick=\"opener.location = 'logout.do';window.close();\"></form>";
      timeoutText += "</body>";
      timeoutText += "<script>window.setTimeout('window.close()',120000);<\/script>";
      timeoutText += "</html>";

      timeoutWindow.document.write(timeoutText);
      timeoutWindow.document.close();
  } //end doTimeoutAlert()

  function doTimeoutMessage() {
      if (sessionRefreshed) {
          sessionRefreshed = false;
      }
      else {
          alert("Your session has timed out.  You must re-login to continue. NOTE: Some of your data may have been lost.");
      } //end if else
  } //end doTimeoutMessage()
//-->
</script>
