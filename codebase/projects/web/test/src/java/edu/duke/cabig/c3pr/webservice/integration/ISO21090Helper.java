/**
 * 
 */
package edu.duke.cabig.c3pr.webservice.integration;

import java.util.Arrays;
import java.util.List;

import edu.duke.cabig.c3pr.webservice.iso21090.AD;
import edu.duke.cabig.c3pr.webservice.iso21090.ADXP;
import edu.duke.cabig.c3pr.webservice.iso21090.AddressPartType;
import edu.duke.cabig.c3pr.webservice.iso21090.BAGTEL;
import edu.duke.cabig.c3pr.webservice.iso21090.BL;
import edu.duke.cabig.c3pr.webservice.iso21090.CD;
import edu.duke.cabig.c3pr.webservice.iso21090.DSETAD;
import edu.duke.cabig.c3pr.webservice.iso21090.DSETCD;
import edu.duke.cabig.c3pr.webservice.iso21090.DSETENPN;
import edu.duke.cabig.c3pr.webservice.iso21090.DSETST;
import edu.duke.cabig.c3pr.webservice.iso21090.ED;
import edu.duke.cabig.c3pr.webservice.iso21090.ENPN;
import edu.duke.cabig.c3pr.webservice.iso21090.ENXP;
import edu.duke.cabig.c3pr.webservice.iso21090.EntityNamePartType;
import edu.duke.cabig.c3pr.webservice.iso21090.II;
import edu.duke.cabig.c3pr.webservice.iso21090.INTPositive;
import edu.duke.cabig.c3pr.webservice.iso21090.IVLTSDateTime;
import edu.duke.cabig.c3pr.webservice.iso21090.NullFlavor;
import edu.duke.cabig.c3pr.webservice.iso21090.ST;
import edu.duke.cabig.c3pr.webservice.iso21090.TEL;
import edu.duke.cabig.c3pr.webservice.iso21090.TSDateTime;

/**
 * Helper methods to conveniently create instances of ISO 21090 JAXB classes.
 * Method names do not follow Sun's Java coding conventions <b>intentionally</b>
 * to keep names short and simple.
 * 
 * @author dkrylov
 * 
 */
public final class ISO21090Helper {

	/**
	 * We allow creation of instances of this class so that other classes could
	 * use very short form -- a variable with a short name -- of referring to
	 * this class' methods to improve readability. See usage examples for
	 * details.
	 */
	public ISO21090Helper() {
	}

	public static final II II(String ext) {
		II ii = new II();
		ii.setExtension(ext);
		return ii;
	}

	public static final CD CD(String code) {
		CD cd = new CD();
		cd.setCode(code);
		return cd;
	}

	public static final IVLTSDateTime IVLTSDateTime(NullFlavor nf) {
		IVLTSDateTime cd = new IVLTSDateTime();
		cd.setNullFlavor(nf);
		return cd;
	}

	public static final BL BL(Boolean b) {
		BL cd = new BL();
		cd.setValue(b);
		return cd;
	}

	public static final TSDateTime TSDateTime(String s) {
		TSDateTime dateTime = new TSDateTime();
		dateTime.setValue(s);
		return dateTime;
	}
	
	public static final TSDateTime TSDateTime(NullFlavor ni) {
		TSDateTime dateTime = new TSDateTime();
		dateTime.setNullFlavor(ni);
		return dateTime;
	}

	public static final ST ST(String s) {
		ST st = new ST();
		st.setValue(s);
		return st;
	}

	public static final DSETST DSETST(List<ST> list) {
		DSETST dsetst = new DSETST();
		dsetst.getItem().addAll(list);
		return dsetst;
	}

	public static final DSETCD DSETCD(CD... cd) {
		DSETCD d = new DSETCD();
		d.getItem().addAll(Arrays.asList(cd));
		return d;
	}

	public static final ENXP ENXP(String s, EntityNamePartType type) {
		ENXP en = new ENXP();
		en.setValue(s);
		en.setType(type);
		return en;
	}

	public static final ENPN ENPN(ENXP... part) {
		ENPN en = new ENPN();
		en.getPart().addAll(Arrays.asList(part));
		return en;
	}

	public static final DSETENPN DSETENPN(ENPN... enpn) {
		DSETENPN en = new DSETENPN();
		en.getItem().addAll(Arrays.asList(enpn));
		return en;
	}

	public static final ADXP ADXP(String s, AddressPartType typ) {
		ADXP ad = new ADXP();
		ad.setType(typ);
		ad.setValue(s);
		return ad;
	}

	public static final AD AD(ADXP... adxps) {
		AD ad = new AD();
		ad.getPart().addAll(Arrays.asList(adxps));
		return ad;
	}

	public static final DSETAD DSETAD(AD... ads) {
		DSETAD ad = new DSETAD();
		ad.getItem().addAll(Arrays.asList(ads));
		return ad;

	}

	public static final TEL TEL(String s) {
		TEL tel = new TEL();
		tel.setValue(s);
		return tel;
	}

	public static final BAGTEL BAGTEL(TEL... tel) {
		BAGTEL b = new BAGTEL();
		b.getItem().addAll(Arrays.asList(tel));
		return b;
	}

	public static final ED ED(String s) {
		ED ed = new ED();
		ed.setValue(s);
		return ed;
	}

	public static final CD CD(NullFlavor ni) {
		CD cd = new CD();
		cd.setNullFlavor(ni);
		return cd;
	}

	public static final BL BL(NullFlavor ni) {
		BL bl = new BL();
		bl.setNullFlavor(ni);
		return bl;
	}

	public static final INTPositive INTPositive(Integer i) {
		INTPositive int1 = new INTPositive();
		int1.setValue(i);
		return int1;
	}
	
	public ST ST() {
		// TODO Auto-generated method stub
		return new ST();
	}

}
