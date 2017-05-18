package com.ibbl.incident.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Khomeni
 *         Created on : 16-May-17
 */
@Entity
@Table(name = "TAIMS_INCIDENT")
public class Incident implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "ref_No")
    private Integer refNo;

    private Integer type;

    @Column(name = "loss_amt")
    private Double lossAmt;

    @Temporal(TemporalType.DATE)
    @Column(name = "inc_date")
    private Date incidentDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "inc_time")
    private Date incidentTime;

    @Temporal(TemporalType.DATE)
    @Column(name = "rpt_date")
    private Date reportingDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRefNo() {
        return refNo;
    }

    public void setRefNo(Integer refNo) {
        this.refNo = refNo;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getLossAmt() {
        return lossAmt;
    }

    public void setLossAmt(Double lossAmt) {
        this.lossAmt = lossAmt;
    }

    public Date getIncidentDate() {
        return incidentDate;
    }

    public void setIncidentDate(Date incidentDate) {
        this.incidentDate = incidentDate;
    }

    public Date getReportingDate() {
        return reportingDate;
    }

    public void setReportingDate(Date reportingDate) {
        this.reportingDate = reportingDate;
    }

    public Date getIncidentTime() {
        return incidentTime;
    }

    public void setIncidentTime(Date incidentTime) {
        this.incidentTime = incidentTime;
    }
}
