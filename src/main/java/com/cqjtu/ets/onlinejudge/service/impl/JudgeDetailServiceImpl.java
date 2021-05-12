package com.cqjtu.ets.onlinejudge.service.impl;

import com.cqjtu.ets.onlinejudge.mapper.OjJudgeDetailMapper;
import com.cqjtu.ets.onlinejudge.model.entity.OjJudgeDetail;
import com.cqjtu.ets.onlinejudge.service.JudgeDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JudgeDetailServiceImpl implements JudgeDetailService {

    @Autowired
    private OjJudgeDetailMapper detailMapper;

    @Override
    public OjJudgeDetail getJudgeDetailByPK(long detail_id) {
        return detailMapper.selectByPK(detail_id);
    }

    @Override
    public OjJudgeDetail getJudgeDetailByJudgeId(long judge_id) {
        return detailMapper.selectByJudgeId(judge_id);
    }

    @Override
    public int addJudgeDetail(OjJudgeDetail detail) {
        return detailMapper.insert(detail);
    }

    @Override
    public int update(OjJudgeDetail detail) {
        return detailMapper.update(detail);
    }
}
