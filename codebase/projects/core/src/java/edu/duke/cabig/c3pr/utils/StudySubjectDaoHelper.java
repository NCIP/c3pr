/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.HibernateTemplate;

import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.Consent;
import edu.duke.cabig.c3pr.domain.ConsentQuestion;
import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.PermissibleStudySubjectRegistryStatus;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySiteStudyVersion;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.StudySubjectConsentVersion;
import edu.duke.cabig.c3pr.domain.StudySubjectDemographics;
import edu.duke.cabig.c3pr.domain.StudySubjectRegistryStatus;
import edu.duke.cabig.c3pr.domain.StudySubjectStudyVersion;
import edu.duke.cabig.c3pr.domain.SubjectConsentQuestionAnswer;

public class StudySubjectDaoHelper {
	
	private static final String ROW_NUM_SEARCH_PREFIX_STRING = "select * from ( select a.*, rownum rnum from (";
	private static final String ROW_NUM_SEARCH_SUFFIX_STRING = ") a where rownum <= (:MAX_ROWS) ) where rnum > (:MIN_ROWS)";
	/*public static final SortParameter studyShortTitle = new SortParameter(null,"edu.duke.cabig.c3pr.domain.StudyVersion","shortTitleText",null);
	public static final SortParameter studyIdentifierValue = new SortParameter("Study","edu.duke.cabig.c3pr.domain.Identifier","value",null);
	public static final SortParameter studyIdentifierType = new SortParameter("Study","edu.duke.cabig.c3pr.domain.Identifier","type",null);
	public static final SortParameter studySubjectIdentifierValue = new SortParameter("StudySubject","edu.duke.cabig.c3pr.domain.Identifier","value",null);
	public static final SortParameter studySubjectIdentifierType = new SortParameter("StudySubject","edu.duke.cabig.c3pr.domain.Identifier","type",null);
	public static final SortParameter subjectIdentifierValue = new SortParameter("Subject","edu.duke.cabig.c3pr.domain.Identifier","value",null);
	public static final SortParameter subjectIdentifierType = new SortParameter("Subject","edu.duke.cabig.c3pr.domain.Identifier","type",null);
	public static final SortParameter subjectLastName = new SortParameter(null,"edu.duke.cabig.c3pr.domain.StudySubjectDemographics","lastName",null);
	
	private static Map<Integer,SortParameter> sortParametersMap = new HashMap<Integer,SortParameter>();
	
	static{
		sortParametersMap.put(studyShortTitle.hashCode(),studyShortTitle);
		sortParametersMap.put(studyIdentifierValue.hashCode(),studyIdentifierValue);
		sortParametersMap.put(studyIdentifierType.hashCode(),studyIdentifierType);
		sortParametersMap.put(studySubjectIdentifierValue.hashCode(),studySubjectIdentifierValue);
		sortParametersMap.put(studySubjectIdentifierType.hashCode(),studySubjectIdentifierType);
		sortParametersMap.put(subjectIdentifierValue.hashCode(),subjectIdentifierValue);
		sortParametersMap.put(subjectIdentifierType.hashCode(),subjectIdentifierType);
		sortParametersMap.put(subjectLastName.hashCode(),subjectLastName);
	}
	
	public static boolean compareSortParameters(SortParameter sortParameter1, SortParameter sortParameter2 ){
		if(sortParameter1.getContextObjectName() != null ? sortParameter1.getContextObjectName().equals(sortParameter2.getContextObjectName())
				:sortParameter2.getContextObjectName() != null){
			return false;
		}
		
		if(sortParameter1.getObjectName() != null ? sortParameter1.getObjectName().equals(sortParameter2.getObjectName())
				:sortParameter2.getObjectName() != null){
			return false;
		}
		
		if(sortParameter1.getAttributeName() != null ? sortParameter1.getAttributeName().equals(sortParameter2.getAttributeName())
				:sortParameter2.getAttributeName() != null){
			return false;
		}
		
		return true;
		
	}*/
	

	public static Map<Integer,StudySubject> constructStudySubjectsHashMap(List<StudySubject> studySubjects){
		Map<Integer,StudySubject>  studySubjectsMap= new HashMap<Integer,StudySubject>();
		for(StudySubject studySubject : studySubjects) {
			studySubjectsMap.put(studySubject.getId(), studySubject);
		}
		return studySubjectsMap;
	}
	
