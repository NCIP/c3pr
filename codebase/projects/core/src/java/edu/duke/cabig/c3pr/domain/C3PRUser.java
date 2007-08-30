package edu.duke.cabig.c3pr.domain;

import javax.persistence.MappedSuperclass;

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


    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }
}
