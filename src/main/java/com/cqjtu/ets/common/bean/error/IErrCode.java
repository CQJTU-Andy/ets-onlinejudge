package com.cqjtu.ets.common.bean.error;

/**
 * @Author yangyongtao
 * @Description TODO
 * @Date 2021/3/28 17:21
 * @Version 1.0
 */
public interface IErrCode {
    String getCode();

    String getDesc();

    @Override
    String toString();
}
