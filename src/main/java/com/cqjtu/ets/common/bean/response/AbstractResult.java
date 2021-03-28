package com.cqjtu.ets.common.bean.response;

import lombok.Data;

/**
 * @Author yangyongtao
 * @Description TODO
 * @Date 2021/3/28 17:23
 * @Version 1.0
 */
@Data
public abstract class AbstractResult implements IResult{
    /**成功Code*/
    public static final String SUCCESS = "0000";
    /**系统失败Code*/
    public static final String SYSTEM_FAIL = "9999";
    /**检查失败Code*/
    public static final String CHECK_FAIL = "9100";
    /**业务失败Code*/
    public static final String BIZ_FAIL = "9200";
    /**错误代码*/
    protected String code = SUCCESS;
    /**错误信息*/
    protected String msg = "success";
}
