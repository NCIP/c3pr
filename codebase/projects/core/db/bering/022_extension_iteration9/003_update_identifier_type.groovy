class UpdateDtypeContactMechanism extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
       	execute("update identifiers set type='CTEP' where type='CTEP Identifier'");
       	execute("update identifiers set type='NCI' where type='NCI Identifier'");
       	execute("update identifiers set type='AI' where type='Assigned Identifier'");
       	execute("update identifiers set type='COORDINATING_CENTER_IDENTIFIER' where type='Coordinating Center Identifier'");
       	execute("update identifiers set type='COORDINATING_CENTER_ASSIGNED_STUDY_SUBJECT_IDENTIFIER' where type='Coordinating Center Assigned Study Subject Identifier'");
       	execute("update identifiers set type='CLINICAL_TRIALS_GOV_IDENTIFIER' where type='ClinicalTrials.gov Identifier'");
       	execute("update identifiers set type='COOPERATIVE_GROUP_IDENTIFIER' where type='Cooperative Group Identifier'");
       	execute("update identifiers set type='LOCAL' where type='Local'");
       	execute("update identifiers set type='STUDY_FUNDING_SPONSOR' where type='Study Funding Sponsor'");
       	execute("update identifiers set type='MRN' where type='MRN'");
       	execute("update identifiers set type='PROTOCOL_AUTHORITY_IDENTIFIER' where type='Protocol Authority Identifier'");
       	execute("update identifiers set type='C3D_IDENTIFIER' where type='C3D Identifier'");
       	execute("update identifiers set type='C3PR' where type='C3PR'");
       	execute("update identifiers set type='SITE_IRB_IDENTIFIER' where type='Site IRB Identifier'");
       	execute("update identifiers set type='SITE_IDENTIFIER' where type='Site Identifier'");
       	execute("update identifiers set type='STUDY_SUBJECT_IDENTIFIER' where type='Study Subject Identifier'");
       	execute("update identifiers set type='GRID_IDENTIFIER' where type='Grid Identifier'");
    }
  
    void down() {
    }
}