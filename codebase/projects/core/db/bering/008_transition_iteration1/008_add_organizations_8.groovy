class OrganizationCodes extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
        // Have to break up the inserts so as not to exceed the java max method length
        m0()
        m1()
        m2()
        m3()
        m4()
        m5()
        m6()
        m7()
        m8()
        m9()
        m10()
        m11()
        m12()
        m13()
        m14()
        m15()
        m16()
        m17()
        m18()
        m19()
        m20()
        m21()
        m22()
        m23()
        m24()
        m25()
        m26()
        m27()
        m28()
        m29()
        m30()
        m31()
        m32()
        m33()
        m34()
        m35()
        m36()
    }

    void m0() {
        // all0 (25 terms)
      insert('addresses', [ id: 18998, city: "Knoxville", state_code: "TN", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 18998, nci_institute_code: "TN124", name: "Tennessee Cancer Specialists -Baptist Medical Tower" ,address_id: 18998,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -11998,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN124",GROUP_DESC:"TN124 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -11998,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TN124",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TN124",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -11998,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN124", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:11998 ,protection_group_id: -11998, protection_element_id:-11998], primaryKey: false);
      insert('addresses', [ id: 18999, city: "Chattanooga", state_code: "TN", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 18999, nci_institute_code: "TN125", name: "Chattanooga Gynecological Oncology" ,address_id: 18999,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -11999,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN125",GROUP_DESC:"TN125 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -11999,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TN125",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TN125",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -11999,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN125", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:11999 ,protection_group_id: -11999, protection_element_id:-11999], primaryKey: false);
      insert('addresses', [ id: 19000, city: "Chattanooga", state_code: "TN", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19000, nci_institute_code: "TN126", name: "Kimsey Radiation Oncology PLLC" ,address_id: 19000,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12000,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN126",GROUP_DESC:"TN126 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12000,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TN126",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TN126",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12000,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN126", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12000 ,protection_group_id: -12000, protection_element_id:-12000], primaryKey: false);
      insert('addresses', [ id: 19001, city: "Powell", state_code: "TN", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19001, nci_institute_code: "TN127", name: "Saint Mary's North Cancer Center" ,address_id: 19001,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12001,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN127",GROUP_DESC:"TN127 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12001,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TN127",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TN127",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12001,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN127", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12001 ,protection_group_id: -12001, protection_element_id:-12001], primaryKey: false);
      insert('addresses', [ id: 19002, city: "Smyrna", state_code: "TN", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19002, nci_institute_code: "TN128", name: "Tennessee Oncology PLLC" ,address_id: 19002,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12002,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN128",GROUP_DESC:"TN128 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12002,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TN128",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TN128",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12002,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN128", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12002 ,protection_group_id: -12002, protection_element_id:-12002], primaryKey: false);
      insert('addresses', [ id: 19003, city: "Lebanon", state_code: "TN", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19003, nci_institute_code: "TN129", name: "Tennessee Oncology, PLLC" ,address_id: 19003,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12003,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN129",GROUP_DESC:"TN129 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12003,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TN129",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TN129",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12003,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN129", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12003 ,protection_group_id: -12003, protection_element_id:-12003], primaryKey: false);
      insert('addresses', [ id: 19004, city: "Oak Ridge", state_code: "TN", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19004, nci_institute_code: "TN130", name: "Thompson Cancer Survival Center at Methodist" ,address_id: 19004,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12004,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN130",GROUP_DESC:"TN130 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12004,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TN130",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TN130",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12004,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN130", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12004 ,protection_group_id: -12004, protection_element_id:-12004], primaryKey: false);
      insert('addresses', [ id: 19005, city: "Kingsport", state_code: "TN", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19005, nci_institute_code: "TN131", name: "Kingsport Hematology-Oncology Associates" ,address_id: 19005,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12005,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN131",GROUP_DESC:"TN131 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12005,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TN131",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TN131",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12005,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN131", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12005 ,protection_group_id: -12005, protection_element_id:-12005], primaryKey: false);
      insert('addresses', [ id: 19006, city: "Chattanooga", state_code: "TN", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19006, nci_institute_code: "TN132", name: "Associates in Oncology & Hematology" ,address_id: 19006,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12006,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN132",GROUP_DESC:"TN132 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12006,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TN132",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TN132",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12006,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN132", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12006 ,protection_group_id: -12006, protection_element_id:-12006], primaryKey: false);
      insert('addresses', [ id: 19007, city: "Nashville", state_code: "TN", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19007, nci_institute_code: "TN133", name: "Tennessee Oncology PLLC" ,address_id: 19007,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12007,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN133",GROUP_DESC:"TN133 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12007,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TN133",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TN133",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12007,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN133", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12007 ,protection_group_id: -12007, protection_element_id:-12007], primaryKey: false);
      insert('addresses', [ id: 19008, city: "Oak Ridge", state_code: "TN", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19008, nci_institute_code: "TN134", name: "Radiation Medicine Specialists PC" ,address_id: 19008,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12008,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN134",GROUP_DESC:"TN134 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12008,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TN134",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TN134",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12008,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN134", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12008 ,protection_group_id: -12008, protection_element_id:-12008], primaryKey: false);
      insert('addresses', [ id: 19009, city: "Clarksville", state_code: "TN", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19009, nci_institute_code: "TN135", name: "Tennessee Oncology PLLC" ,address_id: 19009,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12009,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN135",GROUP_DESC:"TN135 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12009,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TN135",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TN135",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12009,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN135", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12009 ,protection_group_id: -12009, protection_element_id:-12009], primaryKey: false);
      insert('addresses', [ id: 19010, city: "Gallatin", state_code: "TN", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19010, nci_institute_code: "TN136", name: "Tennessee Oncology PLLC" ,address_id: 19010,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12010,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN136",GROUP_DESC:"TN136 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12010,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TN136",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TN136",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12010,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN136", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12010 ,protection_group_id: -12010, protection_element_id:-12010], primaryKey: false);
      insert('addresses', [ id: 19011, city: "Murfreesboro", state_code: "TN", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19011, nci_institute_code: "TN137", name: "Colombia Radiation Oncology" ,address_id: 19011,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12011,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN137",GROUP_DESC:"TN137 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12011,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TN137",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TN137",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12011,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN137", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12011 ,protection_group_id: -12011, protection_element_id:-12011], primaryKey: false);
      insert('addresses', [ id: 19012, city: "Murfreesboro", state_code: "TN", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19012, nci_institute_code: "TN138", name: "Middle Tennessee Medical Center" ,address_id: 19012,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12012,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN138",GROUP_DESC:"TN138 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12012,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TN138",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TN138",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12012,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN138", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12012 ,protection_group_id: -12012, protection_element_id:-12012], primaryKey: false);
      insert('addresses', [ id: 19013, city: "Memphis", state_code: "TN", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19013, nci_institute_code: "TN139", name: "The West Clinic - Mid-Town" ,address_id: 19013,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12013,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN139",GROUP_DESC:"TN139 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12013,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TN139",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TN139",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12013,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN139", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12013 ,protection_group_id: -12013, protection_element_id:-12013], primaryKey: false);
      insert('addresses', [ id: 19014, city: "Chattanooga", state_code: "TN", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19014, nci_institute_code: "TN140", name: "Alliance of Cardiac Thoracic and Vascular Surgeons" ,address_id: 19014,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12014,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN140",GROUP_DESC:"TN140 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12014,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TN140",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TN140",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12014,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN140", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12014 ,protection_group_id: -12014, protection_element_id:-12014], primaryKey: false);
      insert('addresses', [ id: 19015, city: "Knoxville", state_code: "TN", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19015, nci_institute_code: "TN141", name: "Thompson Cancer Survival Center - West" ,address_id: 19015,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12015,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN141",GROUP_DESC:"TN141 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12015,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TN141",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TN141",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12015,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN141", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12015 ,protection_group_id: -12015, protection_element_id:-12015], primaryKey: false);
      insert('addresses', [ id: 19016, city: "Johnson City", state_code: "TN", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19016, nci_institute_code: "TN142", name: "Cancer Outreach Associates of Tennessee PC" ,address_id: 19016,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12016,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN142",GROUP_DESC:"TN142 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12016,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TN142",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TN142",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12016,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN142", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12016 ,protection_group_id: -12016, protection_element_id:-12016], primaryKey: false);
      insert('addresses', [ id: 19017, city: "Memphis", state_code: "TN", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19017, nci_institute_code: "TN143", name: "Baptist Memorial Hospital for Women" ,address_id: 19017,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12017,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN143",GROUP_DESC:"TN143 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12017,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TN143",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TN143",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12017,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN143", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12017 ,protection_group_id: -12017, protection_element_id:-12017], primaryKey: false);
      insert('addresses', [ id: 19018, city: "Cookeville", state_code: "TN", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19018, nci_institute_code: "TN144", name: "Cookeville Regional Medical Center" ,address_id: 19018,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12018,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN144",GROUP_DESC:"TN144 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12018,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TN144",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TN144",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12018,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN144", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12018 ,protection_group_id: -12018, protection_element_id:-12018], primaryKey: false);
      insert('addresses', [ id: 19019, city: "Knoxville", state_code: "TN", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19019, nci_institute_code: "TN145", name: "Knoxville Comprehensive Breast Center" ,address_id: 19019,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12019,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN145",GROUP_DESC:"TN145 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12019,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TN145",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TN145",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12019,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN145", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12019 ,protection_group_id: -12019, protection_element_id:-12019], primaryKey: false);
      insert('addresses', [ id: 19020, city: "Columbia", state_code: "TN", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19020, nci_institute_code: "TN146", name: "Columbia Surgery Group" ,address_id: 19020,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12020,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN146",GROUP_DESC:"TN146 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12020,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TN146",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TN146",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12020,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN146", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12020 ,protection_group_id: -12020, protection_element_id:-12020], primaryKey: false);
      insert('addresses', [ id: 19021, city: "Knoxville", state_code: "TN", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19021, nci_institute_code: "TN147", name: "Thompson Oncology Group" ,address_id: 19021,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12021,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN147",GROUP_DESC:"TN147 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12021,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TN147",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TN147",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12021,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN147", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12021 ,protection_group_id: -12021, protection_element_id:-12021], primaryKey: false);
      insert('addresses', [ id: 19022, city: "Nashville", state_code: "TN", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19022, nci_institute_code: "TN148", name: "The Surgical Clinic PLLC" ,address_id: 19022,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12022,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN148",GROUP_DESC:"TN148 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12022,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TN148",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TN148",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12022,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN148", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12022 ,protection_group_id: -12022, protection_element_id:-12022], primaryKey: false);
    }

    void m1() {
        // all1 (25 terms)
      insert('addresses', [ id: 19023, city: "Columbia", state_code: "TN", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19023, nci_institute_code: "TN149", name: "Maury Regional Hospital" ,address_id: 19023,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12023,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN149",GROUP_DESC:"TN149 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12023,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TN149",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TN149",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12023,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN149", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12023 ,protection_group_id: -12023, protection_element_id:-12023], primaryKey: false);
      insert('addresses', [ id: 19024, city: "Powell", state_code: "TN", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19024, nci_institute_code: "TN150", name: "Tennessee Cancer Specialists PLLC" ,address_id: 19024,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12024,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN150",GROUP_DESC:"TN150 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12024,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TN150",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TN150",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12024,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN150", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12024 ,protection_group_id: -12024, protection_element_id:-12024], primaryKey: false);
      insert('addresses', [ id: 19025, city: "Nashville", state_code: "TN", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19025, nci_institute_code: "TN151", name: "Cumberland Surgical Associates" ,address_id: 19025,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12025,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN151",GROUP_DESC:"TN151 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12025,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TN151",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TN151",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12025,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN151", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12025 ,protection_group_id: -12025, protection_element_id:-12025], primaryKey: false);
      insert('addresses', [ id: 19026, city: "Chattanooga", state_code: "TN", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19026, nci_institute_code: "TN152", name: "University Surgical Associates" ,address_id: 19026,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12026,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN152",GROUP_DESC:"TN152 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12026,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TN152",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TN152",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12026,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN152", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12026 ,protection_group_id: -12026, protection_element_id:-12026], primaryKey: false);
      insert('addresses', [ id: 19027, city: "Knoxville", state_code: "TN", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19027, nci_institute_code: "TN153", name: "Hall and Martin MDs PC" ,address_id: 19027,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12027,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN153",GROUP_DESC:"TN153 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12027,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TN153",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TN153",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12027,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN153", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12027 ,protection_group_id: -12027, protection_element_id:-12027], primaryKey: false);
      insert('addresses', [ id: 19028, city: "Morristown", state_code: "TN", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19028, nci_institute_code: "TN154", name: "Morristown Regional Cancer Center" ,address_id: 19028,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12028,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN154",GROUP_DESC:"TN154 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12028,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TN154",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TN154",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12028,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN154", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12028 ,protection_group_id: -12028, protection_element_id:-12028], primaryKey: false);
      insert('addresses', [ id: 19029, city: "Nashville", state_code: "TN", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19029, nci_institute_code: "TN155", name: "Saint Thomas Research Institute" ,address_id: 19029,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12029,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN155",GROUP_DESC:"TN155 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12029,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TN155",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TN155",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12029,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN155", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12029 ,protection_group_id: -12029, protection_element_id:-12029], primaryKey: false);
      insert('addresses', [ id: 19030, city: "Franklin", state_code: "TN", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19030, nci_institute_code: "TN156", name: "Vanderbilt-Ingram Cancer Center at Franklin" ,address_id: 19030,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12030,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN156",GROUP_DESC:"TN156 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12030,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TN156",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TN156",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12030,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN156", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12030 ,protection_group_id: -12030, protection_element_id:-12030], primaryKey: false);
      insert('addresses', [ id: 19031, city: "Knoxville", state_code: "TN", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19031, nci_institute_code: "TN157", name: "Vijay R Patil MD" ,address_id: 19031,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12031,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN157",GROUP_DESC:"TN157 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12031,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TN157",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TN157",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12031,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN157", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12031 ,protection_group_id: -12031, protection_element_id:-12031], primaryKey: false);
      insert('addresses', [ id: 19032, city: "Chattanooga", state_code: "TN", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19032, nci_institute_code: "TN158", name: "Women's Institute for Specialized Health" ,address_id: 19032,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12032,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN158",GROUP_DESC:"TN158 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12032,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TN158",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TN158",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12032,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN158", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12032 ,protection_group_id: -12032, protection_element_id:-12032], primaryKey: false);
      insert('addresses', [ id: 19033, city: "Chattanoga", state_code: "TN", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19033, nci_institute_code: "TN159", name: "John L Gwin General Surgery PLLC" ,address_id: 19033,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12033,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN159",GROUP_DESC:"TN159 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12033,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TN159",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TN159",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12033,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN159", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12033 ,protection_group_id: -12033, protection_element_id:-12033], primaryKey: false);
      insert('addresses', [ id: 19034, city: "Collierville", state_code: "TN", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19034, nci_institute_code: "TN160", name: "The West Clinic - Collierville" ,address_id: 19034,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12034,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN160",GROUP_DESC:"TN160 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12034,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TN160",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TN160",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12034,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN160", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12034 ,protection_group_id: -12034, protection_element_id:-12034], primaryKey: false);
      insert('addresses', [ id: 19035, city: "Nashville", state_code: "TN", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19035, nci_institute_code: "TN161", name: "B Stephens Dudley MD PC" ,address_id: 19035,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12035,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN161",GROUP_DESC:"TN161 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12035,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TN161",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TN161",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12035,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TN161", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12035 ,protection_group_id: -12035, protection_element_id:-12035], primaryKey: false);
      insert('addresses', [ id: 19036, country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19036, nci_institute_code: "TPN", name: "Total Parenteral Nutrition Group" ,address_id: 19036,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12036,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TPN",GROUP_DESC:"TPN group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12036,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TPN",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TPN",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12036,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TPN", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12036 ,protection_group_id: -12036, protection_element_id:-12036], primaryKey: false);
      insert('addresses', [ id: 19037, city: "Dallas", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19037, nci_institute_code: "TX002", name: "Dallas VA Medical Center" ,address_id: 19037,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12037,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX002",GROUP_DESC:"TX002 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12037,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX002",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX002",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12037,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX002", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12037 ,protection_group_id: -12037, protection_element_id:-12037], primaryKey: false);
      insert('addresses', [ id: 19038, city: "Dallas", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19038, nci_institute_code: "TX003", name: "Doctors Health Facilities Dba" ,address_id: 19038,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12038,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX003",GROUP_DESC:"TX003 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12038,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX003",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX003",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12038,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX003", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12038 ,protection_group_id: -12038, protection_element_id:-12038], primaryKey: false);
      insert('addresses', [ id: 19039, city: "Dallas", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19039, nci_institute_code: "TX004", name: "Medical City Dallas Hospital" ,address_id: 19039,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12039,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX004",GROUP_DESC:"TX004 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12039,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX004",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX004",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12039,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX004", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12039 ,protection_group_id: -12039, protection_element_id:-12039], primaryKey: false);
      insert('addresses', [ id: 19040, city: "Dallas", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19040, nci_institute_code: "TX005", name: "Presbyterian Hospital of Dallas" ,address_id: 19040,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12040,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX005",GROUP_DESC:"TX005 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12040,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX005",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX005",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12040,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX005", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12040 ,protection_group_id: -12040, protection_element_id:-12040], primaryKey: false);
      insert('addresses', [ id: 19041, city: "Dallas", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19041, nci_institute_code: "TX006", name: "Parkland Memorial Hospital" ,address_id: 19041,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12041,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX006",GROUP_DESC:"TX006 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12041,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX006",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX006",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12041,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX006", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12041 ,protection_group_id: -12041, protection_element_id:-12041], primaryKey: false);
      insert('addresses', [ id: 19042, city: "Dallas", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19042, nci_institute_code: "TX007", name: "Saint Paul Hospital" ,address_id: 19042,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12042,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX007",GROUP_DESC:"TX007 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12042,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX007",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX007",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12042,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX007", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12042 ,protection_group_id: -12042, protection_element_id:-12042], primaryKey: false);
      insert('addresses', [ id: 19043, city: "Fort Worth", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19043, nci_institute_code: "TX008", name: "Harris Methodist" ,address_id: 19043,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12043,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX008",GROUP_DESC:"TX008 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12043,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX008",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX008",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12043,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX008", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12043 ,protection_group_id: -12043, protection_element_id:-12043], primaryKey: false);
      insert('addresses', [ id: 19044, city: "Dallas", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19044, nci_institute_code: "TX009", name: "Childrens Medical Center" ,address_id: 19044,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12044,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX009",GROUP_DESC:"TX009 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12044,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX009",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX009",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12044,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX009", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12044 ,protection_group_id: -12044, protection_element_id:-12044], primaryKey: false);
      insert('addresses', [ id: 19045, city: "Dallas", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19045, nci_institute_code: "TX010", name: "Wadley Institution of Molecular Medicine" ,address_id: 19045,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12045,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX010",GROUP_DESC:"TX010 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12045,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX010",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX010",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12045,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX010", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12045 ,protection_group_id: -12045, protection_element_id:-12045], primaryKey: false);
      insert('addresses', [ id: 19046, city: "Dallas", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19046, nci_institute_code: "TX011", name: "University of Texas Southwestern Medical Center" ,address_id: 19046,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12046,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX011",GROUP_DESC:"TX011 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12046,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX011",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX011",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12046,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX011", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12046 ,protection_group_id: -12046, protection_element_id:-12046], primaryKey: false);
      insert('addresses', [ id: 19047, city: "Dallas", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19047, nci_institute_code: "TX012", name: "Baylor University Medical Center" ,address_id: 19047,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12047,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX012",GROUP_DESC:"TX012 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12047,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX012",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX012",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12047,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX012", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12047 ,protection_group_id: -12047, protection_element_id:-12047], primaryKey: false);
    }

    void m2() {
        // all2 (25 terms)
      insert('addresses', [ id: 19048, city: "Dallas", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19048, nci_institute_code: "TX013", name: "Wadley Research Institute" ,address_id: 19048,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12048,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX013",GROUP_DESC:"TX013 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12048,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX013",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX013",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12048,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX013", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12048 ,protection_group_id: -12048, protection_element_id:-12048], primaryKey: false);
      insert('addresses', [ id: 19049, city: "Dallas", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19049, nci_institute_code: "TX014", name: "Methodist Dallas" ,address_id: 19049,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12049,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX014",GROUP_DESC:"TX014 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12049,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX014",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX014",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12049,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX014", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12049 ,protection_group_id: -12049, protection_element_id:-12049], primaryKey: false);
      insert('addresses', [ id: 19050, city: "Tyler", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19050, nci_institute_code: "TX017", name: "Trinity Mother Francis Hospital" ,address_id: 19050,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12050,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX017",GROUP_DESC:"TX017 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12050,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX017",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX017",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12050,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX017", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12050 ,protection_group_id: -12050, protection_element_id:-12050], primaryKey: false);
      insert('addresses', [ id: 19051, city: "Tyler", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19051, nci_institute_code: "TX018", name: "University of Texas Health Center at Tyler" ,address_id: 19051,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12051,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX018",GROUP_DESC:"TX018 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12051,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX018",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX018",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12051,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX018", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12051 ,protection_group_id: -12051, protection_element_id:-12051], primaryKey: false);
      insert('addresses', [ id: 19052, city: "Nacogdoches", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19052, nci_institute_code: "TX019", name: "Memorial Hospital" ,address_id: 19052,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12052,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX019",GROUP_DESC:"TX019 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12052,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX019",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX019",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12052,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX019", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12052 ,protection_group_id: -12052, protection_element_id:-12052], primaryKey: false);
      insert('addresses', [ id: 19053, city: "Fort Worth", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19053, nci_institute_code: "TX020", name: "Cook Children's Medical Center" ,address_id: 19053,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12053,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX020",GROUP_DESC:"TX020 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12053,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX020",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX020",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12053,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX020", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12053 ,protection_group_id: -12053, protection_element_id:-12053], primaryKey: false);
      insert('addresses', [ id: 19054, city: "Fort Worth", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19054, nci_institute_code: "TX021", name: "Saint Joseph Hospital" ,address_id: 19054,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12054,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX021",GROUP_DESC:"TX021 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12054,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX021",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX021",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12054,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX021", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12054 ,protection_group_id: -12054, protection_element_id:-12054], primaryKey: false);
      insert('addresses', [ id: 19055, city: "Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19055, nci_institute_code: "TX022", name: "MD Anderson Cancer Network" ,address_id: 19055,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12055,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX022",GROUP_DESC:"TX022 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12055,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX022",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX022",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12055,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX022", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12055 ,protection_group_id: -12055, protection_element_id:-12055], primaryKey: false);
      insert('addresses', [ id: 19056, city: "Fort Worth", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19056, nci_institute_code: "TX023", name: "John Peter Smith Hospital" ,address_id: 19056,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12056,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX023",GROUP_DESC:"TX023 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12056,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX023",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX023",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12056,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX023", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12056 ,protection_group_id: -12056, protection_element_id:-12056], primaryKey: false);
      insert('addresses', [ id: 19057, city: "Fort Worth", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19057, nci_institute_code: "TX024", name: "United States Air Force Regional Hospital" ,address_id: 19057,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12057,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX024",GROUP_DESC:"TX024 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12057,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX024",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX024",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12057,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX024", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12057 ,protection_group_id: -12057, protection_element_id:-12057], primaryKey: false);
      insert('addresses', [ id: 19058, city: "Denton", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19058, nci_institute_code: "TX025", name: "Texas Woman's University" ,address_id: 19058,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12058,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX025",GROUP_DESC:"TX025 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12058,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX025",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX025",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12058,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX025", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12058 ,protection_group_id: -12058, protection_element_id:-12058], primaryKey: false);
      insert('addresses', [ id: 19059, city: "Temple", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19059, nci_institute_code: "TX026", name: "Olin E Teague Veterns Administratiom Center" ,address_id: 19059,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12059,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX026",GROUP_DESC:"TX026 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12059,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX026",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX026",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12059,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX026", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12059 ,protection_group_id: -12059, protection_element_id:-12059], primaryKey: false);
      insert('addresses', [ id: 19060, city: "Temple", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19060, nci_institute_code: "TX027", name: "Scott and White Memorial Hospital" ,address_id: 19060,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12060,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX027",GROUP_DESC:"TX027 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12060,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX027",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX027",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12060,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX027", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12060 ,protection_group_id: -12060, protection_element_id:-12060], primaryKey: false);
      insert('addresses', [ id: 19061, city: "Waco", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19061, nci_institute_code: "TX028", name: "Providence Hospital" ,address_id: 19061,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12061,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX028",GROUP_DESC:"TX028 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12061,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX028",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX028",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12061,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX028", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12061 ,protection_group_id: -12061, protection_element_id:-12061], primaryKey: false);
      insert('addresses', [ id: 19062, city: "Waco", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19062, nci_institute_code: "TX029", name: "Hillcrest Baptist Medical Center" ,address_id: 19062,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12062,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX029",GROUP_DESC:"TX029 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12062,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX029",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX029",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12062,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX029", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12062 ,protection_group_id: -12062, protection_element_id:-12062], primaryKey: false);
      insert('addresses', [ id: 19063, city: "San Angelo", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19063, nci_institute_code: "TX030", name: "Shannon Medical Center" ,address_id: 19063,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12063,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX030",GROUP_DESC:"TX030 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12063,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX030",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX030",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12063,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX030", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12063 ,protection_group_id: -12063, protection_element_id:-12063], primaryKey: false);
      insert('addresses', [ id: 19064, city: "Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19064, nci_institute_code: "TX031", name: "Diagnostic Center Hospital" ,address_id: 19064,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12064,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX031",GROUP_DESC:"TX031 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12064,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX031",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX031",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12064,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX031", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12064 ,protection_group_id: -12064, protection_element_id:-12064], primaryKey: false);
      insert('addresses', [ id: 19065, city: "Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19065, nci_institute_code: "TX032", name: "Saint Joseph Medical Center" ,address_id: 19065,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12065,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX032",GROUP_DESC:"TX032 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12065,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX032",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX032",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12065,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX032", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12065 ,protection_group_id: -12065, protection_element_id:-12065], primaryKey: false);
      insert('addresses', [ id: 19066, city: "Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19066, nci_institute_code: "TX033", name: "Stehlin Foundation" ,address_id: 19066,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12066,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX033",GROUP_DESC:"TX033 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12066,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX033",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX033",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12066,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX033", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12066 ,protection_group_id: -12066, protection_element_id:-12066], primaryKey: false);
      insert('addresses', [ id: 19067, city: "Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19067, nci_institute_code: "TX034", name: "Park Plaza Hospital" ,address_id: 19067,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12067,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX034",GROUP_DESC:"TX034 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12067,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX034",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX034",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12067,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX034", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12067 ,protection_group_id: -12067, protection_element_id:-12067], primaryKey: false);
      insert('addresses', [ id: 19068, city: "Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19068, nci_institute_code: "TX035", name: "M D Anderson Cancer Center" ,address_id: 19068,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12068,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX035",GROUP_DESC:"TX035 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12068,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX035",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX035",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12068,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX035", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12068 ,protection_group_id: -12068, protection_element_id:-12068], primaryKey: false);
      insert('addresses', [ id: 19069, city: "Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19069, nci_institute_code: "TX036", name: "Methodist Hospital" ,address_id: 19069,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12069,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX036",GROUP_DESC:"TX036 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12069,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX036",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX036",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12069,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX036", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12069 ,protection_group_id: -12069, protection_element_id:-12069], primaryKey: false);
      insert('addresses', [ id: 19070, city: "Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19070, nci_institute_code: "TX037", name: "Stehlin Foundation for Cancer Research" ,address_id: 19070,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12070,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX037",GROUP_DESC:"TX037 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12070,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX037",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX037",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12070,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX037", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12070 ,protection_group_id: -12070, protection_element_id:-12070], primaryKey: false);
      insert('addresses', [ id: 19071, city: "Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19071, nci_institute_code: "TX038", name: "University Texas System Cancer Center" ,address_id: 19071,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12071,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX038",GROUP_DESC:"TX038 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12071,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX038",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX038",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12071,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX038", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12071 ,protection_group_id: -12071, protection_element_id:-12071], primaryKey: false);
      insert('addresses', [ id: 19072, city: "Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19072, nci_institute_code: "TX039", name: "Memorial Hermann Texas Medical Center" ,address_id: 19072,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12072,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX039",GROUP_DESC:"TX039 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12072,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX039",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX039",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12072,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX039", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12072 ,protection_group_id: -12072, protection_element_id:-12072], primaryKey: false);
    }

    void m3() {
        // all3 (25 terms)
      insert('addresses', [ id: 19073, city: "Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19073, nci_institute_code: "TX040", name: "Texas Children's Hospital" ,address_id: 19073,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12073,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX040",GROUP_DESC:"TX040 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12073,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX040",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX040",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12073,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX040", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12073 ,protection_group_id: -12073, protection_element_id:-12073], primaryKey: false);
      insert('addresses', [ id: 19074, city: "Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19074, nci_institute_code: "TX041", name: "Baylor College of Medicine" ,address_id: 19074,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12074,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX041",GROUP_DESC:"TX041 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12074,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX041",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX041",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12074,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX041", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12074 ,protection_group_id: -12074, protection_element_id:-12074], primaryKey: false);
      insert('addresses', [ id: 19075, city: "Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19075, nci_institute_code: "TX042", name: "Veterans Administration Medical Center" ,address_id: 19075,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12075,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX042",GROUP_DESC:"TX042 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12075,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX042",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX042",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12075,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX042", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12075 ,protection_group_id: -12075, protection_element_id:-12075], primaryKey: false);
      insert('addresses', [ id: 19076, city: "Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19076, nci_institute_code: "TX043", name: "Bellaire General Hospital" ,address_id: 19076,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12076,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX043",GROUP_DESC:"TX043 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12076,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX043",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX043",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12076,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX043", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12076 ,protection_group_id: -12076, protection_element_id:-12076], primaryKey: false);
      insert('addresses', [ id: 19077, city: "Galveston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19077, nci_institute_code: "TX045", name: "University of Texas Medical Branch" ,address_id: 19077,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12077,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX045",GROUP_DESC:"TX045 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12077,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX045",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX045",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12077,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX045", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12077 ,protection_group_id: -12077, protection_element_id:-12077], primaryKey: false);
      insert('addresses', [ id: 19078, city: "Webster", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19078, nci_institute_code: "TX046", name: "Clear Lake Regional Medical Center" ,address_id: 19078,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12078,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX046",GROUP_DESC:"TX046 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12078,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX046",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX046",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12078,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX046", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12078 ,protection_group_id: -12078, protection_element_id:-12078], primaryKey: false);
      insert('addresses', [ id: 19079, city: "College Station", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19079, nci_institute_code: "TX047", name: "Texas A&M" ,address_id: 19079,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12079,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX047",GROUP_DESC:"TX047 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12079,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX047",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX047",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12079,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX047", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12079 ,protection_group_id: -12079, protection_element_id:-12079], primaryKey: false);
      insert('addresses', [ id: 19080, city: "San Antonio", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19080, nci_institute_code: "TX048", name: "Nix Memorial Hospital" ,address_id: 19080,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12080,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX048",GROUP_DESC:"TX048 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12080,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX048",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX048",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12080,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX048", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12080 ,protection_group_id: -12080, protection_element_id:-12080], primaryKey: false);
      insert('addresses', [ id: 19081, city: "San Antonio", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19081, nci_institute_code: "TX049", name: "Robert B. Green Memorial Hospital" ,address_id: 19081,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12081,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX049",GROUP_DESC:"TX049 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12081,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX049",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX049",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12081,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX049", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12081 ,protection_group_id: -12081, protection_element_id:-12081], primaryKey: false);
      insert('addresses', [ id: 19082, city: "San Antonio", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19082, nci_institute_code: "TX050", name: "Audie L. Murphy Veterans Affairs Hospital" ,address_id: 19082,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12082,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX050",GROUP_DESC:"TX050 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12082,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX050",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX050",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12082,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX050", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12082 ,protection_group_id: -12082, protection_element_id:-12082], primaryKey: false);
      insert('addresses', [ id: 19083, city: "San Antonio", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19083, nci_institute_code: "TX051", name: "Saint Lukes Lutheran" ,address_id: 19083,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12083,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX051",GROUP_DESC:"TX051 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12083,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX051",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX051",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12083,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX051", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12083 ,protection_group_id: -12083, protection_element_id:-12083], primaryKey: false);
      insert('addresses', [ id: 19084, city: "San Antonio", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19084, nci_institute_code: "TX052", name: "Methodist Children's Hospital of South Texas" ,address_id: 19084,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12084,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX052",GROUP_DESC:"TX052 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12084,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX052",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX052",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12084,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX052", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12084 ,protection_group_id: -12084, protection_element_id:-12084], primaryKey: false);
      insert('addresses', [ id: 19085, city: "Dallas", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19085, nci_institute_code: "TX053", name: "US Oncology Inc" ,address_id: 19085,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12085,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX053",GROUP_DESC:"TX053 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12085,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX053",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX053",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12085,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX053", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12085 ,protection_group_id: -12085, protection_element_id:-12085], primaryKey: false);
      insert('addresses', [ id: 19086, city: "Lubbock", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19086, nci_institute_code: "TX054", name: "Covenant Medical Center-Lakeside" ,address_id: 19086,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12086,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX054",GROUP_DESC:"TX054 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12086,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX054",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX054",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12086,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX054", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12086 ,protection_group_id: -12086, protection_element_id:-12086], primaryKey: false);
      insert('addresses', [ id: 19087, city: "Fort Sam Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19087, nci_institute_code: "TX055", name: "Brooke Army Medical Center" ,address_id: 19087,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12087,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX055",GROUP_DESC:"TX055 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12087,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX055",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX055",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12087,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX055", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12087 ,protection_group_id: -12087, protection_element_id:-12087], primaryKey: false);
      insert('addresses', [ id: 19088, city: "Lackland AFB", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19088, nci_institute_code: "TX056", name: "Wilford Hall Medical Center" ,address_id: 19088,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12088,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX056",GROUP_DESC:"TX056 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12088,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX056",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX056",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12088,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX056", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12088 ,protection_group_id: -12088, protection_element_id:-12088], primaryKey: false);
      insert('addresses', [ id: 19089, city: "Midland", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19089, nci_institute_code: "TX058", name: "Memorial Hospital & Medical Center" ,address_id: 19089,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12089,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX058",GROUP_DESC:"TX058 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12089,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX058",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX058",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12089,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX058", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12089 ,protection_group_id: -12089, protection_element_id:-12089], primaryKey: false);
      insert('addresses', [ id: 19090, city: "San Antonio", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19090, nci_institute_code: "TX059", name: "University of Texas Health Science Center" ,address_id: 19090,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12090,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX059",GROUP_DESC:"TX059 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12090,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX059",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX059",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12090,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX059", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12090 ,protection_group_id: -12090, protection_element_id:-12090], primaryKey: false);
      insert('addresses', [ id: 19091, city: "San Antonio", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19091, nci_institute_code: "TX060", name: "Santa Rosa Health Care" ,address_id: 19091,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12091,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX060",GROUP_DESC:"TX060 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12091,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX060",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX060",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12091,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX060", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12091 ,protection_group_id: -12091, protection_element_id:-12091], primaryKey: false);
      insert('addresses', [ id: 19092, city: "San Antonio", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19092, nci_institute_code: "TX061", name: "Childrens Hospital" ,address_id: 19092,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12092,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX061",GROUP_DESC:"TX061 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12092,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX061",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX061",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12092,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX061", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12092 ,protection_group_id: -12092, protection_element_id:-12092], primaryKey: false);
      insert('addresses', [ id: 19093, city: "Corpus Christi", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19093, nci_institute_code: "TX062", name: "Memorial Medical Center Hospital" ,address_id: 19093,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12093,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX062",GROUP_DESC:"TX062 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12093,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX062",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX062",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12093,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX062", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12093 ,protection_group_id: -12093, protection_element_id:-12093], primaryKey: false);
      insert('addresses', [ id: 19094, city: "Austin", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19094, nci_institute_code: "TX063", name: "Brackenridge Hospital" ,address_id: 19094,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12094,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX063",GROUP_DESC:"TX063 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12094,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX063",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX063",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12094,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX063", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12094 ,protection_group_id: -12094, protection_element_id:-12094], primaryKey: false);
      insert('addresses', [ id: 19095, city: "Austin", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19095, nci_institute_code: "TX064", name: "Holy Cross Hospital" ,address_id: 19095,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12095,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX064",GROUP_DESC:"TX064 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12095,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX064",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX064",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12095,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX064", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12095 ,protection_group_id: -12095, protection_element_id:-12095], primaryKey: false);
      insert('addresses', [ id: 19096, city: "Amarillo", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19096, nci_institute_code: "TX066", name: "High Plains Baptist Hospital" ,address_id: 19096,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12096,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX066",GROUP_DESC:"TX066 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12096,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX066",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX066",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12096,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX066", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12096 ,protection_group_id: -12096, protection_element_id:-12096], primaryKey: false);
      insert('addresses', [ id: 19097, city: "Amarillo", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19097, nci_institute_code: "TX067", name: "Texas Tech University Health Science Center-Amarillo" ,address_id: 19097,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12097,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX067",GROUP_DESC:"TX067 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12097,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX067",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX067",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12097,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX067", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12097 ,protection_group_id: -12097, protection_element_id:-12097], primaryKey: false);
    }

    void m4() {
        // all4 (25 terms)
      insert('addresses', [ id: 19098, city: "Amarillo", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19098, nci_institute_code: "TX068", name: "The Don and Sybil Harrington Cancer Center" ,address_id: 19098,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12098,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX068",GROUP_DESC:"TX068 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12098,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX068",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX068",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12098,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX068", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12098 ,protection_group_id: -12098, protection_element_id:-12098], primaryKey: false);
      insert('addresses', [ id: 19099, city: "Lubbock", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19099, nci_institute_code: "TX070", name: "University Medical Center" ,address_id: 19099,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12099,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX070",GROUP_DESC:"TX070 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12099,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX070",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX070",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12099,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX070", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12099 ,protection_group_id: -12099, protection_element_id:-12099], primaryKey: false);
      insert('addresses', [ id: 19100, city: "Lubbock", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19100, nci_institute_code: "TX071", name: "Texas Tech University Health Sciences Center" ,address_id: 19100,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12100,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX071",GROUP_DESC:"TX071 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12100,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX071",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX071",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12100,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX071", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12100 ,protection_group_id: -12100, protection_element_id:-12100], primaryKey: false);
      insert('addresses', [ id: 19101, city: "Abilene", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19101, nci_institute_code: "TX072", name: "Hendrick Medical Center" ,address_id: 19101,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12101,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX072",GROUP_DESC:"TX072 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12101,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX072",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX072",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12101,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX072", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12101 ,protection_group_id: -12101, protection_element_id:-12101], primaryKey: false);
      insert('addresses', [ id: 19102, city: "Odessa", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19102, nci_institute_code: "TX073", name: "Medical Center Hospital" ,address_id: 19102,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12102,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX073",GROUP_DESC:"TX073 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12102,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX073",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX073",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12102,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX073", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12102 ,protection_group_id: -12102, protection_element_id:-12102], primaryKey: false);
      insert('addresses', [ id: 19103, city: "El Paso", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19103, nci_institute_code: "TX076", name: "Texas Technology University" ,address_id: 19103,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12103,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX076",GROUP_DESC:"TX076 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12103,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX076",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX076",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12103,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX076", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12103 ,protection_group_id: -12103, protection_element_id:-12103], primaryKey: false);
      insert('addresses', [ id: 19104, city: "El Paso", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19104, nci_institute_code: "TX077", name: "William Beaumont Army Medical Center" ,address_id: 19104,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12104,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX077",GROUP_DESC:"TX077 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12104,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX077",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX077",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12104,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX077", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12104 ,protection_group_id: -12104, protection_element_id:-12104], primaryKey: false);
      insert('addresses', [ id: 19105, city: "Corpus Christi", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19105, nci_institute_code: "TX079", name: "Driscoll Children's Hospital" ,address_id: 19105,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12105,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX079",GROUP_DESC:"TX079 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12105,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX079",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX079",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12105,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX079", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12105 ,protection_group_id: -12105, protection_element_id:-12105], primaryKey: false);
      insert('addresses', [ id: 19106, city: "Tyler", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19106, nci_institute_code: "TX080", name: "East Texas Medical Center" ,address_id: 19106,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12106,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX080",GROUP_DESC:"TX080 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12106,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX080",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX080",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12106,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX080", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12106 ,protection_group_id: -12106, protection_element_id:-12106], primaryKey: false);
      insert('addresses', [ id: 19107, city: "Bedford", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19107, nci_institute_code: "TX081", name: "Osteopathic Medical Center" ,address_id: 19107,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12107,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX081",GROUP_DESC:"TX081 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12107,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX081",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX081",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12107,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX081", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12107 ,protection_group_id: -12107, protection_element_id:-12107], primaryKey: false);
      insert('addresses', [ id: 19108, city: "Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19108, nci_institute_code: "TX082", name: "Memorial Hosp-Memorial City" ,address_id: 19108,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12108,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX082",GROUP_DESC:"TX082 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12108,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX082",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX082",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12108,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX082", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12108 ,protection_group_id: -12108, protection_element_id:-12108], primaryKey: false);
      insert('addresses', [ id: 19109, city: "Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19109, nci_institute_code: "TX083", name: "Kelsey-Seybold Clinic" ,address_id: 19109,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12109,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX083",GROUP_DESC:"TX083 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12109,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX083",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX083",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12109,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX083", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12109 ,protection_group_id: -12109, protection_element_id:-12109], primaryKey: false);
      insert('addresses', [ id: 19110, city: "Tyler", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19110, nci_institute_code: "TX084", name: "Tyler Hematology/Oncology" ,address_id: 19110,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12110,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX084",GROUP_DESC:"TX084 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12110,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX084",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX084",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12110,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX084", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12110 ,protection_group_id: -12110, protection_element_id:-12110], primaryKey: false);
      insert('addresses', [ id: 19111, city: "San Antonio", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19111, nci_institute_code: "TX085", name: "Baptist Memorial Hospital" ,address_id: 19111,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12111,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX085",GROUP_DESC:"TX085 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12111,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX085",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX085",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12111,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX085", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12111 ,protection_group_id: -12111, protection_element_id:-12111], primaryKey: false);
      insert('addresses', [ id: 19112, city: "Beaumont", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19112, nci_institute_code: "TX086", name: "Saint Elizabeth Hospital" ,address_id: 19112,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12112,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX086",GROUP_DESC:"TX086 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12112,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX086",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX086",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12112,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX086", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12112 ,protection_group_id: -12112, protection_element_id:-12112], primaryKey: false);
      insert('addresses', [ id: 19113, city: "Wharton", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19113, nci_institute_code: "TX087", name: "South Texas Medical Clinics" ,address_id: 19113,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12113,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX087",GROUP_DESC:"TX087 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12113,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX087",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX087",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12113,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX087", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12113 ,protection_group_id: -12113, protection_element_id:-12113], primaryKey: false);
      insert('addresses', [ id: 19114, city: "Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19114, nci_institute_code: "TX088", name: "Ben Taub General Hospital" ,address_id: 19114,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12114,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX088",GROUP_DESC:"TX088 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12114,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX088",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX088",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12114,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX088", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12114 ,protection_group_id: -12114, protection_element_id:-12114], primaryKey: false);
      insert('addresses', [ id: 19115, city: "Dallas", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19115, nci_institute_code: "TX089", name: "Kaiser Permanente" ,address_id: 19115,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12115,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX089",GROUP_DESC:"TX089 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12115,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX089",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX089",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12115,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX089", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12115 ,protection_group_id: -12115, protection_element_id:-12115], primaryKey: false);
      insert('addresses', [ id: 19116, city: "Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19116, nci_institute_code: "TX090", name: "Medical Center Hospital" ,address_id: 19116,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12116,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX090",GROUP_DESC:"TX090 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12116,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX090",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX090",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12116,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX090", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12116 ,protection_group_id: -12116, protection_element_id:-12116], primaryKey: false);
      insert('addresses', [ id: 19117, city: "Victoria", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19117, nci_institute_code: "TX091", name: "Citizens Medical Center" ,address_id: 19117,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12117,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX091",GROUP_DESC:"TX091 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12117,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX091",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX091",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12117,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX091", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12117 ,protection_group_id: -12117, protection_element_id:-12117], primaryKey: false);
      insert('addresses', [ id: 19118, city: "Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19118, nci_institute_code: "TX092", name: "Memorial Hermann Southwest Hospital" ,address_id: 19118,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12118,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX092",GROUP_DESC:"TX092 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12118,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX092",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX092",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12118,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX092", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12118 ,protection_group_id: -12118, protection_element_id:-12118], primaryKey: false);
      insert('addresses', [ id: 19119, city: "Harlingen", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19119, nci_institute_code: "TX093", name: "Valley Baptist Medical Center - Harlingen" ,address_id: 19119,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12119,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX093",GROUP_DESC:"TX093 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12119,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX093",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX093",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12119,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX093", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12119 ,protection_group_id: -12119, protection_element_id:-12119], primaryKey: false);
      insert('addresses', [ id: 19120, city: "Lake Jackson", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19120, nci_institute_code: "TX094", name: "Brazosport Regional Health System" ,address_id: 19120,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12120,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX094",GROUP_DESC:"TX094 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12120,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX094",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX094",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12120,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX094", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12120 ,protection_group_id: -12120, protection_element_id:-12120], primaryKey: false);
      insert('addresses', [ id: 19121, city: "Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19121, nci_institute_code: "TX095", name: "University of Texas Medical School" ,address_id: 19121,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12121,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX095",GROUP_DESC:"TX095 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12121,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX095",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX095",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12121,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX095", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12121 ,protection_group_id: -12121, protection_element_id:-12121], primaryKey: false);
      insert('addresses', [ id: 19122, city: "Arlington", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19122, nci_institute_code: "TX096", name: "Arlington Cancer Center" ,address_id: 19122,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12122,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX096",GROUP_DESC:"TX096 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12122,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX096",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX096",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12122,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX096", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12122 ,protection_group_id: -12122, protection_element_id:-12122], primaryKey: false);
    }

    void m5() {
        // all5 (25 terms)
      insert('addresses', [ id: 19123, city: "San Antonio", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19123, nci_institute_code: "TX098", name: "Cancer Therapy and Research Center" ,address_id: 19123,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12123,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX098",GROUP_DESC:"TX098 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12123,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX098",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX098",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12123,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX098", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12123 ,protection_group_id: -12123, protection_element_id:-12123], primaryKey: false);
      insert('addresses', [ id: 19124, city: "Austin", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19124, nci_institute_code: "TX099", name: "Dell Children's Medical Center of Central Texas" ,address_id: 19124,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12124,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX099",GROUP_DESC:"TX099 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12124,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX099",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX099",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12124,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX099", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12124 ,protection_group_id: -12124, protection_element_id:-12124], primaryKey: false);
      insert('addresses', [ id: 19125, city: "Lubbock", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19125, nci_institute_code: "TX100", name: "Covenant Children's Hospital" ,address_id: 19125,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12125,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX100",GROUP_DESC:"TX100 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12125,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX100",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX100",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12125,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX100", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12125 ,protection_group_id: -12125, protection_element_id:-12125], primaryKey: false);
      insert('addresses', [ id: 19126, city: "Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19126, nci_institute_code: "TX101", name: "Saint Luke's Episcopal Hospital" ,address_id: 19126,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12126,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX101",GROUP_DESC:"TX101 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12126,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX101",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX101",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12126,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX101", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12126 ,protection_group_id: -12126, protection_element_id:-12126], primaryKey: false);
      insert('addresses', [ id: 19127, city: "Fort Worth", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19127, nci_institute_code: "TX102", name: "All Saints Episcopal" ,address_id: 19127,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12127,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX102",GROUP_DESC:"TX102 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12127,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX102",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX102",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12127,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX102", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12127 ,protection_group_id: -12127, protection_element_id:-12127], primaryKey: false);
      insert('addresses', [ id: 19128, city: "El Paso", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19128, nci_institute_code: "TX103", name: "Providence Memorial Hospital" ,address_id: 19128,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12128,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX103",GROUP_DESC:"TX103 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12128,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX103",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX103",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12128,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX103", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12128 ,protection_group_id: -12128, protection_element_id:-12128], primaryKey: false);
      insert('addresses', [ id: 19129, city: "Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19129, nci_institute_code: "TX104", name: "Houston Cancer Institute" ,address_id: 19129,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12129,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX104",GROUP_DESC:"TX104 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12129,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX104",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX104",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12129,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX104", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12129 ,protection_group_id: -12129, protection_element_id:-12129], primaryKey: false);
      insert('addresses', [ id: 19130, city: "Amarillo", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19130, nci_institute_code: "TX105", name: "Northwest Texas Hospitals" ,address_id: 19130,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12130,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX105",GROUP_DESC:"TX105 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12130,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX105",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX105",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12130,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX105", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12130 ,protection_group_id: -12130, protection_element_id:-12130], primaryKey: false);
      insert('addresses', [ id: 19131, city: "El Paso", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19131, nci_institute_code: "TX107", name: "Columbia Medical Center" ,address_id: 19131,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12131,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX107",GROUP_DESC:"TX107 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12131,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX107",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX107",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12131,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX107", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12131 ,protection_group_id: -12131, protection_element_id:-12131], primaryKey: false);
      insert('addresses', [ id: 19132, city: "Longview", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19132, nci_institute_code: "TX108", name: "Longview Cancer Center" ,address_id: 19132,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12132,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX108",GROUP_DESC:"TX108 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12132,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX108",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX108",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12132,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX108", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12132 ,protection_group_id: -12132, protection_element_id:-12132], primaryKey: false);
      insert('addresses', [ id: 19133, city: "Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19133, nci_institute_code: "TX109", name: "Spring Branch Medical Center" ,address_id: 19133,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12133,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX109",GROUP_DESC:"TX109 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12133,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX109",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX109",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12133,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX109", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12133 ,protection_group_id: -12133, protection_element_id:-12133], primaryKey: false);
      insert('addresses', [ id: 19134, city: "Victoria", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19134, nci_institute_code: "TX110", name: "Detar Hospital" ,address_id: 19134,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12134,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX110",GROUP_DESC:"TX110 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12134,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX110",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX110",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12134,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX110", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12134 ,protection_group_id: -12134, protection_element_id:-12134], primaryKey: false);
      insert('addresses', [ id: 19135, city: "Humble", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19135, nci_institute_code: "TX111", name: "Northeast Medical Center Hospital" ,address_id: 19135,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12135,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX111",GROUP_DESC:"TX111 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12135,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX111",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX111",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12135,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX111", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12135 ,protection_group_id: -12135, protection_element_id:-12135], primaryKey: false);
      insert('addresses', [ id: 19136, city: "San Antonio", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19136, nci_institute_code: "TX112", name: "San Antonio CCOP" ,address_id: 19136,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12136,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX112",GROUP_DESC:"TX112 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12136,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX112",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX112",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12136,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX112", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12136 ,protection_group_id: -12136, protection_element_id:-12136], primaryKey: false);
      insert('addresses', [ id: 19137, city: "El Paso", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19137, nci_institute_code: "TX113", name: "Sierra Medical Center" ,address_id: 19137,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12137,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX113",GROUP_DESC:"TX113 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12137,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX113",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX113",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12137,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX113", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12137 ,protection_group_id: -12137, protection_element_id:-12137], primaryKey: false);
      insert('addresses', [ id: 19138, city: "Bedford", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19138, nci_institute_code: "TX115", name: "Edwards Cancer Center" ,address_id: 19138,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12138,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX115",GROUP_DESC:"TX115 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12138,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX115",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX115",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12138,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX115", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12138 ,protection_group_id: -12138, protection_element_id:-12138], primaryKey: false);
      insert('addresses', [ id: 19139, city: "El Paso", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19139, nci_institute_code: "TX116", name: "El Paso Ear Nose Throat Association" ,address_id: 19139,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12139,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX116",GROUP_DESC:"TX116 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12139,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX116",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX116",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12139,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX116", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12139 ,protection_group_id: -12139, protection_element_id:-12139], primaryKey: false);
      insert('addresses', [ id: 19140, city: "Austin", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19140, nci_institute_code: "TX117", name: "Central Texas Oncology Associates" ,address_id: 19140,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12140,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX117",GROUP_DESC:"TX117 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12140,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX117",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX117",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12140,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX117", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12140 ,protection_group_id: -12140, protection_element_id:-12140], primaryKey: false);
      insert('addresses', [ id: 19141, city: "San Antonio", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19141, nci_institute_code: "TX118", name: "Hematology Oncology Association of San Antonio Texas" ,address_id: 19141,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12141,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX118",GROUP_DESC:"TX118 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12141,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX118",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX118",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12141,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX118", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12141 ,protection_group_id: -12141, protection_element_id:-12141], primaryKey: false);
      insert('addresses', [ id: 19142, city: "Austin", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19142, nci_institute_code: "TX120", name: "The Stratum" ,address_id: 19142,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12142,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX120",GROUP_DESC:"TX120 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12142,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX120",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX120",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12142,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX120", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12142 ,protection_group_id: -12142, protection_element_id:-12142], primaryKey: false);
      insert('addresses', [ id: 19143, city: "Austin", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19143, nci_institute_code: "TX121", name: "Texas Oncology - South Austin Cancer Center" ,address_id: 19143,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12143,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX121",GROUP_DESC:"TX121 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12143,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX121",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX121",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12143,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX121", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12143 ,protection_group_id: -12143, protection_element_id:-12143], primaryKey: false);
      insert('addresses', [ id: 19144, city: "Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19144, nci_institute_code: "TX122", name: "Memorial Hospital Southwest" ,address_id: 19144,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12144,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX122",GROUP_DESC:"TX122 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12144,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX122",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX122",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12144,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX122", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12144 ,protection_group_id: -12144, protection_element_id:-12144], primaryKey: false);
      insert('addresses', [ id: 19145, city: "Wichita Falls", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19145, nci_institute_code: "TX123", name: "Wichita Falls Clinic" ,address_id: 19145,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12145,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX123",GROUP_DESC:"TX123 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12145,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX123",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX123",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12145,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX123", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12145 ,protection_group_id: -12145, protection_element_id:-12145], primaryKey: false);
      insert('addresses', [ id: 19146, city: "Abilene", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19146, nci_institute_code: "TX124", name: "Texas Cancer Center-Abilene" ,address_id: 19146,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12146,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX124",GROUP_DESC:"TX124 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12146,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX124",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX124",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12146,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX124", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12146 ,protection_group_id: -12146, protection_element_id:-12146], primaryKey: false);
      insert('addresses', [ id: 19147, city: "San Antonio", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19147, nci_institute_code: "TX125", name: "Diagnostic Clinic of San Antonio" ,address_id: 19147,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12147,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX125",GROUP_DESC:"TX125 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12147,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX125",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX125",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12147,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX125", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12147 ,protection_group_id: -12147, protection_element_id:-12147], primaryKey: false);
    }

    void m6() {
        // all6 (25 terms)
      insert('addresses', [ id: 19148, city: "Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19148, nci_institute_code: "TX126", name: "Oncology Medical Associates" ,address_id: 19148,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12148,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX126",GROUP_DESC:"TX126 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12148,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX126",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX126",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12148,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX126", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12148 ,protection_group_id: -12148, protection_element_id:-12148], primaryKey: false);
      insert('addresses', [ id: 19149, city: "Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19149, nci_institute_code: "TX127", name: "Diagnostic Clinic of Houston" ,address_id: 19149,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12149,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX127",GROUP_DESC:"TX127 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12149,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX127",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX127",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12149,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX127", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12149 ,protection_group_id: -12149, protection_element_id:-12149], primaryKey: false);
      insert('addresses', [ id: 19150, city: "San Antonio", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19150, nci_institute_code: "TX128", name: "Urologic Surgical Association PA" ,address_id: 19150,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12150,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX128",GROUP_DESC:"TX128 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12150,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX128",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX128",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12150,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX128", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12150 ,protection_group_id: -12150, protection_element_id:-12150], primaryKey: false);
      insert('addresses', [ id: 19151, city: "San Antonio", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19151, nci_institute_code: "TX129", name: "San Antonio Gastroenterology Assoc., Pa" ,address_id: 19151,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12151,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX129",GROUP_DESC:"TX129 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12151,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX129",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX129",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12151,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX129", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12151 ,protection_group_id: -12151, protection_element_id:-12151], primaryKey: false);
      insert('addresses', [ id: 19152, city: "Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19152, nci_institute_code: "TX130", name: "Oncology Consultants, P.A." ,address_id: 19152,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12152,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX130",GROUP_DESC:"TX130 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12152,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX130",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX130",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12152,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX130", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12152 ,protection_group_id: -12152, protection_element_id:-12152], primaryKey: false);
      insert('addresses', [ id: 19153, city: "Fort Worth", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19153, nci_institute_code: "TX131", name: "Texas Oncology , P.A." ,address_id: 19153,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12153,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX131",GROUP_DESC:"TX131 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12153,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX131",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX131",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12153,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX131", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12153 ,protection_group_id: -12153, protection_element_id:-12153], primaryKey: false);
      insert('addresses', [ id: 19154, city: "Irving", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19154, nci_institute_code: "TX132", name: "Texas Oncology PA" ,address_id: 19154,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12154,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX132",GROUP_DESC:"TX132 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12154,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX132",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX132",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12154,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX132", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12154 ,protection_group_id: -12154, protection_element_id:-12154], primaryKey: false);
      insert('addresses', [ id: 19155, city: "El Paso", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19155, nci_institute_code: "TX133", name: "El Paso Cancer Treatment Center" ,address_id: 19155,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12155,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX133",GROUP_DESC:"TX133 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12155,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX133",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX133",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12155,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX133", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12155 ,protection_group_id: -12155, protection_element_id:-12155], primaryKey: false);
      insert('addresses', [ id: 19156, city: "Odessa", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19156, nci_institute_code: "TX134", name: "West Texas Cancer Center" ,address_id: 19156,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12156,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX134",GROUP_DESC:"TX134 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12156,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX134",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX134",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12156,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX134", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12156 ,protection_group_id: -12156, protection_element_id:-12156], primaryKey: false);
      insert('addresses', [ id: 19157, city: "Fort Worth", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19157, nci_institute_code: "TX135", name: "Tarrant County Oncology Alliance" ,address_id: 19157,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12157,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX135",GROUP_DESC:"TX135 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12157,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX135",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX135",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12157,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX135", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12157 ,protection_group_id: -12157, protection_element_id:-12157], primaryKey: false);
      insert('addresses', [ id: 19158, city: "Dallas", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19158, nci_institute_code: "TX136", name: "Southeast Dallas Health Center" ,address_id: 19158,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12158,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX136",GROUP_DESC:"TX136 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12158,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX136",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX136",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12158,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX136", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12158 ,protection_group_id: -12158, protection_element_id:-12158], primaryKey: false);
      insert('addresses', [ id: 19159, city: "Austin", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19159, nci_institute_code: "TX137", name: "Southwest Regional Cancer Center" ,address_id: 19159,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12159,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX137",GROUP_DESC:"TX137 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12159,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX137",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX137",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12159,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX137", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12159 ,protection_group_id: -12159, protection_element_id:-12159], primaryKey: false);
      insert('addresses', [ id: 19160, city: "Dallas", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19160, nci_institute_code: "TX138", name: "Algur H. Meadows Imaging Center" ,address_id: 19160,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12160,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX138",GROUP_DESC:"TX138 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12160,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX138",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX138",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12160,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX138", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12160 ,protection_group_id: -12160, protection_element_id:-12160], primaryKey: false);
      insert('addresses', [ id: 19161, city: "El Paso", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19161, nci_institute_code: "TX139", name: "West Texas Blood Cancer Center., Ste B & C" ,address_id: 19161,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12161,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX139",GROUP_DESC:"TX139 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12161,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX139",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX139",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12161,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX139", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12161 ,protection_group_id: -12161, protection_element_id:-12161], primaryKey: false);
      insert('addresses', [ id: 19162, city: "San Antonio", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19162, nci_institute_code: "TX140", name: "US Texas Pediatric CCOP" ,address_id: 19162,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12162,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX140",GROUP_DESC:"TX140 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12162,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX140",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX140",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12162,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX140", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12162 ,protection_group_id: -12162, protection_element_id:-12162], primaryKey: false);
      insert('addresses', [ id: 19163, city: "Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19163, nci_institute_code: "TX141", name: "Houston Northwest Medical Center" ,address_id: 19163,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12163,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX141",GROUP_DESC:"TX141 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12163,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX141",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX141",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12163,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX141", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12163 ,protection_group_id: -12163, protection_element_id:-12163], primaryKey: false);
      insert('addresses', [ id: 19164, city: "Plano", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19164, nci_institute_code: "TX142", name: "Columbia Medical Center of Plano" ,address_id: 19164,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12164,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX142",GROUP_DESC:"TX142 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12164,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX142",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX142",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12164,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX142", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12164 ,protection_group_id: -12164, protection_element_id:-12164], primaryKey: false);
      insert('addresses', [ id: 19165, city: "Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19165, nci_institute_code: "TX143", name: "Macgregor Medical Association" ,address_id: 19165,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12165,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX143",GROUP_DESC:"TX143 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12165,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX143",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX143",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12165,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX143", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12165 ,protection_group_id: -12165, protection_element_id:-12165], primaryKey: false);
      insert('addresses', [ id: 19166, city: "Waco", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19166, nci_institute_code: "TX144", name: "Waco Medcial Group" ,address_id: 19166,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12166,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX144",GROUP_DESC:"TX144 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12166,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX144",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX144",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12166,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX144", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12166 ,protection_group_id: -12166, protection_element_id:-12166], primaryKey: false);
      insert('addresses', [ id: 19167, city: "Dallas", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19167, nci_institute_code: "TX145", name: "Pediatric Surgical Associates" ,address_id: 19167,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12167,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX145",GROUP_DESC:"TX145 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12167,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX145",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX145",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12167,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX145", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12167 ,protection_group_id: -12167, protection_element_id:-12167], primaryKey: false);
      insert('addresses', [ id: 19168, city: "Sherman", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19168, nci_institute_code: "TX146", name: "Texas Cancer Center-Sherman" ,address_id: 19168,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12168,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX146",GROUP_DESC:"TX146 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12168,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX146",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX146",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12168,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX146", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12168 ,protection_group_id: -12168, protection_element_id:-12168], primaryKey: false);
      insert('addresses', [ id: 19169, city: "Pasadena", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19169, nci_institute_code: "TX147", name: "Bayshore Cancer Center" ,address_id: 19169,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12169,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX147",GROUP_DESC:"TX147 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12169,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX147",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX147",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12169,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX147", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12169 ,protection_group_id: -12169, protection_element_id:-12169], primaryKey: false);
      insert('addresses', [ id: 19170, city: "Lubbock", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19170, nci_institute_code: "TX149", name: "Oncology Hematology Assoc" ,address_id: 19170,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12170,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX149",GROUP_DESC:"TX149 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12170,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX149",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX149",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12170,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX149", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12170 ,protection_group_id: -12170, protection_element_id:-12170], primaryKey: false);
      insert('addresses', [ id: 19171, city: "Fort Worth", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19171, nci_institute_code: "TX150", name: "Mocrief Cancer Center-The University of Texas Southwestern Medical Center" ,address_id: 19171,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12171,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX150",GROUP_DESC:"TX150 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12171,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX150",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX150",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12171,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX150", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12171 ,protection_group_id: -12171, protection_element_id:-12171], primaryKey: false);
      insert('addresses', [ id: 19172, city: "Fort Worth", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19172, nci_institute_code: "TX151", name: "Plaza Medical Center" ,address_id: 19172,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12172,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX151",GROUP_DESC:"TX151 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12172,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX151",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX151",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12172,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX151", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12172 ,protection_group_id: -12172, protection_element_id:-12172], primaryKey: false);
    }

    void m7() {
        // all7 (25 terms)
      insert('addresses', [ id: 19173, city: "Beaumont", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19173, nci_institute_code: "TX152", name: "Texas Oncology PA Beaumont" ,address_id: 19173,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12173,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX152",GROUP_DESC:"TX152 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12173,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX152",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX152",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12173,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX152", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12173 ,protection_group_id: -12173, protection_element_id:-12173], primaryKey: false);
      insert('addresses', [ id: 19174, city: "Plano", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19174, nci_institute_code: "TX153", name: "North Texas Regional Cancer Center" ,address_id: 19174,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12174,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX153",GROUP_DESC:"TX153 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12174,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX153",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX153",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12174,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX153", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12174 ,protection_group_id: -12174, protection_element_id:-12174], primaryKey: false);
      insert('addresses', [ id: 19175, city: "Tyler", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19175, nci_institute_code: "TX154", name: "Tyler Cancer Center" ,address_id: 19175,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12175,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX154",GROUP_DESC:"TX154 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12175,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX154",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX154",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12175,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX154", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12175 ,protection_group_id: -12175, protection_element_id:-12175], primaryKey: false);
      insert('addresses', [ id: 19176, city: "El Paso", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19176, nci_institute_code: "TX155", name: "Texas Oncology, El Paso" ,address_id: 19176,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12176,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX155",GROUP_DESC:"TX155 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12176,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX155",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX155",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12176,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX155", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12176 ,protection_group_id: -12176, protection_element_id:-12176], primaryKey: false);
      insert('addresses', [ id: 19177, city: "Midland", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19177, nci_institute_code: "TX157", name: "Allison Cancer Center" ,address_id: 19177,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12177,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX157",GROUP_DESC:"TX157 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12177,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX157",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX157",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12177,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX157", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12177 ,protection_group_id: -12177, protection_element_id:-12177], primaryKey: false);
      insert('addresses', [ id: 19178, city: "San Antonio", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19178, nci_institute_code: "TX158", name: "CHRISTUS Santa Rosa Childrens Hospital" ,address_id: 19178,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12178,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX158",GROUP_DESC:"TX158 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12178,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX158",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX158",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12178,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX158", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12178 ,protection_group_id: -12178, protection_element_id:-12178], primaryKey: false);
      insert('addresses', [ id: 19179, city: "Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19179, nci_institute_code: "TX159", name: "Texas Oncology, P.A." ,address_id: 19179,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12179,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX159",GROUP_DESC:"TX159 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12179,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX159",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX159",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12179,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX159", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12179 ,protection_group_id: -12179, protection_element_id:-12179], primaryKey: false);
      insert('addresses', [ id: 19180, city: "Dallas", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19180, nci_institute_code: "TX162", name: "Simmons Cancer Center" ,address_id: 19180,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12180,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX162",GROUP_DESC:"TX162 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12180,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX162",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX162",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12180,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX162", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12180 ,protection_group_id: -12180, protection_element_id:-12180], primaryKey: false);
      insert('addresses', [ id: 19181, city: "Frederickburg", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19181, nci_institute_code: "TX163", name: "Hill County Memorial Hospital" ,address_id: 19181,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12181,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX163",GROUP_DESC:"TX163 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12181,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX163",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX163",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12181,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX163", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12181 ,protection_group_id: -12181, protection_element_id:-12181], primaryKey: false);
      insert('addresses', [ id: 19182, city: "Laredo", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19182, nci_institute_code: "TX164", name: "Elmer Pacheco" ,address_id: 19182,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12182,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX164",GROUP_DESC:"TX164 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12182,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX164",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX164",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12182,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX164", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12182 ,protection_group_id: -12182, protection_element_id:-12182], primaryKey: false);
      insert('addresses', [ id: 19183, city: "Bryan", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19183, nci_institute_code: "TX165", name: "Saint Joseph Regional Cancer Center" ,address_id: 19183,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12183,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX165",GROUP_DESC:"TX165 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12183,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX165",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX165",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12183,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX165", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12183 ,protection_group_id: -12183, protection_element_id:-12183], primaryKey: false);
      insert('addresses', [ id: 19184, city: "Beaumont", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19184, nci_institute_code: "TX166", name: "Baptist Hospital South East Texas" ,address_id: 19184,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12184,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX166",GROUP_DESC:"TX166 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12184,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX166",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX166",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12184,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX166", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12184 ,protection_group_id: -12184, protection_element_id:-12184], primaryKey: false);
      insert('addresses', [ id: 19185, city: "Dallas", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19185, nci_institute_code: "TX167", name: "Veterans Affairs Medical Center Dallas" ,address_id: 19185,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12185,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX167",GROUP_DESC:"TX167 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12185,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX167",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX167",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12185,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX167", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12185 ,protection_group_id: -12185, protection_element_id:-12185], primaryKey: false);
      insert('addresses', [ id: 19186, city: "Dallas", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19186, nci_institute_code: "TX169", name: "The Cancer Center Associates" ,address_id: 19186,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12186,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX169",GROUP_DESC:"TX169 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12186,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX169",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX169",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12186,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX169", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12186 ,protection_group_id: -12186, protection_element_id:-12186], primaryKey: false);
      insert('addresses', [ id: 19187, city: "San Antonio", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19187, nci_institute_code: "TX170", name: "Children's Hematology & Oncology Center" ,address_id: 19187,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12187,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX170",GROUP_DESC:"TX170 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12187,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX170",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX170",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12187,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX170", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12187 ,protection_group_id: -12187, protection_element_id:-12187], primaryKey: false);
      insert('addresses', [ id: 19188, city: "Fort Worth", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19188, nci_institute_code: "TX171", name: "Texas College of Osteo" ,address_id: 19188,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12188,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX171",GROUP_DESC:"TX171 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12188,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX171",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX171",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12188,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX171", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12188 ,protection_group_id: -12188, protection_element_id:-12188], primaryKey: false);
      insert('addresses', [ id: 19189, city: "Dallas", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19189, nci_institute_code: "TX173", name: "University of Texas Health Science Center" ,address_id: 19189,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12189,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX173",GROUP_DESC:"TX173 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12189,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX173",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX173",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12189,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX173", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12189 ,protection_group_id: -12189, protection_element_id:-12189], primaryKey: false);
      insert('addresses', [ id: 19190, city: "Austin", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19190, nci_institute_code: "TX174", name: "Seton Medical Center" ,address_id: 19190,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12190,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX174",GROUP_DESC:"TX174 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12190,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX174",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX174",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12190,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX174", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12190 ,protection_group_id: -12190, protection_element_id:-12190], primaryKey: false);
      insert('addresses', [ id: 19191, city: "Denton", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19191, nci_institute_code: "TX175", name: "Hca Denton Regional Medical Center" ,address_id: 19191,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12191,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX175",GROUP_DESC:"TX175 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12191,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX175",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX175",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12191,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX175", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12191 ,protection_group_id: -12191, protection_element_id:-12191], primaryKey: false);
      insert('addresses', [ id: 19192, city: "Abilene", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19192, nci_institute_code: "TX176", name: "Texas Cancer Center" ,address_id: 19192,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12192,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX176",GROUP_DESC:"TX176 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12192,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX176",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX176",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12192,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX176", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12192 ,protection_group_id: -12192, protection_element_id:-12192], primaryKey: false);
      insert('addresses', [ id: 19193, city: "Lufkin", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19193, nci_institute_code: "TX177", name: "Memorial Health System of East Texas/Arthur Temple Sr. Regional Cancer Center" ,address_id: 19193,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12193,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX177",GROUP_DESC:"TX177 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12193,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX177",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX177",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12193,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX177", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12193 ,protection_group_id: -12193, protection_element_id:-12193], primaryKey: false);
      insert('addresses', [ id: 19194, city: "Dallas", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19194, nci_institute_code: "TX179", name: "Baylor Institute for Immunology Research" ,address_id: 19194,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12194,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX179",GROUP_DESC:"TX179 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12194,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX179",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX179",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12194,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX179", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12194 ,protection_group_id: -12194, protection_element_id:-12194], primaryKey: false);
      insert('addresses', [ id: 19195, city: "San Antonio", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19195, nci_institute_code: "TX180", name: "Baptist Health System" ,address_id: 19195,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12195,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX180",GROUP_DESC:"TX180 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12195,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX180",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX180",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12195,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX180", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12195 ,protection_group_id: -12195, protection_element_id:-12195], primaryKey: false);
      insert('addresses', [ id: 19196, city: "Lubbock", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19196, nci_institute_code: "TX181", name: "Covenant Medical Center" ,address_id: 19196,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12196,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX181",GROUP_DESC:"TX181 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12196,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX181",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX181",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12196,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX181", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12196 ,protection_group_id: -12196, protection_element_id:-12196], primaryKey: false);
      insert('addresses', [ id: 19197, city: "San Antonio", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19197, nci_institute_code: "TX182", name: "University Hospital" ,address_id: 19197,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12197,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX182",GROUP_DESC:"TX182 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12197,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX182",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX182",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12197,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX182", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12197 ,protection_group_id: -12197, protection_element_id:-12197], primaryKey: false);
    }

    void m8() {
        // all8 (25 terms)
      insert('addresses', [ id: 19198, city: "Garland", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19198, nci_institute_code: "TX183", name: "Baylor Medical Center" ,address_id: 19198,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12198,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX183",GROUP_DESC:"TX183 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12198,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX183",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX183",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12198,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX183", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12198 ,protection_group_id: -12198, protection_element_id:-12198], primaryKey: false);
      insert('addresses', [ id: 19199, city: "Richardson", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19199, nci_institute_code: "TX184", name: "Richardson Regional Medical Center" ,address_id: 19199,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12199,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX184",GROUP_DESC:"TX184 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12199,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX184",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX184",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12199,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX184", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12199 ,protection_group_id: -12199, protection_element_id:-12199], primaryKey: false);
      insert('addresses', [ id: 19200, city: "Irving", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19200, nci_institute_code: "TX185", name: "Baylor Medical Center at Irving" ,address_id: 19200,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12200,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX185",GROUP_DESC:"TX185 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12200,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX185",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX185",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12200,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX185", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12200 ,protection_group_id: -12200, protection_element_id:-12200], primaryKey: false);
      insert('addresses', [ id: 19201, city: "Corpus Christi", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19201, nci_institute_code: "TX186", name: "Christus Spohn Hospital South" ,address_id: 19201,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12201,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX186",GROUP_DESC:"TX186 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12201,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX186",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX186",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12201,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX186", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12201 ,protection_group_id: -12201, protection_element_id:-12201], primaryKey: false);
      insert('addresses', [ id: 19202, city: "Austin", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19202, nci_institute_code: "TX187", name: "Saint David's Medical Center" ,address_id: 19202,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12202,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX187",GROUP_DESC:"TX187 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12202,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX187",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX187",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12202,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX187", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12202 ,protection_group_id: -12202, protection_element_id:-12202], primaryKey: false);
      insert('addresses', [ id: 19203, city: "San Antonio", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19203, nci_institute_code: "TX188", name: "Pediatric Hematology and Oncology Associates of South Texas" ,address_id: 19203,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12203,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX188",GROUP_DESC:"TX188 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12203,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX188",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX188",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12203,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX188", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12203 ,protection_group_id: -12203, protection_element_id:-12203], primaryKey: false);
      insert('addresses', [ id: 19204, city: "San Antonio", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19204, nci_institute_code: "TX189", name: "South Texas Oncology and Hematology PA" ,address_id: 19204,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12204,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX189",GROUP_DESC:"TX189 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12204,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX189",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX189",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12204,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX189", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12204 ,protection_group_id: -12204, protection_element_id:-12204], primaryKey: false);
      insert('addresses', [ id: 19205, city: "Laredo", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19205, nci_institute_code: "TX190", name: "Doctor's Hospital of Laredo" ,address_id: 19205,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12205,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX190",GROUP_DESC:"TX190 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12205,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX190",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX190",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12205,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX190", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12205 ,protection_group_id: -12205, protection_element_id:-12205], primaryKey: false);
      insert('addresses', [ id: 19206, city: "Lubbock", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19206, nci_institute_code: "TX191", name: "Lubbock Diagnostic Clinic" ,address_id: 19206,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12206,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX191",GROUP_DESC:"TX191 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12206,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX191",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX191",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12206,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX191", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12206 ,protection_group_id: -12206, protection_element_id:-12206], primaryKey: false);
      insert('addresses', [ id: 19207, city: "Forth Worth", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19207, nci_institute_code: "TX192", name: "University of North Texas Health Science Center" ,address_id: 19207,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12207,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX192",GROUP_DESC:"TX192 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12207,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX192",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX192",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12207,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX192", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12207 ,protection_group_id: -12207, protection_element_id:-12207], primaryKey: false);
      insert('addresses', [ id: 19208, city: "College Station", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19208, nci_institute_code: "TX193", name: "Hope Cancer Center" ,address_id: 19208,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12208,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX193",GROUP_DESC:"TX193 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12208,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX193",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX193",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12208,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX193", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12208 ,protection_group_id: -12208, protection_element_id:-12208], primaryKey: false);
      insert('addresses', [ id: 19209, city: "Forth Worth", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19209, nci_institute_code: "TX194", name: "Dba Texas Cancer Care" ,address_id: 19209,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12209,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX194",GROUP_DESC:"TX194 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12209,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX194",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX194",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12209,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX194", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12209 ,protection_group_id: -12209, protection_element_id:-12209], primaryKey: false);
      insert('addresses', [ id: 19210, city: "Dallas", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19210, nci_institute_code: "TX195", name: "Zale Lipshy University Hospital" ,address_id: 19210,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12210,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX195",GROUP_DESC:"TX195 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12210,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX195",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX195",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12210,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX195", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12210 ,protection_group_id: -12210, protection_element_id:-12210], primaryKey: false);
      insert('addresses', [ id: 19211, city: "San Antonio", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19211, nci_institute_code: "TX197", name: "San Antonio Tumor and Blood Clinic" ,address_id: 19211,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12211,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX197",GROUP_DESC:"TX197 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12211,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX197",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX197",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12211,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX197", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12211 ,protection_group_id: -12211, protection_element_id:-12211], primaryKey: false);
      insert('addresses', [ id: 19212, city: "Fort Worth", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19212, nci_institute_code: "TX198", name: "The Center for Cancer and Blood Disorders-Fort Worth" ,address_id: 19212,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12212,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX198",GROUP_DESC:"TX198 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12212,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX198",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX198",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12212,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX198", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12212 ,protection_group_id: -12212, protection_element_id:-12212], primaryKey: false);
      insert('addresses', [ id: 19213, city: "Denton", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19213, nci_institute_code: "TX199", name: "Denton Regional Medical Center" ,address_id: 19213,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12213,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX199",GROUP_DESC:"TX199 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12213,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX199",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX199",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12213,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX199", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12213 ,protection_group_id: -12213, protection_element_id:-12213], primaryKey: false);
      insert('addresses', [ id: 19214, city: "El Paso", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19214, nci_institute_code: "TX200", name: "Las Palmas Regional Oncology" ,address_id: 19214,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12214,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX200",GROUP_DESC:"TX200 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12214,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX200",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX200",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12214,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX200", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12214 ,protection_group_id: -12214, protection_element_id:-12214], primaryKey: false);
      insert('addresses', [ id: 19215, city: "EI Paso", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19215, nci_institute_code: "TX201", name: "Las Palmas Hospital" ,address_id: 19215,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12215,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX201",GROUP_DESC:"TX201 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12215,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX201",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX201",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12215,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX201", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12215 ,protection_group_id: -12215, protection_element_id:-12215], primaryKey: false);
      insert('addresses', [ id: 19216, city: "San Antonio", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19216, nci_institute_code: "TX202", name: "Saint Luke's Baptist Health System" ,address_id: 19216,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12216,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX202",GROUP_DESC:"TX202 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12216,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX202",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX202",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12216,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX202", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12216 ,protection_group_id: -12216, protection_element_id:-12216], primaryKey: false);
      insert('addresses', [ id: 19217, city: "Bedford", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19217, nci_institute_code: "TX203", name: "Harris Methodist Southwest" ,address_id: 19217,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12217,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX203",GROUP_DESC:"TX203 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12217,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX203",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX203",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12217,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX203", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12217 ,protection_group_id: -12217, protection_element_id:-12217], primaryKey: false);
      insert('addresses', [ id: 19218, city: "Austin", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19218, nci_institute_code: "TX205", name: "Lone Star Oncology Consultants, PA" ,address_id: 19218,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12218,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX205",GROUP_DESC:"TX205 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12218,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX205",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX205",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12218,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX205", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12218 ,protection_group_id: -12218, protection_element_id:-12218], primaryKey: false);
      insert('addresses', [ id: 19219, city: "Laredo", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19219, nci_institute_code: "TX206", name: "Laredo Medical Center" ,address_id: 19219,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12219,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX206",GROUP_DESC:"TX206 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12219,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX206",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX206",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12219,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX206", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12219 ,protection_group_id: -12219, protection_element_id:-12219], primaryKey: false);
      insert('addresses', [ id: 19220, city: "Harlingen", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19220, nci_institute_code: "TX207", name: "South Texas Cancer Center" ,address_id: 19220,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12220,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX207",GROUP_DESC:"TX207 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12220,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX207",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX207",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12220,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX207", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12220 ,protection_group_id: -12220, protection_element_id:-12220], primaryKey: false);
      insert('addresses', [ id: 19221, city: "Waco", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19221, nci_institute_code: "TX208", name: "Waco Cancer Care/Res Ctr/Providence Hosp" ,address_id: 19221,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12221,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX208",GROUP_DESC:"TX208 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12221,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX208",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX208",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12221,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX208", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12221 ,protection_group_id: -12221, protection_element_id:-12221], primaryKey: false);
      insert('addresses', [ id: 19222, city: "Texas City", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19222, nci_institute_code: "TX209", name: "Beeler-Manske Clinic" ,address_id: 19222,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12222,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX209",GROUP_DESC:"TX209 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12222,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX209",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX209",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12222,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX209", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12222 ,protection_group_id: -12222, protection_element_id:-12222], primaryKey: false);
    }

    void m9() {
        // all9 (25 terms)
      insert('addresses', [ id: 19223, city: "Dallas", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19223, nci_institute_code: "TX210", name: "Harold C. Simmons Cancer Center" ,address_id: 19223,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12223,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX210",GROUP_DESC:"TX210 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12223,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX210",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX210",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12223,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX210", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12223 ,protection_group_id: -12223, protection_element_id:-12223], primaryKey: false);
      insert('addresses', [ id: 19224, city: "San Antonio", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19224, nci_institute_code: "TX211", name: "Bexar County Medical Hem. & Onc. Specialists, P.A." ,address_id: 19224,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12224,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX211",GROUP_DESC:"TX211 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12224,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX211",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX211",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12224,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX211", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12224 ,protection_group_id: -12224, protection_element_id:-12224], primaryKey: false);
      insert('addresses', [ id: 19225, city: "Denton", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19225, nci_institute_code: "TX212", name: "Network Cancer Care" ,address_id: 19225,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12225,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX212",GROUP_DESC:"TX212 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12225,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX212",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX212",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12225,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX212", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12225 ,protection_group_id: -12225, protection_element_id:-12225], primaryKey: false);
      insert('addresses', [ id: 19226, city: "San Antonio", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19226, nci_institute_code: "TX213", name: "Cancer Care Centers of South Texas - Village Drive South" ,address_id: 19226,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12226,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX213",GROUP_DESC:"TX213 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12226,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX213",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX213",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12226,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX213", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12226 ,protection_group_id: -12226, protection_element_id:-12226], primaryKey: false);
      insert('addresses', [ id: 19227, city: "Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19227, nci_institute_code: "TX214", name: "Flight Medicine Clinic" ,address_id: 19227,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12227,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX214",GROUP_DESC:"TX214 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12227,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX214",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX214",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12227,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX214", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12227 ,protection_group_id: -12227, protection_element_id:-12227], primaryKey: false);
      insert('addresses', [ id: 19228, city: "Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19228, nci_institute_code: "TX215", name: "Thomas Street Clinic" ,address_id: 19228,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12228,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX215",GROUP_DESC:"TX215 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12228,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX215",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX215",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12228,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX215", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12228 ,protection_group_id: -12228, protection_element_id:-12228], primaryKey: false);
      insert('addresses', [ id: 19229, city: "Fort Worth", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19229, nci_institute_code: "TX217", name: "University of Texas Southwest Medical Center" ,address_id: 19229,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12229,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX217",GROUP_DESC:"TX217 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12229,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX217",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX217",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12229,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX217", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12229 ,protection_group_id: -12229, protection_element_id:-12229], primaryKey: false);
      insert('addresses', [ id: 19230, city: "Dallas", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19230, nci_institute_code: "TX218", name: "Southwest Urology Associates" ,address_id: 19230,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12230,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX218",GROUP_DESC:"TX218 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12230,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX218",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX218",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12230,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX218", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12230 ,protection_group_id: -12230, protection_element_id:-12230], primaryKey: false);
      insert('addresses', [ id: 19231, city: "Galveston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19231, nci_institute_code: "TX219", name: "University of Texas Medical Branch-Family Medicine" ,address_id: 19231,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12231,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX219",GROUP_DESC:"TX219 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12231,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX219",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX219",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12231,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX219", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12231 ,protection_group_id: -12231, protection_element_id:-12231], primaryKey: false);
      insert('addresses', [ id: 19232, city: "Fort Worth", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19232, nci_institute_code: "TX220", name: "Urology Clinics of North Texas" ,address_id: 19232,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12232,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX220",GROUP_DESC:"TX220 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12232,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX220",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX220",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12232,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX220", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12232 ,protection_group_id: -12232, protection_element_id:-12232], primaryKey: false);
      insert('addresses', [ id: 19233, city: "San Antonio", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19233, nci_institute_code: "TX221", name: "South Texas Oncology and Hematology, P. A." ,address_id: 19233,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12233,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX221",GROUP_DESC:"TX221 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12233,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX221",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX221",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12233,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX221", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12233 ,protection_group_id: -12233, protection_element_id:-12233], primaryKey: false);
      insert('addresses', [ id: 19234, city: "Laredo", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19234, nci_institute_code: "TX222", name: "Doctors Regional Cancer Treatment Center" ,address_id: 19234,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12234,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX222",GROUP_DESC:"TX222 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12234,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX222",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX222",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12234,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX222", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12234 ,protection_group_id: -12234, protection_element_id:-12234], primaryKey: false);
      insert('addresses', [ id: 19235, city: "San Antonio", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19235, nci_institute_code: "TX223", name: "Texas Prostate Branchtherapy Services" ,address_id: 19235,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12235,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX223",GROUP_DESC:"TX223 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12235,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX223",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX223",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12235,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX223", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12235 ,protection_group_id: -12235, protection_element_id:-12235], primaryKey: false);
      insert('addresses', [ id: 19236, city: "Dallas", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19236, nci_institute_code: "TX224", name: "Dallas Surgical Group" ,address_id: 19236,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12236,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX224",GROUP_DESC:"TX224 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12236,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX224",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX224",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12236,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX224", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12236 ,protection_group_id: -12236, protection_element_id:-12236], primaryKey: false);
      insert('addresses', [ id: 19237, city: "Dallas", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19237, nci_institute_code: "TX225", name: "Center for Oncology Research and Treatment" ,address_id: 19237,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12237,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX225",GROUP_DESC:"TX225 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12237,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX225",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX225",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12237,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX225", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12237 ,protection_group_id: -12237, protection_element_id:-12237], primaryKey: false);
      insert('addresses', [ id: 19238, city: "Temple", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19238, nci_institute_code: "TX226", name: "Temple Cancer Clinic" ,address_id: 19238,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12238,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX226",GROUP_DESC:"TX226 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12238,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX226",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX226",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12238,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX226", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12238 ,protection_group_id: -12238, protection_element_id:-12238], primaryKey: false);
      insert('addresses', [ id: 19239, city: "Lubbock", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19239, nci_institute_code: "TX227", name: "Covenant Medical Arts Clinic" ,address_id: 19239,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12239,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX227",GROUP_DESC:"TX227 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12239,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX227",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX227",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12239,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX227", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12239 ,protection_group_id: -12239, protection_element_id:-12239], primaryKey: false);
      insert('addresses', [ id: 19240, city: "San Antonio", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19240, nci_institute_code: "TX228", name: "Texas Transplant Institute" ,address_id: 19240,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12240,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX228",GROUP_DESC:"TX228 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12240,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX228",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX228",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12240,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX228", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12240 ,protection_group_id: -12240, protection_element_id:-12240], primaryKey: false);
      insert('addresses', [ id: 19241, city: "Lubbock", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19241, nci_institute_code: "TX229", name: "West Texas Surgical Associates" ,address_id: 19241,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12241,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX229",GROUP_DESC:"TX229 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12241,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX229",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX229",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12241,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX229", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12241 ,protection_group_id: -12241, protection_element_id:-12241], primaryKey: false);
      insert('addresses', [ id: 19242, city: "San Antonio", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19242, nci_institute_code: "TX230", name: "South Texas Oncology & Hematology, P.A." ,address_id: 19242,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12242,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX230",GROUP_DESC:"TX230 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12242,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX230",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX230",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12242,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX230", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12242 ,protection_group_id: -12242, protection_element_id:-12242], primaryKey: false);
      insert('addresses', [ id: 19243, city: "McAllen", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19243, nci_institute_code: "TX231", name: "Vannie Cook Children's Clinic" ,address_id: 19243,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12243,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX231",GROUP_DESC:"TX231 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12243,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX231",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX231",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12243,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX231", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12243 ,protection_group_id: -12243, protection_element_id:-12243], primaryKey: false);
      insert('addresses', [ id: 19244, city: "McAllen", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19244, nci_institute_code: "TX232", name: "Valley Oncology Pharmacy" ,address_id: 19244,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12244,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX232",GROUP_DESC:"TX232 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12244,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX232",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX232",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12244,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX232", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12244 ,protection_group_id: -12244, protection_element_id:-12244], primaryKey: false);
      insert('addresses', [ id: 19245, city: "Austin", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19245, nci_institute_code: "TX233", name: "Capital Surgeons Group, PLLC" ,address_id: 19245,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12245,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX233",GROUP_DESC:"TX233 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12245,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX233",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX233",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12245,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX233", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12245 ,protection_group_id: -12245, protection_element_id:-12245], primaryKey: false);
      insert('addresses', [ id: 19246, city: "Irving", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19246, nci_institute_code: "TX234", name: "Las Colinas Hematology/Oncology" ,address_id: 19246,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12246,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX234",GROUP_DESC:"TX234 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12246,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX234",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX234",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12246,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX234", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12246 ,protection_group_id: -12246, protection_element_id:-12246], primaryKey: false);
      insert('addresses', [ id: 19247, city: "Dallas", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19247, nci_institute_code: "TX235", name: "Methodist Charlton Medical Center" ,address_id: 19247,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12247,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX235",GROUP_DESC:"TX235 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12247,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX235",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX235",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12247,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX235", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12247 ,protection_group_id: -12247, protection_element_id:-12247], primaryKey: false);
    }

    void m10() {
        // all10 (25 terms)
      insert('addresses', [ id: 19248, city: "Lubbock", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19248, nci_institute_code: "TX236", name: "Covenant Children's Hospital" ,address_id: 19248,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12248,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX236",GROUP_DESC:"TX236 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12248,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX236",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX236",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12248,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX236", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12248 ,protection_group_id: -12248, protection_element_id:-12248], primaryKey: false);
      insert('addresses', [ id: 19249, city: "Amarillo", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19249, nci_institute_code: "TX237", name: "Amarillo Veterans Administration Health Care System" ,address_id: 19249,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12249,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX237",GROUP_DESC:"TX237 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12249,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX237",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX237",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12249,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX237", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12249 ,protection_group_id: -12249, protection_element_id:-12249], primaryKey: false);
      insert('addresses', [ id: 19250, city: "San Antonio", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19250, nci_institute_code: "TX238", name: "Cancer Care Centers of South Texas - Stone Oak" ,address_id: 19250,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12250,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX238",GROUP_DESC:"TX238 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12250,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX238",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX238",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12250,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX238", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12250 ,protection_group_id: -12250, protection_element_id:-12250], primaryKey: false);
      insert('addresses', [ id: 19251, city: "Beaumont", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19251, nci_institute_code: "TX239", name: "Julie and Ben Rogers Cancer Institute" ,address_id: 19251,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12251,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX239",GROUP_DESC:"TX239 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12251,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX239",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX239",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12251,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX239", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12251 ,protection_group_id: -12251, protection_element_id:-12251], primaryKey: false);
      insert('addresses', [ id: 19252, city: "Texarkana", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19252, nci_institute_code: "TX240", name: "Collom & Carney Clinic" ,address_id: 19252,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12252,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX240",GROUP_DESC:"TX240 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12252,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX240",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX240",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12252,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX240", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12252 ,protection_group_id: -12252, protection_element_id:-12252], primaryKey: false);
      insert('addresses', [ id: 19253, city: "Wichita Falls", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19253, nci_institute_code: "TX241", name: "Texas Oncology PA" ,address_id: 19253,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12253,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX241",GROUP_DESC:"TX241 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12253,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX241",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX241",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12253,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX241", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12253 ,protection_group_id: -12253, protection_element_id:-12253], primaryKey: false);
      insert('addresses', [ id: 19254, city: "San Antonio", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19254, nci_institute_code: "TX242", name: "San Antonio Pediatric Surgical Associates" ,address_id: 19254,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12254,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX242",GROUP_DESC:"TX242 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12254,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX242",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX242",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12254,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX242", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12254 ,protection_group_id: -12254, protection_element_id:-12254], primaryKey: false);
      insert('addresses', [ id: 19255, city: "Dallas", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19255, nci_institute_code: "TX243", name: "Dallas Oncology Consultants" ,address_id: 19255,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12255,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX243",GROUP_DESC:"TX243 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12255,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX243",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX243",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12255,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX243", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12255 ,protection_group_id: -12255, protection_element_id:-12255], primaryKey: false);
      insert('addresses', [ id: 19256, city: "Corpus Christi", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19256, nci_institute_code: "TX244", name: "Coastal Ben Oncology" ,address_id: 19256,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12256,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX244",GROUP_DESC:"TX244 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12256,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX244",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX244",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12256,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX244", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12256 ,protection_group_id: -12256, protection_element_id:-12256], primaryKey: false);
      insert('addresses', [ id: 19257, city: "Tyler", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19257, nci_institute_code: "TX245", name: "Blood and Cancer Center of East Texas" ,address_id: 19257,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12257,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX245",GROUP_DESC:"TX245 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12257,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX245",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX245",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12257,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX245", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12257 ,protection_group_id: -12257, protection_element_id:-12257], primaryKey: false);
      insert('addresses', [ id: 19258, city: "Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19258, nci_institute_code: "TX246", name: "Salem Oncology Centre" ,address_id: 19258,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12258,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX246",GROUP_DESC:"TX246 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12258,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX246",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX246",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12258,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX246", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12258 ,protection_group_id: -12258, protection_element_id:-12258], primaryKey: false);
      insert('addresses', [ id: 19259, city: "Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19259, nci_institute_code: "TX247", name: "Houston Northwest Radiotherapy Center, LLC" ,address_id: 19259,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12259,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX247",GROUP_DESC:"TX247 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12259,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX247",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX247",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12259,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX247", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12259 ,protection_group_id: -12259, protection_element_id:-12259], primaryKey: false);
      insert('addresses', [ id: 19260, city: "El Paso", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19260, nci_institute_code: "TX248", name: "The Center for Integrative Cancer Medicine" ,address_id: 19260,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12260,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX248",GROUP_DESC:"TX248 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12260,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX248",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX248",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12260,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX248", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12260 ,protection_group_id: -12260, protection_element_id:-12260], primaryKey: false);
      insert('addresses', [ id: 19261, city: "Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19261, nci_institute_code: "TX249", name: "Southeast Texas Oncology Partners" ,address_id: 19261,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12261,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX249",GROUP_DESC:"TX249 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12261,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX249",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX249",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12261,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX249", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12261 ,protection_group_id: -12261, protection_element_id:-12261], primaryKey: false);
      insert('addresses', [ id: 19262, city: "College Station", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19262, nci_institute_code: "TX250", name: "Cancer Physicians Associated" ,address_id: 19262,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12262,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX250",GROUP_DESC:"TX250 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12262,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX250",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX250",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12262,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX250", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12262 ,protection_group_id: -12262, protection_element_id:-12262], primaryKey: false);
      insert('addresses', [ id: 19263, city: "Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19263, nci_institute_code: "TX251", name: "Synergos, Inc." ,address_id: 19263,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12263,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX251",GROUP_DESC:"TX251 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12263,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX251",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX251",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12263,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX251", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12263 ,protection_group_id: -12263, protection_element_id:-12263], primaryKey: false);
      insert('addresses', [ id: 19264, city: "Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19264, nci_institute_code: "TX252", name: "University of Texas" ,address_id: 19264,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12264,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX252",GROUP_DESC:"TX252 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12264,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX252",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX252",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12264,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX252", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12264 ,protection_group_id: -12264, protection_element_id:-12264], primaryKey: false);
      insert('addresses', [ id: 19265, city: "Dallas", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19265, nci_institute_code: "TX253", name: "North Texas Colen and Rectal" ,address_id: 19265,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12265,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX253",GROUP_DESC:"TX253 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12265,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX253",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX253",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12265,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX253", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12265 ,protection_group_id: -12265, protection_element_id:-12265], primaryKey: false);
      insert('addresses', [ id: 19266, city: "Fort Worth", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19266, nci_institute_code: "TX254", name: "Texas Pulmonary and Critical Care Consultants PA" ,address_id: 19266,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12266,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX254",GROUP_DESC:"TX254 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12266,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX254",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX254",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12266,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX254", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12266 ,protection_group_id: -12266, protection_element_id:-12266], primaryKey: false);
      insert('addresses', [ id: 19267, city: "San Antonio", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19267, nci_institute_code: "TX255", name: "South Texas Oncology and Hematology, P.A." ,address_id: 19267,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12267,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX255",GROUP_DESC:"TX255 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12267,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX255",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX255",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12267,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX255", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12267 ,protection_group_id: -12267, protection_element_id:-12267], primaryKey: false);
      insert('addresses', [ id: 19268, city: "Grapevine", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19268, nci_institute_code: "TX256", name: "Baylor Regional Medical Center At Grapevine" ,address_id: 19268,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12268,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX256",GROUP_DESC:"TX256 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12268,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX256",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX256",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12268,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX256", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12268 ,protection_group_id: -12268, protection_element_id:-12268], primaryKey: false);
      insert('addresses', [ id: 19269, city: "Baytown", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19269, nci_institute_code: "TX257", name: "San Jacinto Cancer Center" ,address_id: 19269,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12269,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX257",GROUP_DESC:"TX257 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12269,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX257",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX257",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12269,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX257", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12269 ,protection_group_id: -12269, protection_element_id:-12269], primaryKey: false);
      insert('addresses', [ id: 19270, city: "Round Rock", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19270, nci_institute_code: "TX258", name: "Texas Oncology - Round Rock Cancer Center" ,address_id: 19270,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12270,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX258",GROUP_DESC:"TX258 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12270,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX258",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX258",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12270,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX258", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12270 ,protection_group_id: -12270, protection_element_id:-12270], primaryKey: false);
      insert('addresses', [ id: 19271, city: "Dallas", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19271, nci_institute_code: "TX259", name: "Texas Cancer Center Dallas Southwest" ,address_id: 19271,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12271,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX259",GROUP_DESC:"TX259 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12271,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX259",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX259",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12271,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX259", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12271 ,protection_group_id: -12271, protection_element_id:-12271], primaryKey: false);
      insert('addresses', [ id: 19272, city: "San Antonio", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19272, nci_institute_code: "TX260", name: "Texas Cancer Clinic" ,address_id: 19272,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12272,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX260",GROUP_DESC:"TX260 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12272,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX260",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX260",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12272,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX260", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12272 ,protection_group_id: -12272, protection_element_id:-12272], primaryKey: false);
    }

    void m11() {
        // all11 (25 terms)
      insert('addresses', [ id: 19273, city: "Laredo", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19273, nci_institute_code: "TX261", name: "Medical Oncology Hematology of South Texas" ,address_id: 19273,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12273,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX261",GROUP_DESC:"TX261 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12273,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX261",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX261",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12273,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX261", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12273 ,protection_group_id: -12273, protection_element_id:-12273], primaryKey: false);
      insert('addresses', [ id: 19274, city: "Fredericksburg", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19274, nci_institute_code: "TX262", name: "Cancer Care Centers of South Texas - Fredericksburg" ,address_id: 19274,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12274,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX262",GROUP_DESC:"TX262 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12274,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX262",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX262",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12274,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX262", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12274 ,protection_group_id: -12274, protection_element_id:-12274], primaryKey: false);
      insert('addresses', [ id: 19275, city: "San Antonio", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19275, nci_institute_code: "TX263", name: "South Texas Urology & Urologic Oncology, PA" ,address_id: 19275,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12275,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX263",GROUP_DESC:"TX263 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12275,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX263",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX263",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12275,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX263", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12275 ,protection_group_id: -12275, protection_element_id:-12275], primaryKey: false);
      insert('addresses', [ id: 19276, city: "Waco", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19276, nci_institute_code: "TX264", name: "Texas Oncology, PA" ,address_id: 19276,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12276,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX264",GROUP_DESC:"TX264 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12276,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX264",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX264",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12276,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX264", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12276 ,protection_group_id: -12276, protection_element_id:-12276], primaryKey: false);
      insert('addresses', [ id: 19277, city: "Dallas", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19277, nci_institute_code: "TX265", name: "Dallas Oncology Consultants, PA" ,address_id: 19277,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12277,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX265",GROUP_DESC:"TX265 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12277,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX265",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX265",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12277,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX265", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12277 ,protection_group_id: -12277, protection_element_id:-12277], primaryKey: false);
      insert('addresses', [ id: 19278, city: "San Antonio", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19278, nci_institute_code: "TX266", name: "Cancer Care Centers of South Texas - Village Drive North" ,address_id: 19278,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12278,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX266",GROUP_DESC:"TX266 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12278,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX266",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX266",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12278,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX266", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12278 ,protection_group_id: -12278, protection_element_id:-12278], primaryKey: false);
      insert('addresses', [ id: 19279, city: "Dallas", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19279, nci_institute_code: "TX267", name: "Texas Oncology PA" ,address_id: 19279,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12279,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX267",GROUP_DESC:"TX267 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12279,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX267",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX267",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12279,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX267", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12279 ,protection_group_id: -12279, protection_element_id:-12279], primaryKey: false);
      insert('addresses', [ id: 19280, city: "Webster", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19280, nci_institute_code: "TX268", name: "Deke Slayton Cancer Center" ,address_id: 19280,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12280,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX268",GROUP_DESC:"TX268 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12280,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX268",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX268",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12280,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX268", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12280 ,protection_group_id: -12280, protection_element_id:-12280], primaryKey: false);
      insert('addresses', [ id: 19281, city: "Dallas", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19281, nci_institute_code: "TX269", name: "General and Oncology Surgery Associates" ,address_id: 19281,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12281,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX269",GROUP_DESC:"TX269 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12281,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX269",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX269",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12281,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX269", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12281 ,protection_group_id: -12281, protection_element_id:-12281], primaryKey: false);
      insert('addresses', [ id: 19282, city: "Dallas", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19282, nci_institute_code: "TX270", name: "The Surgical Institute" ,address_id: 19282,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12282,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX270",GROUP_DESC:"TX270 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12282,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX270",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX270",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12282,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX270", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12282 ,protection_group_id: -12282, protection_element_id:-12282], primaryKey: false);
      insert('addresses', [ id: 19283, city: "Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19283, nci_institute_code: "TX271", name: "The Methodist Hospital Breast Care Center" ,address_id: 19283,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12283,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX271",GROUP_DESC:"TX271 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12283,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX271",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX271",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12283,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX271", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12283 ,protection_group_id: -12283, protection_element_id:-12283], primaryKey: false);
      insert('addresses', [ id: 19284, city: "Austin", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19284, nci_institute_code: "TX272", name: "Children's Ambulatory Blood and Cancer Center" ,address_id: 19284,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12284,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX272",GROUP_DESC:"TX272 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12284,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX272",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX272",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12284,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX272", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12284 ,protection_group_id: -12284, protection_element_id:-12284], primaryKey: false);
      insert('addresses', [ id: 19285, city: "San Antonio", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19285, nci_institute_code: "TX273", name: "Urology San Antonio Research PA" ,address_id: 19285,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12285,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX273",GROUP_DESC:"TX273 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12285,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX273",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX273",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12285,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX273", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12285 ,protection_group_id: -12285, protection_element_id:-12285], primaryKey: false);
      insert('addresses', [ id: 19286, city: "Garland", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19286, nci_institute_code: "TX274", name: "Dallas Cancer Specialists PA" ,address_id: 19286,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12286,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX274",GROUP_DESC:"TX274 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12286,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX274",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX274",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12286,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX274", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12286 ,protection_group_id: -12286, protection_element_id:-12286], primaryKey: false);
      insert('addresses', [ id: 19287, city: "Fort Worth", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19287, nci_institute_code: "TX275", name: "Texas Oncology PA" ,address_id: 19287,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12287,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX275",GROUP_DESC:"TX275 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12287,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX275",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX275",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12287,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX275", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12287 ,protection_group_id: -12287, protection_element_id:-12287], primaryKey: false);
      insert('addresses', [ id: 19288, city: "Mesquite", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19288, nci_institute_code: "TX276", name: "Texas Cancer Center - Mesquite" ,address_id: 19288,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12288,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX276",GROUP_DESC:"TX276 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12288,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX276",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX276",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12288,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX276", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12288 ,protection_group_id: -12288, protection_element_id:-12288], primaryKey: false);
      insert('addresses', [ id: 19289, city: "Dallas", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19289, nci_institute_code: "TX277", name: "Dallas Fort Worth Sarcoma Group P A" ,address_id: 19289,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12289,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX277",GROUP_DESC:"TX277 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12289,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX277",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX277",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12289,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX277", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12289 ,protection_group_id: -12289, protection_element_id:-12289], primaryKey: false);
      insert('addresses', [ id: 19290, city: "Dallas", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19290, nci_institute_code: "TX278", name: "Texas Cancer Center - Medical City Dallas" ,address_id: 19290,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12290,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX278",GROUP_DESC:"TX278 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12290,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX278",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX278",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12290,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX278", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12290 ,protection_group_id: -12290, protection_element_id:-12290], primaryKey: false);
      insert('addresses', [ id: 19291, city: "Bedford", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19291, nci_institute_code: "TX279", name: "Texas Oncology PA- Bedford" ,address_id: 19291,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12291,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX279",GROUP_DESC:"TX279 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12291,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX279",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX279",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12291,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX279", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12291 ,protection_group_id: -12291, protection_element_id:-12291], primaryKey: false);
      insert('addresses', [ id: 19292, city: "Austin", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19292, nci_institute_code: "TX280", name: "Texas Oncology - Central Austin Cancer Center" ,address_id: 19292,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12292,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX280",GROUP_DESC:"TX280 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12292,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX280",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX280",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12292,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX280", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12292 ,protection_group_id: -12292, protection_element_id:-12292], primaryKey: false);
      insert('addresses', [ id: 19293, city: "Irving", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19293, nci_institute_code: "TX281", name: "Surgical Associates of Irving-Coppell LLP" ,address_id: 19293,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12293,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX281",GROUP_DESC:"TX281 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12293,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX281",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX281",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12293,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX281", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12293 ,protection_group_id: -12293, protection_element_id:-12293], primaryKey: false);
      insert('addresses', [ id: 19294, city: "Plano", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19294, nci_institute_code: "TX282", name: "Baylor Regional Medical Center at Plano" ,address_id: 19294,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12294,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX282",GROUP_DESC:"TX282 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12294,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX282",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX282",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12294,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX282", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12294 ,protection_group_id: -12294, protection_element_id:-12294], primaryKey: false);
      insert('addresses', [ id: 19295, city: "Richardson", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19295, nci_institute_code: "TX283", name: "Richardson Regional Medical Center - North Collins" ,address_id: 19295,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12295,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX283",GROUP_DESC:"TX283 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12295,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX283",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX283",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12295,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX283", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12295 ,protection_group_id: -12295, protection_element_id:-12295], primaryKey: false);
      insert('addresses', [ id: 19296, city: "Corpus Christi", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19296, nci_institute_code: "TX284", name: "Cancer Specialists of South Texas PA" ,address_id: 19296,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12296,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX284",GROUP_DESC:"TX284 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12296,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX284",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX284",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12296,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX284", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12296 ,protection_group_id: -12296, protection_element_id:-12296], primaryKey: false);
      insert('addresses', [ id: 19297, city: "Austin", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19297, nci_institute_code: "TX285", name: "Cardiothoracic and Vascular Surgeons" ,address_id: 19297,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12297,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX285",GROUP_DESC:"TX285 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12297,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX285",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX285",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12297,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX285", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12297 ,protection_group_id: -12297, protection_element_id:-12297], primaryKey: false);
    }

    void m12() {
        // all12 (25 terms)
      insert('addresses', [ id: 19298, city: "Lubbock", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19298, nci_institute_code: "TX286", name: "Joe Arrington Cancer Research and Treatment Center" ,address_id: 19298,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12298,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX286",GROUP_DESC:"TX286 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12298,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX286",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX286",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12298,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX286", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12298 ,protection_group_id: -12298, protection_element_id:-12298], primaryKey: false);
      insert('addresses', [ id: 19299, city: "Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19299, nci_institute_code: "TX287", name: "Kelsey Research Foundation" ,address_id: 19299,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12299,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX287",GROUP_DESC:"TX287 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12299,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX287",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX287",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12299,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX287", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12299 ,protection_group_id: -12299, protection_element_id:-12299], primaryKey: false);
      insert('addresses', [ id: 19300, city: "Dallas", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19300, nci_institute_code: "TX288", name: "Mary Crowley Medical Research Center" ,address_id: 19300,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12300,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX288",GROUP_DESC:"TX288 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12300,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX288",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX288",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12300,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX288", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12300 ,protection_group_id: -12300, protection_element_id:-12300], primaryKey: false);
      insert('addresses', [ id: 19301, city: "Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19301, nci_institute_code: "TX289", name: "Colorectal Surgical Associates PA" ,address_id: 19301,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12301,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX289",GROUP_DESC:"TX289 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12301,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX289",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX289",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12301,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX289", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12301 ,protection_group_id: -12301, protection_element_id:-12301], primaryKey: false);
      insert('addresses', [ id: 19302, city: "Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19302, nci_institute_code: "TX290", name: "Colorectal Surgical Associates PA" ,address_id: 19302,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12302,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX290",GROUP_DESC:"TX290 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12302,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX290",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX290",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12302,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX290", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12302 ,protection_group_id: -12302, protection_element_id:-12302], primaryKey: false);
      insert('addresses', [ id: 19303, city: "Dallas", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19303, nci_institute_code: "TX291", name: "LEM Consulting LLC" ,address_id: 19303,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12303,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX291",GROUP_DESC:"TX291 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12303,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX291",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX291",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12303,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX291", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12303 ,protection_group_id: -12303, protection_element_id:-12303], primaryKey: false);
      insert('addresses', [ id: 19304, city: "Amarillo", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19304, nci_institute_code: "TX292", name: "Texas Oncology PA - Amarillo" ,address_id: 19304,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12304,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX292",GROUP_DESC:"TX292 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12304,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX292",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX292",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12304,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX292", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12304 ,protection_group_id: -12304, protection_element_id:-12304], primaryKey: false);
      insert('addresses', [ id: 19305, city: "Harlingen", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19305, nci_institute_code: "TX293", name: "Todd Shenkenberg MD PA" ,address_id: 19305,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12305,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX293",GROUP_DESC:"TX293 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12305,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX293",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX293",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12305,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX293", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12305 ,protection_group_id: -12305, protection_element_id:-12305], primaryKey: false);
      insert('addresses', [ id: 19306, city: "Corpus Christi", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19306, nci_institute_code: "TX294", name: "Coastal Bend Cancer Center" ,address_id: 19306,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12306,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX294",GROUP_DESC:"TX294 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12306,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX294",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX294",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12306,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX294", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12306 ,protection_group_id: -12306, protection_element_id:-12306], primaryKey: false);
      insert('addresses', [ id: 19307, city: "Corpus Christi", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19307, nci_institute_code: "TX295", name: "Christus Spohn Shoreline Hospital" ,address_id: 19307,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12307,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX295",GROUP_DESC:"TX295 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12307,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX295",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX295",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12307,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX295", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12307 ,protection_group_id: -12307, protection_element_id:-12307], primaryKey: false);
      insert('addresses', [ id: 19308, city: "El Paso", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19308, nci_institute_code: "TX296", name: "Cancer Treatment Institute" ,address_id: 19308,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12308,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX296",GROUP_DESC:"TX296 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12308,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX296",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX296",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12308,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX296", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12308 ,protection_group_id: -12308, protection_element_id:-12308], primaryKey: false);
      insert('addresses', [ id: 19309, city: "Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19309, nci_institute_code: "TX297", name: "Surgical Diseases of the Breast" ,address_id: 19309,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12309,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX297",GROUP_DESC:"TX297 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12309,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX297",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX297",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12309,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX297", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12309 ,protection_group_id: -12309, protection_element_id:-12309], primaryKey: false);
      insert('addresses', [ id: 19310, city: "San Antonio", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19310, nci_institute_code: "TX298", name: "Cancer Therapy Research Center Institute for Drug Development" ,address_id: 19310,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12310,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX298",GROUP_DESC:"TX298 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12310,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX298",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX298",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12310,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX298", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12310 ,protection_group_id: -12310, protection_element_id:-12310], primaryKey: false);
      insert('addresses', [ id: 19311, city: "Richardson", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19311, nci_institute_code: "TX299", name: "Elizabeth Jekot MD Breast Imaging Center" ,address_id: 19311,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12311,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX299",GROUP_DESC:"TX299 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12311,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX299",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX299",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12311,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX299", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12311 ,protection_group_id: -12311, protection_element_id:-12311], primaryKey: false);
      insert('addresses', [ id: 19312, city: "Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19312, nci_institute_code: "TX300", name: "Radiation Treatment Center at Bellaire" ,address_id: 19312,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12312,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX300",GROUP_DESC:"TX300 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12312,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX300",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX300",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12312,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX300", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12312 ,protection_group_id: -12312, protection_element_id:-12312], primaryKey: false);
      insert('addresses', [ id: 19313, city: "Richardson", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19313, nci_institute_code: "TX301", name: "Hope Oncology" ,address_id: 19313,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12313,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX301",GROUP_DESC:"TX301 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12313,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX301",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX301",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12313,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX301", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12313 ,protection_group_id: -12313, protection_element_id:-12313], primaryKey: false);
      insert('addresses', [ id: 19314, city: "Fort Worth", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19314, nci_institute_code: "TX302", name: "Mary Milam MD PA" ,address_id: 19314,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12314,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX302",GROUP_DESC:"TX302 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12314,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX302",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX302",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12314,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX302", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12314 ,protection_group_id: -12314, protection_element_id:-12314], primaryKey: false);
      insert('addresses', [ id: 19315, city: "Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19315, nci_institute_code: "TX303", name: "Rice University" ,address_id: 19315,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12315,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX303",GROUP_DESC:"TX303 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12315,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX303",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX303",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12315,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX303", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12315 ,protection_group_id: -12315, protection_element_id:-12315], primaryKey: false);
      insert('addresses', [ id: 19316, city: "McAllen", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19316, nci_institute_code: "TX304", name: "Driscoll Children's Specialty Clinic" ,address_id: 19316,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12316,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX304",GROUP_DESC:"TX304 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12316,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX304",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX304",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12316,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX304", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12316 ,protection_group_id: -12316, protection_element_id:-12316], primaryKey: false);
      insert('addresses', [ id: 19317, city: "Fort Worth", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19317, nci_institute_code: "TX305", name: "Richard A Artim MD PA" ,address_id: 19317,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12317,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX305",GROUP_DESC:"TX305 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12317,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX305",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX305",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12317,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX305", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12317 ,protection_group_id: -12317, protection_element_id:-12317], primaryKey: false);
      insert('addresses', [ id: 19318, city: "Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19318, nci_institute_code: "TX306", name: "The Methodist Hospital Research Institute" ,address_id: 19318,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12318,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX306",GROUP_DESC:"TX306 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12318,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX306",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX306",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12318,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX306", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12318 ,protection_group_id: -12318, protection_element_id:-12318], primaryKey: false);
      insert('addresses', [ id: 19319, city: "Laredo", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19319, nci_institute_code: "TX307", name: "Unzeitig Gary Walter MD (Office)" ,address_id: 19319,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12319,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX307",GROUP_DESC:"TX307 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12319,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX307",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX307",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12319,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX307", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12319 ,protection_group_id: -12319, protection_element_id:-12319], primaryKey: false);
      insert('addresses', [ id: 19320, city: "Sugar Land", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19320, nci_institute_code: "TX308", name: "Texas Oncology Cancer Center Sugar Land" ,address_id: 19320,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12320,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX308",GROUP_DESC:"TX308 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12320,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX308",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX308",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12320,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX308", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12320 ,protection_group_id: -12320, protection_element_id:-12320], primaryKey: false);
      insert('addresses', [ id: 19321, city: "San Antonio", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19321, nci_institute_code: "TX309", name: "South Texas Accelerated Research Therapeutics" ,address_id: 19321,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12321,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX309",GROUP_DESC:"TX309 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12321,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX309",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX309",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12321,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX309", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12321 ,protection_group_id: -12321, protection_element_id:-12321], primaryKey: false);
      insert('addresses', [ id: 19322, city: "Houston", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19322, nci_institute_code: "TX310", name: "Baylor Breast Care Center" ,address_id: 19322,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12322,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX310",GROUP_DESC:"TX310 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12322,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX310",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX310",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12322,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX310", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12322 ,protection_group_id: -12322, protection_element_id:-12322], primaryKey: false);
    }

    void m13() {
        // all13 (25 terms)
      insert('addresses', [ id: 19323, city: "Austin", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19323, nci_institute_code: "TX311", name: "Texas Oncology - Midtown Austin" ,address_id: 19323,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12323,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX311",GROUP_DESC:"TX311 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12323,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX311",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX311",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12323,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX311", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12323 ,protection_group_id: -12323, protection_element_id:-12323], primaryKey: false);
      insert('addresses', [ id: 19324, city: "Mount Pleasant", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19324, nci_institute_code: "TX312", name: "Titus Regional Medical Center" ,address_id: 19324,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12324,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX312",GROUP_DESC:"TX312 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12324,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX312",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX312",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12324,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX312", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12324 ,protection_group_id: -12324, protection_element_id:-12324], primaryKey: false);
      insert('addresses', [ id: 19325, city: "Fort Worth", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19325, nci_institute_code: "TX313", name: "The Klabzuba Cancer Center" ,address_id: 19325,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12325,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX313",GROUP_DESC:"TX313 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12325,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX313",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX313",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12325,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX313", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12325 ,protection_group_id: -12325, protection_element_id:-12325], primaryKey: false);
      insert('addresses', [ id: 19326, city: "Weatherford", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19326, nci_institute_code: "TX314", name: "The Center for Cancer and Blood Disorders-Weatherford" ,address_id: 19326,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12326,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX314",GROUP_DESC:"TX314 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12326,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX314",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX314",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12326,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX314", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12326 ,protection_group_id: -12326, protection_element_id:-12326], primaryKey: false);
      insert('addresses', [ id: 19327, city: "Burleson", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19327, nci_institute_code: "TX315", name: "The Center for Cancer and Blood Disorders-Burleson" ,address_id: 19327,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12327,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX315",GROUP_DESC:"TX315 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12327,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX315",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX315",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12327,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX315", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12327 ,protection_group_id: -12327, protection_element_id:-12327], primaryKey: false);
      insert('addresses', [ id: 19328, city: "Cleburne", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19328, nci_institute_code: "TX316", name: "The Center for Cancer and Blood Disorders-Cleburne" ,address_id: 19328,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12328,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX316",GROUP_DESC:"TX316 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12328,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX316",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX316",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12328,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX316", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12328 ,protection_group_id: -12328, protection_element_id:-12328], primaryKey: false);
      insert('addresses', [ id: 19329, city: "Mineral Wells", state_code: "TX", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19329, nci_institute_code: "TX317", name: "The Center for Cancer and Blood Disorders-Mineral Wells" ,address_id: 19329,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12329,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX317",GROUP_DESC:"TX317 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12329,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.TX317",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.TX317",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12329,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.TX317", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12329 ,protection_group_id: -12329, protection_element_id:-12329], primaryKey: false);
      insert('addresses', [ id: 19330, country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19330, nci_institute_code: "UBUENA", name: "University of Buenos Aires" ,address_id: 19330,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12330,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UBUENA",GROUP_DESC:"UBUENA group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12330,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UBUENA",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UBUENA",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12330,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UBUENA", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12330 ,protection_group_id: -12330, protection_element_id:-12330], primaryKey: false);
      insert('addresses', [ id: 19331, country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19331, nci_institute_code: "UKCCSG", name: "United Kingdom Children's Cancer Study Group" ,address_id: 19331,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12331,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UKCCSG",GROUP_DESC:"UKCCSG group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12331,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UKCCSG",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UKCCSG",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12331,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UKCCSG", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12331 ,protection_group_id: -12331, protection_element_id:-12331], primaryKey: false);
      insert('addresses', [ id: 19332, country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19332, nci_institute_code: "UORG", name: "Uro-Oncology Research Group" ,address_id: 19332,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12332,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UORG",GROUP_DESC:"UORG group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12332,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UORG",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UORG",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12332,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UORG", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12332 ,protection_group_id: -12332, protection_element_id:-12332], primaryKey: false);
      insert('addresses', [ id: 19333, city: "Salt Lake City", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19333, nci_institute_code: "UT001", name: "Holy Cross Hospital" ,address_id: 19333,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12333,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT001",GROUP_DESC:"UT001 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12333,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT001",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT001",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12333,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT001", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12333 ,protection_group_id: -12333, protection_element_id:-12333], primaryKey: false);
      insert('addresses', [ id: 19334, city: "Salt Lake City", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19334, nci_institute_code: "UT002", name: "Primary Children's Medical Center" ,address_id: 19334,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12334,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT002",GROUP_DESC:"UT002 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12334,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT002",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT002",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12334,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT002", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12334 ,protection_group_id: -12334, protection_element_id:-12334], primaryKey: false);
      insert('addresses', [ id: 19335, city: "Salt Lake City", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19335, nci_institute_code: "UT003", name: "University of Utah" ,address_id: 19335,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12335,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT003",GROUP_DESC:"UT003 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12335,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT003",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT003",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12335,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT003", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12335 ,protection_group_id: -12335, protection_element_id:-12335], primaryKey: false);
      insert('addresses', [ id: 19336, city: "Salt Lake City", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19336, nci_institute_code: "UT004", name: "LDS Hospital" ,address_id: 19336,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12336,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT004",GROUP_DESC:"UT004 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12336,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT004",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT004",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12336,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT004", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12336 ,protection_group_id: -12336, protection_element_id:-12336], primaryKey: false);
      insert('addresses', [ id: 19337, city: "Salt Lake City", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19337, nci_institute_code: "UT005", name: "Salt Lake City Veterans Affairs Medical Center" ,address_id: 19337,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12337,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT005",GROUP_DESC:"UT005 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12337,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT005",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT005",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12337,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT005", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12337 ,protection_group_id: -12337, protection_element_id:-12337], primaryKey: false);
      insert('addresses', [ id: 19338, city: "Ogden", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19338, nci_institute_code: "UT006", name: "Saint Benedicts Hospital" ,address_id: 19338,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12338,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT006",GROUP_DESC:"UT006 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12338,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT006",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT006",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12338,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT006", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12338 ,protection_group_id: -12338, protection_element_id:-12338], primaryKey: false);
      insert('addresses', [ id: 19339, city: "Ogden", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19339, nci_institute_code: "UT007", name: "McKay-Dee Hospital Center" ,address_id: 19339,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12339,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT007",GROUP_DESC:"UT007 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12339,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT007",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT007",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12339,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT007", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12339 ,protection_group_id: -12339, protection_element_id:-12339], primaryKey: false);
      insert('addresses', [ id: 19340, city: "Provo", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19340, nci_institute_code: "UT008", name: "Utah Valley Regional Medical Center" ,address_id: 19340,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12340,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT008",GROUP_DESC:"UT008 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12340,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT008",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT008",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12340,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT008", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12340 ,protection_group_id: -12340, protection_element_id:-12340], primaryKey: false);
      insert('addresses', [ id: 19341, city: "Logan", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19341, nci_institute_code: "UT009", name: "Logan Regional Hospital" ,address_id: 19341,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12341,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT009",GROUP_DESC:"UT009 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12341,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT009",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT009",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12341,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT009", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12341 ,protection_group_id: -12341, protection_element_id:-12341], primaryKey: false);
      insert('addresses', [ id: 19342, city: "St. George", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19342, nci_institute_code: "UT010", name: "Dixie Medical Center Regional Cancer Center" ,address_id: 19342,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12342,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT010",GROUP_DESC:"UT010 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12342,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT010",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT010",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12342,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT010", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12342 ,protection_group_id: -12342, protection_element_id:-12342], primaryKey: false);
      insert('addresses', [ id: 19343, city: "Salt Lake City", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19343, nci_institute_code: "UT011", name: "Hca Saint Mark's Hospital" ,address_id: 19343,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12343,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT011",GROUP_DESC:"UT011 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12343,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT011",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT011",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12343,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT011", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12343 ,protection_group_id: -12343, protection_element_id:-12343], primaryKey: false);
      insert('addresses', [ id: 19344, city: "Murray", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19344, nci_institute_code: "UT012", name: "Cottonwood Hospital Medical Center" ,address_id: 19344,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12344,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT012",GROUP_DESC:"UT012 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12344,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT012",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT012",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12344,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT012", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12344 ,protection_group_id: -12344, protection_element_id:-12344], primaryKey: false);
      insert('addresses', [ id: 19345, city: "Salt Lake City", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19345, nci_institute_code: "UT013", name: "Fhp Utah Hospital" ,address_id: 19345,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12345,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT013",GROUP_DESC:"UT013 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12345,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT013",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT013",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12345,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT013", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12345 ,protection_group_id: -12345, protection_element_id:-12345], primaryKey: false);
      insert('addresses', [ id: 19346, city: "Ogden", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19346, nci_institute_code: "UT014", name: "Ogden Regional Medical Center" ,address_id: 19346,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12346,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT014",GROUP_DESC:"UT014 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12346,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT014",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT014",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12346,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT014", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12346 ,protection_group_id: -12346, protection_element_id:-12346], primaryKey: false);
      insert('addresses', [ id: 19347, city: "Salt Lake City", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19347, nci_institute_code: "UT015", name: "Ihc Health Center Salt Lake Clinic" ,address_id: 19347,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12347,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT015",GROUP_DESC:"UT015 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12347,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT015",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT015",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12347,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT015", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12347 ,protection_group_id: -12347, protection_element_id:-12347], primaryKey: false);
    }

    void m14() {
        // all14 (25 terms)
      insert('addresses', [ id: 19348, city: "Provo", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19348, nci_institute_code: "UT016", name: "Central Utah Clinic" ,address_id: 19348,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12348,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT016",GROUP_DESC:"UT016 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12348,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT016",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT016",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12348,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT016", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12348 ,protection_group_id: -12348, protection_element_id:-12348], primaryKey: false);
      insert('addresses', [ id: 19349, city: "Salt Lake City", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19349, nci_institute_code: "UT018", name: "Special Gynecology and Oncology" ,address_id: 19349,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12349,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT018",GROUP_DESC:"UT018 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12349,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT018",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT018",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12349,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT018", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12349 ,protection_group_id: -12349, protection_element_id:-12349], primaryKey: false);
      insert('addresses', [ id: 19350, city: "Salt Lake City", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19350, nci_institute_code: "UT019", name: "Utah Cancer Specialists-Salt Lake City" ,address_id: 19350,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12350,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT019",GROUP_DESC:"UT019 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12350,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT019",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT019",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12350,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT019", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12350 ,protection_group_id: -12350, protection_element_id:-12350], primaryKey: false);
      insert('addresses', [ id: 19351, city: "Salt Lake City", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19351, nci_institute_code: "UT020", name: "Wasatch Hematology/Oncology Associates" ,address_id: 19351,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12351,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT020",GROUP_DESC:"UT020 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12351,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT020",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT020",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12351,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT020", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12351 ,protection_group_id: -12351, protection_element_id:-12351], primaryKey: false);
      insert('addresses', [ id: 19352, city: "Salt Lake City", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19352, nci_institute_code: "UT022", name: "University of Utah Health Network" ,address_id: 19352,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12352,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT022",GROUP_DESC:"UT022 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12352,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT022",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT022",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12352,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT022", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12352 ,protection_group_id: -12352, protection_element_id:-12352], primaryKey: false);
      insert('addresses', [ id: 19353, city: "Sandy", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19353, nci_institute_code: "UT023", name: "South Valley Surgical Associates" ,address_id: 19353,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12353,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT023",GROUP_DESC:"UT023 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12353,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT023",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT023",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12353,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT023", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12353 ,protection_group_id: -12353, protection_element_id:-12353], primaryKey: false);
      insert('addresses', [ id: 19354, city: "American Fork", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19354, nci_institute_code: "UT024", name: "American Fork Hospital" ,address_id: 19354,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12354,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT024",GROUP_DESC:"UT024 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12354,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT024",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT024",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12354,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT024", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12354 ,protection_group_id: -12354, protection_element_id:-12354], primaryKey: false);
      insert('addresses', [ id: 19355, city: "Provo", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19355, nci_institute_code: "UT025", name: "Utah County Surgical Associates" ,address_id: 19355,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12355,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT025",GROUP_DESC:"UT025 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12355,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT025",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT025",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12355,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT025", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12355 ,protection_group_id: -12355, protection_element_id:-12355], primaryKey: false);
      insert('addresses', [ id: 19356, city: "American Fork", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19356, nci_institute_code: "UT026", name: "Central Utah Medical Clinic Inc." ,address_id: 19356,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12356,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT026",GROUP_DESC:"UT026 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12356,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT026",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT026",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12356,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT026", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12356 ,protection_group_id: -12356, protection_element_id:-12356], primaryKey: false);
      insert('addresses', [ id: 19357, city: "Bountiful", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19357, nci_institute_code: "UT027", name: "Intermountain Hematology Oncology Associates, PC" ,address_id: 19357,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12357,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT027",GROUP_DESC:"UT027 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12357,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT027",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT027",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12357,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT027", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12357 ,protection_group_id: -12357, protection_element_id:-12357], primaryKey: false);
      insert('addresses', [ id: 19358, city: "Salt Lake City", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19358, nci_institute_code: "UT028", name: "Intermountain Health Care" ,address_id: 19358,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12358,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT028",GROUP_DESC:"UT028 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12358,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT028",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT028",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12358,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT028", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12358 ,protection_group_id: -12358, protection_element_id:-12358], primaryKey: false);
      insert('addresses', [ id: 19359, city: "Bountiful", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19359, nci_institute_code: "UT029", name: "IHC Health Center" ,address_id: 19359,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12359,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT029",GROUP_DESC:"UT029 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12359,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT029",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT029",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12359,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT029", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12359 ,protection_group_id: -12359, protection_element_id:-12359], primaryKey: false);
      insert('addresses', [ id: 19360, city: "Salt Lake City", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19360, nci_institute_code: "UT030", name: "IHC Memorial Clinic" ,address_id: 19360,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12360,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT030",GROUP_DESC:"UT030 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12360,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT030",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT030",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12360,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT030", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12360 ,protection_group_id: -12360, protection_element_id:-12360], primaryKey: false);
      insert('addresses', [ id: 19361, city: "Ivins", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19361, nci_institute_code: "UT031", name: "Snow Canyon Clinic" ,address_id: 19361,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12361,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT031",GROUP_DESC:"UT031 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12361,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT031",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT031",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12361,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT031", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12361 ,protection_group_id: -12361, protection_element_id:-12361], primaryKey: false);
      insert('addresses', [ id: 19362, city: "Salt Lake City", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19362, nci_institute_code: "UT032", name: "Foothill Family Clinic" ,address_id: 19362,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12362,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT032",GROUP_DESC:"UT032 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12362,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT032",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT032",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12362,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT032", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12362 ,protection_group_id: -12362, protection_element_id:-12362], primaryKey: false);
      insert('addresses', [ id: 19363, city: "Salt Lake City", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19363, nci_institute_code: "UT033", name: "Middleton Urology Associates" ,address_id: 19363,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12363,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT033",GROUP_DESC:"UT033 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12363,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT033",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT033",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12363,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT033", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12363 ,protection_group_id: -12363, protection_element_id:-12363], primaryKey: false);
      insert('addresses', [ id: 19364, city: "West Jordan", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19364, nci_institute_code: "UT034", name: "IHC West Jordan Family Clinic" ,address_id: 19364,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12364,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT034",GROUP_DESC:"UT034 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12364,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT034",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT034",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12364,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT034", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12364 ,protection_group_id: -12364, protection_element_id:-12364], primaryKey: false);
      insert('addresses', [ id: 19365, city: "Salt Lake City", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19365, nci_institute_code: "UT035", name: "Bryner Clinic" ,address_id: 19365,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12365,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT035",GROUP_DESC:"UT035 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12365,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT035",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT035",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12365,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT035", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12365 ,protection_group_id: -12365, protection_element_id:-12365], primaryKey: false);
      insert('addresses', [ id: 19366, city: "Orem", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19366, nci_institute_code: "UT036", name: "IHC Central Orem Family Fractice" ,address_id: 19366,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12366,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT036",GROUP_DESC:"UT036 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12366,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT036",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT036",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12366,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT036", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12366 ,protection_group_id: -12366, protection_element_id:-12366], primaryKey: false);
      insert('addresses', [ id: 19367, city: "Layton", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19367, nci_institute_code: "UT037", name: "IHC Health Center" ,address_id: 19367,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12367,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT037",GROUP_DESC:"UT037 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12367,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT037",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT037",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12367,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT037", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12367 ,protection_group_id: -12367, protection_element_id:-12367], primaryKey: false);
      insert('addresses', [ id: 19368, city: "Saint George", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19368, nci_institute_code: "UT038", name: "Dixie Regional Medical Center" ,address_id: 19368,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12368,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT038",GROUP_DESC:"UT038 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12368,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT038",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT038",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12368,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT038", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12368 ,protection_group_id: -12368, protection_element_id:-12368], primaryKey: false);
      insert('addresses', [ id: 19369, city: "Cedar City", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19369, nci_institute_code: "UT039", name: "Valley View Medical Center" ,address_id: 19369,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12369,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT039",GROUP_DESC:"UT039 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12369,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT039",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT039",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12369,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT039", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12369 ,protection_group_id: -12369, protection_element_id:-12369], primaryKey: false);
      insert('addresses', [ id: 19370, city: "Murray", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19370, nci_institute_code: "UT040", name: "Middleton Urological Associates" ,address_id: 19370,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12370,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT040",GROUP_DESC:"UT040 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12370,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT040",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT040",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12370,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT040", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12370 ,protection_group_id: -12370, protection_element_id:-12370], primaryKey: false);
      insert('addresses', [ id: 19371, city: "American Fork", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19371, nci_institute_code: "UT041", name: "Utah Advanced Laparoscopy" ,address_id: 19371,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12371,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT041",GROUP_DESC:"UT041 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12371,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT041",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT041",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12371,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT041", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12371 ,protection_group_id: -12371, protection_element_id:-12371], primaryKey: false);
      insert('addresses', [ id: 19372, city: "Cedar City", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19372, nci_institute_code: "UT042", name: "Southern Utah Urology Associates" ,address_id: 19372,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12372,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT042",GROUP_DESC:"UT042 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12372,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT042",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT042",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12372,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT042", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12372 ,protection_group_id: -12372, protection_element_id:-12372], primaryKey: false);
    }

    void m15() {
        // all15 (25 terms)
      insert('addresses', [ id: 19373, city: "Sandy", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19373, nci_institute_code: "UT043", name: "IHC Health Center - Sandy" ,address_id: 19373,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12373,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT043",GROUP_DESC:"UT043 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12373,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT043",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT043",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12373,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT043", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12373 ,protection_group_id: -12373, protection_element_id:-12373], primaryKey: false);
      insert('addresses', [ id: 19374, city: "Ivins", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19374, nci_institute_code: "UT044", name: "Snow Canyon Cancer Clinic" ,address_id: 19374,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12374,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT044",GROUP_DESC:"UT044 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12374,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT044",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT044",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12374,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT044", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12374 ,protection_group_id: -12374, protection_element_id:-12374], primaryKey: false);
      insert('addresses', [ id: 19375, city: "St. George", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19375, nci_institute_code: "UT045", name: "Southwest Surgical Associates" ,address_id: 19375,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12375,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT045",GROUP_DESC:"UT045 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12375,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT045",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT045",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12375,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT045", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12375 ,protection_group_id: -12375, protection_element_id:-12375], primaryKey: false);
      insert('addresses', [ id: 19376, city: "Salt Lake City", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19376, nci_institute_code: "UT046", name: "Huntsman Cancer Hospital" ,address_id: 19376,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12376,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT046",GROUP_DESC:"UT046 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12376,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT046",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT046",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12376,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT046", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12376 ,protection_group_id: -12376, protection_element_id:-12376], primaryKey: false);
      insert('addresses', [ id: 19377, city: "Ogden", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19377, nci_institute_code: "UT047", name: "Utah Hematology Oncology" ,address_id: 19377,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12377,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT047",GROUP_DESC:"UT047 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12377,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT047",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT047",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12377,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT047", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12377 ,protection_group_id: -12377, protection_element_id:-12377], primaryKey: false);
      insert('addresses', [ id: 19378, city: "Salt Lake City", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19378, nci_institute_code: "UT048", name: "Bryner Surgical Specialists" ,address_id: 19378,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12378,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT048",GROUP_DESC:"UT048 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12378,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT048",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT048",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12378,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT048", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12378 ,protection_group_id: -12378, protection_element_id:-12378], primaryKey: false);
      insert('addresses', [ id: 19379, city: "Salt Lake City", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19379, nci_institute_code: "UT049", name: "GammaWest Brachytherapy LLC-Business Office" ,address_id: 19379,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12379,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT049",GROUP_DESC:"UT049 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12379,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT049",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT049",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12379,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT049", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12379 ,protection_group_id: -12379, protection_element_id:-12379], primaryKey: false);
      insert('addresses', [ id: 19380, city: "Ogden", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19380, nci_institute_code: "UT050", name: "GammaWest Brachytherapy LLC" ,address_id: 19380,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12380,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT050",GROUP_DESC:"UT050 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12380,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT050",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT050",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12380,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT050", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12380 ,protection_group_id: -12380, protection_element_id:-12380], primaryKey: false);
      insert('addresses', [ id: 19381, city: "Salt Lake City", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19381, nci_institute_code: "UT051", name: "GammaWest Brachytherapy LLC - Salt Lake City" ,address_id: 19381,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12381,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT051",GROUP_DESC:"UT051 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12381,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT051",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT051",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12381,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT051", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12381 ,protection_group_id: -12381, protection_element_id:-12381], primaryKey: false);
      insert('addresses', [ id: 19382, city: "Salt Lake City", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19382, nci_institute_code: "UT052", name: "Huntsman Cancer Institute" ,address_id: 19382,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12382,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT052",GROUP_DESC:"UT052 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12382,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT052",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT052",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12382,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT052", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12382 ,protection_group_id: -12382, protection_element_id:-12382], primaryKey: false);
      insert('addresses', [ id: 19383, city: "American Fork", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19383, nci_institute_code: "UT053", name: "Huntsman Intermountain Cancer Center" ,address_id: 19383,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12383,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT053",GROUP_DESC:"UT053 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12383,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT053",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT053",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12383,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT053", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12383 ,protection_group_id: -12383, protection_element_id:-12383], primaryKey: false);
      insert('addresses', [ id: 19384, city: "Salt Lake City", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19384, nci_institute_code: "UT054", name: "Intermountain Cardiovascular Associates" ,address_id: 19384,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12384,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT054",GROUP_DESC:"UT054 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12384,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT054",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT054",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12384,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT054", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12384 ,protection_group_id: -12384, protection_element_id:-12384], primaryKey: false);
      insert('addresses', [ id: 19385, city: "Salt Lake City", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19385, nci_institute_code: "UT055", name: "Legant, Patricia MD (office)" ,address_id: 19385,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12385,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT055",GROUP_DESC:"UT055 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12385,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT055",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT055",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12385,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT055", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12385 ,protection_group_id: -12385, protection_element_id:-12385], primaryKey: false);
      insert('addresses', [ id: 19386, city: "Murray", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19386, nci_institute_code: "UT056", name: "Intermountain Medical Center" ,address_id: 19386,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12386,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT056",GROUP_DESC:"UT056 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12386,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT056",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT056",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12386,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT056", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12386 ,protection_group_id: -12386, protection_element_id:-12386], primaryKey: false);
      insert('addresses', [ id: 19387, city: "Cedar City", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19387, nci_institute_code: "UT057", name: "Sandra L Maxwell Cancer Center" ,address_id: 19387,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12387,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT057",GROUP_DESC:"UT057 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12387,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT057",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT057",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12387,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT057", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12387 ,protection_group_id: -12387, protection_element_id:-12387], primaryKey: false);
      insert('addresses', [ id: 19388, city: "Provo", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19388, nci_institute_code: "UT058", name: "Utah Cancer Specialists-Provo" ,address_id: 19388,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12388,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT058",GROUP_DESC:"UT058 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12388,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT058",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT058",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12388,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT058", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12388 ,protection_group_id: -12388, protection_element_id:-12388], primaryKey: false);
      insert('addresses', [ id: 19389, city: "Ogden", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19389, nci_institute_code: "UT059", name: "Utah Hematology Oncology PC" ,address_id: 19389,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12389,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT059",GROUP_DESC:"UT059 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12389,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT059",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT059",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12389,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT059", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12389 ,protection_group_id: -12389, protection_element_id:-12389], primaryKey: false);
      insert('addresses', [ id: 19390, city: "Layton", state_code: "UT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19390, nci_institute_code: "UT060", name: "Utah Cancer Specialists-Layton" ,address_id: 19390,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12390,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT060",GROUP_DESC:"UT060 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12390,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.UT060",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.UT060",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12390,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.UT060", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12390 ,protection_group_id: -12390, protection_element_id:-12390], primaryKey: false);
      insert('addresses', [ id: 19391, city: "Falls Church", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19391, nci_institute_code: "VA001", name: "Hazelton Laboratories" ,address_id: 19391,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12391,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA001",GROUP_DESC:"VA001 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12391,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA001",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA001",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12391,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA001", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12391 ,protection_group_id: -12391, protection_element_id:-12391], primaryKey: false);
      insert('addresses', [ id: 19392, city: "Falls Church", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19392, nci_institute_code: "VA002", name: "Inova Fairfax Hospital" ,address_id: 19392,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12392,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA002",GROUP_DESC:"VA002 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12392,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA002",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA002",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12392,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA002", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12392 ,protection_group_id: -12392, protection_element_id:-12392], primaryKey: false);
      insert('addresses', [ id: 19393, city: "Springfield", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19393, nci_institute_code: "VA004", name: "Kaiser Permanente - Springfield Medical Center" ,address_id: 19393,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12393,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA004",GROUP_DESC:"VA004 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12393,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA004",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA004",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12393,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA004", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12393 ,protection_group_id: -12393, protection_element_id:-12393], primaryKey: false);
      insert('addresses', [ id: 19394, city: "Arlington", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19394, nci_institute_code: "VA005", name: "Arlington Hospital" ,address_id: 19394,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12394,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA005",GROUP_DESC:"VA005 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12394,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA005",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA005",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12394,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA005", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12394 ,protection_group_id: -12394, protection_element_id:-12394], primaryKey: false);
      insert('addresses', [ id: 19395, city: "Alexandria", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19395, nci_institute_code: "VA006", name: "Alexandria Hospital" ,address_id: 19395,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12395,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA006",GROUP_DESC:"VA006 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12395,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA006",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA006",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12395,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA006", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12395 ,protection_group_id: -12395, protection_element_id:-12395], primaryKey: false);
      insert('addresses', [ id: 19396, city: "Kilmarnock", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19396, nci_institute_code: "VA007", name: "Rappahannock General Hospital" ,address_id: 19396,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12396,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA007",GROUP_DESC:"VA007 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12396,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA007",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA007",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12396,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA007", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12396 ,protection_group_id: -12396, protection_element_id:-12396], primaryKey: false);
      insert('addresses', [ id: 19397, city: "Winchester", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19397, nci_institute_code: "VA008", name: "Winchester Memorial Hospital" ,address_id: 19397,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12397,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA008",GROUP_DESC:"VA008 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12397,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA008",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA008",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12397,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA008", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12397 ,protection_group_id: -12397, protection_element_id:-12397], primaryKey: false);
    }

    void m16() {
        // all16 (25 terms)
      insert('addresses', [ id: 19398, city: "Charlottesville", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19398, nci_institute_code: "VA009", name: "University of Virginia" ,address_id: 19398,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12398,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA009",GROUP_DESC:"VA009 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12398,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA009",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA009",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12398,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA009", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12398 ,protection_group_id: -12398, protection_element_id:-12398], primaryKey: false);
      insert('addresses', [ id: 19399, city: "Richmond", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19399, nci_institute_code: "VA010", name: "Virginia Commonwealth University" ,address_id: 19399,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12399,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA010",GROUP_DESC:"VA010 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12399,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA010",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA010",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12399,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA010", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12399 ,protection_group_id: -12399, protection_element_id:-12399], primaryKey: false);
      insert('addresses', [ id: 19400, city: "Richmond", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19400, nci_institute_code: "VA011", name: "Retreat Hospital" ,address_id: 19400,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12400,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA011",GROUP_DESC:"VA011 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12400,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA011",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA011",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12400,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA011", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12400 ,protection_group_id: -12400, protection_element_id:-12400], primaryKey: false);
      insert('addresses', [ id: 19401, city: "Richmond", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19401, nci_institute_code: "VA012", name: "Chippenham - Johnson" ,address_id: 19401,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12401,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA012",GROUP_DESC:"VA012 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12401,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA012",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA012",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12401,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA012", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12401 ,protection_group_id: -12401, protection_element_id:-12401], primaryKey: false);
      insert('addresses', [ id: 19402, city: "Richmond", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19402, nci_institute_code: "VA013", name: "Bon Secours Saint Mary's Hospital" ,address_id: 19402,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12402,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA013",GROUP_DESC:"VA013 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12402,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA013",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA013",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12402,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA013", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12402 ,protection_group_id: -12402, protection_element_id:-12402], primaryKey: false);
      insert('addresses', [ id: 19403, city: "Richmond", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19403, nci_institute_code: "VA014", name: "City Hospital" ,address_id: 19403,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12403,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA014",GROUP_DESC:"VA014 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12403,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA014",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA014",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12403,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA014", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12403 ,protection_group_id: -12403, protection_element_id:-12403], primaryKey: false);
      insert('addresses', [ id: 19404, city: "Richmond", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19404, nci_institute_code: "VA015", name: "Hunter Holmes McGuire Veterans Hospital" ,address_id: 19404,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12404,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA015",GROUP_DESC:"VA015 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12404,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA015",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA015",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12404,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA015", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12404 ,protection_group_id: -12404, protection_element_id:-12404], primaryKey: false);
      insert('addresses', [ id: 19405, city: "Virginia Beach", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19405, nci_institute_code: "VA016", name: "General Hospital of Virginia Beach" ,address_id: 19405,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12405,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA016",GROUP_DESC:"VA016 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12405,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA016",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA016",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12405,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA016", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12405 ,protection_group_id: -12405, protection_element_id:-12405], primaryKey: false);
      insert('addresses', [ id: 19406, city: "Fairfax", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19406, nci_institute_code: "VA018", name: "Children's Center for Cancer and Blood Disorders in Northern Virginia" ,address_id: 19406,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12406,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA018",GROUP_DESC:"VA018 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12406,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA018",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA018",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12406,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA018", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12406 ,protection_group_id: -12406, protection_element_id:-12406], primaryKey: false);
      insert('addresses', [ id: 19407, city: "Norfolk", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19407, nci_institute_code: "VA019", name: "Depaul Hospital" ,address_id: 19407,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12407,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA019",GROUP_DESC:"VA019 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12407,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA019",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA019",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12407,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA019", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12407 ,protection_group_id: -12407, protection_element_id:-12407], primaryKey: false);
      insert('addresses', [ id: 19408, city: "Norfolk", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19408, nci_institute_code: "VA020", name: "Childrens Hospital-King's Daughters" ,address_id: 19408,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12408,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA020",GROUP_DESC:"VA020 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12408,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA020",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA020",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12408,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA020", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12408 ,protection_group_id: -12408, protection_element_id:-12408], primaryKey: false);
      insert('addresses', [ id: 19409, city: "Norfolk", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19409, nci_institute_code: "VA021", name: "Eastern Virginia Medical School" ,address_id: 19409,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12409,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA021",GROUP_DESC:"VA021 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12409,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA021",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA021",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12409,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA021", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12409 ,protection_group_id: -12409, protection_element_id:-12409], primaryKey: false);
      insert('addresses', [ id: 19410, city: "Norfolk", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19410, nci_institute_code: "VA022", name: "USPHS Hospital, Norfolk" ,address_id: 19410,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12410,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA022",GROUP_DESC:"VA022 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12410,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA022",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA022",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12410,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA022", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12410 ,protection_group_id: -12410, protection_element_id:-12410], primaryKey: false);
      insert('addresses', [ id: 19411, city: "Hampton", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19411, nci_institute_code: "VA023", name: "Veterans Administration Medical Center" ,address_id: 19411,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12411,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA023",GROUP_DESC:"VA023 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12411,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA023",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA023",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12411,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA023", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12411 ,protection_group_id: -12411, protection_element_id:-12411], primaryKey: false);
      insert('addresses', [ id: 19412, city: "Portsmouth", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19412, nci_institute_code: "VA024", name: "Naval Medical Center - Portsmouth" ,address_id: 19412,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12412,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA024",GROUP_DESC:"VA024 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12412,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA024",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA024",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12412,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA024", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12412 ,protection_group_id: -12412, protection_element_id:-12412], primaryKey: false);
      insert('addresses', [ id: 19413, city: "Petersburg", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19413, nci_institute_code: "VA025", name: "Petersburg General Hospital" ,address_id: 19413,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12413,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA025",GROUP_DESC:"VA025 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12413,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA025",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA025",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12413,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA025", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12413 ,protection_group_id: -12413, protection_element_id:-12413], primaryKey: false);
      insert('addresses', [ id: 19414, city: "Roanoke", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19414, nci_institute_code: "VA026", name: "Oncology and Hematology Associates of Southwest Virginia" ,address_id: 19414,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12414,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA026",GROUP_DESC:"VA026 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12414,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA026",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA026",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12414,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA026", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12414 ,protection_group_id: -12414, protection_element_id:-12414], primaryKey: false);
      insert('addresses', [ id: 19415, city: "Salem", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19415, nci_institute_code: "VA027", name: "Veterans Administration Medical Center" ,address_id: 19415,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12415,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA027",GROUP_DESC:"VA027 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12415,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA027",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA027",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12415,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA027", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12415 ,protection_group_id: -12415, protection_element_id:-12415], primaryKey: false);
      insert('addresses', [ id: 19416, city: "Danville", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19416, nci_institute_code: "VA028", name: "Danville Regional Medical Center" ,address_id: 19416,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12416,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA028",GROUP_DESC:"VA028 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12416,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA028",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA028",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12416,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA028", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12416 ,protection_group_id: -12416, protection_element_id:-12416], primaryKey: false);
      insert('addresses', [ id: 19417, city: "Clifton Forge", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19417, nci_institute_code: "VA030", name: "Emmett Memorial Hospital" ,address_id: 19417,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12417,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA030",GROUP_DESC:"VA030 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12417,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA030",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA030",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12417,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA030", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12417 ,protection_group_id: -12417, protection_element_id:-12417], primaryKey: false);
      insert('addresses', [ id: 19418, city: "Lynchburg", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19418, nci_institute_code: "VA031", name: "Naval Hospital, Lynchburg" ,address_id: 19418,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12418,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA031",GROUP_DESC:"VA031 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12418,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA031",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA031",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12418,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA031", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12418 ,protection_group_id: -12418, protection_element_id:-12418], primaryKey: false);
      insert('addresses', [ id: 19419, city: "Norfolk", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19419, nci_institute_code: "VA033", name: "Harold Pugh Can Res Institute" ,address_id: 19419,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12419,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA033",GROUP_DESC:"VA033 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12419,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA033",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA033",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12419,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA033", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12419 ,protection_group_id: -12419, protection_element_id:-12419], primaryKey: false);
      insert('addresses', [ id: 19420, city: "Lynchburg", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19420, nci_institute_code: "VA034", name: "Lynchburg Hematology-Oncology Clinic" ,address_id: 19420,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12420,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA034",GROUP_DESC:"VA034 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12420,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA034",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA034",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12420,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA034", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12420 ,protection_group_id: -12420, protection_element_id:-12420], primaryKey: false);
      insert('addresses', [ id: 19421, city: "Falls Church", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19421, nci_institute_code: "VA036", name: "Kaiser Permanente - Falls Church Medical Center" ,address_id: 19421,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12421,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA036",GROUP_DESC:"VA036 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12421,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA036",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA036",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12421,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA036", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12421 ,protection_group_id: -12421, protection_element_id:-12421], primaryKey: false);
      insert('addresses', [ id: 19422, city: "Norfolk", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19422, nci_institute_code: "VA037", name: "Norfolk Community Hospital" ,address_id: 19422,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12422,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA037",GROUP_DESC:"VA037 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12422,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA037",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA037",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12422,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA037", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12422 ,protection_group_id: -12422, protection_element_id:-12422], primaryKey: false);
    }

    void m17() {
        // all17 (25 terms)
      insert('addresses', [ id: 19423, city: "Newport News", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19423, nci_institute_code: "VA038", name: "Riverside Regional Medical Center" ,address_id: 19423,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12423,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA038",GROUP_DESC:"VA038 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12423,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA038",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA038",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12423,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA038", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12423 ,protection_group_id: -12423, protection_element_id:-12423], primaryKey: false);
      insert('addresses', [ id: 19424, city: "Newport News", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19424, nci_institute_code: "VA039", name: "Virginia Oncology Associates" ,address_id: 19424,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12424,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA039",GROUP_DESC:"VA039 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12424,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA039",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA039",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12424,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA039", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12424 ,protection_group_id: -12424, protection_element_id:-12424], primaryKey: false);
      insert('addresses', [ id: 19425, city: "Alexandria", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19425, nci_institute_code: "VA040", name: "Mount Vernon Hospital" ,address_id: 19425,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12425,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA040",GROUP_DESC:"VA040 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12425,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA040",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA040",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12425,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA040", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12425 ,protection_group_id: -12425, protection_element_id:-12425], primaryKey: false);
      insert('addresses', [ id: 19426, city: "Chesapeake", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19426, nci_institute_code: "VA042", name: "Mid-Atlantic Hematology/Oncology Spec Ltd." ,address_id: 19426,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12426,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA042",GROUP_DESC:"VA042 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12426,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA042",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA042",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12426,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA042", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12426 ,protection_group_id: -12426, protection_element_id:-12426], primaryKey: false);
      insert('addresses', [ id: 19427, city: "Martinsville", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19427, nci_institute_code: "VA043", name: "Memorial Hospital Of Martinsville" ,address_id: 19427,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12427,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA043",GROUP_DESC:"VA043 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12427,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA043",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA043",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12427,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA043", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12427 ,protection_group_id: -12427, protection_element_id:-12427], primaryKey: false);
      insert('addresses', [ id: 19428, city: "Fredericksburg", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19428, nci_institute_code: "VA045", name: "Medical Specialists of Fredricksburg" ,address_id: 19428,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12428,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA045",GROUP_DESC:"VA045 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12428,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA045",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA045",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12428,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA045", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12428 ,protection_group_id: -12428, protection_element_id:-12428], primaryKey: false);
      insert('addresses', [ id: 19429, city: "Richlands", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19429, nci_institute_code: "VA046", name: "Humana Hospital Clinic Valley" ,address_id: 19429,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12429,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA046",GROUP_DESC:"VA046 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12429,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA046",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA046",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12429,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA046", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12429 ,protection_group_id: -12429, protection_element_id:-12429], primaryKey: false);
      insert('addresses', [ id: 19430, city: "Hampton", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19430, nci_institute_code: "VA047", name: "James River Cancer Center" ,address_id: 19430,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12430,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA047",GROUP_DESC:"VA047 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12430,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA047",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA047",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12430,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA047", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12430 ,protection_group_id: -12430, protection_element_id:-12430], primaryKey: false);
      insert('addresses', [ id: 19431, city: "Charlottesville", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19431, nci_institute_code: "VA048", name: "Martha Jefferson Hospital" ,address_id: 19431,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12431,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA048",GROUP_DESC:"VA048 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12431,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA048",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA048",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12431,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA048", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12431 ,protection_group_id: -12431, protection_element_id:-12431], primaryKey: false);
      insert('addresses', [ id: 19432, city: "Salem", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19432, nci_institute_code: "VA049", name: "Lewis-Gale Hospital" ,address_id: 19432,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12432,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA049",GROUP_DESC:"VA049 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12432,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA049",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA049",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12432,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA049", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12432 ,protection_group_id: -12432, protection_element_id:-12432], primaryKey: false);
      insert('addresses', [ id: 19433, city: "Roanoke", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19433, nci_institute_code: "VA050", name: "Carilion Roanoke Community Hospital" ,address_id: 19433,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12433,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA050",GROUP_DESC:"VA050 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12433,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA050",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA050",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12433,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA050", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12433 ,protection_group_id: -12433, protection_element_id:-12433], primaryKey: false);
      insert('addresses', [ id: 19434, city: "Richmond", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19434, nci_institute_code: "VA051", name: "McGuire Clinic Cancer Center" ,address_id: 19434,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12434,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA051",GROUP_DESC:"VA051 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12434,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA051",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA051",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12434,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA051", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12434 ,protection_group_id: -12434, protection_element_id:-12434], primaryKey: false);
      insert('addresses', [ id: 19435, city: "Norfolk", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19435, nci_institute_code: "VA052", name: "Sentara Hospitals" ,address_id: 19435,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12435,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA052",GROUP_DESC:"VA052 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12435,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA052",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA052",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12435,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA052", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12435 ,protection_group_id: -12435, protection_element_id:-12435], primaryKey: false);
      insert('addresses', [ id: 19436, city: "Richmond", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19436, nci_institute_code: "VA053", name: "CJW Medical Center - Johnston-Willis Campus" ,address_id: 19436,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12436,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA053",GROUP_DESC:"VA053 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12436,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA053",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA053",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12436,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA053", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12436 ,protection_group_id: -12436, protection_element_id:-12436], primaryKey: false);
      insert('addresses', [ id: 19437, city: "Richmond", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19437, nci_institute_code: "VA054", name: "Richmond Memorial Hospital" ,address_id: 19437,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12437,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA054",GROUP_DESC:"VA054 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12437,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA054",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA054",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12437,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA054", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12437 ,protection_group_id: -12437, protection_element_id:-12437], primaryKey: false);
      insert('addresses', [ id: 19438, city: "Roanoke", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19438, nci_institute_code: "VA055", name: "Oncology Consortium of The Virginias" ,address_id: 19438,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12438,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA055",GROUP_DESC:"VA055 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12438,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA055",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA055",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12438,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA055", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12438 ,protection_group_id: -12438, protection_element_id:-12438], primaryKey: false);
      insert('addresses', [ id: 19439, city: "Fredericksburg", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19439, nci_institute_code: "VA056", name: "Mary Washington Hospital" ,address_id: 19439,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12439,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA056",GROUP_DESC:"VA056 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12439,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA056",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA056",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12439,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA056", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12439 ,protection_group_id: -12439, protection_element_id:-12439], primaryKey: false);
      insert('addresses', [ id: 19440, city: "Winchester", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19440, nci_institute_code: "VA057", name: "Houck and Associates" ,address_id: 19440,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12440,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA057",GROUP_DESC:"VA057 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12440,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA057",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA057",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12440,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA057", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12440 ,protection_group_id: -12440, protection_element_id:-12440], primaryKey: false);
      insert('addresses', [ id: 19441, city: "Charlottesville", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19441, nci_institute_code: "VA059", name: "Center for Cancer Care" ,address_id: 19441,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12441,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA059",GROUP_DESC:"VA059 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12441,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA059",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA059",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12441,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA059", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12441 ,protection_group_id: -12441, protection_element_id:-12441], primaryKey: false);
      insert('addresses', [ id: 19442, city: "Williamsburg", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19442, nci_institute_code: "VA060", name: "Virginia Oncology Associates- Williamsburg" ,address_id: 19442,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12442,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA060",GROUP_DESC:"VA060 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12442,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA060",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA060",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12442,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA060", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12442 ,protection_group_id: -12442, protection_element_id:-12442], primaryKey: false);
      insert('addresses', [ id: 19443, city: "Fairfax", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19443, nci_institute_code: "VA061", name: "CCOP, Fairfax" ,address_id: 19443,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12443,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA061",GROUP_DESC:"VA061 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12443,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA061",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA061",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12443,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA061", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12443 ,protection_group_id: -12443, protection_element_id:-12443], primaryKey: false);
      insert('addresses', [ id: 19444, city: "Roanoke", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19444, nci_institute_code: "VA062", name: "Carilion Center of Roanoke" ,address_id: 19444,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12444,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA062",GROUP_DESC:"VA062 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12444,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA062",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA062",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12444,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA062", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12444 ,protection_group_id: -12444, protection_element_id:-12444], primaryKey: false);
      insert('addresses', [ id: 19445, city: "Harrisonburg", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19445, nci_institute_code: "VA063", name: "Rockingham Memorial Hospital Regional Cancer Center" ,address_id: 19445,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12445,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA063",GROUP_DESC:"VA063 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12445,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA063",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA063",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12445,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA063", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12445 ,protection_group_id: -12445, protection_element_id:-12445], primaryKey: false);
      insert('addresses', [ id: 19446, city: "Norfolk", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19446, nci_institute_code: "VA064", name: "Gynecologists of Tidewater, P.C." ,address_id: 19446,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12446,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA064",GROUP_DESC:"VA064 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12446,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA064",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA064",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12446,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA064", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12446 ,protection_group_id: -12446, protection_element_id:-12446], primaryKey: false);
      insert('addresses', [ id: 19447, city: "Annandale", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19447, nci_institute_code: "VA065", name: "Fairfax-Northern Virginia Hematology-Oncology, P.C." ,address_id: 19447,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12447,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA065",GROUP_DESC:"VA065 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12447,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA065",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA065",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12447,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA065", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12447 ,protection_group_id: -12447, protection_element_id:-12447], primaryKey: false);
    }

    void m18() {
        // all18 (25 terms)
      insert('addresses', [ id: 19448, city: "Winchester", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19448, nci_institute_code: "VA066", name: "Shenandoah Onc Assoc., P.C." ,address_id: 19448,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12448,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA066",GROUP_DESC:"VA066 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12448,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA066",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA066",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12448,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA066", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12448 ,protection_group_id: -12448, protection_element_id:-12448], primaryKey: false);
      insert('addresses', [ id: 19449, city: "Richmond", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19449, nci_institute_code: "VA067", name: "Virginia Cancer Institute" ,address_id: 19449,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12449,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA067",GROUP_DESC:"VA067 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12449,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA067",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA067",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12449,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA067", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12449 ,protection_group_id: -12449, protection_element_id:-12449], primaryKey: false);
      insert('addresses', [ id: 19450, city: "Nasawadox", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19450, nci_institute_code: "VA068", name: "E. Shore Physicians & Surg" ,address_id: 19450,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12450,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA068",GROUP_DESC:"VA068 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12450,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA068",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA068",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12450,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA068", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12450 ,protection_group_id: -12450, protection_element_id:-12450], primaryKey: false);
      insert('addresses', [ id: 19451, city: "Mechanicville", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19451, nci_institute_code: "VA069", name: "Hanover Medical Park" ,address_id: 19451,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12451,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA069",GROUP_DESC:"VA069 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12451,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA069",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA069",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12451,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA069", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12451 ,protection_group_id: -12451, protection_element_id:-12451], primaryKey: false);
      insert('addresses', [ id: 19452, city: "Norfolk", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19452, nci_institute_code: "VA070", name: "Devine-Fiveash Uro., Ltd" ,address_id: 19452,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12452,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA070",GROUP_DESC:"VA070 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12452,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA070",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA070",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12452,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA070", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12452 ,protection_group_id: -12452, protection_element_id:-12452], primaryKey: false);
      insert('addresses', [ id: 19453, city: "Danville", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19453, nci_institute_code: "VA071", name: "Danville Hematology/Oncology" ,address_id: 19453,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12453,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA071",GROUP_DESC:"VA071 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12453,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA071",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA071",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12453,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA071", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12453 ,protection_group_id: -12453, protection_element_id:-12453], primaryKey: false);
      insert('addresses', [ id: 19454, city: "Manassas", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19454, nci_institute_code: "VA073", name: "Prince Williams Hospital" ,address_id: 19454,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12454,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA073",GROUP_DESC:"VA073 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12454,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA073",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA073",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12454,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA073", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12454 ,protection_group_id: -12454, protection_element_id:-12454], primaryKey: false);
      insert('addresses', [ id: 19455, city: "Richmond", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19455, nci_institute_code: "VA074", name: "Medical Specialists, Incorporated" ,address_id: 19455,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12455,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA074",GROUP_DESC:"VA074 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12455,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA074",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA074",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12455,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA074", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12455 ,protection_group_id: -12455, protection_element_id:-12455], primaryKey: false);
      insert('addresses', [ id: 19456, city: "Lynchburg", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19456, nci_institute_code: "VA075", name: "Virginia Baptist Hospital" ,address_id: 19456,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12456,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA075",GROUP_DESC:"VA075 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12456,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA075",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA075",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12456,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA075", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12456 ,protection_group_id: -12456, protection_element_id:-12456], primaryKey: false);
      insert('addresses', [ id: 19457, city: "Farmville", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19457, nci_institute_code: "VA076", name: "Southside Community Hospital" ,address_id: 19457,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12457,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA076",GROUP_DESC:"VA076 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12457,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA076",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA076",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12457,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA076", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12457 ,protection_group_id: -12457, protection_element_id:-12457], primaryKey: false);
      insert('addresses', [ id: 19458, city: "Richmond", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19458, nci_institute_code: "VA077", name: "The Courtyard Medical Office Building" ,address_id: 19458,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12458,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA077",GROUP_DESC:"VA077 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12458,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA077",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA077",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12458,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA077", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12458 ,protection_group_id: -12458, protection_element_id:-12458], primaryKey: false);
      insert('addresses', [ id: 19459, city: "Chesapeake", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19459, nci_institute_code: "VA078", name: "Cancer Specialists of Tidewater Ltd" ,address_id: 19459,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12459,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA078",GROUP_DESC:"VA078 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12459,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA078",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA078",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12459,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA078", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12459 ,protection_group_id: -12459, protection_element_id:-12459], primaryKey: false);
      insert('addresses', [ id: 19460, city: "Norfolk", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19460, nci_institute_code: "VA079", name: "Hematology Oncology Consultants/Tidewater, Limited.," ,address_id: 19460,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12460,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA079",GROUP_DESC:"VA079 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12460,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA079",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA079",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12460,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA079", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12460 ,protection_group_id: -12460, protection_element_id:-12460], primaryKey: false);
      insert('addresses', [ id: 19461, city: "Hampton", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19461, nci_institute_code: "VA084", name: "Virginia Oncology Associates" ,address_id: 19461,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12461,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA084",GROUP_DESC:"VA084 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12461,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA084",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA084",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12461,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA084", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12461 ,protection_group_id: -12461, protection_element_id:-12461], primaryKey: false);
      insert('addresses', [ id: 19462, city: "Fairfax", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19462, nci_institute_code: "VA086", name: "Northern Virginia Oncology Group" ,address_id: 19462,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12462,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA086",GROUP_DESC:"VA086 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12462,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA086",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA086",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12462,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA086", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12462 ,protection_group_id: -12462, protection_element_id:-12462], primaryKey: false);
      insert('addresses', [ id: 19463, city: "Norfolk", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19463, nci_institute_code: "VA087", name: "Norfolk Surgical Group, Ltd." ,address_id: 19463,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12463,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA087",GROUP_DESC:"VA087 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12463,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA087",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA087",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12463,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA087", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12463 ,protection_group_id: -12463, protection_element_id:-12463], primaryKey: false);
      insert('addresses', [ id: 19464, city: "Norfolk", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19464, nci_institute_code: "VA088", name: "Virginia Oncology Associates - Lake Wright" ,address_id: 19464,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12464,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA088",GROUP_DESC:"VA088 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12464,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA088",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA088",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12464,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA088", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12464 ,protection_group_id: -12464, protection_element_id:-12464], primaryKey: false);
      insert('addresses', [ id: 19465, city: "Richmond", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19465, nci_institute_code: "VA090", name: "Bon Secours Commonwealth Gynecologic Oncology" ,address_id: 19465,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12465,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA090",GROUP_DESC:"VA090 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12465,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA090",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA090",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12465,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA090", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12465 ,protection_group_id: -12465, protection_element_id:-12465], primaryKey: false);
      insert('addresses', [ id: 19466, city: "Portsmouth", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19466, nci_institute_code: "VA091", name: "Maryview Medical Center" ,address_id: 19466,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12466,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA091",GROUP_DESC:"VA091 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12466,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA091",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA091",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12466,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA091", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12466 ,protection_group_id: -12466, protection_element_id:-12466], primaryKey: false);
      insert('addresses', [ id: 19467, city: "Pulaski", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19467, nci_institute_code: "VA092", name: "Pulaski Community Hospital" ,address_id: 19467,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12467,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA092",GROUP_DESC:"VA092 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12467,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA092",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA092",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12467,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA092", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12467 ,protection_group_id: -12467, protection_element_id:-12467], primaryKey: false);
      insert('addresses', [ id: 19468, city: "Warrenton", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19468, nci_institute_code: "VA093", name: "Fauquier Hospital" ,address_id: 19468,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12468,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA093",GROUP_DESC:"VA093 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12468,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA093",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA093",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12468,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA093", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12468 ,protection_group_id: -12468, protection_element_id:-12468], primaryKey: false);
      insert('addresses', [ id: 19469, city: "Richmond", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19469, nci_institute_code: "VA094", name: "Henrico Doctor's Hospital" ,address_id: 19469,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12469,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA094",GROUP_DESC:"VA094 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12469,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA094",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA094",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12469,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA094", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12469 ,protection_group_id: -12469, protection_element_id:-12469], primaryKey: false);
      insert('addresses', [ id: 19470, city: "South Hill", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19470, nci_institute_code: "VA095", name: "Community Memorial Health Center" ,address_id: 19470,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12470,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA095",GROUP_DESC:"VA095 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12470,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA095",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA095",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12470,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA095", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12470 ,protection_group_id: -12470, protection_element_id:-12470], primaryKey: false);
      insert('addresses', [ id: 19471, city: "Blacksburgh", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19471, nci_institute_code: "VA096", name: "Medical Associates of South West Virginia" ,address_id: 19471,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12471,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA096",GROUP_DESC:"VA096 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12471,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA096",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA096",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12471,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA096", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12471 ,protection_group_id: -12471, protection_element_id:-12471], primaryKey: false);
      insert('addresses', [ id: 19472, city: "Fairfax", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19472, nci_institute_code: "VA097", name: "Medical Oncology/Hematology Associates of Northern Virginia Ltd." ,address_id: 19472,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12472,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA097",GROUP_DESC:"VA097 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12472,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA097",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA097",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12472,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA097", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12472 ,protection_group_id: -12472, protection_element_id:-12472], primaryKey: false);
    }

    void m19() {
        // all19 (25 terms)
      insert('addresses', [ id: 19473, city: "Portsmouth", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19473, nci_institute_code: "VA098", name: "Delta Hematology Oncology Associates" ,address_id: 19473,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12473,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA098",GROUP_DESC:"VA098 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12473,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA098",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA098",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12473,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA098", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12473 ,protection_group_id: -12473, protection_element_id:-12473], primaryKey: false);
      insert('addresses', [ id: 19474, city: "Richmond", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19474, nci_institute_code: "VA099", name: "Virginia Physicians Incorporated" ,address_id: 19474,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12474,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA099",GROUP_DESC:"VA099 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12474,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA099",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA099",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12474,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA099", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12474 ,protection_group_id: -12474, protection_element_id:-12474], primaryKey: false);
      insert('addresses', [ id: 19475, city: "Fairfax", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19475, nci_institute_code: "VA100", name: "Kaiser Permanente - Fair Oaks Medical Center" ,address_id: 19475,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12475,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA100",GROUP_DESC:"VA100 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12475,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA100",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA100",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12475,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA100", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12475 ,protection_group_id: -12475, protection_element_id:-12475], primaryKey: false);
      insert('addresses', [ id: 19476, city: "Abingdon", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19476, nci_institute_code: "VA101", name: "Cancer Outreach Associates PC" ,address_id: 19476,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12476,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA101",GROUP_DESC:"VA101 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12476,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA101",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA101",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12476,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA101", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12476 ,protection_group_id: -12476, protection_element_id:-12476], primaryKey: false);
      insert('addresses', [ id: 19477, city: "Sterling", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19477, nci_institute_code: "VA102", name: "Loudon Hospital Cancer Center" ,address_id: 19477,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12477,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA102",GROUP_DESC:"VA102 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12477,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA102",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA102",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12477,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA102", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12477 ,protection_group_id: -12477, protection_element_id:-12477], primaryKey: false);
      insert('addresses', [ id: 19478, city: "Christiansburg", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19478, nci_institute_code: "VA103", name: "Oncology and Hematology of South West Virginia" ,address_id: 19478,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12478,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA103",GROUP_DESC:"VA103 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12478,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA103",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA103",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12478,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA103", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12478 ,protection_group_id: -12478, protection_element_id:-12478], primaryKey: false);
      insert('addresses', [ id: 19479, city: "Norton", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19479, nci_institute_code: "VA105", name: "Southwest VA Regional Cancer Center" ,address_id: 19479,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12479,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA105",GROUP_DESC:"VA105 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12479,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA105",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA105",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12479,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA105", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12479 ,protection_group_id: -12479, protection_element_id:-12479], primaryKey: false);
      insert('addresses', [ id: 19480, city: "Radford", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19480, nci_institute_code: "VA106", name: "Onco/Hema Assoc of Southwest VA" ,address_id: 19480,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12480,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA106",GROUP_DESC:"VA106 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12480,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA106",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA106",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12480,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA106", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12480 ,protection_group_id: -12480, protection_element_id:-12480], primaryKey: false);
      insert('addresses', [ id: 19481, city: "Reston", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19481, nci_institute_code: "VA107", name: "Medical Oncology and Hematology of Northern Virginia" ,address_id: 19481,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12481,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA107",GROUP_DESC:"VA107 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12481,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA107",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA107",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12481,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA107", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12481 ,protection_group_id: -12481, protection_element_id:-12481], primaryKey: false);
      insert('addresses', [ id: 19482, city: "Alexandria", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19482, nci_institute_code: "VA109", name: "Fairfax Northern VA Hema\\Oncology" ,address_id: 19482,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12482,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA109",GROUP_DESC:"VA109 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12482,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA109",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA109",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12482,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA109", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12482 ,protection_group_id: -12482, protection_element_id:-12482], primaryKey: false);
      insert('addresses', [ id: 19483, city: "Fairfax", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19483, nci_institute_code: "VA110", name: "Inova Fair Oaks Hospital" ,address_id: 19483,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12483,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA110",GROUP_DESC:"VA110 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12483,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA110",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA110",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12483,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA110", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12483 ,protection_group_id: -12483, protection_element_id:-12483], primaryKey: false);
      insert('addresses', [ id: 19484, city: "Fairfax", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19484, nci_institute_code: "VA111", name: "Fairfax Northern Virginia Hematology and Oncology PC" ,address_id: 19484,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12484,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA111",GROUP_DESC:"VA111 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12484,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA111",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA111",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12484,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA111", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12484 ,protection_group_id: -12484, protection_element_id:-12484], primaryKey: false);
      insert('addresses', [ id: 19485, city: "Richmond", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19485, nci_institute_code: "VA112", name: "Richmond Radiation Oncology Center" ,address_id: 19485,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12485,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA112",GROUP_DESC:"VA112 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12485,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA112",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA112",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12485,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA112", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12485 ,protection_group_id: -12485, protection_element_id:-12485], primaryKey: false);
      insert('addresses', [ id: 19486, city: "Newport News", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19486, nci_institute_code: "VA113", name: "Oyster Point Surgical Associates" ,address_id: 19486,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12486,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA113",GROUP_DESC:"VA113 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12486,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA113",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA113",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12486,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA113", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12486 ,protection_group_id: -12486, protection_element_id:-12486], primaryKey: false);
      insert('addresses', [ id: 19487, city: "Danville", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19487, nci_institute_code: "VA115", name: "Cancer Center of the Piedmont, Inc" ,address_id: 19487,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12487,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA115",GROUP_DESC:"VA115 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12487,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA115",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA115",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12487,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA115", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12487 ,protection_group_id: -12487, protection_element_id:-12487], primaryKey: false);
      insert('addresses', [ id: 19488, city: "Fairfax", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19488, nci_institute_code: "VA116", name: "Virginia Surgery Associates, P.C." ,address_id: 19488,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12488,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA116",GROUP_DESC:"VA116 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12488,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA116",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA116",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12488,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA116", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12488 ,protection_group_id: -12488, protection_element_id:-12488], primaryKey: false);
      insert('addresses', [ id: 19489, city: "Christiansburg", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19489, nci_institute_code: "VA117", name: "Carilion New River Valley Medical Center" ,address_id: 19489,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12489,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA117",GROUP_DESC:"VA117 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12489,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA117",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA117",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12489,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA117", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12489 ,protection_group_id: -12489, protection_element_id:-12489], primaryKey: false);
      insert('addresses', [ id: 19490, city: "Fishersville", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19490, nci_institute_code: "VA118", name: "Hematology Oncology Patient Enterprise (H.O.P.E)" ,address_id: 19490,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12490,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA118",GROUP_DESC:"VA118 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12490,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA118",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA118",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12490,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA118", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12490 ,protection_group_id: -12490, protection_element_id:-12490], primaryKey: false);
      insert('addresses', [ id: 19491, city: "Culpeper", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19491, nci_institute_code: "VA119", name: "Culpeper Regional Hospital" ,address_id: 19491,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12491,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA119",GROUP_DESC:"VA119 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12491,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA119",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA119",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12491,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA119", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12491 ,protection_group_id: -12491, protection_element_id:-12491], primaryKey: false);
      insert('addresses', [ id: 19492, city: "Woodbridge", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19492, nci_institute_code: "VA120", name: "Fairfax-Northern VA Hematology & Oncology, PC" ,address_id: 19492,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12492,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA120",GROUP_DESC:"VA120 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12492,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA120",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA120",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12492,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA120", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12492 ,protection_group_id: -12492, protection_element_id:-12492], primaryKey: false);
      insert('addresses', [ id: 19493, city: "Roanoke", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19493, nci_institute_code: "VA122", name: "Breast Care Specialists/Blue Ridge PC" ,address_id: 19493,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12493,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA122",GROUP_DESC:"VA122 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12493,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA122",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA122",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12493,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA122", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12493 ,protection_group_id: -12493, protection_element_id:-12493], primaryKey: false);
      insert('addresses', [ id: 19494, city: "Annandale", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19494, nci_institute_code: "VA123", name: "Northern Virginia Pelvic Surgery Associates" ,address_id: 19494,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12494,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA123",GROUP_DESC:"VA123 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12494,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA123",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA123",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12494,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA123", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12494 ,protection_group_id: -12494, protection_element_id:-12494], primaryKey: false);
      insert('addresses', [ id: 19495, city: "Norfolk", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19495, nci_institute_code: "VA124", name: "Virginia Hematology and Oncology, PLLC" ,address_id: 19495,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12495,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA124",GROUP_DESC:"VA124 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12495,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA124",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA124",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12495,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA124", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12495 ,protection_group_id: -12495, protection_element_id:-12495], primaryKey: false);
      insert('addresses', [ id: 19496, city: "Richlands", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19496, nci_institute_code: "VA125", name: "Virginia Oncology Care" ,address_id: 19496,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12496,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA125",GROUP_DESC:"VA125 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12496,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA125",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA125",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12496,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA125", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12496 ,protection_group_id: -12496, protection_element_id:-12496], primaryKey: false);
      insert('addresses', [ id: 19497, city: "Virginia Beach", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19497, nci_institute_code: "VA126", name: "Devine-Tidewater Urology" ,address_id: 19497,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12497,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA126",GROUP_DESC:"VA126 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12497,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA126",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA126",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12497,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA126", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12497 ,protection_group_id: -12497, protection_element_id:-12497], primaryKey: false);
    }

    void m20() {
        // all20 (25 terms)
      insert('addresses', [ id: 19498, city: "Norfolk", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19498, nci_institute_code: "VA127", name: "Devine-Tidewater Urology-Norfolk" ,address_id: 19498,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12498,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA127",GROUP_DESC:"VA127 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12498,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA127",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA127",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12498,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA127", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12498 ,protection_group_id: -12498, protection_element_id:-12498], primaryKey: false);
      insert('addresses', [ id: 19499, city: "Norfolk", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19499, nci_institute_code: "VA128", name: "Urology of Virginia PC" ,address_id: 19499,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12499,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA128",GROUP_DESC:"VA128 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12499,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA128",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA128",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12499,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA128", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12499 ,protection_group_id: -12499, protection_element_id:-12499], primaryKey: false);
      insert('addresses', [ id: 19500, city: "Suffolk", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19500, nci_institute_code: "VA129", name: "Virginia Oncology Associates- Suffolk" ,address_id: 19500,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12500,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA129",GROUP_DESC:"VA129 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12500,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA129",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA129",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12500,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA129", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12500 ,protection_group_id: -12500, protection_element_id:-12500], primaryKey: false);
      insert('addresses', [ id: 19501, city: "Norfolk", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19501, nci_institute_code: "VA130", name: "Breast Care Specialists, P.C." ,address_id: 19501,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12501,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA130",GROUP_DESC:"VA130 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12501,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA130",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA130",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12501,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA130", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12501 ,protection_group_id: -12501, protection_element_id:-12501], primaryKey: false);
      insert('addresses', [ id: 19502, city: "Roanoke", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19502, nci_institute_code: "VA131", name: "Carilion Gynecologic Oncology Associates" ,address_id: 19502,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12502,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA131",GROUP_DESC:"VA131 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12502,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA131",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA131",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12502,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA131", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12502 ,protection_group_id: -12502, protection_element_id:-12502], primaryKey: false);
      insert('addresses', [ id: 19503, city: "Salem", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19503, nci_institute_code: "VA132", name: "Salem Surgical Associates, P.C." ,address_id: 19503,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12503,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA132",GROUP_DESC:"VA132 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12503,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA132",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA132",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12503,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA132", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12503 ,protection_group_id: -12503, protection_element_id:-12503], primaryKey: false);
      insert('addresses', [ id: 19504, city: "Manassas", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19504, nci_institute_code: "VA133", name: "Fairfax Northern Virginia Hematology and Oncology, PC" ,address_id: 19504,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12504,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA133",GROUP_DESC:"VA133 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12504,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA133",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA133",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12504,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA133", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12504 ,protection_group_id: -12504, protection_element_id:-12504], primaryKey: false);
      insert('addresses', [ id: 19505, city: "Salem", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19505, nci_institute_code: "VA135", name: "Oncology and Hematology Associates of Southwest Virginia Inc" ,address_id: 19505,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12505,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA135",GROUP_DESC:"VA135 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12505,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA135",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA135",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12505,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA135", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12505 ,protection_group_id: -12505, protection_element_id:-12505], primaryKey: false);
      insert('addresses', [ id: 19506, city: "Newport News", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19506, nci_institute_code: "VA136", name: "Peninsula Cancer Institute" ,address_id: 19506,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12506,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA136",GROUP_DESC:"VA136 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12506,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA136",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA136",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12506,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA136", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12506 ,protection_group_id: -12506, protection_element_id:-12506], primaryKey: false);
      insert('addresses', [ id: 19507, city: "Williamsburg", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19507, nci_institute_code: "VA137", name: "Peninsula Cancer Institute" ,address_id: 19507,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12507,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA137",GROUP_DESC:"VA137 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12507,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA137",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA137",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12507,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA137", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12507 ,protection_group_id: -12507, protection_element_id:-12507], primaryKey: false);
      insert('addresses', [ id: 19508, city: "Arlington", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19508, nci_institute_code: "VA138", name: "Arlington Fairfax Hematology & Oncology PC" ,address_id: 19508,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12508,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA138",GROUP_DESC:"VA138 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12508,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA138",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA138",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12508,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA138", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12508 ,protection_group_id: -12508, protection_element_id:-12508], primaryKey: false);
      insert('addresses', [ id: 19509, city: "Fairfax", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19509, nci_institute_code: "VA139", name: "Fairfax Northern Virginia Hematology and Oncology PC" ,address_id: 19509,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12509,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA139",GROUP_DESC:"VA139 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12509,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA139",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA139",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12509,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA139", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12509 ,protection_group_id: -12509, protection_element_id:-12509], primaryKey: false);
      insert('addresses', [ id: 19510, city: "Fredericksburg", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19510, nci_institute_code: "VA140", name: "Fredericksburg Oncology Inc" ,address_id: 19510,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12510,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA140",GROUP_DESC:"VA140 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12510,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA140",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA140",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12510,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA140", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12510 ,protection_group_id: -12510, protection_element_id:-12510], primaryKey: false);
      insert('addresses', [ id: 19511, city: "Fairfax", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19511, nci_institute_code: "VA141", name: "George Mason University" ,address_id: 19511,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12511,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA141",GROUP_DESC:"VA141 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12511,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA141",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA141",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12511,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA141", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12511 ,protection_group_id: -12511, protection_element_id:-12511], primaryKey: false);
      insert('addresses', [ id: 19512, city: "Gloucester", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19512, nci_institute_code: "VA143", name: "Riverside Middle Peninsula Cancer Center" ,address_id: 19512,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12512,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA143",GROUP_DESC:"VA143 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12512,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA143",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA143",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12512,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA143", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12512 ,protection_group_id: -12512, protection_element_id:-12512], primaryKey: false);
      insert('addresses', [ id: 19513, city: "Roanoke", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19513, nci_institute_code: "VA144", name: "OPUS Clinical Reaserch LLC" ,address_id: 19513,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12513,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA144",GROUP_DESC:"VA144 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12513,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA144",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA144",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12513,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA144", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12513 ,protection_group_id: -12513, protection_element_id:-12513], primaryKey: false);
      insert('addresses', [ id: 19514, city: "Fairfax", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19514, nci_institute_code: "VA145", name: "Fairfax Family Practice" ,address_id: 19514,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12514,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA145",GROUP_DESC:"VA145 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12514,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA145",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA145",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12514,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA145", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12514 ,protection_group_id: -12514, protection_element_id:-12514], primaryKey: false);
      insert('addresses', [ id: 19515, city: "Abingdon", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19515, nci_institute_code: "VA146", name: "Johnston Memorial Hospital" ,address_id: 19515,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12515,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA146",GROUP_DESC:"VA146 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12515,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA146",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA146",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12515,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA146", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12515 ,protection_group_id: -12515, protection_element_id:-12515], primaryKey: false);
      insert('addresses', [ id: 19516, city: "Williamsburg", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19516, nci_institute_code: "VA147", name: "Williamsburg Radiation Therapy Center" ,address_id: 19516,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12516,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA147",GROUP_DESC:"VA147 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12516,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA147",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA147",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12516,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA147", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12516 ,protection_group_id: -12516, protection_element_id:-12516], primaryKey: false);
      insert('addresses', [ id: 19517, city: "Reston", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19517, nci_institute_code: "VA148", name: "Reston Hospital Center" ,address_id: 19517,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12517,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA148",GROUP_DESC:"VA148 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12517,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA148",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA148",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12517,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA148", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12517 ,protection_group_id: -12517, protection_element_id:-12517], primaryKey: false);
      insert('addresses', [ id: 19518, city: "Newport News", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19518, nci_institute_code: "VA149", name: "Surgical Oncology Associates" ,address_id: 19518,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12518,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA149",GROUP_DESC:"VA149 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12518,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA149",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA149",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12518,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA149", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12518 ,protection_group_id: -12518, protection_element_id:-12518], primaryKey: false);
      insert('addresses', [ id: 19519, city: "Harrisonburg", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19519, nci_institute_code: "VA150", name: "Rockingham Memorial Hospital" ,address_id: 19519,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12519,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA150",GROUP_DESC:"VA150 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12519,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA150",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA150",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12519,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA150", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12519 ,protection_group_id: -12519, protection_element_id:-12519], primaryKey: false);
      insert('addresses', [ id: 19520, city: "Alexandria", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19520, nci_institute_code: "VA151", name: "CLG Associates LLC" ,address_id: 19520,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12520,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA151",GROUP_DESC:"VA151 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12520,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA151",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA151",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12520,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA151", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12520 ,protection_group_id: -12520, protection_element_id:-12520], primaryKey: false);
      insert('addresses', [ id: 19521, city: "Roanoke", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19521, nci_institute_code: "VA152", name: "Carilion Pediatric Surgery" ,address_id: 19521,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12521,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA152",GROUP_DESC:"VA152 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12521,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA152",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA152",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12521,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA152", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12521 ,protection_group_id: -12521, protection_element_id:-12521], primaryKey: false);
      insert('addresses', [ id: 19522, city: "Blacksburg", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19522, nci_institute_code: "VA153", name: "Edward Via Virginia College of Osteopathic Medicine" ,address_id: 19522,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12522,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA153",GROUP_DESC:"VA153 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12522,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA153",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA153",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12522,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA153", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12522 ,protection_group_id: -12522, protection_element_id:-12522], primaryKey: false);
    }

    void m21() {
        // all21 (25 terms)
      insert('addresses', [ id: 19523, city: "Fairfax", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19523, nci_institute_code: "VA154", name: "NoVa Oncology Associates PC" ,address_id: 19523,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12523,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA154",GROUP_DESC:"VA154 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12523,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA154",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA154",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12523,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA154", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12523 ,protection_group_id: -12523, protection_element_id:-12523], primaryKey: false);
      insert('addresses', [ id: 19524, city: "Virginia Beach", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19524, nci_institute_code: "VA155", name: "Virginia Oncology Associates" ,address_id: 19524,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12524,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA155",GROUP_DESC:"VA155 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12524,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA155",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA155",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12524,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA155", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12524 ,protection_group_id: -12524, protection_element_id:-12524], primaryKey: false);
      insert('addresses', [ id: 19525, city: "Newport News", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19525, nci_institute_code: "VA156", name: "Hampton Roads Surgical Specialists" ,address_id: 19525,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12525,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA156",GROUP_DESC:"VA156 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12525,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA156",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA156",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12525,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA156", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12525 ,protection_group_id: -12525, protection_element_id:-12525], primaryKey: false);
      insert('addresses', [ id: 19526, city: "Richmond", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19526, nci_institute_code: "VA157", name: "Surgical Associates of Richmond" ,address_id: 19526,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12526,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA157",GROUP_DESC:"VA157 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12526,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA157",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA157",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12526,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA157", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12526 ,protection_group_id: -12526, protection_element_id:-12526], primaryKey: false);
      insert('addresses', [ id: 19527, city: "Newport News", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19527, nci_institute_code: "VA158", name: "Riverside Cancer Care Center Radiation Oncology" ,address_id: 19527,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12527,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA158",GROUP_DESC:"VA158 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12527,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA158",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA158",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12527,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA158", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12527 ,protection_group_id: -12527, protection_element_id:-12527], primaryKey: false);
      insert('addresses', [ id: 19528, city: "Williamsburg", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19528, nci_institute_code: "VA159", name: "Williamsburg Surgery" ,address_id: 19528,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12528,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA159",GROUP_DESC:"VA159 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12528,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA159",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA159",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12528,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA159", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12528 ,protection_group_id: -12528, protection_element_id:-12528], primaryKey: false);
      insert('addresses', [ id: 19529, city: "Newport News", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19529, nci_institute_code: "VA160", name: "Riverside Gynecologic Oncology" ,address_id: 19529,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12529,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA160",GROUP_DESC:"VA160 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12529,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA160",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA160",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12529,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA160", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12529 ,protection_group_id: -12529, protection_element_id:-12529], primaryKey: false);
      insert('addresses', [ id: 19530, city: "Alexandria", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19530, nci_institute_code: "VA161", name: "C3: Colorectal Cancer Coalition" ,address_id: 19530,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12530,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA161",GROUP_DESC:"VA161 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12530,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA161",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA161",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12530,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA161", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12530 ,protection_group_id: -12530, protection_element_id:-12530], primaryKey: false);
      insert('addresses', [ id: 19531, city: "Norfolk", state_code: "VA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19531, nci_institute_code: "VA162", name: "Neurosurgical Associates PC" ,address_id: 19531,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12531,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA162",GROUP_DESC:"VA162 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12531,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VA162",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VA162",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12531,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VA162", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12531 ,protection_group_id: -12531, protection_element_id:-12531], primaryKey: false);
      insert('addresses', [ id: 19532, country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19532, nci_institute_code: "VALG", name: "Veteran's Administration Lung Group" ,address_id: 19532,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12532,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VALG",GROUP_DESC:"VALG group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12532,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VALG",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VALG",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12532,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VALG", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12532 ,protection_group_id: -12532, protection_element_id:-12532], primaryKey: false);
      insert('addresses', [ id: 19533, country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19533, nci_institute_code: "VASOG", name: "Veteran's Administration Surgical Oncology Group" ,address_id: 19533,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12533,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VASOG",GROUP_DESC:"VASOG group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12533,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VASOG",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VASOG",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12533,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VASOG", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12533 ,protection_group_id: -12533, protection_element_id:-12533], primaryKey: false);
      insert('addresses', [ id: 19534, city: "White River Junction", state_code: "VT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19534, nci_institute_code: "VT001", name: "White River Junction Veteran Administration Medical Center" ,address_id: 19534,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12534,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT001",GROUP_DESC:"VT001 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12534,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VT001",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VT001",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12534,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT001", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12534 ,protection_group_id: -12534, protection_element_id:-12534], primaryKey: false);
      insert('addresses', [ id: 19535, city: "Burlington", state_code: "VT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19535, nci_institute_code: "VT002", name: "Medical Center Hospital of Vermont" ,address_id: 19535,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12535,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT002",GROUP_DESC:"VT002 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12535,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VT002",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VT002",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12535,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT002", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12535 ,protection_group_id: -12535, protection_element_id:-12535], primaryKey: false);
      insert('addresses', [ id: 19536, city: "Burlington", state_code: "VT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19536, nci_institute_code: "VT003", name: "Fletcher Allen Health Care" ,address_id: 19536,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12536,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT003",GROUP_DESC:"VT003 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12536,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VT003",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VT003",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12536,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT003", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12536 ,protection_group_id: -12536, protection_element_id:-12536], primaryKey: false);
      insert('addresses', [ id: 19537, city: "Burlington", state_code: "VT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19537, nci_institute_code: "VT004", name: "University of Vermont" ,address_id: 19537,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12537,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT004",GROUP_DESC:"VT004 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12537,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VT004",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VT004",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12537,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT004", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12537 ,protection_group_id: -12537, protection_element_id:-12537], primaryKey: false);
      insert('addresses', [ id: 19538, city: "Bennington", state_code: "VT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19538, nci_institute_code: "VT005", name: "Southwestern Vermont Medical Center" ,address_id: 19538,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12538,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT005",GROUP_DESC:"VT005 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12538,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VT005",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VT005",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12538,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT005", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12538 ,protection_group_id: -12538, protection_element_id:-12538], primaryKey: false);
      insert('addresses', [ id: 19539, city: "Rutland", state_code: "VT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19539, nci_institute_code: "VT006", name: "Rutland Regional Medical Center" ,address_id: 19539,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12539,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT006",GROUP_DESC:"VT006 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12539,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VT006",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VT006",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12539,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT006", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12539 ,protection_group_id: -12539, protection_element_id:-12539], primaryKey: false);
      insert('addresses', [ id: 19540, city: "Colchester", state_code: "VT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19540, nci_institute_code: "VT007", name: "Vermont Cancer Center" ,address_id: 19540,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12540,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT007",GROUP_DESC:"VT007 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12540,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VT007",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VT007",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12540,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT007", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12540 ,protection_group_id: -12540, protection_element_id:-12540], primaryKey: false);
      insert('addresses', [ id: 19541, city: "Bennington", state_code: "VT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19541, nci_institute_code: "VT008", name: "Southwestern Oncology Research Department" ,address_id: 19541,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12541,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT008",GROUP_DESC:"VT008 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12541,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VT008",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VT008",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12541,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT008", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12541 ,protection_group_id: -12541, protection_element_id:-12541], primaryKey: false);
      insert('addresses', [ id: 19542, city: "Brattleboro", state_code: "VT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19542, nci_institute_code: "VT009", name: "Brattleboro Memorial Hospital" ,address_id: 19542,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12542,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT009",GROUP_DESC:"VT009 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12542,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VT009",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VT009",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12542,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT009", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12542 ,protection_group_id: -12542, protection_element_id:-12542], primaryKey: false);
      insert('addresses', [ id: 19543, city: "Springfield", state_code: "VT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19543, nci_institute_code: "VT010", name: "Springfield Hospital" ,address_id: 19543,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12543,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT010",GROUP_DESC:"VT010 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12543,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VT010",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VT010",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12543,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT010", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12543 ,protection_group_id: -12543, protection_element_id:-12543], primaryKey: false);
      insert('addresses', [ id: 19544, city: "St. Johnsbury", state_code: "VT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19544, nci_institute_code: "VT011", name: "Northeastern" ,address_id: 19544,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12544,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT011",GROUP_DESC:"VT011 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12544,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VT011",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VT011",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12544,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT011", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12544 ,protection_group_id: -12544, protection_element_id:-12544], primaryKey: false);
      insert('addresses', [ id: 19545, city: "Barre", state_code: "VT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19545, nci_institute_code: "VT012", name: "Central Vermont Hospital" ,address_id: 19545,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12545,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT012",GROUP_DESC:"VT012 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12545,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VT012",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VT012",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12545,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT012", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12545 ,protection_group_id: -12545, protection_element_id:-12545], primaryKey: false);
      insert('addresses', [ id: 19546, city: "Barre", state_code: "VT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19546, nci_institute_code: "VT013", name: "Central Vermont Medical Center" ,address_id: 19546,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12546,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT013",GROUP_DESC:"VT013 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12546,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VT013",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VT013",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12546,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT013", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12546 ,protection_group_id: -12546, protection_element_id:-12546], primaryKey: false);
      insert('addresses', [ id: 19547, city: "Bennington", state_code: "VT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19547, nci_institute_code: "VT014", name: "Associates In Urology, P.C." ,address_id: 19547,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12547,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT014",GROUP_DESC:"VT014 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12547,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VT014",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VT014",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12547,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT014", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12547 ,protection_group_id: -12547, protection_element_id:-12547], primaryKey: false);
    }

    void m22() {
        // all22 (25 terms)
      insert('addresses', [ id: 19548, city: "Randolph", state_code: "VT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19548, nci_institute_code: "VT015", name: "Gifford Medical Center" ,address_id: 19548,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12548,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT015",GROUP_DESC:"VT015 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12548,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VT015",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VT015",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12548,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT015", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12548 ,protection_group_id: -12548, protection_element_id:-12548], primaryKey: false);
      insert('addresses', [ id: 19549, city: "Morrisville", state_code: "VT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19549, nci_institute_code: "VT016", name: "Copley Hospital" ,address_id: 19549,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12549,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT016",GROUP_DESC:"VT016 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12549,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VT016",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VT016",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12549,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT016", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12549 ,protection_group_id: -12549, protection_element_id:-12549], primaryKey: false);
      insert('addresses', [ id: 19550, city: "Water River Junction", state_code: "VT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19550, nci_institute_code: "VT017", name: "Veterans Administration Medical Center" ,address_id: 19550,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12550,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT017",GROUP_DESC:"VT017 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12550,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VT017",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VT017",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12550,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT017", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12550 ,protection_group_id: -12550, protection_element_id:-12550], primaryKey: false);
      insert('addresses', [ id: 19551, city: "Burlington", state_code: "VT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19551, nci_institute_code: "VT018", name: "University of Vermont" ,address_id: 19551,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12551,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT018",GROUP_DESC:"VT018 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12551,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VT018",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VT018",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12551,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT018", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12551 ,protection_group_id: -12551, protection_element_id:-12551], primaryKey: false);
      insert('addresses', [ id: 19552, city: "Berlin", state_code: "VT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19552, nci_institute_code: "VT019", name: "Mountainview Medical" ,address_id: 19552,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12552,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT019",GROUP_DESC:"VT019 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12552,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VT019",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VT019",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12552,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT019", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12552 ,protection_group_id: -12552, protection_element_id:-12552], primaryKey: false);
      insert('addresses', [ id: 19553, city: "Colchester", state_code: "VT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19553, nci_institute_code: "VT020", name: "Green Mountain Urology, Inc" ,address_id: 19553,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12553,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT020",GROUP_DESC:"VT020 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12553,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VT020",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VT020",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12553,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT020", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12553 ,protection_group_id: -12553, protection_element_id:-12553], primaryKey: false);
      insert('addresses', [ id: 19554, city: "Newport", state_code: "VT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19554, nci_institute_code: "VT021", name: "North Country Health" ,address_id: 19554,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12554,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT021",GROUP_DESC:"VT021 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12554,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VT021",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VT021",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12554,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT021", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12554 ,protection_group_id: -12554, protection_element_id:-12554], primaryKey: false);
      insert('addresses', [ id: 19555, city: "Windsor", state_code: "VT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19555, nci_institute_code: "VT022", name: "Mount Ascutney Hospital and Health Center" ,address_id: 19555,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12555,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT022",GROUP_DESC:"VT022 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12555,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VT022",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VT022",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12555,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT022", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12555 ,protection_group_id: -12555, protection_element_id:-12555], primaryKey: false);
      insert('addresses', [ id: 19556, city: "Bennington", state_code: "VT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19556, nci_institute_code: "VT023", name: "Southwestern Vermont Health Care Oncolgy Associates" ,address_id: 19556,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12556,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT023",GROUP_DESC:"VT023 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12556,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VT023",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VT023",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12556,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT023", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12556 ,protection_group_id: -12556, protection_element_id:-12556], primaryKey: false);
      insert('addresses', [ id: 19557, city: "South Burlington", state_code: "VT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19557, nci_institute_code: "VT024", name: "Lake Champlain Gynecologic Oncology, P.C." ,address_id: 19557,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12557,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT024",GROUP_DESC:"VT024 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12557,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VT024",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VT024",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12557,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT024", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12557 ,protection_group_id: -12557, protection_element_id:-12557], primaryKey: false);
      insert('addresses', [ id: 19558, city: "Burlington", state_code: "VT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19558, nci_institute_code: "VT025", name: "Fletcher Allen Health Care-Medical Center Campus" ,address_id: 19558,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12558,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT025",GROUP_DESC:"VT025 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12558,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VT025",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VT025",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12558,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT025", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12558 ,protection_group_id: -12558, protection_element_id:-12558], primaryKey: false);
      insert('addresses', [ id: 19559, city: "Morrisville", state_code: "VT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19559, nci_institute_code: "VT026", name: "Morrisville Family Healthcare" ,address_id: 19559,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12559,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT026",GROUP_DESC:"VT026 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12559,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VT026",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VT026",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12559,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT026", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12559 ,protection_group_id: -12559, protection_element_id:-12559], primaryKey: false);
      insert('addresses', [ id: 19560, city: "South Burlington", state_code: "VT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19560, nci_institute_code: "VT027", name: "Flecher Allen Health Care/University of Vermont College of Medicine" ,address_id: 19560,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12560,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT027",GROUP_DESC:"VT027 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12560,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VT027",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VT027",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12560,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT027", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12560 ,protection_group_id: -12560, protection_element_id:-12560], primaryKey: false);
      insert('addresses', [ id: 19561, city: "Saint Johnsbury", state_code: "VT", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19561, nci_institute_code: "VT029", name: "Norris Cotton Cancer Center" ,address_id: 19561,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12561,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT029",GROUP_DESC:"VT029 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12561,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.VT029",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.VT029",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12561,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.VT029", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12561 ,protection_group_id: -12561, protection_element_id:-12561], primaryKey: false);
      insert('addresses', [ id: 19562, city: "Bellevue", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19562, nci_institute_code: "WA001", name: "Overlake Hospital Medical Center" ,address_id: 19562,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12562,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA001",GROUP_DESC:"WA001 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12562,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA001",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA001",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12562,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA001", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12562 ,protection_group_id: -12562, protection_element_id:-12562], primaryKey: false);
      insert('addresses', [ id: 19563, city: "Edmonds", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19563, nci_institute_code: "WA002", name: "Stevens Memorial" ,address_id: 19563,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12563,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA002",GROUP_DESC:"WA002 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12563,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA002",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA002",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12563,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA002", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12563 ,protection_group_id: -12563, protection_element_id:-12563], primaryKey: false);
      insert('addresses', [ id: 19564, city: "Kirkland", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19564, nci_institute_code: "WA003", name: "Evergreen Hospital Medical Center" ,address_id: 19564,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12564,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA003",GROUP_DESC:"WA003 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12564,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA003",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA003",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12564,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA003", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12564 ,protection_group_id: -12564, protection_element_id:-12564], primaryKey: false);
      insert('addresses', [ id: 19565, city: "Renton", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19565, nci_institute_code: "WA004", name: "Valley Medical Center" ,address_id: 19565,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12565,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA004",GROUP_DESC:"WA004 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12565,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA004",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA004",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12565,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA004", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12565 ,protection_group_id: -12565, protection_element_id:-12565], primaryKey: false);
      insert('addresses', [ id: 19566, city: "Seattle", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19566, nci_institute_code: "WA005", name: "Virginia Mason Hospital" ,address_id: 19566,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12566,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA005",GROUP_DESC:"WA005 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12566,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA005",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA005",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12566,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA005", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12566 ,protection_group_id: -12566, protection_element_id:-12566], primaryKey: false);
      insert('addresses', [ id: 19567, city: "Seattle", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19567, nci_institute_code: "WA007", name: "Swedish Hospital Medical Center" ,address_id: 19567,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12567,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA007",GROUP_DESC:"WA007 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12567,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA007",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA007",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12567,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA007", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12567 ,protection_group_id: -12567, protection_element_id:-12567], primaryKey: false);
      insert('addresses', [ id: 19568, city: "Seattle", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19568, nci_institute_code: "WA008", name: "Fred Hutchinson Cancer Research Center" ,address_id: 19568,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12568,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA008",GROUP_DESC:"WA008 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12568,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA008",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA008",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12568,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA008", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12568 ,protection_group_id: -12568, protection_element_id:-12568], primaryKey: false);
      insert('addresses', [ id: 19569, city: "Seattle", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19569, nci_institute_code: "WA009", name: "Ballard Community Hospital" ,address_id: 19569,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12569,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA009",GROUP_DESC:"WA009 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12569,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA009",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA009",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12569,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA009", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12569 ,protection_group_id: -12569, protection_element_id:-12569], primaryKey: false);
      insert('addresses', [ id: 19570, city: "Seattle", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19570, nci_institute_code: "WA010", name: "Veterans Administration Center, Seattle" ,address_id: 19570,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12570,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA010",GROUP_DESC:"WA010 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12570,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA010",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA010",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12570,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA010", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12570 ,protection_group_id: -12570, protection_element_id:-12570], primaryKey: false);
      insert('addresses', [ id: 19571, city: "Seattle", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19571, nci_institute_code: "WA011", name: "Group Health Cooperative of Puget Sound Oncology Consortium" ,address_id: 19571,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12571,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA011",GROUP_DESC:"WA011 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12571,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA011",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA011",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12571,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA011", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12571 ,protection_group_id: -12571, protection_element_id:-12571], primaryKey: false);
      insert('addresses', [ id: 19572, city: "Seattle", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19572, nci_institute_code: "WA012", name: "Seattle Public Health Hospital" ,address_id: 19572,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12572,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA012",GROUP_DESC:"WA012 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12572,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA012",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA012",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12572,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA012", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12572 ,protection_group_id: -12572, protection_element_id:-12572], primaryKey: false);
    }

    void m23() {
        // all23 (25 terms)
      insert('addresses', [ id: 19573, city: "Seattle", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19573, nci_institute_code: "WA013", name: "Central Hospital" ,address_id: 19573,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12573,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA013",GROUP_DESC:"WA013 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12573,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA013",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA013",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12573,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA013", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12573 ,protection_group_id: -12573, protection_element_id:-12573], primaryKey: false);
      insert('addresses', [ id: 19574, city: "Seattle", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19574, nci_institute_code: "WA014", name: "Childrens Orthopedic Hospital" ,address_id: 19574,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12574,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA014",GROUP_DESC:"WA014 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12574,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA014",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA014",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12574,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA014", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12574 ,protection_group_id: -12574, protection_element_id:-12574], primaryKey: false);
      insert('addresses', [ id: 19575, city: "Seatlle", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19575, nci_institute_code: "WA015", name: "Neorx Corporation" ,address_id: 19575,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12575,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA015",GROUP_DESC:"WA015 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12575,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA015",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA015",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12575,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA015", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12575 ,protection_group_id: -12575, protection_element_id:-12575], primaryKey: false);
      insert('addresses', [ id: 19576, city: "Seattle", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19576, nci_institute_code: "WA017", name: "Northwest Hospital" ,address_id: 19576,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12576,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA017",GROUP_DESC:"WA017 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12576,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA017",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA017",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12576,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA017", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12576 ,protection_group_id: -12576, protection_element_id:-12576], primaryKey: false);
      insert('addresses', [ id: 19577, city: "Seattle", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19577, nci_institute_code: "WA018", name: "Pacific Medical Center" ,address_id: 19577,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12577,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA018",GROUP_DESC:"WA018 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12577,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA018",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA018",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12577,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA018", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12577 ,protection_group_id: -12577, protection_element_id:-12577], primaryKey: false);
      insert('addresses', [ id: 19578, city: "Seattle", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19578, nci_institute_code: "WA019", name: "Highline Community Hospital" ,address_id: 19578,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12578,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA019",GROUP_DESC:"WA019 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12578,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA019",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA019",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12578,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA019", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12578 ,protection_group_id: -12578, protection_element_id:-12578], primaryKey: false);
      insert('addresses', [ id: 19579, city: "Seattle", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19579, nci_institute_code: "WA020", name: "University of Washington Medical Center" ,address_id: 19579,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12579,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA020",GROUP_DESC:"WA020 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12579,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA020",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA020",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12579,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA020", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12579 ,protection_group_id: -12579, protection_element_id:-12579], primaryKey: false);
      insert('addresses', [ id: 19580, city: "Everett", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19580, nci_institute_code: "WA021", name: "Providence General Medical Center" ,address_id: 19580,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12580,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA021",GROUP_DESC:"WA021 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12580,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA021",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA021",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12580,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA021", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12580 ,protection_group_id: -12580, protection_element_id:-12580], primaryKey: false);
      insert('addresses', [ id: 19581, city: "Everett", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19581, nci_institute_code: "WA022", name: "General Hospital of Everett" ,address_id: 19581,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12581,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA022",GROUP_DESC:"WA022 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12581,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA022",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA022",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12581,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA022", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12581 ,protection_group_id: -12581, protection_element_id:-12581], primaryKey: false);
      insert('addresses', [ id: 19582, city: "Bellingham", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19582, nci_institute_code: "WA023", name: "St. Lukes General Hospital" ,address_id: 19582,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12582,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA023",GROUP_DESC:"WA023 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12582,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA023",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA023",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12582,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA023", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12582 ,protection_group_id: -12582, protection_element_id:-12582], primaryKey: false);
      insert('addresses', [ id: 19583, city: "Mt. Vernon", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19583, nci_institute_code: "WA024", name: "Skagit Valley Hospital" ,address_id: 19583,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12583,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA024",GROUP_DESC:"WA024 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12583,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA024",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA024",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12583,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA024", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12583 ,protection_group_id: -12583, protection_element_id:-12583], primaryKey: false);
      insert('addresses', [ id: 19584, city: "Sedro-Wooley", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19584, nci_institute_code: "WA025", name: "United General Hospital" ,address_id: 19584,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12584,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA025",GROUP_DESC:"WA025 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12584,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA025",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA025",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12584,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA025", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12584 ,protection_group_id: -12584, protection_element_id:-12584], primaryKey: false);
      insert('addresses', [ id: 19585, city: "Bremerton", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19585, nci_institute_code: "WA026", name: "Harrison Medical Center" ,address_id: 19585,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12585,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA026",GROUP_DESC:"WA026 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12585,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA026",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA026",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12585,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA026", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12585 ,protection_group_id: -12585, protection_element_id:-12585], primaryKey: false);
      insert('addresses', [ id: 19586, city: "Tacoma", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19586, nci_institute_code: "WA027", name: "Madigan Army Medical Center" ,address_id: 19586,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12586,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA027",GROUP_DESC:"WA027 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12586,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA027",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA027",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12586,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA027", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12586 ,protection_group_id: -12586, protection_element_id:-12586], primaryKey: false);
      insert('addresses', [ id: 19587, city: "Port Angeles", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19587, nci_institute_code: "WA028", name: "Olympic Memorial Hospital" ,address_id: 19587,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12587,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA028",GROUP_DESC:"WA028 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12587,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA028",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA028",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12587,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA028", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12587 ,protection_group_id: -12587, protection_element_id:-12587], primaryKey: false);
      insert('addresses', [ id: 19588, city: "Puyallup", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19588, nci_institute_code: "WA029", name: "Good Samaritan Community Hospital" ,address_id: 19588,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12588,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA029",GROUP_DESC:"WA029 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12588,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA029",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA029",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12588,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA029", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12588 ,protection_group_id: -12588, protection_element_id:-12588], primaryKey: false);
      insert('addresses', [ id: 19589, city: "Tacoma", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19589, nci_institute_code: "WA030", name: "Tacoma General Hospital" ,address_id: 19589,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12589,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA030",GROUP_DESC:"WA030 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12589,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA030",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA030",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12589,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA030", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12589 ,protection_group_id: -12589, protection_element_id:-12589], primaryKey: false);
      insert('addresses', [ id: 19590, city: "Tacoma", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19590, nci_institute_code: "WA031", name: "Mary Bridge Children's Hospital and Health Center" ,address_id: 19590,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12590,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA031",GROUP_DESC:"WA031 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12590,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA031",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA031",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12590,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA031", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12590 ,protection_group_id: -12590, protection_element_id:-12590], primaryKey: false);
      insert('addresses', [ id: 19591, city: "Tacoma", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19591, nci_institute_code: "WA032", name: "Humana Hospital Tacoma" ,address_id: 19591,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12591,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA032",GROUP_DESC:"WA032 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12591,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA032",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA032",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12591,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA032", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12591 ,protection_group_id: -12591, protection_element_id:-12591], primaryKey: false);
      insert('addresses', [ id: 19592, city: "Tacoma", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19592, nci_institute_code: "WA033", name: "Veterans Administration Medical Center, Tacoma" ,address_id: 19592,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12592,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA033",GROUP_DESC:"WA033 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12592,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA033",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA033",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12592,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA033", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12592 ,protection_group_id: -12592, protection_element_id:-12592], primaryKey: false);
      insert('addresses', [ id: 19593, city: "Aberdeen", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19593, nci_institute_code: "WA034", name: "Grays Harbor Community Hospital" ,address_id: 19593,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12593,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA034",GROUP_DESC:"WA034 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12593,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA034",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA034",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12593,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA034", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12593 ,protection_group_id: -12593, protection_element_id:-12593], primaryKey: false);
      insert('addresses', [ id: 19594, city: "Longview", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19594, nci_institute_code: "WA036", name: "Saint John Medical Center" ,address_id: 19594,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12594,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA036",GROUP_DESC:"WA036 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12594,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA036",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA036",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12594,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA036", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12594 ,protection_group_id: -12594, protection_element_id:-12594], primaryKey: false);
      insert('addresses', [ id: 19595, city: "Vancouver", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19595, nci_institute_code: "WA037", name: "Southwest Washington Medical Center" ,address_id: 19595,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12595,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA037",GROUP_DESC:"WA037 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12595,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA037",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA037",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12595,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA037", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12595 ,protection_group_id: -12595, protection_element_id:-12595], primaryKey: false);
      insert('addresses', [ id: 19596, city: "Wenatchee", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19596, nci_institute_code: "WA039", name: "Central Washington Hospital" ,address_id: 19596,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12596,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA039",GROUP_DESC:"WA039 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12596,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA039",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA039",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12596,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA039", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12596 ,protection_group_id: -12596, protection_element_id:-12596], primaryKey: false);
      insert('addresses', [ id: 19597, city: "Omak", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19597, nci_institute_code: "WA041", name: "Mid-Valley Hospital" ,address_id: 19597,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12597,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA041",GROUP_DESC:"WA041 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12597,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA041",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA041",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12597,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA041", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12597 ,protection_group_id: -12597, protection_element_id:-12597], primaryKey: false);
    }

    void m24() {
        // all24 (25 terms)
      insert('addresses', [ id: 19598, city: "Yakima", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19598, nci_institute_code: "WA042", name: "Providence Yakima Medical Center" ,address_id: 19598,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12598,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA042",GROUP_DESC:"WA042 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12598,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA042",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA042",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12598,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA042", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12598 ,protection_group_id: -12598, protection_element_id:-12598], primaryKey: false);
      insert('addresses', [ id: 19599, city: "Yakima", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19599, nci_institute_code: "WA043", name: "Yakima Valley Memorial Hospital North Star Lodge Cancer Center" ,address_id: 19599,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12599,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA043",GROUP_DESC:"WA043 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12599,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA043",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA043",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12599,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA043", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12599 ,protection_group_id: -12599, protection_element_id:-12599], primaryKey: false);
      insert('addresses', [ id: 19600, city: "Spokane", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19600, nci_institute_code: "WA046", name: "Rockwood Clinic" ,address_id: 19600,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12600,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA046",GROUP_DESC:"WA046 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12600,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA046",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA046",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12600,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA046", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12600 ,protection_group_id: -12600, protection_element_id:-12600], primaryKey: false);
      insert('addresses', [ id: 19601, city: "Spokane", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19601, nci_institute_code: "WA047", name: "Department of Veterans Affairs Medical Center, Spokane" ,address_id: 19601,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12601,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA047",GROUP_DESC:"WA047 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12601,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA047",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA047",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12601,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA047", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12601 ,protection_group_id: -12601, protection_element_id:-12601], primaryKey: false);
      insert('addresses', [ id: 19602, city: "Spokane", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19602, nci_institute_code: "WA048", name: "Holy Family Hospital" ,address_id: 19602,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12602,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA048",GROUP_DESC:"WA048 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12602,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA048",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA048",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12602,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA048", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12602 ,protection_group_id: -12602, protection_element_id:-12602], primaryKey: false);
      insert('addresses', [ id: 19603, city: "Spokane", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19603, nci_institute_code: "WA049", name: "Deaconess Medical Center" ,address_id: 19603,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12603,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA049",GROUP_DESC:"WA049 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12603,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA049",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA049",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12603,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA049", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12603 ,protection_group_id: -12603, protection_element_id:-12603], primaryKey: false);
      insert('addresses', [ id: 19604, city: "Spokane", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19604, nci_institute_code: "WA050", name: "Sacred Heart Medical Center" ,address_id: 19604,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12604,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA050",GROUP_DESC:"WA050 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12604,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA050",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA050",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12604,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA050", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12604 ,protection_group_id: -12604, protection_element_id:-12604], primaryKey: false);
      insert('addresses', [ id: 19605, city: "Walla Walla", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19605, nci_institute_code: "WA053", name: "Saint Mary Regional Cancer Center" ,address_id: 19605,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12605,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA053",GROUP_DESC:"WA053 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12605,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA053",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA053",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12605,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA053", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12605 ,protection_group_id: -12605, protection_element_id:-12605], primaryKey: false);
      insert('addresses', [ id: 19606, city: "Seattle", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19606, nci_institute_code: "WA054", name: "Virginia Mason Medical Center" ,address_id: 19606,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12606,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA054",GROUP_DESC:"WA054 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12606,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA054",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA054",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12606,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA054", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12606 ,protection_group_id: -12606, protection_element_id:-12606], primaryKey: false);
      insert('addresses', [ id: 19607, city: "Tacoma", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19607, nci_institute_code: "WA055", name: "Saint Joseph Medical Center" ,address_id: 19607,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12607,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA055",GROUP_DESC:"WA055 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12607,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA055",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA055",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12607,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA055", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12607 ,protection_group_id: -12607, protection_element_id:-12607], primaryKey: false);
      insert('addresses', [ id: 19608, city: "Tacoma", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19608, nci_institute_code: "WA056", name: "Multicare Health System" ,address_id: 19608,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12608,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA056",GROUP_DESC:"WA056 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12608,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA056",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA056",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12608,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA056", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12608 ,protection_group_id: -12608, protection_element_id:-12608], primaryKey: false);
      insert('addresses', [ id: 19609, city: "Seattle", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19609, nci_institute_code: "WA058", name: "First Hill Women's Clinic" ,address_id: 19609,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12609,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA058",GROUP_DESC:"WA058 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12609,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA058",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA058",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12609,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA058", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12609 ,protection_group_id: -12609, protection_element_id:-12609], primaryKey: false);
      insert('addresses', [ id: 19610, city: "Seattle", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19610, nci_institute_code: "WA059", name: "Group Health Cooperative" ,address_id: 19610,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12610,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA059",GROUP_DESC:"WA059 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12610,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA059",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA059",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12610,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA059", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12610 ,protection_group_id: -12610, protection_element_id:-12610], primaryKey: false);
      insert('addresses', [ id: 19611, city: "Seattle", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19611, nci_institute_code: "WA061", name: "Children's Hospital and Regional Medical Center" ,address_id: 19611,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12611,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA061",GROUP_DESC:"WA061 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12611,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA061",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA061",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12611,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA061", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12611 ,protection_group_id: -12611, protection_element_id:-12611], primaryKey: false);
      insert('addresses', [ id: 19612, city: "Seatle", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19612, nci_institute_code: "WA062", name: "Providence Medical Center" ,address_id: 19612,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12612,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA062",GROUP_DESC:"WA062 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12612,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA062",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA062",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12612,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA062", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12612 ,protection_group_id: -12612, protection_element_id:-12612], primaryKey: false);
      insert('addresses', [ id: 19613, city: "Vancouver", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19613, nci_institute_code: "WA063", name: "Vancouver Medical Office" ,address_id: 19613,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12613,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA063",GROUP_DESC:"WA063 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12613,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA063",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA063",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12613,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA063", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12613 ,protection_group_id: -12613, protection_element_id:-12613], primaryKey: false);
      insert('addresses', [ id: 19614, city: "Tacoma", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19614, nci_institute_code: "WA064", name: "Northwest CCOP" ,address_id: 19614,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12614,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA064",GROUP_DESC:"WA064 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12614,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA064",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA064",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12614,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA064", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12614 ,protection_group_id: -12614, protection_element_id:-12614], primaryKey: false);
      insert('addresses', [ id: 19615, city: "Vancouver", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19615, nci_institute_code: "WA065", name: "Vancouver Clinic" ,address_id: 19615,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12615,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA065",GROUP_DESC:"WA065 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12615,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA065",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA065",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12615,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA065", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12615 ,protection_group_id: -12615, protection_element_id:-12615], primaryKey: false);
      insert('addresses', [ id: 19616, city: "Tacoma", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19616, nci_institute_code: "WA067", name: "Tacoma Radiation Center" ,address_id: 19616,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12616,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA067",GROUP_DESC:"WA067 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12616,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA067",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA067",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12616,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA067", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12616 ,protection_group_id: -12616, protection_element_id:-12616], primaryKey: false);
      insert('addresses', [ id: 19617, city: "Bellingham", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19617, nci_institute_code: "WA069", name: "Saint Joseph Hospital" ,address_id: 19617,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12617,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA069",GROUP_DESC:"WA069 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12617,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA069",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA069",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12617,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA069", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12617 ,protection_group_id: -12617, protection_element_id:-12617], primaryKey: false);
      insert('addresses', [ id: 19618, city: "Puyallup", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19618, nci_institute_code: "WA070", name: "Rainier Physicians - Northwest Medical Specialties" ,address_id: 19618,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12618,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA070",GROUP_DESC:"WA070 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12618,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA070",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA070",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12618,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA070", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12618 ,protection_group_id: -12618, protection_element_id:-12618], primaryKey: false);
      insert('addresses', [ id: 19619, city: "Wenatchee", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19619, nci_institute_code: "WA071", name: "Wenatchee Valley Medical Center" ,address_id: 19619,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12619,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA071",GROUP_DESC:"WA071 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12619,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA071",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA071",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12619,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA071", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12619 ,protection_group_id: -12619, protection_element_id:-12619], primaryKey: false);
      insert('addresses', [ id: 19620, city: "Seattle", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19620, nci_institute_code: "WA072", name: "Virginia Mason CCOP" ,address_id: 19620,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12620,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA072",GROUP_DESC:"WA072 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12620,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA072",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA072",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12620,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA072", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12620 ,protection_group_id: -12620, protection_element_id:-12620], primaryKey: false);
      insert('addresses', [ id: 19621, city: "Seattle", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19621, nci_institute_code: "WA073", name: "The Polyclinic" ,address_id: 19621,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12621,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA073",GROUP_DESC:"WA073 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12621,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA073",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA073",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12621,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA073", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12621 ,protection_group_id: -12621, protection_element_id:-12621], primaryKey: false);
      insert('addresses', [ id: 19622, city: "Edmonds", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19622, nci_institute_code: "WA074", name: "Puget Sound Cancer Center" ,address_id: 19622,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12622,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA074",GROUP_DESC:"WA074 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12622,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA074",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA074",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12622,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA074", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12622 ,protection_group_id: -12622, protection_element_id:-12622], primaryKey: false);
    }

    void m25() {
        // all25 (25 terms)
      insert('addresses', [ id: 19623, city: "Renton", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19623, nci_institute_code: "WA075", name: "Southlake Clinic" ,address_id: 19623,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12623,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA075",GROUP_DESC:"WA075 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12623,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA075",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA075",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12623,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA075", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12623 ,protection_group_id: -12623, protection_element_id:-12623], primaryKey: false);
      insert('addresses', [ id: 19624, city: "Olympia", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19624, nci_institute_code: "WA076", name: "Western Washington Cancer Treatment Center" ,address_id: 19624,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12624,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA076",GROUP_DESC:"WA076 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12624,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA076",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA076",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12624,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA076", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12624 ,protection_group_id: -12624, protection_element_id:-12624], primaryKey: false);
      insert('addresses', [ id: 19625, city: "Olympia", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19625, nci_institute_code: "WA077", name: "Olympia Radiological Association Limited.," ,address_id: 19625,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12625,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA077",GROUP_DESC:"WA077 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12625,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA077",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA077",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12625,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA077", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12625 ,protection_group_id: -12625, protection_element_id:-12625], primaryKey: false);
      insert('addresses', [ id: 19626, city: "Tacoma", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19626, nci_institute_code: "WA078", name: "Medical Oncology/Hematology Association" ,address_id: 19626,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12626,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA078",GROUP_DESC:"WA078 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12626,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA078",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA078",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12626,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA078", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12626 ,protection_group_id: -12626, protection_element_id:-12626], primaryKey: false);
      insert('addresses', [ id: 19627, city: "Olympia", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19627, nci_institute_code: "WA079", name: "Memorial Clinic" ,address_id: 19627,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12627,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA079",GROUP_DESC:"WA079 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12627,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA079",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA079",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12627,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA079", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12627 ,protection_group_id: -12627, protection_element_id:-12627], primaryKey: false);
      insert('addresses', [ id: 19628, city: "Bellingham", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19628, nci_institute_code: "WA080", name: "Northwest Gastroenterology Clinic" ,address_id: 19628,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12628,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA080",GROUP_DESC:"WA080 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12628,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA080",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA080",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12628,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA080", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12628 ,protection_group_id: -12628, protection_element_id:-12628], primaryKey: false);
      insert('addresses', [ id: 19629, city: "Tacoma", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19629, nci_institute_code: "WA081", name: "Group Health Cooperative- Tacoma Medical Center" ,address_id: 19629,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12629,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA081",GROUP_DESC:"WA081 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12629,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA081",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA081",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12629,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA081", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12629 ,protection_group_id: -12629, protection_element_id:-12629], primaryKey: false);
      insert('addresses', [ id: 19630, city: "Longview", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19630, nci_institute_code: "WA082", name: "Longview Surgery" ,address_id: 19630,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12630,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA082",GROUP_DESC:"WA082 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12630,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA082",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA082",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12630,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA082", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12630 ,protection_group_id: -12630, protection_element_id:-12630], primaryKey: false);
      insert('addresses', [ id: 19631, city: "Spokane", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19631, nci_institute_code: "WA083", name: "Spokane Oncology Hematology Associates" ,address_id: 19631,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12631,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA083",GROUP_DESC:"WA083 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12631,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA083",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA083",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12631,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA083", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12631 ,protection_group_id: -12631, protection_element_id:-12631], primaryKey: false);
      insert('addresses', [ id: 19632, city: "Yakima", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19632, nci_institute_code: "WA084", name: "Internal Medicine Associates/Yakima" ,address_id: 19632,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12632,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA084",GROUP_DESC:"WA084 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12632,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA084",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA084",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12632,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA084", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12632 ,protection_group_id: -12632, protection_element_id:-12632], primaryKey: false);
      insert('addresses', [ id: 19633, city: "Auburn", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19633, nci_institute_code: "WA085", name: "Auburn Regional Medical Center" ,address_id: 19633,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12633,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA085",GROUP_DESC:"WA085 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12633,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA085",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA085",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12633,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA085", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12633 ,protection_group_id: -12633, protection_element_id:-12633], primaryKey: false);
      insert('addresses', [ id: 19634, city: "Tacoma", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19634, nci_institute_code: "WA086", name: "Allenmore Hospital" ,address_id: 19634,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12634,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA086",GROUP_DESC:"WA086 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12634,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA086",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA086",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12634,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA086", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12634 ,protection_group_id: -12634, protection_element_id:-12634], primaryKey: false);
      insert('addresses', [ id: 19635, city: "Olympia", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19635, nci_institute_code: "WA087", name: "Capital Medical Center" ,address_id: 19635,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12635,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA087",GROUP_DESC:"WA087 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12635,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA087",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA087",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12635,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA087", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12635 ,protection_group_id: -12635, protection_element_id:-12635], primaryKey: false);
      insert('addresses', [ id: 19636, city: "Olympia", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19636, nci_institute_code: "WA088", name: "Providence - Saint Peter Hospital" ,address_id: 19636,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12636,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA088",GROUP_DESC:"WA088 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12636,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA088",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA088",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12636,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA088", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12636 ,protection_group_id: -12636, protection_element_id:-12636], primaryKey: false);
      insert('addresses', [ id: 19637, city: "Tacoma", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19637, nci_institute_code: "WA089", name: "Saint Clare Hospital" ,address_id: 19637,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12637,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA089",GROUP_DESC:"WA089 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12637,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA089",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA089",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12637,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA089", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12637 ,protection_group_id: -12637, protection_element_id:-12637], primaryKey: false);
      insert('addresses', [ id: 19638, city: "Federal Way", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19638, nci_institute_code: "WA090", name: "Saint Francis Hospital" ,address_id: 19638,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12638,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA090",GROUP_DESC:"WA090 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12638,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA090",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA090",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12638,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA090", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12638 ,protection_group_id: -12638, protection_element_id:-12638], primaryKey: false);
      insert('addresses', [ id: 19639, city: "Redmond", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19639, nci_institute_code: "WA091", name: "Group Health Cooperative" ,address_id: 19639,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12639,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA091",GROUP_DESC:"WA091 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12639,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA091",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA091",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12639,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA091", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12639 ,protection_group_id: -12639, protection_element_id:-12639], primaryKey: false);
      insert('addresses', [ id: 19640, city: "Seattle", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19640, nci_institute_code: "WA092", name: "Harborview Medical Center" ,address_id: 19640,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12640,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA092",GROUP_DESC:"WA092 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12640,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA092",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA092",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12640,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA092", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12640 ,protection_group_id: -12640, protection_element_id:-12640], primaryKey: false);
      insert('addresses', [ id: 19641, city: "Centralia", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19641, nci_institute_code: "WA093", name: "Providence Centralia Hospital" ,address_id: 19641,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12641,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA093",GROUP_DESC:"WA093 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12641,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA093",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA093",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12641,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA093", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12641 ,protection_group_id: -12641, protection_element_id:-12641], primaryKey: false);
      insert('addresses', [ id: 19642, city: "Burien", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19642, nci_institute_code: "WA094", name: "Southwest Tumor Institute" ,address_id: 19642,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12642,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA094",GROUP_DESC:"WA094 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12642,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA094",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA094",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12642,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA094", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12642 ,protection_group_id: -12642, protection_element_id:-12642], primaryKey: false);
      insert('addresses', [ id: 19643, city: "Vancouver", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19643, nci_institute_code: "WA096", name: "Urology Clinic of Southwest Washington PS" ,address_id: 19643,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12643,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA096",GROUP_DESC:"WA096 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12643,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA096",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA096",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12643,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA096", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12643 ,protection_group_id: -12643, protection_element_id:-12643], primaryKey: false);
      insert('addresses', [ id: 19644, city: "Spokane", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19644, nci_institute_code: "WA097", name: "Cancer Care Northwest" ,address_id: 19644,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12644,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA097",GROUP_DESC:"WA097 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12644,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA097",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA097",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12644,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA097", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12644 ,protection_group_id: -12644, protection_element_id:-12644], primaryKey: false);
      insert('addresses', [ id: 19645, city: "Burien", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19645, nci_institute_code: "WA099", name: "Highline Community Hospital" ,address_id: 19645,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12645,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA099",GROUP_DESC:"WA099 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12645,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA099",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA099",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12645,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA099", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12645 ,protection_group_id: -12645, protection_element_id:-12645], primaryKey: false);
      insert('addresses', [ id: 19646, city: "Kirkland", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19646, nci_institute_code: "WA100", name: "Cascade Cancer Center" ,address_id: 19646,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12646,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA100",GROUP_DESC:"WA100 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12646,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA100",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA100",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12646,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA100", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12646 ,protection_group_id: -12646, protection_element_id:-12646], primaryKey: false);
      insert('addresses', [ id: 19647, city: "Spokane", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19647, nci_institute_code: "WA101", name: "Cancer Care Northwest - Spokane South" ,address_id: 19647,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12647,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA101",GROUP_DESC:"WA101 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12647,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA101",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA101",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12647,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA101", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12647 ,protection_group_id: -12647, protection_element_id:-12647], primaryKey: false);
    }

    void m26() {
        // all26 (25 terms)
      insert('addresses', [ id: 19648, city: "Seattle", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19648, nci_institute_code: "WA102", name: "Seattle Cancer Care Alliance" ,address_id: 19648,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12648,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA102",GROUP_DESC:"WA102 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12648,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA102",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA102",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12648,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA102", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12648 ,protection_group_id: -12648, protection_element_id:-12648], primaryKey: false);
      insert('addresses', [ id: 19649, city: "Everett", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19649, nci_institute_code: "WA103", name: "Everett Clinic Ctr for Cancer Care" ,address_id: 19649,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12649,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA103",GROUP_DESC:"WA103 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12649,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA103",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA103",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12649,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA103", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12649 ,protection_group_id: -12649, protection_element_id:-12649], primaryKey: false);
      insert('addresses', [ id: 19650, city: "Bellingham", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19650, nci_institute_code: "WA104", name: "Madrona Medical Group PC" ,address_id: 19650,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12650,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA104",GROUP_DESC:"WA104 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12650,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA104",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA104",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12650,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA104", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12650 ,protection_group_id: -12650, protection_element_id:-12650], primaryKey: false);
      insert('addresses', [ id: 19651, city: "Bellevue", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19651, nci_institute_code: "WA105", name: "Eastside Oncology/Hematology" ,address_id: 19651,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12651,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA105",GROUP_DESC:"WA105 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12651,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA105",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA105",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12651,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA105", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12651 ,protection_group_id: -12651, protection_element_id:-12651], primaryKey: false);
      insert('addresses', [ id: 19652, city: "Yakima", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19652, nci_institute_code: "WA106", name: "North Star Lodge Cancer Center" ,address_id: 19652,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12652,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA106",GROUP_DESC:"WA106 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12652,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA106",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA106",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12652,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA106", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12652 ,protection_group_id: -12652, protection_element_id:-12652], primaryKey: false);
      insert('addresses', [ id: 19653, city: "Olympia", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19653, nci_institute_code: "WA107", name: "Western Washington Oncology, INC. P.S." ,address_id: 19653,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12653,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA107",GROUP_DESC:"WA107 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12653,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA107",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA107",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12653,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA107", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12653 ,protection_group_id: -12653, protection_element_id:-12653], primaryKey: false);
      insert('addresses', [ id: 19654, city: "Vancouver", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19654, nci_institute_code: "WA108", name: "Northwest Cancer Specialists" ,address_id: 19654,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12654,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA108",GROUP_DESC:"WA108 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12654,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA108",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA108",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12654,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA108", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12654 ,protection_group_id: -12654, protection_element_id:-12654], primaryKey: false);
      insert('addresses', [ id: 19655, city: "Seattle", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19655, nci_institute_code: "WA109", name: "Pacific Gynecology Specialists" ,address_id: 19655,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12655,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA109",GROUP_DESC:"WA109 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12655,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA109",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA109",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12655,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA109", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12655 ,protection_group_id: -12655, protection_element_id:-12655], primaryKey: false);
      insert('addresses', [ id: 19656, city: "Seattle", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19656, nci_institute_code: "WA110", name: "Pacific Medical Center-First Hill" ,address_id: 19656,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12656,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA110",GROUP_DESC:"WA110 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12656,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA110",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA110",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12656,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA110", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12656 ,protection_group_id: -12656, protection_element_id:-12656], primaryKey: false);
      insert('addresses', [ id: 19657, city: "Vancouver", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19657, nci_institute_code: "WA111", name: "Kaiser Permanente-Vancouver" ,address_id: 19657,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12657,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA111",GROUP_DESC:"WA111 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12657,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA111",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA111",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12657,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA111", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12657 ,protection_group_id: -12657, protection_element_id:-12657], primaryKey: false);
      insert('addresses', [ id: 19658, city: "Seattle", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19658, nci_institute_code: "WA112", name: "Tumor Institute at Swedish Hospital" ,address_id: 19658,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12658,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA112",GROUP_DESC:"WA112 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12658,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA112",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA112",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12658,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA112", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12658 ,protection_group_id: -12658, protection_element_id:-12658], primaryKey: false);
      insert('addresses', [ id: 19659, city: "Bremerton", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19659, nci_institute_code: "WA113", name: "Olympic Hematology & Oncology Associates" ,address_id: 19659,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12659,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA113",GROUP_DESC:"WA113 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12659,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA113",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA113",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12659,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA113", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12659 ,protection_group_id: -12659, protection_element_id:-12659], primaryKey: false);
      insert('addresses', [ id: 19660, city: "Mount Vernon", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19660, nci_institute_code: "WA114", name: "Skagit Valley Hospital Regional Cancer Care Center" ,address_id: 19660,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12660,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA114",GROUP_DESC:"WA114 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12660,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA114",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA114",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12660,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA114", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12660 ,protection_group_id: -12660, protection_element_id:-12660], primaryKey: false);
      insert('addresses', [ id: 19661, city: "Vnacouver", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19661, nci_institute_code: "WA115", name: "Alliance Lung Cancer Center" ,address_id: 19661,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12661,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA115",GROUP_DESC:"WA115 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12661,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA115",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA115",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12661,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA115", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12661 ,protection_group_id: -12661, protection_element_id:-12661], primaryKey: false);
      insert('addresses', [ id: 19662, city: "Seattle", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19662, nci_institute_code: "WA116", name: "PhenoPath Laboratories, PLLC" ,address_id: 19662,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12662,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA116",GROUP_DESC:"WA116 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12662,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA116",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA116",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12662,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA116", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12662 ,protection_group_id: -12662, protection_element_id:-12662], primaryKey: false);
      insert('addresses', [ id: 19663, city: "Spokane", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19663, nci_institute_code: "WA117", name: "Medical Oncology Associates" ,address_id: 19663,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12663,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA117",GROUP_DESC:"WA117 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12663,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA117",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA117",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12663,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA117", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12663 ,protection_group_id: -12663, protection_element_id:-12663], primaryKey: false);
      insert('addresses', [ id: 19664, city: "Vancouver", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19664, nci_institute_code: "WA118", name: "The Women's Clinic/Vancouver, P.S." ,address_id: 19664,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12664,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA118",GROUP_DESC:"WA118 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12664,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA118",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA118",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12664,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA118", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12664 ,protection_group_id: -12664, protection_element_id:-12664], primaryKey: false);
      insert('addresses', [ id: 19665, city: "Covington", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19665, nci_institute_code: "WA119", name: "Medical Oncology Clinic" ,address_id: 19665,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12665,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA119",GROUP_DESC:"WA119 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12665,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA119",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA119",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12665,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA119", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12665 ,protection_group_id: -12665, protection_element_id:-12665], primaryKey: false);
      insert('addresses', [ id: 19666, city: "Tacoma", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19666, nci_institute_code: "WA120", name: "Northwest Medical Specialties, PLLC" ,address_id: 19666,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12666,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA120",GROUP_DESC:"WA120 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12666,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA120",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA120",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12666,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA120", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12666 ,protection_group_id: -12666, protection_element_id:-12666], primaryKey: false);
      insert('addresses', [ id: 19667, city: "Sequim", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19667, nci_institute_code: "WA121", name: "Olympic Medical Cancer Care Center" ,address_id: 19667,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12667,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA121",GROUP_DESC:"WA121 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12667,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA121",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA121",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12667,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA121", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12667 ,protection_group_id: -12667, protection_element_id:-12667], primaryKey: false);
      insert('addresses', [ id: 19668, city: "Yakima", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19668, nci_institute_code: "WA122", name: "Cancer Institutes of Washington PLLC" ,address_id: 19668,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12668,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA122",GROUP_DESC:"WA122 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12668,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA122",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA122",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12668,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA122", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12668 ,protection_group_id: -12668, protection_element_id:-12668], primaryKey: false);
      insert('addresses', [ id: 19669, city: "Sequim", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19669, nci_institute_code: "WA123", name: "Jamestown Family Health Clinic" ,address_id: 19669,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12669,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA123",GROUP_DESC:"WA123 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12669,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA123",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA123",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12669,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA123", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12669 ,protection_group_id: -12669, protection_element_id:-12669], primaryKey: false);
      insert('addresses', [ id: 19670, city: "Bellingham", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19670, nci_institute_code: "WA124", name: "Cascadia Clinical Trials" ,address_id: 19670,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12670,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA124",GROUP_DESC:"WA124 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12670,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA124",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA124",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12670,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA124", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12670 ,protection_group_id: -12670, protection_element_id:-12670], primaryKey: false);
      insert('addresses', [ id: 19671, city: "Olympia", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19671, nci_institute_code: "WA126", name: "Radiation Oncology" ,address_id: 19671,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12671,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA126",GROUP_DESC:"WA126 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12671,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA126",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA126",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12671,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA126", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12671 ,protection_group_id: -12671, protection_element_id:-12671], primaryKey: false);
      insert('addresses', [ id: 19672, city: "Spokane", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19672, nci_institute_code: "WA127", name: "Associated Surgeons" ,address_id: 19672,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12672,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA127",GROUP_DESC:"WA127 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12672,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA127",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA127",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12672,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA127", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12672 ,protection_group_id: -12672, protection_element_id:-12672], primaryKey: false);
    }

    void m27() {
        // all27 (25 terms)
      insert('addresses', [ id: 19673, city: "Bellevue", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19673, nci_institute_code: "WA129", name: "Northwest Breast Association" ,address_id: 19673,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12673,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA129",GROUP_DESC:"WA129 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12673,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA129",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA129",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12673,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA129", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12673 ,protection_group_id: -12673, protection_element_id:-12673], primaryKey: false);
      insert('addresses', [ id: 19674, city: "Kent", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19674, nci_institute_code: "WA130", name: "Central Admixture Pharmacy" ,address_id: 19674,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12674,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA130",GROUP_DESC:"WA130 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12674,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA130",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA130",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12674,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA130", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12674 ,protection_group_id: -12674, protection_element_id:-12674], primaryKey: false);
      insert('addresses', [ id: 19675, city: "Bellingham", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19675, nci_institute_code: "WA131", name: "Bellingham Breast Center" ,address_id: 19675,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12675,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA131",GROUP_DESC:"WA131 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12675,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA131",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA131",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12675,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA131", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12675 ,protection_group_id: -12675, protection_element_id:-12675], primaryKey: false);
      insert('addresses', [ id: 19676, city: "Bellingham", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19676, nci_institute_code: "WA132", name: "North Sound Family Medicine" ,address_id: 19676,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12676,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA132",GROUP_DESC:"WA132 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12676,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA132",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA132",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12676,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA132", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12676 ,protection_group_id: -12676, protection_element_id:-12676], primaryKey: false);
      insert('addresses', [ id: 19677, city: "Olympia", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19677, nci_institute_code: "WA133", name: "Western Institutional Review Board" ,address_id: 19677,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12677,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA133",GROUP_DESC:"WA133 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12677,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA133",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA133",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12677,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA133", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12677 ,protection_group_id: -12677, protection_element_id:-12677], primaryKey: false);
      insert('addresses', [ id: 19678, city: "Seattle", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19678, nci_institute_code: "WA134", name: "Swedish Cancer Institute at Northwest Hospital and Medical Center" ,address_id: 19678,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12678,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA134",GROUP_DESC:"WA134 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12678,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA134",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA134",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12678,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA134", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12678 ,protection_group_id: -12678, protection_element_id:-12678], primaryKey: false);
      insert('addresses', [ id: 19679, city: "Seattle", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19679, nci_institute_code: "WA135", name: "VA Puget Sound Health Care System" ,address_id: 19679,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12679,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA135",GROUP_DESC:"WA135 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12679,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA135",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA135",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12679,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA135", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12679 ,protection_group_id: -12679, protection_element_id:-12679], primaryKey: false);
      insert('addresses', [ id: 19680, city: "Spokane", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19680, nci_institute_code: "WA136", name: "Riverfront Medical Center/GH" ,address_id: 19680,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12680,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA136",GROUP_DESC:"WA136 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12680,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA136",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA136",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12680,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA136", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12680 ,protection_group_id: -12680, protection_element_id:-12680], primaryKey: false);
      insert('addresses', [ id: 19681, city: "Lacey", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19681, nci_institute_code: "WA137", name: "Western Washington Oncology, Inc., P.S." ,address_id: 19681,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12681,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA137",GROUP_DESC:"WA137 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12681,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA137",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA137",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12681,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA137", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12681 ,protection_group_id: -12681, protection_element_id:-12681], primaryKey: false);
      insert('addresses', [ id: 19682, city: "Seattle", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19682, nci_institute_code: "WA138", name: "Seattle Urological Associates" ,address_id: 19682,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12682,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA138",GROUP_DESC:"WA138 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12682,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA138",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA138",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12682,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA138", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12682 ,protection_group_id: -12682, protection_element_id:-12682], primaryKey: false);
      insert('addresses', [ id: 19683, city: "Seattle", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19683, nci_institute_code: "WA139", name: "Cancer Research and Biostatistics" ,address_id: 19683,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12683,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA139",GROUP_DESC:"WA139 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12683,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA139",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA139",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12683,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA139", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12683 ,protection_group_id: -12683, protection_element_id:-12683], primaryKey: false);
      insert('addresses', [ id: 19684, city: "Vancouver", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19684, nci_institute_code: "WA140", name: "The Cancer Center of Southwest Washington" ,address_id: 19684,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12684,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA140",GROUP_DESC:"WA140 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12684,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA140",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA140",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12684,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA140", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12684 ,protection_group_id: -12684, protection_element_id:-12684], primaryKey: false);
      insert('addresses', [ id: 19685, city: "Seattle", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19685, nci_institute_code: "WA141", name: "Swedish Cancer Institute - First Hill Campus" ,address_id: 19685,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12685,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA141",GROUP_DESC:"WA141 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12685,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA141",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA141",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12685,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA141", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12685 ,protection_group_id: -12685, protection_element_id:-12685], primaryKey: false);
      insert('addresses', [ id: 19686, city: "Seattle", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19686, nci_institute_code: "WA142", name: "Seattle Children's Hospital" ,address_id: 19686,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12686,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA142",GROUP_DESC:"WA142 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12686,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA142",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA142",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12686,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA142", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12686 ,protection_group_id: -12686, protection_element_id:-12686], primaryKey: false);
      insert('addresses', [ id: 19687, city: "West Perth", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19687, nci_institute_code: "WA143", name: "Telethon Institute for Child Health Research" ,address_id: 19687,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12687,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA143",GROUP_DESC:"WA143 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12687,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA143",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA143",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12687,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA143", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12687 ,protection_group_id: -12687, protection_element_id:-12687], primaryKey: false);
      insert('addresses', [ id: 19688, city: "Seattle", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19688, nci_institute_code: "WA144", name: "Seattle Institute for Biomedical and Clinical Research" ,address_id: 19688,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12688,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA144",GROUP_DESC:"WA144 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12688,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA144",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA144",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12688,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA144", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12688 ,protection_group_id: -12688, protection_element_id:-12688], primaryKey: false);
      insert('addresses', [ id: 19689, city: "Seattle", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19689, nci_institute_code: "WA145", name: "Swedish Medical Center Providence Campus" ,address_id: 19689,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12689,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA145",GROUP_DESC:"WA145 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12689,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA145",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA145",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12689,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA145", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12689 ,protection_group_id: -12689, protection_element_id:-12689], primaryKey: false);
      insert('addresses', [ id: 19690, city: "Spokane", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19690, nci_institute_code: "WA146", name: "Spokane Radiation Oncology Associates" ,address_id: 19690,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12690,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA146",GROUP_DESC:"WA146 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12690,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA146",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA146",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12690,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA146", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12690 ,protection_group_id: -12690, protection_element_id:-12690], primaryKey: false);
      insert('addresses', [ id: 19691, city: "Spokane", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19691, nci_institute_code: "WA147", name: "Surgical Specialists of Spokane" ,address_id: 19691,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12691,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA147",GROUP_DESC:"WA147 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12691,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA147",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA147",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12691,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA147", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12691 ,protection_group_id: -12691, protection_element_id:-12691], primaryKey: false);
      insert('addresses', [ id: 19692, city: "Seattle", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19692, nci_institute_code: "WA148", name: "Benaroya Research Institute at Virginia Mason" ,address_id: 19692,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12692,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA148",GROUP_DESC:"WA148 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12692,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA148",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA148",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12692,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA148", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12692 ,protection_group_id: -12692, protection_element_id:-12692], primaryKey: false);
      insert('addresses', [ id: 19693, city: "Spokane", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19693, nci_institute_code: "WA149", name: "Rockwood Cancer Treatment Center" ,address_id: 19693,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12693,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA149",GROUP_DESC:"WA149 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12693,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA149",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA149",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12693,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA149", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12693 ,protection_group_id: -12693, protection_element_id:-12693], primaryKey: false);
      insert('addresses', [ id: 19694, city: "Seattle", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19694, nci_institute_code: "WA150", name: "Minor & James Medical PLLC" ,address_id: 19694,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12694,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA150",GROUP_DESC:"WA150 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12694,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA150",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA150",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12694,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA150", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12694 ,protection_group_id: -12694, protection_element_id:-12694], primaryKey: false);
      insert('addresses', [ id: 19695, city: "Spokane", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19695, nci_institute_code: "WA151", name: "Providence Cancer Center" ,address_id: 19695,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12695,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA151",GROUP_DESC:"WA151 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12695,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA151",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA151",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12695,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA151", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12695 ,protection_group_id: -12695, protection_element_id:-12695], primaryKey: false);
      insert('addresses', [ id: 19696, city: "Bothell", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19696, nci_institute_code: "WA152", name: "Tender Care" ,address_id: 19696,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12696,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA152",GROUP_DESC:"WA152 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12696,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA152",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA152",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12696,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA152", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12696 ,protection_group_id: -12696, protection_element_id:-12696], primaryKey: false);
      insert('addresses', [ id: 19697, city: "Seattle", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19697, nci_institute_code: "WA153", name: "Frazier Healthcare Ventures" ,address_id: 19697,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12697,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA153",GROUP_DESC:"WA153 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12697,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA153",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA153",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12697,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA153", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12697 ,protection_group_id: -12697, protection_element_id:-12697], primaryKey: false);
    }

    void m28() {
        // all28 (25 terms)
      insert('addresses', [ id: 19698, city: "Kennewick", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19698, nci_institute_code: "WA154", name: "Columbia Basin Hematology and Oncology PLLC" ,address_id: 19698,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12698,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA154",GROUP_DESC:"WA154 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12698,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA154",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA154",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12698,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA154", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12698 ,protection_group_id: -12698, protection_element_id:-12698], primaryKey: false);
      insert('addresses', [ id: 19699, city: "Vancouver", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19699, nci_institute_code: "WA155", name: "Northwest Cancer Specialists" ,address_id: 19699,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12699,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA155",GROUP_DESC:"WA155 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12699,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA155",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA155",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12699,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA155", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12699 ,protection_group_id: -12699, protection_element_id:-12699], primaryKey: false);
      insert('addresses', [ id: 19700, city: "Bellevue", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19700, nci_institute_code: "WA156", name: "Partnership Oncology" ,address_id: 19700,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12700,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA156",GROUP_DESC:"WA156 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12700,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA156",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA156",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12700,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA156", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12700 ,protection_group_id: -12700, protection_element_id:-12700], primaryKey: false);
      insert('addresses', [ id: 19701, city: "Vancouver", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19701, nci_institute_code: "WA157", name: "Southwest Washington Thoracic and Vascular Surgery" ,address_id: 19701,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12701,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA157",GROUP_DESC:"WA157 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12701,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA157",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA157",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12701,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA157", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12701 ,protection_group_id: -12701, protection_element_id:-12701], primaryKey: false);
      insert('addresses', [ id: 19702, city: "Spokane", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19702, nci_institute_code: "WA158", name: "Inland Imaging Associates PS" ,address_id: 19702,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12702,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA158",GROUP_DESC:"WA158 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12702,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA158",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA158",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12702,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA158", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12702 ,protection_group_id: -12702, protection_element_id:-12702], primaryKey: false);
      insert('addresses', [ id: 19703, city: "Seattle", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19703, nci_institute_code: "WA159", name: "First Hill Surgeons" ,address_id: 19703,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12703,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA159",GROUP_DESC:"WA159 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12703,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA159",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA159",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12703,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA159", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12703 ,protection_group_id: -12703, protection_element_id:-12703], primaryKey: false);
      insert('addresses', [ id: 19704, city: "Olympia", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19704, nci_institute_code: "WA160", name: "Capital Oncology PLLC" ,address_id: 19704,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12704,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA160",GROUP_DESC:"WA160 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12704,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA160",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA160",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12704,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA160", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12704 ,protection_group_id: -12704, protection_element_id:-12704], primaryKey: false);
      insert('addresses', [ id: 19705, city: "Seattle", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19705, nci_institute_code: "WA161", name: "Seattle Prostate Institute" ,address_id: 19705,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12705,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA161",GROUP_DESC:"WA161 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12705,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA161",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA161",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12705,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA161", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12705 ,protection_group_id: -12705, protection_element_id:-12705], primaryKey: false);
      insert('addresses', [ id: 19706, city: "Everett", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19706, nci_institute_code: "WA162", name: "Providence Regional Cancer Partnership" ,address_id: 19706,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12706,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA162",GROUP_DESC:"WA162 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12706,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA162",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA162",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12706,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA162", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12706 ,protection_group_id: -12706, protection_element_id:-12706], primaryKey: false);
      insert('addresses', [ id: 19707, city: "Tacoma", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19707, nci_institute_code: "WA163", name: "Mary Bridge Hematology Oncology Clinic" ,address_id: 19707,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12707,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA163",GROUP_DESC:"WA163 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12707,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA163",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA163",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12707,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA163", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12707 ,protection_group_id: -12707, protection_element_id:-12707], primaryKey: false);
      insert('addresses', [ id: 19708, city: "Lakewood", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19708, nci_institute_code: "WA164", name: "Partner Oncology" ,address_id: 19708,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12708,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA164",GROUP_DESC:"WA164 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12708,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA164",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA164",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12708,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA164", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12708 ,protection_group_id: -12708, protection_element_id:-12708], primaryKey: false);
      insert('addresses', [ id: 19709, city: "Seattle", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19709, nci_institute_code: "WA165", name: "Inland Imaging at Beacon Hill" ,address_id: 19709,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12709,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA165",GROUP_DESC:"WA165 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12709,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA165",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA165",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12709,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA165", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12709 ,protection_group_id: -12709, protection_element_id:-12709], primaryKey: false);
      insert('addresses', [ id: 19710, city: "Spokane", state_code: "WA", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19710, nci_institute_code: "WA166", name: "North Spokane Pulmonary Clinic" ,address_id: 19710,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12710,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA166",GROUP_DESC:"WA166 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12710,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WA166",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WA166",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12710,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WA166", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12710 ,protection_group_id: -12710, protection_element_id:-12710], primaryKey: false);
      insert('addresses', [ id: 19711, country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19711, nci_institute_code: "WCCG", name: "Western Cancer Chemotherapy Group" ,address_id: 19711,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12711,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WCCG",GROUP_DESC:"WCCG group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12711,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WCCG",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WCCG",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12711,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WCCG", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12711 ,protection_group_id: -12711, protection_element_id:-12711], primaryKey: false);
      insert('addresses', [ id: 19712, country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19712, nci_institute_code: "WCG", name: "Weski Cancer Group" ,address_id: 19712,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12712,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WCG",GROUP_DESC:"WCG group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12712,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WCG",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WCG",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12712,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WCG", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12712 ,protection_group_id: -12712, protection_element_id:-12712], primaryKey: false);
      insert('addresses', [ id: 19713, city: "Kenosha", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19713, nci_institute_code: "WI003", name: "Saint Catherines Hospital" ,address_id: 19713,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12713,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI003",GROUP_DESC:"WI003 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12713,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI003",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI003",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12713,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI003", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12713 ,protection_group_id: -12713, protection_element_id:-12713], primaryKey: false);
      insert('addresses', [ id: 19714, city: "Milwaukee", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19714, nci_institute_code: "WI004", name: "Zablocki Veterans Administration Medical Center" ,address_id: 19714,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12714,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI004",GROUP_DESC:"WI004 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12714,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI004",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI004",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12714,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI004", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12714 ,protection_group_id: -12714, protection_element_id:-12714], primaryKey: false);
      insert('addresses', [ id: 19715, city: "Wood", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19715, nci_institute_code: "WI005", name: "Medical College of Wisconsin-Wood" ,address_id: 19715,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12715,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI005",GROUP_DESC:"WI005 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12715,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI005",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI005",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12715,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI005", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12715 ,protection_group_id: -12715, protection_element_id:-12715], primaryKey: false);
      insert('addresses', [ id: 19716, city: "Milwaukee", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19716, nci_institute_code: "WI006", name: "Children's Hospital of Wisconsin" ,address_id: 19716,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12716,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI006",GROUP_DESC:"WI006 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12716,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI006",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI006",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12716,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI006", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12716 ,protection_group_id: -12716, protection_element_id:-12716], primaryKey: false);
      insert('addresses', [ id: 19717, city: "Milwaukee", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19717, nci_institute_code: "WI007", name: "Aurora Sinai Medical Center" ,address_id: 19717,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12717,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI007",GROUP_DESC:"WI007 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12717,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI007",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI007",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12717,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI007", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12717 ,protection_group_id: -12717, protection_element_id:-12717], primaryKey: false);
      insert('addresses', [ id: 19718, city: "Milwaukee", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19718, nci_institute_code: "WI008", name: "Columbia Saint Mary's Hospital -Milwaukee" ,address_id: 19718,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12718,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI008",GROUP_DESC:"WI008 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12718,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI008",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI008",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12718,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI008", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12718 ,protection_group_id: -12718, protection_element_id:-12718], primaryKey: false);
      insert('addresses', [ id: 19719, city: "Minocqua", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19719, nci_institute_code: "WI009", name: "Marshfield Clinic-Lakeland Ctr" ,address_id: 19719,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12719,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI009",GROUP_DESC:"WI009 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12719,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI009",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI009",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12719,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI009", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12719 ,protection_group_id: -12719, protection_element_id:-12719], primaryKey: false);
      insert('addresses', [ id: 19720, city: "Milwaukee", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19720, nci_institute_code: "WI010", name: "Columbia Saint Mary's Hospital-Columbia" ,address_id: 19720,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12720,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI010",GROUP_DESC:"WI010 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12720,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI010",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI010",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12720,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI010", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12720 ,protection_group_id: -12720, protection_element_id:-12720], primaryKey: false);
      insert('addresses', [ id: 19721, city: "Milwaukee", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19721, nci_institute_code: "WI011", name: "Aurora Saint Luke's Medical Center" ,address_id: 19721,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12721,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI011",GROUP_DESC:"WI011 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12721,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI011",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI011",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12721,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI011", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12721 ,protection_group_id: -12721, protection_element_id:-12721], primaryKey: false);
      insert('addresses', [ id: 19722, city: "Milwaukee", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19722, nci_institute_code: "WI012", name: "John Doyne Hospital" ,address_id: 19722,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12722,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI012",GROUP_DESC:"WI012 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12722,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI012",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI012",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12722,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI012", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12722 ,protection_group_id: -12722, protection_element_id:-12722], primaryKey: false);
    }

    void m29() {
        // all29 (25 terms)
      insert('addresses', [ id: 19723, city: "Milwaukee", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19723, nci_institute_code: "WI013", name: "Medical College of Wisconsin" ,address_id: 19723,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12723,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI013",GROUP_DESC:"WI013 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12723,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI013",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI013",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12723,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI013", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12723 ,protection_group_id: -12723, protection_element_id:-12723], primaryKey: false);
      insert('addresses', [ id: 19724, city: "Racine", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19724, nci_institute_code: "WI014", name: "Saint Luke's Hospital, Racine" ,address_id: 19724,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12724,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI014",GROUP_DESC:"WI014 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12724,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI014",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI014",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12724,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI014", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12724 ,protection_group_id: -12724, protection_element_id:-12724], primaryKey: false);
      insert('addresses', [ id: 19725, city: "Janesville", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19725, nci_institute_code: "WI015", name: "Mercy Health Systems" ,address_id: 19725,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12725,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI015",GROUP_DESC:"WI015 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12725,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI015",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI015",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12725,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI015", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12725 ,protection_group_id: -12725, protection_element_id:-12725], primaryKey: false);
      insert('addresses', [ id: 19726, city: "Janesville", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19726, nci_institute_code: "WI016", name: "Janesville Riverview Clinic" ,address_id: 19726,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12726,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI016",GROUP_DESC:"WI016 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12726,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI016",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI016",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12726,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI016", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12726 ,protection_group_id: -12726, protection_element_id:-12726], primaryKey: false);
      insert('addresses', [ id: 19727, city: "Monroe", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19727, nci_institute_code: "WI017", name: "Saint Clare Hospital Monroe Clinic" ,address_id: 19727,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12727,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI017",GROUP_DESC:"WI017 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12727,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI017",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI017",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12727,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI017", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12727 ,protection_group_id: -12727, protection_element_id:-12727], primaryKey: false);
      insert('addresses', [ id: 19728, city: "Madison", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19728, nci_institute_code: "WI018", name: "William S.Middleton VA Medical Center" ,address_id: 19728,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12728,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI018",GROUP_DESC:"WI018 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12728,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI018",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI018",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12728,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI018", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12728 ,protection_group_id: -12728, protection_element_id:-12728], primaryKey: false);
      insert('addresses', [ id: 19729, city: "Eau Claire", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19729, nci_institute_code: "WI019", name: "Sacred Heart Hospital" ,address_id: 19729,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12729,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI019",GROUP_DESC:"WI019 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12729,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI019",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI019",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12729,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI019", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12729 ,protection_group_id: -12729, protection_element_id:-12729], primaryKey: false);
      insert('addresses', [ id: 19730, city: "Madison", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19730, nci_institute_code: "WI020", name: "University of Wisconsin Hospital and Clinics" ,address_id: 19730,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12730,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI020",GROUP_DESC:"WI020 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12730,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI020",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI020",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12730,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI020", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12730 ,protection_group_id: -12730, protection_element_id:-12730], primaryKey: false);
      insert('addresses', [ id: 19731, city: "Madison", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19731, nci_institute_code: "WI021", name: "Meriter Hospital" ,address_id: 19731,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12731,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI021",GROUP_DESC:"WI021 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12731,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI021",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI021",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12731,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI021", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12731 ,protection_group_id: -12731, protection_element_id:-12731], primaryKey: false);
      insert('addresses', [ id: 19732, city: "Madison", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19732, nci_institute_code: "WI022", name: "Saint Nicholas Hospital" ,address_id: 19732,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12732,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI022",GROUP_DESC:"WI022 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12732,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI022",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI022",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12732,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI022", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12732 ,protection_group_id: -12732, protection_element_id:-12732], primaryKey: false);
      insert('addresses', [ id: 19733, city: "Madison", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19733, nci_institute_code: "WI023", name: "Wisconsin Clinical Cancer Center" ,address_id: 19733,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12733,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI023",GROUP_DESC:"WI023 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12733,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI023",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI023",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12733,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI023", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12733 ,protection_group_id: -12733, protection_element_id:-12733], primaryKey: false);
      insert('addresses', [ id: 19734, city: "Green Bay", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19734, nci_institute_code: "WI027", name: "Saint Vincent Hospital" ,address_id: 19734,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12734,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI027",GROUP_DESC:"WI027 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12734,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI027",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI027",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12734,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI027", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12734 ,protection_group_id: -12734, protection_element_id:-12734], primaryKey: false);
      insert('addresses', [ id: 19735, city: "Wausau", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19735, nci_institute_code: "WI028", name: "Aspirus Wausau Hospital Center" ,address_id: 19735,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12735,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI028",GROUP_DESC:"WI028 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12735,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI028",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI028",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12735,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI028", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12735 ,protection_group_id: -12735, protection_element_id:-12735], primaryKey: false);
      insert('addresses', [ id: 19736, city: "La Crosse", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19736, nci_institute_code: "WI029", name: "Gundersen Lutheran" ,address_id: 19736,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12736,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI029",GROUP_DESC:"WI029 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12736,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI029",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI029",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12736,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI029", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12736 ,protection_group_id: -12736, protection_element_id:-12736], primaryKey: false);
      insert('addresses', [ id: 19737, city: "Marshfield", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19737, nci_institute_code: "WI030", name: "Saint Joseph's Hospital" ,address_id: 19737,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12737,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI030",GROUP_DESC:"WI030 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12737,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI030",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI030",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12737,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI030", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12737 ,protection_group_id: -12737, protection_element_id:-12737], primaryKey: false);
      insert('addresses', [ id: 19738, city: "Marshfield", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19738, nci_institute_code: "WI031", name: "Marshfield Clinic" ,address_id: 19738,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12738,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI031",GROUP_DESC:"WI031 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12738,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI031",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI031",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12738,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI031", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12738 ,protection_group_id: -12738, protection_element_id:-12738], primaryKey: false);
      insert('addresses', [ id: 19739, city: "La Crosse", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19739, nci_institute_code: "WI032", name: "Lacrosse Lutheran Hospital" ,address_id: 19739,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12739,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI032",GROUP_DESC:"WI032 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12739,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI032",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI032",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12739,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI032", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12739 ,protection_group_id: -12739, protection_element_id:-12739], primaryKey: false);
      insert('addresses', [ id: 19740, city: "La Crosse", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19740, nci_institute_code: "WI035", name: "Saint Francis - Skemp" ,address_id: 19740,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12740,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI035",GROUP_DESC:"WI035 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12740,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI035",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI035",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12740,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI035", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12740 ,protection_group_id: -12740, protection_element_id:-12740], primaryKey: false);
      insert('addresses', [ id: 19741, city: "Eau Claire", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19741, nci_institute_code: "WI036", name: "Luther Hospital" ,address_id: 19741,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12741,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI036",GROUP_DESC:"WI036 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12741,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI036",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI036",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12741,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI036", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12741 ,protection_group_id: -12741, protection_element_id:-12741], primaryKey: false);
      insert('addresses', [ id: 19742, city: "Oshkosh", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19742, nci_institute_code: "WI038", name: "Mercy Medical Center" ,address_id: 19742,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12742,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI038",GROUP_DESC:"WI038 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12742,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI038",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI038",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12742,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI038", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12742 ,protection_group_id: -12742, protection_element_id:-12742], primaryKey: false);
      insert('addresses', [ id: 19743, city: "Appleton", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19743, nci_institute_code: "WI039", name: "Saint Elizabeth Hospital" ,address_id: 19743,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12743,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI039",GROUP_DESC:"WI039 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12743,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI039",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI039",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12743,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI039", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12743 ,protection_group_id: -12743, protection_element_id:-12743], primaryKey: false);
      insert('addresses', [ id: 19744, city: "Fond Du Lac", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19744, nci_institute_code: "WI040", name: "Fond Du Lac Clinic" ,address_id: 19744,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12744,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI040",GROUP_DESC:"WI040 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12744,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI040",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI040",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12744,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI040", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12744 ,protection_group_id: -12744, protection_element_id:-12744], primaryKey: false);
      insert('addresses', [ id: 19745, city: "Neenah", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19745, nci_institute_code: "WI042", name: "Nicolet Clinic, S.C." ,address_id: 19745,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12745,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI042",GROUP_DESC:"WI042 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12745,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI042",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI042",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12745,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI042", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12745 ,protection_group_id: -12745, protection_element_id:-12745], primaryKey: false);
      insert('addresses', [ id: 19746, city: "Milwaukee", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19746, nci_institute_code: "WI043", name: "Wheaton Franciscan Healthcare - Saint Joseph" ,address_id: 19746,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12746,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI043",GROUP_DESC:"WI043 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12746,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI043",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI043",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12746,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI043", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12746 ,protection_group_id: -12746, protection_element_id:-12746], primaryKey: false);
      insert('addresses', [ id: 19747, city: "Milwaukee", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19747, nci_institute_code: "WI044", name: "Oncology Alliance - Milwaukee South" ,address_id: 19747,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12747,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI044",GROUP_DESC:"WI044 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12747,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI044",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI044",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12747,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI044", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12747 ,protection_group_id: -12747, protection_element_id:-12747], primaryKey: false);
    }

    void m30() {
        // all30 (25 terms)
      insert('addresses', [ id: 19748, city: "Eau Claire", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19748, nci_institute_code: "WI045", name: "Midelfort Clinic" ,address_id: 19748,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12748,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI045",GROUP_DESC:"WI045 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12748,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI045",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI045",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12748,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI045", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12748 ,protection_group_id: -12748, protection_element_id:-12748], primaryKey: false);
      insert('addresses', [ id: 19749, city: "Madison", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19749, nci_institute_code: "WI046", name: "Southern Wisconsin Radiotherapy Center" ,address_id: 19749,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12749,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI046",GROUP_DESC:"WI046 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12749,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI046",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI046",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12749,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI046", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12749 ,protection_group_id: -12749, protection_element_id:-12749], primaryKey: false);
      insert('addresses', [ id: 19750, city: "Milwaukee", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19750, nci_institute_code: "WI047", name: "Saint Michael Hospital" ,address_id: 19750,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12750,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI047",GROUP_DESC:"WI047 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12750,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI047",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI047",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12750,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI047", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12750 ,protection_group_id: -12750, protection_element_id:-12750], primaryKey: false);
      insert('addresses', [ id: 19751, city: "Racine", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19751, nci_institute_code: "WI048", name: "Racine Medical Clinic" ,address_id: 19751,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12751,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI048",GROUP_DESC:"WI048 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12751,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI048",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI048",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12751,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI048", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12751 ,protection_group_id: -12751, protection_element_id:-12751], primaryKey: false);
      insert('addresses', [ id: 19752, city: "Cudahy", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19752, nci_institute_code: "WI049", name: "Trinity Memorial Hospital" ,address_id: 19752,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12752,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI049",GROUP_DESC:"WI049 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12752,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI049",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI049",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12752,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI049", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12752 ,protection_group_id: -12752, protection_element_id:-12752], primaryKey: false);
      insert('addresses', [ id: 19753, city: "Burlington", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19753, nci_institute_code: "WI050", name: "Burlington Memorial Hospital" ,address_id: 19753,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12753,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI050",GROUP_DESC:"WI050 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12753,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI050",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI050",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12753,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI050", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12753 ,protection_group_id: -12753, protection_element_id:-12753], primaryKey: false);
      insert('addresses', [ id: 19754, city: "Waukesha", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19754, nci_institute_code: "WI051", name: "Waukesha Memorial Hospital" ,address_id: 19754,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12754,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI051",GROUP_DESC:"WI051 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12754,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI051",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI051",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12754,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI051", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12754 ,protection_group_id: -12754, protection_element_id:-12754], primaryKey: false);
      insert('addresses', [ id: 19755, city: "Madison", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19755, nci_institute_code: "WI052", name: "Saint Mary's Hospital" ,address_id: 19755,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12755,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI052",GROUP_DESC:"WI052 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12755,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI052",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI052",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12755,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI052", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12755 ,protection_group_id: -12755, protection_element_id:-12755], primaryKey: false);
      insert('addresses', [ id: 19756, city: "Milwaukee", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19756, nci_institute_code: "WI053", name: "Midwest Children's Cancer Center" ,address_id: 19756,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12756,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI053",GROUP_DESC:"WI053 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12756,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI053",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI053",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12756,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI053", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12756 ,protection_group_id: -12756, protection_element_id:-12756], primaryKey: false);
      insert('addresses', [ id: 19757, city: "Middleton", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19757, nci_institute_code: "WI054", name: "University of Wisconsin Women's Health Center" ,address_id: 19757,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12757,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI054",GROUP_DESC:"WI054 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12757,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI054",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI054",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12757,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI054", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12757 ,protection_group_id: -12757, protection_element_id:-12757], primaryKey: false);
      insert('addresses', [ id: 19758, city: "Milwaukee", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19758, nci_institute_code: "WI055", name: "Saint Francis Hospital" ,address_id: 19758,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12758,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI055",GROUP_DESC:"WI055 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12758,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI055",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI055",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12758,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI055", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12758 ,protection_group_id: -12758, protection_element_id:-12758], primaryKey: false);
      insert('addresses', [ id: 19759, city: "Milwaukee", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19759, nci_institute_code: "WI056", name: "Froedtert Hospital" ,address_id: 19759,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12759,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI056",GROUP_DESC:"WI056 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12759,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI056",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI056",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12759,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI056", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12759 ,protection_group_id: -12759, protection_element_id:-12759], primaryKey: false);
      insert('addresses', [ id: 19760, city: "Madison", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19760, nci_institute_code: "WI057", name: "University Hospital" ,address_id: 19760,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12760,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI057",GROUP_DESC:"WI057 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12760,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI057",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI057",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12760,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI057", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12760 ,protection_group_id: -12760, protection_element_id:-12760], primaryKey: false);
      insert('addresses', [ id: 19761, city: "Rhinelander", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19761, nci_institute_code: "WI058", name: "Saint Mary's Hospital" ,address_id: 19761,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12761,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI058",GROUP_DESC:"WI058 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12761,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI058",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI058",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12761,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI058", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12761 ,protection_group_id: -12761, protection_element_id:-12761], primaryKey: false);
      insert('addresses', [ id: 19762, city: "Madison", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19762, nci_institute_code: "WI059", name: "Dean Clinic" ,address_id: 19762,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12762,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI059",GROUP_DESC:"WI059 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12762,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI059",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI059",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12762,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI059", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12762 ,protection_group_id: -12762, protection_element_id:-12762], primaryKey: false);
      insert('addresses', [ id: 19763, city: "Fond Du Lac", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19763, nci_institute_code: "WI060", name: "Saint Agnes Hospital" ,address_id: 19763,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12763,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI060",GROUP_DESC:"WI060 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12763,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI060",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI060",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12763,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI060", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12763 ,protection_group_id: -12763, protection_element_id:-12763], primaryKey: false);
      insert('addresses', [ id: 19764, city: "Racine", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19764, nci_institute_code: "WI061", name: "Wheaton Franciscan Cancer Care - All Saints" ,address_id: 19764,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12764,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI061",GROUP_DESC:"WI061 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12764,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI061",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI061",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12764,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI061", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12764 ,protection_group_id: -12764, protection_element_id:-12764], primaryKey: false);
      insert('addresses', [ id: 19765, city: "Appleton", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19765, nci_institute_code: "WI062", name: "Fox Valley Hematology and Oncology" ,address_id: 19765,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12765,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI062",GROUP_DESC:"WI062 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12765,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI062",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI062",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12765,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI062", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12765 ,protection_group_id: -12765, protection_element_id:-12765], primaryKey: false);
      insert('addresses', [ id: 19766, city: "Manitowoc", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19766, nci_institute_code: "WI063", name: "Manitowoc Clinic" ,address_id: 19766,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12766,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI063",GROUP_DESC:"WI063 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12766,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI063",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI063",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12766,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI063", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12766 ,protection_group_id: -12766, protection_element_id:-12766], primaryKey: false);
      insert('addresses', [ id: 19767, city: "Beloit", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19767, nci_institute_code: "WI064", name: "Beloit Clinic" ,address_id: 19767,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12767,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI064",GROUP_DESC:"WI064 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12767,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI064",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI064",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12767,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI064", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12767 ,protection_group_id: -12767, protection_element_id:-12767], primaryKey: false);
      insert('addresses', [ id: 19768, city: "Beloit", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19768, nci_institute_code: "WI065", name: "Beloit Memorial Hospital" ,address_id: 19768,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12768,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI065",GROUP_DESC:"WI065 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12768,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI065",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI065",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12768,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI065", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12768 ,protection_group_id: -12768, protection_element_id:-12768], primaryKey: false);
      insert('addresses', [ id: 19769, city: "Green Bay", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19769, nci_institute_code: "WI066", name: "Urological Surgeons Limited" ,address_id: 19769,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12769,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI066",GROUP_DESC:"WI066 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12769,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI066",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI066",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12769,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI066", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12769 ,protection_group_id: -12769, protection_element_id:-12769], primaryKey: false);
      insert('addresses', [ id: 19770, city: "Green Bay", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19770, nci_institute_code: "WI067", name: "Saint Mary's Hospital" ,address_id: 19770,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12770,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI067",GROUP_DESC:"WI067 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12770,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI067",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI067",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12770,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI067", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12770 ,protection_group_id: -12770, protection_element_id:-12770], primaryKey: false);
      insert('addresses', [ id: 19771, city: "Chippewa Falls", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19771, nci_institute_code: "WI069", name: "Marshfield Clinic/Chippewa Center" ,address_id: 19771,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12771,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI069",GROUP_DESC:"WI069 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12771,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI069",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI069",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12771,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI069", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12771 ,protection_group_id: -12771, protection_element_id:-12771], primaryKey: false);
      insert('addresses', [ id: 19772, city: "Sheboygan", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19772, nci_institute_code: "WI072", name: "Sheboygan Medical Clinic" ,address_id: 19772,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12772,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI072",GROUP_DESC:"WI072 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12772,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI072",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI072",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12772,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI072", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12772 ,protection_group_id: -12772, protection_element_id:-12772], primaryKey: false);
    }

    void m31() {
        // all31 (25 terms)
      insert('addresses', [ id: 19773, city: "Milwaukee", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19773, nci_institute_code: "WI073", name: "Milwaukee Medical Clinic" ,address_id: 19773,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12773,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI073",GROUP_DESC:"WI073 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12773,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI073",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI073",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12773,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI073", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12773 ,protection_group_id: -12773, protection_element_id:-12773], primaryKey: false);
      insert('addresses', [ id: 19774, city: "Madison", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19774, nci_institute_code: "WI074", name: "S. Wi Radiotherapy Ctr" ,address_id: 19774,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12774,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI074",GROUP_DESC:"WI074 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12774,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI074",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI074",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12774,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI074", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12774 ,protection_group_id: -12774, protection_element_id:-12774], primaryKey: false);
      insert('addresses', [ id: 19775, city: "Neenah", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19775, nci_institute_code: "WI075", name: "Affinity Health System - Lincoln Street Clinic" ,address_id: 19775,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12775,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI075",GROUP_DESC:"WI075 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12775,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI075",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI075",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12775,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI075", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12775 ,protection_group_id: -12775, protection_element_id:-12775], primaryKey: false);
      insert('addresses', [ id: 19776, city: "Milwaukee", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19776, nci_institute_code: "WI076", name: "University of Wisconsin Medical School" ,address_id: 19776,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12776,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI076",GROUP_DESC:"WI076 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12776,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI076",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI076",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12776,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI076", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12776 ,protection_group_id: -12776, protection_element_id:-12776], primaryKey: false);
      insert('addresses', [ id: 19777, city: "La Crosse", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19777, nci_institute_code: "WI077", name: "Franciscan Skemp Healthcare" ,address_id: 19777,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12777,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI077",GROUP_DESC:"WI077 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12777,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI077",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI077",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12777,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI077", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12777 ,protection_group_id: -12777, protection_element_id:-12777], primaryKey: false);
      insert('addresses', [ id: 19778, city: "Kenosha", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19778, nci_institute_code: "WI078", name: "Kenosha Hospital Medical Center Incorporated" ,address_id: 19778,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12778,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI078",GROUP_DESC:"WI078 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12778,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI078",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI078",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12778,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI078", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12778 ,protection_group_id: -12778, protection_element_id:-12778], primaryKey: false);
      insert('addresses', [ id: 19779, city: "Green Bay", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19779, nci_institute_code: "WI080", name: "Prevea Clinic" ,address_id: 19779,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12779,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI080",GROUP_DESC:"WI080 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12779,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI080",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI080",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12779,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI080", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12779 ,protection_group_id: -12779, protection_element_id:-12779], primaryKey: false);
      insert('addresses', [ id: 19780, city: "Milwaukee", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19780, nci_institute_code: "WI081", name: "Saint Mary's Medical Center" ,address_id: 19780,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12780,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI081",GROUP_DESC:"WI081 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12780,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI081",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI081",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12780,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI081", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12780 ,protection_group_id: -12780, protection_element_id:-12780], primaryKey: false);
      insert('addresses', [ id: 19781, city: "Racine", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19781, nci_institute_code: "WI082", name: "Hematology and Oncology Consultants" ,address_id: 19781,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12781,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI082",GROUP_DESC:"WI082 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12781,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI082",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI082",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12781,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI082", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12781 ,protection_group_id: -12781, protection_element_id:-12781], primaryKey: false);
      insert('addresses', [ id: 19782, city: "West Allis", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19782, nci_institute_code: "WI083", name: "West Allis Memorial Hospital" ,address_id: 19782,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12782,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI083",GROUP_DESC:"WI083 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12782,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI083",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI083",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12782,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI083", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12782 ,protection_group_id: -12782, protection_element_id:-12782], primaryKey: false);
      insert('addresses', [ id: 19783, city: "Ashland", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19783, nci_institute_code: "WI084", name: "Ashland Memorial Medical Center" ,address_id: 19783,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12783,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI084",GROUP_DESC:"WI084 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12783,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI084",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI084",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12783,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI084", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12783 ,protection_group_id: -12783, protection_element_id:-12783], primaryKey: false);
      insert('addresses', [ id: 19784, city: "Milwaukee", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19784, nci_institute_code: "WI085", name: "Medical Consultants Limited" ,address_id: 19784,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12784,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI085",GROUP_DESC:"WI085 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12784,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI085",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI085",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12784,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI085", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12784 ,protection_group_id: -12784, protection_element_id:-12784], primaryKey: false);
      insert('addresses', [ id: 19785, city: "Milwaukee", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19785, nci_institute_code: "WI086", name: "Oncology Associates, P.C." ,address_id: 19785,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12785,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI086",GROUP_DESC:"WI086 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12785,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI086",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI086",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12785,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI086", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12785 ,protection_group_id: -12785, protection_element_id:-12785], primaryKey: false);
      insert('addresses', [ id: 19786, city: "Oconomowoc", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19786, nci_institute_code: "WI087", name: "Oconomowoc Memorial Hospital-ProHealth Care Inc" ,address_id: 19786,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12786,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI087",GROUP_DESC:"WI087 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12786,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI087",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI087",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12786,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI087", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12786 ,protection_group_id: -12786, protection_element_id:-12786], primaryKey: false);
      insert('addresses', [ id: 19787, city: "Menomonee Falls", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19787, nci_institute_code: "WI088", name: "Community Memorial Hospital" ,address_id: 19787,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12787,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI088",GROUP_DESC:"WI088 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12787,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI088",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI088",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12787,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI088", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12787 ,protection_group_id: -12787, protection_element_id:-12787], primaryKey: false);
      insert('addresses', [ id: 19788, city: "Green Bay", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19788, nci_institute_code: "WI089", name: "Bellin Memorial Hospital" ,address_id: 19788,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12788,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI089",GROUP_DESC:"WI089 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12788,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI089",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI089",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12788,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI089", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12788 ,protection_group_id: -12788, protection_element_id:-12788], primaryKey: false);
      insert('addresses', [ id: 19789, city: "Oshkosh", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19789, nci_institute_code: "WI090", name: "Central Wi Cancer Program" ,address_id: 19789,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12789,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI090",GROUP_DESC:"WI090 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12789,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI090",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI090",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12789,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI090", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12789 ,protection_group_id: -12789, protection_element_id:-12789], primaryKey: false);
      insert('addresses', [ id: 19790, city: "Menomonee Falls", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19790, nci_institute_code: "WI091", name: "Community Memorial Hospital Cancer Care Center" ,address_id: 19790,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12790,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI091",GROUP_DESC:"WI091 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12790,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI091",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI091",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12790,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI091", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12790 ,protection_group_id: -12790, protection_element_id:-12790], primaryKey: false);
      insert('addresses', [ id: 19791, city: "Marinette", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19791, nci_institute_code: "WI092", name: "Bay Area Medical Center" ,address_id: 19791,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12791,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI092",GROUP_DESC:"WI092 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12791,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI092",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI092",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12791,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI092", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12791 ,protection_group_id: -12791, protection_element_id:-12791], primaryKey: false);
      insert('addresses', [ id: 19792, city: "Wisconsin Rapids", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19792, nci_institute_code: "WI093", name: "Riverview Hospital" ,address_id: 19792,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12792,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI093",GROUP_DESC:"WI093 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12792,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI093",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI093",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12792,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI093", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12792 ,protection_group_id: -12792, protection_element_id:-12792], primaryKey: false);
      insert('addresses', [ id: 19793, city: "Mauston", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19793, nci_institute_code: "WI094", name: "Hess Memorial Hospital (Mile Bluff Clinic)" ,address_id: 19793,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12793,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI094",GROUP_DESC:"WI094 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12793,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI094",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI094",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12793,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI094", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12793 ,protection_group_id: -12793, protection_element_id:-12793], primaryKey: false);
      insert('addresses', [ id: 19794, city: "Milwaukee", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19794, nci_institute_code: "WI095", name: "Milwaukee Veterans Administration Center (Clement J Zablocki VA Medical Center)" ,address_id: 19794,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12794,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI095",GROUP_DESC:"WI095 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12794,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI095",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI095",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12794,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI095", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12794 ,protection_group_id: -12794, protection_element_id:-12794], primaryKey: false);
      insert('addresses', [ id: 19795, city: "Manitowoc", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19795, nci_institute_code: "WI096", name: "Holy Family Memorial Hospital" ,address_id: 19795,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12795,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI096",GROUP_DESC:"WI096 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12795,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI096",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI096",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12795,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI096", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12795 ,protection_group_id: -12795, protection_element_id:-12795], primaryKey: false);
      insert('addresses', [ id: 19796, city: "Fond Du Lac", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19796, nci_institute_code: "WI098", name: "Central Wisconsin Cancer Program" ,address_id: 19796,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12796,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI098",GROUP_DESC:"WI098 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12796,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI098",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI098",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12796,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI098", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12796 ,protection_group_id: -12796, protection_element_id:-12796], primaryKey: false);
      insert('addresses', [ id: 19797, city: "Glendale", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19797, nci_institute_code: "WI099", name: "Oncology Alliance-Glendale" ,address_id: 19797,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12797,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI099",GROUP_DESC:"WI099 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12797,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI099",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI099",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12797,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI099", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12797 ,protection_group_id: -12797, protection_element_id:-12797], primaryKey: false);
    }

    void m32() {
        // all32 (25 terms)
      insert('addresses', [ id: 19798, city: "Milwaukee", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19798, nci_institute_code: "WI100", name: "Lakeshore Medical Clinic" ,address_id: 19798,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12798,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI100",GROUP_DESC:"WI100 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12798,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI100",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI100",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12798,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI100", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12798 ,protection_group_id: -12798, protection_element_id:-12798], primaryKey: false);
      insert('addresses', [ id: 19799, city: "Madison", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19799, nci_institute_code: "WI101", name: "Covance Clinical Research Unit" ,address_id: 19799,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12799,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI101",GROUP_DESC:"WI101 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12799,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI101",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI101",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12799,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI101", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12799 ,protection_group_id: -12799, protection_element_id:-12799], primaryKey: false);
      insert('addresses', [ id: 19800, city: "Madison", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19800, nci_institute_code: "WI102", name: "University Station Clinics" ,address_id: 19800,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12800,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI102",GROUP_DESC:"WI102 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12800,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI102",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI102",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12800,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI102", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12800 ,protection_group_id: -12800, protection_element_id:-12800], primaryKey: false);
      insert('addresses', [ id: 19801, city: "Wausau", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19801, nci_institute_code: "WI103", name: "Marshfield Clinic-Wausau Center" ,address_id: 19801,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12801,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI103",GROUP_DESC:"WI103 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12801,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI103",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI103",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12801,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI103", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12801 ,protection_group_id: -12801, protection_element_id:-12801], primaryKey: false);
      insert('addresses', [ id: 19802, city: "Franklin", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19802, nci_institute_code: "WI104", name: "Oncology Alliance - Franklin" ,address_id: 19802,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12802,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI104",GROUP_DESC:"WI104 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12802,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI104",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI104",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12802,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI104", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12802 ,protection_group_id: -12802, protection_element_id:-12802], primaryKey: false);
      insert('addresses', [ id: 19803, city: "Stevens Point", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19803, nci_institute_code: "WI105", name: "Saint Michael's Hospital" ,address_id: 19803,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12803,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI105",GROUP_DESC:"WI105 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12803,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI105",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI105",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12803,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI105", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12803 ,protection_group_id: -12803, protection_element_id:-12803], primaryKey: false);
      insert('addresses', [ id: 19804, city: "Milwaukee", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19804, nci_institute_code: "WI107", name: "Aurora Health Care" ,address_id: 19804,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12804,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI107",GROUP_DESC:"WI107 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12804,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI107",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI107",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12804,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI107", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12804 ,protection_group_id: -12804, protection_element_id:-12804], primaryKey: false);
      insert('addresses', [ id: 19805, city: "West Allis", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19805, nci_institute_code: "WI108", name: "Aurora Women's Pavilion of West Allis Memorial Hospital" ,address_id: 19805,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12805,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI108",GROUP_DESC:"WI108 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12805,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI108",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI108",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12805,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI108", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12805 ,protection_group_id: -12805, protection_element_id:-12805], primaryKey: false);
      insert('addresses', [ id: 19806, city: "Rice Lake", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19806, nci_institute_code: "WI109", name: "Marshfield Clinic Indian Head Center" ,address_id: 19806,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12806,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI109",GROUP_DESC:"WI109 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12806,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI109",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI109",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12806,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI109", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12806 ,protection_group_id: -12806, protection_element_id:-12806], primaryKey: false);
      insert('addresses', [ id: 19807, city: "Madison", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19807, nci_institute_code: "WI111", name: "UW Health Oncology-One South Park" ,address_id: 19807,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12807,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI111",GROUP_DESC:"WI111 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12807,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI111",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI111",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12807,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI111", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12807 ,protection_group_id: -12807, protection_element_id:-12807], primaryKey: false);
      insert('addresses', [ id: 19808, city: "Ashland", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19808, nci_institute_code: "WI112", name: "Duluth Clinic Ashland" ,address_id: 19808,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12808,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI112",GROUP_DESC:"WI112 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12808,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI112",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI112",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12808,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI112", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12808 ,protection_group_id: -12808, protection_element_id:-12808], primaryKey: false);
      insert('addresses', [ id: 19809, city: "Sheboygan", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19809, nci_institute_code: "WI113", name: "Vince Lombardi Cancer Clinic-Sheboygan" ,address_id: 19809,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12809,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI113",GROUP_DESC:"WI113 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12809,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI113",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI113",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12809,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI113", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12809 ,protection_group_id: -12809, protection_element_id:-12809], primaryKey: false);
      insert('addresses', [ id: 19810, city: "Elkhorn", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19810, nci_institute_code: "WI114", name: "Aurora Health Care" ,address_id: 19810,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12810,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI114",GROUP_DESC:"WI114 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12810,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI114",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI114",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12810,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI114", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12810 ,protection_group_id: -12810, protection_element_id:-12810], primaryKey: false);
      insert('addresses', [ id: 19811, city: "Green Bay", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19811, nci_institute_code: "WI115", name: "Aurora BayCare Medical Center" ,address_id: 19811,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12811,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI115",GROUP_DESC:"WI115 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12811,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI115",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI115",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12811,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI115", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12811 ,protection_group_id: -12811, protection_element_id:-12811], primaryKey: false);
      insert('addresses', [ id: 19812, city: "Green Bay", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19812, nci_institute_code: "WI116", name: "Green Bay Oncology at Saint Vincent Hospital" ,address_id: 19812,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12812,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI116",GROUP_DESC:"WI116 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12812,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI116",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI116",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12812,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI116", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12812 ,protection_group_id: -12812, protection_element_id:-12812], primaryKey: false);
      insert('addresses', [ id: 19813, city: "Oconto Falls", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19813, nci_institute_code: "WI117", name: "Green Bay Oncology - Oconto Falls" ,address_id: 19813,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12813,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI117",GROUP_DESC:"WI117 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12813,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI117",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI117",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12813,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI117", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12813 ,protection_group_id: -12813, protection_element_id:-12813], primaryKey: false);
      insert('addresses', [ id: 19814, city: "Sturgeon Bay", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19814, nci_institute_code: "WI118", name: "Green Bay Oncology - Sturgeon Bay" ,address_id: 19814,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12814,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI118",GROUP_DESC:"WI118 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12814,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI118",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI118",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12814,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI118", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12814 ,protection_group_id: -12814, protection_element_id:-12814], primaryKey: false);
      insert('addresses', [ id: 19815, city: "Green Bay", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19815, nci_institute_code: "WI119", name: "Green Bay Oncology at Saint Mary's Hospital" ,address_id: 19815,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12815,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI119",GROUP_DESC:"WI119 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12815,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI119",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI119",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12815,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI119", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12815 ,protection_group_id: -12815, protection_element_id:-12815], primaryKey: false);
      insert('addresses', [ id: 19816, city: "Kenosha", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19816, nci_institute_code: "WI121", name: "Oncology Alliance - Kenosha South" ,address_id: 19816,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12816,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI121",GROUP_DESC:"WI121 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12816,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI121",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI121",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12816,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI121", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12816 ,protection_group_id: -12816, protection_element_id:-12816], primaryKey: false);
      insert('addresses', [ id: 19817, city: "Milwaukee", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19817, nci_institute_code: "WI122", name: "Milwaukee General and Vascular Surgery" ,address_id: 19817,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12817,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI122",GROUP_DESC:"WI122 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12817,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI122",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI122",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12817,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI122", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12817 ,protection_group_id: -12817, protection_element_id:-12817], primaryKey: false);
      insert('addresses', [ id: 19818, city: "Shawano", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19818, nci_institute_code: "WI123", name: "ThedaCare Physicians" ,address_id: 19818,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12818,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI123",GROUP_DESC:"WI123 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12818,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI123",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI123",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12818,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI123", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12818 ,protection_group_id: -12818, protection_element_id:-12818], primaryKey: false);
      insert('addresses', [ id: 19819, city: "Appleton", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19819, nci_institute_code: "WI124", name: "Fox Valley Surgical Associates Ltd" ,address_id: 19819,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12819,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI124",GROUP_DESC:"WI124 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12819,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI124",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI124",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12819,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI124", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12819 ,protection_group_id: -12819, protection_element_id:-12819], primaryKey: false);
      insert('addresses', [ id: 19820, city: "Appleton", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19820, nci_institute_code: "WI125", name: "Appleton Medical Center" ,address_id: 19820,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12820,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI125",GROUP_DESC:"WI125 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12820,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI125",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI125",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12820,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI125", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12820 ,protection_group_id: -12820, protection_element_id:-12820], primaryKey: false);
      insert('addresses', [ id: 19821, city: "Milwaukee", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19821, nci_institute_code: "WI127", name: "Southeast Surgical, S.C." ,address_id: 19821,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12821,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI127",GROUP_DESC:"WI127 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12821,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI127",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI127",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12821,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI127", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12821 ,protection_group_id: -12821, protection_element_id:-12821], primaryKey: false);
      insert('addresses', [ id: 19822, city: "Rhinelander", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19822, nci_institute_code: "WI128", name: "Marshfield Clinic at James Beck Cancer Center" ,address_id: 19822,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12822,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI128",GROUP_DESC:"WI128 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12822,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI128",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI128",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12822,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI128", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12822 ,protection_group_id: -12822, protection_element_id:-12822], primaryKey: false);
    }

    void m33() {
        // all33 (25 terms)
      insert('addresses', [ id: 19823, city: "Wisconsin Rapids", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19823, nci_institute_code: "WI129", name: "Marshfield Clinic - Wisconsin Rapids Center" ,address_id: 19823,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12823,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI129",GROUP_DESC:"WI129 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12823,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI129",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI129",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12823,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI129", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12823 ,protection_group_id: -12823, protection_element_id:-12823], primaryKey: false);
      insert('addresses', [ id: 19824, city: "Mequon", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19824, nci_institute_code: "WI130", name: "Advanced Healthcare, S.C.-East Mequon Clinic" ,address_id: 19824,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12824,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI130",GROUP_DESC:"WI130 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12824,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI130",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI130",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12824,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI130", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12824 ,protection_group_id: -12824, protection_element_id:-12824], primaryKey: false);
      insert('addresses', [ id: 19825, city: "Neenah", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19825, nci_institute_code: "WI131", name: "Surgical Associates of Neenah SC" ,address_id: 19825,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12825,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI131",GROUP_DESC:"WI131 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12825,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI131",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI131",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12825,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI131", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12825 ,protection_group_id: -12825, protection_element_id:-12825], primaryKey: false);
      insert('addresses', [ id: 19826, city: "Ladysmith", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19826, nci_institute_code: "WI132", name: "Marshfield Clinic - Ladysmith Center" ,address_id: 19826,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12826,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI132",GROUP_DESC:"WI132 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12826,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI132",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI132",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12826,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI132", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12826 ,protection_group_id: -12826, protection_element_id:-12826], primaryKey: false);
      insert('addresses', [ id: 19827, city: "Merrill", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19827, nci_institute_code: "WI133", name: "Marshfield Clinic - Merrill Center" ,address_id: 19827,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12827,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI133",GROUP_DESC:"WI133 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12827,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI133",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI133",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12827,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI133", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12827 ,protection_group_id: -12827, protection_element_id:-12827], primaryKey: false);
      insert('addresses', [ id: 19828, city: "Eau Claire", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19828, nci_institute_code: "WI134", name: "Marshfield Clinic Cancer Care at Regional Cancer Center" ,address_id: 19828,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12828,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI134",GROUP_DESC:"WI134 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12828,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI134",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI134",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12828,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI134", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12828 ,protection_group_id: -12828, protection_element_id:-12828], primaryKey: false);
      insert('addresses', [ id: 19829, city: "Wauwautosa", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19829, nci_institute_code: "WI135", name: "Oncology Alliance - Milwaukee West" ,address_id: 19829,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12829,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI135",GROUP_DESC:"WI135 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12829,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI135",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI135",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12829,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI135", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12829 ,protection_group_id: -12829, protection_element_id:-12829], primaryKey: false);
      insert('addresses', [ id: 19830, city: "Two Rivers", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19830, nci_institute_code: "WI136", name: "Vince Lombardi Cancer Clinic" ,address_id: 19830,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12830,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI136",GROUP_DESC:"WI136 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12830,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI136",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI136",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12830,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI136", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12830 ,protection_group_id: -12830, protection_element_id:-12830], primaryKey: false);
      insert('addresses', [ id: 19831, city: "Franklin", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19831, nci_institute_code: "WI137", name: "Reiman Center for Cancer Care" ,address_id: 19831,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12831,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI137",GROUP_DESC:"WI137 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12831,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI137",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI137",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12831,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI137", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12831 ,protection_group_id: -12831, protection_element_id:-12831], primaryKey: false);
      insert('addresses', [ id: 19832, city: "Milwaukee", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19832, nci_institute_code: "WI138", name: "Milwaukee Oncology Consultants Inc" ,address_id: 19832,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12832,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI138",GROUP_DESC:"WI138 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12832,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI138",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI138",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12832,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI138", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12832 ,protection_group_id: -12832, protection_element_id:-12832], primaryKey: false);
      insert('addresses', [ id: 19833, city: "Fox Point", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19833, nci_institute_code: "WI139", name: "Aurora Medical Group Northshore" ,address_id: 19833,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12833,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI139",GROUP_DESC:"WI139 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12833,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI139",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI139",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12833,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI139", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12833 ,protection_group_id: -12833, protection_element_id:-12833], primaryKey: false);
      insert('addresses', [ id: 19834, city: "Mequon", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19834, nci_institute_code: "WI140", name: "Columbia Saint Mary's Hospital - Ozaukee" ,address_id: 19834,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12834,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI140",GROUP_DESC:"WI140 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12834,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI140",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI140",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12834,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI140", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12834 ,protection_group_id: -12834, protection_element_id:-12834], primaryKey: false);
      insert('addresses', [ id: 19835, city: "Weston", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19835, nci_institute_code: "WI141", name: "Diagnostic & Treatment Center" ,address_id: 19835,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12835,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI141",GROUP_DESC:"WI141 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12835,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI141",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI141",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12835,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI141", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12835 ,protection_group_id: -12835, protection_element_id:-12835], primaryKey: false);
      insert('addresses', [ id: 19836, city: "Wisconsin Rapids", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19836, nci_institute_code: "WI142", name: "University of Wisconsin Cancer Center Riverview" ,address_id: 19836,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12836,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI142",GROUP_DESC:"WI142 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12836,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI142",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI142",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12836,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI142", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12836 ,protection_group_id: -12836, protection_element_id:-12836], primaryKey: false);
      insert('addresses', [ id: 19837, city: "Mukwonago", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19837, nci_institute_code: "WI143", name: "D N Greenwald Center" ,address_id: 19837,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12837,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI143",GROUP_DESC:"WI143 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12837,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI143",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI143",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12837,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI143", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12837 ,protection_group_id: -12837, protection_element_id:-12837], primaryKey: false);
      insert('addresses', [ id: 19838, city: "Weston", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19838, nci_institute_code: "WI144", name: "Marshfield Clinic - Weston Center" ,address_id: 19838,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12838,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI144",GROUP_DESC:"WI144 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12838,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI144",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI144",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12838,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI144", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12838 ,protection_group_id: -12838, protection_element_id:-12838], primaryKey: false);
      insert('addresses', [ id: 19839, city: "Brookfield", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19839, nci_institute_code: "WI145", name: "Elmbrook Memorial Hospital" ,address_id: 19839,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12839,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI145",GROUP_DESC:"WI145 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12839,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI145",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI145",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12839,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI145", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12839 ,protection_group_id: -12839, protection_element_id:-12839], primaryKey: false);
      insert('addresses', [ id: 19840, city: "Antigo", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19840, nci_institute_code: "WI146", name: "Langlade Memorial Hospital" ,address_id: 19840,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12840,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI146",GROUP_DESC:"WI146 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12840,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI146",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI146",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12840,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI146", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12840 ,protection_group_id: -12840, protection_element_id:-12840], primaryKey: false);
      insert('addresses', [ id: 19841, city: "Brookfield", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19841, nci_institute_code: "WI147", name: "Brookfield Surgical Associates" ,address_id: 19841,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12841,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI147",GROUP_DESC:"WI147 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12841,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI147",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI147",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12841,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI147", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12841 ,protection_group_id: -12841, protection_element_id:-12841], primaryKey: false);
      insert('addresses', [ id: 19842, city: "Glendale", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19842, nci_institute_code: "WI148", name: "Wheaton Franciscan Healthcare" ,address_id: 19842,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12842,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI148",GROUP_DESC:"WI148 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12842,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI148",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI148",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12842,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI148", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12842 ,protection_group_id: -12842, protection_element_id:-12842], primaryKey: false);
      insert('addresses', [ id: 19843, city: "Pleasant Praire", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19843, nci_institute_code: "WI149", name: "United Hospital System - Saint Catherine's Medical Center Campus" ,address_id: 19843,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12843,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI149",GROUP_DESC:"WI149 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12843,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI149",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI149",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12843,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI149", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12843 ,protection_group_id: -12843, protection_element_id:-12843], primaryKey: false);
      insert('addresses', [ id: 19844, city: "Milwaukee", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19844, nci_institute_code: "WI150", name: "Integrated Breast Specialists SC" ,address_id: 19844,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12844,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI150",GROUP_DESC:"WI150 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12844,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI150",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI150",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12844,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI150", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12844 ,protection_group_id: -12844, protection_element_id:-12844], primaryKey: false);
      insert('addresses', [ id: 19845, city: "Johnson Creek", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19845, nci_institute_code: "WI151", name: "UW Cancer Center Johnson Creek" ,address_id: 19845,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12845,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI151",GROUP_DESC:"WI151 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12845,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI151",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI151",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12845,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI151", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12845 ,protection_group_id: -12845, protection_element_id:-12845], primaryKey: false);
      insert('addresses', [ id: 19846, city: "Milwaukee", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19846, nci_institute_code: "WI152", name: "Prospect Medical Commons" ,address_id: 19846,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12846,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI152",GROUP_DESC:"WI152 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12846,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI152",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI152",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12846,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI152", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12846 ,protection_group_id: -12846, protection_element_id:-12846], primaryKey: false);
      insert('addresses', [ id: 19847, city: "Sturgeon Bay", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19847, nci_institute_code: "WI153", name: "Door County Cancer Center" ,address_id: 19847,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12847,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI153",GROUP_DESC:"WI153 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12847,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI153",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI153",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12847,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI153", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12847 ,protection_group_id: -12847, protection_element_id:-12847], primaryKey: false);
    }

    void m34() {
        // all34 (25 terms)
      insert('addresses', [ id: 19848, city: "Madison", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19848, nci_institute_code: "WI154", name: "Turville Bay MRI and Radiation Oncology Center" ,address_id: 19848,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12848,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI154",GROUP_DESC:"WI154 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12848,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI154",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI154",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12848,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI154", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12848 ,protection_group_id: -12848, protection_element_id:-12848], primaryKey: false);
      insert('addresses', [ id: 19849, city: "Milwaukee", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19849, nci_institute_code: "WI155", name: "Thoracic Surgery Associates Limited" ,address_id: 19849,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12849,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI155",GROUP_DESC:"WI155 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12849,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI155",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI155",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12849,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI155", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12849 ,protection_group_id: -12849, protection_element_id:-12849], primaryKey: false);
      insert('addresses', [ id: 19850, city: "Oshkosh", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19850, nci_institute_code: "WI156", name: "Vince Lombardi Cancer Clinic - Oshkosh" ,address_id: 19850,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12850,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI156",GROUP_DESC:"WI156 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12850,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI156",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI156",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12850,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI156", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12850 ,protection_group_id: -12850, protection_element_id:-12850], primaryKey: false);
      insert('addresses', [ id: 19851, city: "Slinger", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19851, nci_institute_code: "WI158", name: "Vince Lombardi Cancer Clinic - Slinger" ,address_id: 19851,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12851,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI158",GROUP_DESC:"WI158 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12851,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI158",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI158",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12851,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI158", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12851 ,protection_group_id: -12851, protection_element_id:-12851], primaryKey: false);
      insert('addresses', [ id: 19852, city: "Greenfield", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19852, nci_institute_code: "WI159", name: "Greater Milwaukee Otolaryngology LLC" ,address_id: 19852,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12852,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI159",GROUP_DESC:"WI159 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12852,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI159",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI159",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12852,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI159", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12852 ,protection_group_id: -12852, protection_element_id:-12852], primaryKey: false);
      insert('addresses', [ id: 19853, city: "West Bend", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19853, nci_institute_code: "WI160", name: "The Alyce and Elmore Kraemer Cancer Care Center" ,address_id: 19853,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12853,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI160",GROUP_DESC:"WI160 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12853,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI160",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI160",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12853,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI160", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12853 ,protection_group_id: -12853, protection_element_id:-12853], primaryKey: false);
      insert('addresses', [ id: 19854, city: "Milwaukee", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19854, nci_institute_code: "WI161", name: "Columbia Saint Mary's Water Tower Medical Commons" ,address_id: 19854,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12854,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI161",GROUP_DESC:"WI161 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12854,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI161",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI161",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12854,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI161", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12854 ,protection_group_id: -12854, protection_element_id:-12854], primaryKey: false);
      insert('addresses', [ id: 19855, city: "Milwaukee", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19855, nci_institute_code: "WI162", name: "Aurora Gynecologic Oncology" ,address_id: 19855,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12855,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI162",GROUP_DESC:"WI162 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12855,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI162",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI162",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12855,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI162", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12855 ,protection_group_id: -12855, protection_element_id:-12855], primaryKey: false);
      insert('addresses', [ id: 19856, city: "Weston", state_code: "WI", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19856, nci_institute_code: "WI163", name: "Saint Clare's Hospital" ,address_id: 19856,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12856,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI163",GROUP_DESC:"WI163 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12856,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WI163",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WI163",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12856,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WI163", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12856 ,protection_group_id: -12856, protection_element_id:-12856], primaryKey: false);
      insert('addresses', [ id: 19857, city: "Welch", state_code: "WV", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19857, nci_institute_code: "WV001", name: "Stevens Hospital" ,address_id: 19857,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12857,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV001",GROUP_DESC:"WV001 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12857,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WV001",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WV001",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12857,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV001", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12857 ,protection_group_id: -12857, protection_element_id:-12857], primaryKey: false);
      insert('addresses', [ id: 19858, city: "Montgomery", state_code: "WV", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19858, nci_institute_code: "WV002", name: "Montgomery General Hospital" ,address_id: 19858,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12858,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV002",GROUP_DESC:"WV002 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12858,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WV002",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WV002",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12858,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV002", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12858 ,protection_group_id: -12858, protection_element_id:-12858], primaryKey: false);
      insert('addresses', [ id: 19859, city: "Charleston", state_code: "WV", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19859, nci_institute_code: "WV003", name: "Kanawha Valley Memorial Hospital" ,address_id: 19859,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12859,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV003",GROUP_DESC:"WV003 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12859,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WV003",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WV003",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12859,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV003", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12859 ,protection_group_id: -12859, protection_element_id:-12859], primaryKey: false);
      insert('addresses', [ id: 19860, city: "Charleston", state_code: "WV", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19860, nci_institute_code: "WV004", name: "West Virginia University Charleston" ,address_id: 19860,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12860,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV004",GROUP_DESC:"WV004 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12860,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WV004",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WV004",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12860,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV004", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12860 ,protection_group_id: -12860, protection_element_id:-12860], primaryKey: false);
      insert('addresses', [ id: 19861, city: "Charleston", state_code: "WV", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19861, nci_institute_code: "WV005", name: "Charleston Area Medical Center" ,address_id: 19861,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12861,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV005",GROUP_DESC:"WV005 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12861,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WV005",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WV005",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12861,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV005", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12861 ,protection_group_id: -12861, protection_element_id:-12861], primaryKey: false);
      insert('addresses', [ id: 19862, city: "Martinsburg", state_code: "WV", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19862, nci_institute_code: "WV006", name: "City Hospital" ,address_id: 19862,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12862,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV006",GROUP_DESC:"WV006 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12862,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WV006",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WV006",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12862,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV006", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12862 ,protection_group_id: -12862, protection_element_id:-12862], primaryKey: false);
      insert('addresses', [ id: 19863, city: "Martinsburg", state_code: "WV", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19863, nci_institute_code: "WV007", name: "Veterans Administration Hospital" ,address_id: 19863,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12863,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV007",GROUP_DESC:"WV007 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12863,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WV007",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WV007",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12863,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV007", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12863 ,protection_group_id: -12863, protection_element_id:-12863], primaryKey: false);
      insert('addresses', [ id: 19864, city: "Huntington", state_code: "WV", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19864, nci_institute_code: "WV008", name: "Cabell-Huntington Hospital" ,address_id: 19864,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12864,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV008",GROUP_DESC:"WV008 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12864,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WV008",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WV008",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12864,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV008", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12864 ,protection_group_id: -12864, protection_element_id:-12864], primaryKey: false);
      insert('addresses', [ id: 19865, city: "Huntington", state_code: "WV", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19865, nci_institute_code: "WV009", name: "Marshall University Medical Center" ,address_id: 19865,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12865,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV009",GROUP_DESC:"WV009 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12865,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WV009",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WV009",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12865,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV009", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12865 ,protection_group_id: -12865, protection_element_id:-12865], primaryKey: false);
      insert('addresses', [ id: 19866, city: "Huntington", state_code: "WV", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19866, nci_institute_code: "WV010", name: "Saint Mary's Medical Center" ,address_id: 19866,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12866,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV010",GROUP_DESC:"WV010 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12866,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WV010",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WV010",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12866,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV010", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12866 ,protection_group_id: -12866, protection_element_id:-12866], primaryKey: false);
      insert('addresses', [ id: 19867, city: "Huntington", state_code: "WV", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19867, nci_institute_code: "WV012", name: "Veterans Administration Medical Center" ,address_id: 19867,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12867,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV012",GROUP_DESC:"WV012 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12867,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WV012",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WV012",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12867,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV012", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12867 ,protection_group_id: -12867, protection_element_id:-12867], primaryKey: false);
      insert('addresses', [ id: 19868, city: "Wheeling", state_code: "WV", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19868, nci_institute_code: "WV013", name: "Wheeling Hospital" ,address_id: 19868,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12868,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV013",GROUP_DESC:"WV013 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12868,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WV013",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WV013",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12868,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV013", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12868 ,protection_group_id: -12868, protection_element_id:-12868], primaryKey: false);
      insert('addresses', [ id: 19869, city: "Wheeling", state_code: "WV", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19869, nci_institute_code: "WV014", name: "Ohio Valley Medical Center" ,address_id: 19869,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12869,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV014",GROUP_DESC:"WV014 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12869,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WV014",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WV014",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12869,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV014", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12869 ,protection_group_id: -12869, protection_element_id:-12869], primaryKey: false);
      insert('addresses', [ id: 19870, city: "Parkersburg", state_code: "WV", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19870, nci_institute_code: "WV015", name: "Saint Joseph's Hospital" ,address_id: 19870,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12870,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV015",GROUP_DESC:"WV015 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12870,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WV015",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WV015",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12870,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV015", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12870 ,protection_group_id: -12870, protection_element_id:-12870], primaryKey: false);
      insert('addresses', [ id: 19871, city: "Elkins", state_code: "WV", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19871, nci_institute_code: "WV016", name: "Davis Memorial Hospital" ,address_id: 19871,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12871,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV016",GROUP_DESC:"WV016 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12871,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WV016",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WV016",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12871,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV016", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12871 ,protection_group_id: -12871, protection_element_id:-12871], primaryKey: false);
      insert('addresses', [ id: 19872, city: "Clarksburg", state_code: "WV", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19872, nci_institute_code: "WV017", name: "United Hospital Center" ,address_id: 19872,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12872,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV017",GROUP_DESC:"WV017 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12872,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WV017",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WV017",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12872,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV017", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12872 ,protection_group_id: -12872, protection_element_id:-12872], primaryKey: false);
    }

    void m35() {
        // all35 (25 terms)
      insert('addresses', [ id: 19873, city: "Beckley", state_code: "WV", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19873, nci_institute_code: "WV019", name: "Raleigh General Hospital" ,address_id: 19873,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12873,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV019",GROUP_DESC:"WV019 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12873,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WV019",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WV019",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12873,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV019", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12873 ,protection_group_id: -12873, protection_element_id:-12873], primaryKey: false);
      insert('addresses', [ id: 19874, city: "Morgantown", state_code: "WV", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19874, nci_institute_code: "WV020", name: "Monongalia Hospital" ,address_id: 19874,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12874,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV020",GROUP_DESC:"WV020 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12874,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WV020",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WV020",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12874,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV020", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12874 ,protection_group_id: -12874, protection_element_id:-12874], primaryKey: false);
      insert('addresses', [ id: 19875, city: "Morgantown", state_code: "WV", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19875, nci_institute_code: "WV021", name: "West Virginia University Hospital/Robert C Byrd Health and Science Center" ,address_id: 19875,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12875,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV021",GROUP_DESC:"WV021 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12875,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WV021",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WV021",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12875,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV021", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12875 ,protection_group_id: -12875, protection_element_id:-12875], primaryKey: false);
      insert('addresses', [ id: 19876, city: "Princeton", state_code: "WV", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19876, nci_institute_code: "WV022", name: "Princeton Community Hospital" ,address_id: 19876,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12876,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV022",GROUP_DESC:"WV022 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12876,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WV022",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WV022",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12876,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV022", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12876 ,protection_group_id: -12876, protection_element_id:-12876], primaryKey: false);
      insert('addresses', [ id: 19877, city: "Parkersburg", state_code: "WV", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19877, nci_institute_code: "WV023", name: "Camden-Clark Memorial Hospital" ,address_id: 19877,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12877,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV023",GROUP_DESC:"WV023 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12877,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WV023",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WV023",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12877,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV023", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12877 ,protection_group_id: -12877, protection_element_id:-12877], primaryKey: false);
      insert('addresses', [ id: 19878, city: "South Charleston", state_code: "WV", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19878, nci_institute_code: "WV024", name: "Thomas Memorial Hospital" ,address_id: 19878,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12878,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV024",GROUP_DESC:"WV024 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12878,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WV024",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WV024",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12878,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV024", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12878 ,protection_group_id: -12878, protection_element_id:-12878], primaryKey: false);
      insert('addresses', [ id: 19879, city: "Morgantown", state_code: "WV", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19879, nci_institute_code: "WV025", name: "West Virginia University" ,address_id: 19879,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12879,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV025",GROUP_DESC:"WV025 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12879,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WV025",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WV025",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12879,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV025", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12879 ,protection_group_id: -12879, protection_element_id:-12879], primaryKey: false);
      insert('addresses', [ id: 19880, city: "Morgantown", state_code: "WV", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19880, nci_institute_code: "WV026", name: "Ruby Memorial Hospital" ,address_id: 19880,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12880,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV026",GROUP_DESC:"WV026 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12880,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WV026",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WV026",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12880,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV026", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12880 ,protection_group_id: -12880, protection_element_id:-12880], primaryKey: false);
      insert('addresses', [ id: 19881, city: "Clarksburg", state_code: "WV", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19881, nci_institute_code: "WV027", name: "Louis A Johnson Veterans Affairs Medical Center" ,address_id: 19881,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12881,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV027",GROUP_DESC:"WV027 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12881,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WV027",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WV027",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12881,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV027", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12881 ,protection_group_id: -12881, protection_element_id:-12881], primaryKey: false);
      insert('addresses', [ id: 19882, city: "Logan", state_code: "WV", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19882, nci_institute_code: "WV028", name: "Logan General Hospital" ,address_id: 19882,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12882,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV028",GROUP_DESC:"WV028 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12882,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WV028",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WV028",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12882,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV028", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12882 ,protection_group_id: -12882, protection_element_id:-12882], primaryKey: false);
      insert('addresses', [ id: 19883, city: "Bluefield", state_code: "WV", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19883, nci_institute_code: "WV029", name: "Bluefield Regional Medical Center" ,address_id: 19883,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12883,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV029",GROUP_DESC:"WV029 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12883,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WV029",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WV029",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12883,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV029", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12883 ,protection_group_id: -12883, protection_element_id:-12883], primaryKey: false);
      insert('addresses', [ id: 19884, city: "Charleston", state_code: "WV", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19884, nci_institute_code: "WV030", name: "Robert C. Byrd Health Sciences Center" ,address_id: 19884,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12884,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV030",GROUP_DESC:"WV030 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12884,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WV030",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WV030",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12884,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV030", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12884 ,protection_group_id: -12884, protection_element_id:-12884], primaryKey: false);
      insert('addresses', [ id: 19885, city: "Charleston", state_code: "WV", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19885, nci_institute_code: "WV031", name: "Charleston Radiation Therapy Consultants LLC" ,address_id: 19885,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12885,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV031",GROUP_DESC:"WV031 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12885,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WV031",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WV031",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12885,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV031", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12885 ,protection_group_id: -12885, protection_element_id:-12885], primaryKey: false);
      insert('addresses', [ id: 19886, city: "Morgantown", state_code: "WV", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19886, nci_institute_code: "WV032", name: "Morgantown Internal Medicine Group Inc" ,address_id: 19886,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12886,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV032",GROUP_DESC:"WV032 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12886,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WV032",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WV032",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12886,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV032", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12886 ,protection_group_id: -12886, protection_element_id:-12886], primaryKey: false);
      insert('addresses', [ id: 19887, city: "Charleston", state_code: "WV", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19887, nci_institute_code: "WV034", name: "Womens and Childrens Hospital" ,address_id: 19887,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12887,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV034",GROUP_DESC:"WV034 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12887,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WV034",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WV034",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12887,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV034", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12887 ,protection_group_id: -12887, protection_element_id:-12887], primaryKey: false);
      insert('addresses', [ id: 19888, city: "Oak Hill", state_code: "WV", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19888, nci_institute_code: "WV035", name: "Plateau Medical Center" ,address_id: 19888,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12888,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV035",GROUP_DESC:"WV035 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12888,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WV035",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WV035",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12888,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV035", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12888 ,protection_group_id: -12888, protection_element_id:-12888], primaryKey: false);
      insert('addresses', [ id: 19889, city: "Beckley", state_code: "WV", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19889, nci_institute_code: "WV036", name: "Beckley Appalachian Regional Hospital" ,address_id: 19889,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12889,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV036",GROUP_DESC:"WV036 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12889,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WV036",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WV036",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12889,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV036", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12889 ,protection_group_id: -12889, protection_element_id:-12889], primaryKey: false);
      insert('addresses', [ id: 19890, city: "Wheeling", state_code: "WV", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19890, nci_institute_code: "WV037", name: "Wheeling Valley" ,address_id: 19890,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12890,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV037",GROUP_DESC:"WV037 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12890,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WV037",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WV037",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12890,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV037", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12890 ,protection_group_id: -12890, protection_element_id:-12890], primaryKey: false);
      insert('addresses', [ id: 19891, city: "Princeton", state_code: "WV", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19891, nci_institute_code: "WV038", name: "Princeton Hematology/Oncology" ,address_id: 19891,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12891,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV038",GROUP_DESC:"WV038 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12891,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WV038",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WV038",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12891,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV038", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12891 ,protection_group_id: -12891, protection_element_id:-12891], primaryKey: false);
      insert('addresses', [ id: 19892, city: "Princeton", state_code: "WV", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19892, nci_institute_code: "WV039", name: "The Center for Cancer Care" ,address_id: 19892,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12892,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV039",GROUP_DESC:"WV039 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12892,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WV039",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WV039",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12892,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV039", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12892 ,protection_group_id: -12892, protection_element_id:-12892], primaryKey: false);
      insert('addresses', [ id: 19893, city: "Weirton", state_code: "WV", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19893, nci_institute_code: "WV040", name: "Weirton Medical Center" ,address_id: 19893,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12893,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV040",GROUP_DESC:"WV040 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12893,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WV040",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WV040",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12893,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV040", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12893 ,protection_group_id: -12893, protection_element_id:-12893], primaryKey: false);
      insert('addresses', [ id: 19894, city: "Hurricane", state_code: "WV", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19894, nci_institute_code: "WV041", name: "Putnam General Hospital" ,address_id: 19894,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12894,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV041",GROUP_DESC:"WV041 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12894,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WV041",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WV041",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12894,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV041", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12894 ,protection_group_id: -12894, protection_element_id:-12894], primaryKey: false);
      insert('addresses', [ id: 19895, city: "Morgantown", state_code: "WV", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19895, nci_institute_code: "WV042", name: "Blanchette Rockefeller Neurosciences Institute" ,address_id: 19895,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12895,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV042",GROUP_DESC:"WV042 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12895,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WV042",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WV042",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12895,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV042", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12895 ,protection_group_id: -12895, protection_element_id:-12895], primaryKey: false);
      insert('addresses', [ id: 19896, city: "Charleston", state_code: "WV", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19896, nci_institute_code: "WV043", name: "Charleston Area Medical Center Health Education & Research Institute" ,address_id: 19896,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12896,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV043",GROUP_DESC:"WV043 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12896,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WV043",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WV043",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12896,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV043", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12896 ,protection_group_id: -12896, protection_element_id:-12896], primaryKey: false);
      insert('addresses', [ id: 19897, city: "Huntington", state_code: "WV", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19897, nci_institute_code: "WV044", name: "Huntington Internal Medicine Group Inc" ,address_id: 19897,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12897,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV044",GROUP_DESC:"WV044 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12897,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WV044",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WV044",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12897,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV044", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12897 ,protection_group_id: -12897, protection_element_id:-12897], primaryKey: false);
    }

    void m36() {
        // all36 (19 terms)
      insert('addresses', [ id: 19898, city: "Charleston", state_code: "WV", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19898, nci_institute_code: "WV045", name: "David Lee Outpatient Cancer Center" ,address_id: 19898,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12898,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV045",GROUP_DESC:"WV045 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12898,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WV045",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WV045",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12898,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV045", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12898 ,protection_group_id: -12898, protection_element_id:-12898], primaryKey: false);
      insert('addresses', [ id: 19899, city: "Huntington", state_code: "WV", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19899, nci_institute_code: "WV046", name: "Edwards Comprehensive Cancer Center" ,address_id: 19899,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12899,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV046",GROUP_DESC:"WV046 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12899,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WV046",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WV046",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12899,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV046", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12899 ,protection_group_id: -12899, protection_element_id:-12899], primaryKey: false);
      insert('addresses', [ id: 19900, city: "Nutter Fort", state_code: "WV", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19900, nci_institute_code: "WV047", name: "Roger K Pons MD Inc" ,address_id: 19900,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12900,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV047",GROUP_DESC:"WV047 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12900,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WV047",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WV047",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12900,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV047", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12900 ,protection_group_id: -12900, protection_element_id:-12900], primaryKey: false);
      insert('addresses', [ id: 19901, city: "Martinsburg", state_code: "WV", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19901, nci_institute_code: "WV048", name: "Gateway Hematology Oncology" ,address_id: 19901,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12901,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV048",GROUP_DESC:"WV048 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12901,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WV048",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WV048",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12901,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV048", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12901 ,protection_group_id: -12901, protection_element_id:-12901], primaryKey: false);
      insert('addresses', [ id: 19902, city: "Clarksburg", state_code: "WV", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19902, nci_institute_code: "WV049", name: "Primary Oncology Network PLLC" ,address_id: 19902,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12902,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV049",GROUP_DESC:"WV049 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12902,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WV049",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WV049",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12902,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WV049", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12902 ,protection_group_id: -12902, protection_element_id:-12902], primaryKey: false);
      insert('addresses', [ id: 19903, city: "Cheyenne", state_code: "WY", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19903, nci_institute_code: "WY001", name: "Veterans Administration Hospital" ,address_id: 19903,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12903,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WY001",GROUP_DESC:"WY001 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12903,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WY001",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WY001",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12903,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WY001", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12903 ,protection_group_id: -12903, protection_element_id:-12903], primaryKey: false);
      insert('addresses', [ id: 19904, city: "Cheyenne", state_code: "WY", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19904, nci_institute_code: "WY003", name: "Memorial Hospital of Laramie County" ,address_id: 19904,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12904,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WY003",GROUP_DESC:"WY003 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12904,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WY003",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WY003",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12904,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WY003", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12904 ,protection_group_id: -12904, protection_element_id:-12904], primaryKey: false);
      insert('addresses', [ id: 19905, city: "Sheridan", state_code: "WY", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19905, nci_institute_code: "WY004", name: "Welch Cancer Center" ,address_id: 19905,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12905,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WY004",GROUP_DESC:"WY004 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12905,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WY004",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WY004",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12905,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WY004", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12905 ,protection_group_id: -12905, protection_element_id:-12905], primaryKey: false);
      insert('addresses', [ id: 19906, city: "Cheyenne", state_code: "WY", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19906, nci_institute_code: "WY005", name: "United Medical Center" ,address_id: 19906,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12906,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WY005",GROUP_DESC:"WY005 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12906,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WY005",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WY005",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12906,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WY005", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12906 ,protection_group_id: -12906, protection_element_id:-12906], primaryKey: false);
      insert('addresses', [ id: 19907, city: "Cheyenne", state_code: "WY", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19907, nci_institute_code: "WY006", name: "Intrl Med/Neuro., P.C." ,address_id: 19907,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12907,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WY006",GROUP_DESC:"WY006 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12907,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WY006",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WY006",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12907,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WY006", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12907 ,protection_group_id: -12907, protection_element_id:-12907], primaryKey: false);
      insert('addresses', [ id: 19908, city: "Cody", state_code: "WY", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19908, nci_institute_code: "WY007", name: "West Park Hospital" ,address_id: 19908,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12908,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WY007",GROUP_DESC:"WY007 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12908,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WY007",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WY007",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12908,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WY007", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12908 ,protection_group_id: -12908, protection_element_id:-12908], primaryKey: false);
      insert('addresses', [ id: 19909, city: "Cheyenne", state_code: "WY", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19909, nci_institute_code: "WY008", name: "Internal Medical Group, P.C." ,address_id: 19909,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12909,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WY008",GROUP_DESC:"WY008 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12909,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WY008",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WY008",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12909,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WY008", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12909 ,protection_group_id: -12909, protection_element_id:-12909], primaryKey: false);
      insert('addresses', [ id: 19910, city: "Casper", state_code: "WY", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19910, nci_institute_code: "WY009", name: "Wyoming Medical Center" ,address_id: 19910,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12910,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WY009",GROUP_DESC:"WY009 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12910,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WY009",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WY009",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12910,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WY009", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12910 ,protection_group_id: -12910, protection_element_id:-12910], primaryKey: false);
      insert('addresses', [ id: 19911, city: "Laramie", state_code: "WY", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19911, nci_institute_code: "WY010", name: "Ivinson Memorial Hospital" ,address_id: 19911,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12911,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WY010",GROUP_DESC:"WY010 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12911,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WY010",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WY010",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12911,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WY010", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12911 ,protection_group_id: -12911, protection_element_id:-12911], primaryKey: false);
      insert('addresses', [ id: 19912, city: "Cheyenne", state_code: "WY", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19912, nci_institute_code: "WY011", name: "Cheyenne Hematology/Oncology Services" ,address_id: 19912,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12912,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WY011",GROUP_DESC:"WY011 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12912,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WY011",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WY011",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12912,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WY011", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12912 ,protection_group_id: -12912, protection_element_id:-12912], primaryKey: false);
      insert('addresses', [ id: 19913, city: "Cody", state_code: "WY", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19913, nci_institute_code: "WY012", name: "Big Horn Basin Cancer Center" ,address_id: 19913,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12913,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WY012",GROUP_DESC:"WY012 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12913,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WY012",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WY012",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12913,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WY012", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12913 ,protection_group_id: -12913, protection_element_id:-12913], primaryKey: false);
      insert('addresses', [ id: 19914, city: "Sheridan", state_code: "WY", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19914, nci_institute_code: "WY013", name: "Hematology -Oncology Centers of the Northern Rockies - Sheridan" ,address_id: 19914,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12914,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WY013",GROUP_DESC:"WY013 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12914,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WY013",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WY013",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12914,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WY013", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12914 ,protection_group_id: -12914, protection_element_id:-12914], primaryKey: false);
      insert('addresses', [ id: 19915, city: "Cody", state_code: "WY", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19915, nci_institute_code: "WY014", name: "Hematology-Oncology Centers of the Northern Rockies - Cody" ,address_id: 19915,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12915,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WY014",GROUP_DESC:"WY014 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12915,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WY014",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WY014",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12915,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WY014", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12915 ,protection_group_id: -12915, protection_element_id:-12915], primaryKey: false);
      insert('addresses', [ id: 19916, city: "Buffalo", state_code: "WY", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 19916, nci_institute_code: "WY015", name: "Family Medical Center" ,address_id: 19916,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -12916,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WY015",GROUP_DESC:"WY015 group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -12916,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.WY015",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.WY015",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -12916,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.WY015", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:12916 ,protection_group_id: -12916, protection_element_id:-12916], primaryKey: false);
    }

    void down() {
        execute("delete from csm_pg_pe where pg_pe_id >= 5000 and  pg_pe_id <= 12916 ");
        execute("delete from CSM_PROTECTION_GROUP where protection_group_id  <= -5000 ");
        execute("delete from csm_protection_element where protection_element_id <= -5000 ");
        execute("delete from csm_group where group_id <= -5000 ");
        execute("DELETE from addresses where id >= 12000 and id < 19916");
        execute("DELETE from organizations where id >= 12000 and id < 19916");
    }
}
