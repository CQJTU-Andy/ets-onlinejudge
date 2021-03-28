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
public class ResultBean<T> extends AbstractResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据
     */
    private T data;

    public ResultBean() {
        super();
    }

    /**
     * 构造函数
     * @param data
     */
    public ResultBean(T data) {
        super();
        this.data = data;
    }

    /**
     * 构造函数
     * @param e
     */
    public ResultBean(IErrCode e) {
        super();
        this.msg = e.getDesc();
        this.code = e.getCode();
    }

    /**
     * 构造函数
     * @param e
     */
    public ResultBean(Throwable e) {
        super();
        this.msg = e.toString();
        this.code = SYSTEM_FAIL;
    }

    /**
     * 构造函数
     * @param code
     * @param msg
     */
    public ResultBean(String code,String msg) {
        super();
        this.msg = msg;
        this.code = code;
    }

    @Override
    public String toString(){
        return "[code="+code+",data="+data+"]";
    }
}

