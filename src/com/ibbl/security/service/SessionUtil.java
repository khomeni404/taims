package com.ibbl.security.service;

import com.ibbl.security.model.Action;
import com.ibbl.security.model.LoggedUser;
import org.apache.commons.validator.GenericValidator;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Khomeni
 * Created on : 17-May-17 at 12:08 AM
 */

public class SessionUtil {


    public static HttpSession getSession() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true);
    }

    public static LoggedUser getSessionUser() {
        Object user = getSession().getAttribute(SecurityConstants.SESSION_USER);
        if (user instanceof LoggedUser) {
            return (LoggedUser) user;
        }
        return null;
    }

    public static String getCasmUserid() {
        String userid = (String) getSession().getAttribute(SecurityConstants.SESSION_USER_CASM_USER_ID);
        if (!GenericValidator.isBlankOrNull(userid)) {
            return userid;
        }
        return null;
    }


    public static String getCasmUserOID() {
        String userOid = (String) getSession().getAttribute(SecurityConstants.SESSION_USER_CASM_OID);
        if (!GenericValidator.isBlankOrNull(userOid)) {
            return userOid;
        }
        return null;
    }


    @SuppressWarnings("unchecked")
    public static List<Action> getSessionUsersActions() {
        return  (List<Action>) getSession().getAttribute(SecurityConstants.SESSION_USER_GRANTED_ACTIONS);
    }

    @SuppressWarnings("unchecked")
    public static List<String> getSessionUsersMappings() {
        return  (List<String>) getSession().getAttribute(SecurityConstants.SESSION_USER_GRANTED_MAPPINGS);
    }


}
