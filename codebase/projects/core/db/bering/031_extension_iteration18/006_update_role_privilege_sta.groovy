class UpdateRolePrivilegesForSta extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
        execute("delete from role_privileges where role_name='study_team_administrator' and object_id='edu.duke.cabig.c3pr.utils.web.navigation.Task.ResearchStaff' and privilege='READ'");
        execute("delete from role_privileges where role_name='study_team_administrator' and object_id='edu.duke.cabig.c3pr.utils.web.navigation.Task.Investigator' and privilege='READ'");
	}
	
	void down() {
    }
}



