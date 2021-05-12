package com.cqjtu.ets.onlinejudge.service;

import com.cqjtu.ets.onlinejudge.model.entity.OjCommit;

import java.util.List;

public interface CommitService {

    OjCommit getCommitByPK(long commit_id);
    List<OjCommit> getCommitBy(long experiment_id, long student_number_id);
    OjCommit getLatestCommitBy(long eid,long userId);

    /**
     * 获取最大的commit_id
     * @return 如果表中没有数据，则返回0
     */
    Long getMaxCommitId();

    int addCommit(OjCommit commit);
}
