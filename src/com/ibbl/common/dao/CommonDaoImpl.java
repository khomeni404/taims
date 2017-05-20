package com.ibbl.common.dao;

import com.google.gson.internal.Primitives;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Khomeni
 *         Created on : 16-May-17
 */


@Repository
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class CommonDaoImpl implements CommonDAO {
    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean save(Object model) {
        hibernateTemplate.persist(model);
        return true;
    }

    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean saveAll(List modelList) {
        modelList.stream().forEach(this::save);
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean update(Object model) {
        hibernateTemplate.merge(model);
        return true;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @SuppressWarnings("unchecked")
    public boolean updateAll(List<?> modelList) {
        modelList.stream().forEach(this::update);
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean delete(Object model) {
        hibernateTemplate.delete(model);
        return true;
    }

    @Override
    public <MODEL> MODEL get(Class<MODEL> clazz, Long id) {
        Object model = hibernateTemplate.get(clazz, id);
        return Primitives.wrap(clazz).cast(model);
    }

    @Override
    public <MODEL> MODEL get(Class<MODEL> clazz, Long id, List<String> projections) {
        try {
            DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
            criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            Criteria executableCriteria = criteria.getExecutableCriteria(hibernateTemplate.getSessionFactory().openSession());

            if (!CollectionUtils.isEmpty(projections)) {
                ProjectionList projectionList = Projections.projectionList();
                for (String property : projections) {
                    projectionList.add(Projections.property(property), property);
                }
                executableCriteria.setProjection(projectionList);
            }
            executableCriteria.add(Restrictions.eq("id", id));
            executableCriteria.setResultTransformer(Transformers.aliasToBean(clazz));
            Object model = executableCriteria.uniqueResult();
            return Primitives.wrap(clazz).cast(model);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }


    @Override
    public <MODEL> MODEL get(Class<MODEL> clazz, String propertyName, Object propertyValue) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz)
                .add(Restrictions.eq(propertyName, propertyValue));
        try {
            Object model = hibernateTemplate.findByCriteria(criteria).get(0);
            return Primitives.wrap(clazz).cast(model);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    @Override
    public <MODEL> MODEL get(Class<MODEL> clazz, String propertyName1, Object propertyValue1, String propertyName2, Object propertyValue2) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz)
                .add(Restrictions.eq(propertyName1, propertyValue1))
                .add(Restrictions.eq(propertyName2, propertyValue2));
        Object model = hibernateTemplate.findByCriteria(criteria).get(0);
        return model == null ? null : Primitives.wrap(clazz).cast(model);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <MODEL> MODEL get(Class<MODEL> clazz, String aliasModel, String propertyName, Object propertyValue) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz)
                .createAlias(aliasModel, "model2")
                .add(Restrictions.eq("model2." + propertyName, propertyValue));
        try {
            Object model = hibernateTemplate.findByCriteria(criteria).get(0);
            return Primitives.wrap(clazz).cast(model);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <MODEL> MODEL get(Class<MODEL> clazz, String aliasModel, String aliasPropertyName, Object aliasPropertyValue, String propertyName, Object propertyValue) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz)
                .createAlias(aliasModel, "model2")
                .add(Restrictions.eq("model2." + aliasPropertyName, aliasPropertyValue))
                .add(Restrictions.eq(propertyName, propertyValue));
        try {
            Object model = hibernateTemplate.findByCriteria(criteria).get(0);
            return Primitives.wrap(clazz).cast(model);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public <MODEL> MODEL get(Class<MODEL> clazz, Long id, Boolean active) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz)
                .add(Restrictions.eq("id", id))
                .add(Restrictions.eq("active", active));
        try {
            Object model = hibernateTemplate.findByCriteria(criteria).get(0);
            return Primitives.wrap(clazz).cast(model);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }


    @Override
    @SuppressWarnings("unchecked")
    public <MODEL> List<MODEL> findAll(Class<MODEL> clazz) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
        return (List<MODEL>) hibernateTemplate.findByCriteria(criteria);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <MODEL> List<MODEL> findAll(Class<MODEL> clazz, Order order) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz)
                .addOrder(order);
        return (List<MODEL>) hibernateTemplate.findByCriteria(criteria);
    }


    @Override
    @SuppressWarnings("unchecked")
    public <MODEL> List<MODEL> findAll(Class<MODEL> clazz, String[] idArray) {
        List<String> integerList = Arrays.asList(idArray);
        List<Long> longList = integerList.stream().map(NumberUtils::toLong).collect(Collectors.toList());
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz)
                .add(Restrictions.in("id", longList));
        return (List<MODEL>) hibernateTemplate.findByCriteria(criteria);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <MODEL> List<MODEL> findAll(Class<MODEL> clazz, int start, int limit) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        Criteria executableCriteria = criteria.getExecutableCriteria(hibernateTemplate.getSessionFactory().openSession());

        executableCriteria.setFirstResult(start);
        executableCriteria.setMaxResults(limit);
        return (List<MODEL>) executableCriteria.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <MODEL> List<MODEL> findAll(Class<MODEL> clazz, int start, int limit, List<String> projections) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        Criteria executableCriteria = criteria.getExecutableCriteria(hibernateTemplate.getSessionFactory().openSession());

        if (!CollectionUtils.isEmpty(projections)) {
            ProjectionList projectionList = Projections.projectionList();
            for (String property : projections) {
                projectionList.add(Projections.property(property), property);
            }
            executableCriteria.setProjection(projectionList);
        }

        executableCriteria.setResultTransformer(Transformers.aliasToBean(clazz));
        executableCriteria.setFirstResult(start);
        executableCriteria.setMaxResults(limit);
        return (List<MODEL>) executableCriteria.list();

    }

    @Override
    @SuppressWarnings("unchecked")
    public <MODEL> List<MODEL> findAll(Class<MODEL> clazz, List<String> projections) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        Criteria executableCriteria = criteria.getExecutableCriteria(hibernateTemplate.getSessionFactory().openSession());

        if (!CollectionUtils.isEmpty(projections)) {
            ProjectionList projectionList = Projections.projectionList();
            for (String property : projections) {
                projectionList.add(Projections.property(property), property);
            }
            executableCriteria.setProjection(projectionList);
        }
        executableCriteria.setResultTransformer(Transformers.aliasToBean(clazz));
        return (List<MODEL>) executableCriteria.list();

    }


    @Override
    @SuppressWarnings("unchecked")
    public <MODEL> List<MODEL> findAll(Class<MODEL> clazz, Boolean active) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz)
                .add(Restrictions.eq("active", active));
        return (List<MODEL>) hibernateTemplate.findByCriteria(criteria);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <MODEL> List<MODEL> findAll(Class<MODEL> clazz, String propertyName, Object propertyValue) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz)
                .add(Restrictions.eq(propertyName, propertyValue));
        return (List<MODEL>) hibernateTemplate.findByCriteria(criteria);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <MODEL> List<MODEL> findAll(Class<MODEL> clazz, String propertyName1, Object propertyValue1, String propertyName2, Object propertyValue2) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz)
                .add(Restrictions.eq(propertyName1, propertyValue1))
                .add(Restrictions.eq(propertyName2, propertyValue2));
        return (List<MODEL>) hibernateTemplate.findByCriteria(criteria);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <MODEL> List<MODEL> findAll(Class<MODEL> clazz, String aliasModel, String propertyName, Object propertyValue) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz)
                .createAlias(aliasModel, "model2")
                .add(Restrictions.eq("model2." + propertyName, propertyValue));
        return (List<MODEL>) hibernateTemplate.findByCriteria(criteria);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <MODEL> List<MODEL> findAll(Class<MODEL> clazz, Map<String, Object> propertyValueMap) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
        for (String property : propertyValueMap.keySet()) {
            criteria.add(Restrictions.eq(property, propertyValueMap.get(property)));
        }
        return (List<MODEL>) hibernateTemplate.findByCriteria(criteria);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <MODEL> List<MODEL> searchBy_name(Class<MODEL> clazz, String text) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz)
                .add(Restrictions.like("name", text, MatchMode.ANYWHERE));
        return (List<MODEL>) hibernateTemplate.findByCriteria(criteria);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <MODEL> List<MODEL> searchBy_name_nameBN(Class<MODEL> clazz, String text) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz)
                .add(Restrictions.disjunction()
                                .add(
                                        Restrictions.or(
                                                Restrictions.like("name", text, MatchMode.ANYWHERE),
                                                Restrictions.like("nameBN", text, MatchMode.ANYWHERE)
                                        )
                                )
                );
        return (List<MODEL>) hibernateTemplate.findByCriteria(criteria);
    }


    @Override
    public Integer count(Class clazz) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz)
                .setProjection(Projections.rowCount());
        Object o = hibernateTemplate.findByCriteria(criteria).get(0);
        if (o == null) {
            return 0;
        } else {
            return Integer.valueOf(o.toString());
        }
    }

    @Override
    public Integer count(Class clazz, String propName, Object propVal) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz)
                .add(Restrictions.eq(propName, propVal))
                .setProjection(Projections.rowCount());
        Object o = hibernateTemplate.findByCriteria(criteria).get(0);
        if (o == null) {
            return 0;
        } else {
            return Integer.valueOf(o.toString());
        }
    }

    @Override
    public Integer count(Class clazz, Boolean active) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz)
                .add(Restrictions.eq("active", active))
                .setProjection(Projections.rowCount());
        Object o = hibernateTemplate.findByCriteria(criteria).get(0);
        if (o == null) {
            return 0;
        } else {
            return Integer.valueOf(o.toString());
        }
    }
}
