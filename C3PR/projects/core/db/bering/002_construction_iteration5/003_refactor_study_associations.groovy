class RefactorStudyAssociations extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
        dropColumn('ELIGIBILITY_CRITERIA','STU_ID');
        dropColumn('STRAT_CRITERIA','STUDY_ID');        
    }

    void down() {
        addColumn('ELIGIBILITY_CRITERIA','STU_ID');
        addColumn('STRAT_CRITERIA','STUDY_ID');     
        execute("ALTER TABLE ELIGIBILITY_CRITERIA ADD CONSTRAINT FK_ELGCT_STU FOREIGN KEY (STU_ID) REFERENCES STUDIES (ID)");
        execute("ALTER TABLE STRAT_CRITERIA ADD CONSTRAINT FK_STR_CRI_STUDY FOREIGN KEY (study_id) REFERENCES STUDIES (ID)");
    }
}