package com.cqjtu.ets.onlinejudge.service;

import com.cqjtu.ets.onlinejudge.model.entity.OjCommit;
import com.cqjtu.ets.onlinejudge.model.vo.UpLoadedFilesVO;

import java.util.List;

public interface CommitService {

    OjCommit getCommitByPK(long commit_id);
    List<OjCommit> getCommitBy(long experiment_id, long student_number_id);
}
