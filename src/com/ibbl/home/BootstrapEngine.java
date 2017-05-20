package com.ibbl.home;

import com.ibbl.common.service.CommonService;
import com.ibbl.security.model.Action;
import com.ibbl.security.service.SecurityService;
import ibbl.security.common.bean.PrivilegeBean;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

/**
 * @author Khomeni
 *         Created on : 17-May-17 at 8:24 PM
 */

@Component
public class BootstrapEngine {

    @Autowired
    private CommonService commonService;

    @PostConstruct
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    private void initPrivilege() {
        int counter = 0;
        if (CollectionUtils.isEmpty(commonService.findAll(Action.class, Arrays.asList("id")))) {
            List<PrivilegeBean> ll = SecurityService.getPrivilegeList();
            for (PrivilegeBean pb : ll) {
                Action action = new Action();
                action.setPrivilegeID(pb.getPrivilegeID());
                action.setActionName(pb.getPrivilegeName());
                commonService.save(action);
                if (counter++ > 5) break;
            }
        }

    }


    /**
     * This is to record 5 Action with Granted privilege for test purpose
     * @param userid
     * @param ll
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void init5TestPrivilege(String userid, List<PrivilegeBean> ll) {
        int counter = 0;
        if (CollectionUtils.isEmpty(commonService.findAll(Action.class, "mapping", userid))) {
            for (PrivilegeBean pb : ll) {
                Action action = new Action();
                action.setPrivilegeID(pb.getPrivilegeID());
                action.setActionName(pb.getPrivilegeName());
                action.setMapping(userid);
                commonService.save(action);
                if (counter++ > 5) break;
            }
        }

    }
}
