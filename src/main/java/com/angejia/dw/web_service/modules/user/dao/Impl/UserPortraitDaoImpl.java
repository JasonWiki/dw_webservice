package com.angejia.dw.web_service.modules.user.dao.Impl;

import org.springframework.stereotype.Repository;

import com.angejia.dw.web_service.core.utils.hbase.HbaseUtil;

import com.angejia.dw.web_service.modules.user.dao.UserPortraitDao;

@Repository("userPortraitDao")
public class UserPortraitDaoImpl extends HbaseUtil implements UserPortraitDao  {

    public void getUserPortraitByRowkey(String Rowkey) {
        System.out.println(Rowkey);
    }
}
