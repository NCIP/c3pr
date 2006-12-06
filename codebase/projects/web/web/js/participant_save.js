//  Title       : participant_search.js
//  Created By  : Jin Soo Choi - AC Technologies, Inc.
//  Created     : 06/06/2003
//  Description :

// Used to check for ' -- messes up db insert/update/delete
function checkEntry(entry)
{
  var strEntry = new String();
  var numEntryLength = new Number();
  var blnStatus = new Boolean();

  strEntry = entry;
  numEntryLength = strEntry.length;
  blnStatus = true;

  for (var i = 0; i < numEntryLength; i++)
  {
    if (strEntry.substr(i, 1) == "'")
    {
      alert("Invalid character entered: '");
      blnStatus = false;
      break;
    }
  }
  return(blnStatus);
}

function checkInput()
{
  document.forms[0].sumit();
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

function afterToday(_inputDate)
{
  var dte_inputDate = new Date(_inputDate);
  var dte_today = new Date();
  var bln_Status = false;

  if (dte_inputDate >= dte_today)
  {
    bln_Status = true;
  }
  else
  {  alert("The date cannot be earlier than tomorrow!");  }

  return (bln_Status);
}

function checkAlpha(entry)
{
  var strEntry = new String();
  var numEntryLength = new Number();
  var blnStatus = new Boolean();

  strEntry = entry;
  numEntryLength = strEntry.length;
  blnStatus = true;

  for (var i = 0; i < numEntryLength; i++)
  {
    if (!isNaN(strEntry.substr(i, 1)))
    {
      alert("15th character of Content TCN must not be numeric.");
      blnStatus = false;
      break;
    }
  }
  return(blnStatus);
}

function checkForLetter(Letter)
{
  var blnStatus = new Boolean();
  blnStatus = false;

  if ((Letter == "A") || (Letter == "B") || (Letter == "C") || (Letter == "D") ||
      (Letter == "E") || (Letter == "F") || (Letter == "G") || (Letter == "H") ||
      (Letter == "J") || (Letter == "K") || (Letter == "L") || (Letter == "M") ||
      (Letter == "N") || (Letter == "P") || (Letter == "Q") || (Letter == "R") ||
      (Letter == "S") || (Letter == "T") || (Letter == "U") || (Letter == "V") ||
      (Letter == "W") || (Letter == "X") || (Letter == "Y") || (Letter == "Z"))
  {
    blnStatus = true;
  }
  else if ((Letter == "I") || (Letter == "O"))
  {
    alert("Invalid letter entered for 15th character: 'I' and 'O' not allowed!");
  }
  else
  {
    alert("Invalid letter entered for 15th character.  Please enter a letter (excluding 'I' and 'O').");
  }
  return blnStatus;
}

function checkForValid(entry)
{
  var strEntry = new String();
  var Letter = new String();
  var numEntryLength = new Number();
  var blnStatus = new Boolean();

  strEntry = entry;
  numEntryLength = strEntry.length;
  blnStatus = true;

  for (var i = 0; i < numEntryLength; i++)
  {

    Letter = strEntry.substr(i, 1);

    if ((Letter == "A") || (Letter == "B") || (Letter == "C") || (Letter == "D") ||
        (Letter == "E") || (Letter == "F") || (Letter == "G") || (Letter == "H") ||
        (Letter == "J") || (Letter == "K") || (Letter == "L") || (Letter == "M") ||
        (Letter == "N") || (Letter == "P") || (Letter == "Q") || (Letter == "R") ||
        (Letter == "S") || (Letter == "T") || (Letter == "U") || (Letter == "V") ||
        (Letter == "W") || (Letter == "X") || (Letter == "Y") || (Letter == "Z") ||
        (Letter == "0") || (Letter == "1") || (Letter == "2") || (Letter == "3") ||
        (Letter == "4") || (Letter == "5") || (Letter == "6") || (Letter == "7") ||
        (Letter == "8") || (Letter == "9") || (Letter == "I") || (Letter == "O"))
    {
      blnStatus = true;
    }
    else
    {
      alert("Invalid character entered in requisition number: Characters other than A-Z, 0-9 not allowed!");
      return false;
    }
  }
  return blnStatus;
}
