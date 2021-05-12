package com.cqjtu.ets.onlinejudge.service.impl;

import com.cqjtu.ets.onlinejudge.mapper.OjTestDataResultMapper;
import com.cqjtu.ets.onlinejudge.model.entity.OjTestDataResult;
import com.cqjtu.ets.onlinejudge.service.TestDataResultService;
import org.springframework.beans.factory.annotation.Autowired;

public class TestDataResultServiceImpl implements TestDataResultService {

    @Autowired
    private OjTestDataResultMapper testDataResultMapper;

    @Override
    public OjTestDataResult getTestDataResultByPK(long test_data_id) {
        return testDataResultMapper.selectByPK(test_data_id);
    }
}
