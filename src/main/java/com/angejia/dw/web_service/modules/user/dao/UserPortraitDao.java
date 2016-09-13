package com.angejia.dw.web_service.modules.user.dao;

import java.util.List;
import java.util.Map;

public interface UserPortraitDao {

    public Map<String, String> getUserPortraitTagsByRowkey(String Rowkey, List<String> columnNames);

    public Map<String, String> getUserPortraitNeedsByRowkey(String Rowkey);
}
