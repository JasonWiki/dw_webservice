package com.angejia.dw.web_service.modules.user.action;

import org.apache.struts2.StrutsSpringTestCase;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import com.opensymphony.xwork2.ActionProxy;

public class UserRecommendActionTest extends StrutsSpringTestCase {
    @Override
    protected String[] getContextLocations() {
        return new String[] { "classpath:spring/applicationContext.xml" };
    }

    @Test
    public void testExecute() throws Exception {
        request = new MockHttpServletRequest();
        ActionProxy proxy = getActionProxy("/user/recommend/user-recommend-inventories");
        UserRecommendAction test = (UserRecommendAction) proxy.getAction();
        assertNotNull(test);
        String result = proxy.execute();
        assertEquals("success", result);
    }
}
