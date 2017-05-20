package com.ibbl.security.dao;

import com.ibbl.security.model.Action;
import com.ibbl.security.model.LoggedUser;

import java.util.List;

/**
 * @author Khomeni
 * Created on : 17-May-17 at 9:08 PM
 */


public interface SecurityDAO {
    List<Action> findAllActions(List<String> privilegeIDList);

    List<String> findAllActionMapping(List<String> privilegeIDList);

    LoggedUser getLoggedUser(String userOID, String userID);
}
