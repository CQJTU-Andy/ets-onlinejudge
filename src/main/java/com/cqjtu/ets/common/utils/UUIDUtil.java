package com.cqjtu.ets.common.utils;

import java.util.UUID;

/**
 * @Author yangyongtao
 * @Description TODO
 * @Date 2021/3/28 17:28
 * @Version 1.0
 */
public class UUIDUtil {
    public static String getUUID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }
}
