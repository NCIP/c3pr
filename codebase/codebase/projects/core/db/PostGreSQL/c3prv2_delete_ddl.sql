drop table scheduled_arms;
drop table epochs cascade;
drop table study_participant_assignments cascade;
drop table if exists healthcare_sites cascade;
drop table if exists organizations cascade;
drop table study_investigators cascade;
drop table study_sites cascade;
drop table studies cascade;
drop table investigators cascade;
drop table addresses cascade;
drop table participants cascade;
drop table hc_site_investigators cascade;
drop table arms cascade;
drop table identifiers cascade;
drop table study_personnels cascade;
drop table research_staffs cascade;
drop table subject_eligibility_answers cascade;
drop table subject_stratification_answers cascade;
drop table study_diseases cascade;
drop table disease_terms cascade;
drop table disease_categories cascade;
drop table stratification_criterion cascade;
drop table stratification_cri_per_ans cascade;
drop table if exists disease_history cascade;
drop table if exists anatomic_sites cascade;
drop table eligibility_criterias cascade;
drop table contact_mechanisms cascade;

drop SEQUENCE IDENTIFIERS_ID_seq ;
drop SEQUENCE ARMS_ID_seq ;
drop SEQUENCE STUDY_PARTICIPANT_ASSIG_ID_seq ;
drop SEQUENCE ADDRESSES_ID_seq ;
drop SEQUENCE EPOCHS_ID_seq ;
drop SEQUENCE HC_SITE_INVESTIGATORS_ID_seq ;
drop SEQUENCE if exists HEALTHCARE_SITES_ID_seq ;
drop SEQUENCE if exists ORGANIZATIONS_ID_seq ;
drop SEQUENCE INVESTIGATORS_ID_seq ;
drop SEQUENCE PARTICIPANTS_ID_seq ;
drop SEQUENCE SCHEDULED_ARMS_ID_seq ;
drop SEQUENCE STUDIES_ID_seq ;
drop SEQUENCE STUDY_INVESTIGATORS_ID_seq ;
drop SEQUENCE STUDY_SITES_ID_seq;
drop SEQUENCE STUDY_PERSONNELS_ID_seq;
drop SEQUENCE RESEARCH_STAFFS_ID_seq;
drop SEQUENCE ELIGIBILITY_CRITERIAS_ID_seq;
drop SEQUENCE PRT_STRAT_ANS_id_seq;
drop SEQUENCE PRT_ELIGIBILITY_ANS_ID_seq;
drop SEQUENCE STUDY_DISEASES_ID_seq;
drop SEQUENCE DISEASE_TERMS_ID_seq;
drop SEQUENCE DISEASE_CATEGORIES_ID_seq;
drop SEQUENCE STRATIFICATION_CRI_ID_SEQ;
drop SEQUENCE STRATIFICATION_CRI_ANS_ID_SEQ;
drop SEQUENCE DISEASE_HISTORY_ID_SEQ;
drop SEQUENCE ANATOMIC_SITES_ID_SEQ;
drop SEQUENCE CONTACT_MECHANISMS_ID_SEQ;