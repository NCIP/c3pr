class AddMultisiteConfigDefaultProperty extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
		insert('configuration', [ key:"psc_window_name", value:"_psc" ], primaryKey: false)
		insert('configuration', [ key:"caaers_window_name", value:"_caaers" ], primaryKey: false)
		insert('configuration', [ key:"c3d_window_name", value:"_c3d" ], primaryKey: false)
	}

	void down(){

	}
}