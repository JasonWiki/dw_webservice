package com.angejia.dw.web_service.modules.inventory.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.angejia.dw.web_service.modules.inventory.service.InventoryService;
import com.angejia.dw.web_service.modules.entity.dw.dw_service.ProertyInventoryIndexEntity;
import com.angejia.dw.web_service.modules.inventory.dao.ProertyInventoryIndexDao;

@Service("inventoryService")
public class InventoryServiceImpl implements InventoryService{

    @Autowired
    private ProertyInventoryIndexDao proertyInventoryIndexDao;

    public List<Map<String, String>> searchInventoryByEntity(ProertyInventoryIndexEntity entity, Integer offset, Integer limit) {
        
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
 
        System.out.println( proertyInventoryIndexDao.getInventorysByEntity(entity, offset, limit) );

        return result;
    }
}
