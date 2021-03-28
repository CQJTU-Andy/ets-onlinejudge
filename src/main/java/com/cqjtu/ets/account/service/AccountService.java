package com.cqjtu.ets.account.service;

import com.cqjtu.ets.account.model.vo.AccountVO;

import java.util.List;

/**
 * @Author yangyongtao
 * @Description TODO
 * @Date 2021/3/28 17:44
 * @Version 1.0
 */
public interface AccountService {
    List<AccountVO> findAll();
}
