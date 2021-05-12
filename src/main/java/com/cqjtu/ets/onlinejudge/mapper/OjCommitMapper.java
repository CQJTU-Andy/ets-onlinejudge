package com.cqjtu.ets.onlinejudge.mapper;

import com.cqjtu.ets.onlinejudge.model.entity.OjCommit;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OjCommitMapper {
//    int deleteByPrimaryKey(Long commitId);

//    int insert(OjCommit record);
//
//    int insertSelective(OjCommit record);
//
    OjCommit selectByPK(Long commit_id);
    List<OjCommit> selectBy(long experiment_id,long student_number_id);
    List<Long> selectCommitId(); // 降序
    int insert(OjCommit commit);
//
//    int updateByPrimaryKeySelective(OjCommit record);
//
//    int updateByPrimaryKey(OjCommit record);
}