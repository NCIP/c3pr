class LoadingCooperativeGroups extends edu.northwestern.bioinformatics.bering.Migration {

    void up() {

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
        }

        void m0()
        {

        insert('csm_group',[GROUP_ID: -1016,GROUP_NAME:"edu.duke.cabig.c3pr.domain.HealthcareSite.ACOSOG",GROUP_DESC: "American College of Surgeons Oncology Trials Group",application_id: 1], primaryKey: false);
        insert('csm_protection_element',[protection_element_id: -1016,protection_element_name:"edu.duke.cabig.c3pr.domain.HealthcareSite.ACOSOG", object_id: "edu.duke.cabig.c3pr.domain.HealthcareSite.ACOSOG",application_id: 1], primaryKey: false);
        insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -1016,PROTECTION_GROUP_NAME:"edu.duke.cabig.c3pr.domain.HealthcareSite.ACOSOG", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
        insert('csm_pg_pe',[pg_pe_id: -1016,protection_group_id: -1016, protection_element_id: -1016], primaryKey: false);
        //organization group has ACCESS to organization
        insert('csm_user_group_role_pg',[USER_GROUP_ROLE_PG_ID: -1016,GROUP_ID: -1016, PROTECTION_GROUP_ID: -1016, ROLE_ID: 19], primaryKey: false);
      	 }

      	 void m1()
        {
        insert('csm_group',[GROUP_ID: -1017,GROUP_NAME:"edu.duke.cabig.c3pr.domain.HealthcareSite.CALGB",GROUP_DESC: "Cancer and Leukemia Group B",application_id: 1], primaryKey: false);
        insert('csm_protection_element',[protection_element_id: -1017,protection_element_name:"edu.duke.cabig.c3pr.domain.HealthcareSite.CALGB", object_id: "edu.duke.cabig.c3pr.domain.HealthcareSite.CALGB",application_id: 1], primaryKey: false);
        insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -1017,PROTECTION_GROUP_NAME:"edu.duke.cabig.c3pr.domain.HealthcareSite.CALGB", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
        insert('csm_pg_pe',[pg_pe_id: -1017,protection_group_id: -1017, protection_element_id: -1017], primaryKey: false);
        //organization group has ACCESS to organization
        insert('csm_user_group_role_pg',[USER_GROUP_ROLE_PG_ID: -1017,GROUP_ID: -1017, PROTECTION_GROUP_ID: -1017, ROLE_ID: 19], primaryKey: false);
         }

      	 void m2()
        {
        insert('csm_group',[GROUP_ID: -1018,GROUP_NAME:"edu.duke.cabig.c3pr.domain.HealthcareSite.ECOG",GROUP_DESC: "Eastern Cooperative Oncology Group",application_id: 1], primaryKey: false);
        insert('csm_protection_element',[protection_element_id: -1018,protection_element_name:"edu.duke.cabig.c3pr.domain.HealthcareSite.ECOG", object_id: "edu.duke.cabig.c3pr.domain.HealthcareSite.ECOG",application_id: 1], primaryKey: false);
        insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -1018,PROTECTION_GROUP_NAME:"edu.duke.cabig.c3pr.domain.HealthcareSite.ECOG", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
        insert('csm_pg_pe',[pg_pe_id: -1018,protection_group_id: -1018, protection_element_id: -1018], primaryKey: false);
        //organization group has ACCESS to organization
        insert('csm_user_group_role_pg',[USER_GROUP_ROLE_PG_ID: -1018,GROUP_ID: -1018, PROTECTION_GROUP_ID: -1018, ROLE_ID: 19], primaryKey: false);
      	 }

      	 void m3()
        {
        insert('csm_group',[GROUP_ID: -1019,GROUP_NAME:"edu.duke.cabig.c3pr.domain.HealthcareSite.GOG",GROUP_DESC: "Gynecologic Oncology Group",application_id: 1], primaryKey: false);
        insert('csm_protection_element',[protection_element_id: -1019,protection_element_name:"edu.duke.cabig.c3pr.domain.HealthcareSite.GOG", object_id: "edu.duke.cabig.c3pr.domain.HealthcareSite.GOG",application_id: 1], primaryKey: false);
        insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -1019,PROTECTION_GROUP_NAME:"edu.duke.cabig.c3pr.domain.HealthcareSite.GOG", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
        insert('csm_pg_pe',[pg_pe_id: -1019,protection_group_id: -1019, protection_element_id: -1019], primaryKey: false);
        //admin has ACCESS to site
        insert('csm_user_group_role_pg',[USER_GROUP_ROLE_PG_ID: -1019,GROUP_ID: -1019, PROTECTION_GROUP_ID: -1019, ROLE_ID: 19], primaryKey: false);
      	 }

      	 void m4()
        {
        insert('csm_group',[GROUP_ID: -1020,GROUP_NAME:"edu.duke.cabig.c3pr.domain.HealthcareSite.NCCTG",GROUP_DESC: "North Central Cancer Treatment Group",application_id: 1], primaryKey: false);
        insert('csm_protection_element',[protection_element_id: -1020,protection_element_name:"edu.duke.cabig.c3pr.domain.HealthcareSite.NCCTG", object_id: "edu.duke.cabig.c3pr.domain.HealthcareSite.NCCTG",application_id: 1], primaryKey: false);
        insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -1020,PROTECTION_GROUP_NAME:"edu.duke.cabig.c3pr.domain.HealthcareSite.NCCTG", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
        insert('csm_pg_pe',[pg_pe_id: -1020,protection_group_id: -1020, protection_element_id: -1020], primaryKey: false);
        //admin has ACCESS to site
        insert('csm_user_group_role_pg',[USER_GROUP_ROLE_PG_ID: -1020,GROUP_ID: -1020, PROTECTION_GROUP_ID: -1020, ROLE_ID: 19], primaryKey: false);
      	 }
      	 void m5()
        {
        insert('csm_group',[GROUP_ID: -1021,GROUP_NAME:"edu.duke.cabig.c3pr.domain.HealthcareSite.NCIC",GROUP_DESC: "National Cancer Institute of Canada Clinical Trials Group",application_id: 1], primaryKey: false);
        insert('csm_protection_element',[protection_element_id: -1021,protection_element_name:"edu.duke.cabig.c3pr.domain.HealthcareSite.NCIC", object_id: "edu.duke.cabig.c3pr.domain.HealthcareSite.NCIC",application_id: 1], primaryKey: false);
        insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -1021,PROTECTION_GROUP_NAME:"edu.duke.cabig.c3pr.domain.HealthcareSite.NCIC", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
        insert('csm_pg_pe',[pg_pe_id: -1021,protection_group_id: -1021, protection_element_id: -1021], primaryKey: false);
        //admin has ACCESS to site
        insert('csm_user_group_role_pg',[USER_GROUP_ROLE_PG_ID: -1021,GROUP_ID: -1021, PROTECTION_GROUP_ID: -1021, ROLE_ID: 19], primaryKey: false);
      	 }

      	 void m6()
        {
        insert('csm_group',[GROUP_ID: -1022,GROUP_NAME:"edu.duke.cabig.c3pr.domain.HealthcareSite.NSABP",GROUP_DESC: "National Surgical Adjuvant Breast and Bowel Project",application_id: 1], primaryKey: false);
        insert('csm_protection_element',[protection_element_id: -1022,protection_element_name:"edu.duke.cabig.c3pr.domain.HealthcareSite.NSABP", object_id: "edu.duke.cabig.c3pr.domain.HealthcareSite.NSABP",application_id: 1], primaryKey: false);
        insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -1022,PROTECTION_GROUP_NAME:"edu.duke.cabig.c3pr.domain.HealthcareSite.NSABP", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
        insert('csm_pg_pe',[pg_pe_id: -1022,protection_group_id: -1022, protection_element_id: -1022], primaryKey: false);
        //admin has ACCESS to site
        insert('csm_user_group_role_pg',[USER_GROUP_ROLE_PG_ID: -1022,GROUP_ID: -1022, PROTECTION_GROUP_ID: -1022, ROLE_ID: 19], primaryKey: false);
      	 }

      	 void m7()
        {
        insert('csm_group',[GROUP_ID: -1023,GROUP_NAME:"edu.duke.cabig.c3pr.domain.HealthcareSite.RTOG",GROUP_DESC: "Radiation Therapy Oncology Group",application_id: 1], primaryKey: false);
        insert('csm_protection_element',[protection_element_id: -1023,protection_element_name:"edu.duke.cabig.c3pr.domain.HealthcareSite.RTOG", object_id: "edu.duke.cabig.c3pr.domain.HealthcareSite.RTOG",application_id: 1], primaryKey: false);
        insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -1023,PROTECTION_GROUP_NAME:"edu.duke.cabig.c3pr.domain.HealthcareSite.RTOG", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
        insert('csm_pg_pe',[pg_pe_id: -1023,protection_group_id: -1023, protection_element_id: -1023], primaryKey: false);
        //admin has ACCESS to site
        insert('csm_user_group_role_pg',[USER_GROUP_ROLE_PG_ID: -1023,GROUP_ID: -1023, PROTECTION_GROUP_ID: -1023, ROLE_ID: 19], primaryKey: false);
      	 }
      	 void m8()
        {
        insert('csm_group',[GROUP_ID: -1024,GROUP_NAME:"edu.duke.cabig.c3pr.domain.HealthcareSite.SWOG",GROUP_DESC: "Southwest Oncology Group",application_id: 1], primaryKey: false);
        insert('csm_protection_element',[protection_element_id: -1024,protection_element_name:"edu.duke.cabig.c3pr.domain.HealthcareSite.SWOG", object_id: "edu.duke.cabig.c3pr.domain.HealthcareSite.SWOG",application_id: 1], primaryKey: false);
        insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -1024,PROTECTION_GROUP_NAME:"edu.duke.cabig.c3pr.domain.HealthcareSite.SWOG", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
        insert('csm_pg_pe',[pg_pe_id: -1024,protection_group_id: -1024, protection_element_id: -1024], primaryKey: false);
        //admin has ACCESS to site
        insert('csm_user_group_role_pg',[USER_GROUP_ROLE_PG_ID: -1024,GROUP_ID: -1024, PROTECTION_GROUP_ID: -1024, ROLE_ID: 19], primaryKey: false);
      	 }

      	 void m9()
        {
        insert('csm_group',[GROUP_ID: -1025,GROUP_NAME:"edu.duke.cabig.c3pr.domain.HealthcareSite.COG",GROUP_DESC: "Children's Oncology Group",application_id: 1], primaryKey: false);
        insert('csm_protection_element',[protection_element_id: -1025,protection_element_name:"edu.duke.cabig.c3pr.domain.HealthcareSite.COG", object_id: "edu.duke.cabig.c3pr.domain.HealthcareSite.COG",application_id: 1], primaryKey: false);
        insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -1025,PROTECTION_GROUP_NAME:"edu.duke.cabig.c3pr.domain.HealthcareSite.COG", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
        insert('csm_pg_pe',[pg_pe_id: -1025,protection_group_id: -1025, protection_element_id: -1025], primaryKey: false);
        //admin has ACCESS to site
        insert('csm_user_group_role_pg',[USER_GROUP_ROLE_PG_ID: -1025,GROUP_ID: -1025, PROTECTION_GROUP_ID: -1025, ROLE_ID: 19], primaryKey: false);
      	 }

    void down() {

	    execute("delete from csm_protection_element where protection_element_id IN (-1016,-1017,-1018,-1019,-1020,-1021,-1022,-1023,-1024,-1025)");
	    execute("delete from csm_pg_pe where pg_pe_id IN (-1016,-1017,-1018,-1019,-1020,-1021,-1022,-1023,-1024,-1025)");

   	}

}