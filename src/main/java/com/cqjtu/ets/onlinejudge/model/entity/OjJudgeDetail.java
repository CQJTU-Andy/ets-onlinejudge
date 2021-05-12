package com.cqjtu.ets.onlinejudge.model.entity;

public class OjJudgeDetail {
    private Long detail_id=-1L;

    private Long judge_id=-1L;

    private String judge_result="none";

    private String detail_result="none";

    private long used_memory=0;

    private double used_real_time=0;

    private double used_cpu_time=0;

    public OjJudgeDetail() {
    }

    @Override
    public String toString() {
        return "OjJudgeDetail{" +
                "detailId=" + detail_id +
                ", judgeId=" + judge_id +
                ", judgeResult='" + judge_result + '\'' +
                ", detailResult='" + detail_result + '\'' +
                ", usedMemory=" + used_memory +
                ", usedRealTime=" + used_real_time +
                ", usedCpuTime=" + used_cpu_time +
                '}';
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

    public double getUsed_real_time() {
        return used_real_time;
    }

    public void setUsed_real_time(double used_real_time) {
        this.used_real_time = used_real_time;
    }

    public double getUsed_cpu_time() {
        return used_cpu_time;
    }

    public void setUsed_cpu_time(double used_cpu_time) {
        this.used_cpu_time = used_cpu_time;
    }
}