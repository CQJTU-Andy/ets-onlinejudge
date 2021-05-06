package com.cqjtu.ets.onlinejudge.model.vo;

import java.io.Serializable;

/**
 * 用于评测页面视图的数据对象
 */
public class ExperimentInfoVO implements Serializable {
    // 实验信息
    private String experimentName;
    private String publishTime;
    private String endTime;

    // 题目信息
    private String problem_name;
    private String input_description;
    private String output_description;
    private String test_input_dataPath;
    private String test_output_dataPath;
    private String tips;
    private Integer cpu_time_limited;
    private Integer real_time_limited;
    private Integer memory_limited;
    private String problem_detail;

    public String getExperimentName() {
        return experimentName;
    }

    public void setExperimentName(String experimentName) {
        this.experimentName = experimentName;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getProblem_name() {
        return problem_name;
    }

    public void setProblem_name(String problem_name) {
        this.problem_name = problem_name;
    }

    public String getInput_description() {
        return input_description;
    }

    public void setInput_description(String input_description) {
        this.input_description = input_description;
    }

    public String getOutput_description() {
        return output_description;
    }

    public void setOutput_description(String output_description) {
        this.output_description = output_description;
    }

    public String getTest_input_dataPath() {
        return test_input_dataPath;
    }

    public void setTest_input_dataPath(String test_input_dataPath) {
        this.test_input_dataPath = test_input_dataPath;
    }

    public String getTest_output_dataPath() {
        return test_output_dataPath;
    }

    public void setTest_output_dataPath(String test_output_dataPath) {
        this.test_output_dataPath = test_output_dataPath;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
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
        this.problem_detail = problem_detail;
    }

    public ExperimentInfoVO() {
    }

    @Override
    public String toString() {
        return "ExperimentInfoVO{" +
                "experimentName='" + experimentName + '\'' +
                ", publishTime='" + publishTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", problem_name='" + problem_name + '\'' +
                ", input_description='" + input_description + '\'' +
                ", output_description='" + output_description + '\'' +
                ", test_input_dataPath='" + test_input_dataPath + '\'' +
                ", test_output_dataPath='" + test_output_dataPath + '\'' +
                ", tips='" + tips + '\'' +
                ", cpu_time_limited=" + cpu_time_limited +
                ", real_time_limited=" + real_time_limited +
                ", memory_limited=" + memory_limited +
                ", problem_detail='" + problem_detail + '\'' +
                '}';
    }
}
