package com.ibbl.security.model;

import com.ibbl.incident.model.Incident;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Khomeni
 *         Created on : 19-May-17 at 8:56 AM
 */

@Entity
@DiscriminatorValue("Logged User")
public class LoggedUser extends User implements Serializable {

    @Column(name = "lu_casm_user_oid", unique = true)
    private String casmOid;

    @Column(name = "lu_userid", unique = true)
    private String casmUserid;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "lu_last_logged")
    private Date lastLogin;

    @Column(name = "lu_emp_id")
    private String employeeID;

    @Column(name = "lu_desig_code")
    private String currentDesignation;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "initiator")
    @Fetch(FetchMode.SELECT)
    private List<Incident> incidentList = new ArrayList<>(0);

    /*    private String fullName;
    private String employeeID;
    private String currentDesignation;
    private Date joiningDate;
    private String cellNo;
    private String phoneNo;
    private String email;
    private String asNo;
    private String placeOfPosting;*/

    public String getCasmUserid() {
        return casmUserid;
    }

    public void setCasmUserid(String casmUserid) {
        this.casmUserid = casmUserid;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getCasmOid() {
        return casmOid;
    }

    public void setCasmOid(String casmOid) {
        this.casmOid = casmOid;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getCurrentDesignation() {
        return currentDesignation;
    }

    public void setCurrentDesignation(String currentDesignation) {
        this.currentDesignation = currentDesignation;
    }

    public List<Incident> getIncidentList() {
        return incidentList;
    }

    public void setIncidentList(List<Incident> incidentList) {
        this.incidentList = incidentList;
    }
}
