package com.ibbl.aop;

import com.ibbl.security.service.SecurityConstants;
import com.ibbl.security.service.SessionUtil;
import ibbl.security.common.bean.UserBean;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Khomeni
 * Created on : 18-May-17 at 12:04 AM
 */


@Aspect
public class AuthenticationAspect {
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
        if (sessionUserInstance instanceof UserBean) {
            return joinPoint.proceed();
        } else {
            return joinPoint.proceed();
//            return new ModelAndView("redirect:/");
        }
    }
}