	public static Query buildPaginationQuery(String baseQuery, final Integer min_rows, final Integer max_rows, HibernateTemplate hibernateTemplate, boolean isOracle){
		Query query = null;
		StringBuilder queryString = new StringBuilder();
		if(min_rows != null && max_rows != null){
			if(isOracle) {
				queryString.append(ROW_NUM_SEARCH_PREFIX_STRING);
				queryString.append(baseQuery);
				queryString.append(ROW_NUM_SEARCH_SUFFIX_STRING);
				query = hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(queryString.toString()).
						setParameter("MIN_ROWS",min_rows).setParameter("MAX_ROWS", max_rows);
			} else{
				// it is postgres
				queryString.append(baseQuery);
				queryString.append(" limit " + (max_rows - min_rows) + " offset " + min_rows);
				query = hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(queryString.toString());
			}
		} else 	{
			query = hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(baseQuery);
		}
		
		return query;
		
	}
	public static Query handleOracle1000LimitForInStatement(List<Integer> ids, String baseQueryString, HibernateTemplate hibernateTemplate){
		
		StringBuilder sb = new StringBuilder();
		sb.append(baseQueryString);
		Query query = null;
		
		if(ids.size() > 1000){
			Map<String,List<Integer>> parametersMap = new HashMap<String,List<Integer>>();
			List<Integer> idsSubSet1 = ids.subList(0, 900);
			ids = ids.subList(900,ids.size());
			sb.append("(:ids)");
			parametersMap.put("ids",idsSubSet1);
			int j =1;
			while(ids.size() > 1000){
				j++;
				List<Integer> idsSubSet = ids.subList(0, 900);
				ids = ids.subList(900,ids.size());
				sb.append(" or ssrs.id in (:" + "ids" + j + ")");
				parametersMap.put("ids" + j,idsSubSet);
			} 
			// to handle the last set of integers whose count is not greater than 1000
			if(ids.size() <= 1000){
				j++;
				sb.append(" or ssrs.id in (:" + "ids" + j + ")");
				parametersMap.put("ids" + j,ids);
			}
			query = hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(sb.toString());
			List<String> idKeys = new ArrayList<String>();
			idKeys.addAll(parametersMap.keySet());
			for(String key: idKeys){
				query.setParameterList(key, parametersMap.get(key));
			}
		} else {
			sb.append("(:ids)");
			query = hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(sb.toString());
			query.setParameterList("ids", ids);
		}
		
		return query;
	}
	public static Map<Integer,PermissibleStudySubjectRegistryStatus> constructPermissibleRegistryStatusHashMap(List<StudySubject> studySubjects){
		Map<Integer,PermissibleStudySubjectRegistryStatus>  permissibleRegistryStatusMap= new HashMap<Integer,PermissibleStudySubjectRegistryStatus>();
		for(StudySubject studySubject : studySubjects) {
			for(StudySubjectRegistryStatus studySubjectRegistryStatus : studySubject.getStudySubjectRegistryStatusHistoryInternal()){
				permissibleRegistryStatusMap.put(studySubjectRegistryStatus.getPermissibleStudySubjectRegistryStatus().getId(), 
						studySubjectRegistryStatus.getPermissibleStudySubjectRegistryStatus());
			}
		}
		return permissibleRegistryStatusMap;
	}
	
	public static Map<Integer,StudySubjectRegistryStatus> constructRegistryStatusHashMap(List<StudySubject> studySubjects){
		Map<Integer,StudySubjectRegistryStatus>  studySubjectRegistryStatusMap= new HashMap<Integer,StudySubjectRegistryStatus>();
		for(StudySubject studySubject : studySubjects) {
			for(StudySubjectRegistryStatus studySubjectRegistryStatus : studySubject.getStudySubjectRegistryStatusHistoryInternal()){
				studySubjectRegistryStatusMap.put(studySubjectRegistryStatus.getId(), studySubjectRegistryStatus);
			}
		}
			
		return studySubjectRegistryStatusMap;
	}
	
	public static Map<Integer,StudySubjectStudyVersion> constructStudySubjectStudyVersionsHashMap(List<StudySubject> studySubjects){
		Map<Integer,StudySubjectStudyVersion>  studySubjectStudyVersionMap= new HashMap<Integer,StudySubjectStudyVersion>();
		for(StudySubject studySubject : studySubjects) {
			for(StudySubjectStudyVersion studySubjectStudyVersion : studySubject.getStudySubjectStudyVersions()){
				studySubjectStudyVersionMap.put(studySubjectStudyVersion.getId(), studySubjectStudyVersion);
			}
			
		}
		return studySubjectStudyVersionMap;
	}
	
