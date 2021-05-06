package com.cqjtu.ets.onlinejudge.model.entity;

public class OjJudgeDetail {
    private Long detailId;

    private Long judgeId;

    private String judgeResult;

    private String detailResult;

    private Integer usedMemory;

    private Integer usedRealTime;

    private Integer usedCpuTime;

    public OjJudgeDetail() {
    }

    @Override
    public String toString() {
        return "OjJudgeDetail{" +
                "detailId=" + detailId +
                ", judgeId=" + judgeId +
                ", judgeResult='" + judgeResult + '\'' +
                ", detailResult='" + detailResult + '\'' +
                ", usedMemory=" + usedMemory +
                ", usedRealTime=" + usedRealTime +
                ", usedCpuTime=" + usedCpuTime +
                '}';
    }

    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    public Long getJudgeId() {
        return judgeId;
    }

    public void setJudgeId(Long judgeId) {
        this.judgeId = judgeId;
    }

    public String getJudgeResult() {
        return judgeResult;
    }

    public void setJudgeResult(String judgeResult) {
        this.judgeResult = judgeResult == null ? null : judgeResult.trim();
    }

    public String getDetailResult() {
        return detailResult;
    }

    public void setDetailResult(String detailResult) {
        this.detailResult = detailResult == null ? null : detailResult.trim();
    }

    public Integer getUsedMemory() {
        return usedMemory;
    }

    public void setUsedMemory(Integer usedMemory) {
        this.usedMemory = usedMemory;
    }

    public Integer getUsedRealTime() {
        return usedRealTime;
    }

    public void setUsedRealTime(Integer usedRealTime) {
        this.usedRealTime = usedRealTime;
    }

    public Integer getUsedCpuTime() {
        return usedCpuTime;
    }

    public void setUsedCpuTime(Integer usedCpuTime) {
        this.usedCpuTime = usedCpuTime;
    }
}