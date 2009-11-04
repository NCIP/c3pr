class DropDuplicateNciInstitute extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
		execute("delete from csm_pg_pe where pg_pe_id IN (9999)");
		execute("delete from CSM_PROTECTION_GROUP where PROTECTION_GROUP_ID IN (-9999)");
		execute("delete from csm_protection_element where protection_element_id IN (-9999)");
		execute("delete from csm_group where GROUP_ID IN (-9999)");
		execute("delete from organizations where id IN (16999)");
		execute("delete from addresses where id IN (16999)");
	}

	void down(){
	
	  insert('addresses', [ id: 16999, city: "Rockville", state_code: "MD", country_code: "USA",version:0], primaryKey: false) 
      insert('organizations', [ id: 16999, nci_institute_code: "NCI", name: "National Cancer Institute" ,address_id: 16999,version:0], primaryKey: false) 
      insert('csm_group',[GROUP_ID: -9999,GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.NCI",GROUP_DESC:"NCI group",application_id: 1], primaryKey: false); 
      insert('csm_protection_element',[protection_element_id: -9999,protection_element_name:"gov.nih.nci.cabig.caaers.domain.Organization.NCI",object_id: "gov.nih.nci.cabig.caaers.domain.Organization.NCI",application_id: 1], primaryKey: false); 
      insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -9999,PROTECTION_GROUP_NAME:"gov.nih.nci.cabig.caaers.domain.Organization.NCI", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
      insert('csm_pg_pe',[pg_pe_id:9999 ,protection_group_id: -9999, protection_element_id:-9999], primaryKey: false);

	}
}