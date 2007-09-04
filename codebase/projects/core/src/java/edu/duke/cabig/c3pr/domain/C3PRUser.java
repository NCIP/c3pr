package edu.duke.cabig.c3pr.domain;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

/**
 * C3PRUser is someone who has the ability to
 * login into the c3pr system
 *
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Aug 29, 2007
 * Time: 5:05:24 PM
 * To change this template use File | Settings | File Templates.
 */
@MappedSuperclass
public abstract class C3PRUser extends Person{

    private String loginId;
    protected List<C3PRUserGroupType> groups = new ArrayList<C3PRUserGroupType>();

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }


    public void addGroup(C3PRUserGroupType group){
        groups.add(group);
    }
    
    @Transient
    public List<C3PRUserGroupType> getGroups() {
        return groups;
    }

    public void setGroups(List<C3PRUserGroupType> groups) {
        this.groups = groups;
    }
}
