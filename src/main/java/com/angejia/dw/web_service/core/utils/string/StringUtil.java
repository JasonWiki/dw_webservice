package com.angejia.dw.web_service.core.utils.string;

import java.awt.List;  
import java.lang.reflect.Array;  
import java.lang.reflect.Method;  
import java.util.ArrayList;  
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;  
import java.util.LinkedHashMap;  
import java.util.LinkedHashSet;  
import java.util.LinkedList;  
import java.util.Map;  
import java.util.Set;  
import java.util.SortedMap;  
import java.util.TreeMap;  
import java.util.TreeSet;  
import java.util.Vector;  
import java.util.WeakHashMap;  
import java.util.regex.Pattern;  
import java.util.Iterator;  

public class StringUtil {

    /** 
     * 空值检查
     * @param pInput   要检查的字符串
     * @return boolean 返回检查结果,但传入的字符串为空的场合,返回真
     */  
    public static boolean isNull (Object pInput) {  
        // 判断参数是否为空或者''  
        if (pInput == null || "".equals(pInput)) {  
            return true;  
        } else if ("java.lang.String".equals(pInput.getClass().getName())){  
            // 判断传入的参数的String类型的  
  
            // 替换各种空格  
            String tmpInput = Pattern.compile("//r|//n|//u3000")  
                                     .matcher((String)pInput).replaceAll("");  
            // 匹配空  
            return Pattern.compile("^(//s)*$")  
                          .matcher(tmpInput).matches();  
        } else {  
            // 方法类  
            Method method = null;  
            String newInput = "";  
            try {  
                // 访问传入参数的size方法  
                method = pInput.getClass().getMethod("size");  
                // 判断size大小  
  
                // 转换为String类型  
                newInput = String.valueOf(method.invoke(pInput));  
                // size为0的场合  
                if (Integer.parseInt(newInput) == 0) {  
  
                    return true;  
                } else {  
  
                    return false;  
                }  
            } catch (Exception e) {  
                // 访问失败  
                try {  
                    // 访问传入参数的getItemCount方法  
                    method = pInput.getClass().getMethod("getItemCount");  
                    // 判断size大小  
                      
                    // 转换为String类型  
                    newInput = String.valueOf(method.invoke(pInput));  
                      
                    // getItemCount为0的场合  
                    if (Integer.parseInt(newInput) == 0) {  
  
                        return true;  
                    } else {  
  
                        return false;  
                    }  
                } catch (Exception ex) {  
                    // 访问失败  
                    try{  
                        // 判断传入参数的长度  
  
                        // 长度为0的场合  
                        if (Array.getLength(pInput) == 0) {  
  
                            return true;  
                        } else {  
  
                            return false;  
                        }  
                    } catch (Exception exx) {  
                        // 访问失败  
                        try{  
                            // 访问传入参数的hasNext方法  
                            method = Iterator.class.getMethod("hasNext");  
                            // 转换String类型  
                            newInput = String.valueOf(method.invoke(pInput));  
                              
                            // 转换hasNext的值  
                            if (!Boolean.valueOf(newInput)) {  
                                return true;  
                            } else {  
                                return false;  
                            }  
                              
                        } catch (Exception exxx) {  
                            // 以上场合不满足  
                              
                            return false;  
                        }  
                    }  
                }  
            }  
        }  
    }  
    
    
    /**
     * string[] 数组转换为 byte[]
     * @param str
     * @return
     */
    public static Byte[] strArrToByteArr(String[] str)  {
        Byte[] bytes = new Byte[str.length];

        int i = 0;
        for(String s:str){
            System.out.println(s);
            bytes[i] = Byte.parseByte(s);
            i ++;
        }
        return bytes;
    }
    
    
    
    /**
     * String 数组转 Integer 数组
     * @param str
     * @return
     */
    public static Integer[] strArrToIntArr(String[] str) {
        Integer[] rs = new Integer[str.length] ;
        int i = 0;
        for(String s:str){
            rs[i] = Integer.parseInt(s);
            i ++;
        }
        return rs;
    }
}
