package com.ibbl.home;

import com.ibbl.security.service.SecurityConstants;
import com.ibbl.security.service.SessionUtil;
import com.ibbl.util.ActionUtil;
import com.ibbl.util.Utility;
import org.apache.commons.validator.GenericValidator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Khomeni
 * Created on : 16-May-17
 */

@Controller
@RequestMapping(value = "/home/", method = {RequestMethod.GET, RequestMethod.HEAD})
public class HomeController {


    @RequestMapping(value = "/login.ibbl", method = RequestMethod.HEAD)
    public ModelAndView login(@RequestParam(required = false) String message) {
        Map<String, Object> map = new HashMap<String, Object>(){{put("message", message);}};
        return new ModelAndView("/home/login", map);
    }

    @RequestMapping(value = "/dashboard.ibbl")
    public ModelAndView dashboard(@RequestParam(required = false) String message) {
        Map<String, Object> modelMap = ActionUtil.getModelMap("TAIMS Home");
        modelMap.put("message", GenericValidator.isBlankOrNull(message) ? "Welcome  Home !" : message);
        return new ModelAndView("home/dashboard", modelMap);

    }

    @RequestMapping(method=RequestMethod.GET, value="/logout.ibbl")
    public ModelAndView logout(){
        HttpSession userSession = SessionUtil.getSession();
        userSession.removeAttribute(SecurityConstants.SESSION_USER);
        userSession.removeAttribute(SecurityConstants.SESSION_USER_CASM_OID);
        userSession.removeAttribute(SecurityConstants.SESSION_USER_CASM_USER_ID);
        userSession.removeAttribute(SecurityConstants.SESSION_USER_GRANTED_ACTIONS);
        userSession.invalidate();
        return new ModelAndView("redirect:/");
    }
}
