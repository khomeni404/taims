package com.ibbl.incident;

import com.ibbl.common.service.ServiceFactory;
import com.ibbl.incident.model.Incident;
import com.ibbl.util.Utility;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("PageTitle", "Incident Create");
        modelMap.put("TU", new Utility());
        modelMap.put("message", message);
        return new ModelAndView("/incident/incident_create", modelMap);
    }

    @RequestMapping(value = "/saveIncident.ibbl", method = RequestMethod.POST)
    public ModelAndView saveIncident(@RequestParam(required = false) String message,
                                     @RequestParam Integer refNo,
                                     @RequestParam Integer type,
                                     @RequestParam Double lossAmt,
                                     @RequestParam
                                     @DateTimeFormat(pattern = "dd/MM/yyyy") Date incidentDate,
                                     @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy")
                                     Date reportingDate) {
        Map<String, Object> modelMap = new HashMap<>();
        Incident incident = new Incident();
        incident.setRefNo(refNo);
        incident.setLossAmt(lossAmt);
        incident.setIncidentDate(new Date());
        incident.setReportingDate(reportingDate);
        commonService.save(incident);

        modelMap.put("PageTitle", "Incident Create");
        modelMap.put("TU", new Utility());
        modelMap.put("message", message);
        return new ModelAndView("redirect:/incident/incidentList.ibbl", modelMap);
    }

    @RequestMapping(value = "/incidentList.ibbl")
    public ModelAndView incidentList(@RequestParam(required = false) String message) {
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("PageTitle", "Incident Create");
        modelMap.put("TU", new Utility());
        modelMap.put("message", message);
        modelMap.put("list", commonService.findAll(Incident.class));
        return new ModelAndView("/incident/incident_list", modelMap);
    }
}
