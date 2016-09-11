package com.angejia.dw.web_service.modules.broker.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;

import com.angejia.dw.web_service.modules.broker.service.MyService;

//import com.angejia.dw.web_service.modules.broker.controller.LoginAction;

/**
 * @author Jason

@Controller默认产生的Bean的name就是类（UserAction）的第一个字母小写（userAction）。
当然你也可以自己设定啊，@Controller("uu"),这样就<action name="user_*" class="uu" method="{1}">

Spring中除了提供 @Component 注释外，还定义了几个拥有特殊语义的注释，
它们分别是：@Repository、@Service 和 @Controller。
     @Service用于标注业务层组件，
     @Controller用于标注控制层组件（如struts中的action）,
     @Repository用于标注数据访问组件，即DAO组件，
     @Component泛指组件，当组件不好归类的时候，我们可以使用这个注解进行标注。
     
@ResultPath
 */


public class LoginAction extends ActionSupport {
    private String username;
    private String password;    

    private MyService ms;
    public void setMs(MyService ms)
    {
        this.ms = ms;
    }
    
    public String getUsername()
    {
        return username;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }    
    public String getPassword()
    {
        return password;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }

    public String execute() throws Exception
    {
        if (ms.validLogin(getUsername(), getPassword()) > 0)
        {
            addActionMessage("123");
            return SUCCESS;
        }
        else
        {
            return ERROR;
        }
    } 
    
     
    public String add() throws Exception
    {
       return SUCCESS;
    }
    
    
    //处理请求
    public String executeBal() throws Exception
    {
        if (this.getUsername().equals("jason")
            && this.getPassword().equals("jason") )
        {
            ActionContext.getContext().getSession()
                .put("user" , this.getUsername());
            this.addActionMessage("你好呀！");
            return SUCCESS;
        }
        return ERROR;
    }
}