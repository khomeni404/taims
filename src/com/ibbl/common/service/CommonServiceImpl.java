package com.ibbl.common.service;

import com.ibbl.common.dao.DaoFactory;
import com.ibbl.common.model.Document;
import com.ibbl.util.ActionResult;
import com.ibbl.util.SEUtil;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author Khomeni
 * Created on : 16-May-17
 */

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class CommonServiceImpl extends DaoFactory implements CommonService {

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean save(Object model) {
        return commonDAO.save(model);
    }

    @Override
    public boolean update(Object model) {
        return commonDAO.update(model);
    }

    @Override
    public boolean delete(Object model) {
        return commonDAO.delete(model);
    }

    @Override
    public <MODEL> List<MODEL> findAll(Class<MODEL> clazz) {
        return commonDAO.findAll(clazz);
    }

    @Override
    public <MODEL> List<MODEL> findAll(Class<MODEL> clazz, Order order) {
        return commonDAO.findAll(clazz, order);
    }

    @Override
    public <MODEL> List<MODEL> findAll(Class<MODEL> clazz, List<String> projections) {
        return commonDAO.findAll(clazz, projections);
    }

    @Override
    public <MODEL> List<MODEL> findAll(Class<MODEL> clazz, int start, int limit) {
        return commonDAO.findAll(clazz, start, limit);
    }

    @Override
    public <MODEL> List<MODEL> findAll(Class<MODEL> clazz, int start, int limit, List<String> projections) {
        return commonDAO.findAll(clazz, start, limit, projections);
    }

    @Override
    public <MODEL> List<MODEL> findAll(Class<MODEL> clazz, Boolean active) {
        return commonDAO.findAll(clazz, active);
    }

    @Override
    public <MODEL> List<MODEL> findAll(Class<MODEL> clazz, String propertyName, Object propertyValue) {
        return commonDAO.findAll(clazz, propertyName, propertyValue);
    }

    @Override
    public <MODEL> List<MODEL> findAll(Class<MODEL> clazz, String aliasModel, String propertyName, Object propertyValue) {
        return commonDAO.findAll(clazz, aliasModel, propertyName, propertyValue);
    }

    @Override
    public <MODEL> List<MODEL> findAll(Class<MODEL> clazz, Map<String, Object> propertyValueMap) {
        return commonDAO.findAll(clazz, propertyValueMap);
    }

    @Override
    public <MODEL> List<MODEL> searchBy_name(Class<MODEL> clazz, String text) {
        return commonDAO.searchBy_name(clazz, text);
    }

    @Override
    public <MODEL> List<MODEL> searchBy_name_nameBN(Class<MODEL> clazz, String text) {
        return commonDAO.searchBy_name_nameBN(clazz, text);
    }

    @Override
    public <MODEL> MODEL get(Class<MODEL> clazz, Long id) {
        return commonDAO.get(clazz, id);
    }

    @Override
    public <MODEL> MODEL get(Class<MODEL> clazz, Long id, List<String> projections) {
        return commonDAO.get(clazz, id, projections);
    }

    @Override
    public <MODEL> MODEL get(Class<MODEL> clazz, String propertyName, Object propertyValue) {
        return commonDAO.get(clazz, propertyName, propertyValue);
    }
    @Override
    public <MODEL> MODEL get(Class<MODEL> clazz, String propertyName1, Object propertyValue1, String propertyName2, Object propertyValue2) {
        return commonDAO.get(clazz, propertyName1, propertyValue1, propertyName2, propertyValue2);
    }


    @Override
    public <MODEL> MODEL get(Class<MODEL> clazz, String aliasModel, String propertyName, Object propertyValue) {
        return commonDAO.get(clazz, aliasModel, propertyName, propertyValue);
    }

    @Override
    public <MODEL> MODEL get(Class<MODEL> clazz, String aliasModel, String aliasPropertyName, Object aliasPropertyValue, String propertyName, Object propertyValue) {
        return commonDAO.get(clazz, aliasModel, aliasPropertyName, aliasPropertyValue, propertyName, propertyValue);
    }

    @Override
    public <MODEL> MODEL get(Class<MODEL> clazz, Long id, Boolean active) {
        return commonDAO.get(clazz, id, active);
    }

    @Override
    public Integer count(Class clazz) {
        return commonDAO.count(clazz);
    }

    @Override
    public Integer count(Class clazz, String propName, Object propVal) {
        return commonDAO.count(clazz, propName, propVal);
    }
    @Override
    public Integer count(Class clazz, Boolean active) {
        return commonDAO.count(clazz, active);
    }


    @Override
    public ActionResult writeDocument(Document doc, MultipartFile mpf) {
        ActionResult result = new ActionResult(false);
        /*if (mpf.getSize() < 1) {
            result.setMsg("File/Image size is ZERO or File/Image is Missing.");
            return result;
        }

        String uniqueName = SEUtil.getUniqueFileName();
        String originalName = SEUtil.getFileNameWithoutExtension(mpf);

        try {
            String saveDirectory = QRFConstant.DOC_PATH + "/" + doc.getDiscriminatorValue();
            File dir = new File(saveDirectory);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String ext = SEUtil.getFileExtension(mpf);
            File file = new File(saveDirectory + "/" + uniqueName + ext);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(mpf.getBytes());
            fos.close();
            fos.flush();

            doc.setOriginalName(originalName);
            doc.setGivenName(uniqueName);
            doc.setResourceDirectory(saveDirectory);
            doc.setExtension(ext);
            doc.setRecordDate(new Date());
            commonDAO.save(doc);

            result.setSuccess(true);

            result.setMsg(doc.getClass().getSimpleName()+" saved Successfully");
            return result;
        } catch (IOException ed) {
            result.setMsg(ed.getMessage());

        }*/
        return result;
    }
}
