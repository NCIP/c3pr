INSERT INTO ADDRESSES (ID,VERSION,STREET_ADDRESS,CITY,STATE_CODE,COUNTRY_CODE,POSTAL_CODE)
VALUES ('10001','1','512 South Magnum Street Suite 400','Durham','NC','USA','27710');
INSERT INTO ADDRESSES (ID,VERSION,STREET_ADDRESS,CITY,STATE_CODE,COUNTRY_CODE,POSTAL_CODE)
VALUES ('10002','1','10 Center Drive Bldg 10','Bethesda','MD','USA','20892');
INSERT INTO ADDRESSES (ID,VERSION,STREET_ADDRESS,CITY,STATE_CODE,COUNTRY_CODE,POSTAL_CODE)
VALUES ('10003','1','6130 Executive Blvd','Rockville','MD','USA','20852');
INSERT INTO ADDRESSES (ID,VERSION,STREET_ADDRESS,CITY,STATE_CODE,COUNTRY_CODE,POSTAL_CODE)
VALUES ('10004','1','12359 Sunrise Valley Drive','Reston','VA','USA','20191');
INSERT INTO ADDRESSES (ID,VERSION,STREET_ADDRESS,CITY,STATE_CODE,COUNTRY_CODE,POSTAL_CODE)
VALUES ('10005','1','Medical Center Boulevard','Winston-Salem','NC','USA','27157');

INSERT INTO ORGANIZATIONS (ID,VERSION,GRID_ID,NAME,ADDRESS_ID)
VALUES ('10001', '1','DUKE GRID ID', 'Duke University Comprehensive Cancer Center', '10001');
INSERT INTO ORGANIZATIONS (ID,VERSION,GRID_ID,NAME,ADDRESS_ID)
VALUES ('10002', '1', 'NCI GRID ID','Warren Grant Magnuson Clinical Center', '10002');
INSERT INTO ORGANIZATIONS (ID,VERSION,GRID_ID,NAME,ADDRESS_ID)
VALUES ('10003', '1', 'NCI GRID ID','National Cancer Institute', '10003');
INSERT INTO ORGANIZATIONS (ID,VERSION,GRID_ID,NAME,ADDRESS_ID)
VALUES ('10004', '1','C3PR GRID ID', 'C3PR', '10004');
INSERT INTO ORGANIZATIONS (ID,VERSION,GRID_ID,NAME,ADDRESS_ID)
VALUES ('10005', '1','WF GRID ID', 'Wake Forest Comprehensive Cancer Center', '10005');

INSERT INTO INVESTIGATORS (ID,VERSION,GRID_ID,FIRST_NAME,LAST_NAME,NCI_IDENTIFIER)
VALUES ('10001', '1','INVESTIGATOR ID', 'Erich', 'Schmidt', '1000001');
INSERT INTO INVESTIGATORS (ID,VERSION,GRID_ID,FIRST_NAME,LAST_NAME,NCI_IDENTIFIER)
VALUES ('10002', '1','INVESTIGATOR ID2', 'Paul', 'Allen', '1000021');
INSERT INTO INVESTIGATORS (ID,VERSION,GRID_ID,FIRST_NAME,LAST_NAME,NCI_IDENTIFIER)
VALUES ('10003', '1','INVESTIGATOR ID3', 'Bill', 'Gates', '1000031');
INSERT INTO INVESTIGATORS (ID,VERSION,GRID_ID,FIRST_NAME,LAST_NAME,NCI_IDENTIFIER)
VALUES ('10004', '1','INVESTIGATOR ID4', 'Warren', 'Buffet', '1000041');
INSERT INTO INVESTIGATORS (ID,VERSION,GRID_ID,FIRST_NAME,LAST_NAME,NCI_IDENTIFIER)
VALUES ('10005', '1','INVESTIGATOR ID5', 'Steve', 'Jobs', '1000051');
INSERT INTO INVESTIGATORS (ID,VERSION,GRID_ID,FIRST_NAME,LAST_NAME,NCI_IDENTIFIER)
VALUES ('10006', '1','INVESTIGATOR ID6', 'Rupert', 'Murdoch', '1000061');
INSERT INTO INVESTIGATORS (ID,VERSION,GRID_ID,FIRST_NAME,LAST_NAME,NCI_IDENTIFIER)
VALUES ('10007', '1','INVESTIGATOR ID7', 'Bill', 'Clinton', '1000071');
INSERT INTO INVESTIGATORS (ID,VERSION,GRID_ID,FIRST_NAME,LAST_NAME,NCI_IDENTIFIER)
VALUES ('10008', '1','INVESTIGATOR ID8', 'George', 'Bush', '1000081');
INSERT INTO INVESTIGATORS (ID,VERSION,GRID_ID,FIRST_NAME,LAST_NAME,NCI_IDENTIFIER)
VALUES ('10009', '1','INVESTIGATOR ID9', 'Hillary', 'Clinton', '1000091');


