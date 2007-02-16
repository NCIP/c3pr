DELETE FROM SCHEDULED_ARMS;
DELETE FROM IDENTIFIERS;
DELETE FROM ARMS;
DELETE FROM EPOCHS;
DELETE FROM STUDY_PARTICIPANT_ASSIGNMENTS;
DELETE FROM STUDY_SITES;
DELETE FROM STUDIES;
DELETE FROM HEALTHCARE_SITES;
DELETE FROM PARTICIPANTS;
DELETE FROM ADDRESSES;
DELETE FROM STUDY_INVESTIGATORS;
DELETE FROM INVESTIGATORS;

INSERT INTO ADDRESSES (ID,VERSION,STREET_ADDRESS,CITY,STATE_CODE,COUNTRY_CODE,POSTAL_CODE)
VALUES ('10001','1','512 South Magnum Street Suite 400','Durham','NC','USA','27710');
INSERT INTO ADDRESSES (ID,VERSION,STREET_ADDRESS,CITY,STATE_CODE,COUNTRY_CODE,POSTAL_CODE)
VALUES ('10002','1','10 Center Drive Bldg 10','Bethesda','MD','USA','20892');
INSERT INTO ADDRESSES (ID,VERSION,STREET_ADDRESS,CITY,STATE_CODE,COUNTRY_CODE,POSTAL_CODE)
VALUES ('10003','1','6130 Executive Blvd','Atlanta','MD','USA','20852');
INSERT INTO ADDRESSES (ID,VERSION,STREET_ADDRESS,CITY,STATE_CODE,COUNTRY_CODE,POSTAL_CODE)
VALUES ('10004','1','12359 Sunrise Valley Drive','Reston','VA','USA','20191');
INSERT INTO ADDRESSES (ID,VERSION,STREET_ADDRESS,CITY,STATE_CODE,COUNTRY_CODE,POSTAL_CODE)
VALUES ('10005','1','Medical Center Boulevard','Winston-Salem','NC','USA','27157');
INSERT INTO ADDRESSES (ID,VERSION,STREET_ADDRESS,CITY,STATE_CODE,COUNTRY_CODE,POSTAL_CODE)
VALUES ('10006','1','211 Main Street','Columbia','SC','USA','29201'');


INSERT INTO HEALTHCARE_SITES (ID,VERSION,GRID_ID,NAME,ADDRESS_ID)
VALUES ('10001', '1','DUKE GRID ID', 'Duke University Comprehensive Cancer Center', '10001');
INSERT INTO HEALTHCARE_SITES (ID,VERSION,GRID_ID,NAME,ADDRESS_ID)
VALUES ('10002', '1', 'NCI GRID ID','Warren Grant Magnuson Clinical Center', '10002');
INSERT INTO HEALTHCARE_SITES (ID,VERSION,GRID_ID,NAME,ADDRESS_ID)
VALUES ('10003', '1', 'NCI GRID ID','National Cancer Institute', '10003');
INSERT INTO HEALTHCARE_SITES (ID,VERSION,GRID_ID,NAME,ADDRESS_ID)
VALUES ('10004', '1','C3PR GRID ID', 'C3PR', '10004');
INSERT INTO HEALTHCARE_SITES (ID,VERSION,GRID_ID,NAME,ADDRESS_ID)
VALUES ('10005', '1','WF GRID ID', 'Wake Forest Comprehensive Cancer Center', '10005');

INSERT INTO STUDIES (ID, VERSION, GRID_ID, SHORT_TITLE_TEXT, LONG_TITLE_TEXT, DESCRIPTION_TEXT, TARGET_ACCRUAL_NUMBER, BLINDED_INDICATOR, RANDOMIZED_INDICATOR, MULTI_INSTITUTION_INDICATOR, SPONSOR_CODE, PHASE_CODE, PRECIS_TEXT, DISEASE_CODE, MONITOR_CODE, STATUS, TYPE)
VALUES (10001, 0, 'B13B207A-2289-4350-9883-25492FC0E8EA', 'LMB-2 IMMUNOTOXIN IN TREATING PATIENTS WITH CHRONIC LYMPHOCYTIC LEUKEMIA OR PROLYMPHOCYTIC LEUKEMIA', 
'A PHASE II CLINICAL TRIAL OF ANTI-TAC(FV)-PE38 (LMB-2) IMMUNOTOXIN FOR TREATMENT OF CD25 POSITIVE CHRONIC LYMPHOCYTIC LEUKEMIA',
'PATIENTS RECEIVE LMB-2 IMMUNOTOXIN IV OVER 30 MINUTES ON DAYS 1, 3, AND 5. TREATMENT REPEATS EVERY 28 DAYS FOR UP TO 6 COURSES IN THE ABSENCE OF DISEASE PROGRESSION, NEUTRALIZING ANTIBODIES (I.E., > 75% OF THE ACTIVITY OF 1�G/ML OF LMB-2 IMMUNOTOXIN), OR UNACCEPTABLE TOXICITY.PATIENTS WHO ACHIEVE A COMPLETE RESPONSE RECEIVE UP TO 2 ADDITIONAL COURSES OF LMB-2 IMMUNOTOXIN. PATIENTS WHO RELAPSE AFTER ACHIEVING A COMPLETE OR PARTIAL RESPONSE FOR MORE THAN 2 MONTHS ARE ELIGIBLE FOR RETREATMENT AS DESCRIBED ABOVE.', 
200,
0,
1,
0, 
'NATIONAL CANCER INSTITUTE', 
'PHASE II TRAIL', 
'A PHASE II CLINICAL TRIAL',
'CANCER', 
'CANCER THERAPY EVALUATION PROGRAM',
'ACTIVE - TRIAL IS OPEN TO ACCRUAL',
'GENETIC THERAPEUTIC');

INSERT INTO EPOCHS (ID, VERSION, GRID_ID,NAME, STU_ID)
VALUES (10001,0,'EPOCHS GRID ID','TREATMENT EPOCH',10001);

INSERT INTO ARMS (ID, VERSION, GRID_ID, NAME, EPH_ID)
VALUES (10001, 0,'ARM GRID ID','ARM A',10001);
INSERT INTO ARMS (ID, VERSION, GRID_ID, NAME, EPH_ID)
VALUES (10002, 0,'ARM GRID ID2','ARM A',10001);
INSERT INTO ARMS (ID, VERSION, GRID_ID, NAME, EPH_ID)
VALUES (10003, 0,'ARM GRID ID3','ARM A',10001);

INSERT INTO IDENTIFIERS(ID, VALUE, TYPE, SOURCE, VERSION, PRIMARY_INDICATOR, STU_ID)
VALUES (10001, '04_C_0121', 'C3D IDENTIFIER', 'NATIONAL CANCER INSTITUTE', 0, 0, 10001);
INSERT INTO IDENTIFIERS(ID, VALUE, TYPE, SOURCE, VERSION, PRIMARY_INDICATOR, STU_ID)
VALUES (10002, 'NCT00080821', 'CLINICALTRAILS.GOV IDENTIFIER', 'NATIONAL CANCER INSTITUTE', 0, 0, 10001);
INSERT INTO IDENTIFIERS(ID, VALUE, TYPE, SOURCE, VERSION, PRIMARY_INDICATOR, STU_ID)
VALUES (10003, 'NCI-04-C-0121', 'PROTOCOL AUTHORITY IDENTIFIER', 'NATIONAL CANCER INSTITUTE', 0, 1, 10001);

INSERT INTO STUDY_SITES(ID, HCS_ID, STUDY_ID, VERSION, GRID_ID, ROLE_CODE, STATUS_CODE, IRB_APPROVAL_DATE, START_DATE, END_DATE)
VALUES (10001, 10004, 10001, 0, 'STUDY SITES GRID ID','SITE', 'ACTIVE', '09-JAN-2006', '09-FEB-2006', '09-FEB-2006'); 


INSERT INTO participants(version,id,first_name, last_name, birth_date, administrative_gender_code, 
            race_code, ethnic_group_code, add_id)
    VALUES (1,10001,'Jeol','Garner',to_date('1950/07/09', 'yyyy/mm/dd'), 'Male', 'Black', 'Unknown', 
            10006);
            
INSERT INTO study_participant_assignments(version,id,study_participant_identifier, start_date, 
            eligibility_waiver_reason_text, informed_consent_signed_date, prt_id, sts_id, eligibility_indicator)
    VALUES (1,44,44,to_date('2003/07/09', 'yyyy/mm/dd'), 'Eligibility Waived', 
            to_date('2003/05/09', 'yyyy/mm/dd'), 
            10001, 10001, 'yes');
            
            
