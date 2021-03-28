package com.cqjtu.ets.account.mapper;

import com.cqjtu.ets.account.model.entity.AccountEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author yangyongtao
 * @Description TODO
 * @Date 2021/3/28 17:40
 * @Version 1.0
 */
@Mapper
@Repository
public interface AccountMapper {
    List<AccountEntity> findAll();
}
