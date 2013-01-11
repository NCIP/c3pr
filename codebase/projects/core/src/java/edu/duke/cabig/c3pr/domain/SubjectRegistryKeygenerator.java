/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.duke.cabig.c3pr.utils.AdvancedSearchCriteriaParameterUtil;

import com.semanticbits.querybuilder.AdvancedSearchCriteriaParameter;


public final class SubjectRegistryKeygenerator {

	public static final Map<Integer, Integer> queryMap = new HashMap<Integer, Integer>();
	static {
		queryMap.put(generateKey(constructAdvancedSearchParamsForCase1()),1);
		queryMap.put(generateKey(constructAdvancedSearchParamsForCase2()),2);
		queryMap.put(generateKey(constructAdvancedSearchParamsForCase3()),3);
		queryMap.put(generateKey(constructAdvancedSearchParamsForCase4()),4);
		// queryMap.put(generateKey(constructAdvancedSearchParamsForCase5()),5);
		queryMap.put(generateKey(constructAdvancedSearchParamsForCase6()),6);
		queryMap.put(generateKey(constructAdvancedSearchParamsForCase7()),7);
		queryMap.put(generateKey(constructAdvancedSearchParamsForCase8()),8);
		queryMap.put(generateKey(constructAdvancedSearchParamsForCase9()),9);
		queryMap.put(generateKey(constructAdvancedSearchParamsForCase10()),10);
		queryMap.put(generateKey(constructAdvancedSearchParamsForCase11()),11);
		queryMap.put(generateKey(constructAdvancedSearchParamsForCase12()),12);
		queryMap.put(generateKey(constructAdvancedSearchParamsForCase13()),13);
		queryMap.put(generateKey(constructAdvancedSearchParamsForCase14()),14);
	}

	// Input case 1: studyIdentifier value, = (exact search on study identifier)
	private static List<AdvancedSearchCriteriaParameter> constructAdvancedSearchParamsForCase1() {
		List<AdvancedSearchCriteriaParameter> advParamsList = new ArrayList<AdvancedSearchCriteriaParameter>();
		advParamsList.add(AdvancedSearchCriteriaParameterUtil.studyIdentifierValueAdvancedParameter);
		return advParamsList;
	}

	// Input case 2: studySubject Identifier value, = (exact search on study
	// subject identifier)
	private static List<AdvancedSearchCriteriaParameter> constructAdvancedSearchParamsForCase2() {
		List<AdvancedSearchCriteriaParameter> advParamsList = new ArrayList<AdvancedSearchCriteriaParameter>();
		advParamsList.add(AdvancedSearchCriteriaParameterUtil.studySubjectIdentifierValueAdvancedParameter);
		return advParamsList;
	}

	// Input case 3: studySubjectConsentVersion documentId, =

	private static List<AdvancedSearchCriteriaParameter> constructAdvancedSearchParamsForCase3() {
		List<AdvancedSearchCriteriaParameter> advParamsList = new ArrayList<AdvancedSearchCriteriaParameter>();
		advParamsList.add(AdvancedSearchCriteriaParameterUtil.consentDocumentIdAdvancedParameter);
		return advParamsList;
	}

	// Input case 4: subjectIdentifier value, =

	private static List<AdvancedSearchCriteriaParameter> constructAdvancedSearchParamsForCase4() {
		List<AdvancedSearchCriteriaParameter> advParamsList = new ArrayList<AdvancedSearchCriteriaParameter>();
		advParamsList.add(AdvancedSearchCriteriaParameterUtil.subjectIdentifierValueAdvancedParameter);
		return advParamsList;
	}

	// Input case 5: studyIdentifier value, = ; 2. Status code, = (exact search
	// on study
	// identifier and exact search on status code (Registry Status))

	private static List<AdvancedSearchCriteriaParameter> constructAdvancedSearchParamsForCase5() {

		List<AdvancedSearchCriteriaParameter> advParamsList = new ArrayList<AdvancedSearchCriteriaParameter>();
		advParamsList.add(AdvancedSearchCriteriaParameterUtil.studyIdentifierValueAdvancedParameter);
		advParamsList.add(AdvancedSearchCriteriaParameterUtil.registryStatusCodeAdvancedParameter);
		return advParamsList;
	}

	// Input case 6: studyIdentifier value, =; 2. subjectIdentifier value, =
	private static List<AdvancedSearchCriteriaParameter> constructAdvancedSearchParamsForCase6() {

		List<AdvancedSearchCriteriaParameter> advParamsList = new ArrayList<AdvancedSearchCriteriaParameter>();
		advParamsList.add(AdvancedSearchCriteriaParameterUtil.studyIdentifierValueAdvancedParameter);
		advParamsList.add(AdvancedSearchCriteriaParameterUtil.subjectIdentifierValueAdvancedParameter);
		return advParamsList;
	}

	// Input case 7: 1. studyIdentifier value, =; 2.
	// study-typeInternal-identifier, =
	private static List<AdvancedSearchCriteriaParameter> constructAdvancedSearchParamsForCase7() {

		List<AdvancedSearchCriteriaParameter> advParamsList = new ArrayList<AdvancedSearchCriteriaParameter>();
		advParamsList.add(AdvancedSearchCriteriaParameterUtil.studyIdentifierValueAdvancedParameter);
		advParamsList.add(AdvancedSearchCriteriaParameterUtil.studyIdentifierTypeAdvancedParameter);
		return advParamsList;
	}

