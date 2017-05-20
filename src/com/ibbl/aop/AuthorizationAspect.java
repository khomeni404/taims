package com.ibbl.aop;

import com.ibbl.security.service.AuthAuthTokenService;
import com.ibbl.security.service.SecurityConstants;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Khomeni
 *         Created on : 16-May-17
 */

@Aspect
public class AuthorizationAspect {


    @Autowired
    private AuthAuthTokenService authService;

    String operationName = null;
    String requestMapping = null;

    @Pointcut("within(@org.springframework.stereotype.Controller *) " +
            "&& !within(@org.springframework.stereotype.Controller com.ibbl.security.AuthenticationController)" +
            "&& !within(@org.springframework.stereotype.Controller com.ibbl..home.HomeController)")
    public void allControllers() {
    }

    @Pointcut("execution(* *(..))")
    public void allOperations() {
    }


    @Around("allControllers() && allOperations()")
    public Object proceedToAction(ProceedingJoinPoint proceedJoinPoint) throws Throwable {

        int annotationIndex = 0;
        Annotation[] annotations = proceedJoinPoint.getTarget().getClass().getAnnotations();
        int annotationLength = annotations.length;
        for (int i = 0; i < annotationLength; i++) {
            if (annotations[i].toString().contains(SecurityConstants.REQUEST_MAPPING)) {
                annotationIndex = i;
                break;
            }
        }

        requestMapping = annotations[annotationIndex].toString();
        operationName = proceedJoinPoint.getSignature().getName();
        //boolean isAllowed = authorizationService.checkAuthorizationUsingAction(operationName);
        boolean isAllowed = authService.checkAuthorizationUsingMapping(operationName);


        // LoggedUser sessionUser = SessionUtil.getSessionUser();
        if (isAllowed) {
            return proceedJoinPoint.proceed();
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("PageTitle", "Sorry");
            map.put("message", "You have no permission to execute this operation.");
            return new ModelAndView("/home/no_privilege", map);
//            return proceedJoinPoint.proceed();
        }


    }

}
