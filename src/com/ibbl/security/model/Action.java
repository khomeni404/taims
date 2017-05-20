package com.ibbl.security.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Khomeni
 * Created on : 17-May-17 at 8:22 PM
 */

@Entity
@Table(name = "TAIMS_SEC_ACTION")
public class Action implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "action_name")
    private String actionName;

    @Column(name = "action_mapping")
    private String mapping;

    @Column(name = "privilege_id")
    private String privilegeID;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMapping() {
        return mapping;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }

    public String getPrivilegeID() {
        return privilegeID;
    }

    public void setPrivilegeID(String privilegeID) {
        this.privilegeID = privilegeID;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
}
