class AddRtrdIndicatorToUnqConst extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	execute("ALTER TABLE epochs drop CONSTRAINT UK_STU_VER_EPH");
    	execute("ALTER TABLE epochs ADD CONSTRAINT UK_STU_VER_EPH UNIQUE(name,stu_version_id,retired_indicator)");
    	
    	execute("ALTER TABLE arms drop CONSTRAINT uk_eph_arm");
    	execute("ALTER TABLE arms ADD CONSTRAINT uk_eph_arm UNIQUE(name,eph_id,retired_indicator)");
    	
	}

	void down() {
		execute("ALTER TABLE epochs drop CONSTRAINT UK_STU_VER_EPH");
    	execute("ALTER TABLE epochs ADD CONSTRAINT UK_STU_VER_EPH UNIQUE(name,stu_version_id)");
    	
    	execute("ALTER TABLE arms drop CONSTRAINT uk_eph_arm");
    	execute("ALTER TABLE arms ADD CONSTRAINT uk_eph_arm UNIQUE(name,eph_id)");
    	
    }
}
