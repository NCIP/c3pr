//  Title       : participant_search.js
//  Created By  : Jin Soo Choi - AC Technologies, Inc.
//  Created     : 06/06/2003
//  Description :

function checkDate()
{
/*
  var strStartDate = new String(document.frmOrderSearch.txtStrDate.value);
  var blnStatus = false;

  if ((strStartDate == "MM/DD/YYYY") || (strStartDate.length == 0))
  {
    document.frmOrderSearch.txtStrDate.value = "";
    document.frmOrderSearch.txtEndDate.value = "";
    blnStatus = true;
  }
  else
  {
    if (strStartDate.length > 0)
    {
      if (validDate(document.frmOrderSearch.txtStrDate.value))
      {
        if (validDate(document.frmOrderSearch.txtEndDate.value))
        {
          blnStatus = true;
        }
        else
        {
          document.frmOrderSearch.txtEndDate.focus();
        }
      }
      else
      {
        document.frmOrderSearch.txtStrDate.focus();
      }
    }
    else
    {
      blnStatus = true;
    }
  }

  if (blnStatus)
  {
    if (checkConsolidator())
    {
      if(checkShipper())
      {
        window.location.replace('participant_select.jsp');
        document.frmParticipantSearch.submit();
      }
    }
  }
*/
        window.location.replace('participant_select.jsp');
        document.frmParticipantSearch.submit();
}

function checkConsolidator()
{
  var blnCheck = true;

  /*if (document.frmOrderSearch.hidUserType.value != "SU")
  {
    if (document.frmOrderSearch.optSupplier.value == "-1")
    {
      alert("Please select a Consolidator/Supplier to continue.");
      blnCheck = false;
    }
  }*/
  return blnCheck;
}


function checkShipper()
{
  var blnCheck = true;


  if (document.frmOrderSearch.optSubShipper.value == "-1")
  {
      alert("Please select a Shipper to continue.");
      blnCheck = false;
  }
  return blnCheck;
}

function sendToInput()
{
  document.frmParticipantSearch.action = "participant_insert.jsp";
  document.frmParticipantSearch.submit();
}

function validDate(_inputDate)
{
  var str_Date = _inputDate;
  var num_Month = new Number();
  var num_Day = new Number();
  var num_Year = new Number();
  var char_Slash = "/";
  var bln_Status = false;

  if (str_Date.length == 10)
  {
    num_Month = str_Date.substr(0, 2);
    num_Day = str_Date.substr(3, 2);
    num_Year = str_Date.substr(6, 4);

    if ((str_Date.charAt(2) == char_Slash) && (str_Date.charAt(5) == char_Slash))
    {
      if ((num_Month > 0) && (num_Month < 13))
      {
        if ((num_Day > 0) && ((((num_Month == 1) || (num_Month == 3) || (num_Month == 5) || (num_Month == 7) || (num_Month == 8) || (num_Month == 10) || (num_Month == 12)) && (num_Day < 32)) || (((num_Month == 2) || (num_Month == 4) || (num_Month == 6) || (num_Month == 9) || (num_Month == 11)) && (num_Day < 31))))
        {
          if ((num_Year > 1899) && (num_Year < 10000))
          {
            if (num_Month == 2)
            {
              if (str_Date.substr(8,2) == "00")
              {
                if ((num_Year % 400) == 0)
                {
                  if (num_Day > 29)
                  {
                    alert("The day you entered is invalid.  Please enter a valid day");
                  }
                  else
                  {
                    bln_Status = true;
                  }
                }
                else
                {
                  if (num_Day > 28)
                  {
                    alert("The day you entered is invalid.  Please enter a valid day");
                  }
                  else
                  {
                    bln_Status = true;
                  }
                }
              }
              else
              {
                if ((num_Year % 4) == 0)
                {
                  if (num_Day > 29)
                  {
                    alert("The day you entered is invalid.  Please enter a valid day");
                  }
                  else
                  {
                    bln_Status = true;
                  }
                }
                else
                {
                  if (num_Day > 28)
                  {
                    alert("The day you entered is invalid.  Please enter a valid day");
                  }
                  else
                  {
                    bln_Status = true;
                  }
                }
              }
            }
            else
            {
              bln_Status = true;
            }
          }
          else
          {
            alert("The year you entered is invalid.  Please enter a year between 1900 and 9999");
          }
        }
        else
        {
          alert("The day you entered is invalid.  Please enter a valid day");
        }
      }
      else
      {
        alert("The month you entered is invalid.  Please enter a month between 01 and 12");
      }
    }
    else
    {
      alert("The format is invalid.  Please enter in a date with the format MM/DD/YYYY");
    }
  }
  else
  {
    alert("The format is invalid.  Please enter in a date with the format MM/DD/YYYY");
  }

  return (bln_Status);
}
