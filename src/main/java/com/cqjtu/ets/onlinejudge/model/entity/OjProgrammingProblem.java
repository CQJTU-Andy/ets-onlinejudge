package com.cqjtu.ets.onlinejudge.model.entity;

import java.util.Date;

public class OjProgrammingProblem {
    private Long problem_id=-1L;

    private String problem_name="none";

    private String input_description="none";

    private String output_description="none";

    private String test_input_data_Path ="none";

    private String test_output_data_Path ="none";

    private String io_sample="none";

    private String tips="none";

    private Date created_time=new Date();

    private Integer cpu_time_limited=0;

    private Integer real_time_limited=0;

    private Integer memory_limited=0;

    private String problem_detail="none";

    public OjProgrammingProblem() {
    }

    @Override
    public String toString() {
        return "OjProgrammingProblem{" +
                "problem_id=" + problem_id +
                ", problem_name='" + problem_name + '\'' +
                ", input_description='" + input_description + '\'' +
                ", output_description='" + output_description + '\'' +
                ", test_input_dataPath='" + test_input_data_Path + '\'' +
                ", test_output_dataPath='" + test_output_data_Path + '\'' +
                ", io_sample='" + io_sample + '\'' +
                ", tips='" + tips + '\'' +
                ", created_time=" + created_time +
                ", cpu_time_limited=" + cpu_time_limited +
                ", real_time_limited=" + real_time_limited +
                ", memory_limited=" + memory_limited +
                ", problem_detail='" + problem_detail + '\'' +
                '}';
    }

    public String getIo_sample() {
        return io_sample;
    }

    public void setIo_sample(String io_sample) {
        this.io_sample = io_sample;
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

    public String getTest_input_data_Path() {
        return test_input_data_Path;
    }

    public void setTest_input_data_Path(String test_input_data_Path) {
        this.test_input_data_Path = test_input_data_Path == null ? null : test_input_data_Path.trim();
    }

    public String getTest_output_data_Path() {
        return test_output_data_Path;
    }

    public void setTest_output_data_Path(String test_output_data_Path) {
        this.test_output_data_Path = test_output_data_Path == null ? null : test_output_data_Path.trim();
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