	public static Map<Integer,Consent> constructStudyConsentsHashMap(List<StudySubjectStudyVersion> studySubjectStudyVersions){
		Map<Integer,Consent>  studyConsentVersionMap= new HashMap<Integer,Consent>();
		for(StudySubjectStudyVersion studySubjectStudyVersion : studySubjectStudyVersions) {
			for(StudySubjectConsentVersion studySubjectConsentVersion : studySubjectStudyVersion.getStudySubjectConsentVersionsInternal()){
				studyConsentVersionMap.put(studySubjectConsentVersion.getConsent().getId(), studySubjectConsentVersion.getConsent());
			}
		}
		return studyConsentVersionMap;
	}
	
	public static Map<Integer,StudySubjectConsentVersion> constructStudySubjectConsentVersionsHashMap(List<StudySubjectStudyVersion> studySubjectStudyVersions){
		Map<Integer,StudySubjectConsentVersion>  studySubjectConsentVersionMap= new HashMap<Integer,StudySubjectConsentVersion>();
		for(StudySubjectStudyVersion studySubjectStudyVersion : studySubjectStudyVersions) {
			for(StudySubjectConsentVersion studySubjectConsentVersion : studySubjectStudyVersion.getStudySubjectConsentVersionsInternal()){
				studySubjectConsentVersionMap.put(studySubjectConsentVersion.getId(), studySubjectConsentVersion);
			}
		}
		return studySubjectConsentVersionMap;
	}
	
	public static Map<Integer,StudySubjectDemographics> constructDempgraphicsHashMap(List<StudySubjectDemographics> studySubjectDemographics){
		Map<Integer,StudySubjectDemographics>  demographicsMap= new HashMap<Integer,StudySubjectDemographics>();
		for(StudySubjectDemographics demographics : studySubjectDemographics) {
			demographicsMap.put(demographics.getId(), demographics);
		}
		return demographicsMap;
	}
	
	public static Map<Integer,ContactMechanism> constructContactMechanismsHashMap(List<StudySubjectDemographics> studySubjectDemographics){
		Map<Integer,ContactMechanism>  contactMechanismsMap= new HashMap<Integer,ContactMechanism>();
		for(StudySubjectDemographics demographics : studySubjectDemographics) {
			for(ContactMechanism contactMechanism: demographics.getContactMechanisms())
			contactMechanismsMap.put(contactMechanism.getId(), contactMechanism);
		}
		return contactMechanismsMap;
	}
	
	public static Map<Integer,Address> constructAddressesHashMap(List<StudySubjectDemographics> studySubjectDemographics){
		Map<Integer,Address>  addressMap= new HashMap<Integer,Address>();
		for(StudySubjectDemographics demographics : studySubjectDemographics) {
			for(Address adress : demographics.getAddresses())
			addressMap.put(adress.getId(), adress);
		}
		return addressMap;
	}
	
	public static Map<Integer,HealthcareSite> constructDemographicsIdentifierHealthcareSiteHashMap(List<StudySubjectDemographics> studySubjectDemographics){
		Map<Integer,HealthcareSite>  healthcareSitesMap= new HashMap<Integer,HealthcareSite>();
		for(StudySubjectDemographics demographics : studySubjectDemographics) {
			for(Identifier identifier : demographics.getIdentifiers()){
				if(identifier instanceof OrganizationAssignedIdentifier){
					healthcareSitesMap.put(((OrganizationAssignedIdentifier) identifier).getHealthcareSite().getId(), 
							((OrganizationAssignedIdentifier) identifier).getHealthcareSite());
				}
			}
		}
		return healthcareSitesMap;
	}
	
	public static Map<Integer,HealthcareSite> constructStudySubjectIdentifierHealthcareSiteHashMap(List<StudySubject> studySubjects){
		Map<Integer,HealthcareSite>  healthcareSitesMap= new HashMap<Integer,HealthcareSite>();
		for(StudySubject studySubject : studySubjects) {
			for(Identifier identifier : studySubject.getIdentifiers()){
				if(identifier instanceof OrganizationAssignedIdentifier){
					healthcareSitesMap.put(((OrganizationAssignedIdentifier) identifier).getHealthcareSite().getId(), 
							((OrganizationAssignedIdentifier) identifier).getHealthcareSite());
				}
			}
		}
		return healthcareSitesMap;
	}
	
