package com.ibbl.security.service;


import com.ibbl.core.common.DataCarrier;
import com.ibbl.security.dao.SecurityDAO;
import com.ibbl.security.model.Action;
import com.ibbl.util.ActionResult;
import com.ibbl.util.Utility;
import com.thoughtworks.xstream.XStream;
import ibbl.security.common.bean.AuthenticationBean;
import ibbl.security.common.bean.PrivilegeBean;
import ibbl.security.common.bean.SecuritySessionBean;
import ibbl.security.common.bean.UserBean;
import ibbl.security.common.util.SecurityDataList;
import ibbl.security.secClient.bl.ISecurityClient;
import ibbl.security.secClient.bl.SecurityClientImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    @Autowired
    private SecurityDAO securityDAO;


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
                Object principal = securitySessionBean.getUserBean();
                //AuthenticationBean authenticationBean = securitySessionBean.getAuthenticationBean();
                List<PrivilegeBean> privilegeBeanList = securitySessionBean.getProfileBean().getPrivilegeBeans();

                if (principal != null) {
                    setAuthenticationToken((UserBean) principal, privilegeBeanList);
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
            Object principal = securitySessionBean.getUserBean();
            AuthenticationBean authenticationBean = securitySessionBean.getAuthenticationBean();
            List<PrivilegeBean> privilegeBeanList = securitySessionBean.getProfileBean().getPrivilegeBeans();

            if (principal != null && authenticationBean.getUserID().equals(username) && authenticationBean.getPassword().equals(password)) {
                setAuthenticationToken((UserBean) principal, privilegeBeanList);
                result.setSuccess(Boolean.TRUE);
            } else {
                result.setMsg("Login failed !"); // TODO... More cognizable message. i.e. Login failed for what ?
            }
        } else {
            result.setMsg("Can't Connect with CASM !");
        }

        return result;
    }


    @Override
    @SuppressWarnings("unchecked")
    public void setAuthenticationToken(UserBean principal, List<PrivilegeBean> privilegeBeanList) {
        if (principal != null) {
            HttpSession session = SessionUtil.getSession();

            session.setAttribute(SecurityConstants.SESSION_USER, principal);
            session.setAttribute(SecurityConstants.SESSION_USER_OID, principal.getUserOID());
            session.setAttribute(SecurityConstants.SESSION_USER_USERNAME, principal.getUserID());

            List<String> privilegeIDList = privilegeBeanList.stream().map(PrivilegeBean::getPrivilegeID).collect(Collectors.toList());

            // If need to do save logs on each action, then All Granted actions can be stored on user's HttpSession
            // List<Action> grantedActions = securityDAO.findAllActions(privilegeIDList);
            // session.setAttribute(SecurityConstants.SESSION_USER_GRANTED_ACTIONS, grantedActions);

            List<String> grantedMappings = securityDAO.findAllActionMapping(privilegeIDList);
            session.setAttribute(SecurityConstants.SESSION_USER_GRANTED_MAPPINGS, grantedMappings);
            System.out.println("User's HttpSession initialized successfully.");
        }
    }


    /**
     * If user having privilege to invoke the mapping method
     * this returns relevant Action
     * @param mapping mapping String action method request mapping.
     *                e.g. in '/ctx/hrm/saveSomething.ibbl' hence, 'saveSomething' is this
     * @return <code>Action</code>
     */
    public Action checkAuthorization(String mapping) {
        List<Action> actions = SessionUtil.getSessionUsersActions();
        if (CollectionUtils.isEmpty(actions)) {
            return null;
        } else {
            Optional<Action> matchingObjects = actions.stream().filter(p -> p.getMapping().equals(mapping)).findFirst();
            return matchingObjects.orElse(null);
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
