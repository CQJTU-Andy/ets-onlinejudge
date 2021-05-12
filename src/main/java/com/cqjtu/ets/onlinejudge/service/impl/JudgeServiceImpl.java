package com.cqjtu.ets.onlinejudge.service.impl;

import com.cqjtu.ets.onlinejudge.mapper.OjJudgeMapper;
import com.cqjtu.ets.onlinejudge.model.entity.OjJudge;
import com.cqjtu.ets.onlinejudge.service.JudgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JudgeServiceImpl implements JudgeService {

    @Autowired
    private OjJudgeMapper judgeMapper;

    @Override
    public OjJudge getJudgeByPK(long judge_id) {
        return judgeMapper.selectByPK(judge_id);
    }

    @Override
    public OjJudge getJudgeByCommitId(long commit_id) {
        return judgeMapper.selectByCommitId(commit_id);
    }

    @Override
    public long getMaxJudgeId() {
        List<Long> judgeIdList = judgeMapper.selectJudgeId();
        if(judgeIdList !=null && judgeIdList.size() > 0){
            return judgeIdList.get(0);
        }
        return 0L;
    }

    @Override
    public int addJudge(OjJudge judge) {
       return judgeMapper.insert(judge);
    }
}
