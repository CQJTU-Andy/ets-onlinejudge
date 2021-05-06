package com.cqjtu.ets.onlinejudge.service.impl;

import com.cqjtu.ets.onlinejudge.mapper.OjProgrammingProblemMapper;
import com.cqjtu.ets.onlinejudge.model.vo.ExperimentInfoVO;
import com.cqjtu.ets.onlinejudge.service.JudgePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JudgePageServiceImpl implements JudgePageService {

    @Autowired
    private OjProgrammingProblemMapper ppMapper;

    //ExperimentMapper?

    @Override
    public ExperimentInfoVO getExperimentBasicInfo(long experiment_id) {
        // Experiment exp = experimentMapper.getExperiment(experiment_id);
        ExperimentInfoVO vo = new ExperimentInfoVO();
//        vo.set
        return null;
    }
}
