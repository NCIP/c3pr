
class CSMIncrememntGroupSeq extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
		execute("ALTER SEQUENCE CSM_GROUP_GROUP_ID_SEQ restart with 4");
	}

	void down(){

	}
}