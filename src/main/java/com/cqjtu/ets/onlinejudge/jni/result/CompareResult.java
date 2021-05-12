package com.cqjtu.ets.onlinejudge.jni.result;

public class CompareResult {
    // 0: ok
    // -1:error
    int statusCode;

    long matchingCharNum; // 与标准文本相匹配的字符数
    long lostCharNum; // 丢失的字符数
    long redundantCharNum; // 多余的字符数

    CompareResult(){
        statusCode = 0;
        matchingCharNum = lostCharNum = redundantCharNum = 0;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public long getMatchingCharNum() {
        return matchingCharNum;
    }

    public void setMatchingCharNum(long matchingCharNum) {
        this.matchingCharNum = matchingCharNum;
    }

    public long getLostCharNum() {
        return lostCharNum;
    }

    public void setLostCharNum(long lostCharNum) {
        this.lostCharNum = lostCharNum;
    }

    public long getRedundantCharNum() {
        return redundantCharNum;
    }

    public void setRedundantCharNum(long redundantCharNum) {
        this.redundantCharNum = redundantCharNum;
    }
}
