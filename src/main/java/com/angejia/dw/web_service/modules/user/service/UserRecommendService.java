package com.angejia.dw.web_service.modules.user.service;

import java.util.List;
import java.util.Map;

public interface UserRecommendService {

    public List<Map<String, String>> getUserPortraitRecommendInventorys(String userId, String cityId);
    
    public List<Map<String, String>> getUserUBCFRecommendInventorys(String userId, String cityId);
}
