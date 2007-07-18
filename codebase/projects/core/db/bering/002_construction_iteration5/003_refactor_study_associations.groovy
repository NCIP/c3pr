class RefactorStudyAssociations extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
        dropColumn('ELIGIBILITY_CRITERIAS','STU_ID');
        dropColumn('STRATIFICATION_CRITERION','STUDY_ID');        
    }

    void down() {
        addColumn('ELIGIBILITY_CRITERIAS','STU_ID');
        addColumn('STRATIFICATION_CRITERION','STUDY_ID');     
        execute("ALTER TABLE ELIGIBILITY_CRITERIAS ADD CONSTRAINT FK_ELGCT_STU FOREIGN KEY (STU_ID) REFERENCES STUDIES (ID)");
        execute("ALTER TABLE STRATIFICATION_CRITERION ADD CONSTRAINT FK_STR_CRI_STUDY FOREIGN KEY (study_id) REFERENCES STUDIES (ID)");
    }
}