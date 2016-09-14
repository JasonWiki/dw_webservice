package com.angejia.dw.web_service.modules.user.service;

import java.util.List;
import java.util.Map;

public interface UserPortraitService {

    public List<Map<String, String>> getUserPortraitResult(String rowKey, String cityId);
}