INSERT INTO HC_SITE_INVESTIGATORS (ID,VERSION,GRID_ID,STATUS_CODE,STATUS_DATE,INV_ID,HCS_ID)
VALUES ('10001', '1','HC_SITE ID1', 'Open', to_date('2007/03/01', 'yyyy/mm/dd'), '10001','10001');
INSERT INTO HC_SITE_INVESTIGATORS (ID,VERSION,GRID_ID,STATUS_CODE,STATUS_DATE,INV_ID,HCS_ID)
VALUES ('10002', '1','HC_SITE ID2', 'Open', to_date('2007/04/01', 'yyyy/mm/dd'), '10002','10001');
INSERT INTO HC_SITE_INVESTIGATORS (ID,VERSION,GRID_ID,STATUS_CODE,STATUS_DATE,INV_ID,HCS_ID)
VALUES ('10003', '1','HC_SITE ID3', 'Open', to_date('2007/05/01', 'yyyy/mm/dd'), '10003','10001');
INSERT INTO HC_SITE_INVESTIGATORS (ID,VERSION,GRID_ID,STATUS_CODE,STATUS_DATE,INV_ID,HCS_ID)
VALUES ('10004', '1','HC_SITE ID4', 'Open', to_date('2007/05/01', 'yyyy/mm/dd'), '10005','10002');
INSERT INTO HC_SITE_INVESTIGATORS (ID,VERSION,GRID_ID,STATUS_CODE,STATUS_DATE,INV_ID,HCS_ID)
VALUES ('10005', '1','HC_SITE ID5', 'Open', to_date('2007/05/01', 'yyyy/mm/dd'), '10008','10002');
INSERT INTO HC_SITE_INVESTIGATORS (ID,VERSION,GRID_ID,STATUS_CODE,STATUS_DATE,INV_ID,HCS_ID)
VALUES ('10006', '1','HC_SITE ID6', 'Open', to_date('2007/05/01', 'yyyy/mm/dd'), '10009','10002');
INSERT INTO HC_SITE_INVESTIGATORS (ID,VERSION,GRID_ID,STATUS_CODE,STATUS_DATE,INV_ID,HCS_ID)
VALUES ('10007', '1','HC_SITE ID7', 'Open', to_date('2007/05/01', 'yyyy/mm/dd'), '10003','10003');
INSERT INTO HC_SITE_INVESTIGATORS (ID,VERSION,GRID_ID,STATUS_CODE,STATUS_DATE,INV_ID,HCS_ID)
VALUES ('10008', '1','HC_SITE ID8', 'Open', to_date('2007/05/01', 'yyyy/mm/dd'), '10005','10003');
INSERT INTO HC_SITE_INVESTIGATORS (ID,VERSION,GRID_ID,STATUS_CODE,STATUS_DATE,INV_ID,HCS_ID)
VALUES ('10009', '1','HC_SITE ID9', 'Open', to_date('2007/05/01', 'yyyy/mm/dd'), '10003','10004');
INSERT INTO HC_SITE_INVESTIGATORS (ID,VERSION,GRID_ID,STATUS_CODE,STATUS_DATE,INV_ID,HCS_ID)
VALUES ('10010', '1','HC_SITE ID10', 'Open', to_date('2007/05/01', 'yyyy/mm/dd'), '10004','10004');
INSERT INTO HC_SITE_INVESTIGATORS (ID,VERSION,GRID_ID,STATUS_CODE,STATUS_DATE,INV_ID,HCS_ID)
VALUES ('10011', '1','HC_SITE ID11', 'Open', to_date('2007/05/01', 'yyyy/mm/dd'), '10007','10004');
INSERT INTO HC_SITE_INVESTIGATORS (ID,VERSION,GRID_ID,STATUS_CODE,STATUS_DATE,INV_ID,HCS_ID)
VALUES ('10012', '1','HC_SITE ID12', 'Open', to_date('2007/05/01', 'yyyy/mm/dd'), '10009','10004');

