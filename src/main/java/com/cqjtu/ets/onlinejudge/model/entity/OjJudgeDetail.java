package com.cqjtu.ets.onlinejudge.model.entity;

public class OjJudgeDetail {
    private Long detail_id=-1L;

    private Long judge_id=-1L;

    private String judge_result="none";

    private float score=0;

    private String detail_result="none";

    private long used_memory=0;

    private Double used_real_time= (double) 0;

    private Double used_cpu_time= (double) 0;

    private long used_stack=0;

    private long used_output_size=0; // kbyte

    private long used_data=0;//数据段

    private long used_exe_size =0;//二进制文件大小

    public OjJudgeDetail() {
    }



    public long getUsed_stack() {
        return used_stack;
    }

    public void setUsed_stack(long used_stack) {
        this.used_stack = used_stack;
    }

    public long getUsed_output_size() {
        return used_output_size;
    }

    public void setUsed_output_size(long used_output_size) {
        this.used_output_size = used_output_size;
    }

    public long getUsed_data() {
        return used_data;
    }

    public void setUsed_data(long used_data) {
        this.used_data = used_data;
    }

    public long getUsed_exe_size() {
        return used_exe_size;
    }

    public void setUsed_exe_size(long used_exe_size) {
        this.used_exe_size = used_exe_size;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public Long getDetail_id() {
        return detail_id;
    }

    public void setDetail_id(Long detail_id) {
        this.detail_id = detail_id;
    }

    public Long getJudge_id() {
        return judge_id;
    }

    public void setJudge_id(Long judge_id) {
        this.judge_id = judge_id;
    }

    public String getJudge_result() {
        return judge_result;
    }

    public void setJudge_result(String judge_result) {
        this.judge_result = judge_result;
    }

    public String getDetail_result() {
        return detail_result;
    }

    public void setDetail_result(String detail_result) {
        this.detail_result = detail_result;
    }

    public long getUsed_memory() {
        return used_memory;
    }

    public void setUsed_memory(long used_memory) {
        this.used_memory = used_memory;
    }

    public Double getUsed_real_time() {
        return used_real_time;
    }

    public void setUsed_real_time(Double used_real_time) {
        this.used_real_time = used_real_time;
    }

    public Double getUsed_cpu_time() {
        return used_cpu_time;
    }

    public void setUsed_cpu_time(Double used_cpu_time) {
        this.used_cpu_time = used_cpu_time;
    }
}