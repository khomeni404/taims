package com.ibbl.incident;

import com.ibbl.common.service.ServiceFactory;
import com.ibbl.incident.model.Incident;
import com.ibbl.incident.model.IncidentReview;
import com.ibbl.security.model.LoggedUser;
import com.ibbl.util.ActionUtil;
import com.ibbl.util.Utility;
import org.apache.commons.validator.GenericValidator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Khomeni
 *         Created on : 16-May-17
 */
@Controller
@RequestMapping("/incident/")
public class IncidentController extends ServiceFactory {

    @RequestMapping(value = "/createIncident.ibbl")
    public ModelAndView createIncident(@RequestParam(required = false) String message) {
        Map<String, Object> modelMap = ActionUtil.getModelMap("Incident Create");
        if (!GenericValidator.isBlankOrNull(message))
            modelMap.put("message", message);
            modelMap.put("reportingDate", new Date());
            modelMap.put("refNo", incidentService.maxRefNo()+1);
        return new ModelAndView("/incident/incident_create", modelMap);
    }

    @RequestMapping(value = "/saveIncident.ibbl", method = RequestMethod.POST)
    public ModelAndView saveIncident(@RequestParam(required = false) String message,
                                     @RequestParam Integer refNo,
                                     @RequestParam Integer type,
                                     @RequestParam Double lossAmt,
                                     @RequestParam String details,
                                     @RequestParam(required = false) String noControllingApparatus,
                                     @RequestParam(required = false) String failureReasons,
                                     @RequestParam(required = false) String preventativeControls,
                                     @DateTimeFormat(pattern = "dd/MM/yyyy")
                                     @RequestParam Date incidentDate,
                                     @RequestParam String startedFrom,
                                     @RequestParam String stoppedAt) throws IOException {
        Map<String, Object> modelMap = new HashMap<>();
        Incident incident = new Incident();
        incident.setType(type);
        incident.setRefNo(refNo);
        incident.setLossAmt(lossAmt);
        incident.setIncidentDate(incidentDate);
        incident.setReportingDate(new Date());
        incident.setDetails(details);
        if (noControllingApparatus != null && noControllingApparatus.equals("on")) {
            incident.setHasControllingApparatus(Boolean.FALSE);
            incident.setFailureReasons("");
        } else {
            incident.setHasControllingApparatus(Boolean.TRUE);
            incident.setFailureReasons(failureReasons);
        }
        incident.setPreventativeControls(preventativeControls);
        incident.setStartedFrom(startedFrom);
        incident.setStoppedAt(stoppedAt);
        commonService.save(incident);

        modelMap.put("PageTitle", "Incident Create");
        modelMap.put("TU", new Utility());
        modelMap.put("message", message);
        return new ModelAndView("redirect:/incident/incidentList.ibbl", modelMap);
    }

    @RequestMapping(value = "/incidentList.ibbl")
    public ModelAndView incidentList(@RequestParam(required = false) String message) {
        Map<String, Object> modelMap = ActionUtil.getModelMap("Incident List");
        modelMap.put("message", message);
        modelMap.put("list", commonService.findAll(Incident.class));
        return new ModelAndView("/incident/incident_list", modelMap);
    }


    @RequestMapping(value = "/viewIncident.ibbl")
    public ModelAndView viewIncident(@RequestParam Long id,
                                     @RequestParam(required = false) String message) {
        Map<String, Object> modelMap = ActionUtil.getModelMap("View Incident");
        if (!GenericValidator.isBlankOrNull(message))
            modelMap.put("message", message);
        Incident incident = commonService.get(Incident.class, id);
        modelMap.put("incident", incident);
        modelMap.put("reviewList", commonService.findAll(IncidentReview.class, "incident", incident)); // TODO
        return new ModelAndView("/incident/incident_view", modelMap);
    }


    @RequestMapping(value = "/createIncidentReview.ibbl")
    public ModelAndView createIncidentReview(@RequestParam Long incidentId,
                                             @RequestParam(required = false) String message) {
        Incident incident = commonService.get(Incident.class, incidentId);
        Map<String, Object> modelMap = ActionUtil.getModelMap("Incident Review");
        if (!GenericValidator.isBlankOrNull(message)) {
            modelMap.put("message", message);
        }
        modelMap.put("incident", incident);
        return new ModelAndView("/incident/incident_review_create", modelMap);
    }

    @RequestMapping(value = "/saveIncidentReview.ibbl")
    public ModelAndView saveIncidentReview(@RequestParam Long incidentId,
                                           @RequestParam String reviewNote,
                                           @RequestParam Long reviewerId,
                                           @RequestParam String signedAs,

                                             @RequestParam(required = false) String message) {
        Incident incident = commonService.get(Incident.class, incidentId);
        Map<String, Object> modelMap = ActionUtil.getModelMap("Incident Review");
        if (!GenericValidator.isBlankOrNull(message)) {
            modelMap.put("message", message);
        }
        IncidentReview review = new IncidentReview();
        review.setReviewDate(new Date());
        review.setReviewNote(reviewNote);
        review.setSignedAs(signedAs);
        review.setIncident(incident);
        LoggedUser user = commonService.get(LoggedUser.class, reviewerId);
        review.setReviewBy(user);
        modelMap.put("message", "Review Note saved Successfully");
        modelMap.put("id", incidentId);
        commonService.save(review);
        return new ModelAndView("redirect:/incident/viewIncident.ibbl", modelMap);
    }
}
