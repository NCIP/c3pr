
class StaticDataCSMInserSitePE extends edu.northwestern.bioinformatics.bering.Migration {

	void up() {
        insert('csm_group',[GROUP_ID: -1000,GROUP_NAME:"edu.duke.cabig.c3pr.domain.HealthcareSite.Duke",GROUP_DESC: "Duke University Group",application_id: 1], primaryKey: false);
        insert('csm_protection_element',[protection_element_id: -1000,protection_element_name:"edu.duke.cabig.c3pr.domain.HealthcareSite.Duke", object_id: "edu.duke.cabig.c3pr.domain.HealthcareSite.Duke",application_id: 1], primaryKey: false);
        insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -1000,PROTECTION_GROUP_NAME:"edu.duke.cabig.c3pr.domain.HealthcareSite.Duke", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
        insert('csm_pg_pe',[pg_pe_id: -1000,protection_group_id: -1000, protection_element_id: -1000], primaryKey: false);
        insert('csm_user_group_role_pg',[USER_GROUP_ROLE_PG_ID: -1000,GROUP_ID: -1000, PROTECTION_GROUP_ID: -1000, ROLE_ID: 19], primaryKey: false);


        insert('csm_group',[GROUP_ID: -1001,GROUP_NAME:"edu.duke.cabig.c3pr.domain.HealthcareSite.Warren",GROUP_DESC: "Warren Grant Magnuson Clinical Center",application_id: 1], primaryKey: false);
        insert('csm_protection_element',[protection_element_id: -1001,protection_element_name:"edu.duke.cabig.c3pr.domain.HealthcareSite.Warren", object_id: "edu.duke.cabig.c3pr.domain.HealthcareSite.Warren",application_id: 1], primaryKey: false);
        insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -1001,PROTECTION_GROUP_NAME:"edu.duke.cabig.c3pr.domain.HealthcareSite.Warren", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
        insert('csm_pg_pe',[pg_pe_id: -1001,protection_group_id: -1001, protection_element_id: -1001], primaryKey: false);
        insert('csm_user_group_role_pg',[USER_GROUP_ROLE_PG_ID: -1001,GROUP_ID: -1001, PROTECTION_GROUP_ID: -1001, ROLE_ID: 19], primaryKey: false);

        insert('csm_group',[GROUP_ID: -1002,GROUP_NAME:"edu.duke.cabig.c3pr.domain.HealthcareSite.NCI",GROUP_DESC: "National Cancer Institute",application_id: 1], primaryKey: false);
        insert('csm_protection_element',[protection_element_id: -1002,protection_element_name:"edu.duke.cabig.c3pr.domain.HealthcareSite.NCI", object_id: "edu.duke.cabig.c3pr.domain.HealthcareSite.NCI",application_id: 1], primaryKey: false);
        insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -1002,PROTECTION_GROUP_NAME:"edu.duke.cabig.c3pr.domain.HealthcareSite.NCI", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
        insert('csm_pg_pe',[pg_pe_id: -1002,protection_group_id: -1002, protection_element_id: -1002], primaryKey: false);
        insert('csm_user_group_role_pg',[USER_GROUP_ROLE_PG_ID: -1002,GROUP_ID: -1002, PROTECTION_GROUP_ID: -1002, ROLE_ID: 19], primaryKey: false);


        insert('csm_group',[GROUP_ID: -1003,GROUP_NAME:"edu.duke.cabig.c3pr.domain.HealthcareSite.C3PR",GROUP_DESC: "C3PR",application_id: 1], primaryKey: false);
        insert('csm_protection_element',[protection_element_id: -1003,protection_element_name:"edu.duke.cabig.c3pr.domain.HealthcareSite.C3PR", object_id: "edu.duke.cabig.c3pr.domain.HealthcareSite.C3PR",application_id: 1], primaryKey: false);
        insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -1003,PROTECTION_GROUP_NAME:"edu.duke.cabig.c3pr.domain.HealthcareSite.C3PR", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
        insert('csm_pg_pe',[pg_pe_id: -1003,protection_group_id: -1003, protection_element_id: -1003], primaryKey: false);
        insert('csm_user_group_role_pg',[USER_GROUP_ROLE_PG_ID: -1003,GROUP_ID: -1003, PROTECTION_GROUP_ID: -1003, ROLE_ID: 19], primaryKey: false);

        insert('csm_group',[GROUP_ID: -1004,GROUP_NAME:"edu.duke.cabig.c3pr.domain.HealthcareSite.Wake",GROUP_DESC: "Wake Forest Comprehensive Cancer Center",application_id: 1], primaryKey: false);
        insert('csm_protection_element',[protection_element_id: -1004,protection_element_name:"edu.duke.cabig.c3pr.domain.HealthcareSite.Wake", object_id: "edu.duke.cabig.c3pr.domain.HealthcareSite.Wake",application_id: 1], primaryKey: false);
        insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -1004,PROTECTION_GROUP_NAME:"edu.duke.cabig.c3pr.domain.HealthcareSite.Wake", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
        insert('csm_pg_pe',[pg_pe_id: -1004,protection_group_id: -1004, protection_element_id: -1004], primaryKey: false);
        insert('csm_user_group_role_pg',[USER_GROUP_ROLE_PG_ID: -1004,GROUP_ID: -1004, PROTECTION_GROUP_ID: -1004, ROLE_ID: 19], primaryKey: false);

	}


	void down(){
	    execute("delete from csm_protection_element where protection_element_id IN (-1000,-1001,-1002,-1003,-1004)");
	    execute("delete from csm_pg_pe where pg_pe_id IN (-1000,-1001,-1002,-1003,-1004)");

	}
}