package com.ibbl.security.service;

import com.ibbl.security.model.Action;
import ibbl.security.common.bean.UserBean;
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

    public static UserBean getSessionUser() {
        Object user = getSession().getAttribute(SecurityConstants.SESSION_USER);
        if (user instanceof UserBean) {
            return (UserBean) user;
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
