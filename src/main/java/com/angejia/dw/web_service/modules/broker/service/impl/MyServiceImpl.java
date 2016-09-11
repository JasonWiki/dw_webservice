package com.angejia.dw.web_service.modules.broker.service.impl;

import com.angejia.dw.web_service.modules.broker.service.*;

public class MyServiceImpl implements MyService
{
    public int validLogin(String username , String pass)
    {
        if ( username.equals("crazyit.org")
            && pass.equals("leegang") )
        {
            return 99;
        }
        return -1;
    }
}
