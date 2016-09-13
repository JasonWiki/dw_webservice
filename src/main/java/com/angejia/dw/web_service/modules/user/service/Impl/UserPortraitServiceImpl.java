package com.angejia.dw.web_service.modules.user.service.Impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.angejia.dw.web_service.core.utils.hbase.HbaseUtil;

import com.angejia.dw.web_service.modules.user.service.UserPortraitService;

import com.angejia.dw.web_service.modules.user.dao.UserPortraitDao;

@Service("userPortraitService")
public class UserPortraitServiceImpl extends HbaseUtil implements UserPortraitService {


    @Autowired
    private UserPortraitDao userPortraitDao;
    
    public void getUserPortraitByRowkey(String Rowkey) {
        
        userPortraitDao.getUserPortraitByRowkey(Rowkey);
    }
}
