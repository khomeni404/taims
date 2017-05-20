package com.ibbl.incident.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibbl.security.model.LoggedUser;
import com.ibbl.util.ActionResult;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

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

    @ManyToOne(fetch = FetchType.EAGER)
    private LoggedUser initiator;

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

    // @Column(columnDefinition = "text") // TODO
    private String details;

    @Column(name = "hca")
    private Boolean hasControllingApparatus;

    @Column(name = "failure_reasons")
    // @Column(columnDefinition = "text") // TODO
    private String failureReasons;

    @Column(name = "preventative_controls")
    // @Column(columnDefinition = "text") // TODO
    private String preventativeControls;

    @Column(name = "start_from")
    private String startedFrom;

    @Column(name = "stopped_at")
    private String stoppedAt;

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

    public LoggedUser getInitiator() {
        return initiator;
    }

    public void setInitiator(LoggedUser initiator) {
        this.initiator = initiator;
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Boolean getHasControllingApparatus() {
        return hasControllingApparatus;
    }

    public void setHasControllingApparatus(Boolean hasControllingApparatus) {
        this.hasControllingApparatus = hasControllingApparatus;
    }

    public String getFailureReasons() {
        return failureReasons;
    }

    public void setFailureReasons(String failureReasons) {
        this.failureReasons = failureReasons;
    }

    public String getPreventativeControls() {
        return preventativeControls;
    }

    public void setPreventativeControls(String preventativeControls) {
        this.preventativeControls = preventativeControls;
    }

    public String getStartedFrom() {
        return startedFrom;
    }

    public void setStartedFrom(String startedFrom) {
        this.startedFrom = startedFrom;
    }

    public String getStoppedAt() {
        return stoppedAt;
    }

    public void setStoppedAt(String stoppedAt) {
        this.stoppedAt = stoppedAt;
    }

    public static ActionResult validate(HttpServletRequest request) {
        ActionResult result = new ActionResult(false);
        try {
            Map<String, String[]> map = request.getParameterMap();
            String formData = new Gson().toJson(map).replaceAll("[\\[ \\]]", "");
            Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
            Incident incident = gson.fromJson(formData, Incident.class);

            result.setSuccess(true);
            result.setDataObject(incident);
            return result;
        } catch (Exception e) {
            result.setException(e);
            return result;
        }
    }

}
