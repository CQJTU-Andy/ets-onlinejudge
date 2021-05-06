package com.cqjtu.ets.onlinejudge.model.entity;

import java.util.Date;

public class OjJudge {
    private Long judgeId;

    private Long commitId;

    private Date startTime;

    private Date endTime;

    private String programPath;

    private String outputPath;

    private String comment;

    public OjJudge() {
    }

    @Override
    public String toString() {
        return "OjJudge{" +
                "judgeId=" + judgeId +
                ", commitId=" + commitId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", programPath='" + programPath + '\'' +
                ", outputPath='" + outputPath + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }

    public Long getJudgeId() {
        return judgeId;
    }

    public void setJudgeId(Long judgeId) {
        this.judgeId = judgeId;
    }

    public Long getCommitId() {
        return commitId;
    }

    public void setCommitId(Long commitId) {
        this.commitId = commitId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getProgramPath() {
        return programPath;
    }

    public void setProgramPath(String programPath) {
        this.programPath = programPath == null ? null : programPath.trim();
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath == null ? null : outputPath.trim();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }
}