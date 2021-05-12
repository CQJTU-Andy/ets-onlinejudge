package com.cqjtu.ets.onlinejudge.service.impl;

import com.cqjtu.ets.onlinejudge.mapper.OjProgrammingProblemMapper;
import com.cqjtu.ets.onlinejudge.model.entity.OjProgrammingProblem;
import com.cqjtu.ets.onlinejudge.service.ProgrammingProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProgrammingProblemServiceImpl implements ProgrammingProblemService {

    @Autowired
    private OjProgrammingProblemMapper programmingProblemMapper;

    @Override
    public OjProgrammingProblem getProgrammingProblemByPK(long problem_id) {
       return programmingProblemMapper.selectByPK(problem_id);
    }
}
