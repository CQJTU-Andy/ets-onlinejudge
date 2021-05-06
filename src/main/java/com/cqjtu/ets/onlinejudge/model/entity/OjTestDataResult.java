package com.cqjtu.ets.onlinejudge.model.entity;

public class OjTestDataResult {
    private Long testDataId;

    private Long detailId;

    private String testDataFilePath;

    private Integer matchingWordsNumber;

    private Integer lostWordsNumber;

    private Integer abundantWordsNumber;

    public OjTestDataResult() {
    }

    @Override
    public String toString() {
        return "OjTestDataResult{" +
                "testDataId=" + testDataId +
                ", detailId=" + detailId +
                ", testDataFilePath='" + testDataFilePath + '\'' +
                ", matchingWordsNumber=" + matchingWordsNumber +
                ", lostWordsNumber=" + lostWordsNumber +
                ", abundantWordsNumber=" + abundantWordsNumber +
                '}';
    }

    public Long getTestDataId() {
        return testDataId;
    }

    public void setTestDataId(Long testDataId) {
        this.testDataId = testDataId;
    }

    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    public String getTestDataFilePath() {
        return testDataFilePath;
    }

    public void setTestDataFilePath(String testDataFilePath) {
        this.testDataFilePath = testDataFilePath == null ? null : testDataFilePath.trim();
    }

    public Integer getMatchingWordsNumber() {
        return matchingWordsNumber;
    }

    public void setMatchingWordsNumber(Integer matchingWordsNumber) {
        this.matchingWordsNumber = matchingWordsNumber;
    }

    public Integer getLostWordsNumber() {
        return lostWordsNumber;
    }

    public void setLostWordsNumber(Integer lostWordsNumber) {
        this.lostWordsNumber = lostWordsNumber;
    }

    public Integer getAbundantWordsNumber() {
        return abundantWordsNumber;
    }

    public void setAbundantWordsNumber(Integer abundantWordsNumber) {
        this.abundantWordsNumber = abundantWordsNumber;
    }
}