package com.cqjtu.ets.onlinejudge.service.impl;

import com.cqjtu.ets.onlinejudge.mapper.OjCommitMapper;
import com.cqjtu.ets.onlinejudge.model.entity.OjCommit;
import com.cqjtu.ets.onlinejudge.model.vo.UpLoadedFilesVO;
import com.cqjtu.ets.onlinejudge.service.CommitService;
import com.cqjtu.ets.onlinejudge.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class CommitServiceImpl implements CommitService {

    @Autowired
    private OjCommitMapper ojCommitMapper;

    @Override
    public OjCommit getCommitByPK(long commit_id) {
        return ojCommitMapper.selectByPrimaryKey(commit_id);
    }

    @Override
    public List<OjCommit> getCommitBy(long experiment_id, long student_number_id) {
        return  ojCommitMapper.selectBy(experiment_id,student_number_id);
    }
}
