package com.cqjtu.ets.account.service.impl;

import com.cqjtu.ets.account.mapper.AccountMapper;
import com.cqjtu.ets.account.model.entity.AccountEntity;
import com.cqjtu.ets.account.model.vo.AccountVO;
import com.cqjtu.ets.account.service.AccountService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * @Author yangyongtao
 * @Description TODO
 * @Date 2021/3/28 17:44
 * @Version 1.0
 */
@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public List<AccountVO> findAll() {
        List<AccountEntity> accountEntities = accountMapper.findAll();
        List<AccountVO> accountVOS = Lists.newArrayList();
        if(CollectionUtils.isEmpty(accountEntities)){
            log.info("数据为空");
            return Collections.emptyList();
        }
        accountEntities.stream().forEach(accountEntity -> {
            AccountVO accountVO = new AccountVO();
            accountVO.setId(accountEntity.getId());
            accountVO.setAccountName(accountEntity.getAccountName());
            accountVO.setAccountPassword(accountEntity.getAccountPassword());
            accountVO.setGender(accountEntity.getGender());
            accountVOS.add(accountVO);
        });
        return accountVOS;
    }
}
