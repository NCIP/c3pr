class AddForeignKeysToConsentTables extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
	 	execute('ALTER TABLE consent_questions ADD CONSTRAINT FK_CONQUE_CON FOREIGN KEY (con_id) REFERENCES consents(ID)');
	 	execute('ALTER TABLE sub_con_que_ans ADD CONSTRAINT FK_SUBCONQUEANS_CONQUE FOREIGN KEY (con_que_id) REFERENCES consent_questions (ID)');
	 	execute('ALTER TABLE sub_con_que_ans ADD CONSTRAINT FK_SUBCONQUEANS_STUSUBCON FOREIGN KEY (stu_sub_con_ver_id) REFERENCES study_subject_consents (ID)');
	}
	void down() {
	 	execute('ALTER TABLE consent_questions drop CONSTRAINT FK_CONQUE_CON');
	 	execute('ALTER TABLE sub_con_que_ans drop CONSTRAINT FK_SUBCONQUEANS_CONQUE ');
	 	execute('ALTER TABLE sub_con_que_ans drop CONSTRAINT FK_SUBCONQUEANS_STUSUBCON');
    }
}
