package com.angejia.dw.web_service.modules.broker.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.springframework.stereotype.Controller;


//import com.angejia.dw.web_service.modules.broker.controller.LoginAction;

/**
 * @author Jason

Spring 注解: 

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Controller;
    import org.springframework.stereotype.Service;
    import org.springframework.stereotype.Repository;
    import org.springframework.stereotype.Component;

    import org.springframework.transaction.annotation.Transactional; // 事物注解

    @Controller 默认产生的Bean的name就是类（UserAction）的第一个字母小写（userAction）。
        当然你也可以自己设定啊，@Controller("uu"),这样就<action name="user_*" class="uu" method="{1}">
        @Controller 用于标注控制层组件（如struts中的action）,

    @Repository 用于标注数据访问组件，即DAO组件，

    @Service 用于标注业务层组件，

    @Component 泛指组件，当组件不好归类的时候，我们可以使用这个注解进行标注。

    @Autowired 它对类成员变量、方法及构造函数进行标注，完成自动装配的工作。 
              通过 @Autowired 的使用来消除 set ，get方法。

    @Transactional spring 与 hibernate 整合时, 用到的事物注解


Struts 注解:
    @Namespace: 定义命名空间
        @Namespace("/broker")

    @ParentPackage
        @ParentPackage("json-default")  //继承 struts2 json 包

    @Result : 设置返回资源
        // 全局设置返回资源
        @Result(
                // 返回资源类型为 json
                type = "json", 
                // 设置返回资源的数据变量
                params = { "root", "result" }
        )

    @Action: 定义访问地址和返回资源类型
        @Action(value="list", results={@Result(location="list.jsp")})
        @Action("broker-user-mate-inventory") // 定义访问地址 

    @ResultPath
    
    

 */


public class DemoAction extends ActionSupport {
    private String username;
    private String password;    

    
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
            return SUCCESS;
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