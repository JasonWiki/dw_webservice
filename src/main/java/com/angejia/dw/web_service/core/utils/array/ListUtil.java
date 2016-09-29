package com.angejia.dw.web_service.core.utils.array;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListUtil {

    
    /**
     * list 中 map key 重复出现的 value 去重
     * @param sourctList
     * @param mapKey
     * @return result
     */
    public static List<Map<String, String>> listMapValDistinct (List<Map<String, String>> sourctList, String mapKey) {

        List<Map<String, String>> result = new ArrayList<Map<String, String>>();

        // 重复出现 mapKey 的值
        Map<String, String> isExistsValues = new HashMap<String, String>();   // 保存已经存在的房源
        for( Map<String, String> curMap : sourctList){

            // 当前推荐出来的房源 ID
            String keyVal = curMap.get(mapKey);

            if (keyVal != null) {
                // 如果 当前 key 不存在
                if (! isExistsValues.containsKey(keyVal)) {
                    // 追加到最终的结果中
                    result.add(curMap);
                }
                isExistsValues.put(keyVal, "");
            }
        }
        return result;
    }
    
   
}
