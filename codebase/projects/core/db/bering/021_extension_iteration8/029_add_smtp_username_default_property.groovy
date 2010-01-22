class AddMultisiteConfigDefaultProperty extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
		if (databaseMatches('postgres')){
			execute("INSERT INTO configuration(prop, value) SELECT 'outgoingMailUsername', ''  WHERE not exists (select * from configuration where prop = 'outgoingMailUsername')")
			execute("INSERT INTO configuration(prop, value) SELECT 'outgoingMailPassword', ''  WHERE not exists (select * from configuration where prop = 'outgoingMailPassword')")
			execute("INSERT INTO configuration(prop, value) SELECT 'outgoingMailFromAddress', ''  WHERE not exists (select * from configuration where prop = 'outgoingMailFromAddress')")
    	}
    	if (databaseMatches('oracle')){
			execute("INSERT INTO configuration(prop, value) SELECT 'outgoingMailUsername', '' from dual  WHERE not exists (select * from configuration where prop = 'outgoingMailUsername')")
			execute("INSERT INTO configuration(prop, value) SELECT 'outgoingMailPassword', ''  from dual WHERE not exists (select * from configuration where prop = 'outgoingMailPassword')")
			execute("INSERT INTO configuration(prop, value) SELECT 'outgoingMailFromAddress', ''  from dual WHERE not exists (select * from configuration where prop = 'outgoingMailFromAddress')")
    	}	
	}

	void down(){

	}
}