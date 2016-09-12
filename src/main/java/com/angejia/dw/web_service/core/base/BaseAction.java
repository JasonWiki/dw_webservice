package com.angejia.dw.web_service.core.base;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

// 以下是 struts2 用到的注解
// Namespace: 指定命名空间
import org.apache.struts2.convention.annotation.Namespace;
// ParentPackage: 指定父包
import org.apache.struts2.convention.annotation.ParentPackage;
// Result: 提供了 Action 类 结果的映射。（一个结果的映射)
import org.apache.struts2.convention.annotation.Result;
// Results: Result 的注解列表
import org.apache.struts2.convention.annotation.Results;
// ResultPath: 指定结果页面的基路径。
import org.apache.struts2.convention.annotation.ResultPath;
// Action: 指定 Action 的访问 URL。
import org.apache.struts2.convention.annotation.Action;
// Actions: "Action" 注解列表。
import org.apache.struts2.convention.annotation.Actions;
// ExceptionMapping：指定异常映射。（映射一个声明异常）
import org.apache.struts2.convention.annotation.ExceptionMapping;
// ExceptionMappings：一级声明异常的数组
import org.apache.struts2.convention.annotation.ExceptionMappings;
// InterceptorRef: 拦截器引用。
import org.apache.struts2.convention.annotation.InterceptorRef;
// InterceptorRefs: 拦截器引用组。
import org.apache.struts2.convention.annotation.InterceptorRefs;

public class BaseAction extends ActionSupport {
    private static final long serialVersionUID = 1L;
}
