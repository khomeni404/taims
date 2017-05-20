package com.ibbl.security.dao;


import com.google.gson.internal.Primitives;
import com.ibbl.security.model.Action;
import com.ibbl.security.model.LoggedUser;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Khomeni
 *         Created on : 17-May-17 at 9:09 PM
 */

@Repository
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class SecurityDaoImpl implements SecurityDAO {

    @Autowired
    HibernateTemplate hibernateTemplate;

    @Override
    @SuppressWarnings("unchecked")
    public List<Action> findAllActions(List<String> privilegeIDList) {
        DetachedCriteria dc = DetachedCriteria.forClass(Action.class).add(Restrictions.isNotNull("name"));
        int totalID = privilegeIDList.size();
        int limit = 500;
        List<Action> result = new ArrayList<>();
        for (int i = 0; i < totalID; i += limit) {
            List<String> subList = privilegeIDList.subList(i, i + limit > totalID ? totalID : i + limit);
            dc.add(Restrictions.in("privilegeID", subList));
            result.addAll((List<Action>) hibernateTemplate.findByCriteria(dc));
        }
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> findAllActionMapping(List<String> privilegeIDList) {
        DetachedCriteria dc = DetachedCriteria.forClass(Action.class)
                .setProjection(Projections.property("mapping"));
        int totalID = privilegeIDList.size();
        int limit = 500;
        List<String> result = new ArrayList<>();
        for (int i = 0; i < totalID; i += limit) {
            List<String> subList = privilegeIDList.subList(i, i + limit > totalID ? totalID : i + limit);
            dc.add(Restrictions.in("privilegeID", subList));
            dc.add(Restrictions.isNotNull("actionName"));
            result.addAll((List<String>) hibernateTemplate.findByCriteria(dc));
        }
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public LoggedUser getLoggedUser(String userOID, String userID) {
        DetachedCriteria criteria = DetachedCriteria.forClass(LoggedUser.class)
                .add(Restrictions.eq("casmOid", userOID))
                .add(Restrictions.eq("casmUserid", userID));
        List modelList = hibernateTemplate.findByCriteria(criteria);
        return CollectionUtils.isEmpty(modelList) ? null : (LoggedUser) modelList.get(0);
    }

}
