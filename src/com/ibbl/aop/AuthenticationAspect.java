package com.ibbl.aop;

import com.ibbl.security.model.LoggedUser;
import com.ibbl.security.service.AuthAuthTokenService;
import com.ibbl.security.service.SecurityConstants;
import com.ibbl.security.service.SessionUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

/**
 * AuthenticationAspect will ensure..
 * 1. Is the session user null
 * 2. Is this the right user (Authenticate)
 * 3. Is this instanceof defined User
 *
 * @author Khomeni
 *         Created on : 18-May-17 at 12:04 AM
 */


@Aspect
public class AuthenticationAspect {


    @Autowired
    private AuthAuthTokenService authService;

    @Pointcut("within(@org.springframework.stereotype.Controller *) " +
            "&& !within(@org.springframework.stereotype.Controller com.ibbl.home.HomeController) " +
            "&& !within(@org.springframework.stereotype.Controller com.ibbl.security.AuthenticationController) ")
    public void allControllers() {
    }

    @Pointcut("execution(* *(..))")
    public void methodPointcut() {
    }

    @Around("allControllers() && methodPointcut()")
    public Object proceedToAction(ProceedingJoinPoint joinPoint) throws Throwable {
        Object sessionUserInstance = SessionUtil.getSession().getAttribute(SecurityConstants.SESSION_USER);
        if (sessionUserInstance != null) {
            boolean authenticated = authService.checkAuthentication(sessionUserInstance);
            if (authenticated) {
                return joinPoint.proceed();
            }
        }
        return new ModelAndView("redirect:/");
        // return joinPoint.proceed();


    }
}
