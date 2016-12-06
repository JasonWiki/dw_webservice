package com.angejia.dw.web_service.modules.broker.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.stereotype.Service;

import com.angejia.dw.web_service.modules.broker.entity.Broker;
import com.angejia.dw.web_service.modules.broker.service.BrokerService;

@Service("BrokerService")
public class BrokerServiceImpl implements BrokerService {

    @Override
    public Broker getBrokerByUserId(Long userId) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPA");
        EntityManager em = factory.createEntityManager();
        Broker u = em.find(Broker.class, userId);
        em.close();
        factory.close();
        return u;
    }
}
