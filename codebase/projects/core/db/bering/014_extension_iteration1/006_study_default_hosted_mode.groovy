class setHostedModeFlag extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
			execute("update studies set hosted_mode = 'true' where hosted_mode is null")
	}

	void down(){
		
	}
}
