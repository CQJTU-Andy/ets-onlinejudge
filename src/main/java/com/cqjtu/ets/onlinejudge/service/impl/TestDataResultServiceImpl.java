package com.cqjtu.ets.onlinejudge.service.impl;

import com.cqjtu.ets.onlinejudge.mapper.OjTestDataResultMapper;
import com.cqjtu.ets.onlinejudge.model.entity.OjTestDataResult;
import com.cqjtu.ets.onlinejudge.service.TestDataResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestDataResultServiceImpl implements TestDataResultService {

    @Autowired
    private OjTestDataResultMapper testDataResultMapper;

    @Override
    public OjTestDataResult getTestDataResultByPK(long test_data_id) {
        return testDataResultMapper.selectByPK(test_data_id);
    }

    @Override
    public int add(OjTestDataResult result) {
        return testDataResultMapper.insert(result);
    }

    @Override
    public List<OjTestDataResult> getResultByDetailId(long detail_id) {
        return testDataResultMapper.getResultByDetailId(detail_id);
    }

    @Override
    public long getMaxDataResultId() {
        List<Long> DataResultIdList = testDataResultMapper.selectDataResultId();
        if(DataResultIdList !=null && DataResultIdList.size() > 0){
            return DataResultIdList.get(0);
        }
        return 0L;
    }
}
