package com.ibbl.util;

import com.ibbl.security.service.SecurityConstants;
import org.apache.commons.validator.GenericValidator;

import java.util.List;

/**
 * @author Khomeni
 * Created on : 16-May-17
 */
public class Utility {

    public static List<String> getIncidentTypeList() {
        return Constants.INCIDENT_TYPE_LIST;
    }

    public static String getIncidentType(Integer index) {
        try {
            return Constants.INCIDENT_TYPE_LIST.get(index);
        } catch (Exception e) {
            return "";
        }
    }


    public static String getMessage(Exception e) {
        String msgKey = e.getMessage();
        if (GenericValidator.isBlankOrNull(msgKey)) {
            return e.toString();
        }
        String message = SecurityConstants.MESSAGE_MAP.get(msgKey);
        return message == null ? (e.getClass().getSimpleName() + " : " + msgKey) : message;

    }
}
