package com.angejia.dw.web_service.modules.inventory.service;

import java.util.List;
import java.util.Map;

import com.angejia.dw.web_service.modules.entity.dw.dw_service.ProertyInventoryIndexEntity;

public interface InventoryService {

    public List<Map<String, String>> searchInventoryByEntity(ProertyInventoryIndexEntity entity, Integer offset, Integer limit);
}
