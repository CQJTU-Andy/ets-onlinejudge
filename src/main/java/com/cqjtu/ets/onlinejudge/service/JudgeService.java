package com.cqjtu.ets.onlinejudge.service;

import com.cqjtu.ets.onlinejudge.model.entity.OjJudge;

public interface JudgeService {
    OjJudge getJudgeByPK(long judge_id);
    OjJudge getJudgeByCommitId(long commit_id);
    long getMaxJudgeId();
    int addJudge(OjJudge judge);
}
