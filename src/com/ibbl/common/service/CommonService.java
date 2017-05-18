package com.ibbl.common.service;

import com.ibbl.common.model.Document;
import com.ibbl.util.ActionResult;
import org.hibernate.criterion.Order;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author Khomeni
 * Created on : 16-May-17
 */

public interface CommonService {
    boolean save(Object model);

    boolean update(Object model);

    boolean delete(Object model);

    <MODEL> List<MODEL> findAll(Class<MODEL> clazz);

    public <MODEL> List<MODEL> findAll(Class<MODEL> clazz, Order order);

    <MODEL> List<MODEL> findAll(Class<MODEL> clazz, List<String> projections);

    <MODEL> List<MODEL> findAll(Class<MODEL> clazz, int start, int limit);

    <MODEL> List<MODEL> findAll(Class<MODEL> clazz, int start, int limit, List<String> projections);

    <MODEL> List<MODEL> findAll(Class<MODEL> clazz, Boolean active);

    <MODEL> List<MODEL> findAll(Class<MODEL> clazz, String propertyName, Object propertyValue);

    <MODEL> List<MODEL> findAll(Class<MODEL> clazz, String aliasModel, String propertyName, Object propertyValue);

    <MODEL> List<MODEL> findAll(Class<MODEL> clazz, Map<String, Object> paramValueMap);

    <MODEL> List<MODEL> searchBy_name(Class<MODEL> clazz, String text);

    <MODEL> List<MODEL> searchBy_name_nameBN(Class<MODEL> clazz, String text);

    <MODEL> MODEL get(Class<MODEL> clazz, Long id);

    <MODEL> MODEL get(Class<MODEL> clazz, Long id, List<String> projections);

    <MODEL> MODEL get(Class<MODEL> clazz, String propertyName, Object propertyValue);

    <MODEL> MODEL get(Class<MODEL> clazz, String propertyName1, Object propertyValue1, String propertyName2, Object propertyValue2);

    <MODEL> MODEL get(Class<MODEL> clazz, String aliasModel, String propertyName, Object propertyValue);

    <MODEL> MODEL get(Class<MODEL> clazz, String aliasModel, String aliasPropertyName, Object aliasPropertyValue, String propertyName, Object propertyValue);

    <MODEL> MODEL get(Class<MODEL> clazz, Long id, Boolean active);

    Integer count(Class clazz);

    Integer count(Class clazz, Boolean active);

    Integer count(Class clazz, String propName, Object propVal);

    ActionResult writeDocument(Document doc, MultipartFile mpf);
}
