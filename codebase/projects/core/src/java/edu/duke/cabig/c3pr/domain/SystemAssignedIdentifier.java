/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("SAI")
public class SystemAssignedIdentifier extends Identifier implements
                Comparable<SystemAssignedIdentifier> {

    private String systemName;

    public String getSystemName() {
        return systemName;
    }
    
    public void setType(String type){
    	setTypeInternal(type);
    }
    
    @Transient
    public String getType(){
    	return getTypeInternal();
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public int compareTo(SystemAssignedIdentifier o) {
        if (this.equals(o)) return 0;
        else return 1;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = PRIME * result + ((systemName == null) ? 0 : systemName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        final SystemAssignedIdentifier other = (SystemAssignedIdentifier) obj;
        if (systemName == null) {
            if (other.systemName != null) return false;
        }
        else if (!systemName.equalsIgnoreCase(other.systemName)) return false;
        if (!this.getValue().equals(other.getValue())) return false;
        return true;
    }
    
    @Override
    public String toString() {
    	 return " Assigning System: " + getSystemName() + " Identifier Type: " + getType() + " Identifier Value: " + getValue() + " Is Primary: " + isPrimary();
    }

}
