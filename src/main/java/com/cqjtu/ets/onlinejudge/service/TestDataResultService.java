package com.cqjtu.ets.onlinejudge.service;

import com.cqjtu.ets.onlinejudge.model.entity.OjTestDataResult;

import java.util.List;

public interface TestDataResultService {
    OjTestDataResult getTestDataResultByPK(long test_data_id);
    int add(OjTestDataResult result);
    List<OjTestDataResult> getResultByDetailId(long detail_id);
    long getMaxDataResultId();
}
