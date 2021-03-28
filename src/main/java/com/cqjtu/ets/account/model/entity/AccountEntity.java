package com.cqjtu.ets.account.model.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author yangyongtao
 * @Description TODO
 * @Date 2021/3/28 17:42
 * @Version 1.0
 */
@Data
public class AccountEntity implements Serializable {
    private String id;
    private String accountName;
    private String accountPassword;
    private Integer gender;
}
