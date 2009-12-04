class AddMultisiteConfigDefaultProperty extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
		if (databaseMatches('postgres')){
			execute("INSERT INTO configuration(prop, value) SELECT 'outgoingMailUsername', 'c3prproject@gmail.com'  WHERE not exists (select * from configuration where prop = 'outgoingMailUsername')")
			execute("INSERT INTO configuration(prop, value) SELECT 'outgoingMailPassword', 'semanticbits'  WHERE not exists (select * from configuration where prop = 'outgoingMailPassword')")
    	}
    	if (databaseMatches('oracle')){
			execute("INSERT INTO configuration(prop, value) SELECT 'outgoingMailUsername', 'c3prproject@gmail.com' from dual  WHERE not exists (select * from configuration where prop = 'outgoingMailUsername')")
			execute("INSERT INTO configuration(prop, value) SELECT 'outgoingMailPassword', 'semanticbits'  from dual WHERE not exists (select * from configuration where prop = 'outgoingMailPassword')")
    	}	
	}

	void down(){

	}
}