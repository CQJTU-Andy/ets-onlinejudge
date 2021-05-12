package com.cqjtu.ets.onlinejudge.service.impl;

import com.cqjtu.ets.onlinejudge.mapper.OjCommitMapper;
import com.cqjtu.ets.onlinejudge.model.entity.OjCommit;
import com.cqjtu.ets.onlinejudge.service.CommitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommitServiceImpl implements CommitService {

    @Autowired
    private OjCommitMapper ojCommitMapper;

    @Override
    public OjCommit getCommitByPK(long commit_id) {
        return ojCommitMapper.selectByPK(commit_id);
    }

    @Override
    public List<OjCommit> getCommitBy(long experiment_id, long student_number_id) {
        return  ojCommitMapper.selectBy(experiment_id,student_number_id);
    }

    @Override
    public OjCommit getLatestCommitBy(long eid,long userId) {

        List<OjCommit> commitList = getCommitBy(eid, userId);
        if(commitList!=null && commitList.size() >0){
            return commitList.get(0);
        }else{
            return null;
        }
    }

    @Override
    public Long getMaxCommitId() {
        List<Long> commitIdList = ojCommitMapper.selectCommitId();
        if(commitIdList !=null && commitIdList.size() > 0){
            return commitIdList.get(0);
        }
        return 0L;
    }

    @Override
    public int addCommit(OjCommit commit) {
        return ojCommitMapper.insert(commit);
    }
}
