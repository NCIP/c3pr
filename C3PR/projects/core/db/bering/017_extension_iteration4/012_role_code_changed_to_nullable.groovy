class RoleCodeChangedToNullable extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
			setNullable('study_personnel', 'role_code', true)	
	}

	void down(){
	        setNullable('study_personnel', 'role_code', false)
	}
}
