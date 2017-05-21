package com.ibbl.incident.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibbl.security.model.User;
import com.ibbl.util.ActionResult;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Khomeni
 *         Created on : 16-May-17
 */
@Entity
@Table(name = "TAIMS_INC_INCIDENT_REVIEW")
public class IncidentReview implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Incident incident;

    @ManyToOne
    @JoinColumn(name = "reviewer")
    private User reviewBy;

    private Integer type;

    @Column(name = "review_note")
    // @Column(columnDefinition = "text") // TODO
    private String reviewNote;


    @Temporal(TemporalType.DATE)
    @Column(name = "review_date")
    private Date reviewDate;

    @Column(name = "signed_as")
    private String signedAs;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getReviewBy() {
        return reviewBy;
    }

    public void setReviewBy(User reviewBy) {
        this.reviewBy = reviewBy;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSignedAs() {
        return signedAs;
    }

    public void setSignedAs(String signedAs) {
        this.signedAs = signedAs;
    }

    public Incident getIncident() {
        return incident;
    }

    public void setIncident(Incident incident) {
        this.incident = incident;
    }

    public String getReviewNote() {
        return reviewNote;
    }

    public void setReviewNote(String reviewNote) {
        this.reviewNote = reviewNote;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public static IncidentReview validate(HttpServletRequest request) throws Exception {
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> parameterMap = request.getParameterMap();
        parameterMap.forEach((key, value) -> params.put(key, value[0]));
        String formData = new Gson().toJson(parameterMap);
        Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
        return gson.fromJson(formData, IncidentReview.class);
    }

}
