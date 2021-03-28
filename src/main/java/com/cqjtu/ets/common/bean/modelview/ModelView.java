package com.cqjtu.ets.common.bean.modelview;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author yangyongtao
 * @Description TODO
 * @Date 2021/3/28 17:22
 * @Version 1.0
 */
@Data
public class ModelView {
    /**
     * 填充model的内容
     * */
    private Map<String,Object> attributes;

    /**
     * 返回视图名
     * */
    private String returnView;

    /**
     * 构造函数
     * */
    public ModelView(){
        this.attributes = new HashMap<String, Object>();
    }
}