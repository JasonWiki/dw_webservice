package com.angejia.dw.web_service.modules.user.service;

import java.util.List;
import java.util.Map;

public interface UserRecommendService {

    public List<Map<String, String>> getUserCBCFRecommendInventorys(String userId, String cityId, Integer limit);

    public List<Map<String, String>> getUserUBCFRecommendInventorys(String userId, String cityId, Integer offset, Integer limit);

    // 通过标签获取推荐房源数据
    public List<Map<String, String>> getRecommendInventorysByTags(Map<String, String> searchTags, String searchFrom, Integer offset, Integer limit);
}
