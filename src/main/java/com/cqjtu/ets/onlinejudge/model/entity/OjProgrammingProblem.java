package com.cqjtu.ets.onlinejudge.model.entity;

import java.util.Date;

public class OjProgrammingProblem {
    private Long problem_id;

    private String problem_name;

    private String input_description;

    private String output_description;

    private String test_input_dataPath;

    private String test_output_dataPath;

    private String tips;

    private Date created_time;

    private Integer cpu_time_limited;

    private Integer real_time_limited;

    private Integer memory_limited;

    private String problem_detail;

    public OjProgrammingProblem() {
    }

    @Override
    public String toString() {
        return "OjProgrammingProblem{" +
                "problemId=" + problem_id +
                ", problemName='" + problem_name + '\'' +
                ", inputDescription='" + input_description + '\'' +
                ", outputDescription='" + output_description + '\'' +
                ", testInputDataPath='" + test_input_dataPath + '\'' +
                ", testOutputDataPath='" + test_output_dataPath + '\'' +
                ", tips='" + tips + '\'' +
                ", createdTime=" + created_time +
                ", cpuTimeLimited=" + cpu_time_limited +
                ", realTimeLimited=" + real_time_limited +
                ", memoryLimited=" + memory_limited +
                ", problemDetail='" + problem_detail + '\'' +
                '}';
    }

    public Long getProblem_id() {
        return problem_id;
    }

    public void setProblem_id(Long problem_id) {
        this.problem_id = problem_id;
    }

    public String getProblem_name() {
        return problem_name;
    }

    public void setProblem_name(String problem_name) {
        this.problem_name = problem_name == null ? null : problem_name.trim();
    }

    public String getInput_description() {
        return input_description;
    }

    public void setInput_description(String input_description) {
        this.input_description = input_description == null ? null : input_description.trim();
    }

    public String getOutput_description() {
        return output_description;
    }

    public void setOutput_description(String output_description) {
        this.output_description = output_description == null ? null : output_description.trim();
    }

    public String getTest_input_dataPath() {
        return test_input_dataPath;
    }

    public void setTest_input_dataPath(String test_input_dataPath) {
        this.test_input_dataPath = test_input_dataPath == null ? null : test_input_dataPath.trim();
    }

    public String getTest_output_dataPath() {
        return test_output_dataPath;
    }

    public void setTest_output_dataPath(String test_output_dataPath) {
        this.test_output_dataPath = test_output_dataPath == null ? null : test_output_dataPath.trim();
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips == null ? null : tips.trim();
    }

    public Date getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Date created_time) {
        this.created_time = created_time;
    }

    public Integer getCpu_time_limited() {
        return cpu_time_limited;
    }

    public void setCpu_time_limited(Integer cpu_time_limited) {
        this.cpu_time_limited = cpu_time_limited;
    }

    public Integer getReal_time_limited() {
        return real_time_limited;
    }

    public void setReal_time_limited(Integer real_time_limited) {
        this.real_time_limited = real_time_limited;
    }

    public Integer getMemory_limited() {
        return memory_limited;
    }

    public void setMemory_limited(Integer memory_limited) {
        this.memory_limited = memory_limited;
    }

    public String getProblem_detail() {
        return problem_detail;
    }

    public void setProblem_detail(String problem_detail) {
        this.problem_detail = problem_detail == null ? null : problem_detail.trim();
    }
}