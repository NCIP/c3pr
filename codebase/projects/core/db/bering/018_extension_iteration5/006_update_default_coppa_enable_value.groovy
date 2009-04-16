class UpdateDefaultCoppaEnableValue extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
			execute("update configuration set value='false' where prop='coppaEnable'");			
	}

	void down(){
	       execute("update configuration set value='true' where prop='coppaEnable'");		
	}
}
