package com.angejia.dw.web_service.modules.broker.service;

import java.util.List;
import java.util.Map;

public interface BrokerUserMateInventoryService {

    // 获取推荐房源数据
    public List<Map<String, String>> getBrokerUserMateInventory(Long brokerId, Long userId, Long cityId);
}
