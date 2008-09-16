class SetOrganizationsRetiredIndicator extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
			
	   		execute("update organizations set retired_indicator='false'");
	   		execute("update organizations set name='Cobb Hospital and Medical Center' where name =' Cobb Hospital and Medical Center'");
	   		execute("update organizations set name='Vanderbilt-Ingram Cancer Center Cool Springs' where name =' Vanderbilt-Ingram Cancer Center Cool Springs'");
	}

	void down(){

	}
}