package com.cqjtu.ets.onlinejudge.mapper;

import com.cqjtu.ets.onlinejudge.model.entity.OjJudge;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OjJudgeMapper {
    OjJudge selectByPK(long judge_id);
}
