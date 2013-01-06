package edu.duke.cabig.c3pr.utils;

import java.util.ArrayList;
import java.util.List;

import edu.duke.cabig.c3pr.domain.Organization;

/**
 * Temporary domain object to hold values of static data loaded from CADsr
 * 
 * @author Priyatam
 * 
 */
// TODO Remove this class when a service for loading static data from CADsr exists
public class Lov {

    private String code;

    private String desc;

    List<Lov> data = new ArrayList();

    public Lov() {
    }

    public Lov(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public void addData(String code, String desc) {
        data.add(new Lov(code, desc));
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<Lov> getData() {
        return data;
    }

    public void setData(List<Lov> data) {
        this.data = data;
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((code == null) ? 0 : code.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final Lov other = (Lov) obj;
        if (code == null) {
            if (other.code != null) return false;
        }
        else if (!code.equals(other.code)) return false;
        return true;
    }
}