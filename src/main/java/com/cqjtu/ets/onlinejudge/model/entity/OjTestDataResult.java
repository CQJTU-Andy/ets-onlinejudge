package com.cqjtu.ets.onlinejudge.model.entity;

public class OjTestDataResult {
    private Long test_data_id=-1L;

    private Long detail_id=-1L;

    private String test_data_file_path="none";

    private Integer matching_words_number=0;

    private Integer lost_words_number=0;

    private Integer abundant_words_number=0;

    public OjTestDataResult() {
    }

    @Override
    public String toString() {
        return "OjTestDataResult{" +
                "testDataId=" + test_data_id +
                ", detailId=" + detail_id +
                ", testDataFilePath='" + test_data_file_path + '\'' +
                ", matchingWordsNumber=" + matching_words_number +
                ", lostWordsNumber=" + lost_words_number +
                ", abundantWordsNumber=" + abundant_words_number +
                '}';
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

    public Integer getMatching_words_number() {
        return matching_words_number;
    }

    public void setMatching_words_number(Integer matching_words_number) {
        this.matching_words_number = matching_words_number;
    }

    public Integer getLost_words_number() {
        return lost_words_number;
    }

    public void setLost_words_number(Integer lost_words_number) {
        this.lost_words_number = lost_words_number;
    }

    public Integer getAbundant_words_number() {
        return abundant_words_number;
    }

    public void setAbundant_words_number(Integer abundant_words_number) {
        this.abundant_words_number = abundant_words_number;
    }
}