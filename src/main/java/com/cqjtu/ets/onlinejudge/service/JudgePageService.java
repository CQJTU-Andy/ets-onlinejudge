package com.cqjtu.ets.onlinejudge.service;

import com.cqjtu.ets.onlinejudge.model.vo.ExperimentInfoVO;

public interface JudgePageService {
    ExperimentInfoVO getExperimentBasicInfo(long experiment_id);
}
