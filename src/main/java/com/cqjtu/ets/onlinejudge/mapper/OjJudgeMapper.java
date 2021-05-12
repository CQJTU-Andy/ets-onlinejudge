package com.cqjtu.ets.onlinejudge.mapper;

import com.cqjtu.ets.onlinejudge.model.entity.OjJudge;
import com.cqjtu.ets.onlinejudge.model.entity.OjJudgeDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OjJudgeMapper {
    OjJudge selectByPK(long judge_id);
    OjJudge selectByCommitId(long commit_id);
    List<Long> selectJudgeId();
    int insert(OjJudge judge);
}
