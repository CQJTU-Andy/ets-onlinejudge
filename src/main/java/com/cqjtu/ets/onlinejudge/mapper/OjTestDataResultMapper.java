package com.cqjtu.ets.onlinejudge.mapper;

import com.cqjtu.ets.onlinejudge.model.entity.OjTestDataResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OjTestDataResultMapper {
    OjTestDataResult selectByPK(long test_data_id);
    int insert(OjTestDataResult result);
    List<Long> selectDataResultId();
    List<OjTestDataResult> getResultByDetailId(long detail_id);
}
