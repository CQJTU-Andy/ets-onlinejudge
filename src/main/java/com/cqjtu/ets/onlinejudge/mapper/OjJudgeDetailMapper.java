package com.cqjtu.ets.onlinejudge.mapper;
import com.cqjtu.ets.onlinejudge.model.entity.OjJudgeDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OjJudgeDetailMapper {
    OjJudgeDetail selectByPK(long detail_id);
    OjJudgeDetail selectByJudgeId(long judge_id);
    int insert(OjJudgeDetail detail);
    int update(OjJudgeDetail detail);
}
