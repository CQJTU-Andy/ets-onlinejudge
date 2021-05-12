package com.cqjtu.ets.onlinejudge.mapper;

import com.cqjtu.ets.onlinejudge.model.entity.OjTestDataResult;

public interface OjTestDataResultMapper {
    OjTestDataResult selectByPK(long test_data_id);
}
