/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.dao.query;

import org.apache.commons.lang.StringUtils;

/**
 * @author Saurabh Agrawal
 */
public class DataAuditEventQuery extends gov.nih.nci.cabig.ctms.audit.dao.query.DataAuditEventQuery {

   private static String ATTRIBUTE_NAME = "attributeName";
   private static String PREVIOS_VALUE = "previousValue";
   private static String CURRENT_VALUE = "currentValue";

   public void filterByValue(String attributeName, String previousValue, String currentValue) {

	   leftJoinFetch("e.values value");
	   
       if (!StringUtils.isBlank(attributeName)) {
           andWhere("value.attributeName=:" + ATTRIBUTE_NAME);
           setParameter(ATTRIBUTE_NAME, attributeName);

       }
       if (!StringUtils.isBlank(previousValue)) {
           andWhere("value.previousValue=:" + PREVIOS_VALUE);
           setParameter(PREVIOS_VALUE, previousValue);

       }
       if (!StringUtils.isBlank(currentValue)) {
           andWhere("value.currentValue=:" + CURRENT_VALUE);
           setParameter(CURRENT_VALUE, currentValue);

       }
   }
}
