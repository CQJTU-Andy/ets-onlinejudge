package com.cqjtu.ets.onlinejudge.model.entity;

public class OjTestDataResult {
    private Long test_data_id=-1L;

    private Long detail_id=-1L;

    private String test_data_file_path="none";

    private long line_number=0; //总行数

    private long hit_number=0; //命中行数

    private float score=-1; // 该组数据得分（满分100)

    public long getHit_number() {
        return hit_number;
    }

    public void setHit_number(long hit_number) {
        this.hit_number = hit_number;
    }

    public OjTestDataResult() {
    }

    public OjTestDataResult(long line_number, long hit_number, float score) {
        this.line_number = line_number;
        this.hit_number = hit_number;
        this.score = score;
    }

    public long getLine_number() {
        return line_number;
    }

    public void setLine_number(long line_number) {
        this.line_number = line_number;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public Long getTest_data_id() {
        return test_data_id;
    }

    public void setTest_data_id(Long test_data_id) {
        this.test_data_id = test_data_id;
    }

    public Long getDetail_id() {
        return detail_id;
    }

    public void setDetail_id(Long detail_id) {
        this.detail_id = detail_id;
    }

    public String getTest_data_file_path() {
        return test_data_file_path;
    }

    public void setTest_data_file_path(String test_data_file_path) {
        this.test_data_file_path = test_data_file_path == null ? null : test_data_file_path.trim();
    }
}