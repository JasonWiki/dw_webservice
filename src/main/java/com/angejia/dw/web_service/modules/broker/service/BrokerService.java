package com.angejia.dw.web_service.modules.broker.service;

import com.angejia.dw.web_service.modules.broker.entity.Broker;

public interface BrokerService {

    public Broker getBrokerByUserId(Long userId);
}
