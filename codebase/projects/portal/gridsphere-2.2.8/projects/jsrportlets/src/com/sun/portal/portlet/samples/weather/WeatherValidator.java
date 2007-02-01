/*
 * Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * - Redistribution in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in
 *   the documentation and/or other materials provided with the
 *   distribution.
 *
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN
 * OR ITS LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR
 * FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR
 * PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF
 * LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE SOFTWARE,
 * EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that Software is not designed, licensed or intended
 * for use in the design, construction, operation or maintenance of
 * any nuclear facility.
 */

package com.sun.portal.portlet.samples.weather;

import javax.portlet.PortletPreferences;
import javax.portlet.PreferencesValidator;
import javax.portlet.ValidatorException;

public class WeatherValidator implements PreferencesValidator {
    public void validate(PortletPreferences prefs) throws ValidatorException {
        String zip = prefs.getValue("zip","");
        if (zip != null && zip.length() != 0 && !isValidZip(zip)) {
            throw new ValidatorException("Invalid zip code!!",null);
        }
        String unit = prefs.getValue("unit","");
        if (zip != null && zip.length() != 0 && !isValidUnit(unit)) {
            throw new ValidatorException("Invalid unit!!",null);
        }
    }
    
    protected boolean isValidZip(String zip) {
        boolean isValid = true;
        boolean stop = false;
        
        if ( zip.length() != 5 ) {
            isValid = false;
        }
        if (isValid) {
            for ( int i = 0; i < zip.length() && !stop; i++ ) {
                char c = zip.charAt(i);
                if ( !(c >= '0' && c <= '9') ) {
                    isValid = false;
                    stop = true;
                }
            }
        }
        
        return isValid;
    }
    
    protected boolean isValidUnit(String unit) {
        boolean isValid = true;
        
        if ( unit.length() != 1 || (unit.charAt(0) != 'F' &&
            unit.charAt(0) != 'C') ){
            isValid = false;
        }

        return isValid;
    }
    
}
