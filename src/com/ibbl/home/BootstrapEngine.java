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
 * Created on : 17-May-17 at 8:24 PM
 */

@Component
public class BootstrapEngine {

    @Autowired
    private CommonService commonService;

    @PostConstruct
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    private void initPrivilege() {
        if (CollectionUtils.isEmpty(commonService.findAll(Action.class, Arrays.asList("id")))) {
            List<PrivilegeBean> ll = SecurityService.getPrivilegeList();
            for (PrivilegeBean pb : ll) {
                Action action = new Action();
                action.setPrivilegeID(pb.getPrivilegeID());
                action.setActionName(pb.getPrivilegeName());
                commonService.save(action);
            }
        }

    }
}
