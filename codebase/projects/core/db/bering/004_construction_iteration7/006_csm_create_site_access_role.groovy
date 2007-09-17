
class CSMCreateSiteAccessRole extends edu.northwestern.bioinformatics.bering.Migration {

	void up() {
	    insert('CSM_ROLE',[role_id: 19, role_name:"edu.duke.cabig.c3pr.domain.HealthcareSite.ACCESS", role_description: "HealthcareSite access role", active_flag: 1, application_id: 1], primaryKey: false);
        insert('CSM_ROLE_PRIVILEGE',[role_privilege_id: 19, role_id: 19,privilege_id:2], primaryKey: false);
        insert('csm_user_group_role_pg',[USER_GROUP_ROLE_PG_ID: 33, GROUP_ID: 1, ROLE_ID: 19, PROTECTION_GROUP_ID: 6], primaryKey: false);

	}


	void down(){
	    execute("delete from csm_user_group_role_pg where USER_GROUP_ROLE_PG_ID IN (33)");
	    execute("delete from CSM_ROLE where role_id IN (19)");
	    execute("delete from CSM_ROLE_PRIVILEGE where role_id IN (19)");
	}
}