class setHostedModeFlag extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
				execute("update studies set hosted_mode = '1' where hosted_mode is null")
       	}

	void down(){
		
	}
}
