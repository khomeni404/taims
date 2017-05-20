package com.ibbl.util;

import com.ibbl.security.model.LoggedUser;
import com.ibbl.security.service.SessionUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Khomeni
 * Created on 27/09/2016.
 */
public interface ActionUtil {

    static Map<String, Object> getModelMap(String pageTitle) {
        LoggedUser user = SessionUtil.getSessionUser();
        Map<String, Object> map = new HashMap<>();
        map.put("title", pageTitle == null ? "TAIMS" : pageTitle);
        map.put("username", user.getCasmUserid());
        map.put("userId", user.getId());
        map.put("userName", user.getName());
        map.put("TU", new Utility());
        map.put("DU", new DateUtil());
        map.put("NU", new NumberUtil());
        return map;
    }

    static ModelAndView redirectHome(Object controller, String message) {
        ActionResult actionResult = new ActionResult(Boolean.FALSE, message);
        return redirectHome(controller, actionResult);
    }

    static ModelAndView redirectHome(Object controller, ActionResult actionResult) {
        String requestMapping = controller.getClass().getAnnotation(RequestMapping.class).value()[0];
        Map<String, Object> map = getModelMap("TAIMS");
        map.put("success", actionResult.isSuccess());
        map.put("message", actionResult.getMsg());
        return new ModelAndView("redirect:" + requestMapping + "home.se", map);
    }

}
