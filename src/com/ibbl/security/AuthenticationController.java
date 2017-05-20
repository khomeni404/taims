package com.ibbl.security;


import com.ibbl.common.DateUtil;
import com.ibbl.security.service.AuthAuthTokenService;
import com.ibbl.util.ActionResult;
import ibbl.security.common.bean.AuthenticationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Khomeni
 *         Created on : 17-May-17 at 12:05 AM
 */

@Controller
@RequestMapping("/auth/")
public class AuthenticationController {
    @Autowired
    private AuthAuthTokenService authService;

    @RequestMapping(method = RequestMethod.POST, value = "/authenticateUser.ibbl")
    public ModelAndView authenticateUser(@RequestParam("username") String username,
                                         @RequestParam("password") String password,
                                         @RequestParam String brCode,
                                         HttpServletRequest httpServletRequest) throws Exception {
        AuthenticationBean authBean = new AuthenticationBean();
        authBean.setUserID(username);
        authBean.setPassword(password);
        authBean.setBranchCode(brCode);
        authBean.setWorkStationIPAddress(httpServletRequest.getRemoteAddr());
        authBean.setSignInTime(DateUtil.getSystemDate());

//        ActionResult result = authService.casmAuthenticate(authBean);
        ActionResult result = authService.staticAuthenticate(username, password);
        if (result.isSuccess()) {
            return new ModelAndView("redirect:/home/dashboard.ibbl", result.getMap());
        } else {
            return new ModelAndView("redirect:/home/dashboard.ibbl");
//            Map<String, Object> map = result.getMap();
//            return new ModelAndView("redirect:/", map);
        }
    }
}
