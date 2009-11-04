class setHostedModeFlag extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
			dropColumn("studies","hosted_mode")
			addColumn('STUDY_ORGANIZATIONS','hosted_mode','boolean')
			execute("update STUDY_ORGANIZATIONS set hosted_mode = 'true' where hosted_mode is null")
	}

	void down(){
		
	}
}
