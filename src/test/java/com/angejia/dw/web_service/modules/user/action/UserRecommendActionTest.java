package com.angejia.dw.web_service.modules.user.action;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/ApplicationContext.xml" })
public class UserRecommendActionTest {
    @Test
    @Transactional
    @Rollback(true)
    public void evaluatesExpression() {
        int sum = 6;
        assertEquals(6, sum);
    }
}
