class AddMultisiteConfigDefaultProperty extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
			insert('configuration', [ prop:"psc_window_name", value:"_psc" ], primaryKey: false)
			insert('configuration', [ prop:"caaers_window_name", value:"_caaers" ], primaryKey: false)
			insert('configuration', [ prop:"c3d_window_name", value:"_c3d" ], primaryKey: false)
	}

	void down(){

	}
}