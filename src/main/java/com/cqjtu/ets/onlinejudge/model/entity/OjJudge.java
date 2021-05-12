package com.cqjtu.ets.onlinejudge.model.entity;

import java.util.Date;

public class OjJudge {
    private Long judge_id=-1L;

    private Long commit_id=-1L;

    private Date start_time=new Date();

    private Date end_time = new Date();

    private String program_path="none";

    private String output_path="none";

    private String comment="none";

    /**
     * 1.普通编译 2.make 3.cmake
     */
    private int compile_method=1;

    public OjJudge() {
    }

    public int getCompile_method() {
        return compile_method;
    }

    public void setCompile_method(int compile_method) {
        this.compile_method = compile_method;
    }

    @Override
    public String toString() {
        return "OjJudge{" +
                "judgeId=" + judge_id +
                ", commitId=" + commit_id +
                ", startTime=" + start_time +
                ", endTime=" + end_time +
                ", programPath='" + program_path + '\'' +
                ", outputPath='" + output_path + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }

    public Long getJudge_id() {
        return judge_id;
    }

    public void setJudge_id(Long judge_id) {
        this.judge_id = judge_id;
    }

    public Long getCommit_id() {
        return commit_id;
    }

    public void setCommit_id(Long commit_id) {
        this.commit_id = commit_id;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public String getProgram_path() {
        return program_path;
    }

    public void setProgram_path(String program_path) {
        this.program_path = program_path == null ? null : program_path.trim();
    }

    public String getOutput_path() {
        return output_path;
    }

    public void setOutput_path(String output_path) {
        this.output_path = output_path == null ? null : output_path.trim();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }
}