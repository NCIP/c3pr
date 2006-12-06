package edu.duke.cabig.c3pr.utils;

public class Constants {
  public Constants() {
  }
  public static final String OPERATION = "operation";
  public static final String SEARCH = "SEARCH";
  public static final String SUB_OPERATION = "subOperation";
  public static final String INSERT ="insert";
  public static final String UPDATE ="update";
  public static final String DELETE ="delete";
  public static final String CLOSE ="close";
  public static final String CLOSE_VIEW ="closeView";
  public static final String DELETE_CONFIRM = "deleteConfirm";
  public static final String EDIT ="edit";
  public static final String PRE_INSERT ="pre_insert";
  public static final String PRE_UPDATE ="pre_update";
  public static final String PRE_INSERT_STEP2 ="pre_insert_STEP2";
  public static final String VIEW = "view";
  public static final String TREATMENT ="treatment";
  public static final String CURRENT_BLOCK = "currentBlock";
  public static final String TOTAL_RECORD = "totalRecords";
  public static final String VALUE_LIST_HANDLER = "valuelistHandler";
  public static final String RESULT_LIST = "resultList";
  public static final String HAS_NEXT_PAGE = "next_page_eval";
  public static final String HAS_PREV_PAGE = "prev_page_eval";
  public static final String PAGE_INDEX = "pageIndex";
  public static final String JVM_CACHED_COLLECTION ="jvmCachedCollection";
  public static final String JSP_RESULT_COLLECTION ="jspResultCollection";
  public static final String VERSION ="1.0";
  public static final String ERRORS = "errors";
  public static final String DETAIL_ERRORS = "detail_errors";
  public static final String RESULT_COLLECTION ="result";
  public static final String SEARCH_CRITERIA ="searchCritria";
  public static final String LIST_REGISTRATION ="listRegistration";
  public static final String DML_OPERATION = "dmlOperation";
  public static final String REGISTRATION_VO ="RegistrationVO";
  public static final String PARTICIPANT_VO ="ParticipantVO";
  public static final String RELATED_PARTICIPANT_VO ="RelatedParticipantVO";
  public static final String PROTOCOL_VO ="ProtocolVO";
  public static final String HEADER_MESSAGE = "headerMessage";
  public static final String MESSAGE = "message";
  public static final String VIEWRDC = "viewRDC";
  public static final String ACCESSOCDATA = "accessOcData";
  public static final String QADONE = "QA Done";
  public static final String QAUNDO = "QA Undo";
  public static final String PARTY_ADDRESS ="partyaddress";
  public static final String CONTACT_MECHANISM ="contactMechanism";
  public static final String PARTY_CONTACT_MECHANISM ="partycontactMechanism";
  public static final String REGISTER ="register";
  public static final String REFRESH ="refresh";
  public static final String EXTSYSTEM ="EXTSYSTEMURL";
  public static final String ERROR_ID ="errorId";
  public static final String TRUE="TRUE";
  public static final String FALSE="FALSE";
  public static final String YES="YES";
  public static final String LIST_CHANGE = "listChange";
  public static final String NO = "NO";
  public static final String PROTOCOL_DISEASE_ASSOCIATION = "ProtocolDiseaseAssociation";
  public static final String PARTICIPANT_DISEASE_ASSOCIATION = "ParticipantDiseaseAssociation";
  public static final String REGISTRATION_DISEASE_ASSOCIATION = "RegistrationDiseaseAssociation";
  public static final String ADD_DISEASE = "AddDisease";
  public static final String DELETE_DISEASE = "DeleteDisease";
  
  public static final String PROTOCOL_DISEASE_SITE_ASSOCIATION = "ProtocolDiseaseSiteAssociation";
  public static final String PARTICIPANT_DISEASE_SITE_ASSOCIATION = "ParticipantDiseaseSiteAssociation";
  public static final String REGISTRATION_DISEASE_SITE_ASSOCIATION = "RegistrationDiseaseSiteAssociation";
  public static final String ADD_DISEASE_SITE = "AddDiseaseSite";
  public static final String DELETE_DISEASE_SITE = "DeleteDiseaseSite";

  public static final String PROTOCOL = "Protocol";
  public static final String PARTICIPANT = "Participant";
  public static final String REGISTRATION = "Registration";
  public static final String PROTOCOL_DISEASE_ASSOCIATION_SEARCH = "ProtocolDiseaseAssociationSearch";



