package com.ibbl.common.service;

import com.ibbl.incident.service.IncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Khomeni
 * Created on : 17-May-17 at 12:54 AM
 */

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ServiceFactory {

    @Autowired
    public CommonService commonService;

    @Autowired
    public IncidentService incidentService;
}
