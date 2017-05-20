package com.ibbl.security.service;


import com.ibbl.common.dao.CommonDAO;
import com.ibbl.core.common.DataCarrier;
import com.ibbl.home.BootstrapEngine;
import com.ibbl.security.dao.SecurityDAO;
import com.ibbl.security.model.Action;
import com.ibbl.security.model.LoggedUser;
import com.ibbl.security.model.User;
import com.ibbl.util.ActionResult;
import com.ibbl.util.Utility;
import com.thoughtworks.xstream.XStream;
import ibbl.security.common.bean.*;
import ibbl.security.common.util.SecurityDataList;
import ibbl.security.secClient.bl.ISecurityClient;
import ibbl.security.secClient.bl.SecurityClientImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthAuthTokenService implements AuthorizationService {

    @Autowired
    private SecurityDAO securityDAO;

    @Autowired
    private CommonDAO commonDAO;

    @Autowired
    private BootstrapEngine bootstrapEngine;


    @Override
    @SuppressWarnings("unchecked")
    public ActionResult casmAuthenticate(AuthenticationBean authBean) {
        ActionResult result = new ActionResult(Boolean.FALSE);

        SecuritySessionBean securitySessionBean = null;
        try {
            ISecurityClient securityClient = SecurityClientImpl.getInstance();
            String resXML = securityClient.signIn(authBean);
            if (!GenericValidator.isBlankOrNull(resXML)) {
                DataCarrier resCarrier = (DataCarrier) new XStream().fromXML(resXML);
                securitySessionBean = (SecuritySessionBean) resCarrier.retrieveData(SecurityDataList.DATA_SECURITY_SESSION);
            }
            if (securitySessionBean != null) {
                UserBean userBean = securitySessionBean.getUserBean();
                AuthenticationBean authenticationBean = securitySessionBean.getAuthenticationBean();
                List<PrivilegeBean> privilegeBeanList = securitySessionBean.getProfileBean().getPrivilegeBeans();

                if (userBean != null) {
                    String casmUserid = authenticationBean.getUserID();
                    String casmUserOID = authenticationBean.getUserOID();
                    UserDetailBean userDetail = userBean.getUserDetailBean();


                    /**Insert 5 Granted Privilege for test*/
                    bootstrapEngine.init5TestPrivilege(casmUserid, privilegeBeanList);


                    LoggedUser loggedUser = securityDAO.getLoggedUser(casmUserOID, casmUserid);
                    if (loggedUser == null) {
                        LoggedUser newLoggedUser = new LoggedUser();
                        newLoggedUser.setCasmOid(casmUserOID);
                        newLoggedUser.setCasmUserid(casmUserid);
                        newLoggedUser.setName(userDetail.getFullName());
                        newLoggedUser.setEmployeeID(userDetail.getEmployeeID());
                        newLoggedUser.setCurrentDesignation(userDetail.getCurrentDesignation());
                        newLoggedUser.setLastLogin(new Date());
                        commonDAO.save(newLoggedUser);
                        setAuthenticationToken(newLoggedUser);
                        setAuthorizationToken(privilegeBeanList);
                        result.setMsg("Welcome, " + newLoggedUser.getName() + " to First Login!");
                    } else {
                        Date lastLogin = loggedUser.getLastLogin();
                        result.setMsg("Welcome, " + loggedUser.getName() + ". Last Login was : " + lastLogin.toString());
                        setAuthenticationToken(loggedUser);
                        setAuthorizationToken(privilegeBeanList);

                        /**
                         * Updating info block. Here designation, EmpID, Last Login Time etc will be updated.
                         */
                        loggedUser.setLastLogin(new Date());
                        commonDAO.update(loggedUser);
                    }
                    result.setSuccess(Boolean.TRUE);
                } else {
                    result.setMsg("Login failed !"); // TODO... More cognizable message. i.e. Login failed for what ?
                }
            } else {
                result.setMsg("Can't Connect with CASM ! ");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg(Utility.getMessage(e));
        }
        return result;
    }


    @SuppressWarnings("unchecked")
    @Override
    public ActionResult staticAuthenticate(String username, String password) {
        ActionResult result = new ActionResult(Boolean.FALSE);

        SecuritySessionBean securitySessionBean = SecurityService.signIn(username, password);

        if (securitySessionBean != null) {
            UserBean userBean = securitySessionBean.getUserBean();
            AuthenticationBean authenticationBean = securitySessionBean.getAuthenticationBean();
            List<PrivilegeBean> privilegeBeanList = securitySessionBean.getProfileBean().getPrivilegeBeans();

            if (userBean != null && authenticationBean.getUserID().equals(username) && authenticationBean.getPassword().equals(password)) {
                String casmUserid = authenticationBean.getUserID();
                String casmUserOID = authenticationBean.getUserOID();
                UserDetailBean userDetail = userBean.getUserDetailBean();
                LoggedUser loggedUser = securityDAO.getLoggedUser(casmUserOID, casmUserid);


                if (loggedUser == null) {
                    LoggedUser newLoggedUser = new LoggedUser();
                    newLoggedUser.setCasmOid(casmUserOID);
                    newLoggedUser.setCasmUserid(casmUserid);
                    newLoggedUser.setName(userDetail.getFullName());
                    newLoggedUser.setEmployeeID(userDetail.getEmployeeID());
                    newLoggedUser.setCurrentDesignation(userDetail.getCurrentDesignation());
                    newLoggedUser.setLastLogin(new Date());
                    commonDAO.save(newLoggedUser);
                    setAuthenticationToken(newLoggedUser);
                    setAuthorizationToken(privilegeBeanList);
                    result.setMsg("Welcome, " + newLoggedUser.getName() + " to First Login!");
                } else {
                    Date lastLogin = loggedUser.getLastLogin();
                    result.setMsg("Welcome, " + loggedUser.getName() + ". Last Login was : " + lastLogin.toString());
                    setAuthenticationToken(loggedUser);
                    setAuthorizationToken(privilegeBeanList);

                    /**
                     * Updating info block. Here designation, EmpID, Last Login Time etc will be updated.
                     */
                    loggedUser.setLastLogin(new Date());
                    commonDAO.update(loggedUser);
                }
                result.setSuccess(Boolean.TRUE);
            } else {
                result.setMsg("Login failed !"); // TODO... More cognizable message. i.e. Login failed for what ?
            }
        } else {
            result.setMsg("Can't Connect with CASM !");
        }

        return result;
    }


    /**
     * Setting up the Session User credentials to the HttpSession
     * This Property can be get from SessionUtil Class from any layer of this application.
     *
     * @param principal The Logged In User
     * @see SessionUtil#getSessionUser(),
     * @see SessionUtil#getCasmUserid(),
     * @see SessionUtil#getSession()  etc.
     */
    @Override
    @SuppressWarnings("unchecked")
    public void setAuthenticationToken(User principal) {
        if (principal != null && principal instanceof LoggedUser) {
            LoggedUser loggedUser = (LoggedUser) principal;
            HttpSession session = SessionUtil.getSession();

            session.setAttribute(SecurityConstants.SESSION_USER, loggedUser);
            session.setAttribute(SecurityConstants.SESSION_USER_ID, loggedUser.getId());
            session.setAttribute(SecurityConstants.SESSION_USER_CASM_OID, loggedUser.getCasmOid());
            session.setAttribute(SecurityConstants.SESSION_USER_CASM_USER_ID, loggedUser.getCasmUserid());

            System.out.println("User's HttpSession initialized successfully.");
        }
    }


    /**
     * Setting up the Session User credentials to the HttpSession
     * This Property can be get from SessionUtil Class from any layer of this application.
     *
     * @param privilegeBeanList The CASM PrivilegeBean list of Logged In User
     * @see SessionUtil#getSessionUsersActions() or
     * @see SessionUtil#getSessionUsersMappings() ,
     * @see SessionUtil#getSession()  etc.
     */
    @Override
    @SuppressWarnings("unchecked")
    public void setAuthorizationToken(List<PrivilegeBean> privilegeBeanList) {
        HttpSession session = SessionUtil.getSession();
        if (CollectionUtils.isEmpty(privilegeBeanList)) {
            session.setAttribute(SecurityConstants.SESSION_USER_GRANTED_MAPPINGS, new ArrayList<>());
        } else {
            List<String> privilegeIDList = privilegeBeanList.stream().map(PrivilegeBean::getPrivilegeID).collect(Collectors.toList());

            // If need to do save logs on each action, then All Granted actions can be stored on user's HttpSession
            // List<Action> grantedActions = securityDAO.findAllActions(privilegeIDList);
            // session.setAttribute(SecurityConstants.SESSION_USER_GRANTED_ACTIONS, grantedActions);

            List<String> grantedMappings = securityDAO.findAllActionMapping(privilegeIDList);
            session.setAttribute(SecurityConstants.SESSION_USER_GRANTED_MAPPINGS, grantedMappings);

            System.out.println("User's HttpSession initialized successfully.");
        }
    }


    public boolean checkAuthentication(Object principal) {
        if (principal instanceof LoggedUser) {
            String username = SessionUtil.getCasmUserid();
            String userOID = SessionUtil.getCasmUserOID();
            return (((LoggedUser) principal).getCasmOid().equals(userOID)) && ((LoggedUser) principal).getCasmUserid().equals(username);
        } else if (principal instanceof User) {
            // TODO... check and return for User
        }
        return false;
    }


    /**
     * If user having privilege to invoke the mapping method
     * this returns relevant Action.
     *
     * @param mapping mapping String action method request mapping.
     *                e.g. in '/ctx/hrm/saveSomething.ibbl' hence, 'saveSomething' is this
     * @return <code>Action</code>
     */
    public Action checkAuthorizationUsingAction(String mapping) {
        List<Action> actions = SessionUtil.getSessionUsersActions();
        if (CollectionUtils.isEmpty(actions)) {
            return null;
        } else {
            Optional<Action> matchingAction = actions.stream().filter(action -> action.getMapping().equals(mapping)).findFirst();
            return matchingAction.orElse(null);
        }
    }


    /**
     * This is light weighted resource checking <code>List<String></code>
     *
     * @param mapping String action method request mapping.
     *                e.g. in '/ctx/hrm/saveSomething.ibbl' hence, 'saveSomething' is this
     * @return Is the session user authorized or having privilege the action.
     */
    public boolean checkAuthorizationUsingMapping(String mapping) {
        List<String> mappings = SessionUtil.getSessionUsersMappings();
        return CollectionUtils.isEmpty(mappings) || mappings.contains(mapping);
    }


}
