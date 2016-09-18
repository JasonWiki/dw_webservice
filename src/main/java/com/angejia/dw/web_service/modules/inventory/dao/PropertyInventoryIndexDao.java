package com.angejia.dw.web_service.modules.inventory.dao;

import java.util.List;

import com.angejia.dw.web_service.core.base.dao.BaseDao;
import com.angejia.dw.web_service.modules.entity.dw.dw_service.PropertyInventoryIndexEntity;


/**
 * @author Jason
 */
public interface PropertyInventoryIndexDao extends BaseDao<PropertyInventoryIndexEntity> {
    
    public List<PropertyInventoryIndexEntity> getInventorysByEntity(PropertyInventoryIndexEntity entity, Integer offset, Integer limit);
}
