package com.cqjtu.ets.common.bean.response;

import com.cqjtu.ets.common.bean.error.IErrCode;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author yangyongtao
 * @Description TODO
 * @Date 2021/3/28 17:24
 * @Version 1.0
 */
@Data
public class PageResultBean<T> extends AbstractResult  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private T data;

    /**
     * 构造函数
     */
    private Integer count;

    public PageResultBean() {
        super();
    }

    /**
     * 构造函数
     * @param data
     * @param count
     */
    public PageResultBean(T data,Integer count) {
        super();
        this.data = data;
        this.count = count;
    }

    /**
     * 构造函数
     * @param e
     */
    public PageResultBean(IErrCode e) {
        super();
        this.msg = e.getDesc();
        this.code = e.getCode();
    }

    public PageResultBean(Throwable e) {
        super();
        this.msg = e.toString();
        this.code = SYSTEM_FAIL;
    }

    /**
     * 构造函数
     * @param code
     * @param msg
     */
    public PageResultBean(String code,String msg) {
        super();
        this.msg = msg;
        this.code = code;
    }
}
