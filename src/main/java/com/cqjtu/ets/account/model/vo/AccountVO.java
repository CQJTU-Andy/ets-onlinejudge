package com.cqjtu.ets.account.model.vo;

import lombok.Data;

/**
 * @Author yangyongtao
 * @Description TODO
 * @Date 2021/3/28 17:43
 * @Version 1.0
 */
@Data
public class AccountVO {
    private String id;
    private String accountName;
    private String accountPassword;
    private Integer gender;
}
