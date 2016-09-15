package com.angejia.dw.web_service.modules.inventory.dao;

import java.util.List;

import com.angejia.dw.web_service.core.base.dao.BaseDao;
import com.angejia.dw.web_service.modules.entity.dw.dw_service.ProertyInventoryIndexEntity;


/**
 * @author Jason
 */
public interface ProertyInventoryIndexDao extends BaseDao<ProertyInventoryIndexEntity> {
    
    public List<ProertyInventoryIndexEntity> getInventorysByEntity(ProertyInventoryIndexEntity entity, Integer offset, Integer limit);
}
