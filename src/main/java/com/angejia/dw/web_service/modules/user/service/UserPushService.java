package com.angejia.dw.web_service.modules.user.service;

import java.util.Map;

public interface UserPushService {

    public Map<String, String> getUserInfoByUserId(String userId, String cityId, Integer limit);

}
