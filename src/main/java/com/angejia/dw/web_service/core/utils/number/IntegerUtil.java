package com.angejia.dw.web_service.core.utils.number;

import java.util.Random;

public class IntegerUtil {

    
    /**
     * 根据范围生成随机数
     * @param min
     * @param max
     * @return
     */
    public static Integer generateRandom (Integer min, Integer max) {
        Random random = new Random();
        Integer s = random.nextInt(max)%(max-min+1) + min;
        return s;
    }
}
