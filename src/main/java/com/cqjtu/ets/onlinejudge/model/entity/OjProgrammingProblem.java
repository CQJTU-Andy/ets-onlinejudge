package com.cqjtu.ets.onlinejudge.model.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class OjProgrammingProblem {
    private Long problem_id=-1L;

    private String problem_name="none";

    private String input_description="none";

    private String output_description="none";

    private String test_input_data_path ="/root/.ets/judge/problemPath/";

    private String test_output_data_path ="/root/.ets/judge/problemPath/";

    private String i_sample ="none";

    private String o_sample = "none";

    private String tips="none";

    private Date created_time = new Date();

    private Integer cpu_time_limited=0;

    private Integer real_time_limited=0;

    private Integer memory_limited=0;

    private String problem_detail="none";

    private int special_judge;//0:false 1:true

    private String sj_code_path="";

    private int is_compiled=0; //0:false 1:true

    private Date end_time = new Date();

    private String seccomp_filter_file_path="";

    public String getSeccomp_filter_file_path() {
        return seccomp_filter_file_path;
    }

    public void setSeccomp_filter_file_path(String seccomp_filter_file_path) {
        this.seccomp_filter_file_path = seccomp_filter_file_path;
    }

    public OjProgrammingProblem() {
    }

    public String getFormattedCreatedTime(){
        return new SimpleDateFormat("yyyy年MM月dd日 hh:mm  EE", Locale.CHINA).format(created_time);
    }

    public String getFormattedEndTime(){
        return new SimpleDateFormat("yyyy年MM月dd日 hh:mm  EE", Locale.CHINA).format(end_time);
    }

    public String getO_sample() {
        return o_sample;
    }

    public void setO_sample(String o_sample) {
        this.o_sample = o_sample;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public int getIs_compiled() {
        return is_compiled;
    }

    public void setIs_compiled(int is_compiled) {
        this.is_compiled = is_compiled;
    }

    public int getSpecial_judge() {
        return special_judge;
    }

    public void setSpecial_judge(int special_judge) {
        this.special_judge = special_judge;
    }

    public String getSj_code_path() {
        return sj_code_path;
    }

    public void setSj_code_path(String sj_code_path) {
        this.sj_code_path = sj_code_path;
    }

    public String getI_sample() {
        return i_sample;
    }

    public void setI_sample(String i_sample) {
        this.i_sample = i_sample;
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

    public String getTest_input_data_path() {
        return test_input_data_path;
    }

    public void setTest_input_data_path(String test_input_data_path) {
        this.test_input_data_path = test_input_data_path == null ? null : test_input_data_path.trim();
    }

    public String getTest_output_data_path() {
        return test_output_data_path;
    }

    public void setTest_output_data_path(String test_output_data_path) {
        this.test_output_data_path = test_output_data_path == null ? null : test_output_data_path.trim();
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