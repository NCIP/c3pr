/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import java.util.Date;
import java.util.List;

import com.semanticbits.querybuilder.AdvancedSearchCriteriaParameter;

import edu.duke.cabig.c3pr.utils.DateUtil;

public class AdvancedSearchCriteriaParameterUtil {
	
	public static final AdvancedSearchCriteriaParameter studyIdentifierValueAdvancedParameter = 
		createAdvancedSearchCriteriaParameter("Study", "edu.duke.cabig.c3pr.domain.Identifier", "value","=");
	
	public static final AdvancedSearchCriteriaParameter studyIdentifierTypeAdvancedParameter = 
			createAdvancedSearchCriteriaParameter("Study", "edu.duke.cabig.c3pr.domain.Identifier", "typeInternal","=");
	
	public static final AdvancedSearchCriteriaParameter studySubjectIdentifierValueAdvancedParameter = 
			createAdvancedSearchCriteriaParameter("StudySubject", "edu.duke.cabig.c3pr.domain.Identifier", "value","=");
		
	public static AdvancedSearchCriteriaParameter studySubjectIdentifierTypeAdvancedParameter = 
			createAdvancedSearchCriteriaParameter("StudySubject", "edu.duke.cabig.c3pr.domain.Identifier", "typeInternal","=");
	
	public static final AdvancedSearchCriteriaParameter subjectIdentifierValueAdvancedParameter = 
			createAdvancedSearchCriteriaParameter("Subject", "edu.duke.cabig.c3pr.domain.Identifier", "value","=");
		
	public static final AdvancedSearchCriteriaParameter subjectIdentifierTypeAdvancedParameter = 
			createAdvancedSearchCriteriaParameter("Subject", "edu.duke.cabig.c3pr.domain.Identifier", "typeInternal","=");
	
	public static final AdvancedSearchCriteriaParameter subjectLastNameAdvancedParameter = 
			createAdvancedSearchCriteriaParameter(
					"edu.duke.cabig.c3pr.domain.StudySubjectDemographics",
					"lastName","!=");
		
	public static final AdvancedSearchCriteriaParameter consentDocumentIdAdvancedParameter = 
			createAdvancedSearchCriteriaParameter("edu.duke.cabig.c3pr.domain.StudySubjectConsentVersion",
					"documentId","=");
	
	public static final AdvancedSearchCriteriaParameter registryStatusCodeAdvancedParameter = 
			createAdvancedSearchCriteriaParameter("edu.duke.cabig.c3pr.domain.RegistryStatus", "code","=");
	
	public static final AdvancedSearchCriteriaParameter nonMathcingRegistryStatusCodeAdvancedParameter = 
			createAdvancedSearchCriteriaParameter("edu.duke.cabig.c3pr.domain.RegistryStatus", "code","!=");
	
	public static final AdvancedSearchCriteriaParameter subjectRegistryStatusEffectiveDateAdvancedParameter = 
			createAdvancedSearchCriteriaParameter("edu.duke.cabig.c3pr.domain.StudySubjectRegistryStatus",
					"effectiveDate",">");
	
	
	public static AdvancedSearchCriteriaParameter createAdvancedSearchCriteriaParameter(
			String contextObjectName,String objectName, String attributeName, String predicate) {
		
		AdvancedSearchCriteriaParameter advParam = new AdvancedSearchCriteriaParameter();
		advParam.setContextObjectName(contextObjectName);
		advParam.setObjectName(objectName);
		advParam.setAttributeName(attributeName);
		advParam.setPredicate(predicate);
		return advParam;
	}
	
	public static AdvancedSearchCriteriaParameter createAdvancedSearchCriteriaParameter(
			String objectName, String attributeName, String predicate) {
		AdvancedSearchCriteriaParameter advParam = new AdvancedSearchCriteriaParameter();
		advParam.setObjectName(objectName);
		advParam.setAttributeName(attributeName);
		advParam.setPredicate(predicate);
		return advParam;
	}
	
	public static boolean compare(AdvancedSearchCriteriaParameter advSrchCriParam1, AdvancedSearchCriteriaParameter advSrchCriParam2){
		if(advSrchCriParam1.getContextObjectName() != null ? !advSrchCriParam1.getContextObjectName().equals(advSrchCriParam2.getContextObjectName()) :
			advSrchCriParam2.getContextObjectName() != null)
		return false;
		
		if(advSrchCriParam1.getObjectName() != null ? !advSrchCriParam1.getObjectName().equals(advSrchCriParam2.getObjectName()) :
			advSrchCriParam2.getObjectName() != null)
		return false;
		
		if(advSrchCriParam1.getAttributeName() != null ? !advSrchCriParam1.getAttributeName().equals(advSrchCriParam2.getAttributeName()) :
			advSrchCriParam2.getAttributeName() != null)
		return false;
		
		if(advSrchCriParam1.getPredicate() != null ? !advSrchCriParam1.getPredicate().equals(advSrchCriParam2.getPredicate()) :
			advSrchCriParam2.getPredicate() != null)
		return false;
		
		return true;
	}
	
public static String extractStudyIdentifierValue(List<AdvancedSearchCriteriaParameter> advParameters){
		
		AdvancedSearchCriteriaParameter param = AdvancedSearchCriteriaParameterUtil.studyIdentifierValueAdvancedParameter;
		for(AdvancedSearchCriteriaParameter advParam : advParameters){
			if(AdvancedSearchCriteriaParameterUtil.compare(param,advParam)){
				return  advParam.getValues().size() > 0 ? advParam.getValues().get(0):null;
			}
		}
		
		return null;
	}
	
