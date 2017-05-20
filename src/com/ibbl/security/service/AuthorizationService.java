package com.ibbl.security.service;

import com.ibbl.security.model.Action;
import com.ibbl.security.model.User;
import com.ibbl.util.ActionResult;
import ibbl.security.common.bean.AuthenticationBean;
import ibbl.security.common.bean.PrivilegeBean;

import java.util.List;

/**
 * Copyright &copy; 2002-2003 Islami Bank Bangladesh Limited
 * <p>
 * Original author: Khomeni
 * <br/> Date: 18/05/2017
 * </p>
 */
public interface AuthorizationService {

    ActionResult casmAuthenticate(AuthenticationBean authBean);

    ActionResult staticAuthenticate(String username, String password);

    void setAuthenticationToken(User principal);

    void setAuthorizationToken(List<PrivilegeBean> privilegeBeanList);

    Action checkAuthorizationUsingAction(String mapping);

    boolean checkAuthorizationUsingMapping(String mapping);

}
