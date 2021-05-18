package com.cqjtu.ets.onlinejudge.model.vo;

import com.cqjtu.ets.onlinejudge.model.entity.OjTestDataResult;

import java.util.ArrayList;
import java.util.List;

public class ScoreVO {
    private int status=0; // 0:ok 1:error
    private String errorInfo="";
    private List<OjTestDataResult> testDataResultList;
    private float score;

    public ScoreVO() {
        testDataResultList = new ArrayList<>();
        score = 0;
    }

    public List<OjTestDataResult> getTestDataResultList() {
        return testDataResultList;
    }

    public void setTestDataResultList(List<OjTestDataResult> testDataResultList) {
        this.testDataResultList = testDataResultList;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }
}
