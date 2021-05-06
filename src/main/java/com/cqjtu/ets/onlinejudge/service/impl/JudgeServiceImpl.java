package com.cqjtu.ets.onlinejudge.service.impl;

import com.cqjtu.ets.onlinejudge.mapper.OjJudgeMapper;
import com.cqjtu.ets.onlinejudge.model.entity.OjJudge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JudgeServiceImpl implements JudgeService{

    @Autowired
    private OjJudgeMapper judgeMapper;

    @Override
    public OjJudge getJudgeByPK(long judge_id) {
        return judgeMapper.selectByPK(judge_id);
    }
}
