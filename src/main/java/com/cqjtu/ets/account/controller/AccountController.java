package com.cqjtu.ets.account.controller;

import com.cqjtu.ets.account.service.AccountService;
import com.cqjtu.ets.common.bean.response.IResult;
import com.cqjtu.ets.common.bean.response.PageResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author yangyongtao
 * @Description TODO
 * @Date 2021/3/28 17:49
 * @Version 1.0
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/findAll")
    public IResult findAll(){
        return new PageResultBean(accountService.findAll(),accountService.findAll().size());
    }

}