	public static Map<Integer,HealthcareSite> constructStudyIdentifierHealthcareSiteHashMap(List<StudySubjectStudyVersion> studySubjectStudyVersions){
		Map<Integer,HealthcareSite>  healthcareSitesMap= new HashMap<Integer,HealthcareSite>();
		for(StudySubjectStudyVersion studySubjectStudyVersion : studySubjectStudyVersions) {
			for(Identifier identifier : studySubjectStudyVersion.getStudySiteStudyVersion().getStudyVersion().getStudy().getIdentifiers()){
				if(identifier instanceof OrganizationAssignedIdentifier){
					healthcareSitesMap.put(((OrganizationAssignedIdentifier) identifier).getHealthcareSite().getId(), 
							((OrganizationAssignedIdentifier) identifier).getHealthcareSite());
				}
			}
		}
		return healthcareSitesMap;
	}
	
	public static Map<Integer,ConsentQuestion> constructStudyConsentQuestionsHashMap(List<ConsentQuestion> consentQuestions){
		Map<Integer,ConsentQuestion>  consentQuestionsHashMap= new HashMap<Integer,ConsentQuestion>();
		for(ConsentQuestion consentQuestion : consentQuestions) {
				consentQuestionsHashMap.put(consentQuestion.getId(), consentQuestion);
		}
		return consentQuestionsHashMap;
	}
	
	public static Map<Integer,StudySiteStudyVersion> constructStudySiteVersionsHashMap(List<StudySubjectStudyVersion> studySubjectStudyVersions){
		Map<Integer,StudySiteStudyVersion>  studySiteStudyVersionMap= new HashMap<Integer,StudySiteStudyVersion>();
		for(StudySubjectStudyVersion studySubjectStudyVersion : studySubjectStudyVersions) {
			studySiteStudyVersionMap.put(studySubjectStudyVersion.getStudySiteStudyVersion().getId(), studySubjectStudyVersion.getStudySiteStudyVersion());
		}
		return studySiteStudyVersionMap;
	}
	
	public static Map<Integer,Study> constructStudiesHashMap(List<StudySubjectStudyVersion> studySubjectStudyVersions){
		Map<Integer,Study>  studiesMap= new HashMap<Integer,Study>();
		for(StudySubjectStudyVersion studySubjectStudyVersion : studySubjectStudyVersions) {
			studiesMap.put(studySubjectStudyVersion.getStudySiteStudyVersion().getStudyVersion().getStudy().getId(), 
					studySubjectStudyVersion.getStudySiteStudyVersion().getStudyVersion().getStudy());
		}
		return studiesMap;
	}
	
	public static List<StudySubjectDemographics> getStudySubjectDemographics(List<StudySubject> studySubjects){
		List<StudySubjectDemographics> studySubjectDemographicsList = new ArrayList<StudySubjectDemographics>();
		for(StudySubject studySubject : studySubjects){
			studySubjectDemographicsList.add(studySubject.getStudySubjectDemographics());
		}
		return studySubjectDemographicsList;
	}
	
	public static List<StudySubjectStudyVersion> getStudySubjectStudyVersions(List<StudySubject> studySubjects){
		List<StudySubjectStudyVersion> studySubjectStudyVersions = new ArrayList<StudySubjectStudyVersion>();
		for(StudySubject studySubject : studySubjects){
			studySubjectStudyVersions.addAll(studySubject.getStudySubjectStudyVersions());
		}
		return studySubjectStudyVersions;
	}
	
	public static List<ConsentQuestion> getStudyConsentQuestions(List<StudySubjectStudyVersion> studySubjectStudyVersions){
		List<ConsentQuestion> consentQuestions = new ArrayList<ConsentQuestion>();
		for(StudySubjectStudyVersion StudySubjectStudyVersion : studySubjectStudyVersions){
			for(StudySubjectConsentVersion sscv : StudySubjectStudyVersion.getStudySubjectConsentVersionsInternal()){
				for(SubjectConsentQuestionAnswer scqa : sscv.getSubjectConsentAnswers()){
					consentQuestions.add(scqa.getConsentQuestion());
				}
			}
		}
		return consentQuestions;
	}
}
