/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigurationProperty {

    // Temporary lov values to hold the data pertaining to CADsr
    // TODO remove the lovMap once a CaDsr service is in place
    private Map map = new HashMap<String, List<Lov>>();

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }
}
