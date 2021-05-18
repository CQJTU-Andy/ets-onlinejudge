package com.cqjtu.ets.onlinejudge.jni.result;

import java.util.ArrayList;
import java.util.List;

/**
 * 一组数据文件的对比数据
 */
public class CompareResult {
    // 0: ok
    // -1:error
    int statusCode;

    List<CompareDetail> compareDetailList;
    String testFilePath; //数据文件全路径

    CompareResult(){
        statusCode = 0;
        compareDetailList = new ArrayList<>();
        testFilePath = "";
    }

    public String getTestFilePath() {
        return testFilePath;
    }

    public void setTestFilePath(String testFilePath) {
        this.testFilePath = testFilePath;
    }

    public void addCompareDetail(CompareDetail detail){
        compareDetailList.add(detail);
    }

    public List<CompareDetail> getCompareDetailList() {
        return compareDetailList;
    }

    public void setCompareDetailList(List<CompareDetail> compareDetailList) {
        this.compareDetailList = compareDetailList;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

}
