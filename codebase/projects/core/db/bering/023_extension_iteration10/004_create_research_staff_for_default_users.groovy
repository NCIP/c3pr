class CreateResearchStaffForDefaultUsers extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	if (databaseMatches('postgres')){
    		
    		// c3pr_admin
    		
    	 	execute("insert into research_staff (id,version,first_name,last_name,hcs_id,assigned_identifier,login_id,retired_indicator,password_last_set,num_failed_logins,dtype,status_code) values(nextVal('research_staff_id_seq'),0,'c3pr_admin','c3pr_admin', 16831,'c3pr_admin','1','false',localtimestamp,0,'Local','AC')");
			execute("update research_staff set hcs_id = (select organizations.id from organizations, identifiers where organizations.id = identifiers.org_id and identifiers.value like (select value from configuration where prop like 'localNciInstituteCode')) where first_name like 'c3pr_admin'");
			execute("insert into csm_user_group(user_group_id,user_id,group_id) values(-1002,1,-9831)");
			execute("update csm_user_group set group_id = (select group_id from csm_group where group_name like '%' || (select value from configuration where prop like 'localNciInstituteCode')  || '%') where user_group_id =-1002");
			
			// c3pr_registrar
    		
    	 	execute("insert into research_staff (id,version,first_name,last_name,hcs_id,assigned_identifier,login_id,retired_indicator,password_last_set,num_failed_logins,dtype,status_code) values(nextVal('research_staff_id_seq'),0,'c3pr_registrar','c3pr_registrar', 16831,'c3pr_registrar','2','false',localtimestamp,0,'Local','AC')");
			execute("update research_staff set hcs_id = (select organizations.id from organizations, identifiers where organizations.id = identifiers.org_id and identifiers.value like (select value from configuration where prop like 'localNciInstituteCode')) where first_name like 'c3pr_registrar'");
			execute("insert into csm_user_group(user_group_id,user_id,group_id) values(-1003,2,-9831)");
			execute("update csm_user_group set group_id = (select group_id from csm_group where group_name like '%' || (select value from configuration where prop like 'localNciInstituteCode')  || '%') where user_group_id =-1003");
			
			// c3pr_study_coordinator
    		
    	 	execute("insert into research_staff (id,version,first_name,last_name,hcs_id,assigned_identifier,login_id,retired_indicator,password_last_set,num_failed_logins,dtype,status_code) values(nextVal('research_staff_id_seq'),0,'c3pr_study_coordinator','c3pr_study_coordinator', 16831,'c3pr_study_coordinator','3','false',localtimestamp,0,'Local','AC')");
			execute("update research_staff set hcs_id = (select organizations.id from organizations, identifiers where organizations.id = identifiers.org_id and identifiers.value like (select value from configuration where prop like 'localNciInstituteCode')) where first_name like 'c3pr_study_coordinator'");
			execute("insert into csm_user_group(user_group_id,user_id,group_id) values(-1004,3,-9831)");
			execute("update csm_user_group set group_id = (select group_id from csm_group where group_name like '%' || (select value from configuration where prop like 'localNciInstituteCode')  || '%') where user_group_id =-1004");
			
			// ccts_demo1 user
    		
    	 	execute("insert into research_staff (id,version,first_name,last_name,hcs_id,assigned_identifier,login_id,retired_indicator,password_last_set,num_failed_logins,dtype,status_code) values(nextVal('research_staff_id_seq'),0,'ccts','demo1', 16831,'cctsdemo1','-1001','false',localtimestamp,0,'Local','AC')");
			execute("update research_staff set hcs_id = (select organizations.id from organizations, identifiers where organizations.id = identifiers.org_id and identifiers.value like (select value from configuration where prop like 'localNciInstituteCode')) where assigned_identifier like 'cctsdemo1'");
			execute("insert into csm_user_group(user_group_id,user_id,group_id) values(-1005,-1001,-9831)");
			execute("update csm_user_group set group_id = (select group_id from csm_group where group_name like '%' || (select value from configuration where prop like 'localNciInstituteCode')  || '%') where user_group_id =-1005");
		}
		if (databaseMatches('oracle')){
		}
	}
	void down() {
    }
}
