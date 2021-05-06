package com.cqjtu.ets.onlinejudge.mapper;

import com.cqjtu.ets.onlinejudge.model.entity.OjProgrammingProblem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OjProgrammingProblemMapper {
    OjProgrammingProblem selectByPrimaryKey(long problem_id);
}