	// Input case 8: studySubjectIdentifier value, =; 2. studySubject-typeInternal-identifier,=
	private static List<AdvancedSearchCriteriaParameter> constructAdvancedSearchParamsForCase8() {

		List<AdvancedSearchCriteriaParameter> advParamsList = new ArrayList<AdvancedSearchCriteriaParameter>();
		advParamsList.add(AdvancedSearchCriteriaParameterUtil.studySubjectIdentifierValueAdvancedParameter);
		advParamsList.add(AdvancedSearchCriteriaParameterUtil.studySubjectIdentifierTypeAdvancedParameter);
		return advParamsList;
	}

	// Input case 9: subjectIdentifier value, = ; 2. subject-typeInternal-identifier,=

	private static List<AdvancedSearchCriteriaParameter> constructAdvancedSearchParamsForCase9() {

		List<AdvancedSearchCriteriaParameter> advParamsList = new ArrayList<AdvancedSearchCriteriaParameter>();
		advParamsList.add(AdvancedSearchCriteriaParameterUtil.subjectIdentifierValueAdvancedParameter);
		advParamsList.add(AdvancedSearchCriteriaParameterUtil.subjectIdentifierTypeAdvancedParameter);
		return advParamsList;
	}

	// Input case 10: studyIdentifier value, =; 2. statusCode, =; 3. lastName, !=
	private static List<AdvancedSearchCriteriaParameter> constructAdvancedSearchParamsForCase10() {

		List<AdvancedSearchCriteriaParameter> advParamsList = new ArrayList<AdvancedSearchCriteriaParameter>();
		advParamsList.add(AdvancedSearchCriteriaParameterUtil.studyIdentifierValueAdvancedParameter);
		advParamsList.add(AdvancedSearchCriteriaParameterUtil.registryStatusCodeAdvancedParameter);
		advParamsList.add(AdvancedSearchCriteriaParameterUtil.subjectLastNameAdvancedParameter);

		return advParamsList;
	}

	// Input case 11: studyIdentifier value, =; 2. statusCode, !=; 3. lastName,
	// !=; 4. effective-StudySubjectRegistryStatus-Date, >
	private static List<AdvancedSearchCriteriaParameter> constructAdvancedSearchParamsForCase11() {

		List<AdvancedSearchCriteriaParameter> advParamsList = new ArrayList<AdvancedSearchCriteriaParameter>();
		advParamsList.add(AdvancedSearchCriteriaParameterUtil.studyIdentifierValueAdvancedParameter);
		advParamsList.add(AdvancedSearchCriteriaParameterUtil.nonMathcingRegistryStatusCodeAdvancedParameter);
		advParamsList.add(AdvancedSearchCriteriaParameterUtil.subjectLastNameAdvancedParameter);
		advParamsList.add(AdvancedSearchCriteriaParameterUtil.subjectRegistryStatusEffectiveDateAdvancedParameter);

		return advParamsList;
	}

	// Input case 12: .studyIdentifier value, =; 2. studySubjectIdentifier value, =
	private static List<AdvancedSearchCriteriaParameter> constructAdvancedSearchParamsForCase12() {

		List<AdvancedSearchCriteriaParameter> advParamsList = new ArrayList<AdvancedSearchCriteriaParameter>();
		advParamsList.add(AdvancedSearchCriteriaParameterUtil.studyIdentifierValueAdvancedParameter);
		advParamsList.add(AdvancedSearchCriteriaParameterUtil.studySubjectIdentifierValueAdvancedParameter);
		return advParamsList;
	}

	// Input case 13: studyIdentifier value, =; 2. consent documentId,= (exact search on study
	// identifier and on document Id) ) here studyIdentifier value might be null sometimes
	private static List<AdvancedSearchCriteriaParameter> constructAdvancedSearchParamsForCase13() {

		List<AdvancedSearchCriteriaParameter> advParamsList = new ArrayList<AdvancedSearchCriteriaParameter>();
		advParamsList.add(AdvancedSearchCriteriaParameterUtil.studyIdentifierValueAdvancedParameter);
		advParamsList.add(AdvancedSearchCriteriaParameterUtil.consentDocumentIdAdvancedParameter);
		return advParamsList;
	}

	// Input case 14: 1.studyIdentifier value, = ; 2. RegistryStatus status, =
	//here studyIdentifier value might be null sometimes
	private static List<AdvancedSearchCriteriaParameter> constructAdvancedSearchParamsForCase14() {

		List<AdvancedSearchCriteriaParameter> advParamsList = new ArrayList<AdvancedSearchCriteriaParameter>();
		advParamsList.add(AdvancedSearchCriteriaParameterUtil.studyIdentifierValueAdvancedParameter);
		advParamsList.add(AdvancedSearchCriteriaParameterUtil.registryStatusCodeAdvancedParameter);
		return advParamsList;
	}

	public static int generateKey(
			List<AdvancedSearchCriteriaParameter> advParams) {

		int hashcode = 0;
		for (AdvancedSearchCriteriaParameter advParam : advParams) {
			StringBuilder sb = new StringBuilder();
			if (advParam.getContextObjectName() != null)
				sb.append(advParam.getContextObjectName());
			// get the actual class name from the qualified class name
			String[] splitString = advParam.getObjectName().split("\\.");
			sb.append(splitString[splitString.length - 1]);
			sb.append(advParam.getAttributeName());
			sb.append(advParam.getPredicate());
			hashcode += sb.toString().hashCode();
		}

		return hashcode;
	}

}
