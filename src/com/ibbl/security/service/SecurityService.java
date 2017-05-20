package com.ibbl.security.service;

import ibbl.security.common.bean.PrivilegeBean;
import ibbl.security.common.bean.SecuritySessionBean;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.List;

/**
 * @author Khomeni
 * Created on : 17-May-17
 */

@Service
public class SecurityService {

    public static List<PrivilegeBean> getPrivilegeList() {
        SecuritySessionBean securitySessionBean =  signIn("", "");
        List<PrivilegeBean> privilegeBeanList = securitySessionBean.getProfileBean().getPrivilegeBeans();
        System.out.println(securitySessionBean.getAuthenticationBean().getPassword());
        return privilegeBeanList;
    }


    @SuppressWarnings("unchecked")
    public static SecuritySessionBean signIn(String username, String password) {

        try {
            FileInputStream fin = new FileInputStream(SecurityConstants.SecuritySessionBean_ser_path);
//            FileInputStream fin = new FileInputStream();
            ObjectInputStream oisBC = new ObjectInputStream(fin);
            SecuritySessionBean securitySessionBean = (SecuritySessionBean) oisBC.readObject();
            oisBC.close();

            return securitySessionBean;

        } catch (Exception e) {

        }

        return null;
    }



}