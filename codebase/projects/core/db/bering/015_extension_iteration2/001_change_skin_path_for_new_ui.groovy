class setNewSkinPath extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
		execute('update configuration set value="mocha" where prop="skinPath")
	}

	void down(){
		execute('update configuration set value="blue" where prop="skinPath")
	}
}
