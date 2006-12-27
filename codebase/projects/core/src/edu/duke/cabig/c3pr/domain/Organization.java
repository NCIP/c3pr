package edu.duke.cabig.c3pr.domain;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * @author Ram Chilukuri
 */

@MappedSuperclass
public abstract class Organization extends AbstractDomainObject {
    private String name;
    private String descriptionText;
    private Address address;
    public Organization() {
    }

    // Bean Methods
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }

    public String getDescriptionText() {
        return descriptionText;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @OneToOne(optional=false)
    @JoinColumn(name="address_id")
    public Address getAddress() {
        return address;
    }
}