INSERT INTO RESEARCH_STAFF (ID,VERSION,GRID_ID,FIRST_NAME,LAST_NAME, HCS_ID)
VALUES ('10001', '1','RSTAFF1', 'George', 'Bush', '10001');
INSERT INTO RESEARCH_STAFF (ID,VERSION,GRID_ID,FIRST_NAME,LAST_NAME, HCS_ID)
VALUES ('10002', '1','RSTAFF2', 'Ben', 'Franklin', '10001');
INSERT INTO RESEARCH_STAFF (ID,VERSION,GRID_ID,FIRST_NAME,LAST_NAME, HCS_ID)
VALUES ('10003', '1','RSTAFF3', 'JF', 'Kennedy', '10002');
INSERT INTO RESEARCH_STAFF (ID,VERSION,GRID_ID,FIRST_NAME,LAST_NAME, HCS_ID)
VALUES ('10004', '1','RSTAFF4', 'Bill', 'Clinton', '10002');
INSERT INTO RESEARCH_STAFF (ID,VERSION,GRID_ID,FIRST_NAME,LAST_NAME, HCS_ID)
VALUES ('10005', '1','RSTAFF1', 'Mahatma', 'Gandhi', '10003');
INSERT INTO RESEARCH_STAFF (ID,VERSION,GRID_ID,FIRST_NAME,LAST_NAME, HCS_ID)
VALUES ('10006', '1','RSTAFF2', 'Rajiv', 'Gandhi', '10003');
INSERT INTO RESEARCH_STAFF (ID,VERSION,GRID_ID,FIRST_NAME,LAST_NAME, HCS_ID)
VALUES ('10007', '1','RSTAFF3', 'Tom', 'Cruise', '10004');
INSERT INTO RESEARCH_STAFF (ID,VERSION,GRID_ID,FIRST_NAME,LAST_NAME, HCS_ID)
VALUES ('10008', '1','RSTAFF4', 'Angelina', 'Jolie', '10004');

INSERT INTO anatomic_sites(
            id, name, category, version)
    VALUES (2,'Peripheral blood','Bone Marrow',1);
INSERT INTO anatomic_sites(
            id, name, category, version)
    VALUES (3,'Brainstem','Central Nervous System',1);
INSERT INTO anatomic_sites(
            id, name, category, version)
    VALUES (4,'CSF','Central Nervous System',1);
INSERT INTO anatomic_sites(
            id, name, category, version)
    VALUES (5,'Cerebellum','Central Nervous System',1);
INSERT INTO anatomic_sites(
            id, name, category, version)
    VALUES (6,'Cerebrum','Central Nervous System',1);
INSERT INTO anatomic_sites(
            id, name, category, version)
    VALUES (7,'Skin','Dermatology',1);
INSERT INTO anatomic_sites(
            id, name, category, version)
    VALUES (8,'bernate ','Gastrointestinal',1);
INSERT INTO anatomic_sites(
            id, name, category, version)
    VALUES (9,'Appendix','Gastrointestinal',1);
INSERT INTO anatomic_sites(
            id, name, category, version)
    VALUES (10,'Colon','Gastrointestinal',1);
 
     