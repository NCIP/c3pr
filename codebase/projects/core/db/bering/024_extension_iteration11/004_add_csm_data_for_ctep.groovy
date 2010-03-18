class AddCsmDataForCtep extends edu.northwestern.bioinformatics.bering.Migration {
    
    void up() {
        execute("delete from csm_group where group_name='edu.duke.cabig.c3pr.domain.HealthcareSite.CTEP'");
        insert('csm_group',[GROUP_ID: -1006, GROUP_NAME:"edu.duke.cabig.c3pr.domain.HealthcareSite.CTEP",GROUP_DESC:"CTEP group",application_id: 1], primaryKey: false); 
        insert('csm_protection_element',[protection_element_id: -1006, protection_element_name:"edu.duke.cabig.c3pr.domain.HealthcareSite.CTEP",object_id: "edu.duke.cabig.c3pr.domain.HealthcareSite.CTEP",application_id: 1], primaryKey: false); 
        insert('CSM_PROTECTION_GROUP',[PROTECTION_GROUP_ID: -1006,PROTECTION_GROUP_NAME:"edu.duke.cabig.c3pr.domain.HealthcareSite.CTEP", PARENT_PROTECTION_GROUP_ID:6, application_id: 1, LARGE_ELEMENT_COUNT_FLAG:0], primaryKey: false);
        insert('csm_pg_pe',[pg_pe_id:-1006 ,protection_group_id: -1006, protection_element_id:-1006], primaryKey: false);
        insert('csm_user_group_role_pg',[user_group_role_pg_id:-12917,protection_group_id:-1006,group_id:-1006,role_id:19], primaryKey: false);

    }

    void down() {
        
    }
}