	public static String extractStudySubjectIdentifierValue(List<AdvancedSearchCriteriaParameter> advParameters){
		
		AdvancedSearchCriteriaParameter param = AdvancedSearchCriteriaParameterUtil.studySubjectIdentifierValueAdvancedParameter;
		for(AdvancedSearchCriteriaParameter advParam : advParameters){
			if(AdvancedSearchCriteriaParameterUtil.compare(param,advParam)){
				return  advParam.getValues().get(0);
			}
		}
		
		return null;
	}
	
	public static String extractSubjectIdentifierValue(List<AdvancedSearchCriteriaParameter> advParameters){
		
		AdvancedSearchCriteriaParameter param = AdvancedSearchCriteriaParameterUtil.subjectIdentifierValueAdvancedParameter;
		for(AdvancedSearchCriteriaParameter advParam : advParameters){
			if(AdvancedSearchCriteriaParameterUtil.compare(param,advParam)){
				return  advParam.getValues().get(0);
			}
		}
		
		return null;
	}
	
	public static String extractRegistryStatusCode(List<AdvancedSearchCriteriaParameter> advParameters){
		
		for(AdvancedSearchCriteriaParameter advParam : advParameters){
			if(AdvancedSearchCriteriaParameterUtil.compare(registryStatusCodeAdvancedParameter,advParam)){
				return  advParam.getValues().get(0);
			}
		}
		
		return null;
	}
	
public static String extractNonMatchingRegistryStatusCode(List<AdvancedSearchCriteriaParameter> advParameters){
		
		for(AdvancedSearchCriteriaParameter advParam : advParameters){
			if(AdvancedSearchCriteriaParameterUtil.compare(nonMathcingRegistryStatusCodeAdvancedParameter,advParam)){
				return  advParam.getValues().get(0);
			}
		}
		
		return null;
	}
	
	public static String extractStudyIdentifierType(List<AdvancedSearchCriteriaParameter> advParameters){
		
		for(AdvancedSearchCriteriaParameter advParam : advParameters){
			if(AdvancedSearchCriteriaParameterUtil.compare(studyIdentifierTypeAdvancedParameter,advParam)){
				return  advParam.getValues().get(0);
			}
		}
		
		return null;
	}
	
	public static String extractStudySubjectIdentifierType(List<AdvancedSearchCriteriaParameter> advParameters){
		
		for(AdvancedSearchCriteriaParameter advParam : advParameters){
			if(AdvancedSearchCriteriaParameterUtil.compare(studySubjectIdentifierTypeAdvancedParameter,advParam)){
				return  advParam.getValues().get(0);
			}
		}
		
		return null;
	}
	
	public static String extractSubjectIdentifierType(List<AdvancedSearchCriteriaParameter> advParameters){
		
		for(AdvancedSearchCriteriaParameter advParam : advParameters){
			if(AdvancedSearchCriteriaParameterUtil.compare(subjectIdentifierTypeAdvancedParameter,advParam)){
				return  advParam.getValues().get(0);
			}
		}
		
		return null;
	}
	
	public static String extractSubjectLastName(List<AdvancedSearchCriteriaParameter> advParameters){
		
		for(AdvancedSearchCriteriaParameter advParam : advParameters){
			if(AdvancedSearchCriteriaParameterUtil.compare(subjectLastNameAdvancedParameter,advParam)){
				return  advParam.getValues().get(0);
			}
		}
		
		return null;
	}
	
	public static String extractSubjectDocumentId(List<AdvancedSearchCriteriaParameter> advParameters){
		
		for(AdvancedSearchCriteriaParameter advParam : advParameters){
			if(AdvancedSearchCriteriaParameterUtil.compare(consentDocumentIdAdvancedParameter,advParam)){
				return  advParam.getValues().get(0);
			}
		}
		
		return null;
	}
	
	public static Date extractSubjectRegistryEffectiveDate(List<AdvancedSearchCriteriaParameter> advParameters){
			for(AdvancedSearchCriteriaParameter advParam : advParameters){
				if(AdvancedSearchCriteriaParameterUtil.compare(subjectRegistryStatusEffectiveDateAdvancedParameter,advParam)){
					return  DateUtil.getUtilDateFromString(advParam.getValues().get(0),"mm/dd/yyyy");
				}
			}
			
			return null;
		}
	
	

}
