package com.cqjtu.ets.onlinejudge.model.entity;

import java.util.Date;
import java.util.Objects;

public class OjCommit {
    private Long commit_id=-1L;

    private Long experiment_id=-1L;

    private Long student_number_id=-1L;

    private Date commit_time=new Date();

    private String file_path="none";

    public OjCommit() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OjCommit ojCommit = (OjCommit) o;
        return Objects.equals(commit_id, ojCommit.commit_id) && Objects.equals(experiment_id, ojCommit.experiment_id) && Objects.equals(student_number_id, ojCommit.student_number_id) && Objects.equals(commit_time, ojCommit.commit_time) && Objects.equals(file_path, ojCommit.file_path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commit_id, experiment_id, student_number_id, commit_time, file_path);
    }

    @Override
    public String toString() {
        return "OjCommit{" +
                "commit_id=" + commit_id +
                ", experiment_id=" + experiment_id +
                ", student_number_id=" + student_number_id +
                ", commit_time=" + commit_time +
                ", file_path='" + file_path + '\'' +
                '}';
    }

    public Long getCommit_id() {
        return commit_id;
    }

    public void setCommit_id(Long commit_id) {
        this.commit_id = commit_id;
    }

    public Long getExperiment_id() {
        return experiment_id;
    }

    public void setExperiment_id(Long experiment_id) {
        this.experiment_id = experiment_id;
    }

    public Long getStudent_number_id() {
        return student_number_id;
    }

    public void setStudent_number_id(Long student_number_id) {
        this.student_number_id = student_number_id;
    }

    public Date getCommit_time() {
        return commit_time;
    }

    public void setCommit_time(Date commit_time) {
        this.commit_time = commit_time;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path == null ? null : file_path.trim();
    }
}