  //Paging Query Type Constants
  public static final String PARTICIPANT_PAGING = "PARTICIPANT_PAGING_QUERY";
  public static final String REGISTRATION_PARTICIPANT_PAGING = "REGISTRATION_PARTICIPANT_PAGING_QUERY";
  public static final String REGISTRATION_PROTOCOL_PAGING = "REGISTRATION_PROTOCOL_PAGING_QUERY";
  public static final String PROTOCOL_PAGING = "PROTOCOL_PAGING_QUERY";
  public static final String DISEASE_PAGING = "DISEASE_PAGING_QUERY";
  public static final String DISEASE_SITE_PAGING = "DISEASE_SITE_PAGING_QUERY";
  public static final String PROTOCOL_PARTICIPANT_ASSOCIATION_PAGING = "PROTOCOL_PARTICIPANT_ASSOCIATION_PAGING_QUERY";
  public static final String PROTOCOL_AMENDMENT_PAGING = "PROTOCOL_AMENDMENT_PAGING_QUERY";


  //Protocol Filtering Constants.  Object ID for Protection elements in the CSM database
  public static final String ALL_PROTOCOLS = "All.Protocols";



  public static final String ELIGIBILITY_INCOMPLETE = "Incomplete";
  public static final String ELIGIBILITY_COMPLETE_ELIGIBLE = "Eligible";
  public static final String ELIGIBILITY_COMPLETE_INELIGIBLE = "Not Eligible";
  public static final String BRANCH_REFRESH = "brRefresh";
  public static final String PROTOCOL_REFRESH = "protRefresh";
  public static final String PENDING = "Pending";

  public static final String REGISTRATION_ALLOWED = "Registration Allowed";
  public static final String REGISTRATION_NOT_ALLOWED_INELIGIBLE = "Ineligible for registration based on eligibility check";
  public static final String REGISTRATION_NOT_ALLOWED_AMENDMENT = "Already registered for treatment protocol";
  public static final String REGISTRATION_NOT_ALLOWED_AMENDMENT_CEILLING = "Amendment ceiling reached";
  public static final String REGISTRATION_NOT_ALLOWED_ARM_CEILLING = "Arm ceiling reached";

  public static final String MANAGE_VIEW_PARTICIPANT_ASSOCIATIONS= "Manage/View Participant Associations";
  public static final String BACK_TO_MANAGE_PARTICIPANT_ASSOCIATIONS = "Back to Manage/View Participant Associations";

  public static final String SEARCH_PARTICIPANT = "searchparticipant";
  public static final String DELETE_ASSOCIATION = "deleteassociation";
  public static final String ADD_ASSOCIATION = "addassociation";
  public static final String LOGIN = "login";
  public static final String ASSOCIATION_ROLE= "associationRole";

  public static final String PATIENT_REGISTRATION_INFORMATION= "PatientRegistrationInformation";
  public static final String NAVIGATION_SOURCE= "navigationSource";

  // Participant Roles
  public static final String PATIENT_CODE = "PT";
  public static final String PRINCIPAL_INVESTIGATOR_CODE = "PI";
  public static final String ASSOCIATE_INVESTIGATOR_CODE = "AI";
  public static final String DATA_MANAGER_CODE = "DM";
  public static final String REFERRING_PHYSICIAN_CODE = "RP";
  public static final String TREATING_PHYSICIAN_CODE = "TP";
  
  public static final String PATIENT = "Patient";
  public static final String PRINCIPAL_INVESTIGATOR = "Primary Investigator";
  public static final String ASSOCIATE_INVESTIGATOR = "Associated Investigator";
  public static final String DATA_MANAGER = "Data Manager";
  public static final String REFERRING_PHYSICIAN = "Referring Physician";
  public static final String TREATING_PHYSICIAN = "Treating Physician";
  
  public static final String PATIENT_ROLE_CODE = "PT";
  public static final String PRINCIPAL_INVESTIGATOR_ROLE_CODE = "PI";
  public static final String ASSOCIATE_INVESTIGATOR_ROLE_CODE= "AI";
  public static final String DATA_MANAGER_ROLE_CODE = "DM";
  public static final String REFERRING_PHYSICIAN_ROLE_CODE = "RP";
  public static final String TREATING_PHYSICIAN_ROLE_CODE = "TP";
  public static final String MSSQL_SERVER ="SQLServer";

  public static final String CLINICAL_DATA_ACCESS ="ClinicalDataAccess";
  public static final String CLINICAL_DATA_ACCESS_SAVE ="SaveClinicalDataAccess";
  
}