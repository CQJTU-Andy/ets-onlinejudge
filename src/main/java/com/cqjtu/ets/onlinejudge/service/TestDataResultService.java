package com.cqjtu.ets.onlinejudge.service;

import com.cqjtu.ets.onlinejudge.model.entity.OjTestDataResult;

public interface TestDataResultService {
    OjTestDataResult getTestDataResultByPK(long test_data_id);
}
