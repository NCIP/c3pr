package edu.duke.cabig.c3pr.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * @author Priyatam
 */
 
 @Entity
 @Table (name = "healthcare_sites")
 @SequenceGenerator(name="id-generator",sequenceName="healthcare_sites_id_seq")
 /*@GenericGenerator(name="id-generator", strategy = "native",
     parameters = {
         @Parameter(name="sequence", value="healthcare_sites_id_seq")
     }
 )*/
public class HealthcareSite extends Organization implements Comparable<HealthcareSite>, Serializable{
		 
	private String nciInstituteCode;
	private List<ParticipantIdentifier> participantIdentifiers; 

	public HealthcareSite() {}
	
	public HealthcareSite(boolean initialise) {
    	super(true);
    }
	
	public String getNciInstituteCode() {
		return nciInstituteCode;
	}

	public void setNciInstituteCode(String nciInstituteCode) {
		this.nciInstituteCode = nciInstituteCode;
	}
		
    @OneToMany (mappedBy="healthcareSite", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    public List<ParticipantIdentifier> getParticipantIdentifiers() {
		return participantIdentifiers;
	}

	public void setParticipantIdentifiers(
			List<ParticipantIdentifier> participantIdentifiers) {
		this.participantIdentifiers = participantIdentifiers;
	}  	 	
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = super.hashCode();
		result = PRIME * result + ((nciInstituteCode == null) ? 0 : nciInstituteCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final HealthcareSite other = (HealthcareSite) obj;
		if (nciInstituteCode == null) {
			if (other.nciInstituteCode != null)
				return false;
		} else if (!nciInstituteCode.equals(other.nciInstituteCode))
			return false;
		return true;
	}

	public int compareTo(HealthcareSite o) {
		// TODO Auto-generated method stub
		return 0;
	}


}
