package com.cqjtu.ets.onlinejudge.service;

import com.cqjtu.ets.onlinejudge.model.entity.OjJudgeDetail;

public interface JudgeDetailService {
    OjJudgeDetail getJudgeDetailByPK(long detail_id);
    OjJudgeDetail getJudgeDetailByJudgeId(long judge_id);
    int addJudgeDetail(OjJudgeDetail detail);
    int update(OjJudgeDetail detail);
}
