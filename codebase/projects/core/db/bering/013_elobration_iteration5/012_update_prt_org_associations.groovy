class UpdatePrtOrgAssociations extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
	if (databaseMatches('postgresql')){
	execute("insert into prt_org_associations (prt_id,org_id) (select * from (select participants.id as prt_id, identifiers.hcs_id
			as org_id from participants inner join identifiers on participants.id= identifiers.prt_id) as part_orgs where
		 	prt_id = (select prt_id from (select distinct(prt_id) as common_id, participants.id as prt_id from prt_org_associations right
		 	join participants on prt_org_associations.prt_id =participants.id)as parts where common_id is null))");
		}
		
	}
	
	if (databaseMatches('oracle')){
	execute("insert into prt_org_associations (prt_id,org_id) (select * from (select participants.id as prt_id, identifiers.hcs_id
			as org_id from participants inner join identifiers on participants.id= identifiers.prt_id) as part_orgs where
		 	prt_id = (select prt_id from (select distinct(prt_id) as common_id, participants.id as prt_id from prt_org_associations right
		 	join participants on prt_org_associations.prt_id =participants.id)as parts where common_id is null))");
		}
		
	}

	void down(){
	
	}
}
