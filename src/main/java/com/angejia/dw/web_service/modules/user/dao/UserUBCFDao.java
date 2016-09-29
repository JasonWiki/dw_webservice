package com.angejia.dw.web_service.modules.user.dao;

import java.util.List;
import java.util.Map;

public interface UserUBCFDao {

    public List<Map<String, String>> getUserCBCFRecommendInventorys(String userId, String cityId, Integer offset, Integer limit);

}
