<SCRIPT LANGUAGE="JavaScript">

// non-digit characters which are allowed in phone numbers
var phoneNumberDelimiters = "()- ";


// characters which are allowed in US phone numbers
var validUSPhoneChars = digits + phoneNumberDelimiters;

var digits = "0123456789";

// U.S. phone numbers have 10 digits.
// They are formatted as 123 456 7890 or (123) 456-7890.
var digitsInUSPhoneNumber = 10;

var iUSPhone = "This field must be a 10 digit U.S. phone number (like 415-555-1212). Please reenter it now."


function checkUSPhone (field)
{    var normalizedPhone = stripCharsInBag(field.value, phoneNumberDelimiters)
       if (!isUSPhoneNumber(normalizedPhone)) 
          return warnInvalid (iUSPhone);
       else 
       {  // if you don't want to reformat as (123) 456-789, comment next line out
          //theField.value = reformatUSPhone(normalizedPhone)
          return true;
       }
    
}


// Notify user that contents of field theField are invalid.
// String s describes expected contents of theField.value.
// Put select theField, pu focus in it, and return false.

function warnInvalid (s)
{   
    alert(s)
    return false
}


function stripCharsInBag (s, bag)

{   var i;
    var returnString = "";

    // Search through string's characters one by one.
    // If character is not in bag, append to returnString.

    for (i = 0; i < s.length; i++)
    {   
        // Check that current character isn't whitespace.
        var c = s.charAt(i);
        if (bag.indexOf(c) == -1) returnString += c;
    }

    return returnString;
}

function isUSPhoneNumber (s)
{   
    return (isInteger(s) && s.length == digitsInUSPhoneNumber)
}

function isInteger (s)

{   var i;

    
    for (i = 0; i < s.length; i++)
    {   
        // Check that current character is number.
        var c = s.charAt(i);

        if (!isDigit(c)) return false;
    }

    // All characters are numbers.
    return true;
}

// Returns true if character c is a digit 
// (0 .. 9).

function isDigit (c)
{   return ((c >= "0") && (c <= "9"))
}



</SCRIPT>