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

    <PO> List<PO> findAll(Class<PO> clazz);

    public <PO> List<PO> findAll(Class<PO> clazz, Order order);

    <PO> List<PO> findAll(Class<PO> clazz, List<String> projections);

    <PO> List<PO> findAll(Class<PO> clazz, int start, int limit);

    <PO> List<PO> findAll(Class<PO> clazz, int start, int limit, List<String> projections);

    <PO> List<PO> findAll(Class<PO> clazz, Boolean active);

    <PO> List<PO> findAll(Class<PO> clazz, String propertyName, Object propertyValue);

    <PO> List<PO> findAll(Class<PO> clazz, String aliasModel, String propertyName, Object propertyValue);

    <PO> List<PO> findAll(Class<PO> clazz, Map<String, Object> paramValueMap);

    <PO> List<PO> searchBy_name(Class<PO> clazz, String text);

    <PO> List<PO> searchBy_name_nameBN(Class<PO> clazz, String text);

    <PO> PO get(Class<PO> clazz, Long id);

    <PO> PO get(Class<PO> clazz, Long id, List<String> projections);

    <PO> PO get(Class<PO> clazz, String propertyName, Object propertyValue);

    <PO> PO get(Class<PO> clazz, String propertyName1, Object propertyValue1, String propertyName2, Object propertyValue2);

    <PO> PO get(Class<PO> clazz, String aliasModel, String propertyName, Object propertyValue);

    <PO> PO get(Class<PO> clazz, String aliasModel, String aliasPropertyName, Object aliasPropertyValue, String propertyName, Object propertyValue);

    <PO> PO get(Class<PO> clazz, Long id, Boolean active);

    Integer count(Class clazz);

    Integer count(Class clazz, Boolean active);

    Integer count(Class clazz, String propName, Object propVal);

    ActionResult writeDocument(Document doc, MultipartFile mpf);
}
