package com.ibm.wps.samples.jsr;

import javax.portlet.*;
import java.util.*;

public class PreferencesValidatorImpl implements PreferencesValidator
{

    public PreferencesValidatorImpl()
    {
    }

    public void validate(PortletPreferences preferences)
        throws ValidatorException
    {
      Collection failedKeys = new ArrayList();
      Enumeration names = preferences.getNames();

      String[] defValues = {"no values"};

      while (names.hasMoreElements())
      {
          String name = names.nextElement().toString();
          String[] values = preferences.getValues(name, defValues);

          for (int i=0; i<values.length;i++)
          {
              //validates that the preferences do not start or end with white space
              if (!values[i].equalsIgnoreCase(values[i].trim()))
              {
                  failedKeys.add(name);
                  i = values.length;
              }
          }
      }

      if (!failedKeys.isEmpty())
      {
          throw new ValidatorException("One or more preferences do not comply with the validation criteria",failedKeys);
      }
    }
}
