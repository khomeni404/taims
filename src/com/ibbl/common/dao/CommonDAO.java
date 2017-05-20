package com.ibbl.common.dao;

import org.hibernate.criterion.Order;

import java.util.List;
import java.util.Map;

/**
 * @author Khomeni
 * Created on : 16-May-17
 */
public interface CommonDAO {
    boolean save(Object model);

    boolean saveAll(List<?> modelList);

    boolean update(Object model);

    boolean updateAll(List<?> modelList);

    boolean delete(Object model);

    <MODEL> MODEL get(Class<MODEL> clazz, Long id);

    <MODEL> MODEL get(Class<MODEL> clazz, Long id, List<String> projections);

    <MODEL> MODEL get(Class<MODEL> clazz, String propertyName, Object propertyValue);

    <MODEL> MODEL get(Class<MODEL> clazz, String propertyName1, Object propertyValue1, String propertyName2, Object propertyValue2);

    <MODEL> MODEL get(Class<MODEL> clazz, String aliasModel, String propertyName, Object propertyValue);

    <MODEL> MODEL get(Class<MODEL> clazz, String aliasModel, String aliasPropertyName, Object aliasPropertyValue, String propertyName, Object propertyValue);

    <MODEL> MODEL get(Class<MODEL> clazz, Long id, Boolean active);


    <MODEL> List<MODEL> findAll(Class<MODEL> clazz);

    <MODEL> List<MODEL> findAll(Class<MODEL> clazz, Order order);

    <MODEL> List<MODEL> findAll(Class<MODEL> clazz, String[] idArray);

    <MODEL> List<MODEL> findAll(Class<MODEL> clazz, int start, int limit);

    <MODEL> List<MODEL> findAll(Class<MODEL> clazz, int start, int limit, List<String> projections);

    <MODEL> List<MODEL> findAll(Class<MODEL> clazz, List<String> projections);

    <MODEL> List<MODEL> findAll(Class<MODEL> clazz, Boolean active);

    <MODEL> List<MODEL> findAll(Class<MODEL> clazz, String propertyName, Object propertyValue);

    <MODEL> List<MODEL> findAll(Class<MODEL> clazz, String propertyName1, Object propertyValue1, String propertyName2, Object propertyValue2);

    <MODEL> List<MODEL> findAll(Class<MODEL> clazz, String aliasModel, String propertyName, Object propertyValue);

    <MODEL> List<MODEL> findAll(Class<MODEL> clazz, Map<String, Object> propertyValueMap);

    <MODEL> List<MODEL> searchBy_name(Class<MODEL> clazz, String text);

    <MODEL> List<MODEL> searchBy_name_nameBN(Class<MODEL> clazz, String text);

    Integer count(Class clazz);

    Integer count(Class clazz, String propName, Object propVal);

    Integer count(Class clazz, Boolean active);
}
