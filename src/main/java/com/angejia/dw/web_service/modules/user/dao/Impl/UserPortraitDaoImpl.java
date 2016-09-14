package com.angejia.dw.web_service.modules.user.dao.Impl;

import java.util.Properties;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.angejia.dw.web_service.core.utils.hbase.HBaseUtil;

import com.angejia.dw.web_service.modules.user.dao.UserPortraitDao;

@Repository("userPortraitDao")
public class UserPortraitDaoImpl extends HBaseUtil implements UserPortraitDao  {

    // 公共配置文件
    @Autowired
    @Qualifier("commonProperties")
    private Properties commonProperties;

    public void init() {
        // 设置 zookeeper 地址
        super.setZookeepers(commonProperties.getProperty("hadoop.zookeepers"));
    }


    // 获取 tags 列族下的所有标签
    public Map<String,String> getUserPortraitTagsByRowkey(String Rowkey, List<String> columnNames) {

        this.init();

        return super.select("userPortrait", Rowkey, "tags", columnNames);
    }


    // 获取 Needs 列组下的所有标签
    public Map<String, String> getUserPortraitNeedsByRowkey(String Rowkey, List<String> columnNames) {

        this.init();

        return super.select("userPortrait", Rowkey, "needs", columnNames);
    }
}
